package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptFaktorTozieNashodeMVP;
import com.saphamrah.MVP.Model.RptFaktorTozieNashodeModel;
import com.saphamrah.Model.RptFaktorTozieNashodehModel;
import com.saphamrah.Model.RptListVosolModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptFaktorTozieNashodePresenter implements RptFaktorTozieNashodeMVP.PresenterOps , RptFaktorTozieNashodeMVP.RequiredPresenterOps
{

    private WeakReference<RptFaktorTozieNashodeMVP.RequiredViewOps> mView;
    private RptFaktorTozieNashodeMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptFaktorTozieNashodePresenter(RptFaktorTozieNashodeMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptFaktorTozieNashodeModel(this);
    }

    @Override
    public void onConfigurationChanged(RptFaktorTozieNashodeMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getListFaktorTozieNashode()
    {
        mModel.getListFaktorTozieNashode();
    }

    @Override
    public void updateFaktorTozeieNashode()
    {
        mModel.updateFaktorTozeieNashode();
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
    public void onGetListFaktorTozieNashode(ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels)
    {
        //bigger than one, because sum of rows is a record even that there is no record
        if (rptFaktorTozieNashodehModels.size() > 1)
        {
            float sum = rptFaktorTozieNashodehModels.get(rptFaktorTozieNashodehModels.size()-1).getRoundMablaghKhalesFaktor();
            rptFaktorTozieNashodehModels.remove(rptFaktorTozieNashodehModels.size()-1);
            mView.get().setListAdapter(rptFaktorTozieNashodehModels , sum);
        }
        else
        {
            mView.get().hideFooter();
        }
    }

    @Override
    public void onSuccessUpdateListFaktorTozeisNashode()
    {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorUpdateListFaktorTozeisNashode()
    {
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


}
