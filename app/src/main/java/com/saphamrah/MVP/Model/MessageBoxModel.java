package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.MessageBoxMVP;
import com.saphamrah.DAO.MessageBoxDAO;
import com.saphamrah.PubFunc.PubFunc;

import java.util.ArrayList;

public class MessageBoxModel implements MessageBoxMVP.ModelOps
{

    private MessageBoxMVP.RequiredPresenterOps mPresenter;

    public MessageBoxModel(MessageBoxMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getMessages()
    {
        MessageBoxDAO messageBoxDAO = new MessageBoxDAO(mPresenter.getAppContext());
        ArrayList<com.saphamrah.Model.MessageBoxModel> messageBoxModels = messageBoxDAO.getAll();
        mPresenter.onGetMessages(messageBoxModels);
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
