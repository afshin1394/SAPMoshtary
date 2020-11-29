package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.AddCustomerDocsMVP;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryPhotoPPCDAO;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryPhotoPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.AddCustomerShared;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class AddCustomerDocsModel implements AddCustomerDocsMVP.ModelOps
{

    private AddCustomerDocsMVP.RequiredPresenterOps mPresenter;

    public AddCustomerDocsModel(AddCustomerDocsMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getAddCustomerInfoModel()
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        mPresenter.onGetAddCustomerInfoModel(shared.getAddCustomerInfoModel(shared.JSON_DATA() , ""));
    }

    @Override
    public void getImageStatus(int ccMoshtary)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels = moshtaryPhotoPPCDAO.getAllByccMoshtary(ccMoshtary);
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
        boolean savedNationalCard = false;
        boolean savedJavazeKasb = false;
        boolean savedDasteCheck = false;
        boolean isOld = true;
        for (MoshtaryPhotoPPCModel moshtaryPhotoPPCModel : moshtaryPhotoPPCModels)
        {
            if (moshtaryPhotoPPCModel.getCcNoePhoto() == Constants.PHOTO_TYPE_NATIONAL_CARD())
            {
                savedNationalCard = true;
            }
            else if (moshtaryPhotoPPCModel.getCcNoePhoto() == Constants.PHOTO_TYPE_JAVAZE_KASB())
            {
                savedJavazeKasb = true;
            }
            else if (moshtaryPhotoPPCModel.getCcNoePhoto() == Constants.PHOTO_TYPE_DASTE_CHECK())
            {
                savedDasteCheck = true;
            }
        }
        isOld = moshtaryModel.getExtraProp_IsOld() == 1;
        mPresenter.onGetImageStatus(savedNationalCard , savedJavazeKasb , savedDasteCheck, isOld);
    }

    @Override
    public void saveNationalCardImage(int ccMoshtary , byte[] nationalcard)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        MoshtaryPhotoPPCModel moshtaryPhotoPPCModel = new MoshtaryPhotoPPCModel();
        moshtaryPhotoPPCModel.setCcMoshtaryPhoto(0);
        moshtaryPhotoPPCModel.setCcMoshtary(ccMoshtary);
        moshtaryPhotoPPCModel.setCcNoePhoto(Constants.PHOTO_TYPE_NATIONAL_CARD());
        moshtaryPhotoPPCModel.setTxtNoePhoto(mPresenter.getAppContext().getResources().getString(R.string.nationalCard));
        moshtaryPhotoPPCModel.setImageMadrak(nationalcard);
        if (moshtaryPhotoPPCDAO.insert(moshtaryPhotoPPCModel))
        {
            mPresenter.onSuccessSavedNationalCardImage();
        }
        else
        {
            mPresenter.onFailedSaveImage(R.string.errorSaveData);
        }
    }

    @Override
    public void saveJavazehKasbImage(int ccMoshtary , byte[] javazehKasb)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        MoshtaryPhotoPPCModel moshtaryPhotoPPCModel = new MoshtaryPhotoPPCModel();
        moshtaryPhotoPPCModel.setCcMoshtaryPhoto(0);
        moshtaryPhotoPPCModel.setCcMoshtary(ccMoshtary);
        moshtaryPhotoPPCModel.setCcNoePhoto(Constants.PHOTO_TYPE_JAVAZE_KASB());
        moshtaryPhotoPPCModel.setTxtNoePhoto(mPresenter.getAppContext().getResources().getString(R.string.javazeKasb));
        moshtaryPhotoPPCModel.setImageMadrak(javazehKasb);
        if (moshtaryPhotoPPCDAO.insert(moshtaryPhotoPPCModel))
        {
            mPresenter.onSuccessSavedJavazehKasbImage();
        }
        else
        {
            mPresenter.onFailedSaveImage(R.string.errorSaveData);
        }
    }

    @Override
    public void saveDastehCheckImage(int ccMoshtary , byte[] dastehCheck)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        MoshtaryPhotoPPCModel moshtaryPhotoPPCModel = new MoshtaryPhotoPPCModel();
        moshtaryPhotoPPCModel.setCcMoshtaryPhoto(0);
        moshtaryPhotoPPCModel.setCcMoshtary(ccMoshtary);
        moshtaryPhotoPPCModel.setCcNoePhoto(Constants.PHOTO_TYPE_DASTE_CHECK());
        moshtaryPhotoPPCModel.setTxtNoePhoto(mPresenter.getAppContext().getResources().getString(R.string.dasteCheck));
        moshtaryPhotoPPCModel.setImageMadrak(dastehCheck);
        if (moshtaryPhotoPPCDAO.insert(moshtaryPhotoPPCModel))
        {
            mPresenter.onSuccessSavedDastehCheckImage();
        }
        else
        {
            mPresenter.onFailedSaveImage(R.string.errorSaveData);
        }
    }

    @Override
    public void getNationalCardImage(int ccMoshtary)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels = moshtaryPhotoPPCDAO.getByccMoshtaryAndType(ccMoshtary , Constants.PHOTO_TYPE_NATIONAL_CARD());
        if (moshtaryPhotoPPCModels != null && moshtaryPhotoPPCModels.size() > 0)
        {
            mPresenter.onGetNationalCardImage(moshtaryPhotoPPCModels.get(0));
        }
        else
        {
            mPresenter.onGetNationalCardImage(null);
        }
    }

    @Override
    public void getJavazeKasbImage(int ccMoshtary)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels = moshtaryPhotoPPCDAO.getByccMoshtaryAndType(ccMoshtary , Constants.PHOTO_TYPE_JAVAZE_KASB());
        if (moshtaryPhotoPPCModels != null && moshtaryPhotoPPCModels.size() > 0)
        {
            mPresenter.onGetJavazeKasbImage(moshtaryPhotoPPCModels.get(0));
        }
        else
        {
            mPresenter.onGetJavazeKasbImage(null);
        }
    }

    @Override
    public void getDasteCheckImage(int ccMoshtary)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels = moshtaryPhotoPPCDAO.getByccMoshtaryAndType(ccMoshtary , Constants.PHOTO_TYPE_DASTE_CHECK());
        if (moshtaryPhotoPPCModels != null && moshtaryPhotoPPCModels.size() > 0)
        {
            mPresenter.onGetDasteCheckImage(moshtaryPhotoPPCModels.get(0));
        }
        else
        {
            mPresenter.onGetDasteCheckImage(null);
        }
    }

    @Override
    public void deleteNationalCardImage(int ccMoshtaryPhoto)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        if (moshtaryPhotoPPCDAO.deleteByccMoshtaryPhoto(ccMoshtaryPhoto))
        {
            mPresenter.onSuccessDeletedNationalCardImage();
        }
        else
        {
            mPresenter.onFailedDeleteImage();
        }
    }

    @Override
    public void deleteJavazeKasbImage(int ccMoshtaryPhoto)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        if (moshtaryPhotoPPCDAO.deleteByccMoshtaryPhoto(ccMoshtaryPhoto))
        {
            mPresenter.onSuccessDeletedJavazeKasbImage();
        }
        else
        {
            mPresenter.onFailedDeleteImage();
        }
    }

    @Override
    public void deleteDasteCheckImage(int ccMoshtaryPhoto)
    {
        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        if (moshtaryPhotoPPCDAO.deleteByccMoshtaryPhoto(ccMoshtaryPhoto))
        {
            mPresenter.onSuccessDeletedDasteCheckImage();
        }
        else
        {
            mPresenter.onFailedDeleteImage();
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
