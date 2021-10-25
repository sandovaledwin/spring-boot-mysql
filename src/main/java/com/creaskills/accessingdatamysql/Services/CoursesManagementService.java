package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.CourseTask;
import com.creaskills.accessingdatamysql.Models.CourseUnit;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CoursesManagementService {
    List<Course> getAllCourses();
    List<Course> getAllCoursesByUser();
    Course getCourse(Integer courseId);
    Course addNewCourse(Course newCourse);
    Course updateCourse(Course course);
    void deleteCourse(Integer courseId);
    List<CourseUnit> getAllUnitsByCourse(Integer courseId);
    CourseUnit addNewUnit(Integer courseId, CourseUnit unit);
    CourseUnit updateUnit(Integer courseId, CourseUnit unit);
    void deleteUnit(Integer courseId, Integer unitId);
    List<CourseTask> getAllTasksByCourseUnit(Integer courseId, Integer unitId);
    CourseTask getTask(Integer courseId, Integer unitId, Integer taskId);
    CourseTask addNewTask(Integer courseId, Integer unitId, CourseTask task);
    CourseTask updateTask(Integer courseId, Integer unitId, Integer taskId, CourseTask task);
    void deleteTask(Integer courseId, Integer unitId, Integer taskId);
}
