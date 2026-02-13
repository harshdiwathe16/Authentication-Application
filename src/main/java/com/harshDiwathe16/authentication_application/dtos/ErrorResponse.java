package com.harshDiwathe16.authentication_application.dtos;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
    String message,
    HttpStatus status)
{


}

