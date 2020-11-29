package com.saphamrah.MVP.Presenter.SaleGoalPresenters;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.RptSaleGoalLevel2MVP;
import com.saphamrah.BaseMVP.RptSaleGoalLevel2MVP;
import com.saphamrah.MVP.Model.SaleGoalModels.RptSaleGoalModelLevel2;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;

import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptSaleGoalLevel2Presenter implements RptSaleGoalLevel2MVP.PresenterOps,RptSaleGoalLevel2MVP.RequiredPresenterOps {
    private WeakReference<RptSaleGoalLevel2MVP.RequiredViewOps> mView;
    private RptSaleGoalLevel2MVP.ModelOps mModel;

    public RptSaleGoalLevel2Presenter(RptSaleGoalLevel2MVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        this.mModel=new RptSaleGoalModelLevel2(this);
    }

    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetSaleGoalReportLevel2(ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels) {
        if (rptGorohKalaHadafForoshModels != null && rptGorohKalaHadafForoshModels.size() > 0){
            Log.i("SasleGoalBrand", "onGetSaleGoalReport: "+ rptGorohKalaHadafForoshModels.size());
            Log.i("NameGoroohs1", "onGetSaleGoalReportLevel2: "+ rptGorohKalaHadafForoshModels.get(0).getNameGorohKala());

            mView.get().drawSaleGoalReportLevel2(rptGorohKalaHadafForoshModels);

        }
    }





    @Override
    public void onNetworkError() {
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

    }

    @Override
    public void onConfigurationChanged(RptSaleGoalLevel2MVP.RequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getSaleGoalReportLevel2(int Brand) {
        mModel.getSaleGoalReportLevel2(Brand);
    }

    @Override
    public void getAllSaleGoalReportLevel2() {
        mModel.getAllSaleGoalReportsLevel2();
    }


    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }
}
