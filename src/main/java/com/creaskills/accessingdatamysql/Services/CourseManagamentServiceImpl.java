package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.UserDetailsImpl;
import com.creaskills.accessingdatamysql.Repositories.CoursesRepository;
import com.creaskills.accessingdatamysql.Repositories.UserRepository;
import com.creaskills.accessingdatamysql.components.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseManagamentServiceImpl implements CoursesManagementService {
    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public List<Course> getAllCourses() {
        return (List<Course>) coursesRepository.findAll();
    }

    public List<Course> getAllCoursesByUser() {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        return userRepository.findByUserName(
                userDetails.getUserDetails().getUserName()
        ).getCourses();
    }

    public Course addNewCourse(Course newCourse) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        newCourse.setUserId(userDetails.getUserDetails().getUserId());
        newCourse.setCreationDate(LocalDateTime.now());
        return coursesRepository.save(newCourse);
    }
}
