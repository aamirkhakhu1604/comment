package com.app.comment.controllers;

import com.app.comment.payload.ApiResponse;
import com.app.comment.payload.UserDto;
import com.app.comment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        UserDto returnedUser = userService.getUserById(userId);
        return ResponseEntity.ok(returnedUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> returnedUsers = userService.getAllUsers();
        return ResponseEntity.ok(returnedUsers);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
    }
}
