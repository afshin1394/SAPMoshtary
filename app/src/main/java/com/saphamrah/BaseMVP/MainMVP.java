package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.Model.OwghatModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface MainMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        //void onFailedGetOwghat();
        //void onShowOwghat(int drawableId , int stringId);
        void onCheckForoshandehMamorPakhsh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel , String deviceIMEI , int ccMasir);
        void showForceUpdateGallery();
        void showForceUpdateJayezehTakhfif();								 
        void hideMessageBox();
        void showMessageBox(int unreadCount);
        void showNotifForMessages(ArrayList<MessageBoxModel> messageBoxModels);
        void startGPSService(int minDistance , int timeInterval , int fastestTimeInterval , int maxAccurancy);
        void setMenuAdapter(ArrayList<SystemMenuModel> arrayListHeaders, HashMap<SystemMenuModel , List<SystemMenuModel>> hashMapMenuItems);
        void openActivity(String activityName);
        //void openFragment(String fragmentName);
        void onRemovedAddCustomerSharedData();
        void onSuccessCopyIMEI(String imei);
        void onGetProfileImage(byte[] profileImage);
        void forceUpdate(String downloadUrl);
        void forceUpdateTest(String downloadUrl);
        void newVersionReleased(String downloadUrl);
        void showAboutAlert(String currentVersion, String newestVersion, String lastStableVersion, String newFeatures);
        void showExitAlert();
		void showErrorAlert(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId);																												 
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showToast(int resId, int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(MainMVP.RequiredViewOps view);
        //void checkOwghat();
        void checkServerTime();
        void checkFakeLocation();
        void checkForGetParameter();
		void checkVersion();
        void checkShowMessageBox();
        void checkccMessagesForUpdateNotifStatus(int ccForoshandeh, int ccMamorPakhsh, String ccMessages);
        void checkSelectedMenuItem(SystemMenuModel selectedMenuItem);
        void checkForoshandehMamorPakhsh();
        void getGPSReceiverConfig();
        void createDrawerMenu(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void checkForCopyToClipboard(String tag , String value);
        void removeAddCustomerSharedData();
        void getImageProfile();
        void checkProfileImage(String profileImageCurrentPath);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        //void onGetOwghat(OwghatModel owghatModel);
        void onGetServerTime(boolean validDiffTime, String message);
        void onGetFakeLocation(boolean fakeLocation);
        void onGetServerVersion(GetVersionResult result , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel , int noeMasouliat , ArrayList<ParameterChildModel> childParameterModelsDownloadUrls);
        void onForceUpdateGallery();
        void onForceUpdateJayezehTakhfif();						
        void onGetAlertAboutData(String currentVersion, String newestVersion, String lastStableVersion, String newFeatures);
        void onGetForoshandehMamorPakhsh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel , String deviceIMEI , int ccMasir);
        void onGetGPSReceiverConfig(int minDistance , int timeInterval , int fastestTimeInterval , int maxAccurancy);
        void disableMessageBox();
        void enableMessageBox(int unreadCount);
        void showNotifForMessages(ArrayList<MessageBoxModel> messageBoxModels);
        void onSuccessGetDrawerMenuItems(ArrayList<SystemMenuModel> arrayListHeaders, HashMap<SystemMenuModel , List<SystemMenuModel>> hashMapMenuItems);
        void onFailureGetDrawerMenuItems(boolean errorNoeForoshandehMamorPakhsh , boolean emptyList);
        void onSuccessCopyToClipboard(String tag , String value);
        void onRemovedAddCustomerSharedData();
        void onGetProfileImage(byte[] profileImage);
        void onSavedProfileImage(byte[] profileImage);
        void onErrorSaveProfileImage();
        void onConfigurationChanged(MainMVP.RequiredViewOps view);
        void onFailed(int resId , int messageType , int duration);
        void onNetworkError(boolean closeActivity);
        void notFoundServerIP();

        void onNeedForceUpdateAzmayeshi(String urlAzmayeshVersion);

        void onNeedForceUpdate(String url);

    }


    interface ModelOps
    {
        //void getOwghat();
        void getServerTime();
        void getFakeLocation();
        void checkForGetParameter();
		void getServerVersion();							
        void checkEnableMessage();
        void updateMessageNotifyStatus(int ccForoshandeh, int ccMamorPakhsh, String ccMessages);
        void getAlertAboutData();
        void getForoshandehMamorPakhsh();
        void getGPSReceiverConfig();
        void getDrawerMenuItems(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel);
        void copyToClipboard(String tag , String value);
        void removeAddCustomerSharedData();
        void getImageProfile();
        void saveProfileImage(String profileImageCurrentPath);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();

    }


}
