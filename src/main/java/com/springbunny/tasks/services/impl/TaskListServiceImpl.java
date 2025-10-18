package com.springbunny.tasks.services.impl;

import com.springbunny.tasks.domain.entities.TaskList;
import com.springbunny.tasks.repositories.TaskListRepository;
import com.springbunny.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //bean declaration for dependency injection
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository; // TaskListRepository being injected into this class

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    } // Constructor for TaskListRepository

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll(); // method defined in Jpa Repository that returns all the required data from DB
    }
}
// The TaskListServiceImpl class provides the concrete implementation of the TaskListService interface.
// Annotated with @Service, it is registered as a Spring bean for dependency injection and marks it as part of the service layer.
// It uses constructor-based injection to receive a TaskListRepository instance, enabling interaction with the database.
// The listTaskLists() method overrides the interface method and calls taskListRepository.findAll() to fetch all TaskList entities from the database.
// This class acts as a bridge between the controller layer and the data access layer, handling business logic operations.
