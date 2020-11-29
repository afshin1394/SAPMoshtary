package com.saphamrah.MVP.Presenter.MainFragmentViewPagerPresenter;

import android.content.Context;

import com.saphamrah.BaseMVP.MainViewPagerMVP.CountFaktorForoshMVP;
import com.saphamrah.BaseMVP.MainViewPagerMVP.MablaghTedadForoshFragmentsBaseMVP;
import com.saphamrah.MVP.Model.MainFragmentViewPagerModel.CountfaktorForoshFragmentModel;
import com.saphamrah.MVP.Model.MainFragmentViewPagerModel.MablaghForoshFragmentModel;
import com.saphamrah.Model.RptForoshModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CountFaktorForoshFragmentPresenter implements CountFaktorForoshMVP.RequiredPresenterOps,CountFaktorForoshMVP.PresenterOps {
    private WeakReference<CountFaktorForoshMVP.RequiredViewOps> mView;
    private CountFaktorForoshMVP.ModelOps mModel;

    public CountFaktorForoshFragmentPresenter(CountFaktorForoshMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new CountfaktorForoshFragmentModel(this);

    }

    @Override
    public void getAmarForosh() {
        mModel.getAmarForosh();
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }

    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onConfigurationChanged(CountFaktorForoshMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void onGetAmarForosh(ArrayList<RptForoshModel> rptForoshModels) {
        mView.get().onGetAmarForosh(rptForoshModels);
    }
}
