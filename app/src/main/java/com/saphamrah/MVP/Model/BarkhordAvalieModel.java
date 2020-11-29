package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.BarkhordAvalieMVP;
import com.saphamrah.DAO.BarkhordForoshandehBaMoshtaryDAO;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BarkhordAvalieModel implements BarkhordAvalieMVP.ModelOps
{

    private BarkhordAvalieMVP.RequiredPresenterOps mPresenter;

    public BarkhordAvalieModel(BarkhordAvalieMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void countBarkhordForToday(int ccMoshtary)
    {
        BarkhordForoshandehBaMoshtaryDAO barkhordForoshandehBaMoshtaryDAO = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        int countTodayBarkhord = barkhordForoshandehBaMoshtaryDAO.getCountTodayByccMoshtary(ccMoshtary);
        if (countTodayBarkhord > 0)
        {
            mPresenter.onGetCountTodayBarkhord();
        }
        else
        {
            mPresenter.onError(R.string.forceBarkhordAvalie);
        }
    }

    @Override
    public void getBarkhords(int ccMoshtary)
    {
        BarkhordForoshandehBaMoshtaryDAO dao = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhords = dao.getAllByccMoshtary(ccMoshtary);
        mPresenter.onGetBarkhords(barkhords);
    }

    @Override
    public void insertNewBarkhord(int ccMoshtary , String desc)
    {
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        BarkhordForoshandehBaMoshtaryDAO dao = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        BarkhordForoshandehBaMoshtaryModel model = new BarkhordForoshandehBaMoshtaryModel();
        model.setTarikh(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
        model.setTozihat(desc);
        model.setCcMoshtary(ccMoshtary);
        model.setExtraProp_IsOld(0);
        model.setCcForoshandeh(shared.getInt(shared.getCcForoshandeh() , 0));
        if (dao.insert(model))
        {
            getBarkhords(ccMoshtary);
            mPresenter.onSuccessInsertNewBarkhord();
        }
        else
        {
            mPresenter.onFailedInsertNewBarkhord();
        }
    }


    @Override
    public void removeBarkhord(int ccBarkhord, int ccMoshtary)
    {
        BarkhordForoshandehBaMoshtaryDAO dao = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        if (dao.deleteByccBarkhord(ccBarkhord, ccMoshtary))
        {
            getBarkhords(ccMoshtary);
        }
        else
        {
            mPresenter.onFailedRemoveBarkhord();
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
