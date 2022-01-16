package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.BaseMVP.RptKharidKalaMVP;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptKharidKalaDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptKharidKalaModel implements RptKharidKalaMVP.ModelOps
{

    private RptKharidKalaMVP.RequiredPresenterOps mPresenter;


    public RptKharidKalaModel(RptKharidKalaMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getListKharidKala()
    {
        RptKharidKalaDAO rptKharidKalaDAO = new RptKharidKalaDAO(mPresenter.getAppContext());
        ArrayList<com.saphamrah.Model.RptKharidKalaModel> kharidKalaModels = rptKharidKalaDAO.getAll();
        mPresenter.onGetListKharidKala(kharidKalaModels);
    }

    @Override
    public void updateListKharidKala()
    {
        final RptKharidKalaDAO rptKharidKalaDAO = new RptKharidKalaDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getListKharidKala();
                    mPresenter.onSuccessUpdate();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onErrorUpdate();
                }
                return false;
            }
        });

        String foroshandeh = "";
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        if(noeMasouliat == 5 )
        {
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            foroshandeh = darkhastFaktorDAO.getccForoshandehByForTasviehVosol(1);
        }
        else
        {
            foroshandeh = String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh());
        }
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case REST :
                rptKharidKalaDAO.fetchKharidKalaByccForoshandeh(mPresenter.getAppContext(), "RptKharidKalaActivity", "2" , foroshandeh, "", new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = rptKharidKalaDAO.deleteAll();
                                boolean insertResult = rptKharidKalaDAO.insertGroup(arrayListData);
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
                        mPresenter.onErrorUpdate();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptListVosolModel", "RptListVosolActivity", "updateListVosol", "onFailed");
                    }
                });
                break;
            case gRPC:
                rptKharidKalaDAO.fetchKharidKalaByccForoshandehGrpc(mPresenter.getAppContext(), "RptKharidKalaActivity", "2" , foroshandeh, "", new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = rptKharidKalaDAO.deleteAll();
                                boolean insertResult = rptKharidKalaDAO.insertGroup(arrayListData);
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
                        mPresenter.onErrorUpdate();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptListVosolModel", "RptListVosolActivity", "updateListVosol", "onFailed");
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
