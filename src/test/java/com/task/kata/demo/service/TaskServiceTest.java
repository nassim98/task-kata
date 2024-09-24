package com.task.kata.demo.service;

import com.task.kata.demo.exceptions.ResourceNotFoundException;
import com.task.kata.demo.model.Task;
import com.task.kata.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        String name = "New Task";
        String description = "New Task Description";
        Task task = new Task(null, name, description, "NEW");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(name, description);

        assertNotNull(createdTask);
        assertEquals("New Task", createdTask.getName());
        assertEquals("New Task Description", createdTask.getDescription());
        assertEquals("NEW", createdTask.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testUpdateTaskSuccess() {
        Long taskId = 1L;
        String newStatus = "COMPLETED";
        Task task = new Task(taskId, "Existing Task", "Existing Description", "NEW");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task updatedTask = taskService.updateTask(taskId, newStatus);

        assertNotNull(updatedTask);
        assertEquals("COMPLETED", updatedTask.getStatus());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testListTasks() {
        Pageable pageable = PageRequest.of(0, 5);
        Task task1 = new Task(1L, "Task 1", "Description 1", "NEW");
        Task task2 = new Task(2L, "Task 2", "Description 2", "NEW");
        Page<Task> page = new PageImpl<>(Arrays.asList(task1, task2), pageable, 2);

        when(taskRepository.findAll(pageable)).thenReturn(page);

        Page<Task> result = taskService.listTasks(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(taskRepository, times(1)).findAll(pageable);
    }
}