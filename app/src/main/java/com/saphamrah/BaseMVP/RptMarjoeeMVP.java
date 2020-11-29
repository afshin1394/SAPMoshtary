package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptMarjoeeKalaModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface RptMarjoeeMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetMarjoeeList(List<RptMarjoeeKalaModel> marjoeeKalaModels, HashMap<RptMarjoeeKalaModel , List<RptMarjoeeKalaModel>> hashMapMarjoeeKala, long sumCost, int sumCount);
        void hideFooterAndPrint();
        void closeLoading();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RptMarjoeeMVP.RequiredViewOps view);
        void getMarjoeeList();
        void updateMarjoeeList();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptMarjoeeMVP.RequiredViewOps view);
        void onGetMarjoeeList(ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels);
        void onErrorUpdateMarjoee();
        void onSuccessUpdateMarjoee();
    }


    interface ModelOps
    {
        void getMarjoeeList();
        void updateMarjoeeList();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
