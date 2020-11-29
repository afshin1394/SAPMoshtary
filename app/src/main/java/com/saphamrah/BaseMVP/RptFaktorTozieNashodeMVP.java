package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptFaktorTozieNashodehModel;

import java.util.ArrayList;

public interface RptFaktorTozieNashodeMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void setListAdapter(ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels , float sum);
        void hideFooter();
        void closeLoadingDialog();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void getListFaktorTozieNashode();
        void updateFaktorTozeieNashode();
        void onConfigurationChanged(RptFaktorTozieNashodeMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptFaktorTozieNashodeMVP.RequiredViewOps view);
        void onGetListFaktorTozieNashode(ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels);
        void onSuccessUpdateListFaktorTozeisNashode();
        void onErrorUpdateListFaktorTozeisNashode();
    }


    interface ModelOps
    {
        void getListFaktorTozieNashode();
        void updateFaktorTozeieNashode();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
