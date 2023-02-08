package com.teamanager.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsernameAndPasswordAuthenticationRequest {

    private String username;
    private String password;


}
