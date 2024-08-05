package com.apharper.todolist.Repositories;

import com.apharper.todolist.Models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepo extends JpaRepository<ToDo, Long> {



}
