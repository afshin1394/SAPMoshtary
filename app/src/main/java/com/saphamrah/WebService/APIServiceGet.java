package com.saphamrah.WebService;


import com.saphamrah.WebService.ServiceResponse.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceGet
{

    //A
    @GET("Api/ApiTablet/GetAnbarakAfrad")
    Call<GetAnbarakAfradResult> getAnbarakAfrad(@Query("ccAfradForoshandehMamorPakhsh") String ccAfradForoshandehMamorPakhsh);

    @GET("Api/ApiAmargar/GetamargarMarkazsazmanForosh")
    Call<GetAmargarMarkazForoshResult> getAmargarMarkazForosh(@Query("ccAmargar") String ccAmargar);

    @GET("Api/ApiAmargar/GetAllAmargarGoroh")
    Call<GetAllAmargarGorohResult> getAllAmargarGoroh();

    @GET("Api/ApiAmargar/GetAllBrand")
    Call<GetAllBrandResult> getAllAmargarBrand();

    @GET("Api/ApiAmargar/GetAllvKala")
    Call<GetMojodyAnbarResult> getAllKalaAmargar();

    @GET("Api/ApiAmargar/GetAllvKalaGoroh")
    Call<GetAllvKalaGorohResult> getAllKalaGorohAmargar();

    @GET("Api/ApiGlobal/GetAllMahalByccMarkazForosh_Amargar")
    Call<GetAllMahalByccMarkazForoshResult> getAllMahalByccMarkazForoshAmargar(@Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiAmargar/GetAllRotbeh")
    Call<GetAllRotbehResult> getAllRotbeh();

    @GET("Api/ApiAmargar/GetElatAdamMoarefiMoshtary")
    Call<GetElatAdamMoarefiMoshtaryResult> getElatAdamMoarefiMoshtary();


    //B
    @GET("Api/ApiSales/GetBarkhordForoshandehBaMoshtary")
    Call<BarkhordForoshandehBaMoshtaryResult> getBarkhordForoshandehBaMoshtary(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMoshtarys") String ccMoshtarys, @Query("TedadMah") String TedadMah);

    @GET("Api/ApiGlobal/GetAllBank")
    Call<GetAllBankResult> getAllBank();

    @GET("Api/ApiWarehouse/GetAllBrand")
    Call<GetAllBrandResult> getAllBrand();

    @GET("Api/ApiSales/Getrpt_ListBargashtyForoshandeh")
    Call<GetListBargashtyForoshandehResult> getListBargashtyForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

	@GET("Api/ApiTablet/getBankPosition")
    Call<GetBankByMamorPakhshPositionResult> getBankPosition(@Query("NoeBank") String NoeBank, @Query("Lat") String lat, @Query("Lng") String lng);

    //C

    @GET("Api/ApiTablet/Control_InsertFaktor")
    Call<ControlInsertFaktorResult> controlInsertFaktor(@Query("UniqID_Tablet") String uniqeIdTablet,
                                                        @Query("ccMoshtary") String ccMoshtary,
                                                        @Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiTreasury/GetConfigMali")
    Call<ConfigMaliResult> getConfigMali(@Query("ccMarkazForosh") String ccMarkazForosh, @Query("ccSazmanForosh") String ccSazmanForosh);



    @GET("Api/apitreasury/GetConfigNoeVosolMojazefaktor")
    Call<GetConfigNoeVosolMojazefaktorResult> getConfigNoeVosolMojazefaktor();

    @GET("Api/apitreasury/GetConfigNoeVosolMojazeMoshtary")
    Call<GetConfigNoeVosolMojazeMoshtaryResult> getConfigNoeVosolMojazeMoshtary(@Query("ccNoeMoshtarys")  String ccNoeMoshtarys);

    //D
    @GET("Api/ApiSales/GetDarkhastFaktor")
    Call<GetDarkhastFaktorResult> getDarkhastFaktor(@Query("ccAfrad") String ccAfrad , @Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetDarkhastFaktor_HavalehSatr")
    Call<GetDarkhastFaktorSatrResult> getDarkhastFaktorSatr(@Query("Noe_Faktor_Havaleh") String noeFaktorHavaleh, @Query("ccDarkhastFaktors") String ccDarkhastFaktors);

    @GET("Api/ApiTreasury/GetDariaftPardakht_HavalehPPC")
    Call<GetDariaftPardakhtHavalePPCResult> getDariaftPardakhtHavalePPC(@Query("Noe_Faktor_Havaleh") String noeFaktorHavale , @Query("ccDarkhastFaktors") String ccDarkhastFaktors);

    @GET("Api/ApiTreasury/GetDariaftPardakhtDarkhastFaktorHavalehPPC")
    Call<GetDariaftPardakhtDarkhastFaktorHavalehPPCResult> getDariaftPardakhtDarkhastFaktorHavalehPPC(@Query("Noe_Faktor_Havaleh") String noeFaktorHavaleh , @Query("ccDarkhastFaktors") String ccDarkhastFaktors);

    @GET("Api/ApiSales/GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate")
    Call<GetAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDateResult> getAllDarkhastFaktorSatrTakhfifByCcForoshandehAndDate(@Query("ccForoshande") String ccForoshande, @Query("fromDate") String fromDate, @Query("toDate") String endDate);

    @GET("Api/ApiSales/GetDarkhastFaktorTakhfif")
    Call<DarkhastFaktorTakhfifResult> getDarkhastFaktorTakhfif(@Query("ccDarkhastHavales") String ccDarkhastHavalehs);
    @GET("Api/ApiSales/GetDarkhastFaktorSatrTakhfif")
    Call<DarkhastFaktorSatrTakhfifResult> getDarkhastFaktorSatrTakhfif(@Query("ccDarkhastHavales") String ccDarkhastHavalehs);
    @GET("Api/ApiSales/GetDarkhastFaktorJayezeh")
    Call<DarkhastFaktorJayezehResult> getDarkhastFaktorjayezeh(@Query("ccDarkhastHavales") String ccDarkhastHavalehs);
    //E
    @GET("Api/ApiSales/GetAllvElatAdamDarkhast")
    Call<GetAllvElatAdamDarkhastResult> getElatAdamDarkhast();

    @GET("Api/ApiWareHouse/GetAllElatMarjoeeKala")
    Call<GetAllElatMarjoeeKalaResult> getElatMarjoeeKala();

    @GET("Api/ApiTablet/GetEtebar")
    Call<GetEtebarResult> getEtebar(@Query("ccForoshandeh") String ccForoshandeh , @Query("ccAnbarak") String ccAnbarak, @Query("flag_updateEtebar") String flag_updateEtebar);

    @GET("Api/ApiSales/GetForoshandehEtebar")
    Call<GetEtebarForoshandehResult> getEtebarForoshandeh(@Query("ccForoshandehs") String ccForoshandehs);

    @GET("Api/ApiSales/GetElamMarjoeeForoshandeh_Pakhsh")
    Call<GetElamMarjoeeForoshandehResult> getElamMarjoeeForoshandeh(@Query("ccDarkhastFaktors") String ccDarkhastFaktors);


    //F
    @GET("Api/ApiSales/GetImageJSON")
    Call<GetPrintFaktorSingleResult> getPrintFaktorImage(@Query("ccDarkhastFaktor") String ccDarkhastFaktor,@Query("ccDarkhastHavaleh") String ccDarkhastHavaleh);


    @GET("Api/ApiSales/GetForoshandehAmoozeshi_DeviceNumber")
    Call<GetForoshandehAmoozeshiResult> getForoshandehAmoozeshi(@Query("DeviceSerialNumber") String DeviceSerialNumber);

    @GET("Api/ApiSales/GetForoshandeh")
    Call<GetAllvForoshandehByccForoshandehResult> getAllvForoshandehByccForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllvForoshandeh")
    Call<GetAllvForoshandehByccForoshandehResult> getAllForoshandeh();

    @GET("Api/ApiTablet/GetAfradByDeviceNumber")
    Call<GetForoshandehMamorPakhshResult> getForoshandehMamorPakhsh(@Query("DeviceSerialNumber") String deviceSerial);

    @GET("Api/ApiSales/GetAllForoshandehMoshtaryByccMasir")
    Call<GetAllForoshandehMoshtaryByccMasirResult> getAllForoshandehMoshtaryByccMasir(@Query("ccMasir") String ccMasir);

    @GET("Api/ApiSales/GetAllrpt_FaktorTozieNashodeh")
    Call<GetAllrptFaktorTozieNashodehResult> getAllrptFaktorTozieNashodeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllrpt_AmarForosh")
    Call<GetAllrptAmarForoshResult> getAllrptAmarForosh(@Query("ccForoshandeh") String ccForoshandeh , @Query("TaTarikh") String TaTarikh);


    //I
    @GET("Api/ApiGlobal/GetImageString")
    Call<GetImageStringResult> getImageString(@Query("ImgName") String ImgName);

    @GET("Api/ApiGlobal/GetImageStringForMamorPakhsh")
    Call<GetImageStringResult> getImageStringForMamorPakhsh(@Query("ImgName") String ImgName);

    @GET("Api/ApiGlobal/GetImageJSON")
    Call<GetImageJsonResult> getImageJSON(@Query("ccDarkhastFaktor") String ccDarkhastFaktor , @Query("ccDarkhastHavaleh") String ccDarkhastHavaleh);

    @GET("Api/ApiGlobal/GetImageKalaJSON")
    Call<GetImageKalaResult> getImageKala(@Query("ccKalaCode") String ccKalaCode);

    //G
    @GET("Api/ApiGlobal/GetLoginInfo")
    Call<GetLoginInfoResult> getLoginInfo();

    @GET("Api/ApiGlobal/GetAllGoroh")
    Call<GetAllGorohResult> getAllGoroh();

    @GET("Api/ApiSales/GetAllGorohKalaNoeSenf")
    Call<GetAllGorohKalaNoeSenfResult> getAllGorohKalaNoeSenf();

    @GET("Api/ApiSales/GetGPSDataByccForoshandeh")
    Call<GetGPSDataResult> getGPSDataByccForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetGPSDataByccMamorPakhsh")
    Call<GetGPSDataResult> getGPSDataByccMamorPakhsh(@Query("ccMamorPakhsh") String ccMamorPakhsh);

	
    @GET("Api/ApiTablet/GetMarjoeeForoshandehByDarkhastFaktor_Titr")
    Call<GetMarjoeeForoshandehByDarkhastFaktorTitrResult> getMarjoeeForoshandehByDarkhastFaktorTitr(@Query("Noe_Titr_Satr") String noeTitrSatr , @Query("ccDarkhastHavaleh") String ccDarkhastHavaleh);

    @GET("Api/ApiTablet/GetMarjoeeForoshandehByDarkhastFaktor_Satr")
    Call<GetMarjoeeForoshandehByDarkhastFaktorSatrResult> getMarjoeeForoshandehByDarkhastFaktorSatr(@Query("Noe_Titr_Satr") String noeTitrSatr , @Query("ccDarkhastHavaleh") String ccDarkhastHavaleh);

    @GET("Api/ApiSales/GetNoeVosolMoshtary")
    Call<NoeVosolMoshtaryResult> getNoeVosolMoshtary(@Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh , @Query("ccNoeMoshtarys") String ccNoeMoshtarys);

    //K
    @GET("Api/ApiSales/GetKalaGheymatForosh")
    Call<KalaGheymatForoshResult> getKalaGheymatForosh(@Query("ccMarkazForosh") String ccMarkazForosh , @Query("ccSazmanForosh") String ccSazmanForosh);

    @GET("Api/ApiTablet/GetKalaOlaviat")
    Call<GetKalaOlaviatResult> getKalaOlaviat(@Query("ccanbarak") String ccAnbarak);

    @GET("Api/ApiWareHouse/GetAllvKalaGoroh")
    Call<GetAllvKalaGorohResult> getAllvKalaGoroh();

    @GET("Api/ApiSales/GetAllvKalaZaribForoshWithMarkazForosh")
    Call<GetAllvKalaZaribForoshResult> getAllvKalaZaribForosh(@Query("ccGorohs") String ccGorohs , @Query("ccMarkazForosh") String ccMarkazForosh);


    @GET("Api/ApiSales/GetKalaZaribForosh")
    Call<GetAllvKalaZaribForoshResult> getKalaZaribForosh(@Query("ccAnbarak") int ccAnbarak , @Query("ccForoshandeh") int ccForoshandeh, @Query("ccMamorPakhsh") int ccMamorPakhsh,@Query("ccGorohs") String ccGorohs);

    @GET("Api/ApiGlobal/GetAllMarkaz_ShahrMarkazi")
    Call<GetAllMarkazShahrMarkaziResult> getAllMarkazShahrMarkazi();

    @GET("Api/apitablet/GetKalaOlaviatGheymat")
    Call<GetKalaOlaviatGheymatResult> getKalaOlaviatGheymat(@Query("ccAnbarak") String ccAnbarak , @Query("ccForoshandeh") int ccForoshandeh, @Query("ccMamorPakhsh") int ccMamorPakhsh, @Query("ccKalaCode") String ccKalaCode);



    //J
    @GET("Api/ApiSales/GetJayezeh")
    Call<GetAllvJayezehByccMarkazForoshResult> getJayezeh(@Query("Noe_Titr_Satr") String noeTiteSatr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccJayezehs") String ccJayezehs);
    @GET("Api/ApiSales/GetAllvJayezehByccMarkazForosh")
    Call<GetAllvJayezehByccMarkazForoshResult> getAllvJayezehByccMarkazForosh(@Query("ccMarkazForosh") String ccMarkazForosh);
    @GET("Api/ApiSales/GetRptJashnvarehForosh")
    Call<GetAllRptJashnvarehResult> getRptJashnvarehForosh(@Query("ccForoshandeh") int ccForoshandeh,@Query("ccMoshtarys") String ccMoshtarys);

    /**
     * @deprecated
     * @param ccMarkazForoshPakhsh
     * @return
     */
    @GET("Api/ApiSales/GetAllvJayezehByccMarkazForoshPakhsh")
    Call<GetAllvJayezehByccMarkazForoshResult> getAllvJayezehByccMarkazForoshForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForoshPakhsh);

    /**
     * @deprecated
     * @return
     */
    @GET("Api/ApiSales/GetAllvJayezehSatr")
    Call<GetAllvJayezehSatrResult> getAllvJayezehSatr();


    @GET("Api/ApiSales/Get_JayezehSatr")
    Call<GetAllvJayezehSatrResult> getJayezehSatr(@Query("Noe_Titr_Satr") String noeTiteSatr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccJayezehs") String ccJayezehs);

    @GET("Api/ApiSales/GetAllvJayezehEntekhabi")
    Call<GetAllvJayezehEntekhabiResult> getAllvJayezehEntekhabi(@Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiSales/GetAllvJayezehEntekhabiPakhsh")
    Call<GetAllvJayezehEntekhabiResult> getAllvJayezehEntekhabiForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForosh);


    //V
    @GET("Version/Version.txt")
    Call<GetVersionResult> getVersionInfo();



    //P
    @GET("Api/ApiGlobal/GetPosShomarehHesab")
    //Call<GetPosShomarehHesabResult> getPosShomarehHesab(@Query("ccPosShomarehHesab") String ccPosShomarehHesab);
    Call<GetPosShomarehHesabResult> getPosShomarehHesab(@Query("ccPosShomarehHesab") String ccPosShomarehHesab , @Query("ccMarkazAnbar") String ccMarkazAnbar);

    @GET("Api/ApiAmargar/GetPorseshnamehTozihat")
    Call<GetPorseshnamehTozihatResult> getPorseshnamehTozihat();

    @GET("Api/ApiMap/GetAllvPolygonForoshSatrByForoshandeh")
    Call<GetAllvPolygonForoshSatrByForoshandehResult> getAllvPolygonForoshSatrByForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiTablet/GetParameter")
    Call<GetParameterResult> getParameter(@Query("Noe_Titr_Satr") String noeTitrSatr , @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccMarkazAnbar") String ccMarkazAnbar, @Query("DateProgram") String DateProgram);

    @GET("Api/ApiTablet/GetParameterChild")
    Call<GetParameterChildResult> getParameterChild(@Query("Noe_Titr_Satr") String noeTitrSatr , @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccMarkazAnbar") String ccMarkazAnbar, @Query("DateProgram") String DateProgram);

    @GET("Api/apisales/GetPrintAndShareFaktor")
    Call<GetPrintFaktorResult> getPrintFaktor(@Query("ccAfrad") int ccAfrad);

    //R
    @GET("reverse")
    Call<LocationToAddressResult> reverseLocationToAddress(@Query("format") String format, @Query("lat") String lat, @Query("lon") String lon, @Query("zoom") String zoom);

    @GET("Api/ApiTablet/GetRpt_MojodiAnbarak")
    Call<GetRptMojodiAnbarakResult> getRptMojodiAnbarak(@Query("ccAnbarak") String ccAnbarak , @Query("ccForoshandeh") String ccForoshandeh , @Query("ccMamorPakhsh") String ccMamorPakhshReq);

    @GET("Api/ApiSales/GetAllrpt_ListAsnadForoshandeh")
    Call<GetAllrptListAsnadForoshandehResult> getAllRptSanad(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllrpt_DarkhastFaktorHavalehVazeiat")
    Call<GetAllrptDarkhastFaktorHavalehVazeiatResult> getAllrptDarkhastFaktorHavalehVazeiat(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMamorPakhsh") String AzTarikh);





    @GET("Api/ApiSales/GetAllrpt_KalaInfo")
    Call<GetAllrptKalaInfoResult> getAllrptKalaInfo(@Query("ccMarkazSazmanSakhtarForosh") String ccMarkazSazmanSakhtarForosh);

    @GET("Api/ApiSales/GetAllrpt_VisitForoshandeh")
    Call<GetAllrptVisitForoshandehResult> getAllrptVisitForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiTreasury/Get_rpt_ListVosol")
    Call<GetrptListVosolResult> getrptListVosol(@Query("ccAfradForoshandehMamorPakhsh") String ccAfradForoshandehMamorPakhsh);

    @GET("Api/ApiSales/GetKharidKalaByccForoshandeh")
    Call<GetKharidKalaByccForoshandehResult> getKharidKalaByccForoshandeh(@Query("Level") String Level , @Query("ccForoshandeh") String ccForoshandeh , @Query("ccKalaGoroh") String ccKalaGoroh);


    @GET("Api/ApiTablet/Getrpt_MarjoeeForoshandeh")
    Call<GetrptMarjoeeForoshandehResult> getrptMarjoeeForoshandeh(@Query("ccAfrad") String ccAfrad);

    @GET("Api/ApiSales/GetAllrpt_HadafForoshTedady")
    Call<GetAllrptHadafeForoshResult> getAllRptHadafForosh(@Query("ccForoshandeh") String ccForoshandeh);

    // S
    @GET("Api/apitablet/getsupportcrisp")
    Call<SupportCrispResult> getSupportCrisp(@Query("ccsazmanforosh") int ccsazmanforosh);

    //M
    @GET("Api/ApiGlobal/GetAllMahalByccMarkazForosh")
    Call<GetAllMahalByccMarkazForoshResult> getAllMahalByccMarkazForosh(@Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh);

    @GET("Api/ApiGlobal/GetAllvMarkaz")
    Call<GetAllvMarkazResult> getAllvMarkaz(@Query("ccMarkaz") String ccMarkaz);

    @GET("Api/ApiSales/GetMoshtaryAmval")
    Call<GetMoshtaryAmvalResult> GetMoshtaryAmval(@Query("ccMoshtary") int ccMoshtary,@Query("ccSazmanForosh") int ccSazmanForosh);

    @GET("Api/ApiGlobal/GetAllvMarkazForosh")
    Call<GetAllvMarkazResult> getAllvMarkazForosh();

    @GET("Api/ApiGlobal/GetAllvMarkazShomarehHesab")
    Call<GetAllvMarkazShomarehHesabResult> getAllvMarkazShomarehHesab(@Query("ccMarkaz") String ccMarkaz);

    @GET("Api/ApiGlobal/Get_ListShomareHesab")
    Call<Get_ListShomareHesab> Get_ListShomareHesab(@Query("ccMarkazAnbar") String ccMarkazAnbar);

    @GET("Api/ApiSales/GetAllMasir")
    Call<GetAllMasirResult> getAllMasir(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMarkazForosh") String ccMarkazForosh, @Query("azTarikh") String azTarikh, @Query("taTarikh") String taTarikh, @Query("codeNoe") String codeNoe);

    @GET("Api/ApiTablet/GetMasirFaalForoshandeh")
    Call<GetAllMasirResult> getAllMasirFaalForoshandeh();

    @GET("Api/ApiSales/GetAllMoshtaryBrand")
    Call<GetAllMoshtaryBrandResult> getAllMoshtaryBrand(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetMair_VaznHajmMashin")
    Call<GetMairVaznHajmMashinResult> getMairVaznHajmMashin(@Query("ccMasir") String ccMasir);

    @GET("Api/ApiSales/GetAllMoshtaryByccMasir")
    Call<GetAllMoshtaryByccMasirResult> getAllMoshtaryByccMasir(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMasirs") String ccMasirs, @Query("CodeMoshtary") String CodeMoshtary);

    @GET("Api/ApiSales/GetMoshtarysFirstParent_PPC")
    Call<GetMoshtarysFirstParentPPCResult> getMoshtaryFirstParentPPC(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiTablet/Get_MoshtaryByRadius")
    Call<GetMoshtaryByRadiusResult> getMoshtaryByRadius(@Query("Radius") String Radius , @Query("Latitude_y") String Latitude_y , @Query("Longitude_x") String Longitude_x);

    @GET("Api/ApiAmargar/GetMoshtaryForAmargar")
    Call<GetMoshtaryByRadiusResult> getListMoshtarianByMasir(@Query("Noe") String noe , @Query("ccMasir") String ccMasir);

    @GET("Api/ApiSales/GetAllvMoshtaryAddress")
    Call<GetAllvMoshtaryAddressResult> getAllvMoshtaryAddress(@Query("ccMoshtarys") String ccMoshtarys, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh);


    @GET("Api/ApiSales/GetMoshtaryAddress")
    Call<GetAllvMoshtaryAddressResult> getMoshtaryAddressByNoeMasouliat(@Query("ccforoshandeh") String ccforoshandeh, @Query("ccMasirs") String ccMasirs, @Query("ccMoshtary") String ccMoshtary);

    @GET("Api/ApiSales/GetAllvMoshtaryAddressPakhsh")
    Call<GetAllvMoshtaryAddressResult> getAllvMoshtaryAddressForPakhsh(@Query("ccMoshtarys") String ccMoshtarys, @Query("ccMarkazSazmanForoshs") String ccMarkazSazmanForoshPakhsh);

    @GET("Api/ApiSales/GetAllvMoshtaryAfrad")
    Call<GetAllvMoshtaryAfradResult> getAllvMoshtaryAfrad(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetMoshtaryEtebarPishfarz")
    Call<GetMoshtaryEtebarPishfarzResult> getMoshtaryEtebarPishfarz(@Query("ccforoshandeh") String ccforoshandeh);

    @GET("Api/ApiSales/GetAllvMoshtaryEtebarSazmanForosh")
    Call<GetAllvMoshtaryEtebarSazmanForoshResult> getAllvMoshtaryEtebarSazmanForosh(@Query("ccMoshtarys") String ccMoshtarys , @Query("ccSazmanForosh") String ccSazmanForosh);

    @GET("Api/ApiSales/GetAllvMoshtaryEtebarSazmanForoshPakhsh")
    Call<GetAllvMoshtaryEtebarSazmanForoshResult> getAllvMoshtaryEtebarSazmanForoshForPakhsh(@Query("ccMoshtarys") String ccMoshtarys , @Query("ccSazmanForoshs") String ccSazmanForoshPakhsh);


    @GET("Api/ApiSales/GetMoshtaryPolygon")
    Call<GetMoshtaryPolygonResult> getMoshtaryPolygon(@Query("ccMasirs") String ccMasirs , @Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetAllvMoshtaryShomarehHesab")
    Call<GetAllvMoshtaryShomarehHesabResult> getAllvMoshtaryShomarehHesab(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetMoshtary_Pakhsh")
    Call<GetMoshtaryPakhshResult> getMoshtaryPakhsh(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiTablet/MoshtaryMorajehShodeh_Rooz")
    Call<MoshtaryMorajehShodehRoozResult> getMoshtaryMorajehShodehRooz(@Query("ccForoshandeh") String ccForoshandeh , @Query("ccMasir") String ccMasir);

    @GET("Api/ApiSales/GetAllvModatVosolByccMarkazForoshGoroh")
    Call<GetAllvModatVosolByccMarkazForoshGorohResult> getAllvModatVosolByccMarkazForoshGoroh(@Query("ccMarkazForosh") String ccMarkazForosh, @Query("ccGorohs") String ccGorohs);

    @GET("Api/ApiSales/GetAllModatVosolGoroh")
    Call<GetAllModatVosolGorohResult> getAllModatVosolGoroh(@Query("ccGorohs") String ccGorohs);

    @GET("Api/ApiSales/GetAllModatVosolMarkazForoshByccMarkazForosh")
    Call<GetAllModatVosolMarkazForoshByccMarkazForoshResult> getAllModatVosolMarkazForoshByccMarkazForosh(@Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiTablet/GetMojodyAnbar")
    Call<GetMojodyAnbarResult> getMojodyAnbar(@Query("ccAfrad") String ccAfrad, @Query("ccMarkazSazmanForoshSakhtarForosh") String ccMarkazSazmanForoshSakhtarForosh);

    @GET("Api/ApiTablet/Get_MandehMojody")
    Call<GetMandehMojodyMashinResult> getMandehMojodyMashin(@Query("ccAnbarak") String ccAnbarak, @Query("ccForoshandeh") String ccForoshandeh, @Query("ccMamorPakhsh") String ccMamorPakhsh);

    @GET("Api/ApiTablet/GetAllMoshtaryChidman")
    Call<GetAllMoshtaryChidmanResult> getAllMoshtaryChidman(@Query("ccMasirs") String ccMasirs);

    @GET("Api/ApiTablet/GetAllMoshtaryForoshandeh")
    Call<GetAllMoshtaryForoshandehResult> getAllMoshtaryForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiTablet/GetAllMoshtaryPishDaryaft")
    Call<GetAllMoshtaryPishdaryaftResult> getAllMoshtaryPishDaryaft(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllrpt_ListMoavaghForoshandeh")
    Call<GetAllrptListMoavaghForoshandehResult> getAllrptListMoavaghForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllrpt_MasirForoshandeh")
    Call<GetAllrptMasirForoshandehResult> getAllrptMasirForoshandeh(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("Api/ApiTablet/Get_ListKalaForMarjoee")
    Call<GetListKalaForMarjoeeResult> getListKalaForMarjoee(@Query("ccForoshandeh") String ccForoshandeh, @Query("ccMoshtary") String ccMoshtary);

    @GET("Api/ApiGlobal/GetAllvMahal_CodePostiByccMarkazForosh")
    Call<GetMahalCodePostiResult> getMahalCodePosti(@Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiTablet/GetMessageBox")
    Call<GetMessageBoxResult> getMessageBoxResult(@Query("ccForoshandeh") String ccForoshandeh , @Query("ccMamorPakhsh") String ccMamorPakhsh);

    @GET("Api/apisales/GetMoshtaryGharardadKala")
    Call<GetAllMoshtaryGharardadKalaResult> getMoshtaryGharardadKala(@Query("ccsazmanForosh") String ccSazmanForosh, @Query("ccMoshtaryGharardad") String ccMoshtaryGharardad);


    @GET("Api/apisales/GetMoshtaryGharardad")
    Call<GetAllMoshtaryGharardadResult> getMoshtaryGharardad(@Query("ccForoshandeh") String ccForoshandeh);


    @GET("Api/apisales/GetMoshtaryGharardad_Pakhsh")
    Call<GetAllMoshtaryGharardadResult> getMoshtaryGharardad(@Query("ccForoshandeh") String ccForoshandeh,@Query("ccMoshtaryGahrardad") int ccMoshtaryGahrardad,@Query("ccSazmanForosh") int ccSazmanForosh);



    @GET("Api/ApiSales/GetMarjoeeMamorPakhsh_Pakhsh")
    Call<MarjoeeMamorPakhshResult> getMarjoeeMamorPakhsh(@Query("ccMoshtarys") String ccMoshtarys);

    //N
    @GET("Api/ApiTablet/GetNoePishnehad")
    Call<NoePishnahadResult> getNoePishnahad();

    @GET("Api/ApiGlobal/GetAllNoeHesab")
    Call<GetAllNoeHesabResult> getAllNoeHesab();

    @GET("Api/ApiSales/GetAllNoeMalekiatMoshtary")
    Call<GetAllNoeMalekiatMoshtaryResult> getAllNoeMalekiatMoshtary();

    @GET("Api/ApiSales/GetAllNoeMoshtaryrialKharidByccMarkazForosh")
    Call<GetAllNoeMoshtaryRialKharidResualt> getAllNoeMoshtaryrialKharid(@Query("ccMarkazSazmanForoshSakhtarForoshs") String ccMarkazSazmanForoshSakhtarForoshs);

    @GET("Api/ApiTablet/GET_NoeFaaliatForMoarefiMoshtaryJadid")
    Call<GetNoeFaaliatForMoarefiMoshtaryJadidResult> getNoeFaaliatForMoarefiMoshtaryJadid(@Query("ccforoshandeh") String ccForoshandeh);

    @GET("Api/ApiSales/GetAllMoshtaryJadidDarkhastPPC")
    Call<GetAllMoshtaryJadidDarkhastPPCResult> getAllMoshtaryJadidDarkhastPPC();

    @GET("Api/apisales/GetMoshtarianKharidNakardeh")
    Call<GetAllMoshtarianKharidNakardeResult> getAllMoshtarianeKharidNakarde(@Query("ccforoshandeh") String ccForoshandeh,@Query("ccMasirs") String ccMasirs);

    //T
    @GET("Api/ApiTablet/GetTedadFaktorMoshtary")
    Call<GetTedadFaktorMoshtaryResult> getTedadFaktorMoshtary(@Query("ccMoshtarys") String ccMoshtarys);

    @GET("Api/ApiSales/GetAllvTakhfifHajmiByccMarkazForosh")
    Call<GetAllvTakhfifHajmiByccMarkazForoshResult> getAllvTakhfifHajmiByccMarkazForosh(@Query("ccMarkazForosh") String ccMarkazForosh);

    @GET("Api/ApiSales/GetAllvTakhfifHajmiByccMarkazForoshPakhsh")
    Call<GetAllvTakhfifHajmiByccMarkazForoshResult> getAllvTakhfifHajmiByccMarkazForoshForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForosh);


    @GET("Api/ApiSales/GetTakhfifHajmi")
    Call<GetAllvTakhfifHajmiByccMarkazForoshResult> getTakhfifHajmiTitr(@Query("Noe_Titr_Satr") String Noe_Titr_Satr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccTakhfifHajmi") String ccTakhfifHajmi);

    @GET("Api/ApiSales/GetTakhfifHajmiSatr")
    Call<GetAllvTakhfifHajmiSatrResult> getTakhfifHajmiSatr(@Query("Noe_Titr_Satr") String Noe_Titr_Satr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccTakhfifHajmi") String ccTakhfifHajmi);

	
	@GET("Api/ApiSales/GetTakhfifSenfi")
    Call<GetAllvTakhfifSenfiByccMarkazForoshResult> getTakhfifSenfiTitr(@Query("Noe_Titr_Satr") String Noe_Titr_Satr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccTakhfifSenfi") String ccTakhfifSenfi);

    @GET("Api/ApiSales/GetTakhfifSenfiSatr")
    Call<GetAllvTakhfifSenfiSatrResult> getTakhfifSenfiSatr(@Query("Noe_Titr_Satr") String Noe_Titr_Satr, @Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh, @Query("ccTakhfifSenfi") String ccTakhfifSenfi);																												  

	@GET("Api/ApiSales/GetTakhfifNaghdy")
    Call<GetAllvTakhfifNaghdyByccMarkazForoshResult> getTakhfifNaghdy(@Query("ccMarkazSazmanForosh") String ccMarkazSazmanForosh);
	
    @GET("Api/ApiSales/GetAllvTakhfifHajmiSatr")
    Call<GetAllvTakhfifHajmiSatrResult> getAllvTakhfifHajmiSatr();

    @GET("Api/apitablet/GetAllTizerTablighat")
    Call<GetTizeriResult> getAllTizer();

    /**
     * @deprecated
     * @param ccMarkazForosh
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifNaghdyByccMarkazForosh")
    Call<GetAllvTakhfifNaghdyByccMarkazForoshResult> getAllvTakhfifNaghdyByccMarkazForosh(@Query("ccMarkazForosh") String ccMarkazForosh);

    /**
     * @deprecated
     * @param ccMarkazForosh
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifNaghdyByccMarkazForoshPakhsh")
    Call<GetAllvTakhfifNaghdyByccMarkazForoshResult> getAllvTakhfifNaghdyByccMarkazForoshForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForosh);

    /**
     * @deprecated
     * @param ccMarkazForosh
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifSenfiByccMarkazForosh")
    Call<GetAllvTakhfifSenfiByccMarkazForoshResult> getAllvTakhfifSenfiByccMarkazForosh(@Query("ccMarkazForosh") String ccMarkazForosh);

    /**
     * @deprecated
     * @param ccMarkazForosh
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifSenfiByccMarkazForoshPakhsh")
    Call<GetAllvTakhfifSenfiByccMarkazForoshResult> getAllvTakhfifSenfiByccMarkazForoshForPakhsh(@Query("ccMarkazForoshs") String ccMarkazForosh);

    /**
     * @deprecated
     * @return
     */
    @GET("Api/ApiSales/GetAllvTakhfifSenfiSatr")
    Call<GetAllvTakhfifSenfiSatrResult> getAllvTakhfifSenfiSatr();


    @GET("Api/ApiGlobal/GetAllTaghvimTatilByccMarkaz")
    Call<GetAllTaghvimTatilByccMarkazResult> getAllTaghvimTatilByccMarkaz(@Query("ccMarkaz") String ccMarkaz);

    @GET("Api/ApiTablet/GetAllTaghiratVersion")
    Call<GetAllTaghiratVersionPPCResult> getAllTaghiratVersionPPC(@Query("Noe") String Noe , @Query("Version") String version);

    @GET("Api/ApiWarehouse/GetTafkikJozePakhsh")
    Call<GetTafkikJozePakhshResult> getTafkikJozePakhsh(@Query("ccDarkhastFaktors") String ccDarkhastFaktors,@Query("ccDarkhastHavalehs") String ccDarkhastHavalehs);

//    @GET("Api/ApiTablet/GetAllTizerTablighat")
//    Call<GetAllTizerTablighatResult> getAllTizerTablighat();

    @GET("Api/ApiAmargar/GetAllNoeTablighat")
    Call<GetAllNoeTablighatResult> getAllNoeTablighat();


    @GET("API/apisales/GetKharidMoshtarian3Month")
    Call<GetRtpThreeMonthPurchaseResult> getRtpThreeMonthPurchaseResult(@Query("ccForoshandeh") String ccForoshandeh);

    @GET("API/apisales/GetDarkhastFaktorKalaPishnahadi")
    Call<DarkhastFaktorKalaPishnahadiResult> getDarkhastFaktorKalaPishnahadiResult(@Query("ccForoshandeh") String ccForoshandeh , @Query("ccmoshtarys") String ccmoshtarys);

    /// V
    @GET("Api/apiTreasury/GetVosolNaghdRooz")
    Call<GetVarizBeBankResult> getVosolNaghdRooz(@Query("ccAfrad") int ccAfrad);

    // U
    @GET("Api/ApiSales/GetElatAdamTahvilDarkhast")
    Call<ElatAdamTahvilDarkhastResult> getElatTahvilDarkhast();

}
