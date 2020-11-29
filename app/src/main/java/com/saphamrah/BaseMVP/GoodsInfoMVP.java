package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.RptKalaInfoModel;

import java.util.ArrayList;

public interface GoodsInfoMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetListOfGoods(ArrayList<RptKalaInfoModel> rptKalaInfoModels);
        void updateProgressBar(int progress);
        void closeProgressBar();
        void closeLoading();
        void showAlert(int resId, int messageType);
        void showToast(int resId, int messageType);

        void onGetKalaInfoCcBrandList(ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList);
        void onGetKalaInfoCcGorohList(ArrayList<RptKalaInfoModel> kalaInfoByCcGorohList);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void getListOfGoods();
        void updateGallery();
        void updateGoodsList();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);

        void getKalaInfoCcBrandList();
        void getKalaByCcBrand(String ccBrands);

        void getKalaInfoCcGorohList(String ccBrands);
        void getKalaByCcGoroh(String ccBrands);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetListOfGoods(ArrayList<RptKalaInfoModel> rptKalaInfoModels);
        void updateProgress(int progress);
        void onSuccessUpdateGallery();
        void onUpdateGoodsList(boolean status, int resId);
        void onError(int resId);

        void onGetKalaInfoCcBrandList(ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList);
        void onGetKalaInfoCcGorohList(ArrayList<RptKalaInfoModel> kalaInfoByCcGorohList);

    }


    interface ModelOps
    {
        void getListOfGoods();
        void updateGallery();
        void updateGoodsList();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();

        void getKalaInfoCcBrandList();
        void getKalaByCcBrand(String ccBrands);
        void getKalaInfoCcGorohList(String ccBrands);
        void getKalaByCcGoroh(String ccBrands);
    }
    
}
