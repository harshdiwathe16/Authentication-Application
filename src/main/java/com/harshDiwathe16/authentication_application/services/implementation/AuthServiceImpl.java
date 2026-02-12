package com.harshDiwathe16.authentication_application.services.implementation;

import com.harshDiwathe16.authentication_application.dtos.UserDto;
import com.harshDiwathe16.authentication_application.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService
{
    private final UserServiceImpl userServiceImpl;

    @Override
    public UserDto registerUser(UserDto userDto)
    {
        UserDto userDto1 = userServiceImpl.createUser(userDto);
        return userDto1;
    }
}
