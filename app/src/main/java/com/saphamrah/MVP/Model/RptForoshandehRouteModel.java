package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.BaseMVP.RptForoshandehRouteMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptMasirDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptMasirModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;

import java.util.ArrayList;

public class RptForoshandehRouteModel implements RptForoshandehRouteMVP.ModelOps
{

    private RptForoshandehRouteMVP.RequiredPresenterOps mPresenter;


    public RptForoshandehRouteModel(RptForoshandehRouteMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getRouteList()
    {
        RptMasirDAO rptMasirDAO = new RptMasirDAO(mPresenter.getAppContext());
        ArrayList<RptMasirModel> rptMasirModels = rptMasirDAO.getRptForoshandehMaisrWithSum();
        mPresenter.onGetRouteList(rptMasirModels);
    }

    @Override
    public void updateRouteList()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();
        if (foroshandehMamorPakhshModel != null)
        {
            final Handler handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg)
                {
                    if (msg.arg1 == 1)
                    {
                        getRouteList();
                        mPresenter.onSuccessUpdateRouteList();
                    }
                    else if (msg.arg1 == -1)
                    {
                        mPresenter.onFailedUpdateRouteList(R.string.errorGetData);
                    }
                    return false;
                }
            });

            final RptMasirDAO rptMasirDAO = new RptMasirDAO(mPresenter.getAppContext());
            rptMasirDAO.fetchRPTMasirForoshandeh(mPresenter.getAppContext(), "RptForoshandehRouteActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse()
            {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            boolean deleteResult = rptMasirDAO.deleteAll();
                            boolean insertResult = rptMasirDAO.insertGroup(arrayListData);
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
                    mPresenter.onFailedUpdateRouteList(R.string.errorGetData);
                }
            });
        }
        else
        {
            mPresenter.onFailedUpdateRouteList(R.string.errorFindForoshandeh);
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
