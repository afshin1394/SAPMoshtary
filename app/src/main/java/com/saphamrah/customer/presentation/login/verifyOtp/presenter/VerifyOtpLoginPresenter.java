package com.saphamrah.customer.presentation.login.verifyOtp.presenter;

import android.content.Context;

import com.saphamrah.customer.presentation.login.verifyOtp.interactor.VerifyOtpLoginInteracts;
import com.saphamrah.customer.presentation.login.verifyOtp.model.VerifyOtpLoginModel;

public class VerifyOtpLoginPresenter implements VerifyOtpLoginInteracts.PresenterOps, VerifyOtpLoginInteracts.RequiredPresenterOps{

    private final VerifyOtpLoginInteracts.RequiredViewOps view;
    private final VerifyOtpLoginModel model;

    public VerifyOtpLoginPresenter(VerifyOtpLoginInteracts.RequiredViewOps view) {
        this.view = view;
        model = new VerifyOtpLoginModel(this);
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        model.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);

    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public Context getContext() {
        return view.getAppContext();
    }

    @Override
    public void sendMobile(String mobile) {
        view.onSendMobile();
    }

    @Override
    public void verifyOtp(String mobile, String code) {
        view.showLoading("لطفا کمی صبر نمایید");

    }
}
