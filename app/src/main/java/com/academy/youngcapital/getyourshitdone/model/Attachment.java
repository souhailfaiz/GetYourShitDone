package com.academy.youngcapital.getyourshitdone.model;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;

import com.academy.youngcapital.getyourshitdone.util.ImageCoder;
import com.academy.youngcapital.getyourshitdone.util.UriExtractor;

public class Attachment extends UriExtractor{

    private String image;
    private String path;
    private String name;
    private ImageCoder imageCoder;


    public Attachment(Uri uri, Bitmap bitmap, ContentResolver resolver) {
        /**uri is an abstract class, so we can not save the uri in this model because this model
        will be saved and abstract classes can not be saved into sharedpreferences.**/
        super();
        imageCoder = new ImageCoder();

        path = extractPath(uri);
        name = extractName(uri, resolver);
        image = imageCoder.encodeTobase64(bitmap);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}