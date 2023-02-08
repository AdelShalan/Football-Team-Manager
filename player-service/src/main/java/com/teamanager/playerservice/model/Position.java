package com.teamanager.playerservice.model;

public enum Position {
    ST("Striker"),
    WG("Winger"),
    MD("Midfielder"),
    DF("Defender"),
    GK("Goalkeeper");

    private final String position;
    Position(String position) {
        this.position = position;
    }
}
