package com.harshDiwathe16.authentication_application.dtos;

import com.harshDiwathe16.authentication_application.entity.Provider;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto
{
    private UUID id;
    
    private String email;

    private String name;

    private String password;

    private String image;

    private boolean enable = true;

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    private Provider provider = Provider.LOCAL;

    private Set<RolesDto> roles = new HashSet<>();

}
