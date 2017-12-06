package com.famnet.famnet.Model;

import java.util.UUID;

/**
 * Created by DungNguyen on 11/21/17.
 */

public class User {
    private UUID id;
    private String name;
    private Family family;
    private String role;
    private String email;
    private Task[] tasks;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String name, Family family, String role, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.family = family;
        this.role = role;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }
}
