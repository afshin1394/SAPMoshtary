package com.saphamrah.MVP.Model;

import android.os.Message;
import android.util.Log;

import com.saphamrah.BaseMVP.SabtAmvalMVP;
import com.saphamrah.DAO.AmvalDAO;
import com.saphamrah.Model.AmvalModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;

import java.util.ArrayList;

public class SabtAmvalModel implements SabtAmvalMVP.ModelOps
{

    private SabtAmvalMVP.RequiredPresenterOps mPresenter;


    public SabtAmvalModel(SabtAmvalMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getListAmvals(int ccMoshtary, int ccSazmanForosh)
    {
        AmvalDAO amvalDAO = new AmvalDAO(mPresenter.getAppContext());
        ArrayList<AmvalModel> sabtMoshtaryAmvalModels = null;

        amvalDAO.fetchAllMoshtaryAmval(mPresenter.getAppContext(), "SabtAmvalActivity", ccMoshtary, ccSazmanForosh,
                new RetrofitResponse() {
//                    @Override
                    public void onSuccess(ArrayList arrayListData) {

//                        Thread thread = new Thread()
//                        {
//                            @Override
//                            public void run()
//                            {
//                                boolean deleteResult = amvalDAO.deleteAll();
                                Log.d("SabtamvalModel", "ccMoshtary="+ ccMoshtary + " ,ccSazmanForosh="+ ccSazmanForosh);
                                boolean deleteResult = amvalDAO.deleteByCcMoshtaryAndCcSazmanForosh(ccMoshtary,ccSazmanForosh);
                                boolean insertResult = amvalDAO.insertGroup(arrayListData);
                                Message message = new Message();

//                                Log.i("TAG65465", "run: " + arrayListData.get(0));
                                if (deleteResult && insertResult)
                                {
                                    message.arg1 = 1;
                                }
                                else
                                {
                                    message.arg1 = -1;
                                }
                                Log.i("TAG65465", "run: " + deleteResult + " " + insertResult);
//                                handler.sendMessage(message);
//                            }
//                        };
//                        thread.start();
                    }

                    @Override
                    public void onFailed(String type, String error) {
//                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "SabtAmvalModel", "SabtAmvalActivity", "getListAmvals", "onFailed");
//                        Log.i("TAG", "onFailed: " + error);
                        Log.i("TAG65465", "run: " + error);

                    }
                });


        mPresenter.onGetListAmvals(sabtMoshtaryAmvalModels);
//        mPresenter.onGetSabtedAmvals(sabtMoshtaryAmvalModels);
        mPresenter.getSabtedAmvals(ccMoshtary);
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        //logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }

    @Override
    public int amvalSabtShodeh(String barcode, int ccMoshtary) {
        AmvalDAO amvalDAO = new AmvalDAO(mPresenter.getAppContext());

        return amvalDAO.getByBarcode(barcode, ccMoshtary);
    }

    @Override
    public void getSabtedMals(int ccMoshtary) {
        //dao
        AmvalDAO amvalDAO = new AmvalDAO(mPresenter.getAppContext());
        ArrayList<AmvalModel> models = amvalDAO.getAllByScanned(ccMoshtary);

        mPresenter.onGetSabtedAmvals(models);
    }

    @Override
    public void onDestroy()
    {

    }


}
