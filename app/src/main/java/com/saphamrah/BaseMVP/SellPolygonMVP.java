package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.PolygonForoshSatrModel;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public interface SellPolygonMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetCurrentLocation(GeoPoint currentPoint);
        void drawSellPolygon(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels , String polygonColor);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(SellPolygonMVP.RequiredViewOps view);
        void getCurrentLocation();
        void getSellPolygon();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetCurrentLocation(GeoPoint currentPoint);
        void onGetSellPolygon(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels , String polygonColor);
        void onConfigurationChanged(SellPolygonMVP.RequiredViewOps view);
        void onNetworkError(boolean closeActivity);
        void onErrorAccessToLocation();
    }


    interface ModelOps
    {
        void getCurrentLocation();
        void getSellPolygon();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }



}
