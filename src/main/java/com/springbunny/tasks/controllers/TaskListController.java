package com.springbunny.tasks.controllers;

import com.springbunny.tasks.domain.dto.TaskListDto;
import com.springbunny.tasks.domain.entities.TaskList;
import com.springbunny.tasks.mappers.TaskListMapper;
import com.springbunny.tasks.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @RestController is also used for bean declaration
@RequestMapping(path = "/api/task-lists")
public class TaskListController {

    private final TaskListService taskListService; //TaskListService & TaskListMapper injected and annotated by @RestController
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists(){ // Endpoint method to return a list of TaskListDto
       return taskListService.listTaskLists() // returns a list of TaskLists
                .stream()
                .map(taskListMapper::toDto) //converts the list of TaskLists int TaskListDto
                .toList(); // coverts stream into list
    }

    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto){ // method defined earlier to create new task list; data to be persisted is passed in via @RequestBody
        TaskList createdTaskList = taskListService.createTaskList( // TaskListDto -> TaskList conversion; taskListService.createTaskList expects TaskList type
                taskListMapper.fromDto(taskListDto)
        );
        return taskListMapper.toDto(createdTaskList); // TaskList -> TaskListDto
    }
}

// The TaskListController class handles incoming HTTP requests related to task lists and returns appropriate responses.
// Annotated with @RestController, it marks this class as a RESTful web controller and automatically serializes responses to JSON.
// The @RequestMapping("/api/task-lists") defines the base URL path for all endpoints in this controller.
// It uses constructor-based dependency injection to get instances of TaskListService and TaskListMapper.
// The @GetMapping method listTaskLists() handles GET requests to fetch all task lists.
// It calls taskListService.listTaskLists() to retrieve TaskList entities, then maps them to TaskListDto using taskListMapper::toDto.
// Finally, the stream of mapped DTOs is collected into a List and returned as the response body.

