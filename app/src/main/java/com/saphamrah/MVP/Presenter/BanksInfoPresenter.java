package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.BanksInfoMVP;
import com.saphamrah.MVP.Model.BanksInfoModel;
import com.saphamrah.Model.BankLocation;
import com.saphamrah.Model.BankModel;
import com.saphamrah.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BanksInfoPresenter implements BanksInfoMVP.PresenterOps , BanksInfoMVP.RequiredPresenterOps
{

    private WeakReference<BanksInfoMVP.RequiredViewOps> mView;
    private BanksInfoModel mModel;

    public BanksInfoPresenter(BanksInfoMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
        mModel = new BanksInfoModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(BanksInfoMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getListOfAllBanks()
    {
        mModel.getListOfAllBanks();
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void getBranchOfBank(String noeBank, String lat, String lng)
    {
        if (noeBank == null || noeBank.trim().equals(""))
        {
            mView.get().showError(R.string.errorBank);
            return;
        }
        else if (lat == null || lat.trim().equals("") || lat.trim().equals("0.0") || lng == null || lng.trim().equals("") || lng.trim().equals("0.0"))
        {
            mView.get().showError(R.string.errorGetLocation);
            return;
        }
        else
        {
            mModel.getBranchOfBank(noeBank, lat, lng);
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
    public void onGetListOfAllBanks(ArrayList<BankModel> bankModels)
    {
        mView.get().showBanks(bankModels);
    }

    @Override
    public void onGetBranchOfBank(ArrayList<BankLocation> bankLocations)
    {
        if (bankLocations == null || bankLocations.size() == 0)
        {
            mView.get().showError(R.string.emptyList);
        }
        else
        {
            mView.get().showBranchOfBank(bankLocations);
        }
    }

    @Override
    public void onError(int resId)
    {
        mView.get().showError(resId);
    }
}
