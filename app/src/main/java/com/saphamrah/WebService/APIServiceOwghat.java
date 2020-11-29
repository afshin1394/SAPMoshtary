package com.saphamrah.WebService;

import com.saphamrah.WebService.ServiceResponse.OwghatResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceOwghat
{

    @GET("owghat/")
    Call<OwghatResult> getOwghatByLatLong(@Query("lat") double lat , @Query("long") double lng);

}
