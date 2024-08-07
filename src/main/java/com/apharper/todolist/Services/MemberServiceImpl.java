package com.apharper.todolist.Services;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.MemberRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepo memberRepo;

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepo.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberRepo.findAll();
    }

    @Override
    public Member save (Member member) {
        memberRepo.save(member);
        return member;
    }

    @Override
    public void addTaskToMember(ToDo toDo) {
        Member member = toDo.getMember();
        member.addTask(toDo);
    }
}
