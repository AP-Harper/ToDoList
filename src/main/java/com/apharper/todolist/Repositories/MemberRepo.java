package com.apharper.todolist.Repositories;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {


}
