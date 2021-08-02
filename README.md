# tasks-api
This API offers methods to manipule tasks registers
Postman collection test: https://www.getpostman.com/collections/65c25df3e84be7c78bba

Database: POSTGRESQL

Methods:
POST /tasks - Create a new task - Returns the id of created task
GET /tasks/{id} - Get a task by id
PUT /tasks/{id} - Update a task by its id
DELETE /tasks/{id} - Delete a task by its id
GET /tasks/describe/{id} - Get the task description by its id
GET /tasks - Return all tasks registered
GET /tasks/describe - Return all task's descriptions

