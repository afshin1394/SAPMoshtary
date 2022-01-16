package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.BaseMVP.RptDarkhastFaktorVazeiatMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptDarkhastFaktorVazeiatPPCDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptDarkhastFaktorVazeiatModel implements RptDarkhastFaktorVazeiatMVP.ModelOps
{

    private RptDarkhastFaktorVazeiatMVP.RequiredPresenterOps mPresenter;


    public RptDarkhastFaktorVazeiatModel(RptDarkhastFaktorVazeiatMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getDarkhastFaktorVazeiatList()
    {
        RptDarkhastFaktorVazeiatPPCDAO rptDarkhastFaktorVazeiatPPCDAO = new RptDarkhastFaktorVazeiatPPCDAO(mPresenter.getAppContext());
        ArrayList<RptDarkhastFaktorVazeiatPPCModel> darkhastFaktorVazeiatModels = rptDarkhastFaktorVazeiatPPCDAO.getAll();
        mPresenter.onGetDarkhastFaktorVazeiatList(darkhastFaktorVazeiatModels);
    }


    @Override
    public void updateData()
    {
        final RptDarkhastFaktorVazeiatPPCDAO rptDarkhastFaktorVazeiatPPCDAO = new RptDarkhastFaktorVazeiatPPCDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        String ccForoshandeh = String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh());
        String ccMamorPakhsh = String.valueOf(foroshandehMamorPakhshModel.getCcMamorPakhsh());
        if(noeMasouliat == 4 || noeMasouliat == 5)
        {
            ccForoshandeh = "0";
        }
        else
        {
            ccMamorPakhsh = "0";
        }

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getDarkhastFaktorVazeiatList();
                    mPresenter.onSuccessUpdateData();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onErrorUpdateData();
                }
                return false;
            }
        });
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case REST:
                rptDarkhastFaktorVazeiatPPCDAO.fetchRptDarkhastFaktorVazeiat(mPresenter.getAppContext(), "RptDarkhastFaktorVazeiatActivity", ccForoshandeh, ccMamorPakhsh, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = rptDarkhastFaktorVazeiatPPCDAO.deleteAll();
                                boolean insertResult = rptDarkhastFaktorVazeiatPPCDAO.insertGroup(arrayListData);
                                Message message = new Message();
                                if (deleteResult && insertResult)
                                {
                                    message.arg1 = 1;
                                }
                                else
                                {
                                    message.arg1 = -1;
                                }
                                handler.sendMessage(message);
                            }
                        };
                        thread.start();
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onErrorUpdateData();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptDarkhastFaktorVazeiatPPCModel", "RptDarkhastFaktorVazeiatActivity", "updateData", "onFailed");
                    }
                });
             break;

            case gRPC:
                rptDarkhastFaktorVazeiatPPCDAO.fetchRptDarkhastFaktorVazeiatGrpc(mPresenter.getAppContext(), "RptDarkhastFaktorVazeiatActivity", ccForoshandeh, ccMamorPakhsh, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                boolean deleteResult = rptDarkhastFaktorVazeiatPPCDAO.deleteAll();
                                boolean insertResult = rptDarkhastFaktorVazeiatPPCDAO.insertGroup(arrayListData);
                                Message message = new Message();
                                if (deleteResult && insertResult)
                                {
                                    message.arg1 = 1;
                                }
                                else
                                {
                                    message.arg1 = -1;
                                }
                                handler.sendMessage(message);
                            }
                        };
                        thread.start();
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onErrorUpdateData();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptDarkhastFaktorVazeiatPPCModel", "RptDarkhastFaktorVazeiatActivity", "updateData", "onFailed");
                    }
                });
                break;
        }

    }


    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }

    @Override
    public void onDestroy()
    {

    }


}
