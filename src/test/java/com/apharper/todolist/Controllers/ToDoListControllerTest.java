package com.apharper.todolist.Controllers;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Services.MemberServiceImpl;
import com.apharper.todolist.Services.ToDoListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers =  ToDoListController.class)
//@ExtendWith(MockitoExtension.class)
class ToDoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoListServiceImpl toDoListService;

//    @InjectMocks
//    private ToDoListController toDoListController;
//
//    @Mock
//    private ToDoListServiceImpl toDoListService;


//    MockMvc mockMvc;



    @BeforeEach
    void setUp() {

    }

    @Test
    public void testGetAllToDos() throws Exception {

        Member member = new Member("Test");
//        member.setId(1L);
        List<ToDo> tasks = new ArrayList<>();
        ToDo toDo1 = new ToDo("Wash", member);
        ToDo toDo2 = new ToDo("Clean", member);
        tasks.add(toDo1);
        tasks.add(toDo2);


        when(toDoListService.findAll()).thenReturn(tasks);

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
        member.setId(1L);
        ToDo toDo = new ToDo("Brush", member);

        long userId = 1L;
        toDo.setId(userId);

        Mockito.when(toDoListService.findById(Mockito.anyLong())).thenReturn(Optional.of(toDo));
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/tasks/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.id").value(1))
                        .andExpect(jsonPath("$.data.task").value("Brush"))
                        .andExpect(jsonPath("$.data.completed").value(false));
    }

    @Test
    public void testGetCompletedTasks() throws Exception {

        Member member = new Member("Test");
        member.setId(1L);
        List<ToDo> tasks = new ArrayList<>();
        ToDo toDo1 = new ToDo("Wash", member);
        ToDo toDo2 = new ToDo("Clean", member);
        tasks.add(toDo1);
        tasks.add(toDo2);

        when(toDoListService.getCompletedTasks()).thenReturn(tasks);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tasks/completed")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].completed").value(false));

    }
}







