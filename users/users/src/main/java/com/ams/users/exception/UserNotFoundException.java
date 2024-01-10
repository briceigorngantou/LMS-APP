package com.ams.users.exception;

public class UserNotFoundException extends Exception {

    private String message;

    /**
     * @param message
     */
    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public UserNotFoundException() {
    }
}
