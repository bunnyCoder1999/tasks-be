package com.springbunny.tasks.services;

import com.springbunny.tasks.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    Optional<TaskList> getTaskList(UUID id);
    TaskList updateTaskList (UUID taskListId, TaskList taskList);
}

// The TaskListService interface defines the contract for task list–related business logic in the application.
// It acts as a service layer abstraction, separating business operations from controller and repository logic.
// The method listTaskLists() declares a service operation to retrieve all TaskList entities from the database.
// Implementations of this interface (e.g., TaskListServiceImpl) will contain the actual logic to fetch and process the data.
// getTaskList method is an optional that takes in a UUID id and returns a TaskList or else returns Optional.empty.