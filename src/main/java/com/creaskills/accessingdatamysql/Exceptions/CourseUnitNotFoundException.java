package com.creaskills.accessingdatamysql.Exceptions;

public class CourseUnitNotFoundException extends RuntimeException {
    public CourseUnitNotFoundException(Integer courseId) {
        super("Could not find the unit with id " + courseId);
    }
}
