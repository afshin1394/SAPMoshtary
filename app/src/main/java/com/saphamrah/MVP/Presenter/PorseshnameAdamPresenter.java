package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.PorseshnameAdamMVP;
import com.saphamrah.MVP.Model.PorseshnameAdamModel;
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.UIModel.CustomerVisitModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class PorseshnameAdamPresenter implements PorseshnameAdamMVP.PresenterOps, PorseshnameAdamMVP.RequiredPresenterOps
{

    private WeakReference<PorseshnameAdamMVP.RequiredViewOps> mView;
    private PorseshnameAdamMVP.ModelOps mModel;


    public PorseshnameAdamPresenter(PorseshnameAdamMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new PorseshnameAdamModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(PorseshnameAdamMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getAllPorseshname()
    {
        mModel.getAllPorseshname();
    }

    @Override
    public void getAllAdamFaal()
    {
        mModel.getAllAdamFaal();
    }

    @Override
    public void deletePorseshname(int ccPorseshnameh)
    {
        mModel.deletePorseshname(ccPorseshnameh);
    }

    @Override
    public void deleteAdamFaal(int ccVisitMoshtary)
    {
        mModel.deleteAdamFaal(ccVisitMoshtary);
    }

    @Override
    public void sendPorseshname(int ccPorseshnameh)
    {
        mView.get().showLoading();
        mModel.sendPorseshname(ccPorseshnameh);
    }

    @Override
    public void sendAdamFaal(int ccVisitMoshtary)
    {
        mView.get().showLoading();
        mModel.sendAdamFaal(ccVisitMoshtary);
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetPorseshname(List<PorseshnamehModel> porseshnamehModels)
    {
        mView.get().changeTitleToPorseshname();
        mView.get().changeFabToAdam();
        mView.get().showAllPorseshname(porseshnamehModels);
        if (porseshnamehModels.size() == 0)
        {
            mView.get().showNotFoundData();
        }
    }

    @Override
    public void onGetallAdamFaal(List<CustomerVisitModel> customerVisitModels)
    {
        mView.get().changeTitleToAdam();
        mView.get().changeFabToPorseshname();
        mView.get().showAllAdamFaal(customerVisitModels);
        if (customerVisitModels.size() == 0)
        {
            mView.get().showNotFoundData();
        }
    }

    @Override
    public void onSuccessDelete()
    {
        mView.get().showAlertSuccessDelete();
    }

    @Override
    public void onErrorSendPorseshnameToServer(String message)
    {
        mView.get().closeLoading();
        mView.get().showErrorMessage(message);
    }

    @Override
    public void onErrorSendAdamToServer(String message)
    {
        mView.get().closeLoading();
        mView.get().showErrorMessage(message);
    }

    @Override
    public void onSuccessSend()
    {
        mView.get().closeLoading();
        mView.get().showSuccessSendData();
    }
}
