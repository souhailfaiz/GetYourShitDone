package com.academy.youngcapital.getyourshitdone.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Task;

import java.io.File;

public class ImageActivity extends AppCompatActivity {

    private Tasks dataTasks;
    private int task_id;
    private Task currentTask;
    private ImageView picture;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        picture = (ImageView)findViewById(R.id.picture);
        getDataFromPreviousScreen();

        currentTask = dataTasks.getTaskById(task_id);

        Toast.makeText(getApplicationContext(), "Picture: " + currentTask.getUriPicture(), Toast.LENGTH_SHORT).show();

        picture.setImageBitmap(currentTask.getUriPicture());
    }


    //get data from previous screen
    private void getDataFromPreviousScreen(){
        Intent intent = getIntent();

        dataTasks = new Tasks(getApplicationContext());
        task_id = intent.getIntExtra("task_id",-1);
    }
}
