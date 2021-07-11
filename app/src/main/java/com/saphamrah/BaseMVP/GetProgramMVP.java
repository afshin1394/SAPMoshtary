package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.ForoshandehMamorPakhshModel;

import java.util.ArrayList;
import java.util.List;

public interface GetProgramMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void setAdapter(ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels);
        void onGetNoeMasouliat(int noeMasouliat);
        void updateStatusOfSuccessfulItem(int itemIndex);
        void updateStatusOfFailedItem(int getProgramType , int itemIndex , String error);
        void showCompletedGetProgramSuccessfully();
        void showCompletedUpdateKalaModatVosolSuccessfully();
        void showCompletedUpdateJayezehTakhfifSuccessfully();
        void showCompletedUpdateCustomerSuccessfully();
        void showCompletedUpdateParametersSuccessfully();
        void showCompletedUpdateEtebarForoshandehSuccessfully();
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showServerMessage(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);

        void onGetProgramType(int service);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(GetProgramMVP.RequiredViewOps view);
        void getAllForoshandehMamorPakhsh();
        void checkGetProgram(String date , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void checkUpdateForoshandeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void checkUpdateKalaModatVosol(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void checkUpdateJayezehTakhfif(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void checkUpdateCustomers(String date , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void checkUpdateParameter(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void checkUpdateEtebarForoshandeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);

        void getProgramServiceType();
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetAllForoshandehMamorPakhsh(ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels);
        void onGetNoeMasouliat(int noeMasouliat);
        void onSuccessfullyGetNewProgramItem(int getProgramItemCount , int itemIndex);
        void onFailedGetProgram(int itemIndex , String error);
        void onSuccessUpdateForoshandeh();
        void onFailedUpdateForoshandeh(String errorMessage);
        void onSuccessUpdateKalaModatVosolItem(int getProgramItemCount , int itemIndex);
        void onFailedUpdateKalaModatVosolItem(int itemIndex , String errorMessage);
        void onSuccessUpdateJayezehTakhfifItem(int getProgramItemCount , int itemIndex);
        void onFailedUpdateJayezehTakhfif(int itemIndex , String errorMessage);
        void onSuccessUpdateCustomers(int getProgramItemCount , int itemIndex);
        void onFailedUpdateCustomers(int itemIndex , String errorMessage);
        void onSuccessUpdateParameters(int getProgramItemCount , int itemIndex);
        void onFailedUpdateParameters(int itemIndex , String errorMessage);
        void onSuccessUpdateEtebarForoshandeh(int getProgramItemCount , int itemIndex);
        void onFailedUpdateEtebarForoshandeh(int itemIndex , String errorMessage);
        void onConfigurationChanged(GetProgramMVP.RequiredViewOps view);
        void onNetworkError(boolean closeActivity);
        void onGetProgramServiceType(int service);
    }


    interface ModelOps
    {
        void getAllForoshandehMamorPakhsh();
        void getProgram(int getProgramType , String date , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void setProgramDateToShared();
        void updateForoshandeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void updateKalaModatVosol(int getProgramType , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void updateJayezehTakhfif(int getProgramType , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void updateCustomers(int getProgramType , String date , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void updateParameter(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void updateEtebarForoshandeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();

        void clearRam();
        void releaseResources();

        void getProgramServiceType();
    }

}
