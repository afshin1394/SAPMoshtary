package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.OnlineSupportMVP;
import com.saphamrah.MVP.Model.OnlineSupportModel;
import com.saphamrah.Model.SupportCrispModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;

public class OnlineSupportPresenter implements OnlineSupportMVP.PresenterOps , OnlineSupportMVP.RequiredPresenterOps
{

    private WeakReference<OnlineSupportMVP.RequiredViewOps> mView;
    private OnlineSupportMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public OnlineSupportPresenter(OnlineSupportMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new OnlineSupportModel(this);
    }

    @Override
    public void onConfigurationChanged(OnlineSupportMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void getCrispId()
    {
        mModel.getCrispId();
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
        mView = null;
        mIsChangingConfig = isChangingConfig;
        if (!isChangingConfig)
        {
            mModel.onDestroy();
        }
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public Context getAppContext()
    {
        try
        {
            return mView.get().getAppContext();
        }
        catch (NullPointerException e)
        {
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "OnlineSupportPresenter" , "" , "getAppContext" , "");
            return null;
        }
    }

    @Override
    public void onGetCrispId(SupportCrispModel supportCrispModel)
    {
        if (supportCrispModel != null)
        {
            if (supportCrispModel.getCrispID() != null && supportCrispModel.getCrispID().trim().equals(""))
            {
                mView.get().showResourceError(true, R.string.error, R.string.errorGetCrispID, Constants.FAILED_MESSAGE(), R.string.apply);
            }
            else if (supportCrispModel.getCrispID() != null && supportCrispModel.getCrispID().trim().equals("-1"))
            {
                mView.get().showResourceError(true, R.string.error, R.string.errorGetCcSazmanForosh, Constants.FAILED_MESSAGE(), R.string.apply);
            }
            else
            {
                mView.get().onGetCrispId(supportCrispModel.getCrispID());
            }
        }
        else
        {
            mView.get().showResourceError(true, R.string.error, R.string.errorGetCrispID, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void onFailed(int resId, int messageType, int duration)
    {
        mView.get().showToast(resId , messageType , duration);
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }


}
