package com.creaskills.accessingdatamysql.Repositories;

import com.creaskills.accessingdatamysql.Models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CoursesRepository extends CrudRepository<Course, Integer> {
}
