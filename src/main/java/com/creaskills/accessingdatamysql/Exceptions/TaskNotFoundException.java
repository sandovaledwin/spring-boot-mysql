package com.creaskills.accessingdatamysql.Exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Integer id) {
        super("Could not find the task " + id);
    }
}