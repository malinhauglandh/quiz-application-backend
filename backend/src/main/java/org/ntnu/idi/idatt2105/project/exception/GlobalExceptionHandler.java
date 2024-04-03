package org.ntnu.idi.idatt2105.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Global exception handler for the application. */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles InvalidTokenException.
     *
     * @param ex The exception that was thrown
     * @return A response entity with the exception message and status code to the client
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    /**
     * Handles Exception.
     *
     * @param ex The exception that was thrown
     * @return A response entity with the exception message and status code to the client
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    /**
     * Handles ExistingUserException.
     *
     * @param ex The exception that was thrown
     * @return A response entity with the exception message and status code to the client
     */
    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<Object> handleExistingUserException(ExistingUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}