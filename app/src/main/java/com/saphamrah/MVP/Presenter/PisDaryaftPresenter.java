package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.PishDaryaftMVP;
import com.saphamrah.MVP.Model.PishDaryaftModel;
import com.saphamrah.Model.AllMoshtaryPishdaryaftModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PisDaryaftPresenter implements PishDaryaftMVP.PresenterOps , PishDaryaftMVP.RequiredPresenterOps
{

    private WeakReference<PishDaryaftMVP.RequiredViewOps> mView;
    private PishDaryaftMVP.ModelOps mModel;

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
    public void onUpdateData() {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void failedUpdate() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(),Constants.DURATION_LONG());
    }



    @Override
    public void searchCustomer(String searchWord , ArrayList<AllMoshtaryPishdaryaftModel> moshtaryModels)
    {
        ArrayList<AllMoshtaryPishdaryaftModel> searchResultModel = new ArrayList<>();
        for (AllMoshtaryPishdaryaftModel model : moshtaryModels)
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

    @Override
    public void refresh() {
        mModel.refresh();
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetAllCustomers( ArrayList<AllMoshtaryPishdaryaftModel> allMoshtaryPishdaryaftModels)
    {
        if (allMoshtaryPishdaryaftModels.size() > 0)
        {
            mView.get().onGetAllCustomers(allMoshtaryPishdaryaftModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

}
