package com.academy.youngcapital.getyourshitdone.model;

import android.graphics.Color;

public class Category{
    private int id;
    private String title;
    private String color;
    private int colorCode;

    // Public constructor van de category
    public Category(int id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;

        this.colorCode = createColor(color);
    }

    // Get colorcode
    public int getColorCode()
    {
        return this.colorCode;
    }

    // Colorcode aanmaken van text naar color code.
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

    // Getter voor ID
    public int getId(){
        return this.id;
    }

    // Getter voor titel
    public String getTitle(){
        return this.title;
    }

    // Getter voor kleur
    public String getColor(){
        return this.color;
    }


    }
