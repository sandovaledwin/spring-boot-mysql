package com.creaskills.accessingdatamysql.Controllers;

import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.CourseTask;
import com.creaskills.accessingdatamysql.Models.CourseUnit;
import com.creaskills.accessingdatamysql.Services.CoursesManagementService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return coursesManagement.getCourse(courseId);
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
        return coursesManagement.updateUnit(courseId, unitModified);
    }

    @DeleteMapping("/unit/{courseId}/{unitId}")
    void deleteUnit(@PathVariable Integer courseId, @PathVariable Integer unitId) {
        coursesManagement.deleteUnit(courseId, unitId);
    }

    @GetMapping("/tasks/{courseId}/{unitId}")
    List<CourseTask> getAllTasksByCourseUnit(@PathVariable Integer courseId, @PathVariable Integer unitId) {
        return coursesManagement.getAllTasksByCourseUnit(courseId, unitId);
    }

    @GetMapping("/tasks/{courseId}/{unitId}/{taskId}")
    CourseTask getTask(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @PathVariable Integer taskId) {
        return coursesManagement.getTask(courseId, unitId, taskId);
    }

    @PostMapping("/task/{courseId}/{unitId}")
    CourseTask addNewTask(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @RequestBody CourseTask newTask) {
        return coursesManagement.addNewTask(courseId, unitId, newTask);
    }

    @PutMapping("/task/{courseId}/{unitId}/{taskId}")
    CourseTask updateTask(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @PathVariable Integer taskId,
            @RequestBody CourseTask task) {
        return coursesManagement.updateTask(courseId, unitId, taskId, task);
    }

    @DeleteMapping("/task/{courseId}/{unitId}/{taskId}")
    void deleteTask(
            @PathVariable Integer courseId,
            @PathVariable Integer unitId,
            @PathVariable Integer taskId) {
        coursesManagement.deleteTask(courseId, unitId, taskId);
    }
}
