package com.academy.youngcapital.getyourshitdone.model;

public class Category{
    private String title;
    private String color;

    public Category(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public String getTitle(){
        return this.title;
    }

    public String getColor(){
        return this.color;
    }


    }
