package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.UIModel.CustomerVisitModel;

import java.util.List;

public interface PorseshnameAdamMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void changeTitleToPorseshname();
        void changeTitleToAdam();
        void changeFabToAdam();
        void changeFabToPorseshname();
        void showNotFoundData();
        void showAllPorseshname(List<PorseshnamehModel> porseshnamehModels);
        void showAllAdamFaal(List<CustomerVisitModel> customerVisitModels);
        void showAlertSuccessDelete();
        void showErrorMessage(String message);
        void showSuccessSendData();
        void showLoading();
        void closeLoading();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(PorseshnameAdamMVP.RequiredViewOps view);
        void getAllPorseshname();
        void getAllAdamFaal();
        void deletePorseshname(int ccPorseshnameh);
        void deleteAdamFaal(int ccVisitMoshtary);
        void sendPorseshname(int ccPorseshnameh);
        void sendAdamFaal(int ccVisitMoshtary);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(PorseshnameAdamMVP.RequiredViewOps view);
        void onGetPorseshname(List<PorseshnamehModel> porseshnamehModels);
        void onGetallAdamFaal(List<CustomerVisitModel> customerVisitModels);
        void onSuccessDelete();
        void onErrorSendPorseshnameToServer(String message);
        void onErrorSendAdamToServer(String message);
        void onSuccessSend();
    }


    interface ModelOps
    {
        void getAllPorseshname();
        void getAllAdamFaal();
        void deletePorseshname(int ccPorseshnameh);
        void deleteAdamFaal(int ccVisitMoshtary);
        void sendPorseshname(int ccPorseshnameh);
        void sendAdamFaal(int ccVisitMoshtary);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
