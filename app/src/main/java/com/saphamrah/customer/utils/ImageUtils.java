package com.saphamrah.customer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class ImageUtils {

    public static byte[] convertBitmapToByteArray(Bitmap bitmap){

        int size = bitmap.getRowBytes() * bitmap.getHeight();

        ByteBuffer b = ByteBuffer.allocate(size);

        bitmap.copyPixelsToBuffer(b);

        byte[] bytes = new byte[size];

        try {
            b.get(bytes, 0, bytes.length);
        } catch (BufferUnderflowException e) {

        }
        return bytes;
    }

    public static Uri convertBitmapToUri(Context context,String fileName,Bitmap bm){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bm, fileName, null);
        return Uri.parse(path);
    }
}
