package com.teamanager.auth.security;

public enum ApplicationUserPermission {
    TEAM_READ("team:read"),
    TEAM_WRITE("team:write"),
    PLAYER_READ("player:read"),
    PLAYER_WRITE("player:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
