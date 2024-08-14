package com.apharper.todolist.Controllers;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;

import com.apharper.todolist.Services.ToDoListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers =  ToDoListController.class)
class ToDoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoListServiceImpl toDoListService;


    @BeforeEach
    void setUp() {
//        Member member = new Member("Test");
//        ToDo toDo = new ToDo("Brush", null);
//        List<ToDo> tasks = Arrays.asList(
//        new ToDo("Wash", member),
//        new ToDo("Clean", member)
//        );
//
    }

    @Test
    public void testGetAllToDos() throws Exception {

        Member member = new Member("Test");
        ToDo toDo = new ToDo("Brush", member);
        List<ToDo> tasks = new ArrayList<>();
        ToDo toDo1 = new ToDo("Wash", member);
        ToDo toDo2 = new ToDo("Clean", member);
        toDo1.setCompleted(true);
        toDo2.setCompleted(true);
        toDo1.setId(1L);
        toDo2.setId(2L);
        tasks.add(toDo1);
        tasks.add(toDo2);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/tasks")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").isBoolean());
    }

    @Test
    public void testGetToDoById() throws Exception {

        Member member = new Member("Test");
        ToDo toDo = new ToDo("Brush", member);
        List<ToDo> tasks = new ArrayList<>();
        ToDo toDo1 = new ToDo("Wash", member);
        ToDo toDo2 = new ToDo("Clean", member);
        toDo1.setCompleted(true);
        toDo2.setCompleted(true);
        toDo1.setId(1L);
        toDo2.setId(2L);
        tasks.add(toDo1);
        tasks.add(toDo2);

        long userId = 1L;
        toDo.setId(userId);

//        Mockito.when(toDoListService.findById(Mockito.anyLong())).thenReturn(Optional.of(toDo));
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/tasks/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCompletedTasks() throws Exception {

        Member member = new Member("Test");
        ToDo toDo = new ToDo("Brush", member);
        List<ToDo> tasks = new ArrayList<>();
        ToDo toDo1 = new ToDo("Wash", member);
        ToDo toDo2 = new ToDo("Clean", member);
        toDo1.setCompleted(true);
        toDo2.setCompleted(true);
        toDo1.setId(1L);
        toDo2.setId(2L);
        tasks.add(toDo1);
        tasks.add(toDo2);
        toDoListService.save(toDo1);
        toDoListService.save(toDo2);

//        when(toDoListService.getCompletedTasks()).thenReturn(tasks);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tasks/completed")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }
}







