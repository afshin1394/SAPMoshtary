package com.saphamrah.customer.presentation.createRequest.productRequest.presenter;

import android.content.Context;

import com.saphamrah.customer.presentation.createRequest.productRequest.interactor.ProductRequestMVPInteractor;
import com.saphamrah.customer.presentation.createRequest.productRequest.model.ProductRequestMVPModel;

public class ProductRequestMVPPresenter implements ProductRequestMVPInteractor.PresenterOps, ProductRequestMVPInteractor.RequiredPresenterOps{
    private ProductRequestMVPInteractor.RequiredViewOps view;
    private  ProductRequestMVPModel model;

    public ProductRequestMVPPresenter(ProductRequestMVPInteractor.RequiredViewOps view, ProductRequestMVPModel model) {
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
