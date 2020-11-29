package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.OnlineSupportMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.SupportCrispDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.SupportCrispModel;
import com.saphamrah.PubFunc.PubFunc;

public class OnlineSupportModel implements OnlineSupportMVP.ModelOps
{

    private OnlineSupportMVP.RequiredPresenterOps mPresenter;

    public OnlineSupportModel(OnlineSupportMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getCrispId()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh();
        if (foroshandehMamorPakhshModel != null && foroshandehMamorPakhshModel.getCcSazmanForosh() >= 0)
        {
            SupportCrispDAO supportCrispDAO = new SupportCrispDAO(mPresenter.getAppContext());
            SupportCrispModel supportCrispModel = supportCrispDAO.getBySazmanForosh(foroshandehMamorPakhshModel.getCcSazmanForosh());
            mPresenter.onGetCrispId(supportCrispModel);
        }
        else
        {
            SupportCrispModel supportCrispModel = new SupportCrispModel();
            supportCrispModel.setCrispID("-1");
            mPresenter.onGetCrispId(supportCrispModel);
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType , message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }


}
