package com.app.comment.service.impl;

import com.app.comment.model.UserLikedComments;
import com.app.comment.payload.UserDto;
import com.app.comment.repository.UserLikedCommentsRepo;
import com.app.comment.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommentServiceImpl implements UserCommentService {
    @Autowired
    private UserLikedCommentsRepo userLikedCommentsRepo;

    @Override
    public List<UserDto> getAllUserByCommentId(Long commentId) {
        return null;
    }

    @Override
    public void createUserComment(UserLikedComments userLikedComments) {
        userLikedCommentsRepo.save(userLikedComments);
    }
}
