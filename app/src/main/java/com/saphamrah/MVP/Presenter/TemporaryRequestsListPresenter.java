package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.TemporaryRequestsListMVP;
import com.saphamrah.MVP.Model.TemporaryRequestsListModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerAdamDarkhastModel;
import com.saphamrah.UIModel.CustomerDarkhastFaktorModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TemporaryRequestsListPresenter implements TemporaryRequestsListMVP.PresenterOps , TemporaryRequestsListMVP.RequiredPresenterOps
{


    private WeakReference<TemporaryRequestsListMVP.RequiredViewOps> mView;
    private TemporaryRequestsListMVP.ModelOps mModel;
    private CompositeDisposable compositeDisposable;
    //private boolean mIsChangingConfig;

    public TemporaryRequestsListPresenter(TemporaryRequestsListMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new TemporaryRequestsListModel(this);
        compositeDisposable=new CompositeDisposable();
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(TemporaryRequestsListMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

	@Override
    public void getMyIP()
    {
        mModel.getMyIP();
    }
	
    @Override
    public void getTemporaryRequests()
    {
        mModel.getTemporaryRequests();
    }

    @Override
    public void getTemporaryNoRequests()
    {
        mModel.getTemporaryNoRequests();
    }

    @Override
    public void checkSelectedActionOnTempRequest(int action, int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        if (action == Constants.DELETE())
        {
            mView.get().showDeleteAlert(position , customerDarkhastFaktorModel);
        }
        else if (action == Constants.SEND())
        {
            mView.get().showSendAlert(position , customerDarkhastFaktorModel);
        }
        else if (action == Constants.PRINT())
        {
            mModel.printTempRequest(position , customerDarkhastFaktorModel);
        }
        else if (action == Constants.SAVE_IMAGE())
        {
            mModel.saveImageTempRequest(position , customerDarkhastFaktorModel);
        }
        else if (action == Constants.CAPTURE_RECEIPT())
        {
            mView.get().openCamera(position , customerDarkhastFaktorModel);
        }
    }

    @Override
    public void checkDeleteTempRequest(int position , CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        mModel.deleteTempRequest(position , customerDarkhastFaktorModel);
    }

    @Override
    public void checkSelectedActionOnNoTempRequest(int action, int position, CustomerAdamDarkhastModel customerAdamDarkhastModel)
    {
        if (position >= 0)
        {
            if (action == Constants.SEND())
            {
                mModel.sendTempNoRequest(position , customerAdamDarkhastModel);
            }
            else if (action == Constants.DELETE())
            {
                mView.get().showDeleteNoRequestAlert(position , customerAdamDarkhastModel);
            }
        }
        else
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkDeleteTempNoRequest(int position, CustomerAdamDarkhastModel customerAdamDarkhastModel)
    {
        mModel.deleteTempNoRequest(position , customerAdamDarkhastModel);
    }

    @Override
    public void checkSendTempRequest(int position, CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        if (position >= 0 && customerDarkhastFaktorModel != null)
        {
            if (customerDarkhastFaktorModel.getHaveFaktorImage() && customerDarkhastFaktorModel.getHaveEmzaImage())
            {
                mModel.sendTempRequest(position , customerDarkhastFaktorModel);
            }
            else
            {
                mView.get().onErrorSendRequest(R.string.errorFaktorImage,"");
            }
        }
        else
        {
            mView.get().onErrorSendRequest(R.string.errorFaktorImage,"");
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

    @Override
    public void unBindDisposable() {
        if (!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
            compositeDisposable.clear();
            compositeDisposable=null;
        }
    }

    @Override
    public void insertReceiptImage(byte[] imageBytes,int position, CustomerDarkhastFaktorModel customerDarkhastFaktorModel) {
        mModel.insertReceiptImage(imageBytes,position,customerDarkhastFaktorModel);
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetTemporaryRequests(ArrayList<CustomerDarkhastFaktorModel> models , int noeForoshandehMamorPakhsh,boolean showReceiptImage)
    {
        if (models.size() > 0)
        {
            mView.get().onGetTemporaryRequests(models , noeForoshandehMamorPakhsh,showReceiptImage);
        }
        else
        {
            mView.get().showToast(R.string.emptyList, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetTemporaryNoRequests(ArrayList<CustomerAdamDarkhastModel> models)
    {
        if (models.size() > 0)
        {
            mView.get().onGetTemporaryNoRequests(models);
        }
        else
        {
            mView.get().showToast(R.string.emptyList, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onCheckPrint(CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        mView.get().openPrintActivity(customerDarkhastFaktorModel.getCcDarkhastFaktor());
    }

    @Override
    public void onCheckSaveImage(long ccDarkhastFaktor,int type)
    {
        mView.get().openSaveImageActivity(ccDarkhastFaktor,type);
    }

    @Override
    public void onSuccessDeleteTempRequest(int position)
    {
        mView.get().onSuccessDeleteTempRequest(position);
    }

    @Override
    public void onErrorFindServer()
    {
        mView.get().showToast(R.string.cantFindServer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onNetworkError(int errorId)
    {
        mView.get().showToast(errorId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onErrorSendRequest(int errorId,String message)
    {
        mView.get().onErrorSendRequest(errorId,message);
    }

	@Override
    public void onErrorSendOtherDataOfFaktor()
    {
        mView.get().showToast(R.string.errorSendOtherData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
	
    @Override
    public void onError(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessDeleteNoRequest(int position)
    {
        mView.get().onSuccessDeleteNoRequest(position);
    }

    @Override
    public void onSuccessSendNoRequest(int position)
    {
        mView.get().onSuccessSendNoRequest(position);
    }

    @Override
    public void onSuccessSendRequest(int position , long ccDarkhastFaktorNew)
    {
        mView.get().onSuccessSendRequest(position , ccDarkhastFaktorNew);
    }

    @Override
    public void bindDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onSuccessSaveReceiptImage(int resId,int position) {
        mView.get().onSuccessSaveReceiptImage(resId,position);
    }

    @Override
    public void onErrorSaveImage() {
        mView.get().onErrorSendRequest(R.string.errorFaktorImageResid,"");
    }

}
