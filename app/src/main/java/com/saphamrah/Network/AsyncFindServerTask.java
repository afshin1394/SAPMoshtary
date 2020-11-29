package com.saphamrah.Network;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class AsyncFindServerTask extends AsyncTask<ArrayList<ServerIpModel> , Void , ServerIpModel>
{

    public AsyncTaskResponse delegate = null;
    private WeakReference<Activity> weakReferenceActivity;

    public AsyncFindServerTask(Activity activity)
    {
        weakReferenceActivity = new WeakReference<>(activity);
    }

    @Override
    protected ServerIpModel doInBackground(ArrayList<ServerIpModel>... arrayLists)
    {
        ServerIpModel currentServer = new ServerIpModel();
        int timeout = 1000; //unit = ms

        try
        {
            ArrayList<ServerIpModel> arrayList = arrayLists[0];

            Log.d("async" , "serverIp count in async : " + arrayList.size());

            for (int i = 0 ; i < arrayList.size() ; i++)
            {
                Log.d("async" , "i = " + i + " , ip = " + arrayList.get(i).getServerIp() + " , port = " + arrayList.get(i).getPort());
                ServerIpModel serverIpModel = new ServerIpModel();
                serverIpModel = arrayList.get(i);
                try
                {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(serverIpModel.getServerIp() , Integer.parseInt(serverIpModel.getPort())) , timeout);
                    if(socket.isConnected())
                    {
                        currentServer = serverIpModel;
                        break;
                    }
                }
                catch (Exception e)
                {
                    Log.d("async" , e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }

            /*if (currentServer != null)
            {
                Log.d("async" , "address : " + currentServer.getServerIp());
                Log.d("async" , "port : " + currentServer.getPort());
            }
            else
            {
                Log.d("async" , "address : null");
                Log.d("async" , "port : null");
            }*/


            return currentServer;
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(weakReferenceActivity.get(), Constants.LOG_EXCEPTION(), e.toString(), "AsyncFindServerTask" , weakReferenceActivity.get().getClass().getSimpleName(), "doInBackground" , "");
            e.printStackTrace();
            return new ServerIpModel();
        }
    }


    @Override
    protected void onPostExecute(ServerIpModel currentServerIpModel)
    {
        if (currentServerIpModel == null)
        {
            Log.d("async" , "result from onpostExcute : no route find to server ips");
        }
        else
        {
            Log.d("async" , "result from onpostExcute : " + currentServerIpModel.getServerIp() + ":" + currentServerIpModel.getPort());
        }
        delegate.processFinished(currentServerIpModel);
    }


}
