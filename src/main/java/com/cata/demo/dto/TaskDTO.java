package com.cata.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String name;
    private String description;
    private String status; // IN PROGRESS - COMPLETED - NEW
}
