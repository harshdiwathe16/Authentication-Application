package com.harshDiwathe16.authentication_application.exceptions;

public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(String message)
    {
        super(message);
    }

    public ResourceNotFoundException()
    {
        super("Resource Not Found !");
    }
}
