package com.springbunny.tasks.domain.dto;

import com.springbunny.tasks.domain.entities.TaskPriority;
import com.springbunny.tasks.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}

// TaskDto is a record used as a Data Transfer Object (DTO) to carry task data between layers.
// It holds immutable task details like id, title, description, due date, priority, and status.
// The use of 'record' provides an automatic constructor, getters, equals, hashCode, and toString methods.
// This helps keep the code concise and ensures data immutability.
