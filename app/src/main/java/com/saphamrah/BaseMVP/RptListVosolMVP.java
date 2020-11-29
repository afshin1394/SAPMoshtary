package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptListVosolModel;

import java.util.ArrayList;

public interface RptListVosolMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetListVosol(ArrayList<RptListVosolModel> rptListVosolModels);
        void closeLoadingDialog();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RptListVosolMVP.RequiredViewOps view);
        void getListVosol();
        void updateListVosol();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptListVosolMVP.RequiredViewOps view);
        void onGetListVosol(ArrayList<RptListVosolModel> rptListVosolModels);
        void onSuccessUpdateListVosol();
        void onErrorUpdateListVosol();
    }


    interface ModelOps
    {
        void getListVosol();
        void updateListVosol();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
