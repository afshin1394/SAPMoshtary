package com.saphamrah.BaseMVP.marjoee;

import android.content.Context;

import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;

import java.util.ArrayList;

public interface MarjoeekoliMVP
{

    interface RequiredViewOps
    {
        void showToast(int resId, int messageType, int duration);
        void onGetMarjoee(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels);
        void onGetElatMarjoeeKol (ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels, ArrayList<String> elatMarjoeeKalaTitles);
        void onCheckTaeidSabtMarjoee(boolean isHaveImage , boolean isValidData);

    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void getMarjoee(long ccDarkhastFaktor);
        void getElatMarjoeeKol();
        void checkTaeidSabtMarjoee(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels , int position , int ccDarkhastFaktor, int ccMoshtary);
        void checkMarjoeeForInsert(byte[] imageMarjoee);
//        void getImageProfile();
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetMarjoee(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels);
        void onGetElatMarjoeeKol  (ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels, ArrayList<String> elatMarjoeeKalaTitles);
        void onCheckTaeidSabtMarjoee(boolean isHaveImage , boolean isVosol);
        void onErrorSaveProfileImage();

    }


    interface ModelOps
    {
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void getMarjoee(long ccDarkhastFaktor);
        void getElatMarjoeeKol();
        void checkTaeidSabtMarjoee(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels , int position , int ccDarkhastFaktor, int ccMoshtary);
        void insertMarjoee(byte[] imageMarjoee);
//        void getImageProfile();
    }

}
