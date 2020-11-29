package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;

import java.util.ArrayList;

public interface RptThreeMonthPurchaseMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void setListAdapter(ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels , int sumTedad , long sumMablaghFaktor);
        void hideFooter();
        void showToast(int resId, int messageType , int duration);
        void closeLoadingDialog();
        void failedUpdate(String type, String error);
        void onGetRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels , int sumTedad, long sumMablaghFaktor);
    }


    interface PresenterOps
    {
        void getList();
        void getRizFaktor(int ccMoshtary);
        void onConfigurationChanged(RptThreeMonthPurchaseMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void updateData(String activityNameForLog);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptThreeMonthPurchaseMVP.RequiredViewOps view);
        void onGetList(ArrayList<Rpt3MonthGetSumModel> moshtaryFaktorModels);
        void onGetRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels);
        void onUpdateData();
        void failedUpdate();
    }


    interface ModelOps
    {
        void getList();
        void getRizFaktor(int ccMoshtary);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void updateData(String activityNameForLog);
        void onDestroy();
    }

}
