package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.EditNationalCodeMVP;
import com.saphamrah.MVP.Model.EditNationalCodeModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;

public class EditNationalCodePresenter implements EditNationalCodeMVP.PresenterOps , EditNationalCodeMVP.RequiredPresenterOps
{

    private WeakReference<EditNationalCodeMVP.RequiredViewOps> mView;
    private EditNationalCodeMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public EditNationalCodePresenter(EditNationalCodeMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new EditNationalCodeModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(EditNationalCodeMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void checkNationalCode(int ccMoshtary, int codeNoeShakhsiat, String nationalCode, byte[] image)
    {
        if (ccMoshtary <= 0)
        {
            mView.get().showToast(R.string.errorSelectCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            return;
        }
        else if (image.length <= 0)
        {
            mView.get().showToast(R.string.errorNationalCardImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            return;
        }

        if (codeNoeShakhsiat == Constants.CODE_NOE_SHAKHSIAT_HAGHIGHI())
        {
            if (new PubFunc().new NationalCodeUtil().checkNationalCode(nationalCode))
            {
                mModel.insertNationalCode(ccMoshtary, nationalCode, "", image);
            }
            else
            {
                mView.get().showToast(R.string.errorNationalCode, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                return;
            }
        }
        else if (codeNoeShakhsiat == Constants.CODE_NOE_SHAKHSIAT_HOGHOGHI())
        {
            if (new PubFunc().new NationalCodeUtil().checkNationalEconomicalCode(nationalCode))
            {
                mModel.insertNationalCode(ccMoshtary, "", nationalCode, image);
            }
            else
            {
                mView.get().showToast(R.string.errorNationalCode, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                return;
            }
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
    public void onSuccessInsert()
    {
        mView.get().onSuccessInsert();
    }

    @Override
    public void onFailedInsert()
    {
        mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
