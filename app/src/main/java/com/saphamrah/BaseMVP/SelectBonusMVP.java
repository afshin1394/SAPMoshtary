package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.UIModel.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.UIModel.JayezehEntekhabiMojodiModel;

import java.util.ArrayList;
import java.util.List;

public interface SelectBonusMVP
{


    interface RequiredViewOps
    {
        Context getAppContext();
        void setBonus(ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels);
        void onEmptyGoodsHaveBonus();
        void openSpinnerSelectBonus();
        void onGetKalaForJayezeh(ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi, int noeJayezehTakhfif);
        void onCalculateMablaghJayezeh(float mablaghJayezeh  , float mandeh, int noeJayezehTakhfif);
        void onErrorInsert(int resId , String kalaName);
        void onSuccessInsert();
        void showToast(int resId, int messageType , int duration);
        void toastTest(int SelectedCount , int Max_MojodyByShomarehBach , int Max_Mojody , int TedadSefarshDarkhast);

        void showTaghirMandehDialog(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, String mablaghTakhfif, String mablaghJayezeh, String mandeh, String maxTedadJayeze, ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi);
        void onFailedInsert();

//        void closeArzeshAfzoodehDialog();
//
//        void onInsertJayezehNaghdyArzeshAfzoodeh(float fltMandeh);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(SelectBonusMVP.RequiredViewOps view);
        void getBonus();
        void checkBeforeSelectBonus(int ccTakhfif);
        void getKalaForJayezeh(int ccJayezehTakhfif, int ccJayezehSatr, int noeJayezehTakhfif);
        void calculateMablaghJayezeh(ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels , String mablaghTakhfif, int noeJayezehTakhfif);
        void checkInsert(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, String mablaghTakhfif, String mablaghJayezeh, String mandeh, String maxTedadJayeze, ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi,String modifiedDialogMondeh);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);

//        void checkInsertMandehArzeshAfzoodeh(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, String mablaghTakhfif, String mablaghJayezeh, String mandeh, String maxTedadJayeze, ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi);

        void checkArzeshAfzoodeh(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, String mablaghTakhfif, String mablaghJayezeh, String mandeh, String maxTedadJayeze, ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(SelectBonusMVP.RequiredViewOps view);
        void onError(int resId);
        void onGetBonus(ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels);
        void onGetKalaForJayezeh(ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi, int noeJayezehTakhfif);
        void onSuccessInsert();
        void onFailedInsert();

        void onSuccess(int resId);
        void onErrorCalculateDiscount(int resId);
//        void onInsertJayezehNaghdyArzeshAfzoodeh(float fltMandeh);
//
//        void onSuccessUpdateLockArzeshAfzoodeh();
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
