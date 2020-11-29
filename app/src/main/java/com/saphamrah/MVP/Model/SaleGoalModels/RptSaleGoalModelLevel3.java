package com.saphamrah.MVP.Model.SaleGoalModels;

import android.util.Log;

import com.saphamrah.BaseMVP.RptSaleGoalLevel3MVP;
import com.saphamrah.BaseMVP.RptSaleGoalLevel1MVP;
import com.saphamrah.DAO.RptHadafForoshDAO;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;

import com.saphamrah.PubFunc.PubFunc;

import java.util.ArrayList;

public class RptSaleGoalModelLevel3 implements RptSaleGoalLevel3MVP.ModelOps {
    private RptSaleGoalLevel3MVP.RequiredPresenterOps mPresenter;

    public RptSaleGoalModelLevel3(RptSaleGoalLevel3MVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getSaleGoalReportLevel3(int Brand, int gorohKala) {
        Log.i("getSaleGoal", "getSaleGoalReportLevel3: ");
        RptHadafForoshDAO RptHadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
        ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels = RptHadafForoshDAO.getKalaByGorohAndBrand(Brand,gorohKala);
        mPresenter.onGetSaleGoalReportLevel3(rptKalaHadafForoshModels);

    }



    @Override
    public void getAllSaleGoalReportLevel3() {
        Log.i("getSaleGoal", "getSaleGoalReportLevel3: ");
        RptHadafForoshDAO RptHadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
        ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels = RptHadafForoshDAO.getAllGorohKalaKalasByBrand();
        mPresenter.onGetSaleGoalReportLevel3(rptKalaHadafForoshModels);
    }

    @Override
    public void getAllSaleGoalByBrandLevel3(int Brand) {
        Log.i("getSaleGoal", "getSaleGoalReportLevel3: ");
        RptHadafForoshDAO RptHadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
        ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels = RptHadafForoshDAO.getAllKalasByBrand(Brand);
        mPresenter.onGetSaleGoalReportLevel3(rptKalaHadafForoshModels);
    }


    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }
}
