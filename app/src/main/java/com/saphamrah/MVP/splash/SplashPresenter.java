package com.saphamrah.MVP.splash;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.NetworkUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SplashPresenter implements SplashMVP.PresenterOps, SplashMVP.RequiredPresenterOps {

    private WeakReference<SplashMVP.RequiredViewOps> mView;
    private SplashMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public SplashPresenter(SplashMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new SplashModel(this);
    }

    @Override
    public void onConfigurationChanged(SplashMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void checkCountClearCache() {
        mModel.checkCountClearCache();
    }

    @Override
    public void clearData(String packageName) {
        if (packageName == null || packageName.trim().equals(""))
        {
            mView.get().showToast(R.string.errorGetPackageName,"", Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            try
            {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear " + packageName);
                mModel.successClearDate();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                checkInsertLogToDB( e.toString(), "SettingPresenter", "", "clearData", "");
            }

        }
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void checkIsRoot()
    {
        mModel.getIsRoot();
    }

    @Override
    public void checkDeviceLanguage()
    {
        mModel.getDeviceLanguage();
    }

    @Override
    public void checkFakeLocation() {
        mModel.getFakeLocation();
    }

    @Override
    public void checkGPS() {
        mModel.getGPS();
    }

    @Override
    public void checkInternetType() {
        mModel.getInternetType();
    }

    @Override
    public void checkWifiStatus() {
        mModel.getWifiStatus();
    }

    @Override
    public void checkGooglePlayServices() {
        mModel.getGooglePlayServices();
    }

    @Override
    public void getInvalidPackages()
    {
        mModel.getInvalidPackages();
    }



    @Override
    public void checkServerTime() {
        mModel.getServerTime();
    }

    @Override
    public void checkForoshandehMamorPakhsh()
    {
        mModel.getForoshandehMamorPakhsh();
    }

    @Override
    public void checkIMEI() {
        mModel.getIMEI();
    }

    @Override
    public void checkAvailableEmail()
    {
        mModel.getAvailableEmail();
    }

    @Override
    public void checkAppVersionName() {
        mModel.getAppVersionName();
    }

    @Override
    public void checkServerVersion() {
        mModel.getServerVersion();
    }


    @Override
    public void checkLastLog()
    {
        mModel.getLastLog();
    }








    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public void onGetIsRoot(boolean isRoot)
    {
        if (isRoot)
        {
            mView.get().showResourceError(true, R.string.errorRootTitle, R.string.errorRoot, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            checkDeviceLanguage();
        }
    }

    @Override
    public void onGetDeviceLanguage(boolean isEnglish)
    {
        if (isEnglish)
        {
            checkGPS();
        }
        else
        {
            mView.get().onErrorDeviceLanguage();
        }
    }

    @Override
    public void onGetFakeLocation(boolean fakeLocation)
    {
        if (fakeLocation)
        {
            mView.get().showResourceError(true, R.string.errorFakeLocationTitle, R.string.errorFakeLocation, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            getInvalidPackages();
        }
    }

    @Override
    public void onGetGPS(boolean enabled)
    {
        if (enabled)
        {
           checkInternetType();
        }
        else
        {
            mView.get().onErrorGPS();
        }
    }

    @Override
    public void onGetInternetType(int networkType)
    {
        if (networkType == NetworkUtils.WIFI || networkType == NetworkUtils.NO_CONNECTION)
        {
            mView.get().showToast(R.string.enableCellular, "", Constants.INFO_MESSAGE(), R.string.apply);
        }

        checkWifiStatus();

    }

    @Override
    public void onGetWifiStatus(boolean wifiStatus)
    {
        if (!wifiStatus)
        {
            mView.get().showToast(R.string.enableWifiForBetterLocation , "" , Constants.INFO_MESSAGE() , Constants.DURATION_SHORT());
        }
        checkGooglePlayServices();
    }

    @Override
    public void onGetGooglePlayServices(int status)
    {
        if(status != ConnectionResult.SUCCESS)
        {
            mView.get().showResourceError(true, R.string.errorGooglePlayServicesTitle, R.string.errorGooglePlayServices, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            mModel.getServerIP(true);
        }
    }

    @Override
    public void onGetInvalidPackages(String packageNames)
    {
        //possible packageNames == "," so check for length bigger than 1
        if (packageNames.length() > 1)
        {
            String[] invalidPackageNames;
            if (packageNames.indexOf(',') > 0)
            {
                invalidPackageNames = packageNames.split(",");
            }
            else
            {
                invalidPackageNames = new String[]{packageNames};
            }
            mView.get().onGetInvalidPackages(invalidPackageNames);
        }
        else
        {
            mView.get().onGetInvalidPackages(new String[]{});
        }
    }

    @Override
    public void showToast(int resId, String param, int messageType, int duration) {
        mView.get().showToast(resId,param,messageType,duration);
    }

    @Override
    public void onGetServerIPs(ArrayList<ServerIpModel> arrayListServerIPs , boolean first)
    {

        if (!first){
            mView.get().showToast(R.string.failConnectServer , "" , Constants.FAILED_MESSAGE() , Constants.DURATION_SHORT());
        }
        mView.get().onGetServerIPs(arrayListServerIPs);

    }

    @Override
    public void selectIp(String ip) {
        mModel.selectIp(ip);
    }

    @Override
    public void onGetServerIP(ServerIpModel serverIpModel)
    {
        if (serverIpModel != null && serverIpModel.getServerIp() != null && serverIpModel.getPort() != null)
        {
            Log.d("serverIp" , "server ip not null"+serverIpModel.getServerIp()+"port: "+serverIpModel.getPort());
            checkServerTime();
        }
        else
        {
            Log.d("serverip" , "server ip is null");
            mView.get().showBtnOffline();// نمایش دکمه اتصال آفلاین
            mView.get().showResourceErrorTwoButton(false, R.string.errorFindServerIPTitle, R.string.errorFindServerIP, Constants.FAILED_MESSAGE(), R.string.apply, R.string.send);
        }
    }

    @Override
    public void onGetServerTime(boolean validDiffTime, String message)
    {
        if (validDiffTime)
        {
            checkAuthentication();
        }
        else
        {
            mView.get().showBtnOffline();// نمایش دکمه اتصال آفلاین
            mView.get().showErrorAlert(false, R.string.errorLocalDateTimeTitle, message, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void checkAuthentication() {
        mModel.checkAuthentication();
    }

    @Override
    public void onStartAuthenticationProcess() {
        mView.get().showAuthenticationProcess();
    }

    @Override
    public void authenticateUser(String identityCode) {

        if (identityCode.length()<10){
            mView.get().showToast(R.string.invalidIdentityCodeLength, "", Constants.FAILED_MESSAGE(), Constants.DURATION_SHORT());
        }else{
            mModel.authenticateUser(identityCode);
        }

    }

    @Override
    public void onGetForoshandehAmoozeshi(boolean setIMEI , boolean errorIMEI)
    {
        if (!setIMEI && errorIMEI)
        {
            mView.get().showResourceError(true, R.string.errorInvalidIMEI, R.string.errorIMEI , Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else if (!setIMEI)
        {
            mView.get().showResourceError(true, R.string.errorGetData, R.string.notFoundData , Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            checkForoshandehMamorPakhsh();
        }
    }

    @Override
    public void onGetForoshandehMamorPakhsh(boolean serviceResult , boolean emptyIMEI)
    {
        if (emptyIMEI)
        {
            mView.get().showResourceError(true, R.string.errorInvalidIMEI, R.string.errorIMEI , Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else if (serviceResult)
        {
            mModel.getUserType();
        }
    }

    @Override
    public void onGetEmptyForoshandehMamorPakhsh(String usingIMEI)
    {
        //mView.get().showResourceError(true, R.string.unregisteredDeviceTitle, R.string.unregisteredDevice, Constants.FAILED_MESSAGE(), R.string.apply);
        mView.get().showErrorAlert(true, R.string.unregisteredDeviceTitle, getAppContext().getResources().getString(R.string.unregisteredDevice) +"\n"+ "َشناسه دستگاه : " + usingIMEI, Constants.FAILED_MESSAGE(), R.string.apply);
        mView.get().copyClipBoard(usingIMEI);
    }

    @Override
    public void onGetUserType(int userType)
    {
        if (userType == 0)
        {
            checkFakeLocation();
        }
        else if (userType == 1)
        {
            checkIMEI();
        }
    }

    @Override
    public void onGetIMEI(String IMEI)
    {
        if (IMEI == null || IMEI.trim().length() == 0 /*|| IMEI.trim().replace("0" , "").length() == 0*/)
        {
            mView.get().showResourceError(true, R.string.errorInvalidIMEI, R.string.errorIMEI , Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            checkAvailableEmail();
        }
    }

    @Override
    public void onGetAvailableEmail(boolean available)
    {
        if (available)
        {
            checkAppVersionName();
        }
        else
        {
            mView.get().showResourceError(true, R.string.error, R.string.errorGetData, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void onGetAppVersionName(String versionName)
    {
        if (versionName.equals("-1"))
        {
            mView.get().goneLblVersionName();
        }
        else
        {
            if (Constants.CURRENT_VERSION_TYPE() == 0)
            {
                mView.get().setLblVersionName(versionName , "");
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 1)
            {
                mView.get().setLblVersionName(versionName , getAppContext().getResources().getString(R.string.Learning));
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 2)
            {
                mView.get().setLblVersionName(versionName , getAppContext().getResources().getString(R.string.Test));
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 3)
            {
                mView.get().setLblVersionName(versionName , getAppContext().getResources().getString(R.string.demo));
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 4)
            {
                mView.get().setLblVersionName(versionName , "");
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 5)
            {
                mView.get().setLblVersionName(versionName , getAppContext().getResources().getString(R.string.Learning));
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 6)
            {
                mView.get().setLblVersionName(versionName , getAppContext().getResources().getString(R.string.demo));
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 7)
            {
                mView.get().setLblVersionName(versionName , getAppContext().getResources().getString(R.string.Test));
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 8)
            {
                mView.get().setLblVersionName(versionName , getAppContext().getResources().getString(R.string.gRPC));
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 9)
            {
                mView.get().setLblVersionName(versionName , "");
            }
        }
        checkServerVersion();
    }

    @Override
    public void onGetServerVersion(GetVersionResult result , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel , int noeMasouliat , ArrayList<ParameterChildModel> childParameterModelsDownloadUrls)
    {
        boolean isTestUser = false;
        int appVersion = currentVersion();
        int intStableVersion = Integer.parseInt(result.getStableVersion().replace("." , ""));
        int intLastVersion = Integer.parseInt(result.getNewVersion().replace("." , ""));
        int intAzmayeshiVersion = Integer.parseInt(result.getAzmayeshVersion().replace("." , ""));

        if (result.getCcMarkazAnbar().length() > 0 || result.getCcMarkazSazmanSakhtarForosh().length() > 0)
        {
            String ccMarkazSazmanSakhtarForoshAnbar = "";
            if (noeMasouliat == 4)
            {
                ccMarkazSazmanSakhtarForoshAnbar = String.valueOf(foroshandehMamorPakhshModel.getCcMarkazAnbar());
            }
            else
            {
                ccMarkazSazmanSakhtarForoshAnbar = String.valueOf(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
            }

            if (result.getCcMarkazSazmanSakhtarForosh().length() > 0)
            {
                String[] ccMarkazSazmanSakhtarForoshs = result.getCcMarkazSazmanSakhtarForosh().split(",");
                for (String ccMarkazSazmanSakhtarForosh : ccMarkazSazmanSakhtarForoshs)
                {
                    if (ccMarkazSazmanSakhtarForosh.equalsIgnoreCase(ccMarkazSazmanSakhtarForoshAnbar))
                    {
                        checkVersionAzmayeshi(appVersion , intAzmayeshiVersion , result.getURLAzmayeshVersion());
                        isTestUser = true;
                        break;
                    }
                }
            }

            if (result.getCcMarkazAnbar().length() > 0)
            {
                String[] ccMarkazAnbars = result.getCcMarkazAnbar().split(",");
                for (String ccMarkazAnbar : ccMarkazAnbars)
                {
                    if (ccMarkazAnbar.equalsIgnoreCase(ccMarkazSazmanSakhtarForoshAnbar))
                    {
                        checkVersionAzmayeshi(appVersion , intAzmayeshiVersion , result.getURLAzmayeshVersion());
                        isTestUser = true;
                        break;
                    }
                }
            }
        }

        if (result.getAfrad().length() > 0)
        {
            String[] ccAfrads = result.getAfrad().split(",");
            String ccAfrad = String.valueOf(foroshandehMamorPakhshModel.getCcAfrad());
            for (int i=0 ; i < ccAfrads.length ; i++)
            {
                if (ccAfrads[i].equals(ccAfrad))
                {
                    checkVersionAzmayeshi(appVersion , intAzmayeshiVersion , result.getURLAzmayeshVersion());
                    isTestUser = true;
                    break;
                }
            }
        }


        if (!isTestUser)
        {
            int today = new PubFunc().new DateUtils().todayDate(getAppContext());
            int serverDate = Integer.parseInt(result.getDateNewVersion().replace("/" , ""));
            if (appVersion == intLastVersion)
            {
                if (today < serverDate)
                {
                    mView.get().newVersionAvailable(result.getDateNewVersion());
                }
                else
                {
                    Log.d("openMain" , "from onGetServerVersion 2");
                    mView.get().onSuccessServerVersion();
                }
            }
            else if (appVersion >= intStableVersion && appVersion < intLastVersion)
            {
                String downloadUrl = result.getURL();
//                for (ParameterChildModel childParameterModel : childParameterModelsDownloadUrls)
//                {
//                    if (childParameterModel.getCcParameterChild() == Constants.CC_CHILD_DOWNLOAD_URL_ASLI())
//                    {
//                        downloadUrl = childParameterModel.getValue();
//                        break;
//                    }
//                }
                mView.get().newVersionReleased(downloadUrl);
            }
            else if (appVersion < intStableVersion)
            {
                String downloadUrl = result.getURL();
//                for (ParameterChildModel childParameterModel : childParameterModelsDownloadUrls)
//                {
//                    if (childParameterModel.getCcParameterChild() == Constants.CC_CHILD_DOWNLOAD_URL_ASLI())
//                    {
//                        downloadUrl = childParameterModel.getValue();
//                        break;
//                    }
//                }
                mView.get().forceUpdate(downloadUrl);
            }
            else if (today < serverDate)
            {
                mView.get().newVersionAvailable(result.getDateNewVersion());
            }
            else
            {
                String downloadUrl = result.getURL();
//                for (ParameterChildModel childParameterModel : childParameterModelsDownloadUrls)
//                {
//                    if (childParameterModel.getCcParameterChild() == Constants.CC_CHILD_DOWNLOAD_URL_ASLI())
//                    {
//                        downloadUrl = childParameterModel.getValue();
//                        break;
//                    }
//                }
                mView.get().forceUpdate(downloadUrl);
            }
        }
    }
    private void checkVersionAzmayeshi(int appVersion , int azmayeshiVersion , String urlAzmayeshi)
    {
        if ((appVersion < azmayeshiVersion) || (appVersion > azmayeshiVersion))
        {
            Log.d("download" , "azmayeshi : " + urlAzmayeshi);
            mView.get().forceUpdateTest(urlAzmayeshi);
        }
        else
        {
            mView.get().onSuccessServerVersion();
        }
    }
    private int currentVersion()
    {
        try
        {
            PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
            return Integer.parseInt(deviceInfo.getCurrentVersion(getAppContext()).replace("." , ""));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mModel.setLogToDB(e.toString(), "SplashPresenter" , "" , "currentVersion" , "");
            return -1;
        }
    }



    @Override
    public void onSendEmail(boolean result)
    {
        if (result)
        {
            mView.get().showResourceError(true, R.string.success, R.string.successSendData, Constants.SUCCESS_MESSAGE(), R.string.apply);
        }
        else
        {
            mView.get().showResourceError(true, R.string.error, R.string.errorSendData, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void onGetLastLog(LogPPCModel logPPCModel)
    {
        if (logPPCModel.getLogClass() != null && logPPCModel.getLogFunctionParent() != null && logPPCModel.getLogClass().trim().toLowerCase().equals("asyncfindservertask") && logPPCModel.getLogFunctionParent().trim().toLowerCase().equals("doinbackground"))
        {
            mModel.sendEmail(logPPCModel.getLogMessage(), logPPCModel.getClass().toString(), logPPCModel.getLogActivity(), logPPCModel.getLogFunctionParent(), logPPCModel.getLogFunctionChild());
        }
        else
        {
            mModel.sendEmail("not found any server" , "" , "SplashActivity" , "onGetLastLog" , "");
        }
    }

    @Override
    public void checkInsertLogToDB(String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {
        mView = null;
        mIsChangingConfig = isChangingConfig;
        if (!isChangingConfig)
        {
            mModel.onDestroy();
        }
    }

    @Override
    public Context getAppContext() {
        try
        {
            return mView.get().getAppContext();
        }
        catch (NullPointerException e)
        {
            return null;
        }
    }

    @Override
    public void onCheckCountClearCache() {
        mView.get().onCheckCountClearCache();
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void notFoundServerIP()
    {
        mView.get().showResourceError(true, R.string.errorGetData , R.string.errorFindServerIP , Constants.FAILED_MESSAGE() , R.string.apply);
    }

    @Override
    public void onNetworkError(boolean closeActivity) {
        mView.get().showResourceError(closeActivity, R.string.errorGetDataTitle, R.string.errorGetData, Constants.FAILED_MESSAGE(), R.string.apply);
    }

    @Override
    public void onGetInvalidIdentityCode(String message,String type) {
        switch (type){
            case "INVALID_IDENTITY_CODE_LENGTH":
                mView.get().showToast(R.string.invalidIdentityCodeLength, "", Constants.FAILED_MESSAGE(), Constants.DURATION_SHORT());
                mView.get().clearInvalidIdentityCode();
                break;

            case "INVALID_IDENTITY_CODE":
                mView.get().showToast(R.string.invalidIdentityCode, "", Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                mView.get().clearInvalidIdentityCode();
                break;
            case "HTTP_ERROR":
                mView.get().showErrorAlert(true,R.string.errorDeniedPermissionTitle, message, Constants.FAILED_MESSAGE(), R.string.apply);
                break;

        }
    }

    @Override
    public void startLoading() {
        mView.get().startLoadingDialog();
    }

    @Override
    public void finishLoading() {
        mView.get().stopLoadingDialog();
    }


}
