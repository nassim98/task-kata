package com.cata.demo.service;


import com.cata.demo.model.Task;
import com.cata.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public void createTask(String name, String description) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setStatus("CREATE");
        taskRepository.save(task);
    }

    public void updateTask(Long id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setStatus(status);
            taskRepository.save(existingTask);
        }
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

}
