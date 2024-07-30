package com.apharper.todolist;

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

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
    private void loadData() {
        if(toDoListRepo.count() == 0) {
            ToDo toDo1 = new ToDo("Buy bread");
            ToDo toDo2 = new ToDo("Wash car");
            toDo1.setCompleted(true);

            toDoListRepo.save(toDo1);
            toDoListRepo.save(toDo2);

        }

        logger.info("Number of ToDo Items: {}", toDoListRepo.count());
    }
}
