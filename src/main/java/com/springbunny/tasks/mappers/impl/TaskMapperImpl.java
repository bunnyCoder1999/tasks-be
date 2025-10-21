package com.springbunny.tasks.mappers.impl;

import com.springbunny.tasks.domain.dto.TaskDto;
import com.springbunny.tasks.domain.entities.Task;
import com.springbunny.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.dueDate(),
                taskDto.status(),
                taskDto.priority(),
                null,
                null,
                null
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }
}

// 'Impls' (implementations) are the concrete classes that provide the actual mapping logic for mapper interfaces.
// When using libraries like MapStruct, these impl classes are auto-generated at compile time based on the mapper interface definitions.
// They contain the real code that performs field-to-field conversions between entities and DTOs.
// Developers rarely modify these manually, as they are managed and regenerated automatically by the mapping framework.
