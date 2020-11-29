package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MoshtaryModel;

public interface AddCustomerApplyMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetAddCustomerInfoModel(AddCustomerInfoModel addCustomerInfoModel);
        void onErrorFirstName();
        void onErrorLastName();
        void onErrorTabloName();
        void onErrorNationalCode();
        void onErrorMobile();
        void onErrorNoeShakhsiat();
        void onErrorNoeFaaliat();
        void onErrorNoeSenf();
        void onErrorNoeVosol();
        void onErrorNoeHaml();
        void onErrorEmptyAddress();
        void onErrorNeedTahvilAddress();
        void onErrorNeedDarkhastAddress();
        void onErrorExistAllTypeOfAddress();
        void onErrorWrongNameSahebHesab();
        void onErrorDuplicateShomareHesab();
        void onSuccessInsertNewCustomer(AddCustomerInfoModel addCustomerInfoModel);
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showServerMessage(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AddCustomerApplyMVP.RequiredViewOps view);
        void getAddCustomerInfoModel();
        void checkAddNewCustomer(AddCustomerInfoModel addCustomerInfoModel);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetAddCustomerInfoModel(AddCustomerInfoModel addCustomerInfoModel);
        void onSuccessInsertNewCustomer(AddCustomerInfoModel addCustomerInfoModel);
        void onFailedInsertNewCustomer(int errorMessageResId);
        void onConfigurationChanged(AddCustomerApplyMVP.RequiredViewOps view);
        void onNetworkError(boolean closeActivity);
    }


    interface ModelOps
    {
        void getAddCustomerInfoModel();
        void insertNewCustomer(AddCustomerInfoModel addCustomerInfoModel);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
