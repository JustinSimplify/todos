package org.acme.API;

import org.acme.Entity.Todo;
import org.acme.Entity.TodoCreateDTO;
import org.acme.Entity.TodoDTO;
import org.acme.Service.TodoService;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.Authenticated;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/todos")
@SecurityRequirement(name = "Keycloak")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @Inject
    TodoService todoService;

    @GET
    public List<TodoDTO> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GET
    @Path("/{id}")
    public Response getTodoById(@PathParam("id") UUID id) {
        return todoService.getTodoById(id);
    }

    @POST
    public Response createTodo(TodoCreateDTO todoCreateDTO) {
        return todoService.createTodo(todoCreateDTO);
    }


    @PUT
    @Path("/{id}")
    public Response updateTodoById(@PathParam("id") UUID id, TodoDTO todoToUpdate) {
        return todoService.updateTodoById(id, todoToUpdate);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTodoById(@PathParam("id") UUID id) {
        return todoService.deleteTodoById(id);
    }
}
