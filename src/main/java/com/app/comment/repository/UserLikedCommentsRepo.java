package com.app.comment.repository;

import com.app.comment.model.Comment;
import com.app.comment.model.Post;
import com.app.comment.model.User;
import com.app.comment.model.UserLikedComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLikedCommentsRepo extends JpaRepository<UserLikedComments, Long> {

    List<UserLikedComments> findAllByComment(Comment comment);

    UserLikedComments findByUserAndComment(User user, Comment comment);
}
