package com.saphamrah.customer.presentation.createRequest.addItemtoCart.presenter;

import android.content.Context;

import com.saphamrah.customer.presentation.createRequest.addItemtoCart.interactor.AddItemToCartInteractor;
import com.saphamrah.customer.presentation.createRequest.addItemtoCart.model.AddItemToCartModel;
import com.saphamrah.customer.presentation.createRequest.filter.model.FilterMVPModel;

public class AddItemToCartPresenter implements AddItemToCartInteractor.PresenterOps, AddItemToCartInteractor.RequiredPresenterOps{
    private AddItemToCartInteractor.RequiredViewOps view;
    private final AddItemToCartModel model;

    public AddItemToCartPresenter(AddItemToCartInteractor.RequiredViewOps view, FilterMVPModel model) {
        this.view = view;
        this.model = new AddItemToCartModel(this);
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
