package com.harshDiwathe16.authentication_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig
{
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
//    {
//        http.authorizeHttpRequests(authorizeHttpRequests ->
//                );
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


//    public UserDetailsService users()
//    {
//        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//
//        UserDetails user1 = userBuilder.username("Harsh").password("Harsh").roles("Admin").build();
//
//        return new InMemoryUserDetailsManager(user1);
//    }
}
