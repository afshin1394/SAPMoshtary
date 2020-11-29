package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.CustomersListMVP;
import com.saphamrah.MVP.Model.CustomersListModel;
import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CustomersListPresenter implements CustomersListMVP.PresenterOps , CustomersListMVP.RequiredPresenterOps
{

    private WeakReference<CustomersListMVP.RequiredViewOps> mView;
    private CustomersListMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public CustomersListPresenter(CustomersListMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new CustomersListModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(CustomersListMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
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
    public void getAllMasirs()
    {
        mModel.getAllMasirs();
    }

    @Override
    public void getAllCustomers()
    {
        mModel.getAllCustomers();
    }

    @Override
    public void getCustomersByccMasir(int ccMasir)
    {
        if (ccMasir == -1)
        {
            mModel.getAllCustomers();
        }
        else if (ccMasir > 0)
        {
            mModel.getCustomersByccMasir(ccMasir);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectMasir, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void searchCustomer(String searchWord , ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels)
    {
        ArrayList<AllMoshtaryForoshandehModel> searchResultModel = new ArrayList<>();
        for (AllMoshtaryForoshandehModel model : allMoshtaryForoshandehModels)
        {
            if (model.getNameMoshtary().contains(searchWord))
            {
                searchResultModel.add(model);
            }
        }
        mView.get().onGetSearchResult(searchResultModel);
    }

    @Override
    public void checkSelectedCustomerForGetInfo(int position, AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        if (position >= 0 && allMoshtaryForoshandehModel != null)
        {
            mModel.getSelectedCustomerInfo(position, allMoshtaryForoshandehModel);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
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
    {

    }

    @Override
    public void getAllMoshtarian(String tag, int ccForoshandeh) {
        mModel.getAllMoshtarian(tag, ccForoshandeh);
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetAllMasirs(ArrayList<MasirModel> masirModels)
    {
        if (masirModels.size() > 0)
        {
            mView.get().onGetAllMasirs(masirModels);
        }
        else
        {
            mView.get().showToast(R.string.errorGetDataAndGetProgram, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetAllCustomers(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels)
    {
        if (allMoshtaryForoshandehModels.size() > 0)
        {
            mView.get().onGetAllCustomers(allMoshtaryForoshandehModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetCustomersByccMasir(ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels)
    {
        if (allMoshtaryForoshandehModels.size() > 0)
        {
            mView.get().onGetCustomersByccMasir(allMoshtaryForoshandehModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onSuccessfullyGetNewItem(int sumOfItems, int position)
    {
        if (position != sumOfItems - 1)
        {
            mView.get().onSuccessfullyGetNewItemOfInfo(position);
        }
        else
        {
            mView.get().onCompleteGetCustomerInfo();
        }
    }

    @Override
    public void onFailedGetNewItem(int position, String errorMessage)
    {
        mView.get().onFailedGetCustomerInfo(position , errorMessage);
    }

}
