package com.software.fire.todo.models;

/**
 * Created by Brad on 11/10/2016.
 */

public class Todo {
    private String toDo;
    private long time;

    public Todo() {
    }

    public Todo(String toDo, long time) {
        this.toDo = toDo;
        this.time = time;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
