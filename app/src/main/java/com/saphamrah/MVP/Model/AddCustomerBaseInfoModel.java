package com.saphamrah.MVP.Model;


import android.util.Log;

import com.google.gson.Gson;
import com.saphamrah.BaseMVP.AddCustomerBaseInfoMVP;
import com.saphamrah.DAO.NoeFaaliatForMoarefiMoshtaryJadidDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.GorohDAO;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.GorohModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.AddCustomerShared;
import com.saphamrah.Utils.Constants;


import java.util.ArrayList;
import java.util.Arrays;

public class AddCustomerBaseInfoModel implements AddCustomerBaseInfoMVP.ModelOps
{

    private AddCustomerBaseInfoMVP.RequiredPresenterOps mPresenter;

    public AddCustomerBaseInfoModel(AddCustomerBaseInfoMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

	@Override
    public void getConfig()
    {
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        boolean requireCodeMeli = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_REQUIER_CODE_MELI).trim().equals("1");
        boolean requireMobile = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_REQUIER_MOBILE).trim().equals("1");
        boolean requireMasahat = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_REQUIER_MASAHAT).trim().equals("1");
        mPresenter.onGetConfig(requireCodeMeli, requireMobile, requireMasahat);
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
    public void getRotbeItems()
    {
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByParameterName(Constants.ROTBE());
        ArrayList<Integer> itemId = new ArrayList<>();
        ArrayList<String> itemTitle = new ArrayList<>();
        if (childParameterModels.size() > 0)
        {
            for (ParameterChildModel childParameterModel : childParameterModels)
            {
                itemId.add(Integer.valueOf(childParameterModel.getValue()));
                itemTitle.add(childParameterModel.getTxt());
            }
        }
        mPresenter.onGetRotbeItems(itemId , itemTitle);
    }

    @Override
    public void getNoeShakhsiatItems()
    {
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByParameterName(Constants.NOE_SHAKHSIAT());
        ArrayList<Integer> itemId = new ArrayList<>();
        ArrayList<String> itemTitle = new ArrayList<>();
        if (childParameterModels.size() > 0)
        {
            for (ParameterChildModel childParameterModel : childParameterModels)
            {
                itemId.add(Integer.valueOf(childParameterModel.getValue()));
                itemTitle.add(childParameterModel.getTxt());
            }
        }
        mPresenter.onGetNoeShakhsiatItems(itemId , itemTitle);
    }

    @Override
    public void getNoeFaaliatItems()
    {
        NoeFaaliatForMoarefiMoshtaryJadidDAO noeFaaliatForMoarefiMoshtaryJadidDAO = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());
        String ccGorohsOfNoeFaaliat = noeFaaliatForMoarefiMoshtaryJadidDAO.getDistinctccNoeMoshtary();

        GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        ArrayList<GorohModel> noeFaaliatItems = gorohDAO.getByccGorohs(ccGorohsOfNoeFaaliat);
        mPresenter.onGetNoeFaaliatItems(noeFaaliatItems);
    }

    @Override
    public void getNoeSenfItems(int ccGorohLink)
    {
        NoeFaaliatForMoarefiMoshtaryJadidDAO noeFaaliatForMoarefiMoshtaryJadidDAO = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());
        String ccGorohsOfSenf = noeFaaliatForMoarefiMoshtaryJadidDAO.getDistinctccSenfMoshtary(ccGorohLink);

        GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        ArrayList<GorohModel> noeSenfItems = gorohDAO.getccNoeSenfByccGorohLink(ccGorohsOfSenf);
        mPresenter.onGetNoeSenfItems(noeSenfItems);
    }

    @Override
    public void getNoeHamlItems()
    {
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String parent = childParameterDAO.getParentsByParameterName(Constants.NOE_HAML_MOSHTARY_JADID());
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByParentsId(parent);
        ArrayList<Integer> itemId = new ArrayList<>();
        ArrayList<String> itemTitle = new ArrayList<>();
        Log.d("AddCustomerBase","childParameterModels:"+childParameterModels+"parent:"+parent);
        if (childParameterModels.size() > 0)
        {
            for (ParameterChildModel childParameterModel : childParameterModels)
            {
                itemId.add(Integer.valueOf(childParameterModel.getValue()));
                itemTitle.add(childParameterModel.getTxt());
            }
        }
        mPresenter.onGetNoeHaml(itemId , itemTitle);
    }

    @Override
    public void getNoeVosolItems(String ccNoeFaaliat)
    {
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String parent = "";
        if (ccNoeFaaliat == null || ccNoeFaaliat.trim().equals("") || ccNoeFaaliat.trim().equals("347") || ccNoeFaaliat.trim().equals("348"))
        {
            parent = childParameterDAO.getParentsByParameterName(Constants.NOE_VOSOL_MOSHTARY_JADID_KHORDE_OMDE());
        }
        else
        {
            parent = childParameterDAO.getParentsByParameterName(Constants.NOE_VOSOL_MOSHTARY_JADID());
        }
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByParentsId(parent);
        ArrayList<Integer> itemId = new ArrayList<>();
        ArrayList<String> itemTitle = new ArrayList<>();
        if (childParameterModels.size() > 0)
        {
            for (ParameterChildModel childParameterModel : childParameterModels)
            {
                itemId.add(Integer.valueOf(childParameterModel.getValue()));
                itemTitle.add(childParameterModel.getTxt());
            }
        }
        mPresenter.onGetNoeVosol(itemId , itemTitle);
    }

    @Override
    public void saveCustomerBaseInfo(AddCustomerInfoModel addCustomerInfoModel)
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        shared.removeAll();
        Gson json = new Gson();
        String jsonData = json.toJson(addCustomerInfoModel);
        if (shared.putString(shared.JSON_DATA() , jsonData))
        {
            mPresenter.onSuccessfullySavedCustomerBaseInfo();
        }
        else
        {
            mPresenter.onFailedSaveCustomerBaseInfo();
        }
    }

    @Override
    public void getBaseInfoFromShared()
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        AddCustomerInfoModel addCustomerInfoModel = shared.getAddCustomerInfoModel(shared.JSON_DATA() , "");
        try
        {
            Gson json = new Gson();
            String data = json.toJson(addCustomerInfoModel);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        mPresenter.onGetBaseInfoFromShared(addCustomerInfoModel);
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
