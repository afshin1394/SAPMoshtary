package com.saphamrah.MVP.Presenter;

import com.saphamrah.MVP.Model.PrintAndShareModel;
import com.saphamrah.BaseMVP.PrintAndShareMVP;
import com.saphamrah.Model.PrintFaktorModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PrintAndSharePresenter implements PrintAndShareMVP.PresenterOps, PrintAndShareMVP.RequiredPresenterOps {

    private WeakReference<PrintAndShareMVP.RequiredViewOps> mView;
    private PrintAndShareMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public PrintAndSharePresenter(PrintAndShareMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new PrintAndShareModel(this);
    }


    @Override
    public void onConfigurationChanged(PrintAndShareMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }




    /////////////////////////// PresenterOps ///////////////////////////
    @Override
    public void onGetAllPrintFaktor(ArrayList<PrintFaktorModel> modelArrayList) {
        mView.get().onGetAllPrintFaktor(modelArrayList);
    }

    @Override
    public void onUpdateData() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.successUpdate, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());

    }

    @Override
    public void failedUpdate() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////
    @Override
    public void update() {
        mModel.update();
    }

    @Override
    public void getAllPrintFaktor() {
        mModel.getAllPrintFaktor();
    }



}
