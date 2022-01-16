package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.BaseMVP.RptFaktorMandehDarMVP;
import com.saphamrah.BaseMVP.RptMoshtarianKharidNakardeMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MasirDAO;
import com.saphamrah.DAO.RptMandehdarDAO;
import com.saphamrah.DAO.RptMoshtarianKharidNakardehDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.RptMoshtaryKharidNakardeModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptMoshtarianKharidNakardehModel implements RptMoshtarianKharidNakardeMVP.ModelOps {

    private static final String TAG = RptMoshtarianKharidNakardehModel.class.getSimpleName() ;
    private RptMoshtarianKharidNakardeMVP.RequiredPresenterOps mPresenter;

    public RptMoshtarianKharidNakardehModel(RptMoshtarianKharidNakardeMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getListMoshtarianKharidNakarde() {
        RptMoshtarianKharidNakardehDAO rptMoshtarianKharidNakardehDAO = new RptMoshtarianKharidNakardehDAO(mPresenter.getAppContext());
        ArrayList<RptMoshtaryKharidNakardeModel> rptMoshtaryKharidNakardeModels = rptMoshtarianKharidNakardehDAO.getAllMoshtarianKharidakarde();
        mPresenter.onGetListMoshtarianKharidNakarde(rptMoshtaryKharidNakardeModels);
    }

    @Override
    public void updateListMoshtarianKharidNakarde() {
        String strCcmasir=null;
        final RptMoshtarianKharidNakardehDAO rptMoshtarianKharidNakardehDAO = new RptMoshtarianKharidNakardehDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        strCcmasir = new MasirDAO((mPresenter.getAppContext())).getstrCcMasir();

        Log.i(TAG, "updateListMoshtarianKharidNakarde: "+strCcmasir);
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    Log.i(TAG, "handleMessage: successUpdate");
                    getListMoshtarianKharidNakarde();
                    mPresenter.onSuccessUpdateListMandehDar();
                }
                else if (msg.arg1 == -1)
                {
                    Log.i(TAG, "handleMessage: errorUpdate");
                    mPresenter.onErrorUpdateListMandehDar("");
                }
                return false;
            }
        });
//        String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh())
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case REST:
                rptMoshtarianKharidNakardehDAO.fetchAllMoshtarianKharidNakarde(mPresenter.getAppContext(), "RptFaktorMandehDarActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh())  ,strCcmasir, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                Log.i(TAG, "run: "+arrayListData.size());
                                boolean deleteResult = rptMoshtarianKharidNakardehDAO.deleteAll();
                                boolean insertResult = rptMoshtarianKharidNakardehDAO.insertGroup(arrayListData);
                                Message message = new Message();
                                if (deleteResult && insertResult)
                                {
                                    Log.i("resaultosk", "run: "+foroshandehMamorPakhshModel.getCcForoshandeh());

                                    Log.i(TAG, "run: ");
                                    message.arg1 = 1;
                                }
                                else
                                {
                                    Log.i("resaultok", "run: ");
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
                        mPresenter.onErrorUpdateListMandehDar(type);
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptListVosolModel", "RptListVosolActivity", "updateListVosol", "onFailed");
                    }
                });
                break;
            case gRPC:
                rptMoshtarianKharidNakardehDAO.fetchAllMoshtarianKharidNakardeGrpc(mPresenter.getAppContext(), "RptFaktorMandehDarActivity", String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh())  ,strCcmasir, new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                Log.i(TAG, "run: "+arrayListData.size());
                                boolean deleteResult = rptMoshtarianKharidNakardehDAO.deleteAll();
                                boolean insertResult = rptMoshtarianKharidNakardehDAO.insertGroup(arrayListData);
                                Message message = new Message();
                                if (deleteResult && insertResult)
                                {
                                    Log.i("resaultosk", "run: "+foroshandehMamorPakhshModel.getCcForoshandeh());

                                    Log.i(TAG, "run: ");
                                    message.arg1 = 1;
                                }
                                else
                                {
                                    Log.i("resaultok", "run: ");
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
                        mPresenter.onErrorUpdateListMandehDar(type);
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptListVosolModel", "RptListVosolActivity", "updateListVosol", "onFailed");
                    }
                });
                break;
        }


    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }

    @Override
    public void onDestroy() {

    }
}
