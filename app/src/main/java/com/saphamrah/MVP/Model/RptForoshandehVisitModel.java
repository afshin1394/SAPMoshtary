package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.BaseMVP.RptForoshandehVisitMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptVisitForoshandehMoshtaryDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptVisitForoshandehMoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptForoshandehVisitModel implements RptForoshandehVisitMVP.ModelOps
{

    private RptForoshandehVisitMVP.RequiredPresenterOps mPresenter;


    public RptForoshandehVisitModel(RptForoshandehVisitMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getVisitList()
    {
        RptVisitForoshandehMoshtaryDAO rptVisitForoshandehMoshtaryDAO = new RptVisitForoshandehMoshtaryDAO(mPresenter.getAppContext());
        ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels = rptVisitForoshandehMoshtaryDAO.getForReportWithSum();
        mPresenter.onGetVisitList(rptVisitForoshandehMoshtaryModels);
    }

    @Override
    public void updateReport()
    {
        final RptVisitForoshandehMoshtaryDAO rptVisitForoshandehMoshtaryDAO = new RptVisitForoshandehMoshtaryDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getVisitList();
                    mPresenter.onSuccessUpdate();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onFailedUpdate();
                }
                return false;
            }
        });

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case REST:
                rptVisitForoshandehMoshtaryDAO.fetchAllrptVisitForoshandehMoshtary(mPresenter.getAppContext(), "RptForoshandehVisitActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = rptVisitForoshandehMoshtaryDAO.deleteAll();
                                boolean insertResult = rptVisitForoshandehMoshtaryDAO.insertGroup(arrayListData);
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
                        mPresenter.onFailedUpdate();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptForoshandehVisitModel", "RptForoshandehVisitActivity", "updateReport", "onFailed");
                    }
                });
                break;

            case gRPC:
                rptVisitForoshandehMoshtaryDAO.fetchAllrptVisitForoshandehMoshtaryGrpc(mPresenter.getAppContext(), "RptForoshandehVisitActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = rptVisitForoshandehMoshtaryDAO.deleteAll();
                                boolean insertResult = rptVisitForoshandehMoshtaryDAO.insertGroup(arrayListData);
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
                        mPresenter.onFailedUpdate();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptForoshandehVisitModel", "RptForoshandehVisitActivity", "updateReport", "onFailed");
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
