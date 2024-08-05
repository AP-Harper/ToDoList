package com.apharper.todolist.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "member")
//    private List<ToDo> tasks = new ArrayList<>();

    public Member() {
    }


    public Member(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<ToDo> getTasks() {
//        return tasks;
//    }
//
//    public void addTask(ToDo task) {
//        tasks.add(task);
//    }


//    @Override
//    public List<ToDo> getCompleted() {
//        //TODO
//        return null;
//    }
//
//    @Override
//    public List<ToDo> getIncomplete() {
//        return null;
//    }
}
