
package com.harshDiwathe16.authentication_application.services;

import com.harshDiwathe16.authentication_application.dtos.UserDto;

import java.util.UUID;

public interface UserService
{
    UserDto createUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto getUserById(String userId);

    UserDto updateUserById(UserDto userDto, String userId);

    void deleteUserById(String userId);

    Iterable<UserDto> getAllUsers();
}
