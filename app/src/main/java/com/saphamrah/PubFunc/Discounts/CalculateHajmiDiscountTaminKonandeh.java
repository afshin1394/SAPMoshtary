package com.saphamrah.PubFunc.Discounts;

import android.content.Context;
import android.util.Log;

import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.TakhfifHajmiSatrDAO;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.DataTableModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.TakhfifHajmiSatrModel;
import com.saphamrah.Model.TakhfifHajmiTitrSatrModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CalculateHajmiDiscountTaminKonandeh extends DiscountCalculation
{

    private Context context;
    

    public CalculateHajmiDiscountTaminKonandeh(Context context, ArrayList<ParameterChildModel> parameterChildModels)
    {
        super(context);
        this.context = context;
    }

    public boolean calculateDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel)
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

            int ccTaminKonandehMohasebeh;
            long sumTedadTaminKonandehMohasebeh; // Sum Tedad3 Darkhast Bar Hasbe TaminKonandeh Mohasebeh..
            long sumTedadBastehTaminKonandehMohasebeh;
            long sumTedadKartonTaminKonandehMohasebeh;
            long sumMablaghKolTaminKonandehMohasebeh; // Sum MablaghKol Darkhast Bar Hasbe TaminKonandeh Mohasebeh..
            long Mablagh=0;
            long MablaghKol=0;
            long mablaghVahed = 0;

            codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());

            TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            ArrayList<DataTableModel> taminKonandehs = darkhastFaktorSatrDAO.getTedadBeTafkikTaminKonandeh(darkhastFaktorModel.getCcDarkhastFaktor(),takhfifHajmiTitrSatrModel.getOlaviat());

            for (DataTableModel taminKonandeh : taminKonandehs)
            {
                ccTaminKonandeh = Integer.valueOf(taminKonandeh.getFiled1());
                sumTedadTaminKonandeh = Long.valueOf(taminKonandeh.getFiled2());
                sumTedadBastehTaminKonandeh = Long.valueOf(taminKonandeh.getFiled3());
                sumTedadKartonTaminKonandeh = Long.valueOf(taminKonandeh.getFiled4());
                sumMablaghKolTaminKonandeh = Math.round(Double.valueOf(taminKonandeh.getFiled5()));
                sumVaznTaminKonandeh = Math.round(Double.valueOf(taminKonandeh.getFiled6()));

                mablaghVahed = Math.round(sumMablaghKolTaminKonandeh / sumTedadTaminKonandeh);

                ArrayList<DataTableModel> brandByccTaminKonandeh = darkhastFaktorSatrDAO.getBrandByccTaminKonandeh(darkhastFaktorModel.getCcDarkhastFaktor(), ccTaminKonandeh, takhfifHajmiTitrSatrModel.getOlaviat());
                Log.d("takhfifTaminKonandeh", "brandByccTaminKonandeh : " + brandByccTaminKonandeh.toString());
                Log.d("takhfifTaminKonandeh", "sumMablaghKolTaminKonandeh : " + sumMablaghKolTaminKonandeh);
                Log.d("takhfifTaminKonandeh", "takhfifHajmiTitrSatrModel.getOlaviat() : " + takhfifHajmiTitrSatrModel.getOlaviat());

                ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                        new int[]{getTedadRialTedad(), getTedadRialRial(), getTedadRialVazn()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                        NAME_NOE_FIELD_TAMIN_KONANDE, ccTaminKonandeh, sumTedadTaminKonandeh, sumTedadBastehTaminKonandeh, sumTedadKartonTaminKonandeh ,
                        sumMablaghKolTaminKonandeh, takhfifHajmiTitrSatrModel.getNoeTedadRial(),(sumVaznTaminKonandeh/1000.0));
                //TODO:Vazn
                for (DataTableModel brand : brandByccTaminKonandeh)
                {
                    Log.d("takhfifTaminKonandeh", "takhfifHajmiSatrModel : " + takhfifHajmiTitrSatrModel.toString());
                    Log.d("takhfifTaminKonandeh", "takhfifHajmiSatrs : " + takhfifHajmiSatrs.toString());
                    for (TakhfifHajmiSatrModel takhfifHajmiSatrModel : takhfifHajmiSatrs)
                    {
                        ccTaminKonandehMohasebeh = Integer.valueOf(takhfifHajmiSatrModel.getCcGorohMohasebeh());

                        ArrayList<DataTableModel> brandByccTaminKonandehMohasebeh = darkhastFaktorSatrDAO.getBrandByccTaminKonandehMohasebeh(darkhastFaktorModel.getCcDarkhastFaktor(), ccTaminKonandehMohasebeh, takhfifHajmiTitrSatrModel.getOlaviat());

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

                        int zarib = calculateZarib(takhfifHajmiSatrModel.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatrModel.getCodeNoeBastehBandy(),
                                sumTedadKartonTaminKonandeh, sumTedadBastehTaminKonandeh, sumTedadTaminKonandeh, sumMablaghKolTaminKonandeh, (sumVaznTaminKonandeh/1000.0));
                        int tedad = calculateTedad(takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatrModel.getCodeNoeBastehBandy(), sumTedadKartonTaminKonandehMohasebeh, sumTedadBastehTaminKonandehMohasebeh, 1);
                        Log.d("takhfifTaminKonandeh", "brand.getFiled5() : " + brand.getFiled5() + "sumMablaghKolTaminKonandehMohasebeh:" + sumMablaghKolTaminKonandehMohasebeh);

                        MablaghKol = sumMablaghKolTaminKonandehMohasebeh;
                        Log.d("takhfifTaminKonandeh", "MablaghKol : " + MablaghKol + "takhfifHajmiTitrSatrModel.getOlaviat:" + takhfifHajmiTitrSatrModel.getOlaviat());
                        Mablagh =  calculateMablagh(takhfifHajmiTitrSatrModel.getNoeTedadRial(), (int) takhfifHajmiSatrModel.getBeEza(), (long) sumMablaghKolTaminKonandehMohasebeh, (long) mablaghVahed, takhfifHajmiSatrModel.getCcNoeField(), takhfifHajmiSatrModel.getCcGorohMohasebeh());
                        Log.d("takhfifTaminKonandeh", "Zarib : " + zarib + "Tedad: " +tedad );
                        Log.d("takhfifTaminKonandeh", "Mablagh : " + Mablagh);
                        long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, mablaghVahed, zarib, takhfifHajmiSatrModel.getBeEza(), takhfifHajmiSatrModel.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                        if (mablaghTakhfif > 0)
                        {
                            insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatrModel.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());
                            long sumMablaghTalkhfifSatr = 0;
                            String allMablaghTakhfifSatr = "-1";

                            ArrayList<DataTableModel> arrayListKalaTaminKonandehInDarkhast = darkhastFaktorSatrDAO.getTaminKonandehOfKalaInDarkhast(darkhastFaktorModel.getCcDarkhastFaktor());
                            for (DataTableModel kalaTaminKonandeh : arrayListKalaTaminKonandehInDarkhast)
                            {
                                if (kalaTaminKonandeh.getFiled1() != null && kalaTaminKonandeh.getFiled1().trim().equals(String.valueOf(ccTaminKonandehMohasebeh)))
                                {
                                    long mablaghTakhfifSatr = calculateMablaghTakhfifSatr(Integer.valueOf(kalaTaminKonandeh.getFiled3()), mablaghVahed, takhfifHajmiSatrModel.getDarsadTakhfif());
                                    sumMablaghTalkhfifSatr += Math.round(mablaghTakhfifSatr);
                                    allMablaghTakhfifSatr += "," + Math.round(mablaghTakhfifSatr);
                                    insertFaktorSatrTakhfifHajmi(context, Long.valueOf(kalaTaminKonandeh.getFiled2()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                            takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatrModel.getDarsadTakhfif(), mablaghTakhfifSatr,
                                            takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                                }
                            }
                            updateMablaghTakhfifDarkhastFaktor(context, darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), Math.round(mablaghTakhfif), sumMablaghTalkhfifSatr, allMablaghTakhfifSatr);
                        }
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateHajmiDiscountTaminKonandeh", "", "calculateDiscount", "");
            return false;
        }
    }


    public boolean calculateMantaghe(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel, int ccMantaghehMoshtary)
    {
        String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());
        int ccTaminKonandeh=0;
        long sumTedadTaminKonandeh=0; // Sum Tedad3 Darkhast Bar Hasbe
        // TaminKonandeh..
        long sumTedadBastehTaminKonandeh=0;
        long sumTedadKartonTaminKonandeh=0;
        long sumMablaghKolTaminKonandeh=0; // Sum MablaghKol Darkhast Bar
        long sumVaznTaminKonandeh=0;

        KalaDAO kalaDAO = new KalaDAO(context);
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
        TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);

        // Hasbe TaminKonandeh..
        List<DataTableModel> taminKonandehs = darkhastFaktorSatrDAO.getTedadBeTafkikTaminKonandeh(darkhastFaktorModel.getCcDarkhastFaktor(),takhfifHajmiTitrSatrModel.getOlaviat());
        for (DataTableModel taminKonandeh : taminKonandehs)
        {
            ccTaminKonandeh = Integer.valueOf(taminKonandeh.getFiled1());
            sumTedadTaminKonandeh = Long.valueOf(taminKonandeh.getFiled2());
            sumTedadBastehTaminKonandeh = Long.valueOf(taminKonandeh.getFiled3());
            sumTedadKartonTaminKonandeh = Long.valueOf(taminKonandeh.getFiled4());
            sumMablaghKolTaminKonandeh = Long.valueOf(taminKonandeh.getFiled5());
            sumVaznTaminKonandeh = Long.valueOf(taminKonandeh.getFiled6());
            // --------------------------------------------------------------------------
            List<DataTableModel> brandByccTaminKonandeh = darkhastFaktorSatrDAO.getBrandByccTaminKonandeh(darkhastFaktorModel.getCcDarkhastFaktor(), ccTaminKonandeh, takhfifHajmiTitrSatrModel.getOlaviat());
            for (DataTableModel brand : brandByccTaminKonandeh)
            {
                if (takhfifHajmiTitrSatrModel.getCcMantagheh() == ccMantaghehMoshtary)// check ccMantaghehTakhfif by ccMahaleh Moshtary
                {
                    // Satrhaye Takhfif..
                    List<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                            new int[]{getTedadRialTedad(), getTedadRialRial(), getTedadRialVazn()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                            NAME_NOE_FIELD_TAMIN_KONANDE, ccTaminKonandeh, sumTedadTaminKonandeh, sumTedadBastehTaminKonandeh, sumTedadKartonTaminKonandeh,
                            sumMablaghKolTaminKonandeh, takhfifHajmiTitrSatrModel.getNoeTedadRial(),sumVaznTaminKonandeh);
                    //TODO:Vazn
                    for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                    {
                        int zarib = calculateZarib(takhfifHajmiSatr.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonTaminKonandeh, sumTedadBastehTaminKonandeh, sumTedadTaminKonandeh, sumMablaghKolTaminKonandeh, (sumVaznTaminKonandeh/1000.0));
                        long mablaghTakhfif = Math.round(sumMablaghKolTaminKonandeh * (zarib * takhfifHajmiSatr.getDarsadTakhfif() / 100));
                        // Insert In DarkhastFaktorTakhfif &
                        // DarkhastFaktorSatrTakhfif..
                        if (mablaghTakhfif != 0) {
                            insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());

                            List<DarkhastFaktorSatrModel> darkhastFaktorSatrs = darkhastFaktorSatrDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
                            for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
                            {
                                KalaModel kalaModel = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());
                                if (kalaModel.getCcTaminKonandeh() == ccTaminKonandeh)
                                {
                                    long mablaghTakhfifSatr = Math.round((darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (takhfifHajmiSatr.getDarsadTakhfif() / 100));
                                    insertFaktorSatrTakhfifHajmi(context, darkhastFaktorSatr.getCcDarkhastFaktorSatr(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                            takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
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
