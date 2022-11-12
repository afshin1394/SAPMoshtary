package com.saphamrah.customer.presentation.createRequest.marjoee.model;

import com.saphamrah.customer.presentation.createRequest.filter.interactor.FilterMVPInteractor;
import com.saphamrah.customer.presentation.createRequest.marjoee.interactor.MarjoeeInteractor;

public class MarjoeeModel extends FilterMVPInteractor.ModelOps{
    private MarjoeeInteractor.RequiredPresenterOps marjoeeModel;


    public MarjoeeModel(MarjoeeInteractor.RequiredPresenterOps marjoeeModel) {
        this.marjoeeModel = marjoeeModel;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
