package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.BaseMVP.ForoshandehRouteMapMVP;
import com.saphamrah.DAO.CustomerAddressDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.GPSDataPpcDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.UIModel.CustomerAddressModel;

import java.util.ArrayList;

public class ForoshandehRouteMapModel implements ForoshandehRouteMapMVP.ModelOps
{

    private ForoshandehRouteMapMVP.RequiredPresenterOps mPresenter;


    public ForoshandehRouteMapModel(ForoshandehRouteMapMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getCustomerInfo()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        CustomerAddressDAO customerAddressDAO = new CustomerAddressDAO(mPresenter.getAppContext());
        ArrayList<CustomerAddressModel> customerAddressModels = new ArrayList<>();
        if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            customerAddressModels = customerAddressDAO.getByMasirPakhsh();
        }
        else
        {
            customerAddressModels = customerAddressDAO.getByMasir();
        }
        mPresenter.onGetCustomerInfo(customerAddressModels);
    }

    @Override
    public void getGpsDatas()
    {
        GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        ArrayList<GPSDataModel> gpsDataModels = new ArrayList<>();
        Log.d("routeMap" , "ccForoshandeh : " + foroshandehMamorPakhshModel.getCcForoshandeh());
        if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            gpsDataModels = gpsDataPpcDAO.getAllByccMamorPakhsh(foroshandehMamorPakhshModel.getCcMamorPakhsh());
        }
        else
        {
            gpsDataModels = gpsDataPpcDAO.getAllByccForoshandeh(foroshandehMamorPakhshModel.getCcForoshandeh());
        }
        mPresenter.onGetGpsDatas(gpsDataModels);
    }

    @Override
    public void updateGPSData()
    {
        final GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        final ArrayList<GPSDataModel> gpsDataModels = new ArrayList<>();

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    mPresenter.onSuccessUpdateGPSData(gpsDataModels);
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onFailedUpdateGPSData();
                }
                return false;
            }
        });


        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat == 7)
        {
            gpsDataPpcDAO.fetchGPSDataByccForoshandeh(mPresenter.getAppContext(), "ForoshandehRouteMapActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
            {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            boolean deleteResult = gpsDataPpcDAO.deleteAll();
                            boolean insertResult = gpsDataPpcDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult)
                            {
                                gpsDataModels.addAll(arrayListData);

                                Message message = new Message();
                                message.arg1 = 1;
                                handler.sendMessage(message);
                            }
                            else
                            {
                                Message message = new Message();
                                message.arg1 = -1;
                                handler.sendMessage(message);
                            }
                        }
                    };
                    thread.start();
                }
                @Override
                public void onFailed(String type, String error)
                {
                    mPresenter.onFailedUpdateGPSData();
                }
            });
        }
        else if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            gpsDataPpcDAO.fetchGPSDataByccMamorPakhs(mPresenter.getAppContext(), "ForoshandehRouteMapActivity", String.valueOf(foroshandehMamorPakhshModel.getCcMamorPakhsh()), new RetrofitResponse()
            {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            boolean deleteResult = gpsDataPpcDAO.deleteAll();
                            boolean insertResult = gpsDataPpcDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult)
                            {
                                gpsDataModels.addAll(arrayListData);

                                Message message = new Message();
                                message.arg1 = 1;
                                handler.sendMessage(message);
                            }
                            else
                            {
                                Message message = new Message();
                                message.arg1 = -1;
                                handler.sendMessage(message);
                            }
                        }
                    };
                    thread.start();
                }
                @Override
                public void onFailed(String type, String error)
                {
                    mPresenter.onFailedUpdateGPSData();
                }
            });
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }

    @Override
    public void onDestroy()
    {

    }


}
