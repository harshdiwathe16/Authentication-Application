package com.harshDiwathe16.authentication_application.services.implementation;

import com.harshDiwathe16.authentication_application.dtos.UserDto;
import com.harshDiwathe16.authentication_application.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService
{
    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto)
    {
        /// Password will be save in Database im encoded pattern
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userServiceImpl.createUser(userDto);
    }
}
