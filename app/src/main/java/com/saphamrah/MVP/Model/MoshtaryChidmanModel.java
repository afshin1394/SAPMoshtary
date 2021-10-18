package com.saphamrah.MVP.Model;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.BanksInfoMVP;
import com.saphamrah.BaseMVP.MoshtaryChidmanMVP;
import com.saphamrah.DAO.MoshtaryChidmanDAO;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.ApiClientGlobal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MoshtaryChidmanModel implements MoshtaryChidmanMVP.ModelOps {
    private static final String TAG = MoshtaryChidmanModel.class.getSimpleName();
    private MoshtaryChidmanMVP.RequiredPresenterOps mPresenter;

    public MoshtaryChidmanModel(MoshtaryChidmanMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getConfig() {

    }

    @Override
    public void getMoshtaryChidman() {
        MoshtaryChidmanDAO moshtaryChidmanDAO = new MoshtaryChidmanDAO(mPresenter.getAppContext());
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        int ccMoshtary = shared.getInt(shared.getCcMoshtary() , 0);
        ArrayList<com.saphamrah.Model.MoshtaryChidmanModel> moshtaryChidmanModels = moshtaryChidmanDAO.getAllByccMoshtary(ccMoshtary);
        mPresenter.onGetMoshtaryChidman(moshtaryChidmanModels);
    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public void insertMoshtaryChidman(com.saphamrah.Model.MoshtaryChidmanModel moshtaryChidmanModel)
    {
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        int ccMoshtary = shared.getInt(shared.getCcMoshtary(),0);
        long ccDarkhastFaktor = shared.getLong(shared.getCcDarkhastFaktor(),0);
        int ccMasir =  shared.getInt(shared.getCcMasirRooz(),0);
        Log.i(TAG, "insertMoshtaryChidman:\t ccMoshtary:"+ccMoshtary+"\t ccMasir"+ccMasir+"\t ccdarkhastFaktor"+ccDarkhastFaktor+"\n");
        String date = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(Calendar.getInstance().getTime());
        moshtaryChidmanModel.setCcMoshtary(ccMoshtary);
        moshtaryChidmanModel.setTarikh(date);
        moshtaryChidmanModel.setCcMasir(ccMasir);
        moshtaryChidmanModel.setCcDarkhastFaktor(ccDarkhastFaktor);
        MoshtaryChidmanDAO moshtaryChidmanDAO = new MoshtaryChidmanDAO(mPresenter.getAppContext());
        moshtaryChidmanModel.setImage(moshtaryChidmanModel.getImage());
        moshtaryChidmanModel.setExtraProp_IsSend(0);
        boolean insert  = moshtaryChidmanDAO.insert(moshtaryChidmanModel);
        if (insert) {
            mPresenter.onInsertMoshtaryChidman(moshtaryChidmanModel);
            mPresenter.onSuccess(R.string.successSaveData);
        }else{
            mPresenter.onError(R.string.errorInsertMoshtaryChidman);
        }

    }

    @Override
    public void updateMoshtaryChidman(com.saphamrah.Model.MoshtaryChidmanModel moshtaryChidmanModel)
    {
        MoshtaryChidmanDAO moshtaryChidmanDAO = new MoshtaryChidmanDAO(mPresenter.getAppContext());
        boolean updateByCcMoshtaryChidman = moshtaryChidmanDAO.updateByccMoshtaryChidman(moshtaryChidmanModel);
        if (updateByCcMoshtaryChidman)
        {
            mPresenter.onUpdateMoshtaryChidman();
            mPresenter.onSuccess(R.string.updateSuccessed);
        }else{
            mPresenter.onError(R.string.errorUpdate);
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }


    public void deleteMoshtaryChidman(int ccMoshtaryChidman) {
        MoshtaryChidmanDAO moshtaryChidmanDAO = new MoshtaryChidmanDAO(mPresenter.getAppContext());
        boolean deleteByccMoshtaryChidman = moshtaryChidmanDAO.deleteByccMoshtaryChidman(ccMoshtaryChidman);
        if (deleteByccMoshtaryChidman){
            mPresenter.onDeleteMoshtaryChidman();
        }else{
            mPresenter.onError(R.string.errorDelete);
        }

    }


    @Override
    public void sendMoshtaryChidmans(ArrayList<com.saphamrah.Model.MoshtaryChidmanModel> moshtaryChidmanModels) {
        MoshtaryChidmanDAO moshtaryChidmanDAO = new MoshtaryChidmanDAO(mPresenter.getAppContext());
        moshtaryChidmanDAO.sendMoshtaryChidmans(moshtaryChidmanModels, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
               mPresenter.onSendMoshtaryChidmans();
               mPresenter.onSuccess(R.string.successSendData);
            }

            @Override
            public void onFailed(String type, String error) {
               mPresenter.onError(R.string.errorSendData);
            }
        });

    }


}
