package com.saphamrah.MVP.Presenter;

import android.content.Context;


import com.saphamrah.BaseMVP.RptEtebarMVP;
import com.saphamrah.MVP.Model.RptEtebarModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.rptEtebarModel.RptEtebarParentModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;

public class RptEtebarPresenter implements RptEtebarMVP.PresenterOps , RptEtebarMVP.RequiredPresenterOps {


    private WeakReference<RptEtebarMVP.RequiredViewOps> mView;
    private RptEtebarMVP.ModelOps mModel;

    public RptEtebarPresenter(RptEtebarMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptEtebarModel(this);
    }



    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onConfigurationChanged(RptEtebarMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void onGetEtebar(List<RptEtebarParentModel> etebarModels) {
        if (etebarModels != null)
        {
            mView.get().setAdapter(etebarModels);
        }
        else
        {
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onFailedGetEtebar(int resId) {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }

    @Override
    public void getEtebar(int foroshande, int moshtary, int sazmanForosh) {
        mModel.getEtebar(foroshande, moshtary, sazmanForosh);
    }
}
