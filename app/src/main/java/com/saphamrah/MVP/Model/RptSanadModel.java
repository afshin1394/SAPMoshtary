package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.BaseMVP.RptSanadMVP;
import com.saphamrah.DAO.ForoshandehDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptSanadDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class RptSanadModel implements RptSanadMVP.ModelOps {
    private static final String TAG = "RptSanadModel";
    private RptSanadMVP.RequiredPresenterOps mPresenter;

    public RptSanadModel(RptSanadMVP.RequiredPresenterOps presenter) {
        mPresenter = presenter;
    }


    @Override
    public void getRptSanad() {
        RptSanadDAO rptSanadDAO = new RptSanadDAO(mPresenter.getAppContext());
        List<com.saphamrah.Model.RptSanadModel> rptSanadModels = rptSanadDAO.getAll();
        if (rptSanadModels != null ) {
            mPresenter.onGetRptSanad(rptSanadModels);
        } else {
            mPresenter.onFailedGetRptSanad(R.string.errorGetDataAndGetProgram);
        }

    }

    @Override
    public void updateRptSanad() {
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    getRptSanad();
                    mPresenter.onSuccessUpdateRptSanad();

                } else if (msg.arg1 == -1) {
                    mPresenter.onErrorUpdateRptSanad();
                }
                return false;
            }
        });


        final RptSanadDAO rptSanadDAO = new RptSanadDAO(mPresenter.getAppContext());
        int noeMasouliat = getNoeMasouliatForoshandeh();

        String cCForoshande = "-1";
        if (noeMasouliat == 4 || noeMasouliat == 5) {

            ForoshandehDAO foroshandehDAO = new ForoshandehDAO(mPresenter.getAppContext());
            List<ForoshandehModel> foroshandeModels = foroshandehDAO.getAll();
            if (foroshandeModels != null && foroshandeModels.size() > 0) {
                    for (int i = 0; i < foroshandeModels.size(); i++) {
                        cCForoshande +=","+ foroshandeModels.get(i).getCcForoshandeh() ;
                    }

            }

        } else {
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            if (foroshandehMamorPakhshModel != null) {
                cCForoshande = String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh());
            }
        }
        Log.d(TAG, "updateRptSanad: " + "cCForoshande = " + cCForoshande);
        if (cCForoshande != "") {
            rptSanadDAO.fetchAllRptSanad(mPresenter.getAppContext(), mPresenter.getAppContext().getClass().getSimpleName(), cCForoshande, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = rptSanadDAO.deleteAll();
                            boolean insertResult = rptSanadDAO.insertGroup(arrayListData);
                            Message message = new Message();
                            if (deleteResult && insertResult)
                                message.arg1 = 1;
                            else
                                message.arg1 = -1;

                            handler.sendMessage(message);
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetRptSanad(R.string.errorGetDataAndGetProgram);
                    setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type, error), "RptSanadModel", mPresenter.getAppContext().getClass().getSimpleName(), "updateRptSanad", "onFailed");
                }
            });
        }


    }

    private int getNoeMasouliatForoshandeh() {
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        int noeMasouliat = -1;
        if (foroshandehMamorPakhshModel != null) {
            noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        }
        return noeMasouliat;
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);

    }

    @Override
    public void onDestroy() {

    }
}
