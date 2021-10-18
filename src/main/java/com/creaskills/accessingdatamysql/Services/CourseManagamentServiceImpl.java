package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Exceptions.CourseNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseUnitNotFoundException;
import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.CourseUnit;
import com.creaskills.accessingdatamysql.Models.UserDetailsImpl;
import com.creaskills.accessingdatamysql.Repositories.CourseUnitRepository;
import com.creaskills.accessingdatamysql.Repositories.CoursesRepository;
import com.creaskills.accessingdatamysql.Repositories.UserRepository;
import com.creaskills.accessingdatamysql.Components.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseManagamentServiceImpl implements CoursesManagementService {
    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CourseUnitRepository courseUnitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public List<Course> getAllCourses() {
        return (List<Course>) coursesRepository.findAll();
    }

    public List<Course> getAllCoursesByUser() {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        return userRepository
                .findByUserName(userName)
                .getCourses();
    }

    public Course addNewCourse(Course newCourse) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        newCourse.setUserId(userDetails.getUserDetails().getUserId());
        newCourse.setCreationDate(LocalDateTime.now());
        return coursesRepository.save(newCourse);
    }

    public List<CourseUnit> getAllUnitsByCourse(Integer courseId) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        return userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .get()
                .getUnits();
    }

    public CourseUnit addNewUnit(Integer courseId, CourseUnit unit) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course courseFound = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit newUnit = new CourseUnit();
        newUnit.setTitle(unit.getTitle());
        newUnit.setDescription(unit.getDescription());
        newUnit.setStatus(unit.getStatus());
        newUnit.setCreationDate(LocalDateTime.now());
        newUnit.setCourseId(courseFound.getId());
        return courseUnitRepository.save(newUnit);
    }

    public CourseUnit updateUnit(Integer courseId, CourseUnit unitModified) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course courseFound = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit unitFound = courseFound
                .getUnits()
                .stream()
                .filter(item -> item.getId().equals(unitModified.getId()))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitModified.getId()));
        unitFound.setTitle(unitModified.getTitle());
        unitFound.setDescription(unitModified.getDescription());
        unitFound.setStatus(unitModified.getStatus());
        return courseUnitRepository.save(unitFound);
    }

    public void deleteUnit(Integer courseId, Integer unitId) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course courseFound = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit unitFound = courseFound
                .getUnits()
                .stream()
                .filter(unit -> unit.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitId));
        courseUnitRepository.deleteById(unitFound.getId());
    }
}
