package com.saphamrah.customer.createRequest.model;

import com.saphamrah.customer.createRequest.interactor.FilterMVPInteracts;

public class FilterMVPModel extends FilterMVPInteracts.ModelOps{
    private FilterMVPInteracts.RequiredPresenterOps filterMvpRequiredPresenterOps;

    public FilterMVPModel(FilterMVPInteracts.RequiredPresenterOps filterMVPInteractsRequiredPresenterOps) {
        this.filterMvpRequiredPresenterOps = filterMVPInteractsRequiredPresenterOps;
    }
    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
