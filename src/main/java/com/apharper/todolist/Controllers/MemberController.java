package com.apharper.todolist.Controllers;

import com.apharper.todolist.Services.MemberServiceImpl;
import com.apharper.todolist.*;
import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.MemberRepo;
import com.apharper.todolist.Repositories.ToDoListRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping({"members", "members/"})
public class MemberController {

    private final MemberServiceImpl memberService;


    public MemberController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<Member> memberList = memberService.findAll();
            return ResponseEntity.ok(new ApiResponse(true, "Users returned successfully", memberList));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse(false, "User list not retrieved" , null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@RequestBody Member member) {
        try {
            Member toAdd = memberService.addMember(member);
            return ResponseEntity.ok
                    (new ApiResponse(true, "User created successfully", toAdd));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Unable to create user", null));
        }
    }

    @RequestMapping({"/{id}/tasks", "/{id}/tasks/"})
    public ResponseEntity<ApiResponse> getAllUserTasks(@PathVariable Long id, Member member) {
        try {
//            Member member = memberService.findById(id).orElse(null);
            List<ToDo> userTasks = memberService.findUserTasks(id);
            return ResponseEntity.ok(
                    new ApiResponse(true, "User tasks successfully retrieved", userTasks));
        } catch (Exception e) {


            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse(false, "User tasks list not retrieved", null));
        }
    }

    @RequestMapping({"/{id}/tasks/complete", "/{id}/tasks/complete/"})
        public ResponseEntity<ApiResponse> getUserCompletedTasks(@PathVariable Long id) {
        try {
            List<ToDo> complete = memberService.findUserCompleted(id);
            return ResponseEntity.ok(
                    new ApiResponse(true, "User completed tasks successfully retrieved", complete));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Completed tasks not retrieved", null));

        }
    }


    @RequestMapping({"/{id}/tasks/incomplete", "/{id}/tasks/incomplete/"})
    public ResponseEntity<ApiResponse> getUserIncompleteTasks(@PathVariable Long id) {
        try {
            List<ToDo> incomplete = memberService.findUserIncomplete(id);
            return ResponseEntity.ok(
                    new ApiResponse(true, "User complete tasks successfully retrieved", incomplete));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Incomplete tasks not retrieved", null));
        }
    }
}
