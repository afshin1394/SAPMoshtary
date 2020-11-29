package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.LogsMVP;
import com.saphamrah.DAO.LogPPCDAO;
import com.saphamrah.Model.EmailLogPPCModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Network.AsyncTaskResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.EmailLogPPCShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.CreateLogPPCResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogsModel implements LogsMVP.ModelOps
{

    private LogsMVP.RequiredPresenterOps mPresenter;

    private int successfullRequest;

    public LogsModel(LogsMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
        successfullRequest = 0;
    }

    @Override
    public void getExceptionsToShow()
    {
        LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
        ArrayList<LogPPCModel> arrayListLogs = logPPCDAO.getUnsendExceptionsOrderByIdDesc();
        mPresenter.onGetExceptionsToShow(arrayListLogs);
    }

    @Override
    public void sendLogsToServer()
    {
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP() , "");
        String serverPort = serverIPShared.getString(serverIPShared.PORT() , "");
		String deviceIP = serverIPShared.getString(serverIPShared.DEVICE_IP() , "");																			
        if (!serverIP.trim().equals("") || !serverPort.trim().equals(""))
        {
            LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
            ArrayList<LogPPCModel> logPPCModels = logPPCDAO.getUnsendExceptionsOrderByIdDesc();
            String ccLogs = "-1";
            if (logPPCModels.size() > 0)
            {
                JSONArray jsonArrayLogs = new JSONArray();
                for (LogPPCModel logPPCModel : logPPCModels)
                {
                    JSONObject jsonObject = logPPCModel.toJsonObject(deviceIP);
                    if (jsonObject != null)
                    {
                        jsonArrayLogs.put(jsonObject);
                        ccLogs += "," + logPPCModel.getCcLogPPC();
                    }
                }
                postLogPPC(serverIP, serverPort, jsonArrayLogs.toString(), ccLogs);
            }
            else
            {
                mPresenter.onSuccessSendLogsToServer(mPresenter.getAppContext().getResources().getString(R.string.emptyListForSend));
            }
        }
        else
        {
            mPresenter.onErrorSendLogsToServer("");
        }
    }

    private void postLogPPC(String serverIP , String serverPort , String jsonArrayStringLogs , final String ccLogs)
    {
        APIServicePost apiServicePost = ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
        Call<CreateLogPPCResult> call = apiServicePost.createLogPPC(jsonArrayStringLogs);
        call.enqueue(new Callback<CreateLogPPCResult>()
        {
            @Override
            public void onResponse(Call<CreateLogPPCResult> call, Response<CreateLogPPCResult> response)
            {
                try
                {
                    if (response.body() != null && response.isSuccessful())
                    {
                        CreateLogPPCResult result = response.body();
                        if (result.getSuccess())
                        {
                            LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
                            logPPCDAO.updateExtraProp_IsOld(ccLogs , 1);
                        }
                        else
                        {
                            setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "LogsModel", "" , "callCreateLogPPCService" , "onResponse");
                            mPresenter.onErrorSendLogsToServer(result.getMessage());
                        }
                    }
                    else
                    {
                        String errorMessage = "response not successful " + response.message();
                        if (response.errorBody() != null)
                        {
                            errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string();
                        }
                        setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "LogsModel", "" , "callCreateLogPPCService" , "onResponse");
                        mPresenter.onErrorSendLogsToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "LogsModel", "" , "callCreateLogPPCService" , "onResponse");
                    mPresenter.onErrorSendLogsToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
                }
            }

            @Override
            public void onFailure(Call<CreateLogPPCResult> call, Throwable t)
            {
                setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "LogsModel", "" , "callCreateLogPPCService" , "onFailure");
                mPresenter.onErrorSendLogsToServer(mPresenter.getAppContext().getString(R.string.errorOperation));
            }
        });
    }

    @Override
    public void getExceptions()
    {
        LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
        ArrayList<LogPPCModel> arrayListLogPPC = logPPCDAO.getUnsendExceptionsOrderByIdDesc();
        mPresenter.onGetExceptionForSendToMail(arrayListLogPPC);
    }

    @Override
    public void postLogPPCToMail(final ArrayList<LogPPCModel> arrayListLogsPPC)
    {
        String mailText = "";
        for (LogPPCModel logPPCModel : arrayListLogsPPC)
        {
            mailText = logPPCModel.toString() + "\n ------------------- \n" + mailText;
        }

        EmailLogPPCShared emailLogPPCShared = new EmailLogPPCShared(mPresenter.getAppContext());
        String email = emailLogPPCShared.getString(emailLogPPCShared.EMAIL() , "");
        String pass = emailLogPPCShared.getString(emailLogPPCShared.PASSWORD() , "");

        if (!email.trim().equals("") && !pass.trim().equals(""))
        {
            PubFunc.Encryption encryption = new PubFunc().new Encryption();
            String decodedPass = encryption.decrypt(mPresenter.getAppContext() , pass);

            if (!decodedPass.trim().equals(""))
            {
                PubFunc.SendMail sendMail = new PubFunc().new SendMail();
                sendMail.sendGmail(email, decodedPass, mPresenter.getAppContext().getResources().getString(R.string.subjctOfSendExceptionToMail), mailText, EmailLogPPCModel.MAIL_RECEIVER, new AsyncTaskResponse() {
                    @Override
                    public void processFinished(Object object)
                    {
                        if ((Boolean)object)
                        {
                            groupUpdateExtraPropToSent(arrayListLogsPPC);
                            mPresenter.onSuccessSendExceptionsToMail();
                        }
                        else
                        {
                            mPresenter.onErrorSendExceptionsToMail("");
                        }
                    }
                });
            }
            else
            {
                mPresenter.onErrorSendExceptionsToMail("");
            }
        }
        else
        {
            mPresenter.onErrorSendExceptionsToMail("");
        }
    }

    private void groupUpdateExtraPropToSent(ArrayList<LogPPCModel> arrayListLogPPC)
    {
        LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
        for (LogPPCModel logPPCModel : arrayListLogPPC)
        {
            logPPCDAO.updateExtraProp_IsOld(String.valueOf(logPPCModel.getCcLogPPC()) , 1);
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }


    @Override
    public void onDestroy()
    {

    }


}
