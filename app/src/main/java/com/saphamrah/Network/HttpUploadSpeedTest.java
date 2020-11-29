package com.saphamrah.Network;


import android.content.Context;
import android.util.Log;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import static com.saphamrah.Network.HttpUploadSpeedTest.uploadedKByte;

import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpUploadSpeedTest extends Thread {

    public String fileURL = "";
    static int uploadedKByte = 0;
    private double uploadElapsedTime = 0;
    private boolean finished = false;
    private double elapsedTime = 0;
    private double finalUploadRate = 0.0;
    private long startTime;
    private Context context;

    public HttpUploadSpeedTest(Context context , String fileURL)
    {
        this.fileURL = fileURL;
        this.context = context;
    }

    private double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd;
        try
        {
            bd = new BigDecimal(value);
        }
        catch (Exception ex)
        {
            return 0.0;
        }
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean isFinished()
    {
        return finished;
    }

    public double getInstantUploadRate()
    {
        try
        {
            BigDecimal bd = new BigDecimal(uploadedKByte);
        }
        catch (Exception ex)
        {
            return 0.0;
        }

        if (uploadedKByte >= 0)
        {
            long now = System.currentTimeMillis();
            elapsedTime = (now - startTime) / 1000.0;
            return round((Double) (((uploadedKByte / 1000.0) * 8) / elapsedTime), 2);
        }
        else
        {
            return 0.0;
        }
    }

    public double getFinalUploadRate()
    {
        return round(finalUploadRate, 2);
    }

    @Override
    public void run()
    {
        try
        {
            URL url = new URL(fileURL);
            uploadedKByte = 0;
            startTime = System.currentTimeMillis();

            ExecutorService executor = Executors.newFixedThreadPool(4);
            for (int i = 0; i < 4; i++)
            {
                executor.execute(new HandlerUpload(context, url));
            }
            executor.shutdown();
            while (!executor.isTerminated())
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException ex)
                {
                }
            }

            long now = System.currentTimeMillis();
            uploadElapsedTime = (now - startTime) / 1000.0;
            finalUploadRate = (Double) (((uploadedKByte / 1000.0) * 8) / uploadElapsedTime);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), HttpUploadSpeedTest.class.getSimpleName(), "", "run" , "");
        }

        finished = true;
    }
}

class HandlerUpload extends Thread
{

    private URL url;
    private Context context;

    public HandlerUpload(Context context , URL url)
    {
        this.url = url;
        this.context = context;
    }

    public void run()
    {
        byte[] buffer = new byte[150 * 1024];
        long startTime = System.currentTimeMillis();
        int timeout = 10;

        while (true)
        {
            try
            {
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                dos.write(buffer, 0, buffer.length);
                dos.flush();

                conn.getResponseCode();

                uploadedKByte += buffer.length / 1024.0;
                long endTime = System.currentTimeMillis();
                double uploadElapsedTime = (endTime - startTime) / 1000.0;
                if (uploadElapsedTime >= timeout)
                {
                    break;
                }

                dos.close();
                conn.disconnect();
            }
            catch (Exception exception)
            {
                Log.d("error" , "host name error");
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), HandlerUpload.class.getSimpleName(), "", "run" , "");
                break;
            }
        }
    }
}