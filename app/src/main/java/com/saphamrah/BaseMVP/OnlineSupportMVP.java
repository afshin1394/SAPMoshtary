package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.SupportCrispModel;

public interface OnlineSupportMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetCrispId(String crispId);
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(OnlineSupportMVP.RequiredViewOps view);
        void getCrispId();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(OnlineSupportMVP.RequiredViewOps view);
        void onGetCrispId(SupportCrispModel supportCrispModel);
        void onFailed(int resId , int messageType , int duration);
        void onNetworkError(boolean closeActivity);
    }


    interface ModelOps
    {
        void getCrispId();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
