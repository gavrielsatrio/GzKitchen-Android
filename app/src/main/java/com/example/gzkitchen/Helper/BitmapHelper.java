package com.example.gzkitchen.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapHelper {
    public String convertToBase64String(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 30, outputStream);
        byte[] byteArray = outputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    public Bitmap convertToBitmap(String base64String) {
        byte[] byteArray = Base64.decode(base64String, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
