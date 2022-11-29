package com.saphamrah.customer.presentation.createRequest.verifyRequest.interactor;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.local.temp.BonusModel;
import com.saphamrah.customer.data.local.temp.DiscountModel;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.data.local.temp.ProductModel;

import java.util.List;

public interface VerifyRequestInteractor {

    interface RequiredViewOps extends BaseView {

    }

    interface PresenterOps extends BasePresenterOps {

        void saveData(List<ProductModel> productModels, List<BonusModel> bonusModels, List<DiscountModel> discountModels);

        void saveRequest(List<ProductModel> productModelGlobal, List<DiscountModel> discountModelsGlobal, List<BonusModel> bonusModelsGlobal, List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModelsGlobal);
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {

    }

    abstract class ModelOps  {

        public abstract void saveElamMarjoeeForoshandeh(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels);

        public abstract void saveDiscounts(List<DiscountModel> discountModelsGlobal);

        public abstract void saveBonuses(List<BonusModel> bonusModelsGlobal);

        public abstract void saveProducts(List<ProductModel> orderedProducts);
    }

}
