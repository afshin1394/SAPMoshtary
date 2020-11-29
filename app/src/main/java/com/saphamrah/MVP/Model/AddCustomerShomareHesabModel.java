package com.saphamrah.MVP.Model;

import com.google.gson.Gson;
import com.saphamrah.BaseMVP.AddCustomerShomareHesabMVP;
import com.saphamrah.DAO.BankDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.NoeHesabDAO;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.NoeHesabModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.AddCustomerShared;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AddCustomerShomareHesabModel implements AddCustomerShomareHesabMVP.ModelOps
{

    private AddCustomerShomareHesabMVP.RequiredPresenterOps mPresenter;

    public AddCustomerShomareHesabModel(AddCustomerShomareHesabMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getBanksItems()
    {
        BankDAO bankDAO = new BankDAO(mPresenter.getAppContext());
        ArrayList<BankModel> bankModels = bankDAO.getAll();
        mPresenter.onGetBanksItems(bankModels);
    }

    @Override
    public void getNoeHesabItems()
    {
        NoeHesabDAO noeHesabDAO = new NoeHesabDAO(mPresenter.getAppContext());
        ArrayList<NoeHesabModel> noeHesabModels = noeHesabDAO.getAll();
        mPresenter.onGetNoeHesabItems(noeHesabModels);
    }

    @Override
    public void getShartBardashtItems()
    {
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByParameterName(Constants.SHART_BARDASHT());
        ArrayList<String> itemsTitle = new ArrayList<>();
        ArrayList<Integer> itemsId = new ArrayList<>();
        for (ParameterChildModel childParameterModel : childParameterModels)
        {
            itemsId.add(Integer.parseInt(childParameterModel.getValue()));
            itemsTitle.add(childParameterModel.getTxt());
        }
        mPresenter.onGetShartBardashtItems(itemsId , itemsTitle);
    }

    @Override
    public void getAddCustomerInfo()
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        mPresenter.onGetAddCustomerInfo(shared.getAddCustomerInfoModel(shared.JSON_DATA() , ""));
    }

    @Override
    public void saveNewMoshtaryShomareHesab(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel)
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        AddCustomerInfoModel addCustomerInfoModel = shared.getAddCustomerInfoModel(shared.JSON_DATA() , "");
        if (addCustomerInfoModel == null)
        {
            mPresenter.onFailedSaveNewShomareHesab(R.string.notFoundCustomerInfoShared);
        }
        else
        {
            addCustomerInfoModel.getMoshtaryShomarehHesabModels().add(moshtaryShomarehHesabModel);
            shared.removeAll();
            Gson json = new Gson();
            if (shared.putString(shared.JSON_DATA() , json.toJson(addCustomerInfoModel)))
            {
                addCustomerInfoModel = shared.getAddCustomerInfoModel(shared.JSON_DATA() , "");
                int size = addCustomerInfoModel.getMoshtaryShomarehHesabModels().size();
                mPresenter.onSuccessSaveNewSomareHesab(addCustomerInfoModel.getMoshtaryShomarehHesabModels().get(size-1));
            }
            else
            {
                mPresenter.onFailedSaveNewShomareHesab(R.string.errorSaveData);
            }
        }
    }

    @Override
    public void deleteShomareHesab(int position)
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        AddCustomerInfoModel addCustomerInfoModel = shared.getAddCustomerInfoModel(shared.JSON_DATA() , "");
        if (addCustomerInfoModel == null)
        {
            mPresenter.onFailedDeleteShomareHesab();
        }
        else
        {
            try
            {
                addCustomerInfoModel.getMoshtaryShomarehHesabModels().remove(position);
                shared.removeAll();
                Gson json = new Gson();
                shared.putString(shared.JSON_DATA() , json.toJson(addCustomerInfoModel));
                mPresenter.onSuccessDeleteShomareHesab(position);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "AddCustomerAddressModel", "", "deleteShomareHesab", "");
                mPresenter.onFailedDeleteShomareHesab();
            }
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }
}
