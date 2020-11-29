package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AddCustomerInfoModel;

import java.util.ArrayList;

public interface AddCustomerListMVP 
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onErrorNotAccessForInsertNewCustomer();
        void onGetNewCustomers(ArrayList<AddCustomerInfoModel> addCustomerInfoModels);
        void onDeleteCustomer(int ccMoshtary , int position);
        void onRemoveAddCustomerInfoShared();
        void onOutOfPolygonError();
        void onErrorAccessToLocation();
        void onSuccessSendToServer(int newccMoshtary , int oldccMoshtary);
        void closeLoading();
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AddCustomerListMVP.RequiredViewOps view);
        void getConfig();
        void getNewCustomers();
        void checkDeleteCustomer(int ccMoshtary , int position);
        void checkAndRemoveAddCustomerInfoShared();
        void checkSendToServer(int ccMoshtary);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetConfig(boolean canInsertNewCustomer);
        void onGetNewCustomers(ArrayList<AddCustomerInfoModel> addCustomerInfoModels);
        void onDeletedCustomer(int ccMoshtary , int position);
        void onFailedDeleteCustomer();
        void onRemoveAddCustomerInfoShared();
        void onOutOfPolygonError();
        void onErrorAccessToLocation();
        void onConfigurationChanged(AddCustomerListMVP.RequiredViewOps view);
        void onNetworkError(boolean closeActivity);
        void onErrorSendToServer(int resErrorId);
        void onSuccessSendToServer(int newccMoshtary , int oldccMoshtary);
    }


    interface ModelOps
    {
        void getConfig();
        void getNewCustomers();
        void deleteCustomer(int ccMoshtary , int position);
        void checkAndRemoveAddCustomerInfoShared();
        void sendToServer(int ccMoshtary);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }
    
}
