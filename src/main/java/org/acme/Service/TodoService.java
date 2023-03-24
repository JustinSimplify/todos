package org.acme.Service;

import org.acme.Entity.Todo;
import org.acme.Dao.TodoRepository;
import org.acme.Entity.TodoCreateDTO;
import org.acme.Entity.TodoDTO;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class TodoService {

    @Inject
    TodoRepository todoRepository;
    @Inject
    JsonWebToken jwt;

    public List<TodoDTO> getAllTodos() {
        String userId = jwt.getSubject(); // Get user ID
        List<TodoDTO> res = new ArrayList<>();
        List<Todo> temp = todoRepository.listAll();
        for (Todo todo : temp) {
            if (Objects.equals(todo.getUserId(), userId)) {
                res.add(new TodoDTO(todo));
            }
        }
        return res;
    }

    public Response getTodoById(UUID id) {
        String userId = jwt.getSubject(); // Get user ID
        Todo todo = todoRepository.findById(id);

        if (todo != null && Objects.equals(todo.getUserId(), userId)) {
            return Response.ok(new TodoDTO(todo)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Transactional
    public Response createTodo(TodoCreateDTO todoCreateDTO) {
        String userId = jwt.getSubject(); // Get user ID
        Todo todo = todoCreateDTO.toEntity();
        todo.setUserId(userId); // Set the user ID
        todoRepository.create(todo);
        return Response.status(Response.Status.CREATED).entity(todo).build();
    }

    @Transactional
    public Response updateTodoById(UUID id, TodoDTO todoToUpdate) {
        Todo existingTodo = todoRepository.findById(id);
        String userId = jwt.getSubject(); // Get user ID

        if (existingTodo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (Objects.equals(existingTodo.getUserId(), userId)) {
            existingTodo.setTitle(todoToUpdate.getTitle());
            existingTodo.setCompleted(todoToUpdate.getCompleted());
            todoRepository.persist(existingTodo);
            return Response.ok(convertToDTO(existingTodo)).build();
        }
        return Response.noContent().build();
    }

    @Transactional
    public Response deleteTodoById(UUID id) {
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


    private TodoDTO convertToDTO(Todo todo) {
        return new TodoDTO(todo.getId(), todo.getTitle(), todo.getCompleted());
    }

}


