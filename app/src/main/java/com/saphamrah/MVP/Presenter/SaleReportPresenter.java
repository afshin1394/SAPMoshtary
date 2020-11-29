package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.SaleReportMVP;
import com.saphamrah.MVP.Model.SaleReportModel;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class SaleReportPresenter implements SaleReportMVP.PresenterOps , SaleReportMVP.RequiredPresenterOps
{

    private WeakReference<SaleReportMVP.RequiredViewOps> mView;
    private SaleReportMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public SaleReportPresenter(SaleReportMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new SaleReportModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(SaleReportMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getSaleReport()
    {
        mModel.getSaleReport();
    }

    @Override
    public void updateSaleReport()
    {
        mModel.updateSaleReport();
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetSaleReport(ArrayList<RptForoshModel> rptForoshModels)
    {
        if (rptForoshModels != null && rptForoshModels.size() > 0)
        {
            float mablaghFaktorRooz = rptForoshModels.get(0).getSumMablaghFaktorRooz() / 1000000;
            float mablaghFaktorMah = (float)rptForoshModels.get(0).getSumMablaghFaktorMah() / 1000000;

            float mablaghMarjoeeRooz = (float)rptForoshModels.get(0).getSumMablaghMarjoeeRooz() / 1000000;
            float mablaghMarjoeeMah = rptForoshModels.get(0).getSumMablaghMarjoeeMah() / 1000000;

            mView.get().drawMablaghForoshChart(mablaghFaktorRooz , mablaghFaktorMah , mablaghMarjoeeRooz , mablaghMarjoeeMah);
            mView.get().drawCountForoshChart(rptForoshModels.get(0).getCountFaktorRooz(), rptForoshModels.get(0).getCountFaktorMah(), (int)rptForoshModels.get(0).getCountMarjoeeRooz(), (int)rptForoshModels.get(0).getCountMarjoeeMah());
        }
        else
        {
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onNetworkError()
    {
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }



}
