package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptCheckBargashtyMVP;
import com.saphamrah.MVP.Model.RptCheckBargashtyModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptCheckBargashtyPresenter implements RptCheckBargashtyMVP.PresenterOps , RptCheckBargashtyMVP.RequiredPresenterOps
{

    private WeakReference<RptCheckBargashtyMVP.RequiredViewOps> mView;
    private RptCheckBargashtyMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptCheckBargashtyPresenter(RptCheckBargashtyMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptCheckBargashtyModel(this);
    }

    @Override
    public void onConfigurationChanged(RptCheckBargashtyMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getListBargashty()
    {
        mModel.getListBargashty();
    }

    @Override
    public void updateListBargashty()
    {
        mModel.updateListBargashty();
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
    public void onGetListBargashty(ArrayList<BargashtyModel> bargashtyModels)
    {
        if (bargashtyModels.size() > 0)
        {
            mView.get().onGetListBargashty(bargashtyModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onSuccessUpdateListBargashty()
    {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorUpdateListBargashty()
    {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

}
