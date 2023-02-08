package com.teamanager.manager.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlePlayerNotFoundException(ManagerNotFoundException managerNotFoundException) {
        log.error("Couldn't find player!!", managerNotFoundException);
        return buildErrorResponse(managerNotFoundException, managerNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HasTeamException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleHasTeamException(HasTeamException hasTeamException) {
        log.error("Player already has a team!!", hasTeamException);
        return buildErrorResponse(hasTeamException, hasTeamException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RestCallException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ResponseEntity<Object> handleRestCallException(RestCallException restCallException) {
        log.error("Problem connecting to another service!!", restCallException);
        return buildErrorResponse(restCallException, restCallException.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    //VERY GENERIC ExceptionHandler ONLY FOR DEVELOPMENT PURPOSES
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundCallException(HttpClientErrorException httpClientErrorException) {
        log.error("Problem fetching data from another service!!", httpClientErrorException);
        return buildErrorResponse(httpClientErrorException, httpClientErrorException.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message,
                                                      HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(message, httpStatus, ZonedDateTime.now());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}
