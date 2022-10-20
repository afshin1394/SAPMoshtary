package com.saphamrah.customer.createRequest.presenter;

import android.content.Context;

import com.saphamrah.customer.createRequest.interactor.FilterMVPInteracts;
import com.saphamrah.customer.createRequest.model.FilterMVPModel;

public class FilterMVPPresenter implements FilterMVPInteracts.PresenterOps, FilterMVPInteracts.RequiredPresenterOps{
    private FilterMVPInteracts.RequiredViewOps view;
    private final FilterMVPModel model;

    public FilterMVPPresenter(FilterMVPInteracts.RequiredViewOps view, FilterMVPModel model) {
        this.view = view;
        this.model = new FilterMVPModel(this);
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
