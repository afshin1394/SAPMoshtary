package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.Model.OwghatModel;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.WeatherDataModel;
import com.saphamrah.Model.WeatherModel;
import com.saphamrah.Model.WindModel;

import java.util.ArrayList;

public interface MainFirstFragmentMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onFailedGetOwghat();
        void onGetForoshandehMamorPakhshName(String name);
        void onGetCurrentDate(String currentDate);
        void onGetWeather(WeatherModel weatherModel, WeatherDataModel weatherDataModel, WindModel windModel);
        void showAlertWeatherDetails(WeatherModel weatherModel, WeatherDataModel weatherDataModel, WindModel windModel);
        void onFailedGetWeather();
        void onShowOwghat(int drawableId , int stringId , OwghatModel owghatModel);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(MainFirstFragmentMVP.RequiredViewOps view);
        void getNoeForoshandehMamorPakhsh();
        void checkOwghat(String currentTime);
        void getForoshandehMamorPakhshName();
        void getCurrentDate();
        void getWeather(int type);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetNoeForoshandehMamorPakhsh(int noeMasouliat);
        void onGetOwghat(OwghatModel owghatModel , String currentTime);
        void onGetForoshandehMamorPakhshName(String name);
        void onGetCurrentDate(String currentDate);
        void onGetWeather(int type , WeatherModel weatherModel, WeatherDataModel weatherDataModel, WindModel windModel);
        void onFailedGetWeather();
        void onConfigurationChanged(MainFirstFragmentMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getNoeForoshandehMamorPakhsh();
        void getOwghat(String currentTime);
        void getForoshandehMamorPakhshName();
        void getCurrentDate();
        void getWeather(int type);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
