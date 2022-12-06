package com.saphamrah.customer.utils.mapModule.ApiClient;



import com.saphamrah.customer.utils.mapModule.Api.APIServiceMapIr;
import com.saphamrah.customer.utils.mapModule.Api.APIServiceValhalla;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClientGlobal {


    private static ApiClientGlobal instance;
    private static APIServiceValhalla apiServiceValhallaInstance = null;
    private static APIServiceMapIr apiServiceMapIrInstance = null;
    private static final int TIME_OUT = 90;



    public static ApiClientGlobal getInstance(){
        if (instance==null)
            instance=new ApiClientGlobal();
        return instance;
    }




    private static <S> S createService(Class<S> serviceClass, String baseURL) {


       HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
       interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
       interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient  client = new OkHttpClient.Builder()
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



    public APIServiceValhalla getClientServiceValhalla(String urlOsrm) {
        String baseUrl = urlOsrm;
        if (apiServiceValhallaInstance == null)
            apiServiceValhallaInstance = createService(APIServiceValhalla.class, baseUrl);
        return apiServiceValhallaInstance;
    }

    public APIServiceMapIr getClientServiceMapIr() {
        String baseUrl = "https://map.ir/";
        if (apiServiceMapIrInstance == null)
            apiServiceMapIrInstance = createService(APIServiceMapIr.class, baseUrl);
        return apiServiceMapIrInstance;
    }


}
