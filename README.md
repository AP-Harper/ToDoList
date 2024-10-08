# ToDoList

To Do List API build using Spring Boot. This was built in order to learn about writing an API in Spring Boot.
It currently uses an in-memory H2 database.

It includes To-Do and Member entities, whereby new To-Do tasks must be assigned to a Member on creation. 


##  End Points

### To-Do Tasks

`GET /tasks` Return all tasks belonging to all Members \
`GET /tasks/{id}` Return specific tasks by its ID \
`GET /tasks/completed` Return all completed tasks \
`GET /tasks/incomplete` Return all incomplete tasks \
`PUT /tasks/completed/{id}` Mark a task as completed \
`GET /tasks/completed` Return all completed tasks \
`POST /tasks` Create a new To Do task \
`DELETE /tasks/{id}` Delete a task by its ID


### Member

`GET /members` Return all members \
`GET /members/{id}/tasks` Get all a member's To Do items \
`GET /members/{id}/complete` Get all a member's completed To Do items \
`GET /members/{id/incomplete` Get all a member's incomplete tasks \
`POST /members` Add a new member \


