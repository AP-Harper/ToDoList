package com.apharper.todolist;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/todos")
public class ToDoListController {
    private final ToDoListRepo toDoListRepo;

    public ToDoListController(ToDoListRepo toDoListRepo) {
        this.toDoListRepo = toDoListRepo;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllToDos(){
        try {
            List<ToDo> toDolist = toDoListRepo.findAll();
            return ResponseEntity.
                    ok(new ApiResponse(true, "All ToDos successfully retrieved", toDolist));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error in retrieving ToDos", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getToDoById(@PathVariable Long id) {
        try {
            ToDo toDo = toDoListRepo.findById(id).orElseThrow(NoSuchElementException::new);
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "ToDo retrieved successfully", toDo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Error retrieving ToDo with ID: " + id, null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createToDo(@RequestBody ToDo todo) {
        try {
            ToDo newToDo = toDoListRepo.save(todo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "New ToDo Item created successfully", newToDo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error creating ToDo entry", null));
        }
    }
}
