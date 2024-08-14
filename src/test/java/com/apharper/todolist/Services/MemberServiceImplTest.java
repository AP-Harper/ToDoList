package com.apharper.todolist.Services;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.ToDoListRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private ToDoListRepo toDoListRepo;
    @InjectMocks
    private ToDoListServiceImpl toDoListService;



    @BeforeEach
    void setUp() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
        Member member = new Member("Test");
        List<ToDo> tasks = Arrays.asList(
                new ToDo("Wash", member),
                new ToDo("Clean", member)
        );

        when (toDoListService.findAll()).thenReturn(tasks);
        List<ToDo> result = toDoListService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findUserTasks() {
    }

    @Test
    void findUserCompleted() {
    }

    @Test
    void findUserIncompleted() {
    }

    @Test
    void save() {
    }

    @Test
    void addTaskToMember() {
    }
}