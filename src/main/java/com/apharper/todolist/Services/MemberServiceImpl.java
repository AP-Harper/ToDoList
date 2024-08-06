package com.apharper.todolist.Services;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Repositories.MemberRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {



    private MemberRepo memberRepo;
//    private ToDoListRepo toDoListRepo;


    public MemberServiceImpl() {
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepo.findById(id);
    }



}
