package com.saphamrah.customer.presentation.login;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;
import com.saphamrah.customer.data.local.db.entity.Bank;

import java.util.List;

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
