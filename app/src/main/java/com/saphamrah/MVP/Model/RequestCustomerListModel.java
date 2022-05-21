package com.saphamrah.MVP.Model;


import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RequestCustomerListMVP;
import com.saphamrah.DAO.AdamDarkhastDAO;
import com.saphamrah.DAO.AnbarakAfradDAO;
import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.DAO.CustomerAddressDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ElatAdamDarkhastDAO;
import com.saphamrah.DAO.ForoshandehAmoozeshiDeviceNumberDAO;
import com.saphamrah.DAO.ForoshandehEtebarDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KalaMojodiDAO;
import com.saphamrah.DAO.MandehMojodyMashinDAO;
import com.saphamrah.DAO.MasirDAO;
import com.saphamrah.DAO.MasirVaznHajmMashinDAO;
import com.saphamrah.DAO.MaxFaktorMandehDarDAO;
import com.saphamrah.DAO.MojoodiGiriDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryAfradDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.DAO.MoshtaryMorajehShodehRoozDAO;
import com.saphamrah.DAO.MoshtaryPolygonDAO;
import com.saphamrah.DAO.MoshtaryRotbehDAO;
import com.saphamrah.DAO.NoeMoshtaryRialKharidDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.RptDarkhastFaktorVazeiatPPCDAO;
import com.saphamrah.DAO.RptForoshDAO;
import com.saphamrah.DAO.RptMandehdarDAO;
import com.saphamrah.DAO.RptSanadDAO;
import com.saphamrah.MVP.View.TreasuryListMapActivity;
import com.saphamrah.Model.AdamDarkhastModel;
import com.saphamrah.Model.AnbarakAfradModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.ForoshandehAmoozeshiModel;
import com.saphamrah.Model.ForoshandehEtebarModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MandehMojodyMashinModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.MasirVaznHajmMashinModel;
import com.saphamrah.Model.MaxFaktorMandehDarModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryAfradModel;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryMorajehShodehRoozModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.RptSanadModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.FakeLocation;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Repository.KalaMojodiRepository;
import com.saphamrah.Repository.MandehMojodyMashinRepository;
import com.saphamrah.Shared.GetProgramShared;
import com.saphamrah.Shared.LastOlaviatMoshtaryShared;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.UIModel.CustomerAddressModel;
import com.saphamrah.UIModel.KalaMojodiGiriModel;
import com.saphamrah.UIModel.OlaviatMorajehModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetMandehMojodyMashinResponse;
import com.saphamrah.WebService.ServiceResponse.GetMojodyAnbarResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.mail.internet.ParameterList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class RequestCustomerListModel implements RequestCustomerListMVP.ModelOps {

    private RequestCustomerListMVP.RequiredPresenterOps mPresenter;
    private final static String ACTIVITY_NAME_FOR_LOG = "RequestCustomerListActivity";
    private final static String CLASS_NAME = "RequestCustomerListModel";
    UserTypeShared userTypeShared = new UserTypeShared(BaseApplication.getContext());
    static CompositeDisposable compositeDisposable;

    public RequestCustomerListModel(RequestCustomerListMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
        compositeDisposable = new CompositeDisposable();

    }

    @Override
    public void checkFakeLocation()
    {
        ForoshandehAmoozeshiDeviceNumberDAO foroshandehAmoozeshiDAO = new ForoshandehAmoozeshiDeviceNumberDAO(mPresenter.getAppContext());
        ArrayList<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModelList = foroshandehAmoozeshiDAO.getAll();

        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        String IMEI = deviceInfo.getIMEI(mPresenter.getAppContext());

        PubFunc.UserType userType = new PubFunc().new UserType();
        String fakeIMEI = userType.checkUserType(mPresenter.getAppContext() , foroshandehAmoozeshiModelList , IMEI);

        if (fakeIMEI.trim().equals(""))
        {
            //this is a real user
            FakeLocation fakeLocation = new FakeLocation();
            if (fakeLocation.useFakeLocation(mPresenter.getAppContext()))
            {
                mPresenter.onErrorUseFakeLocation();
            }
        }
    }

    @Override
    public void getDateOfGetProgram()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());
        GetProgramShared shared = new GetProgramShared(mPresenter.getAppContext());
        String persianGetProgramdate = shared.getString(shared.PERSIAN_DATE_OF_GET_PROGRAM() , "");
        String gregorianGetProgramDate = shared.getString(shared.GREGORIAN_DATE_OF_GET_PROGRAM() , "");
        String today = sdf.format(new Date());
        try
        {
            Date todayDate = sdf.parse(today);
            Date dtGregorianGetProgramDate = sdf.parse(gregorianGetProgramDate);
            //---------------------- For Test -----------------

            int isTest=  userTypeShared.getInt(userTypeShared.USER_TYPE() , 0);
            Log.d("date" , "istest : " + isTest);
            if (isTest == 1)
            {
                mPresenter.onGetDateOfGetProgram(persianGetProgramdate);
                return;
            }

            long diff = todayDate.getTime() - dtGregorianGetProgramDate.getTime();
            if (diff <= 0)
            {
                mPresenter.onGetDateOfGetProgram(persianGetProgramdate);
            }
            else
            {
                mPresenter.onErrorNeedGetProgram();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.onErrorNeedGetProgram();
        }
    }

    @Override
    public void getCustomers()
    {


        updateEtebarForoshandeh();

        final ArrayList<MoshtaryModel> moshtaryModels = new ArrayList<>();
        final ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
        final ArrayList<Integer> arrayListNoeMorajeh = new ArrayList<>();
        final ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels=new ArrayList<>();
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (message.what == 1)
                {
                    ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                    String strCanUpdateCustomer = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CAN_UPDATE_CUSTOMER());
                    boolean canUpdateCustomer = false;
                    if (strCanUpdateCustomer != null && strCanUpdateCustomer.trim().equals("1"))
                    {
                        canUpdateCustomer = true;
                    }
                    mPresenter.onGetCustomers(moshtaryModels , moshtaryAddressModels , arrayListNoeMorajeh ,moshtaryGharardadModels, canUpdateCustomer);
                }
                return false;
            }
        });

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                super.run();

                boolean checkCheckBargashty = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CHECK_MOSHTARY_MASIR_ROOZ_CHECK_BARGASHTY()).equals("1");

                ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                String canInsertFaktorForMoshtaryJadid = "1";
                try
                {
                    canInsertFaktorForMoshtaryJadid = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CAN_INSERT_FAKTOR_MOSHTARY_JADID());
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "getCustomers", "");
                }

                CustomerAddressDAO customerAddressDAO = new CustomerAddressDAO(mPresenter.getAppContext());
                ArrayList<CustomerAddressModel> customerAddressModels = new ArrayList<>();
                Log.i("getCustomersXC", "run: "+canInsertFaktorForMoshtaryJadid.trim());
                if (canInsertFaktorForMoshtaryJadid.trim().equals("1"))
                {
                    customerAddressModels = customerAddressDAO.getByMasir();
                }
                else
                {
                    customerAddressModels = customerAddressDAO.getByMasirWithoutMoshtaryJadid();
                }
                for (int i = 0; i < customerAddressModels.size(); i++) {

                    MoshtaryAddressModel moshtaryAddressModel = customerAddressModels.get(i).getMoshtaryAddressModels().get(0);
                    Log.i("moshtaryAddressModel", "run: latitude:"+moshtaryAddressModel.getLatitude_y() + "longitude:"+moshtaryAddressModel.getLongitude_x());
                    if (moshtaryAddressModel.getLatitude_y() == 0d || moshtaryAddressModel.getLongitude_x() == 0d) {
                        moshtaryAddressModel.setExtraProp_HasLocation(0);
                    } else {
                        moshtaryAddressModel.setExtraProp_HasLocation(1);
                    }
                    //TODO check check bargashty
                    BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
                    MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
                    int ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();
                    int tedadBargashti = bargashtyDAO.getCountByccMoshtaryAndSazmanForosh(customerAddressModels.get(i).getMoshtaryModel().getCcMoshtary(), ccSazmanForosh);
                    int tedadEtebarCheckBargashti = moshtaryEtebarSazmanForoshDAO.getByccMoshtary(customerAddressModels.get(i).getMoshtaryModel().getCcMoshtary()).getTedadBargashty();
                    if(checkCheckBargashty && tedadBargashti > tedadEtebarCheckBargashti)
                        customerAddressModels.get(i).getMoshtaryModel().setHasAdamDarkhastOption(true);
                    moshtaryModels.add(customerAddressModels.get(i).getMoshtaryModel());
                    moshtaryAddressModels.add(customerAddressModels.get(i).getMoshtaryAddressModels().get(0));
                    arrayListNoeMorajeh.add(customerAddressModels.get(i).getMoshtaryMorajehShodehRoozModel().getNoeMorajeh());
                    moshtaryGharardadModels.add(customerAddressModels.get(i).getMoshtaryGharardadModel());

                }
                handler.sendEmptyMessage(1);
            }
        };
        thread.start();
    }

    @Override
    public void checkDuplicateRequestForCustomer(MoshtaryModel moshtaryModel,MoshtaryGharardadModel moshtaryGharardadModel)
    {
        RptDarkhastFaktorVazeiatPPCDAO darkhastFaktorVazeiatPPCDAO = new RptDarkhastFaktorVazeiatPPCDAO(mPresenter.getAppContext());
        int countFaktorForMoshtary = darkhastFaktorVazeiatPPCDAO.getCountFaktorForMoshtary(moshtaryModel.getCcMoshtary());
        int CanMultipleAddRequest = Integer.parseInt(new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_Can_Multiple_Add_Request()));

        if (countFaktorForMoshtary > 0 && CanMultipleAddRequest==0)
        {
            mPresenter.showAlertOneRequestForCustomer();
        }
        else if (countFaktorForMoshtary > 0 && CanMultipleAddRequest==1)
        {
            mPresenter.showAlertDuplicateRequestForCustomer(moshtaryModel,moshtaryGharardadModel);
        }
        else
        {
            checkSelectedCustomer(moshtaryModel.getCcMoshtary(),moshtaryGharardadModel.getCcMoshtaryGharardad(),moshtaryGharardadModel.getCcSazmanForosh());
        }
    }

    @Override
    public void checkSelectedCustomer(final int ccMoshtary,final int ccMoshtareyGharardad ,final int moshtaryGharardadccSazmanForosh)
    {
        Log.d("Check1 requestcustomer",ccMoshtary+"-"+ccMoshtareyGharardad+"-"+moshtaryGharardadccSazmanForosh);
        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();
        AsyncTaskCheckSelectCustomer asyncTaskCheckSelectCustomer = new AsyncTaskCheckSelectCustomer(mPresenter.getAppContext(), ccMoshtary,ccMoshtareyGharardad,moshtaryGharardadccSazmanForosh, locationProvider.getLocation(), new OnCheckSelectedCustomerResponse()
        {
            @Override
            public void onFailed(int resId)
            {
                mPresenter.onErrorSelectCustomer(resId);
            }

            @Override
            public void onWarning(int resId)
            {
                mPresenter.onWarningSelectCustomer(resId);
            }

            @Override
            public void onSuccess()
            {
               // getBottomBarStatus(ccMoshtary,moshtaryGharardadccSazmanForosh);
            }

            @Override
            public void onSuccessUpdateMandehMojodiMashin()
            {
                mPresenter.onSuccessUpdateMandeMojodi();
                getBottomBarStatus(ccMoshtary,moshtaryGharardadccSazmanForosh);
            }

            @Override
            public void onFailedUpdateMandehMojodiMashin()
            {
                mPresenter.onFailedUpdateMandeMojodi();
            }
        });
        asyncTaskCheckSelectCustomer.execute();
    }

    private void getBottomBarStatus(int ccMoshtary,int ccSazmanForoshGharardad)
    {
        PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
        bottomBarConfig.getConfig(mPresenter.getAppContext());
        /*boolean showMojodiGiri = true;
        if (!bottomBarConfig.getShowMojoodiGiri())
        {
            showMojodiGiri = false;
        }
        else //if (bottomBarConfig.getShowMojoodiGiri())
        {
            if (!bottomBarConfig.getMultipleMojoodiGiri() && haveMojoodiGiri)
            {
                showMojodiGiri = false;
            }
        }*/
        mPresenter.onSetRequestInfoShared(ccMoshtary,ccSazmanForoshGharardad, bottomBarConfig.getShowBarkhordAvalie(), bottomBarConfig.getShowMojoodiGiri());
    }

    @Override
    public void updateEtebarMoshtary(final MoshtaryModel moshtaryModel)
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        Log.d("updateEtebarMoshtary","moshtaryModel: " + moshtaryModel);
        getMoshtaryEtebarSazmanForosh(moshtaryModel, foroshandehMamorPakhshModel, noeMasouliat);
    }

    private void getMoshtaryEtebarSazmanForosh(final MoshtaryModel moshtaryModel, final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, final int noeMasouliat)
    {
        final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());

        //TODO alanbodo
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch(serverIpModel.getWebServiceType()){
            case REST:
                moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForosh(mPresenter.getAppContext(), ACTIVITY_NAME_FOR_LOG, String.valueOf(moshtaryModel.getCcMoshtary()), String.valueOf(foroshandehMamorPakhshModel.getCcSazmanForosh()), new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        if (arrayListData.size() > 0)
                        {
                            boolean deleteResult = moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(moshtaryModel.getCcMoshtary());
                            boolean insertResult = moshtaryEtebarSazmanForoshDAO.insert((MoshtaryEtebarSazmanForoshModel) arrayListData.get(0));
                            Log.d("updateEtebarMoshtary","deleteResult: " + deleteResult + " , insertResult: " + insertResult);
                            Log.d("updateEtebarMoshtary","arrayListData: " + arrayListData.toString());
                            if (deleteResult && insertResult)
                            {
                                getRptListBargashty(foroshandehMamorPakhshModel.getCcForoshandeh(), noeMasouliat);
                            }
                            else
                            {
                                mPresenter.onFailedUpdateMoshtaryEtebar();
                            }
                        }
                        else
                        {
                            mPresenter.onFailedUpdateMoshtaryEtebar();
                        }
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, ACTIVITY_NAME_FOR_LOG, "getMoshtaryEtebarSazmanForosh", "onFailed");
                        mPresenter.onFailedUpdateMoshtaryEtebar();
                    }
                });
                break;
            case gRPC:
                moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForoshGrpc(mPresenter.getAppContext(), ACTIVITY_NAME_FOR_LOG, String.valueOf(moshtaryModel.getCcMoshtary()), String.valueOf(foroshandehMamorPakhshModel.getCcSazmanForosh()), new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        if (arrayListData.size() > 0)
                        {
                            boolean deleteResult = moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(moshtaryModel.getCcMoshtary());
                            boolean insertResult = moshtaryEtebarSazmanForoshDAO.insert((MoshtaryEtebarSazmanForoshModel) arrayListData.get(0));
                            Log.d("updateEtebarMoshtary","deleteResult: " + deleteResult + " , insertResult: " + insertResult);
                            Log.d("updateEtebarMoshtary","arrayListData: " + arrayListData.toString());
                            if (deleteResult && insertResult)
                            {
                                getRptListBargashty(foroshandehMamorPakhshModel.getCcForoshandeh(), noeMasouliat);
                            }
                            else
                            {
                                mPresenter.onFailedUpdateMoshtaryEtebar();
                            }
                        }
                        else
                        {
                            mPresenter.onFailedUpdateMoshtaryEtebar();
                        }
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, ACTIVITY_NAME_FOR_LOG, "getMoshtaryEtebarSazmanForosh", "onFailed");
                        mPresenter.onFailedUpdateMoshtaryEtebar();
                    }
                });
                break;


        }

    }

    private void getRptListBargashty(int ccForoshandeh, int noeMasouliat)
    {
        final BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), ACTIVITY_NAME_FOR_LOG, String.valueOf(ccForoshandeh), new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                boolean deleteResult = bargashtyDAO.deleteAll();
                boolean insertResult = bargashtyDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult)
                {
                    mPresenter.onSuccessUpdateMoshtaryEtebar();
                }
                else
                {
                    mPresenter.onFailedUpdateMoshtaryEtebar();
                }
            }
            @Override
            public void onFailed(String type, String error)
            {
                setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, ACTIVITY_NAME_FOR_LOG, "getRptListBargashty", "onFailed");
                mPresenter.onFailedUpdateMoshtaryEtebar();
            }
        });
    }


    public void updateEtebarForoshandeh()
    {
        {
            String getEtebarForoshandehOnline = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_GET_ETEBAR_FOROSHANDEH_ONLINE);
            if (getEtebarForoshandehOnline != null && getEtebarForoshandehOnline.trim().equals("1"))
            {
                final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
                Log.d("updateEtebarForoshandeh","foroshandehMamorPakhshModel:" + foroshandehMamorPakhshModel.toString());
                if (foroshandehMamorPakhshModel != null)
                {
                    final ForoshandehEtebarDAO etebarDAO = new ForoshandehEtebarDAO(mPresenter.getAppContext());
                    AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
                    String ccAnbarak = "-1";
                    int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                    if(noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 5)
                    {
                        ArrayList<AnbarakAfradModel> anbarakAfradModels = anbarakAfradDAO.getAll();
                        if (anbarakAfradModels.size() > 0)
                        {
                            ccAnbarak = String.valueOf(anbarakAfradModels.get(0).getCcAnbarak());
                            Log.d("updateEtebarForoshandeh","ccAnbarak:" + ccAnbarak);
                        }
                    }
                    else if(noeMasouliat == 1 || noeMasouliat == 4 || noeMasouliat == 6 || noeMasouliat == 8)
                    {
                        ccAnbarak = "-1";
                    }
                    //modified
                    ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
                    switch (serverIpModel.getWebServiceType()){
                        case REST:
                            etebarDAO.fetchEtebarForoshandeh(mPresenter.getAppContext(), ACTIVITY_NAME_FOR_LOG, String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
                            {
                                @Override
                                public void onSuccess(final ArrayList arrayListData)
                                {
                                    if (arrayListData.size() > 0)
                                    {
                                        boolean deleteResult = etebarDAO.deleteByccForoshanhde(foroshandehMamorPakhshModel.getCcForoshandeh());

                                        boolean insertResult = etebarDAO.insertGroup(arrayListData);
                                        Log.d("updateEtebarForoshandeh","deleteResult: " + deleteResult + " , insertResult: " + insertResult);
                                        Log.d("updateEtebarForoshandeh","arrayListData: " + arrayListData.toString());
                                        if (!deleteResult && !insertResult)
                                        {
                                            mPresenter.onFailedUpdateForoshandehEtebar();
                                        }
                                    }
                                    else
                                    {
                                        mPresenter.onFailedUpdateForoshandehEtebar();
                                    }
                                }
                                @Override
                                public void onFailed(String type, String error)
                                {
                                    setLogToDB(LogPPCModel.LOG_EXCEPTION, String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, "", "updateEtebarForoshandeh", "");
                                }
                            });
                            break;

                        case gRPC:
                            etebarDAO.fetchEtebarForoshandehGrpc(mPresenter.getAppContext(), ACTIVITY_NAME_FOR_LOG, String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
                            {
                                @Override
                                public void onSuccess(final ArrayList arrayListData)
                                {
                                    if (arrayListData.size() > 0)
                                    {
                                        boolean deleteResult = etebarDAO.deleteByccForoshanhde(foroshandehMamorPakhshModel.getCcForoshandeh());

                                        boolean insertResult = etebarDAO.insertGroup(arrayListData);
                                        Log.d("updateEtebarForoshandeh","deleteResult: " + deleteResult + " , insertResult: " + insertResult);
                                        Log.d("updateEtebarForoshandeh","arrayListData: " + arrayListData.toString());
                                        if (!deleteResult && !insertResult)
                                        {
                                            mPresenter.onFailedUpdateForoshandehEtebar();
                                        }
                                    }
                                    else
                                    {
                                        mPresenter.onFailedUpdateForoshandehEtebar();
                                    }
                                }
                                @Override
                                public void onFailed(String type, String error)
                                {
                                    setLogToDB(LogPPCModel.LOG_EXCEPTION, String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, "", "updateEtebarForoshandeh", "");
                                }
                            });

                            break;
                    }

                }
            }
        }
    }



    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }

    @Override
    public void updateMoshtaryMorajehShodehRooz() {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
        MasirDAO masirDAO = new MasirDAO(BaseApplication.getContext());
        MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(BaseApplication.getContext());
        int ccForoshandeh = foroshandehMamorPakhshDAO.getIsSelect().getCcForoshandeh();
        String ccMasirs = masirDAO.getstrCcMasir();
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());

        switch (serverIpModel.getWebServiceType()){
            case REST:
                moshtaryMorajehShodehRoozDAO.fetchMoshtaryMorajehShodehRooz(BaseApplication.getContext(), "RequestCustomerList", String.valueOf(ccForoshandeh), ccMasirs, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {

                        if (arrayListData.size() > 0)
                        {
                            boolean deleteResult = moshtaryMorajehShodehRoozDAO.deleteAll();
                            boolean insertResult = moshtaryMorajehShodehRoozDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult)
                            {
                                getCustomers();
                                mPresenter.onUpdateMoshtaryMorajehShodehRooz();
                            }
                            else
                            {
                                getCustomers();
                                mPresenter.onFailUpdateMoshtaryMorajehShodehRooz();
                            }
                        }
                        else
                        {
                            getCustomers();
                            mPresenter.onUpdateMoshtaryMorajehShodehRooz();
                        }

                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onFailUpdateMoshtaryMorajehShodehRooz();
                        setLogToDB(LogPPCModel.LOG_EXCEPTION, String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, "", "updateMoshtaryMorajehShodehRooz", "");

                    }
                });
                break;

            case gRPC:
                moshtaryMorajehShodehRoozDAO.fetchMoshtaryMorajehShodehRoozGrpc(BaseApplication.getContext(), "RequestCustomerList", String.valueOf(ccForoshandeh), ccMasirs, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {

                        if (arrayListData.size() > 0)
                        {
                            boolean deleteResult = moshtaryMorajehShodehRoozDAO.deleteAll();
                            boolean insertResult = moshtaryMorajehShodehRoozDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult)
                            {
                                getCustomers();
                                mPresenter.onUpdateMoshtaryMorajehShodehRooz();
                            }
                            else
                            {
                                getCustomers();
                                mPresenter.onFailUpdateMoshtaryMorajehShodehRooz();
                            }
                        }
                        else
                        {
                            getCustomers();
                            mPresenter.onFailUpdateMoshtaryMorajehShodehRooz();
                        }

                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onFailUpdateMoshtaryMorajehShodehRooz();
                        setLogToDB(LogPPCModel.LOG_EXCEPTION, String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, "", "updateMoshtaryMorajehShodehRooz", "");

                    }
                });
                break;

        }


    }

    @Override
    public void sendCustomerLocation(int position, JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        JSONObject jsonObjectFinal = new JSONObject();
        try {
            jsonObjectFinal.put("CustomerLocation",jsonArray);

        }catch (Exception e){

        }
        Log.i("sendCustomerLocation", "jsonObjectFinal: "+jsonObjectFinal.toString());
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
        APIServiceRxjava apiServiceRxjava = new RxHttpRequest().getApiRx(serverIpModel);
        apiServiceRxjava.sendCustomerLocation(jsonObjectFinal.toString())
                .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(), "RequestCustomerListActivity", "sendCustomerLocation", ""))
                .map(sendCustomerLocationResultResponse -> {
                    if (sendCustomerLocationResultResponse.isSuccessful())
                        if (sendCustomerLocationResultResponse.body() != null)
                            if (sendCustomerLocationResultResponse.body().getSuccess())
                                return true;

                    return false;
                })
                .map(send -> {
                    if (send) {

                        int ccMoshtary = Integer.parseInt(jsonObject.get("ccMoshtary").toString());
                        double latitude_y = Double.parseDouble(jsonObject.get("latitude_y").toString());
                        double longitude_x =  Double.parseDouble(jsonObject.get("longitude_x").toString());
                        Log.i("sendCustomerLocation", " ccMoshtary "+ccMoshtary + "latitude_y"+latitude_y+"longitude_x"+longitude_x);
                        return moshtaryAddressDAO.updateMoshtaryAddress(ccMoshtary, longitude_x, latitude_y);
                    } else {
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean updated) {
                        if (updated) {
                            mPresenter.onSuccessUpdateCustomerAddress(position);
                        } else {
                            onError(new Throwable());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onFailedUpdateCustomerAddress();
                        setLogToDB(LogPPCModel.LOG_EXCEPTION, String.format(" type : %1$s \n error : %2$s", e.getCause(), e.getMessage()), CLASS_NAME, "", "sendCustomerLocation", "");

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }

    @Override
    public void getElatAdamDarkhast(int ccMoshtary) {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

        ElatAdamDarkhastDAO elatAdamDarkhastDAO = new ElatAdamDarkhastDAO(mPresenter.getAppContext());
        ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels = elatAdamDarkhastDAO.getElatAdamDarkhast(moshtaryModel.getCodeVazeiat() , moshtaryModel.getCcNoeMoshtary());

        mPresenter.onGetElatAdamDarkhast(moshtaryModel.getCcMoshtary() , elatAdamDarkhastModels);
    }

    @Override
    public void insertAdamDarkhast(int ccMoshtary, Integer ccElatAdamDarkhast, byte[] imageAdamDarkhast, String codeMoshtaryTekrari) {
        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
        /*if (!googleLocationProvider.getHasAccess())
        {
            mPresenter.onErrorAccessToLocation();
        }
        else
        {*/
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        int ccForoshandeh = shared.getInt(shared.getCcForoshandeh() , 0);
        AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(mPresenter.getAppContext());
        AdamDarkhastModel adamDarkhastModel = new AdamDarkhastModel();

        adamDarkhastModel.setCcElatAdamDarkhast(ccElatAdamDarkhast);
        adamDarkhastModel.setCodeMoshtaryTekrari(codeMoshtaryTekrari);
        adamDarkhastModel.setAdamDarkhastImage(imageAdamDarkhast);
        adamDarkhastModel.setCcMoshtary(ccMoshtary);
        adamDarkhastModel.setCcForoshandeh(ccForoshandeh);
        adamDarkhastModel.setDateAdamDarkhast(getCurrentDate());
        adamDarkhastModel.setLongitude((float) googleLocationProvider.getLongitude());
        adamDarkhastModel.setLatitude((float) googleLocationProvider.getLatitude());
        adamDarkhastModel.setIsSentToServer(false);

        if (adamDarkhastDAO.insert(adamDarkhastModel))
        {
            mPresenter.onSuccessInsertAdamDarkhast();
        }
        else
        {
            mPresenter.onFailedInsertAdamDarkhast();
        }
    }
    private Date getCurrentDate()
    {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        String date = sdf.format(new Date());
        try
        {
            currentDate = sdf.parse(date);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MojoodiGiriModel", "", "insertAdamDarkhast", "");
        }
        return currentDate;
    }

    private void updateOlaviat() {
        MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(BaseApplication.getContext());
        OlaviatMorajehModel olaviatMorajehModel = moshtaryMorajehShodehRoozDAO.getOlaviatMorajeh();

        LastOlaviatMoshtaryShared lastOlaviatMoshtaryShared = new LastOlaviatMoshtaryShared(mPresenter.getAppContext());
        lastOlaviatMoshtaryShared.removeAll();

        lastOlaviatMoshtaryShared.putInt(LastOlaviatMoshtaryShared.OLAVIAT, olaviatMorajehModel.getOlaviat());
        lastOlaviatMoshtaryShared.putInt(LastOlaviatMoshtaryShared.CCMOSHTARY, olaviatMorajehModel.getCcMoshtary());
        lastOlaviatMoshtaryShared.putString(LastOlaviatMoshtaryShared.TARIKH, new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
    }

    interface OnCheckSelectedCustomerResponse
    {
        void onFailed(int resId);
        void onWarning(int resId);
        void onSuccess();
        void onSuccessUpdateMandehMojodiMashin();
        void onFailedUpdateMandehMojodiMashin();
    }

    static private class AsyncTaskCheckSelectCustomer extends AsyncTask<Void , Integer , Integer>
    {
        private WeakReference<Context> weakReferenceContext;
        private int ccMoshtary;
        private int ccMoshtaryGharardad;
        private int moshtaryGharardadccSazmanForosh;
        private Location location;
        private OnCheckSelectedCustomerResponse listener;

        AsyncTaskCheckSelectCustomer(Context context , int ccMoshtary,int ccMoshtaryGharardad, int moshtaryGharardadccSazmanForosh, Location location, OnCheckSelectedCustomerResponse listener)
        {
            this.weakReferenceContext = new WeakReference<>(context);
            this.ccMoshtary = ccMoshtary;
            this.ccMoshtaryGharardad=ccMoshtaryGharardad;
            this.moshtaryGharardadccSazmanForosh = moshtaryGharardadccSazmanForosh;
            this.location = location;
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(Void... voids)
        {

            /*
            ***** new object Doa
             */
            DarkhastFaktorDAO darkhastfaktorDAO = new DarkhastFaktorDAO(weakReferenceContext.get());
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(weakReferenceContext.get());
            MoshtaryModel moshtaryModel = new MoshtaryDAO(weakReferenceContext.get()).getByccMoshtary(ccMoshtary);
            MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(weakReferenceContext.get());
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(weakReferenceContext.get());
            ForoshandehEtebarDAO foroshandehEtebarDAO = new ForoshandehEtebarDAO(weakReferenceContext.get());
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(weakReferenceContext.get());
            MoshtaryAfradDAO moshtaryafradDAO = new MoshtaryAfradDAO(weakReferenceContext.get());
            BargashtyDAO bargashtyDAO = new BargashtyDAO(weakReferenceContext.get());
            MoshtaryEtebarSazmanForoshDAO moshtaryetebarsazmanforoshDAO = new MoshtaryEtebarSazmanForoshDAO(weakReferenceContext.get());
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(weakReferenceContext.get());
            AnbarakAfradDAO anbarakafradDAO = new AnbarakAfradDAO(weakReferenceContext.get());

            //updateEtebarForoshandeh();

            darkhastfaktorDAO.deleteAllFaktorTaeedNashode();


            // if value of this variable equal to false, then ignore checks
            boolean checkMojazForDarkhast = true;
            boolean checkEtebarCheckBargashty = true;
            boolean checkMojazForResid = true;
            boolean checkFaktorErsalNashode = true;
            boolean checkCheckBargashty = true;
            boolean checkTimeDarkhast = true;
            boolean checkMasahateMaghaze = true;
            boolean checkAnbarak = true;
            boolean checkMoshtaryForoshande = true;
            boolean checkDistance = true;
			boolean checkMobile = true;
            boolean updateMandeMojodi = true;
            boolean isMorajehShodeh = true;
            boolean isEtebarCheckBargashty = true;
            boolean isEtebarAsnad = true;
            boolean isMojazForResid = true;
            boolean needCheckKharejAzMahal = true;
            boolean moshtaryForoshandehFlag = false;
            /**
             * check Tarikh Masir customer
             */
            boolean checkTarikhMasir = checkTarikhMasir();
            /**
             * check olaviat customer
             */
            boolean checkPriority = checkPriority(moshtaryModel.getOlaviat());


            /**
             *  needCheckKharejAzMahal
             */
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
            boolean isMojazForDarkhast = foroshandehMamorPakhshModel.getIsMojazForSabtDarkhast() == 1;

            try {
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
                Date fromDateKharejAzMahal = sdf.parse(foroshandehMamorPakhshModel.getFromDateKharejAzMahal());
                Date endDateKharejAzMahal = sdf.parse(foroshandehMamorPakhshModel.getEndDateKharejAzMahal());
                Date currentDate = sdf.parse(sdf.format(new Date()));
                long currentDateMiliSecond = currentDate.getTime();
                if (currentDateMiliSecond >= fromDateKharejAzMahal.getTime() && currentDateMiliSecond <= endDateKharejAzMahal.getTime())
                {
                    needCheckKharejAzMahal = false;
                }
            } catch (Exception e){
                e.getMessage();
                setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), CLASS_NAME, "", "AsyncTaskCheckSelectCustomer", "needCheckKharejAzMahal");

            }

            Log.i("selectCustomer" ,"checkTarikhMasir :" + checkTarikhMasir);
            Log.i("selectCustomer" , "needCheckKharejAzMahal :" + needCheckKharejAzMahal);
            Log.i("selectCustomer" , "checkPriority :" + checkPriority);



            /*
               **** MoshtaryMorajehShodeh
             */

            int countMoshtaryMorajeShode = moshtaryMorajehShodehRoozDAO.getCountByccMoshtary(ccMoshtary);
            if(countMoshtaryMorajeShode <= 0)
            {
                isMorajehShodeh=false;
            }



            ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(Constants.REQUEST_CUSTOMER_CCPARAMETER_OF_CHECKS() + "," + Constants.UPDATE_MANDE_MOJODI());
            for (ParameterChildModel model : childParameterModels)
            {
                Log.d("parameter" , model.toString());
                if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_MOJAZ_FOR_DARKHAST())
                {
                    checkMojazForDarkhast = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_ETEBAR_CHECK_BARGASHTY())
                {
                    checkEtebarCheckBargashty = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_MOJAZ_FOR_RESID())
                {
                    checkMojazForResid = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_FAKTOR_ERSAL_NASHODEH())
                {
                    checkFaktorErsalNashode = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_CHECK_BARGASHTY())
                {
                    checkCheckBargashty = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_TIME_DARKHAST())
                {
                    checkTimeDarkhast = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_MASAHAT_MAGHAZEH())
                {
                    checkMasahateMaghaze = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_ANBARAK())
                {
                    checkAnbarak = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_MOSHTARY_FOROSHANDEH())
                {
                    checkMoshtaryForoshande = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_DISTANCE())
                {
                    checkDistance = model.getValue().trim().equals("1");
                }
                else if (model.getCcParameterChild() == Constants.UPDATE_MANDE_MOJODI_MASIR_ROOZ())
                {
                    updateMandeMojodi = model.getValue().trim().equals("1");
                }
            }
			checkMobile = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_REQUIER_MOBILE).trim().equals("1");






            /*
             **** Foroshandeh Etebar
             */
            ForoshandehEtebarModel foroshandehEtebarModel = foroshandehEtebarDAO.getByccForoshandeh(foroshandehMamorPakhshModel.getCcForoshandeh());
            boolean checkEtebarForoshandeh=  moshtaryDAO.getByccMoshtary(ccMoshtary).getControlEtebarForoshandeh()==0?false:true;



            Log.d("EtebarForoshandeh","foroshandehEtebarModel:" + foroshandehEtebarModel.toString());
            Log.d("EtebarForoshandeh","checkEtebarForoshandeh:" + checkEtebarForoshandeh);
            /*
             **** EtebarBargashty
             */
            long rialBargahsty = foroshandehEtebarModel.getRialBargashty();
            int tedadBargahsty = foroshandehEtebarModel.getTedadBargashty();
            long modatBargashty = foroshandehEtebarModel.getModatBargashty();

            /*
              **** EtebarAsnad
             */
            long RialAsnad = foroshandehEtebarModel.getRialAsnad();
            int TedadAsnad = foroshandehEtebarModel.getTedadAsnad();
            //int ModatAsnad = foroshandehEtebarModel.getModatAsnad();

            long etebarRialAsnadForoshandeh = foroshandehEtebarModel.getEtebarRialAsnadMoshtary() + foroshandehEtebarModel.getEtebarRialAsnadShakhsi();
            int etebarTedadAsnadForoshandeh = foroshandehEtebarModel.getEtebarTedadAsnadMoshtary() + foroshandehEtebarModel.getEtebarTedadAsnadShakhsi();
            //int etebarModatAsnadForoshandeh = foroshandehEtebarModel.getEtebarModatAsnadMoshtary() + foroshandehEtebarModel.getEtebarModatAsnadShakhsi();

            /*
              **** EtebarMoavagh
             */
            long rialMoavaghForoshandeh = foroshandehEtebarModel.getRialMoavagh();
            int tedadMoavaghForoshandeh = foroshandehEtebarModel.getTedadMoavagh();
            int modatMoavaghForoshandeh = foroshandehEtebarModel.getModatMoavagh();

            /*
             **** saghf etebar
             */
            long MandehSaghfEtebarRiali = foroshandehEtebarModel.getSaghfEtebarRiali() - (rialBargahsty + RialAsnad + rialMoavaghForoshandeh);
            int MandehSaghfEtebarTedadi = foroshandehEtebarModel.getSaghfEtebarTedadi() - (tedadBargahsty + TedadAsnad + tedadMoavaghForoshandeh);
            long MandehSaghfEtebarModat = foroshandehEtebarModel.getSaghfEtebarModat() - Math.max(modatBargashty , modatMoavaghForoshandeh);
            Log.d("EtebarForoshandeh","saghfEtebarRiali:" +foroshandehEtebarModel.getSaghfEtebarRiali() + "rialBargahsty:"+ rialBargahsty + "RialAsnad:"+RialAsnad + "rialMoavaghForoshandeh:"+rialMoavaghForoshandeh);
            Log.d("EtebarForoshandeh","saghfEtebarTedadi:" +foroshandehEtebarModel.getSaghfEtebarTedadi() + "tedadBargahsty:"+ tedadBargahsty + "TedadAsnad:"+TedadAsnad + "tedadMoavaghForoshandeh:"+tedadMoavaghForoshandeh);
            Log.d("EtebarForoshandeh","SaghfEtebarModat:" +foroshandehEtebarModel.getSaghfEtebarModat() + "Maxmodat:"+ Math.max(modatBargashty , modatMoavaghForoshandeh));
            Log.d("EtebarForoshandeh","MandehSaghfEtebarRiali:"+ MandehSaghfEtebarRiali + " , MandehSaghfEtebarTedadi:"+MandehSaghfEtebarTedadi + " , MandehSaghfEtebarModat:"+MandehSaghfEtebarModat);




            /*
              **** check etebar bargashty
             */
            Log.d("EtebarForoshandeh","rialBargahsty:" + rialBargahsty + " foroshandehEtebarModel.getEtebarRialBargashty():" + foroshandehEtebarModel.getEtebarRialBargashty() + " MandehSaghfEtebarRiali:" + MandehSaghfEtebarRiali);
            if((rialBargahsty >= foroshandehEtebarModel.getEtebarRialBargashty() || MandehSaghfEtebarRiali<=0) && checkEtebarForoshandeh)
            {
                isEtebarCheckBargashty = false;
                Log.d("EtebarForoshandeh","isEtebarCheckBargashty:" + isEtebarCheckBargashty);
            }
            Log.d("EtebarForoshandeh","tedadBargahsty:" + tedadBargahsty + " foroshandehEtebarModel.getEtebarTedadBargashty():" + foroshandehEtebarModel.getEtebarTedadBargashty() + " MandehSaghfEtebarTedadi:" + MandehSaghfEtebarTedadi);
            if((tedadBargahsty >= foroshandehEtebarModel.getEtebarTedadBargashty() || MandehSaghfEtebarTedadi<=0) && checkEtebarForoshandeh)
            {
                isEtebarCheckBargashty = false;
                Log.d("EtebarForoshandeh","isEtebarCheckBargashty:" + isEtebarCheckBargashty);
            }
            Log.d("EtebarForoshandeh","modatBargashty:" + modatBargashty + " foroshandehEtebarModel.getEtebarModatBargashty():" + foroshandehEtebarModel.getEtebarModatBargashty() + " MandehSaghfEtebarModat:" + MandehSaghfEtebarModat);
            if((modatBargashty > foroshandehEtebarModel.getEtebarModatBargashty() || MandehSaghfEtebarModat<0) && checkEtebarForoshandeh)
            {
                isEtebarCheckBargashty = false;
                Log.d("EtebarForoshandeh","isEtebarCheckBargashty:" + isEtebarCheckBargashty);
            }

            /*
              **** check etebar asnad
             */
            Log.d("EtebarForoshandeh","sumRialAsnad:" + RialAsnad + " rialAsnadForoshandeh:" + etebarRialAsnadForoshandeh + " MandehSaghfEtebarRiali:" + MandehSaghfEtebarRiali);
            if((RialAsnad >= etebarRialAsnadForoshandeh || MandehSaghfEtebarRiali<=0) && checkEtebarForoshandeh)
            {
                isEtebarAsnad = false;
                Log.d("EtebarForoshandeh","isEtebarAsnad:" + isEtebarAsnad);
            }
            Log.d("EtebarForoshandeh","sumTedadAsnad:" + TedadAsnad + " tedadAsnadForoshandeh:" + etebarTedadAsnadForoshandeh + " MandehSaghfEtebarTedadi:" + MandehSaghfEtebarTedadi);
            if((TedadAsnad >= etebarTedadAsnadForoshandeh || MandehSaghfEtebarTedadi<=0) && checkEtebarForoshandeh)
            {
                isEtebarAsnad = false;
                Log.d("EtebarForoshandeh","isEtebarAsnad:" + isEtebarAsnad);
            }



            /*
              **** check etebar moavagh
             */
            Log.d("EtebarForoshandeh","rialMoavaghForoshandeh:" + rialMoavaghForoshandeh + " foroshandehEtebarModel.getRialMoavagh():" + foroshandehEtebarModel.getRialMoavagh() + " MandehSaghfEtebarRiali:" + MandehSaghfEtebarRiali);
            if((rialMoavaghForoshandeh >= foroshandehEtebarModel.getEtebarRialMoavagh() || MandehSaghfEtebarRiali<=0) && checkEtebarForoshandeh)
            {
                isMojazForResid = false;
                Log.d("EtebarForoshandeh","isMojazForResid:" + isMojazForResid);
            }

            Log.d("EtebarForoshandeh","tedadMoavaghForoshandeh:" + tedadMoavaghForoshandeh + " foroshandehEtebarModel.getTedadMoavagh():" + foroshandehEtebarModel.getTedadMoavagh() + " MandehSaghfEtebarTedadi:" + MandehSaghfEtebarTedadi);
            if((tedadMoavaghForoshandeh >= foroshandehEtebarModel.getEtebarTedadMoavagh() || MandehSaghfEtebarTedadi<=0) && checkEtebarForoshandeh)
            {
                isMojazForResid = false;
                Log.d("EtebarForoshandeh","isMojazForResid:" + isMojazForResid);
            }

            Log.d("EtebarForoshandeh","modatMoavaghForoshandeh:" + modatMoavaghForoshandeh + " foroshandehEtebarModel.getModatMoavagh():" + foroshandehEtebarModel.getModatMoavagh() + " MandehSaghfEtebarModat:" + MandehSaghfEtebarModat);
            if((modatMoavaghForoshandeh > foroshandehEtebarModel.getEtebarModatMoavagh() || MandehSaghfEtebarModat<0) && checkEtebarForoshandeh)
            {
                isMojazForResid = false;
                Log.d("EtebarForoshandeh","isMojazForResid:" + isMojazForResid);
            }




            ArrayList<MoshtaryAfradModel> moshtaryAfradModels = moshtaryafradDAO.getByccMoshtary(ccMoshtary);
            Log.d("moshtaryAfrad" , "ccMoshtary : " + ccMoshtary + " , moshtaryAfrad size : " + moshtaryAfradModels.size());
            if (moshtaryAfradModels.size() > 0)
            {
                int moshtaryccAfrad =  moshtaryAfradModels.get(0).getCcAfrad();
                if(moshtaryccAfrad == foroshandehMamorPakhshModel.getCcAfrad())
                {
                    moshtaryForoshandehFlag = true;
                    //SelectFaktor.setMoshtaryForoshandehFlag(true);
                }
            }
            else
            {
                return -1;
            }

            Log.d("parameter" , "checkDistance : " + checkDistance);


            int ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();
            int tedadBargashti = bargashtyDAO.getCountByccMoshtaryAndSazmanForosh(ccMoshtary, ccSazmanForosh);
            int tedadEtebarCheckBargashti = moshtaryetebarsazmanforoshDAO.getByccMoshtary(ccMoshtary).getTedadBargashty();
            long rialBargashti = (long) bargashtyDAO.getSumBargashtyByccMoshtary(ccMoshtary);
            long rialEtebarBargashti = moshtaryetebarsazmanforoshDAO.getByccMoshtary(ccMoshtary).getEtebarRialBargashty();

            int countDarkhastFaktorErsalNashodeh = darkhastfaktorDAO.getCountErsalNashode();
            Log.d("customer" , "count ersal nashode : " + countDarkhastFaktorErsalNashodeh);
            if(checkMojazForDarkhast && !isMojazForDarkhast)// & PubFuncs.DeviceInfo_TestBarnameh(context) == false)
            {
                return -2;
            }
            else
            {
                int codeNoeShakhsiatHaghighi = 1;
                try
                {
                    codeNoeShakhsiatHaghighi = Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CODE_NOE_SHAKHSIAT_HAGHIGHI()));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "", "", "", "");
                }

                int ccAnbarakActive = foroshandehMamorPakhshDAO.getAll().get(0).getCcAnbarak();
                ArrayList<AnbarakAfradModel> anbarakAfradModels = anbarakafradDAO.getAll();
				String customerMobile = moshtaryModel.getMobile();
                customerMobile = customerMobile == null ? "" : customerMobile;
                int ccAnbarakFeli = -1;

                if (anbarakAfradModels.size() > 0)
                {
                    ccAnbarakFeli = anbarakAfradModels.get(0).getCcAnbarak();
                }
                else
                {
                    return -11;
                }
				if (checkMobile && moshtaryModel.getCodeNoeShakhsiat() == codeNoeShakhsiatHaghighi && (customerMobile.trim().length() != 11 || !customerMobile.startsWith("09")))
                {
                    return -13;
                }
                Log.d("getCustomer" , "checkCheckBargashty : " + checkCheckBargashty + " , tedadBargashti : " + tedadBargashti + " , tedadEtebarCheckBargashti : " + tedadEtebarCheckBargashti);
                Log.d("getCustomer" , "checkCheckBargashty : " + checkCheckBargashty + " , rialBargashti : " + rialBargashti + " , rialEtebarBargashti : " + rialEtebarBargashti);
                if(checkFaktorErsalNashode && countDarkhastFaktorErsalNashodeh > 0)
                {
                    return -3;
                    //Toast.makeText(context, "به علت عدم ارسال درخواست مشتری شما قادر به ثبت درخواست نمی باشید.\nلطفا تمامی درخواست ها را ارسال نمائید.", Toast.LENGTH_LONG).show();
                }
                else if(checkCheckBargashty && tedadBargashti > tedadEtebarCheckBargashti)
                {
                    return -4;
                    //Toast.makeText(context, "به علت چک برگشتی مشتری شما قادر به ثبت درخواست نمی باشید.\n", Toast.LENGTH_LONG).show();
                }
                else if(checkCheckBargashty &&  rialBargashti > rialEtebarBargashti)
                {
                    return -4;
                    //Toast.makeText(context, "به علت چک برگشتی مشتری شما قادر به ثبت درخواست نمی باشید.\n", Toast.LENGTH_LONG).show();
                }
                else if(checkTimeDarkhast && !checkDateTime(childParameterModels))
                {
                    return -5;
                }
                else if (checkMasahateMaghaze && new MoshtaryDAO(weakReferenceContext.get()).getByccMoshtary(ccMoshtary).getMasahatMaghazeh() == 0)
                {
                    //Toast.makeText(context, "شما قادر به ثبت درخواست نمی باشید.لطفا مساحت مغازه  مشتری را بروزرسانی نمایید.\n", Toast.LENGTH_LONG).show();
                    return -6;
                }
                else if(checkAnbarak && ccAnbarakActive != ccAnbarakFeli)
                {
                    //Toast.makeText(context, "به علت تغییر انبارک شما قادر به ثبت درخواست نمی باشید.لطفا مجددا دریافت برنامه نمایید.\n", Toast.LENGTH_LONG).show();
                    return -7;
                }
//                else if (foroshandehMamorPakhshModel.getCheckOlaviatMoshtary() == 1  && !checkPriority)
//                {
//                    return -14;
//                }
                else
                {
                    int codeNoeVosol = moshtaryDAO.getByccMoshtary(ccMoshtary).getCodeNoeVosolAzMoshtary();
                    int ccChildParameterNoeVosol = -1;
                    if (codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD())
                    {
                        ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_VAJH_NAGHD();
                    }
                    else if (codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK())
                    {
                        ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_CHECK();
                    }
                    else if (codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_RESID())
                    {
                        if(checkMojazForResid)
                        {
                            // etebar foroshandeh
                            if (isMojazForResid)
                            {
                                ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_RESID();
                            }
                            else
                            {
                                ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_CHECK();
                                publishProgress(-1);
                                //Toast.makeText(context, "به علت بسته شدن اعتبار فروشنده، شما تنها قادر به ثبت وصول وجه نقد و چک می باشید.\n", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_CHECK();
                            publishProgress(-1);
                            //Toast.makeText(context, "به علت بسته شدن اعتبار فروشنده، شما تنها قادر به ثبت وصول وجه نقد و چک می باشید.\n", Toast.LENGTH_LONG).show();
                        }
                        //ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_RESID();
                    }

                    /**
                     * help
                     * // 1=> canCreateFaktor , -1=> can'tCreateFaktor , -2=> not in Polygon
                     */
                    int canCreateFaktor = 1;
                    if (checkDistance)
                    {
                        canCreateFaktor = isValidCreateFaktor(ccMoshtary , foroshandehMamorPakhshModel, moshtaryForoshandehFlag, location,needCheckKharejAzMahal , checkPriority);
                    }
                    if (canCreateFaktor == 1)
                    {
                        //updateEtebarForoshandeh();
                        if(noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 4 || noeMasouliat == 5 || noeMasouliat == 6 || noeMasouliat == 8 )//بروزرسانی موجودی انبارک
                        {
                            if (updateMandeMojodi)
                            {
                                updateMandehMojodi(noeMasouliat, String.valueOf(foroshandehMamorPakhshModel.getCcMamorPakhsh()), String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), String.valueOf(foroshandehMamorPakhshModel.getCcAfrad()),String.valueOf(foroshandehMamorPakhshModel.getCcSazmanForosh()));
                            }
                        }
                        if(checkMoshtaryForoshande && moshtaryForoshandehFlag)
                        {
                            ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_MOSHTARY_FOROSHANDE();
                            //Toast.makeText(context, " شما برای مشتری به نام خود تنها قادر به ثبت وصول وجه نقد می باشید.\n", Toast.LENGTH_LONG).show();
                            publishProgress(-2);
                        }
                        if(checkEtebarCheckBargashty && (!isEtebarCheckBargashty))
                        {
                            ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_ETEBAR_CHECK_BARGASHTY();
                            //Toast.makeText(context, "به علت بسته شدن اعتبار فروشنده، شما تنها قادر به ثبت وصول وجه نقد می باشید.\n", Toast.LENGTH_LONG).show();
                            publishProgress(-3);
                        }
                        if (!isEtebarAsnad)
                        {
                            ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_ETEBAR_CHECK_BARGASHTY();
                            //Toast.makeText(context, "به علت بسته شدن اعتبار فروشنده، شما تنها قادر به ثبت وصول وجه نقد می باشید.\n", Toast.LENGTH_LONG).show();
                            publishProgress(-3);
                        }
                        if (setRequestInfoShared(ccMoshtary,ccMoshtaryGharardad, moshtaryGharardadccSazmanForosh, moshtaryForoshandehFlag, isMojazForResid, isEtebarAsnad, isEtebarCheckBargashty, ccChildParameterNoeVosol, location, isMorajehShodeh))
                        {
                            return 1;
                        }
                        else
                        {
                            return -10;
                        }
                    }
                    else if (canCreateFaktor == -1)
                    {
                        return -8;
                    }
                    else if (canCreateFaktor == -2)
                    {
                        return -9;
                    }
                    else if (canCreateFaktor == -3)
                    {
                        return -12;
                    }
                    else if (canCreateFaktor == -4)
                    {
                        return -15;
                    }
                    else if (canCreateFaktor == -5)
                    {
                        return -14;
                    }
                }
            }
            return 1;
        }


        private boolean checkDateTime(ArrayList<ParameterChildModel> childParameterModels)
        {
            String startRestTime = "23:45";
            Date startDate;
            Date endTime;
            Date now = Calendar.getInstance().getTime();
            for (ParameterChildModel model : childParameterModels)
            {
                if (model.getCcParameterChild() == Constants.CHECK_MOSHTARY_MASIR_ROOZ_CC_START_REST_TIME())
                {
                    startRestTime = model.getValue();
                    break;
                }
            }
            try
            {
                String[] timeSection = startRestTime.split(":");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY , Integer.parseInt(timeSection[0]));
                calendar.set(Calendar.MINUTE , Integer.parseInt(timeSection[1]));
                startDate = calendar.getTime();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "checkDateTime", "");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY , 23);
                calendar.set(Calendar.MINUTE , 45);
                startDate = calendar.getTime();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY , 23);
            calendar.set(Calendar.MINUTE , 59);
            calendar.set(Calendar.MILLISECOND , 59);
            endTime = calendar.getTime();

            if (now.after(startDate) && now.before(endTime))
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        private int isValidCreateFaktor(int ccMoshtary , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, boolean moshtaryForoshandehFlag, Location location,boolean needCheckKharejAzMahal, boolean checkPriority)
        {
            ParameterChildDAO parameterChildDAO = new ParameterChildDAO(weakReferenceContext.get());
            MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(weakReferenceContext.get());
            MoshtaryAddressModel moshtaryAddress = moshtaryAddressDAO.getAddressByccMoshtaryAndType(ccMoshtary);
            boolean requireTelephone = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_REQUIER_TELEPHONE).trim().equals("1");
            Log.d("RequestCustomer", moshtaryAddress.toString());
            if (requireTelephone && (moshtaryAddress.getTelephone() == null || moshtaryAddress.getTelephone().trim().equals("")))
            {
                return -3;
            }
            //---------------------- For Test -----------------
            UserTypeShared userTypeShared = new UserTypeShared(weakReferenceContext.get());
            int isTest =  userTypeShared.getInt(userTypeShared.USER_TYPE() , 0);
            Log.d("kharejAzMasir" , "istest : " + isTest);
            if (isTest == 1)
            {
                return 1;
            }

            if (moshtaryForoshandehFlag)
            {
                return 1;
            }

            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(weakReferenceContext.get());
            MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(ccMoshtary);

            //----------- getCanSetFaktorKharejAzMahal ------------------------------------
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(weakReferenceContext.get());
            //String strDate = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_NOT_CHECK_KHAREJ_AZ_MAHAL());
            try
            {


                MoshtaryPolygonDAO moshtaryPolygonDAO = new MoshtaryPolygonDAO(weakReferenceContext.get());
                int CanVisitKharejAzMahal_Polygon = moshtaryPolygonDAO.getCanVisitKharejAzMahal(ccMoshtary);

//                Log.d("kharejAzMahal", "current : " + currentDate + " , from : " + fromDateKharejAzMahal + " , end : " + endDateKharejAzMahal);
//                Log.d("kharejAzMahal", "current time : " + currentDate.getTime() + " , from time : " + fromDateKharejAzMahal.getTime() + " , end time : " + endDateKharejAzMahal.getTime());
                Log.d("kharejAzMahal" , "getExtraProp_IsMoshtaryAmargar : " + moshtary.getExtraProp_IsMoshtaryAmargar());
                Log.d("kharejAzMahal" , "CanVisitKharejAzMahal_Polygon : " + CanVisitKharejAzMahal_Polygon + " , needCheckKharejAzMahal:" + needCheckKharejAzMahal);

                if (foroshandehMamorPakhshModel.getCheckOlaviatMoshtary() == 1  && !checkPriority )//&& !checkTarikhMasir() && needCheckKharejAzMahal)
                {
                    return -5;
                }

                if (needCheckKharejAzMahal && CanVisitKharejAzMahal_Polygon == 0)
                {
                    if (moshtary.getExtraProp_IsMoshtaryAmargar() == 0)
                    {
                        MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(weakReferenceContext.get());
                        int countMoshtaryMorajeShode = moshtaryMorajehShodehRoozDAO.getCountByccMoshtary(ccMoshtary);

                        float[] distance = new float[2];
                        double lngMoshtary = moshtaryAddress.getLongitude_x();
                        double latMoshtary = moshtaryAddress.getLatitude_y();
                        Location.distanceBetween(latMoshtary, lngMoshtary, location.getLatitude(), location.getLongitude(), distance);
                        if (!checkTarikhMasir())
                        {
                            Log.d("Date","checkTarikhMasir:"+checkTarikhMasir());
                            //mPresenter.onErrorSelectCustomer(R.string.errorCantRegisterRequest);
                            return -4;
                        }
                        else
                        {
                            int isMorajehShodeh = 0;
                            if (haveAdamMojoodgiriDarkhast(ccMoshtary, 0, 0, countMoshtaryMorajeShode) || countMoshtaryMorajeShode > 0)
                            {
                                isMorajehShodeh = 1;
                            }
                            int zaribKharejAzMahalMetr = 0;
                            int GPSEnable = 1;

                            try
                            {
                                zaribKharejAzMahalMetr = Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_ZARIB_KHAREJ_AZ_MAHAL_METR));
                                GPSEnable = Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_GPS_ENABLE));
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), CLASS_NAME, "", "isValidCreateFaktor", "");
                            }
                            Log.d("kharejAzMahal" , "countMoshtaryMorajeShode : " + countMoshtaryMorajeShode);
                            Log.d("kharejAzMahal" , "isMorajehShodeh : " + isMorajehShodeh);
                            Log.d("kharejAzMahal" , "IsMoshtaryJadid : " + IsMoshtaryJadid(ccMoshtary));
                            Log.d("kharejAzMahal" , "CanGetDarkhastTelephoni: " + foroshandehMamorPakhshModel.getCanGetDarkhastTelephoni());

                            if(!IsMoshtaryJadid(ccMoshtary)) {
                                if(moshtary.getKharejAzMahal()==0) {
                                    if (isMorajehShodeh == 0 || foroshandehMamorPakhshModel.getCanGetDarkhastTelephoni() == 0) {
                                        if (GPSEnable == 1 /*& SelectFaktor.getccGorohNoeSenf() != ccNoeSenfMoshtary_BedonSenf*/) {
                                            if (distance[0] > zaribKharejAzMahalMetr) {
                                                Log.d("kharejAzMahal", "systemConfig.getZaribKharejAzMahalMetr() : " + zaribKharejAzMahalMetr + " - " + distance[0]);
                                                return -2;
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        //}
                    }
                }
                Log.d("checkPriority", foroshandehMamorPakhshModel.getCheckOlaviatMoshtary() + " - " + checkPriority + " - " + checkTarikhMasir() + " - " + needCheckKharejAzMahal);


                return 1;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "isValidCreateFaktor", "");
                return -1;
            }
        }

        private boolean haveAdamMojoodgiriDarkhast(int ccMoshtary, int ccPorseshnameh, int IsMoshtaryAmargar , int countMoshtaryMorajeShode)
        {
            try
            {
                DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(weakReferenceContext.get());
                int countDarkhastFaktor = darkhastFaktorDAO.getCountByccMoshtaryRooz(ccMoshtary);
                MojoodiGiriDAO mojoodiGiriDAO= new MojoodiGiriDAO(weakReferenceContext.get());
                int countMojodiGiri = mojoodiGiriDAO.getCountByMoshtary(ccMoshtary, true);
                AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(weakReferenceContext.get());
                int countAdamDarkhast = adamDarkhastDAO.getCountByccMoshtary(ccMoshtary);
                if (countDarkhastFaktor == 0 && countMojodiGiri == 0 && countAdamDarkhast == 0  && countMoshtaryMorajeShode == 0)
                {
                    return false;
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "haveAdamMojoodgiriDarkhast", "");
                return false;
            }
            return true;
        }

        private boolean IsMoshtaryJadid(int ccMoshtary)
        {
            SimpleDateFormat sdfDateTime = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            SimpleDateFormat sdfDateWithoutTime = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT());
            final int ConvertToDay = (24 * 60 * 60 * 1000);
            long diffDays = -1;
            Date tarikhMoarefiMoshtary ;
            Date today = new Date();
            try
            {
                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(weakReferenceContext.get());
                MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
                tarikhMoarefiMoshtary = sdfDateTime.parse(moshtaryModel.getTarikhMoarefiMoshtary());
                today = sdfDateWithoutTime.parse(sdfDateWithoutTime.format(today));
                diffDays = (today.getTime() - tarikhMoarefiMoshtary.getTime()) / ConvertToDay;
                Log.d("kharejAzMahal", "IsMoshtaryJadid- diffDays:"+ diffDays + ", tarikhMoarefiMoshtary:" + tarikhMoarefiMoshtary + " , today:" + today.toString());
                if(diffDays>0)
                    return false;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "haveAdamMojoodgiriDarkhast", "");
                return false;
            }
            return true;
        }

        private boolean checkTarikhMasir()
        {
            DateUtils dateUtils = new DateUtils();
            MasirDAO masirDAO = new MasirDAO(weakReferenceContext.get());
            String tarikhMasir = masirDAO.getAll().get(0).getTarikhMasir();
            GetProgramShared shared = new GetProgramShared(weakReferenceContext.get());
//            String lastGetProgramDate = shared.getString(shared.PERSIAN_DATE_OF_GET_PROGRAM() , "");

            String lastGetProgramDate = shared.getString(shared.GREGORIAN_DATE_TIME_OF_GET_CONFIG() , "");



            Log.d("date","lastGetProgramDate:"+lastGetProgramDate + " ,tarikhMasir:" + tarikhMasir);
            if (lastGetProgramDate.equals(""))
            {
                return false;
            }
            else
            {
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
                    SimpleDateFormat sdfGetProgram = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()); //this object used for parse getProgramDate because format of this date is different
                    Date getProgramDate = sdfGetProgram.parse(lastGetProgramDate);
                    Date dateMasir = sdf.parse(tarikhMasir);

                    Log.d("date" , "last program date : " + getProgramDate);
                    Log.d("date" , "masir date : " + dateMasir);

                    long diff = dateMasir.getTime() - getProgramDate.getTime();
                    diff = diff < 0 ? (diff*-1) : diff;
                    long days = diff / 1000 * 60 * 60 * 24;
                    Log.d("date" , "days : " + days);

                    if (days == 0)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "checkTarikhMasir", "");
                    return false;
                }
            }
        }

        private void updateEtebarForoshandeh()
        {
            String getEtebarForoshandehOnline = new ParameterChildDAO(weakReferenceContext.get()).getValueByccChildParameter(Constants.CC_CHILD_GET_ETEBAR_FOROSHANDEH_ONLINE);
            if (getEtebarForoshandehOnline != null && getEtebarForoshandehOnline.trim().equals("1"))
            {
                final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(weakReferenceContext.get()).getIsSelect();
                Log.d("updateEtebarForoshandeh","foroshandehMamorPakhshModel:" + foroshandehMamorPakhshModel.toString());
                if (foroshandehMamorPakhshModel != null)
                {
                    final ForoshandehEtebarDAO etebarDAO = new ForoshandehEtebarDAO(weakReferenceContext.get());
                    AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(weakReferenceContext.get());
                    String ccAnbarak = "-1";
                    int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                    if(noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 5)
                    {
                        ArrayList<AnbarakAfradModel> anbarakAfradModels = anbarakAfradDAO.getAll();
                        if (anbarakAfradModels.size() > 0)
                        {
                            ccAnbarak = String.valueOf(anbarakAfradModels.get(0).getCcAnbarak());
                            Log.d("updateEtebarForoshandeh","ccAnbarak:" + ccAnbarak);
                        }
                    }
                    else if(noeMasouliat == 1 || noeMasouliat == 4 || noeMasouliat == 6 || noeMasouliat == 8)
                    {
                        ccAnbarak = "-1";
                    }
                    //modified
                    ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(weakReferenceContext.get());
                    switch (serverIpModel.getWebServiceType()){
                        case REST:
                            etebarDAO.fetchEtebarForoshandeh(weakReferenceContext.get(), ACTIVITY_NAME_FOR_LOG, String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
                            {
                                @Override
                                public void onSuccess(final ArrayList arrayListData)
                                {
                                    Thread thread = new Thread()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            etebarDAO.deleteByccForoshanhde(foroshandehMamorPakhshModel.getCcForoshandeh());
                                            etebarDAO.insertGroup(arrayListData);
                                            Log.d("updateEtebarForoshandeh","arrayListData:" + arrayListData.toString());

                                        }
                                    };
                                    thread.start();
                                }
                                @Override
                                public void onFailed(String type, String error)
                                {
                                    setLogToDB(LogPPCModel.LOG_EXCEPTION, String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, "", "updateEtebarForoshandeh", "");
                                }
                            });
                            break;
                        case gRPC:
                            etebarDAO.fetchEtebarForoshandehGrpc(weakReferenceContext.get(), ACTIVITY_NAME_FOR_LOG, String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
                            {
                                @Override
                                public void onSuccess(final ArrayList arrayListData)
                                {
                                    Thread thread = new Thread()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            etebarDAO.deleteByccForoshanhde(foroshandehMamorPakhshModel.getCcForoshandeh());
                                            etebarDAO.insertGroup(arrayListData);
                                            Log.d("updateEtebarForoshandeh","arrayListData:" + arrayListData.toString());

                                        }
                                    };
                                    thread.start();
                                }
                                @Override
                                public void onFailed(String type, String error)
                                {
                                    setLogToDB(LogPPCModel.LOG_EXCEPTION, String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, "", "updateEtebarForoshandeh", "");
                                }
                            });
                            break;
                    }
                    //etebarDAO.fetchEtebar(weakReferenceContext.get(), ACTIVITY_NAME_FOR_LOG, String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), ccAnbarak, "1", new RetrofitResponse()

                }
            }
        }

        private void updateEtebarMoshtary(final MoshtaryModel moshtaryModel)
        {
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(weakReferenceContext.get());
            final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            final int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);


            final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(weakReferenceContext.get());
            Log.d("updateEtebarMoshtary","moshtaryModel.getCcMoshtary():" + moshtaryModel.getCcMoshtary() + " , CcSazmanForosh : " + foroshandehMamorPakhshModel.getCcSazmanForosh());
            moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForosh(weakReferenceContext.get(), ACTIVITY_NAME_FOR_LOG, String.valueOf(moshtaryModel.getCcMoshtary()), String.valueOf(foroshandehMamorPakhshModel.getCcSazmanForosh()), new RetrofitResponse()
            {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(moshtaryModel.getCcMoshtary());
                            moshtaryEtebarSazmanForoshDAO.insert((MoshtaryEtebarSazmanForoshModel) arrayListData.get(0));
                            Log.d("updateEtebarMoshtary","arrayListData:" + arrayListData.toString());

                        }
                    };
                    thread.start();
                }
                @Override
                public void onFailed(String type, String error)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), CLASS_NAME, ACTIVITY_NAME_FOR_LOG, "getMoshtaryEtebarSazmanForosh", "onFailed");
                }
            });
        }
        private void updateMandehMojodi(int noeMasouliat , String ccMamorPakhsh , String ccForoshandeh, String ccAfrad, String ccSazmanForosh)
        {
            final int finalCCForoshandeh = Integer.valueOf(ccForoshandeh);
            final int finalCCAfrad = Integer.valueOf(ccAfrad);



            final MandehMojodyMashinDAO mandehMojodyMashinDAO = new MandehMojodyMashinDAO(weakReferenceContext.get());
            String ccAnbarakAfrad = String.valueOf(new AnbarakAfradDAO(weakReferenceContext.get()).getAll().get(0).getCcAnbarak());

            if(noeMasouliat == 1 || noeMasouliat == 6 || noeMasouliat == 8)//1-Foroshandeh-Sard
            {
                ccAnbarakAfrad = "0";
                ccMamorPakhsh = "0";
            }
            else if(noeMasouliat == 2 || noeMasouliat == 3)//2-Foroshandeh-Garm //3-Foroshandeh-Smart
            {
                ccMamorPakhsh = "0";
            }
            else if (noeMasouliat == 4 || noeMasouliat == 5)//4-MamorPakhsh-Sard // 5-MamorPakhsh-Smart
            {
                ccForoshandeh = "0";
            }
            else //6-SarparastForoshandeh 7-Amargar
            {
                ccForoshandeh = "0";
                ccMamorPakhsh = "0";
            }
            String ccKalaCode="-1";

            /**fetch all kala with -1
             * if you want to fetch a specific list of kala append their cckala in a string
             * **/
            String finalCcForoshandeh = ccForoshandeh;
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(weakReferenceContext.get());



           switch (serverIpModel.getWebServiceType()){
               case REST:
                   APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);
                   apiServiceRxjava.getMandehMojodyMashin(ccAnbarakAfrad, ccForoshandeh, ccMamorPakhsh, ccKalaCode, ccSazmanForosh)
                           .compose(RxHttpErrorHandler.parseHttpErrors(CLASS_NAME, "RequestCustomerListActivity", "getAllKalaApis", "getMojodyAnbar"))
                           .subscribeOn(Schedulers.io())
                           .subscribe(new Observer<Response<GetMandehMojodyMashinResponse>>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   compositeDisposable.add(d);
                               }

                               @Override
                               public void onNext(Response<GetMandehMojodyMashinResponse> getMandehMojodyMashinResponseResponse) {
                                   if (getMandehMojodyMashinResponseResponse.body()!=null) {

                                       updateMandehMojodiMashinTable(getMandehMojodyMashinResponseResponse.body().getMandehMojodyMashinModels(), finalCcForoshandeh, ccAfrad);
                                   }
                                   else{
                                       publishProgress(-4);
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.i("MandehMojodiOnline", "onError: ");
                               }

                               @Override
                               public void onComplete() {
                                   Log.i("MandehMojodiOnline", "onComplete: ");

                               }
                           });
                   break;

               case gRPC:
                   String finalCcForoshandeh1 = ccForoshandeh;
                   new MandehMojodyMashinDAO(weakReferenceContext.get()).
                   fetchMandehMojodyMashinGrpc(weakReferenceContext.get(), TreasuryListMapActivity.class.getSimpleName(), ccAnbarakAfrad, ccForoshandeh, ccMamorPakhsh,ccKalaCode,ccSazmanForosh, new RetrofitResponse() {
                       @Override
                       public void onSuccess(ArrayList arrayListData) {
                           updateMandehMojodiMashinTable(arrayListData, finalCcForoshandeh1, ccAfrad);

                       }

                       @Override
                       public void onFailed(String type, String error) {

                       }
                   });
                   break;
           }


        }

        private void updateMandehMojodiMashinTable(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels,String ccForoshandeh,String ccAfrad) {

                MandehMojodyMashinRepository mandehMojodyMashinRepository = new MandehMojodyMashinRepository(weakReferenceContext.get());
                Disposable disposable = mandehMojodyMashinRepository.deleteAll()
                        .subscribe(deleteAll -> {
                            if (deleteAll) {

                                Disposable insertGroup = mandehMojodyMashinRepository.insertGroup(mandehMojodyMashinModels)
                                        .subscribe(new Consumer<Boolean>() {
                                            @Override
                                            public void accept(Boolean insertGroup) throws Exception {
                                                if (insertGroup) {
                                                    updateKalaMojodiTable(mandehMojodyMashinModels, Integer.parseInt(ccForoshandeh), Integer.parseInt(ccAfrad));
                                                } else {
                                                    publishProgress(-4);
                                                }
                                            }
                                        }, throwable -> publishProgress(-4));
                                compositeDisposable.add(insertGroup);
                            }
                        }, throwable -> publishProgress(-4));
                compositeDisposable.add(disposable);



        }

        private void updateKalaMojodiTable(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels,int ccForoshandeh,int ccAfrad) {
            @SuppressLint("SimpleDateFormat") String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(weakReferenceContext.get());
            int checkMojody = foroshandehMamorPakhshDAO.getIsSelect().getCheckMojody();
            ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
            Observable.fromIterable(mandehMojodyMashinModels)
                    .subscribeOn(Schedulers.io())
                    .map(mandehMojodyMashinModel -> {

                                KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();

                                kalaMojodiModel.setCcKalaCode(mandehMojodyMashinModel.getCcKalaCode());
                                kalaMojodiModel.setCcForoshandeh( ccForoshandeh);
                                kalaMojodiModel.setCcDarkhastFaktor(0);
                                kalaMojodiModel.setTarikhDarkhast(currentDate);
                                kalaMojodiModel.setShomarehBach(mandehMojodyMashinModel.getShomarehBach());
                                kalaMojodiModel.setTarikhTolid(mandehMojodyMashinModel.getTarikhTolid());
                                kalaMojodiModel.setTarikhEngheza(mandehMojodyMashinModel.getTarikhEngheza());
                                kalaMojodiModel.setGheymatMasrafKonandeh(mandehMojodyMashinModel.getGheymatMasrafKonandeh());
                                kalaMojodiModel.setGheymatForosh(mandehMojodyMashinModel.getGheymatForosh());
                                kalaMojodiModel.setGheymatKharid(mandehMojodyMashinModel.getGheymatKharid());
                                kalaMojodiModel.setCcTaminKonandeh(mandehMojodyMashinModel.getCcTaminKonandeh());
                                kalaMojodiModel.setZamaneSabt(currentDate);
                                kalaMojodiModel.setIsAdamForosh(mandehMojodyMashinModel.getIsAdamForosh());
                                kalaMojodiModel.setCcAfrad(ccAfrad);
                                if(checkMojody==1)
                                {
                                    kalaMojodiModel.setTedad(mandehMojodyMashinModel.getMojody());
                                    kalaMojodiModel.setMax_Mojody(mandehMojodyMashinModel.getMaxMojody());
                                    kalaMojodiModel.setMax_MojodyByShomarehBach(mandehMojodyMashinModel.getMax_MojodyByShomarehBach());
                                }

                                else
                                {
                                    kalaMojodiModel.setTedad(mandehMojodyMashinModel.getMojody());
                                    //kalaMojodiModel.setTedad(99999999);
                                    kalaMojodiModel.setMax_Mojody(99999999);
                                    kalaMojodiModel.setMax_MojodyByShomarehBach(99999999);
                                }
                                return kalaMojodiModel;
                            }


                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<KalaMojodiModel>() {
                        @Override
                        public void onSubscribe( Disposable d) {
                            compositeDisposable.add(d);

                        }

                        @Override
                        public void onNext( KalaMojodiModel kalaMojodiModel) {
                            kalaMojodiModels.add(kalaMojodiModel);
                        }

                        @Override
                        public void onError( Throwable e) {
                            Log.i("MandehMojodiOnline", "onError: ");
                            publishProgress(-4);
                        }

                        @Override
                        public void onComplete() {
                            Disposable delete = new KalaMojodiRepository(weakReferenceContext.get())
                                    .deleteAll()
                                    .subscribe(deleteAll -> {
                                        if (deleteAll) {
                                            Disposable insertGroup = new KalaMojodiRepository(weakReferenceContext.get())
                                                    .insertGroup(kalaMojodiModels)
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(inserted -> {
                                                        if (inserted) {
                                                            publishProgress(1);
                                                        } else {
                                                            publishProgress(-4);
                                                        }
                                                    }, throwable -> publishProgress(-4));
                                            compositeDisposable.add(insertGroup);
                                        } else {
                                            publishProgress(-4);
                                        }
                                    }, throwable -> publishProgress(-4));
                            compositeDisposable.add(delete);
                        }
                    });

        }

        private boolean checkPriority(int customerPriority)
        {
            //TODO remove comment of this lines
            //return true;
            LastOlaviatMoshtaryShared shared = new LastOlaviatMoshtaryShared(weakReferenceContext.get());
            int lastPriority = shared.getInt(LastOlaviatMoshtaryShared.OLAVIAT , 0);
            int diff = lastPriority - customerPriority;
            Log.d("RequestCustomer", "lastPriority : " + lastPriority + " , customerPriority : " + customerPriority + " , diff : " + diff);
            if (diff >= -1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        private boolean setRequestInfoShared(int ccMoshtary ,int ccMoshtaryGharardad,int moshtaryGharardadccSazmanForosh, boolean moshtaryForoshandehFlag , boolean isMojazForResid, boolean isEtebarAsnad, boolean isEtebarCheckBargashty , int ccChildCodeNoeVosol, Location location, boolean isMorajehShodeh)
        {
            try
            {
                ParameterChildDAO parameterChildDAO = new ParameterChildDAO(BaseApplication.getContext());
                int isKalaOlaviatMablagh =Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_KALA_OLAVIAT_MABLAGH)) ;
                int isKalaOlaviatSabt =Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_KALA_OLAVIAT_SABT));
                Date minTarikhSanadBargashty = null;
                Date tarikh = new Date();
                ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(weakReferenceContext.get());
                ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
                SelectFaktorShared shared = new SelectFaktorShared(weakReferenceContext.get());

                shared.removeAll();
                shared.setDefaultRequestInfo();

                shared.putInt(shared.getCcMoshtary() , ccMoshtary);
                shared.putInt(shared.getCcForoshandeh() , foroshandehMamorPakhshModel.getCcForoshandeh());
                shared.putBoolean(shared.getMoshtaryForoshandehFlag() , moshtaryForoshandehFlag);
                shared.putBoolean(shared.getIsMojazForResid() , isMojazForResid);
                shared.putBoolean(shared.getIsEtebarAsnad() , isEtebarAsnad);
                shared.putBoolean(shared.getIsEtebarCheckBargashty() , isEtebarCheckBargashty);
                shared.putInt(shared.getccChildCodeNoeVosol() , ccChildCodeNoeVosol);
                shared.putInt(shared.getCcMarkazForosh(),foroshandehMamorPakhshModel.getCcMarkazForosh());
                shared.putInt(shared.getCcSazmanForosh(),foroshandehMamorPakhshModel.getCcSazmanForosh());
                shared.putInt(shared.getCcMoshtaryGharardad(),ccMoshtaryGharardad);
                shared.putInt(shared.getMoshtaryGharardadccSazmanForosh(), moshtaryGharardadccSazmanForosh);
                shared.putBoolean(shared.getIsMorajehShodeh(),isMorajehShodeh);
                shared.putInt(shared.getIsKalaOlaviatSabt(),isKalaOlaviatSabt);
                shared.putInt(shared.getIsKalaOlaviatMablagh(),isKalaOlaviatMablagh);
                Log.i("shared.put","ccMoshtaryGharardad:" + ccMoshtaryGharardad + " , moshtaryGharardadccSazmanForosh:" + moshtaryGharardadccSazmanForosh);
                Log.d("shared.put","CcMarkazForosh:" + foroshandehMamorPakhshModel.getCcMarkazForosh());
                Log.d("shared.put","CcSazmanForosh:" + foroshandehMamorPakhshModel.getCcSazmanForosh());

                float latitude = 0.0F;
                float longitude = 0.0F;
                float altitude = 0.0F;
                float accuracy = 0.0F;
                float bearing = 0.0F;
                float speed = 0.0F;
                try
                {
                    latitude = (float) location.getLatitude();
                    longitude = (float) location.getLongitude();
                    altitude = (float) location.getAltitude();
                    accuracy = location.getAccuracy();
                    bearing = location.getBearing();
                    speed = location.getSpeed();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    return false;
                }
                shared.putString(shared.getLatitude(), String.valueOf(latitude));
                shared.putString(shared.getLongitude(), String.valueOf(longitude));
                shared.putString(shared.getAltitude(), String.valueOf(altitude));
                shared.putFloat(shared.getAccurancy(), accuracy);
                shared.putFloat(shared.getBearing(), bearing);
                shared.putFloat(shared.getSpeed(), speed);


                shared.putInt(shared.getCcMarkazSazmanForosh() , foroshandehMamorPakhshModel.getCcMarkazSazmanForosh());
                shared.putString(shared.getSaatVorodBeMaghazeh() , new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
                shared.putString(shared.getSaatKhorojAzMaghazeh() , new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));

                //----------------------------- CodeMely -------------------
                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(weakReferenceContext.get());
                MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(ccMoshtary);
                shared.putString(shared.getCodeMely(), moshtary.getCodeMely());
                shared.putString(shared.getShenasehMely(), moshtary.getShenasehMely());
                shared.putInt(shared.getCcMoshtaryParent(), moshtary.getccMoshtaryParent());
                shared.putInt(shared.getMoshtaryDarajeh(), moshtary.getDarajeh());
                //CodePosty = moshtary.getCodePosty();


                //------------------------------------- MojoodiGiri..-------------------------------------
                MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(weakReferenceContext.get());
                int count = mojoodiGiriDAO.getCountMojodiGiriByMoshtaryForCheck(ccMoshtary, true);
                boolean haveMojoodiGiri = count != 0;
                shared.putBoolean(shared.getHaveMojoodiGiri(), haveMojoodiGiri);

                //------------------------------------- MoshtaryGoroh..-------------------------------------
                shared.putInt(shared.getCcGorohNoeMoshtary(), moshtary.getCcNoeMoshtary());
                shared.putInt(shared.getCcGorohNoeSenf(), moshtary.getCcNoeSenf());

                //SystemConfigDAO systemConfigDAO = new SystemConfigDAO(weakReferenceContext.get());
                //SystemConfigModel systemConfigModel = systemConfigDAO.getAll().get(0);
                NoeMoshtaryRialKharidDAO moshtaryRialKharidDAO = new NoeMoshtaryRialKharidDAO(weakReferenceContext.get());

                long hadaghalMablaghKharid = moshtaryRialKharidDAO.getMablaghByccMoshtary(moshtary.getCcNoeMoshtary() , moshtary.getDarajeh());
                long hadaghalTedadKharid = moshtaryRialKharidDAO.getTedadByccMoshtary(moshtary.getCcNoeMoshtary() , moshtary.getDarajeh());
                long hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid = 0;
                long mablaghMaxFaktorKhordehMoshtaryJadid = 0;
                long mablaghMaxFaktorOmdehMoshtaryJadid = 0;
                int minModatHozorDarMaghazeh = 0;

                String ccChildParameters = Constants.CC_CHILD_HADAGHAL_MABLAGH_KHARID_MOSHTARY_JADID + "," + Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_KHORDEH_MOSHTARY_JADID + "," + Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_OMDEH_MOSHTARY_JADID + "," + Constants.CC_CHILD_Min_Zaman_Hozor_Dar_Maghazeh;
                ArrayList<ParameterChildModel> parameterChildModels = parameterChildDAO.getAllByccChildParameter(ccChildParameters);
                for (ParameterChildModel parameterChild : parameterChildModels)
                {
                    if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_HADAGHAL_MABLAGH_KHARID_MOSHTARY_JADID)
                    {
                        hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid = Long.parseLong(parameterChild.getValue());
                    }
                    else if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_KHORDEH_MOSHTARY_JADID)
                    {
                        mablaghMaxFaktorKhordehMoshtaryJadid = Long.parseLong(parameterChild.getValue());
                    }
                    else if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_OMDEH_MOSHTARY_JADID)
                    {
                        mablaghMaxFaktorOmdehMoshtaryJadid = Long.parseLong(parameterChild.getValue());
                    }
                    else if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_Min_Zaman_Hozor_Dar_Maghazeh)
                    {
                        minModatHozorDarMaghazeh=Integer.parseInt(parameterChild.getValue());
                    }
                }
                shared.putFloat(shared.getHadeAghalMablaghKharid() , hadaghalMablaghKharid);
                shared.putFloat(shared.getHadeAghalTedadKharid() , hadaghalTedadKharid);
                shared.putLong(shared.getHadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid() , hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid);
                shared.putLong(shared.getMablaghMaxFaktorKhordeh_MoshtaryJadid() , mablaghMaxFaktorKhordehMoshtaryJadid);
                shared.putLong(shared.getMablaghMaxFaktorOmdeh_MoshtaryJadid() , mablaghMaxFaktorOmdehMoshtaryJadid);
                shared.putInt(shared.getMinModatHozor() , minModatHozorDarMaghazeh);
                Log.d("RequestCustomerList","hadaghalMablaghKharid:"+hadaghalMablaghKharid+" , hadaghalTedadKharid:"+hadaghalTedadKharid+" , hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid:"+hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid+
                        ", mablaghMaxFaktorKhordehMoshtaryJadid:"+mablaghMaxFaktorKhordehMoshtaryJadid  + " , mablaghMaxFaktorOmdehMoshtaryJadid:"+mablaghMaxFaktorOmdehMoshtaryJadid + " , minModatHozorDarMaghazeh" + minModatHozorDarMaghazeh);

                //-------------------MoshtaryEtebarSzmanForosh.. ------------------
                MoshtaryEtebarSazmanForoshDAO moshtaryetebarsazmanforoshDAO = new MoshtaryEtebarSazmanForoshDAO(weakReferenceContext.get());
                MoshtaryEtebarSazmanForoshModel moshtaryetebarsazmanforosh = moshtaryetebarsazmanforoshDAO.getByccMoshtary(ccMoshtary);

                shared.putLong(shared.getRialCheckBargashty_NoeMoshtary() , moshtaryetebarsazmanforosh.getRialBargashty());
                shared.putInt(shared.getTedadCheckBargashty_NoeMoshtary() , moshtaryetebarsazmanforosh.getTedadBargashty());
                shared.putInt(shared.getModatCheckBargashty_NoeMoshtary() , moshtaryetebarsazmanforosh.getModatBargashty());
                //------------------------ HadaghalMablaghKharidMoshtary ----------------------------
                MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(weakReferenceContext.get());
                int darajehMoshtary = moshtaryRotbehDAO.getRotbehByccMoshtaryForMoshtaryJadid(ccMoshtary);
                shared.putInt(shared.getMoshtaryJadidDarajeh() , darajehMoshtary);

                MaxFaktorMandehDarDAO maxFaktorMandehDarDAO= new MaxFaktorMandehDarDAO(weakReferenceContext.get());
                ArrayList<MaxFaktorMandehDarModel> maxFaktorMandehDars = maxFaktorMandehDarDAO.getAll();
                int maxRoozFaktorMandehDarNoeMoshtary = 0;
                for (MaxFaktorMandehDarModel model : maxFaktorMandehDars)
                {
                    if (model.getCcMarkazForosh() == foroshandehMamorPakhshModel.getCcMarkazForosh()  && model.getCcGorohMoshtary() == moshtary.getCcNoeMoshtary())
                    {
                        maxRoozFaktorMandehDarNoeMoshtary = model.getMaxRooz();
                        shared.putInt(shared.getMaxRoozFaktorMandehDar_NoeMoshtary() , maxRoozFaktorMandehDarNoeMoshtary);
                        shared.putInt(shared.getMaxTedadFaktorMandehDar_NoeMoshtary() , model.getMaxTedad());
                        break;
                    }
                }
                //----------------Amar Forosh.. -----------------------
                int mianginForosh = 0;
                RptForoshDAO rptForoshDAO = new RptForoshDAO(weakReferenceContext.get());
                ArrayList<RptForoshModel> rptForoshModels = rptForoshDAO.getAll();
                int ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
                for (RptForoshModel model : rptForoshModels)
                {
                    if (model.getCcForoshandeh() == ccForoshandeh)
                    {
                        mianginForosh = Math.round(model.getMianginForoshRoozMojaz());
                        shared.putInt(shared.getMianginForosh() , mianginForosh);
                        break;
                    }
                }

                //----------------------- Bargashty.. ----------------------------
                BargashtyDAO bargashtyDAO= new BargashtyDAO(weakReferenceContext.get());
                ArrayList<BargashtyModel> bargashtyModels = bargashtyDAO.getAll();
                float sumBargashty = bargashtyDAO.getSumBargashtyByccMoshtary(ccMoshtary);
                int tedadChekBargashtyMoshtary = 0;
                int modatBargashtyMoshtary = bargashtyDAO.getModatBargashtyByccMoshtary(ccMoshtary);
                double bargashtyMoshtary = 0;
                int tedadChekBargashtyForoshandeh = 0;
                double bargashtyForoshandeh = 0;
                //BargashtyMoshtary......
                for (BargashtyModel model : bargashtyModels)
                {
                    if (model.getCcMoshtary() == ccMoshtary)
                    {
                        tedadChekBargashtyMoshtary += 1;
                        bargashtyMoshtary += model.getMablaghMandeh();
                    }
                }
                //BargashtyForoshandeh......
                long dateDiff = 0;
                ArrayList<BargashtyModel> bargashtyForoshnadehs = bargashtyDAO.getBargashtyForoshandehBishtarAz5Rooz(ccForoshandeh);
                for (BargashtyModel model : bargashtyForoshnadehs)
                {
                    tedadChekBargashtyForoshandeh += 1;
                    bargashtyForoshandeh += model.getMablaghMandeh();
                    Date dateTarikhSanad = new Date();
                    try
                    {
                        dateTarikhSanad = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).parse(model.getTarikhSanad());
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "setRequestInfoShared", "");
                    }

                    dateDiff = (minTarikhSanadBargashty == null ? 0 : new PubFunc().new DateUtils().getDateDiffAsDay(dateTarikhSanad , minTarikhSanadBargashty));
                    if (minTarikhSanadBargashty == null || dateDiff > 0)
                    {
                        minTarikhSanadBargashty = dateTarikhSanad;
                    }
                }
                dateDiff = (minTarikhSanadBargashty== null ? 0 : new PubFunc().new DateUtils().getDateDiffAsDay(minTarikhSanadBargashty, new Date()));
                if (minTarikhSanadBargashty!= null)
                {
                    shared.putInt(shared.getModatCheckBargashtyForoshandeh() , (int) dateDiff);
                }
                shared.putInt(shared.getTedadChekBargashtyMoshtary() ,tedadChekBargashtyMoshtary );
                shared.putInt(shared.getTedadChekBargashtyForoshandeh() , tedadChekBargashtyForoshandeh);
                shared.putString(shared.getBargashtyMoshtary() , String.valueOf(bargashtyMoshtary));
                shared.putString(shared.getBargashtyForoshandeh() , String.valueOf(bargashtyForoshandeh));
                //------------------------------ MandehDar..-----------------------------
                RptMandehdarDAO rptMandehdarDAO = new RptMandehdarDAO(weakReferenceContext.get());
                ArrayList<RptMandehdarModel> rptMandehdarModels = rptMandehdarDAO.getAll();
                double moavaghForoshandeh = 0;
                int tedadFaktorMandehDarNoeMoshtary_BishAzRooz= 0;
                for (RptMandehdarModel model : rptMandehdarModels)
                {
                    if (model.getCcForoshandeh() == ccForoshandeh)
                    {
                        moavaghForoshandeh += model.getMablaghMandehMoshtary();
                    }
                    if (model.getTarikhErsal() != null)
                    {
                        Date dateTarikhersal = new Date();
                        try
                        {
                            dateTarikhersal = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).parse(model.getTarikhErsal());
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "setRequestInfoShared", "");
                        }
                        dateDiff = new PubFunc().new DateUtils().getDateDiffAsDay(dateTarikhersal , tarikh);
                        if (dateDiff > maxRoozFaktorMandehDarNoeMoshtary)
                        {
                            tedadFaktorMandehDarNoeMoshtary_BishAzRooz += 1;
                        }
                    }
                }
                shared.putString(shared.getMoavaghForoshandeh() , String.valueOf(moavaghForoshandeh));
                shared.putInt(shared.getTedadFaktorMandehDarNoeMoshtary_BishAzRooz() , tedadFaktorMandehDarNoeMoshtary_BishAzRooz);

                //----------------------------Tedad Resid Vosol Nashodeh------------------------------------
                ParameterChildDAO childParameterDAO = new ParameterChildDAO(weakReferenceContext.get());
                ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByParameterName(Constants.NOE_VOSOL_MOSHTARY());
                int findedCount = 0;
                int codeNoeVosolResid = -1;
                int codeNoeVosolResidNaghd = -1;
                for (ParameterChildModel model : childParameterModels)
                {
                    if (model.getName().trim().equalsIgnoreCase(Constants.NOE_VOSOL_MOSHTARY_RESID()))
                    {
                        codeNoeVosolResid = Integer.parseInt(model.getValue());
                        findedCount++;
                    }
                    else if (model.getName().trim().equalsIgnoreCase(Constants.NOE_VOSOL_MOSHTARY_RESID_NAGHD()))
                    {
                        codeNoeVosolResidNaghd = Integer.parseInt(model.getValue());
                        findedCount++;
                    }
                    if (findedCount == 2)
                    {
                        break;
                    }
                }
                shared.putInt(shared.getTedadResid() , rptMandehdarDAO.getTedadResid(codeNoeVosolResid , codeNoeVosolResid));
                shared.putInt(shared.getTedadResidNaghd() , rptMandehdarDAO.getTedadResid(codeNoeVosolResidNaghd , codeNoeVosolResid));
                //------------------------------------ Asnad.. -----------------------------------
                float sumAsnad = 0;
                int countAsnad = 0;
                int modatAsnad = 0;
                RptSanadDAO rptSanadDAO = new RptSanadDAO(weakReferenceContext.get());
                ArrayList<RptSanadModel> rptSanadModels = rptSanadDAO.getAll();
                double asnadForoshandeh= 0;
                for (RptSanadModel model: rptSanadModels)
                {
                    if (model.getCcForoshandeh() == ccForoshandeh)
                    {
                        asnadForoshandeh += model.getMablaghTakhsis();
                    }
                }
                shared.putString(shared.getAsnadForoshandeh() , String.valueOf(asnadForoshandeh));
                sumAsnad = rptSanadDAO.getSumAsnadByccMoshtary(ccMoshtary);
                countAsnad = rptSanadDAO.getCountAsnadByccMoshtary(ccMoshtary);
                modatAsnad = rptSanadDAO.getModatAsnadByccMoshtary(ccMoshtary);
                double sumForoshandeh= Math.round(moavaghForoshandeh + asnadForoshandeh + bargashtyForoshandeh);
                int roozVagheii= (int) (mianginForosh!=0 ? (sumForoshandeh / mianginForosh) : 0);
                shared.putInt(shared.getRoozVagheii() , roozVagheii);
                //---------------------------------------- MoshtaryEtebarSazmanForosh ----------------------------
                MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(weakReferenceContext.get());
                MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = moshtaryEtebarSazmanForoshDAO.getByccMoshtary(ccMoshtary);
                /*shared.putLong(shared.getEtebarRialy() , moshtaryEtebarSazmanForoshModel.getSaghfEtebarRiali());
                shared.putInt(shared.getEtebarTedady() , moshtaryEtebarSazmanForoshModel.getTedadMoavagh());*/

                //------------------------------------ Bedehy -------------------------------
                float sumMoavagh = 0;

                //-------------------Get_SumMoavaghByccMoshtary...
                //RptMandehdarDAO rpt_MandehDarDao = new Rpt_MandehdarDAO(context);
                sumMoavagh = rptMandehdarDAO.getSumMoavaghByccMoshtary(ccMoshtary);
                int tedadMoavagh = rptMandehdarDAO.getTedadFaktorMandehDar(ccMoshtary);
                int modatMoavagh = rptMandehdarDAO.getMoavaghFaktorMandehDar(ccMoshtary);

                //-------------- GetMablaghFaktorBaz ...
                DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(weakReferenceContext.get());
                double MablaghFaktorBazRoozMoshtary = darkhastFaktorDAO.getMablaghFaktorBaz(ccMoshtary);
                sumMoavagh += MablaghFaktorBazRoozMoshtary ;
                shared.putFloat(shared.getBedehy() , sumMoavagh + sumAsnad + sumBargashty); // bedoone estefade
                shared.putFloat(shared.getEtebarRial() , sumMoavagh);
                shared.putInt(shared.getEtebarTedad() , tedadMoavagh);
                shared.putInt(shared.getEtebarModat() , modatMoavagh);
                shared.putFloat(shared.getEtebarBargashty() , sumBargashty);
                shared.putInt(shared.getEtebarTedadBargashty() , tedadChekBargashtyMoshtary);
                shared.putInt(shared.getEtebarModatBargashty() , modatBargashtyMoshtary);
                shared.putFloat(shared.getEtebarAsnad() , sumAsnad);
                shared.putInt(shared.getEtebarTedadAsnad() , countAsnad);
                shared.putInt(shared.getEtebarModatAsnad() , modatAsnad);
                shared.putFloat(shared.getSumMoavagh() , sumMoavagh);

                Log.d("SelectCust" , "sumMoavagh : " + sumMoavagh);
                Log.d("SelectCust" , "tedadMoavagh : " + tedadMoavagh);
                Log.d("SelectCust" , "modatMoavagh : " + modatMoavagh);
                Log.d("SelectCust" , "sumBargashty : " + sumBargashty);
                Log.d("SelectCust" , "tedadChekBargashtyMoshtary : " + tedadChekBargashtyMoshtary);
                Log.d("SelectCust" , "modatBargashtyMoshtary : " + modatBargashtyMoshtary);
                Log.d("SelectCust" , "sumAsnad : " + sumAsnad);
                Log.d("SelectCust" , "countAsnad : " + countAsnad);
                Log.d("SelectCust" , "modatAsnad : " + modatAsnad);

                //------------------------- Masir_VaznHajmMashin --------------------------------
                MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(weakReferenceContext.get());
                MasirVaznHajmMashinModel masirVaznHajmMashinModel = masirVaznHajmMashinDAO.getByccMoshtary(ccMoshtary);
                shared.putFloat(shared.getVaznMashin() , masirVaznHajmMashinModel.getVaznMashin() == 0 ? 4000 : masirVaznHajmMashinModel.getVaznMashin());
                shared.putFloat(shared.getHajmMashin() , masirVaznHajmMashinModel.getHajmMashin() == 0 ? 19.73f : Float.parseFloat(String.valueOf(masirVaznHajmMashinModel.getHajmMashin())));
                //----------------------------MoshtaryPolygon------------------------------------
            /*MoshtaryPolygonDAO moshtaryPolygonDAO = new MoshtaryPolygonDAO(weakReferenceContext.get());
            CanVisitKharejAzMahal_Polygon = moshtaryPolygonDAO.GetByccMoshtary(SelectFaktor.getccMoshtary());*/


                //------------------------- Masir --------------------------------
                MasirDAO masirDAO = new MasirDAO(weakReferenceContext.get());
                ArrayList<MasirModel> masirModels = masirDAO.getByccMasir(moshtary.getCcMasir());
                if (masirModels.size() > 0)
                {
                    shared.putInt(shared.getCcMasirRooz() , masirModels.get(0).getCcMasir());
                }
                else
                {
                    shared.putInt(shared.getCcMasirRooz() , masirDAO.getAll().get(0).getCcMasir());
                }
                shared.putBoolean(shared.getVerifiedMarjoee() , false);
                shared.putString(shared.getCcKalaCodesOfKalaAsasi(), "");
                shared.putInt(shared.getCcSazmanForosh(),foroshandehMamorPakhshModel.getCcSazmanForosh());
                shared.putInt(shared.getCheckMojody(),foroshandehMamorPakhshModel.getCheckMojody());

                //-----------------
                return true;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "setRequestInfoShared", "");
                return false;
            }
        }


        public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(weakReferenceContext.get(), logType, message, logClass, logActivity, functionParent, functionChild);
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            if (values != null && values.length > 0)
            {
                if (values[0] == -1)
                {
                    //mPresenter.onWarningSelectCustomer(R.string.cantMojazForResid);
                    listener.onWarning(R.string.cantMojazForResid);
                }
                else if (values[0] == -2)
                {
                    //mPresenter.onWarningSelectCustomer(R.string.onlyVajhNaghdForYourCustomer);
                    listener.onWarning(R.string.onlyVajhNaghdForYourCustomer);
                }
                else if (values[0] == -3)
                {
                    //mPresenter.onWarningSelectCustomer(R.string.onlyVajhNaghdForCloseEtebar);
                    listener.onWarning(R.string.onlyVajhNaghdForCloseEtebar);
                }
                else if (values[0] == -4)
                {
                    listener.onFailedUpdateMandehMojodiMashin();
                }
                else if (values[0] == 1)
                {
                    listener.onSuccessUpdateMandehMojodiMashin();
                }
            }
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            if (result == 1)
            {
                listener.onSuccess();
            }
            else if (result == -1)
            {
                //mPresenter.onErrorSelectCustomer(R.string.errorInvalidCustomerInfo);
                listener.onFailed(R.string.errorInvalidCustomerInfo);
            }
            else if (result == -2)
            {
                //mPresenter.onErrorSelectCustomer(R.string.cantMojazForDarkhast);
                listener.onFailed(R.string.cantMojazForDarkhast);
            }
            else if (result == -3)
            {
                //mPresenter.onErrorSelectCustomer(R.string.errorFaktorErsalNashode);
                listener.onFailed(R.string.errorFaktorErsalNashode);
            }
            else if (result == -4)
            {
                //mPresenter.onErrorSelectCustomer(R.string.errorCheckBargashtyMoshtary);
                listener.onFailed(R.string.errorCheckBargashtyMoshtary);
            }
            else if (result == -5)
            {
                //mPresenter.onErrorSelectCustomer(R.string.errorTimeLimitForRequest);
                listener.onFailed(R.string.errorTimeLimitForRequest);
            }
            else if (result == -6)
            {
                //mPresenter.onErrorSelectCustomer(R.string.errorMasahateMaghazeForRequest);
                listener.onFailed(R.string.errorMasahateMaghazeForRequest);
            }
            else if (result == -7)
            {
                //mPresenter.onErrorSelectCustomer(R.string.errorAnbarakForRequest);
                listener.onFailed(R.string.errorAnbarakForRequest);
            }
            else if (result == -8)
            {
                //mPresenter.onErrorSelectCustomer(R.string.errorCantRegisterRequest);
                listener.onFailed(R.string.errorCantRegisterRequest);
            }
            else if (result == -9)
            {
                //mPresenter.onErrorSelectCustomer(R.string.errorLocationForRequest);
                listener.onFailed(R.string.errorLocationForRequest);
            }
            else if (result == -10)
            {
                //mPresenter.onFailedSetRequestInfoShared();
                listener.onFailed(R.string.errorOperation);
            }
            else if (result == -11)
            {
                listener.onFailed(R.string.notFoundAnbar);
            }
            else if (result == -12)
            {
                listener.onFailed(R.string.errorNotFoundCustomerTelephone);
            }
            else if (result == -13)
            {
                listener.onFailed(R.string.errorNotFoundCustomerMobile);
            }
            else if (result == -14)
            {
                listener.onFailed(R.string.errorInvalidPriority);
            }
            else if (result == -15)
            {
                listener.onFailed(R.string.needGetProgram);
            }
        }
    }



}
