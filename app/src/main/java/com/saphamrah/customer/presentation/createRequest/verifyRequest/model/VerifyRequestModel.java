package com.saphamrah.customer.presentation.createRequest.verifyRequest.model;

import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.data.local.temp.ProductModel;
import com.saphamrah.customer.presentation.createRequest.selectableBonus.interactor.SelectableBonusInteractor;
import com.saphamrah.customer.presentation.createRequest.verifyRequest.interactor.VerifyRequestInteractor;

import java.util.List;

public class VerifyRequestModel extends VerifyRequestInteractor.ModelOps {

    private VerifyRequestInteractor.RequiredPresenterOps view;

    public VerifyRequestModel(VerifyRequestInteractor.RequiredPresenterOps view) {
        this.view = view;
    }





    @Override
    public void saveElamMarjoeeForoshandeh(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {

    }

    @Override
    public void saveDiscounts(List<DiscountModel> discountModelsGlobal) {

    }

    @Override
    public void saveBonuses(List<BonusModel> bonusModelsGlobal) {

    }

    @Override
    public void saveProducts(List<ProductModel> orderedProducts) {

    }


}
