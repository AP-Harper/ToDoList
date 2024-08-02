package com.apharper.todolist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
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

//    @Override
//    public List<ToDo> getToDoList() {
//        return tasks;
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
