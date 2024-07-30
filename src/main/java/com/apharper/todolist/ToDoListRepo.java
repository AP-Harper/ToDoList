package com.apharper.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoListRepo extends JpaRepository<ToDo, Long> {



}
