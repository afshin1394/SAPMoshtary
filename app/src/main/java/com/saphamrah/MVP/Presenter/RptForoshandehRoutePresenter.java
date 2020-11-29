package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptForoshandehRouteMVP;
import com.saphamrah.MVP.Model.RptForoshandehRouteModel;
import com.saphamrah.Model.RptMasirModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptForoshandehRoutePresenter implements RptForoshandehRouteMVP.PresenterOps , RptForoshandehRouteMVP.RequiredPresenterOps
{

    private WeakReference<RptForoshandehRouteMVP.RequiredViewOps> mView;
    private RptForoshandehRouteMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptForoshandehRoutePresenter(RptForoshandehRouteMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptForoshandehRouteModel(this);
    }

    @Override
    public void onConfigurationChanged(RptForoshandehRouteMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getRouteList()
    {
        mModel.getRouteList();
    }

    @Override
    public void updateRouteList()
    {
        mModel.updateRouteList();
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
    public void onGetRouteList(ArrayList<RptMasirModel> rptMasirModels)
    {
        // array list have two record for sum and daily average
        if (rptMasirModels.size() > 2)
        {
            RptMasirModel rptMasirSum = rptMasirModels.get(rptMasirModels.size()-2);
            RptMasirModel rptMasirDailyAvg = rptMasirModels.get(rptMasirModels.size()-1);
            rptMasirModels.remove(rptMasirModels.size()-1);
            rptMasirModels.remove(rptMasirModels.size()-1);
            mView.get().setAdapter(rptMasirModels , rptMasirSum, rptMasirDailyAvg);
        }
        else
        {
            mView.get().emptyList();
        }
    }


    @Override
    public void onSuccessUpdateRouteList()
    {
        mView.get().closeLoading();
        mView.get().showToast(R.string.updateSuccessed, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedUpdateRouteList(int resId)
    {
        mView.get().closeLoading();
        mView.get().showToast(R.string.updateFailed, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
