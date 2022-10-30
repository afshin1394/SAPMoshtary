package com.saphamrah.customer.presentation.interactors.profile;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;

public interface ProfileInteracts  {
    interface RequiredViewOps extends BaseView {

    }

    interface PresenterOps extends BasePresenterOps {

    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {

    }

    abstract class ModelOps extends BaseModel {

    }
}
