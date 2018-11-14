package com.academy.youngcapital.getyourshitdone.model;

import android.graphics.Color;

public class Category{
    private int id;
    private String title;
    private String color;
    private int colorCode;

    public Category(int id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;

        this.colorCode = createColor(color);
    }

    public int getColorCode()
    {
        return this.colorCode;
    }

    private int createColor(String color) {

        switch (color.toLowerCase()) {
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "blue":
                return Color.BLUE;
            case "black":
                return Color.BLACK;
            case "gray":
                return Color.GRAY;
            case "cyan":
                return Color.CYAN;
            case "magenta":
                return Color.MAGENTA;
            default:
                return Color.BLACK;
        }
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
