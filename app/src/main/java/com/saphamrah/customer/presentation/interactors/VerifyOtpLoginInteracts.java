package com.saphamrah.customer.presentation.interactors;

import com.saphamrah.customer.base.BaseModel;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.base.BaseRequiredPresenterOps;
import com.saphamrah.customer.base.BaseView;

public interface VerifyOtpLoginInteracts {

    interface RequiredViewOps extends BaseView {
        void onSendMobile();
        void onVerifyOtp();
    }

    interface PresenterOps extends BasePresenterOps {
        void sendMobile(String mobile);
        void verifyOtp(String mobile, String code);
    }

    interface RequiredPresenterOps extends BaseRequiredPresenterOps {

    }

    interface ModelOps extends BaseModel {

    }

}
