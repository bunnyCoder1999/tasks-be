package com.springbunny.tasks.controllers;

import com.springbunny.tasks.domain.dto.TaskListDto;
import com.springbunny.tasks.domain.entities.TaskList;
import com.springbunny.tasks.mappers.TaskListMapper;
import com.springbunny.tasks.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping(path = "/{task_list_id}") // Specifying path
    public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id")UUID taskListId){
        return taskListService.getTaskList(taskListId).map(taskListMapper::toDto);
    }

    @PutMapping(path = "/{task_list_id}")
    public TaskListDto updateTaskList(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskListDto taskListDto
    ){
        TaskList updatedTaskList = taskListService.updateTaskList(
                taskListId,
                taskListMapper.fromDto(taskListDto)
        );
        return taskListMapper.toDto(updatedTaskList);
    }

    @DeleteMapping(path = "/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id") UUID taskListId){
        taskListService.deleteTaskList(taskListId);
    }
}

// The TaskListController class handles incoming HTTP requests related to task lists and returns appropriate responses.
// Annotated with @RestController, it marks this class as a RESTful web controller and automatically serializes responses to JSON.
// The @RequestMapping("/api/task-lists") defines the base URL path for all endpoints in this controller.
// It uses constructor-based dependency injection to get instances of TaskListService and TaskListMapper.

// The @GetMapping method listTaskLists() handles GET requests to fetch all task lists.
// It calls taskListService.listTaskLists() to retrieve TaskList entities, then maps them to TaskListDto using taskListMapper::toDto.
// Finally, the stream of mapped DTOs is collected into a List and returned as the response body.

// The getTaskList() method handles HTTP GET requests for a specific task list using its unique ID.
// The @GetMapping annotation defines the endpoint path parameter {task_list_id}.
// @PathVariable binds the URL path value to the method parameter taskListId (of type UUID).
// It calls taskListService.getTaskList() to fetch the entity, then uses map(taskListMapper::toDto)
// to convert it into a TaskListDto if present, returning it as an Optional response.

// The updateTaskList() method handles HTTP PUT requests to update an existing task list resource.
// The @PutMapping annotation indicates that this endpoint replaces or updates an existing record identified by {task_list_id}.
// @PathVariable binds the URL path variable to the method parameter taskListId.
// @RequestBody maps the incoming JSON payload to a TaskListDto object representing the updated data.
// The DTO is converted to an entity using taskListMapper.fromDto(), passed to the service layer for update,
// and the updated entity is then mapped back to a TaskListDto and returned as the API response.

// The deleteTaskList() method handles HTTP DELETE requests to remove a specific task list by its ID.
// The @DeleteMapping annotation maps the endpoint, and @PathVariable binds the URL ID to the method parameter before calling the service to delete it.
