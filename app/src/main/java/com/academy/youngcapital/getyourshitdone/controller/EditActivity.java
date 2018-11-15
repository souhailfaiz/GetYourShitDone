package com.academy.youngcapital.getyourshitdone.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Attachment;
import com.academy.youngcapital.getyourshitdone.model.Category;
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.academy.youngcapital.getyourshitdone.util.ListAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class EditActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 43;
    private static final int CAM_REQUEST = 1313;
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
    ArrayList<String> spinnerArray;
    private ImageView imgTakenPic;
    private Button deleteButton;
    private Button opslaanButton;
    private Button uploadBtn;
    private Button showBtn;
    private Button cameraBtn;

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

        //open camera button
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAM_REQUEST);
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
                if(spinnerCategory.getSelectedItem() == null) {
                    catName = null;
                }
                else {
                    catName = spinnerCategory.getSelectedItem().toString();
                }

                if(catName != null) {
                    Category selectedCat = dataTasks.getCategoryByName(catName);
                    currentTask.setCategory(selectedCat);
                }
                else {
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

        if(currentTask.getAttachment() == null){
            showBtn.setEnabled(false);
        }

    }

    //initialize all view variables needed
    private void initView() {
        actionBar = getSupportActionBar();

        editTitle = findViewById(R.id.editTitle);
        editNotes = findViewById(R.id.editNotes);
        switchPriority = findViewById(R.id.editPriority);
        spinnerCategory = findViewById(R.id.editCat);
        checkFinished = findViewById(R.id.editIsCompleted);
        deleteButton = findViewById(R.id.btnDelete);
        opslaanButton = findViewById(R.id.btnOpslaan);
        uploadBtn = findViewById(R.id.btnUploadImg);
        showBtn = findViewById(R.id.btnShowImg);
        cameraBtn = findViewById(R.id.cameraBtn);
        currentTask = dataTasks.getTaskById(task_id);
        spinnerArray = new ArrayList<>();
        imgTakenPic = (ImageView)findViewById(R.id.picture);

        //Add all categories to spinnerArray
        for (Category category : dataTasks.getAllCategories()) {
            spinnerArray.add(category.getTitle());
        }

        //set spinner adapter
        spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
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
                break;
            case R.id.menu_share_todo:
                Intent shareIntent = new Intent();

                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getMessage());
                shareIntent.setType("text/plain");

                startActivity(shareIntent);
                break;
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

                currentTask.setAttachment(new Attachment(uri, bitmap, getContentResolver()));
                showBtn.setEnabled(true);
                Toast.makeText(getApplicationContext(), currentTask.getAttachment().getName(), Toast.LENGTH_SHORT).show();

                dataTasks.saveTasks();
            }
        }
        if(requestCode == CAM_REQUEST){

            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                // Here we make a file on the given path
                FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/_camera.png"));

                // Bitmap compresses to a png and writes it to the file
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                // We get the stored image in the file.
                File file = new File(Environment.getExternalStorageDirectory() + "/_camera.png");
                //We get the Uri from the file
                Uri uri = Uri.fromFile(file);

                // we make a new bitmap from the uri
                Bitmap bm = null;
                try {
                    bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //we make a new attachment
                currentTask.setAttachment(new Attachment(uri, bm));

                showBtn.setEnabled(true);

                //we save the image to sharedpreferences.
                dataTasks.saveTasks();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }





}