package com.academy.youngcapital.getyourshitdone.controller;

import android.annotation.SuppressLint;
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
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.academy.youngcapital.getyourshitdone.util.ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class EditActivity extends AppCompatActivity {
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

    @SuppressLint("A.K.A. JUNAID A NIFFFFFOOOO")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_todo);

        getDataFromPreviousScreen();

        initView();

        //set backbutton on actionbar
        actionBar.setDisplayHomeAsUpEnabled(true);

        setView();

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

        opslaanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                currentTask.setTitle(editTitle.getText().toString());
                currentTask.setDescription(editNotes.getText().toString());
                currentTask.setPriority(switchPriority.isChecked());


                String catName = spinnerCategory.getSelectedItem().toString();
                Category selectedCat = dataTasks.getCategoryByName(catName);
                currentTask.setCategory(selectedCat);
                Log.d("Category Editor", "Selected: " + catName + "  Overgezet:" + selectedCat.getTitle() + " NEWval:" + currentTask.getCategory().getTitle());

                currentTask.setIsCompleted(checkFinished.isChecked());

                ListAdapter listAdapter = new ListAdapter(getApplicationContext(), dataTasks.getAllTasks());
                MainActivity.listView.setAdapter(listAdapter);

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
    private void initView(){
        actionBar = getSupportActionBar();
        editTitle = (EditText)findViewById(R.id.editTitle);
        editNotes = (EditText)findViewById(R.id.editNotes);
        switchPriority = (Switch)findViewById(R.id.editPriority);
        spinnerCategory = (Spinner)findViewById(R.id.editCat);
        checkFinished = (CheckBox)findViewById(R.id.editIsCompleted);
        deleteButton = (Button)findViewById(R.id.btnDelete);
        opslaanButton = (Button)findViewById(R.id.btnOpslaan);
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
    private void getDataFromPreviousScreen(){
        Intent intent = getIntent();

        dataTasks = new Tasks(getApplicationContext());
        task_id = intent.getIntExtra("task_id",-1);
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
        switch (item.getItemId()){
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

    private String getMessage(){
        StringBuilder message = new StringBuilder();

        message.append("Title: ");
        message.append(editTitle.getText());
        message.append("\n\n");
        message.append("Notitie: ");
        message.append(editNotes.getText());

        return message.toString();
    }


}