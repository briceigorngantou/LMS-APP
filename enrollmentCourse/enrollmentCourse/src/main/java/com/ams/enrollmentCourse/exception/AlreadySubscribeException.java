package com.ams.enrollmentCourse.exception;

public class AlreadySubscribeException extends Exception {
    private String message;

    public AlreadySubscribeException(String message) {
        super(message);
        this.message = message;
    }

}
