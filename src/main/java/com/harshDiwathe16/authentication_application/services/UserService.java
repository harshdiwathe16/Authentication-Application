
package com.harshDiwathe16.authentication_application.services;

import com.harshDiwathe16.authentication_application.dtos.UserDto;

public interface UserService
{
    UserDto createUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto updateUserById(UserDto userDto, String userId);

    void deleteUserById(String userId);

    Iterable<UserDto> getAllUsers();
}
