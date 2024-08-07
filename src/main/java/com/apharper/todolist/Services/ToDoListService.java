package com.apharper.todolist.Services;

import com.apharper.todolist.Models.ToDo;

import java.util.List;
import java.util.Optional;

public interface ToDoListService {



    Optional<ToDo> findById(Long id);

    List<ToDo> findAll();

    ToDo save(ToDo toDo);

    List<ToDo> getCompletedTasks();

    List<ToDo> getIncompleteTasks();

    void delete(ToDo toDo);

}
