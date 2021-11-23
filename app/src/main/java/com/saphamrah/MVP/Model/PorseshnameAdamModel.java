package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.PorseshnameAdamMVP;
import com.saphamrah.DAO.PorseshnamehDAO;
import com.saphamrah.DAO.PorseshnamehShomareshDAO;
import com.saphamrah.DAO.PorseshnamehTablighatDAO;
import com.saphamrah.DAO.SuggestDAO;
import com.saphamrah.DAO.VisitMoshtaryDAO;
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.Model.PorseshnamehShomareshModel;
import com.saphamrah.Model.PorseshnamehTablighatModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.SuggestModel;
import com.saphamrah.Model.VisitMoshtaryModel;
import com.saphamrah.PubFunc.DeviceInfo;
import com.saphamrah.PubFunc.FileUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.UIModel.CustomerVisitModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.CreateAmargarResult;
import com.saphamrah.WebService.ServiceResponse.CreateVisitMoshtaryResult;
import com.saphamrah.WebService.ServiceResponse.SuggestResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PorseshnameAdamModel implements PorseshnameAdamMVP.ModelOps
{

    private PorseshnameAdamMVP.RequiredPresenterOps mPresenter;
    private static final String CLASS_NAME = "PorseshnameAdamModel";

    public PorseshnameAdamModel(PorseshnameAdamMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getAllPorseshname()
    {
        PorseshnamehDAO porseshnamehDAO = new PorseshnamehDAO(mPresenter.getAppContext());
        List<PorseshnamehModel> porseshnamehModels = porseshnamehDAO.getAll();
        mPresenter.onGetPorseshname(porseshnamehModels);
    }

    @Override
    public void getAllAdamFaal()
    {
        VisitMoshtaryDAO visitMoshtaryDAO = new VisitMoshtaryDAO(mPresenter.getAppContext());
        List<CustomerVisitModel> customerVisitModels = visitMoshtaryDAO.getAllAdam();
        mPresenter.onGetallAdamFaal(customerVisitModels);
    }

    @Override
    public void deletePorseshname(int ccPorseshnameh)
    {
        new PorseshnamehDAO(mPresenter.getAppContext()).delete(ccPorseshnameh);
        new PorseshnamehShomareshDAO(mPresenter.getAppContext()).delete(ccPorseshnameh);
        new PorseshnamehTablighatDAO(mPresenter.getAppContext()).delete(ccPorseshnameh);
        new VisitMoshtaryDAO(mPresenter.getAppContext()).deleteByPorseshname(ccPorseshnameh);
        mPresenter.onSuccessDelete();
        getAllPorseshname();
    }

    @Override
    public void deleteAdamFaal(int ccVisitMoshtary)
    {
        new VisitMoshtaryDAO(mPresenter.getAppContext()).delete(ccVisitMoshtary);
        mPresenter.onSuccessDelete();
        getAllAdamFaal();
    }

    @Override
    public void sendPorseshname(int ccPorseshnameh)
    {
        PorseshnamehDAO porseshnamehDAO = new PorseshnamehDAO(mPresenter.getAppContext());
        PorseshnamehModel porseshnamehModel = porseshnamehDAO.get(ccPorseshnameh);
        if (porseshnamehModel == null)
        {
            mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorGetPorseshnameInfo));
        }
        else
        {
            ArrayList<PorseshnamehShomareshModel> porseshnamehShomareshModels = new PorseshnamehShomareshDAO(mPresenter.getAppContext()).getAllByPorseshname(porseshnamehModel.getCcPorseshnameh());
            ArrayList<PorseshnamehTablighatModel> porseshnamehTablighatModels = new PorseshnamehTablighatDAO(mPresenter.getAppContext()).getAllByPorseshname(porseshnamehModel.getCcPorseshnameh());
            VisitMoshtaryModel visitMoshtaryModel = new VisitMoshtaryDAO(mPresenter.getAppContext()).getByPorseshname(porseshnamehModel.getCcPorseshnameh());
            try
            {
                String version = new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());
                JSONObject jsonObjectFinal = new JSONObject();
                JSONObject jsonObjectPorseshname = porseshnamehModel.toJsonObject(version);
                JSONArray jsonArrayShomaresh = toJsonArrayShomaresh(porseshnamehShomareshModels);
                JSONArray jsonArrayTablighat = toJsonArrayTablighat(porseshnamehTablighatModels);
                JSONObject jsonObject = (visitMoshtaryModel == null) ? new JSONObject() : visitMoshtaryModel.toJsonObject();

                if (jsonObjectPorseshname != null)
                {
                    jsonObjectFinal.put("Porseshnameh" , jsonObjectPorseshname);
                    jsonObjectFinal.put("PorseshnamehShomaresh" , jsonArrayShomaresh);
                    jsonObjectFinal.put("PorseshnamehTablighat" , jsonArrayTablighat);
                    jsonObjectFinal.put("VisitMoshtary" , jsonObject);

                    String strJsonFinal = jsonObjectFinal.toString();
                    FileUtils.saveToFile(mPresenter.getAppContext(), strJsonFinal, "porseshname" + ccPorseshnameh);
                    postDataToServer(porseshnamehModel.getCcPorseshnameh(), visitMoshtaryModel.getCcVisitMoshtary(), strJsonFinal);
                }
                else
                {
                    mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorGetPorseshnameInfo));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "sendPorseshname", "");
                mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorSendData));
            }
        }
    }

    private JSONArray toJsonArrayShomaresh(ArrayList<PorseshnamehShomareshModel> models)
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            for (PorseshnamehShomareshModel model : models)
            {
                JSONObject jsonObject = model.toJsonObject();
                if (jsonObject != null)
                {
                    jsonArray.put(jsonObject);
                }
            }
            return jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private JSONArray toJsonArrayTablighat(ArrayList<PorseshnamehTablighatModel> models)
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            for (PorseshnamehTablighatModel model : models)
            {
                JSONObject jsonObject = model.toJsonObject();
                if (jsonObject != null)
                {
                    jsonArray.put(jsonObject);
                }
            }
            return jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private void postDataToServer(final int ccPorseshnameh, final int ccVisitMoshtary, String strJsonData)
    {
//        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
//        String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
// , "");
//        String serverPort = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
// , "");
        ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
        String serverIP=serverIpModel.getServerIp();
        String serverPort=serverIpModel.getPort();

        if (!serverIP.trim().equals("") || !serverPort.trim().equals(""))
        {
            //APIServicePost apiServicePost = ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
            final APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);

            Call<CreateAmargarResult> call = apiServicePost.createAmargarJson(strJsonData);
            call.enqueue(new Callback<CreateAmargarResult>()
            {
                @Override
                public void onResponse(Call<CreateAmargarResult> call, Response<CreateAmargarResult> response)
                {
                    try
                    {
                        if (response.body() != null && response.isSuccessful())
                        {
                            CreateAmargarResult result = response.body();
                            if (result.getSuccess())
                            {
                                try
                                {
                                    int ccPorseshnameNew = Integer.valueOf(result.getMessage());
                                    if (ccPorseshnameNew > -1)
                                    {
                                        new VisitMoshtaryDAO(mPresenter.getAppContext()).updateIsOld(ccVisitMoshtary);
                                        new PorseshnamehDAO(mPresenter.getAppContext()).updateIsOld(ccPorseshnameh);
                                        checkOtherData(apiServicePost);
                                        mPresenter.onSuccessSend();
                                        getAllPorseshname();
                                    }
                                    else
                                    {
                                        showMessageOfErrorCode(ccPorseshnameNew);
                                    }
                                }
                                catch (Exception e)
                                {
                                    mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
                                }
                            }
                            else
                            {
                                int ccPorseshnameNew = Integer.valueOf(result.getMessage());
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, "" , "postDataToServer" , "onResponse");
                                showMessageOfErrorCode(ccPorseshnameNew);
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message();
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string();
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, CLASS_NAME, "" , "postDataToServer" , "onResponse");
                            mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "" , "postDataToServer" , "onResponse");
                        mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
                    }
                }

                @Override
                public void onFailure(Call<CreateAmargarResult> call, Throwable t)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), CLASS_NAME, "" , "postDataToServer" , "onFailure");
                    mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
                }
            });
        }
        else
        {
            mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.cantFindServer));
        }
    }

    private void checkOtherData(APIServicePost apiServicePost)
    {
        SuggestDAO suggestDAO = new SuggestDAO(mPresenter.getAppContext());
        ArrayList<SuggestModel> suggestModels = suggestDAO.getAllSuggestIsNotSend();
        if (suggestModels.size() > 0)
        {
            sendSuggest(apiServicePost ,suggestModels,suggestDAO);
        }
    }

    private void sendSuggest(APIServicePost apiServicePost , ArrayList<SuggestModel> suggestModels,SuggestDAO suggestDAO)
    {
        for (SuggestModel model : suggestModels)
        {
            String jsonString = model.toJsonString();
            Call<SuggestResult> call = apiServicePost.createSuggestResult(jsonString);
            call.enqueue(new Callback<SuggestResult>()
            {
                @Override
                public void onResponse(Call<SuggestResult> call, Response<SuggestResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            SuggestResult result = response.body();
                            if (result.getSuccess())
                            {
                                suggestDAO.updateIsSend(model.getCcSuggest());
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "TemporaryRequestsListModel", "" , "sendSuggest" , "onResponse");
                                mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorSendSuggest));
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "TemporaryRequestsListModel", "" , "sendSuggest" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorSendSuggest));
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendSuggest" , "onResponse");
                        mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorSendSuggest));
                    }
                }

                @Override
                public void onFailure(Call<SuggestResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendSuggest" , "onFailure");
                    mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorSendSuggest));
                }
            });
        }

    }


    private void showMessageOfErrorCode(int errorCode)
    {
        if (errorCode == -1)
        {
            mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
        }
        else if (errorCode == -2)
        {
            mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.duplicatedPorseshname));
        }
        else
        {
            mPresenter.onErrorSendPorseshnameToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
        }
    }


    @Override
    public void sendAdamFaal(int ccVisitMoshtary)
    {
        VisitMoshtaryModel visitMoshtaryModel = new VisitMoshtaryDAO(mPresenter.getAppContext()).get(ccVisitMoshtary);
        if (visitMoshtaryModel == null)
        {
            mPresenter.onErrorSendAdamToServer(mPresenter.getAppContext().getString(R.string.notFoundAdam));
        }
        else
        {
            String jsonString = visitMoshtaryModel.toJsonObject().toString();
            Log.d("porseshAdam" , "json string adam : " + jsonString);
            postAdamToServer(ccVisitMoshtary, jsonString);
        }
    }

    private void postAdamToServer(final int ccVisitMoshtary, String strJsonData)
    {
//        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
//        String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
// , "");
//        String serverPort = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
// , "");
        ServerIpModel serverIpModel=new PubFunc().new NetworkUtils().postServerFromShared(mPresenter.getAppContext());
        String serverIP=serverIpModel.getServerIp();
        String serverPort=serverIpModel.getPort();
        if (!serverIP.trim().equals("") || !serverPort.trim().equals(""))
        {
            //APIServicePost apiServicePost = ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
            final APIServicePost apiServicePost = ApiClientGlobal.getInstance().getClientServicePost(serverIpModel);

            Call<CreateVisitMoshtaryResult> call = apiServicePost.createVisitMoshtary(strJsonData);
            call.enqueue(new Callback<CreateVisitMoshtaryResult>()
            {
                @Override
                public void onResponse(Call<CreateVisitMoshtaryResult> call, Response<CreateVisitMoshtaryResult> response)
                {
                    try
                    {
                        if (response.body() != null && response.isSuccessful())
                        {
                            CreateVisitMoshtaryResult result = response.body();
                            if (result.getSuccess())
                            {
                                if (new VisitMoshtaryDAO(mPresenter.getAppContext()).updateIsOld(ccVisitMoshtary))
                                {
                                    mPresenter.onSuccessSend();
                                    getAllAdamFaal();
                                }
                                else
                                {
                                    mPresenter.onErrorSendAdamToServer(mPresenter.getAppContext().getString(R.string.errorUpdateData));
                                }
                            }
                            else
                            {
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, "" , "postDataToServer" , "onResponse");
                                mPresenter.onErrorSendAdamToServer(result.getMessage());
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message();
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string();
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, CLASS_NAME, "" , "postDataToServer" , "onResponse");
                            mPresenter.onErrorSendAdamToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, "" , "postDataToServer" , "onResponse");
                        mPresenter.onErrorSendAdamToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
                    }
                }

                @Override
                public void onFailure(Call<CreateVisitMoshtaryResult> call, Throwable t)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), CLASS_NAME, "" , "postDataToServer" , "onFailure");
                    mPresenter.onErrorSendAdamToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
                }
            });
        }
        else
        {
            mPresenter.onErrorSendAdamToServer(mPresenter.getAppContext().getString(R.string.cantFindServer));
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }

}
