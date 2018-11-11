package com.academy.youngcapital.getyourshitdone.controller;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getTasks();
    }

    private void getTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("tasksStorage", MODE_PRIVATE);
        Gson gson = new Gson();
        String json =  sharedPreferences.getString("task list", null);

        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        ArrayList testList = new ArrayList<>();
        testList = gson.fromJson(json, type);

        // output testen
        String finall = "";

        for (int i = 0; i < testList.size(); i++) {
            finall = finall + testList.get(i);
        }

        Toast.makeText(getApplicationContext(), finall, Toast.LENGTH_LONG).show();
    }

    private void saveTasks() {
        // Test string array
        ArrayList<String> allTasks = new ArrayList();
        allTasks.add("task1");
        allTasks.add("  - task 2");
        allTasks.add(" - task 3");


        // Sharedpref opzetten
        SharedPreferences sharedPreferences = getSharedPreferences("tasksStorage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Object serialization
        Gson gson = new Gson();
        String json = gson.toJson(allTasks);

        // In bestand opslaan
        editor.putString("task list",   json);
        editor.apply();

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
                taskEditText11.setHint("Task title");;
                taskEditText21.setHint("Task Description");;
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
}
