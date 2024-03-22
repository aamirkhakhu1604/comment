package com.app.comment.payload;

import com.app.comment.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;
    private String body;
    private UserDto user;
    // Response is in loop
    //private CommentDto parent;
    private Set<CommentDto> replies;
    private Integer likes;
    private Integer dislikes;
}
