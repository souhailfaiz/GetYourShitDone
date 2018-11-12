package com.academy.youngcapital.getyourshitdone.controller;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.widget.Spinner;
import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    private static final
    String TAG = "MainActivity";

    // data/tasks.class
    private Tasks dataTasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerId);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataTasks = new Tasks();

        // TEST data //
        Category cat = new Category("TestCategorie", "blue");
        dataTasks.createTask("School Project", "Schoolprojecte nog afmaken", 0, cat, null);
        dataTasks.createTask("Tasknumero2", "Nog een task", 0, cat, null);
        // END Test data //

        getTasks();

    }

    private void getTasks() {

        // shared pref
        SharedPreferences sharedPreferences = getSharedPreferences("tasksStorage", MODE_PRIVATE);
        Gson gson = new Gson();

        // data ophalen uit file
        String json =  sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> testList = new ArrayList<>();
        testList = gson.fromJson(json, type);

        // loop door alle tasks en toevoegen aan listview and data class
        for(Task item : testList)
        {
            dataTasks.createTask(item);

            // Code om tasks in listview zetten moet hier
        }
    }

    private void saveTasks() {

        // Sharedpref opzetten
        SharedPreferences sharedPreferences = getSharedPreferences("tasksStorage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Object serialization
        Gson gson = new Gson();
        String json = gson.toJson(dataTasks.getAllTasks());

        // In bestand opslaan
        editor.putString("task list",   json);
        editor.apply();

        // Kan weg na test
        Toast.makeText(getApplicationContext(), "Data opgeslagen", Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LinearLayout layout = new LinearLayout(this);
        switch (item.getItemId()) {
            case R.id.action_add_task:
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText taskEditText11 = new EditText(this);
                final EditText taskEditText21 = new EditText(this);
                layout.addView(taskEditText11);
                taskEditText11.setHint("Task title");
                taskEditText21.setHint("Task Description");
                layout.addView(taskEditText21);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new Task")
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String categoryName = String.valueOf(taskEditText11.getText());
                                String categoryColor = String.valueOf(taskEditText21.getText());

                                Category category = new Category(categoryName, categoryColor);
                                Log.d(TAG, "Category to add: " + category.getColor()+ category.getTitle());
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

            case R.id.action_add_category:
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText taskEditText1 = new EditText(this);
                final EditText taskEditText2 = new EditText(this);
                layout.addView(taskEditText1);
                taskEditText1.setHint("Category name");;
                taskEditText2.setHint("Category Color");;
                layout.addView(taskEditText2);
                AlertDialog dialog1 = new AlertDialog.Builder(this)
                        .setTitle("Add a new Category")
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String categoryName = String.valueOf(taskEditText1.getText());
                                String categoryColor = String.valueOf(taskEditText2.getText());

                                Category category = new Category(categoryName, categoryColor);
                                Log.d(TAG, "Category to add: " + category.getColor()+ category.getTitle());
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog1.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public boolean onOptionsItemSelectedAmin(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))

            return true;

        return super.onOptionsItemSelected(item);
    }
}
