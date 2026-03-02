package com.harshDiwathe16.authentication_application.security;

import com.harshDiwathe16.authentication_application.entity.User;
import com.harshDiwathe16.authentication_application.exceptions.ResourceNotFoundException;
import com.harshDiwathe16.authentication_application.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService
{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findByEmail(username).orElseThrow(()-> new BadCredentialsException("Invalid Email Or Password"));
    }
}
