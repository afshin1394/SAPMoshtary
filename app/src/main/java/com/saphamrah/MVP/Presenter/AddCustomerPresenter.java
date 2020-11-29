package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.AddCustomerMVP;
import com.saphamrah.MVP.Model.AddCustomerModel;

import java.lang.ref.WeakReference;

public class AddCustomerPresenter implements AddCustomerMVP.PresenterOps , AddCustomerMVP.RequiredPresenterOps
{

    private WeakReference<AddCustomerMVP.RequiredViewOps> mView;
    private AddCustomerMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public AddCustomerPresenter(AddCustomerMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new AddCustomerModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AddCustomerMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }


}
