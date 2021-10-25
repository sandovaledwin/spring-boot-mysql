package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Exceptions.CourseNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseTaskNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseUnitNotFoundException;
import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.CourseTask;
import com.creaskills.accessingdatamysql.Models.CourseUnit;
import com.creaskills.accessingdatamysql.Models.UserDetailsImpl;
import com.creaskills.accessingdatamysql.Repositories.CourseTaskRepository;
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
    private CourseTaskRepository courseTaskRepository;

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

    public Course getCourse(Integer courseId) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        return userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public Course addNewCourse(Course newCourse) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        newCourse.setUserId(userDetails.getUserDetails().getUserId());
        newCourse.setCreationDate(LocalDateTime.now());
        return coursesRepository.save(newCourse);
    }

    public Course updateCourse(Course courseModified) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseModified.getId()))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseModified.getId()));
        course.setTitle(courseModified.getTitle());
        course.setDescription(courseModified.getDescription());
        course.setCategories(courseModified.getCategories());
        course.setImage(courseModified.getImage());
        course.setStatus(courseModified.getStatus());
        return coursesRepository.save(course);
    }

    public void deleteCourse(Integer courseId) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        coursesRepository.deleteById(course.getId());
    }

    public List<CourseUnit> getAllUnitsByCourse(Integer courseId) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        return course.getUnits();
    }

    public CourseUnit addNewUnit(Integer courseId, CourseUnit unit) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit newUnit = new CourseUnit();
        newUnit.setTitle(unit.getTitle());
        newUnit.setDescription(unit.getDescription());
        newUnit.setStatus(unit.getStatus());
        newUnit.setCreationDate(LocalDateTime.now());
        newUnit.setCourseId(course.getId());
        return courseUnitRepository.save(newUnit);
    }

    public CourseUnit updateUnit(Integer courseId, CourseUnit unitModified) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit unit = course
                .getUnits()
                .stream()
                .filter(u -> u.getId().equals(unitModified.getId()))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitModified.getId()));
        unit.setTitle(unitModified.getTitle());
        unit.setDescription(unitModified.getDescription());
        unit.setStatus(unitModified.getStatus());
        return courseUnitRepository.save(unit);
    }

    public void deleteUnit(Integer courseId, Integer unitId) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit unitFound = course
                .getUnits()
                .stream()
                .filter(u -> u.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitId));
        courseUnitRepository.deleteById(unitFound.getId());
    }

    public List<CourseTask> getAllTasksByCourseUnit(Integer courseId, Integer unitId) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit unit = course
                .getUnits()
                .stream()
                .filter(u -> u.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitId));
        return unit.getTasks();
    }

    public CourseTask getTask(Integer courseId, Integer unitId, Integer taskId) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit unit = course
                .getUnits()
                .stream()
                .filter(u -> u.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitId));
        return unit
                .getTasks()
                .stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new CourseTaskNotFoundException(taskId));
    }

    public CourseTask addNewTask(Integer courseId, Integer unitId, CourseTask task) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit unit = course
                .getUnits()
                .stream()
                .filter(u -> u.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitId));
        CourseTask newTask = new CourseTask();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setCreationDate(LocalDateTime.now());
        newTask.setUrl(task.getUrl());
        newTask.setPosition(task.getPosition());
        newTask.setUnitId(unit.getId());
        newTask.setStatus(task.getStatus());
        return courseTaskRepository.save(newTask);
    }

    public CourseTask updateTask(Integer courseId, Integer unitId, Integer taskId, CourseTask modifiedTask) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit unit = course
                .getUnits()
                .stream()
                .filter(u -> u.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitId));
        CourseTask task = unit
                .getTasks()
                .stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new CourseTaskNotFoundException(taskId));
        task.setTitle(modifiedTask.getTitle());
        task.setDescription(modifiedTask.getDescription());
        task.setUrl(modifiedTask.getUrl());
        task.setPosition(modifiedTask.getPosition());
        task.setUnitId(unit.getId());
        task.setStatus(modifiedTask.getStatus());
        return courseTaskRepository.save(task);
    }

    public void deleteTask(Integer courseId, Integer unitId, Integer taskId) {
        UserDetailsImpl userDetails = authenticationFacade.getPrincipal();
        String userName = userDetails.getUserDetails().getUserName();
        Course course = userRepository
                .findByUserName(userName)
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        CourseUnit unit = course
                .getUnits()
                .stream()
                .filter(u -> u.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitId));
        CourseTask task = unit
                .getTasks()
                .stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new CourseTaskNotFoundException(taskId));
        courseTaskRepository.deleteById(task.getId());
    }
}
