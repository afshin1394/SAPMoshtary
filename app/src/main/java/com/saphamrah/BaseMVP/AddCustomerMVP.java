package com.saphamrah.BaseMVP;

import android.content.Context;

public interface AddCustomerMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showServerMessage(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AddCustomerMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(AddCustomerMVP.RequiredViewOps view);
        void onNetworkError(boolean closeActivity);
    }


    interface ModelOps
    {
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }



}
