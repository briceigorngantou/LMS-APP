package com.ams.enrollmentCourse.exception;

public class CoursesNotFoundException extends Exception {

    private String message;

    /**
     * @param message
     */
    public CoursesNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public CoursesNotFoundException() {
    }
}
