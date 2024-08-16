package com.apharper.todolist.Controllers;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Services.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberService;

    private Member member1;
    private Member member2;
    private ToDo toDo;
    private ToDo toDo2;

    List<Member> members;

    @BeforeEach
    void setUp() {
        member1 = new Member("Test 1");
        member1.setId(1L);
        member2 = new Member("Test 2");
        member2.setId(2L);

        toDo = new ToDo("Wash", member1);
        toDo.setId(1L);
        toDo2 = new ToDo("Clean", member1);
        toDo2.setId(2L);

        members = Arrays.asList(member1, member2);

    }

    @Test
    void getAllUsers() throws Exception {

        when(memberService.findAll()).thenReturn(members);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/members")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void addUser() {
    }

    @Test
    void getAllUserTasks() throws Exception {
        Long userId = 1L;
        List<ToDo> membersTasks = Arrays.asList(toDo, toDo2);
        when(memberService.findUserTasks(userId)).thenReturn(membersTasks);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/members/{id}/tasks", userId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect("$.data.*", hasSize(2));

    }

    @Test
    void getUserCompletedTasks() {
    }

    @Test
    void getUserIncompleteTasks() {
    }
}