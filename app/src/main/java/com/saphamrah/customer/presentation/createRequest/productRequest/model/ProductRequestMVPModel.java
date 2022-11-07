package com.saphamrah.customer.presentation.createRequest.productRequest.model;

import com.saphamrah.customer.presentation.createRequest.productRequest.interactor.ProductRequestMVPInteractor;

public class ProductRequestMVPModel extends ProductRequestMVPInteractor.ModelOps {
    private ProductRequestMVPInteractor.RequiredPresenterOps productRequestMVPRequiredPresenterOps;

    public ProductRequestMVPModel(ProductRequestMVPInteractor.RequiredPresenterOps productRequestMVPRequiredPresenterOps) {
        this.productRequestMVPRequiredPresenterOps = productRequestMVPRequiredPresenterOps;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
