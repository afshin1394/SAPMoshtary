package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.MessageBoxMVP;
import com.saphamrah.MVP.Model.MessageBoxModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import org.osmdroid.views.MapView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MessageBoxPresenter implements MessageBoxMVP.PresenterOps , MessageBoxMVP.RequiredPresenterOps
{

    private WeakReference<MessageBoxMVP.RequiredViewOps> mView;
    private MessageBoxMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public MessageBoxPresenter(MessageBoxMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MessageBoxModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(MessageBoxMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getMessages()
    {
        mModel.getMessages();
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
    public void onGetMessages(ArrayList<com.saphamrah.Model.MessageBoxModel> messageBoxModels)
    {
        if (messageBoxModels.size() > 0)
        {
            mView.get().onGetMessages(messageBoxModels);
        }
        else
        {
            mView.get().showToast(R.string.emptyList, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }
}
