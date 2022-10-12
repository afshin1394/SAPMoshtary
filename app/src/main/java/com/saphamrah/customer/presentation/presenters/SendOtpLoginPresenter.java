package com.saphamrah.customer.presentation.presenters;

import android.content.Context;

import com.saphamrah.customer.presentation.interactors.SendOtpLoginInteracts;
import com.saphamrah.customer.presentation.models.SendOtpLoginModel;

public class SendOtpLoginPresenter implements SendOtpLoginInteracts.PresenterOps, SendOtpLoginInteracts.RequiredPresenterOps{

    private final SendOtpLoginInteracts.RequiredViewOps view;
    private final SendOtpLoginModel model;

    public SendOtpLoginPresenter(SendOtpLoginInteracts.RequiredViewOps view) {
        this.view = view;
        model = new SendOtpLoginModel(this);
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        model.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);

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
    public void onSendMobile() {
        view.onSendMobile();
    }
}
