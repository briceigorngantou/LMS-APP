package com.ams.courses.exception;


public class CoursesAlreadyExistsException extends Exception {
    private String message;

    public CoursesAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

}
