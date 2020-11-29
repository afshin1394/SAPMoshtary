package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RptThreeMonthPurchaseMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;

import com.saphamrah.DAO.Rpt3MonthPurchaseDAO;
import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptThreeMonthPurchaseModel implements RptThreeMonthPurchaseMVP.ModelOps
{

    private RptThreeMonthPurchaseMVP.RequiredPresenterOps mPresenter;


    public RptThreeMonthPurchaseModel(RptThreeMonthPurchaseMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getList()
    {

        Rpt3MonthPurchaseDAO rptThreeMonthPurchaseDAO = new Rpt3MonthPurchaseDAO(BaseApplication.getContext());
        ArrayList<Rpt3MonthGetSumModel> rptThreeMonthPurchaseModels = rptThreeMonthPurchaseDAO.getSumByMoshtary();
//        ArrayList<Rpt3MonthPurchaseModel> rptThreeMonthPurchaseModels = rptThreeMonthPurchaseDAO.getAll();
        mPresenter.onGetList(rptThreeMonthPurchaseModels);
    }

    @Override
    public void getRizFaktor(int ccMoshtary) {
        Rpt3MonthPurchaseDAO rptThreeMonthPurchaseDAO = new Rpt3MonthPurchaseDAO(BaseApplication.getContext());
        ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels = rptThreeMonthPurchaseDAO.getAllByCcMoshtary(ccMoshtary);
        mPresenter.onGetRizFaktor(rpt3MonthPurchaseModels);
    }


    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }

    @Override
    public void updateData(String activityNameForLog) {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
        Rpt3MonthPurchaseDAO rptThreeMonthPurchaseDAO = new Rpt3MonthPurchaseDAO(BaseApplication.getContext());

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getList();
                    mPresenter.onUpdateData();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.failedUpdate();
                }
                return false;
            }
        });
        rptThreeMonthPurchaseDAO.fetchRptThreeMonthPurchas(BaseApplication.getContext(), activityNameForLog , foroshandehMamorPakhshDAO.getAll().get(0).getCcForoshandeh() , new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread()
                {
                    @Override
                    public void run(){
                        boolean deleteResult = rptThreeMonthPurchaseDAO.deleteAll();
                        boolean insertResult = rptThreeMonthPurchaseDAO.insertGroup(arrayListData);
                        Message message = new Message();
                        if (deleteResult && insertResult)
                        {
                            mPresenter.onUpdateData();
                            message.arg1 = 1;
                        }
                        else
                        {
                            message.arg1 = -1;
//                            sendThreadMessage(Constants.BULK_INSERT_FAILED() ,++ itemCounter);
                        }
                        handler.sendMessage(message);
                    }
                };
                thread.start();

            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.failedUpdate();
            }
        });
    }

    @Override
    public void onDestroy()
    {

    }



}
