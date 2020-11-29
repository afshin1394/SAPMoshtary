package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;

import java.util.ArrayList;

public interface RptDarkhastFaktorVazeiatMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetDarkhastFaktorVazeiatList(ArrayList<RptDarkhastFaktorVazeiatPPCModel> darkhastFaktorVazeiatPPCModels);
        void closeLoadingAlert();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RptDarkhastFaktorVazeiatMVP.RequiredViewOps view);
        void getDarkhastFaktorVazeiatList();
        void updateData();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptDarkhastFaktorVazeiatMVP.RequiredViewOps view);
        void onGetDarkhastFaktorVazeiatList(ArrayList<RptDarkhastFaktorVazeiatPPCModel> darkhastFaktorVazeiatPPCModels);
        void onSuccessUpdateData();
        void onErrorUpdateData();
    }


    interface ModelOps
    {
        void getDarkhastFaktorVazeiatList();
        void updateData();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
