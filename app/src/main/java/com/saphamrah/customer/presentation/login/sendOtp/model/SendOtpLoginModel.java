package com.saphamrah.customer.presentation.login.sendOtp.model;

import com.saphamrah.customer.presentation.login.sendOtp.interactor.SendOtpLoginInteracts;

public class SendOtpLoginModel extends SendOtpLoginInteracts.ModelOps {

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
