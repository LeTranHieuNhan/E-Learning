package org.example.e_learningback.exception;

public class StudentSessionNotFoundException extends RuntimeException {
    public StudentSessionNotFoundException(String message) {
        super(message);
    }
}
