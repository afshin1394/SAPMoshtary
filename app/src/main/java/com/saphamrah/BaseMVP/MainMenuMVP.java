package com.saphamrah.BaseMVP;

import android.content.Context;
import com.saphamrah.Model.SystemMenuModel;
import java.util.ArrayList;


public interface MainMenuMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void setMenuAdapter(ArrayList<SystemMenuModel> systemMenuModels);
        void openActivity(String activityName);
        void showAboutAlert(String currentVersion, String newestVersion, String lastStableVersion, String newFeatures);
        void showExitAlert();
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(MainMenuMVP.RequiredViewOps view);
        void getMenuItems(String parentsId);
        void checkSelectedMenuItem(SystemMenuModel selectedMenuItem);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetMenuItems(ArrayList<SystemMenuModel> systemMenuModels);
        void onGetAlertAboutData(String currentVersion, String newestVersion, String lastStableVersion, String newFeatures);
    }


    interface ModelOps
    {
        void getMenuItems(String parentsId);
        void getAlertAboutData();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
