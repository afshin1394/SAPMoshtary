package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptMandehdarModel;

import java.util.ArrayList;

public interface RptFaktorMandehDarMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetListMandehDar(ArrayList<RptMandehdarModel> rptMandehdarModels);
        void closeLoadingDialog();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RptFaktorMandehDarMVP.RequiredViewOps view);
        void getListMandehDar();
        void updateListMandehDar();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptFaktorMandehDarMVP.RequiredViewOps view);
        void onGetListMandehDar(ArrayList<RptMandehdarModel> rptMandehdarModels);
        void onSuccessUpdateListMandehDar();
        void onErrorUpdateListMandehDar();
    }


    interface ModelOps
    {
        void getListMandehDar();
        void updateListMandehDar();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
