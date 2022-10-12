package com.saphamrah.customer.presentation.presenters;

import android.content.Context;

import com.saphamrah.customer.presentation.interactors.LoginInteracts;
import com.saphamrah.customer.presentation.models.LoginModel;

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
