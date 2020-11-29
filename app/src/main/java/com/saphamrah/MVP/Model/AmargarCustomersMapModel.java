package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.AmargarCustomersMapMVP;
import com.saphamrah.DAO.ListMoshtarianDAO;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.PubFunc.PubFunc;

import java.util.List;

public class AmargarCustomersMapModel implements AmargarCustomersMapMVP.ModelOps
{

    private AmargarCustomersMapMVP.RequiredPresenterOps mPresenter;

    public AmargarCustomersMapModel(AmargarCustomersMapMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getCustomers()
    {
        ListMoshtarianDAO listMoshtarianDAO = new ListMoshtarianDAO(mPresenter.getAppContext());
        List<ListMoshtarianModel> listMoshtarianModels = listMoshtarianDAO.getAll();
        mPresenter.onGetCustomers(listMoshtarianModels);
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }
}
