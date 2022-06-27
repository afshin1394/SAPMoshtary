package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AmvalModel;

import java.util.ArrayList;

public interface SabtAmvalMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void setListAdapter(ArrayList<AmvalModel> sabtMoshtaryAmvalModels);
        void onArraySabtMalListener(ArrayList<AmvalModel> sabtMoshtaryAmvalModels);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void getListAmvals(int ccMoshtary, int ccSazmanForosh);
//        void getListFaktorTozieNashode();
        int amvalSabtShodeh(String barcode, int ccMoshtary);
        void getSabtedMal(int ccMoshtary);
        void onConfigurationChanged(SabtAmvalMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(SabtAmvalMVP.RequiredViewOps view);
        void onGetListAmvals(ArrayList<AmvalModel> sabtMoshtaryAmvalModels);
        void onGetSabtedAmvals(ArrayList<AmvalModel> models);
        void getSabtedAmvals(int ccMoshtary);
    }


    interface ModelOps
    {
        void getListAmvals(int ccMoshtary, int ccSazmanForosh);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        int amvalSabtShodeh(String barcode, int ccMoshtary);
        void getSabtedMals(int ccMoshtary);
        void onDestroy();
    }

}
