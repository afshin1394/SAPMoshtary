package com.saphamrah.customer.presentation.models.login;

import com.saphamrah.customer.presentation.interactors.login.LoginInteracts;

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
