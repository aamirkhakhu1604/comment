package com.app.comment.service.impl;

import com.app.comment.exception.ResourceNotFoundException;
import com.app.comment.model.User;
import com.app.comment.payload.UserDto;
import com.app.comment.repository.UserRepo;
import com.app.comment.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        User saved = userRepo.save(user);
        return userToDto(saved);
    }

    @Override
    public UserDto updateUser(UserDto user, Long userId) {
        return null;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        return allUsers.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        userRepo.delete(user);
    }

    private UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User dtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }


}
