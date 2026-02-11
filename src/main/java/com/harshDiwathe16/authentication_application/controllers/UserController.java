package com.harshDiwathe16.authentication_application.controllers;

import com.harshDiwathe16.authentication_application.dtos.UserDto;
import com.harshDiwathe16.authentication_application.services.implementation.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController
{

    private final UserServiceImpl userServiceImpl;

    /// CREATE USER
    @PostMapping("/create-user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceImpl.createUser(userDto));
    }

    /// GET ALL USERS
    @GetMapping("/get-all-users")
    public ResponseEntity<Iterable<UserDto>> getAllUsers()
    {
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }

    /// GET USER BY EMAIL
    @GetMapping("/email/{email}")
    public  ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(userServiceImpl.getUserByEmail(email));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId)
    {
        return ResponseEntity.ok(userServiceImpl.getUserById(userId));
    }

    /// UPDATE USER BY EMAIL
    @PutMapping("update/{userId}")
    public ResponseEntity<UserDto> updateUserByEmail(@RequestBody UserDto userDto, @PathVariable("userId") String userId)
    {
        return ResponseEntity.ok(userServiceImpl.updateUserById(userDto,userId));
    }

    /// DELETE USER BY ID
    @DeleteMapping("/delete/{userId}")
    public void deleteUserById(@PathVariable("userId") String userId)
    {
        userServiceImpl.deleteUserById(userId);
    }

}
