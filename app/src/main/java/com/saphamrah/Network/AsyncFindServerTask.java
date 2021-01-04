package com.saphamrah.Network;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.ServerIPDAO;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AsyncFindServerTask extends AsyncTask<ArrayList<ServerIpModel> , Void , ArrayList<ServerIpModel>>
{


    public AsyncTaskFindWebServices delegate =null;
    private WeakReference<Activity> weakReferenceActivity;

    public AsyncFindServerTask(Activity activity)
    {
        weakReferenceActivity = new WeakReference<>(activity);
    }

    @Override
    protected  ArrayList<ServerIpModel>  doInBackground(ArrayList<ServerIpModel>... arrayLists)
    {
//        ServerIpModel currentServer = new ServerIpModel();

        ArrayList<ServerIpModel> currentServers =new ArrayList<>();
        int timeout = 1000; //unit = ms

        try
        {
            ArrayList<ServerIpModel> arrayList = arrayLists[0];


            for (int i = 0 ; i < arrayList.size() ; i++)
            {
                ServerIpModel serverIpModel = arrayList.get(i);
                try
                {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(serverIpModel.getServerIp() , Integer.parseInt(serverIpModel.getPort())) , timeout);
                    if(socket.isConnected())
                    {
                        currentServers.add(serverIpModel);
                    }
                }
                catch (Exception e)
                {
                    //Log.d("server" , String.valueOf(R.string.errorIPConnected));//e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }

/**
 *create a HashSet to find distinct number of different serverIP and store them in a HashSet
 * then after choosing our preferred ip then find servers with that ip(same ip but different ports) and store them in our preferred ip list
 */
            ArrayList<ServerIpModel> preferredServerModels=new ArrayList<>();
            Set<String> distinctIPList=new HashSet<>();
            ServerIPDAO serverIPDAO = new ServerIPDAO(BaseApplication.getContext());
            int countServeType = serverIPDAO.getCountServerType(Constants.CURRENT_VERSION_TYPE());
            for (ServerIpModel serverIpModel:currentServers){
                int countIp=0;
                for( ServerIpModel serverIp : currentServers) {
                    if (serverIpModel.getServerIp().contains(serverIp.getServerIp())){
                        countIp++;
                    }
                }
                if(countServeType==countIp)
                    distinctIPList.add(serverIpModel.getServerIp());
            }
            ArrayList<String> allDistinctServerIp =new ArrayList<>(distinctIPList);
            Random random=new Random();
            Log.d("Rnd","size"+distinctIPList.size());
            int randomIpIndex=random.nextInt(distinctIPList.size());
            String preferredIP=allDistinctServerIp.get(randomIpIndex);

            for(ServerIpModel serverIpModel:currentServers){
                if (serverIpModel.getServerIp().equals(preferredIP)){
                    preferredServerModels.add(serverIpModel);
                }
            }

            return preferredServerModels;
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger ();
            logger.insertLogToDB(weakReferenceActivity.get(), Constants.LOG_EXCEPTION(), e.toString()+R.string.errorIPConnected, "AsyncFindServerTask" , weakReferenceActivity.get().getClass().getSimpleName(), "doInBackground" , "");
            e.printStackTrace();
            ArrayList<ServerIpModel> serverIpModels=new ArrayList<>();
            return serverIpModels;
        }
    }


    @Override
    protected void onPostExecute(ArrayList<ServerIpModel> preferredServerModels)
    {
        for (ServerIpModel serverIpModel:preferredServerModels)
            delegate.processFinished(((ArrayList) preferredServerModels));
    }



}
