package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.CustomerInfoMVP;
import com.saphamrah.MVP.Model.CustomerInfoModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomerInfoPresenter implements CustomerInfoMVP.PresenterOps , CustomerInfoMVP.RequiredPresenterOps
{

    private WeakReference<CustomerInfoMVP.RequiredViewOps> mView;
    private CustomerInfoMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public CustomerInfoPresenter(CustomerInfoMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new CustomerInfoModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(CustomerInfoMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getCustomerInfo(int ccMoshtary, int ccSazmanForosh)
    {
        if (ccMoshtary != -1)
        {
            mModel.getCustomerBaseInfo(ccMoshtary, ccSazmanForosh);
            mModel.getCustomerAddresses(ccMoshtary);
            mModel.getCustomerShomareHesab(ccMoshtary);
            mModel.getCustomerCredit(ccMoshtary);
        }
        else
        {
            mView.get().showNotFoundCustomerError();
        }
    }

    @Override
    public void checkNewChanges(int ccMoshtary , String newNationalCode, String newMasahateMaghaze, int newAnbar, String newSaateVisit)
    {
        int masahateMaghaze = 0;
        if (!newMasahateMaghaze.trim().equals(""))
        {
            try
            {
                masahateMaghaze = Integer.parseInt(newMasahateMaghaze);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                mView.get().showToast(R.string.errorInputMasahateMaghaze , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
                checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "CustomerInfoPresenter", "", "checkNewChanges", "");
                return;
            }
        }

        mModel.updateNewChange(ccMoshtary , newNationalCode , masahateMaghaze , newAnbar , newSaateVisit);
    }

    @Override
    public void checkNewAddressData(int ccMoshtary, int ccAddress, String telephone, String postalCode)
    {
        mModel.updateAddress(ccMoshtary, ccAddress, telephone, postalCode);
    }

    @Override
    public void getAnbarItems()
    {
        mModel.getAnbarItems();
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
    public void onGetCustomerBaseInfo(MoshtaryModel moshtaryModel)
    {
        mView.get().onGetCustomerBaseInfo(moshtaryModel);
    }

    @Override
    public void onGetNoeVosolHamlDarajeShakhsiat(String noeVosolName, String noeHamlName, String darajeName , String noeShakhsiat, int olaviat)
    {
        mView.get().onGetNoeVosolHamlDarajeShakhsiat(noeVosolName, noeHamlName, darajeName , noeShakhsiat, olaviat);
    }

    @Override
    public void onGetSaateVisitOlaviat(String saateVisit, String olaviat)
    {
        mView.get().onGetSaateVisitOlaviat(saateVisit, olaviat);
    }

    @Override
    public void onGetNoeSenfNoeMoshtary(String noeSenf, String noeMoshtary)
    {
        mView.get().onGetNoeSenfNoeMoshtary(noeSenf, noeMoshtary);
    }

    @Override
    public void onGetCustomerAddresses(ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        if (moshtaryAddressModels.size() > 0)
        {
            mView.get().onGetCustomerAddresses(moshtaryAddressModels);
        }
        else
        {
            mView.get().hideCustomerAddress();
        }
    }

    @Override
    public void onGetCustomerShomareHesab(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels)
    {
        if (moshtaryShomarehHesabModels.size() > 0)
        {
            mView.get().onGetCustomerShomareHesab(moshtaryShomarehHesabModels);
        }
        else
        {
            mView.get().hideCustomerShomareHesab();
        }
    }

    @Override
    public void onGetAnbarItems(ArrayList<Integer> itemIds, ArrayList<String> itemTitles)
    {
        mView.get().onGetAnbarItems(itemIds , itemTitles);
    }



    @Override
    public void onErrorNationalCode()
    {
        mView.get().onErrorNationalCode();
    }

    @Override
    public void onErrorSaateVisit()
    {
        mView.get().onErrorSaateVisit();
    }

    @Override
    public void onErrorUpdateCustomer()
    {
        mView.get().showToast(R.string.errorUpdateCustomer , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onFailedUpdateAddress()
    {
        mView.get().showToast(R.string.errorUpdateCustomer , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessUpdateCustomer()
    {
        mView.get().showToast(R.string.successfullyDoneOps , Constants.SUCCESS_MESSAGE() , Constants.DURATION_LONG());
    }
}
