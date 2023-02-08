package com.teamanager.auth.authentication;

import com.teamanager.auth.jwt.RegistrationRequest;

import java.util.Optional;

public interface ApplicationUserDAO {

    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
    void addApplicationUser(RegistrationRequest request);
}
