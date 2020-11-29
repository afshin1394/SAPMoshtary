package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.ListMoshtarianModel;

import java.util.List;

public interface AmargarCustomersMapMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void showCustomers(List<ListMoshtarianModel> listMoshtarianModels);
        void showErrorNotFoundCustomer();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AmargarCustomersMapMVP.RequiredViewOps view);
        void getCustomers();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(AmargarCustomersMapMVP.RequiredViewOps view);
        void onGetCustomers(List<ListMoshtarianModel> listMoshtarianModels);
    }


    interface ModelOps
    {
        void getCustomers();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
