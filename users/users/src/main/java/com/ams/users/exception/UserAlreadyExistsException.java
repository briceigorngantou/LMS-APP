package com.ams.users.exception;


public class UserAlreadyExistsException extends Exception {
    private String message;

    /**
     * @param message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

}
