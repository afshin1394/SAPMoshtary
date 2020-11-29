package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;

import java.util.ArrayList;

public interface TreasuryListOfflineMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int noeMasouliat);
        void onGetCustomerAddress(double latitude , double longitude);
        void onGetFaktorImage(byte[] faktorImage);
        void openDarkhastKalaActivity(long ccDarkhastFaktor , int ccMoshtary);
        void onError(boolean closeActivity , int errorResId);
        void onError(boolean closeActivity , String message);
        void showAlertMessage(int resId, int messageType);
        void showAlertMessage(int resId , String parameter, int messageType);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(TreasuryListOfflineMVP.RequiredViewOps view);
        void checkDateAndFakeLocation();
        void getTreasuryList(int faktorRooz, int sortType);
        void getCustomerLocation(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void getFaktorImage(long ccDarkhastFaktor);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(TreasuryListOfflineMVP.RequiredViewOps view);
        void onErrorUseFakeLocation();
        void onCheckServerTime(boolean valid, String message);
        void onGetCustomerAddress(double latitude , double longitude);
        void onGetFaktorImage(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel);
        void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int faktorRooz , int noeMasouliat);
        void onErrorAccessToLocation();
        void onError(int resId);
    }


    interface ModelOps
    {
        void checkDateAndFakeLocation();
        void getTreasuryList(int faktorRooz, int sortType);
        void getCustomerLocation(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel);
        void getFaktorImage(long ccDarkhastFaktor);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
