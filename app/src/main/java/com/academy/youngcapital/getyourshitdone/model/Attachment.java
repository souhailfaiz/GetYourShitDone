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

    // Constructer voor een attachment
    public Attachment(Uri uri, Bitmap bitmap, ContentResolver resolver) {
        /**uri is an abstract class, so we can not save the uri in this model because this model
        will be saved and abstract classes can not be saved into sharedpreferences.**/
        super();
        imageCoder = new ImageCoder();

        // Img naar uri
        path = extractPath(uri);
        name = extractName(uri, resolver);

        // image decoden om het te kunnen opslaan
        image = imageCoder.encodeTobase64(bitmap);
    }

    // Constructor voor camera input die heeft alleen een bitmap nodig en geen URI extractor
    public Attachment(Bitmap bitmap) {
        super();
        imageCoder = new ImageCoder();

        // Image decoden om te kunnen opslaan
        image = imageCoder.encodeTobase64(bitmap);
    }

    // Return image
    public String getImage() {
        return image;
    }

    // Set image
    public void setImage(String image) {
        this.image = image;
    }

    // Return path
    public String getPath() {
        return path;
    }

    // Set path
    public void setPath(String path) {
        this.path = path;
    }

    // Return name
    public String getName() {
        return name;
    }

    // Set name
    public void setName(String name) {
        this.name = name;
    }
}