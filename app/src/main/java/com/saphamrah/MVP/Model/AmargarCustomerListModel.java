package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.util.Log;

import com.saphamrah.BaseMVP.AmargarCustomerListMVP;
import com.saphamrah.DAO.AmargarMarkazSazmanForoshDAO;
import com.saphamrah.DAO.ElatAdamMoarefiMoshtaryDAO;
import com.saphamrah.DAO.ForoshandehDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.ListMoshtarianDAO;
import com.saphamrah.DAO.MasirDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.PorseshnamehDAO;
import com.saphamrah.DAO.PorseshnamehShomareshDAO;
import com.saphamrah.DAO.PorseshnamehTablighatDAO;
import com.saphamrah.DAO.VisitMoshtaryDAO;
import com.saphamrah.Model.AmargarMarkazSazmanForoshModel;
import com.saphamrah.Model.ElatAdamMoarefiMoshtaryModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.Model.PorseshnamehShomareshModel;
import com.saphamrah.Model.PorseshnamehTablighatModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.VisitMoshtaryModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.DeviceInfo;
import com.saphamrah.PubFunc.FileUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.PorseshnamehInfoShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Valhalla.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmargarCustomerListModel implements AmargarCustomerListMVP.ModelOps
{

    private AmargarCustomerListMVP.RequiredPresenterOps mPresenter;
    private static final String CLASS_NAME = "AmargarCustomerListModel";

    public AmargarCustomerListModel(AmargarCustomerListMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getAllCustomers()
    {
        List<ListMoshtarianModel> listMoshtarianModels = new ListMoshtarianDAO(mPresenter.getAppContext()).getAll();
        mPresenter.onGetAllCustomers(listMoshtarianModels);
    }

    @Override
    public void getAmargarMarkazForosh()
    {
        AmargarMarkazSazmanForoshDAO amargarMarkazSazmanForoshDAO = new AmargarMarkazSazmanForoshDAO(mPresenter.getAppContext());
        List<AmargarMarkazSazmanForoshModel> markazForoshModels = amargarMarkazSazmanForoshDAO.getAllMarkaz();
        mPresenter.onGetAmargarMarkazForosh(markazForoshModels);
    }

    @Override
    public void getAmargarSazmanForosh(Integer selectedMarkazForosh)
    {
        AmargarMarkazSazmanForoshDAO amargarMarkazSazmanForoshDAO = new AmargarMarkazSazmanForoshDAO(mPresenter.getAppContext());
        List<AmargarMarkazSazmanForoshModel> markazModels = amargarMarkazSazmanForoshDAO.getAllSazmanByMarkaz(selectedMarkazForosh);
        mPresenter.onGetAmargarSazmanForosh(markazModels);
    }

    @Override
    public void getForoshandeh(Integer ccSazman)
    {
        ForoshandehDAO foroshandehDAO = new ForoshandehDAO(mPresenter.getAppContext());
        List<ForoshandehModel> foroshandehModels = foroshandehDAO.getByccSazman(ccSazman);
        mPresenter.onGetForoshandeh(foroshandehModels);
    }

    @Override
    public void getMasir(Integer selectedForoshandehId)
    {
        MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
        List<MasirModel> masirModels = masirDAO.getByccForoshandeh(selectedForoshandehId);
        mPresenter.onGetMasir(masirModels);
    }

    @Override
    public void getRadiusConfig()
    {
        String ccChildParameters = Constants.CC_CHILD_MAX_RADIUS_FOR_CUSTOMERS_LIST() + "," + Constants.CC_CHILD_STEP_RADIUS_FOR_CUSTOMERS_LIST();
        ArrayList<ParameterChildModel> childParameterModelsConfig = new ParameterChildDAO(mPresenter.getAppContext()).getAllByccChildParameter(ccChildParameters);
        mPresenter.onGetRadiusConfig(childParameterModelsConfig);
    }

    @Override
    public void getListMoshtarian(Integer selectedMasirId)
    {
        final ListMoshtarianDAO listMoshtarianDAO = new ListMoshtarianDAO(mPresenter.getAppContext());
        listMoshtarianDAO.fetchByMasir(mPresenter.getAppContext(), "AmargarCustomerListActivity", "1", String.valueOf(selectedMasirId), new RetrofitResponse()
        {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                boolean deleteResult = listMoshtarianDAO.deleteAll();
                boolean insertResult = listMoshtarianDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult)
                {
                    mPresenter.onGetListMoshtarian(arrayListData);
                }
            }

            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onGetListMoshtarian(null);
            }
        });
    }

    @Override
    public void getCustomerListByLocation(String selectedItem, String latitude, String longitude)
    {
        final ListMoshtarianDAO listMoshtarianDAO = new ListMoshtarianDAO(mPresenter.getAppContext());
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case REST:
                listMoshtarianDAO.fetchByRadius(mPresenter.getAppContext(), "AmargarCustomerListActivity", selectedItem, latitude, longitude, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(ArrayList arrayListData)
                    {
                        boolean deleteResult = listMoshtarianDAO.deleteAll();
                        boolean insertResult = listMoshtarianDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            mPresenter.onGetListMoshtarianByLocation(arrayListData);
                        }
                    }

                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onGetListMoshtarianByLocation(null);
                    }
                });
                break;
            case gRPC:
                listMoshtarianDAO.fetchByRadiusGrpc(mPresenter.getAppContext(), "AmargarCustomerListActivity", selectedItem, latitude, longitude, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(ArrayList arrayListData)
                    {
                        boolean deleteResult = listMoshtarianDAO.deleteAll();
                        boolean insertResult = listMoshtarianDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            mPresenter.onGetListMoshtarianByLocation(arrayListData);
                        }
                    }

                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onGetListMoshtarianByLocation(null);
                    }
                });
                break;

        }

    }

    @Override
    public void sendPorseshname(int ccMoshtary)
    {
        PorseshnamehDAO porseshnamehDAO = new PorseshnamehDAO(mPresenter.getAppContext());
        PorseshnamehModel porseshnamehModel = porseshnamehDAO.getByMoshtary(ccMoshtary);
        if (porseshnamehModel == null)
        {
            mPresenter.onErrorSendPorseshname(-2);
        }
        else
        {
            ArrayList<PorseshnamehShomareshModel> porseshnamehShomareshModels = new PorseshnamehShomareshDAO(mPresenter.getAppContext()).getAllByPorseshname(porseshnamehModel.getCcPorseshnameh());
            ArrayList<PorseshnamehTablighatModel> porseshnamehTablighatModels = new PorseshnamehTablighatDAO(mPresenter.getAppContext()).getAllByPorseshname(porseshnamehModel.getCcPorseshnameh());
            VisitMoshtaryModel visitMoshtaryModel = new VisitMoshtaryDAO(mPresenter.getAppContext()).getByPorseshname(porseshnamehModel.getCcPorseshnameh());
            try
            {
                String version = new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());
                JSONObject jsonObjectFinal = new JSONObject();
                JSONObject jsonObjectPorseshname = porseshnamehModel.toJsonObject(version);
                JSONArray jsonArrayShomaresh = toJsonArrayShomaresh(porseshnamehShomareshModels);
                JSONArray jsonArrayTablighat = toJsonArrayTablighat(porseshnamehTablighatModels);
                JSONObject jsonObject = (visitMoshtaryModel == null) ? new JSONObject() : visitMoshtaryModel.toJsonObject();

                if (jsonObjectPorseshname != null)
                {
                    jsonObjectFinal.put("Porseshnameh" , jsonObjectPorseshname);
                    jsonObjectFinal.put("PorseshnamehShomaresh" , jsonArrayShomaresh);
                    jsonObjectFinal.put("PorseshnamehTablighat" , jsonArrayTablighat);
                    jsonObjectFinal.put("VisitMoshtary" , jsonObject);

                    String strJsonFinal = jsonObjectFinal.toString();
                    FileUtils.saveToFile(mPresenter.getAppContext(), strJsonFinal, "porseshname" + porseshnamehModel.getCcPorseshnameh());
                }
                else
                {
                    mPresenter.onErrorSendPorseshname(-2);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "AmargarCustomerListModel", "", "sendPorseshname", "");
                mPresenter.onErrorSendPorseshname(-1);
            }
        }
    }

    private JSONArray toJsonArrayShomaresh(ArrayList<PorseshnamehShomareshModel> models)
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            for (PorseshnamehShomareshModel model : models)
            {
                JSONObject jsonObject = model.toJsonObject();
                if (jsonObject != null)
                {
                    jsonArray.put(jsonObject);
                }
            }
            return jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private JSONArray toJsonArrayTablighat(ArrayList<PorseshnamehTablighatModel> models)
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            for (PorseshnamehTablighatModel model : models)
            {
                JSONObject jsonObject = model.toJsonObject();
                if (jsonObject != null)
                {
                    jsonArray.put(jsonObject);
                }
            }
            return jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONArray();
        }
    }


    @Override
    public void getElatAdamMoarefiMoshtary(int ccMoshtary)
    {
        ElatAdamMoarefiMoshtaryDAO elatAdamMoarefiMoshtaryDAO = new ElatAdamMoarefiMoshtaryDAO(mPresenter.getAppContext());
        ArrayList<ElatAdamMoarefiMoshtaryModel> elatAdamMoarefiMoshtaryModels = elatAdamMoarefiMoshtaryDAO.getAll();
        mPresenter.onGetElatAdamMoarefiMoshtary(elatAdamMoarefiMoshtaryModels, ccMoshtary);
    }

    @Override
    public void saveAdamFaal(int selectedccElat, int ccMoshtary, String codeMoshtaryTekrari)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        String currentDate = sdf.format(new Date());
        VisitMoshtaryDAO visitMoshtaryDAO = new VisitMoshtaryDAO(mPresenter.getAppContext());
        VisitMoshtaryModel visitMoshtaryModel = new VisitMoshtaryModel();

        visitMoshtaryModel.setCcAmargar(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect().getCcAmargar());
        visitMoshtaryModel.setCcMoshtary(ccMoshtary);
        visitMoshtaryModel.setCcPorseshnameh(0);
        visitMoshtaryModel.setTarikhVisitMoshtary(currentDate);
        visitMoshtaryModel.setCodeVazeiatMoshtary(new ListMoshtarianDAO(mPresenter.getAppContext()).getByccMoshtary(ccMoshtary).getMoshtaryCodeVazeiat());
        visitMoshtaryModel.setCcElatAdamMoarefiMoshtary(selectedccElat);
        visitMoshtaryModel.setCodeMoshtaryTekrari(codeMoshtaryTekrari);
        visitMoshtaryModel.setSaatVorod(currentDate);
        visitMoshtaryModel.setSaatKhoroj(currentDate);

        if (visitMoshtaryDAO.insert(visitMoshtaryModel))
        {
            mPresenter.onSaveAdamFaal(new ListMoshtarianDAO(mPresenter.getAppContext()).updateStatus(ccMoshtary, 2));
        }
        else
        {
            mPresenter.onSaveAdamFaal(false);
        }
    }

    @Override
    public void checkForAddPorseshname(Location currentLocation, ListMoshtarianModel model)
    {
        UserTypeShared userTypeShared = new UserTypeShared(mPresenter.getAppContext());
        int isTest = userTypeShared.getInt(userTypeShared.USER_TYPE() , 0);
        if (isTest == 1)
        {
            saveInfo(currentLocation);
            mPresenter.onCheckAddPorsehsname(1, model.getCcMoshtary(), model.getCodeMoshtaryOld());
            return;
        }

        int countNotSendedPorseshname = new PorseshnamehDAO(mPresenter.getAppContext()).getCountNotSended();
        int countNotSendedAdam = new VisitMoshtaryDAO(mPresenter.getAppContext()).getCountNotSended();
        if (countNotSendedAdam > 0 || countNotSendedPorseshname > 0)
        {
            mPresenter.onCheckAddPorsehsname(-2, -1,"-1");
            return;
        }

        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        int zaribKharejAzMahalMetr = 0;
        int GPSEnable = 1;
        try
        {
            zaribKharejAzMahalMetr = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_ZARIB_KHAREJ_AZ_MAHAL_METR));
            GPSEnable = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_GPS_ENABLE));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), CLASS_NAME, "", "checkForAddPorseshname", "");
        }

        if (GPSEnable == 1)
        {
            float[] distance = new float[2];
            android.location.Location.distanceBetween(model.getLatitudeY(), model.getLongitudeX(), currentLocation.getLat(), currentLocation.getLon(), distance);
            Log.d("customerlist", "distance : " + distance[0]);
            if (distance[0] > zaribKharejAzMahalMetr)
            {
                mPresenter.onCheckAddPorsehsname(-1,-1,"-1");
            }
            else
            {
                saveInfo(currentLocation);
                mPresenter.onCheckAddPorsehsname(1, model.getCcMoshtary(), model.getCodeMoshtaryOld());
            }
        }
        else
        {
            saveInfo(currentLocation);
            mPresenter.onCheckAddPorsehsname(1, model.getCcMoshtary(), model.getCodeMoshtaryOld());
        }
    }

    private void saveInfo(Location currentLocation)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        PorseshnamehInfoShared shared = new PorseshnamehInfoShared(mPresenter.getAppContext());
        shared.removeAll();
        shared.putString(PorseshnamehInfoShared.LATITUDE_KEY, String.valueOf(currentLocation.getLat()));
        shared.putString(PorseshnamehInfoShared.LONGITUDE_KEY, String.valueOf(currentLocation.getLon()));
        shared.putString(PorseshnamehInfoShared.ZAMANE_VOROD_KEY, String.valueOf(sdf.format(new Date())));
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
