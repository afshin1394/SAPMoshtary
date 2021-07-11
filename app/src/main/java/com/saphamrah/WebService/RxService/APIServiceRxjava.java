package com.saphamrah.WebService.RxService;

import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.WebService.RxService.Response.DataResponse.CodeMelyResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetAllrptHadafeForoshResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetAllrptKalaInfoResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetMandehMojodyMashinResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetRtpThreeMonthPurchaseResponse;
import com.saphamrah.WebService.RxService.Response.DataResponse.KalaPhotoResponse;
import com.saphamrah.WebService.ServiceResponse.*;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceRxjava {










    /**************************************************************************************M*****************************************************************************/
    @GET("Api/ApiTablet/Get_MandehMojody")
    Observable<Response< GetMandehMojodyMashinResponse>>getMandehMojodyMashin(@Query("ccAnbarak") String ccAnbarak
            , @Query("ccForoshandeh") String ccForoshandeh
            , @Query("ccMamorPakhsh") String ccMamorPakhsh
            , @Query("cckalaCode") String ccKalaCode
            , @Query("ccSazmanForosh") String ccSazmanForosh);









    //A
    @GET("Api/ApiTablet/GetAnbarakAfrad")
    Observable<Response<GetAnbarakAfradResult>>getAnbarakAfrad(@Query("ccAfradForoshandehMamorPakhsh") String ccAfradForoshandehMamorPakhsh);

    @GET("Api/ApiAmargar/GetamargarMarkazsazmanForosh")
    Observable<Response<GetAmargarMarkazForoshResult>>getAmargarMarkazForosh(@Query("ccAmargar") String ccAmargar);

    @GET("Api/ApiAmargar/GetAllAmargarGoroh")
    Observable<Response<GetAllAmargarGorohResult>>getAllAmargarGoroh();

    @GET("Api/ApiAmargar/GetAllBrand")
    Observable<Response<GetAllBrandResult>>getAllAmargarBrand();

    @GET("Api/ApiAmargar/GetAllvKala")
    Observable<Response<GetMojodyAnbarResult>>getAllKalaAmargar();

    @GET("Api/ApiAmargar/GetAllvKalaGoroh")
    Observable<Response<GetAllvKalaGorohResult>>getAllKalaGorohAmargar();

    @GET("Api/ApiGlobal/GetAllMahalByccMarkazForosh_Amargar")
    Observable<Response<GetAllMahalByccMarkazForoshResult>>getAllMahalByccMarkazForoshAmargar(@Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiAmargar/GetAllRotbeh")
    Observable<Response<GetAllRotbehResult>>getAllRotbeh();

    @GET("Api/ApiAmargar/GetElatAdamMoarefiMoshtary")
    Observable<Response<GetElatAdamMoarefiMoshtaryResult>>getElatAdamMoarefiMoshtary();


    //B
    @GET("Api/ApiSales/GetBarkhordForoshandehBaMoshtary")
    Observable<Response<BarkhordForoshandehBaMoshtaryResult>>getBarkhordForoshandehBaMoshtary(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMoshtarys") String ccMoshtarys, @Query("TedadMah") String TedadMah);

    @GET("Api/ApiGlobal/GetAllBank")
    Observable<Response<GetAllBankResult>>getAllBank();

    @GET("Api/ApiWarehouse/GetAllBrand")
    Observable<Response<GetAllBrandResult>>getAllBrand();

    @GET("Api/ApiSales/Getrpt_ListBargashtyForoshandeh")
    Observable<Response<GetListBargashtyForoshandehResult>>getListBargashtyForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiTablet/GetBankByMamorPakhshPosition")
    Observable<Response<GetBankByMamorPakhshPositionResult>>getBankByMamorPakhshPosition(@Query("NoeBank") String NoeBank, @Query("Lat_y_MamorPakhshPakhsh") String lat, @Query("Lng_x_MamorPakhshPakhsh") String lng);




    /**************************************************************************************C*****************************************************************************/




    @GET("Api/ApiTablet/Control_InsertFaktor")
    Observable<Response<ControlInsertFaktorResult>>controlInsertFaktor(@Query("UniqID_Tablet") String uniqeIdTablet,
                                                        @Query("ccMoshtary") String ccMoshtary,
                                                        @Query("ccForoshandeh") String ccForoshandeh);


    @GET("Api/ApiTablet/CheckAfrad")
    Observable<Response<CodeMelyResponse>>checkAfrad(@Query("CodeMely") String CodeMely);



    //D
    @GET("Api/ApiSales/GetDarkhastFaktor")
    Observable<Response<GetDarkhastFaktorResult>>getDarkhastFaktor(@Query("ccAfrad") String ccAfrad , @Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetDarkhastFaktor_HavalehSatr")
    Observable<Response<GetDarkhastFaktorSatrResult>>getDarkhastFaktorSatr(@Query("Noe_Faktor_Havaleh") String noeFaktorHavaleh, @Query("ccDarkhastFaktors") String ccDarkhastFaktors);

    @GET("Api/ApiTreasury/GetDariaftPardakht_HavalehPPC")
    Observable<Response<GetDariaftPardakhtHavalePPCResult>>getDariaftPardakhtHavalePPC(@Query("Noe_Faktor_Havaleh") String noeFaktorHavale , @Query("ccDarkhastFaktors") String ccDarkhastFaktors);

    @GET("Api/ApiTreasury/GetDariaftPardakhtDarkhastFaktorHavalehPPC")
    Observable<Response<GetDariaftPardakhtDarkhastFaktorHavalehPPCResult>>getDariaftPardakhtDarkhastFaktorHavalehPPC(@Query("Noe_Faktor_Havaleh") String noeFaktorHavaleh , @Query("ccDarkhastFaktors") String ccDarkhastFaktors);

    @GET("Api/ApiSales/GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate")
    Observable<Response<GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDateResult>>getAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate(@Query("ccForoshande") String ccForoshande, @Query("fromDate") String fromDate, @Query("toDate") String endDate);

    @GET("Api/apitreasury/GetConfigNoeVosolMojazefaktor")
    Observable<Response<GetConfigNoeVosolMojazefaktorResult>> getConfigNoeVosolMojazefaktor();

    @GET("Api/apitreasury/GetConfigNoeVosolMojazeMoshtary")
    Observable<Response<GetConfigNoeVosolMojazeMoshtaryResult>> getConfigNoeVosolMojazeMoshtary(@Query("ccNoeMoshtarys")  String ccNoeMoshtarys);


    //E
    @GET("Api/ApiSales/GetAllvElatAdamDarkhast")
    Observable<Response<GetAllvElatAdamDarkhastResult>>getElatAdamDarkhast();

    @GET("Api/ApiWareHouse/GetAllElatMarjoeeKala")
    Observable<Response<GetAllElatMarjoeeKalaResult>>getElatMarjoeeKala();

    @GET("Api/ApiTablet/GetEtebar")
    Observable<Response<GetEtebarResult>>getEtebar(@Query("ccForoshandeh") String ccForoshandeh , @Query("ccAnbarak") String ccAnbarak, @Query("flag_updateEtebar") String flag_updateEtebar);

    @GET("Api/ApiSales/GetForoshandehEtebar")
    Observable<Response<GetEtebarForoshandehResult>>getEtebarForoshandeh(@Query("ccForoshandehs") String ccForoshandehs);

    //F
    @GET("Api/ApiSales/GetForoshandehAmoozeshi_DeviceNumber")
    Observable<Response<GetForoshandehAmoozeshiResult>>getForoshandehAmoozeshi();

    @GET("Api/ApiSales/GetForoshandeh")
    Observable<Response<GetAllvForoshandehByccForoshandehResult>>getAllvForoshandehByccForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllvForoshandeh")
    Observable<Response<GetAllvForoshandehByccForoshandehResult>>getAllForoshandeh();

    @GET("Api/ApiTablet/GetAfradByDeviceNumber")
    Observable<Response<GetForoshandehMamorPakhshResult>>getForoshandehMamorPakhsh(@Query("DeviceSerialNumber") String deviceSerial);

    @GET("Api/ApiSales/GetAllForoshandehMoshtaryByccMasir")
    Observable<Response<GetAllForoshandehMoshtaryByccMasirResult>>getAllForoshandehMoshtaryByccMasir(@Query("ccMasir") String ccMasir);

    @GET("Api/ApiSales/GetAllrpt_FaktorTozieNashodeh")
    Observable<Response<GetAllrptFaktorTozieNashodehResult>>getAllrptFaktorTozieNashodeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllrpt_AmarForosh")
    Observable<Response<GetAllrptAmarForoshResult>>getAllrptAmarForosh(@Query("ccForoshandeh") String ccForoshandeh , @Query("TaTarikh") String TaTarikh);


    //I
    @GET("Api/ApiGlobal/GetImageString")
    Observable<Response<GetImageStringResult>>getImageString(@Query("ImgName") String ImgName);

    @GET("Api/ApiGlobal/GetImageStringForMamorPakhsh")
    Observable<Response<GetImageStringResult>>getImageStringForMamorPakhsh(@Query("ImgName") String ImgName);

    @GET("Api/ApiGlobal/GetImageJSON")
    Observable<Response<GetImageJsonResult>>getImageJSON(@Query("ccDarkhastFaktor") String ccDarkhastFaktor , @Query("ccDarkhastHavaleh") String ccDarkhastHavaleh);

    @GET("Api/ApiGlobal/GetImageKalaJSON")
    Observable<Response<GetImageKalaResult>>getImageKala(@Query("ccKalaCode") String ccKalaCode);

    @GET("Api/ApiGlobal/GetImageKalaJSON")
    Observable<Response<KalaPhotoResponse>> getAllImageKala(@Query("ccKalaCode") String ccKalaCode);

    //G
    @GET("Api/ApiGlobal/GetLoginInfo")
    Observable<Response<GetLoginInfoResult>>getLoginInfo();

    @GET("Api/ApiGlobal/GetAllGoroh")
    Observable<Response<GetAllGorohResult>>getAllGoroh();

    @GET("Api/ApiSales/GetAllGorohKalaNoeSenf")
    Observable<Response<GetAllGorohKalaNoeSenfResult>>getAllGorohKalaNoeSenf();

    @GET("Api/ApiSales/GetGPSDataByccForoshandeh")
    Observable<Response<GetGPSDataResult>>getGPSDataByccForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetGPSDataByccMamorPakhsh")
    Observable<Response<GetGPSDataResult>>getGPSDataByccMamorPakhsh(@Query("ccMamorPakhsh") String ccMamorPakhsh);


    @GET("Api/ApiTablet/GetMarjoeeForoshandehByDarkhastFaktor_Titr")
    Observable<Response<GetMarjoeeForoshandehByDarkhastFaktorTitrResult>>getMarjoeeForoshandehByDarkhastFaktorTitr(@Query("Noe_Titr_Satr") String noeTitrSatr , @Query("ccDarkhastHavaleh") String ccDarkhastHavaleh);

    @GET("Api/ApiTablet/GetMarjoeeForoshandehByDarkhastFaktor_Satr")
    Observable<Response<GetMarjoeeForoshandehByDarkhastFaktorSatrResult>>getMarjoeeForoshandehByDarkhastFaktorSatr(@Query("Noe_Titr_Satr") String noeTitrSatr , @Query("ccDarkhastHavaleh") String ccDarkhastHavaleh);

    @GET("Api/ApiSales/GetNoeVosolMoshtary")
    Observable<Response<NoeVosolMoshtaryResult>>getNoeVosolMoshtary(@Query("ccMarkazSazmanForosh") int ccMarkazSazmanForosh);


    @GET("Api/ApiSales/GetGPSDataByccMamorPakhsh")
    Observable<Response<GPSDataModel>>GetGPSDataByccMamorPakhsh(@Query("ccMamorPakhsh") int ccMamorPakhsh);


    //K
    @GET("Api/ApiTablet/GetKalaOlaviat")
    Observable<Response<GetKalaOlaviatResult>>getKalaOlaviat(@Query("ccanbarak") String ccAnbarak);

    @GET("Api/ApiWareHouse/GetAllvKalaGoroh")
    Observable<Response<GetAllvKalaGorohResult>>getAllvKalaGoroh();

    @GET("Api/ApiSales/GetAllvKalaZaribForoshWithMarkazForosh")
    Observable<Response<GetAllvKalaZaribForoshResult>>getAllvKalaZaribForosh(@Query("ccGorohs") String ccGorohs , @Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiGlobal/GetAllMarkaz_ShahrMarkazi")
    Observable<Response<GetAllMarkazShahrMarkaziResult>>getAllMarkazShahrMarkazi();


    //J
    @GET("Api/ApiSales/GetJayezeh")
    Observable<Response<GetAllvJayezehByccMarkazForoshResult>>getJayezeh(@Query("Noe_Titr_Satr") String noeTiteSatr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccJayezehs") String ccJayezehs);
    @GET("Api/ApiSales/GetAllvJayezehByccMarkazForosh")
    Observable<Response<GetAllvJayezehByccMarkazForoshResult>>getAllvJayezehByccMarkazForosh(@Query("ccMarkazForosh") String ccMarkazForosh);

    /**
     * @deprecated
     * @param ccMarkazForoshPakhsh
     * @return
     */
    @GET("Api/ApiSales/GetAllvJayezehByccMarkazForoshPakhsh")
    Observable<Response<GetAllvJayezehByccMarkazForoshResult>>getAllvJayezehByccMarkazForoshForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForoshPakhsh);

    /**
     * @deprecated
     * @return
     */
    @GET("Api/ApiSales/GetAllvJayezehSatr")
    Observable<Response<GetAllvJayezehSatrResult>>getAllvJayezehSatr();


    @GET("Api/ApiSales/GetJayezehSatr")
    Observable<Response<GetAllvJayezehSatrResult>>getJayezehSatr(@Query("Noe_Titr_Satr") String noeTiteSatr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccJayezehs") String ccJayezehs);

    @GET("Api/ApiSales/GetAllvJayezehEntekhabi")
    Observable<Response<GetAllvJayezehEntekhabiResult>>getAllvJayezehEntekhabi(@Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiSales/GetAllvJayezehEntekhabiPakhsh")
    Observable<Response<GetAllvJayezehEntekhabiResult>>getAllvJayezehEntekhabiForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForosh);


    //V
    @GET("Version/Version.txt")
    Observable<Response<GetVersionResult>>getVersionInfo();



    //P
    @GET("Api/ApiGlobal/GetPosShomarehHesab")
    Observable<Response<GetPosShomarehHesabResult>>getPosShomarehHesab(@Query("ccPosShomarehHesab") String ccPosShomarehHesab);

    @GET("Api/ApiAmargar/GetPorseshnamehTozihat")
    Observable<Response<GetPorseshnamehTozihatResult>>getPorseshnamehTozihat();

    @GET("Api/ApiMap/GetAllvPolygonForoshSatrByForoshandeh")
    Observable<Response<GetAllvPolygonForoshSatrByForoshandehResult>>getAllvPolygonForoshSatrByForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiTablet/GetParameter")
    Observable<Response<GetParameterResult>>getParameter(@Query("Noe_Titr_Satr") String noeTitrSatr , @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccMarkazAnbar") String ccMarkazAnbar, @Query("DateProgram") String DateProgram);

    @GET("Api/ApiTablet/GetParameterChild")
    Observable<Response<GetParameterChildResult>>getParameterChild(@Query("Noe_Titr_Satr") String noeTitrSatr , @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccMarkazAnbar") String ccMarkazAnbar, @Query("DateProgram") String DateProgram);

    /**************************************************************************************R*****************************************************************************/

    @GET("reverse")
    Observable<Response<LocationToAddressResult>>reverseLocationToAddress(@Query("format") String format, @Query("lat") String lat, @Query("lon") String lon, @Query("zoom") String zoom);

    @GET("Api/ApiTablet/GetRpt_MojodiAnbarak")
    Observable<Response<GetRptMojodiAnbarakResult>>getRptMojodiAnbarak(@Query("ccAnbarak") String ccAnbarak , @Query("ccForoshandeh") String ccForoshandeh , @Query("ccMamorPakhsh") String ccMamorPakhshReq);

    @GET("Api/ApiSales/GetAllrpt_ListAsnadForoshandeh")
    Observable<Response<GetAllrptListAsnadForoshandehResult>>getAllRptSanad(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllrpt_DarkhastFaktorHavalehVazeiat")
    Observable<Response<GetAllrptDarkhastFaktorHavalehVazeiatResult>>getAllrptDarkhastFaktorHavalehVazeiat(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMamorPakhsh") String ccMamorPakhsh);





    @GET("Api/ApiSales/GetAllrpt_KalaInfo")
    Observable<Response<GetAllrptKalaInfoResponse>>getAllrptKalaInfo(@Query("ccMarkazSazmanSakhtarForosh") String ccMarkazSazmanSakhtarForosh);

    @GET("Api/ApiSales/GetAllrpt_VisitForoshandeh")
    Observable<Response<GetAllrptVisitForoshandehResult>>getAllrptVisitForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiTreasury/Get_rpt_ListVosol")
    Observable<Response<GetrptListVosolResult>>getrptListVosol(@Query("ccAfradForoshandehMamorPakhsh") String ccAfradForoshandehMamorPakhsh);

    @GET("Api/ApiSales/GetKharidKalaByccForoshandeh")
    Observable<Response<GetKharidKalaByccForoshandehResult>>getKharidKalaByccForoshandeh(@Query("Level") String Level , @Query("ccForoshandeh") String ccForoshandeh , @Query("ccKalaGoroh") String ccKalaGoroh);


    @GET("Api/ApiTablet/Getrpt_MarjoeeForoshandeh")
    Observable<Response<GetrptMarjoeeForoshandehResult>>getrptMarjoeeForoshandeh(@Query("ccAfrad") String ccAfrad);

    @GET("Api/ApiSales/GetAllrpt_HadafForoshTedady")
    Observable<Response<GetAllrptHadafeForoshResponse>>getAllRptHadafForosh(@Query("ccForoshandeh") String ccForoshandeh);

    // S
    @GET("Api/apitablet/getsupportcrisp")
    Observable<Response<SupportCrispResult>>getSupportCrisp(@Query("ccsazmanforosh") int ccsazmanforosh);

    //M
    @GET("Api/apisales/GetMoshtaryGharardadKala")
    Observable<Response<GetAllMoshtaryGharardadKalaResult>>getMoshtaryGharardadKala(@Query("ccsazmanForosh") String ccSazmanForosh, @Query("ccMoshtaryGharardad") String ccMoshtaryGharardad);


    @GET("Api/apisales/GetMoshtaryGharardad")
    Observable<Response<GetAllMoshtaryGharardadResult>>getMoshtaryGharardad(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiGlobal/GetAllMahalByccMarkazForosh")
    Observable<Response<GetAllMahalByccMarkazForoshResult>>getAllMahalByccMarkazForosh(@Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh);

    @GET("Api/ApiGlobal/GetAllvMarkaz")
    Observable<Response<GetAllvMarkazResult>>getAllvMarkaz(@Query("ccMarkaz") String ccMarkaz);

    @GET("Api/ApiGlobal/GetAllvMarkazForosh")
    Observable<Response<GetAllvMarkazResult>>getAllvMarkazForosh();

    @GET("Api/ApiGlobal/GetAllvMarkazShomarehHesab")
    Observable<Response<GetAllvMarkazShomarehHesabResult>>getAllvMarkazShomarehHesab(@Query("ccMarkaz") String ccMarkaz);

    @GET("Api/ApiGlobal/Get_ListShomareHesab")
    Observable<Response<Get_ListShomareHesab>>Get_ListShomareHesab(@Query("ccMarkazAnbar") String ccMarkazAnbar);

    @GET("Api/ApiSales/GetAllMasir")
    Observable<Response<GetAllMasirResult>>getAllMasir(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMarkazForosh") String ccMarkazForosh, @Query("azTarikh") String azTarikh, @Query("taTarikh") String taTarikh, @Query("codeNoe") String codeNoe);

    @GET("Api/ApiTablet/GetMasirFaalForoshandeh")
    Observable<Response<GetAllMasirResult>>getAllMasirFaalForoshandeh();

    @GET("Api/ApiSales/GetAllMoshtaryBrand")
    Observable<Response<GetAllMoshtaryBrandResult>>getAllMoshtaryBrand(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetMarjoeeMamorPakhsh_Pakhsh")
    Observable<Response<MarjoeeMamorPakhshResult>> getMarjoeeMamorPakhsh(@Query("ccMoshtarys") String ccMoshtarys);


    //TODO
    @GET("Api/ApiSales/GetMair_VaznHajmMashin")
    Observable<Response<GetMairVaznHajmMashinResult>>  getMasirVaznHajmMashin(@Query("ccMasir") String ccMasir);

    @GET("Api/ApiSales/GetAllMoshtaryByccMasir")
    Observable<Response<GetAllMoshtaryByccMasirResult>>getAllMoshtaryByccMasir(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMasirs") String ccMasirs, @Query("CodeMoshtary") String CodeMoshtary);

    @GET("Api/ApiSales/GetMoshtarysFirstParent_PPC")
    Observable<Response<GetMoshtarysFirstParentPPCResult>>getMoshtaryFirstParentPPC(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiTablet/Get_MoshtaryByRadius")
    Observable<Response<GetMoshtaryByRadiusResult>>getMoshtaryByRadius(@Query("Radius") String Radius , @Query("Latitude_y") String Latitude_y , @Query("Longitude_x") String Longitude_x);

    @GET("Api/ApiAmargar/GetMoshtaryForAmargar")
    Observable<Response<GetMoshtaryByRadiusResult>>getListMoshtarianByMasir(@Query("Noe") String noe , @Query("ccMasir") String ccMasir);

    @GET("Api/ApiSales/GetAllvMoshtaryAddress")
    Observable<Response<GetAllvMoshtaryAddressResult>>getAllvMoshtaryAddress(@Query("ccMoshtarys") String ccMoshtarys, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh);


    @GET("Api/ApiSales/GetMoshtaryAddress")
    Observable<Response<GetAllvMoshtaryAddressResult>>getMoshtaryAddressByNoeMasouliat(@Query("ccforoshandeh") String ccforoshandeh, @Query("ccMasirs") String ccMasirs, @Query("ccMoshtary") String ccMoshtary);

    @GET("Api/ApiSales/GetAllvMoshtaryAddressPakhsh")
    Observable<Response<GetAllvMoshtaryAddressResult>>getAllvMoshtaryAddressForPakhsh(@Query("ccMoshtarys") String ccMoshtarys, @Query("ccMarkazSazmanForoshs") String ccMarkazSazmanForoshPakhsh);

    @GET("Api/ApiSales/GetAllvMoshtaryAfrad")
    Observable<Response<GetAllvMoshtaryAfradResult>>getAllvMoshtaryAfrad(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetMoshtaryEtebarPishfarz")
    Observable<Response<GetMoshtaryEtebarPishfarzResult>>getMoshtaryEtebarPishfarz(@Query("ccforoshandeh") String ccforoshandeh);

    @GET("Api/ApiSales/GetAllvMoshtaryEtebarSazmanForosh")
    Observable<Response<GetAllvMoshtaryEtebarSazmanForoshResult>>getAllvMoshtaryEtebarSazmanForosh(@Query("ccMoshtarys") String ccMoshtarys , @Query("ccSazmanForosh") String ccSazmanForosh);

    @GET("Api/ApiSales/GetAllvMoshtaryEtebarSazmanForoshPakhsh")
    Observable<Response<GetAllvMoshtaryEtebarSazmanForoshResult>>getAllvMoshtaryEtebarSazmanForoshForPakhsh(@Query("ccMoshtarys") String ccMoshtarys , @Query("ccSazmanForoshs") String ccSazmanForoshPakhsh);


    @GET("Api/ApiSales/GetMoshtaryPolygon")
    Observable<Response<GetMoshtaryPolygonResult>>getMoshtaryPolygon(@Query("ccMasirs") String ccMasirs , @Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetAllvMoshtaryShomarehHesab")
    Observable<Response<GetAllvMoshtaryShomarehHesabResult>>getAllvMoshtaryShomarehHesab(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetMoshtary_Pakhsh")
    Observable<Response<GetMoshtaryPakhshResult>>getMoshtaryPakhsh(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiTablet/MoshtaryMorajehShodeh_Rooz")
    Observable<Response<MoshtaryMorajehShodehRoozResult>>getMoshtaryMorajehShodehRooz(@Query("ccForoshandeh") String ccForoshandeh , @Query("ccMasir") String ccMasir);

    @GET("Api/ApiSales/GetAllvModatVosolByccMarkazForoshGoroh")
    Observable<Response<GetAllvModatVosolByccMarkazForoshGorohResult>>getAllvModatVosolByccMarkazForoshGorohRx(@Query("ccMarkazForosh") int ccMarkazForosh, @Query("ccGorohs") String ccGorohs);

    @GET("Api/ApiSales/GetAllModatVosolGoroh")
    Observable<Response<GetAllModatVosolGorohResult>>getAllModatVosolGoroh(@Query("ccGorohs") String ccGorohs);

    @GET("Api/ApiSales/GetAllModatVosolMarkazForoshByccMarkazForosh")
    Observable<Response<GetAllModatVosolMarkazForoshByccMarkazForoshResult>>getAllModatVosolMarkazForoshByccMarkazForoshRx(@Query("ccMarkazForosh") int ccMarkazForosh);

    @GET("Api/ApiTablet/GetMojodyAnbar")
    Observable<Response<GetMojodyAnbarResult>>getMojodyAnbar(@Query("ccAfrad") String ccAfrad,@Query("ccMarkazSazmanForoshSakhtarForosh") String ccMarkazSazmanForoshSakhtarForosh);

    @GET("Api/ApiTablet/Get_MandehMojody")
    Observable<Response<GetMandehMojodyMashinResult>>getMandehMojodyMashin(@Query("ccAnbarak") String ccAnbarak, @Query("ccForoshandeh") String ccForoshandeh, @Query("ccMamorPakhsh") String ccMamorPakhsh);

    @GET("Api/ApiTablet/GetAllMoshtaryChidman")
    Observable<Response<GetAllMoshtaryChidmanResult>>getAllMoshtaryChidman(@Query("ccMasirs") String ccMasirs);

    @GET("Api/ApiTablet/GetAllMoshtaryForoshandeh")
    Observable<Response<GetAllMoshtaryForoshandehResult>>getAllMoshtaryForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllrpt_ListMoavaghForoshandeh")
    Observable<Response<GetAllrptListMoavaghForoshandehResult>>getAllrptListMoavaghForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllrpt_MasirForoshandeh")
    Observable<Response<GetAllrptMasirForoshandehResult>>getAllrptMasirForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiTablet/Get_ListKalaForMarjoee")
    Observable<Response<GetListKalaForMarjoeeResult>>getListKalaForMarjoee(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMoshtary") String ccMoshtary);

    @GET("Api/ApiGlobal/GetAllvMahal_CodePostiByccMarkazForosh")
    Observable<Response<GetMahalCodePostiResult>>getMahalCodePosti(@Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiTablet/GetMessageBox")
    Observable<Response<GetMessageBoxResult>>getMessageBoxResult(@Query("ccForoshandeh") String ccForoshandeh , @Query("ccMamorPakhsh") String ccMamorPakhsh);

    //N
    @GET("Api/ApiGlobal/GetAllNoeHesab")
    Observable<Response<GetAllNoeHesabResult>>getAllNoeHesab();

    @GET("Api/ApiSales/GetAllNoeMalekiatMoshtary")
    Observable<Response<GetAllNoeMalekiatMoshtaryResult>>getAllNoeMalekiatMoshtary();

    @GET("Api/ApiSales/GetAllNoeMoshtaryrialKharidByccMarkazForosh")
    Observable<Response<GetAllNoeMoshtaryRialKharidResualt>>getAllNoeMoshtaryrialKharid(@Query("ccMarkazSazmanForoshSakhtarForoshs") String ccMarkazSazmanForoshSakhtarForoshs);
//
//    @GET("Api/ApiTreasury/GetCodeNoeVosol")
//    Observable<Response<GetCodeNoeVosolResult>>getCodeNoeVosol();

    @GET("Api/ApiTablet/GET_NoeFaaliatForMoarefiMoshtaryJadid")
    Observable<Response<GetNoeFaaliatForMoarefiMoshtaryJadidResult>>getNoeFaaliatForMoarefiMoshtaryJadid(@Query("ccforoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllMoshtaryJadidDarkhastPPC")
    Observable<Response<GetAllMoshtaryJadidDarkhastPPCResult>>getAllMoshtaryJadidDarkhastPPC();

    @GET("Api/apisales/GetMoshtarianKharidNakardeh")
    Observable<Response<GetAllMoshtarianKharidNakardeResult>>getAllMoshtarianeKharidNakarde(@Query("ccforoshandeh") String ccForoshandeh, @Query("ccMasirs") String ccMasirs);

    //T
    @GET("Api/ApiTablet/GetTedadFaktorMoshtary")
    Observable<Response<GetTedadFaktorMoshtaryResult>>getTedadFaktorMoshtary(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetAllvTakhfifHajmiByccMarkazForosh")
    Observable<Response<GetAllvTakhfifHajmiByccMarkazForoshResult>>getAllvTakhfifHajmiByccMarkazForosh(@Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiSales/GetAllvTakhfifHajmiByccMarkazForoshPakhsh")
    Observable<Response<GetAllvTakhfifHajmiByccMarkazForoshResult>>getAllvTakhfifHajmiByccMarkazForoshForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForosh);


    @GET("Api/ApiSales/GetTakhfifHajmi")
    Observable<Response<GetAllvTakhfifHajmiByccMarkazForoshResult>>getTakhfifHajmiTitr(@Query("Noe_Titr_Satr") String Noe_Titr_Satr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccTakhfifHajmi") String ccTakhfifHajmi);

    @GET("Api/ApiSales/GetTakhfifHajmiSatr")
    Observable<Response<GetAllvTakhfifHajmiSatrResult>>getTakhfifHajmiSatr(@Query("Noe_Titr_Satr") String Noe_Titr_Satr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccTakhfifHajmi") String ccTakhfifHajmi);


    @GET("Api/ApiSales/GetTakhfifSenfi")
    Observable<Response<GetAllvTakhfifSenfiByccMarkazForoshResult>>getTakhfifSenfiTitr(@Query("Noe_Titr_Satr") String Noe_Titr_Satr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccTakhfifSenfi") String ccTakhfifSenfi);

    @GET("Api/ApiSales/GetTakhfifSenfiSatr")
    Observable<Response<GetAllvTakhfifSenfiSatrResult>>getTakhfifSenfiSatr(@Query("Noe_Titr_Satr") String Noe_Titr_Satr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccTakhfifSenfi") String ccTakhfifSenfi);

    @GET("Api/ApiSales/GetTakhfifNaghdy")
    Observable<Response<GetAllvTakhfifNaghdyByccMarkazForoshResult>>getTakhfifNaghdy(@Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh);

    @GET("Api/ApiSales/GetAllvTakhfifHajmiSatr")
    Observable<Response<GetAllvTakhfifHajmiSatrResult>>getAllvTakhfifHajmiSatr();

    @GET("Api/apitablet/GetAllTizerTablighat")
    Observable<Response<GetTizeriResult>>getAllTizer();

    /**
     * @deprecated
     * @param ccMarkazForosh
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifNaghdyByccMarkazForosh")
    Observable<Response<GetAllvTakhfifNaghdyByccMarkazForoshResult>>getAllvTakhfifNaghdyByccMarkazForosh(@Query("ccMarkazForosh") String ccMarkazForosh);

    /**
     * @deprecated
     * @param ccMarkazForosh
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifNaghdyByccMarkazForoshPakhsh")
    Observable<Response<GetAllvTakhfifNaghdyByccMarkazForoshResult>>getAllvTakhfifNaghdyByccMarkazForoshForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForosh);

    /**
     * @deprecated
     * @param ccMarkazForosh
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifSenfiByccMarkazForosh")
    Observable<Response<GetAllvTakhfifSenfiByccMarkazForoshResult>>getAllvTakhfifSenfiByccMarkazForosh(@Query("ccMarkazForosh") String ccMarkazForosh);

    /**
     * @deprecated
     * @param ccMarkazForosh
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifSenfiByccMarkazForoshPakhsh")
    Observable<Response<GetAllvTakhfifSenfiByccMarkazForoshResult>>getAllvTakhfifSenfiByccMarkazForoshForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForosh);

    /**
     * @deprecated
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifSenfiSatr")
    Observable<Response<GetAllvTakhfifSenfiSatrResult>>getAllvTakhfifSenfiSatr();


    @GET("Api/ApiGlobal/GetAllTaghvimTatilByccMarkaz")
    Observable<Response<GetAllTaghvimTatilByccMarkazResult>>getAllTaghvimTatilByccMarkaz(@Query("ccMarkaz") String ccMarkaz);

    @GET("Api/ApiTablet/GetAllTaghiratVersion")
    Observable<Response<GetAllTaghiratVersionPPCResult>>getAllTaghiratVersionPPC(@Query("Noe") String Noe , @Query("Version") String version);

    @GET("Api/ApiWarehouse/GetTafkikJoze_Pakhsh")
    Observable<Response<GetTafkikJozePakhshResult>>getTafkikJozePakhsh(@Query("ccDarkhastFaktors") String ccDarkhastFaktors);

    @GET("Api/ApiTablet/GetAllTizerTablighat")
    Observable<Response<GetAllTizerTablighatResult>>getAllTizerTablighat();

    @GET("Api/ApiAmargar/GetAllNoeTablighat")
    Observable<Response<GetAllNoeTablighatResult>>getAllNoeTablighat();


    @GET("API/apisales/GetKharidMoshtarian3Month")
    Observable<Response<GetRtpThreeMonthPurchaseResponse>>getRtpThreeMonthPurchaseResult(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("API/apisales/GetDarkhastFaktorKalaPishnahadi")
    Observable<Response<DarkhastFaktorKalaPishnahadiResult>>getDarkhastFaktorKalaPishnahadiResult(@Query("ccForoshandeh") String ccForoshandeh , @Query("ccmoshtarys") String ccmoshtarys);







}
