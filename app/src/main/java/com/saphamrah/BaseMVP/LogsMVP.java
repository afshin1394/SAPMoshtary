package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.LogPPCModel;

import java.util.ArrayList;

public interface LogsMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void setAdapter(ArrayList<LogPPCModel> arrayListLogs);
        void disableSendButton();
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showServerMessage(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId);
        void showAlertSendEmail();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(LogsMVP.RequiredViewOps view);
        void getExceptionsToShow();
        void copyLog(String tag , String value);
        void checkLogsForSendToServer();
        void checkExceptionsToMail();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetExceptionsToShow(ArrayList<LogPPCModel> arrayListLogs);
        void onGetExceptionForSendToMail(ArrayList<LogPPCModel> arrayListLogsPPC);
        void onErrorSendLogsToServer(String errorMessage);
        void onSuccessSendLogsToServer(String message);
        void onErrorSendExceptionsToMail(String message);
        void onSuccessSendExceptionsToMail();
    }


    interface ModelOps
    {
        void getExceptionsToShow();
        void sendLogsToServer();      // send exception and content-length of response to server
        void getExceptions();
        void postLogPPCToMail(ArrayList<LogPPCModel> arrayListLogsPPC); // send exception to mail
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
