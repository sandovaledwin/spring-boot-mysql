package com.creaskills.accessingdatamysql.Controllers;

import java.util.List;

import com.creaskills.accessingdatamysql.Exceptions.TaskNotFoundException;
import com.creaskills.accessingdatamysql.Models.Task;
import com.creaskills.accessingdatamysql.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="/task")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/getAll")
    List<Task> all() {
        return taskRepository.findAll();
    }

    @PostMapping("/add")
    Task addNewTask(@RequestBody Task newTask) {
        return taskRepository.save(newTask);
    }

    @GetMapping("/{id}")
    Task getTask(@PathVariable Integer id) {

        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable Integer id) {
        taskRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    Task updateTask(@RequestBody Task newTask, @PathVariable Integer id) {

        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(newTask.getName());
                    task.setStatus(newTask.getStatus());
                    return taskRepository.save(task);
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return taskRepository.save(newTask);
                });
    }
}
