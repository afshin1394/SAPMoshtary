package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AllMoshtaryPishdaryaftModel;
import com.saphamrah.Model.MoshtaryModel;

import java.util.ArrayList;

public interface PishDaryaftMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetAllCustomers(ArrayList<AllMoshtaryPishdaryaftModel> allMoshtaryPishdaryaftModels);
        void onGetSearchResult(ArrayList<AllMoshtaryPishdaryaftModel> moshtaryModels);
        void showToast(int resId, int messageType, int duration);
        void showAlertMessage(int resId, int messageType);
        void closeLoadingDialog();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void getAllCustomers();
        void searchCustomer(String searchWord, ArrayList<AllMoshtaryPishdaryaftModel> moshtaryModels);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void getDariaftPardakhtForSend(int ccMoshtary  , int position);
        void refresh();
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetAllCustomers( ArrayList<AllMoshtaryPishdaryaftModel> allMoshtaryPishdaryaftModels);
        void onConfigurationChanged(RequiredViewOps view);
        void onErrorSend(int resId);
        void onSuccessSend(int position);
        void onUpdateData();
        void failedUpdate();
    }


    interface ModelOps
    {
        void getAllCustomers();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void getDariaftPardakhtForSend(int ccMoshtary  , int position);
        void refresh();
    }

}
