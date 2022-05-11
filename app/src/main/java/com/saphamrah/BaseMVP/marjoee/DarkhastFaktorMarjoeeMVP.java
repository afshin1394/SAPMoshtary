package com.saphamrah.BaseMVP.marjoee;

import com.saphamrah.Model.ElamMarjoeeForoshandehModel;

import java.util.ArrayList;

public interface DarkhastFaktorMarjoeeMVP
{

    interface RequiredViewOps
    {
        void showToast(int resId, int messageType, int duration);
        void showLoading();
        void closeLoading();
        void onSuccessSend();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void sendMarjoee(long ccDarkhastFaktor, int ccMoshtary);
    }


    interface RequiredPresenterOps
    {
        void onSuccessSend();
        void onErrorSend(int resId);
    }


    interface ModelOps
    {
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void sendMarjoee(long ccDarkhastFaktor,int ccMoshtary);
    }

}
