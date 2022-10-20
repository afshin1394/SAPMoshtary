package com.saphamrah.customer.presentation.models.login;

import com.saphamrah.customer.presentation.interactors.login.VerifyOtpLoginInteracts;

public class VerifyOtpLoginModel extends VerifyOtpLoginInteracts.ModelOps {

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
