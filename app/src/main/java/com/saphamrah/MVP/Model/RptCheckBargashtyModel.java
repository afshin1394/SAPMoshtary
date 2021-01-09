package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.BaseMVP.RptCheckBargashtyMVP;
import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class RptCheckBargashtyModel implements RptCheckBargashtyMVP.ModelOps
{

    private RptCheckBargashtyMVP.RequiredPresenterOps mPresenter;


    public RptCheckBargashtyModel(RptCheckBargashtyMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getListBargashty()
    {
        BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        ArrayList<BargashtyModel> bargashtyModels = bargashtyDAO.getAllWithSum();
        mPresenter.onGetListBargashty(bargashtyModels);
    }

    @Override
    public void updateListBargashty()
    {
        final BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getListBargashty();
                    mPresenter.onSuccessUpdateListBargashty();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onErrorUpdateListBargashty();
                }
                return false;
            }
        });

        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3)
        {
            bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), "RptCheckBargashtyActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()), new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run(){
                            boolean deleteResult = bargashtyDAO.deleteAll();
                            boolean insertResult = bargashtyDAO.insertGroup(arrayListData);
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
                    mPresenter.onErrorUpdateListBargashty();
                    setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptCheckBargashtyModel", "RptCheckBargashtyActivity", "updateListBargashty", "onFailed");
                }
            });
        }
        else if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            boolean deleteResult = bargashtyDAO.deleteAll();
            final ArrayList<Integer> arrayListCounter = new ArrayList<>();
            final String[] foroshandehArray;
            if(noeMasouliat != 4)
            {
                if (foroshandehMamorPakhshModel.getCcForoshandehs().contains(","))
                {
                    foroshandehArray = foroshandehMamorPakhshModel.getCcForoshandehs().split(",");//از فروشنده مامور پخش
                }
                else
                {
                    foroshandehArray = new String[]{foroshandehMamorPakhshModel.getCcForoshandehs()};
                }
            }
            else
            {
                DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                ArrayList<String> ccForoshandehs = darkhastFaktorDAO.getccForoshandehForGetCheckBargashty();
                foroshandehArray = ccForoshandehs.toArray(new String[ccForoshandehs.size()]);
            }

            for (String strccForoshandeh : foroshandehArray)
            {
                bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), "RptCheckBargashtyActivity", strccForoshandeh, new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run(){
                                boolean insertResult = bargashtyDAO.insertGroup(arrayListData);
                                if (insertResult)
                                {
                                    arrayListCounter.add(1);
                                    if (arrayListCounter.size() == foroshandehArray.length)
                                    {
                                        Message message = new Message();
                                        message.arg1 = 1;
                                        handler.sendMessage(message);
                                    }
                                }
                                else
                                {
                                    Message message = new Message();
                                    message.arg1 = -1;
                                    handler.sendMessage(message);
                                }
                            }
                        };
                        thread.start();
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        mPresenter.onErrorUpdateListBargashty();
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptCheckBargashtyModel", "RptCheckBargashtyActivity", "updateListBargashty", "onFailed");
                    }
                });
            }
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
