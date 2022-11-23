package com.saphamrah.customer.presentation.createRequest.productRequest.interactor;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;

import java.util.List;

public interface ProductRequestMVPInteractor {
    interface RequiredViewOps extends BaseView {


        void onGetDiscountAndBonuses(List<DiscountModel> discountModels, List<BonusModel> bonusModels);
    }

    interface PresenterOps extends BasePresenterOps {
        void getJayezehTakhfifDetails();
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {

    }

    abstract class ModelOps extends BaseModel {

    }
}
