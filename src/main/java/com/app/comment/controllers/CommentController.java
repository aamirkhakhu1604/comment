package com.app.comment.controllers;

import com.app.comment.model.User;
import com.app.comment.model.UserLikedComments;
import com.app.comment.payload.ApiResponse;
import com.app.comment.payload.CommentDto;
import com.app.comment.payload.CommentResponse;
import com.app.comment.payload.UserLikeResponse;
import com.app.comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /*
    Create child comment or reply by user on post and reply to comment
    */
    @PostMapping("/users/{userId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentDto commentDto,
            @PathVariable Long userId,
            @PathVariable Long postId,
            @PathVariable(required = false) Long commentId) {
        CommentDto createdComment = commentService.createComment(commentDto, userId, postId, commentId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    /*
    Create comment by user on post
    */
    @PostMapping("/users/{userId}/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentDto commentDto,
            @PathVariable Long userId,
            @PathVariable Long postId) {
        CommentDto createdComment = commentService.createComment(commentDto, userId, postId, null);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment delete successfully", true), HttpStatus.OK);
    }

    /*
    Get all the comments with their replies
    */
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(commentId), HttpStatus.OK);
    }

    /*
    Get top level comment only for a particular post
    */
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> getTopLevelCommentsForPost(
            @PathVariable Long postId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
    ) {
        return new ResponseEntity<>(commentService.getTopLevelComments(postId, pageNumber, pageSize), HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/comments/{commentId}/likes")
    public ResponseEntity<?> addLikeToPost(@PathVariable Long userId,@PathVariable Long commentId) throws Exception {
        commentService.addLikeToComment(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/users/{userId}/comments/{commentId}/dislikes")
    public ResponseEntity<?> addDislikeToPost(@PathVariable Long userId,@PathVariable Long commentId) throws Exception {
        commentService.addDislikeToComment(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/comments/{commentId}/likes")
    public ResponseEntity<UserLikeResponse> getAllUsersLikeComments(@PathVariable Long commentId) throws Exception {
        return new ResponseEntity<>(commentService.getAllUserLikedComments(commentId), HttpStatus.OK);
    }



}
