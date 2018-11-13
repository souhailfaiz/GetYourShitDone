package com.academy.youngcapital.getyourshitdone.controller;

import android.content.DialogInterface;
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

    // data/tasks.class
    private Tasks dataTasks;
    private ListView listView;
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

        listView = (ListView) findViewById(R.id.list_tasks);
        fab = findViewById(R.id.fab);

        dataTasks = new Tasks(getApplicationContext());

        listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Clicked task id = " + view.getTag(), Toast.LENGTH_SHORT).show();
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
                switch (item.getItemId()) {
                    case R.id.addcategory:
                        addCategoryView();
                    case R.id.allcategories:
                        listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());
                        listView.setAdapter(listAdapter);
                }
                for (Category category : dataTasks.getAllCategories()) {
                    if (item.getItemId() == category.getId()) {
                        listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getTasksByCategory(item.getItemId()));
                        listView.setAdapter(listAdapter);
                    }
                }
                return true;
            }
        });
        for (Category num : dataTasks.getAllCategories()) {
            menu.add(0, num.getId(), 0, num.getTitle());
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
        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);
        layout.addView(spinner);

        //prio with category
        Switch simpleSwitch = new Switch(this);
        simpleSwitch.setChecked(false);
        layout.addView(simpleSwitch);
        simpleSwitch.setText("Priority");


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new Task")
                .setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        String category = String.valueOf(priospinner.getText());
                        String taskTitle = String.valueOf(taskEditText11.getText());
                        String taskDescription = String.valueOf(taskEditText21.getText());

//                        Task task = new Task(1, categoryName, categoryColor);
//                        Log.d(TAG, "Category to add: "+category);
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

    public void addCategoryView() {
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
                        Random rand = new Random();
                        int randomid = rand.nextInt(10000) + 1;
                        while (dataTasks.categoryIdExists(randomid)) {
                            randomid = rand.nextInt(10000) + 1;
                        }
                        String categoryName = String.valueOf(taskEditText1.getText());
                        String categoryColor = String.valueOf(taskEditText2.getText());

                        Category cat = new Category(randomid, categoryName, categoryColor);
                        getDataTasks().createCategory(cat);
                        NavigationView navView = (NavigationView) findViewById(R.id.navigationId);
                        Menu menu = navView.getMenu();
                        menu.add(R.id.navmenu, cat.getId(), Menu.NONE, cat.getTitle());
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
        switch (item.getItemId()) {
            case 4:

                return true;
            case 123:
                Log.d(TAG, "Category to addfSDfdsfa: ");
                return true;
            case R.id.addcategory:
                addCategoryView();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
