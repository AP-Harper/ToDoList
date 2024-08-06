package com.apharper.todolist.Services;

import com.apharper.todolist.Models.Member;
import org.springframework.expression.spel.ast.OpPlus;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long id);

}
