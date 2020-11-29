package com.saphamrah.MVP.Model.MainFragmentViewPagerModel;

import android.util.Log;

import com.saphamrah.BaseMVP.MainViewPagerMVP.CountFaktorForoshMVP;
import com.saphamrah.BaseMVP.MainViewPagerMVP.MablaghTedadForoshFragmentsBaseMVP;
import com.saphamrah.DAO.RptForoshDAO;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.PubFunc.PubFunc;

import java.util.ArrayList;

public class CountfaktorForoshFragmentModel implements CountFaktorForoshMVP.ModelOps {
    private CountFaktorForoshMVP.RequiredPresenterOps mPresenter;

    public CountfaktorForoshFragmentModel(CountFaktorForoshMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getAmarForosh() {
        RptForoshDAO foroshDAO = new RptForoshDAO(mPresenter.getAppContext());
        ArrayList<RptForoshModel> rptForoshModels = foroshDAO.getAll();
        mPresenter.onGetAmarForosh(rptForoshModels);
        Log.i("GETAMAR", "getAmarForosh: "+rptForoshModels.size());
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType , message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }
}
