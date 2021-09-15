package com.creaskills.accessingdatamysql.Repositories;

import com.creaskills.accessingdatamysql.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
