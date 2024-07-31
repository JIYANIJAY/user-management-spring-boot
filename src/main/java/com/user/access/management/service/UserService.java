package com.user.access.management.service;

import com.user.access.management.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    void deleteUser(Long id);

    List<UserDto> getAllUsers();
}
