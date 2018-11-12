package com.academy.youngcapital.getyourshitdone.model;

import com.academy.youngcapital.getyourshitdone.model.Attachment;
import com.academy.youngcapital.getyourshitdone.model.Category;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Task {
    private int id;
    private String title;
    private String description;

    private Category category;
    private ArrayList<Attachment> attachments = new ArrayList<>();

    private int priority;
    private boolean isCompleted;
    private Date date;

    public Task(int id, String title, String description, int priority, Category category, ArrayList<Attachment> attachments) {
        this.id = id;
        this.title = title;
        this.description = description;

        this.category = category;
        this.attachments = attachments;

        this.priority = priority;

        this.isCompleted = false;

        setDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public Date getDate() {
        return this.date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    private void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.date = date;
    }


}
