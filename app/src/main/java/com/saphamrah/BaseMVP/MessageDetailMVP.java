package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.MessageBoxModel;

public interface MessageDetailMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetMessage(MessageBoxModel messageBoxModel);
        void onFailedGetMessage();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(MessageDetailMVP.RequiredViewOps view);
        void getMessage(int ccMessage);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetMessage(MessageBoxModel messageBoxModel);
        void onConfigurationChanged(MessageDetailMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getMessage(int ccMessage);
        void updateMessageStatus(MessageBoxModel messageBoxModel);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
