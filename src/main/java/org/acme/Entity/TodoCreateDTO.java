package org.acme.Entity;

public class TodoCreateDTO {
    private String title;
    private Boolean completed;

    public TodoCreateDTO(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public TodoCreateDTO() {
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
