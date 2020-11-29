package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.MessageDetailMVP;
import com.saphamrah.DAO.MessageBoxDAO;
import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.UpdateStatusMessageBoxResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageDetailModel implements MessageDetailMVP.ModelOps
{

    private MessageDetailMVP.RequiredPresenterOps mPresenter;

    public MessageDetailModel(MessageDetailMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getMessage(int ccMessage)
    {
        MessageBoxDAO messageBoxDAO = new MessageBoxDAO(mPresenter.getAppContext());
        MessageBoxModel messageBoxModel = messageBoxDAO.getByccMessage(ccMessage);
        mPresenter.onGetMessage(messageBoxModel);
    }

    @Override
    public void updateMessageStatus(final MessageBoxModel messageBoxModel)
    {
        messageBoxModel.setTitle("");
        messageBoxModel.setMessage("");
        messageBoxModel.setStatusForoshandeh(1);
        String jsonString = messageBoxModel.toJsonString();

        Log.d("message" , "json : " + jsonString);

        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP() , "");
        String serverPort = serverIPShared.getString(serverIPShared.PORT() , "");
        if (!serverIP.trim().equals("") && !serverPort.trim().equals(""))
        {
            final APIServicePost apiServicePost = ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
            Call<UpdateStatusMessageBoxResult> call = apiServicePost.updateStatusMessageBox(jsonString);
            call.enqueue(new Callback<UpdateStatusMessageBoxResult>()
            {
                @Override
                public void onResponse(Call<UpdateStatusMessageBoxResult> call, Response<UpdateStatusMessageBoxResult> response)
                {
                    try
                    {
                        MessageBoxDAO messageBoxDAO = new MessageBoxDAO(mPresenter.getAppContext());
                        messageBoxDAO.updateStatusMessage(messageBoxModel.getCcMessage());
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onResponse");
                    }
                }

                @Override
                public void onFailure(Call<UpdateStatusMessageBoxResult> call, Throwable t)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "TemporaryRequestsListModel", "" , "sendDarkhastFaktorDataToServer" , "onFailure");
                }
            });
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
