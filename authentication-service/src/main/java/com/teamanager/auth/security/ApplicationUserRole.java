package com.teamanager.auth.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.teamanager.auth.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    PLAYER(Sets.newHashSet(PLAYER_READ, PLAYER_WRITE)),
    MANAGER(Sets.newHashSet(TEAM_READ, TEAM_WRITE, PLAYER_READ)),
    ADMIN(Sets.newHashSet(TEAM_READ, TEAM_WRITE, PLAYER_READ, PLAYER_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
