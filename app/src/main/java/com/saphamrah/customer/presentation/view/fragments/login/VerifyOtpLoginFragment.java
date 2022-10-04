package com.saphamrah.customer.presentation.view.fragments.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.base.BasePermissionModel;
import com.saphamrah.customer.presentation.interactors.VerifyOtpLoginInteracts;
import com.saphamrah.customer.listeners.VerifyValidCode;
import com.saphamrah.customer.presentation.presenters.VerifyOtpLoginPresenter;
import com.saphamrah.customer.presentation.view.customView.VerifyCodeTextWatcher;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyOtpLoginFragment extends BaseFragment<VerifyOtpLoginPresenter> implements VerifyOtpLoginInteracts.RequiredViewOps,
        VerifyValidCode {

    public VerifyOtpLoginFragment() {
        super(R.layout.fragment_verify_login);
    }

    private EditText etCode1;
    private EditText etCode2;
    private EditText etCode3;
    private EditText etCode4;

    private Button btnSendCode;
    private Button btnResendCode;
    private Button btnEditNumber;

    private CountDownTimer countDown;

    private Long millisInFuture = 120000L;

    private String phoneNumber = "";


    @Override
    protected void setPresenter() {
        presenter = new VerifyOtpLoginPresenter(this);
    }

    @Override
    protected void initViews() {

        etCode1 = view.findViewById(R.id.et_code1);
        etCode2 = view.findViewById(R.id.et_code2);
        etCode3 = view.findViewById(R.id.et_code3);
        etCode4 = view.findViewById(R.id.et_code4);

        btnSendCode = view.findViewById(R.id.btn_send_code);
        btnResendCode = view.findViewById(R.id.btn_resend_code);
        btnEditNumber = view.findViewById(R.id.btn_edit_number);

        EditText[] edit = {etCode1, etCode2, etCode3, etCode4};

        etCode1.addTextChangedListener(new VerifyCodeTextWatcher(etCode1, edit, this));
        etCode2.addTextChangedListener(new VerifyCodeTextWatcher(etCode2, edit, this));
        etCode3.addTextChangedListener(new VerifyCodeTextWatcher(etCode3, edit, this));
        etCode4.addTextChangedListener(new VerifyCodeTextWatcher(etCode4, edit, this));

        btnClickListeners();

        handleBackPress();

        countDownTimer();

    }

    private void btnClickListeners() {

        String codeNumber1 = etCode1.getText().toString();
        String codeNumber2 = etCode2.getText().toString();
        String codeNumber3 = etCode3.getText().toString();
        String codeNumber4 = etCode4.getText().toString();

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeNumber1.length() +
                        codeNumber2.length() +
                        codeNumber3.length() +
                        codeNumber4.length() == 4) {

                    presenter.verifyOtp(phoneNumber, codeNumber1 + codeNumber2 + codeNumber3 + codeNumber4);

                } else {

                    onError(getString(R.string.pleaseEnterCodeCorrectly));
                }
            }
        });

        btnEditNumber.setOnClickListener(new View.OnClickListener() {
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
            }


        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    private void countDownTimer() {
        countDown = new CountDownTimer(millisInFuture, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                btnResendCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countDown.cancel();
                        countDown.start();
                    }
                });

                btnResendCode.setBackground( ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.button_selector
                ));

                if ((millisUntilFinished / 1000) % 60 < 10) {


                    btnResendCode.setText(getString(R.string.string_resendcodeuntil) + " " + String.format(getString(
                                    R.string.string_time_format_zero
                            ),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                } else {
                    btnResendCode.setText(getString(R.string.string_resendcodeuntil) + " " + String.format(getString(
                                    R.string.string_time_format
                            ),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                }
            }

            @Override
            public void onFinish() {
                btnResendCode.setText(getString(R.string.string_resend_code));

                btnResendCode.setBackground(
                    ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.button_selector_blue
                    ));

                btnResendCode.setOnClickListener(new View.OnClickListener() {
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
        etCode1.setText(messageText.substring(0,1));
        etCode2.setText(messageText.substring(1,2));
        etCode3.setText(messageText.substring(2,3));
        etCode4.setText(messageText.substring(3,4));
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

    }

    @Override
    public void onVerifyOtp() {

    }

    @Override
    public void onVerifyValidCode() {
        String codeNumber1 = etCode1.getText().toString();
        String codeNumber2 = etCode2.getText().toString();
        String codeNumber3 = etCode3.getText().toString();
        String codeNumber4 = etCode4.getText().toString();

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
