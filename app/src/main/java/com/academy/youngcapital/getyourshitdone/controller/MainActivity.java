package com.academy.youngcapital.getyourshitdone.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.util.ListAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    private static final
    String TAG = "MainActivity";
    public static final String MESSAGE = "com.example.SIMPLE_MESSAGE";

    // data/tasks.class
    private Tasks dataTasks;
    public static ListView listView;
    private ListAdapter listAdapter;
    private FloatingActionButton fab;

    public Tasks getDataTasks() {
        return dataTasks;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataTasks = new Tasks(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ?????
        listView = (ListView) findViewById(R.id.list_tasks);
        listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());

        listView.setAdapter(listAdapter);
        final Intent i = new Intent(this, EditActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int taskID = Integer.parseInt(view.getTag().toString());
                i.putExtra("task_id", taskID);
                startActivity(i);
            }
        });
        //????


        // Navigation toggle
        drawerLayout = findViewById(R.id.drawerId);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
        //add button to the layout
        Menu menu = navView.getMenu();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
                // Add category and All categories button listener in navigation
                switch (item.getItemId()) {
                    case R.id.addcategory:
                        showaddCategoryDialog();
                        drawerLayout.closeDrawer(navView);
                    case R.id.allcategories:
                        listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());
                        listView.setAdapter(listAdapter);
                        drawerLayout.closeDrawer(navView);
                }
                //Remove all categories button listener in navigation
                if (item.getItemId() == R.id.removeallcategories) {
                    for (Category category : dataTasks.getAllCategories()) {
                        navView.getMenu().removeItem(category.getId());
                        Log.d(TAG, "Category to remove" + category.getId());
                    }
                    navView.getMenu().removeItem(0);
                    dataTasks.removeAllCategories();
                    drawerLayout.closeDrawer(navView);
                }
                // created category in navigation button listener
                for (Category category : dataTasks.getAllCategories()) {
                    if (item.getItemId() == category.getId()) {
                        Toast.makeText(getApplicationContext(), "C" + item.getItemId() + " " + category.getId(), Toast.LENGTH_LONG).show();
                        listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getTasksByCategory(item.getItemId()));
                        listView.setAdapter(listAdapter);
                        drawerLayout.closeDrawer(navView);
                    }
                }
                return true;
            }
        });

        View.OnClickListener btnclick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "" + view.getId());

                NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
                navView.getMenu().removeItem(view.getId());
                dataTasks.removeCategoryById(view.getId());
                if (view.getId() == 0) {
                    navView.getMenu().removeItem(0);
                }
            }
        };


        // Add categories in navigation
        for (Category category : dataTasks.getAllCategories()) {
            final Button helloTextView = new Button(this);
            helloTextView.setText("X");
            helloTextView.setId(category.getId());
            helloTextView.setOnClickListener(btnclick);
            menu.add(0, category.getId(), 0, category.getTitle() + " " + category.getId()).setActionView(helloTextView);
        }

        // Event listener for + icon for new task
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog(view);
                Toast.makeText(getApplicationContext(), "Clicked add task", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showAddTaskDialog(View view) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //title
        final EditText taskEditText11 = new EditText(this);
        layout.addView(taskEditText11);
        taskEditText11.setHint("Task title");

        //description
        final EditText taskEditText21 = new EditText(this);
        layout.addView(taskEditText21);
        taskEditText21.setHint("Task Description");

        //Category dropdown label
        final TextView helloTextView = new TextView(this);
        helloTextView.setText("Category");
        layout.addView(helloTextView);

        //dropdown with category
        ArrayList<String> spinnerArray = new ArrayList<String>();
        for (Category category : dataTasks.getAllCategories()) {
            spinnerArray.add(category.getTitle());
        }
        final Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);
        layout.addView(spinner);

        //prio with category
        final Switch simpleSwitch = new Switch(this);
        simpleSwitch.setChecked(false);
        layout.addView(simpleSwitch);
        simpleSwitch.setText("Priority");

        //create dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new Task")
                .setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String category = spinner.getSelectedItem().toString();
                        boolean priority = simpleSwitch.isChecked();

                        String taskTitle = String.valueOf(taskEditText11.getText());
                        String taskDescription = String.valueOf(taskEditText21.getText());

                        if (taskTitle.length() > 2 && taskDescription.length() > 2) {
                            dataTasks.createTask(taskTitle, taskDescription, priority, dataTasks.getCategoryByName(category), null);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Voer een titel en beschrijving toe!", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }

    //add new category dialog
    public void showaddCategoryDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // editbox for catergory name
        final EditText taskEditText1 = new EditText(this);
        layout.addView(taskEditText1);
        taskEditText1.setHint("Category name");

        // editbox for category color
        final EditText taskEditText2 = new EditText(this);
        taskEditText2.setHint("Category Color");
        layout.addView(taskEditText2);

        //create dialog box
        final Button helloTextView = new Button(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new Category")
                .setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        View.OnClickListener btnclick = new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.d(TAG, "" + view.getId());

                                NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
                                navView.getMenu().removeItem(view.getId());
                                dataTasks.removeCategoryById(view.getId());
                                if (view.getId() == 0) {
                                    navView.getMenu().removeItem(0);
                                }
                            }
                        };

                        String categoryName = String.valueOf(taskEditText1.getText());
                        String categoryColor = String.valueOf(taskEditText2.getText());
                        Category cat = new Category(dataTasks.getNewIDCategory(), categoryName, categoryColor);
                        if (categoryName.length()>2){
                            dataTasks.createCategory(cat);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Voer een titel toe!", Toast.LENGTH_LONG).show();
                        }

                        NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
                        Menu menu = navView.getMenu();

                        helloTextView.setText("X");
                        helloTextView.setId(cat.getId());
                        helloTextView.setOnClickListener(btnclick);
                        menu.add(0, cat.getId(), 0, cat.getTitle() + " " + cat.getId()).setActionView(helloTextView);
                        Log.d(TAG, "Category to addfSDfdsfa: " + cat.getId());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    //hamburger menu toggle
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }
}
