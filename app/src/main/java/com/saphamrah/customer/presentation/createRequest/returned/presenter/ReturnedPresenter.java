package com.saphamrah.customer.presentation.createRequest.returned.presenter;

import android.content.Context;

import com.saphamrah.customer.base.BasePresenter;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.presentation.createRequest.returned.interactor.ReturnedInteractor;
import com.saphamrah.customer.presentation.createRequest.returned.model.ReturnedModel;

import java.util.List;

public class ReturnedPresenter extends BasePresenter<ReturnedInteractor.RequiredViewOps, ReturnedInteractor.ModelOps> implements ReturnedInteractor.PresenterOps, ReturnedInteractor.RequiredPresenterOps {

    public ReturnedPresenter(ReturnedInteractor.RequiredViewOps view) {
        super(view);
        model = new ReturnedModel(this);
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

    @Override
    public void getMarjoee() {
        model.getMarjoee();
    }

    @Override
    public void onGetMarjoee(List<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {
        view.get().onGetMarjoee(elamMarjoeeForoshandehModels);
    }
}
