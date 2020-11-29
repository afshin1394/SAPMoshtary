package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.AddCustomerApplyMVP;
import com.saphamrah.MVP.Model.AddCustomerApplyModel;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AddCustomerApplyPresenter implements AddCustomerApplyMVP.PresenterOps , AddCustomerApplyMVP.RequiredPresenterOps
{

    private WeakReference<AddCustomerApplyMVP.RequiredViewOps> mView;
    private AddCustomerApplyMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public AddCustomerApplyPresenter(AddCustomerApplyMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new AddCustomerApplyModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AddCustomerApplyMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getAddCustomerInfoModel()
    {
        mModel.getAddCustomerInfoModel();
    }

    @Override
    public void checkAddNewCustomer(AddCustomerInfoModel addCustomerInfoModel)
    {
        boolean validBaseInfo = checkBaseInfo(addCustomerInfoModel);
        boolean validAddress = checkAddresses(addCustomerInfoModel.getMoshtaryAddressModels());
        String nameFamily = addCustomerInfoModel.getFirstName() + " " + addCustomerInfoModel.getLastName();
        boolean validShomareHesab = checkShomareHesab(addCustomerInfoModel.getMoshtaryShomarehHesabModels() , nameFamily);
        if (validBaseInfo && validAddress && validShomareHesab)
        {
            mModel.insertNewCustomer(addCustomerInfoModel);
        }
    }

    private boolean checkBaseInfo(AddCustomerInfoModel addCustomerInfoModel)
    {
        boolean validData = true;
        if (addCustomerInfoModel != null)
        {
            if (addCustomerInfoModel.getFirstName()== null || addCustomerInfoModel.getFirstName().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorFirstName();
            }
            if (addCustomerInfoModel.getLastName() == null || addCustomerInfoModel.getLastName().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorLastName();
            }
            if (addCustomerInfoModel.getTabloName() == null || addCustomerInfoModel.getTabloName().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorTabloName();
            }
            if (addCustomerInfoModel.getNoeShakhsiatId() != null && addCustomerInfoModel.getNoeShakhsiatId().trim().equals("1")) // haghighi = 1
            {
                if (addCustomerInfoModel.getNationalCode().trim().length() > 0 && addCustomerInfoModel.getNationalCode().trim().length() != 10)
                {
                    validData = false;
                    mView.get().onErrorNationalCode();
                }
            }
            else if (addCustomerInfoModel.getNoeShakhsiatId() != null && addCustomerInfoModel.getNoeShakhsiatId().trim().equals("2")) // hoghoghi = 2
            {
                if (addCustomerInfoModel.getNationalCode().trim().length() > 0 && addCustomerInfoModel.getNationalCode().trim().length() != 11)
                {
                    validData = false;
                    mView.get().onErrorNationalCode();
                }
                else if (addCustomerInfoModel.getNationalCode().trim().length() == 11)
                {
                    if (addCustomerInfoModel.getNationalCode().trim().startsWith("4") || addCustomerInfoModel.getNationalCode().trim().startsWith("5"))
                    {
                        validData = false;
                        mView.get().onErrorNationalCode();
                    }
                }
            }

            if (addCustomerInfoModel.getMobile() == null || addCustomerInfoModel.getMobile().trim().equals("") || (addCustomerInfoModel.getMobile().trim().length() > 0 && (addCustomerInfoModel.getMobile().trim().length() != 11 || !addCustomerInfoModel.getMobile().trim().startsWith("09")) ) )
            {
                validData = false;
                mView.get().onErrorMobile();
            }
            if (addCustomerInfoModel.getNoeShakhsiatId() == null || addCustomerInfoModel.getNoeShakhsiatId().trim().equals("") || addCustomerInfoModel.getNoeShakhsiatTitle() == null || addCustomerInfoModel.getNoeShakhsiatTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeShakhsiat();
            }
            if (addCustomerInfoModel.getNoeFaaliatId() == null || addCustomerInfoModel.getNoeFaaliatId().trim().equals("") || addCustomerInfoModel.getNoeFaaliatTitle() == null || addCustomerInfoModel.getNoeFaaliatTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeFaaliat();
            }
            if (addCustomerInfoModel.getNoeSenfId() == null || addCustomerInfoModel.getNoeSenfId().trim().equals("") || addCustomerInfoModel.getNoeSenfTitle() == null || addCustomerInfoModel.getNoeSenfTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeSenf();
            }
            if (addCustomerInfoModel.getNoeVosolId() == null || addCustomerInfoModel.getNoeVosolId().trim().equals("") || addCustomerInfoModel.getNoeVosolTitle() == null || addCustomerInfoModel.getNoeVosolTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeVosol();
            }
            if (addCustomerInfoModel.getNoeHamlId() == null || addCustomerInfoModel.getNoeHamlId().trim().equals("") || addCustomerInfoModel.getNoeHamlTitle() == null || addCustomerInfoModel.getNoeHamlTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeHaml();
            }
        }
        else
        {
            validData = false;
            mView.get().showResourceError(false, R.string.error, R.string.errorPersonalInfo, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        return validData;
    }

    private boolean checkAddresses(ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        if (moshtaryAddressModels == null || moshtaryAddressModels.size() == 0)
        {
            mView.get().onErrorEmptyAddress();
            return false;
        }
        else
        {
            boolean validData = true;
            int countDarkhastAddress = 0;
            int countDarkhastTahvilAddress = 0;
            int countTahvilAddress = 0;
            for (MoshtaryAddressModel address : moshtaryAddressModels)
            {
                if (address != null && address.getCcNoeAddress() > 0)
                {
                    if (address.getCcNoeAddress() == Constants.ADDRESS_TYPE_DARKHAST())
                    {
                        countDarkhastAddress++;
                    }
                    if (address.getCcNoeAddress() == Constants.ADDRESS_TYPE_DARKHAST_TAHVIL())
                    {
                        countDarkhastTahvilAddress++;
                    }
                    if (address.getCcNoeAddress() == Constants.ADDRESS_TYPE_TAHVIL())
                    {
                        countTahvilAddress++;
                    }
                }
            }
            if (countDarkhastAddress > 0 && countTahvilAddress == 0)
            {
                mView.get().onErrorNeedTahvilAddress();
                validData = false;
            }
            if (countTahvilAddress > 0 && countDarkhastAddress == 0)
            {
                mView.get().onErrorNeedDarkhastAddress();
                validData = false;
            }
            if (countDarkhastAddress > 0 && countTahvilAddress > 0 && countDarkhastTahvilAddress > 0)
            {
                mView.get().onErrorExistAllTypeOfAddress();
                validData = false;
            }
            return validData;
        }
    }

    private boolean checkShomareHesab(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels , String customerNameFamily)
    {
        boolean validData = true;
        if (moshtaryShomarehHesabModels != null && moshtaryShomarehHesabModels.size() > 0)
        {
            for (MoshtaryShomarehHesabModel moshtaryShomarehHesabModel : moshtaryShomarehHesabModels)
            {
                if (!moshtaryShomarehHesabModel.getSahebanHesab().equals(customerNameFamily))
                {
                    validData = false;
                    mView.get().onErrorWrongNameSahebHesab();
                    return validData;
                }
            }
        }
        return validData;
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
    public void onGetAddCustomerInfoModel(AddCustomerInfoModel addCustomerInfoModel)
    {
        if (addCustomerInfoModel == null)
        {
            Log.d("fragment" , "on get addcustomerinfo and null");
        }
        else
        {
            Log.d("fragment" , "on get addcustomerinfo and not null");
        }
        mView.get().onGetAddCustomerInfoModel(addCustomerInfoModel);
    }

    @Override
    public void onSuccessInsertNewCustomer(AddCustomerInfoModel addCustomerInfoModel)
    {
        mView.get().onSuccessInsertNewCustomer(addCustomerInfoModel);
    }

    @Override
    public void onFailedInsertNewCustomer(int errorMessageResId)
    {
        mView.get().showToast(errorMessageResId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }

}
