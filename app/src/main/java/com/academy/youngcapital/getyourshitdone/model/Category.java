package com.academy.youngcapital.getyourshitdone.model;

public class Category{
    private int num;
    private String title;
    private String color;

    public Category(String title, String color) {
        this.title = title;
        this.color = color;
        this.num +=1;
    }

    public int getNum(){
        return this.num;
    }

    public String getTitle(){
        return this.title;
    }

    public String getColor(){
        return this.color;
    }


    }
