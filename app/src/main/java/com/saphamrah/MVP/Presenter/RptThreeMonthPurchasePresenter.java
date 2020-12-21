package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.RptThreeMonth.RptThreeMonthMVP;
import com.saphamrah.MVP.Model.RptThreeMonthPurchaseModel;
import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;



public class RptThreeMonthPurchasePresenter implements RptThreeMonthMVP.PresenterOps, RptThreeMonthMVP.RequiredPresenterOps {

    private WeakReference<RptThreeMonthMVP.RequiredViewOps> mView;
    private RptThreeMonthMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptThreeMonthPurchasePresenter(RptThreeMonthMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptThreeMonthPurchaseModel(this);
    }

    @Override
    public void onConfigurationChanged(RptThreeMonthMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getList() {
        mModel.getList();
    }

    @Override
    public void getListFilteredByName(ArrayList<Rpt3MonthGetSumModel> rpt3MonthGetSumModels,String query) {
        mModel.getListFilteredByName(rpt3MonthGetSumModels,query);
    }

    @Override
    public void getListFilteredByCode(ArrayList<Rpt3MonthGetSumModel> rpt3MonthGetSumModels,String query) {
        mModel.getListFilteredByCode(rpt3MonthGetSumModels,query);
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

    @Override
    public void updateData(String activityNameForLog) {
        mModel.updateData(activityNameForLog);
    }

    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetList(ArrayList<Rpt3MonthGetSumModel> moshtaryFaktorModels) {
        //bigger than one, because sum of rows is a record even that there is no record
        if (moshtaryFaktorModels.size() > 0) {
            int sumTedadFaktor = moshtaryFaktorModels.size();
            long sumMablaghFaktor = 0;
            for (int i = 0; i < sumTedadFaktor; i++) {
                sumMablaghFaktor += moshtaryFaktorModels.get(i).getSumMablagh();

            }
            Log.i("moshtaryFaktor", "onGetList: " + moshtaryFaktorModels.size());
            mView.get().setListAdapter(moshtaryFaktorModels, sumTedadFaktor, sumMablaghFaktor);
        } else {
            mView.get().hideFooter();
        }
    }

    @Override
    public void onGetFilteredList(ArrayList<Rpt3MonthGetSumModel> moshtaryFaktorModels, String filter) {



        if (moshtaryFaktorModels.size() > 0) {
            int sumTedadFaktor = moshtaryFaktorModels.size();
            long sumMablaghFaktor = 0;
            for (int i = 0; i < sumTedadFaktor; i++) {
                sumMablaghFaktor += moshtaryFaktorModels.get(i).getSumMablagh();

            }
            Log.i("moshtaryFaktor", "onGetList: " + moshtaryFaktorModels.size());
            mView.get().filterListAdapter(moshtaryFaktorModels,sumTedadFaktor,sumMablaghFaktor);
        }else if (filter.length()>0){
            mView.get().hideFooter();
        }
    }





    @Override
    public void onUpdateData() {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void failedUpdate() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


}
