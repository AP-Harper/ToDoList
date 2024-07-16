package com.apharper.todolist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            ToDo toDo = toDoListRepo.findById(id).orElseThrow(RuntimeException::new);
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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateToDo(@PathVariable Long id, @RequestBody ToDo toDoDetails) {
        try {
            ToDo toDoToUpdate = toDoListRepo.findById(id).orElseThrow(RuntimeException::new);
            toDoToUpdate.setTask(toDoDetails.getTask());
            toDoToUpdate.setCompleted(toDoDetails.isCompleted());
            ToDo updatedToDo = toDoListRepo.save(toDoToUpdate);
            return ResponseEntity.ok(new ApiResponse(true, id + " updated successfully", updatedToDo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Unable to update ToDo Item with ID: " + id, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteToDo(@PathVariable Long id) {
        try {
            ToDo toDoToDelete = toDoListRepo.findById(id).orElseThrow(RuntimeException::new);
            toDoListRepo.delete(toDoToDelete);
            return ResponseEntity.ok(new ApiResponse(true, id + " deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Unable to delete " + id, null));
        }
    }
}
