package com.famnet.famnet;

import java.util.Date;

/**
 * Created by DungNguyen on 11/21/17.
 */

public class Task {
    private int id;
    private String name;
    private String description;
    private String reward;
    private Date deadline;
    private User taker;

    public Task(){
        this.id++;
    }

    public Task(String name, String description, Date deadline){
        this.id++;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public Task(String name, String description, String reward, Date deadline) {
        this.id++;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.deadline = deadline;
    }

    public Task(String name, String description, String reward, Date deadline, User taker) {
        this.id++;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.deadline = deadline;
        this.taker = taker;
    }
}
