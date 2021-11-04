package com.creaskills.accessingdatamysql.Controllers;

import com.creaskills.accessingdatamysql.Exceptions.CourseNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseTaskNotFoundException;
import com.creaskills.accessingdatamysql.Exceptions.CourseUnitNotFoundException;
import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.CourseTask;
import com.creaskills.accessingdatamysql.Models.CourseUnit;
import com.creaskills.accessingdatamysql.Services.CoursesManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/admin")
public class CoursesManagement {

    @Autowired
    private CoursesManagementService coursesManagement;

    @GetMapping("/all-courses")
    List<Course> listAllCourses() {
        return coursesManagement.getAllCourses();
    }

    @GetMapping("/courses")
    List<Course> listAllCoursesByUser() {
        return coursesManagement.getAllCoursesByUser();
    }

    @GetMapping("/course/{courseId}")
    Course getCourse(@PathVariable Integer courseId){
        try {
            return coursesManagement.getCourse(courseId);
        } catch (CourseNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course doesn't exist", ex);
        }
    }

    @PostMapping("/course")
    Course addNewCourse(@RequestBody Course newCourse) {
        return coursesManagement.addNewCourse(newCourse);
    }

    @PutMapping("/course")
    Course updateUnit(@RequestBody Course courseModified) {
        return coursesManagement.updateCourse(courseModified);
    }

    @DeleteMapping("/course/{courseId}")
    void deleteCourse(@PathVariable Integer courseId) {
        coursesManagement.deleteCourse(courseId);
    }

    @GetMapping("/units/{courseId}")
    List<CourseUnit> getAllUnitsByCourse(@PathVariable Integer courseId) {
        return coursesManagement.getAllUnitsByCourse(courseId);
    }

    @PostMapping("/unit/{courseId}")
    CourseUnit addNewUnit(@PathVariable Integer courseId, @RequestBody CourseUnit newUnit) {
        return coursesManagement.addNewUnit(courseId, newUnit);
    }

    @PutMapping("/unit/{courseId}")
    CourseUnit updateUnit(@PathVariable Integer courseId, @RequestBody CourseUnit unitModified) {
        try {
            return coursesManagement.updateUnit(courseId, unitModified);
        } catch (CourseNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course doesn't exist", ex);
        }
    }

    @DeleteMapping("/unit/{courseId}/{unitId}")
    void deleteUnit(@PathVariable Integer courseId, @PathVariable Integer unitId) {
        try {
            coursesManagement.deleteUnit(courseId, unitId);
        } catch (CourseNotFoundException | CourseUnitNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course or unit doesn't exist", ex);
        }
    }

    @GetMapping("/tasks/{courseId}/{unitId}")
    List<CourseTask> getAllTasksByCourseUnit(@PathVariable Integer courseId, @PathVariable Integer unitId) {
        try {
            return coursesManagement.getAllTasksByCourseUnit(courseId, unitId);
        } catch (CourseNotFoundException | CourseUnitNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course or unit doesn't exist", ex);
        }
    }

    @GetMapping("/tasks/{courseId}/{unitId}/{taskId}")
    CourseTask getTask(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @PathVariable Integer taskId) {
        try {
            return coursesManagement.getTask(courseId, unitId, taskId);
        } catch (CourseNotFoundException |
                CourseUnitNotFoundException |
                CourseTaskNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course, unit or task doesn't exist", ex);
        }
    }

    @PostMapping("/task/{courseId}/{unitId}")
    CourseTask addNewTask(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @RequestBody CourseTask newTask) {
        try {
            return coursesManagement.addNewTask(courseId, unitId, newTask);
        } catch (CourseNotFoundException | CourseUnitNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course or unit doesn't exist", ex);
        }
    }

    @PutMapping("/task/{courseId}/{unitId}/{taskId}")
    CourseTask updateTask(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @PathVariable Integer taskId,
            @RequestBody CourseTask task) {
        try {
            return coursesManagement.updateTask(courseId, unitId, taskId, task);
        } catch (CourseNotFoundException |
                CourseUnitNotFoundException |
                CourseTaskNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course, unit or task doesn't exist", ex);
        }
    }

    @DeleteMapping("/task/{courseId}/{unitId}/{taskId}")
    void deleteTask(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @PathVariable Integer taskId) {
        try {
            coursesManagement.deleteTask(courseId, unitId, taskId);
        } catch (CourseNotFoundException |
                CourseUnitNotFoundException |
                CourseTaskNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The course, unit or task doesn't exist", ex);
        }
    }
}
