package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.RptThreeMonth.RptThreeMonthMVP;
import com.saphamrah.BaseMVP.RptThreeMonth.RptThreeMonthRizFaktorMVP;
import com.saphamrah.MVP.Model.RptThreeMonthPurchaseModel;
import com.saphamrah.MVP.Model.RptThreeMonthRizFaktorModel;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptThreeMonthRizFaktorPresenter implements RptThreeMonthRizFaktorMVP.PresenterOps , RptThreeMonthRizFaktorMVP.RequiredPresenterOps {


    private WeakReference<RptThreeMonthRizFaktorMVP.RequiredViewOps> mView;
    private RptThreeMonthRizFaktorMVP.ModelOps mModel;
    public RptThreeMonthRizFaktorPresenter(RptThreeMonthRizFaktorMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptThreeMonthRizFaktorModel(this);
    }

    @Override
    public void getRizFaktor(int ccMoshtary) {
        mModel.getRizFaktor(ccMoshtary);
    }

    @Override
    public void getFilteredListByFactorNu(int ccMoshtary, String filter) {
        mModel.getFilteredListByFactorNu(ccMoshtary,filter);
    }


    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onConfigurationChanged(RptThreeMonthRizFaktorMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);

    }

    @Override
    public void onGetRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels) {
        if (rpt3MonthPurchaseModels.size() > 0) {
            int sumTedadFaktor = rpt3MonthPurchaseModels.size();
            long sumMablaghFaktor = 0;
            for (int i = 0; i < sumTedadFaktor; i++) {
                sumMablaghFaktor += rpt3MonthPurchaseModels.get(i).getMablaghKhalesFaktor();
            }
            mView.get().onGetRizFaktor(rpt3MonthPurchaseModels, sumTedadFaktor, sumMablaghFaktor);
        } else {
            mView.get().hideFooter();
        }

    }

    @Override
    public void onGetFilteredRizFaktor(ArrayList<Rpt3MonthPurchaseModel> moshtaryFaktorModels,String filter) {
        if (moshtaryFaktorModels.size() > 0) {
            int sumTedadFaktor = moshtaryFaktorModels.size();
            long sumMablaghFaktor = 0;
            for (int i = 0; i < sumTedadFaktor; i++) {
                sumMablaghFaktor += moshtaryFaktorModels.get(i).getSumMablagh();

            }
            Log.i("moshtaryFaktor", "onGetList: " + moshtaryFaktorModels.size());
            mView.get().onGetFilteredRizFaktor(moshtaryFaktorModels,sumTedadFaktor,sumMablaghFaktor);
        }else if (filter.length()>0){

            mView.get().hideFooter();
        }
    }




    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals("")) {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }



    @Override
    public void onDestroy(boolean isChangingConfig) {

    }
}
