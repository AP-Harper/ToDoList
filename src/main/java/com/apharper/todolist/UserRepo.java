package com.apharper.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Member, ToDo> {



}
