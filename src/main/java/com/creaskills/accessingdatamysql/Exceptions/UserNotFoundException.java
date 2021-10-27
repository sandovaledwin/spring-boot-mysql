package com.creaskills.accessingdatamysql.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer userId) {
        super("Could not find the user with id " + userId);
    }
    public UserNotFoundException(String userName) {
        super("Could not find the username: " + userName);
    }
}
