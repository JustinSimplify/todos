package org.acme.Entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "todos")

public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id")
    private String user_Id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "completed", nullable = false)
    private Boolean completed;

    public Todo(UUID id, String title, Boolean completed, String user_Id) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.user_Id = user_Id;
    }

    public Todo() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return user_Id;
    }

    public void setUserId(String userId) {
        this.user_Id = userId;
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

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }


}