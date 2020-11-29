package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.AddCustomerShomareHesabMVP;
import com.saphamrah.MVP.Model.AddCustomerShomareHesabModel;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.NoeHesabModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AddCustomerShomareHesabPresenter implements AddCustomerShomareHesabMVP.PresenterOps , AddCustomerShomareHesabMVP.RequiredPresenterOps
{

    private WeakReference<AddCustomerShomareHesabMVP.RequiredViewOps> mView;
    private AddCustomerShomareHesabMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public AddCustomerShomareHesabPresenter(AddCustomerShomareHesabMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new AddCustomerShomareHesabModel(this);
    }

    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AddCustomerShomareHesabMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getBanksItems()
    {
        mModel.getBanksItems();
    }

    @Override
    public void getNoeHesabItems()
    {
        mModel.getNoeHesabItems();
    }

    @Override
    public void getShartBardashtItems()
    {
        mModel.getShartBardashtItems();
    }

    @Override
    public void getAddCustomerInfo()
    {
        mModel.getAddCustomerInfo();
    }

    @Override
    public void checkNewShomareHesab(MoshtaryShomarehHesabModel shomarehHesab , String customerNameFamily , ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels)
    {
        if (shomarehHesab != null)
        {
            boolean validData = true;
            if (shomarehHesab.getCcBank() <= 0 || shomarehHesab.getNameBank() == null || shomarehHesab.getNameBank().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorBank();
            }
            if (shomarehHesab.getCcNoeHesab() <= 0 || shomarehHesab.getNameNoeHesab() == null || shomarehHesab.getNameNoeHesab().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeHesab();
            }
            if(shomarehHesab.getShartBardashtAzHesab() <= 0)
            {
                validData = false;
                mView.get().onErrorShartBardasht();
            }
            if (shomarehHesab.getNameShobeh() == null || shomarehHesab.getNameShobeh().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNameShobe();
            }
            if (shomarehHesab.getCodeShobeh() == null || shomarehHesab.getCodeShobeh().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorCodeShobe();
            }
            if (shomarehHesab.getShomarehHesab() == null || shomarehHesab.getShomarehHesab().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorShomareHesab();
            }
            else
            {
                for (MoshtaryShomarehHesabModel moshtaryShomarehHesabModel : moshtaryShomarehHesabModels)
                {
                    if (moshtaryShomarehHesabModel.getShomarehHesab().trim().equals(shomarehHesab.getShomarehHesab()))
                    {
                        validData = false;
                        mView.get().onErrorDuplicateShomareHesab();
                    }
                }
            }
            if (shomarehHesab.getSahebanHesab() == null || shomarehHesab.getSahebanHesab().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorSahebHesab();
            }
            else if (!shomarehHesab.getSahebanHesab().equals(customerNameFamily))
            {
                validData = false;
                mView.get().onErrorWrongNameSahebHesab();
            }
            if (validData)
            {
                mModel.saveNewMoshtaryShomareHesab(shomarehHesab);
            }
        }
        else
        {
            mView.get().showToast(R.string.errorShomareHesabInfo, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void deleteShomareHesab(int position)
    {
        if (position >= 0)
        {
            mModel.deleteShomareHesab(position);
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetBanksItems(ArrayList<BankModel> bankModels)
    {
        mView.get().onGetBanksItems(bankModels);
    }

    @Override
    public void onGetNoeHesabItems(ArrayList<NoeHesabModel> noeHesabModels)
    {
        mView.get().onGetNoeHesabItems(noeHesabModels);
    }

    @Override
    public void onGetShartBardashtItems(ArrayList<Integer> itemIds, ArrayList<String> itemTitles)
    {
        mView.get().onGetShartBardashtItems(itemIds , itemTitles);
    }

    @Override
    public void onGetAddCustomerInfo(AddCustomerInfoModel addCustomerInfoModel)
    {
        mView.get().onSuccessGetAddCustomerInfo(addCustomerInfoModel);
    }

    @Override
    public void onSuccessSaveNewSomareHesab(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel)
    {
        mView.get().onSuccessSaveNewSomareHesab(moshtaryShomarehHesabModel);
    }

    @Override
    public void onFailedSaveNewShomareHesab(int errorMessageResId)
    {
        mView.get().showToast(errorMessageResId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessDeleteShomareHesab(int position)
    {
        mView.get().onSuccessDeleteShomareHesab(position);
    }

    @Override
    public void onFailedDeleteShomareHesab()
    {
        mView.get().showToast(R.string.errorDeleteShomareHesab, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }
}
