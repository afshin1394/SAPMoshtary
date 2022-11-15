package com.saphamrah.customer.presentation.createRequest.verifyRequest.interactor;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ProductModel;

import java.util.List;

public interface VerifyRequestInteractor {

    interface RequiredViewOps extends BaseView {

    }

    interface PresenterOps extends BasePresenterOps {

        void saveData(List<ProductModel> productModels, List<BonusModel> bonusModels, List<DiscountModel> discountModels);
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {

    }

    abstract class ModelOps extends BaseModel {
        protected abstract void saveData(List<ProductModel> productModels, List<BonusModel> bonusModels, List<DiscountModel> discountModels);
    }

}
