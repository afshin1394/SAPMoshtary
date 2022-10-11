package com.saphamrah.customer.presentation.presenters;

import android.content.Context;

import com.saphamrah.customer.presentation.interactors.RegisterInteracts;
import com.saphamrah.customer.presentation.models.RegisterModel;

public class RegisterPresenter implements RegisterInteracts.PresenterOps, RegisterInteracts.RequiredPresenterOps {

    private RegisterInteracts.RequiredViewOps view;
    private final RegisterModel model;

    public RegisterPresenter(RegisterInteracts.RequiredViewOps view) {
        this.view = view;
        model = new RegisterModel(this);
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
