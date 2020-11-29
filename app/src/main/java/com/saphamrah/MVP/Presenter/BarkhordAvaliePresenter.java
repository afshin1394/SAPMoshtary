package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.BarkhordAvalieMVP;
import com.saphamrah.MVP.Model.BarkhordAvalieModel;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class BarkhordAvaliePresenter implements BarkhordAvalieMVP.PresenterOps , BarkhordAvalieMVP.RequiredPresenterOps
{

    private WeakReference<BarkhordAvalieMVP.RequiredViewOps> mView;
    private BarkhordAvalieMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public BarkhordAvaliePresenter(BarkhordAvalieMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new BarkhordAvalieModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(BarkhordAvalieMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void checkBottomBarClick(int position, int ccMoshtary)
    {
        //check and open activity
        if (position == 1)
        {
            PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
            bottomBarConfig.getConfig(getAppContext());
            if (bottomBarConfig.getShowMojoodiGiri())
            {
                if (bottomBarConfig.getForceBarkhordAvalie())
                {
                    mModel.countBarkhordForToday(ccMoshtary);
                }
                else
                {
                    mView.get().openMojodiGiriActivity();
                }
            }
            else
            {
                mView.get().showToast(R.string.cantMultipleMojodigiri, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                mView.get().openDarkhastKalaActivity();
            }
        }
        else if (position == 2)
        {
            PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
            bottomBarConfig.getConfig(getAppContext());
            if (bottomBarConfig.getShowMojoodiGiri())
            {
                mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
            else
            {
                mView.get().openDarkhastKalaActivity();
            }
        }
        else if (position == 3 || position == 4 || position == 5)
        {
            mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void getBarkhords(int ccMoshtary)
    {
        if (ccMoshtary > 0)
        {
            mModel.getBarkhords(ccMoshtary);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectCustomer , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkNewBarkhord(int ccMoshtary, String desc)
    {
        if (ccMoshtary <= 0)
        {
            mView.get().showToast(R.string.errorSelectCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else if (desc.trim().equals(""))
        {
            mView.get().showToast(R.string.errorInputDescription, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mModel.insertNewBarkhord(ccMoshtary , desc);
        }
    }

    @Override
    public void checkRemoveBarkhord(BarkhordForoshandehBaMoshtaryModel barkhord)
    {
        if (barkhord == null)
        {
            mView.get().showToast(R.string.errorSelectItem , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else if (barkhord.getExtraProp_IsOld() == 1)
        {
            mView.get().showToast(R.string.cantRemoveItem , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mModel.removeBarkhord(barkhord.getCcBarkhordForoshandeh() , barkhord.getCcMoshtary());
        }
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
    public void onGetBarkhords(ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhords)
    {
        mView.get().onGetBarkhords(barkhords);
    }

    @Override
    public void onSuccessInsertNewBarkhord()
    {
        mView.get().showToast(R.string.successfullyDoneOps , Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedInsertNewBarkhord()
    {
        mView.get().showToast(R.string.failedOps , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedRemoveBarkhord()
    {
        mView.get().showToast(R.string.failedOps , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
	
	@Override
    public void onGetCountTodayBarkhord()
    {
        mView.get().openMojodiGiriActivity();
    }

    @Override
    public void onError(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
