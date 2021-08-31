package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.FaktorDetailsMVP;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorEmzaMoshtaryDAO;
import com.saphamrah.DAO.DarkhastFaktorJayezehDAO;
import com.saphamrah.DAO.DarkhastFaktorTakhfifDAO;
import com.saphamrah.DAO.KalaDarkhastFaktorSatrDAO;
import com.saphamrah.DAO.KalaElamMarjoeeDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;

import java.util.ArrayList;

public class FaktorDetailsModel implements FaktorDetailsMVP.ModelOps
{

    private FaktorDetailsMVP.RequiredPresenterOps mPresenter;

    public FaktorDetailsModel(FaktorDetailsMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getFaktorDetails(long ccDarkhastFaktor)
    {
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

        MoshtaryModel moshtaryModel = new MoshtaryDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
        MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressDAO(mPresenter.getAppContext()).getByccMoshtaryAndccAddress(darkhastFaktorModel.getCcMoshtary(), darkhastFaktorModel.getCcAddressMoshtary());

        mPresenter.onGetFaktorDetails(moshtaryModel.getCodeMoshtary(), moshtaryModel.getNameMoshtary(), darkhastFaktorModel.getExtraProp_MablaghArzeshAfzoodeh(),
                darkhastFaktorModel.getMablaghKolFaktor(), darkhastFaktorModel.getMablaghKhalesFaktor(), darkhastFaktorModel.getNameNoeVosolAzMoshtary(), darkhastFaktorModel.getModateVosol(), moshtaryAddressModel.getAddress());
        int ccNoeMoshtary = moshtaryModel.getCcNoeMoshtary();
        Log.d("Check1 faktordeteail",darkhastFaktorModel.getCcDarkhastFaktor() + "," + ccNoeMoshtary + "," + darkhastFaktorModel.getCcMoshtaryGhardad());


        ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels = new KalaDarkhastFaktorSatrDAO(mPresenter.getAppContext()).getByccDarkhastForDarkhastKala(darkhastFaktorModel.getCcDarkhastFaktor(),ccNoeMoshtary,darkhastFaktorModel.getCcMoshtaryGhardad());
        mPresenter.onGetKala(kalaDarkhastFaktorModels);

        DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifs = darkhastFaktorTakhfifDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
        mPresenter.onGetTakhfif(darkhastFaktorTakhfifs);


        KalaElamMarjoeeDAO kalaElamMarjoeeDAO = new KalaElamMarjoeeDAO(mPresenter.getAppContext());
        ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels = kalaElamMarjoeeDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
        mPresenter.onGetKalaElamMarjoee(kalaElamMarjoeeModels);


        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehs = darkhastFaktorJayezehDAO.getByccDarkhastFaktorAndCodeNoe(darkhastFaktorModel.getCcDarkhastFaktor() , DarkhastFaktorJayezehModel.CodeNoeJayezehAuto());
        mPresenter.onGetJayezeh(darkhastFaktorJayezehs);


        DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels = darkhastFaktorEmzaMoshtaryDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
        if (darkhastFaktorEmzaMoshtaryModels.size() > 0)
        {
            mPresenter.onGetEmzaDetail(darkhastFaktorEmzaMoshtaryModels.get(0));
        }
        else
        {
            mPresenter.onGetEmzaDetail(new DarkhastFaktorEmzaMoshtaryModel());
        }
    }


    @Override
    public void getFaktorDetailsForTreasuryList(long ccDarkhastFaktor)
    {
        Log.d("FaktorDetail" , "ccDarkhastFaktor : " + ccDarkhastFaktor);

        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

        MoshtaryModel moshtaryModel = new MoshtaryDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
        MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressDAO(mPresenter.getAppContext()).getByccMoshtaryAndccAddress(darkhastFaktorModel.getCcMoshtary(), darkhastFaktorModel.getCcAddressMoshtary());

        mPresenter.onGetFaktorDetails(moshtaryModel.getCodeMoshtary(), moshtaryModel.getNameMoshtary(), darkhastFaktorModel.getExtraProp_MablaghArzeshAfzoodeh(),
                darkhastFaktorModel.getMablaghKolFaktor(), darkhastFaktorModel.getMablaghKhalesFaktor(), darkhastFaktorModel.getNameNoeVosolAzMoshtary(), darkhastFaktorModel.getModateVosol(), moshtaryAddressModel.getAddress());


        ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels = new KalaDarkhastFaktorSatrDAO(mPresenter.getAppContext()).getByccDarkhastForTreasury(darkhastFaktorModel.getCcDarkhastFaktor());
        
        mPresenter.onGetKala(kalaDarkhastFaktorModels);

        mPresenter.onGetTakhfif(new ArrayList<DarkhastFaktorTakhfifModel>());
        mPresenter.onGetKalaElamMarjoee(new ArrayList<KalaElamMarjoeeModel>());
        mPresenter.onGetJayezeh(new ArrayList<DarkhastFaktorJayezehModel>());
        mPresenter.onGetEmzaDetail(new DarkhastFaktorEmzaMoshtaryModel());
    }

    @Override
    public void updateDarkhastFaktorEmza(byte[] imageBytes, long ccDarkhastFaktor)
    {
        DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
        if (darkhastFaktorEmzaMoshtaryDAO.updateDarkhastFaktorImage(imageBytes, ccDarkhastFaktor))
        {
            mPresenter.onSuccessUpdateDarkhastFaktorEmza();
        }
        else
        {
            mPresenter.onErrorUpdateDarkhastFaktorEmza();
        }
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
