package com.harshDiwathe16.authentication_application.services;

import com.harshDiwathe16.authentication_application.dtos.UserDto;

public interface AuthService
{
    UserDto registerUser(UserDto userDto);
}
