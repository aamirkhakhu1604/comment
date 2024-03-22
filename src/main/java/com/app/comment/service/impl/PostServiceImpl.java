package com.app.comment.service.impl;

import com.app.comment.exception.ResourceNotFoundException;
import com.app.comment.model.Post;
import com.app.comment.model.User;
import com.app.comment.payload.PostDto;
import com.app.comment.payload.PostResponse;
import com.app.comment.repository.PostRepo;
import com.app.comment.repository.UserRepo;
import com.app.comment.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

        Post post = dtoToPost(postDto);
        post.setUser(user);
        post.setCreatedAt(new Date());

        Post newPost = postRepo.save(post);

        return postToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

        postRepo.delete(post);

    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
        return postToDto(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Post> pagePost = postRepo.findAll(pageable);
        List<Post> allPosts = pagePost.getContent();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allPosts.stream().map(this::postToDto).toList());
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
        List<Post> allPosts = postRepo.findByUser(user);
        return allPosts.stream().map(this::postToDto).toList();
    }

    @Override
    public void addLikeToPost(Long userId, Long postId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

        post.setLikes(post.getLikes() == null ? 1 : post.getLikes() + 1);

        postRepo.save(post);
    }

    @Override
    public void addDislikeToPost(Long userId, Long postId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

        post.setLikes(post.getDislikes() == null ? 1 : post.getDislikes() + 1);
        postRepo.addDislike(postId);
    }

    private PostDto postToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private Post dtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

}
