package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.TreasuryListOfflineMVP;
import com.saphamrah.MVP.Model.TreasuryListOfflineModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TreasuryListOfflinePresenter implements TreasuryListOfflineMVP.PresenterOps, TreasuryListOfflineMVP.RequiredPresenterOps {

    private WeakReference<TreasuryListOfflineMVP.RequiredViewOps> mView;
    private TreasuryListOfflineMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public TreasuryListOfflinePresenter(TreasuryListOfflineMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new TreasuryListOfflineModel(this);
    }

    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(TreasuryListOfflineMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }




    @Override
    public void checkDateAndFakeLocation()
    {
        mModel.checkDateAndFakeLocation();
    }

    @Override
    public void getTreasuryList(int faktorRooz , int sortType)
    {
        mModel.getTreasuryList(faktorRooz, sortType);
    }



    @Override
    public void getCustomerLocation(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        mModel.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModel);
    }

    @Override
    public void getFaktorImage(long ccDarkhastFaktor)
    {
        mModel.getFaktorImage(ccDarkhastFaktor);
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
    public void onErrorUseFakeLocation()
    {
        mView.get().onError(true , R.string.errorFakeLocation);
    }

    @Override
    public void onCheckServerTime(boolean valid, String message)
    {
        if (valid)
        {
            mModel.getTreasuryList(0 , Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
        }
        else
        {
            mView.get().onError(true, message);
        }
    }

    @Override
    public void onGetCustomerAddress(double latitude , double longitude)
    {
        if (latitude > 0 && longitude > 0)
        {
            mView.get().onGetCustomerAddress(latitude , longitude);
        }
        else
        {
            mView.get().showToast(R.string.errorFindCustomerAddress, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetFaktorImage(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel)
    {
        if (darkhastFaktorEmzaMoshtaryModel != null && darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage() != null && darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage().length > 0)
        {
            mView.get().onGetFaktorImage(darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage());
        }
        else
        {
            mView.get().showToast(R.string.notFoundAnyFaktorImage, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }


    @Override
    public void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int faktorRooz , int noeMasouliat)
    {
        if (faktorRooz == 0)
        {
            if (darkhastFaktorMoshtaryForoshandeModels.size() > 0)
            {
                mView.get().onGetFaktorRooz(darkhastFaktorMoshtaryForoshandeModels , noeMasouliat);
            }
            else
            {
                mView.get().showToast(R.string.emptyList, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
            }
        }

    }


    @Override
    public void onErrorAccessToLocation()
    {
        mView.get().showToast(R.string.errorAccessToLocation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onError(int resId)
    {
        mView.get().showAlertMessage(resId , Constants.FAILED_MESSAGE());
    }
}
