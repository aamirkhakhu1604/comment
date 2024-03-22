package com.app.comment.service;

import com.app.comment.payload.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Long userId);
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers();
    void deleteUser(Long userId);

}
