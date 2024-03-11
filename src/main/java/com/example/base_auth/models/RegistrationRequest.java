package com.example.base_auth.models;

import lombok.Data;

@Data
public class RegistrationRequest {
    public String firstName;
    public String lastName;
    public String username;
    public String password;
}
