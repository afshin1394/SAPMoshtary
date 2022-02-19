package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.GorohModel;

import java.util.ArrayList;

public interface AddCustomerBaseInfoMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetConfig(boolean requireCodeMeli, boolean requireMobile, boolean requireMasahat);
        void showCodeMeliHint(int resId);
        void showMobileHint(int resId);
        void showMasahateMaghazeHint(int resId);
        void showBirthDateHint(int resId);
        void onGetAnbarItems(ArrayList<Integer> itemIds , ArrayList<String> itemTitles);
        void onGetRotbeItems(ArrayList<Integer> itemsId , ArrayList<String> itemTitle);
        void onGetNoeShakhsiatItems(ArrayList<Integer> itemIds , ArrayList<String> itemTitles);
        void onGetNoeFaaliatItems(ArrayList<GorohModel> noeFaaliats);
        void onGetNoeSenfItems(ArrayList<GorohModel> noeSenfItems);
        void onGetNoeHamlItems(ArrayList<Integer> itemIds , ArrayList<String> itemTitle);
        void onGetNoeVosolItems(ArrayList<Integer> itemIds , ArrayList<String> itemTitle);
        void onErrorFirstName();
        void onErrorLastName();
        void onErrorTabloName();
        void onErrorNationalCode();
        void onErrorMobile();
        void onErrorNoeShakhsiat();
        void onErrorNoeFaaliat();
        void onErrorNoeSenf();
        void onErrorNoeVosol();
        void onErrorNoeHaml();
        void onErrorRotbeh();
        void onErrorMasahateMaghaze();
        void onGetBaseInfoFromShared(AddCustomerInfoModel addCustomerInfoModel);
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);

        void onErrorBirthDate();

    }


    interface PresenterOps
    {
        void onConfigurationChanged(AddCustomerBaseInfoMVP.RequiredViewOps view);
        void getConfig();
        void getAnbar();
        void getRotbe();
        void getNoeShakhsiat();
        void getNoeFaaliatItems();
        void getNoeSenfItems(String ccGorohLink);
        void getNoeHaml();
        void getNoeVosol(String ccNoeFaaliat);
        void checkCustomerBaseInfo(AddCustomerInfoModel addCustomerInfoModel, boolean requireCodeMeli, boolean requireMobile, boolean requireMasahateMaghaze);
        void getBaseInfoFromShared();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetConfig(boolean requireCodeMeli, boolean requireMobile, boolean requireMasahat , boolean requireBirthDate);
        void onGetAnbarItems(ArrayList<Integer> itemIds , ArrayList<String> itemTitles);
        void onGetRotbeItems(ArrayList<Integer> itemsId , ArrayList<String> itemTitle);
        void onGetNoeShakhsiatItems(ArrayList<Integer> itemIds , ArrayList<String> itemTitles);
        void onGetNoeFaaliatItems(ArrayList<GorohModel> noeFaaliats);
        void onGetNoeSenfItems(ArrayList<GorohModel> noeSenfs);
        void onGetNoeHaml(ArrayList<Integer> itemIds , ArrayList<String> itemTitle);
        void onGetNoeVosol(ArrayList<Integer> itemIds , ArrayList<String> itemTitle);
        void onSuccessfullySavedCustomerBaseInfo();
        void onFailedSaveCustomerBaseInfo();
        void onGetBaseInfoFromShared(AddCustomerInfoModel addCustomerInfoModel);
        void onConfigurationChanged(AddCustomerBaseInfoMVP.RequiredViewOps view);
        void onNetworkError(boolean closeActivity);
    }

    interface ModelOps
    {
        void getConfig();
        void getAnbarItems();
        void getRotbeItems();
        void getNoeShakhsiatItems();
        void getNoeFaaliatItems();
        void getNoeSenfItems(int ccGorohLink);
        void getNoeHamlItems();
        void getNoeVosolItems(String ccNoeFaaliat);
        void saveCustomerBaseInfo(AddCustomerInfoModel addCustomerInfoModel);
        void getBaseInfoFromShared();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
