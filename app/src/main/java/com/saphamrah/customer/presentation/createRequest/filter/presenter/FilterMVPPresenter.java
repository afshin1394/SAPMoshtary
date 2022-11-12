package com.saphamrah.customer.presentation.createRequest.filter.presenter;

import android.content.Context;

import com.saphamrah.customer.presentation.createRequest.filter.interactor.FilterMVPInteractor;
import com.saphamrah.customer.presentation.createRequest.filter.model.FilterMVPModel;

public class FilterMVPPresenter implements FilterMVPInteractor.PresenterOps, FilterMVPInteractor.RequiredPresenterOps{
    private FilterMVPInteractor.RequiredViewOps view;
    private final FilterMVPModel model;

    public FilterMVPPresenter(FilterMVPInteractor.RequiredViewOps view) {
        this.view = view;
        this.model = new FilterMVPModel(this);
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        model.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public Context getContext() {
        return view.getAppContext();
    }


}
