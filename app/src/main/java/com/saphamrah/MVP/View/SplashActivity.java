package com.saphamrah.MVP.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.SplashMVP;
import com.saphamrah.MVP.Presenter.SplashPresenter;
import com.saphamrah.Network.AsyncDownloadFile;
import com.saphamrah.Network.AsyncTaskResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.io.File;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;


public class SplashActivity extends AppCompatActivity implements SplashMVP.RequiredViewOps , AsyncTaskResponse {

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, SplashActivity.this);
    SplashMVP.PresenterOps mPresenter;
    CustomAlertDialog customAlertDialog;
    private boolean isTestNewVersion;
    private final int CHECK_ACCESS_FINE_LOCATION_FOR_OPEN_MAIN = 105;
    private final int REQUEST_CODE_FOR_DELETE_SAP = 100;
    private String downloadUrl = "";
    private int invalidPackageUninstalledCounter;
    private int countInvalidPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();

        //checkReadPhoneStatePermission();

        isTestNewVersion = false;
        invalidPackageUninstalledCounter = 0;
        countInvalidPackage = 0;

        customAlertDialog = new CustomAlertDialog(SplashActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.checkAvailableEmail();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.OPEN_LOCALE_SETTING())
        {
            mPresenter.checkDeviceLanguage();
        }
        else if (requestCode == Constants.OPEN_LOCATION_SETTING())
        {
            checkLocationPermission();
        }
        else if (requestCode == REQUEST_CODE_FOR_DELETE_SAP)
        {
            /*if (resultCode == RESULT_OK)
            {*/
                invalidPackageUninstalledCounter++;
                if (invalidPackageUninstalledCounter == countInvalidPackage)
                {
                    mPresenter.getInvalidPackages();
                }
            //}
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.READ_PHONE_STATE())
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                checkReadPhoneStatePermission();
            }
            else
            {
                customAlertDialog.showMessageAlert(SplashActivity.this, true, getResources().getString(R.string.errorDeniedPermissionTitle), getResources().getString(R.string.errorDeniedPermission), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
            }
        }
        else if (requestCode == Constants.ACCESS_FINE_LOCATION())
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                checkLocationPermission();
            }
            else
            {
                customAlertDialog.showMessageAlert(SplashActivity.this, true, getResources().getString(R.string.errorDeniedPermissionTitle), getResources().getString(R.string.errorDeniedPermission), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
            }
        }
        else if (requestCode == Constants.CHANGE_WIFI_STATE()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkWifiPermission();
            } else {
                customAlertDialog.showMessageAlert(SplashActivity.this, true, getResources().getString(R.string.errorDeniedPermissionTitle), getResources().getString(R.string.errorDeniedPermission), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
            }
        } else if (requestCode == Constants.ACCESS_NETWORK_STATE()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkNetworkStateAccess();
            }
        }
        else if (requestCode == Constants.WRITE_EXTERNAL_STORAGE())
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                downloadNewVersion();
            }
        }
        else if (requestCode == CHECK_ACCESS_FINE_LOCATION_FOR_OPEN_MAIN)
        {
            Log.d("openMain" , "from on check location access");
            mPresenter.getInvalidPackages();
        }
    }


    @Override
    public void setLblVersionName(String versionName, String versionType) {
        TextView lblVersionName = findViewById(R.id.lblVersion);
        lblVersionName.setText(getResources().getString(R.string.versionText, versionName, versionType));

        TextView lblImei = findViewById(R.id.lblImei);
        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        final String IMEI = deviceInfo.getIMEI(BaseApplication.getContext());
        lblImei.setText(IMEI);

    }

    @Override
    public void goneLblVersionName() {
        TextView lblVersionName = (TextView) findViewById(R.id.lblVersion);
        lblVersionName.setVisibility(View.GONE);
    }

    @Override
    public void showBtnOffline() {
        TextView btnOffline = (Button) findViewById(R.id.btnOffline);
        btnOffline.setVisibility(View.VISIBLE);

        btnOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this , TreasuryListOfflineActivity.class));
            }
        });
    }


    @Override
    public void onErrorDeviceLanguage() {
        customAlertDialog.showDeviceLanguageMessage(SplashActivity.this);
    }

    @Override
    public void onCorrectGPS() {
        checkWifiPermission();
    }

    @Override
    public void onErrorGPS() {
        turnOnGPS();
    }

    @Override
    public void onCheckWifiStatus(boolean wifiStatus) {
        checkNetworkStateAccess();
    }

    @Override
    public void onCheckInternetType() {
        checkReadPhoneStatePermission();
    }

    @Override
    public void showGooglePlayServicesUpdateDialog(int status) {
        /*if (GoogleApiAvailability.getInstance().isUserResolvableError(status))
        {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(SplashActivity.this , status, Constants.OPEN_UPDATE_GOOGLE_PLAY_SERVICES());
            dialog.setCancelable(false);
            dialog.show();
        }
        else
        {
            customAlertDialog.showMessageAlert(SplashActivity.this , true, getResources().getString(R.string.errorGooglePlayServicesTitle) , getResources().getString(R.string.errorGooglePlayServices), Constants.FAILED_MESSAGE() , getResources().getString(R.string.apply));
        }*/
    }

	@Override
    public void showErrorAlert(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId)
    {
        customAlertDialog.showMessageAlert(SplashActivity.this, closeActivity, getResources().getString(titleResId), message, messageType, getResources().getString(buttonTextResId));
    }
	
    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId) {
        customAlertDialog.showMessageAlert(SplashActivity.this, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showResourceErrorTwoButton(final boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonOneResId, int buttonTwoResId) {
        customAlertDialog.showLogMessageAlert(SplashActivity.this, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(R.string.close), getResources().getString(R.string.send), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
                if (closeActivity) {
                    SplashActivity.this.finish();
                }
            }

            @Override
            public void setOnApplyClick() {
                mPresenter.checkLastLog();
            }
        });
    }

    @Override
    public void onSuccessServerVersion()
    {
        //openMainActivity();
        Log.d("openMain" , "from on success server version");
        mPresenter.getInvalidPackages();
    }

    @Override
    public void forceUpdate(final String downloadUrl)
    {
        Log.d("forceUpdate" , "download URL : " + downloadUrl);
        this.downloadUrl = downloadUrl;
        isTestNewVersion = false;
        customAlertDialog.showMessageAlert(SplashActivity.this, getResources().getString(R.string.forceUpdate), getResources().getString(R.string.forceUpdateMessage), Constants.FAILED_MESSAGE(), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}

            @Override
            public void setOnApplyClick() {
                checkWriteStoragePermission();
            }
        });
    }

    @Override
    public void forceUpdateTest(final String downloadUrl)
    {
        Log.d("forceUpdateTest" , "download URL : " + downloadUrl);
        this.downloadUrl = downloadUrl;
        isTestNewVersion = true;
        customAlertDialog.showMessageAlert(SplashActivity.this, getResources().getString(R.string.forceUpdate), getResources().getString(R.string.forceTestUpdateMessage), Constants.INFO_MESSAGE(), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}

            @Override
            public void setOnApplyClick() {
                checkWriteStoragePermission();
            }
        });
    }

    @Override
    public void newVersionReleased(final String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
        customAlertDialog.showLogMessageAlert(SplashActivity.this, false, getResources().getString(R.string.newVersion), getResources().getString(R.string.newVersionReleased), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
                //openMainActivity();
                Log.d("openMain" , "from on new version released");
                mPresenter.getInvalidPackages();
            }

            @Override
            public void setOnApplyClick() {
                checkWriteStoragePermission();
            }
        });
    }

    @Override
    public void newVersionAvailable(String date)
    {
        customAlertDialog.showMessageAlert(SplashActivity.this, getResources().getString(R.string.newVersion), getResources().getString(R.string.newVersionAvailable, date), Constants.INFO_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
                //openMainActivity();
                Log.d("openMain" , "from on new version available");
                mPresenter.getInvalidPackages();
            }

            @Override
            public void setOnApplyClick() {

            }
        });
    }

    private void checkWriteStoragePermission()
    {
        ArrayList<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (permissions.size() > 0)
        {
            ActivityCompat.requestPermissions(SplashActivity.this, permissions.toArray(new String[permissions.size()]), Constants.WRITE_EXTERNAL_STORAGE());
        }
        else
        {
            downloadNewVersion();
        }
    }

    private void downloadNewVersion()
    {
        ServerIPShared shared = new ServerIPShared(SplashActivity.this);
        String ip = shared.getString(shared.IP_GET_REQUEST()
 , "");
        String port = shared.getString(shared.PORT_GET_REQUEST()
 , "");
        if (ip.trim().equals("") || port.trim().equals(""))
        {
            customAlertDialog.showMessageAlert(SplashActivity.this, true, getResources().getString(R.string.error), getResources().getString(R.string.errorFindServerIP), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }
        else
        {
            /*if (isTestNewVersion)
            {
                url = String.format("%1$s%2$s:%3$s/%4$s" , "http://" , ip , port , "version/saptest.apk.zip");
            }
            else
            {
                url = String.format("%1$s%2$s:%3$s/%4$s" , "http://" , ip , port , "version/sap.apk.zip");
            }*/
            AsyncDownloadFile asyncDownloadFile = new AsyncDownloadFile(SplashActivity.this);
            asyncDownloadFile.delegate = SplashActivity.this;
            asyncDownloadFile.execute(downloadUrl);
        }
    }


    @Override
    public void processFinished(Object object)
    {
        ArrayList<String> arrayList = (ArrayList<String>) object;
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(SplashActivity.this);
        if (arrayList.get(0).equals("1"))
        {
            final String folder = arrayList.get(1);
            final String fileName = arrayList.get(2);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                intent.setDataAndType(Uri.fromFile(new File(folder + fileName)), "application/vnd.android.package-archive");
                startActivity(intent);
                SplashActivity.this.finish();
            }
            else
            {
                File file = new File(folder , fileName);
                if (file != null)
                {
                    Uri uri = FileProvider.getUriForFile(SplashActivity.this, "com.saphamrah.imagefileprovider", file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                    getApplicationContext().grantUriPermission(PACKAGE_NAME, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(uri , "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
                else
                {
                    customAlertDialog.showMessageAlert(SplashActivity.this, true,getResources().getString(R.string.error),getResources().getString(R.string.installManually),Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
                }
            }

            /*String downloadedPath = String.format("%1$s \n %2$s" , getResources().getString(R.string.newVersionDownloaded) , folder + fileName);
            customAlertDialog.showLogMessageAlert(SplashActivity.this, false, getResources().getString(R.string.success), downloadedPath, Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.close), getResources().getString(R.string.install), new CustomAlertDialogResponse() {
                @Override
                public void setOnCancelClick() {
                    SplashActivity.this.finish();
                }

                @Override
                public void setOnApplyClick() {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(folder + fileName)), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            });*/
        }
        else if (arrayList.get(0).equals("-1"))
        {
            customAlertDialog.showMessageAlert(SplashActivity.this, true,getResources().getString(R.string.error),getResources().getString(R.string.errorOccurred),Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }
        else if (arrayList.get(0).equals("-2"))
        {
            customAlertDialog.showMessageAlert(SplashActivity.this, true,getResources().getString(R.string.error),getResources().getString(R.string.downloadUrlNotFound),Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }
    }


    @Override
    public void onGetInvalidPackages(String[] packageNames)
    {
        if (packageNames != null && packageNames.length > 0)
        {
            invalidPackageUninstalledCounter = 0;
            countInvalidPackage = packageNames.length;
            for (String packageName : packageNames)
            {
                Log.d("delete" , "package : " + packageName);
                if (isPackageInstalled(packageName , SplashActivity.this.getPackageManager()))
                {
                    Log.d("delete" , "installed " + packageName);
                    Intent deleteIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
                    deleteIntent.setData(Uri.parse("package:" + packageName));
                    startActivityForResult(deleteIntent , REQUEST_CODE_FOR_DELETE_SAP);
                }
                else
                {
                    Log.d("delete" , "invalidPackageUninstalledCounter : " + invalidPackageUninstalledCounter);
                    Log.d("delete" , "countInvalidPackage : " + countInvalidPackage);
                    invalidPackageUninstalledCounter++;
                    if (invalidPackageUninstalledCounter == countInvalidPackage)
                    {
                        Log.d("openMain" , "from  onGetInvalidPackages ");
                        openMainActivity();
                    }
                }
            }
        }
        else
        {
            openMainActivity();
        }
    }

    public void openMainActivity()
    {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.ACCESS_FINE_LOCATION());
        }
        else
        {
            Intent intent = new Intent(SplashActivity.this , MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }





    private boolean isPackageInstalled(String packageName, PackageManager packageManager)
    {
        try
        {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }


    @Override
    public void showToast(int resId, String param , int messageType , int duration)
    {
        if (param.trim().length() == 0)
        {
            customAlertDialog.showToast(SplashActivity.this , getString(resId) , messageType , duration);
        }
        else
        {
            customAlertDialog.showToast(SplashActivity.this , getString(resId , param) , messageType , duration);
        }
    }


    @Override
    public Context getAppContext()
    {
        return SplashActivity.this;
    }


    public void startMVPOps()
    {
        try
        {
            if (stateMaintainer.firstTimeIn())
            {
                initialize(this);
            }
            else
            {
                reinitialize(this);
            }
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(exception.toString(), "", "SplashActivity", "startMVPOps", "");
        }
    }


    private void initialize( SplashMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new SplashPresenter(view);
            stateMaintainer.put(SplashMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(exception.toString(), "", "SplashActivity", "initialize", "");
        }
    }


    private void reinitialize(SplashMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(SplashMVP.PresenterOps.class.getSimpleName());
            if ( mPresenter == null )
            {
                initialize( view );
            }
            else
            {
                mPresenter.onConfigurationChanged( view );
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(exception.toString(), "", "SplashActivity", "reinitialize", "");
        }
    }

    public void checkReadPhoneStatePermission()
    {
        if (ContextCompat.checkSelfPermission(SplashActivity.this , Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SplashActivity.this , new String[]{Manifest.permission.READ_PHONE_STATE} , Constants.READ_PHONE_STATE());
        }
        else
        {
            mPresenter.checkServerIp();
        }
    }

    public void checkWifiPermission()
    {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SplashActivity.this , new String[]{Manifest.permission.CHANGE_WIFI_STATE} , Constants.CHANGE_WIFI_STATE());
        }
        else
        {
            mPresenter.checkWifiStatus();
        }
    }

    public void checkNetworkStateAccess()
    {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SplashActivity.this , new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE} , Constants.ACCESS_NETWORK_STATE());
        }
        else
        {
            mPresenter.checkInternetType();
        }
    }

    public void checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(SplashActivity.this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SplashActivity.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , Constants.ACCESS_FINE_LOCATION());
        }
        else
        {
            mPresenter.checkGPS();
        }
    }


    private void turnOnGPS()
    {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(SplashActivity.this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(SplashActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                checkLocationPermission();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                e.printStackTrace();
                if (e instanceof ResolvableApiException)
                {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try
                    {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(SplashActivity.this, Constants.OPEN_LOCATION_SETTING());
                    }
                    catch (IntentSender.SendIntentException sendEx)
                    {
                        // Ignore the error.
                    }
                }
                mPresenter.checkInsertLogToDB(e.toString(), "", "SplashActivity", "turnOnGPS", "task.addOnFailureListener");
            }
        });
    }




}
