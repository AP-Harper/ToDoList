package com.apharper.todolist.Controllers;

import com.apharper.todolist.Services.MemberServiceImpl;
import com.apharper.todolist.*;
import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.MemberRepo;
import com.apharper.todolist.Repositories.ToDoListRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"members", "members/"})
public class MemberController {
    private final MemberRepo memberRepo;
    private final ToDoListRepo toDoListRepo;


    private final MemberServiceImpl memberService;


    public MemberController(MemberRepo memberRepo, ToDoListRepo toDoListRepo, MemberServiceImpl memberService) {
        this.memberRepo = memberRepo;
        this.toDoListRepo = toDoListRepo;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<Member> memberList = memberRepo.findAll();
            return ResponseEntity.ok(new ApiResponse(true, "Users returned successfully", memberList));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse(false, "User list not retrieved" , null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@RequestBody Member member) {
        try {
            Member toAdd = memberRepo.save(member);
            return ResponseEntity.ok
                    (new ApiResponse(true, "User created successfully", toAdd));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Unable to create user", null));
        }
    }
//
//    @GetMapping
//    @RequestMapping("/{id}")
//    public ResponseEntity<ApiResponse> addTask(@RequestBody ToDo toDo) {
//        try {
//            ToDo toAdd = ToDoListRepo.s
//
//        }
//    }




    @RequestMapping({"/{id}/tasks", "/{id}/tasks/"})
    public ResponseEntity<ApiResponse> getAllUserTasks(@PathVariable Long id) {
        try {
            Member member = memberService.findById(id).get();
            List<ToDo> userTasks = member.getTasks();



//            List<ToDo> allTasks = toDoListRepo.findAll();
//            List<ToDo> userTasks = new ArrayList<>();
//            for (ToDo item : allTasks) {
//                if (item.getMember().getId() == id) {
//                    userTasks.add(item);
//                }
//            }
            return ResponseEntity.ok(
                    new ApiResponse(true, "User tasks successfully retrieved", userTasks));
        }
        catch (Exception e) {
            Member member = memberService.findById(id).get();
            List<ToDo> userTasks = member.getTasks();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse(false, "User tasks list not retrieved", userTasks));
        }

        }


}
