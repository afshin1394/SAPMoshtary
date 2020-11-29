package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.KalaAmargarMVP;
import com.saphamrah.MVP.Model.KalaAmargarModel;
import com.saphamrah.Model.BrandModel;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.PorseshnamehShomareshModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KalaAmargarPresenter implements KalaAmargarMVP.PresenterOps, KalaAmargarMVP.RequiredPresenterOps
{

    private WeakReference<KalaAmargarMVP.RequiredViewOps> mView;
    private KalaAmargarModel mModel;

    public KalaAmargarPresenter(KalaAmargarMVP.RequiredViewOps viewOps)
    {
        mView = new WeakReference<>(viewOps);
        mModel = new KalaAmargarModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(KalaAmargarMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getAllGoods(int ccPorseshnameh)
    {
        mModel.getAllGoods(ccPorseshnameh);
    }

    @Override
    public void getAllBrands()
    {
        mModel.getAllBrands();
    }

    @Override
    public void getGorohKala(Integer selectedBrandId)
    {
        if (selectedBrandId != null && selectedBrandId > 0)
        {
            mModel.getGorohKala(selectedBrandId);
        }
        else
        {
            mView.get().showErrorSelectBrand();
        }
    }

    @Override
    public void getGoodsByGorohKala(Integer selectedBrandId, Integer selectedKalaGorohId)
    {
        if (selectedKalaGorohId == null || selectedKalaGorohId == 0)
        {
            mView.get().showErrorSelectGorohKala();
            return;
        }
        else if (selectedBrandId == null || selectedBrandId == 0)
        {
            mView.get().showErrorSelectBrand();
            return;
        }
        else
        {
            mModel.getGoodsByGorohKala(selectedBrandId, selectedKalaGorohId);
        }
    }

    @Override
    public void insertGoodsCount(Map<Integer, Integer> mapGoodsCount, int ccPorseshname)
    {
        mModel.deleteAllInsertedGoods(ccPorseshname);
        if (mapGoodsCount.size() > 0)
        {
            mModel.insertGoodsCount(mapGoodsCount, ccPorseshname);
        }
        else
        {
            mView.get().closeActivityAndOpenReport();
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }

    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetGoods(List<KalaModel> kalaModels, Map<Integer,Integer> porseshnamehShomareshModels)
    {
        if (kalaModels.size() > 0)
        {
            for (KalaModel kalaModel : kalaModels)
            {
                Integer count = porseshnamehShomareshModels.get(kalaModel.getCcKalaCode());
                count = (count == null || count == 0) ? -1 : count;
                kalaModel.setAdad(count);
            }
            mView.get().showGoods(kalaModels, porseshnamehShomareshModels);
        }
        else
        {
            mView.get().showErrorNotFoundGoods();
        }
    }

    @Override
    public void onGetKalaByGorohKala(List<KalaModel> kalaModels)
    {
        mView.get().showGoods(kalaModels, null);
        if (kalaModels.size() == 0)
        {
            mView.get().showErrorNotFoundGoods();
        }
    }

    @Override
    public void onGetAllBrands(List<BrandModel> brandModels)
    {
        List<String> brandTitles = new ArrayList<>();
        for (BrandModel model : brandModels)
        {
            brandTitles.add(model.getNameBrand());
        }
        mView.get().onGetAllBrands(brandModels, brandTitles);
    }

    @Override
    public void onGetGorohKala(List<KalaGorohModel> kalaGorohModels)
    {
        List<String> titles = new ArrayList<>();
        for (KalaGorohModel gorohModel : kalaGorohModels)
        {
            titles.add(gorohModel.getNameGoroh());
        }
        mView.get().onGetGorohKala(kalaGorohModels, titles);
    }

    @Override
    public void onInsertGoodsCount(boolean insertResult)
    {
        if (insertResult)
        {
            mView.get().closeActivityAndOpenReport();
        }
        else
        {
            mView.get().showErrorInsert();
        }
    }
}
