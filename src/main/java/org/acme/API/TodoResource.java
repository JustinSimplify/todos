package org.acme.API;

import org.acme.Entity.Todo;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import io.quarkus.security.identity.SecurityIdentity;

import io.quarkus.security.Authenticated;

import org.acme.Dao.TodoRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/todos")
@SecurityRequirement(name = "Keycloak")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class TodoResource {

    @Inject
    TodoRepository todoRepository;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    public List<Todo> getAllTodos() {
        return todoRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getTodoById(@PathParam("id") Long id) {
        Todo todo = todoRepository.findById(id);
        if (todo != null) {
            return Response.ok(todo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Transactional
    public Response createTodo(Todo todo) {
        String userId = securityIdentity.getPrincipal().getName(); // Get user ID
        todo.setUserId(userId); // Set the user ID
        todoRepository.create(todo);
        return Response.status(Response.Status.CREATED).entity(todo).build();
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateTodoById(@PathParam("id") Long id, Todo todoToUpdate) {
        Todo existingTodo = todoRepository.findById(id);
        if (existingTodo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            existingTodo.setTitle(todoToUpdate.getTitle());
            existingTodo.setTitle(todoToUpdate.getTitle());
            existingTodo.setCompleted(todoToUpdate.getCompleted());
            todoRepository.persist(existingTodo);
            return Response.ok(existingTodo).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTodoById(@PathParam("id") Long id) {
        Todo existingTodo = todoRepository.findById(id);
        if (existingTodo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            todoRepository.delete(existingTodo);
            return Response.noContent().build();
        }
    }
}
