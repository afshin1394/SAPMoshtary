package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.MandehdarZangireiMVP;
import com.saphamrah.BaseMVP.TizerMVP;
import com.saphamrah.MVP.Model.MandehdarZangireiModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.PubFunc.LanguageUtil;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MandehdarZangireiPresenter implements MandehdarZangireiMVP.PresenterOps , MandehdarZangireiMVP.RequiredPresenterOps {
    private WeakReference<MandehdarZangireiMVP.RequiredViewOps> mView;
    private MandehdarZangireiMVP.ModelOps mModel;

    public MandehdarZangireiPresenter(MandehdarZangireiMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        this.mModel = new MandehdarZangireiModel(this);
    }

    @Override
    public void onConfigurationChanged(MandehdarZangireiMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }
    /////////////////////////// PresenterOps ///////////////////////////
    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void getListMandehDar() {
        mModel.getListMandehDar();
    }

    @Override
    public void updateListMandehDar() {
        mModel.updateListMandehDar();
    }

    @Override
    public void getDetails(RptMandehdarModel model) {
        mModel.getDetails(model);
    }


    @Override
    public Context getAppContext() {
        try
        {
            return mView.get().getAppContext();
        }
        catch (NullPointerException e)
        {
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "MainPresenter" , "" , "getAppContext" , "");
            return null;
        }
    }

    /////////////////////////// RequiredPresenterOps ///////////////////////////
    @Override
    public void onGetListMandehDar(ArrayList<RptMandehdarModel> mandehdarModels) {
        mView.get().onGetListMandehDar(mandehdarModels);
    }

    @Override
    public void onUpdateData() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.successGetData, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void failedUpdate() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
