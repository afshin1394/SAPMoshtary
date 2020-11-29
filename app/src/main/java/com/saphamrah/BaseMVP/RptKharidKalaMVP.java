package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptKharidKalaModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public interface RptKharidKalaMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetListKharidKala(ArrayList<RptKharidKalaModel> kharidKalaModels);
        void closeLoadingDialog();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void getListKharidKala();
        void updateListKharidKala();
        void onConfigurationChanged(RptKharidKalaMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptKharidKalaMVP.RequiredViewOps view);
        void onGetListKharidKala(ArrayList<RptKharidKalaModel> kharidKalaModels);
        void onSuccessUpdate();
        void onErrorUpdate();
    }


    interface ModelOps
    {
        void getListKharidKala();
        void updateListKharidKala();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
