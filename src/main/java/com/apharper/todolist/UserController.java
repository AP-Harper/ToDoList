package com.apharper.todolist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserRepo userRepo;


    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<Member> userList = userRepo.findAll();
            return ResponseEntity.ok(new ApiResponse(true, "Users returned successfully", userList));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse(false, "User list not retrieved" , null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@RequestBody Member member) {
        try {
            Member toAdd = userRepo.save(member);
            return ResponseEntity.ok
                    (new ApiResponse(true, "User created successfully", toAdd));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Unable to create user", null));
        }
    }




//    @RequestMapping("/{user}/tasks")
//    public ResponseEntity<ApiResponse> getAllTasks(@PathVariable Long id) {
//        try {
//            List<ToDo> allTasks = toDoListRepo.findAll();
//            List<ToDo> userTasks = new ArrayList<>();
//            for (ToDo item : allTasks) {
//                if (item.getId().equals(id)) {
//                    userTasks.add(item);
//                }
//            }
//            return ResponseEntity.ok(
//                    new ApiResponse(true, "User tasks successfully retrieved", userTasks));
//        }
//        catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).
//                    body(new ApiResponse(false, "User tasks list not retrieved", null));
//        }
//
//        }


}
