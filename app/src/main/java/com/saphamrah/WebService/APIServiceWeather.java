package com.saphamrah.WebService;

import com.saphamrah.WebService.ServiceResponse.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceWeather
{

    @GET("data/2.5/weather")
    Call<WeatherResult> getWeatherInfoByCoordinate(@Query("appid") String appid,
                                                   @Query("lat") double lat,
                                                   @Query("lon") double lng,
                                                   @Query("lang") String language,
                                                   @Query("units") String unit);



}
