package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.PishDaryaftMVP;
import com.saphamrah.MVP.Model.PishDaryaftModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PisDaryaftPresenter implements PishDaryaftMVP.PresenterOps , PishDaryaftMVP.RequiredPresenterOps
{

    private WeakReference<PishDaryaftMVP.RequiredViewOps> mView;
    private PishDaryaftMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public PisDaryaftPresenter(PishDaryaftMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new PishDaryaftModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(PishDaryaftMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void onErrorSend(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void getAllCustomers()
    {
        mModel.getAllCustomers();
    }


    @Override
    public void onSuccessSend(int position) {
        mView.get().showAlertMessage(R.string.successSendData , Constants.SUCCESS_MESSAGE());
    }


    @Override
    public void searchCustomer(String searchWord , ArrayList<MoshtaryModel> moshtaryModels)
    {
        ArrayList<MoshtaryModel> searchResultModel = new ArrayList<>();
        for (MoshtaryModel model : moshtaryModels)
        {
            if (model.getNameMoshtary().contains(searchWord))
            {
                searchResultModel.add(model);
            }
        }
        mView.get().onGetSearchResult(searchResultModel);
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

    @Override
    public void getDariaftPardakhtForSend(int ccMoshtary, int position) {
        mModel.getDariaftPardakhtForSend(ccMoshtary,position);
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetAllCustomers( ArrayList<MoshtaryModel> moshtaryModels)
    {
        if (moshtaryModels.size() > 0)
        {
            mView.get().onGetAllCustomers(moshtaryModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }


}
