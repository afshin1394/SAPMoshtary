package com.saphamrah.BaseMVP.RptThreeMonth;

import android.content.Context;

import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;

import java.util.ArrayList;

public interface RptThreeMonthRizFaktorMVP {
    interface RequiredViewOps
    {
        Context getAppContext();
        void hideFooter();
        void showToast(int resId, int messageType , int duration);
        void closeLoadingDialog();
        void failedUpdate(String type, String error);
        void onGetRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels , int sumTedad, long sumMablaghFaktor);
        void onGetFilteredRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels , int sumTedad, long sumMablaghFaktor);
    }


    interface PresenterOps
    {

        void getRizFaktor(int ccMoshtary);
        void getFilteredListByFactorNu(int ccMoshtary,String filter);
        void onConfigurationChanged(RptThreeMonthRizFaktorMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptThreeMonthRizFaktorMVP.RequiredViewOps view);
        void onGetRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels);
        void onGetFilteredRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels,String filter);
    }


    interface ModelOps
    {
        void getFilteredListByFactorNu(int ccMoshtary,String filter);
        void getRizFaktor(int ccMoshtary);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
