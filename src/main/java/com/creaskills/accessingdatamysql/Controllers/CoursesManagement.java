package com.creaskills.accessingdatamysql.Controllers;

import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.CourseUnit;
import com.creaskills.accessingdatamysql.Services.CoursesManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/courses-management")
public class CoursesManagement {

    @Autowired
    private CoursesManagementService coursesManagement;

    @GetMapping("/list-all")
    List<Course> listAllCourses() {
        return coursesManagement.getAllCourses();
    }

    @GetMapping("/list-by-user")
    List<Course> listAllCoursesByUser() {
        return coursesManagement.getAllCoursesByUser();
    }

    @PostMapping("/add")
    Course addNewCourse(@RequestBody Course newCourse) {
        return coursesManagement.addNewCourse(newCourse);
    }

    @GetMapping("/get-units/{courseId}")
    List<CourseUnit> getAllUnitsByCourse(@PathVariable Integer courseId) {
        return coursesManagement.getAllUnitsByCourse(courseId);
    }

    @PostMapping("/save-unit/{courseId}")
    CourseUnit addNewUnit(@PathVariable Integer courseId, @RequestBody CourseUnit newUnit) {
        return coursesManagement.addNewUnit(courseId, newUnit);
    }

    @PutMapping("/update-unit/{courseId}")
    CourseUnit updateUnit(@PathVariable Integer courseId, @RequestBody CourseUnit unitModified) {
        return coursesManagement.updateUnit(courseId, unitModified);
    }

    @DeleteMapping("/delete-unit/{courseId}/{unitId}")
    void deleteUnit(@PathVariable Integer courseId, @PathVariable Integer unitId) {
        coursesManagement.deleteUnit(courseId, unitId);
    }
}
