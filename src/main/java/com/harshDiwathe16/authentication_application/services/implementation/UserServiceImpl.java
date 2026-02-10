package com.harshDiwathe16.authentication_application.services.implementation;

import com.harshDiwathe16.authentication_application.dtos.UserDto;
import com.harshDiwathe16.authentication_application.entity.User;
import com.harshDiwathe16.authentication_application.repositories.UserRepository;
import com.harshDiwathe16.authentication_application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto)
    {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty())
        {
            throw new  IllegalArgumentException("Email is Required");
        }

        if (userRepository.existsByEmail(userDto.getEmail())){
            throw new IllegalArgumentException("Email Already Exists");
        }

        User user = modelMapper.map(userDto, User.class);


        return null;

    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDto updateUserById(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public void deleteUserById(String userId) {

    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        return null;
    }
}
