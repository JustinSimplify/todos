package org.acme.Service;

import org.acme.Entity.Todo;
import org.acme.Dao.TodoRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@ApplicationScoped
public class TodoService {

    @Inject
    TodoRepository todoRepository;
    @Inject
    JsonWebToken jwt;

    public List<Todo> getAllTodos() {
        String userId = jwt.getSubject(); // Get user ID
        List<Todo> res = new ArrayList<>();
        List<Todo> temp = todoRepository.listAll();
        for (Todo todo : temp) {
            if (Objects.equals(todo.getUserId(), userId)) {
                res.add(todo);
            }
        }
        return res;
    }

    public Response getTodoById(Long id) {
        String userId = jwt.getSubject(); // Get user ID
        Todo todo = todoRepository.findById(id);

        if (todo != null && Objects.equals(todo.getUserId(), userId)) {
            return Response.ok(todo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Transactional
    public Response createTodo(Todo todo) {
        String userId = jwt.getSubject(); // Get user ID
        todo.setUserId(userId); // Set the user ID
        todoRepository.create(todo);
        return Response.status(Response.Status.CREATED).entity(todo).build();
    }

    @Transactional
    public Response updateTodoById(Long id, Todo todoToUpdate) {
        Todo existingTodo = todoRepository.findById(id);
        String userId = jwt.getSubject(); // Get user ID

        if (existingTodo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (Objects.equals(existingTodo.getUserId(), userId)) {
            existingTodo.setTitle(todoToUpdate.getTitle());
            existingTodo.setCompleted(todoToUpdate.getCompleted());
            todoRepository.persist(existingTodo);
            return Response.ok(existingTodo).build();
        }
        return Response.noContent().build();
    }

    @Transactional
    public Response deleteTodoById(Long id) {
        Todo existingTodo = todoRepository.findById(id);
        String userId = jwt.getSubject(); // Get user ID

        if (existingTodo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (Objects.equals(existingTodo.getUserId(), userId)) {
            todoRepository.delete(existingTodo);
            return Response.noContent().build();
        }
        return Response.noContent().build();
    }
}


