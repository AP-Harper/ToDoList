package com.apharper.todolist;

import com.apharper.todolist.Models.Member;
import com.apharper.todolist.Models.ToDo;
import com.apharper.todolist.Repositories.MemberRepo;
import com.apharper.todolist.Repositories.ToDoListRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ToDoDataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(ToDoDataLoader.class);

    @Autowired
    ToDoListRepo toDoListRepo;

    @Autowired
    MemberRepo memberRepo;

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
    private void loadData() {
        if(toDoListRepo.count() == 0) {
            Member member1 = new Member("Andrew");
            Member member2 = new Member("John");
            memberRepo.save(member1);
            memberRepo.save(member2);
            ToDo toDo1 = new ToDo("Buy bread", member1);
            ToDo toDo2 = new ToDo("Wash car", member2);
            ToDo toDo3 = new ToDo("Clean kitchen", member2);
//            ToDo toDo1 = new ToDo("Buy bread");
//            ToDo toDo2 = new ToDo("Wash car");
//            ToDo toDo3 = new ToDo("Clean kitchen");
            toDo1.setCompleted(true);
            toDoListRepo.save(toDo1);
            toDoListRepo.save(toDo2);
            toDoListRepo.save(toDo3);
        }

        if (memberRepo.count() == 0 ) {

        }

        logger.info("Number of ToDo Items: {}", toDoListRepo.count());
        logger.info("Number of Users: {}", memberRepo.count());

    }
}
