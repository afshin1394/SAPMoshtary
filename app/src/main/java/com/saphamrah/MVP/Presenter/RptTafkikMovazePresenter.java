package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.RptTafkikMovazeMVP;
import com.saphamrah.MVP.Model.RptTafkikMovazeModel;
import com.saphamrah.Model.RptTafkikMovazeDataModel;
import com.saphamrah.Model.TafkikKalaMovazeDataModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class RptTafkikMovazePresenter implements RptTafkikMovazeMVP.PresenterOps,RptTafkikMovazeMVP.RequiredPresenterOps
{

    private WeakReference<RptTafkikMovazeMVP.RequiredViewOps> mView;
    private RptTafkikMovazeMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptTafkikMovazePresenter(RptTafkikMovazeMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptTafkikMovazeModel(this);
    }

    @Override
    public void onConfigurationChanged(RptTafkikMovazeMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void onGetDarkhastFaktortList(ArrayList<RptTafkikMovazeDataModel> darkhastFaktorVazeiatPPCModels) {
        if (darkhastFaktorVazeiatPPCModels.size() > 0)
        {
            mView.get().onGetDarkhastFaktorList(darkhastFaktorVazeiatPPCModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetTafkikKalaList(ArrayList<TafkikKalaMovazeDataModel> tafkikKalaMovazeDataModels) {
        if (tafkikKalaMovazeDataModels.size() > 0)
        {
            mView.get().onGetTafkikKalaList(tafkikKalaMovazeDataModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void getDarkhastFaktorList()
    {
        mModel.getDarkhastFaktorList();
    }

    @Override
    public void getTafkikKalaList(String ccDarkhastFaktors)
    {
        mModel.getTafkikKalaList(ccDarkhastFaktors);
    }

    @Override
    public void updateData()
    {
        mModel.updateData();
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
    public void onSuccessUpdateData()
    {
        mView.get().closeLoadingAlert();
    }

    @Override
    public void onErrorUpdateData()
    {
        mView.get().closeLoadingAlert();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
