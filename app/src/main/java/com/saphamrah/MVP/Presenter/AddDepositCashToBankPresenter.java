package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.AddDepositCashToBankMVP;
import com.saphamrah.MVP.Model.AddDepositCashToBankModel;

import java.lang.ref.WeakReference;

public class AddDepositCashToBankPresenter implements AddDepositCashToBankMVP.PresenterOps, AddDepositCashToBankMVP.RequiredPresenterOps
{

    private WeakReference<AddDepositCashToBankMVP.RequiredViewOps> mView;
    private AddDepositCashToBankModel mModel;

    public AddDepositCashToBankPresenter(AddDepositCashToBankMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
        mModel = new AddDepositCashToBankModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AddDepositCashToBankMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {}


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

}
