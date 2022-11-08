package com.saphamrah.customer.presentation.login.verifyOtp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.databinding.FragmentVerifyLoginBinding;
import com.saphamrah.customer.presentation.login.LoginActivity;
import com.saphamrah.customer.presentation.login.verifyOtp.interactor.VerifyOtpLoginInteracts;
import com.saphamrah.customer.listeners.VerifyValidCode;
import com.saphamrah.customer.presentation.login.verifyOtp.presenter.VerifyOtpLoginPresenter;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.utils.customViews.CustomSnackBar;
import com.saphamrah.customer.utils.customViews.VerifyCodeTextWatcher;

import java.util.concurrent.TimeUnit;

public class VerifyOtpLoginFragment extends BaseFragment<VerifyOtpLoginPresenter, FragmentVerifyLoginBinding> implements VerifyOtpLoginInteracts.RequiredViewOps,
        VerifyValidCode {

    private CountDownTimer countDown;

    private Long millisInFuture = 120000L;

    private String phoneNumber = "";

    public VerifyOtpLoginFragment() {
        super(R.layout.fragment_verify_login);
    }

    @Override
    protected FragmentVerifyLoginBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentVerifyLoginBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setPresenter() {
        presenter = new VerifyOtpLoginPresenter(this);
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {

        EditText[] edit = {viewBinding.etCode1, viewBinding.etCode2, viewBinding.etCode3, viewBinding.etCode4};

        viewBinding.etCode1.addTextChangedListener(new VerifyCodeTextWatcher(viewBinding.etCode1, edit, this));
        viewBinding.etCode2.addTextChangedListener(new VerifyCodeTextWatcher(viewBinding.etCode2, edit, this));
        viewBinding.etCode3.addTextChangedListener(new VerifyCodeTextWatcher(viewBinding.etCode3, edit, this));
        viewBinding.etCode4.addTextChangedListener(new VerifyCodeTextWatcher(viewBinding.etCode4, edit, this));

        btnClickListeners();

        handleBackPress();

        countDownTimer();

    }

    private void btnClickListeners() {

        String codeNumber1 = viewBinding.etCode1.getText().toString();
        String codeNumber2 = viewBinding.etCode2.getText().toString();
        String codeNumber3 = viewBinding.etCode3.getText().toString();
        String codeNumber4 = viewBinding.etCode4.getText().toString();

        viewBinding.btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, MainActivity.class));

                if (codeNumber1.length() +
                        codeNumber2.length() +
                        codeNumber3.length() +
                        codeNumber4.length() == 4) {

//                    presenter.verifyOtp(phoneNumber, codeNumber1 + codeNumber2 + codeNumber3 + codeNumber4);

                } else {

                    onError(getString(R.string.pleaseEnterCodeCorrectly));
                }
            }
        });

        viewBinding.btnEditNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(R.id.action_VerifyOtpLoginFragment_to_SendOtpLoginFragment);

            }
        });

    }

    private void handleBackPress() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (countDown != null) {
                    countDown.cancel();
                }
                navigate(R.id.action_VerifyOtpLoginFragment_to_SendOtpLoginFragment);

            }


        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    private void countDownTimer() {
        countDown = new CountDownTimer(millisInFuture, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                viewBinding.btnResendCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countDown.cancel();
                        countDown.start();
                    }
                });

                viewBinding.btnResendCode.setBackground( ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.button_selector
                ));

                if ((millisUntilFinished / 1000) % 60 < 10) {


                    viewBinding.btnResendCode.setText(getString(R.string.string_resendcodeuntil) + " " + String.format(getString(
                                    R.string.string_time_format_zero
                            ),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                } else {
                    viewBinding.btnResendCode.setText(getString(R.string.string_resendcodeuntil) + " " + String.format(getString(
                                    R.string.string_time_format
                            ),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                }
            }

            @Override
            public void onFinish() {
                viewBinding.btnResendCode.setText(getString(R.string.string_resend_code));

                viewBinding.btnResendCode.setBackground(
                    ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.button_selector_blue
                    ));

                viewBinding.btnResendCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (countDown != null) {
                            countDown.start();
                        }
                        presenter.sendMobile(phoneNumber);
                    }
                });
            }
        }.start();
    }

    void setTextForEtCode(String messageText) {
        viewBinding.etCode1.setText(messageText.substring(0,1));
        viewBinding.etCode2.setText(messageText.substring(1,2));
        viewBinding.etCode3.setText(messageText.substring(2,3));
        viewBinding.etCode4.setText(messageText.substring(3,4));
    }

    @Override
    public void onError(String error) {
        CustomSnackBar.showSnack(requireContext(), getView(), error, Snackbar.LENGTH_SHORT, "retry")
                .show();
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

    }

    @Override
    public void onVerifyOtp() {

    }

    @Override
    public void onVerifyValidCode() {
        String codeNumber1 = viewBinding.etCode1.getText().toString();
        String codeNumber2 = viewBinding.etCode2.getText().toString();
        String codeNumber3 = viewBinding.etCode3.getText().toString();
        String codeNumber4 = viewBinding.etCode4.getText().toString();

        presenter.verifyOtp(phoneNumber, codeNumber1 + codeNumber2 + codeNumber3 + codeNumber4);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDown != null) {
            countDown.cancel();
        }
    }
}
