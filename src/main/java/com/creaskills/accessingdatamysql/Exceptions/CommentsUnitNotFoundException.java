package com.creaskills.accessingdatamysql.Exceptions;

public class CommentsUnitNotFoundException extends RuntimeException {
    public CommentsUnitNotFoundException(Integer commentId) {
        super("Comment with id " + commentId + " doesn't exist");
    }
}
