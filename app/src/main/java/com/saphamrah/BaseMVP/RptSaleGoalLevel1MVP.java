package com.saphamrah.BaseMVP;

import android.content.Context;


import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;


import java.util.ArrayList;

public interface RptSaleGoalLevel1MVP {
    interface RequiredViewOps
    {
        Context getAppContext();
        void drawSaleGoalReport(ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels);
        void closeLoadingDialog();
        void showToast(int resId, int messageType , int duration);
    }
    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetSaleGoalReport(ArrayList<BaseHadafForoshModel> baseHadafForoshModels);
        void onSuccessUpdateSaleGoalReport();
        void onErrorUpdateSaleGoalReport();
        void onConfigurationChanged(RptSaleGoalLevel1MVP.RequiredViewOps view);
        void onNetworkError();
    }
    interface PresenterOps
    {
        void onConfigurationChanged(RptSaleGoalLevel1MVP.RequiredViewOps view);
        void getSaleGoalReport();
        void updateSaleGoalReport();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }
    interface ModelOps
    {
        void getSaleGoalReport();
        void updateSaleGoalReport();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }
}
