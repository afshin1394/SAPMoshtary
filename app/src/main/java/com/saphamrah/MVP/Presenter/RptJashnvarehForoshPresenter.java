package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptForoshandehVisitMVP;
import com.saphamrah.BaseMVP.RptJashnvarehForoshMVP;
import com.saphamrah.MVP.View.RptJashnvarehActivity;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptJashnvarehForoshModel;
import com.saphamrah.Utils.Constants;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptJashnvarehForoshPresenter implements RptJashnvarehForoshMVP.PresenterOps , RptJashnvarehForoshMVP.RequiredPresenterOps
{
    private WeakReference<RptJashnvarehForoshMVP.RequiredViewOps> mView;
    private RptJashnvarehForoshMVP.ModelOps mModel;

    public RptJashnvarehForoshPresenter(RptJashnvarehForoshMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new com.saphamrah.MVP.Model.RptJashnvarehForoshModel(this);
    }

    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onConfigurationChanged(RptForoshandehVisitMVP.RequiredViewOps view) {

    }

    @Override
    public void onSuccess(int resId) {
        mView.get().showToast(resId, Constants.SUCCESS_MESSAGE(),Constants.DURATION_SHORT());
        mView.get().closeLoading();

    }

    @Override
    public void onError(int resId) {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(),Constants.DURATION_SHORT());
        mView.get().closeLoading();
    }

    @Override
    public void onGetCustomers(ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels) {
        mView.get().onGetCustomers(jashnavareForoshModels);
    }


    @Override
    public void onGetAll(ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels) {
         mView.get().onGetAll(jashnavareForoshModels);
    }



    @Override
    public void onGetAllJashnvareh(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
        mView.get().onGetAllJashnvareh(rptJashnvarehForoshModels);
    }


    @Override
    public void onGetForoshandehScore(RptJashnvarehForoshModel rptJashnvarehForoshModel, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        mView.get().onGetForoshandehScore(rptJashnvarehForoshModel,foroshandehMamorPakhshModel);
    }



    @Override
    public void onGetSumDetails(ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels,int position) {
        mView.get().onGetSumDetails(jashnvarehForoshModels,position);
    }

    @Override
    public void onGetSearch(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
        mView.get().onGetSearch(rptJashnvarehForoshModels);
    }

    @Override
    public void onGetDetails(ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels, int position) {
        mView.get().onGetDetails(jashnvarehForoshModels,position);
    }

    @Override
    public void onWarning(int resId) {
        mView.get().showToast(resId, Constants.INFO_MESSAGE(),Constants.DURATION_SHORT());
        mView.get().closeLoading();
    }

    @Override
    public void onIsForoshandehFromMenu() {
        mView.get().onIsForoshandehFromMenu();
    }

    @Override
    public void onIsMamorPakhshFromMenu() {
        mView.get().onIsMamorPakhshFromMenu();

    }

    @Override
    public void onIsForoshandehFromVerifyRequest(int ccMoshtaryExtra) {
        mView.get().onIsFroshandehFromVerifyRequest(ccMoshtaryExtra);
    }

    @Override
    public void onIsMamorPakhshFromVerifyRequest(int ccMoshtaryExtra) {
        mView.get().onIsMamorpakhshVerifyRequest(ccMoshtaryExtra);
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {
       mModel.onDestroy();
    }

    @Override
    public void getAllCustomers(int ccMoshtary) {
       mModel.getAllCustomers(ccMoshtary);
    }



    @Override
    public void getAll(int ccMoshtary, RptJashnvarehActivity.Mode mode) {
        mView.get().showLoading();
        mModel.getAll(ccMoshtary,mode);
    }

    @Override
    public void search(RptJashnvarehActivity.State state, String searchWord, ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
        switch (state){
            case Jashnvareh:
                mModel.searchSharhJashnvareh(searchWord,rptJashnvarehForoshModels);
                break;

            case Moshtary:
                mModel.searchCustomerName(searchWord , rptJashnvarehForoshModels);
                break;
        }
    }

    @Override
    public void getAllJashnvareh(int ccMoshtary) {
        mModel.getAllJashnvareh(ccMoshtary);
    }

    @Override
    public void getSumForoshandehScore(int ccMoshtary) {
        mModel.getSumForoshandehScore(ccMoshtary);
    }

    @Override
    public void getSumDetails(RptJashnvarehActivity.State state,int ccMoshtaryExtra, int ccUnique,int position) {
        switch (state){
            case Jashnvareh:
                mModel.getSumJashnvarehByccJashnvareh(ccUnique,ccMoshtaryExtra,position);
                break;
            case Moshtary:
                mModel.getAllJashnvarehByCustomer(ccUnique,position);
                break;
        }
    }

    @Override
    public void getDetails(RptJashnvarehActivity.State state, RptJashnvarehForoshModel rptJashnvarehForoshModel,int ccMoshtaryExtra, int position) {
        switch (state){
            case Jashnvareh:
                  mModel.getAllCustomerByJashnvareh(rptJashnvarehForoshModel.getCcJashnvarehForosh(), ccMoshtaryExtra,position);
                break;


            case Moshtary:

                mModel.getMoshtarySatr(rptJashnvarehForoshModel.getCcMoshtary(),position);

                break;

        }
    }

    @Override
    public void checkNoeMasouliat(int ccMoshtary) {
        mModel.checkNoeMasouliat(ccMoshtary);
    }


}
