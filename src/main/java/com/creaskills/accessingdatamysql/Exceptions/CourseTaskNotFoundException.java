package com.creaskills.accessingdatamysql.Exceptions;

public class CourseTaskNotFoundException extends RuntimeException {
    public CourseTaskNotFoundException(Integer taskId) {
        super("Could not find the unit with id " + taskId);
    }
}
