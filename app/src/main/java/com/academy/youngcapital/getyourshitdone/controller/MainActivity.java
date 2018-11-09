package com.academy.youngcapital.getyourshitdone.controller;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.model.Category;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
