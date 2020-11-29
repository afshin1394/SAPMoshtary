package com.saphamrah.MVP.Model.SaleGoalModels;

import android.util.Log;

import com.saphamrah.BaseMVP.RptSaleGoalLevel2MVP;
import com.saphamrah.DAO.RptHadafForoshDAO;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;

import com.saphamrah.PubFunc.PubFunc;

import java.util.ArrayList;

public class RptSaleGoalModelLevel2 implements RptSaleGoalLevel2MVP.ModelOps {
    private RptSaleGoalLevel2MVP.RequiredPresenterOps mPresenter;


    public RptSaleGoalModelLevel2(RptSaleGoalLevel2MVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getAllSaleGoalReportsLevel2() {
        RptHadafForoshDAO RptHadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
        ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels = RptHadafForoshDAO.getAllBrandGorohKala();
        mPresenter.onGetSaleGoalReportLevel2(rptGorohKalaHadafForoshModels);
    }

    @Override
    public void getSaleGoalReportLevel2(int ccBrand) {
        Log.i("getSaleGoal", "getSaleGoalReportLevel2: ");
        RptHadafForoshDAO RptHadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
        ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels = RptHadafForoshDAO.getGorohKalaByBrand(ccBrand);
        mPresenter.onGetSaleGoalReportLevel2(rptGorohKalaHadafForoshModels);
    }



    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger ();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }
}
