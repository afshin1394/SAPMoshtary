package com.saphamrah.PubFunc;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.MVP.View.TemporaryRequestsListActivity;
import com.saphamrah.Utils.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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


    public static File saveImageInFile(byte[] image,String uniqueID , String fileNameToSave) {
        ImageUtils imageUtils = new ImageUtils();
        String fileName = "Print-" + uniqueID + ".jpg";
        File file = null;

        try{
            file = new File(fileNameToSave);
            file.createNewFile();

            //write the bytes in file
            FileOutputStream fos =new  FileOutputStream(file);
            fos.write(image);
            fos.flush();
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return file;
    }

    public  byte[] getImageBaseOnCamera(Context context,Bitmap data){
        int file_size = data.getByteCount()/1024;
        Log.i("length", "getImageBaseOnCamera: "+file_size);
        if (file_size > 20000) {
            return convertBitmapToByteArray(context,data,20, Bitmap.CompressFormat.JPEG);

        }else if (file_size < 20000   && file_size > 10000){
            return convertBitmapToByteArray(context,data,50, Bitmap.CompressFormat.JPEG);

        }else if ( file_size <= 10000 && file_size >= 5000){
            return convertBitmapToByteArray(context,data,70, Bitmap.CompressFormat.JPEG);

        }else{
            return convertBitmapToByteArray(context,data,100, Bitmap.CompressFormat.JPEG);
        }

    }



}
