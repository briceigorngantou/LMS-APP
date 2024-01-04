package com.ams.courses.exception;

public class CoursesNotFoundException extends Exception {

    private String message;

    public CoursesNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public CoursesNotFoundException() {
    }
}
