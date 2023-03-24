package org.acme.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

// Date transfer object
public class TodoDTO {
    private String title;
    private Boolean completed;
    private UUID id;

    public TodoDTO() {

    }

    public TodoDTO(UUID id, String title, Boolean completed) {
            this.id = id;
            this.title = title;
            this.completed = completed;
        }


    public TodoDTO(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.completed = todo.getCompleted();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }
    @JsonProperty
    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
        this.id = id;
    }
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Todo toEntity() {
        Todo todo = new Todo();
        todo.setTitle(this.title);
        todo.setCompleted(this.completed);
        return todo;
    }
}
