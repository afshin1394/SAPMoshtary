package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.UIModel.CustomerAddressModel;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public interface ForoshandehRouteMapMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetCustomerInfo(ArrayList<CustomerAddressModel> customerAddressModels);
        void onGetGpsDatas(ArrayList<GeoPoint> geoPoints);
        void closeLoading();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void getCustomerInfo();
        void getGpsDatas();
        void updateGPSData();
        void onConfigurationChanged(ForoshandehRouteMapMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetCustomerInfo(ArrayList<CustomerAddressModel> customerAddressModels);
        void onGetGpsDatas(ArrayList<GPSDataModel> gpsDataModels);
        void onSuccessUpdateGPSData(ArrayList<GPSDataModel> gpsDataModels);
        void onFailedUpdateGPSData();
        void onConfigurationChanged(ForoshandehRouteMapMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getCustomerInfo();
        void getGpsDatas();
        void updateGPSData();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
