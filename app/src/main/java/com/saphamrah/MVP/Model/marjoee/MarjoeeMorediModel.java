package com.saphamrah.MVP.Model.marjoee;

import android.content.Context;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.marjoee.MarjoeeMorediMVP;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.ElatMarjoeeKalaDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KardexDAO;
import com.saphamrah.DAO.KardexSatrDAO;
import com.saphamrah.DAO.MarjoeeMamorPakhshDAO;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KardexModel;
import com.saphamrah.Model.KardexSatrModel;
import com.saphamrah.Model.MarjoeeMamorPakhshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.Date;

public class MarjoeeMorediModel implements MarjoeeMorediMVP.ModelOps
{

    private MarjoeeMorediMVP.RequiredPresenterOps mPresenter;
    private int ccMarjoeeMamorPakhsh;
    private int tedadMarjoeeForInsert = 0;
    private int ccKardexSatr = 0;
    private int ccKardex = 0;
    ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
    KardexSatrDAO kardexSatrDAO= new KardexSatrDAO(BaseApplication.getContext());
    KardexDAO kardexDAO= new KardexDAO(BaseApplication.getContext());
    MarjoeeMamorPakhshDAO marjoeeMamorPakhshDAO = new MarjoeeMamorPakhshDAO(BaseApplication.getContext());
    public MarjoeeMorediModel(MarjoeeMorediMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(BaseApplication.getContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }

    @Override
    public void getMarjoeeMoredi(int ccMoshtary) {
        MarjoeeMamorPakhshDAO marjoeeMamorPakhshDAO = new MarjoeeMamorPakhshDAO(BaseApplication.getContext());
        marjoeeMamorPakhshDAO.getByccmoshtary(ccMoshtary, models ->
                mPresenter.onGetMarjoeeMoredi(models));
    }

    @Override
    public void getElatMarjoeeMoredi() {

        ElatMarjoeeKalaDAO elatMarjoeeKalaDAO = new ElatMarjoeeKalaDAO(BaseApplication.getContext());
        ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels = elatMarjoeeKalaDAO.getElatMarjoeePakhsh();
        ArrayList<String> elatAdamDarkhastTitles = new ArrayList<>();
        for (ElatMarjoeeKalaModel model : elatMarjoeeKalaModels)
        {
            elatAdamDarkhastTitles.add(model.getSharh());
        }

        mPresenter.onGetElatMarjoeeMoredi(elatMarjoeeKalaModels , elatAdamDarkhastTitles);

    }

    @Override
    public void searchNameKala(String searchWord, ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsAdpater) {
        ArrayList<MarjoeeMamorPakhshModel> modelsSearch = new ArrayList<>();

        for (int i=0 ; i<marjoeeMamorPakhshModelsAdpater.size() ; i++)
        {
            MarjoeeMamorPakhshModel model = marjoeeMamorPakhshModelsAdpater.get(i);
            if (model.getNameKala().contains(searchWord))
            {
                modelsSearch.add(model);
            }
        }

        mPresenter.onGetSearch(modelsSearch);
    }

    @Override
    public void checkTaeidSabtMarjoee(MarjoeeMamorPakhshModel model, int ccMarjoeeMamorPakhsh, int itemCount, int selectedCount, int position , ArrayList<ElatMarjoeeKalaModel> elatMarjoee) {
        this.ccMarjoeeMamorPakhsh = ccMarjoeeMamorPakhsh;
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();
        tedadMarjoeeForInsert = selectedCount;
        insertKardexMoredi( model, foroshandehMamorPakhshModel.getCcMarkazAnbar(), foroshandehMamorPakhshModel.getCcMarkazForosh(), foroshandehMamorPakhshModel.getCcForoshandeh());
        ccKardex =kardexDAO.findccKardexByccMoshtaryAndccMarjoeeMamorPakhsh(model.getCcMoshtary() , ccMarjoeeMamorPakhsh);
        if(ccKardex > 0) {
            insertKardexSatrMoredi(model, ccKardex , elatMarjoee);
        }
        insertUpdateDariaftPardakht(model.getCcMoshtary(), model.getNameMoshtary(), (tedadMarjoeeForInsert * model.getMablaghForoshKhales()) );

        boolean updateTedadMarjoee = marjoeeMamorPakhshDAO.updateTedadMarjoee(model.getCcMarjoeeMamorPakhsh() ,
                model.getShomarehBach(),
                model.getCcTaminKonandeh(),
                model.getMablaghForosh(),
                model.getMablaghMasrafKonandeh(),
                selectedCount);

        if (updateTedadMarjoee){
            mPresenter.onTaeidSabtMarjoee(selectedCount , position);
        }
    }




    /**
     *  insert kardex moredi
     * @param entity
     * @param ccMarkazAnbar
     * @param ccMarkazForosh
     * @param ccForoshandeh
     * @return
     */
    private long insertKardexMoredi(MarjoeeMamorPakhshModel entity, int ccMarkazAnbar, int ccMarkazForosh, int ccForoshandeh)
    {
        KardexModel kardex= new KardexModel();
        try
        {
            kardex = kardexDAO.SetForInsert_Kardex(ccMarkazAnbar,
                    ccMarkazForosh,
                    // TODO :: check entry
                    0,
                    entity.getCcMoshtary(),
                    0,
                    ccForoshandeh,
                   entity.getCcMarjoeeMamorPakhsh(),
                    0
            );

            ccKardex =kardexDAO.findccKardexByccMoshtaryAndccMarjoeeMamorPakhsh(entity.getCcMoshtary() , entity.getCcMarjoeeMamorPakhsh());
            if(ccKardex == 0)
                ccKardex= kardexDAO.insert(kardex);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return ccKardex;
    }

    /**
     * inser kardex satr moredi
     * @param entity
     * @param ccKardex
     */
    private void insertKardexSatrMoredi(MarjoeeMamorPakhshModel entity, long ccKardex,ArrayList<ElatMarjoeeKalaModel> elatMarjoee)
    {
        KardexSatrModel kardexSatr= new KardexSatrModel();
        int ccElatMarjoee = 0;
        if (elatMarjoee !=null)
            if (elatMarjoee.size() > 0)
                ccElatMarjoee = elatMarjoee.get(0).getCcElatMarjoeeKala();
        int mamorPakhash = foroshandehMamorPakhshDAO.getIsSelect().getCcMamorPakhsh();
        try
        {
            kardexSatr = kardexSatrDAO.setForInsert_KardexSatr(ccKardex, entity.getCcTaminKonandeh(),
                    // TODO :: check entry
                    entity.getCcKala(),
                    entity.getCcKalaCode(),
                    entity.getShomarehBach(),
                    entity.getTarikhTolid(),
                    entity.getTarikhEngheza(),
                    tedadMarjoeeForInsert,
                    entity.getMablaghKharid(),
                    entity.getMablaghForosh(),
                    entity.getMablaghForoshKhales(),
                    entity.getMablaghMasrafKonandeh(),
                    entity.getMablaghForosh(),
                    ccElatMarjoee,
                    "",
                    1,
                    entity.getCodeKalaOld(),
                    entity.getNameKala(),
                    0,
                    entity.getCcMarjoeeMamorPakhsh(),
                    entity.getCcMoshtary(),
                    entity.getTarikhTolidShamsi(),
                    entity.getCcAnbarGhesmat());

            ccKardexSatr=kardexSatrDAO.getCcKardexSatrForUpdateTedadMarjoeeForoshandeh(entity.getCcMarjoeeMamorPakhsh());
            if(ccKardexSatr== 0)
                ccKardexSatr = kardexSatrDAO.insert(kardexSatr);
            else
                kardexSatrDAO.updateByccKardexSatr(ccKardexSatr, tedadMarjoeeForInsert);

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * insert update daryaft moredi
     * @param ccMoshtary
     * @param NameMoshtary
     * @param Mablagh
     * @return
     */
    private long insertUpdateDariaftPardakht(int ccMoshtary, String NameMoshtary, double Mablagh)
    {
        DariaftPardakhtPPCModel dariaftPardakhtPPC= new DariaftPardakhtPPCModel();
        DariaftPardakhtPPCDAO dariaftPardakhtDAO= new DariaftPardakhtPPCDAO(BaseApplication.getContext());
        long ccDariaftPardakht;
        try
        {
            int CodeNoeVosol  = 0;
            CodeNoeVosol = Integer.valueOf(Constants.VALUE_MARJOEE());
            dariaftPardakhtPPC = dariaftPardakhtDAO.SetForInsert_DariaftPardakhtPPC( 0,
                    Constants.VALUE_MARJOEE(),
                    "مرجوعی",
                    0,
                    "",
                    NameMoshtary,
                    0,
                    "",
                    "",
                    "",
                    "" ,
                    0,
                    "",
                    "",
                    Mablagh,
                    0,
                    ccMoshtary,
                    0,
                    ccKardex,
                    0,
                    null,
                    0,
                    CodeNoeVosol);
            ccDariaftPardakht = dariaftPardakhtDAO.getMarjoeeByccMoshtary(ccMoshtary ,Constants.VALUE_MARJOEE());
            if(ccDariaftPardakht ==0)
                ccDariaftPardakht = dariaftPardakhtDAO.insert(dariaftPardakhtPPC);
            else
                dariaftPardakhtDAO.updateMablaghMarjoee(ccDariaftPardakht,Mablagh);

            insertDaryaftPardakhtDarkhastFaktor(BaseApplication.getContext(), ccDariaftPardakht);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return ccDariaftPardakht;
    }
    private void insertDaryaftPardakhtDarkhastFaktor (Context context, long ccDariaftPardakht)
    {
        try
        {
            //---------------- DeleteByccKardexSatrFrom DPDF -------
            DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO= new DariaftPardakhtDarkhastFaktorPPCDAO(context);
            dariaftPardakhtDarkhastFaktorPPCDAO.deleteByccKardexSatr(ccKardexSatr);

            //---------------- GetByccKardexSatr -------
            KardexSatrDAO kardexSatrDAO = new KardexSatrDAO(context);
            KardexSatrModel kardexSatr = kardexSatrDAO.getByccKardexSatr(ccKardexSatr);

            //--------------- Update_MablaghMandehFaktor ----------
            DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPC= new DariaftPardakhtDarkhastFaktorPPCModel();
//            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(context);
//            darkhastFaktorDAO.updateMandehDarkhastFaktor(ccMarjoeeMamorPakhsh);
//            double MablaghMandehFaktor = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor).getMablaghMandeh();
//
            double MablaghMarjoee= kardexSatr.getGheymatForoshKhales() * kardexSatr.getTedad3();
            double MablaghDariaftPardakhtDarkhastFaktor = MablaghMarjoee< 0?MablaghMarjoee:0;
            //--------------------------------------
            dariaftPardakhtDarkhastFaktorPPC= dariaftPardakhtDarkhastFaktorPPCDAO.SetForInsert_DariaftPardakhtDarkhastFaktorPPC(
                    0, ccDariaftPardakht, Constants.VALUE_MARJOEE(), "مرجوعی",
                    "0", new Date(), "", MablaghMarjoee, MablaghDariaftPardakhtDarkhastFaktor, 0, 0, 0, ccKardexSatr, 0,0,0,0);
            dariaftPardakhtDarkhastFaktorPPCDAO.insert(dariaftPardakhtDarkhastFaktorPPC);


        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     *  delete marjoee
     * @param ccMarjoeeMamorPakhsh
     */
    @Override
    public void deleteMarjoee(int ccMarjoeeMamorPakhsh , int ccMoshtary) {
        KardexSatrDAO kardexSatrDAO= new KardexSatrDAO(BaseApplication.getContext());
        kardexSatrDAO.deleteByccKardex(ccKardex);

        KardexDAO kardexDAO= new KardexDAO(BaseApplication.getContext());
        kardexDAO.deleteByccMarjoeeMamorPakhsh(ccMarjoeeMamorPakhsh);


        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO= new DariaftPardakhtPPCDAO(BaseApplication.getContext());
        int ccDariaftPardakht =  dariaftPardakhtPPCDAO.getByccMoshtary(ccMoshtary).get(0).getCcDariaftPardakht();
        dariaftPardakhtPPCDAO.deleteMarjoeeForoshandehByccMoshtary(ccMoshtary);


        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO= new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
        dariaftPardakhtDarkhastFaktorPPCDAO.deleteMarjoeeForoshandehByccDariaftPardakht(ccDariaftPardakht);

//       DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext()) ;
//       darkhastFaktorDAO.updateMarjoee(ccDarkhastFaktor, 0);

    }


}
