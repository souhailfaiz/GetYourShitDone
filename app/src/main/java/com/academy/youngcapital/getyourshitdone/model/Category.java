package com.academy.youngcapital.getyourshitdone.model;

public class Category{
    private int id;
    private String title;
    private String color;

    public Category(int id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;

    }



    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getColor(){
        return this.color;
    }


    }
