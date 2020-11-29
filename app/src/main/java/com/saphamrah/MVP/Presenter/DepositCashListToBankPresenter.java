package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.DepositCashListToBankMVP;
import com.saphamrah.MVP.Model.DepositCashListToBankModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.TafkikJozeModel;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DepositCashListToBankPresenter implements DepositCashListToBankMVP.PresenterOps, DepositCashListToBankMVP.RequiredPresenterOps
{

    private WeakReference<DepositCashListToBankMVP.RequiredViewOps> mView;
    private DepositCashListToBankModel mModel;

    public DepositCashListToBankPresenter(DepositCashListToBankMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
        mModel = new DepositCashListToBankModel(this);
    }

    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(DepositCashListToBankMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getAllTafkik()
    {
        mModel.getAllTafkik();
    }

    @Override
    public void getAllShomareHesab()
    {
        mModel.getAllShomareHesab();
    }

    @Override
    public void checkTafkikForMablaghMandehVajh(long ccTafkikJoze)
    {
        if (ccTafkikJoze > 0)
        {
            mModel.getMablaghMandehVajhNaghd(ccTafkikJoze);
        }
        else
        {
            mView.get().showAlertNotSelectedTafkik();
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
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
    public void onGetAllTafkik(ArrayList<TafkikJozeModel> tafkikJozeModels)
    {
        if (tafkikJozeModels.size() > 0)
        {
            mView.get().showAllTafkik(tafkikJozeModels);
        }
        else
        {
            mView.get().showAlertNotFoundTafkik();
        }
    }

    @Override
    public void onGetAllShomareHesab(List<MarkazShomarehHesabModel> markazShomarehHesabModels)
    {
        if (markazShomarehHesabModels.size() > 0)
        {
            mView.get().showAllShomareHesab(markazShomarehHesabModels);
        }
        else
        {
            mView.get().showAlertNotFoundShomareHesab();
        }
    }

    @Override
    public void onGetMablaghMandehVajhNaghd(double sumMablaghVojohNaghd)
    {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        mView.get().showMablaghMandehVajhNaghd(decimalFormat.format(sumMablaghVojohNaghd));
    }
}
