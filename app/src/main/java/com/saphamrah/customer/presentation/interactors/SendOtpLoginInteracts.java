package com.saphamrah.customer.presentation.interactors;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;

public interface SendOtpLoginInteracts {

    interface RequiredViewOps extends BaseView {
        void onSendMobile();

    }

    interface PresenterOps extends BasePresenterOps {
        void sendMobile(String mobile);

    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {
        void onSendMobile();

    }

    abstract class ModelOps extends BaseModel {
        protected abstract void sendMobile(String mobile);

    }

}
