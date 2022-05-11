package com.saphamrah.MVP.Presenter.marjoee;

import com.saphamrah.BaseMVP.marjoee.DarkhastFaktorMarjoeeMVP;
import com.saphamrah.BaseMVP.marjoee.MarjoeeForoshandehMVP;
import com.saphamrah.MVP.Model.marjoee.DarkhastFaktorMarjoeeModel;
import com.saphamrah.MVP.Model.marjoee.MarjoeeForoshandehModel;
import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DarkhastFaktorMarjoeePresenter implements DarkhastFaktorMarjoeeMVP.PresenterOps, DarkhastFaktorMarjoeeMVP.RequiredPresenterOps {

    private WeakReference<DarkhastFaktorMarjoeeMVP.RequiredViewOps> mView;
    private DarkhastFaktorMarjoeeMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public DarkhastFaktorMarjoeePresenter(DarkhastFaktorMarjoeeMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new DarkhastFaktorMarjoeeModel(this);
    }

    @Override
    public void onConfigurationChanged(DarkhastFaktorMarjoeeMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals("")) {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }


    @Override
    public void sendMarjoee(long ccDarkhastFaktor, int ccMoshtary) {
        mModel.sendMarjoee(ccDarkhastFaktor, ccMoshtary);
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////




    @Override
    public void onSuccessSend() {
        mView.get().closeLoading();
        mView.get().onSuccessSend();
    }

    @Override
    public void onErrorSend(int resId) {
        mView.get().closeLoading();
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


}
