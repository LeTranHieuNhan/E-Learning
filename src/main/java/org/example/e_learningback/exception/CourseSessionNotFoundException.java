package org.example.e_learningback.exception;

public class CourseSessionNotFoundException extends RuntimeException {
    public CourseSessionNotFoundException(String message) {
        super(message);
    }
}
