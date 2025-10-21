package com.springbunny.tasks.services.impl;

import com.springbunny.tasks.domain.entities.Task;
import com.springbunny.tasks.domain.entities.TaskList;
import com.springbunny.tasks.domain.entities.TaskPriority;
import com.springbunny.tasks.domain.entities.TaskStatus;
import com.springbunny.tasks.repositories.TaskListRepository;
import com.springbunny.tasks.repositories.TaskRepository;
import com.springbunny.tasks.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId); // custom query that we defined in TaskRepository.
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null != task.getId()){
            throw new IllegalArgumentException("Task already has an ID!"); // Task ID validation
        }

        if(task.getTitle() == null || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task must have a title!"); // Task title validation
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()) // Setting default priority to medium if null
                .orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN; // Setting default status

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task list ID provided!"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );
        return taskRepository.save(taskToSave);
    }
}
