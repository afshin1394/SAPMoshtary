package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.MVP.View.RptJashnvarehActivity;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptJashnvarehForoshModel;
import com.saphamrah.Repository.RptJashnvarehForoshRepository;

import java.util.ArrayList;

public interface RptJashnvarehForoshMVP
{
    interface RequiredViewOps
    {
        Context getAppContext();
        void showToast(int resId, int messageType , int duration);

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
        void getAllCustomers();
        void getAll();
        void search(RptJashnvarehActivity.State state, String searchWord, ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels);
        void getAllJashnvareh();
        void getSumForoshandehScore();

        void getSumDetails(RptJashnvarehActivity.State state, int ccUniqueID,int position);

        void getDetails(RptJashnvarehActivity.State state , RptJashnvarehForoshModel rptJashnvarehForoshModel,int position);
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
    }


    interface ModelOps
    {

        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void getAllCustomers();
        void getAll();
        void searchCustomerName( String searchWord, ArrayList<RptJashnvarehForoshModel> jashnvarehForoshCustomers);
        void getAllJashnvareh();
        void searchSharhJashnvareh(String searchWord, ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels);
        void getSumForoshandehScore();
        void getAllCustomerByJashnvareh(int ccJashnvarehForosh,int position);
        void getAllJashnvarehByCustomer(int ccMoshtary,int position);
        void getMoshtarySatr(int ccMoshtary,int position);
        void getJashnvarehSatr(int ccMoshtary,int ccJashnvarehForosh,int position);

        void getSumJashnvarehByccJashnvareh(int ccJashnvarehForosh, int position);
    }

}
