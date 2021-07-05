package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.KalaAmargarMVP;
import com.saphamrah.DAO.BrandDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaGorohDAO;
import com.saphamrah.DAO.PorseshnamehDAO;
import com.saphamrah.DAO.PorseshnamehShomareshDAO;
import com.saphamrah.DAO.VisitMoshtaryDAO;
import com.saphamrah.Model.BrandModel;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.Model.PorseshnamehShomareshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class KalaAmargarModel implements KalaAmargarMVP.ModelOps
{

    private KalaAmargarMVP.RequiredPresenterOps mPresenter;

    public KalaAmargarModel(KalaAmargarMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getAllGoods(int ccPorseshnameh)
    {
       KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
        ArrayList<KalaModel> kalaModels = kalaDAO.getAll();
        Map<Integer,Integer> porseshnamehShomareshModels = new PorseshnamehShomareshDAO(mPresenter.getAppContext()).getGoodsCountByPorseshnameh(ccPorseshnameh);

        for (Integer ccKala : porseshnamehShomareshModels.keySet())
        {
            Log.d("KalaAmargar" , "ccKala : " + ccKala + " , count : " + porseshnamehShomareshModels.get(ccKala));
        }

        mPresenter.onGetGoods(kalaModels, porseshnamehShomareshModels);
    }

    @Override
    public void getAllBrands()
    {
        BrandDAO brandDAO = new BrandDAO(mPresenter.getAppContext());
        List<BrandModel> brandModels = brandDAO.getAllDistinct();
        mPresenter.onGetAllBrands(brandModels);
    }

    @Override
    public void getGorohKala(Integer selectedBrandId)
    {
        KalaGorohDAO kalaGorohDAO = new KalaGorohDAO(mPresenter.getAppContext());
        List<KalaGorohModel> kalaGorohModels = kalaGorohDAO.getGorohByBrand(selectedBrandId);
        mPresenter.onGetGorohKala(kalaGorohModels);
    }

    @Override
    public void getGoodsByGorohKala(Integer selectedBrandId, Integer selectedKalaGorohId)
    {
        KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
        List<KalaModel> kalaModels = kalaDAO.getAllByBrandAndGoroh(selectedBrandId, selectedKalaGorohId);
        mPresenter.onGetKalaByGorohKala(kalaModels);
    }

    @Override
    public void deleteAllInsertedGoods(int ccPorseshname)
    {
        new PorseshnamehShomareshDAO(mPresenter.getAppContext()).delete(ccPorseshname);
    }

    @Override
    public void insertGoodsCount(Map<Integer, Integer> mapGoodsCount, int ccPorseshname)
    {
        List<PorseshnamehShomareshModel> porseshnamehShomareshModels = new ArrayList<>();
        PorseshnamehShomareshModel porseshnamehShomareshModel;
        for (Map.Entry<Integer,Integer> kalaCount : mapGoodsCount.entrySet())
        {
            if (kalaCount.getValue() > -1)
            {
                porseshnamehShomareshModel = new PorseshnamehShomareshModel();
                porseshnamehShomareshModel.setCcPorseshnameh(ccPorseshname);
                porseshnamehShomareshModel.setCcKala(kalaCount.getKey());
                porseshnamehShomareshModel.setTedadShomaresh(kalaCount.getValue());

                porseshnamehShomareshModels.add(porseshnamehShomareshModel);
            }
        }
        PorseshnamehShomareshDAO porseshnamehShomareshDAO = new PorseshnamehShomareshDAO(mPresenter.getAppContext());
        new VisitMoshtaryDAO(mPresenter.getAppContext()).updateSaateKhoroj(ccPorseshname, new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
        mPresenter.onInsertGoodsCount(porseshnamehShomareshDAO.insertGroup(porseshnamehShomareshModels));
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }
}
