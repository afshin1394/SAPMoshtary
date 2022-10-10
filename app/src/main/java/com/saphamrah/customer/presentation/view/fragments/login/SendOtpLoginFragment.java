package com.saphamrah.customer.presentation.view.fragments.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.base.BasePermissionModel;
import com.saphamrah.customer.databinding.FragmentSendOtpLoginBinding;
import com.saphamrah.customer.presentation.interactors.SendOtpLoginInteracts;
import com.saphamrah.customer.presentation.presenters.SendOtpLoginPresenter;
import com.saphamrah.customer.presentation.view.customView.ShowSnackBar;
import com.saphamrah.customer.utils.RxTextWatcher;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SendOtpLoginFragment extends BaseFragment<SendOtpLoginPresenter, FragmentSendOtpLoginBinding> implements SendOtpLoginInteracts.RequiredViewOps {

    public SendOtpLoginFragment() {
        super(R.layout.fragment_send_otp_login);
    }

    private String phoneNumber = "";

    private RxTextWatcher rxTextWatcher;



    @Override
    protected void setPresenter() {
        presenter = new SendOtpLoginPresenter(this);
    }

    @Override
    protected FragmentSendOtpLoginBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSendOtpLoginBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {


        rxTextWatcher = new RxTextWatcher();
        compositeDisposable = new CompositeDisposable();

        phoneNumberTextWatcher();

        btnClickListeners();

        handleBackPress();
    }

    @SuppressLint("AutoDispose")
    private void phoneNumberTextWatcher() {
        rxTextWatcher.onTextChanged(viewBinding.etPhone)
                .debounce(700, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String s) {
                        if (s != null) {
                            viewBinding.btnSendNumber.setEnabled(true);

                            // change btn color
                            if (viewBinding.etPhone.getText().length() == 9) {
                                checkLogin();

                            } else {

                                viewBinding.btnSendNumber.setBackground(
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

                    }
                });

    }

    private void btnClickListeners() {

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
    protected void onPermission(ArrayList<BasePermissionModel> basePermissionModels) {

    }

    @Override
    public void onError(String error) {
        notifyUser(error);
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

    private void notifyUser(String message) {
       /* LayoutInflater mInflater = LayoutInflater.from(requireView().getContext());
        View snackView = mInflater.inflate(R.layout.snackbar_view, null);
        ShowSnackBar.showSnack(snackView, message, 1).show();*/
    }

}
