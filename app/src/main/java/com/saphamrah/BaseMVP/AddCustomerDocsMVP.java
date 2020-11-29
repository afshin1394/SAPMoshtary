package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MoshtaryPhotoPPCModel;


public interface AddCustomerDocsMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetAddCustomerInfoModel(AddCustomerInfoModel addCustomerInfoModel);
        void onGetImageStatus(boolean savedNationalCard, boolean savedJavazeKasb, boolean savedDasteCheck, boolean isOld);
        void onSuccessSavedNationalCardImage();
        void onSuccessSavedJavazehKasbImage();
        void onSuccessSavedDastehCheckImage();
        void onGetNationalCardImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel);
        void onGetJavazeKasbImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel);
        void onGetDasteCheckImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel);
        void onSuccessDeletedNationalCardImage();
        void onSuccessDeletedJavazeKasbImage();
        void onSuccessDeletedDasteCheckImage();
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);
    }

    interface PresenterOps
    {
        void onConfigurationChanged(AddCustomerDocsMVP.RequiredViewOps view);
        void getAddCustomerInfoModel();
        void getImageStatus(int ccMoshtary);
        void checkNationalCardImage(int ccMoshtary , byte[] nationalcard);
        void checkJavazehKasbImage(int ccMoshtary , byte[] javazehKasb);
        void checkDastehCheckImage(int ccMoshtary , byte[] dastehCheck);
        void getNationalCardImage(int ccMoshtary);
        void getJavazeKasbImage(int ccMoshtary);
        void getDasteCheckImage(int ccMoshtary);
        void deleteNationalCardImage(int ccMoshtaryPhoto);
        void deleteJavazeKasbImage(int ccMoshtaryPhoto);
        void deleteDasteCheckImage(int ccMoshtaryPhoto);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }

    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetAddCustomerInfoModel(AddCustomerInfoModel addCustomerInfoModel);
        void onGetImageStatus(boolean savedNationalCard, boolean savedJavazeKasb, boolean savedDasteCheck, boolean isOld);
        void onSuccessSavedNationalCardImage(/*byte[] nationalCardImage*/);
        void onSuccessSavedJavazehKasbImage(/*byte[] javazehKasb*/);
        void onSuccessSavedDastehCheckImage(/*byte[] dastehCheck*/);
        void onFailedSaveImage(int errorMessageResId);
        void onGetNationalCardImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel);
        void onGetJavazeKasbImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel);
        void onGetDasteCheckImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel);
        void onSuccessDeletedNationalCardImage();
        void onSuccessDeletedJavazeKasbImage();
        void onSuccessDeletedDasteCheckImage();
        void onFailedDeleteImage();
        void onConfigurationChanged(AddCustomerDocsMVP.RequiredViewOps view);
        void onNetworkError(boolean closeActivity);
    }

    interface ModelOps
    {
        void getAddCustomerInfoModel();
        void getImageStatus(int ccMoshtary);
        void saveNationalCardImage(int ccMoshtary , byte[] nationalcard);
        void saveJavazehKasbImage(int ccMoshtary , byte[] javazehKasb);
        void saveDastehCheckImage(int ccMoshtary , byte[] dastehCheck);
        void getNationalCardImage(int ccMoshtary);
        void getJavazeKasbImage(int ccMoshtary);
        void getDasteCheckImage(int ccMoshtary);
        void deleteNationalCardImage(int ccMoshtaryPhoto);
        void deleteJavazeKasbImage(int ccMoshtaryPhoto);
        void deleteDasteCheckImage(int ccMoshtaryPhoto);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
