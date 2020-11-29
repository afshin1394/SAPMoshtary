package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.BaseMVP.RptListVosolMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptListVosolDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptListVosolModel implements RptListVosolMVP.ModelOps
{

    private RptListVosolMVP.RequiredPresenterOps mPresenter;


    public RptListVosolModel(RptListVosolMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getListVosol()
    {
        RptListVosolDAO rptListVosolDAO = new RptListVosolDAO(mPresenter.getAppContext());
        ArrayList<com.saphamrah.Model.RptListVosolModel> rptListVosolModels = rptListVosolDAO.getFaktorMandehDar();
        mPresenter.onGetListVosol(rptListVosolModels);
    }

    @Override
    public void updateListVosol()
    {
        final RptListVosolDAO rptListVosolDAO = new RptListVosolDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne();

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getListVosol();
                    mPresenter.onSuccessUpdateListVosol();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onErrorUpdateListVosol();
                }
                return false;
            }
        });

        rptListVosolDAO.fetchRPTListVosol(mPresenter.getAppContext(), "RptListVosolActivity", String.valueOf(foroshandehMamorPakhshModel.getCcAfrad()), new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptListVosolDAO.deleteAll();
                        boolean insertResult = rptListVosolDAO.insertGroup(arrayListData);
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
                mPresenter.onErrorUpdateListVosol();
                setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptListVosolModel", "RptListVosolActivity", "updateListVosol", "onFailed");
            }
        });
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
