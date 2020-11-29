package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.ListMoshtarianModel;

import java.util.ArrayList;

public interface RptCustomersListByDistanceMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetRadiusConfig(ArrayList<String> arrayListRadiusItems);
        void onFailedGetConfig();
        void onGetCustomerList(ArrayList<ListMoshtarianModel> customerList , String radius);
        void closeLoadingAlert();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RptCustomersListByDistanceMVP.RequiredViewOps view);
        void getRadiusConfig();
        void getCustomerList(String radius , String latitude , String longitude);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptCustomersListByDistanceMVP.RequiredViewOps view);
        void onGetRadiusConfig(String maxRadius , String stepRadius);
        void onGetCustomerList(ArrayList<ListMoshtarianModel> customerList , String radius);
        void onErrorGetCustomerList();
    }


    interface ModelOps
    {
        void getRadiusConfig();
        void getCustomerList(String radius , String latitude , String longitude);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
