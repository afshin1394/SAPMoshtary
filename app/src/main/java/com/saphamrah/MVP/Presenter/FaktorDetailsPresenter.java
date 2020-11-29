package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.FaktorDetailsMVP;
import com.saphamrah.MVP.Model.FaktorDetailsModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FaktorDetailsPresenter implements FaktorDetailsMVP.PresenterOps , FaktorDetailsMVP.RequiredPresenterOps
{

    private WeakReference<FaktorDetailsMVP.RequiredViewOps> mView;
    private FaktorDetailsMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public FaktorDetailsPresenter(FaktorDetailsMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new FaktorDetailsModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(FaktorDetailsMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getFaktorDetails(long ccDarkhastFaktor , boolean getDetailOfNewFaktor)
    {
        if (ccDarkhastFaktor <= 0)
        {
            mView.get().showToast(R.string.errorFindccDarkhastFaktor, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            if (getDetailOfNewFaktor)
            {
                mModel.getFaktorDetails(ccDarkhastFaktor);
            }
            else
            {
                mModel.getFaktorDetailsForTreasuryList(ccDarkhastFaktor);
            }
        }
    }

    @Override
    public void checkUpdateDarkhastFaktorEmza(byte[] imageBytes, long ccDarkhastFaktor)
    {
        if (imageBytes.length > 0 && ccDarkhastFaktor > 0)
        {
            mModel.updateDarkhastFaktorEmza(imageBytes, ccDarkhastFaktor);
        }
        else
        {
            mView.get().showToast(R.string.errorGetScreenShot, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
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
    {}


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetFaktorDetails(String customerCode, String customerName, float mablaghArzeshAfzoode, double mablaghKol, double mablaghKhalesFaktor, String noeVosol, int modatVosol, String address)
    {
        mView.get().onGetFaktorDetails(customerCode, customerName, mablaghArzeshAfzoode, mablaghKol, mablaghKhalesFaktor, noeVosol, modatVosol, address);
    }

    @Override
    public void onGetKala(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels)
    {
        mView.get().onGetKala(kalaDarkhastFaktorModels);
    }

    @Override
    public void onGetTakhfif(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels)
    {
        mView.get().onGetTakhfif(darkhastFaktorTakhfifModels);
    }

    @Override
    public void onGetKalaElamMarjoee(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels)
    {
        mView.get().onGetKalaElamMarjoee(kalaElamMarjoeeModels);
    }

    @Override
    public void onGetJayezeh(ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels)
    {
        mView.get().onGetJayezeh(darkhastFaktorJayezehModels);
    }

    @Override
    public void onGetEmzaDetail(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel)
    {
        mView.get().onGetEmzaDetail(darkhastFaktorEmzaMoshtaryModel);
    }


    @Override
    public void onErrorUpdateDarkhastFaktorEmza()
    {
        mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessUpdateDarkhastFaktorEmza()
    {
        mView.get().onSuccessUpdateDarkhastFaktorEmza();
    }
}
