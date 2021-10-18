package com.creaskills.accessingdatamysql.Exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Integer courseId) {
        super("Could not find the course " + courseId);
    }
}