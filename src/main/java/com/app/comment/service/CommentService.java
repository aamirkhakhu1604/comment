package com.app.comment.service;

import com.app.comment.model.User;
import com.app.comment.model.UserLikedComments;
import com.app.comment.payload.CommentDto;
import com.app.comment.payload.CommentResponse;
import com.app.comment.payload.UserDto;
import com.app.comment.payload.UserLikeResponse;

import java.util.List;
import java.util.Set;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Long userId, Long postId, Long commentId);

    void deleteComment(Long commentId);

    CommentDto getCommentById(Long commentId);

    CommentResponse getTopLevelComments(Long postId, Integer pageNumber, Integer pageSize);

    void addLikeToComment(Long userId, Long commentId);
    void addDislikeToComment(Long userId, Long commentId);

    UserLikeResponse getAllUserLikedComments(Long commentId);

}
