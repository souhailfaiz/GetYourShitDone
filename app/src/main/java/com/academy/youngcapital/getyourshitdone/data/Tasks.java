package com.academy.youngcapital.getyourshitdone.data;


import com.academy.youngcapital.getyourshitdone.model.Attachment;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.model.Task;

import java.util.ArrayList;
import java.util.Date;

public class Tasks {

    private ArrayList<Task> allTasks = new ArrayList<>();

    public void createTask(String title, String description, int priority, Category category, ArrayList<Attachment> attachments) {
        this.allTasks.add(new Task(title, description, priority, category, attachments));
    }

    // Method overloading - Voor direct task toevoegen
    public void createTask(Task addableTask)
    {
        this.allTasks.add(addableTask);
    }


    public ArrayList<String> getItems() {
        ArrayList<String> list = new ArrayList<>();
        for (Task item : this.allTasks) {
            list.add(item.getTitle());
        }
        return list;
    }

    public ArrayList<Task> getAllTasks() {
        return this.allTasks;
    }

}
