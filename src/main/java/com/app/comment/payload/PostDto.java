package com.app.comment.payload;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long postId;
    private String title;
    private String body;
    private Date createdAt;
    private UserDto user;
    //private Set<CommentDto> comments;
    private Integer likes;
    private Integer dislikes;
}
