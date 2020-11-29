package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.AddCustomerListMVP;
import com.saphamrah.MVP.Model.AddCustomerListModel;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AddCustomerListPresenter implements AddCustomerListMVP.PresenterOps , AddCustomerListMVP.RequiredPresenterOps
{

    private WeakReference<AddCustomerListMVP.RequiredViewOps> mView;
    private AddCustomerListMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public AddCustomerListPresenter(AddCustomerListMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new AddCustomerListModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AddCustomerListMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getConfig()
    {
        mModel.getConfig();
    }

    @Override
    public void getNewCustomers()
    {
        mModel.getNewCustomers();
    }

    @Override
    public void checkDeleteCustomer(int ccMoshtary , int position)
    {
        if (ccMoshtary > 0 && position >= 0)
        {
            mModel.deleteCustomer(ccMoshtary , position);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectCustomer , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkAndRemoveAddCustomerInfoShared()
    {
        mModel.checkAndRemoveAddCustomerInfoShared();
    }

    @Override
    public void checkSendToServer(int ccMoshtary)
    {
        if (ccMoshtary <= 0)
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mModel.sendToServer(ccMoshtary);
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
    public void onGetConfig(boolean canInsertNewCustomer)
    {
        if (!canInsertNewCustomer)
        {
            mView.get().onErrorNotAccessForInsertNewCustomer();
        }
    }

    @Override
    public void onGetNewCustomers(ArrayList<AddCustomerInfoModel> addCustomerInfoModels)
    {
        mView.get().onGetNewCustomers(addCustomerInfoModels);
    }

    @Override
    public void onDeletedCustomer(int ccMoshtary , int position)
    {
        mView.get().onDeleteCustomer(ccMoshtary , position);
    }

    @Override
    public void onFailedDeleteCustomer()
    {
        mView.get().showToast(R.string.errorDeleteCustomer , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onRemoveAddCustomerInfoShared()
    {
        mView.get().onRemoveAddCustomerInfoShared();
    }

    @Override
    public void onOutOfPolygonError()
    {
        mView.get().onOutOfPolygonError();
    }

    @Override
    public void onErrorAccessToLocation()
    {
        mView.get().onErrorAccessToLocation();
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }

    @Override
    public void onErrorSendToServer(int resErrorId)
    {
        mView.get().closeLoading();
        mView.get().showResourceError(false, R.string.error, resErrorId, Constants.FAILED_MESSAGE(), R.string.apply);
    }

    @Override
    public void onSuccessSendToServer(int newccMoshtary , int oldccMoshtary)
    {
        mView.get().closeLoading();
        mView.get().onSuccessSendToServer(newccMoshtary, oldccMoshtary);
    }
}
