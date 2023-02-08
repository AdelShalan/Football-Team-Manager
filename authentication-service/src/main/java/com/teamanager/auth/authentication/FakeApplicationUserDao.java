package com.teamanager.auth.authentication;

import com.google.common.collect.Lists;
import com.teamanager.auth.jwt.RegistrationRequest;
import com.teamanager.auth.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Repository("fake")
public class FakeApplicationUserDao implements ApplicationUserDAO {
    private final PasswordEncoder passwordEncoder;
    private static List<ApplicationUser> applicationUsers;
    @Autowired
    public FakeApplicationUserDao(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct //post construct executed once after bean constructor
    private void init(){
        applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "annasmith",
                        passwordEncoder.encode("pass"),
                        ApplicationUserRole.PLAYER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "linda",
                        passwordEncoder.encode("pass"),
                        ApplicationUserRole.MANAGER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("pass"),
                        ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    public void addApplicationUser(RegistrationRequest request) {
        getApplicationUsers().add(
                new ApplicationUser(
                        request.getUsername().trim(),
                        passwordEncoder.encode(request.getPassword()),
                        request.getRole().getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
        ));
    }
    private List<ApplicationUser> getApplicationUsers() {
        return applicationUsers;
    }
}