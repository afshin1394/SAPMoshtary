package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;


import java.util.ArrayList;

public interface RptSaleGoalLevel2MVP {
    interface RequiredViewOps
    {
        Context getAppContext();
        void drawSaleGoalReportLevel2(ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels);
        void closeLoadingDialog();
        void showToast(int resId, int messageType , int duration);
    }
    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetSaleGoalReportLevel2(ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels);
        void onNetworkError();
    }
    interface PresenterOps
    {
        void onConfigurationChanged(RptSaleGoalLevel2MVP.RequiredViewOps view);
        void getSaleGoalReportLevel2(int Brand);
        void getAllSaleGoalReportLevel2();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }
    interface ModelOps
    {
        void getAllSaleGoalReportsLevel2();
        void getSaleGoalReportLevel2(int Brand);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);

    }
}
