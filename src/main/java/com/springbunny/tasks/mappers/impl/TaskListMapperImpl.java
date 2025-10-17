package com.springbunny.tasks.mappers.impl;

import com.springbunny.tasks.domain.dto.TaskListDto;
import com.springbunny.tasks.domain.entities.Task;
import com.springbunny.tasks.domain.entities.TaskList;
import com.springbunny.tasks.domain.entities.TaskStatus;
import com.springbunny.tasks.mappers.TaskListMapper;
import com.springbunny.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper; // taskMapper injected

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks()) // to return a list of tasks by taking in a taskListDto.
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto) //taskMapper injected
                                .toList()
                        ).orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks ->
                                tasks.stream().map(taskMapper::toDto).toList()
                ).orElse(null)
        );
    }

    private Double calculateTaskListProgress(List<Task> tasks){
        if(null == tasks){
            return null;
        }

        long closedTaskCount = tasks.stream().filter(task -> TaskStatus.CLOSED == task.getStatus()
        ).count();

        return(double) closedTaskCount / tasks.size();
    }
}

// The Optional.ofNullable() function safely wraps a value that might be null, preventing NullPointerExceptions.
// If the value is non-null, it allows further operations (like map()); if null, it gracefully skips processing.
// In this mapper, Optional.ofNullable() is used to handle potential null task lists or DTO task collections.
// The map() function transforms each non-null list by streaming its elements, applying the taskMapper (fromDto or toDto) to convert between entities and DTOs.
// The resulting stream is collected back into a List using .toList(), ensuring safe and clean data mapping.
// TaskListImpl also contains implementations of calculateTaskListProgress