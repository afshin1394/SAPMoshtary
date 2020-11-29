package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.BankLocation;
import com.saphamrah.Model.BankModel;

import java.util.ArrayList;

public interface BanksInfoMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void showBanks(ArrayList<BankModel> bankModels);
        void showBranchOfBank(ArrayList<BankLocation> bankLocations);
        void showError(int resId);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(BanksInfoMVP.RequiredViewOps view);
        void getListOfAllBanks();
        void getBranchOfBank(String noeBank, String lat, String lng);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetListOfAllBanks(ArrayList<BankModel> bankModels);
        void onGetBranchOfBank(ArrayList<BankLocation> bankLocations);
        void onError(int resId);
        void onConfigurationChanged(BanksInfoMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getListOfAllBanks();
        void getBranchOfBank(String noeBank, String lat, String lng);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
