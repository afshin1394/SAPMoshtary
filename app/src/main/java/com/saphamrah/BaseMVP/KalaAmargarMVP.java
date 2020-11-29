package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.BrandModel;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.PorseshnamehShomareshModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface KalaAmargarMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void showGoods(List<KalaModel> kalaModels, Map<Integer,Integer> porseshnamehShomareshModels);
        void onGetAllBrands(List<BrandModel> brandModels, List<String> brandTitles);
        void onGetGorohKala(List<KalaGorohModel> kalaGorohModels, List<String> titles);
        void closeActivityAndOpenReport();
        void showErrorNotFoundGoods();
        void showErrorInsert();
        void showErrorSelectBrand();
        void showErrorSelectGorohKala();
    }


    interface PresenterOps
    {
        void onConfigurationChanged(KalaAmargarMVP.RequiredViewOps view);
        void getAllGoods(int ccPorseshnameh);
        void getAllBrands();
        void getGorohKala(Integer selectedBrandId);
        void getGoodsByGorohKala(Integer selectedBrandId, Integer selectedKalaGorohId);
        void insertGoodsCount(Map<Integer, Integer> mapGoodsCount, int ccPorseshname);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetGoods(List<KalaModel> kalaModels, Map<Integer,Integer> porseshnamehShomareshModels);
        void onGetKalaByGorohKala(List<KalaModel> kalaModels);
        void onGetAllBrands(List<BrandModel> brandModels);
        void onGetGorohKala(List<KalaGorohModel> kalaGorohModels);
        void onInsertGoodsCount(boolean insertResult);
        void onConfigurationChanged(KalaAmargarMVP.RequiredViewOps view);
    }


    interface ModelOps
    {
        void getAllGoods(int ccPorseshnameh);
        void getAllBrands();
        void getGorohKala(Integer selectedBrandId);
        void getGoodsByGorohKala(Integer selectedBrandId, Integer selectedKalaGorohId);
        void deleteAllInsertedGoods(int ccPorseshname);
        void insertGoodsCount(Map<Integer, Integer> mapGoodsCount, int ccPorseshname);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
    }

}
