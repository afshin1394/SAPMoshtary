package com.saphamrah.MVP.Presenter.MainFragmentViewPagerPresenter;

import android.content.Context;

import com.saphamrah.BaseMVP.MainViewPagerMVP.HadafForoshTedadyFragmentMVP;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.MVP.Model.MainFragmentViewPagerModel.HadafForoshTedadyFragmentModel;

import java.lang.ref.WeakReference;

public class HadafForoshTedadyFragmentsPresenter implements HadafForoshTedadyFragmentMVP.PresenterOps, HadafForoshTedadyFragmentMVP.RequiredPresenterOps {
    private WeakReference<HadafForoshTedadyFragmentMVP.RequiredViewOps> mView;
    private HadafForoshTedadyFragmentMVP.ModelOps mModel;
    public HadafForoshTedadyFragmentsPresenter(HadafForoshTedadyFragmentMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new HadafForoshTedadyFragmentModel(this);

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
    public void onGetHadafForoshTedadyKole(BaseHadafForoshModel baseHadafForoshModel) {
        mView.get().onGetHadafForoshTedady(baseHadafForoshModel);
    }

    @Override
    public void getHadafForoshTedady() {
        mModel.getHadafForoshTedadyKole();
    }

    @Override
    public void onConfigurationChanged(HadafForoshTedadyFragmentMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }
}
