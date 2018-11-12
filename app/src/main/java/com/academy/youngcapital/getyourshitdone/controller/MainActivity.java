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
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.academy.youngcapital.getyourshitdone.util.ListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // data/tasks.class
    private Tasks dataTasks;

    private ListView listView;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list_tasks);

        dataTasks = new Tasks();

        // TEST data //
        Category cat = new Category("TestCategorie", "blue");
        dataTasks.createTask(1, "School Project", "Schoolprojecte nog afmaken", 0, cat, null);
        dataTasks.createTask(2, "Tasknumero2", "Nog een task", 0, cat, null);
        // END Test data //

        saveTasks();
        getTasks();

        listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Clicked task id = " + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

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
