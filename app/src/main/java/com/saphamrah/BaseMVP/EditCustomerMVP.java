package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.MoshtaryAddressModel;

import java.util.ArrayList;

public interface EditCustomerMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void hideEdttxtNationalCode();
        void hideEdttxtNoeFaaliat();
        void hideEdttxtNoeSenf();
        void hideEdttxtAnbar();
        void hideEdttxtMasahatMaghaze();
        void hideEdttxtSaateVisit();
        void hideEdttxtMobile();
        void onGetBaseCustomerInfo(String nationalCode, String mobile, String masahateMaghaze, int hasAnbar, String saateVisit, String noeSenf, String noeFaaliat, int codeNoeShakhsiat);
        void onGetMoshtaryAddress(ArrayList<MoshtaryAddressModel> moshtaryAddressModels);
        void onGetNoeFaaliat(ArrayList<Integer> noeFaaliatIds, ArrayList<String> noeFaaliatNames);
		void onGetNoeSenf(boolean showAlertDialog, ArrayList<Integer> noeSenfIds, ArrayList<String> noeSenfNames);																												  
        void onUpdateNoeFaaliat(String nameNoeFaaliat);
        void onUpdateNoeSenf(String nameNoeSenf);
        void onUpdateSaateVisit(String time);
        void onUpdateMobile(String mobile);
        void onUpdateHasAnbarMasahateMaghaze(int hasAnbar , int masahateMaghaze);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(EditCustomerMVP.RequiredViewOps view);
        void getUpdateableItems();
        void getCustomerInfo(int ccMoshtary);
        void checkNewAddressData(int ccMoshtary , int ccAddress , String telephone , String postalCode);
        void getNoeFaaliatSenf();
		void getNoeSenf(int ccGorohLink);								 
        void updateMoshtaryGoroh(int ccMoshtary , int ccNoeFaaliat, String nameNoeFaaliat, int ccNoeSenf, String nameNoeSenf);
        void updateSaateVisit(int ccMoshtary , String time);
        void updateCustomerMobile(int ccMoshtary , String mobile);
        void updateHasAnbarAndMasahateMaghaze(int ccMoshtary , int hasAnbar , int masahateMaghaze);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetUpdateableItems(ArrayList<ParameterChildModel> childParameterModels);
        void onGetBaseCustomerInfo(String nationalCode, String mobile, String masahateMaghaze, int hasAnbar, int codeNoeShakhsiat, String noeShakhsiat, String saateVisit, String noeSenf, String noeFaaliat);
        void onGetMoshtaryAddress(ArrayList<MoshtaryAddressModel> moshtaryAddressModels);
        void onFailedUpdate();
		void onGetNoeFaaliat(ArrayList<Integer> noeFaaliatIds, ArrayList<String> noeFaaliatNames);
        void onGetNoeSenf(boolean showAlertDialog, ArrayList<Integer> noeSenfIds, ArrayList<String> noeSenfNames);																									  
        void onUpdateNoeFaaliat(String nameNoeFaaliat);
        void onUpdateNoeSenf(String nameNoeSenf);
        void onUpdateSaateVisit(String time);
        void onUpdateMobile(String mobile);
        void onUpdateHasAnbarMasahateMaghaze(int hasAnbar , int masahateMaghaze);
		void onError(int resId);						
        void onConfigurationChanged(EditCustomerMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getUpdateableItems();
        void getCustomerInfo(int ccMoshtary);
        void updateAddress(int ccMoshtary , int ccAddress , String telephone , String postalCode);
        void getNoeFaaliat();
        void getNoeSenf(int ccGorohLink, boolean showAlertDialog);
        void updateNoeFaaliatSenf(int ccMoshtary , int ccNoeFaaliat, String nameNoeFaaliat, int ccNoeSenf, String nameNoeSenf);
        void updateSaateVisit(int ccMoshtary , String time);
        void updateCustomerMobile(int ccMoshtary , String mobile);
        void updateHasAnbarAndMasahateMaghaze(int ccMoshtary , int hasAnbar , int masahateMaghaze);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
