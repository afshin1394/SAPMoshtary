package com.saphamrah.customer.presentation.login.verifyOtp.presenter;

import android.content.Context;

import com.saphamrah.customer.base.BasePresenter;
import com.saphamrah.customer.base.BasePresenterOps;
import com.saphamrah.customer.presentation.login.verifyOtp.interactor.VerifyOtpLoginInteracts;
import com.saphamrah.customer.presentation.login.verifyOtp.model.VerifyOtpLoginModel;

import java.lang.ref.WeakReference;

public class VerifyOtpLoginPresenter extends BasePresenter<VerifyOtpLoginInteracts.RequiredViewOps,VerifyOtpLoginInteracts.ModelOps> implements VerifyOtpLoginInteracts.PresenterOps, VerifyOtpLoginInteracts.RequiredPresenterOps{

    public VerifyOtpLoginPresenter(VerifyOtpLoginInteracts.RequiredViewOps view) {
     super(view);
     model = new VerifyOtpLoginModel(this);
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        model.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public Context getContext() {
        return view.get().getAppContext();
    }

    @Override
    public void sendMobile(String mobile) {
        view.get().onSendMobile();
    }

    @Override
    public void verifyOtp(String mobile, String code) {
        view.get().showLoading("لطفا کمی صبر نمایید");
    }
}
