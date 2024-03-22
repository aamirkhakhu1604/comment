package com.app.comment.service;

import com.app.comment.payload.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void testCommentFlow() {
        UserDto userDto = new UserDto();
        userDto.setName("test-user");
        userService.createUser(userDto);
    }
}
