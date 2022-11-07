package com.saphamrah.customer.presentation.createRequest.addItemtoCart.model;

import com.saphamrah.customer.presentation.createRequest.addItemtoCart.interactor.AddItemToCartInteractor;

public class AddItemToCartModel extends AddItemToCartInteractor.ModelOps{
    private AddItemToCartInteractor.RequiredPresenterOps filterMvpRequiredPresenterOps;

    public AddItemToCartModel(AddItemToCartInteractor.RequiredPresenterOps filterMVPInteractsRequiredPresenterOps) {
        this.filterMvpRequiredPresenterOps = filterMVPInteractsRequiredPresenterOps;
    }
    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
