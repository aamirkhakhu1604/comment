package com.app.comment.service;

import com.app.comment.payload.PostDto;
import com.app.comment.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Long userId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);
    PostDto getPostById(Long postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize);
    List<PostDto> getPostsByUser(Long userId);

    void addLikeToPost(Long userId, Long postId);
    void addDislikeToPost(Long userId, Long postId);

}
