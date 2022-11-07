package com.saphamrah.customer.presentation.login;

import android.content.Context;

public class LoginPresenter implements LoginInteracts.PresenterOps, LoginInteracts.RequiredPresenterOps {

    private final LoginInteracts.RequiredViewOps view;
    private final LoginModel model;

    public LoginPresenter(LoginInteracts.RequiredViewOps view) {
        this.view = view;
        model = new LoginModel(this);
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
