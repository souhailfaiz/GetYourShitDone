package com.academy.youngcapital.getyourshitdone.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Task;

import java.util.HashMap;

public class EditActivity extends Activity {

    private Tasks dataTasks;

    private EditText editTitle;
    private EditText editNotes;
    private Switch switchPriority;
    private Spinner spinnerCategory;
    private CheckBox checkFinished;



    @SuppressLint("NewNiffo")
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

        Task currentTask = dataTasks.getTaskById(task_id);

        editTitle.setText(currentTask.getTitle());
        editNotes.setText(currentTask.getDescription());

        switchPriority.setChecked(currentTask.getPriority());
        checkFinished.setChecked(currentTask.isCompleted());


    }
}