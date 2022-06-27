package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RptMojodiAnbrakMVP;
import com.saphamrah.DAO.AnbarakAfradDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KalaPhotoDAO;
import com.saphamrah.DAO.RptMojodiAnbarDAO;
import com.saphamrah.Model.AnbarakAfradModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.RptMojodiAnbarModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;

import java.util.ArrayList;

public class RptMojodiAnbarakModel implements RptMojodiAnbrakMVP.ModelOps {

    private RptMojodiAnbrakMVP.RequiredPresenterOps mPresenter;

    private int successfullRequest;

    public RptMojodiAnbarakModel(RptMojodiAnbrakMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        successfullRequest = 0;
    }


    @Override
    public void getGallery() {
        KalaPhotoDAO kalaPhotoDAO = new KalaPhotoDAO(mPresenter.getAppContext());
        ArrayList<KalaPhotoModel> kalaPhotoModels = kalaPhotoDAO.getAll();
        mPresenter.onGetGallery(kalaPhotoModels);
    }

    @Override
    public void getMojodiAnbar(int sortHavalehFaktor, boolean viewAsTable) {
        AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
        ArrayList<AnbarakAfradModel> anbarakAfradModels = anbarakAfradDAO.getAll();
        if (anbarakAfradModels != null && anbarakAfradModels.size() > 0) {
            RptMojodiAnbarDAO mojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());
            ArrayList<RptMojodiAnbarModel> mojodiAnbarModels = mojodiAnbarDAO.getAllOrderBySortHavalehFaktor(sortHavalehFaktor);
            mPresenter.onGetMojodiAnbar(mojodiAnbarModels, viewAsTable);
        } else {
            mPresenter.onFailedGetMojodiAnbar(R.string.notFoundAnbar);
        }
    }

    @Override
    public void updateMojodiAnbar(int sortHavalehFaktor, boolean viewAsTable) {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int ccMamorPakhsh = foroshandehMamorPakhshModel.getCcMamorPakhsh();
        int ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();

        // get noeMasoliat
        ForoshandehMamorPakhshUtils foroshandehMamorPakhshUtils = new ForoshandehMamorPakhshUtils();
        int noeMasouliat = foroshandehMamorPakhshUtils.getNoeMasouliat(foroshandehMamorPakhshModel);

        AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
        ArrayList<AnbarakAfradModel> anbarakAfradModels = anbarakAfradDAO.getAll();

        Log.d("RptMojodyAnbarak", "anbarakAfradModels:" + anbarakAfradModels.toString());
        Log.d("RptMojodyAnbarak", "anbarakAfradModels size:" + anbarakAfradModels.size());
        if (anbarakAfradModels != null && anbarakAfradModels.size() > 0) {
            String ccAnbarak = String.valueOf(anbarakAfradModels.get(0).getCcAnbarak());
            final RptMojodiAnbarDAO mojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());

            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
            switch (serverIpModel.getWebServiceType()) {
                case REST:

                    mojodiAnbarDAO.fetchRptMojodyAnbarak(mPresenter.getAppContext(), mPresenter.getAppContext().getClass().getSimpleName(), ccAnbarak, ccForoshandeh, ccMamorPakhsh, noeMasouliat, new RetrofitResponse() {
                        @Override
                        public void onSuccess(final ArrayList arrayListData) {
                            mojodiAnbarDAO.deleteAll();
                            mojodiAnbarDAO.insertGroup(arrayListData);
                            getMojodiAnbar(sortHavalehFaktor, viewAsTable);
//                            final Handler handler = new Handler(new Handler.Callback() {
//                                @Override
//                                public boolean handleMessage(Message msg) {
//                                    if (msg.arg1 == 1) {
//                                        getMojodiAnbar(sortHavalehFaktor, viewAsTable);
//                                    }
//                                    return false;
//                                }
//                            });
//
//                            Thread thread = new Thread() {
//                                @Override
//                                public void run() {
//                                    mojodiAnbarDAO.deleteAll();
//                                    mojodiAnbarDAO.insertGroup(arrayListData);
//                                    Message msg = new Message();
//                                    msg.arg1 = 1;
//                                    handler.sendMessage(msg);
//                                }
//                            };
//                            thread.start();
                        }

                        @Override
                        public void onFailed(String type, String error) {
                            mPresenter.onFailedGetMojodiAnbar(R.string.errorGetData);
                        }
                    });
                    break;
                case gRPC:
                    mojodiAnbarDAO.fetchRptMojodyAnbarakGrpc(mPresenter.getAppContext(), mPresenter.getAppContext().getClass().getSimpleName(), ccAnbarak, ccForoshandeh, ccMamorPakhsh, noeMasouliat, new RetrofitResponse() {
                        @Override
                        public void onSuccess(final ArrayList arrayListData) {
                              mojodiAnbarDAO.deleteAll();
                              mojodiAnbarDAO.insertGroup(arrayListData);
                              getMojodiAnbar(sortHavalehFaktor, viewAsTable);

//                            final Handler handler = new Handler(new Handler.Callback() {
//                                @Override
//                                public boolean handleMessage(Message msg) {
//                                    if (msg.arg1 == 1) {
//                                        getMojodiAnbar(sortHavalehFaktor, viewAsTable);
//                                    }
//                                    return false;
//                                }
//                            });
//
//                            Thread thread = new Thread() {
//                                @Override
//                                public void run() {
//                                    mojodiAnbarDAO.deleteAll();
//                                    mojodiAnbarDAO.insertGroup(arrayListData);
//                                    Message msg = new Message();
//                                    msg.arg1 = 1;
//                                    handler.sendMessage(msg);
//                                }
//                            };
//                            thread.start();
                        }

                        @Override
                        public void onFailed(String type, String error) {
                            mPresenter.onFailedGetMojodiAnbar(R.string.errorGetData);
                        }
                    });
                    break;
            }

        } else {
            mPresenter.onFailedGetMojodiAnbar(R.string.notFoundAnbar);
        }
    }

    @Override
    public void getMojodiAnbarOrderByNameKala(boolean viewAsTable, int sortByHavalehFaktor) {
        RptMojodiAnbarDAO mojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());
        ArrayList<RptMojodiAnbarModel> mojodiAnbarakModels = mojodiAnbarDAO.getAllOrderByNameKala(sortByHavalehFaktor);
        mPresenter.onGetMojodiAnbarOrderByNameKala(mojodiAnbarakModels, viewAsTable);
    }

    @Override
    public void getMojodiAnbarOrderByCount(boolean viewAsTable, int sortByHavalehFaktor) {
        RptMojodiAnbarDAO mojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());
        ArrayList<RptMojodiAnbarModel> mojodiAnbarakModels = mojodiAnbarDAO.getAllOrderByCount(sortByHavalehFaktor);
        mPresenter.onGetMojodiAnbarOrderByCount(mojodiAnbarakModels, viewAsTable);
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
