package com.springbunny.tasks.repositories;

import com.springbunny.tasks.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByTaskListId(UUID taskListId);
    Optional<Task> findByTaskListIdAndId(UUID taskListId, UUID id);
}

// Repository interfaces in Spring Boot act as the data access layer, providing an abstraction over database operations.
// By extending JpaRepository, TaskRepository inherits CRUD methods like save(), findAll(), findById(), and delete() without needing implementation.
// Spring Data JPA automatically generates SQL queries based on method names, following naming conventions.
// The method findByTaskListId(UUID taskListId) retrieves all tasks linked to a specific TaskList.
// The method findByTaskListIdAndId(UUID taskListId, UUID id) fetches a single task by both its TaskList ID and its own ID.
// These methods enhance readability and reduce boilerplate by eliminating manual query definitions.
