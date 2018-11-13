package com.academy.youngcapital.getyourshitdone.data;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Log;

import com.academy.youngcapital.getyourshitdone.controller.MainActivity;
import com.academy.youngcapital.getyourshitdone.model.Attachment;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class Tasks implements Serializable {

    private ArrayList<Task> allTasks = new ArrayList<>();
    private ArrayList<Category> allCategories = new ArrayList<>();

    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();


    public Tasks(Context context) {
        // Set context & Wrapper
        this.context = context;


        // OPGESLAGEN TASKS OPHALEN //

        // shared pref
        sharedPreferences = this.context.getSharedPreferences("tasksStorage", this.context.MODE_PRIVATE);

        // data ophalen uit file
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Task>>() {
        }.getType();
        ArrayList<Task> testList = new ArrayList<>();
        testList = gson.fromJson(json, type);

        // Loop door json objects en in arrayList zetten
        if (testList != null) {
            for (Task item : testList) {
                this.createTask(item);
                Log.d("MainActivity", item.getTitle());
            }
        }

        // OPGESLAGEN CATEGORIES OPHALEN //

        // shared pref
        sharedPreferences = this.context.getSharedPreferences("catStorage", this.context.MODE_PRIVATE);

        // data ophalen uit file
        json = sharedPreferences.getString("cat list", null);
        type = new TypeToken<ArrayList<Category>>() {
        }.getType();
        ArrayList<Category> testList2 = new ArrayList<>();
        testList2 = gson.fromJson(json, type);

        // loop door alle categoriene en toevoegen aan menu
        if (testList2 != null) {
            for (Category item : testList2) {
                this.createCategory(item);
            }
        }

    }

    public void deleteTask(int id)
    {
        for(Task item : allTasks)
        {
            if(item.getId() == id)
            {
                allTasks.remove(item);
                saveTasks();

            }
        }



    }

    private int getNewID()
    {
        if(allTasks.size() < 1){
            return 0;
        }

        Task lastTask = getAllTasks().get(getAllTasks().size() - 1);
        lastTask.getId();

        return lastTask.getId() + 1;
    }

    public int getNewIDCategory()
    {
        if(allCategories.size() < 1){
            return 0;
        }

        Category lastCategory = getAllCategories().get(getAllCategories().size() - 1);
        lastCategory.getId();

        return lastCategory.getId() + 1;
    }

    public void createTask(String title, String description, boolean priority, Category category, ArrayList<Attachment> attachments) {

        this.allTasks.add(new Task(getNewID(), title, description, priority, category, attachments));

        this.saveTasks();
    }

    // Method overloading - Voor direct task toevoegen
    public void createTask(Task addableTask) {
        this.allTasks.add(addableTask);
        this.saveTasks();
    }

    public void createCategory(String name, String kleur) {
        this.allCategories.add(new Category(getNewIDCategory(), name, kleur));
        this.saveCategories();
    }


    public void createCategory(Category cat) {
        this.allCategories.add(cat);
        this.saveCategories();
    }


    public ArrayList<String> getItems() {
        ArrayList<String> list = new ArrayList<>();
        for (Task item : this.allTasks) {
            list.add(item.getTitle());
        }
        return list;
    }

    public ArrayList<Task> getTasksByCategory(int category) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task item : this.getAllTasks()) {
            if (item.getCategory().getId() == category) {
                filteredTasks.add(item);
            }
        }
        return filteredTasks;
    }

    public void removeCategoryById(int id) {
        this.allCategories.remove(id);
        saveCategories();
    }

    public ArrayList<Task> getAllTasks() {
        return this.allTasks;
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void saveTasks() {

        // Sharedpref opzetten
        sharedPreferences = this.context.getSharedPreferences("tasksStorage", this.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Object serialization
        String json = gson.toJson(this.getAllTasks());

        // In bestand opslaan
        editor.putString("task list", json);
        editor.apply();
    }

    public Category getCategoryByName(String name){
        for(Category category : this.getAllCategories()){
            if(category.getTitle().contains(name)){
                return category;
            }
        }
        return null;
    }

    public void saveCategories() {
        // Sharedpref opzetten
        sharedPreferences = this.context.getSharedPreferences("catStorage", this.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Object serialization
        String json = gson.toJson(this.getAllCategories());

        // In bestand opslaan
        editor.putString("cat list", json);
        editor.apply();
    }

    public Task getTaskById(int id) {
        for (Task item : allTasks) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }
}
