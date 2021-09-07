package com.saphamrah.MVP.Presenter;

import com.saphamrah.BaseMVP.RptMoshtaryGharardadMVP;
import com.saphamrah.MVP.Model.RptMoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.UIModel.RptMoshtaryGharardadUiModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptMoshtaryGharardadPresenter implements RptMoshtaryGharardadMVP.PresenterOps, RptMoshtaryGharardadMVP.RequiredPresenterOps {

    private WeakReference<RptMoshtaryGharardadMVP.RequiredViewOps> mView;
    private RptMoshtaryGharardadMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptMoshtaryGharardadPresenter(RptMoshtaryGharardadMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptMoshtaryGharardadModel(this);
    }

    @Override
    public void onConfigurationChanged(RptMoshtaryGharardadMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }




    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onGetMoshtary(ArrayList<MoshtaryModel> moshtaryModels, ArrayList<String> title) {
        mView.get().onGetMoshtary(moshtaryModels,title);
    }

    @Override
    public void onGetMoshtaryGharardad(ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels, ArrayList<String> title) {
        mView.get().onGetMoshtaryGharardad(moshtaryGharardadModels,title);
    }

    @Override
    public void onGetKala(ArrayList<RptMoshtaryGharardadUiModel> models) {
        mView.get().onGetKala(models);
    }

    @Override
    public void onDetailsMoshtary(String nameMoshtary, String codeMoshtary, String shomarehGharardad, String nameSazmanForosh) {
        mView.get().onDetailsMoshtary(nameMoshtary,codeMoshtary,shomarehGharardad,nameSazmanForosh);
    }

    @Override
    public void onError(int resId,boolean isFinish) {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        if (isFinish)
            mView.get().onFinishActivity();
    }




    /////////////////////////// RequiredPresenterOps ///////////////////////////
    @Override
    public void getMoshtary() {
        mModel.getMoshtary();
    }

    @Override
    public void getGharardadMoshtary(int ccMoshtary) {
        mModel.getGharardadMoshtary(ccMoshtary);
    }

    @Override
    public void getKala(String ccMoshtaryGharardad, String ccSazmanForosh,int from) {
        mModel.getKala(ccMoshtaryGharardad,ccSazmanForosh,from);
    }


}
