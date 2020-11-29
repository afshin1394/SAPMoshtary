package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptFaktorMandehDarMVP;
import com.saphamrah.MVP.Model.RptFaktorMandehDarModel;
import com.saphamrah.Model.RptListVosolModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptFaktorMandehDarPresenter implements RptFaktorMandehDarMVP.PresenterOps, RptFaktorMandehDarMVP.RequiredPresenterOps
{

    private WeakReference<RptFaktorMandehDarMVP.RequiredViewOps> mView;
    private RptFaktorMandehDarMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptFaktorMandehDarPresenter(RptFaktorMandehDarMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptFaktorMandehDarModel(this);
    }

    @Override
    public void onConfigurationChanged(RptFaktorMandehDarMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getListMandehDar()
    {
        mModel.getListMandehDar();
    }

    @Override
    public void updateListMandehDar()
    {
        mModel.updateListMandehDar();
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


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetListMandehDar(ArrayList<RptMandehdarModel> rptMandehdarModels)
    {
        mView.get().onGetListMandehDar(rptMandehdarModels);
    }

    @Override
    public void onSuccessUpdateListMandehDar()
    {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorUpdateListMandehDar()
    {
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
