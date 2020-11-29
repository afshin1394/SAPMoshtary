package com.saphamrah.PubFunc;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils
{

    public static void saveToFile(Context context, String jsonAllData , String filaName)
    {
        try
        {
            File path = context.getExternalFilesDir(null);
            File file = new File(path, filaName + ".txt");
            FileOutputStream stream = new FileOutputStream(file);
            try
            {
                stream.write(jsonAllData.getBytes());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
