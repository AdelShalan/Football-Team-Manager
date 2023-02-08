package com.teamanager.auth.jwt;

import com.teamanager.auth.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequest {

    private String username;
    private String password;
    private ApplicationUserRole role;
}
