package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.RptMarjoeeMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptMarjoeeDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptMarjoeeKalaModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;

import java.util.ArrayList;

public class RptMarjoeeModel implements RptMarjoeeMVP.ModelOps
{

    private RptMarjoeeMVP.RequiredPresenterOps mPresenter;


    public RptMarjoeeModel(RptMarjoeeMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getMarjoeeList()
    {
        RptMarjoeeDAO rptMarjoeeDAO = new RptMarjoeeDAO(mPresenter.getAppContext());
        ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels = rptMarjoeeDAO.getAll();
        mPresenter.onGetMarjoeeList(rptMarjoeeKalaModels);
    }

    @Override
    public void updateMarjoeeList()
    {
        final RptMarjoeeDAO rptMarjoeeDAO = new RptMarjoeeDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();
        rptMarjoeeDAO.fetchRPTMarjoee(mPresenter.getAppContext(), "RptMarjoeeActivity", String.valueOf(foroshandehMamorPakhshModel.getCcAfrad()), new RetrofitResponse()
        {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                boolean deleteResult = rptMarjoeeDAO.deleteAll();
                boolean insertResult = rptMarjoeeDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult)
                {
                    mPresenter.onSuccessUpdateMarjoee();
                    getMarjoeeList();
                }
                else
                {
                    mPresenter.onErrorUpdateMarjoee();
                }
            }

            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onErrorUpdateMarjoee();
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
    {}


}
