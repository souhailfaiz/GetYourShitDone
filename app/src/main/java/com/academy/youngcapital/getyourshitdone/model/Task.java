package com.academy.youngcapital.getyourshitdone.model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.academy.youngcapital.getyourshitdone.model.Attachment;
import com.academy.youngcapital.getyourshitdone.model.Category;


import java.io.Serializable;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Task {
    private int id;
    private String title;
    private String description;

    private Category category;
    private Bitmap uriPicture;

    private boolean priority;
    private boolean isCompleted;
    private Date date;

    public Task(int id, String title, String description, boolean priority, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;

        this.category = category;

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

    public Bitmap getUriPicture() {
        return uriPicture;
    }

    public void setUriPicture(Bitmap uriPicture) {
        this.uriPicture = uriPicture;
    }

    private void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.date = date;
    }


}
