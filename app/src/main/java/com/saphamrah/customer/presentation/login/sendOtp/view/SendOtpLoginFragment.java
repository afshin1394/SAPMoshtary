package com.saphamrah.customer.presentation.login.sendOtp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.databinding.FragmentSendOtpLoginBinding;
import com.saphamrah.customer.presentation.login.sendOtp.interactor.SendOtpLoginInteracts;
import com.saphamrah.customer.presentation.login.sendOtp.presenter.SendOtpLoginPresenter;
import com.saphamrah.customer.utils.customViews.CustomSnackBar;

import java.util.Objects;

public class SendOtpLoginFragment extends BaseFragment<SendOtpLoginPresenter, FragmentSendOtpLoginBinding> implements SendOtpLoginInteracts.RequiredViewOps {


    private String phoneNumber = "";

    @Override
    protected FragmentSendOtpLoginBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSendOtpLoginBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setPresenter() {
        presenter = new SendOtpLoginPresenter(this);
    }


    @Override
    protected void initViews() {

        phoneNumberTextWatcher();

        btnClickListeners();

        handleBackPress();
    }

    @SuppressLint("AutoDispose")
    private void phoneNumberTextWatcher() {
        viewBinding.etPhone.addTextWatcher(s -> {
            if (s != null) {
                viewBinding.btnSendNumber.setEnabled(true);
                // change btn color
                if (viewBinding.etPhone.getText().length() == 9) {
                    checkLogin();

                } else {

                    /*viewBinding.btnSendNumber.setBackground(
                            ContextCompat.getDrawable(
                                    requireActivity(),
                                    R.drawable.button_selector
                            )
                    );*/
                }
            }
        }, 500);

    }

    private void btnClickListeners() {

        viewBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(R.id.action_SendOtpLoginFragment_to_RegisterFragment);
            }
        });

        viewBinding.btnSendNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        try {

            phoneNumber = Objects.requireNonNull(viewBinding.etPhone.getText()).toString();

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
    public void onError(String error) {
        CustomSnackBar.showSnack(requireContext(), getView(), error, Snackbar.LENGTH_INDEFINITE, "dismiss").show();
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


}
