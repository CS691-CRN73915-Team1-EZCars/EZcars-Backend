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
        NO_USERS_FOUND,
        INVALID_CREDENTIALS,
        EMAIL_ALREADY_EXISTS,
        PHONE_NUMBER_ALREADY_EXISTS,
        USERNAME_ALREADY_EXISTS,
        DUPLICATE_ENTRY
    }
}