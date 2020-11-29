package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;


import java.util.ArrayList;

public interface RptSaleGoalLevel3MVP {
    interface RequiredViewOps
    {
        Context getAppContext();
        void drawSaleGoalReportLevel3(ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels);
        void closeLoadingDialog();
        void showToast(int resId, int messageType , int duration);
    }
    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetSaleGoalReportLevel3(ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels);
        void onSuccessUpdateSaleGoalReportLevel3();
        void onErrorUpdateSaleGoalReportLevel3();
        void onNetworkError();
    }
    interface PresenterOps
    {
        void onConfigurationChanged(RptSaleGoalLevel3MVP.RequiredViewOps view);
        void getSaleGoalReportLevel3(int Brand,int gorohKala);
        void getAllSaleGoalReportLevel3();
        void getAllSaleGoalByBrandLevel3(int mBrand);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }
    interface ModelOps
    {
        void getSaleGoalReportLevel3(int Brand,int gorohKala);
        void getAllSaleGoalReportLevel3();
        void getAllSaleGoalByBrandLevel3(int Brand);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);

    }
}
