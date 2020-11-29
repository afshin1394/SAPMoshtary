package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.LogsMVP;
import com.saphamrah.BaseMVP.MainMVP;
import com.saphamrah.MVP.Model.LogsModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LogPresenter implements LogsMVP.PresenterOps , LogsMVP.RequiredPresenterOps
{

    private WeakReference<LogsMVP.RequiredViewOps> mView;
    private LogsMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public LogPresenter(LogsMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new LogsModel(this);
    }

    @Override
    public void onConfigurationChanged(LogsMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void getExceptionsToShow()
    {
        mModel.getExceptionsToShow();
    }

    @Override
    public void copyLog(String tag, String value)
    {
        if (tag.trim().equals("") || value.trim().equals(""))
        {
            mView.get().showToast(R.string.error, Constants.FAILED_MESSAGE(), Constants.DURATION_SHORT());
        }
        else
        {
            PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
            if (deviceInfo.copyToClipboard(getAppContext() , tag , value))
            {
                mView.get().showToast(R.string.copied, Constants.SUCCESS_MESSAGE(), Constants.DURATION_SHORT());
            }
            else
            {
                mView.get().showToast(R.string.error, Constants.FAILED_MESSAGE(), Constants.DURATION_SHORT());
            }
        }
    }

    @Override
    public void checkLogsForSendToServer()
    {
        mModel.sendLogsToServer();
    }

    @Override
    public void checkExceptionsToMail()
    {
        mModel.getExceptions();
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {

    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }

    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext() {
        try
        {
            return mView.get().getAppContext();
        }
        catch (NullPointerException e)
        {
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "LogPresenter" , "" , "getAppContext" , "");
            return null;
        }
    }

    @Override
    public void onGetExceptionsToShow(ArrayList<LogPPCModel> arrayListLogs)
    {
        mView.get().setAdapter(arrayListLogs);
    }


    @Override
    public void onGetExceptionForSendToMail(ArrayList<LogPPCModel> arrayListLogsPPC)
    {
        if (arrayListLogsPPC.size() > 0)
        {
            mModel.postLogPPCToMail(arrayListLogsPPC);
        }
        else
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorSendMail, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void onErrorSendLogsToServer(String errorMessage)
    {
        mView.get().showAlertSendEmail();
        /*if (errorMessage.trim().equals(""))
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorSendData, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            mView.get().showServerMessage(false, R.string.error, errorMessage, Constants.FAILED_MESSAGE(), R.string.apply);
        }*/
    }

    @Override
    public void onSuccessSendLogsToServer(String message)
    {
        mModel.getExceptionsToShow();
        mView.get().showServerMessage(false, R.string.success, message, Constants.SUCCESS_MESSAGE(), R.string.apply);
    }

    @Override
    public void onErrorSendExceptionsToMail(String message)
    {
        if (message.trim().equals(""))
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorSendMail, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            mView.get().showServerMessage(false, R.string.error, message, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void onSuccessSendExceptionsToMail()
    {
        mView.get().showResourceError(false, R.string.success, R.string.successfullySentMail, Constants.SUCCESS_MESSAGE(), R.string.apply);
    }


}
