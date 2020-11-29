package com.saphamrah.MVP.Model.SaleGoalModels;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.BaseMVP.RptSaleGoalLevel1MVP;
import com.saphamrah.BaseMVP.RptSaleGoalLevel1MVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptHadafForoshDAO;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptSaleGoalModelLevel1 implements RptSaleGoalLevel1MVP.ModelOps{
    private RptSaleGoalLevel1MVP.RequiredPresenterOps mPresenter;
    private RptSaleGoalLevel1MVP.ModelOps mModel;

    public RptSaleGoalModelLevel1(RptSaleGoalLevel1MVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getSaleGoalReport() {
        RptHadafForoshDAO RptHadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
        ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels = RptHadafForoshDAO.getHadafForoshAllBrands();
        mPresenter.onGetSaleGoalReport(rptBrandHadafForoshModels);

    }

    @Override
    public void updateSaleGoalReport() {
        final RptHadafForoshDAO RptHadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        String ccForoshandeh = String.valueOf(foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh().getCcForoshandeh());
        Log.i("foroshande", "updateSaleGoalReport: "+ccForoshandeh);
        RptHadafForoshDAO.fetchAllrpHadafeForosh(mPresenter.getAppContext(), "SaleGoalReportActivity", ccForoshandeh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Log.i("updateSaleGoalReport", "onSuccess: ");


                final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        Log.i("massage.arg1", "handleMessage: "+msg.arg1);
                        if (msg.arg1 == 1)
                        {
//                            Log.d("saleGoalReport" , "on update sale Goal report : " + arrayListData.get(0).toString());

                            ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels =RptHadafForoshDAO.getHadafForoshAllBrands();
                            mPresenter.onSuccessUpdateSaleGoalReport();
                            mPresenter.onGetSaleGoalReport(rptBrandHadafForoshModels);


                        }
                        else
                        {
                            mPresenter.onErrorUpdateSaleGoalReport();
                            mPresenter.onNetworkError();
                        }
                        return false;
                    }
                });

                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = RptHadafForoshDAO.deleteAll();
                        boolean insertResult = RptHadafForoshDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            Message msg = new Message();
                            msg.arg1 = 1;
                            handler.sendMessage(msg);
                        }
                        else
                        {
                            Message msg = new Message();
                            msg.arg1 = -1;
                            handler.sendMessage(msg);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                Log.i("SaleGoal", "onFailed: Sale Goal Failed");
                setLogToDB(Constants.LOG_EXCEPTION(), error, "SaleReportModel", "SaleReportActivity", "updateSaleReport", "");
                mPresenter.onNetworkError();
            }
        });
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger ();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }
}
