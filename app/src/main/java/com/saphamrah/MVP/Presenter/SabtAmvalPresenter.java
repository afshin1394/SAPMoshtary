package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.SabtAmvalMVP;
import com.saphamrah.Model.AmvalModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SabtAmvalPresenter implements SabtAmvalMVP.PresenterOps, SabtAmvalMVP.RequiredPresenterOps
{

    private WeakReference<SabtAmvalMVP.RequiredViewOps> mView;
    private SabtAmvalMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public SabtAmvalPresenter(SabtAmvalMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new com.saphamrah.MVP.Model.SabtAmvalModel(this);
    }

    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onConfigurationChanged(SabtAmvalMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void onGetListAmvals(ArrayList<AmvalModel> sabtMoshtaryAmvalModels) {

        mView.get().setListAdapter(sabtMoshtaryAmvalModels);


    }

    @Override
    public void onGetSabtedAmvals(ArrayList<AmvalModel> models) {
        mView.get().onArraySabtMalListener(models);
    }

    @Override
    public void getSabtedAmvals(int ccMoshtary) {
        getSabtedMal(ccMoshtary);
    }

    @Override
    public void getListAmvals(int ccMoshtary, int ccSazmanForosh)
    {
        mModel.getListAmvals(ccMoshtary, ccSazmanForosh);
    }

    @Override
    public int amvalSabtShodeh(String barcode, int ccMoshtary) {
        return mModel.amvalSabtShodeh(barcode, ccMoshtary);
    }

    @Override
    public void getSabtedMal(int ccMoshtary) {
        mModel.getSabtedMals(ccMoshtary);
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }
}
