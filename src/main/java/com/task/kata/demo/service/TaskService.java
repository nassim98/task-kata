package com.task.kata.demo.service;


import com.task.kata.demo.model.Task;
import com.task.kata.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(String name, String description) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setStatus("NEW");
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setStatus(status);
            return taskRepository.save(existingTask);
        }
        return null;
    }

    public Page<Task> listTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

}
