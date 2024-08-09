package com.apharper.todolist.Services;

import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.ToDoListRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    ToDoListRepo toDoListRepo;

    public ToDoListServiceImpl(ToDoListRepo toDoListRepo) {
        this.toDoListRepo = toDoListRepo;
    }

    @Override
    public Optional<ToDo> findById(Long id) {
        return toDoListRepo.findById(id);

    }

    @Override
    public List<ToDo> findAll() {
        return toDoListRepo.findAll();
    }

    @Override
    public ToDo save(ToDo toDo) {
        toDoListRepo.save(toDo);
        return toDo;
    }

    @Override
    public List<ToDo> getCompletedTasks() {
        List<ToDo> toDolist = this.findAll();
        List<ToDo> completed = new ArrayList<>();

        for (ToDo item : toDolist) {
            if (item.isCompleted()) {
                completed.add(item);
            }
        }
        return completed;
    }

    @Override
    public List<ToDo> getIncompleteTasks() {
        List<ToDo> toDoList = this.findAll();
        List<ToDo> incomplete = new ArrayList<>();

        for (ToDo item : toDoList) {
            if (!item.isCompleted()) {
                incomplete.add(item);
            }
        }
        return incomplete;
    }

    @Override
    public void delete(ToDo toDo) {
        toDoListRepo.delete(toDo);
    }
}
