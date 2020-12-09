package com.saphamrah.MVP.Presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.GoodsInfoMVP;
import com.saphamrah.MVP.Model.GoodsInfoModel;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.RptKalaInfoModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GoodsInfoPresenter implements GoodsInfoMVP.PresenterOps, GoodsInfoMVP.RequiredPresenterOps {

    private GoodsInfoMVP.ModelOps mModel;
    private WeakReference<GoodsInfoMVP.RequiredViewOps> mView;


    public GoodsInfoPresenter(GoodsInfoMVP.RequiredViewOps mView) {
        this.mView = new WeakReference<>(mView);
        mModel = new GoodsInfoModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(GoodsInfoMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getListOfGoods() {
        mModel.getListOfGoods();
    }

    @Override
    public void updateGallery() {
        mModel.updateGallery();
    }

    @Override
    public void getGallery() {
        mModel.getGallery();
    }

    @Override
    public void onStartProgressBar() {

    }

    @Override
    public void updateGoodsList() {
        mModel.updateGoodsList();
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }

    @Override
    public void getKalaInfoCcBrandList() {
        mModel.getKalaInfoCcBrandList();
    }

    @Override
    public void getKalaByCcBrand(String ccBrands) {
        mModel.getKalaByCcBrand(ccBrands);
    }

    @Override
    public void getKalaInfoCcGorohList(String ccBrands) {
        mModel.getKalaInfoCcGorohList(ccBrands);
    }

    @Override
    public void getKalaByCcGoroh(String ccBrands) {
        mModel.getKalaByCcGoroh(ccBrands);
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetListOfGoods(ArrayList<RptKalaInfoModel> rptKalaInfoModels) {
        if (rptKalaInfoModels.size() > 0) {
            mView.get().onGetListOfGoods(rptKalaInfoModels);
        } else {
            mView.get().showToast(R.string.emptyList, Constants.FAILED_MESSAGE());
        }
    }

    @Override
    public void onGetGallery(ArrayList<KalaPhotoModel> kalaPhotoModels) {
      mView.get().onGetGallery(kalaPhotoModels);
    }

    @Override
    public void startProgressBar() {
        mView.get().onStartProgressBar();
    }

    @Override
    public void updateProgress(int progress) {
        if (progress > 0) {
            mView.get().updateProgressBar(progress);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCompleteUpdateGallery(ArrayList<Integer> messages,ArrayList<KalaPhotoModel> kalaPhotoModels) {

        Log.i("--OnCompleteUpdateGallery--", "onCompleteUpdateGallery"+messages.size());

        if (messages.contains(-1)){
            Log.i("--OnCompleteUpdateGallery--", "onCompleteUpdateGallery: contains -1");

            mView.get().showToast(R.string.updateNotComplete, Constants.RESPONSE_NOT_COMPLETE());
            mView.get().closeProgressBar();
        }else{
            Log.i("--OnCompleteUpdateGallery--", "onCompleteUpdateGallery:dont contain -1");
            mView.get().onUpdateGallery(kalaPhotoModels);
            mView.get().showToast(R.string.updateSuccessed,Constants.SUCCESS_MESSAGE());
            mView.get().closeProgressBar();
        }


    }

    @Override
    public void onUpdateGoodsList(boolean status, int resId) {
        mView.get().closeLoading();
        if (status) {
            mView.get().showToast(resId, Constants.SUCCESS_MESSAGE());
        } else {
            mView.get().showAlert(resId, Constants.FAILED_MESSAGE());
        }
    }

    @Override
    public void onError(int resId) {
        mView.get().closeProgressBar();
        mView.get().showAlert(resId, Constants.FAILED_MESSAGE());
    }

    @Override
    public void onGetKalaInfoCcBrandList(ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList) {
        mView.get().onGetKalaInfoCcBrandList(kalaInfoByCcBrandList);
    }

    @Override
    public void onGetKalaInfoCcGorohList(ArrayList<RptKalaInfoModel> kalaInfoByCcGorohList) {
        mView.get().onGetKalaInfoCcGorohList(kalaInfoByCcGorohList);
    }
}
