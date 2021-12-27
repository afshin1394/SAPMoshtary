package com.saphamrah.WebService.GrpcService;

import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;

import io.grpc.ManagedChannel;
import io.grpc.okhttp.OkHttpChannelBuilder;

public  class GrpcChannel {

    private static ManagedChannel managedChannelInstance;
    private static GrpcInterceptor grpcInterceptor;

    public static  ManagedChannel channel(ServerIpModel serverIpModel){
        if (grpcInterceptor == null)
            grpcInterceptor = new GrpcInterceptor();

         serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(BaseApplication.getContext());

        int port ;
        if (serverIpModel.getPort().isEmpty())
            port = 0;
        else
            port=Integer.parseInt(serverIpModel.getPort());


        if (managedChannelInstance == null) {
            Log.i("GrpcChannel", "channel: channelCreated");
            return managedChannelInstance = OkHttpChannelBuilder
                    .forAddress(serverIpModel.getServerIp(), port)
                    .intercept(grpcInterceptor)
                    .maxRetryAttempts(5)
                    .usePlaintext()
                    .build();
        } else {
            return managedChannelInstance;
        }

    }

    public static long responseSize(){
        return grpcInterceptor.getResponseSize()/1000;
    }
    public static void shutDown(){
        if (grpcInterceptor!=null)
        grpcInterceptor.resetResponseSize();

        if (managedChannelInstance != null) {
            if (!managedChannelInstance.isShutdown())
                managedChannelInstance.shutdown();

            managedChannelInstance = null;
        }
    }


}
