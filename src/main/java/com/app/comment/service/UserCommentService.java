package com.app.comment.service;

import com.app.comment.model.UserLikedComments;
import com.app.comment.payload.UserDto;

import java.util.List;

public interface UserCommentService {

    List<UserDto> getAllUserByCommentId(Long commentId);

    void createUserComment(UserLikedComments userLikedComments);
}
