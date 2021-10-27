package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Exceptions.CourseNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseTaskNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseUnitNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.UserNotFoundException;
import com.creaskills.accessingdatamysql.Models.*;
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
        return getUserInstance().getCourses();
    }

    public Course getCourse(Integer courseId) {
        return getCourseById(courseId);
    }

    public Course addNewCourse(Course newCourse) {
        newCourse.setUserId(authenticationFacade.getUserDetails().getUserId());
        newCourse.setCreationDate(LocalDateTime.now());
        return coursesRepository.save(newCourse);
    }

    public Course updateCourse(Course courseModified) {
        Course course = getCourseById(courseModified.getId());
        course.setTitle(courseModified.getTitle());
        course.setDescription(courseModified.getDescription());
        course.setCategories(courseModified.getCategories());
        course.setImage(courseModified.getImage());
        course.setStatus(courseModified.getStatus());
        return coursesRepository.save(course);
    }

    public void deleteCourse(Integer courseId) {
        coursesRepository.deleteById(getCourseById(courseId).getId());
    }

    public List<CourseUnit> getAllUnitsByCourse(Integer courseId) {
        return getCourseById(courseId).getUnits();
    }

    public CourseUnit addNewUnit(Integer courseId, CourseUnit unit) {
        CourseUnit newUnit = new CourseUnit();
        newUnit.setTitle(unit.getTitle());
        newUnit.setDescription(unit.getDescription());
        newUnit.setStatus(unit.getStatus());
        newUnit.setCreationDate(LocalDateTime.now());
        newUnit.setCourseId(getCourseById(courseId).getId());
        return courseUnitRepository.save(newUnit);
    }

    public CourseUnit updateUnit(Integer courseId, CourseUnit unitModified) {
        CourseUnit unit = getCourseUnit(courseId, unitModified.getId());
        unit.setTitle(unitModified.getTitle());
        unit.setDescription(unitModified.getDescription());
        unit.setStatus(unitModified.getStatus());
        return courseUnitRepository.save(unit);
    }

    public void deleteUnit(Integer courseId, Integer unitId) {
        courseUnitRepository.deleteById(getCourseUnit(courseId, unitId).getId());
    }

    public List<CourseTask> getAllTasksByCourseUnit(Integer courseId, Integer unitId) {
        return getCourseUnit(courseId, unitId).getTasks();
    }

    public CourseTask getTask(Integer courseId, Integer unitId, Integer taskId) {
        return getCourseTask(courseId, unitId, taskId);
    }

    public CourseTask addNewTask(Integer courseId, Integer unitId, CourseTask task) {
        CourseTask newTask = new CourseTask();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setCreationDate(LocalDateTime.now());
        newTask.setUrl(task.getUrl());
        newTask.setPosition(task.getPosition());
        newTask.setUnitId(getCourseUnit(courseId, unitId).getId());
        newTask.setStatus(task.getStatus());
        return courseTaskRepository.save(newTask);
    }

    public CourseTask updateTask(Integer courseId, Integer unitId, Integer taskId, CourseTask modifiedTask) {
        CourseTask task = getCourseTask(courseId, unitId, taskId);
        task.setTitle(modifiedTask.getTitle());
        task.setDescription(modifiedTask.getDescription());
        task.setUrl(modifiedTask.getUrl());
        task.setPosition(modifiedTask.getPosition());
        task.setStatus(modifiedTask.getStatus());
        return courseTaskRepository.save(task);
    }

    public void deleteTask(Integer courseId, Integer unitId, Integer taskId) {
        courseTaskRepository.deleteById(
                getCourseTask(courseId, unitId, taskId).getId()
        );
    }

    private User getUserInstance() {
        String currentUser = authenticationFacade.getUserName();
        return userRepository.findByUserName(currentUser)
                .orElseThrow(() -> new UserNotFoundException(currentUser));
    }

    private Course getCourseById(Integer courseId) {
        return  getUserInstance()
                .getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    private CourseUnit getCourseUnit(Integer courseId, Integer unitId) {
        return getCourseById(courseId)
                .getUnits()
                .stream()
                .filter(u -> u.getId().equals(unitId))
                .findFirst()
                .orElseThrow(() -> new CourseUnitNotFoundException(unitId));
    }

    private CourseTask getCourseTask(Integer courseId, Integer unitId, Integer taskId) {
        return getCourseUnit(courseId, unitId)
                .getTasks()
                .stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new CourseTaskNotFoundException(taskId));
    }
}
