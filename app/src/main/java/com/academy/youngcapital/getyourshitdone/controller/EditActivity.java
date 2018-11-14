package com.academy.youngcapital.getyourshitdone.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.academy.youngcapital.getyourshitdone.util.ListAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 43;
    private ActionBar actionBar;
    private Tasks dataTasks;
    private int task_id;
    private Task currentTask;
    private EditText editTitle;
    private EditText editNotes;
    private Switch switchPriority;
    private Spinner spinnerCategory;
    private CheckBox checkFinished;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private ArrayList<String> spinnerArray;
    private Button deleteButton;
    private Button opslaanButton;
    private Button uploadBtn;
    private Button showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_todo);

        getDataFromPreviousScreen();

        initView();

        //set backbutton on actionbar
        actionBar.setDisplayHomeAsUpEnabled(true);

        setView();


        //upload image
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });

        //show image
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ImageActivity.class);
                i.putExtra("task_id", currentTask.getId());
                startActivity(i);
            }
        });

        // delete knop
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataTasks.deleteTask(currentTask.getId());

                ListAdapter listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());
                MainActivity.listView.setAdapter(listAdapter);
                dataTasks.saveTasks();
                MainActivity.dataTasks = dataTasks;
                onBackPressed();

            }
        });

        //opslaan
        opslaanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTask.setTitle(editTitle.getText().toString());
                currentTask.setDescription(editNotes.getText().toString());
                currentTask.setPriority(switchPriority.isChecked());


                String catName;
                if(spinnerCategory.getSelectedItem() == null)
                {
                    catName = null;
                }
                else
                {
                    catName = spinnerCategory.getSelectedItem().toString();
                }

                if(catName != null) {
                    Category selectedCat = dataTasks.getCategoryByName(catName);
                    currentTask.setCategory(selectedCat);
                }
                else
                {
                    currentTask.setCategory(null);
                }

                currentTask.setIsCompleted(checkFinished.isChecked());

                ListAdapter listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());
                MainActivity.listView.setAdapter(listAdapter);
                MainActivity.dataTasks = dataTasks;

                dataTasks.saveTasks();

                onBackPressed();

                Log.d("Editor:", "Opgeslagen");
            }
        });
    }

    //set the view values
    private void setView() {
        editTitle.setText(currentTask.getTitle());
        editNotes.setText(currentTask.getDescription());
        switchPriority.setChecked(currentTask.getPriority());
        checkFinished.setChecked(currentTask.isCompleted());
        spinnerCategory.setAdapter(spinnerArrayAdapter);

    }

    //initialize all view variables needed
    private void initView() {
        actionBar = getSupportActionBar();

        editTitle = (EditText)findViewById(R.id.editTitle);
        editNotes = (EditText)findViewById(R.id.editNotes);
        switchPriority = (Switch)findViewById(R.id.editPriority);
        spinnerCategory = (Spinner)findViewById(R.id.editCat);
        checkFinished = (CheckBox)findViewById(R.id.editIsCompleted);
        deleteButton = (Button)findViewById(R.id.btnDelete);
        opslaanButton = (Button)findViewById(R.id.btnOpslaan);
        uploadBtn = (Button)findViewById(R.id.btnUploadImg);
        showBtn = (Button)findViewById(R.id.btnShowImg);
        currentTask = dataTasks.getTaskById(task_id);
        spinnerArray = new ArrayList<String>();

        //Add all categories to spinnerArray
        for (Category category : dataTasks.getAllCategories()) {
            spinnerArray.add(category.getTitle());
        }

        //set spinner adapter
        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
    }

    //get data from previous screen
    private void getDataFromPreviousScreen() {
        Intent intent = getIntent();

        dataTasks = new Tasks(getApplicationContext());
        task_id = intent.getIntExtra("task_id", -1);
    }


    //menu zichtbaar maken (daarin zit een share button).
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Listeners for menu buttons.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();

            case R.id.menu_share_todo:
                Intent shareIntent = new Intent();

                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getMessage());
                shareIntent.setType("text/plain");

                startActivity(shareIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private String getMessage() {
        StringBuilder message = new StringBuilder();

        message.append("Title: ");
        message.append(editTitle.getText());
        message.append("\n\n");
        message.append("Notitie: ");
        message.append(editNotes.getText());

        return message.toString();
    }

    private void startSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");//Files having mime datatype
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    //Bitmap aanmaken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
                if(data != null){
                    Uri uri = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentTask.setUriPicture(bitmap);
                    dataTasks.saveTasks();
                }
        }
    }




}