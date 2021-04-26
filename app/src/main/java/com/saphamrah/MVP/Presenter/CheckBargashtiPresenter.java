package com.saphamrah.MVP.Presenter;


import com.saphamrah.BaseMVP.CheckBargashtiMVP;
import com.saphamrah.MVP.Model.CheckBargashtiModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CheckBargashtiPresenter implements CheckBargashtiMVP.PresenterOps , CheckBargashtiMVP.RequiredPresenterOps
{

    private WeakReference<CheckBargashtiMVP.RequiredViewOps> mView;
    private CheckBargashtiMVP.ModelOps mModel;


    public CheckBargashtiPresenter(CheckBargashtiMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new CheckBargashtiModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(CheckBargashtiMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void onGetAllCheckBargashti() {
        mModel.getAllCheckBargashti();
    }

    @Override
    public void getDariaftPardakhtForSend(long ccDariaftPardakht, int position) {
        mModel.getDariaftPardakhtForSend(ccDariaftPardakht , position);
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
    public void updateListBargashty() {
        mModel.updateListBargashty();
    }

    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public void onGetAllCheckBargashti(ArrayList<BargashtyModel> checkBargashtiModels) {
        mView.get().closeLoadingDialog();
        mView.get().onGetAllCheckBargashti(checkBargashtiModels);
    }
    @Override
    public void onErrorSend(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessSend(int position) {
        mView.get().showAlertMessage(R.string.successSendData , Constants.SUCCESS_MESSAGE());
    }

    @Override
    public void onErrorUpdateListBargashty()  {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


}
