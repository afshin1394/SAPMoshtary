package com.saphamrah.customer.createRequest.presenter;

import android.content.Context;

import com.saphamrah.customer.createRequest.interactor.FilterMVPInteracts;
import com.saphamrah.customer.createRequest.interactor.ProductRequestMVPInteracts;
import com.saphamrah.customer.createRequest.model.FilterMVPModel;
import com.saphamrah.customer.createRequest.model.ProductRequestMVPModel;

public class ProductRequestMVPPresenter implements ProductRequestMVPInteracts.PresenterOps, ProductRequestMVPInteracts.RequiredPresenterOps{
    private ProductRequestMVPInteracts.RequiredViewOps view;
    private final ProductRequestMVPModel model;

    public ProductRequestMVPPresenter(ProductRequestMVPInteracts.RequiredViewOps view, ProductRequestMVPModel model) {
        this.view = view;
        this.model = new ProductRequestMVPModel(this);
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
