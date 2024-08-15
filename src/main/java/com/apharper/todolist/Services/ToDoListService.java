package com.apharper.todolist.Services;

import com.apharper.todolist.Models.ToDo;

import java.util.List;
import java.util.Optional;

public interface ToDoListService {



    Optional<ToDo> findById(Long id);

    List<ToDo> findAll();

    List<ToDo> getCompletedTasks();

    List<ToDo> getIncompleteTasks();

    ToDo createToDo(ToDo toDo);

    ToDo setAsCompleted(Long id);

    void deleteToDo(ToDo toDo);

}
