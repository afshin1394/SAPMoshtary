package com.saphamrah.MVP.Presenter.SaleGoalPresenters;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.RptSaleGoalLevel3MVP;
import com.saphamrah.MVP.Model.SaleGoalModels.RptSaleGoalModelLevel3;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;

import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptSaleGoalLevel3Presenter implements RptSaleGoalLevel3MVP.PresenterOps,RptSaleGoalLevel3MVP.RequiredPresenterOps {
    private WeakReference<RptSaleGoalLevel3MVP.RequiredViewOps> mView;
    private RptSaleGoalLevel3MVP.ModelOps mModel;
    public RptSaleGoalLevel3Presenter(RptSaleGoalLevel3MVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        this.mModel=new RptSaleGoalModelLevel3(this);
    }

    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetSaleGoalReportLevel3(ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels) {
        if (rptKalaHadafForoshModels != null && rptKalaHadafForoshModels.size() > 0){
            Log.i("SasleGoalKala", "onGetSaleGoalReport: "+rptKalaHadafForoshModels.size());
            Log.i("NameKala1", "onGetSaleGoalReportLevel2: "+rptKalaHadafForoshModels.get(0).getNameKala());

            mView.get().drawSaleGoalReportLevel3(rptKalaHadafForoshModels);

        }
    }

    @Override
    public void onSuccessUpdateSaleGoalReportLevel3() {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorUpdateSaleGoalReportLevel3() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onNetworkError() {
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onConfigurationChanged(RptSaleGoalLevel3MVP.RequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getSaleGoalReportLevel3(int Brand, int gorohKala) {
        mModel.getSaleGoalReportLevel3(Brand,gorohKala);
    }

    @Override
    public void getAllSaleGoalReportLevel3() {
        mModel.getAllSaleGoalReportLevel3();
    }

    @Override
    public void getAllSaleGoalByBrandLevel3(int mBrand) {
        mModel.getAllSaleGoalByBrandLevel3(mBrand);
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }
}
