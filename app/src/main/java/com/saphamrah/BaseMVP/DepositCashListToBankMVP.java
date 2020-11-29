package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.TafkikJozeModel;

import java.util.ArrayList;
import java.util.List;

public interface DepositCashListToBankMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void showAllTafkik(ArrayList<TafkikJozeModel> tafkikJozeModels);
        void showAlertNotFoundTafkik();
        void showAlertNotSelectedTafkik();
        void showAllShomareHesab(List<MarkazShomarehHesabModel> markazShomarehHesabModels);
        void showAlertNotFoundShomareHesab();
        void showMablaghMandehVajhNaghd(String sumMablaghVojohNaghd);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(DepositCashListToBankMVP.RequiredViewOps view);
        void getAllTafkik();
        void getAllShomareHesab();
        void checkTafkikForMablaghMandehVajh(long ccTafkikJoze);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(DepositCashListToBankMVP.RequiredViewOps view);
        void onGetAllTafkik(ArrayList<TafkikJozeModel> tafkikJozeModels);
        void onGetAllShomareHesab(List<MarkazShomarehHesabModel> markazShomarehHesabModels);
        void onGetMablaghMandehVajhNaghd(double sumMablaghVojohNaghd);
    }


    interface ModelOps
    {
        void getAllTafkik();
        void getAllShomareHesab();
        void getMablaghMandehVajhNaghd(long ccTafkikJoze);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
