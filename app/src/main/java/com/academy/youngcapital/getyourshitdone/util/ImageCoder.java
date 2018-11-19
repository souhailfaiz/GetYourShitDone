package com.academy.youngcapital.getyourshitdone.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class ImageCoder {

    public String encodeTobase64(Bitmap bitmap) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        return Base64.encodeToString(output.toByteArray(), Base64.DEFAULT);
    }

    public Bitmap decodeBase64(String image) {
        return BitmapFactory.decodeByteArray(Base64.decode(image, 0), 0, Base64.decode(image, 0).length);
    }
}
