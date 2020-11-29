package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.NoeHesabModel;

import java.util.ArrayList;

public interface AddCustomerShomareHesabMVP
{
    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetBanksItems(ArrayList<BankModel> bankModels);
        void onGetNoeHesabItems(ArrayList<NoeHesabModel> noeHesabModels);
        void onGetShartBardashtItems(ArrayList<Integer> itemIds , ArrayList<String> itemTitles);
        void onSuccessSaveNewSomareHesab(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel);
        void onErrorBank();
        void onErrorNoeHesab();
        void onErrorShartBardasht();
        void onErrorNameShobe();
        void onErrorCodeShobe();
        void onErrorShomareHesab();
        void onErrorDuplicateShomareHesab();
        void onErrorSahebHesab();
        void onErrorWrongNameSahebHesab();
        void onSuccessGetAddCustomerInfo(AddCustomerInfoModel addCustomerInfoModel);
        void onSuccessDeleteShomareHesab(int position);
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(AddCustomerShomareHesabMVP.RequiredViewOps view);
        void getBanksItems();
        void getNoeHesabItems();
        void getShartBardashtItems();
        void getAddCustomerInfo();
        void checkNewShomareHesab(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel , String customerNameFamily , ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels);
        void deleteShomareHesab(int position);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetBanksItems(ArrayList<BankModel> bankModels);
        void onGetNoeHesabItems(ArrayList<NoeHesabModel> noeHesabModels);
        void onGetShartBardashtItems(ArrayList<Integer> itemIds , ArrayList<String> itemTitles);
        void onSuccessSaveNewSomareHesab(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel);
        void onFailedSaveNewShomareHesab(int errorMessageResId);
        void onGetAddCustomerInfo(AddCustomerInfoModel addCustomerInfoModel);
        void onSuccessDeleteShomareHesab(int position);
        void onFailedDeleteShomareHesab();
        void onConfigurationChanged(AddCustomerShomareHesabMVP.RequiredViewOps view);
        void onNetworkError(boolean closeActivity);
    }

    interface ModelOps
    {
        void getBanksItems();
        void getNoeHesabItems();
        void getShartBardashtItems();
        void getAddCustomerInfo();
        void saveNewMoshtaryShomareHesab(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel);
        void deleteShomareHesab(int position);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
