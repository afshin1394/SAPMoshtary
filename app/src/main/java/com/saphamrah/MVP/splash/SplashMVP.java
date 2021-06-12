package com.saphamrah.MVP.splash;

import android.content.Context;

import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;

import java.util.ArrayList;

public interface SplashMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onErrorGPS();

        void setLblVersionName(String versionName , String versionType);
        void goneLblVersionName();
        void onGetInvalidPackages(String[] packageNames);
        void onGetServerIPs(ArrayList<ServerIpModel> arrayListServerIPs);
        void showAuthenticationProcess();
        void onSuccessServerVersion();

        void showBtnOffline();
        void onErrorDeviceLanguage();
        void showGooglePlayServicesUpdateDialog(int status);

        void forceUpdate(String downloadUrl);
        void forceUpdateTest(String downloadUrl);
        void newVersionReleased(String downloadUrl);
        void newVersionAvailable(String date);

		void showErrorAlert(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId);
        void copyClipBoard(String usingIMEI);
        void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId);
        void showResourceErrorTwoButton(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonOneResId, int buttonTwoResId);
        void showToast(int resId, String param , int messageType , int duration);

        void startLoadingDialog();
        void stopLoadingDialog();
        void clearInvalidIdentityCode();


    }
    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void checkIsRoot();
        void checkDeviceLanguage();

        void checkGPS();
        void checkInternetType();
        void checkWifiStatus();
        void checkGooglePlayServices();


        void selectIp(String ip);
        void checkServerTime();
        void checkAuthentication();
        void authenticateUser(String identityCode);
        void checkForoshandehMamorPakhsh();
        void checkIMEI();
        void checkAvailableEmail();
        void checkAppVersionName();
        void checkServerVersion();

        // if is test == 0
        void checkFakeLocation();
        void getInvalidPackages();

        void checkLastLog();
        void checkInsertLogToDB(String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetIsRoot(boolean isRoot);
        void onGetDeviceLanguage(boolean isEnglish);

        void onGetGPS(boolean enabled);
        void onGetInternetType(int networkType);
        void onGetWifiStatus(boolean wifiStatus);
        void onGetGooglePlayServices(int status);

        void onGetServerIPs(ArrayList<ServerIpModel> arrayListServerIPs , boolean first);
        void onGetServerIP(ServerIpModel serverIpModel);
        void onGetServerTime(boolean validDiffTime, String message);
        void onStartAuthenticationProcess();
        void onGetForoshandehAmoozeshi(boolean setIMEI , boolean errorIMEI);
        void onGetForoshandehMamorPakhsh(boolean serviceResult , boolean emptyIMEI);
        void onGetEmptyForoshandehMamorPakhsh(String usingIMEI);
        void onGetUserType(int userType);
        void onGetIMEI(String IMEI);
        void onGetAvailableEmail(boolean available);
        void onGetAppVersionName(String versionName);
        void onGetServerVersion(GetVersionResult result , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel , int noeMasouliat , ArrayList<ParameterChildModel> childParameterModelsDownloadUrls);

        // for is test == 0
        void onGetFakeLocation(boolean fakeLocation);
        void onGetInvalidPackages(String packageNames);

        void onGetLastLog(LogPPCModel logPPCModel);
        void onSendEmail(boolean result);
        void onError(String msg);
        void notFoundServerIP();
        void onNetworkError(boolean closeActivity);
        void checkAuthentication();
        void onGetInvalidIdentityCode(String message, String type);
        void startLoading();
        void finishLoading();

    }
    interface ModelOps
    {
        void getIsRoot();
        void getDeviceLanguage();
        void getGPS();
        void getInternetType();
        void getWifiStatus();
        void getGooglePlayServices();
        void getServerIP(boolean first);
        void selectIp(String ip);
        void getServerTime();
        void checkAuthentication();
        void authenticateUser(String identityCode);
        void getForoshandehAmoozeshi(ServerIpModel serverIpModel);
        void getForoshandehMamorPakhsh();
        void getUserType();
        void getIMEI();
        void getAvailableEmail();
        void getAppVersionName();
        void getServerVersion();

        // if is test == 0
        void getFakeLocation();
        void getInvalidPackages();

        void getLastLog();
        void sendEmail(String message, String logClass, String logActivity,String logFunctionParent, String logFunctionChild);
        void setLogToDB(String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }


}
