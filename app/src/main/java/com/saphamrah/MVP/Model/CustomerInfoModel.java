package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.CustomerInfoMVP;
import com.saphamrah.DAO.GorohDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.DAO.MoshtaryShomarehHesabDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomerInfoModel implements CustomerInfoMVP.ModelOps
{

    private CustomerInfoMVP.RequiredPresenterOps mPresenter;

    public CustomerInfoModel(CustomerInfoMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getCustomerBaseInfo(int ccMoshtary, int ccSazmanForosh)
    {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
        
        mPresenter.onGetCustomerBaseInfo(moshtaryModel);

        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String noeHamlName = childParameterDAO.getTextByParameterNameAndValue(Constants.NOE_HAML() , String.valueOf(moshtaryModel.getCodeNoeHaml()));
        String noeVosolName = childParameterDAO.getTextByParameterNameAndValue(Constants.NOE_VOSOL_MOSHTARY() , String.valueOf(moshtaryModel.getCodeNoeVosolAzMoshtary()));
        //String darajeName = childParameterDAO.getTextByParameterNameAndValue(Constants.ROTBE() , String.valueOf(moshtaryModel.getDarajeh()));
        String noeShakhsiat = childParameterDAO.getTextByParameterNameAndValue(Constants.NOE_SHAKHSIAT() , String.valueOf(moshtaryModel.getCodeNoeShakhsiat()));
        mPresenter.onGetNoeVosolHamlDarajeShakhsiat(noeVosolName , noeHamlName , moshtaryModel.getNameDarajeh() , noeShakhsiat, moshtaryModel.getExtraProp_Olaviat());

		GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
		Log.d("CustomerInfoModel","CcNoeSenf()" + moshtaryModel.getCcNoeSenf() + " , CcNoeMoshtary()" + moshtaryModel.getCcNoeMoshtary());
        String noeSenf = gorohDAO.getByccGoroh(moshtaryModel.getCcNoeSenf()).get(0).getNameGoroh();
        String noeMoshtary = gorohDAO.getByccGoroh(moshtaryModel.getCcNoeMoshtary()).get(0).getNameGoroh();
        mPresenter.onGetNoeSenfNoeMoshtary(noeSenf , noeMoshtary);
    }


    @Override
    public void getCustomerAddresses(int ccMoshtary)
    {
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = moshtaryAddressDAO.getByccMoshtary(ccMoshtary);
        mPresenter.onGetCustomerAddresses(moshtaryAddressModels);
    }

    @Override
    public void getCustomerShomareHesab(int ccMoshtary)
    {
        MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = moshtaryShomarehHesabDAO.getAllByccMoshtary(ccMoshtary);
        mPresenter.onGetCustomerShomareHesab(moshtaryShomarehHesabModels);
    }

    @Override
    public void getCustomerCredit(int ccMoshtary)
    {
        MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = moshtaryEtebarSazmanForoshDAO.getByccMoshtary(ccMoshtary);

    }

    @Override
    public void getAnbarItems()
    {
        ArrayList<Integer> itemId = new ArrayList<>();
        ArrayList<String> itemTitle = new ArrayList<>(Arrays.asList(mPresenter.getAppContext().getResources().getStringArray(R.array.anbarItems)));
        itemId.add(1);
        itemId.add(0);
        mPresenter.onGetAnbarItems(itemId , itemTitle);
    }

    @Override
    public void updateNewChange(int ccMoshtary , String newNationalCode, int newMasahateMaghaze, int newAnbar, String newSaateVisit)
    {
        boolean validData = true;
        String nationalCode = "";
        String shenaseMeli = "";
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        if (newNationalCode.trim().length() > 0)
        {
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
            ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByParameterName(Constants.NOE_SHAKHSIAT());
            String codeNoeShakhsiat = String.valueOf(moshtaryDAO.getByccMoshtary(ccMoshtary).getCodeNoeShakhsiat());
            String nameNoeShakhsiat = "";
            for (ParameterChildModel parameter : childParameterModels)
            {
                if (codeNoeShakhsiat.equals(parameter.getValue()))
                {
                    nameNoeShakhsiat = parameter.getTxt();
                    break;
                }
            }
            if (nameNoeShakhsiat.equals(mPresenter.getAppContext().getResources().getString(R.string.realpersonality)))
            {
                nationalCode = newNationalCode;
                if (!new PubFunc().new NationalCodeUtil().checkNationalCode(newNationalCode))
                {
                    validData = false;
                    mPresenter.onErrorNationalCode();
                }
            }
            else if (nameNoeShakhsiat.equals(mPresenter.getAppContext().getResources().getString(R.string.legalpersonality)))
            {
                shenaseMeli = newNationalCode;
                if (!new PubFunc().new NationalCodeUtil().checkNationalEconomicalCode(newNationalCode))
                {
                    validData = false;
                    mPresenter.onErrorNationalCode();
                }
            }
        }

        if (validData)
        {
            boolean updateMoshtary = moshtaryDAO.updateNatCodeMasahatAnbar(ccMoshtary, nationalCode, shenaseMeli, newMasahateMaghaze, newAnbar);
            if (updateMoshtary)
            {
                mPresenter.onSuccessUpdateCustomer();
            }
            else
            {
                mPresenter.onErrorUpdateCustomer();
            }
        }
    }

    @Override
    public void updateAddress(int ccMoshtary, int ccAddress, String telephone, String postalCode)
    {
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        if (moshtaryAddressDAO.updateTelephonePostalCode(ccAddress , ccMoshtary , telephone , postalCode))
        {
            getCustomerAddresses(ccMoshtary);
        }
        else
        {
            mPresenter.onFailedUpdateAddress();
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }

}
