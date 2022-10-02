package com.saphamrah.customer.mvp.view.login.models;

import com.saphamrah.customer.mvp.view.login.interactors.VerifyOtpLoginInteracts;

public class VerifyOtpLoginModel implements VerifyOtpLoginInteracts.ModelOps{

    private VerifyOtpLoginInteracts.RequiredPresenterOps verifyOtpLoginRequiredPresenterOps;

    public VerifyOtpLoginModel(VerifyOtpLoginInteracts.RequiredPresenterOps verifyOtpLoginRequiredPresenterOps) {
        this.verifyOtpLoginRequiredPresenterOps = verifyOtpLoginRequiredPresenterOps;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
