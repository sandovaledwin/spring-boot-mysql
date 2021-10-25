package com.creaskills.accessingdatamysql.Repositories;

import com.creaskills.accessingdatamysql.Models.CourseTask;
import org.springframework.data.repository.CrudRepository;

public interface CourseTaskRepository extends CrudRepository<CourseTask, Integer> {
}
