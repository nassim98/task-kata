package com.task.kata.demo.controller;

import com.task.kata.demo.model.Task;
import com.task.kata.demo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    public void testCreateTask() throws Exception {
        Task task = new Task(1L, "New Task", "Description of new task", "NEW");

        when(taskService.createTask(anyString(), anyString())).thenReturn(task);

        mockMvc.perform(post("/api/task")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Task\", \"description\": \"Description of new task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Task"))
                .andExpect(jsonPath("$.description").value("Description of new task"))
                .andExpect(jsonPath("$.status").value("NEW"));

        verify(taskService, times(1)).createTask(anyString(), anyString());
    }

    @Test
    @WithMockUser
    public void testUpdateTask() throws Exception {
        Long taskId = 1L;
        Task task = new Task(taskId, "Task", "Description", "COMPLETED");

        when(taskService.updateTask(taskId, "COMPLETED")).thenReturn(task);

        mockMvc.perform(put("/api/task/{id}", taskId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Task\", \"status\": \"COMPLETED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        verify(taskService, times(1)).updateTask(eq(taskId), eq("COMPLETED"));
    }

    @Test
    @WithMockUser
    public void testListTasks() throws Exception {
        Pageable pageable = PageRequest.of(0, 5);
        Task task1 = new Task(1L, "Task 1", "Description 1", "NEW");
        Task task2 = new Task(2L, "Task 2", "Description 2", "NEW");
        Page<Task> page = new PageImpl<>(Arrays.asList(task1, task2), pageable, 2);

        when(taskService.listTasks(pageable)).thenReturn(page);

        mockMvc.perform(get("/api/task")
                        .param("page", "0")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Task 1"))
                .andExpect(jsonPath("$.content[1].name").value("Task 2"));

        verify(taskService, times(1)).listTasks(pageable);
    }
}