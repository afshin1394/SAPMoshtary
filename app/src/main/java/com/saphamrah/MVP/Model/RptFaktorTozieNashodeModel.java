package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;

import com.google.zxing.oned.rss.AbstractRSSReader;
import com.saphamrah.BaseMVP.RptFaktorTozieNashodeMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptFaktorTozieNashodehDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptFaktorTozieNashodehModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptFaktorTozieNashodeModel implements RptFaktorTozieNashodeMVP.ModelOps
{

    private RptFaktorTozieNashodeMVP.RequiredPresenterOps mPresenter;


    public RptFaktorTozieNashodeModel(RptFaktorTozieNashodeMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getListFaktorTozieNashode()
    {
        RptFaktorTozieNashodehDAO rptFaktorTozieNashodehDAO = new RptFaktorTozieNashodehDAO(mPresenter.getAppContext());
        ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels = rptFaktorTozieNashodehDAO.getAllWithSum();
        mPresenter.onGetListFaktorTozieNashode(rptFaktorTozieNashodehModels);
    }

    @Override
    public void updateFaktorTozeieNashode()
    {
        final RptFaktorTozieNashodehDAO rptFaktorTozieNashodehDAO = new RptFaktorTozieNashodehDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();

        final Handler handler = new Handler(msg -> {
            if (msg.arg1 == 1)
            {
                getListFaktorTozieNashode();
                mPresenter.onSuccessUpdateListFaktorTozeisNashode();
            }
            else if (msg.arg1 == -1)
            {
                mPresenter.onErrorUpdateListFaktorTozeisNashode();
            }
            return false;
        });
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case REST:
                rptFaktorTozieNashodehDAO.fetchAllrptFaktorTozieNashodeh(mPresenter.getAppContext(), "RptFaktorTozieNashodeActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = rptFaktorTozieNashodehDAO.deleteAll();
                                boolean insertResult = rptFaktorTozieNashodehDAO.insertGroup(arrayListData);
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
                        mPresenter.onErrorUpdateListFaktorTozeisNashode();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptFaktorTozieNashodeModel", "RptFaktorTozieNashodeActivity", "updateFaktorTozeieNashode", "onFailed");
                    }
                });
                break;

            case gRPC:
                rptFaktorTozieNashodehDAO.fetchAllrptFaktorTozieNashodehGrpc(mPresenter.getAppContext(), "RptFaktorTozieNashodeActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = rptFaktorTozieNashodehDAO.deleteAll();
                                boolean insertResult = rptFaktorTozieNashodehDAO.insertGroup(arrayListData);
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
                        mPresenter.onErrorUpdateListFaktorTozeisNashode();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptFaktorTozieNashodeModel", "RptFaktorTozieNashodeActivity", "updateFaktorTozeieNashode", "onFailed");
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
