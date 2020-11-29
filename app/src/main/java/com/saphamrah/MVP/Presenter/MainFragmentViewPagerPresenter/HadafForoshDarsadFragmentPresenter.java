package com.saphamrah.MVP.Presenter.MainFragmentViewPagerPresenter;

import android.content.Context;

import com.saphamrah.BaseMVP.MainViewPagerMVP.HadafForoshDarsadFragmentMVP;
import com.saphamrah.BaseMVP.MainViewPagerMVP.HadafForoshTedadyFragmentMVP;
import com.saphamrah.MVP.Model.MainFragmentViewPagerModel.HadafForoshDarsadFragmentModel;
import com.saphamrah.MVP.Model.MainFragmentViewPagerModel.HadafForoshTedadyFragmentModel;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;

import java.lang.ref.WeakReference;

public class HadafForoshDarsadFragmentPresenter implements HadafForoshDarsadFragmentMVP.PresenterOps, HadafForoshDarsadFragmentMVP.RequiredPresenterOps {

    private WeakReference<HadafForoshDarsadFragmentMVP.RequiredViewOps> mView;
    private HadafForoshDarsadFragmentMVP.ModelOps mModel;
    public HadafForoshDarsadFragmentPresenter(HadafForoshDarsadFragmentMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new HadafForoshDarsadFragmentModel(this);

    }

    @Override
    public void getHadafForoshTedady() {
        mModel.getHadafForoshTedadyKole();
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
    public void onConfigurationChanged(HadafForoshDarsadFragmentMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
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
}
