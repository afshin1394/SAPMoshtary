package com.saphamrah.BaseMVP;

import android.content.Context;
import com.saphamrah.Valhalla.Location;

import com.saphamrah.Model.MoshtaryModel;

public interface RouteMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void showTripTime(int hour, int min, String timeInDistance);
        void showTripDistance(String distance , int resIdUnit);
        void showCustomerName(String customerName);
        void hideCustomerName();
		void onGetRoute(String routeResult);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RouteMVP.RequiredViewOps view);
        void getTripInfo(String ccMoshtary, int tripTime, double tripDistance);
		void route(Location startLocation, Location destinationLocation, String routeId);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetCustomerName(MoshtaryModel moshtaryModel);
        void onGetRoute(String routeResult);
        void onError(int resId);
        void onConfigurationChanged(RouteMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getCustomerName(int ccMoshtary);
		void route(Location startLocation, Location destinationLocation, String routeId);																				 
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
