package com.saphamrah.MVP.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.View.MainActivity;
import com.saphamrah.MVP.View.TreasuryListOfflineActivity;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.AsyncDownloadFile;
import com.saphamrah.Network.AsyncTaskResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import java.io.File;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;
import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class SplashActivity extends AppCompatActivity implements SplashMVP.RequiredViewOps, AsyncTaskResponse {

//******* if each of the above went wrong we should show (Dialog or Toast error ) or exit application  *******\\
    /**
     ***** Road map For Splash ***** */

     /** @see #checkPermission()
      * 1 - check all permission --> when check all permission is ok we can continue
      */

    /** @see SplashModel#getIsRoot()
     * 2 - check root device
     */

    /** @see SplashModel#getDeviceLanguage()
     * 3 - check device language
     */

    /** @see SplashModel#getGPS()
     * 4 - check gps device
     */

    /** @see SplashModel#getInternetType()
     * 5 - check internet device
     */

    /** @see SplashModel#getWifiStatus()
     * 6 - check wifi device
     */

    /** @see SplashModel#getGooglePlayServices()
     * 7 - check google play service device
     */



    /** @see SplashModel#getServerIP(boolean)
     * 8 - get all IP for show dialog select server
     */

    /** @see SplashModel#getServerTime()
     * 9 - check server time
     */

    /** @see SplashModel#checkAuthentication()
     * 10 - check code unique (Authentication)
     */

    /** @see SplashModel#getForoshandehAmoozeshi(ServerIpModel)
     * 11 - check foroshandeh amoozeshi
     */

    /** @see SplashModel#getForoshandehMamorPakhsh()
     * 12 - check foroshandeh mamorPakhsh
     */

    /** @see SplashModel#getUserType()
     * 13 - check user type
     */

    /** @see SplashModel#getIMEI()
     * 14 - get IMEI
     */

    /** @see SplashModel#getAvailableEmail()
     * 15 - check Available Email
     */

    /** @see SplashModel#getAppVersionName()
     * 16 - check app version name
     */

    /** @see SplashModel#getServerVersion() ()
     * 17 - check server version
     */

    // if is test == 0 (Asli)
    /** @see SplashModel#getFakeLocation()
     *  - check fake location device
     */

    /** @see SplashModel#getInvalidPackages()
     *  - check invalid packages app
     */

    /**
     * class
     */
    private SplashPresenter mPresenter;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;
    private CustomSpinner customSpinner;


    /**
     * variable
     */
    private boolean isTestNewVersion = false ;
    private final int REQUEST_CODE_FOR_DELETE_SAP = 100;
    private String downloadUrl = "";
    private int invalidPackageUninstalledCounter = 0;
    private int countInvalidPackage = 0;
    private ArrayList<String> arrayListTitles ;


    /**
     * find view
     */
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.crdIdentityCode)
    CardView crdIdentityCode;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etv_IdentityCode)
    EditText etvIdentityCode;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mPresenter = new SplashPresenter(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        customSpinner = new CustomSpinner();
        arrayListTitles = new ArrayList<>();
        customAlertDialog = new CustomAlertDialog(SplashActivity.this);
        customLoadingDialog = new CustomLoadingDialog();


        checkPermission();
    }


    /**
     * check permission
     */
    public void checkPermission() {
        int ALL_PERMISSIONS = Constants.ALL_PERMISSIONS();

        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA
                , Manifest.permission.CHANGE_WIFI_STATE
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.READ_PHONE_STATE};


        if (!hasPermissions(SplashActivity.this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS);
        } else {
            mPresenter.checkIsRoot();
        }
    }
    public boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.ALL_PERMISSIONS()) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            } else {
                customAlertDialog.showMessageAlert(SplashActivity.this, true, getResources().getString(R.string.errorDeniedPermissionTitle), getResources().getString(R.string.errorDeniedPermission), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
            }

        }
    }

    /**
     * error gps ( gps is off )
     */
    @Override
    public void onErrorGPS() {
        turnOnGPS();
    }

    /**
     * turn on gps
     */
    private void turnOnGPS() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(SplashActivity.this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(SplashActivity.this, locationSettingsResponse ->  mPresenter.checkGPS());

        task.addOnFailureListener(this, e -> {
            e.printStackTrace();
            if (e instanceof ResolvableApiException)
            {
                try
                {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(SplashActivity.this, Constants.OPEN_LOCATION_SETTING());
                    SplashActivity.this.finish();
                }
                catch (IntentSender.SendIntentException sendEx)
                {
                   e.getMessage();
                }
            }
            mPresenter.checkInsertLogToDB(e.toString(), "", "SplashActivity", "turnOnGPS", "task.addOnFailureListener");
        });
    }

    /**
     * invalid package
     * @param packageNames
     */
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
                        mPresenter.checkIMEI();
                    }
                }
            }
        }
        else
        {
            mPresenter.checkIMEI();
        }
    }

    /**
     * get server ip and show dialog for select server
     * @param arrayListServerIPs
     */
    @Override
    public void onGetServerIPs(ArrayList<ServerIpModel> arrayListServerIPs) {
        arrayListTitles.clear();
        for (int i = 0; i <arrayListServerIPs.size() ; i++) {
            arrayListTitles.add(arrayListServerIPs.get(i).getNameServerPersian());
        }
        customSpinner.showSpinnerSingleButton(SplashActivity.this, arrayListTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                if (arrayListServerIPs.size()>0){
                    String ip = arrayListServerIPs.get(selectedIndex).getServerIp();
                    mPresenter.selectIp(ip);
                } else {
                    finish();
                }

            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });

    }

    /**
     * show Authentication process and get nationalCode
     */
    @Override
    public void showAuthenticationProcess() {
        crdIdentityCode.setVisibility(View.VISIBLE);
        setIdentityCodeTextWatcher();
    }

    private void setIdentityCodeTextWatcher() {
        TextWatcher etvIdentityCodeTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable identityCode) {
                if (identityCode.length() == 10)
                    mPresenter.authenticateUser(identityCode.toString());
            }
        };
        etvIdentityCode.addTextChangedListener(etvIdentityCodeTextWatcher);
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
    public void copyClipBoard(String usingIMEI)
    {
        ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", usingIMEI);
        myClipboard.setPrimaryClip(myClip);
        customAlertDialog.showToast(SplashActivity.this , "شناسه دستگاه ("+usingIMEI+") کپی شد" , Constants.INFO_MESSAGE(), Constants.DURATION_SHORT());
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
        openMainActivity();
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

    public void openMainActivity()
    {
            Intent intent = new Intent(SplashActivity.this , MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
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
    public void startLoadingDialog() {
        alertDialog = customLoadingDialog.showLoadingDialog(SplashActivity.this);

    }

    @Override
    public void stopLoadingDialog() {
        if (alertDialog != null) {
            try {
                alertDialog.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void clearInvalidIdentityCode() {
        if (etvIdentityCode!=null){
            etvIdentityCode.getText().clear();
        }
    }

    @Override
    public Context getAppContext()
    {
        return SplashActivity.this;
    }

}
