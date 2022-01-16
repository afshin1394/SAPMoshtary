package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.BaseMVP.SaleReportMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptForoshDAO;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class SaleReportModel implements SaleReportMVP.ModelOps
{


    private SaleReportMVP.RequiredPresenterOps mPresenter;

    public SaleReportModel(SaleReportMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getSaleReport()
    {
        RptForoshDAO rptForoshDAO = new RptForoshDAO(mPresenter.getAppContext());
        ArrayList<RptForoshModel> rptForoshModels = rptForoshDAO.getAll();
        mPresenter.onGetSaleReport(rptForoshModels);
    }

    @Override
    public void updateSaleReport()
    {
        final RptForoshDAO rptForoshDAO = new RptForoshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        String today = new PubFunc().new DateUtils().todayDateGregorianWithSlash();
        String ccForoshandeh = String.valueOf(foroshandehMamorPakhshDAO.getIsSelect().getCcForoshandeh());
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());

        switch (serverIpModel.getWebServiceType()){
            case REST:
                rptForoshDAO.fetchAllrptAmarForosh(mPresenter.getAppContext(), "SaleReportActivity", ccForoshandeh, today, new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData) {
                        final Handler handler = new Handler(new Handler.Callback() {
                            @Override
                            public boolean handleMessage(Message msg) {
                                if (msg.arg1 == 1)
                                {
                                    Log.d("saleReport" , "on update sale report : " + arrayListData.get(0).toString());
                                    mPresenter.onGetSaleReport(arrayListData);
                                }
                                else
                                {
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
                                boolean deleteResult = rptForoshDAO.deleteAll();
                                boolean insertResult = rptForoshDAO.insertGroup(arrayListData);
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
                        setLogToDB(Constants.LOG_EXCEPTION(), error, "SaleReportModel", "SaleReportActivity", "updateSaleReport", "");
                        mPresenter.onNetworkError();
                    }
                });
                break;


            case gRPC:
                rptForoshDAO.fetchAllrptAmarForoshGrpc(mPresenter.getAppContext(), "SaleReportActivity", ccForoshandeh, today, new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData) {
                        final Handler handler = new Handler(new Handler.Callback() {
                            @Override
                            public boolean handleMessage(Message msg) {
                                if (msg.arg1 == 1)
                                {
                                    Log.d("saleReport" , "on update sale report : " + arrayListData.get(0).toString());
                                    mPresenter.onGetSaleReport(arrayListData);
                                }
                                else
                                {
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
                                boolean deleteResult = rptForoshDAO.deleteAll();
                                boolean insertResult = rptForoshDAO.insertGroup(arrayListData);
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
                        setLogToDB(Constants.LOG_EXCEPTION(), error, "SaleReportModel", "SaleReportActivity", "updateSaleReport", "");
                        mPresenter.onNetworkError();
                    }
                });
                break;

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
