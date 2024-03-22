package com.app.comment.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TopLevelCommentsDto {
    private Long id;
    private String body;
    private UserDto user;
}
