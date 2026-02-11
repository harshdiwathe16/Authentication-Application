package com.harshDiwathe16.authentication_application.services.implementation;

import com.harshDiwathe16.authentication_application.dtos.UserDto;
import com.harshDiwathe16.authentication_application.entity.Provider;
import com.harshDiwathe16.authentication_application.entity.User;
import com.harshDiwathe16.authentication_application.exceptions.ResourceNotFoundException;
import com.harshDiwathe16.authentication_application.helpers.UserHelper;
import com.harshDiwathe16.authentication_application.repositories.UserRepository;
import com.harshDiwathe16.authentication_application.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserHelper userHelper;

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
    public UserDto getUserById(String userId)
    {
        UUID uId = userHelper.parseUUID(userId);

        User user = userRepository.findById(uId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserById(UserDto userDto, String userId)
    {
        UUID uId = userHelper.parseUUID(userId);

        User existingUser = userRepository.findById(uId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

        if (userDto.getName() != null) existingUser.setName(userDto.getName());
        if (userDto.getImage() != null) existingUser.setImage(userDto.getImage());
        if (userDto.getProvider() != null) existingUser.setProvider(userDto.getProvider());
        if (userDto.getPassword() != null) existingUser.setPassword(userDto.getPassword());

        existingUser.setEnable(userDto.isEnable());
        existingUser.setUpdatedAt(Instant.now());

        User updateduser = userRepository.save(existingUser);

        return modelMapper.map(updateduser,UserDto.class);
    }

    @Override
    public void deleteUserById(String userId)
    {
        UUID uId = userHelper.parseUUID(userId);

        User user = userRepository.findById(uId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers()
    {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
