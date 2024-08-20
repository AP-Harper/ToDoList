package com.apharper.todolist.Controllers;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Services.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
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
    void testGetAllUsers() throws Exception {
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
    void testAddUser() throws Exception {
        member1.setId(1L);
        member1.setName("Andrew");
        String jsonData = "{\"id\":1,\"name\":\"Test\"}";
        when(memberService.addMember(Mockito.any(Member.class))).thenReturn(member1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Andrew"));
    }

    @Test
    void testGetAllUserTasks() throws Exception {
        Long userId = 1L;
        member1.setId(1L);
        List<ToDo> membersTasks = Arrays.asList(toDo, toDo2);
        when(memberService.findUserTasks(Mockito.anyLong())).thenReturn(membersTasks);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/members/{id}/tasks", userId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.*", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].id").value(2));
    }

    @Test
    void testGetUserCompletedTasks() throws Exception {
        Long userId = 1L;
        toDo.setCompleted(true);
        toDo2.setCompleted(true);
        List<ToDo> completedTasks = Arrays.asList(toDo, toDo2);
        when(memberService.findUserCompleted(Mockito.anyLong())).thenReturn(completedTasks);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/members/{id}/tasks/complete", userId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.*", hasSize(2)))
                .andExpect(jsonPath("$.data[0].completed").value(true))
                .andExpect(jsonPath("$.data[1].completed").value(true));
    }

    @Test
    void testGetUserIncompleteTasks() throws Exception {
        Long userId = 1L;
        toDo.setCompleted(false);
        toDo2.setCompleted(false);
        List<ToDo> incompleteTasks = Arrays.asList(toDo, toDo2);
        when(memberService.findUserIncomplete(Mockito.anyLong())).thenReturn(incompleteTasks);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/members/{id}/tasks/incomplete", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.*", hasSize(2)))
                .andExpect(jsonPath("$.data[0].completed").value(false))
                .andExpect(jsonPath("$.data[1].completed").value(false));
    }
}