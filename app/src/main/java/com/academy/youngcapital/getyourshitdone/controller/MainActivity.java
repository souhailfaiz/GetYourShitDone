package com.academy.youngcapital.getyourshitdone.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.academy.youngcapital.getyourshitdone.util.ListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    private static final
    String TAG = "MainActivity";
    public static final String MESSAGE = "com.example.SIMPLE_MESSAGE";

    // data/tasks.class
    private Tasks dataTasks;
    public static ListView listView;
    private  ListAdapter listAdapter;
    private FloatingActionButton fab;

    public Tasks getDataTasks() {
        return dataTasks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dataTasks = new Tasks(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_tasks);
        fab = findViewById(R.id.fab);

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

        drawerLayout = findViewById(R.id.drawerId);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
        Menu menu = navView.getMenu();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
                switch (item.getItemId()) {
                    case R.id.addcategory:
                        showaddCategoryDialog();
                        drawerLayout.closeDrawer(navView);
                        break;
                    case R.id.allcategories:
                        listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());
                        listView.setAdapter(listAdapter);
                        drawerLayout.closeDrawer(navView);
                        break;
                    case R.id.removeallcategories:

//                        dataTasks.removeAllCategories();
//                        for(Category category: getDataTasks().getAllCategories()){
//                            dataTasks.removeCategoryById(category.getId());
////                            navView.getMenu().removeItem(category.getId());
//                            Log.d(TAG, "Category to addfSDfdsfa: "+category.getId());
//                        }
//                        break;
//                        drawerLayout.closeDrawer(navView);
                }
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
        for (Category category : dataTasks.getAllCategories()) {
            menu.add(0, category.getId(), 0, category.getTitle());
        }

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

                        dataTasks.createTask(taskTitle, taskDescription, priority, dataTasks.getCategoryByName(category), null);
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

    public void showaddCategoryDialog() {
        LinearLayout layout = new LinearLayout(this);
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
                        Category cat = new Category(dataTasks.getNewIDCategory(), categoryName, categoryColor);
                        dataTasks.createCategory(cat);
                        NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
                        Menu menu = navView.getMenu();
                        menu.add(R.id.navmenu, cat.getId(), Menu.NONE, cat.getTitle());
                        Log.d(TAG, "Category to addfSDfdsfa: ");
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog1.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }
}
