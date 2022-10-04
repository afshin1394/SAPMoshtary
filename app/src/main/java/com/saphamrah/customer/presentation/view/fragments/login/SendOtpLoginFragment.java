package com.saphamrah.customer.presentation.view.fragments.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.base.BasePermissionModel;
import com.saphamrah.customer.presentation.interactors.SendOtpLoginInteracts;
import com.saphamrah.customer.presentation.presenters.SendOtpLoginPresenter;
import com.saphamrah.customer.presentation.view.activities.MainActivity;
import com.saphamrah.customer.presentation.view.customView.ShowSnackBar;
import com.saphamrah.customer.utils.RxTextWatcher;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SendOtpLoginFragment extends BaseFragment<SendOtpLoginPresenter> implements SendOtpLoginInteracts.RequiredViewOps {

    public SendOtpLoginFragment() {
        super(R.layout.fragment_send_otp_login);
    }


    private EditText etPhone;

    private Button btnSendNumber;

    private String phoneNumber = "";

    private CompositeDisposable compositeDisposable;

    private RxTextWatcher rxTextWatcher;


    @Override
    protected void setPresenter() {
        presenter = new SendOtpLoginPresenter(this);
    }

    @Override
    protected void initViews() {

        etPhone = view.findViewById(R.id.et_phone);
        btnSendNumber = view.findViewById(R.id.btn_send_number);

        rxTextWatcher = new RxTextWatcher();
        compositeDisposable = new CompositeDisposable();

        phoneNumberTextWatcher();

        btnClickListeners();

        handleBackPress();
    }

    @SuppressLint("AutoDispose")
    private void phoneNumberTextWatcher() {
        rxTextWatcher.onTextChanged(etPhone)
                .debounce(700, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        if (s != null) {
                            btnSendNumber.setEnabled(true);

                            // change btn color
                            if (etPhone.getText().length() == 9) {
                                checkLogin();

                            } else {

                                btnSendNumber.setBackground(
                                        ContextCompat.getDrawable(
                                                requireActivity(),
                                                R.drawable.button_selector
                                        )
                                );
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.clear();
                    }
                });

    }

    private void btnClickListeners() {

        btnSendNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        try {

            phoneNumber = etPhone.getText().toString();

            if (phoneNumber.length() == 9) {
                presenter.sendMobile("09" + phoneNumber);

            } else {
                onError(getString(R.string.pleaseEnterPhoneNumberCorrectly));


            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private void handleBackPress() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }


    @Override
    protected void onPermission(ArrayList<BasePermissionModel> basePermissionModels) {

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
        return getContext();
    }

    @Override
    public void onSendMobile() {

        navigate(R.id.action_SendOtpLoginFragment_to_VerifyOtpLoginFragment);
/*
        if (!prefs.isLogin) {
            navigate(R.id.action_SendOtpLoginFragment_to_VerifyOtpLoginFragment)

        } else {

            *//* startActivity(new Intent(this, MainActivity.class));
             finish()*//*
        }*/
    }

   /* // it checks whether the app the app has fingerprint permission
    @RequiresApi(Build.VERSION_CODES.M)
    private Boolean checkBiometricSupport() {
        KeyguardManager keyguardManager =
                (KeyguardManager) requireActivity().getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            notifyUser(getString(R.string.fingerprintAuthenticationHasNotBeenEnabledInSettings));
            return false;
        }
        if (
            ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.USE_BIOMETRIC
                            )!= PackageManager.PERMISSION_GRANTED)
         {
            notifyUser(getString(R.string.fingerprintAuthenticationPermissionIsNotEnabled));
            return false;
        }

        if (requireContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
           return true;
         else
             return true;
    }*/

   /* private CancellationSignal getCancellationSignal() {
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(() -> notifyUser(getString(R.string.authenticationWasCancelledByTheUser)));
        return cancellationSignal;
    }*/


   /* private void notifyUser(String message) {
        LayoutInflater mInflater = LayoutInflater.from(view.getContext());
        View snackView = mInflater.inflate(R.layout.snackbar_view, null);
        ShowSnackBar.showSnack(snackView, message, 1).show();
    }*/

}
