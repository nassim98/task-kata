package com.task.kata.demo.dto;

import com.task.kata.demo.model.Task;

import java.util.List;

public class TaskMapper {
    public static TaskDTO tasktoTaskDTO(Task task) {
         return TaskDTO.builder()
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }

    public static List<TaskDTO> taskstoTaskDTOS(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper::tasktoTaskDTO)
                .toList();
    }
}
