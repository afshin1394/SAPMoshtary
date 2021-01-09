package com.saphamrah.MVP.Model;


import android.util.Log;

import com.saphamrah.BaseMVP.MarjoeeKalaMVP;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ElamMarjoeePPCDAO;
import com.saphamrah.DAO.ElamMarjoeeSatrPPCDAO;
import com.saphamrah.DAO.ElatMarjoeeKalaDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KalaElamMarjoeeDAO;
import com.saphamrah.DAO.ListKalaForMarjoeeDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ElamMarjoeePPCModel;
import com.saphamrah.Model.ElamMarjoeeSatrPPCModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ListKalaForMarjoeeModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MarjoeeKalaModel implements MarjoeeKalaMVP.ModelOps
{

    private MarjoeeKalaMVP.RequiredPresenterOps mPresenter;

    public MarjoeeKalaModel(MarjoeeKalaMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getForoshandehMamorPakhshInfo()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        if (foroshandehMamorPakhshModel != null)
        {
            int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
            mPresenter.onGetForoshandehMamorPakhshInfo(noeMasouliat , foroshandehMamorPakhshModel.getNoeSabtMarjoee());
        }
        else
        {
            mPresenter.onGetForoshandehMamorPakhshInfo(-1 ,-1);
        }
    }

    @Override
    public void updateListKalaForMarjoee(long ccDarkhastFaktor)
    {
        final ListKalaForMarjoeeDAO listKalaForMarjoeeDAO = new ListKalaForMarjoeeDAO(mPresenter.getAppContext());
        final ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels = new ArrayList<>();
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        String ccForoshandeh = String.valueOf(shared.getInt(shared.getCcForoshandeh() , 0));
        String ccMoshtary = String.valueOf(shared.getInt(shared.getCcMoshtary() , 0));

        listKalaForMarjoeeDAO.fetchListKala(mPresenter.getAppContext(), "MarjoeeKalaActivity", ccForoshandeh, ccMoshtary, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                boolean deleteResult = listKalaForMarjoeeDAO.deleteAll();
                boolean insertResult = listKalaForMarjoeeDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult)
                {
                    listKalaForMarjoeeModels.addAll(arrayListData);
                    mPresenter.onSuccessUpdateListKalaForMarjoee(listKalaForMarjoeeModels);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                String message = type + "\n" + error;
                setLogToDB(Constants.LOG_EXCEPTION(), message, "MarjoeeKalaModel", "", "getListKalaForMarjoee", "onFailed");
                mPresenter.onErrorUpdateListKalaForMarjoee(message);
            }
        });

    }

    @Override
    public void getListElatMarjoee()
    {
        ElatMarjoeeKalaDAO elatMarjoeeKalaDAO = new ElatMarjoeeKalaDAO(mPresenter.getAppContext());
        ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels = elatMarjoeeKalaDAO.getAll();
        mPresenter.onGetListElatMarjoeeKala(elatMarjoeeKalaModels);
    }

    @Override
    public void getKalaMarjoee(long ccDarkhastFaktor)
    {
        KalaElamMarjoeeDAO kalaElamMarjoeeDAO = new KalaElamMarjoeeDAO(mPresenter.getAppContext());
        ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels = kalaElamMarjoeeDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        Log.d("MarjoeeKala","kalaElamMarjoeeModels: " + kalaElamMarjoeeModels.toString());
        mPresenter.onGetKalaMarjoee(kalaElamMarjoeeModels);
    }

    @Override
    public void insertKalaToMarjoee(ElamMarjoeeSatrPPCModel elamMarjoeeSatrPPCModel , int ccMoshtary)
    {
        Log.d("MarjoeeKala" , "elamMarjoeeSatrPPCModel : " + elamMarjoeeSatrPPCModel.toString());
        Log.d("MarjoeeKala" , "ccDarkhastFaktor : " + elamMarjoeeSatrPPCModel.getCcDarkhastFaktor());
        ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
        ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
        int ccElamMarjoee = elamMarjoeePPCDAO.getccElamMarjoeeByccDarkhastFaktor(elamMarjoeeSatrPPCModel.getCcDarkhastFaktor());
        Log.d("MarjoeeKala" , "ccElamMarjoee before insert satr : " + ccElamMarjoee);
        if (ccElamMarjoee > 0)
        {
            Log.d("MarjoeeKala" , "in if ,  ccElamMarjoee before insert satr : " + ccElamMarjoee);
            elamMarjoeeSatrPPCModel.setCcElamMarjoeePPC(ccElamMarjoee);
            Log.d("MarjoeeKala" , "in if ,  getCcElamMarjoeePPC(ccElamMarjoee) : " + elamMarjoeeSatrPPCModel.getCcElamMarjoeePPC());
            if (elamMarjoeeSatrPPCDAO.insert(elamMarjoeeSatrPPCModel))
            {
                getKalaMarjoee(elamMarjoeeSatrPPCModel.getCcDarkhastFaktor());
                mPresenter.onSuccessInsertMarjoee();
            }
            else
            {
                mPresenter.onFailedInsertMarjoee();
            }
        }
        else
        {
            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
            int ccForoshandeh = selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh() , 0);
            ElamMarjoeePPCModel elamMarjoeePPCModel = new ElamMarjoeePPCModel();
            elamMarjoeePPCModel.setTedadAghlamMarjoee(0);
            elamMarjoeePPCModel.setElat("");
            elamMarjoeePPCModel.setTarikhElamMarjoee(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
            elamMarjoeePPCModel.setCcMoshtary(ccMoshtary);
            elamMarjoeePPCModel.setCcForoshandeh(ccForoshandeh);
            elamMarjoeePPCModel.setCcDarkhastFaktor(elamMarjoeeSatrPPCModel.getCcDarkhastFaktor());

            ccElamMarjoee = elamMarjoeePPCDAO.insert(elamMarjoeePPCModel);
            Log.d("marjoeeKala" , "ccElamMarjoee after insert titr : " + ccElamMarjoee);
            if (ccElamMarjoee > 0)
            {
                elamMarjoeeSatrPPCModel.setCcElamMarjoeePPC(ccElamMarjoee);
                if (elamMarjoeeSatrPPCDAO.insert(elamMarjoeeSatrPPCModel))
                {
                    getKalaMarjoee(elamMarjoeeSatrPPCModel.getCcDarkhastFaktor());
                    mPresenter.onSuccessInsertMarjoee();
                }
                else
                {
                    mPresenter.onFailedInsertMarjoee();
                }
            }
            else
            {
                mPresenter.onFailedInsertMarjoee();
            }
        }
    }

    @Override
    public void insertDariaftPardakht(long ccDarkhastFaktor , int ccMoshtary)
    {
        long ccDariaftPardakht;
        long MablaghKolMarjoeeMoshtary = 0;
        String codeNoeVosolMarjoee = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_CODE_NOE_VOSOL_MARJOEE());
        //int ccAfradForoshandeh = 0;
        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        DariaftPardakhtPPCDAO dariaftPardakhtDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
        DariaftPardakhtPPCModel dariaftPardakhtPPC = new DariaftPardakhtPPCModel();
        try
        {
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
            MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            //ccAfradForoshandeh = foroshandehMamorPakhshModel.getCcAfrad();
            ElamMarjoeeSatrPPCDAO elammarjoeesatrDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
            MablaghKolMarjoeeMoshtary = elammarjoeesatrDAO.getSumMablaghMarjoeeByccDarkhastFaktor(ccDarkhastFaktor);

            dariaftPardakhtDAO.deleteByccDarkhastFaktorAndCodeNoeVosol(ccDarkhastFaktor , codeNoeVosolMarjoee);
            dariaftPardakhtDarkhastFaktorPPCDAO.deleteByccDarkhastFaktorAndCodeNoeVosol(ccDarkhastFaktor , codeNoeVosolMarjoee);

            if (MablaghKolMarjoeeMoshtary != 0)
            {
                //insertKardexAnbarakForoshandeh(ccAfradForoshandeh, ccMoshtary);
                //ccDariaftPardakht = dariaftPardakhtDAO.getMarjoeeByccMoshtary(ccMoshtary , codeNoeVosolMarjoee);

                dariaftPardakhtPPC.setCcMarkazAnbar(foroshandehMamorPakhshModel.getCcMarkazAnbarVosol());
                dariaftPardakhtPPC.setCcMarkazForosh(foroshandehMamorPakhshModel.getCcMarkazForosh());
                dariaftPardakhtPPC.setCcMarkazSazmanForoshSakhtarForosh(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
                dariaftPardakhtPPC.setCodeNoeVosol(Integer.parseInt(codeNoeVosolMarjoee));
                dariaftPardakhtPPC.setNameNoeVosol(mPresenter.getAppContext().getResources().getString(R.string.marjoee));
                dariaftPardakhtPPC.setCcShomarehHesab(0);
                dariaftPardakhtPPC.setSharhShomarehHesab("");
                dariaftPardakhtPPC.setZamaneSabt(new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(new Date()));
                dariaftPardakhtPPC.setNameSahebHesab(moshtaryModel.getNameMoshtary());
                dariaftPardakhtPPC.setCcBankSanad(0);
                dariaftPardakhtPPC.setNameBankSanad("");
                dariaftPardakhtPPC.setNameShobehSanad("");
                dariaftPardakhtPPC.setCodeShobehSanad("");
                dariaftPardakhtPPC.setShomarehHesabSanad("");
                dariaftPardakhtPPC.setCcNoeHesabSanad(0);
                dariaftPardakhtPPC.setNameNoeHesabSanad("");
                dariaftPardakhtPPC.setCodeNoeCheck(0);
                dariaftPardakhtPPC.setNameNoeCheck("");
                dariaftPardakhtPPC.setShomarehSanad("");
                dariaftPardakhtPPC.setTarikhSanadShamsi("");
                dariaftPardakhtPPC.setTarikhSanad(null);
                dariaftPardakhtPPC.setMablagh(MablaghKolMarjoeeMoshtary);
                dariaftPardakhtPPC.setMablaghMandeh(darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor());
                dariaftPardakhtPPC.setCcDariaftPardakhtLink(0);
                dariaftPardakhtPPC.setCcAfradErsalKonandeh(foroshandehMamorPakhshModel.getCcAfrad());
                dariaftPardakhtPPC.setCcDarkhastFaktor(ccDarkhastFaktor);
                dariaftPardakhtPPC.setCcKardex(0);
                dariaftPardakhtPPC.setCodeVazeiat(0);
                dariaftPardakhtPPC.setCcMoshtary(ccMoshtary);
                dariaftPardakhtPPC.setIsCheckMoshtary(0);
                dariaftPardakhtPPC.setIsPishDariaft(0);
                dariaftPardakhtPPC.setTabdil_NaghdBeFish(0);
                dariaftPardakhtPPC.setExtraProp_IsSend(1); //CodeNoeVosol_moshatry==CodeNoeVosol.Marjoee.getValue()?1:0
                dariaftPardakhtPPC.setExtraProp_IsDirkard(0);
                ccDariaftPardakht = dariaftPardakhtDAO.insert(dariaftPardakhtPPC);
                Log.d("marjoee" , "ccDariaftPardakht after insert : " + ccDariaftPardakht);
                Log.d("marjoee" , "MablaghKolMarjoeeMoshtary : " + MablaghKolMarjoeeMoshtary);

                double MablaghDariaftPardakhtDarkhastFaktor = MablaghKolMarjoeeMoshtary < darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor() ? MablaghKolMarjoeeMoshtary : darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor();

                Log.d("mablaghMandeh" , "MablaghDariaftPardakhtDarkhastFaktor : " + MablaghDariaftPardakhtDarkhastFaktor);
                Log.d("mablaghMandeh" , "getExtraProp_MablaghNahaeeFaktor : " + darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor());

                boolean insertResult = insertMarjoeeDariaftPardakhtDarkhastFaktor(foroshandehMamorPakhshModel, darkhastFaktorModel, ccDariaftPardakht,MablaghKolMarjoeeMoshtary,(long) MablaghDariaftPardakhtDarkhastFaktor);
                boolean updateResult = darkhastFaktorDAO.updateMandehDarkhastFaktor(ccDarkhastFaktor);

                //Log.d("mablaghMandeh" , "mandeh in marjoee : " + darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor).getMablaghMandeh());


                if (!insertResult || !updateResult)
                {
                    removeAllMarjoee(ccDarkhastFaktor);
                    mPresenter.onFailedInsertDariaftPardakht();
                }
            }
            SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
            shared.putBoolean(shared.getVerifiedMarjoee() , true);
            mPresenter.onSuccessInsertDariaftPardakht();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MarjoeeKalaModel", "", "insertDariaftPardakht", "");
            removeAllMarjoee(ccDarkhastFaktor);
            mPresenter.onFailedInsertDariaftPardakht();
        }
    }


    private void removeAllMarjoee(long ccDarkhastFaktor)
    {
        ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
        elamMarjoeePPCDAO.deleteAllByccDarkhastFaktor(ccDarkhastFaktor);
        ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
        elamMarjoeeSatrPPCDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
    }


    private boolean insertMarjoeeDariaftPardakhtDarkhastFaktor(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, DarkhastFaktorModel darkhastFaktorModel , long ccDariaftPardakht, long MablaghKolMarjoeeMoshtary, long MablaghDariaftPardakhtDarkhastFaktor)
    {
        //TODO update mablagh marjoee when enter to verifyRequest activity
        try
        {
            Log.d("mablaghMandeh" , "MablaghDariaftPardakhtDarkhastFaktor in insert : " + MablaghDariaftPardakhtDarkhastFaktor);
            String codeNoeVosolMarjoee = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_CODE_NOE_VOSOL_MARJOEE());
            DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
            DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPC = new DariaftPardakhtDarkhastFaktorPPCModel();
            dariaftPardakhtDarkhastFaktorPPC.setCcDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
            dariaftPardakhtDarkhastFaktorPPC.setCcDariaftPardakht(ccDariaftPardakht);
            dariaftPardakhtDarkhastFaktorPPC.setCodeNoeVosol(Integer.parseInt(codeNoeVosolMarjoee));
            dariaftPardakhtDarkhastFaktorPPC.setNameNoeVosol(mPresenter.getAppContext().getResources().getString(R.string.marjoee));
            dariaftPardakhtDarkhastFaktorPPC.setShomarehSanad("0");
            dariaftPardakhtDarkhastFaktorPPC.setTarikhSanad(new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(new Date()));
            dariaftPardakhtDarkhastFaktorPPC.setTarikhSanadShamsi("");
            dariaftPardakhtDarkhastFaktorPPC.setMablaghDariaftPardakht(MablaghKolMarjoeeMoshtary);
            dariaftPardakhtDarkhastFaktorPPC.setMablagh(MablaghDariaftPardakhtDarkhastFaktor);
            dariaftPardakhtDarkhastFaktorPPC.setCodeVazeiat(0);
            dariaftPardakhtDarkhastFaktorPPC.setZamaneTakhsiseFaktor(new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(new Date()));
            dariaftPardakhtDarkhastFaktorPPC.setZamaneTakhsiseFaktorShamsi(new PubFunc().new DateUtils().todayDateWithSlash(mPresenter.getAppContext()));
            dariaftPardakhtDarkhastFaktorPPC.setCcAfradErsalKonandeh(foroshandehMamorPakhshModel.getCcAfrad());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazAnbar(foroshandehMamorPakhshModel.getCcMarkazAnbarVosol());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazForosh(foroshandehMamorPakhshModel.getCcMarkazForosh());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazSazmanForoshSakhtarForosh(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
            dariaftPardakhtDarkhastFaktorPPC.setTabdil_NaghdBeFish(0);
            dariaftPardakhtDarkhastFaktorPPC.setCcTafkikJoze(darkhastFaktorModel.getCcTafkikJoze());
            dariaftPardakhtDarkhastFaktorPPC.setNaghlAzGhabl(0);
            dariaftPardakhtDarkhastFaktorPPC.setIsForTasviehTakhir(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsDirkard(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccKardexSatr(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsBestankari_ForTasviehTakhir(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsSend(1); // CodeNoeVosol_moshtary== CodeNoeVosol.Marjoee.getValue()?1:0
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_CanDelete(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsTajil(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccDarkhastFaktorServer(darkhastFaktorModel.getCcDarkhastFaktor());

            return dariaftPardakhtDarkhastFaktorPPCDAO.insert(dariaftPardakhtDarkhastFaktorPPC) > 0;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MarjoeeKalaModel", "", "insertMarjoeeDariaftPardakhtDarkhastFaktor", "");
            //mPresenter.onErrorCheck(R.string.errorOperation , "");
            return false;
        }
    }
    

    @Override
    public void removeKalaFromMarjoee(KalaElamMarjoeeModel kalaElamMarjoeeModel, int position)
    {
        ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
        if (elamMarjoeeSatrPPCDAO.deleteByccElamMarjoeeSatr(kalaElamMarjoeeModel.getCcElamMarjoeeSatrPPC()))
        {
            int countSatr = elamMarjoeeSatrPPCDAO.getCountByccDarkhastFaktor(kalaElamMarjoeeModel.getCcDarkhastFaktor());
            if (countSatr == 0)
            {
                ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
                elamMarjoeePPCDAO.deleteAllByccDarkhastFaktor(kalaElamMarjoeeModel.getCcDarkhastFaktor());
            }
            mPresenter.onSuccessRemoveItem(position);
        }
        else
        {
            mPresenter.onFailedRemoveItem();
        }
    }

    @Override
    public void updateCountOfMarjoee(long ccDarkhastFaktor, int ccElamMarjoeeSatr, int newCount)
    {
        ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
        int affectedRows = elamMarjoeeSatrPPCDAO.updateCount(ccElamMarjoeeSatr, newCount);
        if (affectedRows > 0)
        {
            getKalaMarjoee(ccDarkhastFaktor);
            mPresenter.onUpdateCount(true);
        }
        else
        {
            mPresenter.onUpdateCount(false);
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
