package com.saphamrah.customer.presentation.models;

import com.saphamrah.customer.presentation.interactors.LoginInteracts;

public class LoginModel extends LoginInteracts.ModelOps {

    private LoginInteracts.RequiredPresenterOps loginRequiredPresenterOps;

    public LoginModel(LoginInteracts.RequiredPresenterOps loginRequiredPresenterOps) {
        this.loginRequiredPresenterOps = loginRequiredPresenterOps;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
