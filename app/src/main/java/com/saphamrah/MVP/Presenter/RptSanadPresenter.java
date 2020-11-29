package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptSanadMVP;

import com.saphamrah.MVP.Model.RptSanadModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;

public class RptSanadPresenter implements RptSanadMVP.PresenterOps , RptSanadMVP.RequiredPresenterOps {
    private WeakReference<RptSanadMVP.RequiredViewOps> mView;
    private RptSanadMVP.ModelOps mModel;

    public RptSanadPresenter(RptSanadMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptSanadModel(this);
    }

    @Override
    public void getRptSanad() {
        mModel.getRptSanad();
    }

    @Override
    public void updateRptSanad() {
        mModel.updateRptSanad();
    }

    @Override
    public void onGetRptSanad(List<com.saphamrah.Model.RptSanadModel> rptSanadModels) {
        if (rptSanadModels != null)
        {
            mView.get().setAdapter(rptSanadModels);
        }
        else
        {
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onFailedGetRptSanad(int resId) {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessUpdateRptSanad() {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorUpdateRptSanad() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onConfigurationChanged(RptSanadMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
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


}
