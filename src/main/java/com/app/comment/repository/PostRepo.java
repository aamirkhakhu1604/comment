package com.app.comment.repository;

import com.app.comment.model.Post;
import com.app.comment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long>  {

    List<Post> findByUser(User user);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE posts p SET likes = likes + 1 WHERE p.post_id = :postId")
    void addLike(@Param("postId") Long postId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE posts p SET dislikes = dislikes + 1 WHERE p.post_id = :postId")
    void addDislike(@Param("postId") Long postId);

}
