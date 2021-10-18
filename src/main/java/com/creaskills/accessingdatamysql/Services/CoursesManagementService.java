package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.CourseUnit;

import java.util.List;

public interface CoursesManagementService {
    List<Course> getAllCourses();
    List<Course> getAllCoursesByUser();
    Course addNewCourse(Course newCourse);
    List<CourseUnit> getAllUnitsByCourse(Integer courseId);
    CourseUnit addNewUnit(Integer courseId, CourseUnit unit);
    CourseUnit updateUnit(Integer courseId, CourseUnit unit);
    void deleteUnit(Integer courseId, Integer unitId);
}
