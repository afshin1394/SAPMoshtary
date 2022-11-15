package com.saphamrah.customer.presentation.createRequest.verifyRequest.presenter;

import android.content.Context;

import com.saphamrah.customer.presentation.createRequest.verifyRequest.interactor.VerifyRequestInteractor;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.model.VerifyRequestModel;

import java.lang.ref.WeakReference;

public class VerifyRequestPresenter implements VerifyRequestInteractor.PresenterOps, VerifyRequestInteractor.RequiredPresenterOps {

    private WeakReference<VerifyRequestInteractor.RequiredViewOps> view;
    private VerifyRequestModel model;

    public VerifyRequestPresenter(VerifyRequestInteractor.RequiredViewOps view) {
        this.view = new WeakReference<>(view);
        model = new VerifyRequestModel(this);
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
