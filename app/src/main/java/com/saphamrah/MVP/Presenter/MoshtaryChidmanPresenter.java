package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.AddCustomerListMVP;
import com.saphamrah.BaseMVP.MoshtaryChidmanMVP;
import com.saphamrah.MVP.Model.MoshtaryChidmanModel;
import com.saphamrah.MVP.View.MoshtaryChidmanActivity;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MoshtaryChidmanPresenter implements MoshtaryChidmanMVP.PresenterOps, MoshtaryChidmanMVP.RequiredPresenterOps {

    private WeakReference<MoshtaryChidmanMVP.RequiredViewOps> mView;
    private MoshtaryChidmanModel mModel;

    public MoshtaryChidmanPresenter(MoshtaryChidmanMVP.RequiredViewOps view) {
        mView = new WeakReference<>(view);
        mModel = new MoshtaryChidmanModel(this);
    }

    @Override
    public void onConfigurationChanged(AddCustomerListMVP.RequiredViewOps view) {

    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {

    }

    @Override
    public void insertMoshtaryChidman(com.saphamrah.Model.MoshtaryChidmanModel moshtaryChidmanModel) {
        mModel.insertMoshtaryChidman(moshtaryChidmanModel);
    }

    @Override
    public void getMoshtaryChidman() {
        mModel.getMoshtaryChidman();
    }


    @Override
    public void onDestroy(boolean isChangingConfig) {

    }

    @Override
    public void deleteMoshtaryChidman(int ccMoshtaryChidman) {
        mModel.deleteMoshtaryChidman(ccMoshtaryChidman);
    }

    @Override
    public void updateMoshtaryChidman(com.saphamrah.Model.MoshtaryChidmanModel moshtaryChidmanModel) {
        mModel.updateMoshtaryChidman(moshtaryChidmanModel);
    }

    @Override
    public void sendMoshtaryChidmans(ArrayList<com.saphamrah.Model.MoshtaryChidmanModel> moshtaryChidmanModels) {
        mView.get().showLoadingDialog();
        ArrayList<com.saphamrah.Model.MoshtaryChidmanModel> moshtaryChidmanModelsNotSend = new ArrayList<>();
        for (com.saphamrah.Model.MoshtaryChidmanModel moshtaryChidmanModel : moshtaryChidmanModels) {
            if (moshtaryChidmanModel.getExtraProp_IsSend() == 0)
                moshtaryChidmanModelsNotSend.add(moshtaryChidmanModel);
        }
        mModel.sendMoshtaryChidmans(moshtaryChidmanModelsNotSend);
    }

    @Override
    public void checkNotSendMoshtaryChidman(ArrayList<com.saphamrah.Model.MoshtaryChidmanModel> moshtaryChidmanModels) {
        for (com.saphamrah.Model.MoshtaryChidmanModel moshtaryChidmanModel : moshtaryChidmanModels)
        {
            if (moshtaryChidmanModel.getExtraProp_IsSend() == 0)
            {
                mView.get().showAlertDialog(R.string.NotSentItemsExist,Constants.FAILED_MESSAGE());
                return;
            }
        }
        mView.get().closeActivity();
    }


    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetMoshtaryChidman(ArrayList<com.saphamrah.Model.MoshtaryChidmanModel> moshtaryChidmanModels) {
        mView.get().onGetMoshtaryChidman(moshtaryChidmanModels);
    }

    @Override
    public void onInsertMoshtaryChidman(com.saphamrah.Model.MoshtaryChidmanModel moshtaryChidmanModel) {
        mView.get().onInsertMoshtaryChidman(moshtaryChidmanModel);
    }

    @Override
    public void onSuccess(int resId) {
        mView.get().closeLoading();
        mView.get().showToast(resId, Constants.SUCCESS_MESSAGE(), Constants.DURATION_SHORT());
    }

    @Override
    public void onError(int resId) {
        mView.get().closeLoading();
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_SHORT());

    }

    @Override
    public void onGetConfig(boolean canInsertNewCustomer) {

    }

    @Override
    public void onDeleteMoshtaryChidman() {
        mView.get().onDeleteMoshtaryChidman();
    }

    @Override
    public void onUpdateMoshtaryChidman() {
        mView.get().onUpdateMoshtaryChidman();
    }

    @Override
    public void onSendMoshtaryChidmans() {
        mView.get().onSendMoshtaryChidman();
    }
}
