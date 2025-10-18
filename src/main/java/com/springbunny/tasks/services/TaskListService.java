package com.springbunny.tasks.services;

import com.springbunny.tasks.domain.entities.TaskList;

import java.util.List;

public interface TaskListService {
    List<TaskList> listTaskLists();
}

// The TaskListService interface defines the contract for task list–related business logic in the application.
// It acts as a service layer abstraction, separating business operations from controller and repository logic.
// The method listTaskLists() declares a service operation to retrieve all TaskList entities from the database.
// Implementations of this interface (e.g., TaskListServiceImpl) will contain the actual logic to fetch and process the data.
