package com.saphamrah.customer.presentation.createRequest.verifyRequest.model;

import com.saphamrah.customer.presentation.createRequest.selectableBonus.interactor.SelectableBonusInteractor;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.interactor.VerifyRequestInteractor;

public class VerifyRequestModel extends VerifyRequestInteractor.ModelOps {

    private VerifyRequestInteractor.RequiredPresenterOps view;

    public VerifyRequestModel(VerifyRequestInteractor.RequiredPresenterOps view) {
        this.view = view;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
