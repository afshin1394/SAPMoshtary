package com.saphamrah.Network;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class AsyncDownloadFile extends AsyncTask<String , Integer , Integer>
{

    private String fileName;
    private String folder;
    private Activity activity;
    private AlertDialog show;
    private View view;
    private ProgressBar progressBar;
    private TextView lblPercentage;
    private TextView lblProgressValue;
    private int fileLength;
    //private float fileLengthMB;
    private long downloaded;
    public AsyncTaskResponse delegate = null;


    public AsyncDownloadFile(Activity activity)
    {
        this.fileName = "";
        this.folder = "";
        this.activity = activity;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        CustomAlertDialog customAlertDialog = new CustomAlertDialog(activity);
        view = customAlertDialog.createProgressBar(activity);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setView(view);
        builder.create();
        if (!(activity).isFinishing())
        {
            show = builder.show();
            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    progressBar = view.findViewById(R.id.progress);
                    lblPercentage = view.findViewById(R.id.lblProgressPercentage);
                    lblProgressValue = view.findViewById(R.id.lblProgressValue);
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(activity,Constants.LOG_EXCEPTION(), exception.toString(), "AsyncDownloadFile", activity.getClass().getSimpleName(), "onPreExecute", "");
            }
        }
    }



    @Override
    protected Integer doInBackground(String... params)
    {
        if (params != null)
        {
            String downloadUrl = params[0];
            int count;
            try
            {
                URL url = new URL(downloadUrl);
                URLConnection connection = url.openConnection();
                connection.connect();
                fileLength = connection.getContentLength();
                //fileLengthMB = (float) fileLength / 1048576;
                Log.d("download" , "length of file : " + fileLength);


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                //Extract file name from URL
                fileName = downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1).replace(".zip" , "");

                //External directory path to save file
                folder = Environment.getExternalStorageDirectory() + "/SapHamrah/Download/";

                //Create androiddeft folder if it does not exist
                File directory = new File(folder);
                if (!directory.exists())
                {
                    directory.mkdirs();
                }

                Log.d("download" , "folder for download : " + new File(folder , fileName));

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte[] data = new byte[1024];
                downloaded = 0;

                while ((count = input.read(data)) != -1)
                {
                    downloaded += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress((int) ((downloaded * 100) / fileLength));
                    //Log.d("download", "Progress: " + (int) ((downloaded * 100) / fileLength));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return 1;

            }
            catch (Exception e)
            {
                Log.e("Error: ", e.getMessage());
            }

            return -1;

        }
        return -2;
    }



    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
        lblPercentage.setText(values[0] + "%");
        //float totalByte = (float) fileLength / 1048576;
        /*float downloadedByte = (float) downloaded / 1048576;
        lblProgressValue.setText(String.format("%1$s / %2$s" , downloadedByte , fileLengthMB));*/
    }


    @Override
    protected void onPostExecute(Integer statusCode)
    {
        super.onPostExecute(statusCode);
        show.dismiss();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(statusCode.toString());
        arrayList.add(folder);
        arrayList.add(fileName);
        delegate.processFinished(arrayList);
    }



}
