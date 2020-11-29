package com.saphamrah.MVP.Model;


import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.saphamrah.BaseMVP.TreasuryListMVP;
import com.saphamrah.BaseMVP.TreasuryListOfflineMVP;
import com.saphamrah.DAO.AdamDarkhastDAO;
import com.saphamrah.DAO.AnbarakAfradDAO;
import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.DAO.CodeNoeVosolDAO;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorEmzaMoshtaryDAO;
import com.saphamrah.DAO.DarkhastFaktorMoshtaryForoshandeDAO;
import com.saphamrah.DAO.DarkhastFaktorRoozSortDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.EtebarDAO;
import com.saphamrah.DAO.ForoshandehAmoozeshiDeviceNumberDAO;
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
import com.saphamrah.DAO.MoshtaryRotbehDAO;
import com.saphamrah.DAO.NoeMoshtaryRialKharidDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.RptForoshDAO;
import com.saphamrah.DAO.RptMandehdarDAO;
import com.saphamrah.DAO.RptSanadDAO;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.CodeNoeVosolModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorRoozSortModel;
import com.saphamrah.Model.EtebarModel;
import com.saphamrah.Model.ForoshandehAmoozeshiModel;
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
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.RptSanadModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.GetProgramShared;
import com.saphamrah.Shared.RoutingServerShared;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Valhalla.SourceToTargetSuccessResult;
import com.saphamrah.Valhalla.SourcesToTargetData;
import com.saphamrah.Valhalla.SourcesToTargetsFailedResult;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.APIServiceValhalla;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ApiClientValhalla;
import com.saphamrah.WebService.ServiceResponse.CreateDariaftPardakhtPPCJSONResult;
import com.saphamrah.WebService.ServiceResponse.GetLoginInfoCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreasuryListOfflineModel implements TreasuryListOfflineMVP.ModelOps
{

    private TreasuryListOfflineMVP.RequiredPresenterOps mPresenter;


    public TreasuryListOfflineModel(TreasuryListOfflineMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void checkDateAndFakeLocation()
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
            String serverIP = serverIPShared.getString(serverIPShared.IP() , "");
            String port = serverIPShared.getString(serverIPShared.PORT() , "");
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
                        setLogToDB(Constants.LOG_EXCEPTION(), error, "TreasuryListModel", "", "getServerTime", "onFailure");
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


    /**
     * لیست وصول مانده دار و یا لیست وصول امروز
     * @param faktorRooz 0 -> today , 1 -> last
     */
    @Override
    public void getTreasuryList(int faktorRooz, int sortType)
    {
        DarkhastFaktorMoshtaryForoshandeDAO darkhastFaktorMoshtaryForoshandeDAO = new DarkhastFaktorMoshtaryForoshandeDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();
        int noeMasouliat = getNoeMasouliatForoshandeh();
        if (faktorRooz == 0)
        {
            if (sortType == Constants.SORT_TREASURY_BY_CUSTOMER_CODE)
            {
                darkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeDAO.getAll(faktorRooz);
            }
            else
            {
                DarkhastFaktorRoozSortDAO darkhastFaktorRoozSortDAO = new DarkhastFaktorRoozSortDAO(mPresenter.getAppContext());
                int getCountDarkhastFaktorSort = darkhastFaktorRoozSortDAO.getCount();
                if (getCountDarkhastFaktorSort > 0)
                {
                    darkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeDAO.getAllOrderByRoutingSort(faktorRooz);
                }
                else
                {
                    darkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeDAO.getAll(faktorRooz);
                }
            }
        }
        else if (faktorRooz == 1)
        {
            darkhastFaktorMoshtaryForoshandeModels = darkhastFaktorMoshtaryForoshandeDAO.getAll(faktorRooz);
        }
        mPresenter.onGetFaktorRooz(darkhastFaktorMoshtaryForoshandeModels , faktorRooz , noeMasouliat);
    }


    private int getNoeMasouliatForoshandeh()
    {
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne();
        int noeMasouliat = -1;
        if (foroshandehMamorPakhshModel != null)
        {
            noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        }
        return noeMasouliat;
    }





    @Override
    public void getCustomerLocation(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        /*MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        MoshtaryAddressModel moshtaryAddressModel = moshtaryAddressDAO.getTopOneAddress(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
        Log.d("customerLocation" , "moshtaryAddress : " + moshtaryAddressModel.getLatitude_y() + " , " + moshtaryAddressModel.getLatitude_y());
        mPresenter.onGetCustomerAddress(moshtaryAddressModel.getLatitude_y() , moshtaryAddressModel.getLongitude_x());*/


        double[] location = getLocation(darkhastFaktorMoshtaryForoshandeModel.getCcAfradForoshandeh(), darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary(),
                darkhastFaktorMoshtaryForoshandeModel.getCcUser(), darkhastFaktorMoshtaryForoshandeModel.getLatitude(), darkhastFaktorMoshtaryForoshandeModel.getLongitude());
        mPresenter.onGetCustomerAddress(location[0] , location[1]);

        /*if (darkhastFaktorMoshtaryForoshandeModel.getCcAfradForoshandeh() == darkhastFaktorMoshtaryForoshandeModel.getCcUser())
        {
            Log.d("customerLocation" , "equal : " + darkhastFaktorMoshtaryForoshandeModel.getLatitude() + " , " + darkhastFaktorMoshtaryForoshandeModel.getLongitude());
            mPresenter.onGetCustomerAddress(darkhastFaktorMoshtaryForoshandeModel.getLatitude() , darkhastFaktorMoshtaryForoshandeModel.getLongitude());
        }
        else
        {
            MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
            MoshtaryAddressModel moshtaryAddressModel = moshtaryAddressDAO.getTopOneAddress(darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
            Log.d("customerLocation" , "not equal : " + moshtaryAddressModel.getLatitude_y() + " , " + moshtaryAddressModel.getLatitude_y());
            mPresenter.onGetCustomerAddress(moshtaryAddressModel.getLatitude_y() , moshtaryAddressModel.getLongitude_x());
        }*/
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


}
