package com.springbunny.tasks.controllers;

import com.springbunny.tasks.domain.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice //Annotation that handles exceptions in all the controllers
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class}) // Since we're throwing an IllegalArgumentException
    public ResponseEntity<ErrorResponse> handleExceptions(
            RuntimeException ex, WebRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
// handleExceptions is a method that returns a ResponseEntity of type ErrorResponse, taking in arguments namely a RuntimeException ex and a WebRequest request.
// The GlobalExceptionHandler class handles exceptions thrown across all controllers in a centralized way.
// The @ControllerAdvice annotation makes it a global error handler for the entire application.
// The handleExceptions() method catches RuntimeExceptions and returns a structured ErrorResponse with status, message, and request details.
// This approach ensures consistent and readable error responses for API clients while keeping controller code clean.
// ResponseEntity is a Spring class used to represent the entire HTTP response, including status code, headers, and body.
// It provides fine-grained control over what the API returns to the client.
// Developers can use it to send custom responses like success data, error messages, or specific HTTP statuses (e.g., 200, 400, 404).
// Example: new ResponseEntity<>(responseBody, HttpStatus.OK) sends a response body with an HTTP 200 status.

/// ┌────────────────────────────────────────────────────────────┐
/// │      1️⃣  Controller Layer Execution                       │
/// │------------------------------------------------------------│
/// │ - A REST controller method is called (e.g., createTaskList) │
/// │ - During execution, an exception occurs                    │
/// │   → Example: throw new IllegalArgumentException("Invalid"); │
/// └────────────────────────────────────────────────────────────┘
///                 │
///                 ▼
/// ┌────────────────────────────────────────────────────────────┐
/// │      2️⃣  Exception Propagation                             │
/// │------------------------------------------------------------│
/// │ - The exception (IllegalArgumentException) is not caught    │
/// │   inside the controller.                                   │
/// │ - Spring detects it and looks for a matching handler.       │
/// └────────────────────────────────────────────────────────────┘
///                 │
///                 ▼
/// ┌────────────────────────────────────────────────────────────┐
/// │      3️⃣  GlobalExceptionHandler Activation                 │
/// │------------------------------------------------------------│
/// │ - @ControllerAdvice tells Spring this class handles errors │
/// │   globally for all controllers.                            │
/// │ - @ExceptionHandler({IllegalArgumentException.class})       │
/// │   matches the thrown exception type.                       │
/// │ - The handleExceptions() method is triggered.               │
/// └────────────────────────────────────────────────────────────┘
///                 │
///                 ▼
/// ┌────────────────────────────────────────────────────────────┐
/// │      4️⃣  ErrorResponse Creation                            │
/// │------------------------------------------------------------│
/// │ - A new ErrorResponse object is built containing:           │
/// │     • HTTP status code (400)                                │
/// │     • Exception message                                     │
/// │     • Request details (from WebRequest)                     │
/// └────────────────────────────────────────────────────────────┘
///                 │
///                 ▼
/// ┌────────────────────────────────────────────────────────────┐
/// │      5️⃣  ResponseEntity Returned                           │
/// │------------------------------------------------------------│
/// │ - A ResponseEntity<ErrorResponse> is created                │
/// │   with status HttpStatus.BAD_REQUEST (400).                 │
/// │ - Spring converts it into an HTTP JSON response.            │
/// └────────────────────────────────────────────────────────────┘
///                 │
///                 ▼
/// ┌────────────────────────────────────────────────────────────┐
/// │      6️⃣  Client Receives Response                          │
/// │------------------------------------------------------------│
/// │ - The client gets a structured JSON response like:          │
/// │   {                                                        │
/// │     "status": 400,                                         │
/// │     "message": "Task list title must be present!",         │
/// │     "path": "uri=/api/tasks"                               │
/// │   }                                                        │
/// └────────────────────────────────────────────────────────────┘