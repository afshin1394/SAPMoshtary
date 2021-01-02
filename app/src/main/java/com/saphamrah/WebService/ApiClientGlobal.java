package com.saphamrah.WebService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientGlobal {
    private static final int TIME_OUT = 90;

    public static <S> S createService(Class<S> serviceClass,String baseURL) {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();




        Retrofit.Builder builder = new Retrofit
                .Builder()
                .client(client)
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());



        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    public APIServicePost getClientServicePOST(String serverIP, String port){
        String baseURL = "http://" + serverIP + ":" + port + "/";
        APIServicePost apiServicePost = createService(APIServicePost.class,baseURL);
        return apiServicePost;
    }

}


