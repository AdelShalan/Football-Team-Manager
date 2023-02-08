package com.teamanager.playerservice.exception;

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

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlePlayerNotFoundException(PlayerNotFoundException playerNotFoundException) {
        log.error("Couldn't find player!!", playerNotFoundException);
        return buildErrorResponse(playerNotFoundException, playerNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleOfferNotFoundException(OfferNotFoundException offerNotFoundException) {
        log.error("Couldn't find offer!!", offerNotFoundException);
        return buildErrorResponse(offerNotFoundException, offerNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
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

    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(message, httpStatus, ZonedDateTime.now());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}
