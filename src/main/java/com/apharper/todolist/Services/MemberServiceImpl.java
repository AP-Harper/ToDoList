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

    public MemberServiceImpl(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepo.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberRepo.findAll();
    }

    @Override
    public List<ToDo> findUserTasks(Long id) {
        try {
            Member member = memberRepo.findById(id).orElseThrow(Exception::new);
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
    public List<ToDo> findUserIncompleted(Long id) {
        List<ToDo> tasks = this.findUserTasks(id);
        List<ToDo> incompleted = new ArrayList<>();

        for(ToDo item : tasks) {
            if (!(item.isCompleted())) {
                incompleted.add(item);
            }
        }
        return incompleted;
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
