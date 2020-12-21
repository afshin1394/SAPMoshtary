package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RptThreeMonth.RptThreeMonthMVP;
import com.saphamrah.BaseMVP.RptThreeMonth.RptThreeMonthRizFaktorMVP;
import com.saphamrah.DAO.Rpt3MonthPurchaseDAO;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;
import com.saphamrah.PubFunc.PubFunc;

import java.util.ArrayList;

public class RptThreeMonthRizFaktorModel implements RptThreeMonthRizFaktorMVP.ModelOps{
    private static final String TAG = RptThreeMonthRizFaktorModel.class.getSimpleName();
    private RptThreeMonthRizFaktorMVP.RequiredPresenterOps mPresenter;


    public RptThreeMonthRizFaktorModel(RptThreeMonthRizFaktorMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }




    @Override
    public void getFilteredListByFactorNu(int ccMoshtary, String filter) {
        Rpt3MonthPurchaseDAO rpt3MonthPurchaseDAO=new Rpt3MonthPurchaseDAO(mPresenter.getAppContext());
        ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels=rpt3MonthPurchaseDAO.getFilteredListByFactorNu(ccMoshtary,filter);
        Log.i(TAG, "getRizFaktorFilteredByCode: "+filter);
        Log.i(TAG, "getRizFaktorFilteredByCode: "+ccMoshtary);
        Log.i(TAG, "getRizFaktorFilteredByCode: "+rpt3MonthPurchaseModels.size());
        mPresenter.onGetFilteredRizFaktor(rpt3MonthPurchaseModels,filter);
    }

    @Override
    public void getRizFaktor(int ccMoshtary) {
        Rpt3MonthPurchaseDAO rptThreeMonthPurchaseDAO = new Rpt3MonthPurchaseDAO(BaseApplication.getContext());
        ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels = rptThreeMonthPurchaseDAO.getAllByCcMoshtary(ccMoshtary);
        mPresenter.onGetRizFaktor(rpt3MonthPurchaseModels);
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }



    @Override
    public void onDestroy() {

    }
}
