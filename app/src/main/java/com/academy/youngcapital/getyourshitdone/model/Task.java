package com.academy.youngcapital.getyourshitdone.model;

import com.academy.youngcapital.getyourshitdone.model.Attachment;
import com.academy.youngcapital.getyourshitdone.model.Category;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public Task(String title, String description, int priority, Category category, ArrayList<Attachment> attachments) {
        this.title = title;
        this.description = description;

        this.category = category;
        this.attachments = attachments;

        this.priority = priority;
        this.isCompleted = false;

        setDate();
    }

    public String getTitle(){
        return this.title;
    }

    public void setDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.date = date;
    }


}
