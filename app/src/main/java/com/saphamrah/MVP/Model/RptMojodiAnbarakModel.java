package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RptMojodiAnbrakMVP;
import com.saphamrah.DAO.AnbarakAfradDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptMojodiAnbarDAO;
import com.saphamrah.Model.AnbarakAfradModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptMojodiAnbarModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;

import java.util.ArrayList;

public class RptMojodiAnbarakModel implements RptMojodiAnbrakMVP.ModelOps
{

    private RptMojodiAnbrakMVP.RequiredPresenterOps mPresenter;

    private int successfullRequest;

    public RptMojodiAnbarakModel(RptMojodiAnbrakMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
        successfullRequest = 0;
    }


    @Override
    public void getMojodiAnbar()
    {
        AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
        ArrayList<AnbarakAfradModel> anbarakAfradModels = anbarakAfradDAO.getAll();
        if (anbarakAfradModels != null && anbarakAfradModels.size() > 0)
        {
            RptMojodiAnbarDAO mojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());
            ArrayList<RptMojodiAnbarModel> mojodiAnbarModels = mojodiAnbarDAO.getAllOrderByCodeKala();
            mPresenter.onGetMojodiAnbar(mojodiAnbarModels);
        }
        else
        {
            mPresenter.onFailedGetMojodiAnbar(R.string.notFoundAnbar);
        }
    }

    @Override
    public void updateMojodiAnbar()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
        ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels = foroshandehMamorPakhshDAO.getAll();
        int ccMamorPakhsh = foroshandehMamorPakhshModels.get(0).getCcMamorPakhsh();
        int ccForoshandeh = foroshandehMamorPakhshModels.get(0).getCcForoshandeh();

        // get noeMasoliat
        ForoshandehMamorPakhshUtils foroshandehMamorPakhshUtils = new ForoshandehMamorPakhshUtils();
        int noeMasouliat = foroshandehMamorPakhshUtils.getNoeMasouliat(foroshandehMamorPakhshModels.get(0));

        AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
        ArrayList<AnbarakAfradModel> anbarakAfradModels = anbarakAfradDAO.getAll();

        Log.d("RptMojodyAnbarak","anbarakAfradModels:"+anbarakAfradModels.toString());
        Log.d("RptMojodyAnbarak","anbarakAfradModels size:"+anbarakAfradModels.size());
        if (anbarakAfradModels != null && anbarakAfradModels.size() > 0)
        {
            String ccAnbarak = String.valueOf(anbarakAfradModels.get(0).getCcAnbarak());
            final RptMojodiAnbarDAO mojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());
            mojodiAnbarDAO.fetchRptMojodyAnbarak(mPresenter.getAppContext(), mPresenter.getAppContext().getClass().getSimpleName(), ccAnbarak , ccForoshandeh , ccMamorPakhsh , noeMasouliat , new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    final Handler handler = new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg)
                        {
                            if (msg.arg1 == 1)
                            {
                                mPresenter.onGetMojodiAnbar(arrayListData);
                            }
                            return false;
                        }
                    });

                    Thread thread = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            mojodiAnbarDAO.deleteAll();
                            mojodiAnbarDAO.insertGroup(arrayListData);
                            Message msg = new Message();
                            msg.arg1 = 1;
                            handler.sendMessage(msg);
                        }
                    };
                    thread.start();
                }
                @Override
                public void onFailed(String type, String error)
                {
                    mPresenter.onFailedGetMojodiAnbar(R.string.errorGetData);
                }
            });
        }
        else
        {
            mPresenter.onFailedGetMojodiAnbar(R.string.notFoundAnbar);
        }
    }

    @Override
    public void getMojodiAnbarOrderByNameKala(boolean viewAsTable)
    {
        RptMojodiAnbarDAO mojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());
        ArrayList<RptMojodiAnbarModel> mojodiAnbarakModels = mojodiAnbarDAO.getAllOrderByNameKala();
        mPresenter.onGetMojodiAnbarOrderByNameKala(mojodiAnbarakModels , viewAsTable);
    }

    @Override
    public void getMojodiAnbarOrderByCount(boolean viewAsTable)
    {
        RptMojodiAnbarDAO mojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());
        ArrayList<RptMojodiAnbarModel> mojodiAnbarakModels = mojodiAnbarDAO.getAllOrderByCount();
        mPresenter.onGetMojodiAnbarOrderByCount(mojodiAnbarakModels , viewAsTable);
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
