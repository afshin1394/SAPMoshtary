package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.MainMVP;
import com.saphamrah.MVP.Model.MainModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPresenter implements MainMVP.PresenterOps , MainMVP.RequiredPresenterOps
{

    private WeakReference<MainMVP.RequiredViewOps> mView;
    private MainMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public MainPresenter(MainMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MainModel(this);
    }

    @Override
    public void onConfigurationChanged(MainMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    /*@Override
    public void checkOwghat()
    {
        mModel.getOwghat();
    }*/

    @Override
    public void checkServerTime()
    {
        mModel.getServerTime();
    }

    @Override
    public void checkFakeLocation()
    {
        mModel.getFakeLocation();
    }

    @Override
    public void checkForGetParameter()
    {
        mModel.checkForGetParameter();
    }
	
	@Override
    public void checkVersion()
    {
        mModel.getServerVersion();
    }

    @Override
    public void onNeedForceUpdateAzmayeshi(String urlAzmayeshVersion) {
        mView.get().forceUpdateTest(urlAzmayeshVersion);
    }

    @Override
    public void onNeedForceUpdate(String url) {
        mView.get().forceUpdate(url);
    }




    @Override
    public void checkShowMessageBox()
    {
        mModel.checkEnableMessage();
    }

    @Override
    public void checkccMessagesForUpdateNotifStatus(int ccForoshandeh, int ccMamorPakhsh, String ccMessages)
    {
        //default == -1 so if length bigger than 2, need to update
        if (ccMessages.length() > 2)
        {
            mModel.updateMessageNotifyStatus(ccForoshandeh, ccMamorPakhsh, ccMessages);
        }
    }

    @Override
    public void checkSelectedMenuItem(SystemMenuModel selectedMenuItem)
    {
        if (selectedMenuItem.getLinkTypeName().equals(SystemMenuModel.LINK_TYPE_ACTIVITY()))
        {
            mView.get().openActivity(selectedMenuItem.getLink());
        }
        else if (selectedMenuItem.getLinkTypeName().equals(SystemMenuModel.LINK_TYPE_FRAGMENT()))
        {
            //open fragment
        }
        else if (selectedMenuItem.getLinkTypeName().equals(SystemMenuModel.LINK_TYPE_ALERT_DIALOG()))
        {
            if (selectedMenuItem.getLink().equals(SystemMenuModel.ALERT_ABOUT()))
            {
                mModel.getAlertAboutData();
            }
            else if (selectedMenuItem.getLink().equals(SystemMenuModel.ALERT_EXIT()))
            {
                mView.get().showExitAlert();
            }
        }
    }

    @Override
    public void checkForoshandehMamorPakhsh()
    {
        mModel.getForoshandehMamorPakhsh();
    }

    @Override
    public void getGPSReceiverConfig()
    {
        mModel.getGPSReceiverConfig();
    }

    @Override
    public void createDrawerMenu(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        if (foroshandehMamorPakhshModel != null )
        {
            mModel.getDrawerMenuItems(foroshandehMamorPakhshModel);
        }
        else
        {
            mView.get().showResourceError(true, R.string.errorGetNoeForoshandehTitle, R.string.errorGetNoeForoshandeh, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void checkForCopyToClipboard(String tag, String value)
    {
        if (tag.trim().equals("") || value.trim().equals(""))
        {
            mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_SHORT());
        }
        else
        {
            mModel.copyToClipboard(tag , value);
        }
    }

    @Override
    public void removeAddCustomerSharedData()
    {
        mModel.removeAddCustomerSharedData();
    }

    @Override
    public void getImageProfile()
    {
        mModel.getImageProfile();
    }

    @Override
    public void checkProfileImage(String profileImageCurrentPath)
    {
        if (profileImageCurrentPath != null && profileImageCurrentPath.trim().length() > 0)
        {
            mModel.saveProfileImage(profileImageCurrentPath);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {
        mView = null;
        mIsChangingConfig = isChangingConfig;
        if (!isChangingConfig)
        {
            mModel.onDestroy();
        }
    }



    /////////////////////////// RequiredPresenterOps ///////////////////////////


    /*@Override
    public void onGetOwghat(OwghatModel owghatModel)
    {
        if (owghatModel == null)
        {
            mView.get().onFailedGetOwghat();
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.TIME_FORMAT());
            Date currentTime = Calendar.getInstance().getTime();
            try
            {
                Date sunrise = sdf.parse(owghatModel.getToluAftab());
                long diff = currentTime.getTime() - sunrise.getTime();
                if (diff > 0) // after sunrise
                {
                    int days = (int) (diff / (1000*60*60*24));
                    int hourDiff = (int) ((diff - (1000*60*60*24*days)) / (1000*60*60));
                    if (hourDiff < 4)
                    {
                        mView.get().onShowOwghat(R.drawable.sunrise , R.string.goodMorning);
                    }
                }
                else // before sunrise
                {

                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), MainPresenter.class.getSimpleName(), "", "onGetOwghat", "");
                mView.get().onFailedGetOwghat();
            }
        }
    }*/

    @Override
    public void onGetServerTime(boolean validDiffTime, String message)
    {
        if (validDiffTime)
        {
            checkFakeLocation();
        }
        else
        {
            mView.get().showErrorAlert(true, R.string.errorLocalDateTimeTitle, message, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void onGetFakeLocation(boolean fakeLocation)
    {
        if (fakeLocation)
        {
            mView.get().showResourceError(true, R.string.errorFakeLocationTitle, R.string.errorFakeLocation, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void onGetServerVersion(GetVersionResult result, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, int noeMasouliat, ArrayList<ParameterChildModel> childParameterModelsDownloadUrls)
    {
        boolean isTestUser = false;
        int appVersion = currentVersion();
        int intStableVersion = Integer.parseInt(result.getStableVersion().replace("." , ""));
        int intLastVersion = Integer.parseInt(result.getNewVersion().replace("." , ""));
        int intAzmayeshiVersion = Integer.parseInt(result.getAzmayeshVersion().replace("." , ""));

        Log.d("Main","appVersion:"+appVersion + " , intStableVersion:"+intStableVersion+
                " ,intLastVersion:" +intLastVersion + " , intAzmayeshiVersion:"+ intAzmayeshiVersion);

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
                /*if (today < serverDate)
                {
                    mView.get().newVersionAvailable(result.getDateNewVersion());
                }*/
            }
            else if (appVersion >= intStableVersion && appVersion < intLastVersion)
            {
                String downloadUrl = "";
                for (ParameterChildModel childParameterModel : childParameterModelsDownloadUrls)
                {
                    if (childParameterModel.getCcParameterChild() == Constants.CC_CHILD_DOWNLOAD_URL_ASLI())
                    {
                        downloadUrl = childParameterModel.getValue();
                        break;
                    }
                }
                mView.get().newVersionReleased(downloadUrl);
            }
            else if (appVersion < intStableVersion)
            {
                String downloadUrl = "";
                for (ParameterChildModel childParameterModel : childParameterModelsDownloadUrls)
                {
                    if (childParameterModel.getCcParameterChild() == Constants.CC_CHILD_DOWNLOAD_URL_ASLI())
                    {
                        downloadUrl = childParameterModel.getValue();
                        break;
                    }
                }
                mView.get().forceUpdate(downloadUrl);
            }
            /*else if (today < serverDate)
            {
                mView.get().newVersionAvailable(result.getDateNewVersion());
            }*/
            else
            {
                String downloadUrl = "";
                for (ParameterChildModel childParameterModel : childParameterModelsDownloadUrls)
                {
                    if (childParameterModel.getCcParameterChild() == Constants.CC_CHILD_DOWNLOAD_URL_ASLI())
                    {
                        downloadUrl = childParameterModel.getValue();
                        break;
                    }
                }
                mView.get().forceUpdate(downloadUrl);
            }
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
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "MainPresenter" , "" , "currentVersion" , "");
            return -1;
        }
    }

    private void checkVersionAzmayeshi(int appVersion , int azmayeshiVersion , String urlAzmayeshi)
    {
        if (appVersion < azmayeshiVersion)
        {
            Log.d("Main","checkVersionAzmayeshi , appVersion:"+appVersion+
            " , azmayeshiVersion: "+ azmayeshiVersion + ", urlAzmayeshi"+urlAzmayeshi);
            mView.get().forceUpdateTest(urlAzmayeshi);
        }
    }

    @Override
    public void onForceUpdateGallery()
    {
        mView.get().showForceUpdateGallery();
    }

    @Override
    public void onForceUpdateJayezehTakhfif()
    {
        mView.get().showForceUpdateJayezehTakhfif();
    }

    @Override
    public void onGetAlertAboutData(String currentVersion, String newestVersion, String lastStableVersion, String newFeatures)
    {
        mView.get().showAboutAlert(currentVersion, newestVersion, lastStableVersion, newFeatures);
    }

    @Override
    public void onGetForoshandehMamorPakhsh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel , String deviceIMEI , int ccMasir)
    {
        if (foroshandehMamorPakhshModel == null)
        {
            mView.get().showResourceError(true, R.string.errorGetNoeForoshandehTitle, R.string.errorGetNoeForoshandeh, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            mView.get().onCheckForoshandehMamorPakhsh(foroshandehMamorPakhshModel , deviceIMEI , ccMasir);
        }
    }

    @Override
    public void onGetGPSReceiverConfig(int minDistance, int timeInterval, int fastestTimeInterval, int maxAccurancy)
    {
        mView.get().startGPSService(minDistance, timeInterval, fastestTimeInterval, maxAccurancy);
    }

    @Override
    public void disableMessageBox()
    {
        mView.get().hideMessageBox();
    }

    @Override
    public void enableMessageBox(int unreadCount)
    {
        mView.get().showMessageBox(unreadCount);
    }

    @Override
    public void showNotifForMessages(ArrayList<MessageBoxModel> messageBoxModels)
    {
        if (messageBoxModels.size() > 0)
        {
            mView.get().showNotifForMessages(messageBoxModels);
        }
    }

    @Override
    public void onSuccessGetDrawerMenuItems(ArrayList<SystemMenuModel> arrayListHeaders, HashMap<SystemMenuModel , List<SystemMenuModel>> hashMapMenuItems)
    {
        mView.get().setMenuAdapter(arrayListHeaders , hashMapMenuItems);
    }

    @Override
    public void onFailureGetDrawerMenuItems(boolean errorNoeForoshandehMamorPakhsh , boolean emptyList)
    {
        if (errorNoeForoshandehMamorPakhsh)
        {
            mView.get().showResourceError(true, R.string.errorGetNoeForoshandehTitle, R.string.errorGetNoeForoshandeh, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else if (emptyList)
        {
            mView.get().showResourceError(true, R.string.errorGetMenuTitle, R.string.errorGetMenu, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void onSuccessCopyToClipboard(String tag , String value)
    {
        if (tag.trim().toLowerCase().equals("imei"))
        {
            mView.get().onSuccessCopyIMEI(value);
        }
        else
        {
            mView.get().showToast(R.string.copied , Constants.NONE_MESSAGE() , Constants.DURATION_SHORT());
        }
    }

    @Override
    public void onRemovedAddCustomerSharedData()
    {
        mView.get().onRemovedAddCustomerSharedData();
    }

    @Override
    public void onGetProfileImage(byte[] profileImage)
    {
        mView.get().onGetProfileImage(profileImage);
    }

    @Override
    public void onSavedProfileImage(byte[] profileImage)
    {
        mView.get().showToast(R.string.imageSavedSuccesfuly , Constants.SUCCESS_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onErrorSaveProfileImage()
    {
        mView.get().showToast(R.string.errorSaveImage , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public Context getAppContext()
    {
        try
        {
            return mView.get().getAppContext();
        }
        catch (NullPointerException e)
        {
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "MainPresenter" , "" , "getAppContext" , "");
            return null;
        }
    }

    @Override
    public void onFailed(int resId , int messageType , int duration)
    {
        mView.get().showToast(resId , messageType , duration);
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }

    @Override
    public void notFoundServerIP()
    {
        mView.get().showResourceError(true, R.string.errorGetData , R.string.errorFindServerIP , Constants.FAILED_MESSAGE() , R.string.apply);
    }



}
