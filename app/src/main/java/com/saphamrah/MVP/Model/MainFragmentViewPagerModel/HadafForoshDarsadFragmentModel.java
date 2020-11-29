package com.saphamrah.MVP.Model.MainFragmentViewPagerModel;

import android.util.Log;

import com.saphamrah.BaseMVP.MainViewPagerMVP.HadafForoshDarsadFragmentMVP;
import com.saphamrah.BaseMVP.MainViewPagerMVP.HadafForoshTedadyFragmentMVP;
import com.saphamrah.DAO.RptHadafForoshDAO;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.PubFunc.PubFunc;

public class HadafForoshDarsadFragmentModel implements HadafForoshDarsadFragmentMVP.ModelOps {
    private HadafForoshDarsadFragmentMVP.RequiredPresenterOps mPresenter;

    public HadafForoshDarsadFragmentModel(HadafForoshDarsadFragmentMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getHadafForoshTedadyKole() {
        RptHadafForoshDAO hadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
        BaseHadafForoshModel rptBrandHadafForoshModel = hadafForoshDAO.getJustSumHadafForoshBrands();
        Log.i("MainFirstFragmentModel", "getHadafForoshTedadyKole: "+ rptBrandHadafForoshModel.getTedadForoshRooz());
        mPresenter.onGetHadafForoshTedadyKole(rptBrandHadafForoshModel);
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
