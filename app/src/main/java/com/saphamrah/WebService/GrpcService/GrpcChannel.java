package com.saphamrah.WebService.GrpcService;

import com.saphamrah.Model.ServerIpModel;

import io.grpc.ManagedChannel;
import io.grpc.okhttp.OkHttpChannelBuilder;

public  class GrpcChannel {

    private static ManagedChannel managedChannelInstance;
    private static GrpcInterceptor grpcInterceptor;
    private static ServerIpModel serverIpModel;

    public static  ManagedChannel channel(ServerIpModel serverIpModel){

        int port = 0;
        if (serverIpModel.getPort()!=null)
            port = Integer.parseInt(serverIpModel.getPort());



        if (grpcInterceptor == null)
            grpcInterceptor = new GrpcInterceptor();


      //  if (GrpcChannel.serverIpModel.getServerIp().equals(serverIpModel.getServerIp()) && GrpcChannel.serverIpModel.getPort().equals(serverIpModel.getPort())) {

            if (managedChannelInstance == null) {
                GrpcChannel.serverIpModel = serverIpModel;
                return managedChannelInstance = OkHttpChannelBuilder
                        .forAddress(serverIpModel.getServerIp(), port)
                        .intercept(grpcInterceptor)
                        .usePlaintext()
                        .build();
            } else {
                return managedChannelInstance;
            }
       // }else{
//            return managedChannelInstance = OkHttpChannelBuilder
//                    .forAddress(serverIpModel.getServerIp(), port)
//                    .intercept(grpcInterceptor)
//                    .usePlaintext()
//                    .build();
        //}

    }

    public static long responseSize(){
        return grpcInterceptor.getResponseSize()/1000;
    }
    public static void shutDown(){
        grpcInterceptor.resetResponseSize();
        if (managedChannelInstance != null) {
            if (!managedChannelInstance.isShutdown())
                managedChannelInstance.shutdown();

            managedChannelInstance = null;
        }
    }


}
