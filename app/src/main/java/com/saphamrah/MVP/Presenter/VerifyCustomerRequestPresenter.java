package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.VerifyCustomerRequestMVP;
import com.saphamrah.MVP.Model.VerifyCustomerRequestModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;

public class VerifyCustomerRequestPresenter implements VerifyCustomerRequestMVP.PresenterOps , VerifyCustomerRequestMVP.RequiredPresenterOps
{

    private WeakReference<VerifyCustomerRequestMVP.RequiredViewOps> mView;
    private VerifyCustomerRequestMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public VerifyCustomerRequestPresenter(VerifyCustomerRequestMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new VerifyCustomerRequestModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(VerifyCustomerRequestMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void checkBottomBarClick(int position)
    {
        if (position == 0)
        {
            PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
            bottomBarConfig.getConfig(getAppContext());
            if (bottomBarConfig.getShowMojoodiGiri())
            {
                mView.get().openBarkhordAvalieActivity();
            }
            else
            {
                mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        }
        else if (position == 1 || position == 2 || position == 3 || position == 4)
        {
            mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void getAgreementContent(int ccMoshtary)
    {
        if (ccMoshtary != -1)
        {
            mModel.getAgreementContent(ccMoshtary);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkSaveBitmap(int ccMoshtary , byte[] customerSignPic)
    {
        if (ccMoshtary > 0)
        {
            mModel.saveBitmap(ccMoshtary , customerSignPic);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void getccDarkhastFaktor()
    {
        mModel.getccDarkhastFaktor();
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
    public void onGetAgreementContent(String text)
    {
        mView.get().onGetAgreementContent(text);
    }

    @Override
    public void onSuccessInsertCustomerSign()
    {
        mView.get().onSuccessInsertCustomerSign();
    }

    @Override
    public void onFailedInsertCustomerSign(int errorResId)
    {
        mView.get().showToast(errorResId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onGetccDarkhastFaktor(long ccDarkhastFaktor)
    {
        if (ccDarkhastFaktor != -1)
        {
            mView.get().openFaktorDetailActivity(ccDarkhastFaktor);
        }
        else
        {
            mView.get().showToast(R.string.errorFindccDarkhastFaktor, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }


}
