package com.saphamrah.MVP.Model;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.TreasuryListMapMVP;
import com.saphamrah.DAO.AdamDarkhastDAO;
import com.saphamrah.DAO.AnbarakAfradDAO;
import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorEmzaMoshtaryDAO;
import com.saphamrah.DAO.DarkhastFaktorMoshtaryForoshandeDAO;
import com.saphamrah.DAO.DarkhastFaktorRoozSortDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.ForoshandehAmoozeshiDeviceNumberDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.GPSDataPpcDAO;
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
import com.saphamrah.DAO.MoshtaryRotbehDAO;
import com.saphamrah.DAO.NoeMoshtaryRialKharidDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.RptForoshDAO;
import com.saphamrah.DAO.RptMandehdarDAO;
import com.saphamrah.DAO.RptSanadDAO;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorRoozSortModel;
import com.saphamrah.Model.ForoshandehAmoozeshiModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MandehMojodyMashinModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.MasirVaznHajmMashinModel;
import com.saphamrah.Model.MaxFaktorMandehDarModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryAfradModel;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.RptSanadModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Repository.KalaMojodiRepository;
import com.saphamrah.Repository.MandehMojodyMashinRepository;
import com.saphamrah.Shared.GetProgramShared;
import com.saphamrah.Shared.RoutingServerShared;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RoutingUtils;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.Valhalla.OptimizedRouteResult;
import com.saphamrah.Valhalla.SourceToTargetSuccessResult;
import com.saphamrah.Valhalla.SourcesToTargetData;
import com.saphamrah.Valhalla.SourcesToTargetsFailedResult;
import com.saphamrah.Valhalla.Trip;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.APIServiceValhalla;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.GetMandehMojodyMashinResponse;
import com.saphamrah.WebService.ServiceResponse.CreateDariaftPardakhtPPCJSONResult;
import com.saphamrah.WebService.ServiceResponse.CreateGpsDataPPCResult;
import com.saphamrah.WebService.ServiceResponse.GetLoginInfoCallback;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreasuryListMapModel implements TreasuryListMapMVP.ModelOps
{
    private CompositeDisposable compositeDisposable;
    private TreasuryListMapMVP.RequiredPresenterOps mPresenter;
    private String CLASS_NAME = "TreasuryListMapModel";
    SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(BaseApplication.getContext());
    DarkhastFaktorMoshtaryForoshandeDAO darkhastFaktorMoshtaryForoshandeDAO = new DarkhastFaktorMoshtaryForoshandeDAO(BaseApplication.getContext());
    DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());


    public TreasuryListMapModel(TreasuryListMapMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getNoeMasouliat()
    {
        mPresenter.onGetNoeMasouliat(getNoeMasouliatWithReturnData());
    }


    private int getNoeMasouliatWithReturnData()
    {
        int noeMasouliat = -1;
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        if (foroshandehMamorPakhshModel != null)
        {
            ForoshandehMamorPakhshUtils foroshandehMamorPakhshUtils = new ForoshandehMamorPakhshUtils();
            noeMasouliat = foroshandehMamorPakhshUtils.getNoeMasouliat(foroshandehMamorPakhshModel);
        }
        return noeMasouliat;
    }

    private boolean canEditDarkhast(int noeMasouliat , DarkhastFaktorMoshtaryForoshandeModel model)
    {
        return (noeMasouliat == 4 && model.getCodeVazeiat() == 99) || (noeMasouliat == 5 && model.getExtraProp_IsSend() == 0 && model.getCodeVazeiat() < 6);
    }


    @Override
    public void checkFakeLocationAndDateTime()
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
            PubFunc.FakeLocation fakeLocation = new PubFunc().new FakeLocation();
            if (fakeLocation.useFakeLocation(mPresenter.getAppContext()))
            {
                mPresenter.onErrorUseFakeLocation();
                return;
            }

            ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
            String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
 , "");
            String port = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
 , "");
            if (serverIP.equals("") || port.equals(""))
            {
                mPresenter.onCheckServerTime(false, mPresenter.getAppContext().getString(R.string.errorGetDateTimeData));
            }
            else
            {
                PubFunc.LoginInfo loginInfo = new PubFunc().new LoginInfo();
                loginInfo.callLoginInfoService(mPresenter.getAppContext(), serverIP, port, new GetLoginInfoCallback()
                {
                    @Override
                    public void onSuccess(boolean validDiffTime, String serverDateTime, String deviceDateTime, long diff)
                    {
                        String message = String.format("%1$s \n %2$s : %3$s \n %4$s : %5$s \n %6$s ( %7$s %8$s) : %9$s %10$s", mPresenter.getAppContext().getString(R.string.errorLocalDateTime),
                                mPresenter.getAppContext().getString(R.string.serverTime), serverDateTime, mPresenter.getAppContext().getString(R.string.deviceTime), deviceDateTime,
                                mPresenter.getAppContext().getString(R.string.timeDiff), Constants.ALLOWABLE_SERVER_LOCAL_TIME_DIFF(),
                                mPresenter.getAppContext().getString(R.string.second), diff, mPresenter.getAppContext().getString(R.string.second));
                        mPresenter.onCheckServerTime(validDiffTime, message);
                    }

                    @Override
                    public void onFailure(String error)
                    {
                        setLogToDB(Constants.LOG_EXCEPTION(), error, CLASS_NAME, "", "getServerTime", "onFailure");
                        mPresenter.onCheckServerTime(false, mPresenter.getAppContext().getString(R.string.errorGetDateTimeData));
                    }
                });
            }

        }
        else
        {
            //this is a test user
            mPresenter.onCheckServerTime(true, "");
        }
    }

    @Override
    public void getCustomersOrderByRouting() {
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeDAO.getDistinctCustomers(0);
        int noeMasouliat = getNoeMasouliatWithReturnData();
        for (DarkhastFaktorMoshtaryForoshandeModel model : darkhastFaktorMoshtaryForoshandeModels) {
            double[] location = getLocation(model.getCcAfradForoshandeh(), model.getCcMoshtary(), model.getCcUser(), model.getLatitude(), model.getLongitude());
            model.setLatitude((float) location[0]);
            model.setLongitude((float) location[1]);
            int countCanEdit = 0;
            int codeVaziatt = 0;
            if (model.getCcDarkhastFaktorNoeForosh() == 1){
                codeVaziatt = 6;
            } else if (model.getCcDarkhastFaktorNoeForosh() == 2){
                codeVaziatt = 99;
            }
            if (noeMasouliat == 4) {
                countCanEdit = darkhastFaktorMoshtaryForoshandeDAO.getCountCanEditDarkhastForMamorPakhshSard(model.getCcMoshtary(), 0, codeVaziatt);
            } else if (noeMasouliat == 5) {
                countCanEdit = darkhastFaktorMoshtaryForoshandeDAO.getCountCanEditDarkhastForMamorPakhshSmart(model.getCcMoshtary(), 0, 0, codeVaziatt);
            }
            if (countCanEdit == 0) {
                model.setCanEditDarkhast(false);
            } else {
                model.setCanEditDarkhast(true);
            }
        }
        getRouting(darkhastFaktorMoshtaryForoshandeModels);
    }

    @Override
    public void getCustomersOrderByCode()
    {
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(BaseApplication.getContext());
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
        ArrayList<Integer> ccMoshtarys = new ArrayList<>();
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeDAO.getDistinctCustomers(0);
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListCanEditCustomerDarkhast = new ArrayList<>();
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListAllDarkhastEdited = new ArrayList<>();
        int noeMasouliat = getNoeMasouliatWithReturnData();
        for (DarkhastFaktorMoshtaryForoshandeModel model : darkhastFaktorMoshtaryForoshandeModels)
        {
            ccMoshtarys.add(model.getCcMoshtary());
            double[] location = getLocation(model.getCcAfradForoshandeh(), model.getCcMoshtary(), model.getCcUser(), model.getLatitude(), model.getLongitude());
            model.setLatitude((float) location[0]);
            model.setLongitude((float) location[1]);
            int countCanEdit = 0;
            int statusCode=0;
            if((noeMasouliat==4 || noeMasouliat==5) && model.getCcDarkhastFaktorNoeForosh()==1)
                statusCode=6;
            else if( (noeMasouliat==4 || noeMasouliat==5) && model.getCcDarkhastFaktorNoeForosh()==2)
                statusCode=99;

                if (noeMasouliat == 4) {
                countCanEdit = darkhastFaktorMoshtaryForoshandeDAO.getCountCanEditDarkhastForMamorPakhshSard(model.getCcMoshtary(), 0, statusCode);
            } else if (noeMasouliat == 5) {
                countCanEdit = darkhastFaktorMoshtaryForoshandeDAO.getCountCanEditDarkhastForMamorPakhshSmart(model.getCcMoshtary(), 0, 0, statusCode);
            }
            if (countCanEdit == 0)
            {
                arrayListAllDarkhastEdited.add(model);
            }
            else
            {
                arrayListCanEditCustomerDarkhast.add(model);
            }
        }
        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();

        for (int i = 0 ; i < ccMoshtarys.size(); i++){
            ArrayList<MoshtaryAddressModel> models = moshtaryAddressDAO.getByccMoshtary(ccMoshtarys.get(i));
            moshtaryAddressModels.add(models.get(0));
        }
        mPresenter.onGetCustomersList(arrayListCanEditCustomerDarkhast, arrayListAllDarkhastEdited, Constants.SORT_TREASURY_BY_CUSTOMER_CODE, locationProvider.getLatitude(), locationProvider.getLongitude() , moshtaryAddressModels);
    }

    @Override
    public void getCustomerFaktors(DarkhastFaktorMoshtaryForoshandeModel customerInfo, MoshtaryAddressModel moshtaryAddressModel, String customerPriority)
    {
        DarkhastFaktorMoshtaryForoshandeDAO darkhastFaktorMoshtaryForoshandeDAO = new DarkhastFaktorMoshtaryForoshandeDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeDAO.getCustomerDarkhastFaktor(customerInfo.getCcMoshtary() , 0);
        int noeMasouliat = getNoeMasouliatWithReturnData();
        for (DarkhastFaktorMoshtaryForoshandeModel model : darkhastFaktorMoshtaryForoshandeModels)
        {
            model.setCanEditDarkhast(canEditDarkhast(noeMasouliat, model));
        }
        mPresenter.onGetCustomerFaktors(darkhastFaktorMoshtaryForoshandeModels, moshtaryAddressModel , customerPriority , customerInfo);
    }

    @Override
    public void getTodayTreasuryListByRouting()
    {
        DarkhastFaktorMoshtaryForoshandeDAO darkhastFaktorMoshtaryForoshandeDAO = new DarkhastFaktorMoshtaryForoshandeDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeDAO.getAll(0);
        getRouting(darkhastFaktorMoshtaryForoshandeModels);
    }

    @Override
    public void getTodayTreasuryListByCustomerCode()
    {
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(BaseApplication.getContext());
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
        ArrayList<Integer> ccMoshtarys = new ArrayList<>();
        DarkhastFaktorMoshtaryForoshandeDAO darkhastFaktorMoshtaryForoshandeDAO = new DarkhastFaktorMoshtaryForoshandeDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> allDarkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeDAO.getAll(0);
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> editedDarkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> canEditDarkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();
        int noeMasouliat = getNoeMasouliatWithReturnData();
        for (DarkhastFaktorMoshtaryForoshandeModel model : allDarkhastFaktorMoshtaryForoshandeModels)
        {
            ccMoshtarys.add(model.getCcMoshtary());
            if (canEditDarkhast(noeMasouliat , model))
            {
                canEditDarkhastFaktorMoshtaryForoshandeModels.add(model);
            }
            else
            {
                editedDarkhastFaktorMoshtaryForoshandeModels.add(model);
            }
            double[] location = getLocation(model.getCcAfradForoshandeh(), model.getCcMoshtary(), model.getCcUser(), model.getLatitude(), model.getLongitude());
            model.setLatitude((float) location[0]);
            model.setLongitude((float) location[1]);
            ccMoshtarys.add(model.getCcMoshtary());
        }
        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();
        for (int i = 0 ; i < ccMoshtarys.size(); i++){
            ArrayList<MoshtaryAddressModel> models = moshtaryAddressDAO.getByccMoshtary(ccMoshtarys.get(i));
            moshtaryAddressModels.add(models.get(0));
        }
        mPresenter.onGetTodayTreasuryList(canEditDarkhastFaktorMoshtaryForoshandeModels, editedDarkhastFaktorMoshtaryForoshandeModels,locationProvider.getLatitude(), locationProvider.getLongitude(), Constants.SORT_TREASURY_BY_CUSTOMER_CODE , moshtaryAddressModels);
    }

    @Override
    public void getFaktorImage(long ccDarkhastFaktor)
    {
        DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels = darkhastFaktorEmzaMoshtaryDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        if (darkhastFaktorEmzaMoshtaryModels.size() > 0)
        {
            mPresenter.onGetFaktorImage(darkhastFaktorEmzaMoshtaryModels.get(0));
        }
        else
        {
            mPresenter.onGetFaktorImage(null);
        }
    }


    /**
     * check selected customer and then set value of shared
     * @param darkhastFaktorMoshtaryForoshandeModel لیست وصول
     */
    @Override
    public void setDarkhastFaktorShared(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        DarkhastFaktorDAO darkhastfaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
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
        boolean updateMandeMojodi = true;

        Log.d("treasury" , "ccdarkhastfaktor : " + darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor() + " " + darkhastFaktorMoshtaryForoshandeModel.toString());

        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
        int countDarkhastFaktorSatr = darkhastFaktorSatrDAO.getCountByccDarkhastFaktor(darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor());
        Log.d("treasury","countDarkhastFaktorSatr:"+countDarkhastFaktorSatr);
        if (countDarkhastFaktorSatr <= 0)
        {
            mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorGetDarkhastFaktorSatrInfo);
            return;
        }

        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
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

        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        boolean isMojazForDarkhast = foroshandehMamorPakhshModel.getIsMojazForSabtDarkhast() == 1;
        boolean isEtebarCheckBargashty = true;
        boolean isMojazForResid = true;

        boolean moshtaryForoshandehFlag = false;
        MoshtaryAfradDAO moshtaryafradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        Log.d("treasury" , "getCcMoshtary : " + darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
        ArrayList<MoshtaryAfradModel> moshtaryAfradModels = moshtaryafradDAO.getByccMoshtary(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
        if (moshtaryAfradModels.size() > 0)
        {
            if(moshtaryAfradModels.get(0).getCcAfrad() == foroshandehMamorPakhshModel.getCcAfrad())
            {
                moshtaryForoshandehFlag = true;
                //SelectFaktor.setMoshtaryForoshandehFlag(true);
            }
        }

        int ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();
        // int tedadBargashti = bargashtyDAO.getCountByccMoshtaryAndSazmanForosh(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary(), ccSazmanForosh);
        MoshtaryEtebarSazmanForoshDAO moshtaryetebarsazmanforoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        int tedadEtebarCheckBargashti = moshtaryetebarsazmanforoshDAO.getByccMoshtary(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary()).getTedadBargashty();
        Log.d("customer" , "getByccMoshtary : " + moshtaryetebarsazmanforoshDAO.getByccMoshtary(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary()).toString());
        Log.d("customer" , "ccMoshtary : " + darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
        int countDarkhastFaktorErsalNashodeh = darkhastfaktorDAO.getCountErsalNashode();
        Log.d("customer" , "count ersal nashode : " + countDarkhastFaktorErsalNashodeh);

        if(checkMojazForDarkhast && !isMojazForDarkhast)// & PubFuncs.DeviceInfo_TestBarnameh(context) == false)
        {
            mPresenter.onFailedSetDarkhastFaktorShared(R.string.cantMojazForDarkhast);
            //Toast.makeText(context, ".\n", Toast.LENGTH_LONG).show();
        }
        else
        {
            int ccAnbarakActive = foroshandehMamorPakhshDAO.getAll().get(0).getCcAnbarak();
            AnbarakAfradDAO anbarakafradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
            int ccAnbarakFeli = anbarakafradDAO.getAll().get(0).getCcAnbarak();

            PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();


            //Log.d("getCustomer" , "checkCheckBargashty : " + checkCheckBargashty + " , tedadBargashti : " + tedadBargashti + " , tedadEtebarCheckBargashti : " + tedadEtebarCheckBargashti);

            if(checkFaktorErsalNashode && countDarkhastFaktorErsalNashodeh > 0)
            {
                mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorFaktorErsalNashode);
                //Toast.makeText(context, "به علت عدم ارسال درخواست مشتری شما قادر به ثبت درخواست نمی باشید.\nلطفا تمامی درخواست ها را ارسال نمائید.", Toast.LENGTH_LONG).show();
            }
            /*else if(checkCheckBargashty && tedadBargashti > tedadEtebarCheckBargashti)
            {
                mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorCheckBargashtyMoshtary);
                //Toast.makeText(context, "به علت چک برگشتی مشتری شما قادر به ثبت درخواست نمی باشید.\n", Toast.LENGTH_LONG).show();
            }*/
            else if(checkTimeDarkhast && !checkDateTime(childParameterModels))
            {
                mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorTimeLimitForRequest);
            }
            else if (checkMasahateMaghaze && new MoshtaryDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary()).getMasahatMaghazeh() == 0)
            {
                //Toast.makeText(context, "شما قادر به ثبت درخواست نمی باشید.لطفا مساحت مغازه  مشتری را بروزرسانی نمایید.\n", Toast.LENGTH_LONG).show();
                mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorMasahateMaghazeForRequest);
            }
            else if(checkAnbarak && ccAnbarakActive != ccAnbarakFeli)
            {
                //Toast.makeText(context, "به علت تغییر انبارک شما قادر به ثبت درخواست نمی باشید.لطفا مجددا دریافت برنامه نمایید.\n", Toast.LENGTH_LONG).show();
                mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorAnbarakForRequest);
            }
            /*else if (!googleLocationProvider.getHasAccess())
            {
                mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorAccessToLocation);
            }*/
            else
            {
                int codeNoeVosol = darkhastFaktorMoshtaryForoshandeModel.getCodeNoeVosolAzMoshtary();
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
                    ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_RESID();
                }

                boolean canCreateFaktor = true;
                Log.d("treasury" , "checkDistance : " + checkDistance);
                Log.d("treasury" , "ccAfrad : " + foroshandehMamorPakhshModel.getCcAfrad());
                if (checkDistance)
                {
                    canCreateFaktor = isValidCreateFaktor(darkhastFaktorMoshtaryForoshandeModel , foroshandehMamorPakhshModel);
                }
                if (canCreateFaktor)
                {

                    if(checkMojazForResid && codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_RESID())
                    {
                        ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_RESID();
                    }
                    else if (codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK())
                    {
                        ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_CHECK();
                        mPresenter.onWarningSetDarkhastFaktorShared(R.string.cantMojazForResid);
                        Log.d("vosol" , "not allow resid");
                        //Toast.makeText(context, "به علت بسته شدن اعتبار فروشنده، شما تنها قادر به ثبت وصول وجه نقد و چک می باشید.\n", Toast.LENGTH_LONG).show();
                    }
                    if(checkMoshtaryForoshande && moshtaryForoshandehFlag)
                    {
                        ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_MOSHTARY_FOROSHANDE();
                        //Toast.makeText(context, " شما برای مشتری به نام خود تنها قادر به ثبت وصول وجه نقد می باشید.\n", Toast.LENGTH_LONG).show();
                        mPresenter.onWarningSetDarkhastFaktorShared(R.string.onlyVajhNaghdForYourCustomer);
                    }

                    if(noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 4 || noeMasouliat == 5 || noeMasouliat == 6 || noeMasouliat ==8)//بروزرسانی موجودی انبارک
                    {
                        if (updateMandeMojodi)
                        {
                            updateMandehMojodi(noeMasouliat, darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor(), darkhastFaktorMoshtaryForoshandeModel.getTarikhFaktor(), String.valueOf(foroshandehMamorPakhshModel.getCcMamorPakhsh()), String.valueOf(darkhastFaktorMoshtaryForoshandeModel.getCcForoshandeh()), String.valueOf(foroshandehMamorPakhshModel.getCcAfrad()), darkhastFaktorMoshtaryForoshandeModel,
                                    moshtaryForoshandehFlag , isMojazForResid , isEtebarCheckBargashty , ccChildParameterNoeVosol, googleLocationProvider);
                        }
//                        if (updateMandeMojodi)
//                        {
//                            updateMandehMojodi(noeMasouliat, darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor(), darkhastFaktorMoshtaryForoshandeModel.getTarikhFaktor(), String.valueOf(foroshandehMamorPakhshModel.getCcMamorPakhsh()), String.valueOf(darkhastFaktorMoshtaryForoshandeModel.getCcForoshandeh()), String.valueOf(foroshandehMamorPakhshModel.getCcAfrad()));
//                        }
                    }

                    /////////////////////

                    Log.d("vosol" , "checkMojazForResid : " + checkMojazForResid);
                    //Log.d("vosol" , "isMojazForResid : " + isMojazForResid);
                    Log.d("vosol" , "checkMoshtaryForoshande : " + checkMoshtaryForoshande);
                    Log.d("vosol" , "moshtaryForoshandehFlag : " + moshtaryForoshandehFlag);
                    Log.d("vosol" , "checkEtebarCheckBargashty : " + checkEtebarCheckBargashty);
                    //Log.d("vosol" , "isEtebarCheckBargashty : " + isEtebarCheckBargashty);

                    //setRequestInfoShared(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary(), darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor(), darkhastFaktorMoshtaryForoshandeModel.getCcForoshandeh(), darkhastFaktorMoshtaryForoshandeModel.getCcMarkazForosh(), darkhastFaktorMoshtaryForoshandeModel.getCcSazmanForosh(), darkhastFaktorMoshtaryForoshandeModel.getCcMarkazSazmanForosh(), moshtaryForoshandehFlag , isMojazForResid , isEtebarCheckBargashty , ccChildParameterNoeVosol, googleLocationProvider, (long)darkhastFaktorMoshtaryForoshandeModel.getMablaghKhalesFaktor());
                }
            }
        }
    }


    @Override
    public void sendDariaftPardakht(long ccDarkhastFaktor)
    {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = dariaftPardakhtPPCDAO.getForSendToSqlByccDarkhastFaktor(ccDarkhastFaktor);
        Log.d("treasury" , "dariaftPardakhtPPCModels.size : " + dariaftPardakhtPPCModels.size());
        if (dariaftPardakhtPPCModels.size() > 0)
        {
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            if (foroshandehMamorPakhshModel == null)
            {
                mPresenter.onError(R.string.errorFindForoshandehMamorPakhsh);
            }
            else
            {
                ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
                String ip = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
                        , "");
                String port = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
                        , "");
                if (ip.equals("") || port.equals(""))
                {
                    mPresenter.onError(R.string.errorFindServerIP);
                }
                else
                {
                    int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                    //DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                    DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
                    ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                    int codeNoeVosolVajhNaghd = Integer.parseInt(childParameterDAO.getAllByccChildParameter(String.valueOf(Constants.CC_CHILD_CODE_NOE_VOSOL_VAJH_NAGHD())).get(0).getValue());
                    String currentVersionNumber = new PubFunc().new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());
                    sendDariaftPardakhtToServer(dariaftPardakhtPPCModels, foroshandehMamorPakhshModel, noeMasouliat, darkhastFaktorModel, codeNoeVosolVajhNaghd, currentVersionNumber);
                }
            }
        }
        else
        {
            mPresenter.onError(R.string.errorNotExistItemForSend);
        }
    }


    private void sendDariaftPardakhtToServer(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, int noeMasouliat, final DarkhastFaktorModel darkhastFaktorModel, int codeNoeVosolVajhNaghd, String currentVersionNumber)
    {
        ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
        final DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);

        String ccDpdfs = "-1";
        JSONArray jsonArrayDariaftPardakht = new JSONArray();
        JSONArray jsonArrayDariaftPardakhtDarkhastFaktor = new JSONArray();
        JSONArray jsonArrayKardex = new JSONArray();
        JSONArray jsonArrayKardexSatr = new JSONArray();
        // get ccMarkazForosh , ccMarkazAnbar , ccMarkazSazmanForoshSakhtarForosh
        int ccMarkazForosh = 0;
        int ccMarkazAnbar = 0;
        int ccMarkazSazmanForoshSakhtarForosh = 0;
        if(noeMasouliat != 4)
        {
            ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
            ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbar();
            ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
        }
        else
        {
            ccMarkazForosh = darkhastFaktorModel.getCcMarkazForosh();
            ccMarkazAnbar = darkhastFaktorModel.getCcMarkazAnbar();
            ccMarkazSazmanForoshSakhtarForosh = darkhastFaktorModel.getCcMarkazSazmanForoshSakhtarForosh();
        }

        //create JsonArray of DariaftPardakhtPPCModel
        for (DariaftPardakhtPPCModel dpModel : dariaftPardakhtPPCModels)
        {
            int codeNoeSanad = 0;
            int codeNoeCheck = 0;
            jsonArrayDariaftPardakht.put(dpModel.toJsonObject(ccMarkazForosh, ccMarkazAnbar, ccMarkazSazmanForoshSakhtarForosh, codeNoeSanad, codeNoeCheck, codeNoeVosolVajhNaghd, currentVersionNumber));
            ccDpdfs += "," + dpModel.getCcDariaftPardakht();
        }
        //create JsonArray of DariaftPardakhtDarkhastFaktorPPCModel
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktors = dariaftPardakhtDarkhastFaktorPPCDAO.getForSendToSqlByccDariaftPardakhts(ccDpdfs);
        for (DariaftPardakhtDarkhastFaktorPPCModel dpdfModel : dariaftPardakhtDarkhastFaktors)
        {
            jsonArrayDariaftPardakhtDarkhastFaktor.put(dpdfModel.toJsonObject(ccMarkazForosh, ccMarkazAnbar, ccMarkazSazmanForoshSakhtarForosh, foroshandehMamorPakhshModel.getCcAfrad()));
        }


        try
        {
            JSONObject jsonObjectTreasury = new JSONObject();
            jsonObjectTreasury.put("DariaftPardakht" , jsonArrayDariaftPardakht);
            jsonObjectTreasury.put("DariaftPardakhtDarkhastFaktor" , jsonArrayDariaftPardakhtDarkhastFaktor);
            jsonObjectTreasury.put("kardex" , jsonArrayKardex);
            jsonObjectTreasury.put("kardexSatr" , jsonArrayKardexSatr);

            String strJsonObjectTreasury = jsonObjectTreasury.toString();
            saveToFile("treasury" + darkhastFaktorModel.getCcDarkhastFaktor() + ".txt" , strJsonObjectTreasury);

            Call<CreateDariaftPardakhtPPCJSONResult> call = apiServicePost.createDariaftPardakhtPPCJSON(strJsonObjectTreasury);
//            mPresenter.closeLoading();
            call.enqueue(new Callback<CreateDariaftPardakhtPPCJSONResult>()
            {
                @Override
                public void onResponse(Call<CreateDariaftPardakhtPPCJSONResult> call, Response<CreateDariaftPardakhtPPCJSONResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            CreateDariaftPardakhtPPCJSONResult result = response.body();
                            if (result.getSuccess())
                            {
                                darkhastFaktorDAO.updateSendedDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor(), darkhastFaktorModel.getCcDarkhastFaktor(), 1);
                                dariaftPardakhtDarkhastFaktorPPCDAO.updateSendedDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor(), darkhastFaktorModel.getCcDarkhastFaktor(), 1);
                                DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
                                dariaftPardakhtPPCDAO.updateSendedDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor(), darkhastFaktorModel.getCcDarkhastFaktor(), 1);
                                mPresenter.onSuccess(R.string.successSendData);
                            }
                            else
                            {
                                showErrorMessageOfSend(result.getMessage());
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TreasuryListModel", "" , "sendDariaftPardakhtToServer" , "onResponse");
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TreasuryListModel", "" , "sendDariaftPardakhtToServer" , "onResponse");
                            mPresenter.onError(R.string.errorOperation);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TreasuryListModel", "" , "sendDariaftPardakhtToServer" , "onResponse");
                        mPresenter.onError(R.string.errorOperation);
                    }
                }

                @Override
                public void onFailure(Call<CreateDariaftPardakhtPPCJSONResult> call, Throwable t)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TreasuryListModel", "" , "sendDariaftPardakhtToServer" , "onFailure");
                    mPresenter.onError(R.string.errorOperation);
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "TreasuryListModel", "", "sendDariaftPardakhtToServer", "");
        }

    }

    private void showErrorMessageOfSend(String errorCode)
    {
        if (errorCode.trim().equals("-9"))
        {
            mPresenter.onError(R.string.errorSendDariaftPardakhtMoghayeratVosol);
        }
        else if (errorCode.trim().equals("-11"))
        {
            mPresenter.onError(R.string.errorSendDariaftPardakhtNameSahebHesab);
        }
        else if (errorCode.trim().equals("-12"))
        {
            mPresenter.onError(R.string.errorSendDariaftPardakhtMablaghVosolManfi);
        }
    }


    private void saveToFile(String fileName , String jsonStringData)
    {
        try
        {
            File path = mPresenter.getAppContext().getExternalFilesDir(null);
            File file = new File(path, fileName);
            FileOutputStream stream = new FileOutputStream(file);
            try
            {
                stream.write(jsonStringData.getBytes());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    @Override
    public void routingFromCurrentLocation(final int ccMoshtary, final String customerName, double desLatitude, double desLongitude)
    {
        String jsonData = prepareDataForGetOptimizedRoute(ccMoshtary, desLatitude, desLongitude);
        if (jsonData != null && !jsonData.equals(""))
        {
            RoutingServerShared routingServerShared = new RoutingServerShared(BaseApplication.getContext());
            String urlOsrm = routingServerShared.getString(RoutingServerShared.IP,"http://91.92.125.244:8002");
            Log.d("urlOsrm",urlOsrm);
            if (urlOsrm.length() > 0)
            {
                APIServiceValhalla apiServiceValhalla = ApiClientGlobal.getInstance().getClientServiceValhalla(urlOsrm);
                Call<Object> call = apiServiceValhalla.getOptimizedRoute(jsonData);
                call.enqueue(new Callback<Object>()
                {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response)
                    {
                        try
                        {
                            Gson gson = new Gson();
                            if (response.isSuccessful())
                            {
                                String jsonObjectResponse = gson.toJson(response.body());
                                OptimizedRouteResult result = gson.fromJson(jsonObjectResponse , OptimizedRouteResult.class);
                                if (result != null && result.getTrip() != null)
                                {
                                    Trip trip = result.getTrip();
                                    if (trip.getStatus() == 0 && trip.getLegs() != null && trip.getLegs().size() > 0 && trip.getSummary() != null)
                                    {
                                        ArrayList<GeoPoint> pointsOfPolyline = RoutingUtils.decode(trip.getLegs().get(0).getShape() , false);
                                        mPresenter.onSuccessRouting(customerName, pointsOfPolyline, jsonObjectResponse);
                                    }
                                    else
                                    {
                                        PubFunc.Logger logger = new PubFunc().new Logger();
                                        logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), "error get optimized route", CLASS_NAME, "", "routingFromCurrentLocation", "onResponse");
                                        mPresenter.onError(R.string.errorGetData);
                                    }
                                }
                                else
                                {
                                    String endpoint = getEndpoint(call);
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", mPresenter.getAppContext().getString(R.string.resultIsNull), endpoint), CLASS_NAME, "", "routingFromCurrentLocation", "onResponse");
                                    mPresenter.onError(R.string.errorGetData);
                                }
                            }
                            else
                            {
                                SourcesToTargetsFailedResult responseError = gson.fromJson(gson.toJson(response), SourcesToTargetsFailedResult.class);

                                String endpoint = getEndpoint(call);
                                String message = String.format("message : %1$s \n code : %2$s * %3$s", responseError.getError(), responseError.getErrorCode(), endpoint);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, CLASS_NAME, "", "routingFromCurrentLocation", "onResponse");
                                mPresenter.onError(R.string.errorGetData);
                            }
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "routingFromCurrentLocation", "onResponse");
                            mPresenter.onError(R.string.errorGetData);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t)
                    {
                        String endpoint = getEndpoint(call);
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, "", "routingFromCurrentLocation", "onFailure");
                        mPresenter.onError(R.string.errorGetData);
                    }
                });
            }
            else
            {
                mPresenter.onError(R.string.errorFindServerIP);
            }
        }
    }


    private String prepareDataForGetOptimizedRoute(int ccMoshtary, double desLatitude , double desLongitude)
    {
        JSONArray jsonArrayLocations = getJsonArraySourceLocation();
        if (jsonArrayLocations == null || jsonArrayLocations.length() == 0)
        {
            mPresenter.onError(R.string.errorGetLocation);
            return null;
        }
        try
        {
            JSONObject jsonObjectDestination = new JSONObject();
            jsonObjectDestination.put("lat" , desLatitude);
            jsonObjectDestination.put("lon" , desLongitude);
            jsonArrayLocations.put(jsonObjectDestination);

            JSONObject jsonObjectAllData = new JSONObject();
            jsonObjectAllData.put("locations" , jsonArrayLocations);
            jsonObjectAllData.put("costing" , "auto");
            jsonObjectAllData.put("id" , String.valueOf(ccMoshtary));
            return jsonObjectAllData.toString();
        }
        catch (Exception e)
        {
            mPresenter.onError(R.string.errorOccurred);
            e.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "routingFromCurrentLocation", "");
            return null;
        }
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
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RequestCustomerListModel", "", "checkDateTime", "");
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

    private boolean isValidCreateFaktor(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        //---------------------- For Test -----------------
        UserTypeShared userTypeShared = new UserTypeShared(mPresenter.getAppContext());
        int isTest = userTypeShared.getInt(userTypeShared.USER_TYPE() , 0);
        if (isTest == 1)
        {
            return true;
        }

        //SystemConfigDAO systemConfigDAO = new SystemConfigDAO(mPresenter.getAppContext());
        //SystemConfigModel systemConfig = systemConfigDAO.getAll().get(0);
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());

        //----------- getCanSetFaktorKharejAzMahal ------------------------------------
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        //String strDate = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_NOT_CHECK_KHAREJ_AZ_MAHAL());
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            Date fromDateKharejAzMahal = sdf.parse(foroshandehMamorPakhshModel.getFromDateKharejAzMahal());
            Date endDateKharejAzMahal = sdf.parse(foroshandehMamorPakhshModel.getEndDateKharejAzMahal());
            Date currentDate = sdf.parse(sdf.format(new Date()));
            long currentDateMiliSecond = currentDate.getTime();
            boolean needCheckKharejAzMahal = true;
            if (currentDateMiliSecond >= fromDateKharejAzMahal.getTime() && currentDateMiliSecond <= endDateKharejAzMahal.getTime() || moshtary.getKharejAzMahal() == 1)
            {
                needCheckKharejAzMahal = false;
            }

            if (needCheckKharejAzMahal)//&& !CanInsFaktor_KharejAzMahal && SelectFaktor.getccGorohNoeMoshtary() != 350)// && CanVisitKharejAzMahal_Polygon == 0)
            {
                if (moshtary.getExtraProp_IsMoshtaryAmargar() == 0)
                {
                    /*MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
                    MoshtaryAddressModel moshtaryAddress = moshtaryAddressDAO.getTopOneAddress(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());*/
                    MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(mPresenter.getAppContext());
                    int countMoshtaryMorajeShode = moshtaryMorajehShodehRoozDAO.getCountByccMoshtary(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
                    PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();

                    float[] distance = new float[2];
                    double[] location = getLocation(darkhastFaktorMoshtaryForoshandeModel.getCcAfradForoshandeh(), darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary(),
                            darkhastFaktorMoshtaryForoshandeModel.getCcUser(), darkhastFaktorMoshtaryForoshandeModel.getLatitude(), darkhastFaktorMoshtaryForoshandeModel.getLongitude());

                    double latMoshtary = location[0];
                    double lngMoshtary = location[1];

                    Location.distanceBetween(latMoshtary, lngMoshtary, googleLocationProvider.getLatitude(), googleLocationProvider.getLongitude(), distance);
                    Log.d("treasury", "distance[0] : " + distance[0]);

                    int isMorajehShodeh = 0;
                    if (haveAdamMojoodgiriDarkhast(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary(), 0, 0, countMoshtaryMorajeShode) || countMoshtaryMorajeShode > 0)
                    {
                        isMorajehShodeh = 1;
                    }

                    if (isMorajehShodeh == 0 || foroshandehMamorPakhshModel.getCanGetDarkhastTelephoni() == 0)
                    {
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
                            setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "RequestCustomerListModel", "", "isValidCreateFaktor", "");
                        }
                        if (GPSEnable == 1 /*& SelectFaktor.getccGorohNoeSenf() != ccNoeSenfMoshtary_BedonSenf*/)
                        {
                            if (distance[0] > zaribKharejAzMahalMetr)
                            {
                                mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorLocationForRequest);
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "isValidCreateFaktor", "");
            mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorCantRegisterRequest);
            return false;
        }
    }


    private boolean haveAdamMojoodgiriDarkhast(int ccMoshtary, int ccPorseshnameh, int IsMoshtaryAmargar , int countMoshtaryMorajeShode)
    {
        try
        {
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            int countDarkhastFaktor = darkhastFaktorDAO.getCountByccMoshtaryRooz(ccMoshtary);
            MojoodiGiriDAO mojoodiGiriDAO= new MojoodiGiriDAO(mPresenter.getAppContext());
            int countMojodiGiri = mojoodiGiriDAO.getCountByMoshtary(ccMoshtary, true);
            AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(mPresenter.getAppContext());
            int countAdamDarkhast = adamDarkhastDAO.getCountByccMoshtary(ccMoshtary);
            if (countDarkhastFaktor == 0 && countMojodiGiri == 0 && countAdamDarkhast == 0  && countMoshtaryMorajeShode == 0)
            {
                return false;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RequestCustomerListModel", "", "haveAdamMojoodgiriDarkhast", "");
            return false;
        }
        return true;
    }

    private boolean checkTarikhMasir()
    {
        MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
        String tarikhMasir = masirDAO.getAll().get(0).getTarikhMasir();
        GetProgramShared shared = new GetProgramShared(mPresenter.getAppContext());
        String lastGetProgramDate = shared.getString(shared.PERSIAN_DATE_OF_GET_PROGRAM() , "");
        if (lastGetProgramDate.equals(""))
        {
            return false;
        }
        else
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
                SimpleDateFormat sdfGetProgram = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH()); //this object used for parse getProgramDate because format of this date is different
                Date getProgramDate = sdfGetProgram.parse(lastGetProgramDate);
                Date dateMasir = sdf.parse(tarikhMasir);

                Log.d("date" , "last program date : " + getProgramDate);
                Log.d("date" , "masir date : " + dateMasir);

                long diff = dateMasir.getTime() - getProgramDate.getTime();
                diff = diff < 0 ? (diff*-1) : diff;
                long days = diff / 1000 * 60 * 60 * 24;
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
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RequestCustomerListModel", "", "checkTarikhMasir", "");
                return false;
            }
        }
    }

//    private void updateMandehMojodi(int noeMasouliat , final long ccDarkhastFaktor, final String tarikhDarkhast , String ccMamorPakhsh , String ccForoshandeh, final String ccAfrad,String ccSazmanForoshFaktor)
//    {
//        final int finalCCForoshandeh = Integer.valueOf(ccForoshandeh);
//        final int finalCCAfrad = Integer.valueOf(ccAfrad);
//        final Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg)
//            {
//                if (msg.arg1 == 1)
//                {
//                    mPresenter.onSuccessUpdateMandeMojodiMashin();
//                }
//                else if (msg.arg1 == -1)
//                {
//                    mPresenter.onFailedUpdateMandeMojodiMashin();
//                }
//                return false;
//            }
//        });
//
//
//        final MandehMojodyMashinDAO mandehMojodyMashinDAO = new MandehMojodyMashinDAO(mPresenter.getAppContext());
//        String ccAnbarakAfrad = String.valueOf(new AnbarakAfradDAO(mPresenter.getAppContext()).getAll().get(0).getCcAnbarak());
//
//        if(noeMasouliat == 1)//1-Foroshandeh-Sard
//        {
//            ccAnbarakAfrad = "0";
//            ccMamorPakhsh = "0";
//        }
//        else if(noeMasouliat == 2 || noeMasouliat == 3)//2-Foroshandeh-Garm //3-Foroshandeh-Smart
//        {
//            ccMamorPakhsh = "0";
//        }
//        else if (noeMasouliat == 4 || noeMasouliat == 5)//4-MamorPakhsh-Sard // 5-MamorPakhsh-Smart
//        {
//            ccForoshandeh = "0";
//        }
//        else //6-SarparastForoshandeh 7-Amargar
//        {
//            ccForoshandeh = "0";
//            ccMamorPakhsh = "0";
//        }
//        String ccKalaCode="-1";
//
//        mandehMojodyMashinDAO.fetchMandehMojodyMashin(mPresenter.getAppContext(), "RequestCustomerListActivity", ccAnbarakAfrad, ccForoshandeh, ccMamorPakhsh,ccKalaCode,ccSazmanForoshFaktor, new RxResponseHandler()
//        {
//            @Override
//            public void onStart(Disposable disposable) {
//                super.onStart(disposable);
//            }
//
//            @Override
//            public void onSuccess(final ArrayList arrayListData)
//            {
//                Thread thread = new Thread()
//                {
//                    @Override
//                    public void run()
//                    {
//                        boolean deleteResult = mandehMojodyMashinDAO.deleteAll();
//                        boolean insertResult = mandehMojodyMashinDAO.insertGroup(arrayListData);
//                        KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
//                        kalaMojodiDAO.deleteAll();
//                        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
//                        if (arrayListData.size() > 0)
//                        {
//                            String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
//                            for (int i=0; i<arrayListData.size() ; i++)
//                            {
//                                MandehMojodyMashinModel mandehMojodyMashinModel = (MandehMojodyMashinModel)arrayListData.get(i);
//                                KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();
//
//                                kalaMojodiModel.setCcKalaCode(mandehMojodyMashinModel.getCcKalaCode());
//                                kalaMojodiModel.setCcForoshandeh(finalCCForoshandeh);
//                                kalaMojodiModel.setTedad(mandehMojodyMashinModel.getMojody());
//                                kalaMojodiModel.setCcDarkhastFaktor(ccDarkhastFaktor);
//                                kalaMojodiModel.setTarikhDarkhast(tarikhDarkhast);
//                                kalaMojodiModel.setShomarehBach(mandehMojodyMashinModel.getShomarehBach());
//                                kalaMojodiModel.setTarikhTolid(mandehMojodyMashinModel.getTarikhTolid());
//                                kalaMojodiModel.setGheymatMasrafKonandeh(mandehMojodyMashinModel.getGheymatMasrafKonandeh());
//                                kalaMojodiModel.setGheymatForosh(mandehMojodyMashinModel.getGheymatForosh());
//                                kalaMojodiModel.setCcTaminKonandeh(mandehMojodyMashinModel.getCcTaminKonandeh());
//                                kalaMojodiModel.setZamaneSabt(currentDate);
//                                kalaMojodiModel.setIsAdamForosh(mandehMojodyMashinModel.getIsAdamForosh());
//                                kalaMojodiModel.setMax_Mojody(mandehMojodyMashinModel.getMaxMojody());
//                                kalaMojodiModel.setMax_MojodyByShomarehBach(mandehMojodyMashinModel.getMax_MojodyByShomarehBach());
//                                kalaMojodiModel.setCcAfrad(finalCCAfrad);
//
//                                kalaMojodiModels.add(kalaMojodiModel);
//                            }
//                            insertResult = kalaMojodiDAO.insertGroup(kalaMojodiModels);
//                        }
//                        if (deleteResult && insertResult)
//                        {
//                            Message message = new Message();
//                            message.arg1 = 1;
//                            handler.sendMessage(message);
//                        }
//                        else
//                        {
//                            Message message = new Message();
//                            message.arg1 = -1;
//                            handler.sendMessage(message);
//                        }
//                    }
//                };
//                thread.start();
//            }
//            @Override
//            public void onFailed(String type, String error)
//            {
//                Message message = new Message();
//                message.arg1 = -1;
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onComplete() {
//                super.onComplete();
//            }
//        });
//    }

    private void updateMandehMojodi(int noeMasouliat , final long ccDarkhastFaktor, final String tarikhDarkhast , String ccMamorPakhsh , String ccForoshandeh, String ccAfrad, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel,
                                    boolean moshtaryForoshandehFlag , boolean isMojazForResid , boolean isEtebarCheckBargashty , int ccChildParameterNoeVosol, PubFunc.LocationProvider googleLocationProvider)
//    private void updateMandehMojodi(int noeMasouliat , final long ccDarkhastFaktor, final String tarikhDarkhast , String ccMamorPakhsh , String ccForoshandeh, String ccAfrad)
    {
        final int finalCCForoshandeh = Integer.valueOf(ccForoshandeh);
        final int finalCCAfrad = Integer.valueOf(ccAfrad);

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    setRequestInfoShared(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary(), darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor(), darkhastFaktorMoshtaryForoshandeModel.getCcForoshandeh(), darkhastFaktorMoshtaryForoshandeModel.getCcMarkazForosh(), darkhastFaktorMoshtaryForoshandeModel.getCcSazmanForosh(), darkhastFaktorMoshtaryForoshandeModel.getCcMarkazSazmanForosh(), moshtaryForoshandehFlag , isMojazForResid , isEtebarCheckBargashty , ccChildParameterNoeVosol, googleLocationProvider, (long)darkhastFaktorMoshtaryForoshandeModel.getMablaghKhalesFaktor());
                    mPresenter.onSuccessUpdateMandeMojodiMashin();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onFailedUpdateMandeMojodiMashin();
                }
                return false;
            }
        });


        final MandehMojodyMashinDAO mandehMojodyMashinDAO = new MandehMojodyMashinDAO(mPresenter.getAppContext());
        String ccAnbarakAfrad = String.valueOf(new AnbarakAfradDAO(mPresenter.getAppContext()).getAll().get(0).getCcAnbarak());

        if(noeMasouliat == 1 || noeMasouliat == 6 || noeMasouliat ==8)//1-Foroshandeh-Sard
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
        String ccSazmanForosh=String.valueOf(darkhastFaktorMoshtaryForoshandeModel.getCcSazmanForosh());
        /**fetch all kala with -1
         * if you want to fetch a specific list of kala append their cckala in a string
         * **/
        String ccKalaCode="-1";
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());

        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        String finalCcForoshandeh = ccForoshandeh;
        apiServiceRxjava.getMandehMojodyMashin(ccAnbarakAfrad, ccForoshandeh, ccMamorPakhsh, ccKalaCode, ccSazmanForosh)
                .compose(RxHttpErrorHandler.parseHttpErrors("TreasuryListModel", "TreasuryListActivity", "updateMandehMojodi", "updateMandehMojodi"))
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetMandehMojodyMashinResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<GetMandehMojodyMashinResponse> getMandehMojodyMashinResponseResponse) {
                        updateMandehMojodiMashinTable(getMandehMojodyMashinResponseResponse.body(), finalCcForoshandeh, ccAfrad,handler);

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

    }

    private void updateMandehMojodiMashinTable(GetMandehMojodyMashinResponse body,String ccForoshandeh,String ccAfrad,Handler handler) {
        if (body != null) {
            MandehMojodyMashinRepository mandehMojodyMashinRepository = new MandehMojodyMashinRepository(mPresenter.getAppContext());
            Disposable disposable = mandehMojodyMashinRepository.deleteAll()
                    .subscribe(deleteAll -> {
                        if (deleteAll) {

                            Disposable insertGroup = mandehMojodyMashinRepository.insertGroup(body.getMandehMojodyMashinModels())
                                    .subscribe(insertGroup1 -> {
                                        if (insertGroup1) {
                                            updateKalaMojodiTable(body.getMandehMojodyMashinModels(), Integer.parseInt(ccForoshandeh), Integer.parseInt(ccAfrad),handler);
                                        } else {
                                            Message message = new Message();
                                            message.arg1 = -1;
                                            handler.sendMessage(message);
                                        }
                                    }, throwable -> {
                                                Message message = new Message();
                                                message.arg1 = -1;
                                                handler.sendMessage(message);
                                            }

                                    );
                            compositeDisposable.add(insertGroup);
                        }
                    }, throwable -> {
                        Message message = new Message();
                        message.arg1 = -1;
                        handler.sendMessage(message);

                    });
            compositeDisposable.add(disposable);


        }else{
            Message message = new Message();
            message.arg1 = -1;
            handler.sendMessage(message);
        }
    }

    private void updateKalaMojodiTable(ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels,int ccForoshandeh,int ccAfrad,Handler handler) {
        @SuppressLint("SimpleDateFormat") String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
        ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
        Observable.fromIterable(mandehMojodyMashinModels)
                .subscribeOn(Schedulers.io())
                .map(mandehMojodyMashinModel -> {

                            KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();

                            kalaMojodiModel.setCcKalaCode(mandehMojodyMashinModel.getCcKalaCode());
                            kalaMojodiModel.setCcForoshandeh( ccForoshandeh);
                            kalaMojodiModel.setTedad(mandehMojodyMashinModel.getMojody());
                            kalaMojodiModel.setCcDarkhastFaktor(0);
                            kalaMojodiModel.setTarikhDarkhast(currentDate);
                            kalaMojodiModel.setShomarehBach(mandehMojodyMashinModel.getShomarehBach());
                            kalaMojodiModel.setTarikhTolid(mandehMojodyMashinModel.getTarikhTolid());
                            kalaMojodiModel.setGheymatMasrafKonandeh(mandehMojodyMashinModel.getGheymatMasrafKonandeh());
                            kalaMojodiModel.setGheymatForosh(mandehMojodyMashinModel.getGheymatForosh());
                            kalaMojodiModel.setCcTaminKonandeh(mandehMojodyMashinModel.getCcTaminKonandeh());
                            kalaMojodiModel.setZamaneSabt(currentDate);
                            kalaMojodiModel.setIsAdamForosh(mandehMojodyMashinModel.getIsAdamForosh());
                            kalaMojodiModel.setMax_Mojody(mandehMojodyMashinModel.getMaxMojody());
                            kalaMojodiModel.setMax_MojodyByShomarehBach(mandehMojodyMashinModel.getMax_MojodyByShomarehBach());
                            kalaMojodiModel.setCcAfrad(ccAfrad);
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
                        Message message = new Message();
                        message.arg1 = -1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onComplete() {
                        KalaMojodiRepository kalaMojodiRepository = new KalaMojodiRepository(mPresenter.getAppContext());
                        Disposable delete = kalaMojodiRepository
                                .deleteAll()
                                .subscribe(deleteAll -> {
                                    if (deleteAll){
                                        Disposable insertGroup = new KalaMojodiRepository(mPresenter.getAppContext())
                                                .insertGroup(kalaMojodiModels)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(inserted -> {
                                                    if (inserted){
                                                        Message message = new Message();
                                                        message.arg1 = 1;
                                                        handler.sendMessage(message);
                                                    }else{
                                                        Message message = new Message();
                                                        message.arg1 = -1;
                                                        handler.sendMessage(message);
                                                    }
                                                }, throwable ->{
                                                    Message message = new Message();
                                                    message.arg1 = -1;
                                                    handler.sendMessage(message);});
                                        compositeDisposable.add(insertGroup);
                                    }else{
                                        Message message = new Message();
                                        message.arg1 = -1;
                                        handler.sendMessage(message);
                                    }

                                }, throwable -> {
                                    Message message = new Message();
                                    message.arg1 = -1;
                                    handler.sendMessage(message);
                                });
                        compositeDisposable.add(delete);

                    }
                });

    }
//    private void setRequestInfoShared(int ccMoshtary , long ccDarkhastFaktor , int ccForoshandeh, int ccMarkazSazmanForosh , boolean moshtaryForoshandehFlag , boolean isMojazForResid , boolean isEtebarCheckBargashty , int ccChildParameterNoeVosol, PubFunc.LocationProvider googleLocationProvider)
//    {
//        try
//        {
//            Date minTarikhSanadBargashty = null;
//            Date tarikh = new Date();
//            String currentDateTime = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
//            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
//            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
//            SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
//
//            shared.removeAll();
//            shared.setDefaultRequestInfo();
//            Log.d("setRequestInfoShared" , "ccDarkhastFaktor : " + ccDarkhastFaktor);
//
//            shared.putLong(shared.getCcDarkhastFaktor() , ccDarkhastFaktor);
//            shared.putBoolean(shared.getInsertDarkhastFaktorSatrForMamorPakhsh() , false);
//            shared.putInt(shared.getCcMoshtary() , ccMoshtary);
//            shared.putInt(shared.getCcForoshandeh() , ccForoshandeh);
//            shared.putBoolean(shared.getMoshtaryForoshandehFlag() , moshtaryForoshandehFlag);
//            shared.putBoolean(shared.getIsMojazForResid() , isMojazForResid);
//            shared.putBoolean(shared.getIsEtebarCheckBargashty() , isEtebarCheckBargashty);
//            shared.putInt(shared.getccChildCodeNoeVosol() , ccChildParameterNoeVosol);
//
//            shared.putInt(shared.getCcMarkazSazmanForosh() , ccMarkazSazmanForosh);
//
//            shared.putString(shared.getLatitude(), String.valueOf((float) googleLocationProvider.getLatitude()));
//            shared.putString(shared.getLongitude(), String.valueOf((float) googleLocationProvider.getLongitude()));
//            shared.putString(shared.getAltitude(), String.valueOf(googleLocationProvider.getAltitude()));
//            shared.putFloat(shared.getAccurancy(), googleLocationProvider.getAccuracy());
//            shared.putFloat(shared.getBearing(), googleLocationProvider.getBearing());
//            shared.putFloat(shared.getSpeed(), googleLocationProvider.getSpeed());
//            shared.putString(shared.getSaatVorodBeMaghazeh() , currentDateTime);
//            shared.putString(shared.getSaatKhorojAzMaghazeh() , currentDateTime);
//
//            //----------------------------- CodeMely -------------------
//            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
//            MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(ccMoshtary);
//            shared.putString(shared.getCodeMely(), moshtary.getCodeMely());
//            shared.putString(shared.getShenasehMely(), moshtary.getShenasehMely());
//            shared.putInt(shared.getCcMoshtaryParent(), moshtary.getExtraProp_ccMoshtaryParent());
//            shared.putInt(shared.getMoshtaryDarajeh(), moshtary.getDarajeh());
//            //CodePosty = moshtary.getCodePosty();
//
//            //------------------------------------- MojoodiGiri..-------------------------------------
//            MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(mPresenter.getAppContext());
//            int count = mojoodiGiriDAO.getCountMojodiGiriByMoshtaryForCheck(ccMoshtary, true);
//            boolean haveMojoodiGiri = count != 0;
//            shared.putBoolean(shared.getHaveMojoodiGiri(), haveMojoodiGiri);
//
//            //------------------------------------- MoshtaryGoroh..-------------------------------------
//			shared.putInt(shared.getCcGorohNoeMoshtary(), moshtary.getCcNoeMoshtary());
//            shared.putInt(shared.getCcGorohNoeSenf(), moshtary.getCcNoeSenf());
//
//            //SystemConfigDAO systemConfigDAO = new SystemConfigDAO(mPresenter.getAppContext());
//            //SystemConfigModel systemConfigModel = systemConfigDAO.getAll().get(0);
//            NoeMoshtaryRialKharidDAO moshtaryRialKharidDAO = new NoeMoshtaryRialKharidDAO(mPresenter.getAppContext());
//
//            long hadaghalMablaghKharid = moshtaryRialKharidDAO.getMablaghByccMoshtary(moshtary.getCcNoeMoshtary() , moshtary.getDarajeh());
//            long hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid = 0;
//            long mablaghMaxFaktorKhordehMoshtaryJadid = 0;
//            long mablaghMaxFaktorOmdehMoshtaryJadid = 0;
//
//            String ccChildParameters = Constants.CC_CHILD_HADAGHAL_MABLAGH_KHARID_MOSHTARY_JADID + "," + Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_KHORDEH_MOSHTARY_JADID + "," + Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_OMDEH_MOSHTARY_JADID;
//            ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
//            ArrayList<ParameterChildModel> parameterChildModels = parameterChildDAO.getAllByccChildParameter(ccChildParameters);
//            for (ParameterChildModel parameterChild : parameterChildModels)
//            {
//                if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_HADAGHAL_MABLAGH_KHARID_MOSHTARY_JADID)
//                {
//                    hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid = Long.parseLong(parameterChild.getValue());
//                }
//                else if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_KHORDEH_MOSHTARY_JADID)
//                {
//                    mablaghMaxFaktorKhordehMoshtaryJadid = Long.parseLong(parameterChild.getValue());
//                }
//                else if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_OMDEH_MOSHTARY_JADID)
//                {
//                    mablaghMaxFaktorOmdehMoshtaryJadid = Long.parseLong(parameterChild.getValue());
//                }
//            }
//
//            shared.putFloat(shared.getHadeAghalMablaghKharid() , hadaghalMablaghKharid);
//            shared.putLong(shared.getHadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid() , hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid);
//            shared.putLong(shared.getMablaghMaxFaktorKhordeh_MoshtaryJadid() , mablaghMaxFaktorKhordehMoshtaryJadid);
//            shared.putLong(shared.getMablaghMaxFaktorOmdeh_MoshtaryJadid() , mablaghMaxFaktorOmdehMoshtaryJadid);
//
//            //-------------------MoshtaryEtebarSzmanForosh.. ------------------
//            MoshtaryEtebarSazmanForoshDAO moshtaryetebarsazmanforoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
//            MoshtaryEtebarSazmanForoshModel moshtaryetebarsazmanforosh = moshtaryetebarsazmanforoshDAO.getByccMoshtary(ccMoshtary);
//
//            shared.putLong(shared.getRialCheckBargashty_NoeMoshtary() , moshtaryetebarsazmanforosh.getRialBargashty());
//            shared.putInt(shared.getTedadCheckBargashty_NoeMoshtary() , moshtaryetebarsazmanforosh.getTedadBargashty());
//            shared.putInt(shared.getModatCheckBargashty_NoeMoshtary() , moshtaryetebarsazmanforosh.getModatBargashty());
//
//            //------------------------ HadaghalMablaghKharidMoshtary ----------------------------
//            MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(mPresenter.getAppContext());
//            int darajehMoshtary = moshtaryRotbehDAO.getRotbehByccMoshtaryForMoshtaryJadid(ccMoshtary);
//            shared.putInt(shared.getMoshtaryJadidDarajeh() , darajehMoshtary);
//
//            MaxFaktorMandehDarDAO maxFaktorMandehDarDAO= new MaxFaktorMandehDarDAO(mPresenter.getAppContext());
//            ArrayList<MaxFaktorMandehDarModel> maxFaktorMandehDars= maxFaktorMandehDarDAO.getAll();
//            int maxRoozFaktorMandehDarNoeMoshtary = 0;
//            for (MaxFaktorMandehDarModel model : maxFaktorMandehDars)
//            {
//                if (model.getCcMarkazForosh() == foroshandehMamorPakhshModel.getCcMarkazForosh()  && model.getCcGorohMoshtary() == moshtary.getCcNoeMoshtary())
//                {
//                    maxRoozFaktorMandehDarNoeMoshtary = model.getMaxRooz();
//                    shared.putInt(shared.getMaxRoozFaktorMandehDar_NoeMoshtary() , maxRoozFaktorMandehDarNoeMoshtary);
//                    shared.putInt(shared.getMaxTedadFaktorMandehDar_NoeMoshtary() , model.getMaxTedad());
//                    break;
//                }
//            }
//
//            //----------------Amar Forosh.. -----------------------
//            int mianginForosh = 0;
//            RptForoshDAO rptForoshDAO = new RptForoshDAO(mPresenter.getAppContext());
//            ArrayList<RptForoshModel> rptForoshModels = rptForoshDAO.getAll();
//            for (RptForoshModel model : rptForoshModels)
//            {
//                if (model.getCcForoshandeh() == ccForoshandeh)
//                {
//                    mianginForosh = Math.round(model.getMianginForoshRoozMojaz());
//                    shared.putInt(shared.getMianginForosh() , mianginForosh);
//                    break;
//                }
//            }
//
//            //----------------------- Bargashty.. ----------------------------
//            BargashtyDAO bargashtyDAO= new BargashtyDAO(mPresenter.getAppContext());
//            ArrayList<BargashtyModel> bargashtyModels = bargashtyDAO.getAll();
//            float sumBargashty = bargashtyDAO.getSumBargashtyByccMoshtary(ccMoshtary);
//            int tedadChekBargashtyMoshtary = 0;
//            int modatBargashtyMoshtary = bargashtyDAO.getModatBargashtyByccMoshtary(ccMoshtary);
//            double bargashtyMoshtary = 0;
//            int tedadChekBargashtyForoshandeh = 0;
//            double bargashtyForoshandeh = 0;
//            //BargashtyMoshtary......
//            for (BargashtyModel model : bargashtyModels)
//            {
//                if (model.getCcMoshtary() == ccMoshtary)
//                {
//                    tedadChekBargashtyMoshtary += 1;
//                    bargashtyMoshtary += model.getMablaghMandeh();
//                }
//            }
//
//            //BargashtyForoshandeh......
//            long dateDiff = 0;
//            ArrayList<BargashtyModel> bargashtyForoshnadehs = bargashtyDAO.getBargashtyForoshandehBishtarAz5Rooz(ccForoshandeh);
//            for (BargashtyModel model : bargashtyForoshnadehs)
//            {
//                tedadChekBargashtyForoshandeh += 1;
//                bargashtyForoshandeh += model.getMablaghMandeh();
//                Date dateTarikhSanad = new Date();
//                try
//                {
//                    dateTarikhSanad = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).parse(model.getTarikhSanad());
//                }
//                catch (Exception exception)
//                {
//                    exception.printStackTrace();
//                    setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RequestCustomerListModel", "", "setRequestInfoShared", "");
//                }
//
//                dateDiff = (minTarikhSanadBargashty == null ? 0 : new PubFunc().new DateUtils().getDateDiffAsDay(dateTarikhSanad , minTarikhSanadBargashty));
//                if (minTarikhSanadBargashty == null || dateDiff > 0)
//                {
//                    minTarikhSanadBargashty = dateTarikhSanad;
//                }
//            }
//
//            dateDiff = (minTarikhSanadBargashty== null ? 0 : new PubFunc().new DateUtils().getDateDiffAsDay(minTarikhSanadBargashty, new Date()));
//            if (minTarikhSanadBargashty!= null)
//            {
//                shared.putInt(shared.getModatCheckBargashtyForoshandeh() , (int) dateDiff);
//            }
//
//            shared.putInt(shared.getTedadChekBargashtyMoshtary() ,tedadChekBargashtyMoshtary );
//            shared.putInt(shared.getTedadChekBargashtyForoshandeh() , tedadChekBargashtyForoshandeh);
//            shared.putString(shared.getBargashtyMoshtary() , String.valueOf(bargashtyMoshtary));
//            shared.putString(shared.getBargashtyForoshandeh() , String.valueOf(bargashtyForoshandeh));
//            //------------------------------ MandehDar..-----------------------------
//            RptMandehdarDAO rptMandehdarDAO = new RptMandehdarDAO(mPresenter.getAppContext());
//            ArrayList<RptMandehdarModel> rptMandehdarModels = rptMandehdarDAO.getAll();
//            double moavaghForoshandeh = 0;
//            int tedadFaktorMandehDarNoeMoshtary_BishAzRooz= 0;
//
//            for (RptMandehdarModel model : rptMandehdarModels)
//            {
//                if (model.getCcForoshandeh() == ccForoshandeh)
//                {
//                    moavaghForoshandeh += model.getMablaghMandehMoshtary();
//                }
//                if (model.getTarikhErsal() != null)
//                {
//                    Date dateTarikhersal = new Date();
//                    try
//                    {
//                        dateTarikhersal = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).parse(model.getTarikhErsal());
//                    }
//                    catch (Exception exception)
//                    {
//                        exception.printStackTrace();
//                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RequestCustomerListModel", "", "setRequestInfoShared", "");
//                    }
//                    dateDiff = new PubFunc().new DateUtils().getDateDiffAsDay(dateTarikhersal , tarikh);
//                    if (dateDiff > maxRoozFaktorMandehDarNoeMoshtary)
//                    {
//                        tedadFaktorMandehDarNoeMoshtary_BishAzRooz += 1;
//                    }
//                }
//            }
//
//            shared.putString(shared.getMoavaghForoshandeh() , String.valueOf(moavaghForoshandeh));
//            shared.putInt(shared.getTedadFaktorMandehDarNoeMoshtary_BishAzRooz() , tedadFaktorMandehDarNoeMoshtary_BishAzRooz);
//
//            //----------------------------Tedad Resid Vosol Nashodeh------------------------------------
//            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
//            ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByParameterName(Constants.NOE_VOSOL_MOSHTARY());
//            int findedCount = 0;
//            int codeNoeVosolResid = -1;
//            int codeNoeVosolResidNaghd = -1;
//
//            for (ParameterChildModel model : childParameterModels)
//            {
//                if (model.getName().trim().equalsIgnoreCase(Constants.NOE_VOSOL_MOSHTARY_RESID()))
//                {
//                    codeNoeVosolResid = Integer.parseInt(model.getValue());
//                    findedCount++;
//                }
//                else if (model.getName().trim().equalsIgnoreCase(Constants.NOE_VOSOL_MOSHTARY_RESID_NAGHD()))
//                {
//                    codeNoeVosolResidNaghd = Integer.parseInt(model.getValue());
//                    findedCount++;
//                }
//                if (findedCount == 2)
//                {
//                    break;
//                }
//            }
//
//            shared.putInt(shared.getTedadResid() , rptMandehdarDAO.getTedadResid(codeNoeVosolResid , codeNoeVosolResid));
//            shared.putInt(shared.getTedadResidNaghd() , rptMandehdarDAO.getTedadResid(codeNoeVosolResidNaghd , codeNoeVosolResid));
//            //------------------------------------ Asnad.. -----------------------------------
//            float sumAsnad = 0;
//            int countAsnad = 0;
//            int modatAsnad = 0;
//            RptSanadDAO rptSanadDAO = new RptSanadDAO(mPresenter.getAppContext());
//            ArrayList<RptSanadModel> rptSanadModels = rptSanadDAO.getAll();
//            double asnadForoshandeh= 0;
//            for (RptSanadModel model: rptSanadModels)
//            {
//                if (model.getCcForoshandeh() == ccForoshandeh)
//                {
//                    asnadForoshandeh += model.getMablaghTakhsis();
//                }
//            }
//
//            shared.putString(shared.getAsnadForoshandeh() , String.valueOf(asnadForoshandeh));
//            sumAsnad = rptSanadDAO.getSumAsnadByccMoshtary(ccMoshtary);
//            countAsnad = rptSanadDAO.getCountAsnadByccMoshtary(ccMoshtary);
//            modatAsnad = rptSanadDAO.getModatAsnadByccMoshtary(ccMoshtary);
//            double sumForoshandeh= Math.round(moavaghForoshandeh + asnadForoshandeh + bargashtyForoshandeh);
//            int roozVagheii= (int) (mianginForosh!=0 ? (sumForoshandeh / mianginForosh) : 0);
//            shared.putInt(shared.getRoozVagheii() , roozVagheii);
//            //---------------------------------------- MoshtaryEtebarSazmanForosh ----------------------------
//            MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
//            MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = moshtaryEtebarSazmanForoshDAO.getByccMoshtary(ccMoshtary);
//            /*shared.putLong(shared.getEtebarRialy() , moshtaryEtebarSazmanForoshModel.getSaghfEtebarRiali());
//            shared.putInt(shared.getEtebarTedady() , moshtaryEtebarSazmanForoshModel.getTedadMoavagh());*/
//
//            //------------------------------------ Bedehy -------------------------------
//            float sumMoavagh = 0;
//            int tedadMoavagh = rptMandehdarDAO.getTedadFaktorMandehDar(ccMoshtary);
//            int modatMoavagh = rptMandehdarDAO.getMoavaghFaktorMandehDar(ccMoshtary);
//
//            //-------------------Get_SumMoavaghByccMoshtary...
//            //RptMandehdarDAO rpt_MandehDarDao = new Rpt_MandehdarDAO(context);
//            sumMoavagh = rptMandehdarDAO.getSumMoavaghByccMoshtary(ccMoshtary);
//
//            //-------------- GetMablaghFaktorBaz ...
//            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
//            double MablaghFaktorBazRoozMoshtary = darkhastFaktorDAO.getMablaghFaktorBaz(ccMoshtary);
//            sumMoavagh += MablaghFaktorBazRoozMoshtary ;
//            shared.putFloat(shared.getBedehy() , sumMoavagh + sumAsnad + sumBargashty);
//            shared.putFloat(shared.getBedehy() , sumMoavagh + sumAsnad + sumBargashty);
//            shared.putFloat(shared.getEtebarRial() , sumMoavagh);
//            shared.putFloat(shared.getEtebarTedad() , tedadMoavagh);
//            shared.putFloat(shared.getEtebarModat() , modatMoavagh);
//            shared.putFloat(shared.getEtebarBargashty() , sumBargashty);
//            shared.putFloat(shared.getEtebarTedadBargashty() , tedadChekBargashtyMoshtary);
//            shared.putFloat(shared.getEtebarModatBargashty() , modatBargashtyMoshtary);
//            shared.putFloat(shared.getEtebarAsnad() , sumAsnad);
//            shared.putFloat(shared.getEtebarTedadAsnad() , countAsnad);
//            shared.putFloat(shared.getEtebarModatAsnad() , modatAsnad);
//            shared.putFloat(shared.getSumMoavagh() , sumMoavagh);
//            //------------------------- Masir_VaznHajmMashin --------------------------------
//            MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
//            MasirVaznHajmMashinModel masirVaznHajmMashinModel = masirVaznHajmMashinDAO.getByccMoshtary(ccMoshtary);
//            shared.putFloat(shared.getVaznMashin() , masirVaznHajmMashinModel.getVaznMashin() == 0 ? 99999 : masirVaznHajmMashinModel.getVaznMashin());
//            shared.putFloat(shared.getHajmMashin() , masirVaznHajmMashinModel.getHajmMashin() == 0 ? 999 : Float.parseFloat(String.valueOf(masirVaznHajmMashinModel.getHajmMashin())));
//            //----------------------------MoshtaryPolygon------------------------------------
//            /*MoshtaryPolygonDAO moshtaryPolygonDAO = new MoshtaryPolygonDAO(mPresenter.getAppContext());
//            CanVisitKharejAzMahal_Polygon = moshtaryPolygonDAO.GetByccMoshtary(SelectFaktor.getccMoshtary());*/
//
//
//            //------------------------- Masir --------------------------------
//            MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
//            ArrayList<MasirModel> masirModels = masirDAO.getByccMasir(moshtary.getCcMasir());
//
//            if (masirModels.size() > 0)
//            {
//                shared.putInt(shared.getCcMasirRooz() , masirModels.get(0).getCcMasir());
//            }
//            else
//            {
//                ArrayList<MasirModel> allMasirs = masirDAO.getAll();
//                if (allMasirs.size() > 0)
//                {
//                    shared.putInt(shared.getCcMasirRooz() , allMasirs.get(0).getCcMasir());
//                }
//            }
//
//            darkhastFaktorDAO.updateSaateVorodAndTarikhFaktor(ccDarkhastFaktor , currentDateTime , currentDateTime);
//            darkhastFaktorDAO.updateExtraPropShowFaktorMamorPakhsh(ccDarkhastFaktor , 1);
//            shared.putBoolean(shared.getVerifiedMarjoee() , false);
//            shared.putString(shared.getCcKalaCodesOfKalaAsasi(), "");
//
//            mPresenter.onSuccessSetDarkhastFaktorShared(ccDarkhastFaktor , ccMoshtary);
//        }
//        catch (Exception exception)
//        {
//            exception.printStackTrace();
//            mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorOperation);
//            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "setRequestInfoShared", "");
//        }
//    }

    private void setRequestInfoShared(int ccMoshtary , long ccDarkhastFaktor , int ccForoshandeh, int ccMarkazForosh , int ccSazmanForosh ,int ccMarkazSazmanForosh , boolean moshtaryForoshandehFlag , boolean isMojazForResid , boolean isEtebarCheckBargashty , int ccChildParameterNoeVosol, PubFunc.LocationProvider googleLocationProvider, long MablaghKhalesHavaleh)
    {
        try
        {
            Date minTarikhSanadBargashty = null;
            Date tarikh = new Date();
            String currentDateTime = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());

            shared.removeAll();
            shared.setDefaultRequestInfo();
            Log.d("setRequestInfoShared" , "ccDarkhastFaktor : " + ccDarkhastFaktor);
            Log.d("setRequestInfoShared" , "MablaghKhalesHavaleh : " + MablaghKhalesHavaleh);

            shared.putLong(shared.getCcDarkhastFaktor() , ccDarkhastFaktor);
            shared.putBoolean(shared.getInsertDarkhastFaktorSatrForMamorPakhsh() , false);
            shared.putInt(shared.getCcMoshtary() , ccMoshtary);
            shared.putInt(shared.getCcForoshandeh() , ccForoshandeh);
            shared.putBoolean(shared.getMoshtaryForoshandehFlag() , moshtaryForoshandehFlag);
            shared.putBoolean(shared.getIsMojazForResid() , isMojazForResid);
            shared.putBoolean(shared.getIsEtebarCheckBargashty() , isEtebarCheckBargashty);
            shared.putInt(shared.getccChildCodeNoeVosol() , ccChildParameterNoeVosol);
            shared.putLong(shared.getMablaghHavaleh() , MablaghKhalesHavaleh);

            shared.putInt(shared.getCcMarkazForosh() , ccMarkazForosh);
            shared.putInt(shared.getCcSazmanForosh() , ccSazmanForosh);
            shared.putInt(shared.getCcMarkazSazmanForosh() , ccMarkazSazmanForosh);

            shared.putString(shared.getLatitude(), String.valueOf((float) googleLocationProvider.getLatitude()));
            shared.putString(shared.getLongitude(), String.valueOf((float) googleLocationProvider.getLongitude()));
            shared.putString(shared.getAltitude(), String.valueOf(googleLocationProvider.getAltitude()));
            shared.putFloat(shared.getAccurancy(), googleLocationProvider.getAccuracy());
            shared.putFloat(shared.getBearing(), googleLocationProvider.getBearing());
            shared.putFloat(shared.getSpeed(), googleLocationProvider.getSpeed());
            shared.putString(shared.getSaatVorodBeMaghazeh() , currentDateTime);
            shared.putString(shared.getSaatKhorojAzMaghazeh() , currentDateTime);

            //----------------------------- CodeMely -------------------
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
            MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(ccMoshtary);
            shared.putString(shared.getCodeMely(), moshtary.getCodeMely());
            shared.putString(shared.getShenasehMely(), moshtary.getShenasehMely());
            shared.putInt(shared.getCcMoshtaryParent(), moshtary.getccMoshtaryParent());
            shared.putInt(shared.getMoshtaryDarajeh(), moshtary.getDarajeh());
            //CodePosty = moshtary.getCodePosty();

            //------------------------------------- MojoodiGiri..-------------------------------------
            MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(mPresenter.getAppContext());
            int count = mojoodiGiriDAO.getCountMojodiGiriByMoshtaryForCheck(ccMoshtary, true);
            boolean haveMojoodiGiri = count != 0;
            shared.putBoolean(shared.getHaveMojoodiGiri(), haveMojoodiGiri);

            //------------------------------------- MoshtaryGoroh..-------------------------------------
            shared.putInt(shared.getCcGorohNoeMoshtary(), moshtary.getCcNoeMoshtary());
            shared.putInt(shared.getCcGorohNoeSenf(), moshtary.getCcNoeSenf());

            NoeMoshtaryRialKharidDAO moshtaryRialKharidDAO = new NoeMoshtaryRialKharidDAO(mPresenter.getAppContext());

            long hadaghalMablaghKharid = moshtaryRialKharidDAO.getMablaghByccMoshtary(moshtary.getCcNoeMoshtary(), moshtary.getDarajeh());
            long hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid = 0;
            long mablaghMaxFaktorKhordehMoshtaryJadid = 0;
            long mablaghMaxFaktorOmdehMoshtaryJadid = 0;

            String ccChildParameters = Constants.CC_CHILD_HADAGHAL_MABLAGH_KHARID_MOSHTARY_JADID + "," + Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_KHORDEH_MOSHTARY_JADID + "," + Constants.CC_CHILD_MABLAGH_MAX_FAKTOR_OMDEH_MOSHTARY_JADID;
            ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
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
            }

            shared.putFloat(shared.getHadeAghalMablaghKharid() , hadaghalMablaghKharid);
            shared.putLong(shared.getHadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid() , hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid);
            shared.putLong(shared.getMablaghMaxFaktorKhordeh_MoshtaryJadid() , mablaghMaxFaktorKhordehMoshtaryJadid);
            shared.putLong(shared.getMablaghMaxFaktorOmdeh_MoshtaryJadid() , mablaghMaxFaktorOmdehMoshtaryJadid);

            //-------------------MoshtaryEtebarSzmanForosh.. ------------------
            MoshtaryEtebarSazmanForoshDAO moshtaryetebarsazmanforoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
            MoshtaryEtebarSazmanForoshModel moshtaryetebarsazmanforosh = moshtaryetebarsazmanforoshDAO.getByccMoshtary(ccMoshtary);

            shared.putLong(shared.getRialCheckBargashty_NoeMoshtary() , moshtaryetebarsazmanforosh.getRialBargashty());
            shared.putInt(shared.getTedadCheckBargashty_NoeMoshtary() , moshtaryetebarsazmanforosh.getTedadBargashty());
            shared.putInt(shared.getModatCheckBargashty_NoeMoshtary() , moshtaryetebarsazmanforosh.getModatBargashty());

            //------------------------ HadaghalMablaghKharidMoshtary ----------------------------
            MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(mPresenter.getAppContext());
            int darajehMoshtary = moshtaryRotbehDAO.getRotbehByccMoshtaryForMoshtaryJadid(ccMoshtary);
            shared.putInt(shared.getMoshtaryJadidDarajeh() , darajehMoshtary);

            MaxFaktorMandehDarDAO maxFaktorMandehDarDAO= new MaxFaktorMandehDarDAO(mPresenter.getAppContext());
            ArrayList<MaxFaktorMandehDarModel> maxFaktorMandehDars= maxFaktorMandehDarDAO.getAll();
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
            RptForoshDAO rptForoshDAO = new RptForoshDAO(mPresenter.getAppContext());
            ArrayList<RptForoshModel> rptForoshModels = rptForoshDAO.getAll();
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
            BargashtyDAO bargashtyDAO= new BargashtyDAO(mPresenter.getAppContext());
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
                    setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RequestCustomerListModel", "", "setRequestInfoShared", "");
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
            RptMandehdarDAO rptMandehdarDAO = new RptMandehdarDAO(mPresenter.getAppContext());
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
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RequestCustomerListModel", "", "setRequestInfoShared", "");
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
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
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
            RptSanadDAO rptSanadDAO = new RptSanadDAO(mPresenter.getAppContext());
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
            MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
            MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = moshtaryEtebarSazmanForoshDAO.getByccMoshtary(ccMoshtary);
            /*shared.putLong(shared.getEtebarRialy() , moshtaryEtebarSazmanForoshModel.getSaghfEtebarRiali());
            shared.putInt(shared.getEtebarTedady() , moshtaryEtebarSazmanForoshModel.getTedadMoavagh());*/

            //------------------------------------ Bedehy -------------------------------
            float sumMoavagh = 0;
            int tedadMoavagh = rptMandehdarDAO.getTedadFaktorMandehDar(ccMoshtary);
            int modatMoavagh = rptMandehdarDAO.getMoavaghFaktorMandehDar(ccMoshtary);

            //-------------------Get_SumMoavaghByccMoshtary...
            //RptMandehdarDAO rpt_MandehDarDao = new Rpt_MandehdarDAO(context);
            sumMoavagh = rptMandehdarDAO.getSumMoavaghByccMoshtary(ccMoshtary);

            //-------------- GetMablaghFaktorBaz ...
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            double MablaghFaktorBazRoozMoshtary = darkhastFaktorDAO.getMablaghFaktorBaz(ccMoshtary);
            sumMoavagh += MablaghFaktorBazRoozMoshtary ;
            shared.putFloat(shared.getBedehy() , sumMoavagh + sumAsnad + sumBargashty);
            shared.putFloat(shared.getEtebarRial() , sumMoavagh);
            shared.putFloat(shared.getEtebarTedad() , tedadMoavagh);
            shared.putFloat(shared.getEtebarModat() , modatMoavagh);
            shared.putFloat(shared.getEtebarBargashty() , sumBargashty);
            shared.putFloat(shared.getEtebarTedadBargashty() , tedadChekBargashtyMoshtary);
            shared.putFloat(shared.getEtebarModatBargashty() , modatBargashtyMoshtary);
            shared.putFloat(shared.getEtebarAsnad() , sumAsnad);
            shared.putFloat(shared.getEtebarTedadAsnad() , countAsnad);
            shared.putFloat(shared.getEtebarModatAsnad() , modatAsnad);
            shared.putFloat(shared.getSumMoavagh() , sumMoavagh);
            //------------------------- Masir_VaznHajmMashin --------------------------------
            MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
            MasirVaznHajmMashinModel masirVaznHajmMashinModel = masirVaznHajmMashinDAO.getByccMoshtary(ccMoshtary);
            shared.putFloat(shared.getVaznMashin() , masirVaznHajmMashinModel.getVaznMashin() == 0 ? 99999 : masirVaznHajmMashinModel.getVaznMashin());
            shared.putFloat(shared.getHajmMashin() , masirVaznHajmMashinModel.getHajmMashin() == 0 ? 999 : Float.parseFloat(String.valueOf(masirVaznHajmMashinModel.getHajmMashin())));
            //----------------------------MoshtaryPolygon------------------------------------
            /*MoshtaryPolygonDAO moshtaryPolygonDAO = new MoshtaryPolygonDAO(mPresenter.getAppContext());
            CanVisitKharejAzMahal_Polygon = moshtaryPolygonDAO.GetByccMoshtary(SelectFaktor.getccMoshtary());*/


            //------------------------- Masir --------------------------------
            MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
            ArrayList<MasirModel> masirModels = masirDAO.getByccMasir(moshtary.getCcMasir());

            if (masirModels.size() > 0)
            {
                shared.putInt(shared.getCcMasirRooz() , masirModels.get(0).getCcMasir());
            }
            else
            {
                ArrayList<MasirModel> allMasirs = masirDAO.getAll();
                if (allMasirs.size() > 0)
                {
                    shared.putInt(shared.getCcMasirRooz() , allMasirs.get(0).getCcMasir());
                }
            }

            darkhastFaktorDAO.updateSaateVorodAndTarikhFaktor(ccDarkhastFaktor , currentDateTime , currentDateTime);
            darkhastFaktorDAO.updateExtraPropShowFaktorMamorPakhsh(ccDarkhastFaktor , 1);
            shared.putBoolean(shared.getVerifiedMarjoee() , false);
            shared.putString(shared.getCcKalaCodesOfKalaAsasi(), "");

            mPresenter.onSuccessSetDarkhastFaktorShared(ccDarkhastFaktor , ccMoshtary);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.onFailedSetDarkhastFaktorShared(R.string.errorOperation);
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TreasuryListModel", "", "setRequestInfoShared", "");
        }
    }

    private void getRouting(final ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels)
    {

        try {
            if (darkhastFaktorMoshtaryForoshandeModels.size() > 0) {
                final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
                final int noeMasouliat = getNoeMasouliatWithReturnData();
                String requestId = "";
                if (foroshandehMamorPakhshModel != null) {
                    requestId = String.valueOf(foroshandehMamorPakhshModel.getCcAfrad());
                }
                requestId += " - " + new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());

                JSONObject jsonObjectAllData = new JSONObject();
                JSONArray jsonArrayTargetsLocation = new JSONArray();
                final ArrayList<DarkhastFaktorMoshtaryForoshandeModel> cantEditDarkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();
                final ArrayList<DarkhastFaktorMoshtaryForoshandeModel> unEditDarkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();

                for (DarkhastFaktorMoshtaryForoshandeModel model : darkhastFaktorMoshtaryForoshandeModels) {
                    double[] location = getLocation(model.getCcAfradForoshandeh(), model.getCcMoshtary(), model.getCcUser(), model.getLatitude(), model.getLongitude());
                    model.setLatitude((float) location[0]);
                    model.setLongitude((float) location[1]);
                    //if (canEditDarkhast(noeMasouliat , model))
                    if (model.isCanEditDarkhast()) {
                        unEditDarkhastFaktorMoshtaryForoshandeModels.add(model);
                        JSONObject jsonObjectTarget = getJsonObjectTargetLocation(model.getLatitude(), model.getLongitude());
                        if (jsonObjectTarget != null) {
                            jsonArrayTargetsLocation.put(jsonObjectTarget);
                        } else {
                            mPresenter.onErrorGetCustomerLocation(R.string.errorGetCustomerLocation, model.getFullNameMoshtary());
                            return;
                        }
                    } else {
                        cantEditDarkhastFaktorMoshtaryForoshandeModels.add(model);
                    }
                }


                final JSONArray jsonArraySourceLocation = getJsonArraySourceLocation();
                if (jsonArraySourceLocation == null || jsonArraySourceLocation.length() == 0) {
                    mPresenter.onError(R.string.errorGetLocation);
                    return;
                }
                jsonObjectAllData.put("sources", jsonArraySourceLocation);
                jsonObjectAllData.put("targets", jsonArrayTargetsLocation);
                jsonObjectAllData.put("costing", "auto");

                Log.d("treasury", "json All data : " + jsonObjectAllData.toString());

                RoutingServerShared routingServerShared = new RoutingServerShared(BaseApplication.getContext());
                String urlOsrm = routingServerShared.getString(RoutingServerShared.IP, "http://91.92.125.244:8002");
                Log.d("urlOsrm", urlOsrm.substring(0, 11));
                if (urlOsrm.length() > 0) {
                    APIServiceValhalla apiServiceValhalla = ApiClientGlobal.getInstance().getClientServiceValhalla(urlOsrm);
                    Call<Object> call = apiServiceValhalla.getSourcesToTargets(jsonObjectAllData.toString(), requestId);
                    call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            try {
                                Gson gson = new Gson();
                                if (response.isSuccessful()) {
                                    SourceToTargetSuccessResult result = gson.fromJson(gson.toJson(response.body()), SourceToTargetSuccessResult.class);
                                    if (result != null) {
                                        if (result.getSources() != null && result.getSources().size() > 0 && result.getSourcesToTargets() != null && result.getSourcesToTargets().size() > 0) {
                                            sortAndInsertToDatabase(unEditDarkhastFaktorMoshtaryForoshandeModels, cantEditDarkhastFaktorMoshtaryForoshandeModels, result.getSourcesToTargets().get(0), jsonArraySourceLocation.getJSONObject(0).getDouble("lat"), jsonArraySourceLocation.getJSONObject(0).getDouble("lon"), noeMasouliat);
                                        } else {
                                            PubFunc.Logger logger = new PubFunc().new Logger();
                                            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), "error get sources or sources-to-targets", CLASS_NAME, "", "getRouting", "onResponse");
                                            mPresenter.onError(R.string.errorGetData);
                                        }
                                    } else {
                                        String endpoint = getEndpoint(call);
                                        PubFunc.Logger logger = new PubFunc().new Logger();
                                        logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", mPresenter.getAppContext().getString(R.string.resultIsNull), endpoint), CLASS_NAME, "", "getRouting", "onResponse");
                                        mPresenter.onError(R.string.errorGetData);
                                    }
                                } else {
                                    SourcesToTargetsFailedResult responseError = gson.fromJson(gson.toJson(response), SourcesToTargetsFailedResult.class);

                                    String endpoint = getEndpoint(call);
                                    String message = String.format("message : %1$s \n code : %2$s * %3$s", responseError.getError(), responseError.getErrorCode(), endpoint);
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, CLASS_NAME, "", "getRouting", "onResponse");
                                    mPresenter.onError(R.string.errorGetData);
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "", "getRouting", "onResponse");
                                mPresenter.onError(R.string.errorGetData);
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            String endpoint = getEndpoint(call);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, "", "getRouting", "onFailure");
                            mPresenter.onError(R.string.errorGetData);
                        }
                    });
                } else {
                    mPresenter.onError(R.string.cantFindServer);
                }
            } else {
                mPresenter.onWarning(R.string.emptyList);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "getRouting", "");
            mPresenter.onError(R.string.errorOccurred);
        }
    }


    /**
     *
     * @param unEditDarkhastFaktorMoshtaryForoshandeModels لیست همه درخواست فاکتورها
     * @param cantEditDarkhastFaktorMoshtaryForoshandeModels لیست درخواست فاکتورهایی که ارسال کرده و امکان ویرایش آن ها را ندارد و به وب سرویس مسیریابی هم ارسال نشده اند.
     * @param sourcesToTargetsData لیست موقعیت های جغرافیایی مسیریابی شده(درخواست فاکتورهایی برای مسیریابی ارسال می شوند که امکان ویرایش داشته باشند یعنی ارسال نشده اند.)
     * @param noeMasouliat نوع مسئولیت فروشنده مامورپخش
     */
    private void sortAndInsertToDatabase(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> unEditDarkhastFaktorMoshtaryForoshandeModels, ArrayList<DarkhastFaktorMoshtaryForoshandeModel> cantEditDarkhastFaktorMoshtaryForoshandeModels, ArrayList<SourcesToTargetData> sourcesToTargetsData, double currentLocationLat, double currentLocationLong  , int noeMasouliat)
    {
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(BaseApplication.getContext());
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
        ArrayList<Integer> ccMoshtarys = new ArrayList<>();
        DarkhastFaktorRoozSortDAO darkhastFaktorRoozSortDAO = new DarkhastFaktorRoozSortDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> SortedDarkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();
        darkhastFaktorRoozSortDAO.deleteAll();
        Collections.sort(sourcesToTargetsData);
        for (int i = 0 ; i < sourcesToTargetsData.size() ; i++)
        {
            SourcesToTargetData model = sourcesToTargetsData.get(i);
            // add sorted item in new arrarylist
            SortedDarkhastFaktorMoshtaryForoshandeModels.add(unEditDarkhastFaktorMoshtaryForoshandeModels.get(model.getToIndex()));
            ccMoshtarys.add(unEditDarkhastFaktorMoshtaryForoshandeModels.get(model.getToIndex()).getCcMoshtary());
            // insert darkhastFaktorRoozSort
            DarkhastFaktorRoozSortModel darkhastFaktorRoozSortModel = new DarkhastFaktorRoozSortModel();
            darkhastFaktorRoozSortModel.setCcDarkhastFaktor(unEditDarkhastFaktorMoshtaryForoshandeModels.get(model.getToIndex()).getCcDarkhastFaktor());
            darkhastFaktorRoozSortModel.setSort(i+1);
            darkhastFaktorRoozSortDAO.insert(darkhastFaktorRoozSortModel);
        }
        for (int i = 0 ; i < ccMoshtarys.size() ; i++){
            ArrayList<MoshtaryAddressModel> models = moshtaryAddressDAO.getByccMoshtary(ccMoshtarys.get(i));
            moshtaryAddressModels.add(models.get(0));
        }
        mPresenter.onGetTodayTreasuryList(SortedDarkhastFaktorMoshtaryForoshandeModels, cantEditDarkhastFaktorMoshtaryForoshandeModels, currentLocationLat, currentLocationLong, Constants.SORT_TREASURY_BY_ROUTING,moshtaryAddressModels);
    }


    private String getEndpoint(Call call)
    {
        String endpoint = "";
        try
        {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
        }
        catch (Exception e)
        {e.printStackTrace();}
        return endpoint;
    }


    private JSONArray getJsonArraySourceLocation()
    {
        try
        {
            JSONArray jsonArraySource = new JSONArray();
            // create json array of source location
            PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();
            JSONObject jsonObjectSource = new JSONObject();
            jsonObjectSource.put("lat" , locationProvider.getLatitude());
            jsonObjectSource.put("lon" , locationProvider.getLongitude());
            jsonArraySource.put(jsonObjectSource);
            return jsonArraySource;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    private JSONObject getJsonObjectTargetLocation(float lat , float lng)
    {
        JSONObject jsonObjectTargetLocation = new JSONObject();
        try
        {
            jsonObjectTargetLocation.put("lat" , lat);
            jsonObjectTargetLocation.put("lon" , lng);
            return jsonObjectTargetLocation;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }



    /**
     *  بررسی و دریافت موقعیت مکانی فاکتور
     * @param ccAfradForoshandeh
     * @param ccUserMoshtary
     * @param latitudeDarkhastFaktor
     * @param longitudeDarkhastFaktor
     * @return double[] -> [0] = latitude , [1] longitude
     */
    private double[] getLocation(int ccAfradForoshandeh, int ccMohstary, int ccUserMoshtary, double latitudeDarkhastFaktor, double longitudeDarkhastFaktor)
    {
        if (ccAfradForoshandeh == ccUserMoshtary && (latitudeDarkhastFaktor != 0 && longitudeDarkhastFaktor != 0))
        {
            return new double[]{latitudeDarkhastFaktor , longitudeDarkhastFaktor};
        }
        else
        {
            MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
            ArrayList<ParameterChildModel> parameterChildModelsNoeAddress = childParameterDAO.getAllByccChildParameter(Constants.CC_CHILD_CC_NOE_ADDRESS_DARKHAST_TAHVIL() + "," + Constants.CC_CHILD_CC_NOE_ADDRESS_TAHVIL());
            String ccNoeAddresses = "-1";
            if (parameterChildModelsNoeAddress.size() > 0)
            {
                for (ParameterChildModel model : parameterChildModelsNoeAddress)
                {
                    ccNoeAddresses += "," + model.getValue();
                }
            }
            else
            {
                ccNoeAddresses = "2,3";
            }
            ArrayList<MoshtaryAddressModel> addressModels = moshtaryAddressDAO.getByccMoshtaryAndccNoeAddress(ccMohstary , ccNoeAddresses);
            if (addressModels.size() > 0)
            {
                return new double[]{addressModels.get(0).getLatitude_y() , addressModels.get(0).getLongitude_x()};
            }
            else
            {
                return new double[]{0.0 , 0.0};
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

    /**
     * get sort list in systemConfigTabletDAO
     */
    @Override
    public void getSortList() {
       int sort = systemConfigTabletDAO.getSortList();
       mPresenter.onGetSortList(sort);
    }

    @Override
    public void checkMoshtaryKharejAzMahal(int noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel) {


        if ((noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD
                || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART)
                && darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktorNoeForosh() == 1) {
            boolean checkDistance = true;
            ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
            checkDistance = parameterChildDAO.getValueByccChildParameter(Constants.CHECK_MOSHTARY_MASIR_ROOZ_DISTANCE()).equals("1");
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
            if (checkDistance) {
                if (isValidCreateFaktor(darkhastFaktorMoshtaryForoshandeModel, foroshandehMamorPakhshModel)) {
                    sendGps(noeMasouliat, foroshandehMamorPakhshModel, darkhastFaktorMoshtaryForoshandeModel);

                } else {

                    mPresenter.onError(R.string.errorCantRegisterRequest);
                }
            } else {
                sendGps(noeMasouliat, foroshandehMamorPakhshModel, darkhastFaktorMoshtaryForoshandeModel);
            }
        }
    }

    @Override
    public void checkIsLocationSendToServer(int noeMasouliat, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel) {
        final Handler handler = new Handler(msg -> {
            if (msg.arg1 == 1) {
                mPresenter.onOpenInvoiceSettlement(darkhastFaktorMoshtaryForoshandeModel, true);

            } else if (msg.arg1 == -1) {
                mPresenter.onOpenInvoiceSettlement(darkhastFaktorMoshtaryForoshandeModel, false);
            }
            return true;
        });
        Message message = new Message();

        new Thread(() -> {

            UserTypeShared userTypeShared = new UserTypeShared(mPresenter.getAppContext());

            int isTest = userTypeShared.getInt(userTypeShared.USER_TYPE(), 0);
            if (isTest == 0) {

                if ((darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktorNoeForosh() == 1) &&
                        (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART
                                || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD)) {
                    GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());

                    message.arg1 = 1;
                    ArrayList<GPSDataModel> gpsDataModels = gpsDataPpcDAO.getAllByccMoshtary(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
                    if (gpsDataModels.size() > 0) {

                        for (GPSDataModel gpsDataModel : gpsDataModels) {
                            if (gpsDataModel.getExtraProp_IsSend() == 0) {
                                message.arg1 = -1;
                                break;
                            }
                        }
                    } else {
                        message.arg1 = -1;
                    }

                } else {
                    message.arg1 = 1;
                }
            } else {
                message.arg1 = 1;

            }
            handler.sendMessage(message);
        }).start();
    }

    private void sendGps(int noeMasouliat, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel) {
        String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();

        int ccForoshandehMamorPakhsh = 0;
        if (noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.MAMOR_PAKHSH_SMART) {
            ccForoshandehMamorPakhsh = foroshandehMamorPakhshModel.getCcMamorPakhsh();
        } else {
            ccForoshandehMamorPakhsh = foroshandehMamorPakhshModel.getCcForoshandeh();
        }
        insertGpsData(ccForoshandehMamorPakhsh, foroshandehMamorPakhshModel.getCcAfrad(), currentDate, locationProvider.getLatitude(), locationProvider.getLongitude(), foroshandehMamorPakhshModel.getCcMamorPakhsh(), darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary(), darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor());
        GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
        String serverIP = serverIpModel.getServerIp();
        String serverPort = serverIpModel.getPort();
        if (serverIP.trim().equals("") || serverPort.trim().equals("")) {
            mPresenter.onError(R.string.errorFindServerIP);
        } else {
            final APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);
           // ArrayList<GPSDataModel> gpsDataModels = gpsDataPpcDAO.getAllByccMoshtary(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
            ArrayList<GPSDataModel> gpsDataModels = gpsDataPpcDAO.getAllNotSend();
            if (gpsDataModels.size() > 0) {
                sendGPSDataToServer(apiServicePost, gpsDataModels);
            }
        }
    }
    private boolean insertGpsData(int ccForoshandeh, int ccAfrad, String currentDate, double latitude, double longtitude, int ccMamorPakhsh, int ccMoshtary, long ccDarkhastFaktor) {
        GPSDataModel gpsDataModel = new GPSDataModel();
        GPSDataPpcDAO gpsDataDAO = new GPSDataPpcDAO(mPresenter.getAppContext());
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
        gpsDataModel.setCcAfrad(ccAfrad);
        gpsDataModel.setCcForoshandeh(ccForoshandeh);
        gpsDataModel.setCcMasir(moshtaryModel.getCcMasir());
        gpsDataModel.setTarikh(currentDate);
        gpsDataModel.setLatitude(latitude);
        gpsDataModel.setLongitude(longtitude);
        gpsDataModel.setExtraProp_IsSend(0);
        gpsDataModel.setDistance(0D);
        gpsDataModel.setCcMamorPakhsh(ccMamorPakhsh);
        gpsDataModel.setCcMoshtary(ccMoshtary);
        gpsDataModel.setCcDarkhastFaktor(ccDarkhastFaktor);
        return gpsDataDAO.insert(gpsDataModel);
    }

    private void sendGPSDataToServer(APIServicePost apiServicePost, ArrayList<GPSDataModel> gpsDataModels) {
        JSONArray jsonArray = new JSONArray();
        String ccGPSDatas = "-1";
        for (GPSDataModel model : gpsDataModels) {
            JSONObject jsonObject = model.toJsonObject();
            if (jsonObject != null) {
                jsonArray.put(jsonObject);
                ccGPSDatas += "," + model.getCcGpsData_PPC();
            }
        }
        //به دلیل اینکه برای استفاده از شناسه موقعیت مکانی های ارسال شده به سرور در کالبک باید متغییر final داشته باشیم و اگر ccGPSDatas را final تعریف کنیم سپس امکان افزودن شناسه های جدید را نداریم، از این فیلد استفاده میکنیم.
        final String ccGPSDatasFinal = ccGPSDatas;
        if (jsonArray.length() > 0) {
            Call<CreateGpsDataPPCResult> call = apiServicePost.createGpsDataPPCResult(jsonArray.toString());
            call.enqueue(new Callback<CreateGpsDataPPCResult>() {
                @Override
                public void onResponse(Call<CreateGpsDataPPCResult> call, Response<CreateGpsDataPPCResult> response) {
                    try {

                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("noTemp", "in if success and body not null");
                            CreateGpsDataPPCResult result = response.body();
                            if (result.getSuccess()) {
                                GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());
                                gpsDataPpcDAO.updateIsSend(ccGPSDatasFinal);
                                mPresenter.onSuccess(R.string.successSendData);
                            } else {
                                Log.d("noTemp", "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "", "sendGPSDataToServer", "onResponse");
                                mPresenter.onError(R.string.errorSendGpsData);
                            }
                        } else {
                            String errorMessage = "response not successful " + response.message();//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null) {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string();//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "", "sendGPSDataToServer", "onResponse");
                            Log.d("tempRequest", "message : " + errorMessage);
                            mPresenter.onError(R.string.errorSendGpsData);
                        }
                    } catch (Exception exception) {
                        Log.d("noTemp", "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "", "sendGPSDataToServer", "onResponse");
                        mPresenter.onError(R.string.errorSendGpsData);
                    }
                }

                @Override
                public void onFailure(Call<CreateGpsDataPPCResult> call, Throwable t) {

                    Log.d("noTemp", "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "", "sendGPSDataToServer", "onFailure");
                    mPresenter.onError(R.string.errorSendGpsData);
                }
            });
        }


    }

}
