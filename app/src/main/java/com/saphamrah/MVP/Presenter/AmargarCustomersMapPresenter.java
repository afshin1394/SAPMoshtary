package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.AmargarCustomersMapMVP;
import com.saphamrah.MVP.Model.AmargarCustomersMapModel;
import com.saphamrah.Model.ListMoshtarianModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AmargarCustomersMapPresenter implements AmargarCustomersMapMVP.PresenterOps, AmargarCustomersMapMVP.RequiredPresenterOps
{

    private WeakReference<AmargarCustomersMapMVP.RequiredViewOps> mView;
    private AmargarCustomersMapModel mModel;

    public AmargarCustomersMapPresenter(AmargarCustomersMapMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
        mModel = new AmargarCustomersMapModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AmargarCustomersMapMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getCustomers()
    {
        mModel.getCustomers();
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
    public void onGetCustomers(List<ListMoshtarianModel> listMoshtarianModels)
    {
        if (listMoshtarianModels.size() > 0)
        {
            mView.get().showCustomers(listMoshtarianModels);
        }
        else
        {
            mView.get().showErrorNotFoundCustomer();
        }
    }
}
