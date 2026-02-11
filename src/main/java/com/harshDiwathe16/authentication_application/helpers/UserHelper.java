package com.harshDiwathe16.authentication_application.helpers;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserHelper
{
    public static UUID parseUUID(String uuid)
    {
        return UUID.fromString(uuid);
    }
}
