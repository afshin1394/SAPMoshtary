package com.saphamrah.customer.presentation.presenters.login;

import android.content.Context;

import com.saphamrah.customer.presentation.interactors.login.LoginInteracts;
import com.saphamrah.customer.presentation.models.login.LoginModel;

public class LoginPresenter implements LoginInteracts.PresenterOps, LoginInteracts.RequiredPresenterOps {

    private LoginInteracts.RequiredViewOps view;
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
