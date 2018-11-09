package com.academy.youngcapital.getyourshitdone;


import java.util.ArrayList;
import java.util.Date;

public class Tasks {

    private ArrayList<Task> allTasks = new ArrayList<>();

    public void createTask(String title, String description, int priority, Category category, ArrayList<Attachment> attachments, Date date) {
            this.allTasks.add(new Task(title, description, priority, category, attachments, date));
    }

    public ArrayList<String> getItems() {
        ArrayList<String> list = new ArrayList<>();
        for (Task item : this.allTasks) {
            list.add(item.getTitle());
        }
        return list;
    }
}
