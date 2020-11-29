package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.MasirModel;

import java.util.ArrayList;

public interface CustomersListMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetAllMasirs(ArrayList<MasirModel> masirModels);
        void onGetAllCustomers(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels);
        void onGetCustomersByccMasir(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels);
        void onGetSearchResult(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels);
        void onSuccessfullyGetNewItemOfInfo(int itemIndex);
        void onCompleteGetCustomerInfo();
        void onFailedGetCustomerInfo(int itemIndex , String error);
        void showToast(int resId, int messageType , int duration);
        void closeLoadingDialog();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(CustomersListMVP.RequiredViewOps view);
        void getAllMasirs();
        void getAllCustomers();
        void getCustomersByccMasir(int ccMasir);
        void searchCustomer(String searchWord , ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels);
        void checkSelectedCustomerForGetInfo(int position , AllMoshtaryForoshandehModel allMoshtaryForoshandehModel);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void getAllMoshtarian(String tag, int ccForoshandeh);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetAllMasirs(ArrayList<MasirModel> masirModels);
        void onGetAllCustomers(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels);
        void onGetCustomersByccMasir(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels);
        void onSuccessfullyGetNewItem(int sumOfItems , int position);
        void onFailedGetNewItem(int position , String errorMessage);
        void onConfigurationChanged(CustomersListMVP.RequiredViewOps view);
        void onUpdateData();
        void failedUpdate();
    }


    interface ModelOps
    {
        void getAllMasirs();
        void getAllCustomers();
        void getCustomersByccMasir(int ccMasir);
        void getSelectedCustomerInfo(int position , AllMoshtaryForoshandehModel allMoshtaryForoshandehModel);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void getAllMoshtarian(String tag, int ccForoshandeh);
    }

}
