package com.saphamrah.customer.utils.mapModule.Api;



import com.saphamrah.customer.utils.mapModule.Constants;
import com.saphamrah.customer.utils.mapModule.Responses.MapIr.Matrix.MatrixSuccessResponse;
import com.saphamrah.customer.utils.mapModule.Responses.MapIr.OptimizedResponse.OptimizedRouteResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServiceMapIr {
//    @Headers({"x-api-key:"+ Constants.MAP_IR_KEY})
    @GET("distancematrix/")
    Call<MatrixSuccessResponse> getMatrixDistance(
            @Header("x-api-key:") String mapKey,
            @Query("origins") String origins,
            @Query("destinations") String destinations,
            @Query("sorted") boolean sorted);

    @Headers({"x-api-key:"+ Constants.MAP_IR_KEY})
    @GET("/routes/route/v1/driving/{coordinates}")
    Call<OptimizedRouteResult> getDrivingRoute(
            @Path("coordinates") String latLngs,
            @Query("alternatives") boolean alternatives,
            @Query("steps") boolean steps);

    @Headers({"x-api-key:"+ Constants.MAP_IR_KEY})
    @GET("/routes/bicycle/v1/driving/{coordinates}")
    Call<OptimizedRouteResult> getBicycleRoute(
            @Path("coordinates") String latLngs,
            @Query("alternatives") boolean alternatives,
            @Query("steps") boolean steps);


    @Headers({"x-api-key:"+ Constants.MAP_IR_KEY})
    @GET("/routes/foot/v1/driving/{coordinates}")
    Call<OptimizedRouteResult> getFootRoute(
            @Path("coordinates") String latLngs,
            @Query("alternatives") boolean alternatives,
            @Query("steps") boolean steps);


    @Headers({"x-api-key:"+ Constants.MAP_IR_KEY})
    @GET("/routes/tarh/v1/driving/{coordinates}")
    Call<OptimizedRouteResult> getDrivingTarhRoute(
            @Path("coordinates") String latLngs,
            @Query("alternatives") boolean alternatives,
            @Query("steps") boolean steps);


    @Headers({"x-api-key:"+ Constants.MAP_IR_KEY})
    @GET("/routes/zojofard/v1/driving/")
    Call<OptimizedRouteResult> getDrivingZojoFardRoute(
            @Path("coordinates") String latLngs,
            @Query("alternatives") boolean alternatives,
            @Query("steps") boolean steps);


}
