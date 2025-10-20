package com.springbunny.tasks.services.impl;

import com.springbunny.tasks.domain.entities.TaskList;
import com.springbunny.tasks.repositories.TaskListRepository;
import com.springbunny.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public TaskList createTaskList(TaskList taskList) { // method to create a new Task list
        if(null != taskList.getId()){
            throw new  IllegalArgumentException("Task list alredy has an ID!"); // ensures the task list has no pre-existing ID
        }

        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list title must be present!"); // ensures that the title is not empty or blank
        }

        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(  // A new TaskList instance is then created with the current timestamps for creation and update times
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        )); // The taskListRepository.save() call persists this new TaskList object into the database
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if(null == taskList.getId()){
            throw new IllegalArgumentException("Task list must have an ID!");
        }

        if(!Objects.equals(taskList.getId(), taskListId)){
            throw new IllegalArgumentException("Attempt to change Task list ID is not permitted");
        }

       TaskList existingTaskList = taskListRepository.findById(taskListId).orElseThrow(() ->
                new IllegalArgumentException("Task list not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());
        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        taskListRepository.deleteById(taskListId);
    }
}
// The TaskListServiceImpl class provides the concrete implementation of the TaskListService interface.
// Annotated with @Service, it is registered as a Spring bean for dependency injection and marks it as part of the service layer.
// It uses constructor-based injection to receive a TaskListRepository instance, enabling interaction with the database.
// The listTaskLists() method overrides the interface method and calls taskListRepository.findAll() to fetch all TaskList entities from the database.
// This class acts as a bridge between the controller layer and the data access layer, handling business logic operations.

// The updateTaskList() method updates an existing TaskList identified by taskListId.
// It first validates that the provided TaskList has an ID and that it matches the path ID to prevent unauthorized ID changes.
// Then, it retrieves the existing TaskList from the repository or throws an exception if not found.
// The method updates the title, description, and the 'updated' timestamp of the existing record.
// Finally, taskListRepository.save() persists the modified TaskList back to the database, replacing the old record.
