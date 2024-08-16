package com.apharper.todolist.Controllers;
import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Services.ToDoListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers =  ToDoListController.class)
//@ExtendWith(MockitoExtension.class)
class ToDoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoListServiceImpl toDoListService;

    private Member member;
    private ToDo toDo1;
    private ToDo toDo2;

    private ToDo toDo3;
    private List<ToDo> incompleteTasks;
    private List<ToDo> completedTasks;


    @BeforeEach
    void setUp() {
         member = new Member("Test");
         member.setId(1L);
        incompleteTasks = new ArrayList<>();
        completedTasks = new ArrayList<>();
        toDo1 = new ToDo("Wash", member);
        toDo2 = new ToDo("Clean", member);
        incompleteTasks = Arrays.asList(toDo1, toDo2);

        toDo3 = new ToDo("Cook", member);
        toDo3.setCompleted(true);
        completedTasks.add(toDo3);

    }

    @Test
    public void testGetAllToDos() throws Exception {

        when(toDoListService.findAll()).thenReturn(incompleteTasks);

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

        long userId = 1L;
        toDo1.setId(userId);

        Mockito.when(toDoListService.findById(Mockito.anyLong())).thenReturn(Optional.of(toDo1));
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/tasks/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.id").value(1))
                        .andExpect(jsonPath("$.data.task").value("Wash"))
                        .andExpect(jsonPath("$.data.completed").value(false));
    }

    @Test
    public void testGetCompletedTasks() throws Exception {
        toDo1.setCompleted(true);


        when(toDoListService.getCompletedTasks()).thenReturn(completedTasks);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tasks/completed")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].completed").value(true))
                .andExpect(jsonPath("$.data.*", hasSize(1)));

    }

    @Test
    public void testGetIncompleteTasks() throws Exception {
        when(toDoListService.getCompletedTasks()).thenReturn(incompleteTasks);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tasks/completed")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].completed").value(false))
                .andExpect(jsonPath("$.data.*", hasSize(2)));
    }

    @Test
    public void testCreateToDo() throws Exception {

        ToDo toTest = new ToDo("Created", member);
        toTest.setId(1L);
        member.setId(1L);

        String jsonData = "{\"id\":1,\"member\":{\"id\":1,\"name\":\"Test\"},\"task\":\"Shop\"}";
        when(toDoListService.createToDo(Mockito.any(ToDo.class))).thenReturn(toTest);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.task").value("Created"))
                .andExpect(jsonPath("$.data.completed").value(false))
                .andExpect(jsonPath("$.data.member.id").value(1));
    }

    @Test
    public void setAsCompleted() throws Exception {
        toDo1.setCompleted(true);
        toDo1.setId(1L);
        when(toDoListService.setAsCompleted(Mockito.anyLong())).thenReturn(toDo1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/tasks/completed/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.completed").value(true));
    }
}







