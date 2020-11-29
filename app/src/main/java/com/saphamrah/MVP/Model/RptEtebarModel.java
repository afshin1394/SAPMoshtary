package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.RptEtebarMVP;
import com.saphamrah.DAO.ForoshandehEtebarDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.Model.ForoshandehEtebarModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.rptEtebarModel.CustomEtebarModel;

public class RptEtebarModel implements RptEtebarMVP.ModelOps {

    private RptEtebarMVP.RequiredPresenterOps mPresenter;
    private ForoshandehEtebarModel mForoshandehEtebarModel;
    private MoshtaryModel mMoshtaryModel;

    public RptEtebarModel(RptEtebarMVP.RequiredPresenterOps presenter) {
        mPresenter = presenter;
    }

    @Override
    public int getCcForoshandeForRptEtebar() {
        int ccForoshande = -1;
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();
        if (foroshandehMamorPakhshModel != null) {
            ccForoshande = foroshandehMamorPakhshModel.getCcForoshandeh();
        }
        return ccForoshande;
    }

    @Override
    public void getEtebar(int ccForoshande, int ccMoshtary, int ccSazmanForosh) {

        CustomEtebarModel customEtebarModel;
        //Get etebar moshtari
        if (ccMoshtary != 0) {
            MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
            MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = moshtaryEtebarSazmanForoshDAO.getByccMoshtaryccSazmanForosh(ccMoshtary, ccSazmanForosh);
            Log.d("etebar","ccForoshande: " + ccForoshande + " ccMoshtary: " +ccMoshtary + " ccSazmanForosh:" + ccSazmanForosh);
            Log.d("etebar","moshtaryEtebarSazmanForoshModel :" + moshtaryEtebarSazmanForoshModel.toString());
            if (moshtaryEtebarSazmanForoshModel != null) {

                customEtebarModel = new CustomEtebarModel(
                        ccMoshtary,
                        moshtaryEtebarSazmanForoshModel.getSaghfEtebarRiali(),
                        moshtaryEtebarSazmanForoshModel.getSaghfEtebarTedadi(),
                        moshtaryEtebarSazmanForoshModel.getSaghfEtebarModat(),

                        moshtaryEtebarSazmanForoshModel.getEtebarRialAsnadShakhsi(),
                        moshtaryEtebarSazmanForoshModel.getEtebarTedadAsnadShakhsi(),
                        moshtaryEtebarSazmanForoshModel.getEtebarModatAsnadShakhsi(),
                        moshtaryEtebarSazmanForoshModel.getEtebarRialAsnadMoshtary(),
                        moshtaryEtebarSazmanForoshModel.getEtebarTedadAsnadMoshtary(),
                        moshtaryEtebarSazmanForoshModel.getEtebarModatAsnadMoshtary(),

                        moshtaryEtebarSazmanForoshModel.getEtebarRialMoavagh(),
                        moshtaryEtebarSazmanForoshModel.getEtebarTedadMoavagh(),
                        moshtaryEtebarSazmanForoshModel.getEtebarModatMoavagh(),

                        moshtaryEtebarSazmanForoshModel.getEtebarRialBargashty(),
                        moshtaryEtebarSazmanForoshModel.getEtebarTedadBargashty(),
                        moshtaryEtebarSazmanForoshModel.getEtebarModatBargashty(),

                        moshtaryEtebarSazmanForoshModel.getRialAsnad(),
                        moshtaryEtebarSazmanForoshModel.getTedadAsnad(),
                        moshtaryEtebarSazmanForoshModel.getModatAsnad(),
                        moshtaryEtebarSazmanForoshModel.getRialMoavagh(),
                        moshtaryEtebarSazmanForoshModel.getTedadMoavagh(),
                        moshtaryEtebarSazmanForoshModel.getModatMoavagh(),

                        moshtaryEtebarSazmanForoshModel.getRialBargashty(),
                        moshtaryEtebarSazmanForoshModel.getTedadBargashty(),
                        moshtaryEtebarSazmanForoshModel.getModatBargashty());

                mPresenter.onGetEtebar(customEtebarModel.generateEtebarList());
            } else {
                mPresenter.onFailedGetEtebar(R.string.errorGetDataAndGetProgram);
            }
        }
        //Get etebar foroshande
        else {
            int cCForoshande = getCcForoshandeForRptEtebar();
            ForoshandehEtebarDAO foroshandehEtebarDAO = new ForoshandehEtebarDAO(mPresenter.getAppContext());
            ForoshandehEtebarModel foroshandehEtebarModel = foroshandehEtebarDAO.getByccForoshandeh(cCForoshande);
            if (foroshandehEtebarModel != null) {
                mForoshandehEtebarModel = foroshandehEtebarModel;
                customEtebarModel = new CustomEtebarModel(
                        ccForoshande,
                        foroshandehEtebarModel.getSaghfEtebarRiali(),
                        foroshandehEtebarModel.getSaghfEtebarTedadi(),
                        foroshandehEtebarModel.getSaghfEtebarModat(),

                        foroshandehEtebarModel.getEtebarRialAsnadShakhsi(),
                        foroshandehEtebarModel.getEtebarTedadAsnadShakhsi(),
                        foroshandehEtebarModel.getEtebarModatAsnadShakhsi(),
                        foroshandehEtebarModel.getEtebarRialAsnadMoshtary(),
                        foroshandehEtebarModel.getEtebarTedadAsnadMoshtary(),
                        foroshandehEtebarModel.getEtebarModatAsnadMoshtary(),

                        foroshandehEtebarModel.getEtebarRialMoavagh(),
                        foroshandehEtebarModel.getEtebarTedadMoavagh(),
                        foroshandehEtebarModel.getEtebarModatMoavagh(),

                        foroshandehEtebarModel.getEtebarRialBargashty(),
                        foroshandehEtebarModel.getEtebarTedadBargashty(),
                        foroshandehEtebarModel.getEtebarModatBargashty(),

                        foroshandehEtebarModel.getRialAsnad(),
                        foroshandehEtebarModel.getTedadAsnad(),
                        foroshandehEtebarModel.getModatAsnad(),
                        foroshandehEtebarModel.getRialMoavagh(),
                        foroshandehEtebarModel.getTedadMoavagh(),
                        foroshandehEtebarModel.getModatMoavagh(),

                        foroshandehEtebarModel.getRialBargashty(),
                        foroshandehEtebarModel.getTedadBargashty(),
                        foroshandehEtebarModel.getModatBargashty());


                mPresenter.onGetEtebar(customEtebarModel.generateEtebarList());
            } else {
                mPresenter.onFailedGetEtebar(R.string.errorGetDataAndGetProgram);
            }
        }


    }



    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }
}
