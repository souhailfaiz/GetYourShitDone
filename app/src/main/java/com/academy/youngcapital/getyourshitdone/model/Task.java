package com.academy.youngcapital.getyourshitdone.model;

import java.util.Date;

public class Task {
    private int id;
    private String title;
    private String description;
    private Attachment attachment;

    private Category category;

    private boolean priority;
    private boolean isCompleted;
    private Date date;

    public Task(int id, String title, String description, boolean priority, Category category) {
        // create new task
        this.id = id;
        this.title = title;
        this.description = description;

        this.category = category;

        this.priority = priority;

        this.isCompleted = false;

        setDate();
    }

    //getters and setters for task

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean getPriority() {
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

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    private void setDate() {
        this.date = new Date();
    }


}
