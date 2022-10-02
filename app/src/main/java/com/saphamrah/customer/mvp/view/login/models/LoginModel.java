package com.saphamrah.customer.mvp.view.login.models;

import com.saphamrah.customer.mvp.view.login.interactors.LoginInteracts;

public class LoginModel implements LoginInteracts.ModelOps {

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
