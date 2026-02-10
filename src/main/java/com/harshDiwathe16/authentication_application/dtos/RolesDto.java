package com.harshDiwathe16.authentication_application.dtos;

import jakarta.persistence.Column;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RolesDto
{
    private UUID id;

    private String roleName;
}
