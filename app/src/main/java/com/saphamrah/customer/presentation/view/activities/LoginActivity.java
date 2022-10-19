package com.saphamrah.customer.presentation.view.activities;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseActivity;
import com.saphamrah.customer.Application;
import com.saphamrah.customer.base.BasePermissionModel;
import com.saphamrah.customer.databinding.ActivityLoginBinding;
import com.saphamrah.customer.listeners.SmsListener;
import com.saphamrah.customer.presentation.interactors.LoginInteracts;
import com.saphamrah.customer.presentation.presenters.LoginPresenter;
import com.saphamrah.customer.presentation.view.customView.ShowSnackBar;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class LoginActivity extends BaseActivity<LoginInteracts.PresenterOps, ActivityLoginBinding> implements LoginInteracts.RequiredViewOps, SmsListener {


    @Override
    protected ActivityLoginBinding inflateBiding(LayoutInflater inflater) {
        return ActivityLoginBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        setActivityPresenter(new LoginPresenter(this));

        checkPermissions(new String[]{Manifest.permission.RECEIVE_SMS});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            checkFingerPrintLogin();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void checkFingerPrintLogin() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkBiometricSupport();
        }

        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt
                .Builder(this)
                .setTitle("Biometric Authentication")
                .setSubtitle("Please authenticate to continue")
                .setDescription("Fingerprinting in biometric ")
                .setNegativeButton("Cancel", executor,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface
                                                        dialogInterface, int i) {
                                                      /*  tvstatus.setText(tvstatus.getText() +
                                                                        "nnStep 2: Fingerprint authentication
                                                                cancelled.");*/
                            }
                        })
                .build();

        // Authenticate with callback functions

        executor.execute(() -> biometricPrompt.authenticate(getCancellationSignal(),
                executor, getAuthenticationCallback()));


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private Boolean checkBiometricSupport() {
        KeyguardManager keyguardManager =
                (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            notifyUser(getString(R.string.fingerprintAuthenticationHasNotBeenEnabledInSettings));
            return false;
        }
        if (
                ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.USE_BIOMETRIC
                )!= PackageManager.PERMISSION_GRANTED)
        {
            notifyUser(getString(R.string.fingerprintAuthenticationPermissionIsNotEnabled));
            return false;
        }

        return true;
    }


    private CancellationSignal getCancellationSignal() {
        CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(() ->
                notifyUser(getString(R.string.authenticationWasCancelledByTheUser)));

        return cancellationSignal;
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    private BiometricPrompt.AuthenticationCallback
    getAuthenticationCallback() {
        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              CharSequence errString) {
               /* tvstatus.setText(tvstatus.getText() + "nnStep 2:
                        Fingerprint authentication error: " + errString);*/
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode,
                                             CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }

            @Override
            public void onAuthenticationSucceeded(
                    BiometricPrompt.AuthenticationResult result) {
               /* tvstatus.setText(tvstatus.getText() + "nnStep 2:
                        Fingerprint authentication Succeeded.");*/

                super.onAuthenticationSucceeded(result);
            }
        };
    }


    @Override
    public void onError(String error) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public Context getAppContext() {
        return LoginActivity.this;
    }



    @Override
    public void onPermission(ArrayList<BasePermissionModel> basePermissionModels) {
        ((Application) getApplication()).setSmsListener(this);

    }

    @Override
    public void messageReceived(String messageText) {

    }

    private void notifyUser(String message) {

       /* LayoutInflater mInflater = LayoutInflater.from(this);
        View snackView = mInflater.inflate(R.layout.snackbar_view, null);
        ShowSnackBar.showSnack(snackView, message, 1).show();*/
    }
}