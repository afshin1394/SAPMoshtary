package com.saphamrah.MVP.Model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.saphamrah.BaseMVP.MainMVP;
import com.saphamrah.DAO.ForoshandehAmoozeshiDeviceNumberDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MessageBoxDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.ParameterDAO;
import com.saphamrah.DAO.SystemMenuDAO;
import com.saphamrah.DAO.TaghiratVersionPPCDAO;
import com.saphamrah.Model.ForoshandehAmoozeshiModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.AddCustomerShared;
import com.saphamrah.Shared.GetProgramShared;
import com.saphamrah.Shared.LocalConfigShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIService;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.GetLoginInfoCallback;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;
import com.saphamrah.WebService.ServiceResponse.UpdateNotificationMessageBoxResult;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel implements MainMVP.ModelOps
{

    private MainMVP.RequiredPresenterOps mPresenter;

    public MainModel(MainMVP.RequiredPresenterOps presenterOps)
    {
        mPresenter = presenterOps;
    }

    /*@Override
    public void getOwghat()
    {
        PubFunc.GPSTracker gpsTracker = new PubFunc().new GPSTracker(mPresenter.getAppContext());
        APIServiceOwghat apiService = ApiClientOwghat.getClient().create(APIServiceOwghat.class);
        Call<OwghatResult> call = apiService.getOwghatByLatLong(gpsTracker.getLatitude() , gpsTracker.getLongitude());
        call.enqueue(new Callback<OwghatResult>() {
            @Override
            public void onResponse(Call<OwghatResult> call, Response<OwghatResult> response)
            {
                if (response.isSuccessful())
                {
                    OwghatResult result = response.body();
                    if (result != null)
                    {
                        if (result.getOk())
                        {
                            mPresenter.onGetOwghat(result.getResult());
                        }
                        else
                        {
                            mPresenter.onGetOwghat(null);
                        }
                    }
                    else
                    {
                        mPresenter.onGetOwghat(null);
                    }
                }
                else
                {
                    mPresenter.onGetOwghat(null);
                }
            }

            @Override
            public void onFailure(Call<OwghatResult> call, Throwable t)
            {
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), t.toString(), "MainModel", "", "getOwghat", "onFailure");
                mPresenter.onGetOwghat(null);
            }
        });
    }*/

    @Override
    public void getServerTime()
    {
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP() , "");
        String port = serverIPShared.getString(serverIPShared.PORT() , "");
        if (serverIP.equals("") || port.equals(""))
        {
            mPresenter.notFoundServerIP();
        }
        else
        {
            PubFunc.LoginInfo loginInfo = new PubFunc().new LoginInfo();
            loginInfo.callLoginInfoService(mPresenter.getAppContext(), serverIP, port, new GetLoginInfoCallback()
            {
                @Override
                public void onSuccess(boolean validDiffTime, String serverDateTime, String deviceDateTime, long diff)
                {
                    String message = String.format("%1$s \n %2$s : %3$s \n %4$s : %5$s \n %6$s ( %7$s %8$s) : %9$s %10$s", mPresenter.getAppContext().getString(R.string.errorLocalDateTime),
                            mPresenter.getAppContext().getString(R.string.serverTime), serverDateTime, mPresenter.getAppContext().getString(R.string.deviceTime), deviceDateTime,
                            mPresenter.getAppContext().getString(R.string.timeDiff), Constants.ALLOWABLE_SERVER_LOCAL_TIME_DIFF(),
                            mPresenter.getAppContext().getString(R.string.second), diff, mPresenter.getAppContext().getString(R.string.second));
                    mPresenter.onGetServerTime(validDiffTime, message);
                }

                @Override
                public void onFailure(String error)
                {
                    setLogToDB(LogPPCModel.LOG_EXCEPTION, error, "MainModel", "", "getServerTime", "onFailure");
                    mPresenter.onGetServerTime(false, mPresenter.getAppContext().getString(R.string.errorGetDateTimeData));
                }
            });
        }
    }

    @Override
    public void getFakeLocation()
    {
        ForoshandehAmoozeshiDeviceNumberDAO foroshandehAmoozeshiDAO = new ForoshandehAmoozeshiDeviceNumberDAO(mPresenter.getAppContext());
        ArrayList<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModelList = foroshandehAmoozeshiDAO.getAll();

        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        String IMEI = deviceInfo.getIMEI(mPresenter.getAppContext());

        PubFunc.UserType userType = new PubFunc().new UserType();
        String fakeIMEI = userType.checkUserType(mPresenter.getAppContext() , foroshandehAmoozeshiModelList , IMEI);

        if (fakeIMEI.trim().equals(""))
        {
            PubFunc.FakeLocation fakeLocation = new PubFunc().new FakeLocation();
            boolean useFakeLocation = fakeLocation.useFakeLocation(mPresenter.getAppContext());
            mPresenter.onGetFakeLocation(useFakeLocation);
        }
        else
        {
            mPresenter.onGetFakeLocation(false);
        }
    }

    @Override
    public void checkForGetParameter()					  
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();

        GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
        final String lastDateTimeGetConfig = getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG() , "20190101 00:00:00");

        final ParameterDAO parameterDAO = new ParameterDAO(mPresenter.getAppContext());

        int ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        int ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbar();
        if (ccMarkazSazmanForosh > 0)
        {
            ccMarkazAnbar = -1;
        }
        else													 
        {
            ccMarkazSazmanForosh = -1;										   
        }
        parameterDAO.fetchParameter(mPresenter.getAppContext(), "MainActivity", "1", String.valueOf(ccMarkazSazmanForosh), String.valueOf(ccMarkazAnbar), lastDateTimeGetConfig, new RetrofitResponse()
        {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                if (arrayListData.size() > 1)
                {
                    parameterDAO.deleteAll();
                    parameterDAO.insertGroup(arrayListData);
                    getParameterChild(lastDateTimeGetConfig , foroshandehMamorPakhshModel.getCcMarkazSazmanForosh(), foroshandehMamorPakhshModel.getCcMarkazAnbar());
                }
                else
                {
                    checkJayezehTakhfifVersion();
                }
            }

            @Override
            public void onFailed(String type, String error)
            {
                setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "MainModel", "MainActivity", "checkForGetParameter", "onFailed");
            }
        });
    }
	
    @Override
    public void getServerVersion()
    {
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP() , "");
        String port = serverIPShared.getString(serverIPShared.PORT() , "");
        final ArrayList<ParameterChildModel> childParameterModelsDownloadUrls = new ParameterChildDAO(mPresenter.getAppContext()).getAllByccParameter(String.valueOf(Constants.CC_DOWNLOAD_URL()));

        if (serverIP.equals("") || port.equals(""))
        {
            mPresenter.notFoundServerIP();
        }
        else
        {
            APIService apiService = ApiClient.getClient(serverIP , port).create(APIService.class);
            Call<GetVersionResult> call = apiService.getVersionInfo();
            call.enqueue(new Callback<GetVersionResult>() {
                @Override
                public void onResponse(Call<GetVersionResult> call, Response<GetVersionResult> response)
                {
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetVersionResult result = response.body();
                            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
                            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();
                            int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                            mPresenter.onGetServerVersion(result , foroshandehMamorPakhshModel , noeMasouliat , childParameterModelsDownloadUrls);
                        }
                        else
                        {
                            mPresenter.onNetworkError(false);
                        }
                    }
                    catch (Exception exception)
                    {
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MainModel", "", "getServerVersion", "onResponse");
                        exception.printStackTrace();
                        mPresenter.onNetworkError(false);
                    }
                }

                @Override
                public void onFailure(Call<GetVersionResult> call, Throwable t)
                {
                    Log.d("fail" , t.getMessage());
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "MainModel", "", "getServerVersion", "onFailure");
                    mPresenter.onNetworkError(false);
                }
            });
        }
    }

    /**
     * check version of gallery from local with Server
     */
    private void checkGalleryVersion()
    {
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        LocalConfigShared shared = new LocalConfigShared(mPresenter.getAppContext());
        int localGalleryVersion = shared.getInt(LocalConfigShared.GALLERY_VERSION , 1);
        int serverGalleryVersion = 0;
        try
        {
            serverGalleryVersion = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_UPDATE_GALLERY));
        }
        catch (Exception e) {e.printStackTrace();}
        Log.d("updateGallery" , "server version : " + serverGalleryVersion);
        Log.d("updateGallery" , "local version : " + localGalleryVersion);
        if (localGalleryVersion < serverGalleryVersion)
        {
            mPresenter.onForceUpdateGallery();
        }
    }


    /**
     * check version of JayezehTakhfif from local with server
     */
    private void checkJayezehTakhfifVersion()
    {
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        LocalConfigShared shared = new LocalConfigShared(mPresenter.getAppContext());
        //int localJayezehTakhfifVersion = shared.getInt(LocalConfigShared.JAYEZEH_TAKHFIF_VERSION , -1);
        int localJayezehTakhfifVersion = shared.getInt(LocalConfigShared.JAYEZEH_TAKHFIF_VERSION , 1);
        int serverJayezehTakhfifVersion = 0;
        try
        {
            serverJayezehTakhfifVersion = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_UPDATE_JAYEZEH_TAKHFIF));
        }
        catch (Exception e) {e.printStackTrace();}
        Log.d("updateJayezehTakhfif" , "server version : " + serverJayezehTakhfifVersion);
        Log.d("updateJayezehTakhfif" , "local version : " + localJayezehTakhfifVersion);
        if (localJayezehTakhfifVersion < serverJayezehTakhfifVersion)
        {
            mPresenter.onForceUpdateJayezehTakhfif();
        }
        else
        {
            checkGalleryVersion();
        }
    }
	
	
    @Override
    public void checkEnableMessage()
    {
        int enableGetMessage = 0;
        try
        {
            ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
            enableGetMessage = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_ENABLE_GET_MESSAGE()));
            if (enableGetMessage == 1)
            {
                fetchMessages();
            }
            else
            {
                mPresenter.disableMessageBox();
            }
        }
        catch (Exception e)
        {
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "MainModel", "MainActivity", "checkEnableMessage", "");
        }
    }


    private void fetchMessages()
    {
        final MessageBoxDAO messageBoxDAO = new MessageBoxDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();
        messageBoxDAO.fetchMessages(mPresenter.getAppContext(), "MainActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), String.valueOf(foroshandehMamorPakhshModel.getCcMamorPakhsh()), new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                messageBoxDAO.deleteAll();
                messageBoxDAO.insertGroup(arrayListData);

                getCountUnreadMessages(messageBoxDAO);
                getMessagesForShowNotif(messageBoxDAO);

                /*final Handler handler = new Handler(new Handler.Callback()
                {
                    @Override
                    public boolean handleMessage(Message msg)
                    {
                        getCountUnreadMessages(messageBoxDAO);
                        getMessagesForShowNotif(messageBoxDAO);
                        return false;
                    }
                });

                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        messageBoxDAO.deleteAll();
                        messageBoxDAO.insertGroup(arrayListData);

                        Message message = new Message();
                        message.arg1 = 1;
                        handler.sendMessage(message);
                    }
                };
                thread.start();*/
            }

            @Override
            public void onFailed(String type, String error)
            {
                setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "MainModel", "MainActivity", "getMessages", "onFailed");
                getCountUnreadMessages(messageBoxDAO);
            }
        });
    }


    private void getCountUnreadMessages(MessageBoxDAO messageBoxDAO)
    {
        int unreadCount = messageBoxDAO.getUnreadCount();
        mPresenter.enableMessageBox(unreadCount);
    }


    private void getMessagesForShowNotif(MessageBoxDAO messageBoxDAO)
    {
        ArrayList<MessageBoxModel> messageBoxModels = messageBoxDAO.getAllForSendNotification();
        Log.d("message" , "messageBoxModels.size() : " + messageBoxModels.size());
        mPresenter.showNotifForMessages(messageBoxModels);
    }

    @Override
    public void updateMessageNotifyStatus(int ccForoshandeh, int ccMamorPakhsh, final String ccMessages)
    {
        Log.d("message" , "ccMessages : " + ccMessages);

        String jsonString = jsonStringForUpdateNotifStatus(ccForoshandeh, ccMamorPakhsh, ccMessages);
        if (!jsonString.trim().equals(""))
        {
            ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
            String serverIP = serverIPShared.getString(serverIPShared.IP() , "");
            String serverPort = serverIPShared.getString(serverIPShared.PORT() , "");
            if (!serverIP.trim().equals("") && !serverPort.trim().equals(""))
            {
                final APIServicePost apiServicePost = ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
                Call<UpdateNotificationMessageBoxResult> call = apiServicePost.updateNotificationMessage(jsonString);
                call.enqueue(new Callback<UpdateNotificationMessageBoxResult>()
                {
                    @Override
                    public void onResponse(Call<UpdateNotificationMessageBoxResult> call, Response<UpdateNotificationMessageBoxResult> response)
                    {
                        try
                        {
                            MessageBoxDAO messageBoxDAO = new MessageBoxDAO(mPresenter.getAppContext());
                            messageBoxDAO.updateNotifStatus(ccMessages);
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateNotificationMessageBoxResult> call, Throwable t)
                    {
                        setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onFailure");
                    }
                });
            }
        }
    }


    private String jsonStringForUpdateNotifStatus(int ccForoshandeh, int ccMamorPakhsh, String ccMessages)
    {
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("ccMamorPakhsh" , ccMamorPakhsh);
            jsonObject.put("ccMessages" , ccMessages);
            return jsonObject.toString();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MainModel", "", "jsonStringForUpdateNotifStatus", "");
            return "";
        }
    }

    private void getParameterChild(String lastDateTimeGetConfig , int ccMarkazSazmanForosh , int ccMarkazAnbar)
    {
        final ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        if (ccMarkazSazmanForosh > 0)
        {
            ccMarkazAnbar = -1;
        }
        else
        {
            ccMarkazSazmanForosh = -1;
        }
        parameterChildDAO.fetchParameterChild(mPresenter.getAppContext(), "MainModel", "2", String.valueOf(ccMarkazSazmanForosh), String.valueOf(ccMarkazAnbar), lastDateTimeGetConfig, new RetrofitResponse()
        {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                parameterChildDAO.deleteByParameterNotGetProgram();
                parameterChildDAO.insertGroup(arrayListData);
                GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
                String currentDate = new SimpleDateFormat(Constants.DATE_TIME_WITH_SPACE()).format(new Date());
                getProgramShared.putString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG() , currentDate);
                checkJayezehTakhfifVersion();
            }

            @Override
            public void onFailed(String type, String error)
            {
                setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "MainModel", "MainActivity", "getParameterChild", "onFailed");
            }
        });
    }


    @Override
    public void getAlertAboutData()
    {
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP() , "");
        String port = serverIPShared.getString(serverIPShared.PORT() , "");

        final String currentVersion = new PubFunc().new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());
        TaghiratVersionPPCDAO taghiratVersionPPCDAO = new TaghiratVersionPPCDAO(mPresenter.getAppContext());
        final String newFeatures = taghiratVersionPPCDAO.getNewFeaturesDesc().replace("#" , "\n");

        if (!currentVersion.trim().equals("") && !serverIP.equals("") && !port.equals(""))
        {
            APIService apiService = ApiClient.getClient(serverIP , port).create(APIService.class);
            Call<GetVersionResult> call = apiService.getVersionInfo();
            call.enqueue(new Callback<GetVersionResult>() {
                @Override
                public void onResponse(Call<GetVersionResult> call, Response<GetVersionResult> response)
                {
                    if (response.raw().body() != null)
                    {
                        long contentLength = response.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(mPresenter.getAppContext(),Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "MainModel", "", "getAlertAboutData", "onResponse");
                    }
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            GetVersionResult result = response.body();
                            mPresenter.onGetAlertAboutData(currentVersion, result.getNewVersion(), result.getStableVersion() , newFeatures);
                        }
                        else
                        {
                            mPresenter.onGetAlertAboutData(currentVersion , "" , "" , newFeatures);
                        }
                    }
                    catch (Exception exception)
                    {
                        setLogToDB(Constants.LOG_EXCEPTION(),exception.toString(), "MainModel", "", "getAlertAboutData", "onResponse");
                        exception.printStackTrace();
                        mPresenter.onGetAlertAboutData(currentVersion , "" , "" , newFeatures);
                    }
                }

                @Override
                public void onFailure(Call<GetVersionResult> call, Throwable t)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(),t.toString(), "MainModel", "", "getAlertAboutData", "onFailure");
                    mPresenter.onGetAlertAboutData(currentVersion , "" , "" , newFeatures);
                }
            });
        }
        else
        {
            mPresenter.onGetAlertAboutData(currentVersion , "" , "" , newFeatures);
        }
    }

    @Override
    public void getForoshandehMamorPakhsh()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh();
        ForoshandehMamorPakhshUtils foroshandehMamorPakhsh = new ForoshandehMamorPakhshUtils();
        int stringResId = foroshandehMamorPakhsh.getNoeForoshandehMamorPakhsh(foroshandehMamorPakhshModel);
        String noeForoshandeh = "";
        if (stringResId != -1)
        {
            noeForoshandeh = mPresenter.getAppContext().getResources().getString(stringResId);
            foroshandehMamorPakhshModel.setNameNoeForoshandehMamorPakhsh(noeForoshandeh);
        }
        String deviceIMEI = new PubFunc().new DeviceInfo().getIMEI(mPresenter.getAppContext());

        int ccMasir = 0;
        /*MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
        ArrayList<MasirModel> masirModels = masirDAO.getMasirRooz();
        if (masirModels.size() > 0)
        {
            ccMasir = masirModels.get(0).getCcMasir();
        }*/
        mPresenter.onGetForoshandehMamorPakhsh(foroshandehMamorPakhshModel , deviceIMEI , ccMasir);
    }

    @Override
    public void getGPSReceiverConfig()
    {
        int minDistance = 20;
        int timeInterval = 3;
        int fastestTimeInterval = 2;
        int maxAccurancy = 8;
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String ccChildPArameters = Constants.CC_CHILD_GPS_MIN_DISTANCE + "," + Constants.CC_CHILD_GPS_TIME_INTERVAL + "," + Constants.CC_CHILD_GPS_TIME_FASTEST_INTERVAL + "," + Constants.CC_CHILD_GPS_MAX_ACCURANCY;
        ArrayList<ParameterChildModel> parameterChildModels = parameterChildDAO.getAllByccChildParameter(ccChildPArameters);
        for (ParameterChildModel parameterModel : parameterChildModels)
        {
            if (parameterModel.getCcParameterChild() == Constants.CC_CHILD_GPS_MIN_DISTANCE)
            {
                try
                {
                    minDistance = Integer.parseInt(parameterModel.getValue());
                }
                catch (Exception e){e.printStackTrace();}
            }
            else if (parameterModel.getCcParameterChild() == Constants.CC_CHILD_GPS_TIME_INTERVAL)
            {
                try
                {
                    timeInterval = Integer.parseInt(parameterModel.getValue());
                }
                catch (Exception e){e.printStackTrace();}
            }
            else if (parameterModel.getCcParameterChild() == Constants.CC_CHILD_GPS_TIME_FASTEST_INTERVAL)
            {
                try
                {
                    fastestTimeInterval = Integer.parseInt(parameterModel.getValue());
                }
                catch (Exception e){e.printStackTrace();}
            }
            else if (parameterModel.getCcParameterChild() == Constants.CC_CHILD_GPS_MAX_ACCURANCY)
            {
                try
                {
                    maxAccurancy = Integer.parseInt(parameterModel.getValue());
                }
                catch (Exception e){e.printStackTrace();}
            }
        }																				   
        mPresenter.onGetGPSReceiverConfig(minDistance , timeInterval , fastestTimeInterval , maxAccurancy);
    }

    @Override
    public void getDrawerMenuItems(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);

        SystemMenuDAO systemMenuDAO = new SystemMenuDAO(mPresenter.getAppContext());
        ArrayList<SystemMenuModel> arrayListAllMenuItems = systemMenuDAO.getMenu(noeMasouliat);
        ArrayList<SystemMenuModel> arrayListHeaders = new ArrayList<>();
        HashMap<SystemMenuModel , List<SystemMenuModel>> hashMapMenu = new HashMap<>();

        if (arrayListAllMenuItems.size() > 0)
        {
            int i = 0;
            while (arrayListAllMenuItems.get(i).getParent() == 0)
            {
                arrayListHeaders.add(arrayListAllMenuItems.get(i));
                i++;
            }

            for (SystemMenuModel systemMenuModel : arrayListHeaders)
            {
                hashMapMenu.put(systemMenuModel , new ArrayList<SystemMenuModel>());
            }
            Log.d("MainModel","arrayListHeaders :" + arrayListHeaders.toString());
            int counterCurrentHeader = 0;
            SystemMenuModel currentSystemMenuModelHeader = arrayListHeaders.get(counterCurrentHeader);
            List<SystemMenuModel> listChild = new ArrayList<>();
            for (int j=i ; j<arrayListAllMenuItems.size() ; j++)
            {
                if (arrayListAllMenuItems.get(j).getParent() == currentSystemMenuModelHeader.getId())
                {
                    Log.d("MainModel","if , arrayListAllMenuItems.get(j) : " + arrayListAllMenuItems.get(j) );
                    Log.d("MainModel", "if , currentSystemMenuModelHeader : " + currentSystemMenuModelHeader.toString());
                    listChild.add(arrayListAllMenuItems.get(j));
                }
                else
                {
                    Log.d("MainModel", "else , arrayListAllMenuItems.get(j) : " + arrayListAllMenuItems.get(j).toString());
                    hashMapMenu.put(currentSystemMenuModelHeader , listChild);
                    listChild = new ArrayList<>();
                    listChild.add(arrayListAllMenuItems.get(j));
                    counterCurrentHeader++;
                    currentSystemMenuModelHeader = new SystemMenuModel();
                    Log.d("MainModel","arrayListHeaders.get(counterCurrentHeader): " + arrayListHeaders.get(counterCurrentHeader));
                    currentSystemMenuModelHeader = arrayListHeaders.get(counterCurrentHeader);
                }
            }
            hashMapMenu.put(currentSystemMenuModelHeader , listChild);

            mPresenter.onSuccessGetDrawerMenuItems(arrayListHeaders , hashMapMenu);
        }
        else
        {
            mPresenter.onFailureGetDrawerMenuItems(false , true);
        }
    }

    @Override
    public void copyToClipboard(String tag, String value)
    {
        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        if (deviceInfo.copyToClipboard(mPresenter.getAppContext() , tag , value))
        {
            mPresenter.onSuccessCopyToClipboard(tag , value);
        }
        else
        {
            mPresenter.onFailed(R.string.errorOperation , Constants.FAILED_MESSAGE() , Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void removeAddCustomerSharedData()
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        shared.removeAll();
        mPresenter.onRemovedAddCustomerSharedData();
    }

    @Override
    public void getImageProfile()
    {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
                ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();
                File storageDir = new File(Environment.getExternalStorageDirectory() + "/SapHamrah/Pictures/Profile/");
                if (storageDir.exists())
                {
                    if (storageDir.listFiles().length > 0)
                    {
                        storageDir = new File(Environment.getExternalStorageDirectory() + "/SapHamrah/Pictures/Profile/profile-" + foroshandehMamorPakhshModel.getCcAfrad() + ".jpg");
                        Bitmap profile = BitmapFactory.decodeFile(storageDir.getAbsolutePath());
                        final byte[] profileBytes = new PubFunc().new ImageUtils().convertBitmapToByteArray(mPresenter.getAppContext() , profile , Constants.BITMAP_TO_BYTE_QUALITY());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.onGetProfileImage(profileBytes);
                            }
                        });
                    }
                    else
                    {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.onGetProfileImage(new byte[]{});
                            }
                        });
                    }
                }
                else
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.onGetProfileImage(new byte[]{});
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void saveProfileImage(String profileImageCurrentPath)
    {
        try
        {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(profileImageCurrentPath);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
            mPresenter.getAppContext().sendBroadcast(mediaScanIntent);
            getImageProfile();
        }
        catch (Exception exception)
        {
            mPresenter.onErrorSaveProfileImage();
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MainModel", "", "saveProfileImage", "");
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType , message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }


}
