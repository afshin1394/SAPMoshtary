package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.TizerMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.Rpt3MonthPurchaseDAO;
import com.saphamrah.DAO.TizerDAO;
import com.saphamrah.Model.TizerModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class TizersModel implements TizerMVP.ModelOps
{

    private TizerMVP.RequiredPresenterOps mPresenter;


    public TizersModel(TizerMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getListFolder()
    {

        TizerDAO tizerDAO = new TizerDAO(BaseApplication.getContext());
        ArrayList<String> tizerModels = tizerDAO.getFolder();
        Log.d("Tizer","tizerModels:"+tizerModels);
        mPresenter.onGetListFolder(tizerModels);
    }

    @Override
    public void updateData(String activityNameForLog) {
        TizerDAO tizerDAO = new TizerDAO(BaseApplication.getContext());


        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getListFolder();
                    mPresenter.onUpdateData();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.failedUpdate();
                }
                return false;
            }
        });


        tizerDAO.fetchTizer(BaseApplication.getContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread()
                {
                    @Override
                    public void run(){

                        boolean deleteResult = tizerDAO.deleteAll();
                        boolean insertResult = tizerDAO.insertGroup(arrayListData);
                        Message message = new Message();
                        if (deleteResult && insertResult)
                        {
                            mPresenter.onUpdateData();
                            message.arg1 = 1;
                        }
                        else
                        {
                            mPresenter.failedUpdate();
                            message.arg1 = -1;

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
    public void getListFile(String folderName)
    {

        TizerDAO tizerDAO = new TizerDAO(BaseApplication.getContext());
        ArrayList<TizerModel> tizerModels = tizerDAO.getFile(folderName);

        mPresenter.onGetListFile(tizerModels);
    }


    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(BaseApplication.getContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }




}
