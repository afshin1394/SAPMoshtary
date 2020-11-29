package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.MessageDetailMVP;
import com.saphamrah.MVP.Model.MessageDetailModel;
import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MessageDetailPresenter implements MessageDetailMVP.PresenterOps , MessageDetailMVP.RequiredPresenterOps
{

    private WeakReference<MessageDetailMVP.RequiredViewOps> mView;
    private MessageDetailMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public MessageDetailPresenter(MessageDetailMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MessageDetailModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(MessageDetailMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getMessage(int ccMessage)
    {
        if (ccMessage > 0)
        {
            mModel.getMessage(ccMessage);
        }
        else
        {
            mView.get().onFailedGetMessage();
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetMessage(MessageBoxModel messageBoxModel)
    {
        if (messageBoxModel.getCcMessage() > 0)
        {
            mView.get().onGetMessage(messageBoxModel);
            mModel.updateMessageStatus(messageBoxModel);
        }
        else
        {
            mView.get().onFailedGetMessage();
        }
    }

}
