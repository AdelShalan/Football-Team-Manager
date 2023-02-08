package com.teamanager.auth.controller;

import com.teamanager.auth.authentication.ApplicationUser;
import com.teamanager.auth.authentication.ApplicationUserService;
import com.teamanager.auth.jwt.JwtUtil;
import com.teamanager.auth.jwt.RegistrationRequest;
import com.teamanager.auth.jwt.UsernameAndPasswordAuthenticationRequest;
import com.teamanager.auth.security.ApplicationUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final ApplicationUserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UsernameAndPasswordAuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        final UserDetails user = userService.loadUserByUsername(request.getUsername());

        if (user != null) {
            return ResponseEntity.ok(jwtUtil.generateToken(user));
        }
        return ResponseEntity.status(400).body("Authentication Error!");
    }

    @PostMapping("/register")
    public ResponseEntity<String> getRegister(@RequestBody RegistrationRequest request) {
        userService.addApplicationUser(request);
        return ResponseEntity.status(200).body("registered user successfully, please try to login!");
    }
}
