package com.saphamrah.Network;

import android.os.AsyncTask;

import com.saphamrah.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AsyncDownloadZipFile extends AsyncTask<String, Integer, Integer>
{

    private AsyncDownloadFileResponse response;
    private String downloadURL;
    private String path;
    private String zipFileName;
    private boolean extractZipFile;



    public AsyncDownloadZipFile(boolean extractZipFile, AsyncDownloadFileResponse response)
    {
        this.response = response;
        this.extractZipFile = extractZipFile;
    }


    /**
     *
     * @param params params[0] = downloadURL , params[1] = path , params[2] = zipFileName
     * @return -1 = download failed , -2 = extract failed
     */
    @Override
    protected Integer doInBackground(String... params)
    {
        downloadURL = params[0];
        path = params[1];
        zipFileName = params[2];

        if (downloadFile())
        {
            if (extractZipFile)
            {
                if (extractZip())
                {
                    return 1;
                }
                else
                {
                    return -2;
                }
            }
            else
            {
                return 1;
            }
        }
        else
        {
            return -1;
        }
    }


    private boolean downloadFile()
    {
        int count;
        try
        {
            URL url = new URL(downloadURL);
            URLConnection connection = url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            //Create androiddeft folder if it does not exist
            File directory = new File(path);
            if (!directory.exists())
            {
                if (!directory.mkdirs())
                {
                    return false;
                }
            }

            // Output stream to write file
            OutputStream output = new FileOutputStream(path + zipFileName);
            byte[] data = new byte[1024];
            long downloaded = 0;

            while ((count = input.read(data)) != -1)
            {
                downloaded += count;
                publishProgress((int) ((downloaded * 100) / fileLength));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private boolean extractZip()
    {
        InputStream is;
        ZipInputStream zis;
        try
        {
            String filename;
            is = new FileInputStream(path + zipFileName);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                filename = ze.getName();

                if (ze.isDirectory())
                {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(path + filename);
                while ((count = zis.read(buffer)) != -1)
                {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }
            zis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
        response.updateProgressBar(values[0]);
    }

    @Override
    protected void onPostExecute(Integer integer)
    {
        super.onPostExecute(integer);
        if (integer == 1)
        {
            response.downloadCompleted();
        }
        else if (integer == -1)
        {
            response.downloadFailed(R.string.downloadFailed);
        }
        else if (integer == -2)
        {
            response.downloadFailed(R.string.extractFailed);
        }
    }

}
