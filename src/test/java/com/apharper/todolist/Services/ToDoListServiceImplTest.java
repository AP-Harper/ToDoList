package com.apharper.todolist.Services;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.ToDoListRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToDoListServiceImplTest {

    @Mock
    ToDoListRepo toDoListRepo;

    @InjectMocks
    ToDoListServiceImpl toDoListService;

    List<ToDo> tasks = new ArrayList<>();

    Member member;

    ToDo toDo1;
    ToDo toDo2;
    ToDo toDo3;

    @BeforeEach
    void setUp() {
        member = new Member("Test");
        member.setId(1L);
        toDo1 = new ToDo("Wash", member);
        toDo1.setId(1L);
        toDo2 = new ToDo("Clean", member);
        toDo2.setId(2L);
        toDo3 = new ToDo("Cook", member);
        toDo3.setId(3L);

        toDo1.setCompleted(true);

        tasks = Arrays.asList(toDo1, toDo2, toDo3);


    }

    @Test
    void findById() {
//        Long id = 1L;
//        ToDo toDoById = toDoListService.findById(id).orElseThrow(RuntimeException::new);
//        when(toDoListService.findById(id)).thenReturn(Optional.of(toDoById));
//        assertEquals(id, toDoById.getId());
    }

    @Test
    void findAll() {
        when(toDoListService.findAll()).thenReturn(tasks);
        List<ToDo> result = toDoListService.findAll();
        assertEquals(3, tasks.size());
    }

    @Test
    void getCompletedTasks() {
        List<ToDo> completedTasks = new ArrayList<>();
        for (ToDo task : tasks) {
            if (task.isCompleted()) {
                completedTasks.add(task);
            }
        }
        when(toDoListService.getCompletedTasks()).thenReturn(completedTasks);
        int result = toDoListService.getCompletedTasks().size();
        assertEquals(completedTasks.size(), result);
        assert(completedTasks.get(0).isCompleted());
    }

    @Test
    void getIncompleteTasks() {
        List<ToDo> incompleteTasks = new ArrayList<>();
        for (ToDo task : tasks) {
            if (!(task.isCompleted())) {
                incompleteTasks.add(task);
            }
        }
        when(toDoListService.getIncompleteTasks()).thenReturn(incompleteTasks);
        int result = toDoListService.getIncompleteTasks().size();
        assertEquals(incompleteTasks.size(), result);
        assert(!(incompleteTasks.get(0).isCompleted()));
    }

    @Test
    void createToDo() {
    }

    @Test
    void setAsCompleted() {
    }

    @Test
    void deleteToDo() {
    }
}