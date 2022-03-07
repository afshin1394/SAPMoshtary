package com.saphamrah.MVP.Presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.MVP.Model.PrintAndShareModel;
import com.saphamrah.BaseMVP.PrintAndShareMVP;
import com.saphamrah.MVP.View.PrintAndShareActivity;
import com.saphamrah.Model.PrintFaktorModel;
import com.saphamrah.Model.SystemConfigTabletModel;
import com.saphamrah.PubFunc.ImageUtils;
import com.saphamrah.R;
import com.saphamrah.Repository.PrintFaktorRepository;
import com.saphamrah.Utils.BixolonPrinter;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.UrovoPrinter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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

    public void onGetImagePrintFaktor(PrintFaktorModel model) {
        mView.get().onGetImagePrintFaktor(model);
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

    @Override
    public void onGetPrintFaktor(String uniqueID,int action,byte[] image) {
        mView.get().closeLoadingDialog();
        mView.get().onGetPrintfaktor(uniqueID,action,image);
    }

    @Override
    public void onError(int resID) {
        mView.get().closeLoadingDialog();
        mView.get().showToast(resID, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

    }

    @Override
    public void onWarning(int resID) {
        mView.get().closeLoadingDialog();
        mView.get().showToast(resID, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
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

    @Override
    public void getImagePrintFaktor(String UniqID) {
        mModel.getImagePrintFaktor(UniqID);
    }

    public void getFaktorImage( int action , PrintFaktorModel printFaktorModel) {
        mView.get().showLoading();
        mModel.getFaktorImage( action , printFaktorModel);
    }
}
