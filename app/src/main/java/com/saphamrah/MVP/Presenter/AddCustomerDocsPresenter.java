package com.saphamrah.MVP.Presenter;

import android.content.Context;


import com.saphamrah.BaseMVP.AddCustomerDocsMVP;
import com.saphamrah.MVP.Model.AddCustomerDocsModel;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MoshtaryPhotoPPCModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;

public class AddCustomerDocsPresenter implements AddCustomerDocsMVP.PresenterOps , AddCustomerDocsMVP.RequiredPresenterOps
{

    private WeakReference<AddCustomerDocsMVP.RequiredViewOps> mView;
    private AddCustomerDocsMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public AddCustomerDocsPresenter(AddCustomerDocsMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new AddCustomerDocsModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AddCustomerDocsMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getAddCustomerInfoModel()
    {
        mModel.getAddCustomerInfoModel();
    }

    @Override
    public void getImageStatus(int ccMoshtary)
    {
        if (ccMoshtary > 0)
        {
            mModel.getImageStatus(ccMoshtary);
        }
    }

    @Override
    public void checkNationalCardImage(int ccMoshtary , byte[] nationalcard)
    {
        if (nationalcard.length > 0)
        {
            mModel.saveNationalCardImage(ccMoshtary , nationalcard);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkJavazehKasbImage(int ccMoshtary , byte[] javazehKasb)
    {
        if (javazehKasb.length > 0)
        {
            mModel.saveJavazehKasbImage(ccMoshtary , javazehKasb);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkDastehCheckImage(int ccMoshtary , byte[] dastehCheck)
    {
        if (dastehCheck.length > 0)
        {
            mModel.saveDastehCheckImage(ccMoshtary , dastehCheck);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void getNationalCardImage(int ccMoshtary)
    {
        mModel.getNationalCardImage(ccMoshtary);
    }

    @Override
    public void getJavazeKasbImage(int ccMoshtary)
    {
        mModel.getJavazeKasbImage(ccMoshtary);
    }

    @Override
    public void getDasteCheckImage(int ccMoshtary)
    {
        mModel.getDasteCheckImage(ccMoshtary);
    }

    @Override
    public void deleteNationalCardImage(int ccMoshtaryPhoto)
    {
        if (ccMoshtaryPhoto > 0)
        {
            mModel.deleteNationalCardImage(ccMoshtaryPhoto);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void deleteJavazeKasbImage(int ccMoshtaryPhoto)
    {
        if (ccMoshtaryPhoto > 0)
        {
            mModel.deleteJavazeKasbImage(ccMoshtaryPhoto);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void deleteDasteCheckImage(int ccMoshtaryPhoto)
    {
        if (ccMoshtaryPhoto > 0)
        {
            mModel.deleteDasteCheckImage(ccMoshtaryPhoto);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public void onGetAddCustomerInfoModel(AddCustomerInfoModel addCustomerInfoModel)
    {
        mView.get().onGetAddCustomerInfoModel(addCustomerInfoModel);
    }

    @Override
    public void onGetImageStatus(boolean savedNationalCard, boolean savedJavazeKasb, boolean savedDasteCheck, boolean isOld)
    {
        mView.get().onGetImageStatus(savedNationalCard, savedJavazeKasb, savedDasteCheck, isOld);
    }

    @Override
    public void onSuccessSavedNationalCardImage()
    {
        mView.get().onSuccessSavedNationalCardImage();
    }

    @Override
    public void onSuccessSavedJavazehKasbImage()
    {
        mView.get().onSuccessSavedJavazehKasbImage();
    }

    @Override
    public void onSuccessSavedDastehCheckImage()
    {
        mView.get().onSuccessSavedDastehCheckImage();
    }

    @Override
    public void onFailedSaveImage(int errorMessageResId)
    {
        mView.get().showToast(errorMessageResId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onGetNationalCardImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel)
    {
        if (moshtaryPhotoPPCModel == null)
        {
            mView.get().showToast(R.string.imageNotFound, Constants.INFO_MESSAGE(), Constants.DURATION_SHORT());
        }
        else
        {
            mView.get().onGetNationalCardImage(moshtaryPhotoPPCModel);
        }
    }

    @Override
    public void onGetJavazeKasbImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel)
    {
        if (moshtaryPhotoPPCModel == null)
        {
            mView.get().showToast(R.string.imageNotFound, Constants.INFO_MESSAGE(), Constants.DURATION_SHORT());
        }
        else
        {
            mView.get().onGetJavazeKasbImage(moshtaryPhotoPPCModel);
        }
    }

    @Override
    public void onGetDasteCheckImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel)
    {
        if (moshtaryPhotoPPCModel == null)
        {
            mView.get().showToast(R.string.imageNotFound, Constants.INFO_MESSAGE(), Constants.DURATION_SHORT());
        }
        else
        {
            mView.get().onGetDasteCheckImage(moshtaryPhotoPPCModel);
        }
    }

    @Override
    public void onSuccessDeletedNationalCardImage()
    {
        mView.get().onSuccessDeletedNationalCardImage();
    }

    @Override
    public void onSuccessDeletedJavazeKasbImage()
    {
        mView.get().onSuccessDeletedJavazeKasbImage();
    }

    @Override
    public void onSuccessDeletedDasteCheckImage()
    {
        mView.get().onSuccessDeletedDasteCheckImage();
    }

    @Override
    public void onFailedDeleteImage()
    {
        mView.get().showToast(R.string.errorDeleteImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }


}
