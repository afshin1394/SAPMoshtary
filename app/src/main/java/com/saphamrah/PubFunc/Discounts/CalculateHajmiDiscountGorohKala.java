package com.saphamrah.PubFunc.Discounts;

import android.content.Context;
import android.util.Log;

import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.JayezehEntekhabiDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaGorohDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryRotbehDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.TakhfifHajmiSatrDAO;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.DataTableModel;
import com.saphamrah.Model.JayezehEntekhabiModel;
import com.saphamrah.Model.KalaDarkhastFaktorModel;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.TakhfifHajmiSatrModel;
import com.saphamrah.Model.TakhfifHajmiTitrSatrModel;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculateHajmiDiscountGorohKala extends DiscountCalculation
{

    private final static String CLASS_NAME = "CalculateHajmiDiscountGorohKala";
    private Context context;


    public CalculateHajmiDiscountGorohKala(Context context)
    {
        super(context);
        this.context = context;
    }


    public boolean calculateDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel)
    {
        try
        {
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());
            long sumTedadGorohKala = 0; // Sum Tedad Darkhast Bar Hasbe GorohKala..
            long sumTedadBastehGorohKala = 0;
            long sumTedadKartonGorohKala = 0;
            long sumMablaghForoshGorohKala = 0;// Sum MablaghForosh Darkhast Bar Hasbe GorohKala..
            long sumMablaghKolGorohKala = 0; // Sum MablaghKol Darkhast Bar Hasbe GorohKala..
            long sumVaznGorohkala=0;

            long sumTedadGorohKalaMohasebeh = 0; // Sum Tedad Darkhast Bar Hasbe GorohKala Mohasebeh..
            long sumTedadBastehGorohKalaMohasebeh = 0;
            long sumTedadKartonGorohKalaMohasebeh = 0;
            long sumMablaghForoshGorohKalaMohasebeh = 0;// Sum MablaghForosh Darkhast Bar Hasbe GorohKala Mohasebeh..
            long sumMablaghKolGorohKalaMohasebeh = 0; // Sum MablaghKol Darkhast Bar Hasbe GorohKala Mohasebeh..

            TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            MoshtaryModel moshtaryModel = new MoshtaryDAO(context).getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
            int currentOlaviat = takhfifHajmiTitrSatrModel.getOlaviat();
            ArrayList<DataTableModel> gorohKalas= darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), currentOlaviat);
            ArrayList<DataTableModel> rowGorohKalas= darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi());
            Log.d("takhfifHajmi" , "takhfifHajmiTitrSatrModel1 : " + takhfifHajmiTitrSatrModel.toString());
            Log.d("takhfifHajmi" , "gorohKalas : " + gorohKalas.toString());
            Log.d("takhfifHajmi" , "rowGorohKalas : " + rowGorohKalas.toString());

            for (DataTableModel gorohKala : gorohKalas)
            {
                Log.d("takhfifHajmi","gorohKala.getFiled5: " +Math.round(Double.valueOf(gorohKala.getFiled5())));
                Log.d("takhfifHajmi","gorohKala.getFiled8: " +Math.round(Double.valueOf(gorohKala.getFiled8())));
                sumTedadGorohKala = Math.round(Double.valueOf(gorohKala.getFiled2()));
                sumTedadBastehGorohKala = Math.round(Double.valueOf(gorohKala.getFiled3()));
                sumTedadKartonGorohKala = Math.round(Double.valueOf(gorohKala.getFiled4()));
                sumMablaghKolGorohKala = Math.round(Double.valueOf(gorohKala.getFiled5()));
                sumVaznGorohkala = Math.round(Double.valueOf(gorohKala.getFiled8()));



                int darajehBrandMoshtary = moshtaryModel.getDarajeh();

                Log.d("takhfifHajmi" , "sumMablaghKolGorohKala : " + sumMablaghKolGorohKala);

                if (darajehBrandMoshtary == takhfifHajmiTitrSatrModel.getDarajeh() || takhfifHajmiTitrSatrModel.getDarajeh()==0)
                {
                    TakhfifHajmiSatrModel takhfifhajmiSatr = takhfifHajmiSatrDAO.getByccTakhfifHajmi(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi() , "1").get(0);
                    Log.d("takhfifHajmi" , "takhfifhajmiSatr1 : " + takhfifhajmiSatr.toString());
                    if(takhfifhajmiSatr.getCodeNoeBastehBandy() == getBasteBandiBaste())
                    {
                        ArrayList<DataTableModel> gorohs = darkhastFaktorSatrDAO.getTedadKartonByccGorohKala(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), Integer.valueOf(gorohKala.getFiled1()), takhfifHajmiTitrSatrModel.getOlaviat());
                        sumTedadGorohKala= Math.round(Double.valueOf(gorohs.get(0).getFiled4()));
                        sumTedadBastehGorohKala = Math.round(Double.valueOf(gorohs.get(0).getFiled3()));
                        sumTedadKartonGorohKala = Math.round(Double.valueOf(gorohs.get(0).getFiled2()));
                    }
                    else if (takhfifhajmiSatr.getCodeNoeBastehBandy() == getBasteBandiCarton())
                    {
                        ArrayList<DataTableModel> gorohs = darkhastFaktorSatrDAO.getTedadKartonByccGorohKala(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), Integer.valueOf(gorohKala.getFiled1()), takhfifHajmiTitrSatrModel.getOlaviat());
                        sumTedadGorohKala= Math.round(Double.valueOf(gorohs.get(0).getFiled4()));
                        sumTedadBastehGorohKala = Math.round(Double.valueOf(gorohs.get(0).getFiled3()));
                        sumTedadKartonGorohKala = Math.round(Double.valueOf(gorohs.get(0).getFiled2()));
                    }

                    Log.d("takhfifHajmi" , "takhfifHajmiSatrs : before" );
                    ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi() ,
                            new int[]{getTedadRialTedad(), getTedadRialRial(),getTedadRialVazn()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                            NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()), sumTedadGorohKala, sumTedadBastehGorohKala, sumTedadKartonGorohKala,
                            sumMablaghKolGorohKala, takhfifHajmiTitrSatrModel.getNoeTedadRial(),(sumVaznGorohkala/1000.0));
                    //TODO : VAZN
                    Log.d("takhfifHajmi" , "takhfifHajmiSatrs : " + takhfifHajmiSatrs.toString());
                    for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                    {
                        ArrayList<DataTableModel> gorohKalaMohasebehs= darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaMohasebehAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiSatr.getCcTakhfifHajmiSatr(), takhfifHajmiSatr.getCcGorohMohasebeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                        ArrayList<DataTableModel> rowGorohKalaMohasebehs= darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaMohasebehAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getOlaviat());

                        Log.d("takhfifHajmi" , "takhfifHajmiSatr : " + takhfifHajmiSatr.toString());
                        Log.d("takhfifHajmi" , "gorohKalaMohasebehs : " + gorohKalaMohasebehs.toString());
                        Log.d("takhfifHajmi" , "rowGorohKalaMohasebehs : " + rowGorohKalaMohasebehs.toString());

                        if(gorohKalaMohasebehs.size()>0)
                        {
                            sumTedadGorohKalaMohasebeh = Math.round(Double.valueOf(gorohKalaMohasebehs.get(0).getFiled2()));
                            sumTedadBastehGorohKalaMohasebeh = Math.round(Double.valueOf(gorohKalaMohasebehs.get(0).getFiled3()));
                            sumTedadKartonGorohKalaMohasebeh = Math.round(Double.valueOf(gorohKalaMohasebehs.get(0).getFiled4()));
                            sumMablaghKolGorohKalaMohasebeh = Math.round(Double.valueOf(gorohKalaMohasebehs.get(0).getFiled5()));
                        }

                        Log.d("takhfifHajmi" , "sumTedadGorohKalaMohasebeh : " + sumTedadGorohKalaMohasebeh);
                        Log.d("takhfifHajmi" , "sumTedadBastehGorohKalaMohasebeh : " + sumTedadBastehGorohKalaMohasebeh);
                        Log.d("takhfifHajmi" , "sumTedadKartonGorohKalaMohasebeh : " + sumTedadKartonGorohKalaMohasebeh);
                        Log.d("takhfifHajmi" , "sumMablaghKolGorohKalaMohasebeh : " + sumMablaghKolGorohKalaMohasebeh);

                        long Mablagh=0;
                        long MablaghVahed=0;
                        int zarib = 0;
                        int Tedad = 0;
                        //zarib = calculateZarib(takhfifHajmiSatr.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala, sumMablaghKolGorohKala);
                        zarib = calculateZarib(takhfifHajmiSatr.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala, sumMablaghKolGorohKala, (sumVaznGorohkala/1000.0));
                        //Tedad = calculateTedad(takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala);
                        Tedad = calculateTedad(takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKalaMohasebeh, sumTedadBastehGorohKalaMohasebeh, sumTedadGorohKalaMohasebeh);
                        Log.d("takhfifHajmi" , "tedad : " + Tedad + " , zarib : " + zarib + " , sumMablaghKolGorohKala : " + sumMablaghKolGorohKala);

                        //if(Tedad>0)
                        MablaghVahed = calculateMablaghVahed(takhfifHajmiTitrSatrModel.getNoeTedadRial(), Tedad, sumVaznGorohkala, sumMablaghKolGorohKalaMohasebeh);//Math.round(sumMablaghKolGorohKalaMohasebeh/Tedad);

                        Mablagh =  calculateMablagh(takhfifHajmiTitrSatrModel.getNoeTedadRial(), (int) takhfifHajmiSatr.getBeEza(), (long) sumMablaghKolGorohKalaMohasebeh, MablaghVahed, takhfifHajmiSatr.getCcNoeField(), takhfifHajmiSatr.getCcGorohMohasebeh());

                        if (takhfifHajmiTitrSatrModel.getForJayezeh() != 2)
                        {
                            Log.d("takhfifHajmi" , "mablaghVahed : " + MablaghVahed);
                            Log.d("takhfifHajmi" , "Mablagh : " + Mablagh);
                            Log.d("takhfifHajmi" , "zarib : " + zarib);
                            Log.d("takhfifHajmi" , "takhfifHajmiSatr.getBeEza() : " + takhfifHajmiSatr.getBeEza());
                            Log.d("takhfifHajmi" , "Tedad : " + Tedad);
                            Log.d("takhfifHajmi" , "(takhfifHajmiSatr.getBeEza()*Tedad) : " + (takhfifHajmiSatr.getBeEza()*Tedad));
                            //double mablaghTakhfif = calculateMablaghTakhfif(sumMablaghKolGorohKala, MablaghVahed, zarib, takhfifHajmiSatr.getBeEza(), takhfifHajmiSatr.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                            long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, MablaghVahed, zarib, takhfifHajmiSatr.getBeEza(), takhfifHajmiSatr.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                            Log.d("takhfifHajmi","after mablaghTakhfif" + mablaghTakhfif);
                            if (mablaghTakhfif > 0)
                            {
                                //insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());
                                long sumMablaghTalkhfifSatr = 0;
                                // تعداد کالاهایی که در گروه کالا وجود دارد برای محاسبه آخرین مرحله تخفیف سطر
                                int countKalaExistInGoroh = 0;
                                Log.d("takhfifHajmi","takhfifHajmisatr-Before" + rowGorohKalaMohasebehs.toString());

                                for (int i=0 ; i < rowGorohKalaMohasebehs.size() ; i++)
                                {
                                    Log.d("takhfifHajmi","rowGorohKalaMohasebehs.get(i).getFiled2():"+rowGorohKalaMohasebehs.get(i).getFiled2()+" ,gorohKala.getFiled1():"+gorohKala.getFiled1());
                                    if (rowGorohKalaMohasebehs.get(i).getFiled2().equals(gorohKalaMohasebehs.get(0).getFiled1()))
                                    {
                                        countKalaExistInGoroh++;
                                        Log.d("takhfifHajmi","takhfifHajmisatr-countKalaExistInGoroh" + countKalaExistInGoroh);
                                    }
                                }
                                int j = 0;
                                for (int i=0 ; i < rowGorohKalaMohasebehs.size() ; i++)
                                {
                                    DataTableModel row = rowGorohKalaMohasebehs.get(i);
                                    Log.d("takhfifHajmi","1 .1 takhfifHajmisatr-row: " + row.getFiled1() + " , "+ row.getFiled2() + " , "+ row.getFiled3() + " , "+ row.getFiled4() );

                                    Log.d("takhfifHajmi","takhfifHajmisatr-row.getFiled2(): "+ row.getFiled2() + " ,gorohKalaMohasebehs.get(0).getFiled1(): "+ gorohKalaMohasebehs.get(0).getFiled1());
                                    if (row.getFiled2().equals(gorohKalaMohasebehs.get(0).getFiled1()))
                                    {
                                        j++;
                                        long mablaghTakhfifSatr = 0;
                                        double mablaghTakhfifVahed = 0;
                                        Log.d("takhfifHajmi","takhfifHajmisatr-mablaghTakhfif"+mablaghTakhfif+", tedad"+sumTedadGorohKalaMohasebeh+ "-- " +
                                                (double) mablaghTakhfif/(double) sumTedadGorohKalaMohasebeh+"    " +takhfifHajmiTitrSatrModel.getCcTakhfifHajmi());
                                       // mablaghTakhfifVahed = (double) mablaghTakhfif/ (double) sumTedadGorohKalaMohasebeh;
                                        Log.d("takhfifHajmi" , "takhfifHajmisatr-noetedadrial : " + takhfifHajmiTitrSatrModel.getNoeTedadRial());
//                                        if (takhfifHajmiTitrSatrModel.getNoeTedadRial() == getTedadRialTedad())
//                                        {
//                                            mablaghTakhfifSatr = Math.round(Long.valueOf(row.getFiled4())* mablaghTakhfifVahed) ;
//                                            Log.d("takhfifHajmi" , "takhfifHajmisatr-mablaghTakhfif : " + mablaghTakhfif + " , Tedad : " + Tedad);
//                                            Log.d("takhfifHajmi" , "takhfifHajmisatr-row.getFiled4() : " + row.getFiled4() + " , mablaghTakhfifVahed : " + mablaghTakhfifVahed);
//                                            Log.d("takhfifHajmi" , "mablaghTakhfifSatr : " + mablaghTakhfifSatr);
//                                        }
//                                        else
//                                        {
//                                            mablaghTakhfifSatr = Math.round(Long.valueOf(row.getFiled4())* mablaghTakhfifVahed) ;
//                                            Log.d("takhfifHajmi" , "mablaghTakhfifSatr : " + mablaghTakhfifSatr + " , mablaghTakhfifVahed : " + mablaghTakhfifVahed + " , row.getFiled4() : " + row.getFiled4());
//                                        }
                                        Log.d("takhfifHajmi", "1.4 f3: " + Double.parseDouble(row.getFiled3()) + " , f4:" + Double.parseDouble(row.getFiled4()) + " , f5: " + Double.parseDouble(row.getFiled5()) + " , darsad:" +takhfifHajmiSatr.getDarsadTakhfif());
                                        mablaghTakhfifSatr = Math.round((((Double.parseDouble(row.getFiled3())*Double.parseDouble(row.getFiled4()))-Double.parseDouble(row.getFiled5()))*takhfifHajmiSatr.getDarsadTakhfif())/100);
                                        if (mablaghTakhfifSatr > 0)
                                        {
                                            Log.d("takhfifHajmi" , " 1.5 sumMablaghTakhfifSatr : " + mablaghTakhfifSatr + " , " + Math.round(mablaghTakhfifSatr)+ " , mablaghTakhfif: " +mablaghTakhfif + " , sumMablaghTalkhfifSatr: "+sumMablaghTalkhfifSatr );
                                            Log.d("takhfifHajmi" , " j  : " + j + " , rowKala.Size : " + rowGorohKalas.size() + " , rowGorohKalas.size()-1 : " + (rowGorohKalas.size()-1) + " , countKalaExistInGoroh : " + countKalaExistInGoroh);
//                                            if (j == countKalaExistInGoroh || mablaghTakhfif <= mablaghTakhfifSatr)
//                                            {
//                                                mablaghTakhfifSatr = mablaghTakhfif - sumMablaghTalkhfifSatr;
//                                            }
//                                            else
//                                            {
//                                                sumMablaghTalkhfifSatr += mablaghTakhfifSatr;
//                                            }
                                            sumMablaghTalkhfifSatr += mablaghTakhfifSatr;
                                            if (mablaghTakhfifSatr > 0)
                                            {
                                                Log.d("takhfifHajmi" , " 1.6 takhfifHajmiTitrSatrModel CcTakhfifHajmi: " + takhfifHajmiTitrSatrModel.getCcTakhfifHajmi() +" mablaghTakhfifSatr : " + mablaghTakhfifSatr + " , mablaghTakhfif: " + mablaghTakhfif + " , sumMablaghTalkhfifSatr: " + sumMablaghTalkhfifSatr);
                                                insertFaktorSatrTakhfifHajmi(context, Long.valueOf(row.getFiled1()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                                            }
                                        }
                                    }
                                }
                                Log.d("takhfifHajmi" , " 1.7 takhfifHajmiTitrSatrModel CcTakhfifHajmi: " + takhfifHajmiTitrSatrModel.getCcTakhfifHajmi()  + " , sumMablaghTalkhfifSatr: " + sumMablaghTalkhfifSatr);
                                mablaghTakhfif = sumMablaghTalkhfifSatr;
                                insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());
                            }
                        }
                        else if (takhfifHajmiTitrSatrModel.getForJayezeh() == 2)
                        {
                            long mablaghMandehTakhfifPasAzKasrPoromotion = calculateMablaghMandehTakhfifPasAzKasrPromotion(sumMablaghKolGorohKala, zarib, darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiSatr.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                            if (mablaghMandehTakhfifPasAzKasrPoromotion > 0)
                            {
                                insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghMandehTakhfifPasAzKasrPoromotion, takhfifHajmiTitrSatrModel.getForJayezeh());
                                long ccDarkhastFaktorSatr =  Long.valueOf(rowGorohKalas.get(0).getFiled1());
                                insertFaktorSatrTakhfifHajmi(context, ccDarkhastFaktorSatr, codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghMandehTakhfifPasAzKasrPoromotion, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                            }
                        }
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateHajmiDiscountGorohKala", "", "calculateDiscount", "");
            return false;
        }
    }

    public boolean calculateDiscountTakidi(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel)
    {
        try
        {
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());
            long sumTedadGorohKala= 0;
            long sumTedadBastehGorohKala= 0;
            long sumTedadKartonGorohKala= 0;
            long sumMablaghKolGorohKala= 0;
            long sumVaznGorohKala = 0;
            int tedadKalaTakidi = 0;
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            KalaGorohDAO kalaGorohDAO = new KalaGorohDAO(context);
            ArrayList<KalaGorohModel> kalaGorohModels = kalaGorohDAO.getByccGoroh(takhfifHajmiTitrSatrModel.getCcGorohTakidi());
            ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = darkhastFaktorSatrDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());

            for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrModels)
            {
                for (KalaGorohModel kalaGorohModel : kalaGorohModels)
                {
                    if (darkhastFaktorSatrModel.getCcKalaCode() == kalaGorohModel.getCcKalaCode())
                    {
                        tedadKalaTakidi++;
                    }
                }
            }

            if (tedadKalaTakidi > 0)
            {
                TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);
                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(context);
                MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
                ArrayList<DataTableModel> gorohKalas = darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),takhfifHajmiTitrSatrModel.getOlaviat());
                ArrayList<DataTableModel> rowGorohKalas = darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi());

                for (DataTableModel gorohKala : gorohKalas)
                {
                    sumTedadGorohKala= Long.valueOf(gorohKala.getFiled2());
                    sumTedadBastehGorohKala = Long.valueOf(gorohKala.getFiled3());
                    sumTedadKartonGorohKala = Long.valueOf(gorohKala.getFiled4());
                    sumMablaghKolGorohKala = Math.round(Double.valueOf(gorohKala.getFiled5()));
                    sumVaznGorohKala = Long.valueOf(gorohKala.getFiled8());

                    int darajehBrandMoshtary = moshtaryModel.getDarajeh();

                    if(darajehBrandMoshtary == takhfifHajmiTitrSatrModel.getDarajeh())
                    {
                        TakhfifHajmiSatrModel takhfifhajmiSatr = takhfifHajmiSatrDAO.getByccTakhfifHajmi(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi() , "1").get(0);
                        if(takhfifhajmiSatr.getCodeNoeBastehBandy() == getBasteBandiCarton())
                        {
                            ArrayList<DataTableModel> gorohs = darkhastFaktorSatrDAO.getTedadKartonByccGorohKala(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), Integer.valueOf(gorohKala.getFiled1()), takhfifHajmiTitrSatrModel.getOlaviat());
                            sumTedadGorohKala= Long.valueOf(gorohs.get(0).getFiled4());
                            sumTedadBastehGorohKala = Long.valueOf(gorohs.get(0).getFiled3());
                            sumTedadKartonGorohKala = Long.valueOf(gorohs.get(0).getFiled2());
                        }
                        //Satrhaye Takhfif..
                        ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi() ,
                                new int[]{getTedadRialTedad(),getTedadRialRial(),getTedadRialVazn()}, new int[]{getBasteBandiCarton(),getBasteBandiBaste(),getBasteBandiAdad()},
                                NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()), sumTedadGorohKala, sumTedadBastehGorohKala,
                                sumTedadKartonGorohKala, sumMablaghKolGorohKala, takhfifHajmiTitrSatrModel.getNoeTedadRial(),0);
                        //TODO:Vazn
                        for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                        {
                            int zarib = calculateZarib(takhfifHajmiSatr.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala, sumMablaghKolGorohKala, (sumVaznGorohKala/1000.0));
                            int Tedad = calculateTedad(takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala);
                            int MablaghVahed = 0;
                            if(Tedad>0)
                                MablaghVahed = (int)(sumMablaghKolGorohKala/Tedad);

                            if (takhfifHajmiTitrSatrModel.getForJayezeh() != 2)
                            {
                                long mablaghTakhfif = calculateMablaghTakhfif(sumMablaghKolGorohKala, MablaghVahed, zarib, takhfifHajmiSatr.getBeEza(), takhfifHajmiSatr.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                                if (mablaghTakhfif > 0)
                                {
                                    insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(),
                                            takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());

                                    long sumMablaghTalkhfifSatr = 0;
                                    String allMablaghTakhfifSatr = "-1";
                                    for (DataTableModel row : rowGorohKalas)
                                    {
                                        if (row.getFiled2().equals(gorohKala.getFiled1()))
                                        {
                                            long mablaghTakhfifSatr = 0;
                                            double mablaghTakhfifVahed = 0;
                                            if (takhfifHajmiTitrSatrModel.getNoeTedadRial() == getTedadRialTedad())
                                            {
                                                Log.d("takhfifHajmi" , "mablaghTakhfif : " + mablaghTakhfif + " , tedad : " + Tedad);
                                                Log.d("takhfifHajmi" , "field4 : " + row.getFiled4() + " , " + mablaghTakhfifVahed);
                                                mablaghTakhfifVahed = (double)mablaghTakhfif/(double)Tedad;
                                                mablaghTakhfifSatr = Math.round(Double.valueOf(row.getFiled4())* mablaghTakhfifVahed);
                                                Log.d("takhfifHajmi" , "mablaghTakhfifSatr : " + mablaghTakhfifSatr);
                                            }
                                            else
                                            {
                                                Log.d("takhfifHajmi" , "field3 : " + row.getFiled3() + " , row.getFiled4() : " + row.getFiled4() + " , darsad : " + takhfifHajmiSatr.getDarsadTakhfif());
                                                mablaghTakhfifSatr = calculateMablaghTakhfifSatr(Integer.valueOf(row.getFiled3()), Long.valueOf(row.getFiled4()), takhfifHajmiSatr.getDarsadTakhfif());
                                            }
                                            Log.d("takhfifHajmi" , "mablaghTakhfifSatr : " + mablaghTakhfifSatr);
                                            if (mablaghTakhfifSatr > 0)
                                            {
                                                sumMablaghTalkhfifSatr += Math.round(mablaghTakhfifSatr);
                                                allMablaghTakhfifSatr += "," + Math.round(mablaghTakhfifSatr);
                                                insertFaktorSatrTakhfifHajmi(context,Long.valueOf(row.getFiled1()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(),
                                                        takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                                            }
                                        }
                                    }
                                    //بروز رسانی مبلغ تخفیف تیتر
                                    updateMablaghTakhfifDarkhastFaktor(context,darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), Math.round(mablaghTakhfif), sumMablaghTalkhfifSatr, allMablaghTakhfifSatr);
                                }
                            }
                            //-------------- تخفیفات جایزه پروموشن
                            else if(takhfifHajmiTitrSatrModel.getForJayezeh() == 2 )
                            {
                                long mablaghMandehTakhfifPasAzKasrPoromotion = calculateMablaghMandehTakhfifPasAzKasrPromotion(sumMablaghKolGorohKala, zarib, darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiSatr.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                                if (mablaghMandehTakhfifPasAzKasrPoromotion > 0)
                                {
                                    insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghMandehTakhfifPasAzKasrPoromotion, takhfifHajmiTitrSatrModel.getForJayezeh());
                                    long ccDarkhastFaktorSatr =  Long.valueOf(rowGorohKalas.get(0).getFiled1());
                                    insertFaktorSatrTakhfifHajmi(context, ccDarkhastFaktorSatr, codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghMandehTakhfifPasAzKasrPoromotion, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateHajmiDiscountGorohKala", "", "calculateDiscountTakidi", "");
            return false;
        }
    }


    public boolean calculatePelekani(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel)
    {
        double MianginDarsadTakhfif = 0;
        long SumMablaghTakhfif = 0;
        long MablaghTakhfifMiamgin = 0;

        if (takhfifHajmiTitrSatrModel.getIsPelekani() == 1)
        {
            double Mablaghmax = 0;
            long sumTedadGorohKala = 0;
            long sumTedadBastehGorohKala = 0;
            long sumTedadKartonGorohKala = 0;
            long sumMablaghKolGorohKala = 0;
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());

            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            KalaDAO kalaDAO = new KalaDAO(context);
            MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(context);
            TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);

            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
            boolean isMoshtaryJadid = selectFaktorShared.getBoolean(selectFaktorShared.getIsMoshtaryJadid(), false);

            // -----------------------------------------
            if (takhfifHajmiTitrSatrModel.getNoeTedadRial() != getTedadRialAghlam())
            {
                List<DataTableModel> gorohKalas = darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getOlaviat());
                List<DataTableModel> rowGorohKalas = darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi());

                for (DataTableModel gorohKala : gorohKalas)
                {
                    int ccGoroh = Integer.valueOf(gorohKala.getFiled1());
                    sumTedadGorohKala = Long.valueOf(gorohKala.getFiled2());
                    sumTedadBastehGorohKala = Long.valueOf(gorohKala.getFiled3());
                    sumTedadKartonGorohKala = Long.valueOf(gorohKala.getFiled4());
                    sumMablaghKolGorohKala = Math.round(Double.valueOf(gorohKala.getFiled5()));

                    // ------------------ ccBrand -------------------------------
                    int ccBrand = kalaDAO.getBrandByccKalaCode(darkhastFaktorModel.getCcDarkhastFaktor(), ccGoroh);
                    int DarajehBrandMoshtary = moshtaryRotbehDAO.getRotbehByccMoshtaryAndBrand(darkhastFaktorModel.getCcMoshtary(), ccBrand);
                    if (isMoshtaryJadid)
                    {
                        DarajehBrandMoshtary = selectFaktorShared.getInt(selectFaktorShared.getMoshtaryJadidDarajeh(), 4);
                    }

                    if (DarajehBrandMoshtary == takhfifHajmiTitrSatrModel.getDarajeh())
                    {
                        List<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getByccTakhfifHajmi(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi());
                        for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                        {
                            if (takhfifHajmiSatr.getCcNoeField() == ccGoroh)
                            {
                                //int Zarib = 0;
                                double MablaghBaghimanedehPele = 0;
                                double MablaghHarPele = 0;
                                double MablaghTakhfif = 0;
                                MablaghBaghimanedehPele = sumMablaghKolGorohKala - takhfifHajmiSatr.getTa();
                                //mosavi check shavad
                                if (MablaghBaghimanedehPele >= takhfifHajmiSatr.getBeEza())
                                {
                                    MablaghHarPele = takhfifHajmiSatr.getTa() - Mablaghmax;
                                    //Zarib = (int) (MablaghHarPele / takhfifHajmiSatr.getBeEza());
                                }
                                else
                                {
                                    MablaghHarPele = sumMablaghKolGorohKala - Mablaghmax;
                                    //Zarib = (int) (MablaghHarPele / takhfifHajmiSatr.getBeEza());
                                }

                                if (MablaghHarPele >= 0)
                                {
                                    MablaghTakhfif = (MablaghHarPele * takhfifHajmiSatr.getDarsadTakhfif() / 100);// (Zarib
                                }
                                if (MablaghTakhfif != 0)
                                {
                                    SumMablaghTakhfif += MablaghTakhfif;
                                }
                                if (takhfifHajmiSatr.getDarsadTakhfif() != 0)
                                {
                                    Mablaghmax = takhfifHajmiSatr.getTa();
                                }
                                if (MablaghBaghimanedehPele < takhfifHajmiSatr.getBeEza())
                                {
                                    break;
                                }
                            }
                        }

                        MianginDarsadTakhfif = SumMablaghTakhfif / sumMablaghKolGorohKala;
                        MablaghTakhfifMiamgin = Math.round(sumMablaghKolGorohKala * MianginDarsadTakhfif);

                        if (MablaghTakhfifMiamgin > 0)
                        {
                            insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                    takhfifHajmiTitrSatrModel.getSharhTakhfif(), MianginDarsadTakhfif * 100, MablaghTakhfifMiamgin, takhfifHajmiTitrSatrModel.getForJayezeh());

                            insertFaktorSatrTakhfifHajmi(context, Long.valueOf(gorohKala.getFiled1()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                    takhfifHajmiTitrSatrModel.getSharhTakhfif(), MianginDarsadTakhfif * 100, MablaghTakhfifMiamgin, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean calculateAghlam(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel)
    {
        try
        {
            long sumMablaghKol; // Sum Tedad Darkhast Bar Hasbe GorohKala..
            boolean isMoshtaryJadid = false;
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());

            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
            KalaDAO kalaDAO = new KalaDAO(context);
            MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            List<ParameterChildModel> parameterChildModels = new ParameterChildDAO(context).getAllByccParameter(Constants.CC_PARAMETER_GOROH_MOSHTARY());
            String ccGorohOmdeh = "";
            String ccGorohNamayandeh1 = "";
            String ccGorohNamayandeh2 = "";
            for (ParameterChildModel parameterChildModel : parameterChildModels)
            {
                if (parameterChildModel.getCcParameterChild() == Constants.CC_CHILD_GOROH_MOSHTARY_OMDE())
                {
                    ccGorohOmdeh = parameterChildModel.getValue().trim();
                }
                else if (parameterChildModel.getCcParameterChild() == Constants.CC_CHILD_GOROH_MOSHTARY_NAMAYANDE1())
                {
                    ccGorohNamayandeh1 = parameterChildModel.getValue().trim();
                }
                else if (parameterChildModel.getCcParameterChild() == Constants.CC_CHILD_GOROH_MOSHTARY_NAMAYANDE2())
                {
                    ccGorohNamayandeh2 = parameterChildModel.getValue().trim();
                }
            }

            isMoshtaryJadid = selectFaktorShared.getBoolean(selectFaktorShared.getIsMoshtaryJadid(), false);

            if (takhfifHajmiTitrSatrModel.getIsPelekani() == 0 && takhfifHajmiTitrSatrModel.getCcGorohTakidi()==0 && takhfifHajmiTitrSatrModel.getCcMantagheh() == 0)// check takhfif pelekani and gorohTakidi
            {
                double sumAghlamGorohKala = 0;
                // -----------------------------------------
                if (takhfifHajmiTitrSatrModel.getNoeTedadRial() == getTedadRialAghlam())
                {
                    List<DataTableModel> gorohKalas = null;

                    String ccGorohMoshtary = String.valueOf(new MoshtaryDAO(context).getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).getCcNoeMoshtary()).trim();
                    if (ccGorohMoshtary.equals(ccGorohOmdeh) || ccGorohMoshtary.equals(ccGorohNamayandeh1) || ccGorohMoshtary.equals(ccGorohNamayandeh2))
                    {
                        int ccGoroh = ccGorohMoshtary.equals(ccGorohOmdeh) ? 2 : 5;
                        gorohKalas = darkhastFaktorSatrDAO.getTedadAghlamBeTafkikGorohKalaAndTakhfifHajmiAndNoeMoshtary(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), ccGoroh);
                    }
                    else
                    {
                        gorohKalas = darkhastFaktorSatrDAO.getTedadAghlamBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi());
                    }

                    List<DataTableModel> rowGorohKalas = darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi());
                    for (DataTableModel gorohKala : gorohKalas)
                    {
                        sumAghlamGorohKala = Integer.valueOf(gorohKala.getFiled2());
                        sumMablaghKol = Math.round(Double.valueOf(gorohKala.getFiled3()));
                        double sumTedadGorohKala = 0;
                        double sumTedadBastehGorohKala = 0;
                        double sumTedadKartonGorohKala = 0;
                        double sumMablaghKolGorohKala = 0;
                        // ------------------ ccBrand ------------------
                        int ccBrand = kalaDAO.getBrandByccKalaCode(darkhastFaktorModel.getCcDarkhastFaktor(), Integer.valueOf(gorohKala.getFiled1()));
                        int DarajehBrandMoshtary = moshtaryRotbehDAO.getRotbehByccMoshtaryAndBrand(darkhastFaktorModel.getCcMoshtary(), ccBrand);
                        if (isMoshtaryJadid)
                        {
                            DarajehBrandMoshtary = selectFaktorShared.getInt(selectFaktorShared.getMoshtaryJadidDarajeh(), 4);
                        }

                        if (DarajehBrandMoshtary == takhfifHajmiTitrSatrModel.getDarajeh())
                        {
                            // Satrhaye Takhfif..
                            List<TakhfifHajmiSatrModel> takhfifHajmiSatrs = new TakhfifHajmiSatrDAO(context).getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                    new int[]{getTedadRialTedad(), getTedadRialRial()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                                    NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()), sumTedadGorohKala, sumTedadBastehGorohKala,
                                    sumTedadKartonGorohKala, sumMablaghKolGorohKala, takhfifHajmiTitrSatrModel.getNoeTedadRial(),0);
                            //TODO:VAZN
                            for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                            {
                                int zarib = 0;
                                if (takhfifHajmiSatr.getBeEza() == 0)
                                {
                                    zarib = 1;
                                }
                                if (takhfifHajmiTitrSatrModel.getNoeTedadRial() == getTedadRialAghlam())
                                {
                                    long mablaghTakhfif = 0;
                                    if (takhfifHajmiSatr.getAz() <= sumAghlamGorohKala && takhfifHajmiSatr.getTa() >= sumAghlamGorohKala)
                                    {
                                        mablaghTakhfif = Math.round(sumMablaghKol * (zarib * takhfifHajmiSatr.getDarsadTakhfif() / 100));
                                        if (mablaghTakhfif != 0)
                                        {
                                            insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());

                                            for (DataTableModel row : rowGorohKalas)
                                            {
                                                if (row.getFiled2().equals(gorohKala.getFiled1()))
                                                {
                                                    long mablaghTakhfifSatr = Math.round((Double.valueOf(row.getFiled3()) * Double.valueOf(row.getFiled4())) * (takhfifHajmiSatr.getDarsadTakhfif() / 100));
                                                    insertFaktorSatrTakhfifHajmi(context,Long.valueOf(row.getFiled1()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                                            takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "calculateAghlam", "");
            return false;
        }
    }

    public boolean calculateMantaghe(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel, int ccMantaghehMoshtary)
    {
        long sumTedadGorohKala = 0; // Sum Tedad Darkhast Bar Hasbe
        // GorohKala..
        long sumTedadBastehGorohKala = 0;
        long sumTedadKartonGorohKala = 0;
        long sumMablaghKolGorohKala = 0; // Sum Tedad Darkhast Bar Hasbe
        long sumAghlamGorohKala = 0;
        long sumVaznGorohKala = 0;
        String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());

        KalaDAO kalaDAO = new KalaDAO(context);
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);

        if (takhfifHajmiTitrSatrModel.getCcMantagheh() == ccMantaghehMoshtary)// check ccMantaghehTakhfif by ccMahaleh Moshtary
        {
            sumTedadGorohKala = 0;
            sumTedadBastehGorohKala = 0;
            sumTedadKartonGorohKala = 0;
            sumMablaghKolGorohKala = 0;
            sumAghlamGorohKala = 0;

            DecimalFormat twoDForm = new DecimalFormat("#.##");
            TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);
            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
            boolean isMoshtaryJadid = selectFaktorShared.getBoolean(selectFaktorShared.getIsMoshtaryJadid(), false);

            // -----------------------------------------
            if (takhfifHajmiTitrSatrModel.getNoeTedadRial() != getTedadRialAghlam())
            {
                List<DataTableModel> gorohKalas = darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getOlaviat());
                List<DataTableModel> rowGorohKalas = darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi());

                for (DataTableModel gorohKala : gorohKalas)
                {
                    sumTedadGorohKala = Long.valueOf(gorohKala.getFiled2());
                    sumTedadBastehGorohKala = Long.valueOf(gorohKala.getFiled3());
                    sumTedadKartonGorohKala = Long.valueOf(gorohKala.getFiled4());
                    sumMablaghKolGorohKala = Math.round(Double.valueOf(gorohKala.getFiled5()));
                    sumAghlamGorohKala = Integer.valueOf(gorohKala.getFiled6());
                    sumVaznGorohKala = Integer.valueOf(gorohKala.getFiled8());

                    // ------------------ ccBrand -------------------------------
                    int ccBrand = kalaDAO.getBrandByccKalaCode(darkhastFaktorModel.getCcDarkhastFaktor(), Integer.valueOf(gorohKala.getFiled1()));
                    MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(context);
                    int DarajehBrandMoshtary = moshtaryRotbehDAO.getRotbehByccMoshtaryAndBrand(darkhastFaktorModel.getCcMoshtary(), ccBrand);
                    if (isMoshtaryJadid)
                    {
                        DarajehBrandMoshtary = selectFaktorShared.getInt(selectFaktorShared.getMoshtaryJadidDarajeh(), 4);
                    }

                    if (DarajehBrandMoshtary == takhfifHajmiTitrSatrModel.getDarajeh())
                    {
                        // Set Tedad Karton For TakhfifKartoni
                        TakhfifHajmiSatrModel takhfifhajmiSatr = takhfifHajmiSatrDAO.getByccTakhfifHajmi(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi()).get(0);
                        if (takhfifhajmiSatr.getCodeNoeBastehBandy() == getBasteBandiCarton())
                        {
                            DataTableModel dataTableModel = darkhastFaktorSatrDAO.getTedadKartonByccGorohKala(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), Integer.valueOf(gorohKala.getFiled1())).get(0);
                            sumTedadGorohKala = 0;
                            sumTedadBastehGorohKala = 0;
                            sumTedadKartonGorohKala = Long.valueOf(dataTableModel.getFiled2());
                            sumTedadKartonGorohKala = Long.valueOf(twoDForm.format(sumTedadKartonGorohKala));
                        }
                        // Satrhaye Takhfif..
                        List<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                new int[]{getTedadRialTedad(), getTedadRialRial(),getTedadRialVazn()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                                NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()), sumTedadGorohKala, sumTedadBastehGorohKala,
                                sumTedadKartonGorohKala, sumMablaghKolGorohKala, takhfifHajmiTitrSatrModel.getNoeTedadRial(),0);
                        //Todo:Vazn
                        for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                        {
                            int zarib = calculateZarib(takhfifHajmiSatr.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(),
                                    sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala, sumMablaghKolGorohKala, (sumVaznGorohKala/1000.0));
                            if (takhfifhajmiSatr.getMinTedadAghlam() == 0 && takhfifhajmiSatr.getMinRial() == 0)
                            {
                                long mablaghTakhfif = Math.round(sumMablaghKolGorohKala * (zarib * takhfifHajmiSatr.getDarsadTakhfif() / 100));

                                if (mablaghTakhfif != 0)
                                {
                                    insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                            takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());

                                    for (DataTableModel row : rowGorohKalas)
                                    {
                                        if (row.getFiled2().equals(gorohKala.getFiled1()))
                                        {
                                            long mablaghTakhfifSatr = Math.round((Long.valueOf(row.getFiled3()) * Long.valueOf(row.getFiled4().toString())) * (takhfifHajmiSatr.getDarsadTakhfif() / 100));
                                            insertFaktorSatrTakhfifHajmi(context, Long.valueOf(row.getFiled1()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                                        }
                                    }
                                }
                            }
                            else if (takhfifhajmiSatr.getMinTedadAghlam() > 0 && takhfifhajmiSatr.getMinRial() == 0 && sumAghlamGorohKala >= takhfifhajmiSatr.getMinTedadAghlam())
                            {
                                long mablaghTakhfif = Math.round(sumMablaghKolGorohKala * (zarib * takhfifHajmiSatr.getDarsadTakhfif() / 100));
                                if (mablaghTakhfif != 0)
                                {
                                    insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                            takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());

                                    for (DataTableModel row : rowGorohKalas)
                                    {
                                        if (row.getFiled2().equals(gorohKala.getFiled1()))
                                        {
                                            long mablaghTakhfifSatr = Math.round((Long.valueOf(row.getFiled3()) * Long.valueOf(row.getFiled4())) * (takhfifHajmiSatr.getDarsadTakhfif() / 100));
                                            insertFaktorSatrTakhfifHajmi(context, Long.valueOf(row.getFiled1()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                                    takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                                        }
                                    }
                                }
                            }
                            else if (takhfifhajmiSatr.getMinTedadAghlam() == 0 && takhfifhajmiSatr.getMinRial() > 0 && sumMablaghKolGorohKala >= takhfifhajmiSatr.getMinRial())
                            {
                                long mablaghTakhfif = Math.round(sumMablaghKolGorohKala * (zarib * takhfifHajmiSatr.getDarsadTakhfif() / 100));
                                if (mablaghTakhfif != 0) {
                                    insertFaktorTakhfifHajmi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                            takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());

                                    for (DataTableModel row : rowGorohKalas)
                                    {
                                        if (row.getFiled2().equals(gorohKala.getFiled1()))
                                        {
                                            long mablaghTakhfifSatr = Math.round((Long.valueOf(row.getFiled3()) * Long.valueOf(row.getFiled4())) * (takhfifHajmiSatr.getDarsadTakhfif() / 100));
                                            insertFaktorSatrTakhfifHajmi(context, Long.valueOf(row.getFiled1()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(),
                                                    takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private long calculateMablaghMandehTakhfifPasAzKasrPromotion(long sumMablaghKolGorohKala, int zarib, long ccDarkhastFaktor, int ccTakhfifHajmi, double darsadTakhfif, int noeTedadRial)
    {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
        long mablaghTakhfifByGoroh = calculateMablaghTakhfif(sumMablaghKolGorohKala, 0, zarib, 0, darsadTakhfif, noeTedadRial);
        int ccGorohNoeMoshtary = selectFaktorShared.getInt(selectFaktorShared.getCcGorohNoeMoshtary() , -1);
        return setJayezehPromotionByGorohKala(ccTakhfifHajmi, ccGorohNoeMoshtary, mablaghTakhfifByGoroh, ccDarkhastFaktor);
    }

    private long setJayezehPromotionByGorohKala(int ccTakhfifHajmi, int ccGorohMoshtary, long MablaghTakhfifGorohKala, long ccDarkhastFaktor)
    {
        long MandehMablagh_TedadJayzeh = MablaghTakhfifGorohKala ;
        double MablaghForosh_KalaPromotion = 0;
        int TedadJayzehPromotion = 0;
        int TedadMojodyGhabelForosh = 0;

        JayezehEntekhabiDAO jayezehEntekhabiDAO = new JayezehEntekhabiDAO(context);
        JayezehEntekhabiModel jayezehEntekhabiModel = jayezehEntekhabiDAO.getKalaPromotionByccTakhfifHajmi(ccGorohMoshtary, ccTakhfifHajmi);

        MablaghForosh_KalaPromotion = jayezehEntekhabiModel.getMablaghForosh();
        String NameKala = jayezehEntekhabiModel.getNameKala();
        TedadJayzehPromotion = (int)( MablaghTakhfifGorohKala / MablaghForosh_KalaPromotion);

        //CheckTedadMojody.......
        KalaDAO kalaDAO = new KalaDAO(context);
        //Kala kala = kalaDao.GetByccKalaCode(jayzehEntekhabi.getccKalaCode());
        TedadMojodyGhabelForosh = kalaDAO.getSumTedadMojodyKala(jayezehEntekhabiModel.getCcKalaCode());

        if(MablaghForosh_KalaPromotion > 0 & TedadMojodyGhabelForosh > TedadJayzehPromotion )
        {
            //TedadJayzehPromotion = (int)( MablaghTakhfifGorohKala / MablaghForosh_KalaPromotion);
            MandehMablagh_TedadJayzeh= (int)Math.round(MablaghTakhfifGorohKala - (MablaghForosh_KalaPromotion * TedadJayzehPromotion)) ;

            if(TedadJayzehPromotion > 0 )
            {
                insertJayezeh(context, ccDarkhastFaktor, jayezehEntekhabiModel.getCcJayezeh(), DarkhastFaktorJayezehModel.CodeNoeJayezehAuto(), context.getResources().getString(R.string.jayezePromotion) + NameKala, jayezehEntekhabiModel.getCcKala(), jayezehEntekhabiModel.getCcKalaCode(), TedadJayzehPromotion);
            }
        }
        return MandehMablagh_TedadJayzeh;
    }





}
