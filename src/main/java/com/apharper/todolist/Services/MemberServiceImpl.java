package com.apharper.todolist.Services;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.MemberRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {


    private MemberRepo memberRepo;

    public MemberServiceImpl(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepo.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Entity not found with id " + id));
    }

    @Override
    public List<Member> findAll() {
        return memberRepo.findAll();
    }

    @Override
    public List<ToDo> findUserTasks(Long id) {
        try {
            Member member = this.getMemberById(id);
            return member.getTasks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<ToDo> findUserCompleted(Long id) {
        List<ToDo> tasks = this.findUserTasks(id);
        List<ToDo> completed = new ArrayList<>();

        for(ToDo item : tasks) {
            if (item.isCompleted()) {
                completed.add(item);
            }
        }
        return completed;
    }

    @Override
    public List<ToDo> findUserIncomplete(Long id) {
        List<ToDo> tasks = this.findUserTasks(id);
        List<ToDo> incomplete = new ArrayList<>();

        for(ToDo item : tasks) {
            if (!(item.isCompleted())) {
                incomplete.add(item);
            }
        }
        return incomplete;
    }

    @Override
    public Member addMember (Member member) {
        memberRepo.save(member);
        return member;
    }
}
