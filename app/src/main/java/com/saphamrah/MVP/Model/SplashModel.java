package com.saphamrah.MVP.Model;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.SplashMVP;
import com.saphamrah.DAO.EmailLogPPCDAO;
import com.saphamrah.DAO.ForoshandehAmoozeshiDeviceNumberDAO;
import com.saphamrah.DAO.ForoshandehDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.LogPPCDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.ServerIPDAO;
import com.saphamrah.MVP.View.SplashActivity;
import com.saphamrah.Model.EmailLogPPCModel;
import com.saphamrah.Model.ForoshandehAmoozeshiModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.AsyncFindServerTask;
import com.saphamrah.Network.AsyncTaskFindWebServices;
import com.saphamrah.Network.AsyncTaskResponse;
import com.saphamrah.Network.RxNetwork.RxCallback;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.Authentication;
import com.saphamrah.PubFunc.DeviceInfo;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.EmailLogPPCShared;
import com.saphamrah.Shared.GetProgramShared;
import com.saphamrah.Shared.InstalledAppsShared;
import com.saphamrah.Shared.RoutingServerShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.CodeMelyResponse;
import com.saphamrah.WebService.ServiceResponse.GetForoshandehAmoozeshiResult;
import com.saphamrah.WebService.ServiceResponse.GetForoshandehMamorPakhshResult;
import com.saphamrah.WebService.ServiceResponse.GetLoginInfoCallback;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;
import com.stericson.RootTools.RootTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashModel implements SplashMVP.ModelOps, AsyncTaskFindWebServices {

    private SplashMVP.RequiredPresenterOps mPresenter;
    private CompositeDisposable compositeDisposable;

    public SplashModel(SplashMVP.RequiredPresenterOps presenterOps)
    {
        mPresenter = presenterOps;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getAvailableEmail()
    {
        EmailLogPPCDAO emailLogPPCDAO = new EmailLogPPCDAO(mPresenter.getAppContext());
        EmailLogPPCModel emailLogPPCModel = emailLogPPCDAO.getRandom();

        if (emailLogPPCModel != null)
        {
            EmailLogPPCShared shared = new EmailLogPPCShared(mPresenter.getAppContext());
            shared.putInt(shared.ID() , emailLogPPCModel.getCcEmailLogPPC());
            shared.putString(shared.EMAIL() , emailLogPPCModel.getEmail());
            shared.putString(shared.PASSWORD() , emailLogPPCModel.getPassword());
            mPresenter.onGetAvailableEmail(true);
        }
        else
        {
            mPresenter.onGetAvailableEmail(false);
        }
    }

    @Override
    public void getLastLog()
    {
        LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
        LogPPCModel logPPCModel = logPPCDAO.getLastUnsendLog();
        Log.d("logPPC" , logPPCModel.toString());
        mPresenter.onGetLastLog(logPPCModel);
    }

    @Override
    public void sendEmail(String message, String logClass, String logActivity,String logFunctionParent, String logFunctionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.sendLogToMail(mPresenter.getAppContext(), message, logClass, logActivity, logFunctionParent, logFunctionChild, new AsyncTaskResponse() {
            @Override
            public void processFinished(Object object)
            {
                mPresenter.onSendEmail((Boolean)object);
            }
        });
    }

    @Override
    public void getAppVersionName()
    {
        Context context = mPresenter.getAppContext();
        try
        {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            Log.d("SplashModel","versionName :" + packageInfo.versionName);
            String version = packageInfo.versionName;
            mPresenter.onGetAppVersionName(version);
        }
        catch (Exception exception)
        {
            setLogToDB(exception.toString(), SplashModel.class.getSimpleName(), "", "getAppVersionName", "");
            mPresenter.onGetAppVersionName("-1");
        }
    }

    @Override
    public void getIsRoot()
    {
        if (RootTools.isAccessGiven())
        {
            mPresenter.onGetIsRoot(true);
        }
        else
        {
            if (RootTools.isRootAvailable())
            {
                mPresenter.onGetIsRoot(true);
            }
            else
            {
                mPresenter.onGetIsRoot(false);
            }
        }
    }


    @Override
    public void getIMEI()
    {
        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        String IMEI = deviceInfo.getIMEI(mPresenter.getAppContext());
        Log.d("SplashModel","IMEI: "+IMEI);
        mPresenter.onGetIMEI(IMEI);
    }


    @Override
    public void getDeviceLanguage() {
        if (Locale.getDefault().getLanguage().trim().toLowerCase().equals("en"))
        {
            mPresenter.onGetDeviceLanguage(true);
        }
        else
        {
            mPresenter.onGetDeviceLanguage(false);
        }
    }

    @Override
    public void getFakeLocation()
    {
        PubFunc.FakeLocation fakeLocation = new PubFunc().new FakeLocation();
        boolean useFakeLocation = fakeLocation.useFakeLocation(mPresenter.getAppContext());
        mPresenter.onGetFakeLocation(useFakeLocation);
    }

    @Override
    public void getGPS()
    {
        boolean gps_enabled = false;
        boolean network_enabled = false;
        Context context = mPresenter.getAppContext();

        try
        {
            LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(exception.toString(), SplashModel.class.getSimpleName(), "", "getGPS", "GPS_PROVIDER");
        }

        try
        {
            LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(exception.toString(), SplashModel.class.getSimpleName(), "", "getGPS", "NETWORK_PROVIDER");
        }

        if(!gps_enabled && !network_enabled)
        {
            mPresenter.onGetGPS(false);
        }
        else
        {
            mPresenter.onGetGPS(true);
        }
    }

    @Override
    public void getWifiStatus()
    {
        boolean forceWifiOnForGPS = false;
        try
        {
            forceWifiOnForGPS = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_FORCE_WIFI_ON_FOR_GPS()).trim().equals("1");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(exception.toString(), "SplashModel", "", "getWifiStatus", "");
        }
        PubFunc.NetworkUtils networkUtils = new PubFunc().new NetworkUtils();
        if (forceWifiOnForGPS)
        {
            mPresenter.onGetWifiStatus(networkUtils.enableWifi(mPresenter.getAppContext()));
        }
        else
        {
            mPresenter.onGetWifiStatus(false);
        }
    }

    @Override
    public void getInternetType()
    {
        PubFunc.NetworkUtils networkUtils = new PubFunc().new NetworkUtils();
        int networkType = networkUtils.checkInternetType(mPresenter.getAppContext());
        mPresenter.onGetInternetType(networkType);
    }

    @Override
    public void getGooglePlayServices()
    {
        int status = ConnectionResult.SERVICE_INVALID;
        try
        {
            status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mPresenter.getAppContext());
            if (status == ConnectionResult.SERVICE_INVALID)
            {
                setLogToDB("google play services status = SERVICE_INVALID", SplashModel.class.getSimpleName(), "", "getGooglePlayServices", "");
            }
            else if (status == ConnectionResult.SERVICE_MISSING)
            {
                setLogToDB("google play services status = SERVICE_MISSING", SplashModel.class.getSimpleName(), "", "getGooglePlayServices", "");
            }
            else if (status == ConnectionResult. SERVICE_DISABLED)
            {
                setLogToDB("google play services status = SERVICE_DISABLED", SplashModel.class.getSimpleName(), "", "getGooglePlayServices", "");
            }
            else
            {
                int version = GoogleApiAvailability.getInstance().getClientVersion(mPresenter.getAppContext());
                setLogToDB("google play services version = " + version + " , and status = " + status, SplashModel.class.getSimpleName(), "", "getGooglePlayServices", "");
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(exception.toString(), SplashModel.class.getSimpleName(), "", "getGooglePlayServices", "");
        }
        mPresenter.onGetGooglePlayServices(status);
    }

    @Override
    public void getServerIP() {
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        serverIPShared.removeAll();
        ServerIPDAO serverIPDAO = new ServerIPDAO(mPresenter.getAppContext());
        ArrayList<ServerIpModel> arrayListServerIPs = serverIPDAO.getServerIPwithIsTestFilter(Constants.CURRENT_VERSION_TYPE());

        if (arrayListServerIPs.size() > 0) {
            AsyncFindServerTask asyncFindServerTask = new AsyncFindServerTask((Activity) mPresenter.getAppContext());
            asyncFindServerTask.delegate = SplashModel.this;
            asyncFindServerTask.execute(arrayListServerIPs);
        } else {
            mPresenter.onGetServerIP(null);
        }
    }

    @Override
    public void processFinished(ArrayList<Object> objects) {


        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        serverIPShared.removeAll();


        for (Object object : objects) {
            if (object.getClass() == ServerIpModel.class) {

                ServerIpModel serverIpModel = (ServerIpModel) object;

                Log.i("serverType", "processFinished: " + serverIpModel.getServerType());
/***********************************************************   X server has divided webservice base URls for get post and multi *******************************************************/


          if (objects.size()==3) {
                    switch (serverIpModel.getServerType()) {
                        case Constants.POST_REQUEST:
                            serverIPShared.putString(serverIPShared.IP_POST_REQUEST(), serverIpModel.getServerIp());
                            serverIPShared.putString(serverIPShared.PORT_POST_REQUEST(), serverIpModel.getPort());

                            break;

                        case Constants.MULTI_REQUEST:
                            serverIPShared.putString(serverIPShared.IP_MULTI_REQUEST(), serverIpModel.getServerIp());
                            serverIPShared.putString(serverIPShared.PORT_MULTI_REQUEST(), serverIpModel.getPort());

                            break;

                        case Constants.GET_REQUESTS:
                            serverIPShared.putString(serverIPShared.IP_GET_REQUEST(), serverIpModel.getServerIp());
                            serverIPShared.putString(serverIPShared.PORT_GET_REQUEST(), serverIpModel.getPort());
                            break;

                    }

/***********************************************************   M server has divided webservice base URls for get  and (post,multi) *******************************************************/

                } else if (objects.size()==2){
                    switch (serverIpModel.getServerType()) {

                        case Constants.MULTI_REQUEST:
                            serverIPShared.putString(serverIPShared.IP_MULTI_REQUEST(), serverIpModel.getServerIp());
                            serverIPShared.putString(serverIPShared.PORT_MULTI_REQUEST(), serverIpModel.getPort());

                            serverIPShared.putString(serverIPShared.IP_POST_REQUEST(), serverIpModel.getServerIp());
                            serverIPShared.putString(serverIPShared.PORT_POST_REQUEST(), serverIpModel.getPort());
                            break;

                        case Constants.GET_REQUESTS:
                            serverIPShared.putString(serverIPShared.IP_GET_REQUEST(), serverIpModel.getServerIp());
                            serverIPShared.putString(serverIPShared.PORT_GET_REQUEST(), serverIpModel.getPort());
                            break;

                    }

                }
/************************************************************ Z server doesn't have divided webservice base URls  for get post and multi***********************************************/


                else{
                    serverIPShared.putString(serverIPShared.IP_POST_REQUEST(), serverIpModel.getServerIp());
                    serverIPShared.putString(serverIPShared.PORT_POST_REQUEST(), serverIpModel.getPort());

                    serverIPShared.putString(serverIPShared.IP_MULTI_REQUEST(), serverIpModel.getServerIp());
                    serverIPShared.putString(serverIPShared.PORT_MULTI_REQUEST(), serverIpModel.getPort());

                    serverIPShared.putString(serverIPShared.IP_GET_REQUEST(), serverIpModel.getServerIp());
                    serverIPShared.putString(serverIPShared.PORT_GET_REQUEST(), serverIpModel.getPort());
                }
            }
        }
        //////////////////////////////////////////////////////getting Ip and port for all requests\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        ServerIpModel serverIpModelGet = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        ServerIpModel serverIpModelPost = new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
        ServerIpModel serverIpModelMulti=new PubFunc().new NetworkUtils().multiServerFromShared(mPresenter.getAppContext());

        //////////////////////////////////////////////////////getting Ip and port for splash\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        mPresenter.onGetServerIP(serverIpModelGet);

    }



    @Override
    public void getServerTime() {
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
                , "");
        String port = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
                , "");
        if (serverIP.equals("") || port.equals("")) {
            mPresenter.notFoundServerIP();
        } else {
            PubFunc.LoginInfo loginInfo = new PubFunc().new LoginInfo();
            loginInfo.callLoginInfoService(mPresenter.getAppContext(), serverIP, port, new GetLoginInfoCallback() {
                @Override
                public void onSuccess(boolean validDiffTime, String serverDateTime, String deviceDateTime, long diff) {
                    String message = String.format("%1$s \n %2$s : %3$s \n %4$s : %5$s \n %6$s ( %7$s %8$s) : %9$s %10$s", mPresenter.getAppContext().getString(R.string.errorLocalDateTime),
                            mPresenter.getAppContext().getString(R.string.serverTime), serverDateTime, mPresenter.getAppContext().getString(R.string.deviceTime), deviceDateTime,
                            mPresenter.getAppContext().getString(R.string.timeDiff), Constants.ALLOWABLE_SERVER_LOCAL_TIME_DIFF(),
                            mPresenter.getAppContext().getString(R.string.second), diff, mPresenter.getAppContext().getString(R.string.second));
                    mPresenter.onGetServerTime(validDiffTime, message);
                }

                @Override
                public void onFailure(String error) {

                    setLogToDB(error, SplashModel.class.getSimpleName(), "", "getServerTime", "onFailure");
                    mPresenter.onGetServerTime(false, mPresenter.getAppContext().getString(R.string.errorGetDateTimeData));
                }
            });
        }
        Log.d("test",Build.getRadioVersion());
        Log.d("test",Build.BOARD);
        Log.d("test",Build.DEVICE);
        Log.d("test",Build.HOST);
        Log.d("test",Build.ID);
        Log.d("test",Build.CPU_ABI);
        Log.d("test",Build.CPU_ABI2);
        Log.d("test",Build.SERIAL);


    }

    @Override
    public void getServerVersion() {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());

        final ArrayList<ParameterChildModel> childParameterModelsDownloadUrls = new ParameterChildDAO(mPresenter.getAppContext()).getAllByccParameter(String.valueOf(Constants.CC_DOWNLOAD_URL()));

        if (serverIpModel.getServerIp().equals("") || serverIpModel.getPort().equals("")) {
            mPresenter.notFoundServerIP();
        } else {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);//ApiClient.getClient(serverIP, port).create(APIServiceGet.class);
            Call<GetVersionResult> call = apiServiceGet.getVersionInfo();
            call.enqueue(new Callback<GetVersionResult>() {
                @Override
                public void onResponse(Call<GetVersionResult> call, Response<GetVersionResult> response) {
                    if (response.raw().body() != null) {
                        Log.d("intercept", "in on response SplashModel.getServerVersion and response : " + response.raw().body().contentLength());
                        long contentLength = response.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, SplashModel.class.getSimpleName(), "", "getServerVersion", "onResponse");
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetVersionResult result = response.body();
                            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
                            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
                            int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                            RoutingServerShared routingServerShared = new RoutingServerShared(mPresenter.getAppContext());
                            if (result != null && result.getURLOSRM() != null) {
                                routingServerShared.putString(RoutingServerShared.IP, result.getURLOSRM());
                            }
                            else
                            {
                                routingServerShared.putString(RoutingServerShared.IP, "http://91.92.125.244:8002/");
                            }
                            mPresenter.onGetServerVersion(result, foroshandehMamorPakhshModel, noeMasouliat, childParameterModelsDownloadUrls);
                        } else {
                            mPresenter.onNetworkError(false);
                        }
                    } catch (Exception exception) {
                        setLogToDB(exception.toString(), SplashModel.class.getSimpleName(), "", "getServerVersion", "onResponse");
                        exception.printStackTrace();
                        mPresenter.onNetworkError(false);
                    }
                }

                @Override
                public void onFailure(Call<GetVersionResult> call, Throwable t) {
                    Log.d("fail", t.getMessage());
                    setLogToDB(t.getMessage(), SplashModel.class.getSimpleName(), "", "getServerVersion", "onFailure");
                    mPresenter.onNetworkError(false);
                }
            });
        }

    }

    @Override
    public void getInvalidPackages() {
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String invalidPackageNames = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_UNINSTALL_INVALID_PACKAGE());
        mPresenter.onGetInvalidPackages(invalidPackageNames);
    }


    /**
     * define using IMEI
     * {@test 1 foroshandehAmoozeshi}
     * {@test 0 foroshandehAsli}
     */

    @Override
    public void getForoshandehAmoozeshi(ServerIpModel serverIpModel) {
        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        final String IMEI = deviceInfo.getIMEI(mPresenter.getAppContext());
        Log.d("SplashModel", "getForoshandehAmoozeshi Imei:" + IMEI);
        if (IMEI.equals("")) {
            mPresenter.onGetForoshandehAmoozeshi(false, true);
        } else {
            String ip = serverIpModel.getServerIp();
            String port = serverIpModel.getPort();

            if (ip.equals("") || port.equals("")) {
                mPresenter.onNetworkError(true);
            } else {
                APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);//ApiClient.getClient(serverIP, port).create(APIServiceGet.class);
                final String deviceIMEI = deviceInfo.getIMEI(mPresenter.getAppContext());
                Log.d("SplashModel","getForoshandehAmoozeshi deviceIMEI:" + deviceIMEI);
                Call<GetForoshandehAmoozeshiResult> call = apiServiceGet.getForoshandehAmoozeshi(deviceIMEI);
                call.enqueue(new Callback<GetForoshandehAmoozeshiResult>() {
                    @Override
                    public void onResponse(Call<GetForoshandehAmoozeshiResult> call, Response<GetForoshandehAmoozeshiResult> response) {
                        if (response.raw().body() != null) {
                            Log.d("intercept", "in on response SplashModel.getForoshandehAmoozeshi and response : " + response.raw().body().contentLength());
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, SplashModel.class.getSimpleName(), "", "getForoshandehAmoozeshi", "onResponse");
                        }
                        if (response.isSuccessful()) {
                            try {
                                List<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModelList = response.body().getData();
                                if (foroshandehAmoozeshiModelList.size() > 0) {
                                    ForoshandehAmoozeshiDeviceNumberDAO foroshandehAmoozeshiDAO = new ForoshandehAmoozeshiDeviceNumberDAO(mPresenter.getAppContext());
                                    foroshandehAmoozeshiDAO.deleteAll();
                                    foroshandehAmoozeshiDAO.insertGroup(foroshandehAmoozeshiModelList);
                                }

                                PubFunc.UserType userType = new PubFunc().new UserType();
                                Log.d("SplashModel", "foroshandehAmoozeshiModelList:" + foroshandehAmoozeshiModelList.toString() + " , IMEI: " + IMEI);
                                userType.checkUserType(mPresenter.getAppContext(), foroshandehAmoozeshiModelList, IMEI);

                                mPresenter.onGetForoshandehAmoozeshi(true, false);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                setLogToDB(exception.getMessage(), SplashModel.class.getSimpleName(), "", "getForoshandehAmoozeshi", "onResponse");
                                mPresenter.onNetworkError(true);
                            }
                        } else {
                            mPresenter.onNetworkError(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetForoshandehAmoozeshiResult> call, Throwable t) {
                        Log.d("fail", "error message : " + t.getMessage());
                        setLogToDB(t.getMessage(), SplashModel.class.getSimpleName(), "", "getForoshandehAmoozeshi", "onFailure");
                        mPresenter.onNetworkError(true);
                    }
                });
            }
        }
    }


    /**
     * now we get the info of foroshandeh mamoor pakhsh
     **/

    @Override
    public void getForoshandehMamorPakhsh() {
        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        final String deviceIMEI = deviceInfo.getIMEI(mPresenter.getAppContext());
        Log.d("SplashModel", "deviceIMEI:" + deviceIMEI);
        UserTypeShared userTypeShared = new UserTypeShared(mPresenter.getAppContext());
        //int isTest = userTypeShared.getInt(userTypeShared.USER_TYPE() , 0); //0-Main , 1-Test
        String usingIMEI = userTypeShared.getString(userTypeShared.USING_IMEI(), deviceIMEI);

        if (usingIMEI.equals("")) {
            mPresenter.onGetForoshandehMamorPakhsh(false, true);
        } else {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());

            if (serverIpModel.getServerIp().equals("") || serverIpModel.getPort().equals("")) {
                mPresenter.onNetworkError(true);
            } else {
                APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);//ApiClient.getClient(serverIP, port).create(APIServiceGet.class);
                Call<GetForoshandehMamorPakhshResult> call = apiServiceGet.getForoshandehMamorPakhsh(usingIMEI);
                call.enqueue(new Callback<GetForoshandehMamorPakhshResult>() {
                    @Override
                    public void onResponse(Call<GetForoshandehMamorPakhshResult> call, Response<GetForoshandehMamorPakhshResult> response) {
                        if (response.raw().body() != null) {
                            Log.d("intercept", "in on response SplashModel.getForoshandehMamorPakhsh and response : " + response.raw().body().contentLength());
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, SplashModel.class.getSimpleName(), "", "getForoshandehMamorPakhsh", "onResponse");
                        }
                        if (response.isSuccessful()) {
                            try {
                                List<ForoshandehMamorPakhshModel> listForoshandehMamorPakhsh = response.body().getData();
                                Log.d("SplashModel", "listForoshandehMamorPakhsh:" + listForoshandehMamorPakhsh.toString());
                                if (listForoshandehMamorPakhsh.size() > 0) {
                                    ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
                                    foroshandehMamorPakhshDAO.deleteAll();
//                                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = listForoshandehMamorPakhsh.get(0);
//                                    foroshandehMamorPakhshModel.setExtraPropIsSelect(0);
//                                    foroshandehMamorPakhshDAO.insert(foroshandehMamorPakhshModel);
                                    foroshandehMamorPakhshDAO.insertGroup(listForoshandehMamorPakhsh);
                                    GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
                                    int selectCcforoshandeh = Integer.valueOf(getProgramShared.getString(getProgramShared.SELECT_FOROSHANDEH() , "0"));
                                    if(selectCcforoshandeh!=0 && foroshandehMamorPakhshDAO.getByccForoshandeh(selectCcforoshandeh)!=null)
                                        foroshandehMamorPakhshDAO.updateIsSelect(selectCcforoshandeh);
                                    else
                                        foroshandehMamorPakhshDAO.updateIsSelect(foroshandehMamorPakhshDAO.getOne().getCcForoshandeh());
                                    mPresenter.onGetForoshandehMamorPakhsh(true, false);
                                } else {
                                    mPresenter.onGetEmptyForoshandehMamorPakhsh(deviceInfo.getIMEI(BaseApplication.getContext()));
                                }
                            } catch (Exception exception) {
                                setLogToDB(exception.getMessage(), SplashModel.class.getSimpleName(), "", "getForoshandehMamorPakhsh", "onResponse");
                                exception.printStackTrace();
                                mPresenter.onNetworkError(true);
                            }
                        } else {
                            mPresenter.onNetworkError(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetForoshandehMamorPakhshResult> call, Throwable t) {
                        setLogToDB(t.getMessage(), SplashModel.class.getSimpleName(), "", "getForoshandehMamorPakhsh", "onFailure");
                        mPresenter.onNetworkError(true);
                    }
                });
            }
        }
    }

    @Override
    public void getSystemConfig() {
        mPresenter.onGetSystemConfig(true);
        /*ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String ip = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
 , "");
        String port = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
 , "");

        if (ip.equals("") || port.equals(""))
        {
            mPresenter.onNetworkError(true);
        }
        else
        {
            APIService apiService = ApiClient.getClient(ip , port).create(APIService.class);
            Call<GetSystemConfigResult> call = apiService.getSystemConfig();
            call.enqueue(new Callback<GetSystemConfigResult>() {
                @Override
                public void onResponse(Call<GetSystemConfigResult> call, Response<GetSystemConfigResult> response)
                {
                    if (response.raw().body() != null)
                    {
                        Log.d("intercept" , "in on response SplashModel.getSystemConfig and response : " + response.raw().body().contentLength());
                        long contentLength = response.raw().body().contentLength();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, SplashModel.class.getSimpleName(), "", "getSystemConfig", "onResponse");
                    }
                    if (response.isSuccessful())
                    {
                        try
                        {
                            List<SystemConfigModel> listSystemConfigModel = response.body().getData();
                            if (listSystemConfigModel.size() > 0)
                            {
                                SystemConfigDAO systemConfigDAO = new SystemConfigDAO(mPresenter.getAppContext());
                                systemConfigDAO.deleteAll();
                                boolean result = systemConfigDAO.insert(listSystemConfigModel.get(0));
                                mPresenter.onGetSystemConfig(result);
                            }
                            else
                            {
                                mPresenter.onNetworkError(true);
                            }
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                            setLogToDB(exception.getMessage(), SplashModel.class.getSimpleName(), "", "getSystemConfig", "onResponse");
                            mPresenter.onNetworkError(true);
                        }
                    }
                    else
                    {
                        mPresenter.onNetworkError(true);
                    }
                }

                @Override
                public void onFailure(Call<GetSystemConfigResult> call, Throwable t)
                {
                    setLogToDB(t.getMessage(), SplashModel.class.getSimpleName(), "", "getSystemConfig", "onFailure");
                    mPresenter.onNetworkError(true);
                }
            });
        }*/
    }


    @Override
    public void getUserType() {
        UserTypeShared userTypeShared = new UserTypeShared(mPresenter.getAppContext());
        int isTest = userTypeShared.getInt(userTypeShared.USER_TYPE(), 0); //0-Main , 1-Test
        mPresenter.onGetUserType(isTest);
    }

    @Override
    public void setLogToDB(String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }


    @Override
    public void checkAuthentication() {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            boolean authFileExist = Authentication.getInstance().checkIfFileExists();
            Log.i("checkAuthentication", "checkAuthentication: " + authFileExist);
            if (authFileExist) {
                String identityCodeWithHashKey = Authentication.getInstance().getIdentityCodeWithHashKey();
                Log.i("checkAuthentication", "checkAuthentication, getHashKey:" + identityCodeWithHashKey.isEmpty());
                if (identityCodeWithHashKey.isEmpty()) {
                    Log.i("checkAuthentication", "checkAuthentication, not empty :" + identityCodeWithHashKey);
                    mPresenter.onGetForoshandehMamorPakhsh(false, true);
                } else {
                    Log.i("checkAuthentication", "checkAuthentication, done :" + identityCodeWithHashKey);
                    getForoshandehAmoozeshi(serverIpModel);
                }

            } else {
                mPresenter.onStartAuthenticationProcess();
            }
        } else {
            getForoshandehAmoozeshi(serverIpModel);
        }
    }

    @Override
    public void authenticateUser(String identityCode) {

        fetchUserHashKey(identityCode, new RxResponseHandler() {

            @Override
            public void onStart(Disposable disposable) {
                super.onStart(disposable);
                mPresenter.startLoading();
            }


            @Override
            public void onSuccess(ArrayList response) {
                mPresenter.finishLoading();
                String hashKey = response.toString();
                Log.i("fetchUserHashKey", "onSuccess: " + hashKey + "+" + identityCode);
                Authentication.getInstance().encrypt(identityCode, hashKey);
                checkAuthentication();
            }

            @Override
            public void onFailed(String message, String type) {
                mPresenter.finishLoading();
                mPresenter.onGetInvalidIdentityCode(message,type);

            }

            @Override
            public void onComplete() {
                super.onComplete();

            }
        });


    }

    private void fetchUserHashKey(String identityCode, RxResponseHandler rxResponseHandler) {

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        Log.i("fetchUserHashKey", "fetchUserHashKey: " + serverIpModel.getPort() + " " + serverIpModel.getServerIp());
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);
        RxHttpRequest.getInstance().execute(apiServiceRxjava.checkAfrad(identityCode), SplashActivity.class.getSimpleName(), SplashModel.class.getSimpleName(), "authenticateUser", new RxCallback<CodeMelyResponse>() {
            @Override
            public void onStart(Disposable disposable) {
                rxResponseHandler.onStart(disposable);
            }

            @Override
            public void onSuccess(CodeMelyResponse response) {
                Log.i("fetchUserHashKey", "onSuccess: " + response.getMessage());
                if (response != null) {
                    if (response.getMessage() != null) {
                        switch (response.getMessage()) {
                            /**INVALID_IDENTITY_CODE**/
                            case "-1":
                                onError(mPresenter.getAppContext().getString(R.string.invalidIdentityCodeLength), "INVALID_IDENTITY_CODE_LENGTH");

                                break;
                            /**INVALID_IDENTITY_CODE_LENGTH**/
                            case "-2":
                                onError(mPresenter.getAppContext().getString(R.string.invalidIdentityCode), "INVALID_IDENTITY_CODE");

                                break;
                            /**VALID_IDENTITY_CODE**/
                            case "1":
                                ArrayList hashCode = new ArrayList();
                                hashCode.add(response.getHashCode());
                                rxResponseHandler.onSuccess(hashCode);
                                break;

                        }
                    } else {
                        onError(mPresenter.getAppContext().getString(R.string.invalidIdentityCodeLength), "INVALID_IDENTITY_CODE_LENGTH");
                    }
                }

            }

            @Override
            public void onError(String message, String type) {
                rxResponseHandler.onFailed(message, type);
            }
        });
    }
    @Override
    public void getInstalledPackages() {

        List<ApplicationInfo> apps = mPresenter.getAppContext().getPackageManager().getInstalledApplications(0);
        Log.i("getInstalledPackages", "getInstalledPackages: "+apps.size());
        final String[] installedApps = {mPresenter.getAppContext().getResources().getString(R.string.installedApplications)};
        Observable.fromIterable(apps)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(applicationInfo -> applicationInfo!=null)
                .filter(applicationInfo -> applicationInfo.flags > 0)
                .filter(applicationInfo -> (applicationInfo.flags != ApplicationInfo.FLAG_SYSTEM))
                .filter(applicationInfo -> (applicationInfo.flags != ApplicationInfo.FLAG_UPDATED_SYSTEM_APP))
                .filter(applicationInfo -> applicationInfo.taskAffinity!=null)

                .subscribe(new Observer<ApplicationInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d)
                    {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ApplicationInfo applicationInfo) {
                        String appName = applicationInfo.taskAffinity.trim();
                        Log.i("getInstalledPackages", "onNext: processName"+applicationInfo.processName+"\n"
                                +"Name"+applicationInfo.name+"\n"
                                +"ClassName"+applicationInfo.className+"\n"
                                +"flag"+applicationInfo.flags+"\n"
                                +"nativeLibraryDir"+applicationInfo.nativeLibraryDir+"\n"
                                +"taskAffinity"+applicationInfo.taskAffinity+"\n"
                        );
                        installedApps[0] +="\n"+appName+"\n";

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(mPresenter.getAppContext(),Constants.LOG_EXCEPTION(),"error getting installed packages:"+e.getMessage(),"SplashModel","SplashActivity","getInstalledPackages","");
                        Log.i("getInstalledPackages", "onError: exception in finding packages" + e.getMessage()+"");
                    }

                    @Override
                    public void onComplete() {
                        InstalledAppsShared shared = new InstalledAppsShared(mPresenter.getAppContext());
                        String installedAppsLog = shared.getString(shared.INSTALLED_APPLICATIONS,null);
                        if (installedAppsLog != null)
                        {
                            if (!installedApps[0].equals(installedAppsLog))
                            {
                                shared.removeAll();
                                shared.putString(shared.INSTALLED_APPLICATIONS, installedApps[0]);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), installedApps[0], "SplashModel", "SplashActivity", "getInstalledPackages", "");
                            }
                        }
                        else
                        {
                            shared.putString(shared.INSTALLED_APPLICATIONS, installedApps[0]);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), installedApps[0], "SplashModel", "SplashActivity", "getInstalledPackages", "");
                        }
                    }
                });
    }
}

