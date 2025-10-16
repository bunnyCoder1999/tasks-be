package com.springbunny.tasks.mappers;

import com.springbunny.tasks.domain.dto.TaskDto;
import com.springbunny.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto); //a method that takes in a TaskDto and returns a task.

    TaskDto toDto(Task task); //a method that takes in a task and returns a TaskDto.
}

// Mappers in Spring Boot are used to convert between entities and DTOs, simplifying data transfer between layers.
// They help keep business logic clean by separating conversion logic from service or controller code.
// Mappers are often declared as interfaces (e.g., using MapStruct) so that implementations can be auto-generated at compile time.
// This approach reduces boilerplate code, improves maintainability, and ensures consistent object mapping.

