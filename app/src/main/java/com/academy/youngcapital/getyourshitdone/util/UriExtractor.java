package com.academy.youngcapital.getyourshitdone.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.OpenableColumns;

public class UriExtractor {

    public UriExtractor() {
    }

    public String extractName(Uri uri, ContentResolver resolver) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);

        int fileNameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

        returnCursor.moveToFirst();

        String fileName = returnCursor.getString(fileNameIndex);

        returnCursor.close();

        return fileName;
    }

    public String extractPath(Uri uri){
        return uri.getPath();
    }


}
