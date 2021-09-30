package com.saphamrah.MVP.Model.marjoee;


import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.marjoee.DarkhastFaktorMarjoeeMVP;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KardexDAO;
import com.saphamrah.DAO.KardexSatrDAO;
import com.saphamrah.DAO.MarjoeeKamelImageDAO;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KardexModel;
import com.saphamrah.Model.KardexSatrModel;
import com.saphamrah.Model.MarjoeeKamelImageModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.MarjoeeKardexResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DarkhastFaktorMarjoeeModel implements DarkhastFaktorMarjoeeMVP.ModelOps {
    private DarkhastFaktorMarjoeeMVP.RequiredPresenterOps mPresenter;
    KardexSatrDAO kardexSatrDAO = new KardexSatrDAO(BaseApplication.getContext());
    KardexDAO kardexDAO = new KardexDAO(BaseApplication.getContext());
    ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
    ServerIPShared serverIPShared = new ServerIPShared(BaseApplication.getContext());
    ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().postServerFromShared(BaseApplication.getContext());
    MarjoeeKamelImageDAO marjoeeKamelImageDAO = new MarjoeeKamelImageDAO(BaseApplication.getContext());
    DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
    public DarkhastFaktorMarjoeeModel(DarkhastFaktorMarjoeeMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(BaseApplication.getContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }


    @Override
    public void sendMarjoee(long ccDarkhastFaktor) {
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        boolean isMarjoeeKamel = darkhastFaktorModel.getExtraProp_IsMarjoeeKamel() == 1 ? true : false;
        boolean isMarjoee = kardexDAO.getByCcRefrence(ccDarkhastFaktor).size() > 0 ? true : false;
        if (isMarjoeeKamel || isMarjoee) {
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            if (foroshandehMamorPakhshModel == null) {
                mPresenter.onErrorSend(R.string.errorFindForoshandehMamorPakhsh);
            } else {
                String ip = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
                        , "");
                String port = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
                        , "");
                if (ip.equals("") || port.equals("")) {
                    mPresenter.onErrorSend(R.string.errorFindServerIP);
                } else {
                    sendMarjoeeKamelToserver(darkhastFaktorModel);
                }
            }
        } else {
            mPresenter.onErrorSend(R.string.errorNotExistItemForSend);
        }
    }

    private void sendMarjoeeKamelToserver(final DarkhastFaktorModel darkhastFaktorModel) {
        APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);

        JSONArray jsonArrayKardex = new JSONArray();
        JSONArray jsonArrayKardexSatr = new JSONArray();
        JSONArray jsonArrayImageMarjoee = new JSONArray();
        JSONArray jsonArrayAfradAnbarak = new JSONArray();

        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int ccAfrad = foroshandehMamorPakhshModel.getCcAfrad();
        int ccAnbarak = foroshandehMamorPakhshModel.getCcAnbarak();


        jsonArrayAfradAnbarak.put(AfradAnbarakToJson(ccAnbarak,ccAfrad));

        /*
         * add kardex and kardexSatr and marjoee Image to json
         */
        ArrayList<KardexModel> kardexModels = kardexDAO.getByCcRefrence(darkhastFaktorModel.getCcDarkhastFaktor());
        for (KardexModel kardexModel : kardexModels) {
            jsonArrayKardex.put(kardexModel.toJsonForKardexForSend(kardexModel));
        }
        ArrayList<KardexSatrModel> kardexSatrModels = new ArrayList<>();
        ArrayList<MarjoeeKamelImageModel> marjoeeKamelImageModels = new ArrayList<>();
        if (kardexModels.size() > 0) {
            kardexSatrModels = kardexSatrDAO.getByCcKardex(kardexModels.get(0).getCcKardex());
            marjoeeKamelImageModels = marjoeeKamelImageDAO.getByCcKardex(kardexModels.get(0).getCcKardex());
        }
        for (KardexSatrModel model : kardexSatrModels) {
            jsonArrayKardexSatr.put(model.toJsonForKardexForSend(model));
        }
        for (MarjoeeKamelImageModel model : marjoeeKamelImageModels) {
            jsonArrayImageMarjoee.put(model.toJsonForMarjoeeKamelImageForSend(model));
        }

        try {
            JSONObject jsonObjectTreasury = new JSONObject();
            jsonObjectTreasury.put("kardex", jsonArrayKardex);
            jsonObjectTreasury.put("kardexSatr", jsonArrayKardexSatr);
            jsonObjectTreasury.put("MarjoeeKamelImage", jsonArrayImageMarjoee);
            jsonObjectTreasury.put("AfradAnbarak", jsonArrayAfradAnbarak);

            String strJsonObjectTreasury = jsonObjectTreasury.toString();
            saveToFile("marjoee" + darkhastFaktorModel.getCcDarkhastFaktor() + ".txt", strJsonObjectTreasury);

            Call<MarjoeeKardexResult> call = apiServicePost.createKardexWithJson(strJsonObjectTreasury);
            call.enqueue(new Callback<MarjoeeKardexResult>() {
                @Override
                public void onResponse(Call<MarjoeeKardexResult> call, Response<MarjoeeKardexResult> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            MarjoeeKardexResult result = response.body();
                            if (result.getSuccess()) {
                                dariaftPardakhtPPCDAO.updateSendedMarjoee(darkhastFaktorModel.getCcDarkhastFaktor());
                                mPresenter.onSuccessSend();
                            } else {
                                showErrorMessageOfSend(result.getMessage());
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "MarjoeeForoshandehModel", "", "sendMarjoeeToServer", "onResponse");
                            }
                        } else {
                            String errorMessage = "response not successful " + response.message();
                            if (response.errorBody() != null) {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string();//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "MarjoeeForoshandehModel", "", "sendMarjoeeToServer", "onResponse");
                            mPresenter.onErrorSend(R.string.errorSendDataToServer);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MarjoeeForoshandehModel", "", "sendMarjoeeToServer", "onResponse");
                        mPresenter.onErrorSend(R.string.errorException);
                    }
                }

                @Override
                public void onFailure(Call<MarjoeeKardexResult> call, Throwable t) {
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "MarjoeeForoshandehModel", "", "sendMarjoeeToServer", "onFailure");
                    mPresenter.onErrorSend(R.string.errorFromServer);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "MarjoeeForoshandehModel", "", "sendMarjoeeToServer", "");
        }
    }

    private JSONObject AfradAnbarakToJson(int ccAnbarak, int ccAfrad) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ccAfrad",ccAfrad);
            jsonObject.put("ccAnbark",ccAnbarak);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return jsonObject;
    }

    private void showErrorMessageOfSend(String errorCode) {
        if (errorCode.trim().equals("-9")) {
            mPresenter.onErrorSend(R.string.errorSendDariaftPardakhtMoghayeratVosol);
        } else if (errorCode.trim().equals("-11")) {
            mPresenter.onErrorSend(R.string.errorSendDariaftPardakhtNameSahebHesab);
        } else if (errorCode.trim().equals("-12")) {
            mPresenter.onErrorSend(R.string.errorSendDariaftPardakhtMablaghVosolManfi);
        } else if (errorCode.trim().equals("-13")) {
            mPresenter.onErrorSend(R.string.errorSendDariaftPardakhtDuplicate);
        } else if (errorCode.trim().equals("-14")) {
            mPresenter.onErrorSend(R.string.errorSendMarjoeeDubliacte);
        } else {
            mPresenter.onErrorSend(R.string.errorSendUnknow);
        }
    }

    private void saveToFile(String fileName, String jsonStringData) {
        try {
            File path = BaseApplication.getContext().getExternalFilesDir(null);
            File file = new File(path, fileName);
            FileOutputStream stream = new FileOutputStream(file);
            try {
                stream.write(jsonStringData.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
