package com.saphamrah.WebService.RxService;

import com.saphamrah.WebService.RxService.Response.DataResponse.CodeMelyResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetAllrptHadafeForoshResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetAllrptKalaInfoResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetMandehMojodyMashinResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetRtpThreeMonthPurchaseResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.KalaPhotoResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceRxjava {



    /**************************************************************************************C*****************************************************************************/

    @GET("Api/ApiTablet/CheckAfrad")
    Observable<Response<CodeMelyResponse>> checkAfrad(@Query("CodeMely") String CodeMely);




    /**************************************************************************************I*****************************************************************************/
    @GET("Api/ApiGlobal/GetImageKalaJSON")
    Observable<Response<KalaPhotoResponse>> getAllImageKala(@Query("ccKalaCode") String ccKalaCode);



    /**************************************************************************************M*****************************************************************************/
    @GET("Api/ApiTablet/Get_MandehMojody")
    Observable<Response<GetMandehMojodyMashinResponse>> getMandehMojodyMashin(@Query("ccAnbarak") String ccAnbarak, @Query("ccForoshandeh") String ccForoshandeh, @Query("ccMamorPakhsh") String ccMamorPakhsh);



    /**************************************************************************************R*****************************************************************************/
    @GET("Api/ApiSales/GetAllrpt_KalaInfo")
    Observable<Response<GetAllrptKalaInfoResponse>> getAllrptKalaInfo(@Query("ccMarkazSazmanSakhtarForosh") String ccMarkazSazmanSakhtarForosh);

    @GET("API/apisales/GetKharidMoshtarian3Month")
    Observable<Response<GetRtpThreeMonthPurchaseResponse>> getRtpThreeMonthPurchaseResult(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllrpt_HadafForoshTedady")
    Observable<Response<GetAllrptHadafeForoshResponse>> getAllRptHadafForosh(@Query("ccForoshandeh") String ccForoshandeh);


}
