package com.teamanager.contract.service.exception;

public class RestCallException extends RuntimeException {
    public RestCallException(String message) {
        super(message);
    }
}
