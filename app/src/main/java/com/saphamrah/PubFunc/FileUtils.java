package com.saphamrah.PubFunc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Base64;
import android.util.Log;

import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.MVP.View.PrintAndShareActivity;
import com.saphamrah.Utils.Constants;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {

    public static boolean deleteListOfFilesInFolder(final File dir){
        ArrayList<Boolean> deleteAll = new ArrayList<>();
        if (dir!=null) {
            if (dir.exists()) {
                for (final File fileEntry : dir.listFiles()) {
                    if (fileEntry.isDirectory()) {
                        listFilesForFolder(fileEntry);
                    } else {
                        File file = null;
                        try {
                            file = new File(fileEntry.getCanonicalPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (file.exists()) {

                            deleteAll.add(new File( fileEntry.getPath()).delete());
                            Log.i("filePath", "path: "+file.getAbsolutePath() + "file.exists():"+ dir.getAbsolutePath());
                        }
                        else {
                            Log.i("filePath", "file.exists(): " + file.getAbsolutePath());
                        }
                    }
                }
            }
        }
       return !deleteAll.contains(false);
    }



    public static ArrayList<File> listFilesForFolder(final File folder) {
        ArrayList<File> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                files.add(fileEntry);
                System.out.println(fileEntry.getName());
            }
        }
        return files;
    }

    public static void saveToFile(Context context, String jsonAllData, String filaName) {
        try {
            File path = context.getExternalFilesDir(null);
            File file = new File(path, filaName + ".txt");
            FileOutputStream stream = new FileOutputStream(file);
            try {
                stream.write(jsonAllData.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static byte[] convertToByteArray(Context context,File file) {
        try {
            byte[] image = com.google.common.io.Files.toByteArray(file);
            byte[] base64 = Base64.encode(image,Base64.NO_WRAP);
            Log.i("saveToFile", "convertToByteArray: "+base64);
           return Base64.decode(com.google.common.io.Files.toByteArray(file), Base64.NO_WRAP);



        } catch (IOException e) {
            e.printStackTrace();
            return new byte[]{};
        }



//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//        Log.i("saveToFile", "convertToByteArray: "+file.getAbsolutePath());
//        try
//        {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , stream);
//            Log.d("saveToFile", "convertToByteArray: "+stream.toByteArray());
//            return stream.toByteArray();
//        }
//        catch (Exception exception)
//        {
//            exception.printStackTrace();
//            PubFunc.Logger logger = new PubFunc().new Logger();
//            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "FileUtils", "", "convertToByteArray", "");
//            return new byte[]{};
//        }




    }


    public static boolean checkFileExists(File dir, String fileName)
    {
        boolean fileExists = false;
        File file = new File(dir, fileName);
        if (file.exists()) {
            fileExists = true;
        }


        return fileExists;
    }


    /**
     * resize pic before print
     */
    public static void Resize(Context context,File dir, Bitmap tmp,String uniqueID) {
        float Zarib = 0;
        int ResizeWidth = 0, ResizeHeight = 0;
        int quality = 100;


        //-----------------resize-------------------------

        int printerSize = new SystemConfigTabletDAO(context).getAll().get(0).getSizePrint();
        if (printerSize == 384) {
            Zarib = (float) 384 / (tmp.getWidth());
            ResizeWidth = (int) (tmp.getWidth() * Zarib);
            ResizeHeight = (int) (tmp.getHeight() * Zarib);
        } else if (printerSize == 576) {
            Zarib = (float) 576 / (tmp.getWidth());
            ResizeWidth = (int) (tmp.getWidth() * Zarib);
            ResizeHeight = (int) (tmp.getHeight() * Zarib);
        } else if (printerSize == 832) {
            Zarib = (float) 832 / (tmp.getWidth());
            ResizeWidth = (int) (tmp.getWidth() * Zarib);
            ResizeHeight = (int) (tmp.getHeight() * Zarib);
        }

        Bitmap bitmap = Bitmap.createBitmap(ResizeWidth, ResizeHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);


        Bitmap scaled = Bitmap.createScaledBitmap(tmp, ResizeWidth, ResizeHeight, true);
        int leftOffset = 0;//(bitmap.getWidth() - scaled.getWidth()) / 2;
        int topOffset = 0;
        canvas.drawBitmap(scaled, leftOffset, topOffset, null);

        String fileName = "Print-" + uniqueID + ".jpg";


        FileOutputStream outStream;
        File imageFile = new File(dir + "/" + fileName);

        try {
            outStream = new FileOutputStream(imageFile);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
                outStream.flush();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
