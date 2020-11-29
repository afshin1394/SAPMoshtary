package com.saphamrah.BaseMVP;

import android.content.Context;

public interface EditNationalCodeMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onSuccessInsert();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(EditNationalCodeMVP.RequiredViewOps view);
        void checkNationalCode(int ccMoshtary, int codeNoeShakhsiat, String nationalCode, byte[] image);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onSuccessInsert();
        void onFailedInsert();
        void onConfigurationChanged(EditNationalCodeMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void insertNationalCode(int ccMoshtary , String nationalCode , String shenaseMeli , byte[] image);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
