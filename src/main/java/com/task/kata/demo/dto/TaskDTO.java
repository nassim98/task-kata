package com.task.kata.demo.dto;


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
public class TaskDTO {

    @NotNull
    @Size(min = 1, max = 10)
    private String name;

    @Size(min = 1, max = 100)
    private String description;

    private String status; // NEW - IN PROGRESS - COMPLETED
}
