package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;

import java.util.ArrayList;

public interface CustomerInfoMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetCustomerBaseInfo(MoshtaryModel moshtaryModel);
        void onGetNoeVosolHamlDarajeShakhsiat(String noeVosolName , String noeHamlName , String darajeName , String noeShakhsiat, int olaviat);
        void onGetSaateVisitOlaviat(String saateVisit , String olaviat);
        void onGetNoeSenfNoeMoshtary(String noeSenf , String noeMoshtary);
        void onGetCustomerAddresses(ArrayList<MoshtaryAddressModel> moshtaryAddressModels);
        void hideCustomerAddress();
        void onGetCustomerShomareHesab(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels);
        void hideCustomerShomareHesab();
        void onGetAnbarItems(final ArrayList<Integer> itemIds, final ArrayList<String> itemTitles);

        void onErrorNationalCode();
        void onErrorSaateVisit();
        void showToast(int resId, int messageType , int duration);
        void showNotFoundCustomerError();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(CustomerInfoMVP.RequiredViewOps view);
        void getCustomerInfo(int ccMoshtary,int ccSazmanForosh);
        void getAnbarItems();
        void checkNewChanges(int ccMoshtary , String newNationalCode, String newMasahateMaghaze, int newAnbar, String newSaateVisit);
        void checkNewAddressData(int ccMoshtary , int ccAddress , String telephone , String postalCode);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetCustomerBaseInfo(MoshtaryModel moshtaryModel);
        void onGetNoeVosolHamlDarajeShakhsiat(String noeVosolName , String noeHamlName , String darajeName , String noeShakhsiat, int olaviat);
        void onGetSaateVisitOlaviat(String saateVisit , String olaviat);
        void onGetNoeSenfNoeMoshtary(String noeSenf , String noeMoshtary);
        void onGetCustomerAddresses(ArrayList<MoshtaryAddressModel> moshtaryAddressModels);
        void onGetCustomerShomareHesab(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels);
        void onGetAnbarItems(final ArrayList<Integer> itemIds, final ArrayList<String> itemTitles);

        void onErrorNationalCode();
        void onErrorSaateVisit();
        void onErrorUpdateCustomer();
        void onSuccessUpdateCustomer();
        void onFailedUpdateAddress();
        void onConfigurationChanged(CustomerInfoMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getCustomerBaseInfo(int ccMoshtary, int ccSazmanForosh);
        void getCustomerAddresses(int ccMoshtary);
        void getCustomerShomareHesab(int ccMoshtary);
        void getCustomerCredit(int ccMoshtary);
        void getAnbarItems();
        void updateNewChange(int ccMoshtary , String newNationalCode, int newMasahateMaghaze, int newAnbar, String newSaateVisit);
        void updateAddress(int ccMoshtary , int ccAddress , String telephone , String postalCode);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
