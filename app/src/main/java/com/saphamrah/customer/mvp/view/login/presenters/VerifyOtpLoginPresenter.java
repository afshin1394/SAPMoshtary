package com.saphamrah.customer.mvp.view.login.presenters;

import android.content.Context;

import com.saphamrah.customer.mvp.view.login.interactors.VerifyOtpLoginInteracts;
import com.saphamrah.customer.mvp.view.login.models.VerifyOtpLoginModel;

public class VerifyOtpLoginPresenter implements VerifyOtpLoginInteracts.PresenterOps, VerifyOtpLoginInteracts.RequiredPresenterOps{

    private VerifyOtpLoginInteracts.RequiredViewOps view;
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
    public Context getContext() {
        return view.getAppContext();
    }
}
