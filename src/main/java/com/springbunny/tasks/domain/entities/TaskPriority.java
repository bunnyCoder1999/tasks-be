package com.springbunny.tasks.domain.entities;

public enum TaskPriority {
    HIGH, MEDIUM, LOW
}
// Enums in Java (and Spring Boot) are special data types used to define a fixed set of constant values.
// They improve code readability and prevent invalid values by restricting variables to predefined options.
// In Spring Boot, enums are often used for fields like status, priority, or roles within entities (e.g., TaskStatus, UserRole).
// When persisted with JPA, enums can be stored as either their name (STRING) or position (ORDINAL) in the database.
// They integrate seamlessly with JSON serialization, making them useful in APIs for clear, consistent value representation.
