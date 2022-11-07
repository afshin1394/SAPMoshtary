package com.saphamrah.customer.presentation.createRequest.cart.model;

import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.presentation.createRequest.cart.interactor.CartInteractor;

import java.util.List;

public class CartModel extends CartInteractor.ModelOps {
    private CartInteractor.RequiredPresenterOps cartRequiredPresenterOps;

    public CartModel(CartInteractor.RequiredPresenterOps cartRequiredPresenterOps) {
        this.cartRequiredPresenterOps = cartRequiredPresenterOps;
    }

    @Override
    public void setLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }
    

    @Override
    public void onDestroy() {

    }


    public void saveData(List<ProductModel> productModels, List<BonusModel> bonusModels, List<DiscountModel> discountModels) {

    }
}
