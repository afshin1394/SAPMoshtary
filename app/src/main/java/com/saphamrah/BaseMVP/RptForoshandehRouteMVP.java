package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptMasirModel;

import java.util.ArrayList;

public interface RptForoshandehRouteMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void setAdapter(ArrayList<RptMasirModel> rptMasirModels, RptMasirModel rptMasirModelSum, RptMasirModel rptMasirModelDailyAverage);
        void emptyList();
        void closeLoading();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void getRouteList();
        void updateRouteList();
        void onConfigurationChanged(RptForoshandehRouteMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptForoshandehRouteMVP.RequiredViewOps view);
        void onGetRouteList(ArrayList<RptMasirModel> rptMasirModels);
        void onSuccessUpdateRouteList();
        void onFailedUpdateRouteList(int resId);
    }


    interface ModelOps
    {
        void getRouteList();
        void updateRouteList();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
