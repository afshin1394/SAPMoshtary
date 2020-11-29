package com.saphamrah.WebService;

import com.saphamrah.WebService.ServiceResponse.CreateAdamDarkhastResult;
import com.saphamrah.WebService.ServiceResponse.CreateAddressResult;
import com.saphamrah.WebService.ServiceResponse.CreateAfradResult;
import com.saphamrah.WebService.ServiceResponse.CreateAmargarResult;
import com.saphamrah.WebService.ServiceResponse.CreateBarkhordForoshandehBaMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.CreateDariaftPardakhtPPCJSONResult;
import com.saphamrah.WebService.ServiceResponse.CreateDariaftPardakhtPPCResult;
import com.saphamrah.WebService.ServiceResponse.CreateDarkhastFaktorAfradForoshResult;
import com.saphamrah.WebService.ServiceResponse.CreateDarkhastFaktorWithVosol;
import com.saphamrah.WebService.ServiceResponse.CreateElamMarjoeePPCResult;
import com.saphamrah.WebService.ServiceResponse.CreateElamMarjoeeSatrPPCTedadResult;
import com.saphamrah.WebService.ServiceResponse.CreateGpsDataMashinResult;
import com.saphamrah.WebService.ServiceResponse.CreateGpsDataPPCResult;
import com.saphamrah.WebService.ServiceResponse.CreateKalaMojodyWithJSONResult;
import com.saphamrah.WebService.ServiceResponse.CreateLogPPCResult;
import com.saphamrah.WebService.ServiceResponse.CreateMojoodiGiriResult;
import com.saphamrah.WebService.ServiceResponse.CreateMoshtaryWithJSONResult;
import com.saphamrah.WebService.ServiceResponse.CreateMoshtaryWithMadarekResult;
import com.saphamrah.WebService.ServiceResponse.CreateShomarehHesabResult;
import com.saphamrah.WebService.ServiceResponse.CreateVisitMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.GetUpdateMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.UpdateDarkhastFaktorWithJSONResult;
import com.saphamrah.WebService.ServiceResponse.UpdateNotificationMessageBoxResult;
import com.saphamrah.WebService.ServiceResponse.UpdateStatusMessageBoxResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServicePost
{

    //A
    @FormUrlEncoded
    @POST("api/apiSales/CreateAdamDarkhast")
    Call<CreateAdamDarkhastResult> createAdamDarkhast(@Field("") String adamDarkhastData);

    @FormUrlEncoded
    @POST("api/apiGlobal/CreateAddress")
    Call<CreateAddressResult> createAddressResult(@Field("") String moshtaryAddressData);

    @FormUrlEncoded
    @POST("/api/apiGlobal/CreateAfrad")
    Call<CreateAfradResult> createAfradResult(@Field("") String moshtaryAfradData);


    //B
    @FormUrlEncoded
    @POST("api/apiSales/CreateBarkhordForoshandehBaMoshtary")
    Call<CreateBarkhordForoshandehBaMoshtaryResult> createBarkhordForoshandehBaMoshtary(@Field("") String barkhord);

    @FormUrlEncoded
    @POST("api/apiTreasury/CreateDariaftPardakhtPPC100")
    Call<CreateDariaftPardakhtPPCResult> createDariaftPardakht(@Field("") String dariaftPardakht);

    @FormUrlEncoded
    @POST("Api/ApiSales/CreateDariaftPardakhtPPCJSON")
    Call<CreateDariaftPardakhtPPCJSONResult> createDariaftPardakhtPPCJSON(@Field("") String jsonDariaftPardakht);


    //D
    @FormUrlEncoded
    @POST("api/apiSales/CreateDarkhastFaktorWithVosol")
    Call<CreateDarkhastFaktorWithVosol> createDarkhastFaktorWithVosol(@Field("") String darkhastFaktorData);

    @FormUrlEncoded
    @POST("api/apiSales/CreateDarkhastFaktorWithJson")
    Call<CreateDarkhastFaktorWithVosol> createDarkhastFaktorWithJson(@Field("") String darkhastFaktorData);

    @FormUrlEncoded
    @POST("api/apiSales/UpdateDarkhastFaktorWithJSON")
    Call<UpdateDarkhastFaktorWithJSONResult> updateDarkhastFaktorWithJSON(@Field("") String darkhastFaktorData);


    @FormUrlEncoded
    @POST("api/apiSales/CreateDarkhastHavalehWithJson")
    Call<CreateDarkhastFaktorWithVosol> createDarkhastFaktorWithVosolSard(@Field("") String darkhastFaktorSardData);

    @FormUrlEncoded
    @POST("api/apiSales/CreateDarkhastFaktorAfradForosh")
    Call<CreateDarkhastFaktorAfradForoshResult> createDarkhastFaktorAfradForosh(@Field("") String createDarkhastFaktorAfradForoshData);


    //G
    @FormUrlEncoded
    @POST("api/apiSales/CreateGpsData")
    Call<CreateGpsDataPPCResult> createGpsDataPPCResult(@Field("") String gpsData);

    @FormUrlEncoded
    @POST("api/apiTablet/CreateGpsData_Mashin")
    Call<CreateGpsDataMashinResult> createGpsDataMashinResult(@Field("") String gpsDataMashin);

    @FormUrlEncoded
    @POST("Api/ApiSales/CreateKalaMojodyWithJSON")
    Call<CreateKalaMojodyWithJSONResult> createKalaMojodyWithJSON(@Field("") String jsonStringData);


    //P
    @FormUrlEncoded
    @POST("Api/ApiSales/createAmargarJson")
    Call<CreateAmargarResult> createAmargarJson(@Field("") String data);


    //S
    @FormUrlEncoded
    @POST("/api/apiGlobal/CreateShomarehHesab")
    Call<CreateShomarehHesabResult> createShomarehHesabResult(@Field("") String moshtaryShomareHesabData);

    //L
    @FormUrlEncoded
    @POST("Api/ApiTablet/CreateLog")
    Call<CreateLogPPCResult> createLogPPC(@Field("") String logData);


    //M
    @FormUrlEncoded
    @POST("api/apiSales/CreateMojoodiGiri")
    Call<CreateMojoodiGiriResult> createMojoodiGiri(@Field("") String mojoodigiri);

    @FormUrlEncoded
    @POST("api/apiSales/CreateElamMarjoeePPC")
    Call<CreateElamMarjoeePPCResult> createElamMarjoeePPCResult(@Field("") String elamMarjoeePPCData);

    @FormUrlEncoded
    @POST("api/apiSales/CreateElamMarjoeeSatrPPC_Tedad")
    Call<CreateElamMarjoeeSatrPPCTedadResult> createElamMarjoeeSatrPPCTedadResult(@Field("") String elamMarjoeeSatrPPCTedadData);

    @FormUrlEncoded
    @POST("api/apiSales/CreateMoshtaryWithMadarek")
    Call<CreateMoshtaryWithMadarekResult> createMoshtaryWithMadarekResult(@Field("") String moshtaryWithMadarekData);


    @FormUrlEncoded
    @POST("Api/ApiSales/CreateMoshtaryWithJSON")
    Call<CreateMoshtaryWithJSONResult> createMoshtaryWithJSON(@Field("") String moshtaryData);

    @FormUrlEncoded
    @POST("Api/ApiTablet/Update_StatusMessageBox")
    Call<UpdateStatusMessageBoxResult> updateStatusMessageBox(@Field("") String messageBoxData);

    @FormUrlEncoded
    @POST("Api/ApiTablet/Update_NotificationMessageBox")
    Call<UpdateNotificationMessageBoxResult> updateNotificationMessage(@Field("") String jsonData);


    @FormUrlEncoded
    @POST("Api/ApiAmargar/CreateVisitMoshtary")
    Call<CreateVisitMoshtaryResult> createVisitMoshtary(@Field("") String data);

    // U
    @FormUrlEncoded
    @POST("Api/ApiSales/UpdateMoshtaryJSON")
    Call<GetUpdateMoshtaryResult> getUpdateMoshtaryResult(@Field("") String data);

}
