package com.cata.demo.controller;


import com.cata.demo.dto.TaskDTO;
import com.cata.demo.dto.TaskMapper;
import com.cata.demo.model.Task;
import com.cata.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO) {
        taskService.createTask(taskDTO.getName(), taskDTO.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body("task is created to cart successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        taskService.updateTask(id, taskDTO.getStatus());
        return ResponseEntity.ok(taskDTO);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> listTasks() {
        List<Task> tasks = taskService.listTasks();
        List<TaskDTO> taskDTOs = TaskMapper.taskstoTaskDTS(tasks);
        return ResponseEntity.ok(taskDTOs);
    }
}
