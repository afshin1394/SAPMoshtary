package com.saphamrah.MVP.splash;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.EmailLogPPCDAO;
import com.saphamrah.DAO.ForoshandehAmoozeshiDeviceNumberDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.LogPPCDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.ServerIPDAO;
import com.saphamrah.Model.EmailLogPPCModel;
import com.saphamrah.Model.ForoshandehAmoozeshiModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.AsyncFindServerTask;
import com.saphamrah.Network.AsyncTaskFindWebServices;
import com.saphamrah.Network.AsyncTaskResponse;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxCallback;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.Authentication;
import com.saphamrah.PubFunc.FakeLocation;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.NetworkUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.EmailLogPPCShared;
import com.saphamrah.Shared.GetProgramShared;
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

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashModel implements SplashMVP.ModelOps, AsyncTaskFindWebServices {

    private SplashMVP.RequiredPresenterOps mPresenter;


    public SplashModel(SplashMVP.RequiredPresenterOps presenterOps)
    {
        mPresenter = presenterOps;
    }
    ServerIPShared serverIPShared = new ServerIPShared(BaseApplication.getContext());
    ServerIPDAO serverIPDAO = new ServerIPDAO(BaseApplication.getContext());

    /**
     * check root device
     */
    @Override
    public void getIsRoot()
    {
        Log.d("splashModel", "getIsRoot" );

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

    /**
     * check language device
     */
    @Override
    public void getDeviceLanguage() {
        Log.d("splashModel", "getDeviceLanguage" );
        if (Locale.getDefault().getLanguage().trim().toLowerCase().equals("en"))
        {
            mPresenter.onGetDeviceLanguage(true);
        }
        else
        {
            mPresenter.onGetDeviceLanguage(false);
        }
    }

    /**
     * check fake location device
     */
    @Override
    public void getFakeLocation()
    {
        Log.d("splashModel", "getFakeLocation" );
        FakeLocation fakeLocation = new FakeLocation();
        boolean useFakeLocation = fakeLocation.useFakeLocation(mPresenter.getAppContext());
        mPresenter.onGetFakeLocation(useFakeLocation);
    }

    /**
     * check gps
     */
    @Override
    public void getGPS()
    {
        Log.d("splashModel", "getGPS" );
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

    /**
     * check internet
     */
    @Override
    public void getInternetType()
    {
        Log.d("splashModel", "getInternetType" );
        int networkType = new NetworkUtils().checkInternetType(BaseApplication.getContext());
        mPresenter.onGetInternetType(networkType);
    }

    /**
     * check wifi
     */
    @Override
    public void getWifiStatus()
    {
        Log.d("splashModel", "getWifiStatus" );
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

        if (forceWifiOnForGPS)
        {
            mPresenter.onGetWifiStatus(new NetworkUtils().enableWifi(mPresenter.getAppContext()));
        }
        else
        {
            mPresenter.onGetWifiStatus(false);
        }
    }

    /**
     * check google play service
     */
    @Override
    public void getGooglePlayServices()
    {
        Log.d("splashModel", "getGooglePlayServices" );
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

    /**
     * check invalid package
     */
    @Override
    public void getInvalidPackages() {
        Log.d("splashModel", "getInvalidPackages" );
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String invalidPackageNames = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_UNINSTALL_INVALID_PACKAGE());
        mPresenter.onGetInvalidPackages(invalidPackageNames);
    }

    /**
     * get IP
     * @param first = false --> we show again dialog select server ip
     *              = true --> just continue
     */
    @Override
    public void getServerIP(boolean first) {
        Log.d("splashModel", "getServerIP" );
        serverIPShared.removeAll();
        ArrayList<ServerIpModel> arrayListServerIPs = serverIPDAO.getServerIPwithIsTestFilter(Constants.CURRENT_VERSION_TYPE());
        mPresenter.onGetServerIPs(arrayListServerIPs, first);

    }

    /**
     * selected Ip
     *
     * @param ip
     */
    @Override
    public void selectIp(String ip) {
        Log.d("splashModel", "selectIp" );
        if (NetworkUtils.isNetworkAvailable(BaseApplication.getContext())) {
            ArrayList<ServerIpModel> arrayListServerIPs = serverIPDAO.getServerISelected(Constants.CURRENT_VERSION_TYPE(), ip);
            Log.d("splashModel", "selectIp" +arrayListServerIPs.toString());
            if (arrayListServerIPs.size() > 0) {
                AsyncFindServerTask asyncFindServerTask = new AsyncFindServerTask((Activity) mPresenter.getAppContext());
                asyncFindServerTask.delegate = SplashModel.this;
                asyncFindServerTask.execute(arrayListServerIPs);
            } else {
                mPresenter.onGetServerIP(null);
            }
        } else {
            mPresenter.onGetServerIP(null);
        }


    }
    @Override
    public void processFinished(ArrayList<Object> objects) {

        Log.d("splashModel", "processFinished" );
        if (objects.size() > 0) {

            ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
            serverIPShared.removeAll();


            for (Object object : objects) {
                if (object.getClass() == ServerIpModel.class) {

                    ServerIpModel serverIpModel = (ServerIpModel) object;

                    Log.i("serverType", "processFinished: " + serverIpModel.getServerType() + ", " + serverIpModel.getServerIp() + ":" + serverIpModel.getPort());
/***********************************************************   X server has divided webservice base URls for get post and multi *******************************************************/


                    if (objects.size() == 3) {
                        switch (serverIpModel.getServerType()) {
                            case Constants.POST_REQUEST:
                                serverIPShared.putString(serverIPShared.IP_POST_REQUEST(), serverIpModel.getServerIp());
                                serverIPShared.putString(serverIPShared.PORT_POST_REQUEST(), serverIpModel.getPort());
                                serverIPShared.putInt(serverIPShared.TYPE_POST_REQUEST(), serverIpModel.getWebServiceType());

                                Log.i("serverType", "POST_REQUEST");
                                break;

                            case Constants.MULTI_REQUEST:
                                serverIPShared.putString(serverIPShared.IP_MULTI_REQUEST(), serverIpModel.getServerIp());
                                serverIPShared.putString(serverIPShared.PORT_MULTI_REQUEST(), serverIpModel.getPort());
                                serverIPShared.putInt(serverIPShared.TYPE_MULTI_REQUEST(), serverIpModel.getWebServiceType());

                                Log.i("serverType", "MULTI_REQUEST");
                                break;

                            case Constants.GET_REQUESTS:
                                serverIPShared.putString(serverIPShared.IP_GET_REQUEST(), serverIpModel.getServerIp());
                                serverIPShared.putString(serverIPShared.PORT_GET_REQUEST(), serverIpModel.getPort());
                                serverIPShared.putInt(serverIPShared.TYPE_GET_REQUEST(), serverIpModel.getWebServiceType());

                                Log.i("serverType", "GET_REQUESTS");
                                break;

                        }

/***********************************************************   M server has divided webservice base URls for get  and (post,multi) *******************************************************/

                    } else if (objects.size() == 2) {
                        switch (serverIpModel.getServerType()) {

                            case Constants.MULTI_REQUEST:
                                serverIPShared.putString(serverIPShared.IP_MULTI_REQUEST(), serverIpModel.getServerIp());
                                serverIPShared.putString(serverIPShared.PORT_MULTI_REQUEST(), serverIpModel.getPort());
                                serverIPShared.putInt(serverIPShared.TYPE_MULTI_REQUEST(), serverIpModel.getWebServiceType());


                                serverIPShared.putString(serverIPShared.IP_POST_REQUEST(), serverIpModel.getServerIp());
                                serverIPShared.putString(serverIPShared.PORT_POST_REQUEST(), serverIpModel.getPort());
                                serverIPShared.putInt(serverIPShared.TYPE_POST_REQUEST(), serverIpModel.getWebServiceType());

                                break;

                            case Constants.GET_REQUESTS:
                                serverIPShared.putString(serverIPShared.IP_GET_REQUEST(), serverIpModel.getServerIp());
                                serverIPShared.putString(serverIPShared.PORT_GET_REQUEST(), serverIpModel.getPort());
                                serverIPShared.putInt(serverIPShared.TYPE_GET_REQUEST(), serverIpModel.getWebServiceType());

                                break;

                        }

                    }
/************************************************************ Z server doesn't have divided webservice base URls  for get post and multi***********************************************/


                    else {
                        serverIPShared.putString(serverIPShared.IP_POST_REQUEST(), serverIpModel.getServerIp());
                        serverIPShared.putString(serverIPShared.PORT_POST_REQUEST(), serverIpModel.getPort());
                        serverIPShared.putInt(serverIPShared.TYPE_POST_REQUEST(), serverIpModel.getWebServiceType());


                        serverIPShared.putString(serverIPShared.IP_MULTI_REQUEST(), serverIpModel.getServerIp());
                        serverIPShared.putString(serverIPShared.PORT_MULTI_REQUEST(), serverIpModel.getPort());
                        serverIPShared.putInt(serverIPShared.TYPE_MULTI_REQUEST(), serverIpModel.getWebServiceType());


                        serverIPShared.putString(serverIPShared.IP_GET_REQUEST(), serverIpModel.getServerIp());
                        serverIPShared.putString(serverIPShared.PORT_GET_REQUEST(), serverIpModel.getPort());
                        serverIPShared.putInt(serverIPShared.TYPE_GET_REQUEST(), serverIpModel.getWebServiceType());

                    }
                }
            }
            //////////////////////////////////////////////////////getting Ip and port for all requests\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

            ServerIpModel serverIpModelGet = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
            ServerIpModel serverIpModelPost = new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
            ServerIpModel serverIpModelMulti = new PubFunc().new NetworkUtils().multiServerFromShared(mPresenter.getAppContext());

            //////////////////////////////////////////////////////getting Ip and port for splash\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            mPresenter.onGetServerIP(serverIpModelGet);
        } else {
            getServerIP(false);
        }

    }

    /**
     * get server time
     */
    @Override
    public void getServerTime() {
        Log.d("splashModel", "getServerTime" );
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
                , "");
        String port = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
                , "");
        if (serverIP.equals("") || port.equals("")) {
            mPresenter.notFoundServerIP();
        } else {
            Log.d("splashModel", "getServerTime else" );
            PubFunc.LoginInfo loginInfo = new PubFunc().new LoginInfo();
            loginInfo.callLoginInfoService(mPresenter.getAppContext(), serverIP, port, new GetLoginInfoCallback() {
                @Override
                public void onSuccess(boolean validDiffTime, String serverDateTime, String deviceDateTime, long diff) {
                    String message = String.format("%1$s \n %2$s : %3$s \n %4$s : %5$s \n %6$s ( %7$s %8$s) : %9$s %10$s", mPresenter.getAppContext().getString(R.string.errorLocalDateTime),
                            mPresenter.getAppContext().getString(R.string.serverTime), serverDateTime, mPresenter.getAppContext().getString(R.string.deviceTime), deviceDateTime,
                            mPresenter.getAppContext().getString(R.string.timeDiff), Constants.ALLOWABLE_SERVER_LOCAL_TIME_DIFF(),
                            mPresenter.getAppContext().getString(R.string.second), diff, mPresenter.getAppContext().getString(R.string.second));
                    Log.d("splashModel", "getServerTime" + validDiffTime + message);
                    mPresenter.onGetServerTime(validDiffTime, message);
                }

                @Override
                public void onFailure(String error) {

                    setLogToDB(error, SplashModel.class.getSimpleName(), "", "getServerTime", "onFailure");
                    mPresenter.onGetServerTime(false, mPresenter.getAppContext().getString(R.string.errorGetDateTimeData));
                }
            });
        }
        Log.d("test", Build.getRadioVersion());
        Log.d("test", Build.BOARD);
        Log.d("test", Build.DEVICE);
        Log.d("test", Build.HOST);
        Log.d("test", Build.ID);
        Log.d("test", Build.CPU_ABI);
        Log.d("test", Build.CPU_ABI2);
        Log.d("test", Build.SERIAL);


    }

    /**
     * check Authentication with national code
     */
    @Override
    public void checkAuthentication() {
        Log.d("splashModel", "checkAuthentication" );
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
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
        Log.d("splashModel", "authenticateUser" );
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
                mPresenter.onGetInvalidIdentityCode(message, type);

            }

            @Override
            public void onComplete() {
                super.onComplete();

            }
        });


    }

    private void fetchUserHashKey(String identityCode, RxResponseHandler rxResponseHandler) {
        Log.d("splashModel", "fetchUserHashKey" );
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()) {
            case REST:
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

                    @Override
                    public void onError(String message, String type) {
                        rxResponseHandler.onFailed(message, type);
                    }
                });
            break;
            case gRPC:
                Authentication.getInstance().fetchUserHashKeyGrpc(mPresenter.getAppContext(), identityCode, new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList response) {
                        String output = response.get(0).toString();
                        switch (output) {
                            /**INVALID_IDENTITY_CODE**/
                            case "-1":
                                onFailed(mPresenter.getAppContext().getString(R.string.invalidIdentityCodeLength), "INVALID_IDENTITY_CODE_LENGTH");

                                break;
                            /**INVALID_IDENTITY_CODE_LENGTH**/
                            case "-2":
                                onFailed(mPresenter.getAppContext().getString(R.string.invalidIdentityCode), "INVALID_IDENTITY_CODE");

                                break;
                            /**VALID_IDENTITY_CODE**/
                            default:

                                rxResponseHandler.onSuccess(response);
                                break;

                        }
                    }

                    @Override
                    public void onFailed(String message, String type) {
                        rxResponseHandler.onFailed(message, type);

                    }
                });
                break;

        }

    }

    /**
     * get foroshandeh amoozeshi
     * define using IMEI
     * {@test 1 foroshandehAmoozeshi}
     * {@test 0 foroshandehAsli}
     */
    @Override
    public void getForoshandehAmoozeshi(ServerIpModel serverIpModel) {
        Log.d("splashModel", "getForoshandehAmoozeshi" );
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

                ForoshandehAmoozeshiDeviceNumberDAO foroshandehAmoozeshiDeviceNumberDAO = new ForoshandehAmoozeshiDeviceNumberDAO(mPresenter.getAppContext());
                switch (serverIpModel.getWebServiceType()){
                    case REST:
                        foroshandehAmoozeshiDeviceNumberDAO.getForoshandehAmoozeshi(mPresenter.getAppContext(), serverIpModel, SplashActivity.class.getSimpleName(), new RetrofitResponse() {
                            @Override
                            public void onSuccess(ArrayList arrayListData) {
                                try {
                                    List<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModelList = arrayListData;
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
                            }

                            @Override
                            public void onFailed(String type, String error) {
                                setLogToDB(error, SplashModel.class.getSimpleName(), "", "getForoshandehAmoozeshi", "onFailure");
                                mPresenter.onNetworkError(true);
                            }
                        });

                        break;

                    case gRPC:
                        foroshandehAmoozeshiDeviceNumberDAO.getForoshandehAmoozeshiGrpc(mPresenter.getAppContext(), serverIpModel, SplashActivity.class.getSimpleName(), new RetrofitResponse() {
                            @Override
                            public void onSuccess(ArrayList arrayListData) {
                                try {
                                    List<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModelList = arrayListData;
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
                            }

                            @Override
                            public void onFailed(String type, String error) {
                                setLogToDB(error, SplashModel.class.getSimpleName(), "", "getForoshandehAmoozeshi", "onFailure");
                                mPresenter.onNetworkError(true);
                            }
                        });

                        break;
                }
            }
        }
    }

    /**
     * get foroshandeh mamor pakhsh
     * now we get the info of foroshandeh mamoor pakhsh
     **/
    @Override
    public void getForoshandehMamorPakhsh() {
        Log.d("splashModel", "getForoshandehMamorPakhsh" );
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
                switch (serverIpModel.getWebServiceType()){
                    case REST:
                        new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).fetchForoshandehMamorPakhshForUpdate(mPresenter.getAppContext(), SplashActivity.class.getSimpleName(), usingIMEI, new RetrofitResponse() {
                            @Override
                            public void onSuccess(ArrayList arrayListData) {
                                try {
                                    List<ForoshandehMamorPakhshModel> listForoshandehMamorPakhsh = arrayListData;
                                    Log.d("SplashModel", "listForoshandehMamorPakhsh:" + listForoshandehMamorPakhsh.toString());
                                    if (listForoshandehMamorPakhsh.size() > 0) {
                                        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
                                        foroshandehMamorPakhshDAO.deleteAll();
//                                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = listForoshandehMamorPakhsh.get(0);
//                                    foroshandehMamorPakhshModel.setExtraPropIsSelect(0);
//                                    foroshandehMamorPakhshDAO.insert(foroshandehMamorPakhshModel);
                                        foroshandehMamorPakhshDAO.insertGroup(listForoshandehMamorPakhsh);
                                        GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
                                        int selectCcforoshandeh = Integer.valueOf(getProgramShared.getString(getProgramShared.SELECT_FOROSHANDEH(), "0"));
                                        if (selectCcforoshandeh != 0 && foroshandehMamorPakhshDAO.getByccForoshandeh(selectCcforoshandeh) != null)
                                            foroshandehMamorPakhshDAO.updateIsSelect(selectCcforoshandeh);
                                        else
                                            foroshandehMamorPakhshDAO.updateIsSelect(foroshandehMamorPakhshDAO.getSplash().getCcForoshandeh());
                                        mPresenter.onGetForoshandehMamorPakhsh(true, false);
                                    } else {
                                        mPresenter.onGetEmptyForoshandehMamorPakhsh(deviceIMEI);
                                    }
                                } catch (Exception exception) {
                                    setLogToDB(exception.getMessage(), SplashModel.class.getSimpleName(), "", "getForoshandehMamorPakhsh", "onResponse");
                                    exception.printStackTrace();
                                    mPresenter.onNetworkError(true);
                                }
                            }

                            @Override
                            public void onFailed(String type, String error) {
                                setLogToDB(error, SplashModel.class.getSimpleName(), "", "getForoshandehMamorPakhsh", "onFailure");
                                mPresenter.onNetworkError(true);
                            }
                        });
                        break;


                    case gRPC:
                        new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).fetchForoshandehMamorPakhshForUpdateGrpc(mPresenter.getAppContext(), SplashActivity.class.getSimpleName(), usingIMEI, new RetrofitResponse() {
                            @Override
                            public void onSuccess(ArrayList arrayListData) {
                                try {
                                    List<ForoshandehMamorPakhshModel> listForoshandehMamorPakhsh = arrayListData;
                                    Log.d("SplashModel", "listForoshandehMamorPakhsh:" + listForoshandehMamorPakhsh.toString());
                                    if (listForoshandehMamorPakhsh.size() > 0) {
                                        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
                                        foroshandehMamorPakhshDAO.deleteAll();
//                                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = listForoshandehMamorPakhsh.get(0);
//                                    foroshandehMamorPakhshModel.setExtraPropIsSelect(0);
//                                    foroshandehMamorPakhshDAO.insert(foroshandehMamorPakhshModel);
                                        foroshandehMamorPakhshDAO.insertGroup(listForoshandehMamorPakhsh);
                                        GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
                                        int selectCcforoshandeh = Integer.valueOf(getProgramShared.getString(getProgramShared.SELECT_FOROSHANDEH(), "0"));
                                        if (selectCcforoshandeh != 0 && foroshandehMamorPakhshDAO.getByccForoshandeh(selectCcforoshandeh) != null)
                                            foroshandehMamorPakhshDAO.updateIsSelect(selectCcforoshandeh);
                                        else
                                            foroshandehMamorPakhshDAO.updateIsSelect(foroshandehMamorPakhshDAO.getSplash().getCcForoshandeh());
                                        mPresenter.onGetForoshandehMamorPakhsh(true, false);
                                    } else {
                                        mPresenter.onGetEmptyForoshandehMamorPakhsh(deviceIMEI);
                                    }
                                } catch (Exception exception) {
                                    setLogToDB(exception.getMessage(), SplashModel.class.getSimpleName(), "", "getForoshandehMamorPakhsh", "onResponse");
                                    exception.printStackTrace();
                                    mPresenter.onNetworkError(true);
                                }
                            }

                            @Override
                            public void onFailed(String type, String error) {
                                setLogToDB(error, SplashModel.class.getSimpleName(), "", "getForoshandehMamorPakhsh", "onFailure");
                                mPresenter.onNetworkError(true);
                            }
                        });

                        break;
                }


            }
        }
    }


    /**
     * get user type
     */
    @Override
    public void getUserType() {
        Log.d("splashModel", "getUserType" );
        UserTypeShared userTypeShared = new UserTypeShared(mPresenter.getAppContext());
        int isTest = userTypeShared.getInt(userTypeShared.USER_TYPE(), 0); //0-Main , 1-Test
        mPresenter.onGetUserType(isTest);
    }

    /**
     * get IMEI or IdentityCode
     */
    @Override
    public void getIMEI()
    {
        Log.d("splashModel", "getIMEI" );
        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        String IMEI = deviceInfo.getIMEI(mPresenter.getAppContext());
        Log.d("SplashModel","IMEI: "+IMEI);
        mPresenter.onGetIMEI(IMEI);
    }

    /**
     * check Available Email
     */
    @Override
    public void getAvailableEmail()
    {
        Log.d("splashModel", "getAvailableEmail" );
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

    /**
     * get app version name
     */
    @Override
    public void getAppVersionName()
    {
        Log.d("splashModel", "getAppVersionName" );
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

    /**
     * get server version app
     */
    @Override
    public void getServerVersion() {
        Log.d("splashModel", "getServerVersion" );
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        //TODO must be modified
        serverIpModel.setPort("8040");
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

    /**
     * send log with email
     * @param message
     * @param logClass
     * @param logActivity
     * @param logFunctionParent
     * @param logFunctionChild
     */
    @Override
    public void sendEmail(String message, String logClass, String logActivity,String logFunctionParent, String logFunctionChild)
    {
        Log.d("splashModel", "sendEmail" );
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
    public void getLastLog()
    {
        Log.d("splashModel", "getLastLog" );
        LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
        LogPPCModel logPPCModel = logPPCDAO.getLastUnsendLog();
        Log.d("logPPC" , logPPCModel.toString());
        mPresenter.onGetLastLog(logPPCModel);
    }

    @Override
    public void setLogToDB(String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }



}

