package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.MoshtaryModel;

import java.util.ArrayList;

public interface PishDaryaftMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetAllCustomers(ArrayList<MoshtaryModel> moshtaryModels);
        void onGetSearchResult(ArrayList<MoshtaryModel> moshtaryModels);
        void showToast(int resId, int messageType, int duration);
        void showAlertMessage(int resId, int messageType);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void getAllCustomers();
        void searchCustomer(String searchWord, ArrayList<MoshtaryModel> moshtaryModels);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);

        void getDariaftPardakhtForSend(int ccMoshtary  , int position);

    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetAllCustomers( ArrayList<MoshtaryModel> moshtaryModels);
        void onConfigurationChanged(RequiredViewOps view);
        void onErrorSend(int resId);
        void onSuccessSend(int position);
    }


    interface ModelOps
    {
        void getAllCustomers();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();

        void getDariaftPardakhtForSend(int ccMoshtary  , int position);
    }

}
