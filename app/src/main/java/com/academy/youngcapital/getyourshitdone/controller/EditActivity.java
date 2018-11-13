package com.academy.youngcapital.getyourshitdone.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.academy.youngcapital.getyourshitdone.util.ListAdapter;

import java.util.HashMap;

public class EditActivity extends Activity {

    private Tasks dataTasks;

    private EditText editTitle;
    private EditText editNotes;
    private Switch switchPriority;
    private Spinner spinnerCategory;
    private CheckBox checkFinished;

    private Button deleteButton;

    @SuppressLint("aasdas")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_todo);
        Intent intent = getIntent();

        dataTasks = new Tasks(getApplicationContext());
        int task_id = intent.getIntExtra("task_id",-1);

        editTitle = (EditText)findViewById(R.id.editTitle);
        editNotes = (EditText)findViewById(R.id.editNotes);
        switchPriority = (Switch)findViewById(R.id.editPriority);
        spinnerCategory = (Spinner)findViewById(R.id.editCat);
        checkFinished = (CheckBox)findViewById(R.id.editIsCompleted);
        deleteButton = (Button)findViewById(R.id.btnDelete);


        final Task currentTask = dataTasks.getTaskById(task_id);

        editTitle.setText(currentTask.getTitle());
        editNotes.setText(currentTask.getDescription());

        switchPriority.setChecked(currentTask.getPriority());
        checkFinished.setChecked(currentTask.isCompleted());



        // delete knop

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataTasks.deleteTask(currentTask.getId());

                ListAdapter listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());
                MainActivity.listView.setAdapter(listAdapter);

                onBackPressed();

            }
        });


    }
}