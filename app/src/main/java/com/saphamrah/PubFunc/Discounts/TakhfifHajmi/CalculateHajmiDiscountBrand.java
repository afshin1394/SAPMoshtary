package com.saphamrah.PubFunc.Discounts.TakhfifHajmi;

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
import com.saphamrah.Model.TakhfifHajmiSatrModel;
import com.saphamrah.Model.TakhfifHajmiTitrSatrModel;
import com.saphamrah.PubFunc.Discounts.DiscountCalculation;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CalculateHajmiDiscountBrand extends DiscountCalculation
{
    private Context context;


    public CalculateHajmiDiscountBrand(Context context)
    {
        super(context);
        this.context = context;
    }

    public boolean calculateDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel)
    {
        try
        {
            int ccBrand;
            long sumTedadBrand; // Sum Tedad3 Darkhast Bar Hasbe Brand..
            long sumTedadBastehBrand;
            long sumTedadKartonBrand;
            long sumMablaghKolBrand; // Sum MablaghKol Darkhast Bar Hasbe Brand..
            long sumVaznBrand=0;
            int tedadAghlam=0;

            int ccBrandMohasebeh;
            long sumTedadBrandMohasebeh; // Sum Tedad3 Darkhast Bar Hasbe Brand Mohasebeh..
            long sumTedadBastehBrandMohasebeh;
            long sumTedadKartonBrandMohasebeh;
            long sumMablaghKolBrandMohasebeh; // Sum MablaghKol Darkhast Bar Hasbe Brand Mohasebeh..

            long mablaghVahed=0;
            int zarib = 0;
            String codeTakhfif = "";
            long Mablagh=0;

            ParameterChildDAO childParameterDAO = new ParameterChildDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);
            ArrayList<DataTableModel> brands = darkhastFaktorSatrDAO.getTedadBeTafkikBrand(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getOlaviat(),takhfifHajmiTitrSatrModel.getNoeGheymat());
            codeTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());

            for (DataTableModel brand : brands)
            {
                ccBrand = Integer.valueOf(brand.getFiled1());
                sumTedadBrand = Long.valueOf(brand.getFiled2());
                sumTedadBastehBrand = Long.valueOf(brand.getFiled4());
                sumTedadKartonBrand = Long.valueOf(brand.getFiled3());
                sumMablaghKolBrand = Math.round(Double.valueOf(brand.getFiled5()));
                sumVaznBrand = Math.round(Double.valueOf(brand.getFiled6()));
                tedadAghlam = Integer.valueOf(brand.getFiled7());


                mablaghVahed = Math.round(sumMablaghKolBrand / sumTedadBrand);

                Log.d("takhfifBrand", "mablaghVahed : " + mablaghVahed);

                ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                        new int[]{getTedadRialTedad(), getTedadRialRial(), getTedadRialVazn(), getTedadRialAghlam()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                        NAME_NOE_FIELD_BRAND, ccBrand, sumTedadBrand, sumTedadBastehBrand, sumTedadKartonBrand, sumMablaghKolBrand, takhfifHajmiTitrSatrModel.getNoeTedadRial(),(sumVaznBrand/1000.0),tedadAghlam);
                //TODO : Vazn

                for (TakhfifHajmiSatrModel takhfifHajmiSatrModel : takhfifHajmiSatrs)
                {
                    ccBrandMohasebeh = 0;
                    sumTedadBrandMohasebeh = 0;
                    sumTedadBastehBrandMohasebeh = 0;
                    sumTedadKartonBrandMohasebeh = 0;
                    sumMablaghKolBrandMohasebeh = 0;



                    ccBrandMohasebeh = takhfifHajmiSatrModel.getCcGorohMohasebeh();
                    ArrayList<DataTableModel> brandMohasebeh = darkhastFaktorSatrDAO.getTedadBeTafkikBrandMohasebeh(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getOlaviat(), ccBrandMohasebeh,takhfifHajmiTitrSatrModel.getNoeGheymat());

                    sumTedadBrandMohasebeh = Long.valueOf(brandMohasebeh.get(0).getFiled2());
                    sumTedadBastehBrandMohasebeh = Long.valueOf(brandMohasebeh.get(0).getFiled4());
                    sumTedadKartonBrandMohasebeh = Long.valueOf(brandMohasebeh.get(0).getFiled3());
                    sumMablaghKolBrandMohasebeh = Math.round(Double.valueOf(brandMohasebeh.get(0).getFiled5()));

                    mablaghVahed = Math.round (sumMablaghKolBrandMohasebeh / sumTedadBrandMohasebeh);

                    Log.d("takhfif", "takhfifBrand sumTedadBrandMohasebeh : " + sumTedadBrandMohasebeh);
                    Log.d("takhfif", "takhfifBrand sumTedadBastehBrandMohasebeh : " + sumTedadBastehBrandMohasebeh);
                    Log.d("takhfif", "takhfifBrand sumTedadKartonBrandMohasebeh : " + sumTedadKartonBrandMohasebeh);
                    Log.d("takhfif", "takhfifBrand sumMablaghKolBrandMohasebeh : " + sumMablaghKolBrandMohasebeh);
                    Log.d("takhfif", "takhfifBrand mablaghVahed : " + mablaghVahed);

                    zarib = calculateZarib(takhfifHajmiSatrModel.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatrModel.getCodeNoeBastehBandy(), sumTedadKartonBrand, sumTedadBastehBrand, sumTedadBrand, sumMablaghKolBrand,(sumVaznBrand/1000.0),tedadAghlam);
                    int tedad = calculateTedad(takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatrModel.getCodeNoeBastehBandy(), sumTedadKartonBrandMohasebeh, sumTedadBastehBrandMohasebeh, 1);
                    Mablagh =  calculateMablagh(takhfifHajmiTitrSatrModel.getNoeTedadRial(), (int) takhfifHajmiSatrModel.getBeEza(), (long) sumMablaghKolBrandMohasebeh, (long) mablaghVahed, takhfifHajmiSatrModel.getCcNoeField(), takhfifHajmiSatrModel.getCcGorohMohasebeh());
                    long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, mablaghVahed, zarib, takhfifHajmiSatrModel.getBeEza(), takhfifHajmiSatrModel.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                    if (mablaghTakhfif > 0)
                    {
                        long sumMablaghTalkhfifSatr = 0;
                        insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatrModel.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());
                        long sumMablaghTakhfifSatr = 0;
                        String allMablaghTakhfifSatr = "-1";
                        ArrayList<DataTableModel> arrayListKalaBrandInDarkhast = darkhastFaktorSatrDAO.getBrandOfKalaInDarkhast(darkhastFaktorModel.getCcDarkhastFaktor());
                        int j = 0;
                        for (DataTableModel kalaBrand : arrayListKalaBrandInDarkhast)
                        {
                            j++;
                            if (kalaBrand.getFiled1() != null && kalaBrand.getFiled1().trim().equals(String.valueOf(ccBrandMohasebeh)))
                            {
                                long mablaghTakhfifSatr = calculateMablaghTakhfifSatr(Integer.valueOf(kalaBrand.getFiled3()), mablaghVahed, takhfifHajmiSatrModel.getDarsadTakhfif());
                                if (mablaghTakhfifSatr > 0)
                                {
                                    Log.d("takhfif" , "takhfifHajmiBrand 1.5 sumMablaghTakhfifSatr : " + mablaghTakhfifSatr + " , " + Math.round(mablaghTakhfifSatr)+ " , mablaghTakhfif: " +mablaghTakhfif + " , sumMablaghTalkhfifSatr: "+sumMablaghTalkhfifSatr );
                                    Log.d("takhfif" , "takhfifHajmiBrand j  : " + j + " , arrayListKalaBrandInDarkhast.Size : " + arrayListKalaBrandInDarkhast.size() );
                                    if (j == arrayListKalaBrandInDarkhast.size() || mablaghTakhfif <= mablaghTakhfifSatr)
                                    {
                                        mablaghTakhfifSatr = mablaghTakhfif - sumMablaghTalkhfifSatr;
                                    }
                                    else
                                    {
                                        sumMablaghTalkhfifSatr += mablaghTakhfifSatr;
                                    }
                                    if (mablaghTakhfifSatr > 0)
                                    {
                                        Log.d("takhfif" , "takhfifHajmi 1.6 takhfifHajmiTitrSatrModel CcTakhfifHajmi: " + takhfifHajmiTitrSatrModel.getCcTakhfifHajmi() +" mablaghTakhfifSatr : " + mablaghTakhfifSatr + " , mablaghTakhfif: " + mablaghTakhfif + " , sumMablaghTalkhfifSatr: " + sumMablaghTalkhfifSatr);
                                        insertFaktorSatrTakhfif(context, Long.valueOf(kalaBrand.getFiled2()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                                takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatrModel.getDarsadTakhfif(), mablaghTakhfifSatr,
                                                takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                                    }
                                }






                                Log.d("takhfif" , "takhfifHajmiBrand mablaghTakhfifSatr : " + mablaghTakhfifSatr + "kalaBrand,filed2:" + kalaBrand.getFiled2() +"mablaghVahed:" + mablaghVahed + "Tedad:" + kalaBrand.getFiled3() + "Darsad:" + takhfifHajmiSatrModel.getDarsadTakhfif());
//                                sumMablaghTakhfifSatr += Math.round(mablaghTakhfifSatr);
//                                allMablaghTakhfifSatr += "," + Math.round(mablaghTakhfifSatr);
//                                insertFaktorSatrTakhfif(context, Long.valueOf(kalaBrand.getFiled2()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
//                                        takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatrModel.getDarsadTakhfif(), mablaghTakhfifSatr,
//                                        takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                            }
                        }
//                        updateMablaghTakhfifDarkhastFaktor(context, darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), Math.round(mablaghTakhfif), sumMablaghTakhfifSatr, allMablaghTakhfifSatr);
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateHajmiDiscountBrand", "", "calculateDiscount", "");
            return false;
        }
    }

    public boolean calculateMantaghe(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel, int ccMantaghehMoshtary)
    {
        String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());
        int ccBrand=0;
        double sumTedadBrand=0; // Sum Tedad3 Darkhast Bar Hasbe Brand..
        double sumTedadBastehBrand=0;
        double sumTedadKartonBrand=0;
        double sumMablaghKolBrand=0; // Sum MablaghKol Darkhast Bar Hasbe
        double sumVaznBrand=0;
        int tedadAghlam=0;

        KalaDAO kalaDAO = new KalaDAO(context);
        TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);

        // Brand..
        List<DataTableModel> brands = new DarkhastFaktorSatrDAO(context).getTedadBeTafkikBrand(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getOlaviat(),takhfifHajmiTitrSatrModel.getNoeGheymat());
        for (DataTableModel brand : brands)
        {
            ccBrand = Integer.valueOf(brand.getFiled1());
            sumTedadBrand = Double.valueOf(brand.getFiled2());
            sumTedadBastehBrand = Double.valueOf(brand.getFiled3());
            sumTedadKartonBrand = Double.valueOf(brand.getFiled4());
            sumMablaghKolBrand = Double.valueOf(brand.getFiled5());
            sumVaznBrand = Double.valueOf(brand.getFiled6());
            tedadAghlam = Integer.valueOf(brand.getFiled7());

            if (takhfifHajmiTitrSatrModel.getCcMantagheh() == ccMantaghehMoshtary)// check ccMantaghehTakhfif by ccMahaleh Moshtary
            {
                // Satrhaye Takhfif...
                List<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                        new int[]{getTedadRialTedad(), getTedadRialRial(),getTedadRialVazn(), getTedadRialAghlam()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                        NAME_NOE_FIELD_BRAND, ccBrand, sumTedadBrand, sumTedadBastehBrand, sumTedadKartonBrand, sumMablaghKolBrand, takhfifHajmiTitrSatrModel.getNoeTedadRial(),(sumVaznBrand/1000.0),tedadAghlam);
                //TODO:Vazn
                for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                {
                    int zarib = calculateZarib(takhfifHajmiSatr.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonBrand, sumTedadBastehBrand, sumTedadBrand, sumMablaghKolBrand,(sumVaznBrand/1000.0),tedadAghlam);
                    long mablaghTakhfif = Math.round(sumMablaghKolBrand * (zarib * takhfifHajmiSatr.getDarsadTakhfif() / 100));
                    // Insert In DarkhastFaktorTakhfif &
                    // DarkhastFaktorSatrTakhfif..
                    if (mablaghTakhfif != 0)
                    {
                        insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());

                        List<DarkhastFaktorSatrModel> darkhastFaktorSatrs = new DarkhastFaktorSatrDAO(context).getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
                        for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
                        {
                            KalaModel kalaModel = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());
                            if (kalaModel.getCcBrand() == ccBrand)
                            {
                                long mablaghTakhfifSatr = Math.round((darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (takhfifHajmiSatr.getDarsadTakhfif() / 100));
                                insertFaktorSatrTakhfif(context, darkhastFaktorSatr.getCcDarkhastFaktorSatr(), codeTakhfif,
                                        takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(),
                                        mablaghTakhfifSatr, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}
