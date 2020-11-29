package com.saphamrah.WebService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientNominatim
{

    private static final int TIME_OUT = 90;
    private static Retrofit retrofit = null;


    public static Retrofit getClient()
    {
        final String baseURL = "https://nominatim.openstreetmap.org/";

        if (retrofit == null)
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(TIME_OUT , TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT , TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
