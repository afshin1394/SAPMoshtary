package com.saphamrah.customer.presentation.createRequest.marjoee.presenter;

import android.content.Context;

import com.saphamrah.customer.base.BasePresenter;
import com.saphamrah.customer.presentation.createRequest.filter.interactor.FilterMVPInteractor;
import com.saphamrah.customer.presentation.createRequest.filter.model.FilterMVPModel;
import com.saphamrah.customer.presentation.createRequest.marjoee.interactor.MarjoeeInteractor;
import com.saphamrah.customer.presentation.createRequest.marjoee.model.MarjoeeModel;
import com.saphamrah.customer.presentation.createRequest.marjoee.view.fragment.MarjoeeFragment;
import com.saphamrah.customer.presentation.login.verifyOtp.model.VerifyOtpLoginModel;

public class MarjoeePresenter extends BasePresenter<MarjoeeInteractor.RequiredViewOps,MarjoeeInteractor.ModelOps> implements MarjoeeInteractor.PresenterOps, MarjoeeInteractor.RequiredPresenterOps {

    public MarjoeePresenter(MarjoeeInteractor.RequiredViewOps view) {
        super(view);
        model = new MarjoeeModel(this);
    }

    @Override
    public void checkInsertLogToDB(Integer logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Context getContext() {
        return view.get().getAppContext();
    }
}
