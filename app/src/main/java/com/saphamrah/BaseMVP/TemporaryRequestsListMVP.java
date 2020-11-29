package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.UIModel.CustomerAdamDarkhastModel;
import com.saphamrah.UIModel.CustomerDarkhastFaktorModel;

import java.util.ArrayList;

public interface TemporaryRequestsListMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetTemporaryRequests(ArrayList<CustomerDarkhastFaktorModel> models , int noeForoshandehMamorPakhsh);
        void onGetTemporaryNoRequests(ArrayList<CustomerAdamDarkhastModel> models);
        void showDeleteAlert(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void showSendAlert(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void onSuccessDeleteTempRequest(int position);
        void openPrintActivity(long ccDarkhastFaktor);
        void openSaveImageActivity(long ccDarkhastFaktor);
        void onSuccessDeleteNoRequest(int position);
        void showDeleteNoRequestAlert(int position , CustomerAdamDarkhastModel customerAdamDarkhastModel);
        void onSuccessSendNoRequest(int position);
        void onSuccessSendRequest(int position , long ccDarkhastFaktorNew);
        void onErrorSendRequest(int errorId);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(TemporaryRequestsListMVP.RequiredViewOps view);
        void getMyIP();
        /**
         * دریافت لیست درخواست هایی که ثبت شده اند ولی به سرور ارسال نشده.
         */
        void getTemporaryRequests();
        /**
         * دریافت لیست عدم درخواست هایی که ثبت شده ولی به سرور فرستاده نشده.
         */
        void getTemporaryNoRequests();
        void checkSelectedActionOnTempRequest(int action , int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void checkDeleteTempRequest(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void checkSelectedActionOnNoTempRequest(int action , int position , CustomerAdamDarkhastModel customerAdamDarkhastModel);
        void checkDeleteTempNoRequest(int position , CustomerAdamDarkhastModel customerAdamDarkhastModel);
        void checkSendTempRequest(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetTemporaryRequests(ArrayList<CustomerDarkhastFaktorModel> models , int noeForoshandehMamorPakhsh);
        void onGetTemporaryNoRequests(ArrayList<CustomerAdamDarkhastModel> models);
        void onConfigurationChanged(TemporaryRequestsListMVP.RequiredViewOps view);
        void onSuccessDeleteTempRequest(int position);
        void onCheckPrint(CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void onCheckSaveImage(long ccDarkhastFaktor);
        void onErrorFindServer();
        void onNetworkError(int errorId);
        void onErrorSendRequest(int errorId);
        void onErrorSendOtherDataOfFaktor();
        void onError(int resId);
        void onSuccessDeleteNoRequest(int position);
        void onSuccessSendNoRequest(int position);
        void onSuccessSendRequest(int position , long ccDarkhastFaktorNew);
    }


    interface ModelOps
    {
        void getMyIP();
        void getTemporaryRequests();
        void getTemporaryNoRequests();
        void deleteTempRequest(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void sendTempRequest(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void saveImageTempRequest(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void printTempRequest(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel);
        void deleteTempNoRequest(int position , CustomerAdamDarkhastModel customerAdamDarkhastModel);
        void sendTempNoRequest(int position , CustomerAdamDarkhastModel customerAdamDarkhastModel);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
