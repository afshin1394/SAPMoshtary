package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryModel;

import java.util.ArrayList;


public interface RequestCustomerListMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetDateOfGetProgram(String date);
        void onGetCustomers(ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh , boolean canUpdateCustomer);
        void onGetSearch(ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh);
        void showBarkhordAvalieActivity(int ccMoshtary);
        void showMojoodiGiriActivity(int ccMoshtary);
        void showDarkhastKalaActivity(int ccMoshtary);
        void showAlertDuplicateRequestForCustomer(MoshtaryModel moshtaryModel);
        void closeLoading();
        void showToast(int resId, int messageType , int duration);
        void showErrorAlert(int resId, int messageType, boolean closeActivity);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequestCustomerListMVP.RequiredViewOps view);
        void checkFakeLocation();
        void checkDateOfGetProgram();
        void getCustomers();
        void searchCustomerName(String searchWord , ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh);
        void searchCustomerCode(String searchWord , ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh);
        void checkDuplicateRequestForCustomer(MoshtaryModel moshtaryModel);
        void checkSelectedCustomer(int ccMoshtary);
        void checkUpdateEtebarMoshtary(MoshtaryModel moshtaryModel);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onErrorUseFakeLocation();
        void onGetDateOfGetProgram(String date);
        void onErrorNeedGetProgram();
        void onSetRequestInfoShared(int ccMoshtary, boolean showBarkhordAvalie, boolean showMojodiGiri);
        void onFailedSetRequestInfoShared();
        void showAlertDuplicateRequestForCustomer(MoshtaryModel moshtaryModel);
        void onErrorSelectCustomer(int resId);
        void onWarningSelectCustomer(int resId);
        void onSuccessUpdateMandeMojodi();
        void onFailedUpdateMandeMojodi();
        void onGetCustomers(ArrayList<MoshtaryModel> moshtaryModels , ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh , boolean canUpdateCustomer);
        void onErrorAccessToLocation();
        void onSuccessUpdateMoshtaryEtebar();
        void onFailedUpdateMoshtaryEtebar();
        void onFailedUpdateForoshandehEtebar();
        void onConfigurationChanged(RequestCustomerListMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void checkFakeLocation();
        void getDateOfGetProgram();
        void getCustomers();
        void checkDuplicateRequestForCustomer(MoshtaryModel moshtaryModel);
        void checkSelectedCustomer(int ccMoshtary);
        void updateEtebarMoshtary(MoshtaryModel moshtaryModel);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
