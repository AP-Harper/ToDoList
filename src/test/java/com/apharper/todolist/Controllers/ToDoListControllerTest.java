package com.apharper.todolist.Controllers;

import com.apharper.todolist.ApiResponse;
import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Services.MemberService;
import com.apharper.todolist.Services.MemberServiceImpl;
import com.apharper.todolist.Services.ToDoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers =  ToDoListController.class)
class ToDoListControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ToDoListController toDoListController;
    @MockBean
    private ToDoListService toDoListService;
    @MockBean
    private MemberServiceImpl memberService;





    @BeforeEach
    void setUp() {
        Member member = new Member("Test");
        ToDo toDo1 = new ToDo("Wash", member);
        ToDo toDo2 = new ToDo("Clean", member);
        memberService.save(member);
        toDoListService.save(toDo1);
        toDoListService.save(toDo2);

    }

    @Test
    public void testGetAllToDos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/tasks")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tasks[*].id").isNotEmpty());
    }

    @Test
    public void testGetToDoById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/tasks/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tasks.id").value(1));
    }







}