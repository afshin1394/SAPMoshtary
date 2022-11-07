package com.saphamrah.customer.presentation.createRequest.productRequest.interactor;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;

public interface ProductRequestMVPInteractor {
    interface RequiredViewOps extends BaseView {

    }

    interface PresenterOps extends BasePresenterOps {
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {

    }

    abstract class ModelOps extends BaseModel {

    }
}
