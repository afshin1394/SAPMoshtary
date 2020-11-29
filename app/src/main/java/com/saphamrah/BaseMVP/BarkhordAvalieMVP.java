package com.saphamrah.BaseMVP;

import android.content.Context;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import java.util.ArrayList;

public interface BarkhordAvalieMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetBarkhords(ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhords);
        void openMojodiGiriActivity();
        void openDarkhastKalaActivity();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(BarkhordAvalieMVP.RequiredViewOps view);
        void checkBottomBarClick(int position, int ccMoshtary);
        void getBarkhords(int ccMoshtary);
        void checkNewBarkhord(int ccMoshtary , String desc);
        void checkRemoveBarkhord(BarkhordForoshandehBaMoshtaryModel barkhord);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetBarkhords(ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhords);
        void onSuccessInsertNewBarkhord();
        void onFailedInsertNewBarkhord();
        void onFailedRemoveBarkhord();
        void onGetCountTodayBarkhord();
        void onError(int resId);
        void onConfigurationChanged(BarkhordAvalieMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void countBarkhordForToday(int ccMoshtary);
        void getBarkhords(int ccMoshtary);
        void insertNewBarkhord(int ccMoshtary , String desc);
        void removeBarkhord(int ccBarkhord , int ccMoshtary);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
