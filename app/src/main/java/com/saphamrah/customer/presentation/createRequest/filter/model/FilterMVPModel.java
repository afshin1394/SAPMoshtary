package com.saphamrah.customer.presentation.createRequest.filter.model;

import com.saphamrah.customer.presentation.createRequest.filter.interactor.FilterMVPInteractor;

public class FilterMVPModel extends FilterMVPInteractor.ModelOps{
    private FilterMVPInteractor.RequiredPresenterOps filterMvpRequiredPresenterOps;

    public FilterMVPModel(FilterMVPInteractor.RequiredPresenterOps filterMVPInteractsRequiredPresenterOps) {
        this.filterMvpRequiredPresenterOps = filterMVPInteractsRequiredPresenterOps;
    }
    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
