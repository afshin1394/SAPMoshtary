package com.saphamrah.PubFunc.Discounts.TakhfifSenfi;

import android.content.Context;
import android.util.Log;

import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.TakhfifSenfiSatrDAO;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.DataTableModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.TakhfifSenfiSatrModel;
import com.saphamrah.Model.TakhfifSenfiTitrSatrModel;
import com.saphamrah.PubFunc.Discounts.DiscountCalculation;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CalculateSenfiDiscountTaminKonandeh extends DiscountCalculation
{

    private Context context;


    public CalculateSenfiDiscountTaminKonandeh(Context context, ArrayList<ParameterChildModel> parameterChildModels)
    {
        super(context);
        this.context = context;
    }

    public boolean calculateDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel)
    {
        try
        {
            String codeTakhfif = "";
            int ccTaminKonandeh;
            long sumTedadTaminKonandeh; // Sum Tedad3 Darkhast Bar Hasbe TaminKonandeh..
            long sumTedadBastehTaminKonandeh;
            long sumTedadKartonTaminKonandeh;
            long sumMablaghKolTaminKonandeh;// Sum MablaghKol Darkhast Bar Hasbe TaminKonandeh..
            long sumVaznTaminKonandeh;
            int tedadAghlam;

            int ccTaminKonandehMohasebeh;
            long sumTedadTaminKonandehMohasebeh; // Sum Tedad3 Darkhast Bar Hasbe TaminKonandeh Mohasebeh..
            long sumTedadBastehTaminKonandehMohasebeh;
            long sumTedadKartonTaminKonandehMohasebeh;
            long sumMablaghKolTaminKonandehMohasebeh; // Sum MablaghKol Darkhast Bar Hasbe TaminKonandeh Mohasebeh..
            long Mablagh=0;
            long MablaghKol=0;
            long mablaghVahed = 0;

            codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());

            TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            ArrayList<DataTableModel> taminKonandehs = darkhastFaktorSatrDAO.getTedadBeTafkikTaminKonandeh(darkhastFaktorModel.getCcDarkhastFaktor(),takhfifSenfiTitrSatrModel.getOlaviat(), takhfifSenfiTitrSatrModel.getNoeGheymat());

            for (DataTableModel taminKonandeh : taminKonandehs)
            {
                ccTaminKonandeh = Integer.valueOf(taminKonandeh.getFiled1());
                sumTedadTaminKonandeh = Long.valueOf(taminKonandeh.getFiled2());
                sumTedadBastehTaminKonandeh = Long.valueOf(taminKonandeh.getFiled3());
                sumTedadKartonTaminKonandeh = Long.valueOf(taminKonandeh.getFiled4());
                sumMablaghKolTaminKonandeh = Math.round(Double.valueOf(taminKonandeh.getFiled5()));
                sumVaznTaminKonandeh = Math.round(Double.valueOf(taminKonandeh.getFiled6()));
                tedadAghlam = Integer.valueOf(taminKonandeh.getFiled7());

                mablaghVahed = Math.round(sumMablaghKolTaminKonandeh / sumTedadTaminKonandeh);

                ArrayList<DataTableModel> brandByccTaminKonandeh = darkhastFaktorSatrDAO.getBrandByccTaminKonandeh(darkhastFaktorModel.getCcDarkhastFaktor(), ccTaminKonandeh, takhfifSenfiTitrSatrModel.getOlaviat(), takhfifSenfiTitrSatrModel.getNoeGheymat());
                Log.d("takhfifTaminKonandeh", "brandByccTaminKonandeh : " + brandByccTaminKonandeh.toString());
                Log.d("takhfifTaminKonandeh", "sumMablaghKolTaminKonandeh : " + sumMablaghKolTaminKonandeh);
                Log.d("takhfifTaminKonandeh", "takhfifSenfiTitrSatrModel.getOlaviat() : " + takhfifSenfiTitrSatrModel.getOlaviat());

                ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                        new int[]{getTedadRialTedad(), getTedadRialRial(), getTedadRialVazn(), getTedadRialAghlam()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                        NAME_NOE_FIELD_TAMIN_KONANDE, ccTaminKonandeh, sumTedadTaminKonandeh, sumTedadBastehTaminKonandeh, sumTedadKartonTaminKonandeh ,
                        sumMablaghKolTaminKonandeh, takhfifSenfiTitrSatrModel.getNoeTedadRial(),(sumVaznTaminKonandeh/1000.0));
                //TODO:Vazn
                for (DataTableModel brand : brandByccTaminKonandeh)
                {
                    Log.d("takhfifTaminKonandeh", "takhfifSenfiSatrModel : " + takhfifSenfiTitrSatrModel.toString());
                    Log.d("takhfifTaminKonandeh", "takhfifSenfiSatrs : " + takhfifSenfiSatrs.toString());
                    for (TakhfifSenfiSatrModel takhfifSenfiSatrModel : takhfifSenfiSatrs)
                    {
                        ccTaminKonandehMohasebeh = Integer.valueOf(takhfifSenfiSatrModel.getCcGorohMohasebeh());

                        ArrayList<DataTableModel> brandByccTaminKonandehMohasebeh = darkhastFaktorSatrDAO.getBrandByccTaminKonandehMohasebeh(darkhastFaktorModel.getCcDarkhastFaktor(), ccTaminKonandehMohasebeh, takhfifSenfiTitrSatrModel.getOlaviat(), takhfifSenfiTitrSatrModel.getNoeGheymat());

                        sumTedadTaminKonandehMohasebeh = Long.valueOf(brandByccTaminKonandehMohasebeh.get(0).getFiled2());
                        sumTedadBastehTaminKonandehMohasebeh = Long.valueOf(brandByccTaminKonandehMohasebeh.get(0).getFiled3());
                        sumTedadKartonTaminKonandehMohasebeh = Long.valueOf(brandByccTaminKonandehMohasebeh.get(0).getFiled4());
                        sumMablaghKolTaminKonandehMohasebeh = Math.round(Double.valueOf(brandByccTaminKonandehMohasebeh.get(0).getFiled5()));
                        mablaghVahed = Math.round(sumMablaghKolTaminKonandehMohasebeh / sumTedadTaminKonandehMohasebeh);


                        Log.d("takhfifTaminKonandeh", "sumTedadTaminKonandehMohasebeh : " + sumTedadTaminKonandehMohasebeh);
                        Log.d("takhfifTaminKonandeh", "sumTedadBastehTaminKonandehMohasebeh : " + sumTedadBastehTaminKonandehMohasebeh);
                        Log.d("takhfifTaminKonandeh", "sumTedadKartonTaminKonandehMohasebeh : " + sumTedadKartonTaminKonandehMohasebeh);
                        Log.d("takhfifTaminKonandeh", "sumMablaghKolTaminKonandehMohasebeh : " + sumMablaghKolTaminKonandehMohasebeh);
                        Log.d("takhfifTaminKonandeh", "mablaghVahedMohasebeh : " + mablaghVahed);

                        int zarib = calculateZarib(takhfifSenfiSatrModel.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatrModel.getCodeNoeBastehBandy(),
                                sumTedadKartonTaminKonandeh, sumTedadBastehTaminKonandeh, sumTedadTaminKonandeh, sumMablaghKolTaminKonandeh, (sumVaznTaminKonandeh/1000.0),tedadAghlam);
                        int tedad = calculateTedad(takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatrModel.getCodeNoeBastehBandy(), sumTedadKartonTaminKonandehMohasebeh, sumTedadBastehTaminKonandehMohasebeh, 1);
                        Log.d("takhfifTaminKonandeh", "brand.getFiled5() : " + brand.getFiled5() + "sumMablaghKolTaminKonandehMohasebeh:" + sumMablaghKolTaminKonandehMohasebeh);

                        MablaghKol = sumMablaghKolTaminKonandehMohasebeh;
                        Log.d("takhfifTaminKonandeh", "MablaghKol : " + MablaghKol + "takhfifSenfiTitrSatrModel.getOlaviat:" + takhfifSenfiTitrSatrModel.getOlaviat());
                        Mablagh =  calculateMablagh(takhfifSenfiTitrSatrModel.getNoeTedadRial(), (int) takhfifSenfiSatrModel.getBeEza(), (long) sumMablaghKolTaminKonandehMohasebeh, (long) mablaghVahed, takhfifSenfiSatrModel.getCcNoeField(), takhfifSenfiSatrModel.getCcGorohMohasebeh());
                        Log.d("takhfifTaminKonandeh", "Zarib : " + zarib + "Tedad: " +tedad );
                        Log.d("takhfifTaminKonandeh", "Mablagh : " + Mablagh);
                        long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, mablaghVahed, zarib, takhfifSenfiSatrModel.getBeEza(), takhfifSenfiSatrModel.getDarsadTakhfif(), takhfifSenfiTitrSatrModel.getNoeTedadRial());
                        if (mablaghTakhfif > 0)
                        {
                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatrModel.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());
                            long sumMablaghTalkhfifSatr = 0;
                            String allMablaghTakhfifSatr = "-1";

                            ArrayList<DataTableModel> arrayListKalaTaminKonandehInDarkhast = darkhastFaktorSatrDAO.getTaminKonandehOfKalaInDarkhast(darkhastFaktorModel.getCcDarkhastFaktor());
                            int j = 0;
                            for (DataTableModel kalaTaminKonandeh : arrayListKalaTaminKonandehInDarkhast)
                            {
                                j++;
                                if (kalaTaminKonandeh.getFiled1() != null && kalaTaminKonandeh.getFiled1().trim().equals(String.valueOf(ccTaminKonandehMohasebeh)))
                                {
                                    long mablaghTakhfifSatr = calculateMablaghTakhfifSatr(Integer.valueOf(kalaTaminKonandeh.getFiled3()), mablaghVahed, takhfifSenfiSatrModel.getDarsadTakhfif());
                                    if (mablaghTakhfifSatr > 0)
                                    {
                                        Log.d("takhfif" , "takhfifHajmiBrand 1.5 sumMablaghTakhfifSatr : " + mablaghTakhfifSatr + " , " + Math.round(mablaghTakhfifSatr)+ " , mablaghTakhfif: " +mablaghTakhfif + " , sumMablaghTalkhfifSatr: "+sumMablaghTalkhfifSatr );
                                        Log.d("takhfif" , "takhfifHajmiBrand j  : " + j + " , arrayListKalaBrandInDarkhast.Size : " + arrayListKalaTaminKonandehInDarkhast.size() );
                                        if (j == arrayListKalaTaminKonandehInDarkhast.size() || mablaghTakhfif <= mablaghTakhfifSatr)
                                        {
                                            mablaghTakhfifSatr = mablaghTakhfif - sumMablaghTalkhfifSatr;
                                        }
                                        else
                                        {
                                            sumMablaghTalkhfifSatr += mablaghTakhfifSatr;
                                        }
                                        //sumMablaghTalkhfifSatr += mablaghTakhfifSatr;
                                        if (mablaghTakhfifSatr > 0)
                                        {
                                            Log.d("takhfif" , "takhfifHajmi 1.6 takhfifHajmiTitrSatrModel CcTakhfifHajmi: " + takhfifSenfiTitrSatrModel.getCcTakhfifSenfi() +" mablaghTakhfifSatr : " + mablaghTakhfifSatr + " , mablaghTakhfif: " + mablaghTakhfif + " , sumMablaghTalkhfifSatr: " + sumMablaghTalkhfifSatr);
                                            insertFaktorSatrTakhfif(context, Long.valueOf(kalaTaminKonandeh.getFiled2()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                                    takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatrModel.getDarsadTakhfif(), mablaghTakhfifSatr,
                                                    takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                                        }
                                    }





                                    sumMablaghTalkhfifSatr += Math.round(mablaghTakhfifSatr);
                                    allMablaghTakhfifSatr += "," + Math.round(mablaghTakhfifSatr);
                                    insertFaktorSatrTakhfif(context, Long.valueOf(kalaTaminKonandeh.getFiled2()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                            takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatrModel.getDarsadTakhfif(), mablaghTakhfifSatr,
                                            takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                                }
                            }
                            updateMablaghTakhfifDarkhastFaktor(context, darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), Math.round(mablaghTakhfif), sumMablaghTalkhfifSatr, allMablaghTakhfifSatr);
                        }
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateSenfiDiscountTaminKonandeh", "", "calculateDiscount", "");
            return false;
        }
    }


    public boolean calculateMantaghe(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel, int ccMantaghehMoshtary)
    {
        String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());
        int ccTaminKonandeh=0;
        long sumTedadTaminKonandeh=0; // Sum Tedad3 Darkhast Bar Hasbe
        // TaminKonandeh..
        long sumTedadBastehTaminKonandeh=0;
        long sumTedadKartonTaminKonandeh=0;
        long sumMablaghKolTaminKonandeh=0; // Sum MablaghKol Darkhast Bar
        long sumVaznTaminKonandeh=0;
        int tedadAghlam=0;

        KalaDAO kalaDAO = new KalaDAO(context);
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
        TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);

        // Hasbe TaminKonandeh..
        List<DataTableModel> taminKonandehs = darkhastFaktorSatrDAO.getTedadBeTafkikTaminKonandeh(darkhastFaktorModel.getCcDarkhastFaktor(),takhfifSenfiTitrSatrModel.getOlaviat(), takhfifSenfiTitrSatrModel.getNoeGheymat());
        for (DataTableModel taminKonandeh : taminKonandehs)
        {
            ccTaminKonandeh = Integer.valueOf(taminKonandeh.getFiled1());
            sumTedadTaminKonandeh = Long.valueOf(taminKonandeh.getFiled2());
            sumTedadBastehTaminKonandeh = Long.valueOf(taminKonandeh.getFiled3());
            sumTedadKartonTaminKonandeh = Long.valueOf(taminKonandeh.getFiled4());
            sumMablaghKolTaminKonandeh = Long.valueOf(taminKonandeh.getFiled5());
            sumVaznTaminKonandeh = Long.valueOf(taminKonandeh.getFiled6());
            tedadAghlam=Integer.valueOf(taminKonandeh.getFiled7());
            // --------------------------------------------------------------------------
            List<DataTableModel> brandByccTaminKonandeh = darkhastFaktorSatrDAO.getBrandByccTaminKonandeh(darkhastFaktorModel.getCcDarkhastFaktor(), ccTaminKonandeh, takhfifSenfiTitrSatrModel.getOlaviat(), takhfifSenfiTitrSatrModel.getNoeGheymat());
            for (DataTableModel brand : brandByccTaminKonandeh)
            {
                if (takhfifSenfiTitrSatrModel.getCcMantagheh() == ccMantaghehMoshtary)// check ccMantaghehTakhfif by ccMahaleh Moshtary
                {
                    // Satrhaye Takhfif..
                    List<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                            new int[]{getTedadRialTedad(), getTedadRialRial(), getTedadRialVazn(), getTedadRialAghlam()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                            NAME_NOE_FIELD_TAMIN_KONANDE, ccTaminKonandeh, sumTedadTaminKonandeh, sumTedadBastehTaminKonandeh, sumTedadKartonTaminKonandeh,
                            sumMablaghKolTaminKonandeh, takhfifSenfiTitrSatrModel.getNoeTedadRial(),sumVaznTaminKonandeh);
                    //TODO:Vazn
                    for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs)
                    {
                        int zarib = calculateZarib(takhfifSenfiSatr.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonTaminKonandeh, sumTedadBastehTaminKonandeh, sumTedadTaminKonandeh, sumMablaghKolTaminKonandeh, (sumVaznTaminKonandeh/1000.0),tedadAghlam);
                        long mablaghTakhfif = Math.round(sumMablaghKolTaminKonandeh * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100));
                        // Insert In DarkhastFaktorTakhfif &
                        // DarkhastFaktorSatrTakhfif..
                        if (mablaghTakhfif != 0) {
                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());

                            List<DarkhastFaktorSatrModel> darkhastFaktorSatrs = darkhastFaktorSatrDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
                            for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
                            {
                                KalaModel kalaModel = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());
                                if (kalaModel.getCcTaminKonandeh() == ccTaminKonandeh)
                                {
                                    long mablaghTakhfifSatr = Math.round((darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (takhfifSenfiSatr.getDarsadTakhfif() / 100));
                                    insertFaktorSatrTakhfif(context, darkhastFaktorSatr.getCcDarkhastFaktorSatr(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                            takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}
