package com.software.fire.todo.models;

/**
 * Created by Brad on 11/10/2016.
 */

public class Todo {
    private int id;
    private String description;
    private String time;

    public Todo() {
    }

    public Todo(int id, String description, String time) {
        this.id = id;
        this.description = description;
        this.time = time;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
