package com.apharper.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepo extends JpaRepository<ToDo, Long> {
}
