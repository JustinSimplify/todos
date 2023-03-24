package org.acme.API;

import org.acme.Entity.Todo;
import org.acme.Service.TodoService;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.Authenticated;
import javax.inject.Inject;
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
    TodoService todoService;

    @GET
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GET
    @Path("/{id}")
    public Response getTodoById(@PathParam("id") Long id) {
        return todoService.getTodoById(id);
    }

    @POST
    public Response createTodo(Todo todo) {
        return todoService.createTodo(todo);
    }

    @PUT
    @Path("/{id}")
    public Response updateTodoById(@PathParam("id") Long id, Todo todoToUpdate) {
        return todoService.updateTodoById(id, todoToUpdate);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTodoById(@PathParam("id") Long id) {
        return todoService.deleteTodoById(id);
    }
}
