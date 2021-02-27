package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.EditCustomerMVP;
import com.saphamrah.MVP.Model.EditCustomerModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.ParameterChildModel;
											 
												
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
								  
import java.util.ArrayList;

public class EditCustomerPresenter implements EditCustomerMVP.PresenterOps , EditCustomerMVP.RequiredPresenterOps
{

    private WeakReference<EditCustomerMVP.RequiredViewOps> mView;
    private EditCustomerMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public EditCustomerPresenter(EditCustomerMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new EditCustomerModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(EditCustomerMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getUpdateableItems()
    {
        mModel.getUpdateableItems();
    }

    @Override
    public void getCustomerInfo(int ccMoshtary)
    {
        if (ccMoshtary > 0)
        {
            mModel.getCustomerInfo(ccMoshtary);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkNewAddressData(int ccMoshtary, int ccAddress, String telephone, String postalCode)
    {
        mModel.updateAddress(ccMoshtary, ccAddress, telephone, postalCode);
    }

    @Override
    public void getNoeFaaliatSenf()
    {
        mModel.getNoeFaaliat();
    }

    @Override
    public void getNoeSenf(int ccGorohLink)
    {
        mModel.getNoeSenf(ccGorohLink, false);
    }

    @Override
    public void updateMoshtaryGoroh(int ccMoshtary , int ccNoeFaaliat, String nameNoeFaaliat, int ccNoeSenf, String nameNoeSenf)
    {
        if (ccNoeFaaliat <= 0 && ccNoeSenf <= 0)
        {
            mView.get().showToast(R.string.dontChange , Constants.INFO_MESSAGE() , Constants.DURATION_LONG());
        }
        else
        {
            if ( (ccNoeFaaliat > 0 && nameNoeFaaliat.trim().equals("")) ||  (ccNoeSenf > 0 && nameNoeSenf.trim().equals("")))
            {
                mView.get().showToast(R.string.errorOperation , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
            }
            else
            {
                if (ccNoeFaaliat > 0 && ccNoeSenf > 0)				  
                {
                    mModel.updateNoeFaaliatSenf(ccMoshtary , ccNoeFaaliat, nameNoeFaaliat, ccNoeSenf, nameNoeSenf);
                }
            }
        }
    }

    @Override
    public void updateSaateVisit(int ccMoshtary, String time)
    {
        if (ccMoshtary > 0)
        {
            if (time.trim().replace(" " , "").length() != 5)
            {
                mView.get().showToast(R.string.invalidTime , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
            }
            else
            {
                mModel.updateSaateVisit(ccMoshtary , time);
            }
        }
        else
        {
            mView.get().showToast(R.string.errorSelectCustomer , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
        }
    }

    @Override
    public void updateCustomerMobile(int ccMoshtary , String mobile)
    {
        mModel.updateCustomerMobile(ccMoshtary , mobile);
    }

    @Override
    public void updateHasAnbarAndMasahateMaghaze(int ccMoshtary , int hasAnbar, int masahateMaghaze)
    {
        mModel.updateHasAnbarAndMasahateMaghaze(ccMoshtary , hasAnbar, masahateMaghaze);
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
    {}


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetUpdateableItems(ArrayList<ParameterChildModel> childParameterModels)
    {
        for (ParameterChildModel childParameterModel : childParameterModels)
        {
            if (childParameterModel.getValue().trim().equals("0"))
            {
                if (childParameterModel.getName().trim().equalsIgnoreCase(Constants.CAN_UPDATE_NATIONAL_CODE().trim()))
                {
                    mView.get().hideEdttxtNationalCode();
                }
                else if (childParameterModel.getName().trim().equalsIgnoreCase(Constants.CAN_UPDATE_NOE_FAALIAT().trim()))
                {
                    mView.get().hideEdttxtNoeFaaliat();
                }
                else if (childParameterModel.getName().trim().equalsIgnoreCase(Constants.CAN_UPDATE_NOE_SENF().trim()))
                {
                    mView.get().hideEdttxtNoeSenf();
                }
                else if (childParameterModel.getName().trim().equalsIgnoreCase(Constants.CAN_UPDATE_ANBAR().trim()))
                {
                    mView.get().hideEdttxtAnbar();
                }
                else if (childParameterModel.getName().trim().equalsIgnoreCase(Constants.CAN_UPDATE_MASAHAT_MAGHAZE().trim()))
                {
                    mView.get().hideEdttxtMasahatMaghaze();
                }
                else if (childParameterModel.getName().trim().equalsIgnoreCase(Constants.CAN_UPDATE_SAATE_VISIT().trim()))
                {
                    mView.get().hideEdttxtSaateVisit();
                }
                else if (childParameterModel.getName().trim().equalsIgnoreCase(Constants.CAN_UPDATE_MOBILE().trim()))
                {
                    mView.get().hideEdttxtMobile();
                }
                else if (childParameterModel.getName().trim().equalsIgnoreCase(Constants.CAN_UPDATE_ADDRESS().trim()))
                {
                    mView.get().hideAddress();
                }
            }
        }
    }

    @Override
    public void onGetBaseCustomerInfo(String nationalCode, String mobile, String masahateMaghaze, int hasAnbar, int codeNoeShakhsiat, String noeShakhsiat, String saateVisit, String noeSenf, String noeFaaliat)
    {
        mView.get().onGetBaseCustomerInfo(nationalCode, mobile, masahateMaghaze, hasAnbar, saateVisit, noeSenf, noeFaaliat, codeNoeShakhsiat);
    }

    @Override
    public void onGetMoshtaryAddress(ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        mView.get().onGetMoshtaryAddress(moshtaryAddressModels);
    }

    @Override
    public void onFailedUpdate()
    {
        mView.get().showToast(R.string.errorUpdateCustomer , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onGetNoeFaaliat(ArrayList<Integer> noeFaaliatIds, ArrayList<String> noeFaaliatNames)
    {
        mView.get().onGetNoeFaaliat(noeFaaliatIds, noeFaaliatNames);
    }

    @Override
    public void onGetNoeSenf(boolean showAlertDialog, ArrayList<Integer> noeSenfIds, ArrayList<String> noeSenfNames)
    {
        mView.get().onGetNoeSenf(showAlertDialog, noeSenfIds, noeSenfNames);
    }

    @Override
    public void onUpdateNoeFaaliat(String nameNoeFaaliat)
    {
        mView.get().onUpdateNoeFaaliat(nameNoeFaaliat);
        mView.get().showToast(R.string.successfullyDoneOps , Constants.SUCCESS_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onUpdateNoeSenf(String nameNoeSenf)
    {
        mView.get().onUpdateNoeSenf(nameNoeSenf);
        mView.get().showToast(R.string.successfullyDoneOps , Constants.SUCCESS_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onUpdateSaateVisit(String time)
    {
        mView.get().onUpdateSaateVisit(time);
        mView.get().showToast(R.string.successfullyDoneOps , Constants.SUCCESS_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onUpdateMobile(String mobile)
    {
        mView.get().onUpdateMobile(mobile);
        mView.get().showToast(R.string.successfullyDoneOps , Constants.SUCCESS_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onUpdateHasAnbarMasahateMaghaze(int hasAnbar, int masahateMaghaze)
    {
        mView.get().onUpdateHasAnbarMasahateMaghaze(hasAnbar, masahateMaghaze);
        mView.get().showToast(R.string.successfullyDoneOps , Constants.SUCCESS_MESSAGE() , Constants.DURATION_LONG());
    }
	
    @Override
    public void onError(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
