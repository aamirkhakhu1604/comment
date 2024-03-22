package com.app.comment.controllers;

import com.app.comment.payload.PostDto;
import com.app.comment.payload.PostResponse;
import com.app.comment.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Long userId) {
        PostDto createdPost = postService.createPost(postDto, userId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}/posts/{postId}/likes")
    public ResponseEntity<?> addLikeToPost(@PathVariable Long userId,@PathVariable Long postId) throws Exception {
        postService.addLikeToPost(userId, postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/users/{userId}/posts/{postId}/dislikes")
    public ResponseEntity<?> addDislikeToPost(@PathVariable Long userId,@PathVariable Long postId) throws Exception {
        postService.addDislikeToPost(userId, postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
        List<PostDto> allPosts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(allPosts);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
            ) {

        return ResponseEntity.ok(postService.getAllPost(pageNumber, pageSize));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
