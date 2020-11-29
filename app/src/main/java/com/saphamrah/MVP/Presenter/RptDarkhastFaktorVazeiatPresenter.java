package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptDarkhastFaktorVazeiatMVP;
import com.saphamrah.MVP.Model.RptDarkhastFaktorVazeiatModel;
import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class RptDarkhastFaktorVazeiatPresenter implements RptDarkhastFaktorVazeiatMVP.PresenterOps,RptDarkhastFaktorVazeiatMVP.RequiredPresenterOps
{

    private WeakReference<RptDarkhastFaktorVazeiatMVP.RequiredViewOps> mView;
    private RptDarkhastFaktorVazeiatMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptDarkhastFaktorVazeiatPresenter(RptDarkhastFaktorVazeiatMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptDarkhastFaktorVazeiatModel(this);
    }

    @Override
    public void onConfigurationChanged(RptDarkhastFaktorVazeiatMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getDarkhastFaktorVazeiatList()
    {
        mModel.getDarkhastFaktorVazeiatList();
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
    public void onGetDarkhastFaktorVazeiatList(ArrayList<RptDarkhastFaktorVazeiatPPCModel> darkhastFaktorVazeiatPPCModels)
    {
        if (darkhastFaktorVazeiatPPCModels.size() > 0)
        {
            mView.get().onGetDarkhastFaktorVazeiatList(darkhastFaktorVazeiatPPCModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
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
