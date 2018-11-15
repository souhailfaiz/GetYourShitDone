package com.academy.youngcapital.getyourshitdone.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.academy.youngcapital.getyourshitdone.model.Attachment;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Tasks implements Serializable {

    private ArrayList<Task> allTasks = new ArrayList<>();
    private ArrayList<Category> allCategories = new ArrayList<>();

    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();


    public Tasks(Context context) {
        // Set context & Wrapper
        this.context = context;

        //Opgeslagen tasks & Categorien ophalen
        readSharedPrefs();
    }

    public void readSharedPrefs()
    {
        // shared pref
        sharedPreferences = this.context.getSharedPreferences("tasksStorage", Context.MODE_PRIVATE);

        // data ophalen uit file
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Task>>() {
        }.getType();
        ArrayList<Task> testList = gson.fromJson(json, type);

        // Loop door json objects en in arrayList zetten
        if (testList != null) {
            for (Task item : testList) {
                this.createTask(item);
            }
        }

        // OPGESLAGEN CATEGORIES OPHALEN //

        // shared pref
        sharedPreferences = this.context.getSharedPreferences("catStorage", Context.MODE_PRIVATE);

        // data ophalen uit file
        json = sharedPreferences.getString("cat list", null);
        type = new TypeToken<ArrayList<Category>>() {
        }.getType();
        ArrayList<Category> testList2 = gson.fromJson(json, type);

        // loop door alle categoriene en toevoegen aan menu
        if (testList2 != null) {
            for (Category item : testList2) {
                this.createCategory(item);
            }
        }
    }

    public void deleteTask(int id) {
        for (Task item : allTasks) {
            if (item.getId() == id) {
                allTasks.remove(item);
                saveTasks();
                return;
            }
        }

    }

    private int getNewID() {
        if (allTasks.size() < 1) {
            return 0;
        }

        Random rand = new Random();
        Task lastTask = getAllTasks().get(getAllTasks().size() - 1);

        return lastTask.getId() + 1 + rand.nextInt(1000) - rand.nextInt(50);
    }

    public int getNewIDCategory() {
        if (allCategories.size() < 1) {
            return 0;
        }

        Random rand = new Random();
        Category lastCategory = getAllCategories().get(getAllCategories().size() - 1);

        return lastCategory.getId() + 1 + rand.nextInt(1000) - rand.nextInt(50);
    }

    public void createTask(String title, String description, boolean priority, Category category, Attachment attachment) {
        Task newTask = new Task(getNewID(), title, description, priority, category);

        if(attachment != null) {
            newTask.setAttachment(attachment);
        }
        this.allTasks.add(newTask);
        this.saveTasks();
    }

    // Method overloading - Voor direct task toevoegen
    private void createTask(Task addableTask) {
        this.allTasks.add(addableTask);
        this.saveTasks();
    }


    public void createCategory(Category cat) {
        this.allCategories.add(cat);
        this.saveCategories();
    }


    public ArrayList<Task> getTasksByCategory(int category) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        Log.d("catID:", Integer.toString(category));

        for (Task item : allTasks) {
            if (item.getCategory() != null) {
                if (item.getCategory().getId() == category) {
                    filteredTasks.add(item);
                }
            }
        }
        return filteredTasks;
    }

    public void removeAllCategories() {
        this.allCategories.clear();
        saveCategories();
    }

    public void removeCategoryById(int id) {
        for (Category category : this.getAllCategories()) {
            if (category.getId() == id) {
                this.allCategories.remove(category);
                saveCategories();
                return;
            }
        }

    }

    public ArrayList<Task> getAllTasks() {
        return this.allTasks;
    }

    public ArrayList<Category> getAllCategories() {
        return this.allCategories;
    }

    public void saveTasks() {

        // Sharedpref opzetten
        sharedPreferences = this.context.getSharedPreferences("tasksStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Object serialization
        String json = gson.toJson(this.getAllTasks());

        // In bestand opslaan
        editor.putString("task list", json);
        editor.apply();
    }

    public Category getCategoryByName(String name) {
        for (Category category : this.getAllCategories()) {
            if (category.getTitle().contains(name)) {
                return category;
            }
        }
        return null;
    }

    private void saveCategories() {
        // Sharedpref opzetten
        sharedPreferences = this.context.getSharedPreferences("catStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Object serialization
        String json = gson.toJson(this.getAllCategories());

        // In bestand opslaan
        editor.putString("cat list", json);
        editor.apply();
    }

    public Task getTaskById(int id) {
        for (Task item : this.getAllTasks()) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }
}
