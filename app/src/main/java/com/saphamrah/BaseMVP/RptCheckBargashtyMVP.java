package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.BargashtyModel;

import java.util.ArrayList;

public interface RptCheckBargashtyMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetListBargashty(ArrayList<BargashtyModel> bargashtyModels);
        void closeLoadingDialog();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RptCheckBargashtyMVP.RequiredViewOps view);
        void getListBargashty();
        void updateListBargashty();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptCheckBargashtyMVP.RequiredViewOps view);
        void onGetListBargashty(ArrayList<BargashtyModel> bargashtyModels);
        void onSuccessUpdateListBargashty();
        void onErrorUpdateListBargashty();
    }


    interface ModelOps
    {
        void getListBargashty();
        void updateListBargashty();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
