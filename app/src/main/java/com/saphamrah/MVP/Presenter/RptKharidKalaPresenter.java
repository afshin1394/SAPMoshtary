package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptKharidKalaMVP;
import com.saphamrah.MVP.Model.RptKharidKalaModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptKharidKalaPresenter implements RptKharidKalaMVP.PresenterOps , RptKharidKalaMVP.RequiredPresenterOps
{

    private WeakReference<RptKharidKalaMVP.RequiredViewOps> mView;
    private RptKharidKalaMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptKharidKalaPresenter(RptKharidKalaMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptKharidKalaModel(this);
    }

    @Override
    public void onConfigurationChanged(RptKharidKalaMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getListKharidKala()
    {
        mModel.getListKharidKala();
    }

    @Override
    public void updateListKharidKala()
    {
        mModel.updateListKharidKala();
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
    public void onGetListKharidKala(ArrayList<com.saphamrah.Model.RptKharidKalaModel> kharidKalaModels)
    {
        if (kharidKalaModels.size() > 0)
        {
            mView.get().onGetListKharidKala(kharidKalaModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }


    @Override
    public void onSuccessUpdate()
    {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorUpdate()
    {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
