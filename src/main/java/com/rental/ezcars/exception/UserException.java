package com.rental.ezcars.exception;

public class UserException extends RuntimeException {
    
    private final UserExceptionType type;

    public UserException(String message, UserExceptionType type) {
        super(message);
        this.type = type;
    }

    public UserExceptionType getType() {
        return type;
    }

    public enum UserExceptionType {
        USER_NOT_FOUND,
        USER_ALREADY_EXISTS,
        USER_CREATION_ERROR,
        USER_UPDATE_ERROR,
        USER_DELETION_ERROR,
        NO_USERS_FOUND
    }
}