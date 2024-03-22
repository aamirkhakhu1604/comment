package com.app.comment.repository;

import com.app.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    @Query(nativeQuery = true, value = "select * from comment where post_post_id = :postId and parent_id IS NULL")
    Page<Comment> findByParentIsNull(@Param("postId") Long postId, Pageable pageable);
}
