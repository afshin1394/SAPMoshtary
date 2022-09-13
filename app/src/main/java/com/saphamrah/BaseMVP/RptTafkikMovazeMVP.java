package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.Model.RptTafkikMovazeDataModel;
import com.saphamrah.Model.TafkikKalaMovazeDataModel;

import java.util.ArrayList;

public interface RptTafkikMovazeMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetDarkhastFaktorList(ArrayList<RptTafkikMovazeDataModel> darkhastFaktorVazeiatPPCModels);
        void onGetTafkikKalaList(ArrayList<TafkikKalaMovazeDataModel> tafkikKalaMovazeDataModels);
        void closeLoadingAlert();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RptTafkikMovazeMVP.RequiredViewOps view);
        void getDarkhastFaktorList();
        void getTafkikKalaList(String ccDarkhastFaktors);
        void updateData();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptTafkikMovazeMVP.RequiredViewOps view);
        void onGetDarkhastFaktortList(ArrayList<RptTafkikMovazeDataModel> rptTafkikMovazeDataModels);
        void onGetTafkikKalaList(ArrayList<TafkikKalaMovazeDataModel> tafkikKalaMovazeDataModels);
        void onSuccessUpdateData();
        void onErrorUpdateData();
    }


    interface ModelOps
    {
        void getDarkhastFaktorList();
        void getTafkikKalaList(String ccDarkhastFaktors);
        void updateData();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
