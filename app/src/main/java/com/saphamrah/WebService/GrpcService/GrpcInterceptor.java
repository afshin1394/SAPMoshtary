package com.saphamrah.WebService.GrpcService;

import android.util.Log;

import com.google.gson.Gson;
import com.saphamrah.BuildConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;

public  class  GrpcInterceptor implements ClientInterceptor {
    private static final String TAG = "Grpc";
    private
    long requestTime;
    long responseTime;
    private int responseSize = 0;
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            protected ClientCall<ReqT, RespT> delegate() {
                return super.delegate();
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT> listener = new
                        ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                            @Override
                            public void onClose(Status status, Metadata trailers) {
                                Log.i(TAG, "<-- [STATUS] " +
                                        status.getCode().name());
                                super.onClose(status, trailers);
                            }

                            @Override
                            public void onMessage(RespT message) {

                                responseSize += getGRPCResponseSize(message.toString());
                                responseTime  = System.nanoTime();

                                 long seconds = ((responseTime - requestTime)/1000000000);
                                 long  milliSeconds = ((responseTime - requestTime) / 1000000);

                                 if (BuildConfig.DEBUG) {
                                     Log.i(TAG, "<-- [RESPONSE] " +
                                             method.getFullMethodName() +
                                             " (" + message.toString().length() +
                                             " bytes): \n" + message.toString() + "\n" +
                                             " [LATENCY] " + String.valueOf(seconds) + ":" + String.valueOf(milliSeconds));
                                 }
                                super.onMessage(message);
                            }
                        };
                super.start(listener, headers);
            }

            @Override
            public void sendMessage(ReqT message) {
                requestTime = System.nanoTime();
                Log.i(TAG, "--> [REQUEST] " + method.getFullMethodName() + ": \n" + message.toString());
                super.sendMessage(message);
            }
        };
    }


    static <T> MethodDescriptor.Marshaller<T> marshallerFor(Class<T> clz) {
        Gson gson = new Gson();
        return new MethodDescriptor.Marshaller<T>() {
            @Override
            public InputStream stream(T value) {
                return new ByteArrayInputStream(gson.toJson(value, clz).getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public T parse(InputStream stream) {
                return gson.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), clz);
            }
        };
    }


    private int getGRPCResponseSize(String message)  {
        return message.getBytes(StandardCharsets.UTF_8).length;
    }

   public long getResponseSize(){
        return responseSize;
   }
   public void resetResponseSize(){
        responseSize = 0;
   }

}
