package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.PishDaryaftMVP;
import com.saphamrah.DAO.AllMoshtaryPishdaryaftDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.AllMoshtaryPishdaryaftModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.CreateDariaftPardakhtPPCJSONResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PishDaryaftModel implements PishDaryaftMVP.ModelOps {
    private DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
    private ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
    private AllMoshtaryPishdaryaftDAO allMoshtaryPishdaryaftDAO = new AllMoshtaryPishdaryaftDAO(BaseApplication.getContext());
    private PishDaryaftMVP.RequiredPresenterOps mPresenter;

    public PishDaryaftModel(PishDaryaftMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getAllCustomers() {
        ArrayList<AllMoshtaryPishdaryaftModel> allMoshtaryPishdaryaftModels = allMoshtaryPishdaryaftDAO.getAll();
        mPresenter.onGetAllCustomers(allMoshtaryPishdaryaftModels);
    }


    @Override
    public void getDariaftPardakhtForSend(int ccMoshtary, int position) {
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = dariaftPardakhtPPCDAO.getForSendToSqlByccMoshtary(ccMoshtary);
        Log.d("treasury", "dariaftPardakhtPPCModels.size : " + dariaftPardakhtPPCModels.size());
        if (dariaftPardakhtPPCModels.size() > 0) {
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            if (foroshandehMamorPakhshModel == null) {
                mPresenter.onErrorSend(R.string.errorFindForoshandehMamorPakhsh);
            } else {
                ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().postServerFromShared(BaseApplication.getContext());
                if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                    mPresenter.onErrorSend(R.string.errorFindServerIP);
                } else {
                    int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
                    DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(dariaftPardakhtPPCModels.get(0).getCcDarkhastFaktor());
                    ParameterChildDAO childParameterDAO = new ParameterChildDAO(BaseApplication.getContext());
                    int codeNoeVosolVajhNaghd = Integer.parseInt(childParameterDAO.getAllByccChildParameter(String.valueOf(Constants.CC_CHILD_CODE_NOE_VOSOL_VAJH_NAGHD())).get(0).getValue());
                    String currentVersionNumber = new PubFunc().new DeviceInfo().getCurrentVersion(BaseApplication.getContext());
                    sendDariaftPardakhtToServer(position, serverIpModel, dariaftPardakhtPPCModels, foroshandehMamorPakhshModel, noeMasouliat, darkhastFaktorModel, codeNoeVosolVajhNaghd, currentVersionNumber);
                }
            }
        } else {
            mPresenter.onErrorSend(R.string.errorNotExistItemForSend);
        }
    }

    @Override
    public void refresh() {
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getAllCustomers();
                    mPresenter.onUpdateData();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.failedUpdate();
                }
                return false;
            }
        });
        allMoshtaryPishdaryaftDAO.fetchAllMoshtaryforoshandeh(BaseApplication.getContext(), "pishDaryaft", foroshandehMamorPakhshModel.getCcForoshandehs(), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread()
                {
                    @Override
                    public void run(){

                        boolean deleteResult = allMoshtaryPishdaryaftDAO.deleteAll();
                        boolean insertResult = allMoshtaryPishdaryaftDAO.insertGroup(arrayListData);
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

    /**
     * send to server
     */
    private void sendDariaftPardakhtToServer(final int position, ServerIpModel serverIpModel, final ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, int noeMasouliat, final DarkhastFaktorModel darkhastFaktorModel, int codeNoeVosolVajhNaghd, String currentVersionNumber) {

        APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);

        String ccDpdfs = "-1";
        JSONArray jsonArrayDariaftPardakht = new JSONArray();
        int ccMarkazForosh = 0;
        int ccMarkazAnbar = 0;
        int ccMarkazSazmanForoshSakhtarForosh = 0;
        if (noeMasouliat != 4) {
            ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
            ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbar();
            ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
        } else {
            ccMarkazForosh = darkhastFaktorModel.getCcMarkazForosh();
            ccMarkazAnbar = darkhastFaktorModel.getCcMarkazAnbar();
            ccMarkazSazmanForoshSakhtarForosh = darkhastFaktorModel.getCcMarkazSazmanForoshSakhtarForosh();
        }
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
        int ccSazmanForosh = foroshandehMamorPakhshDAO.getIsSelect().getCcSazmanForosh();
        //create JsonArray of DariaftPardakhtPPCModel
        for (DariaftPardakhtPPCModel dpModel : dariaftPardakhtPPCModels) {

            int codeNoeSanad = 0;
            int codeNoeCheck = 0;
            jsonArrayDariaftPardakht.put(dpModel.toJsonObjectCheckPishDariaft(ccMarkazForosh, ccMarkazAnbar, ccMarkazSazmanForoshSakhtarForosh, codeNoeSanad, codeNoeCheck, codeNoeVosolVajhNaghd, currentVersionNumber, ccSazmanForosh));
            ccDpdfs += "," + dpModel.getCcDariaftPardakht();

        }

        /**
         * create json string in model
         */
        try {
            JSONObject jsonObjectPishDaryaft = new JSONObject();
            jsonObjectPishDaryaft.put("DariaftPardakht", jsonArrayDariaftPardakht);

            String strJsonObjectCheckPishDariaft = jsonObjectPishDaryaft.toString();

            Call<CreateDariaftPardakhtPPCJSONResult> call = apiServicePost.createDariaftPardakhtPPCPishDariaftJSON(strJsonObjectCheckPishDariaft);
            call.enqueue(new Callback<CreateDariaftPardakhtPPCJSONResult>() {
                @Override
                public void onResponse(Call<CreateDariaftPardakhtPPCJSONResult> call, Response<CreateDariaftPardakhtPPCJSONResult> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            CreateDariaftPardakhtPPCJSONResult result = response.body();
                            if (result.getSuccess()) {
                                if (Integer.parseInt(result.getMessage()) > 0) {
                                    long ccMoshtary = Integer.parseInt(result.getMessage());
                                    DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
                                    for (int i = 0; i < dariaftPardakhtPPCModels.size(); i++) {
                                        dariaftPardakhtPPCDAO.updateSendedPishDaryaft(ccMoshtary, 1);
                                    }
                                    mPresenter.onSuccessSend(position);
                                } else {
                                    showResultError(Integer.parseInt(result.getMessage()));
                                }

                            } else {
                                showResultError(Integer.parseInt(result.getMessage()));
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "PishDaryaftModel", "", "sendDariaftPardakhtToServer", "onResponse");
                            }
                        } else {
                            String errorMessage = "response not successful " + response.message();
                            if (response.errorBody() != null) {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string();//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "PishDaryaftModel", "", "sendDariaftPardakhtToServer", "onResponse");
                            mPresenter.onErrorSend(R.string.errorOperation);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "PishDaryaftModel", "", "sendDariaftPardakhtToServer", "onResponse");
                        mPresenter.onErrorSend(R.string.errorOperation);
                    }
                }

                @Override
                public void onFailure(Call<CreateDariaftPardakhtPPCJSONResult> call, Throwable t) {
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "PishDaryaftModel", "", "sendDariaftPardakhtToServer", "onFailure");
                    mPresenter.onErrorSend(R.string.errorOperation);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "PishDaryaftModel", "", "sendDariaftPardakhtToServer", "");
        }

    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }




    /**
     * show error for send pish daryaft
     *
     * @param errorCode
     */
    private void showResultError(int errorCode) {
        switch (errorCode) {
            case -1:
                mPresenter.onErrorSend(R.string.errorDuplicatedFaktor);
                break;
            case -2:
                mPresenter.onErrorSend(R.string.errorMoghayeratMablaghDarkhast);
                break;
            case -3:
                mPresenter.onErrorSend(R.string.errorMoghayeratTedadAghlam);
                break;
            case -4:
                mPresenter.onErrorSend(R.string.errorMoghayeratDarJayeze);
                break;
            case -5:
                mPresenter.onErrorSend(R.string.errorMoghayeratTakhfifTitr);
                break;
            case -6:
                mPresenter.onErrorSend(R.string.errorMoghayeratTakhfifSatr);
                break;
            case -7:
                mPresenter.onErrorSend(R.string.errorMoghayeratMablaghTakhfif);
                break;
            case -8:
                mPresenter.onErrorSend(R.string.errorMoghayeratTedadMarjoee);
                break;
            case -9:
                mPresenter.onErrorSend(R.string.errorMoghayeratVosolTitr);
                break;
            case -10:
                mPresenter.onErrorSend(R.string.errorMoghayeratTakhfifNaghdi);
                break;
            case -11:
                mPresenter.onErrorSend(R.string.errorMoghayeratNameSahebHesab);
                break;
            case -12:
                mPresenter.onErrorSend(R.string.errorMablaghVosolManfi);
                break;
            case -13:
                mPresenter.onErrorSend(R.string.errorNotFoundAddress);
                break;
            case -14:
                mPresenter.onErrorSend(R.string.errorTarikhFaktorVosol);
                break;
            default:
                mPresenter.onErrorSend(R.string.errorSendData);
        }
    }

}
