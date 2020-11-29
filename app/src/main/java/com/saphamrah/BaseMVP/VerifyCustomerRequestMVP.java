package com.saphamrah.BaseMVP;

import android.content.Context;

public interface VerifyCustomerRequestMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void openBarkhordAvalieActivity();
        void onGetAgreementContent(String text);
        void onSuccessInsertCustomerSign();
        void openFaktorDetailActivity(long ccDarkhastFaktor);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(VerifyCustomerRequestMVP.RequiredViewOps view);
        void checkBottomBarClick(int position);
        void getAgreementContent(int ccMoshtary);
        void checkSaveBitmap(int ccMoshtary , byte[] customerSignPic);
        void getccDarkhastFaktor();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(VerifyCustomerRequestMVP.RequiredViewOps view);
        void onGetAgreementContent(String text);
        void onSuccessInsertCustomerSign();
        void onFailedInsertCustomerSign(int errorResId);
        void onGetccDarkhastFaktor(long ccDarkhastFaktor);
    }


    interface ModelOps
    {
        void getAgreementContent(int ccMoshtary);
        void saveBitmap(int ccMoshtary , byte[] customerSignPic);
        void getccDarkhastFaktor();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
