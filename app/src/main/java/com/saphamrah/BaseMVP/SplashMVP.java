package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;

import java.util.ArrayList;

public interface SplashMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void setLblVersionName(String versionName , String versionType);
        void goneLblVersionName();
        void showBtnOffline();
        void onCorrectGPS();
        void onErrorGPS();
        void onCheckWifiStatus(boolean wifiStatus);
        void onCheckInternetType();
        void onErrorDeviceLanguage();
        void showGooglePlayServicesUpdateDialog(int status);
        void onSuccessServerVersion();
        void forceUpdate(String downloadUrl);
        void forceUpdateTest(String downloadUrl);
        void newVersionReleased(String downloadUrl);
        void newVersionAvailable(String date);
        void onGetInvalidPackages(String[] packageNames);
		void showErrorAlert(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId);																												 
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showResourceErrorTwoButton(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonOneResId, int buttonTwoResId);
        void showToast(int resId, String param , int messageType , int duration);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(SplashMVP.RequiredViewOps view);
        void checkAvailableEmail();
        void checkLastLog();
        void checkAppVersionName();
        void checkGPS();
        void checkWifiStatus();
        void checkInternetType();
        void checkServerIp();
        void checkForoshandehAmoozeshi();
        void checkForoshandehMamorPakhsh();
        void checkSystemConfig();
        void checkIsRoot();
        void checkIMEI();
        void checkDeviceLanguage();
        void checkFakeLocation();
        void checkGooglePlayServices();
        void checkServerTime();
        void checkServerVersion();
        void getInvalidPackages();
        //void downloadNewVersion();
        void checkInsertLogToDB(String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetAvailableEmail(boolean available);
        void onGetLastLog(LogPPCModel logPPCModel);
        void onSendEmail(boolean result);
        void onGetAppVersionName(String versionName);
        void onGetGPS(boolean enabled);
        void onGetWifiStatus(boolean wifiStatus);
        void onGetInternetType(int networkType);
        void onGetServerIP(ServerIpModel serverIpModel);
        void onGetForoshandehAmoozeshi(boolean setIMEI , boolean errorIMEI);
        void onGetForoshandehMamorPakhsh(boolean serviceResult , boolean emptyIMEI);
        void onGetEmptyForoshandehMamorPakhsh();
        void onGetSystemConfig(boolean result);
        void onGetUserType(int userType);
        void onGetIsRoot(boolean isRoot);
        void onGetIMEI(String IMEI);
        void onGetDeviceLanguage(boolean isEnglish);
        void onGetFakeLocation(boolean fakeLocation);
        void onGetGooglePlayServices(int status);
        void onGetServerTime(boolean validDiffTime, String message);
        //void onGetServerVersion(String ccAfrad , String[] ccAfrads , String lastVersion , String stableVersion , String dateNewVersion , ArrayList<ParameterChildModel> childParameterModelsDownloadUrls);
        void onGetServerVersion(GetVersionResult result , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel , int noeMasouliat , ArrayList<ParameterChildModel> childParameterModelsDownloadUrls);
        void onGetInvalidPackages(String packageNames);
        void onError(String msg);
        void notFoundServerIP();
        //void onDownloadProgress(int progress);
        void onNetworkError(boolean closeActivity);
    }


    interface ModelOps
    {
        void getAvailableEmail();
        void getLastLog();
        void sendEmail(String message, String logClass, String logActivity,String logFunctionParent, String logFunctionChild);
        void getAppVersionName();
        void getGPS();
        void getWifiStatus();
        void getInternetType();
        void getServerIP();
        void getForoshandehAmoozeshi();
        void getForoshandehMamorPakhsh();
        void getSystemConfig();
        void getUserType();
        void getIsRoot();
        void getIMEI();
        void getDeviceLanguage();
        void getFakeLocation();
        void getGooglePlayServices();
        void getServerTime();
        void getServerVersion();
        //void downloadNewVersion();
        void getInvalidPackages();
        void setLogToDB(String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
