package com.saphamrah.PubFunc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.saphamrah.Utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageUtils
{

    public byte[] convertBitmapToByteArray(Context context, Bitmap bitmap, int quality, Bitmap.CompressFormat jpeg)
    {
        try
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG , quality , stream);
            return stream.toByteArray();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "imageUtils", "", "convertBitmapToByteArray", "");
            return new byte[]{};
        }
    }

    public Bitmap convertByteArrayToBitmap(Context context , byte[] bytes)
    {
        try
        {
            return BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "imageUtils", "", "convertBitmapToByteArray", "");
            return null;
        }
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize)
    {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public File bitmapToFile(byte[]  bytes, String fileNameToSave, String dir) {
        File file = null;

         try{
             file = new File(fileNameToSave);
             file.createNewFile();

             //write the bytes in file
             FileOutputStream fos =new  FileOutputStream(file);
             fos.write(bytes);
             fos.flush();
             fos.close();
         } catch (Exception e){
             e.printStackTrace();
         }

         return file;
    }




}
