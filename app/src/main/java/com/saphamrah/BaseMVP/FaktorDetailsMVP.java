package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;

import java.util.ArrayList;

public interface FaktorDetailsMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetFaktorDetails(String customerCode, String customerName, float mablaghArzeshAfzoode, double mablaghKol, double mablaghKhalesFaktor, String noeVosol, int modatVosol, String address);
        void onGetKala(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels);
        void onGetTakhfif(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels);
        void onGetKalaElamMarjoee(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels);
        void onGetJayezeh(ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels);
        void onGetEmzaDetail(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel);
        void onSuccessUpdateDarkhastFaktorEmza();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(FaktorDetailsMVP.RequiredViewOps view);
        void getFaktorDetails(long ccDarkhastFaktor, int ccMoshtary , boolean getDetailOfNewFaktor); //if getDetailOfNewFaktor == true => from darkhastFaktor or ersalDarkhast and get list of kala and takhfif and marjoee and jayezeh , else from treasuryList and only get kala
        void checkUpdateDarkhastFaktorEmza(byte[] imageBytes , long ccDarkhastFaktor);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(FaktorDetailsMVP.RequiredViewOps view);
        void onGetFaktorDetails(String customerCode, String customerName, float mablaghArzeshAfzoode, double mablaghKol, double mablaghKhalesFaktor, String noeVosol, int modatVosol, String address);
        void onGetKala(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels);
        void onGetTakhfif(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels);
        void onGetKalaElamMarjoee(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels);
        void onGetJayezeh(ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels);
        void onGetEmzaDetail(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel);
        void onErrorUpdateDarkhastFaktorEmza();
        void onSuccessUpdateDarkhastFaktorEmza();
    }


    interface ModelOps
    {
        void getFaktorDetails(long ccDarkhastFaktor, int ccMoshtary);
        void getFaktorDetailsForTreasuryList(long ccDarkhastFaktor, int ccMoshtary);
        void updateDarkhastFaktorEmza(byte[] imageBytes , long ccDarkhastFaktor);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
