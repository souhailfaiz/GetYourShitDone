package com.academy.youngcapital.getyourshitdone.model;

import com.academy.youngcapital.getyourshitdone.model.Attachment;
import com.academy.youngcapital.getyourshitdone.model.Category;

import java.util.ArrayList;
import java.util.Date;

public class Task {
    private String title;
    private String description;

    private Category category;
    private ArrayList<Attachment> attachments = new ArrayList<>();

    private int priority;
    private boolean isCompleted;
    private Date date;

    public Task(String title, String description, int priority, Category category, ArrayList<Attachment> attachments, Date date) {
        this.title = title;
        this.description = description;

        this.category = category;
        this.attachments = attachments;

        this.priority = priority;
        this.isCompleted = false;
        this.date = date;
    }

    public String getTitle(){
        return this.title;
    }


}
