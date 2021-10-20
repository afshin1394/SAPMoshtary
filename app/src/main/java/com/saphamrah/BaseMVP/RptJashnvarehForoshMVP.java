package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.MVP.View.RptJashnvarehActivity;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptJashnvarehForoshModel;

import java.util.ArrayList;

public interface RptJashnvarehForoshMVP
{
    interface RequiredViewOps
    {
        Context getAppContext();
        void showToast(int resId, int messageType , int duration);
        void onIsForoshandehFromMenu();
        void onIsMamorPakhshFromMenu();
        void onIsFroshandehFromVerifyRequest(int ccMoshtary);
        void onIsMamorpakhshVerifyRequest(int ccMoshtaryExtra);
        void onGetAll(ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels);
        void showLoading();
        void closeLoading();
        void onGetCustomers(ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels);
        void onGetAllJashnvareh(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels);
        void onGetForoshandehScore(RptJashnvarehForoshModel rptJashnvarehForoshModel,ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void onGetSumDetails(ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels,int position);
        void onGetSearch(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels);

        void onGetDetails(ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels, int position);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RptForoshandehVisitMVP.RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void getAllCustomers(int ccMoshtary);
        void getAll(int ccMoshtary, RptJashnvarehActivity.Mode mode);
        void search(RptJashnvarehActivity.State state, String searchWord, ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels);
        void getAllJashnvareh(int ccMoshtary);
        void getSumForoshandehScore(int ccMoshtary);

        void getSumDetails(RptJashnvarehActivity.State state ,int ccMoshtaryExtra,int ccUniqueID ,int position);

        void getDetails(RptJashnvarehActivity.State state , RptJashnvarehForoshModel rptJashnvarehForoshModel,int ccMoshtaryExtra,int position);

        void checkNoeMasouliat(int ccMoshtaryExtra);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(RptForoshandehVisitMVP.RequiredViewOps view);
        void onSuccess(int resId);
        void onError(int resId);
        void onGetCustomers(ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels);
        void onGetAll(ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels);
        void onGetAllJashnvareh(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels);
        void onGetForoshandehScore(RptJashnvarehForoshModel rptJashnvarehForoshModel, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void onGetSumDetails(ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels,int position);
        void onGetSearch(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels);

        void onGetDetails(ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels, int position);

        void onWarning(int notFoundData);

        void onIsForoshandehFromMenu();

        void onIsMamorPakhshFromMenu();

        void onIsForoshandehFromVerifyRequest(int ccMoshtaryExtra);

        void onIsMamorPakhshFromVerifyRequest(int ccMoshtaryExtra);
    }


    interface ModelOps
    {

        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void getAllCustomers(int ccMoshtary);
        void getAll(int ccMoshtary, RptJashnvarehActivity.Mode mode);
        void searchCustomerName( String searchWord, ArrayList<RptJashnvarehForoshModel> jashnvarehForoshCustomers);
        void getAllJashnvareh(int ccMoshtary);
        void searchSharhJashnvareh(String searchWord, ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels);
        void getSumForoshandehScore(int ccMoshtary);
        void getAllCustomerByJashnvareh(int ccJashnvarehForosh,int ccMoshtaryExtra,int position);
        void getAllJashnvarehByCustomer(int ccMoshtary,int position);
        void getMoshtarySatr(int ccMoshtary,int position);
        void getJashnvarehSatr(int ccMoshtary,int ccJashnvarehForosh,int position);

        void getSumJashnvarehByccJashnvareh(int ccJashnvarehForosh,int ccMoshtaryExtra, int position);

        void checkNoeMasouliat(int ccMoshtaryExtra);
    }

}
