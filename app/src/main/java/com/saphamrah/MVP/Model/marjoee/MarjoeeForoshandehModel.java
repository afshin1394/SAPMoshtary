 package com.saphamrah.MVP.Model.marjoee;

import android.content.Context;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.marjoee.MarjoeeForoshandehMVP;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ElamMarjoeeForoshandehDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KardexDAO;
import com.saphamrah.DAO.KardexSatrDAO;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KardexModel;
import com.saphamrah.Model.KardexSatrModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.Date;

 public class MarjoeeForoshandehModel implements MarjoeeForoshandehMVP.ModelOps
{
    private MarjoeeForoshandehMVP.RequiredPresenterOps mPresenter;
    private long ccDarkhastFaktor;
    private int ccKardex;
    private int tedadMarjoeeForInsert = 0;
    private int ccKardexSatr = 0;
    KardexSatrDAO kardexSatrDAO= new KardexSatrDAO(BaseApplication.getContext());
    KardexDAO kardexDAO= new KardexDAO(BaseApplication.getContext());
    ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
    ElamMarjoeeForoshandehDAO elamMarjoeeForoshandehDAO =new ElamMarjoeeForoshandehDAO(BaseApplication.getContext());
    DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO= new DariaftPardakhtPPCDAO(BaseApplication.getContext());
    DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO= new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext()) ;

    public MarjoeeForoshandehModel(MarjoeeForoshandehMVP.RequiredPresenterOps mPresenter)
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
    public void getMarjoee(long ccDarkhastFaktor) {
        ElamMarjoeeForoshandehDAO elamMarjoeeForoshandehDAO = new ElamMarjoeeForoshandehDAO(BaseApplication.getContext());
        ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels = elamMarjoeeForoshandehDAO.getMarjoeeByCcDarkhastFaktor(ccDarkhastFaktor);
        mPresenter.onGetMarjoee(elamMarjoeeForoshandehModels);
    }

    @Override
    public void getForoshandehMamorPakhshInfo()
    {

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

    /**
     * sabt marjoee foroshandeh
     */
    @Override
    public void checkTaeidSabtMarjoee(ElamMarjoeeForoshandehModel entity  , long ccDarkhastFaktor, int itemCount, int selectedCount, int position,boolean addMarjoee) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        tedadMarjoeeForInsert = selectedCount;
        insertKardexForoshandeh( entity, foroshandehMamorPakhshModel.getCcMarkazAnbar(), ccDarkhastFaktor);
        ccKardex =kardexDAO.findccKardexByccRefrence(Long.parseLong(entity.getCcDarkhastFaktor()));
        if(ccKardex > 0) {
              ccKardexSatr = insertKardexSatrForoshandeh(entity, ccKardex);
           }
        int tedad;

        if (!addMarjoee){
            tedad = entity.getTedad3();
        }
        else
        {
            tedad = tedadMarjoeeForInsert;
        }
        insertUpdateDariaftPardakht(entity, (tedad * entity.getGheymatForosh()));

       boolean updateTedadMarjoee = elamMarjoeeForoshandehDAO.updateTedadMarjoee(entity.getCcDarkhastFaktor() ,
               entity.getShomarehBach(),
               entity.getCcTaminKonandeh(),
               entity.getGheymatForosh(),
               entity.getGheymatMasrafKonandeh(),
               selectedCount);

       if (updateTedadMarjoee && !addMarjoee){
          deleteMarjoee(ccKardexSatr,ccDarkhastFaktor,entity.getCcKalaCode(),selectedCount,position);
       }else{
          mPresenter.onTaeidSabtMarjoee(ccKardexSatr,selectedCount , position);
       }
    }

    public void deleteMarjoee(int ccKardexSatr ,long ccDarkhastFaktor ,int ccKalaCode,int selectedCount,int position) {

        kardexSatrDAO.deleteByccKardexAndccKalaCode(ccKardex,ccKalaCode);

        int countKardexSatr = kardexSatrDAO.getByCcKardex(ccKardex).size();

        if (countKardexSatr == 0) {
            kardexDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
            dariaftPardakhtPPCDAO.deleteMarjoeeForoshandehByccDarkhastFaktor(ccDarkhastFaktor, Constants.VALUE_MARJOEE());
        }

        dariaftPardakhtDarkhastFaktorPPCDAO.deleteMarjoeeForoshandehByccDarkhastFaktor(ccKardexSatr,ccDarkhastFaktor,Constants.VALUE_MARJOEE());

        dariaftPardakhtPPCDAO.deleteDirKardAndTajil(ccDarkhastFaktor,0);
        dariaftPardakhtDarkhastFaktorPPCDAO.deleteDirKardAndTajil(ccDarkhastFaktor,0);


        darkhastFaktorDAO.updateMarjoee(ccDarkhastFaktor, 0);

        mPresenter.onTaeidSabtMarjoee(ccKardexSatr,selectedCount , position);



    }


    /**
     *  insert kardex foroshandeh
     * @param entity
     * @param ccMarkazAnbar

     * @return
     */
   private long insertKardexForoshandeh(ElamMarjoeeForoshandehModel entity, int ccMarkazAnbar, long ccRefrence)
   {
       ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
       ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
       KardexModel kardex= new KardexModel();
       DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
       DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccRefrence);

       try
       {
           kardex = kardexDAO.SetForInsert_Kardex(ccMarkazAnbar,
                   darkhastFaktorModel.getCcMarkazForosh(),
                   darkhastFaktorModel.getCcAnbar(),
                   entity.getCcMoshtary(),
                   0,
                   darkhastFaktorModel.getCcForoshandeh(),
                   ccRefrence,
                   0,
                   foroshandehMamorPakhshModel.getCcAfrad(),
                   foroshandehMamorPakhshModel.getCodeNoeAnbarMarjoee()

                   );

           ccKardex =kardexDAO.findccKardexByccRefrence(Long.parseLong(entity.getCcDarkhastFaktor()));
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
    * inser kardex satr foroshandeh
    * @param entity
    * @param ccKardex
    */
   private int insertKardexSatrForoshandeh(ElamMarjoeeForoshandehModel entity, long ccKardex)
   {
       KardexSatrModel kardexSatr= new KardexSatrModel();
       int mamorPakhash = foroshandehMamorPakhshDAO.getIsSelect().getCcMamorPakhsh();
       try
       {
           kardexSatr = kardexSatrDAO.setForInsert_KardexSatr(ccKardex, entity.getCcTaminKonandeh(),
                   entity.getCcKala(),
                   entity.getCcKalaCode(),
                   entity.getShomarehBach(),
                   entity.getTarikhTolid(),
                   entity.getTarikhEngheza(),
                   tedadMarjoeeForInsert,
                   entity.getGheymatKharid(),
                   entity.getGheymatForosh(),
                   entity.getGheymatForoshKhales(),
                   entity.getGheymatMasrafKonandeh(),
                   entity.getGheymatForoshAsli(),
                   entity.getCcElatMarjoeeKala(),
                   entity.getNameElatMarjoeeKala(),
                   1,
                   entity.getCodeKala(),
                   entity.getNameKala(),
                   entity.getCcElamMarjoeeSatr(),
                   mamorPakhash,
                   entity.getCcMoshtary(),
                   entity.getTarikhTolidShamsi(),
                   entity.getCcAnbarGhesmat());

           ccKardexSatr=kardexSatrDAO.getCcKardexSatrForUpdateTedadMarjoeeForoshandeh(entity.getCcElamMarjoeeSatr());
           if(ccKardexSatr== 0)
               ccKardexSatr = kardexSatrDAO.insert(kardexSatr);
           else
               kardexSatrDAO.updateByccKardexSatr(ccKardexSatr, tedadMarjoeeForInsert);

       }
       catch (Exception e)
       {
           throw new RuntimeException(e);
       }
       return ccKardexSatr;
   }

   /**
    * insert update daryaft pardakht

    * @param Mablagh
    * @return
    */
   private long insertUpdateDariaftPardakht(ElamMarjoeeForoshandehModel entity, double Mablagh)
   {
       int ccMoshtary = entity.getCcMoshtary();
       String nameMoshtary = entity.getNameMoshtary();
       String ccDarkhastFaktor = entity.getCcDarkhastFaktor();

       DariaftPardakhtPPCModel dariaftPardakhtPPC= new DariaftPardakhtPPCModel();
       DariaftPardakhtPPCDAO dariaftPardakhtDAO= new DariaftPardakhtPPCDAO(BaseApplication.getContext());
       long ccDariaftPardakht;
       try
       {
           //cccKardex ccDarkhastFaktor
           int CodeNoeVosol  = 0;
           CodeNoeVosol = Integer.parseInt(Constants.VALUE_MARJOEE());
           dariaftPardakhtPPC = dariaftPardakhtDAO.SetForInsert_DariaftPardakhtPPC( 0,
                   Constants.VALUE_MARJOEE(),
                   "مرجوعی",
                   0,
                   "",
                   nameMoshtary,
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
                   Long.parseLong(ccDarkhastFaktor),
                   0,
                   null,
                   0,
                   CodeNoeVosol);


           //ccKardex
           ccDariaftPardakht = dariaftPardakhtDAO.getMarjoeeByccMoshtary(ccMoshtary ,Constants.VALUE_MARJOEE());
           if(ccDariaftPardakht ==0)
               ccDariaftPardakht = dariaftPardakhtDAO.insert(dariaftPardakhtPPC);
           else {
               DariaftPardakhtPPCModel dariaftPardakhtPPCModel=dariaftPardakhtDAO.getByccDariaftPardakht(ccDariaftPardakht);
               double finalMablagh = dariaftPardakhtPPCModel.getMablagh() - Mablagh;
               dariaftPardakhtDAO.updateMablaghMarjoee(ccDariaftPardakht, finalMablagh);

           }

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
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(context);
            darkhastFaktorDAO.updateMandehDarkhastFaktor(ccDarkhastFaktor);
            double MablaghMandehFaktor = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor).getMablaghMandeh();

            double MablaghMarjoee= kardexSatr.getGheymatForosh() * kardexSatr.getTedad3();
            double MablaghDariaftPardakhtDarkhastFaktor = MablaghMarjoee < MablaghMandehFaktor ? MablaghMarjoee : MablaghMandehFaktor;
            //--------------------------------------
            dariaftPardakhtDarkhastFaktorPPC= dariaftPardakhtDarkhastFaktorPPCDAO.SetForInsert_DariaftPardakhtDarkhastFaktorPPC(
                    ccDarkhastFaktor, ccDariaftPardakht, Constants.VALUE_MARJOEE(), "مرجوعی",
                    "0", new Date(), "", MablaghMarjoee, MablaghDariaftPardakhtDarkhastFaktor, 0, 0, 0, ccKardexSatr, 0,0,0,0);
            dariaftPardakhtDarkhastFaktorPPCDAO.insert(dariaftPardakhtDarkhastFaktorPPC);


        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }




}
