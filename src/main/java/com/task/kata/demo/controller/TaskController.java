package com.task.kata.demo.controller;


import com.task.kata.demo.dto.TaskDTO;
import com.task.kata.demo.dto.TaskMapper;
import com.task.kata.demo.exceptions.ResourceNotFoundException;
import com.task.kata.demo.model.Task;
import com.task.kata.demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Create a new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid task input")
    })
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        try {
            Task createdTask = taskService.createTask(taskDTO.getName(), taskDTO.getDescription());
            return ResponseEntity.ok(createdTask);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create task: " + e.getMessage());
        }
    }

    @Operation(description = "Update a task status by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully updated"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
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

    @Operation(description = "Retrieve a list of all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of task list")
    })
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
