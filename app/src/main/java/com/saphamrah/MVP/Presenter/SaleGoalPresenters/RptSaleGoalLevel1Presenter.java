package com.saphamrah.MVP.Presenter.SaleGoalPresenters;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.RptSaleGoalLevel1MVP;
import com.saphamrah.BaseMVP.RptSaleGoalLevel1MVP;
import com.saphamrah.MVP.Model.SaleGoalModels.RptSaleGoalModelLevel1;


import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;

import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptSaleGoalLevel1Presenter implements RptSaleGoalLevel1MVP.RequiredPresenterOps, RptSaleGoalLevel1MVP.PresenterOps {
    private WeakReference<RptSaleGoalLevel1MVP.RequiredViewOps> mView;
    private RptSaleGoalLevel1MVP.ModelOps mModel;


    public RptSaleGoalLevel1Presenter(RptSaleGoalLevel1MVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptSaleGoalModelLevel1(this);
    }


    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetSaleGoalReport(ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels) {
        if (rptBrandHadafForoshModels != null && rptBrandHadafForoshModels.size() > 0){
            Log.i("SasleGoal", "onGetSaleGoalReport: "+ rptBrandHadafForoshModels.size());

            mView.get().drawSaleGoalReport(rptBrandHadafForoshModels);

        }
    }



    @Override
    public void onSuccessUpdateSaleGoalReport() {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorUpdateSaleGoalReport() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onConfigurationChanged(RptSaleGoalLevel1MVP.RequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getSaleGoalReport() {
        mModel.getSaleGoalReport();
    }




    @Override
    public void updateSaleGoalReport() {
        mModel.updateSaleGoalReport();
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }

    @Override
    public void onNetworkError() {
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

    }

}
