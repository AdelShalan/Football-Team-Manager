package com.teamanager.contract.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleTeamNotFoundException(TeamNotFoundException teamNotFoundException) {
        log.error("Couldn't find team!!", teamNotFoundException);
        return buildErrorResponse(teamNotFoundException, teamNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestCallException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ResponseEntity<Object> handleRestCallException(RestCallException restCallException) {
        log.error("Problem connecting to another service!!", restCallException);
        return buildErrorResponse(restCallException, restCallException.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message,
                                                      HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(message, httpStatus, ZonedDateTime.now());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
