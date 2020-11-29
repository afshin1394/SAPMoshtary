package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.AddDepositCashToBankMVP;
import com.saphamrah.PubFunc.PubFunc;

public class AddDepositCashToBankModel implements AddDepositCashToBankMVP.ModelOps
{

    private AddDepositCashToBankMVP.RequiredPresenterOps mPresenter;

    public AddDepositCashToBankModel(AddDepositCashToBankMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
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
