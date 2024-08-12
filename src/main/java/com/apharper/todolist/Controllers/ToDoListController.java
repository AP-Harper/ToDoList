package com.apharper.todolist.Controllers;

import com.apharper.todolist.ApiResponse;
import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.ToDoListRepo;
import com.apharper.todolist.Services.ToDoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"tasks", "tasks/"})
public class ToDoListController {
    private final ToDoListService toDoListService;

    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllToDos(){
        try {
            List<ToDo> toDolist = toDoListService.findAll();
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
            ToDo toDo = toDoListService.findById(id).orElseThrow(RuntimeException::new);
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "ToDo retrieved successfully", toDo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Error retrieving ToDo with ID: " + id, null));
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<ApiResponse> getCompletedTasks() {
        try {
            List<ToDo> completed = toDoListService.getCompletedTasks();
            return  ResponseEntity.ok().
                    body(new ApiResponse(true, "Completed tasks retrieved successfully", completed));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Error retrieving completed tasks", null));
        }
    }

    @GetMapping("/incomplete")
    public ResponseEntity<ApiResponse> getIncompleteTasks() {
        try {
            List<ToDo> incomplete = toDoListService.getIncompleteTasks();
            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            true, "Incompleted tasks successfully retrieved", incomplete));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Error retrieving incomplete tasks", null));
        }
    }


    @PostMapping
    public ResponseEntity<ApiResponse> createToDo(@RequestBody ToDo todo) {
        try {
            ToDo newToDo = toDoListService.save(todo);
            Member member = todo.getMember();
            member.addTask(todo);
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
            ToDo toDoToUpdate = toDoListService.findById(id).orElseThrow(RuntimeException::new);
            toDoToUpdate.setTask(toDoDetails.getTask());
            toDoToUpdate.setCompleted(toDoDetails.isCompleted());
            toDoToUpdate.setModifiedDate(Instant.now());
            ToDo updatedToDo = toDoListService.save(toDoToUpdate);
            return ResponseEntity.ok(new ApiResponse(true, id + " updated successfully", updatedToDo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Unable to update ToDo Item with ID: " + id, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteToDo(@PathVariable Long id) {
        try {
            ToDo toDoToDelete = toDoListService.findById(id).orElseThrow(RuntimeException::new);
            toDoListService.delete(toDoToDelete);
            return ResponseEntity.ok(new ApiResponse(true, id + " deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Unable to delete " + id, null));
        }
    }
}
