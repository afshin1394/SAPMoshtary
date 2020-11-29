package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.ElamMarjoeeSatrPPCModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.ListKalaForMarjoeeModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;

import java.util.ArrayList;

public interface MarjoeeKalaMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetForoshandehMamorPakhshInfo(int noeMasouliat , int noeSabtMarjoee);
        void onUpdateListKalaForMarjoee(ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels);
        void onGetListElatMarjoeeKala(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels);
        void onGetKalaMarjoee(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels);
        void onErrorAddToMarjoee(int errorResId);
        void onSuccessInsertMarjoee();
        void onSuccessRemoveItem(int position);
        void onSuccessInsertDariaftPardakht();
        void showAlert(int resIdMessage, int messageType, boolean closeActivity);
        void showToast(int resId, int messageType , int duration);
        void showToast(String message, int messageType , int duration);
        void showAlertLoading();
        void closeAlertLoading();

    }


    interface PresenterOps
    {
        void onConfigurationChanged(MarjoeeKalaMVP.RequiredViewOps view);
        void checkFaktorDetails(long ccDarkhastFaktor , int ccMoshtary);
        void getForoshandehMamorPakhshInfo();
        void updateListKalaForMarjoee(long ccDarkhastFaktor);
        void getListElatMarjoee();
        void getKalaMarjoee(long ccDarkhastFaktor);
        void checkKalaForAddToMarjoee(ListKalaForMarjoeeModel selectedKala,ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels , ElamMarjoeeSatrPPCModel elamMarjoeeSatrPPCModel , int ccMoshtary , String count);
        void checkRemoveKalaFromMarjoee(KalaElamMarjoeeModel kalaElamMarjoeeModel , int position);
        void checkUpdateCountOfMarjoee(long ccDarkhastFaktor, int ccElamMarjoeeSatr, int oldCount, int newCount);
        void insertDariaftPardakht(long ccDarkhastFaktor , int ccMoshtary); // when close activity, call this method for insert dariaftPardakht
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(MarjoeeKalaMVP.RequiredViewOps view);
        void onGetForoshandehMamorPakhshInfo(int noeMasouliat , int noeSabtMarjoee);
        void onErrorUpdateListKalaForMarjoee(String message);
        void onSuccessUpdateListKalaForMarjoee(ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels);
        void onGetListElatMarjoeeKala(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels);
        void onGetKalaMarjoee(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels);
        void onFailedInsertMarjoee();
        void onSuccessInsertMarjoee();
        void onSuccessRemoveItem(int position);
        void onFailedRemoveItem();
        void onUpdateCount(boolean status);
        void onSuccessInsertDariaftPardakht();
        void onFailedInsertDariaftPardakht();
    }


    interface ModelOps
    {
        void getForoshandehMamorPakhshInfo();
        void updateListKalaForMarjoee(long ccDarkhastFaktor);
        void getListElatMarjoee();
        void getKalaMarjoee(long ccDarkhastFaktor);
        void insertKalaToMarjoee(ElamMarjoeeSatrPPCModel elamMarjoeeSatrPPCModel , int ccMoshtary);
        void removeKalaFromMarjoee(KalaElamMarjoeeModel kalaElamMarjoeeModel , int position);
        void updateCountOfMarjoee(long ccDarkhastFaktor, int ccElamMarjoeeSatr, int newCount);
        void insertDariaftPardakht(long ccDarkhastFaktor , int ccMoshtary);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
