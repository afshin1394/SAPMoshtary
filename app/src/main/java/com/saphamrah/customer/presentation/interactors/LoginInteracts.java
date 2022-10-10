package com.saphamrah.customer.presentation.interactors;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;

public interface LoginInteracts {

    interface RequiredViewOps extends BaseView {

    }

    interface PresenterOps extends BasePresenterOps {

    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {

    }

    abstract class ModelOps extends BaseModel {

    }

}
