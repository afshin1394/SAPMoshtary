package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.GetProgramMVP;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.MVP.Model.GetProgramGrpcModel;
import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.MVP.Model.GetProgramModelRx;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.Logger;
import com.saphamrah.R;
import com.saphamrah.Repository.BargashtyRepository;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetProgramPresenter implements GetProgramMVP.PresenterOps , GetProgramMVP.RequiredPresenterOps
{

    private WeakReference<GetProgramMVP.RequiredViewOps> mView;
    private GetProgramMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public GetProgramPresenter(GetProgramMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(getAppContext());
        int getProgramService = systemConfigTabletDAO.getProgramService();

        switch (getProgramService){
            case Constants.GET_PROGRAM_RETROFIT:
                mModel = new GetProgramModel(this);
                break;
            case Constants.GET_PROGRAM_RX:
                mModel = new GetProgramModelRx(this);
                break;
            case Constants.GET_PROGRAM_GRPC:
                mModel = new GetProgramGrpcModel(this);
                break;
        }

    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void checkServerTime()
    {
        mModel.getServerTime();
    }

    @Override
    public void onConfigurationChanged(GetProgramMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getAllForoshandehMamorPakhsh()
    {
        mModel.getAllForoshandehMamorPakhsh();
    }

    @Override
    public void checkGetProgram(String date, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        if (foroshandehMamorPakhshModel == null)
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorSelectForoshandehMamorPakhsh, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else if (date== null || date.trim().length() != 10) //format of date = 1398/04/22 , so we only accept the date that conform this format
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorSelectedGetProgramDate, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            mModel.getProgram(Constants.GET_PROGRAM_FULL() , date , foroshandehMamorPakhshModel);
        }
    }

    @Override
    public void checkUpdateForoshandeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        if (noeMasouliat == ForoshandehMamorPakhshUtils.AMARGAR)
        {
            mView.get().showResourceError(false, R.string.error, R.string.dontAccessForOperation, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            if (foroshandehMamorPakhshModel != null && foroshandehMamorPakhshModel.getCcForoshandeh() > 0)
            {
                mModel.updateForoshandeh(foroshandehMamorPakhshModel);
            }
            else
            {
                mView.get().showResourceError(false, R.string.error, R.string.errorSelectForoshandehMamorPakhsh, Constants.FAILED_MESSAGE(), R.string.apply);
            }
        }
    }

    @Override
    public void checkUpdateKalaModatVosol(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        if (noeMasouliat == ForoshandehMamorPakhshUtils.AMARGAR)
        {
            mView.get().showResourceError(false, R.string.error, R.string.dontAccessForOperation, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            if (foroshandehMamorPakhshModel != null)
            {
                mModel.updateKalaModatVosol(Constants.GET_PROGRAM_UPDATE_KALA() , foroshandehMamorPakhshModel);
            }
            else
            {
                mView.get().showResourceError(false, R.string.error, R.string.errorSelectForoshandehMamorPakhsh, Constants.FAILED_MESSAGE(), R.string.apply);
            }
        }
    }

    @Override
    public void checkUpdateJayezehTakhfif(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        if (noeMasouliat == ForoshandehMamorPakhshUtils.AMARGAR)
        {
            mView.get().showResourceError(false, R.string.error, R.string.dontAccessForOperation, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            if (foroshandehMamorPakhshModel != null)
            {
                mModel.updateJayezehTakhfif(Constants.GET_PROGRAM_UPDATE_JAYEZE() , foroshandehMamorPakhshModel);
            }
            else
            {
                mView.get().showResourceError(false, R.string.error, R.string.errorSelectForoshandehMamorPakhsh, Constants.FAILED_MESSAGE(), R.string.apply);
            }
        }
    }

    @Override
    public void checkUpdateCustomers(String date , ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        if (foroshandehMamorPakhshModel == null)
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorSelectForoshandehMamorPakhsh, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else if (date.trim().length() != 10) //format of date = 1398/04/22 , so we only accept the date that conform this format
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorSelectedGetProgramDate, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
            if (noeMasouliat == 4 || noeMasouliat == 5 || noeMasouliat == 7)
            {
                mView.get().showResourceError(false, R.string.error, R.string.dontAccessForOperation, Constants.FAILED_MESSAGE(), R.string.apply);
            }
            else
            {
                mModel.updateCustomers(Constants.GET_PROGRAM_UPDATE_MOSHTARY() , date , foroshandehMamorPakhshModel);
            }
        }
    }

    @Override
    public void checkUpdateParameter(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        if (noeMasouliat == ForoshandehMamorPakhshUtils.AMARGAR)
        {
            mView.get().showResourceError(false, R.string.error, R.string.dontAccessForOperation, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            if (foroshandehMamorPakhshModel == null)
            {
                mView.get().showResourceError(false, R.string.error, R.string.errorSelectForoshandehMamorPakhsh, Constants.FAILED_MESSAGE(), R.string.apply);
            }
            else
            {
                mModel.updateParameter(foroshandehMamorPakhshModel);
            }
        }
    }

    @Override
    public void checkUpdateEtebarForoshandeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        if (noeMasouliat == ForoshandehMamorPakhshUtils.AMARGAR)
        {
            mView.get().showResourceError(false, R.string.error, R.string.dontAccessForOperation, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            if (foroshandehMamorPakhshModel == null)
            {
                mView.get().showResourceError(false, R.string.error, R.string.errorSelectForoshandehMamorPakhsh, Constants.FAILED_MESSAGE(), R.string.apply);
            }
            else
            {
                mModel.updateEtebarForoshandeh(foroshandehMamorPakhshModel);
            }
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

    @Override
    public void getProgramServiceType() {
        mModel.getProgramServiceType();

    }

    @Override
    public void checkUpdateGharardadKalaMosavabeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        if (noeMasouliat == ForoshandehMamorPakhshUtils.AMARGAR)
        {
            mView.get().showResourceError(false, R.string.error, R.string.dontAccessForOperation, Constants.FAILED_MESSAGE(), R.string.apply);
        }
        else
        {
            if (foroshandehMamorPakhshModel == null)
            {
                mView.get().showResourceError(false, R.string.error, R.string.errorSelectForoshandehMamorPakhsh, Constants.FAILED_MESSAGE(), R.string.apply);
            }
            else
            {
                mModel.updateGharardadKalaMosavabeh(foroshandehMamorPakhshModel);
            }
        }
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetAllForoshandehMamorPakhsh(ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels)
    {
        if (foroshandehMamorPakhshModels != null)
        {
            mView.get().setAdapter(foroshandehMamorPakhshModels);
        }
        else
        {
            mView.get().setAdapter(new ArrayList<ForoshandehMamorPakhshModel>());
        }
    }

    @Override
    public void onGetNoeMasouliat(int noeMasouliat)
    {
        mView.get().onGetNoeMasouliat(noeMasouliat);
    }

    @Override
    public void onSuccessfullyGetNewProgramItem(int getProgramItemCount , int itemIndex)
    {
        if (itemIndex != getProgramItemCount - 1)
        {
            mView.get().updateStatusOfSuccessfulItem(itemIndex);
        }
        else
        {
            mView.get().showCompletedGetProgramSuccessfully();
            mModel.setProgramDateToShared();
        }
    }

    @Override
    public void onFailedGetProgram(int itemIndex, String error)
    {
        mView.get().updateStatusOfFailedItem(Constants.GET_PROGRAM_FULL() , itemIndex , error);
        Logger logger  = new Logger();
        logger.insertLogToDB(getAppContext(),Constants.LOG_EXCEPTION(),error,"GetProgramPresenter","GetProgramActivity","onFailedGetProgram","");
    }

    @Override
    public void onSuccessUpdateForoshandeh()
    {
        mView.get().showResourceError(false, R.string.success, R.string.succesUpdateForoshandeh, Constants.SUCCESS_MESSAGE(), R.string.apply);
    }

    @Override
    public void onFailedUpdateForoshandeh(String errorMessage)
    {
        mView.get().showResourceError(false, R.string.error, R.string.errorUpdateForoshandeh, Constants.FAILED_MESSAGE(), R.string.apply);
    }

    @Override
    public void onSuccessUpdateKalaModatVosolItem(int getProgramItemCount, int itemIndex)
    {
        Log.d("update" , "itemindex : " + itemIndex);
        if (itemIndex != getProgramItemCount - 1)
        {
            mView.get().updateStatusOfSuccessfulItem(itemIndex);
        }
        else
        {
            mView.get().showCompletedUpdateKalaModatVosolSuccessfully();
        }
    }

    @Override
    public void onFailedUpdateKalaModatVosolItem(int itemIndex, String errorMessage)
    {
        mView.get().updateStatusOfFailedItem(Constants.GET_PROGRAM_UPDATE_KALA() , itemIndex , errorMessage);
    }

    @Override
    public void onSuccessUpdateJayezehTakhfifItem(int getProgramItemCount, int itemIndex)
    {
        if (itemIndex != getProgramItemCount - 1)
        {
            mView.get().updateStatusOfSuccessfulItem(itemIndex);
        }
        else
        {
            mView.get().showCompletedUpdateJayezehTakhfifSuccessfully();
        }
    }

    @Override
    public void onFailedUpdateJayezehTakhfif(int itemIndex, String errorMessage)
    {
        mView.get().updateStatusOfFailedItem(Constants.GET_PROGRAM_UPDATE_JAYEZE() , itemIndex , errorMessage);
    }

    @Override
    public void onSuccessUpdateCustomers(int getProgramItemCount, int itemIndex)
    {
        if (itemIndex != getProgramItemCount - 1)
        {
            mView.get().updateStatusOfSuccessfulItem(itemIndex);
        }
        else
        {
            mView.get().showCompletedUpdateCustomerSuccessfully();
        }
    }

    @Override
    public void onFailedUpdateCustomers(int itemIndex, String errorMessage)
    {
        mView.get().updateStatusOfFailedItem(Constants.GET_PROGRAM_UPDATE_MOSHTARY() , itemIndex , errorMessage);
    }


    @Override
    public void onSuccessUpdateParameters(int getProgramItemCount, int itemIndex)
    {
        if (itemIndex != getProgramItemCount - 1)
        {
            mView.get().updateStatusOfSuccessfulItem(itemIndex);
        }
        else
        {
            mView.get().showCompletedUpdateParametersSuccessfully();
        }
    }

    @Override
    public void onFailedUpdateParameters(int itemIndex, String errorMessage)
    {
        mView.get().updateStatusOfFailedItem(Constants.GET_PROGRAM_UPDATE_PARAMETERS() , itemIndex , errorMessage);
    }

    @Override
    public void onSuccessUpdateEtebarForoshandeh(int getProgramItemCount, int itemIndex)
    {
        if (itemIndex != getProgramItemCount - 1)
        {
            mView.get().updateStatusOfSuccessfulItem(itemIndex);
        }
        else
        {
            mView.get().showCompletedUpdateEtebarForoshandehSuccessfully();
        }
    }

    @Override
    public void onFailedUpdateEtebarForoshandeh(int itemIndex, String errorMessage)
    {
        mView.get().updateStatusOfFailedItem(Constants.GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH() , itemIndex , errorMessage);
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {
        mView.get().showResourceError(closeActivity, R.string.errorGetDataTitle, R.string.errorGetData, Constants.FAILED_MESSAGE(), R.string.apply);
    }

    @Override
    public void onGetProgramServiceType(int service) {
        mView.get().onGetProgramType(service);
    }

    @Override
    public void onSuccessUpdateGharardadKalaMosavabeh(int getProgramItemCount, int itemIndex) {
        if (itemIndex != getProgramItemCount - 1)
        {
            mView.get().updateStatusOfSuccessfulItem(itemIndex);
        }
        else
        {
            mView.get().showCompletedUpdateGharardadKalaMosavabeh();
        }
    }

    @Override
    public void onFailedUpdateGharardadKalaMosavabeh(int itemIndex, String errorMessage) {
        mView.get().updateStatusOfFailedItem(Constants.GET_PROGRAM_UPDATE_GHARARDAD_KALAMOSAVABEH() , itemIndex , errorMessage);

    }
    @Override
    public void notFoundServerIP()
    {
        mView.get().showResourceError(true, R.string.errorGetData , R.string.errorFindServerIP , Constants.FAILED_MESSAGE() , R.string.apply);
    }

    @Override
    public void onGetServerTime(boolean validDiffTime, String message)
    {
        if (!validDiffTime)
//        {
//            checkFakeLocation();
//        }
//        else
        {
            mView.get().showErrorAlert(true, R.string.errorLocalDateTimeTitle, message, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

}
