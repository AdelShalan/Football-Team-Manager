package com.teamanager.contract.service.VO;

public enum Position {
    ST("Striker"),
    WG("Winger"),
    MD("Midfielder"),
    DF("Defender"),
    GK("Goalkeeper"),
    NA("Unknown");

    private final String position;
    Position(String position) {
        this.position = position;
    }
}
