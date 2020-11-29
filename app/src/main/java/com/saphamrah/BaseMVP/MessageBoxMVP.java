package com.saphamrah.BaseMVP;

import android.content.Context;


import com.saphamrah.Model.MessageBoxModel;

import java.util.ArrayList;

public interface MessageBoxMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetMessages(ArrayList<MessageBoxModel> messageBoxModels);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(MessageBoxMVP.RequiredViewOps view);
        void getMessages();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetMessages(ArrayList<MessageBoxModel> messageBoxModels);
        void onConfigurationChanged(MessageBoxMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getMessages();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
