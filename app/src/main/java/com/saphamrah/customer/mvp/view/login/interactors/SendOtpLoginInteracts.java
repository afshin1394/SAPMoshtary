package com.saphamrah.customer.mvp.view.login.interactors;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;

public interface SendOtpLoginInteracts {

    interface RequiredViewOps extends BaseView {
        void onSendPhone();

    }

    interface PresenterOps extends BasePresenterOps {
        void sendMobile(String mobile);

    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {
        void onSendPhone();

    }

    interface ModelOps extends BaseModel {
        void sendMobile(String mobile);

    }

}
