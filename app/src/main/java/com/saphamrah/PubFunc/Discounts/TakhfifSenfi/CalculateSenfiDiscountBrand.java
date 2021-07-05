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
import com.saphamrah.Model.TakhfifSenfiSatrModel;
import com.saphamrah.Model.TakhfifSenfiTitrSatrModel;
import com.saphamrah.PubFunc.Discounts.DiscountCalculation;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CalculateSenfiDiscountBrand extends DiscountCalculation
{
    private Context context;


    public CalculateSenfiDiscountBrand(Context context)
    {
        super(context);
        this.context = context;
    }

    public boolean calculateDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel)
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
            TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);
            ArrayList<DataTableModel> brands = darkhastFaktorSatrDAO.getTedadBeTafkikBrand(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getOlaviat(),takhfifSenfiTitrSatrModel.getNoeGheymat());
            codeTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());

            for (DataTableModel brand : brands)
            {
                ccBrand = Integer.valueOf(brand.getFiled1());
                sumTedadBrand = Long.valueOf(brand.getFiled2());
                sumTedadBastehBrand = Long.valueOf(brand.getFiled3());
                sumTedadKartonBrand = Long.valueOf(brand.getFiled4());
                sumMablaghKolBrand = Math.round(Double.valueOf(brand.getFiled5()));
                sumVaznBrand = Math.round(Double.valueOf(brand.getFiled6()));
                tedadAghlam = Integer.valueOf(brand.getFiled7());


                mablaghVahed = Math.round(sumMablaghKolBrand / sumTedadBrand);

                Log.d("takhfifBrand", "mablaghVahed : " + mablaghVahed);

                ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                        new int[]{getTedadRialTedad(), getTedadRialRial(), getTedadRialVazn(), getTedadRialAghlam()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                        NAME_NOE_FIELD_BRAND, ccBrand, sumTedadBrand, sumTedadBastehBrand, sumTedadKartonBrand, sumMablaghKolBrand, takhfifSenfiTitrSatrModel.getNoeTedadRial(),(sumVaznBrand/1000.0));
                //TODO : Vazn

                for (TakhfifSenfiSatrModel takhfifSenfiSatrModel : takhfifSenfiSatrs)
                {
                    ccBrandMohasebeh = 0;
                    sumTedadBrandMohasebeh = 0;
                    sumTedadBastehBrandMohasebeh = 0;
                    sumTedadKartonBrandMohasebeh = 0;
                    sumMablaghKolBrandMohasebeh = 0;



                    ccBrandMohasebeh = takhfifSenfiSatrModel.getCcGorohMohasebeh();
                    ArrayList<DataTableModel> brandMohasebeh = darkhastFaktorSatrDAO.getTedadBeTafkikBrandMohasebeh(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getOlaviat(), ccBrandMohasebeh,takhfifSenfiTitrSatrModel.getNoeGheymat());

                    sumTedadBrandMohasebeh = Long.valueOf(brandMohasebeh.get(0).getFiled2());
                    sumTedadBastehBrandMohasebeh = Long.valueOf(brandMohasebeh.get(0).getFiled3());
                    sumTedadKartonBrandMohasebeh = Long.valueOf(brandMohasebeh.get(0).getFiled4());
                    sumMablaghKolBrandMohasebeh = Math.round(Double.valueOf(brandMohasebeh.get(0).getFiled5()));

                    mablaghVahed = Math.round (sumMablaghKolBrandMohasebeh / sumTedadBrandMohasebeh);

                    Log.d("takhfifBrand", "sumTedadBrandMohasebeh : " + sumTedadBrandMohasebeh);
                    Log.d("takhfifBrand", "sumTedadBastehBrandMohasebeh : " + sumTedadBastehBrandMohasebeh);
                    Log.d("takhfifBrand", "sumTedadKartonBrandMohasebeh : " + sumTedadKartonBrandMohasebeh);
                    Log.d("takhfifBrand", "sumMablaghKolBrandMohasebeh : " + sumMablaghKolBrandMohasebeh);
                    Log.d("takhfifBrand", "mablaghVahed : " + mablaghVahed);

                    zarib = calculateZarib(takhfifSenfiSatrModel.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatrModel.getCodeNoeBastehBandy(), sumTedadKartonBrand, sumTedadBastehBrand, sumTedadBrand, sumMablaghKolBrand,(sumVaznBrand/1000.0),tedadAghlam);
                    int tedad = calculateTedad(takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatrModel.getCodeNoeBastehBandy(), sumTedadKartonBrandMohasebeh, sumTedadBastehBrandMohasebeh, 1);
                    Mablagh =  calculateMablagh(takhfifSenfiTitrSatrModel.getNoeTedadRial(), (int) takhfifSenfiSatrModel.getBeEza(), (long) sumMablaghKolBrandMohasebeh, (long) mablaghVahed, takhfifSenfiSatrModel.getCcNoeField(), takhfifSenfiSatrModel.getCcGorohMohasebeh());
                    long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, mablaghVahed, zarib, takhfifSenfiSatrModel.getBeEza(), takhfifSenfiSatrModel.getDarsadTakhfif(), takhfifSenfiTitrSatrModel.getNoeTedadRial());
                    if (mablaghTakhfif > 0)
                    {
                        insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatrModel.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());
                        long sumMablaghTakhfifSatr = 0;
                        String allMablaghTakhfifSatr = "-1";
                        ArrayList<DataTableModel> arrayListKalaBrandInDarkhast = darkhastFaktorSatrDAO.getBrandOfKalaInDarkhast(darkhastFaktorModel.getCcDarkhastFaktor());
                        for (DataTableModel kalaBrand : arrayListKalaBrandInDarkhast)
                        {
                            if (kalaBrand.getFiled1() != null && kalaBrand.getFiled1().trim().equals(String.valueOf(ccBrandMohasebeh)))
                            {
                                long mablaghTakhfifSatr = calculateMablaghTakhfifSatr(Integer.valueOf(kalaBrand.getFiled3()), mablaghVahed, takhfifSenfiSatrModel.getDarsadTakhfif());
                                sumMablaghTakhfifSatr += Math.round(mablaghTakhfifSatr);
                                allMablaghTakhfifSatr += "," + Math.round(mablaghTakhfifSatr);
                                insertFaktorSatrTakhfif(context, Long.valueOf(kalaBrand.getFiled2()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                        takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatrModel.getDarsadTakhfif(), mablaghTakhfifSatr,
                                        takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                            }
                        }
                        updateMablaghTakhfifDarkhastFaktor(context, darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), Math.round(mablaghTakhfif), sumMablaghTakhfifSatr, allMablaghTakhfifSatr);
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateSenfiDiscountBrand", "", "calculateDiscount", "");
            return false;
        }
    }

    public boolean calculateMantaghe(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel, int ccMantaghehMoshtary)
    {
        String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());
        int ccBrand=0;
        double sumTedadBrand=0; // Sum Tedad3 Darkhast Bar Hasbe Brand..
        double sumTedadBastehBrand=0;
        double sumTedadKartonBrand=0;
        double sumMablaghKolBrand=0; // Sum MablaghKol Darkhast Bar Hasbe
        double sumVaznBrand=0;
        int tedadAghlam=0;

        KalaDAO kalaDAO = new KalaDAO(context);
        TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);

        // Brand..
        List<DataTableModel> brands = new DarkhastFaktorSatrDAO(context).getTedadBeTafkikBrand(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getOlaviat(),takhfifSenfiTitrSatrModel.getNoeGheymat());
        for (DataTableModel brand : brands)
        {
            ccBrand = Integer.valueOf(brand.getFiled1());
            sumTedadBrand = Double.valueOf(brand.getFiled2());
            sumTedadBastehBrand = Double.valueOf(brand.getFiled3());
            sumTedadKartonBrand = Double.valueOf(brand.getFiled4());
            sumMablaghKolBrand = Double.valueOf(brand.getFiled5());
            sumVaznBrand = Double.valueOf(brand.getFiled6());
            tedadAghlam = Integer.valueOf(brand.getFiled7());

            if (takhfifSenfiTitrSatrModel.getCcMantagheh() == ccMantaghehMoshtary)// check ccMantaghehTakhfif by ccMahaleh Moshtary
            {
                // Satrhaye Takhfif...
                List<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                        new int[]{getTedadRialTedad(), getTedadRialRial(),getTedadRialVazn(), getTedadRialAghlam()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                        NAME_NOE_FIELD_BRAND, ccBrand, sumTedadBrand, sumTedadBastehBrand, sumTedadKartonBrand, sumMablaghKolBrand, takhfifSenfiTitrSatrModel.getNoeTedadRial(),(sumVaznBrand/1000.0));
                //TODO:Vazn
                for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs)
                {
                    int zarib = calculateZarib(takhfifSenfiSatr.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonBrand, sumTedadBastehBrand, sumTedadBrand, sumMablaghKolBrand,(sumVaznBrand/1000.0),tedadAghlam);
                    long mablaghTakhfif = Math.round(sumMablaghKolBrand * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100));
                    // Insert In DarkhastFaktorTakhfif &
                    // DarkhastFaktorSatrTakhfif..
                    if (mablaghTakhfif != 0)
                    {
                        insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());

                        List<DarkhastFaktorSatrModel> darkhastFaktorSatrs = new DarkhastFaktorSatrDAO(context).getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
                        for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
                        {
                            KalaModel kalaModel = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());
                            if (kalaModel.getCcBrand() == ccBrand)
                            {
                                long mablaghTakhfifSatr = Math.round((darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (takhfifSenfiSatr.getDarsadTakhfif() / 100));
                                insertFaktorSatrTakhfif(context, darkhastFaktorSatr.getCcDarkhastFaktorSatr(), codeTakhfif,
                                        takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(),
                                        mablaghTakhfifSatr, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}
