package com.task.kata.demo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing a task")
public class TaskDTO {

    @NotNull
    @Size(min = 1, max = 10)
    @Schema(description = "Name of the task")
    private String name;

    @Size(min = 1, max = 100)
    @Schema(description = "Description of the task")
    private String description;

    @Schema(description = "Status of the task", example = "IN PROGRESS")
    private String status; // NEW - IN PROGRESS - COMPLETED
}
