package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.UIModel.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.UIModel.JayezehEntekhabiMojodiModel;

import java.util.ArrayList;

public interface SelectBonusMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void setBonus(ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels);
        void onEmptyGoodsHaveBonus();
        void openSpinnerSelectBonus();
        void onGetKalaForJayezeh(ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels , int noeJayezehTakhfif);
        void onCalculateMablaghJayezeh(float mablaghJayezeh  , float mandeh, int noeJayezehTakhfif);
        void onErrorInsert(int resId , String kalaName);
        void onSuccessInsert();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(SelectBonusMVP.RequiredViewOps view);
        void getBonus();
        void checkBeforeSelectBonus(int ccTakhfif);
        void getKalaForJayezeh(int ccJayezehTakhfif, int ccJayezehSatr, int noeJayezehTakhfif);
        void calculateMablaghJayezeh(ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels , String mablaghTakhfif, int noeJayezehTakhfif);
        void checkInsert(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, String mablaghTakhfif, String mablaghJayezeh, String mandeh, String maxTedadJayeze);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(SelectBonusMVP.RequiredViewOps view);
        void onError(int resId);
        void onGetBonus(ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels);
        void onGetKalaForJayezeh(ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, int noeJayezehTakhfif);
        void onSuccessInsert();
        void onFailedInsert();
    }


    interface ModelOps
    {
        void getBonus();
        void getKalaForJayezeh(int ccJayezehTakhfif, int ccJayezehSatr, int noeJayezehTakhfif);
        void insert(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, double mablaghTakhfif, double mablaghJayezeh, double mandeh, int maxTedadJayeze, boolean insertTakhfifNaghdi);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
