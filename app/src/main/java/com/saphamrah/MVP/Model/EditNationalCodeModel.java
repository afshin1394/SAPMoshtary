package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.EditNationalCodeMVP;
import com.saphamrah.DAO.MoshtaryTaghiratDAO;
import com.saphamrah.Model.MoshtaryTaghiratModel;
import com.saphamrah.PubFunc.PubFunc;

public class EditNationalCodeModel implements EditNationalCodeMVP.ModelOps
{
    private EditNationalCodeMVP.RequiredPresenterOps mPresenter;

    public EditNationalCodeModel(EditNationalCodeMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void insertNationalCode(int ccMoshtary, String nationalCode, String shenaseMeli, byte[] image)
    {
        MoshtaryTaghiratDAO moshtaryTaghiratDAO = new MoshtaryTaghiratDAO(mPresenter.getAppContext());
        MoshtaryTaghiratModel moshtaryTaghiratModel = new MoshtaryTaghiratModel();
        moshtaryTaghiratModel.setCcMoshtary(ccMoshtary);
        moshtaryTaghiratModel.setCodeMely(nationalCode);
        moshtaryTaghiratModel.setShenasehMely(shenaseMeli);
        moshtaryTaghiratModel.setCodeMelyImage(image);
        moshtaryTaghiratModel.setExtraProp_IsSendToSql(0);
        moshtaryTaghiratModel.setCcUser(0);
        if (moshtaryTaghiratDAO.deleteNotSendByccMoshtary(ccMoshtary))
        {
            if (moshtaryTaghiratDAO.insert(moshtaryTaghiratModel))
            {
                mPresenter.onSuccessInsert();
            }
            else
            {
                mPresenter.onFailedInsert();
            }
        }
        else
        {
            mPresenter.onFailedInsert();
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
