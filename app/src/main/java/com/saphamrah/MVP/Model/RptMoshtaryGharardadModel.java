package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryGharardadDAO;
import com.saphamrah.DAO.MoshtaryGharardadKalaDAO;
import com.saphamrah.BaseMVP.RptMoshtaryGharardadMVP;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.RptMoshtaryGharardadUiModel;

import java.util.ArrayList;

public class RptMoshtaryGharardadModel implements RptMoshtaryGharardadMVP.ModelOps {

    private RptMoshtaryGharardadMVP.RequiredPresenterOps mPresenter;
    private MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
    private MoshtaryGharardadDAO moshtaryGharardadDAO = new MoshtaryGharardadDAO(BaseApplication.getContext());
    private MoshtaryGharardadKalaDAO moshtaryGharardadKalaDAO = new MoshtaryGharardadKalaDAO(BaseApplication.getContext());
    public RptMoshtaryGharardadModel(RptMoshtaryGharardadMVP.RequiredPresenterOps presenterOps)
    {
        mPresenter = presenterOps;
    }


    @Override
    public void getMoshtary() {
        ArrayList<MoshtaryModel> moshtaryModels = moshtaryDAO.getMoshtaryZanjiree();

        if (moshtaryModels.size() > 0)
        {
            ArrayList<String> titles = new ArrayList<>();
            for (MoshtaryModel model : moshtaryModels)
            {
                titles.add(model.getNameMoshtary());
            }
            mPresenter.onGetMoshtary(moshtaryModels,titles);
        }
        else
        {
            mPresenter.onError(R.string.haveNotMoshtary,true);

        }


    }

    @Override
    public void getGharardadMoshtary(int ccMoshtary) {
        Log.d("RptMoshtaryGharardad", "ccMoshtaryParent:" + ccMoshtary);
       ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels = moshtaryGharardadDAO.getByccMoshtary(ccMoshtary);
        if (moshtaryGharardadModels.size() > 0)
        {
            ArrayList<String> titles = new ArrayList<>();
            for (MoshtaryGharardadModel model : moshtaryGharardadModels)
            {
                titles.add(model.getShomarehGharardad() + " - " + model.getNameSazmanForosh());
            }
            mPresenter.onGetMoshtaryGharardad(moshtaryGharardadModels,titles);
        }
        else
        {
            mPresenter.onError(R.string.haveNotMoshtaryGharardad,false);
        }
    }

    @Override
    public void getKala(String ccMoshtaryGharardad, String ccSazmanForosh,int from) {
        ArrayList<RptMoshtaryGharardadUiModel> models = new ArrayList<>();
        if (from == 1){
            SelectFaktorShared shared = new SelectFaktorShared(BaseApplication.getContext());
            int ccMoshtaryGharardadShare = shared.getInt(shared.getCcMoshtaryGharardad(), -1);
            int ccSazmanForoshShare = shared.getInt(shared.getMoshtaryGharardadccSazmanForosh(), -1);
            int ccMoshtaryParentShare = shared.getInt(shared.getCcMoshtaryParent(),-1);
            int ccMoshtaryshare = shared.getInt(shared.getCcMoshtary(),-1);

            MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtaryshare);
            MoshtaryGharardadModel moshtaryGharardadModel = moshtaryGharardadDAO.getMoshtaryGharardadByCcMoshtaryAndCcSazmanForosh(ccMoshtaryParentShare,ccSazmanForoshShare);
            mPresenter.onDetailsMoshtary(moshtaryModel.getNameMoshtary() , moshtaryModel.getCodeMoshtary() , moshtaryGharardadModel.getShomarehGharardad() , moshtaryGharardadModel.getNameSazmanForosh());
            models = moshtaryGharardadKalaDAO.getKalaByCcMoshtaryGharardadAndCcSazmanForosh(String.valueOf(ccMoshtaryGharardadShare),String.valueOf(ccSazmanForoshShare));

        }
        else if (from == 0){
          models = moshtaryGharardadKalaDAO.getKalaByCcMoshtaryGharardadAndCcSazmanForosh(ccMoshtaryGharardad,ccSazmanForosh);

        }

       mPresenter.onGetKala(models);
    }
}

