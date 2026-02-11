package com.harshDiwathe16.authentication_application.services.implementation;

import com.harshDiwathe16.authentication_application.dtos.UserDto;
import com.harshDiwathe16.authentication_application.entity.Provider;
import com.harshDiwathe16.authentication_application.entity.User;
import com.harshDiwathe16.authentication_application.exceptions.ResourceNotFoundException;
import com.harshDiwathe16.authentication_application.repositories.UserRepository;
import com.harshDiwathe16.authentication_application.services.UserService;
import jakarta.transaction.Transactional;
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
    @Transactional
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
        user.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);
        User savedUser = userRepository.save(user);



        return modelMapper.map(savedUser, UserDto.class);

    }

    @Override
    public UserDto getUserByEmail(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("User not found with given User mail ID"));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserById(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public void deleteUserById(String userId) {

    }

    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
