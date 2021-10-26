package com.creaskills.accessingdatamysql.Exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(String userName) {
        super("The username all ready exits: " + userName);
    }
}
