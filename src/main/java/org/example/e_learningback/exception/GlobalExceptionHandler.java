package org.example.e_learningback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyEnrolledException.class)
    public ResponseEntity<Void> handleUserAlreadyEnrolledException(UserAlreadyEnrolledException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
