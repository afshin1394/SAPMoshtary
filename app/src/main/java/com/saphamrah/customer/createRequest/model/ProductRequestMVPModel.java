package com.saphamrah.customer.createRequest.model;

import com.saphamrah.customer.createRequest.interactor.FilterMVPInteracts;
import com.saphamrah.customer.createRequest.interactor.ProductRequestMVPInteracts;

public class ProductRequestMVPModel extends ProductRequestMVPInteracts.ModelOps{
    private ProductRequestMVPInteracts.RequiredPresenterOps ProductRequestMVPRequiredPresenterOps;

    public ProductRequestMVPModel(ProductRequestMVPInteracts.RequiredPresenterOps productRequestMVPRequiredPresenterOps) {
        ProductRequestMVPRequiredPresenterOps = productRequestMVPRequiredPresenterOps;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }
}
