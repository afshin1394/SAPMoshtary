package com.saphamrah.BaseMVP;

import android.content.Context;

public interface SpeedTestMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void showInternetProvider(String internetProvider);
        void showGoodAlert();
        void showMediumAlert();
        void showBadAlert();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(SpeedTestMVP.RequiredViewOps view);
        void checkInternetProvider();
        void checkResult(double downloadSpeed , double uploadSpeed);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetInternetProvider(String internetProvider);
        void onConfigurationChanged(SpeedTestMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getInternetProvider();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
