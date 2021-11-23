package com.saphamrah.MVP.Model.marjoee;

import android.content.Context;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.marjoee.MarjoeekoliMVP;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.ElatMarjoeeKalaDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaDarkhastFaktorSatrDAO;
import com.saphamrah.DAO.KardexDAO;
import com.saphamrah.DAO.KardexSatrDAO;
import com.saphamrah.DAO.MarjoeeKamelImageDAO;
import com.saphamrah.DAO.MarjoeeKamelImageTmpDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KardexModel;
import com.saphamrah.Model.KardexSatrModel;
import com.saphamrah.Model.MarjoeeKamelImageModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MarjoeeKoliModel implements MarjoeekoliMVP.ModelOps
{

    private MarjoeekoliMVP.RequiredPresenterOps mPresenter;
    private long ccDarkhastFaktor;
    private int CcMoshtary;
    private int ccKardex;
    private DarkhastFaktorModel darkhastFaktorModel;
    private DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
    public MarjoeeKoliModel(MarjoeekoliMVP.RequiredPresenterOps mPresenter)
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
        KalaDarkhastFaktorSatrDAO kalaDarkhastFaktorSatrDAO = new KalaDarkhastFaktorSatrDAO(BaseApplication.getContext());
        ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels = kalaDarkhastFaktorSatrDAO.getByccDarkhastMarjoeeKoli(ccDarkhastFaktor);
          mPresenter.onGetMarjoee(kalaDarkhastFaktorSatrModels);
    }

    @Override
    public void getElatMarjoeeKol() {

        ElatMarjoeeKalaDAO elatMarjoeeKalaDAO = new ElatMarjoeeKalaDAO(BaseApplication.getContext());
        ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels = elatMarjoeeKalaDAO.getElatMarjoeePakhsh();
        ArrayList<String> elatAdamDarkhastTitles = new ArrayList<>();
        for (ElatMarjoeeKalaModel model : elatMarjoeeKalaModels)
        {
            elatAdamDarkhastTitles.add(model.getSharh());
        }

        mPresenter.onGetElatMarjoeeKol(elatMarjoeeKalaModels , elatAdamDarkhastTitles);

    }

    /**
     * Check Taeid Sabt Marjoee in DB
     * @param elatMarjoeeKalaModels
     * @param position
     * @param ccDarkhastFaktor
     */
    @Override
    public void checkTaeidSabtMarjoee(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels, int position , int ccDarkhastFaktor) {

        this.ccDarkhastFaktor = (long) ccDarkhastFaktor;
        if (IsValidData_MarjoeeKol(ccDarkhastFaktor)){
            ccKardex =  Insert_Kardex(ccDarkhastFaktor , elatMarjoeeKalaModels.get(position).getCcElatMarjoeeKala());
            if (ccKardex > 0)
            insert_KardexSatr(ccKardex , elatMarjoeeKalaModels.get(position).getCcElatMarjoeeKala() , elatMarjoeeKalaModels.get(position).getSharh());
            Insert_DariaftPardakht();

            /**
             * if GetImage()==1 we need take photo
             * if isVosol = true we can use sabt Marjoee else we can not and show Toast
             */
            if (elatMarjoeeKalaModels.get(position).getGetImage()==1){
                mPresenter.onCheckTaeidSabtMarjoee(true , true);
            } else {
                darkhastFaktorDAO.updateExtraPropIsMarjoeeKamelDarkhastFaktor(ccDarkhastFaktor , 1);
                mPresenter.onCheckTaeidSabtMarjoee(false , true);
            }
        } else {
            mPresenter.onCheckTaeidSabtMarjoee(false , false);
        }
    }

    /**
     * insert Image Marjoee in DB
     * @param imageMarjoee
     */
    @Override
    public void insertMarjoee(byte[] imageMarjoee) {
        Insert_ImageMarjoeeKamel(ccKardex , CcMoshtary , imageMarjoee);
    }


    /**
     *     check Is ValidData MarjoeeKol
      */
    private boolean IsValidData_MarjoeeKol(int ccDarkhastFaktor)
    {
        boolean isValid = true;

        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO= new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
        if(dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(ccDarkhastFaktor).size() > 0)
            isValid = false;

        return isValid;
    }


    private int Insert_Kardex(long ccDarkhastFaktor , int ExtraProp_ccElatMarjoeeKala)
    {
        int ccKardex;
        try
        {
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            KardexDAO kardexDAO= new KardexDAO(BaseApplication.getContext());
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
            darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            ArrayList<KardexModel> kardexModels = new ArrayList<>();
            KardexModel kardexModel = new KardexModel();
            CcMoshtary = darkhastFaktorModel.getCcMoshtary();
            kardexModel = kardexDAO.SetForInsert_Kardex(darkhastFaktorModel.getCcMarkazAnbar() ,
                    darkhastFaktorModel.getCcMarkazForosh(),
                    darkhastFaktorModel.getCcAnbar(),
                    //foroshandehMamorPakhshModel.getCcAnbarMarjoee() ,
                    darkhastFaktorModel.getCcMoshtary() ,
                    1 ,
                    darkhastFaktorModel.getCcForoshandeh() ,
                    ccDarkhastFaktor,
                    ExtraProp_ccElatMarjoeeKala,
                    foroshandehMamorPakhshDAO.getCCAfrad(),
                    foroshandehMamorPakhshDAO.getIsSelect().getCodeNoeAnbarMarjoee()

            );

            kardexModels.add(kardexModel);
            //-------------------------------------------------------------------------------------------------------
            ccKardex = kardexDAO.findccKardexByccRefrence(darkhastFaktorModel.getCcDarkhastFaktor());
            if(ccKardex == 0)
                kardexDAO.insertGroup(kardexModels);
            ccKardex = kardexDAO.findccKardexByccRefrence(darkhastFaktorModel.getCcDarkhastFaktor());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return ccKardex;
    }

    private void insert_KardexSatr(long ccKardex ,  int ccElatMarjoee , String NameElatMarjoee)
    {
        KardexSatrDAO kardexSatrDAO= new KardexSatrDAO(BaseApplication.getContext());
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(BaseApplication.getContext());
        ArrayList<DarkhastFaktorSatrModel> items_MarjoeeMamorPakhsh_Kol = darkhastFaktorSatrDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        KalaDAO kalaDAO = new KalaDAO(BaseApplication.getContext());

        try
        {
            ArrayList<KardexSatrModel> kardexSatrModels = new ArrayList<>();
            for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : items_MarjoeeMamorPakhsh_Kol)
            {
                KalaModel kalaModel = kalaDAO.getByccKalaCode(darkhastFaktorSatrModel.getCcKalaCode());
                KardexSatrModel model = new KardexSatrModel();
                model = kardexSatrDAO.setForInsert_KardexSatr(ccKardex,
                        darkhastFaktorSatrModel.getCcTaminKonandeh(),
                        darkhastFaktorSatrModel.getCcKala(),
                        darkhastFaktorSatrModel.getCcKalaCode(),
                        darkhastFaktorSatrModel.getShomarehBach(),
                        darkhastFaktorSatrModel.getTarikhTolid(),
                        darkhastFaktorSatrModel.getTarikhEngheza(),
                        darkhastFaktorSatrModel.getTedad3(),
                        darkhastFaktorSatrModel.getMablaghKharid(),
                        (float) darkhastFaktorSatrModel.getMablaghForosh(),
                        darkhastFaktorSatrModel.getMablaghForoshKhalesKala(),
                        darkhastFaktorSatrModel.getGheymatMasrafKonandeh(),
                        darkhastFaktorSatrModel.getGheymatForoshAsli(),
                        ccElatMarjoee,
                        NameElatMarjoee,
                        darkhastFaktorSatrModel.getCodeNoeKala(),
                        kalaModel.getCodeKala(),
                        kalaModel.getNameKala(),
                        0,
                        0,
                        CcMoshtary,
                        darkhastFaktorSatrModel.getTarikhTolid(),
                        darkhastFaktorSatrModel.getCcAnbarGhesmat());

                kardexSatrModels.add(model);

            }

            kardexSatrDAO.insertGroup(kardexSatrModels);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private long Insert_DariaftPardakht()
    {
        long ccDariaftPardakht;

        DariaftPardakhtPPCDAO dariaftPardakhtDAO= new DariaftPardakhtPPCDAO(BaseApplication.getContext());
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
        try
        {
            int CodeNoeVosol  = 0;
            CodeNoeVosol = Integer.valueOf(Constants.VALUE_MARJOEE());
            DariaftPardakhtPPCModel dariaftPardakhtPPC = dariaftPardakhtDAO.SetForInsert_DariaftPardakhtPPC(
                    0,
                    Constants.VALUE_MARJOEE(),
                    "مرجوعی",
                    0,
                    "",
                    moshtaryModel.getNameMoshtary(),
                    0,
                    "",
                    "",
                    "",
                    "" ,
                    0,
                    "",
                    "",
                    darkhastFaktorModel.getMablaghKhalesFaktor(),
                    0,
                    darkhastFaktorModel.getCcMoshtary(),
                    0,
                    ccDarkhastFaktor,
                    0,
                    null,
                    0,
                    CodeNoeVosol);

            ccDariaftPardakht = dariaftPardakhtDAO.getMarjoeeByccMoshtary(darkhastFaktorModel.getCcMoshtary(), Constants.VALUE_MARJOEE());
            if(ccDariaftPardakht ==0)
                ccDariaftPardakht = dariaftPardakhtDAO.insert(dariaftPardakhtPPC);


            InsertDariaftPardakhtDarkhastFaktor(BaseApplication.getContext(), ccDariaftPardakht);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return ccDariaftPardakht;
    }

    private void InsertDariaftPardakhtDarkhastFaktor(Context context, long ccDariaftPardakht)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_WITH_SPACE());
            Date tarikhRooz = sdf.parse(sdf.format(new Date()));
            DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO= new DariaftPardakhtDarkhastFaktorPPCDAO(context);
            DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPC= dariaftPardakhtDarkhastFaktorPPCDAO.SetForInsert_DariaftPardakhtDarkhastFaktorPPC(
                    ccDarkhastFaktor,
                    ccDariaftPardakht,
                    Constants.VALUE_MARJOEE(),
                    "مرجوعی",
                    "0",
                    tarikhRooz,
                    "",
                    darkhastFaktorModel.getMablaghKhalesFaktor(),
                    darkhastFaktorModel.getMablaghMandeh(),
                    0,
                    0,
                    darkhastFaktorModel.getCcTafkikJoze(),
                    0,
                    0,
                    0,
                    0,
                    0);

            dariaftPardakhtDarkhastFaktorPPCDAO.insert(dariaftPardakhtDarkhastFaktorPPC);


            //TODO ::
//            SetMablaghMandehFaktor(context, ccDarkhastFaktor);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * insert Image Marjoee in DB
     * @param ccKardex
     * @param ccMoshtary
     * @param imageMarjoee
     * set IsMarjoeeKol when after take photo
     */
    private void Insert_ImageMarjoeeKamel(long ccKardex, int ccMoshtary,byte[] imageMarjoee)
    {
        MarjoeeKamelImageTmpDAO marjoeeKamelImage_TmpDAO = new MarjoeeKamelImageTmpDAO(BaseApplication.getContext());

        MarjoeeKamelImageDAO marjoeeKamel_ImageDAO = new MarjoeeKamelImageDAO(BaseApplication.getContext());

        ArrayList<MarjoeeKamelImageModel> marjoeeKamelImageTmpModels = new ArrayList<>();
        MarjoeeKamelImageModel entity_Marjoeee = new MarjoeeKamelImageModel();
        entity_Marjoeee.setCcKardex((int) ccKardex);
        entity_Marjoeee.setCcMoshtary(ccMoshtary);
        entity_Marjoeee.setImage(imageMarjoee);
        marjoeeKamelImageTmpModels.add(entity_Marjoeee);
        marjoeeKamel_ImageDAO.insertGroup(marjoeeKamelImageTmpModels);

        marjoeeKamelImage_TmpDAO.deleteAll();

        /*
        * IsMarjoeekol == 1 ( it is marjoee)
        * IsMarjoeekol == 0 ( it is not marjoee)
         */
        darkhastFaktorDAO.updateExtraPropIsMarjoeeKamelDarkhastFaktor(ccDarkhastFaktor , 1);

    }


//    @Override
//    public void deleteMarjoee(String ccDarkhastFaktor) {
//        KardexSatrDAO kardexSatrDAO= new KardexSatrDAO(BaseApplication.getContext());
//        kardexSatrDAO.deleteByccKardex(ccKardex);
//
//        KardexDAO kardexDAO= new KardexDAO(BaseApplication.getContext());
//        kardexDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
//
//        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO= new DariaftPardakhtPPCDAO(BaseApplication.getContext());
//        dariaftPardakhtPPCDAO.deleteMarjoeeForoshandehByccDarkhastFaktor(ccDarkhastFaktor);
//
//        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO= new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
//        dariaftPardakhtDarkhastFaktorPPCDAO.deleteMarjoeeForoshandehByccDarkhastFaktor(ccDarkhastFaktor);
//
//        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext()) ;
//        darkhastFaktorDAO.updateMarjoee(ccDarkhastFaktor, 0);
//
//    }
}
