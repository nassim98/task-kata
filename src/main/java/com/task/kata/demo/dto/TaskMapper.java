package com.task.kata.demo.dto;

import com.task.kata.demo.model.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public class TaskMapper {
    public static TaskDTO tasktoTaskDTO(Task task) {
         return TaskDTO.builder()
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }

    public static Page<TaskDTO> taskstoTaskDTOS(Page<Task> tasks) {
        return tasks.map(TaskMapper::tasktoTaskDTO);
    }
}
