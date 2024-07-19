package org.example.e_learningback.service.impl;

public class CourseSessionNotFoundException extends RuntimeException {
    public CourseSessionNotFoundException(String message) {
        super(message);
    }
}
