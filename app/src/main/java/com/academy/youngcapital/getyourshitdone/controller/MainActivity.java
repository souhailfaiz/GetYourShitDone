package com.academy.youngcapital.getyourshitdone.controller;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
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

    public Tasks getDataTasks() {
        return dataTasks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataTasks = new Tasks(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerId);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
        Menu menu = navView.getMenu();

        for (Category num : dataTasks.getAllCategories()) {
            menu.add(R.id.navmenu, num.getNum(), Menu.NONE, num.getTitle());
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LinearLayout layout = new LinearLayout(this);
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        Log.d(TAG, ""+item.getItemId());
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
                                Log.d(TAG, "Category to add: " + category.getColor() + category.getTitle());
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
            case 5060:
                Log.d(TAG, "Category to addfSDfdsfa: ");

            case R.id.categoryMenuId:
            case R.id.action_add_category:
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText taskEditText1 = new EditText(this);
                final EditText taskEditText2 = new EditText(this);
                layout.addView(taskEditText1);
                taskEditText1.setHint("Category name");
                taskEditText2.setHint("Category Color");
                layout.addView(taskEditText2);
                AlertDialog dialog1 = new AlertDialog.Builder(this)
                        .setTitle("Add a new Category")
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String categoryName = String.valueOf(taskEditText1.getText());
                                String categoryColor = String.valueOf(taskEditText2.getText());
                                Category cat = new Category(categoryName, categoryColor);
                                getDataTasks().createCategory(cat);
                                NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
                                Menu menu = navView.getMenu();
                                menu.add(R.id.navmenu, cat.getNum(), Menu.NONE, cat.getTitle());
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
