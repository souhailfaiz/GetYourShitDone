package com.academy.youngcapital.getyourshitdone.controller;

import android.content.DialogInterface;
import android.os.Bundle;
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
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.util.ListAdapter;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    private static final
    String TAG = "MainActivity";

    // data/tasks.class
    private Tasks dataTasks;
    private ListView listView;
    private ListAdapter listAdapter;

    public Tasks getDataTasks() {
        return dataTasks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataTasks = new Tasks(getApplicationContext());
        dataTasks.createCategory(new Category("School", "blue"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list_tasks);

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
                switch (item.getItemId()){
                    case R.id.addcategory:
                        addCategoryView();
                }
                return true;
            }
        });

        for (Category num : dataTasks.getAllCategories()) {

            menu.add(0, 123, 0, num.getTitle());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        getMenuInflater().inflate(R.menu.nav_menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean addCategoryView() {
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {


            case R.id.action_add_task:
                LinearLayout layout = new LinearLayout(this);
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
            case 123:
                Log.d(TAG, "Category to addfSDfdsfa: ");
                return true;
            case R.id.categoryMenuId:
            case R.id.action_add_category:
                addCategoryView();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
