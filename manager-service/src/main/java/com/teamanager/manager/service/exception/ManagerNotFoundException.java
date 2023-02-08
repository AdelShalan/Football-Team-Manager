package com.teamanager.manager.service.exception;

public class ManagerNotFoundException extends RuntimeException {

    public ManagerNotFoundException(String message) {
        super(message);
    }
}
