package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptFaktorMandehDarMVP;
import com.saphamrah.BaseMVP.RptMoshtarianKharidNakardeMVP;
import com.saphamrah.MVP.Model.RptFaktorMandehDarModel;
import com.saphamrah.MVP.Model.RptMoshtarianKharidNakardehModel;
import com.saphamrah.Model.RptMoshtaryKharidNakardeModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptMoshtarianKharidNakardePresenter implements RptMoshtarianKharidNakardeMVP.RequiredPresenterOps,RptMoshtarianKharidNakardeMVP.PresenterOps {
    private WeakReference<RptMoshtarianKharidNakardeMVP.RequiredViewOps> mView;
    private RptMoshtarianKharidNakardeMVP.ModelOps mModel;
    private boolean mIsChangingConfig;
    public RptMoshtarianKharidNakardePresenter(RptMoshtarianKharidNakardeMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptMoshtarianKharidNakardehModel(this);
    }

    @Override
    public void onConfigurationChanged(RptMoshtarianKharidNakardeMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getListMoshtarianKharidNakarde() {
        mModel.getListMoshtarianKharidNakarde();
    }

    @Override
    public void updateListMoshtarianKharidNakarde() {
        mModel.updateListMoshtarianKharidNakarde();
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }

    // required presenter ops

    @Override
    public Context getAppContext() {
        return  mView.get().getAppContext();
    }



    @Override
    public void onGetListMoshtarianKharidNakarde(ArrayList<RptMoshtaryKharidNakardeModel> rptMoshtaryKharidNakardeModels) {
       mView.get().onGetListMoshtarianKharidNakarde(rptMoshtaryKharidNakardeModels);
    }

    @Override
    public void onSuccessUpdateListMandehDar() {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void onErrorUpdateListMandehDar(String  type) {
        if (type.equals(Constants.RETROFIT_RESULT_IS_EMPTY())){
            mView.get().closeLoadingDialog();
            mView.get().showToast(R.string.errorGetDataEmpty, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }else {

            mView.get().closeLoadingDialog();
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }

    }
}
