package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.BaseMVP.RptCustomersListByDistanceMVP;
import com.saphamrah.DAO.ListMoshtarianDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptCustomersListByDistanceModel implements RptCustomersListByDistanceMVP.ModelOps
{

    private RptCustomersListByDistanceMVP.RequiredPresenterOps mPresenter;


    public RptCustomersListByDistanceModel(RptCustomersListByDistanceMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getRadiusConfig()
    {
        String maxRadius = "0";
        String stepRadius = "0";
        String ccChildParameters = Constants.CC_CHILD_MAX_RADIUS_FOR_CUSTOMERS_LIST() + "," + Constants.CC_CHILD_STEP_RADIUS_FOR_CUSTOMERS_LIST();
        ArrayList<ParameterChildModel> childParameterModelsConfig = new ParameterChildDAO(mPresenter.getAppContext()).getAllByccChildParameter(ccChildParameters);
        for (ParameterChildModel model : childParameterModelsConfig)
        {
            if (model.getCcParameterChild() == Constants.CC_CHILD_MAX_RADIUS_FOR_CUSTOMERS_LIST())
            {
                maxRadius = model.getValue();
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_STEP_RADIUS_FOR_CUSTOMERS_LIST())
            {
                stepRadius = model.getValue();
            }
        }
        mPresenter.onGetRadiusConfig(maxRadius , stepRadius);
    }

    @Override
    public void getCustomerList(final String radius , String latitude , String longitude)
    {
        final ListMoshtarianDAO listMoshtarianDAO = new ListMoshtarianDAO(mPresenter.getAppContext());

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    ArrayList<ListMoshtarianModel> listMoshtarianModels = listMoshtarianDAO.getAll();
                    Log.d("moshtarian" , "size : " + listMoshtarianModels.size());
                    for (ListMoshtarianModel model : listMoshtarianModels)
                    {
                        Log.d("moshtarian" , model.toString());
                    }
                    mPresenter.onGetCustomerList(listMoshtarianModels , radius);
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onErrorGetCustomerList();
                }
                return false;
            }
        });


        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case Constants.REST:
                listMoshtarianDAO.fetchByRadius(mPresenter.getAppContext(), "RptCustomersListByDistanceActivity", radius, latitude, longitude, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = listMoshtarianDAO.deleteAll();
                                boolean insertResult = listMoshtarianDAO.insertGroup(arrayListData);
                                Log.d("moshtarian" , "size arrayListData : " + arrayListData.size());
                                Log.d("moshtarian" , "deleteResult : " + deleteResult);
                                Message message = new Message();
                                if (deleteResult && insertResult)
                                {
                                    message.arg1 = 1;
                                }
                                else
                                {
                                    message.arg1 = -1;
                                }
                                handler.sendMessage(message);
                            }
                        };
                        thread.start();
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onErrorGetCustomerList();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptCustomersListByDistanceModel", "RptListVosolActivity", "updateListVosol", "onFailed");
                    }
                });
                break;

            case Constants.gRPC:
                listMoshtarianDAO.fetchByRadiusGrpc(mPresenter.getAppContext(), "RptCustomersListByDistanceActivity", radius, latitude, longitude, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = listMoshtarianDAO.deleteAll();
                                boolean insertResult = listMoshtarianDAO.insertGroup(arrayListData);
                                Log.d("moshtarian" , "size arrayListData : " + arrayListData.size());
                                Log.d("moshtarian" , "deleteResult : " + deleteResult);
                                Message message = new Message();
                                if (deleteResult && insertResult)
                                {
                                    message.arg1 = 1;
                                }
                                else
                                {
                                    message.arg1 = -1;
                                }
                                handler.sendMessage(message);
                            }
                        };
                        thread.start();
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onErrorGetCustomerList();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptCustomersListByDistanceModel", "RptListVosolActivity", "updateListVosol", "onFailed");
                    }
                });
                break;
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
