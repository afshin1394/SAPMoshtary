package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptListVosolMVP;
import com.saphamrah.MVP.Model.RptListVosolModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptListVosolPresenter implements RptListVosolMVP.PresenterOps,RptListVosolMVP.RequiredPresenterOps
{

    private WeakReference<RptListVosolMVP.RequiredViewOps> mView;
    private RptListVosolMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptListVosolPresenter(RptListVosolMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptListVosolModel(this);
    }

    @Override
    public void onConfigurationChanged(RptListVosolMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getListVosol()
    {
        mModel.getListVosol();
    }

    @Override
    public void updateListVosol()
    {
        mModel.updateListVosol();
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
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
    public void onGetListVosol(ArrayList<com.saphamrah.Model.RptListVosolModel> rptListVosolModels)
    {
        mView.get().onGetListVosol(rptListVosolModels);
    }

    @Override
    public void onSuccessUpdateListVosol()
    {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorUpdateListVosol()
    {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
