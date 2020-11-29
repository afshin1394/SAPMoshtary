package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptForoshandehVisitMVP;
import com.saphamrah.MVP.Model.RptForoshandehVisitModel;
import com.saphamrah.Model.RptVisitForoshandehMoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptForoshandehVisitPresenter implements RptForoshandehVisitMVP.PresenterOps , RptForoshandehVisitMVP.RequiredPresenterOps
{


    private WeakReference<RptForoshandehVisitMVP.RequiredViewOps> mView;
    private RptForoshandehVisitMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptForoshandehVisitPresenter(RptForoshandehVisitMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptForoshandehVisitModel(this);
    }

    @Override
    public void onConfigurationChanged(RptForoshandehVisitMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getVisitList()
    {
        mModel.getVisitList();
    }

    @Override
    public void updateReport()
    {
        mModel.updateReport();
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
    public void onGetVisitList(ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels)
    {
        //at least has one record for sum
        if (rptVisitForoshandehMoshtaryModels.size() > 1)
        {
            RptVisitForoshandehMoshtaryModel rptVisitForoshandehMoshtarySum = rptVisitForoshandehMoshtaryModels.get(rptVisitForoshandehMoshtaryModels.size() - 1);
            rptVisitForoshandehMoshtaryModels.remove(rptVisitForoshandehMoshtaryModels.size() - 1);
            mView.get().setAdapter(rptVisitForoshandehMoshtaryModels , rptVisitForoshandehMoshtarySum);
        }
        else
        {
            mView.get().emptyList();
        }
    }


    @Override
    public void onSuccessUpdate()
    {
        mView.get().showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedUpdate()
    {
        mView.get().showToast(R.string.failedOps, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


}
