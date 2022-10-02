package com.saphamrah.customer.mvp.view.login.models;

import com.saphamrah.customer.mvp.view.login.interactors.SendOtpLoginInteracts;

public class SendOtpLoginModel implements SendOtpLoginInteracts.ModelOps{

    private SendOtpLoginInteracts.RequiredPresenterOps sendOtpLoginRequiredPresenterOps;

    public SendOtpLoginModel(SendOtpLoginInteracts.RequiredPresenterOps sendOtpLoginRequiredPresenterOps) {
        this.sendOtpLoginRequiredPresenterOps = sendOtpLoginRequiredPresenterOps;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void sendMobile(String mobile) {

    }
}
