package com.ecommerce.service;

import com.ecommerce.dto.UserLoginDto;
import com.ecommerce.dto.UserRegisterDto;
import com.ecommerce.dto.UserResponseDto;
import com.ecommerce.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDto register(UserRegisterDto dto);
    UserResponseDto login(UserLoginDto dto);
    UserResponseDto getUserById(Long id);
    User updateUser(Long id, User userDetails);
    List<UserResponseDto> getAllUsers();
    void deleteUser(Long id);
}
