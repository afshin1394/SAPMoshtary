package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.BaseMVP.RptFaktorMandehDarMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptMandehdarDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptListVosolModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptFaktorMandehDarModel implements RptFaktorMandehDarMVP.ModelOps
{

    private RptFaktorMandehDarMVP.RequiredPresenterOps mPresenter;


    public RptFaktorMandehDarModel(RptFaktorMandehDarMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getListMandehDar()
    {
        RptMandehdarDAO rptMandehdarDAO = new RptMandehdarDAO(mPresenter.getAppContext());
        ArrayList<RptMandehdarModel> rptMandehdarModels = rptMandehdarDAO.getAllWithSum();
        mPresenter.onGetListMandehDar(rptMandehdarModels);
    }

    @Override
    public void updateListMandehDar()
    {
        final RptMandehdarDAO rptMandehdarDAO = new RptMandehdarDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getListMandehDar();
                    mPresenter.onSuccessUpdateListMandehDar();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onErrorUpdateListMandehDar();
                }
                return false;
            }
        });

        rptMandehdarDAO.fetchAllMandehdar(mPresenter.getAppContext(), "RptFaktorMandehDarActivity", String.valueOf(foroshandehMamorPakhshModel.getCcAfrad()), new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptMandehdarDAO.deleteAll();
                        boolean insertResult = rptMandehdarDAO.insertGroup(arrayListData);
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
                mPresenter.onErrorUpdateListMandehDar();
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
