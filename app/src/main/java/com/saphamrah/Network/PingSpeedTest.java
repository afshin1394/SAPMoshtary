package com.saphamrah.Network;

import android.content.Context;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PingSpeedTest extends Thread
{

    private HashMap<String, Object> result = new HashMap<>();
    private String server = "";
    private int count;
    private double instantRtt = 0;
    private double avgRtt = 0.0;
    private boolean finished = false;
    private boolean started = false;
    private Context context;

    public PingSpeedTest(Context context, String serverIpAddress, int pingTryCount)
    {
        this.server = serverIpAddress;
        this.count = pingTryCount;
        this.context = context;
    }

    public double getAvgRtt()
    {
        return avgRtt;
    }

    public double getInstantRtt()
    {
        return instantRtt;
    }

    public boolean isFinished()
    {
        return finished;
    }

    @Override
    public void run()
    {
        try
        {
            ProcessBuilder ps = new ProcessBuilder("ping", "-c " + count, this.server);

            ps.redirectErrorStream(true);
            Process pr = ps.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            String output = "";
            while ((line = in.readLine()) != null) {
                if (line.contains("icmp_seq")) {
                    instantRtt = Double.parseDouble(line.split(" ")[line.split(" ").length - 2].replace("time=", ""));
                }
                if (line.startsWith("rtt ")) {
                    avgRtt = Double.parseDouble(line.split("/")[4]);
                    break;
                }
            }
            pr.waitFor();
            in.close();

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(),exception.toString(), PingSpeedTest.class.getSimpleName(), "", "run", "");
        }
        finished = true;
    }


}
