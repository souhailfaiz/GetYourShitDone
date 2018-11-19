package com.academy.youngcapital.getyourshitdone.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Task;
import com.academy.youngcapital.getyourshitdone.util.ImageCoder;


@SuppressWarnings("FieldCanBeLocal")
public class ImageActivity extends AppCompatActivity {

    private Tasks dataTasks;
    private int task_id;
    private Task currentTask;
    private ImageView picture;
    private ImageCoder imageCoder;

    private Button btnDelete;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        getDataFromPreviousScreen();

        initView();


    }

    private void initView() {

        picture = (ImageView)findViewById(R.id.picture);
        btnDelete = findViewById(R.id.btnDelete);

        if(currentTask.getAttachment() != null){
            picture.setImageBitmap(imageCoder.decodeBase64(currentTask.getAttachment().getImage()));
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTask.setAttachment(null);
                dataTasks.saveTasks();

                setResult(1337,
                        new Intent().putExtra("deleted", 1));
                finish();
            }
        });
    }


    //get data from previous screen
    private void getDataFromPreviousScreen(){
        Intent intent = getIntent();

        dataTasks = new Tasks(getApplicationContext());
        task_id = intent.getIntExtra("task_id",-1);
        currentTask = dataTasks.getTaskById(task_id);
        imageCoder = new ImageCoder();
    }


}
