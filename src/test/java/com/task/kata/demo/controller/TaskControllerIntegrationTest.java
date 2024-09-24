package com.task.kata.demo.controller;

import com.task.kata.demo.model.Task;
import com.task.kata.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIntegrationTest {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    public void setTestRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication(USERNAME, PASSWORD));
    }

    @BeforeEach
    public void setup() {
        taskRepository.deleteAll(); // Clean up the database before each test
        baseUrl = "http://localhost:" + port + "/api/task";
    }

    @Test
    public void testCreateTask() {
        String requestBody = "{\"name\": \"Task 1\", \"description\": \"Test Description\", \"status\": \"NEW\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Task> response = restTemplate.postForEntity(baseUrl, request, Task.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Task 1");
        assertThat(response.getBody().getDescription()).isEqualTo("Test Description");

        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks.get(0).getName()).isEqualTo("Task 1");
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task();
        task.setName("Initial");
        task.setDescription("Initial Description");
        task.setStatus("NEW");
        taskRepository.save(task);

        String requestBody = "{\"name\": \"Initial\", \"description\": \"Initial Description\", \"status\": \"COMPLETED\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Task> response = restTemplate.exchange(baseUrl +"/" + task.getId(), HttpMethod.PUT, request, Task.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Initial");
        assertThat(response.getBody().getStatus()).isEqualTo("COMPLETED");

        Task updatedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertThat(updatedTask.getName()).isEqualTo("Initial");
        assertThat(updatedTask.getDescription()).isEqualTo("Initial Description");
        assertThat(updatedTask.getStatus()).isEqualTo("COMPLETED");
    }
}