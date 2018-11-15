package com.academy.youngcapital.getyourshitdone.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;
import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Task;

@SuppressWarnings("FieldCanBeLocal")
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
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        picture = findViewById(R.id.picture);
        getDataFromPreviousScreen();

        currentTask = dataTasks.getTaskById(task_id);

        Toast.makeText(getApplicationContext(), "Picture: " + currentTask.getUriPicture(), Toast.LENGTH_SHORT).show();

        if(!currentTask.getUriPicture().isEmpty()){
            picture.setImageBitmap(decodeBase64(currentTask.getUriPicture()));
        }

    }

    //get data from previous screen
    private void getDataFromPreviousScreen(){
        Intent intent = getIntent();

        dataTasks = new Tasks(getApplicationContext());
        task_id = intent.getIntExtra("task_id",-1);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
