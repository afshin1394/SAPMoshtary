package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.PrintFaktorDAO;
import com.saphamrah.BaseMVP.PrintAndShareMVP;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.PrintFaktorModel;
import com.saphamrah.Network.RetrofitResponse;

import java.util.ArrayList;

public class PrintAndShareModel implements PrintAndShareMVP.ModelOps {

    private PrintAndShareMVP.RequiredPresenterOps mPresenter;
    private PrintFaktorDAO printFaktorDAO = new PrintFaktorDAO(BaseApplication.getContext());
    private ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());

    public PrintAndShareModel(PrintAndShareMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void update() {
        int ccAfrad  = foroshandehMamorPakhshDAO.getCCAfrad();

        final Handler handler = new Handler(msg -> {
            if (msg.arg1 == 1)
            {
                getAllPrintFaktor();
                mPresenter.onUpdateData();
            }
            else if (msg.arg1 == -1)
            {
                mPresenter.failedUpdate();
            }
            return false;
        });

        printFaktorDAO.fetchPrintFaktor(BaseApplication.getContext(), "printFaktor", ccAfrad, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread()
                {
                    @Override
                    public void run(){

                        boolean deleteResult = printFaktorDAO.deleteAll();
                        boolean insertResult = printFaktorDAO.insertGroup(arrayListData);
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
            public void onFailed(String type, String error) {
                mPresenter.failedUpdate();
            }
        });
    }

    @Override
    public void getAllPrintFaktor() {
        ArrayList<PrintFaktorModel> modelArrayList = printFaktorDAO.getAll();
        mPresenter.onGetAllPrintFaktor(modelArrayList);
    }
}
