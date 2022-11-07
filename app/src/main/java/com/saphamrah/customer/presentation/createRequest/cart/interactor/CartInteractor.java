package com.saphamrah.customer.presentation.createRequest.cart.interactor;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ProductModel;

import java.util.List;

public interface CartInteractor {
    interface RequiredViewOps extends BaseView {
       void onGetDiscountAndBonuses(List<DiscountModel> discountModels , List<BonusModel> bonusModels);
    }

    interface PresenterOps extends BasePresenterOps {

        void getDiscountAndBonuses();
        void saveData(List<ProductModel> productModels, List<BonusModel> bonusModels, List<DiscountModel> discountModels);
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {
        void saveData(List<ProductModel> productModels, List<BonusModel> bonusModels, List<DiscountModel> discountModels);

    }

    abstract class ModelOps extends BaseModel {

    }
}
