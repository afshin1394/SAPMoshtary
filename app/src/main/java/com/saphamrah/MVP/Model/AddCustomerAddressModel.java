package com.saphamrah.MVP.Model;


import com.google.gson.Gson;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.AddCustomerAddressMVP;
import com.saphamrah.DAO.MahalCodePostiDAO;
import com.saphamrah.DAO.MahalDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.PolygonForoshSatrDAO;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MahalCodePostiModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.AddCustomerShared;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;


public class AddCustomerAddressModel implements AddCustomerAddressMVP.ModelOps
{

    private AddCustomerAddressMVP.RequiredPresenterOps mPresenter;

    public AddCustomerAddressModel(AddCustomerAddressMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

	@Override
    public void getConfig()
    {
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        boolean requireCodePosti = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_REQUIER_CODE_POSTI).trim().equals("1");
        boolean requireTelephone = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_REQUIER_TELEPHONE).trim().equals("1");
        mPresenter.onGetConfig(requireCodePosti, requireTelephone);
    }
	
    @Override
    public void getOstanItems()
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_OSTAN , 1);
        mPresenter.onGetOstanItems(mahalModels);
    }

    @Override
    public void getShahrestanItems(int ccOstan)
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_SHAHRESTAN , ccOstan);
        mPresenter.onGetShahrestanItems(mahalModels);
    }

    @Override
    public void getBakhshItems(int shahrestanId)
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_BAKHSH , shahrestanId);
        mPresenter.onGetBakhshItems(mahalModels);
    }

    @Override
    public void getShahrItems(int bakhshId)
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_SHAHR , bakhshId);
        mPresenter.onGetShahrItems(mahalModels);
    }

    @Override
    public void getMantagheItems(int shahrId)
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_MANTAGHE , shahrId);
        mPresenter.onGetMantagheItems(mahalModels);
    }

    @Override
    public void getNoeAddressItems()
    {
        ArrayList<String> itemsTitle = new ArrayList<>(Arrays.asList(mPresenter.getAppContext().getResources().getStringArray(R.array.noeAddressItems)));
        ArrayList<Integer> itemsId = new ArrayList<>();
        itemsId.add(Constants.ADDRESS_TYPE_DARKHAST());
        itemsId.add(Constants.ADDRESS_TYPE_DARKHAST_TAHVIL());
        itemsId.add(Constants.ADDRESS_TYPE_TAHVIL());
        mPresenter.onGetNoeAddressItems(itemsId , itemsTitle);




    }

    @Override
    public void getSellPolygon()
    {
        PolygonForoshSatrDAO polygonForoshSatrDAO = new PolygonForoshSatrDAO(mPresenter.getAppContext());
        ArrayList<String> arrayListccPolygonForosh = polygonForoshSatrDAO.getAllccPolygonForosh();
        ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels = new ArrayList<>();
        String[] polygonColors = mPresenter.getAppContext().getResources().getStringArray(R.array.sellPolygonColors);
        if (arrayListccPolygonForosh.size() > 0)
        {
            for (int i = 0 ; i < arrayListccPolygonForosh.size() ; i++)
            {
                polygonForoshSatrModels = new ArrayList<>();
                polygonForoshSatrModels = polygonForoshSatrDAO.getAllByCcPolygonForosh(arrayListccPolygonForosh.get(i));
                if (i < polygonColors.length)
                {
                    mPresenter.onGetSellPolygon(polygonForoshSatrModels , polygonColors[i]);
                }
                else
                {
                    mPresenter.onGetSellPolygon(polygonForoshSatrModels , polygonColors[0]);
                }
            }
        }
        else
        {
            mPresenter.onGetSellPolygon(polygonForoshSatrModels , polygonColors[0]);
        }
    }

    @Override
    public void saveNewAddress(MoshtaryAddressModel moshtaryAddressModel)
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        AddCustomerInfoModel addCustomerInfoModel = shared.getAddCustomerInfoModel(shared.JSON_DATA() , "");
        if (addCustomerInfoModel == null)
        {
            mPresenter.onFailedSaveMoshtaryAddress(R.string.notFoundCustomerInfoShared);
        }
        else
        {
            addCustomerInfoModel.getMoshtaryAddressModels().add(moshtaryAddressModel);
            shared.removeAll();
            Gson json = new Gson();
            if (shared.putString(shared.JSON_DATA() , json.toJson(addCustomerInfoModel)))
            {
                addCustomerInfoModel = shared.getAddCustomerInfoModel(shared.JSON_DATA() , "");
                int size = addCustomerInfoModel.getMoshtaryAddressModels().size();
                mPresenter.onSuccessfullySavedMoshtaryAddress(addCustomerInfoModel.getMoshtaryAddressModels().get(size-1));
            }
            else
            {
                mPresenter.onFailedSaveMoshtaryAddress(R.string.errorSaveData);
            }
        }
    }

    @Override
    public void deleteAddress(int position)
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        AddCustomerInfoModel addCustomerInfoModel = shared.getAddCustomerInfoModel(shared.JSON_DATA() , "");
        if (addCustomerInfoModel == null)
        {
            mPresenter.onFailedDeleteAddress();
        }
        else
        {
            try
            {
                addCustomerInfoModel.getMoshtaryAddressModels().remove(position);
                shared.removeAll();
                Gson json = new Gson();
                shared.putString(shared.JSON_DATA() , json.toJson(addCustomerInfoModel));
                mPresenter.onSuccessDeleteAddress(position);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "AddCustomerAddressModel", "", "deleteAddress", "");
                mPresenter.onFailedDeleteAddress();
            }
        }
    }

    @Override
    public void getAddCustomerInfo()
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        mPresenter.onGetAddCustomerInfo(shared.getAddCustomerInfoModel(shared.JSON_DATA() , ""));
    }

    @Override
    public void getMahalCodePosti(int ccMahal) {
        ArrayList<MahalCodePostiModel> mahalCodePostiModels = new ArrayList<>();
        MahalCodePostiDAO mahalCodePostiDAO = new MahalCodePostiDAO(BaseApplication.getContext());
        mahalCodePostiModels = mahalCodePostiDAO.getCodePostiCcmahal(ccMahal);
        mPresenter.onMahalCodePosti(mahalCodePostiModels);
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
