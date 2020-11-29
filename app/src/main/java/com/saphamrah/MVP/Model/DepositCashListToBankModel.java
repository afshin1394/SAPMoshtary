package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.DepositCashListToBankMVP;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.MarkazShomarehHesabDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.TafkikJozeDAO;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.TafkikJozeModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DepositCashListToBankModel implements DepositCashListToBankMVP.ModelOps
{

    private DepositCashListToBankMVP.RequiredPresenterOps mPresenter;

    public DepositCashListToBankModel(DepositCashListToBankMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getAllTafkik()
    {
        TafkikJozeDAO tafkikJozeDAO = new TafkikJozeDAO(mPresenter.getAppContext());
        ArrayList<TafkikJozeModel> tafkikJozeModels = tafkikJozeDAO.getAll();
        mPresenter.onGetAllTafkik(tafkikJozeModels);
    }

    @Override
    public void getAllShomareHesab()
    {
        MarkazShomarehHesabDAO markazShomarehHesabDAO = new MarkazShomarehHesabDAO(mPresenter.getAppContext());
        List<MarkazShomarehHesabModel> markazShomarehHesabModels = markazShomarehHesabDAO.getAll();
        mPresenter.onGetAllShomareHesab(markazShomarehHesabModels);
    }

    @Override
    public void getMablaghMandehVajhNaghd(long ccTafkikJoze)
    {
        String codeNoeVosolVajhNaghd = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_VOSOL_VAJH_NAGHD());
        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        double sumMablaghVojohNaghd = dariaftPardakhtDarkhastFaktorPPCDAO.getSumMablaghVajhNaghd(ccTafkikJoze, codeNoeVosolVajhNaghd);
        mPresenter.onGetMablaghMandehVajhNaghd(sumMablaghVojohNaghd);
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
