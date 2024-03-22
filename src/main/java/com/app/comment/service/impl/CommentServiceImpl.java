package com.app.comment.service.impl;

import com.app.comment.exception.ResourceNotFoundException;
import com.app.comment.model.Comment;
import com.app.comment.model.Post;
import com.app.comment.model.User;
import com.app.comment.model.UserLikedComments;
import com.app.comment.payload.*;
import com.app.comment.repository.CommentRepo;
import com.app.comment.repository.PostRepo;
import com.app.comment.repository.UserLikedCommentsRepo;
import com.app.comment.repository.UserRepo;
import com.app.comment.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserLikedCommentsRepo userLikedCommentsRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long userId, Long postId, Long commentId) {

        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));

        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));

        Comment parentComment = null;

        if (commentId != null) {
             parentComment = commentRepo.findById(commentId).orElseThrow(() ->
                    new ResourceNotFoundException("Comment", "id", commentId));

        }

        Comment comment = dtoToComment(commentDto);
        comment.setPost(post);
        comment.setUser(user);
        comment.setParent(parentComment);

        Comment savedComment = commentRepo.save(comment);
        return commentToDto(savedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        commentRepo.deleteById(comment.getId());
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        return commentToDto(comment);
    }

    @Override
    public CommentResponse getTopLevelComments(Long postId, Integer pageNumber, Integer pageSize) {
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Comment> pageTopLevelComment = commentRepo.findByParentIsNull(postId, pageable);
        List<Comment> allComments = pageTopLevelComment.getContent();

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(allComments.stream().map(this::commentToTopLevelComments).toList());
        commentResponse.setPageNumber(pageTopLevelComment.getNumber());
        commentResponse.setPageSize(pageTopLevelComment.getSize());
        commentResponse.setTotalElements(pageTopLevelComment.getTotalElements());
        commentResponse.setTotalPages(pageTopLevelComment.getTotalPages());
        commentResponse.setLastPage(pageTopLevelComment.isLast());

        return commentResponse;

    }

    @Override
    public void addLikeToComment(Long userId, Long commentId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        UserLikedComments userLikedCommentsEntity = userLikedCommentsRepo.findByUserAndComment(user, comment);
        if (userLikedCommentsEntity != null) {
            return;
        }
        comment.setLikes(comment.getLikes() == null ? 1 : comment.getLikes() + 1);
        commentRepo.save(comment);

        // can move to different service
        UserLikedComments userLikedComments = new UserLikedComments();
        userLikedComments.setComment(comment);
        userLikedComments.setUser(user);
        userLikedCommentsRepo.save(userLikedComments);

    }

    @Override
    public void addDislikeToComment(Long userId, Long commentId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setDislikes(comment.getDislikes() == null ? 1 : comment.getDislikes() + 1);
        commentRepo.save(comment);

    }

    @Override
    public UserLikeResponse getAllUserLikedComments(Long commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        List<UserLikedComments> userLikedCommentsList = userLikedCommentsRepo.findAllByComment(comment);
        List<User> allUser = new ArrayList<>();
        userLikedCommentsList.forEach(e -> allUser.add(e.getUser()));

        UserLikeResponse userLikeResponse = new UserLikeResponse();
        userLikeResponse.setId(commentId);
        userLikeResponse.setUsers(allUser.stream().map(this::userToDto).collect(Collectors.toList()));
        return userLikeResponse;
    }

    private CommentDto commentToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    private TopLevelCommentsDto commentToTopLevelComments(Comment comment) {
        return modelMapper.map(comment, TopLevelCommentsDto.class);
    }

    private Comment dtoToComment(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
    private UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }




}
