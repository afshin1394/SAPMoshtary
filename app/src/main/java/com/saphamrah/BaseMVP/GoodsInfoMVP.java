package com.saphamrah.BaseMVP;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.RptKalaInfoModel;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public interface GoodsInfoMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetListOfGoods(ArrayList<RptKalaInfoModel> rptKalaInfoModels);
        void updateProgressBar(int progress);
        void onStartProgressBar();
        void onUpdateGallery(ArrayList<KalaPhotoModel> kalaPhotoModels);
        void onGetGallery(ArrayList<KalaPhotoModel> kalaPhotoModels);
        void closeProgressBar();
        void closeLoading();
        void showAlert(String resId, int messageType);
        void showToast(String resId, int messageType);
        void onFinishProgress();
        void onGetKalaInfoCcBrandList(ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList);
        void onGetKalaInfoCcGorohList(ArrayList<RptKalaInfoModel> kalaInfoByCcGorohList);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void getListOfGoods();
        void updateGallery();
        void getGallery();
        void onStartProgressBar();
        void updateGoodsList();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void onFinishProgress();
        void getKalaInfoCcBrandList();
        void getKalaByCcBrand(String ccBrands);

        void getKalaInfoCcGorohList(String ccBrands);
        void getKalaByCcGoroh(String ccBrands);


    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetListOfGoods(ArrayList<RptKalaInfoModel> rptKalaInfoModels);
        void onGetGallery(ArrayList<KalaPhotoModel> kalaPhotoModels);
        void startProgressBar();
        void finishProgress();
        void updateProgress(int progress);
        void onCompleteUpdateGallery(ArrayList<KalaPhotoModel> kalaPhotoModels);
        void onUpdateGoodsList(boolean status , String message);
        void onError(String message);
        void onGetKalaInfoCcBrandList(ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList);
        void onGetKalaInfoCcGorohList(ArrayList<RptKalaInfoModel> kalaInfoByCcGorohList);


    }


    interface ModelOps
    {
        void getListOfGoods();
        void updateGallery();
        void getGallery();
        void updateGoodsList();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void getKalaInfoCcBrandList();
        void getKalaByCcBrand(String ccBrands);
        void getKalaInfoCcGorohList(String ccBrands);
        void getKalaByCcGoroh(String ccBrands);
    }

}
