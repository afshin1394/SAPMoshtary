package com.saphamrah.WebService;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.APIServiceOwghat;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.APIServiceValhalla;
import com.saphamrah.WebService.RxService.APIServiceRxjava;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientGlobal {
    private static final int TIME_OUT = 90;


    private static ApiClientGlobal instance;

    private static APIServiceGet apiServiceGetInstance = null;
    private static APIServicePost apiServicePostInstance = null;
    private static APIServiceRxjava apiServiceRxjavaInstance = null;
    private static APIServiceOwghat apiServiceOwghatInstance = null;
    private static APIServiceValhalla apiServiceValhallaInstance = null;



    public static ApiClientGlobal getInstance(){
        if (instance==null)
            instance=new ApiClientGlobal();
        return instance;
    }

    private static <S> S createService(Class<S> serviceClass, String baseURL) {


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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    public APIServiceGet getClientServiceGet(ServerIpModel serverIpModel) {
        String baseUrl = "http://" + serverIpModel.getServerIp() + ":" + serverIpModel.getPort() + "/";
        if (apiServiceGetInstance == null)
            apiServiceGetInstance = createService(APIServiceGet.class, baseUrl);


        return apiServiceGetInstance;
    }


    public APIServicePost getClientServicePost(ServerIpModel serverIpModel) {
        String baseUrl = "http://" + serverIpModel.getServerIp() + ":" + serverIpModel.getPort() + "/";
        if (apiServicePostInstance == null)
            apiServicePostInstance = createService(APIServicePost.class, baseUrl);


        return apiServicePostInstance;
    }

    public APIServiceRxjava getApiServiceRxjava(ServerIpModel serverIpModel) {
        String baseUrl = "http://" + serverIpModel.getServerIp() + ":" + serverIpModel.getPort() + "/";
        if (apiServiceRxjavaInstance == null)
            apiServiceRxjavaInstance = createService(APIServiceRxjava.class, baseUrl);
        return apiServiceRxjavaInstance;

    }

    public APIServiceOwghat getClientServiceOwghat() {
        String baseUrl = "https://api.keybit.ir/";
        if (apiServiceOwghatInstance == null)
            apiServiceOwghatInstance = createService(APIServiceOwghat.class, baseUrl);


        return apiServiceOwghatInstance;
    }

    public APIServiceValhalla getClientServiceValhalla(String urlOsrm) {
        String baseUrl = urlOsrm;
        if (apiServiceValhallaInstance == null)
            apiServiceValhallaInstance = createService(APIServiceValhalla.class, baseUrl);
        return apiServiceValhallaInstance;
    }


}
