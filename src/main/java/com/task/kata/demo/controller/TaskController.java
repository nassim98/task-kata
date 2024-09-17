package com.task.kata.demo.controller;


import com.task.kata.demo.dto.TaskDTO;
import com.task.kata.demo.dto.TaskMapper;
import com.task.kata.demo.exceptions.ResourceNotFoundException;
import com.task.kata.demo.model.Task;
import com.task.kata.demo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        try {
            Task createdTask = taskService.createTask(taskDTO.getName(), taskDTO.getDescription());
            return ResponseEntity.ok(createdTask);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create task: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        try {
            Task updatedTask = taskService.updateTask(id, taskDTO.getStatus());
            if (updatedTask == null) {
                throw new ResourceNotFoundException("Task not found with ID: " + id);
            }
            return ResponseEntity.ok(updatedTask);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update task: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> listTasks() {
        try {
            List<Task> tasks = taskService.listTasks();
            List<TaskDTO> taskDTOs = TaskMapper.taskstoTaskDTOS(tasks);
            return ResponseEntity.ok(taskDTOs);
        } catch (Exception e) {
            throw new RuntimeException("Failed to list tasks: " + e.getMessage());
        }
    }
}
