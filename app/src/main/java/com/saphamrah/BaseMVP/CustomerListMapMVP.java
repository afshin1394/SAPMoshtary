package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.ListMoshtarianModel;

import java.util.ArrayList;

public interface CustomerListMapMVP {

        interface RequiredViewOps
        {
            Context getAppContext();
            void onGetRadiusConfig(ArrayList<String> arrayListRadiusItems);
            void onFailedGetConfig();
            void onFailedGetCustomerInfo(int itemIndex , String error);
            void onCompleteGetCustomerInfo();
            void onGetCustomerList(ArrayList<ListMoshtarianModel> customerList , String radius);
            void closeLoadingAlert();
            void showToast(int resId, int messageType , int duration);
            void onSuccessfullyGetNewItemOfInfo(int itemIndex);


        }


        interface PresenterOps
        {
            void onConfigurationChanged(com.saphamrah.BaseMVP.CustomerListMapMVP.RequiredViewOps view);
            void getRadiusConfig();
            void checkSelectedCustomerForGetInfo(ListMoshtarianModel listMoshtarianModel);
            void getCustomerList(String radius , String latitude , String longitude);
            void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
            void onDestroy(boolean isChangingConfig);
        }


        interface RequiredPresenterOps
        {
            Context getAppContext();
            void onConfigurationChanged(com.saphamrah.BaseMVP.CustomerListMapMVP.RequiredViewOps view);
            void onGetRadiusConfig(String maxRadius , String stepRadius);
            void onGetCustomerList(ArrayList<ListMoshtarianModel> customerList , String radius);
            void onErrorGetCustomerList();
            void onFailedGetNewItem(int position , String errorMessage);
            void onSuccessfullyGetNewItem(int sumOfItems , int position);

        }


        interface ModelOps
        {
            void getRadiusConfig();
            void getCustomerList(String radius , String latitude , String longitude);
            void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
            void getSelectedCustomerInfo(ListMoshtarianModel listMoshtarianModel);
            void onDestroy();
        }



}
