package com.apharper.todolist.Services;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long id);

    List<Member> findAll();

    List<ToDo> findUserTasks(Long id);

    List<ToDo> findUserCompleted(Long id);

    List<ToDo> findUserIncompleted(Long id);

    Member addMember(Member member);

    void addTaskToMember(ToDo toDo);


}
