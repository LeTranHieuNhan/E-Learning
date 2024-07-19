package org.example.e_learningback.exception;

public class UserAlreadyEnrolledException extends RuntimeException {
    public UserAlreadyEnrolledException(String message) {
        super(message);
    }
}
