package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Repositories.CoursesRepository;
import com.creaskills.accessingdatamysql.Repositories.UserRepository;
import com.creaskills.accessingdatamysql.components.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CoursesManagementService {
    List<Course> getAllCourses();
    List<Course> getAllCoursesByUser();
    Course addNewCourse(Course newCourse);
}
