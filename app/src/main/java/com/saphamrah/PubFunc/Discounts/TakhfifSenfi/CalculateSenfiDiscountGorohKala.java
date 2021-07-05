package com.saphamrah.PubFunc.Discounts.TakhfifSenfi;

import android.content.Context;
import android.util.Log;

import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.JayezehEntekhabiDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaGorohDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryRotbehDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.TakhfifSenfiSatrDAO;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.DataTableModel;
import com.saphamrah.Model.JayezehEntekhabiModel;
import com.saphamrah.Model.KalaDarkhastFaktorModel;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.TakhfifSenfiSatrModel;
import com.saphamrah.Model.TakhfifSenfiTitrSatrModel;
import com.saphamrah.PubFunc.Discounts.DiscountCalculation;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculateSenfiDiscountGorohKala extends DiscountCalculation
{

    private final static String CLASS_NAME = "CalculateSenfiDiscountGorohKala";
    private Context context;


    public CalculateSenfiDiscountGorohKala(Context context)
    {
        super(context);
        this.context = context;
    }


    public boolean calculateDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel)
    {
        try
        {
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());
            long sumTedadGorohKala = 0; // Sum Tedad Darkhast Bar Hasbe GorohKala..
            long sumTedadBastehGorohKala = 0;
            long sumTedadKartonGorohKala = 0;
            long sumMablaghForoshGorohKala = 0;// Sum MablaghForosh Darkhast Bar Hasbe GorohKala..
            long sumMablaghKolGorohKala = 0; // Sum MablaghKol Darkhast Bar Hasbe GorohKala..
            long sumVaznGorohkala=0;
            long  tedadAghlam=0;

            long sumTedadGorohKalaMohasebeh = 0; // Sum Tedad Darkhast Bar Hasbe GorohKala Mohasebeh..
            long sumTedadBastehGorohKalaMohasebeh = 0;
            long sumTedadKartonGorohKalaMohasebeh = 0;
            long sumMablaghForoshGorohKalaMohasebeh = 0;// Sum MablaghForosh Darkhast Bar Hasbe GorohKala Mohasebeh..
            long sumMablaghKolGorohKalaMohasebeh = 0; // Sum MablaghKol Darkhast Bar Hasbe GorohKala Mohasebeh..

            TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            MoshtaryModel moshtaryModel = new MoshtaryDAO(context).getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
            int currentOlaviat = takhfifSenfiTitrSatrModel.getOlaviat();
            ArrayList<DataTableModel> gorohKalas= darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), currentOlaviat, takhfifSenfiTitrSatrModel.getNoeGheymat());
            ArrayList<DataTableModel> rowGorohKalas= darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),takhfifSenfiTitrSatrModel.getNoeGheymat());
            Log.d("takhfifSenfi" , "takhfifSenfiTitrSatrModel1 : " + takhfifSenfiTitrSatrModel.toString());
            Log.d("takhfifSenfi" , "gorohKalas : " + gorohKalas.toString());
            Log.d("takhfifSenfi" , "rowGorohKalas : " + rowGorohKalas.toString());

            for (DataTableModel gorohKala : gorohKalas)
            {
                Log.d("takhfifSenfi","gorohKala.getFiled5: " +Math.round(Double.valueOf(gorohKala.getFiled5())));
                Log.d("takhfifSenfi","gorohKala.getFiled8: " +Math.round(Double.valueOf(gorohKala.getFiled8())));
                Log.d("takhfifSenfi","gorohKala.getFiled9: " +Math.round(Double.valueOf(gorohKala.getFiled9())));
                sumTedadGorohKala = Math.round(Double.valueOf(gorohKala.getFiled2()));
                sumTedadBastehGorohKala = Math.round(Double.valueOf(gorohKala.getFiled3()));
                sumTedadKartonGorohKala = Math.round(Double.valueOf(gorohKala.getFiled4()));
                sumMablaghKolGorohKala = Math.round(Double.valueOf(gorohKala.getFiled5()));
                sumVaznGorohkala = Math.round(Double.valueOf(gorohKala.getFiled8()));
                tedadAghlam = Math.round(Double.valueOf(gorohKala.getFiled9()));



                int darajehBrandMoshtary = moshtaryModel.getDarajeh();

                Log.d("takhfifSenfi" , "sumMablaghKolGorohKala : " + sumMablaghKolGorohKala);

                if (darajehBrandMoshtary == takhfifSenfiTitrSatrModel.getDarajeh() || takhfifSenfiTitrSatrModel.getDarajeh()==0)
                {
                    TakhfifSenfiSatrModel takhfifSenfiSatrModel = takhfifSenfiSatrDAO.getByccTakhfifSenfi(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi() , "1").get(0);
                    Log.d("takhfifSenfi" , "takhfifSenfiSatr1 : " + takhfifSenfiSatrModel.toString());
                    if(takhfifSenfiSatrModel.getCodeNoeBastehBandy() == getBasteBandiBaste())
                    {
                        ArrayList<DataTableModel> gorohs = darkhastFaktorSatrDAO.getTedadKartonByccGorohKala(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), Integer.valueOf(gorohKala.getFiled1()), takhfifSenfiTitrSatrModel.getOlaviat());
                        sumTedadGorohKala= Math.round(Double.valueOf(gorohs.get(0).getFiled4()));
                        sumTedadBastehGorohKala = Math.round(Double.valueOf(gorohs.get(0).getFiled3()));
                        sumTedadKartonGorohKala = Math.round(Double.valueOf(gorohs.get(0).getFiled2()));
                    }
                    else if (takhfifSenfiSatrModel.getCodeNoeBastehBandy() == getBasteBandiCarton())
                    {
                        ArrayList<DataTableModel> gorohs = darkhastFaktorSatrDAO.getTedadKartonByccGorohKala(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), Integer.valueOf(gorohKala.getFiled1()), takhfifSenfiTitrSatrModel.getOlaviat());
                        sumTedadGorohKala= Math.round(Double.valueOf(gorohs.get(0).getFiled4()));
                        sumTedadBastehGorohKala = Math.round(Double.valueOf(gorohs.get(0).getFiled3()));
                        sumTedadKartonGorohKala = Math.round(Double.valueOf(gorohs.get(0).getFiled2()));
                    }

                    Log.d("takhfifSenfi" , "takhfifSenfiSatrs : before" );
                    ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi() ,
                            new int[]{getTedadRialTedad(), getTedadRialRial(),getTedadRialVazn(),getTedadRialAghlam()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                            NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()), sumTedadGorohKala, sumTedadBastehGorohKala, sumTedadKartonGorohKala,
                            sumMablaghKolGorohKala, takhfifSenfiTitrSatrModel.getNoeTedadRial(),(sumVaznGorohkala/1000.0));
                    //TODO : VAZN
                    Log.d("takhfifSenfi" , "takhfifSenfiSatrs : " + takhfifSenfiSatrs.toString());
                    for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs)
                    {
                        ArrayList<DataTableModel> gorohKalaMohasebehs= darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaMohasebehAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiSatr.getCcTakhfifSenfiSatr(), takhfifSenfiSatr.getCcGorohMohasebeh(), takhfifSenfiTitrSatrModel.getOlaviat(), takhfifSenfiTitrSatrModel.getNoeGheymat());
                        ArrayList<DataTableModel> rowGorohKalaMohasebehs= darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaMohasebehAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getOlaviat(),takhfifSenfiTitrSatrModel.getNoeGheymat());

                        Log.d("takhfifSenfi" , "takhfifSenfiSatr : " + takhfifSenfiSatr.toString());
                        Log.d("takhfifSenfi" , "gorohKalaMohasebehs : " + gorohKalaMohasebehs.toString());
                        Log.d("takhfifSenfi" , "rowGorohKalaMohasebehs : " + rowGorohKalaMohasebehs.toString());

                        if(gorohKalaMohasebehs.size()>0)
                        {
                            sumTedadGorohKalaMohasebeh = Math.round(Double.valueOf(gorohKalaMohasebehs.get(0).getFiled2()));
                            sumTedadBastehGorohKalaMohasebeh = Math.round(Double.valueOf(gorohKalaMohasebehs.get(0).getFiled3()));
                            sumTedadKartonGorohKalaMohasebeh = Math.round(Double.valueOf(gorohKalaMohasebehs.get(0).getFiled4()));
                            sumMablaghKolGorohKalaMohasebeh = Math.round(Double.valueOf(gorohKalaMohasebehs.get(0).getFiled5()));
                        }

                        Log.d("takhfifSenfi" , "sumTedadGorohKalaMohasebeh : " + sumTedadGorohKalaMohasebeh);
                        Log.d("takhfifSenfi" , "sumTedadBastehGorohKalaMohasebeh : " + sumTedadBastehGorohKalaMohasebeh);
                        Log.d("takhfifSenfi" , "sumTedadKartonGorohKalaMohasebeh : " + sumTedadKartonGorohKalaMohasebeh);
                        Log.d("takhfifSenfi" , "sumMablaghKolGorohKalaMohasebeh : " + sumMablaghKolGorohKalaMohasebeh);

                        long Mablagh=0;
                        long MablaghVahed=0;
                        int zarib = 0;
                        int Tedad = 0;
                        //zarib = calculateZarib(takhfifSenfiSatr.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala, sumMablaghKolGorohKala);
                        zarib = calculateZarib(takhfifSenfiSatr.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala, sumMablaghKolGorohKala, (sumVaznGorohkala/1000.0), tedadAghlam);
                        //Tedad = calculateTedad(takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala);
                        Tedad = calculateTedad(takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKalaMohasebeh, sumTedadBastehGorohKalaMohasebeh, sumTedadGorohKalaMohasebeh);
                        Log.d("takhfifSenfi" , "tedad : " + Tedad + " , zarib : " + zarib + " , sumMablaghKolGorohKala : " + sumMablaghKolGorohKala);

                        //if(Tedad>0)
                        MablaghVahed = calculateMablaghVahed(takhfifSenfiTitrSatrModel.getNoeTedadRial(), Tedad, sumVaznGorohkala, sumMablaghKolGorohKalaMohasebeh, tedadAghlam);//Math.round(sumMablaghKolGorohKalaMohasebeh/Tedad);

                        Mablagh =  calculateMablagh(takhfifSenfiTitrSatrModel.getNoeTedadRial(), (int) takhfifSenfiSatr.getBeEza(), (long) sumMablaghKolGorohKalaMohasebeh, MablaghVahed, takhfifSenfiSatr.getCcNoeField(), takhfifSenfiSatr.getCcGorohMohasebeh());

                        if (takhfifSenfiTitrSatrModel.getForJayezeh() != 2)
                        {
                            Log.d("takhfifSenfi" , "mablaghVahed : " + MablaghVahed);
                            Log.d("takhfifSenfi" , "Mablagh : " + Mablagh);
                            Log.d("takhfifSenfi" , "zarib : " + zarib);
                            Log.d("takhfifSenfi" , "takhfifSenfiSatr.getBeEza() : " + takhfifSenfiSatr.getBeEza());
                            Log.d("takhfifSenfi" , "Tedad : " + Tedad);
                            Log.d("takhfifSenfi" , "(takhfifSenfiSatr.getBeEza()*Tedad) : " + (takhfifSenfiSatr.getBeEza()*Tedad));
                            //double mablaghTakhfif = calculateMablaghTakhfif(sumMablaghKolGorohKala, MablaghVahed, zarib, takhfifSenfiSatr.getBeEza(), takhfifSenfiSatr.getDarsadTakhfif(), takhfifSenfiTitrSatrModel.getNoeTedadRial());
                            long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, MablaghVahed, zarib, takhfifSenfiSatr.getBeEza(), takhfifSenfiSatr.getDarsadTakhfif(), takhfifSenfiTitrSatrModel.getNoeTedadRial());
                            Log.d("takhfifSenfi","after mablaghTakhfif" + mablaghTakhfif);
                            if (mablaghTakhfif > 0)
                            {
                                //insertFaktorTakhfifSenfi(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());
                                long sumMablaghTalkhfifSatr = 0;
                                // تعداد کالاهایی که در گروه کالا وجود دارد برای محاسبه آخرین مرحله تخفیف سطر
                                int countKalaExistInGoroh = 0;
                                Log.d("takhfifSenfi","takhfifSenfisatr-Before" + rowGorohKalaMohasebehs.toString());

                                for (int i=0 ; i < rowGorohKalaMohasebehs.size() ; i++)
                                {
                                    Log.d("takhfifSenfi","rowGorohKalaMohasebehs.get(i).getFiled2():"+rowGorohKalaMohasebehs.get(i).getFiled2()+" ,gorohKala.getFiled1():"+gorohKala.getFiled1());
                                    if (rowGorohKalaMohasebehs.get(i).getFiled2().equals(gorohKalaMohasebehs.get(0).getFiled1()))
                                    {
                                        countKalaExistInGoroh++;
                                        Log.d("takhfifSenfi","takhfifSenfisatr-countKalaExistInGoroh" + countKalaExistInGoroh);
                                    }
                                }
                                int j = 0;
                                for (int i=0 ; i < rowGorohKalaMohasebehs.size() ; i++)
                                {
                                    DataTableModel row = rowGorohKalaMohasebehs.get(i);
                                    Log.d("takhfifSenfi","1 .1 takhfifSenfisatr-row: " + row.getFiled1() + " , "+ row.getFiled2() + " , "+ row.getFiled3() + " , "+ row.getFiled4() );

                                    Log.d("takhfifSenfi","takhfifSenfisatr-row.getFiled2(): "+ row.getFiled2() + " ,gorohKalaMohasebehs.get(0).getFiled1(): "+ gorohKalaMohasebehs.get(0).getFiled1());
                                    if (row.getFiled2().equals(gorohKalaMohasebehs.get(0).getFiled1()))
                                    {
                                        j++;
                                        long mablaghTakhfifSatr = 0;
                                        double mablaghTakhfifVahed = 0;
                                        Log.d("takhfifSenfi","takhfifSenfisatr-mablaghTakhfif"+mablaghTakhfif+", tedad"+sumTedadGorohKalaMohasebeh+ "-- " +
                                                (double) mablaghTakhfif/(double) sumTedadGorohKalaMohasebeh+"    " +takhfifSenfiTitrSatrModel.getCcTakhfifSenfi());
                                        // mablaghTakhfifVahed = (double) mablaghTakhfif/ (double) sumTedadGorohKalaMohasebeh;
                                        Log.d("takhfifSenfi" , "takhfifSenfisatr-noetedadrial : " + takhfifSenfiTitrSatrModel.getNoeTedadRial());
//                                        if (takhfifSenfiTitrSatrModel.getNoeTedadRial() == getTedadRialTedad())
//                                        {
//                                            mablaghTakhfifSatr = Math.round(Long.valueOf(row.getFiled4())* mablaghTakhfifVahed) ;
//                                            Log.d("takhfifSenfi" , "takhfifSenfisatr-mablaghTakhfif : " + mablaghTakhfif + " , Tedad : " + Tedad);
//                                            Log.d("takhfifSenfi" , "takhfifSenfisatr-row.getFiled4() : " + row.getFiled4() + " , mablaghTakhfifVahed : " + mablaghTakhfifVahed);
//                                            Log.d("takhfifSenfi" , "mablaghTakhfifSatr : " + mablaghTakhfifSatr);
//                                        }
//                                        else
//                                        {
//                                            mablaghTakhfifSatr = Math.round(Long.valueOf(row.getFiled4())* mablaghTakhfifVahed) ;
//                                            Log.d("takhfifSenfi" , "mablaghTakhfifSatr : " + mablaghTakhfifSatr + " , mablaghTakhfifVahed : " + mablaghTakhfifVahed + " , row.getFiled4() : " + row.getFiled4());
//                                        }
                                        Log.d("takhfifSenfi", "1.4 f3: " + Double.parseDouble(row.getFiled3()) + " , f4:" + Double.parseDouble(row.getFiled4()) + " , f5: " + Double.parseDouble(row.getFiled5()) + " , darsad:" +takhfifSenfiSatr.getDarsadTakhfif());
                                        mablaghTakhfifSatr = Math.round((((Double.parseDouble(row.getFiled3())*Double.parseDouble(row.getFiled4()))-Double.parseDouble(row.getFiled5()))*takhfifSenfiSatr.getDarsadTakhfif())/100);
                                        if (mablaghTakhfifSatr > 0)
                                        {
                                            Log.d("takhfifSenfi" , " 1.5 sumMablaghTakhfifSatr : " + mablaghTakhfifSatr + " , " + Math.round(mablaghTakhfifSatr)+ " , mablaghTakhfif: " +mablaghTakhfif + " , sumMablaghTalkhfifSatr: "+sumMablaghTalkhfifSatr );
                                            Log.d("takhfifSenfi" , " j  : " + j + " , rowKala.Size : " + rowGorohKalas.size() + " , rowGorohKalas.size()-1 : " + (rowGorohKalas.size()-1) + " , countKalaExistInGoroh : " + countKalaExistInGoroh);
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
                                                Log.d("takhfifSenfi" , " 1.6 takhfifSenfiTitrSatrModel CcTakhfifSenfi: " + takhfifSenfiTitrSatrModel.getCcTakhfifSenfi() +" mablaghTakhfifSatr : " + mablaghTakhfifSatr + " , mablaghTakhfif: " + mablaghTakhfif + " , sumMablaghTalkhfifSatr: " + sumMablaghTalkhfifSatr);
                                                insertFaktorSatrTakhfif(context, Long.valueOf(row.getFiled1()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                                            }
                                        }
                                    }
                                }
                                Log.d("takhfifSenfi" , " 1.7 takhfifSenfiTitrSatrModel CcTakhfifSenfi: " + takhfifSenfiTitrSatrModel.getCcTakhfifSenfi()  + " , sumMablaghTalkhfifSatr: " + sumMablaghTalkhfifSatr);
                                mablaghTakhfif = sumMablaghTalkhfifSatr;
                                insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());
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
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateSenfiDiscountGorohKala", "", "calculateDiscount", "");
            return false;
        }
    }

    public boolean calculateDiscountTakidi(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel)
    {
        try
        {
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());
            long sumTedadGorohKala= 0;
            long sumTedadBastehGorohKala= 0;
            long sumTedadKartonGorohKala= 0;
            long sumMablaghKolGorohKala= 0;
            long sumVaznGorohKala = 0;
            int tedadKalaTakidi = 0;
            int  tedadAghlam=0;
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            KalaGorohDAO kalaGorohDAO = new KalaGorohDAO(context);
            ArrayList<KalaGorohModel> kalaGorohModels = kalaGorohDAO.getByccGoroh(takhfifSenfiTitrSatrModel.getCcGorohTakidi());
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
                TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);
                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(context);
                MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
                ArrayList<DataTableModel> gorohKalas = darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),takhfifSenfiTitrSatrModel.getOlaviat(),takhfifSenfiTitrSatrModel.getNoeGheymat());
                ArrayList<DataTableModel> rowGorohKalas = darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),takhfifSenfiTitrSatrModel.getNoeGheymat());

                for (DataTableModel gorohKala : gorohKalas)
                {
                    sumTedadGorohKala= Long.valueOf(gorohKala.getFiled2());
                    sumTedadBastehGorohKala = Long.valueOf(gorohKala.getFiled3());
                    sumTedadKartonGorohKala = Long.valueOf(gorohKala.getFiled4());
                    sumMablaghKolGorohKala = Math.round(Double.valueOf(gorohKala.getFiled5()));
                    sumVaznGorohKala = Long.valueOf(gorohKala.getFiled8());
                    tedadAghlam = Integer.valueOf(gorohKala.getFiled9());

                    int darajehBrandMoshtary = moshtaryModel.getDarajeh();

                    if(darajehBrandMoshtary == takhfifSenfiTitrSatrModel.getDarajeh())
                    {
                        TakhfifSenfiSatrModel takhfifSenfiSatrModel = takhfifSenfiSatrDAO.getByccTakhfifSenfi(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi() , "1").get(0);
                        if(takhfifSenfiSatrModel.getCodeNoeBastehBandy() == getBasteBandiCarton())
                        {
                            ArrayList<DataTableModel> gorohs = darkhastFaktorSatrDAO.getTedadKartonByccGorohKala(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), Integer.valueOf(gorohKala.getFiled1()), takhfifSenfiTitrSatrModel.getOlaviat());
                            sumTedadGorohKala= Long.valueOf(gorohs.get(0).getFiled4());
                            sumTedadBastehGorohKala = Long.valueOf(gorohs.get(0).getFiled3());
                            sumTedadKartonGorohKala = Long.valueOf(gorohs.get(0).getFiled2());
                        }
                        //Satrhaye Takhfif..
                        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi() ,
                                new int[]{getTedadRialTedad(),getTedadRialRial(),getTedadRialVazn(),getTedadRialAghlam()}, new int[]{getBasteBandiCarton(),getBasteBandiBaste(),getBasteBandiAdad()},
                                NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()), sumTedadGorohKala, sumTedadBastehGorohKala,
                                sumTedadKartonGorohKala, sumMablaghKolGorohKala, takhfifSenfiTitrSatrModel.getNoeTedadRial(),0);
                        //TODO:Vazn
                        for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs)
                        {
                            int zarib = calculateZarib(takhfifSenfiSatr.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala, sumMablaghKolGorohKala, (sumVaznGorohKala/1000.0),tedadAghlam);
                            int Tedad = calculateTedad(takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(), sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala);
                            int MablaghVahed = 0;
                            if(Tedad>0)
                                MablaghVahed = (int)(sumMablaghKolGorohKala/Tedad);

                            if (takhfifSenfiTitrSatrModel.getForJayezeh() != 2)
                            {
                                long mablaghTakhfif = calculateMablaghTakhfif(sumMablaghKolGorohKala, MablaghVahed, zarib, takhfifSenfiSatr.getBeEza(), takhfifSenfiSatr.getDarsadTakhfif(), takhfifSenfiTitrSatrModel.getNoeTedadRial());
                                if (mablaghTakhfif > 0)
                                {
                                    insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(),
                                            takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());

                                    long sumMablaghTalkhfifSatr = 0;
                                    String allMablaghTakhfifSatr = "-1";
                                    for (DataTableModel row : rowGorohKalas)
                                    {
                                        if (row.getFiled2().equals(gorohKala.getFiled1()))
                                        {
                                            long mablaghTakhfifSatr = 0;
                                            double mablaghTakhfifVahed = 0;
                                            if (takhfifSenfiTitrSatrModel.getNoeTedadRial() == getTedadRialTedad())
                                            {
                                                Log.d("takhfifSenfi" , "mablaghTakhfif : " + mablaghTakhfif + " , tedad : " + Tedad);
                                                Log.d("takhfifSenfi" , "field4 : " + row.getFiled4() + " , " + mablaghTakhfifVahed);
                                                mablaghTakhfifVahed = (double)mablaghTakhfif/(double)Tedad;
                                                mablaghTakhfifSatr = Math.round(Double.valueOf(row.getFiled4())* mablaghTakhfifVahed);
                                                Log.d("takhfifSenfi" , "mablaghTakhfifSatr : " + mablaghTakhfifSatr);
                                            }
                                            else
                                            {
                                                Log.d("takhfifSenfi" , "field3 : " + row.getFiled3() + " , row.getFiled4() : " + row.getFiled4() + " , darsad : " + takhfifSenfiSatr.getDarsadTakhfif());
                                                mablaghTakhfifSatr = calculateMablaghTakhfifSatr(Integer.valueOf(row.getFiled3()), Long.valueOf(row.getFiled4()), takhfifSenfiSatr.getDarsadTakhfif());
                                            }
                                            Log.d("takhfifSenfi" , "mablaghTakhfifSatr : " + mablaghTakhfifSatr);
                                            if (mablaghTakhfifSatr > 0)
                                            {
                                                sumMablaghTalkhfifSatr += Math.round(mablaghTakhfifSatr);
                                                allMablaghTakhfifSatr += "," + Math.round(mablaghTakhfifSatr);
                                                insertFaktorSatrTakhfif(context,Long.valueOf(row.getFiled1()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(),
                                                        takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                                            }
                                        }
                                    }
                                    //بروز رسانی مبلغ تخفیف تیتر
                                    updateMablaghTakhfifDarkhastFaktor(context,darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), Math.round(mablaghTakhfif), sumMablaghTalkhfifSatr, allMablaghTakhfifSatr);
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
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateSenfiDiscountGorohKala", "", "calculateDiscountTakidi", "");
            return false;
        }
    }


    public boolean calculatePelekani(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel)
    {
        double MianginDarsadTakhfif = 0;
        long SumMablaghTakhfif = 0;
        long MablaghTakhfifMiamgin = 0;

        if (takhfifSenfiTitrSatrModel.getIsPelekani() == 1)
        {
            double Mablaghmax = 0;
            long sumTedadGorohKala = 0;
            long sumTedadBastehGorohKala = 0;
            long sumTedadKartonGorohKala = 0;
            long sumMablaghKolGorohKala = 0;
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());

            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            KalaDAO kalaDAO = new KalaDAO(context);
            MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(context);
            TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);

            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
            boolean isMoshtaryJadid = selectFaktorShared.getBoolean(selectFaktorShared.getIsMoshtaryJadid(), false);

            // -----------------------------------------
            if (takhfifSenfiTitrSatrModel.getNoeTedadRial() != getTedadRialAghlam())
            {
                List<DataTableModel> gorohKalas = darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getOlaviat(),takhfifSenfiTitrSatrModel.getNoeGheymat());
                List<DataTableModel> rowGorohKalas = darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),takhfifSenfiTitrSatrModel.getNoeGheymat());

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

                    if (DarajehBrandMoshtary == takhfifSenfiTitrSatrModel.getDarajeh())
                    {
                        List<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getByccTakhfifSenfi(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi());
                        for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs)
                        {
                            if (takhfifSenfiSatr.getCcNoeField() == ccGoroh)
                            {
                                //int Zarib = 0;
                                double MablaghBaghimanedehPele = 0;
                                double MablaghHarPele = 0;
                                double MablaghTakhfif = 0;
                                MablaghBaghimanedehPele = sumMablaghKolGorohKala - takhfifSenfiSatr.getTa();
                                //mosavi check shavad
                                if (MablaghBaghimanedehPele >= takhfifSenfiSatr.getBeEza())
                                {
                                    MablaghHarPele = takhfifSenfiSatr.getTa() - Mablaghmax;
                                    //Zarib = (int) (MablaghHarPele / takhfifSenfiSatr.getBeEza());
                                }
                                else
                                {
                                    MablaghHarPele = sumMablaghKolGorohKala - Mablaghmax;
                                    //Zarib = (int) (MablaghHarPele / takhfifSenfiSatr.getBeEza());
                                }

                                if (MablaghHarPele >= 0)
                                {
                                    MablaghTakhfif = (MablaghHarPele * takhfifSenfiSatr.getDarsadTakhfif() / 100);// (Zarib
                                }
                                if (MablaghTakhfif != 0)
                                {
                                    SumMablaghTakhfif += MablaghTakhfif;
                                }
                                if (takhfifSenfiSatr.getDarsadTakhfif() != 0)
                                {
                                    Mablaghmax = takhfifSenfiSatr.getTa();
                                }
                                if (MablaghBaghimanedehPele < takhfifSenfiSatr.getBeEza())
                                {
                                    break;
                                }
                            }
                        }

                        MianginDarsadTakhfif = SumMablaghTakhfif / sumMablaghKolGorohKala;
                        MablaghTakhfifMiamgin = Math.round(sumMablaghKolGorohKala * MianginDarsadTakhfif);

                        if (MablaghTakhfifMiamgin > 0)
                        {
                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                    takhfifSenfiTitrSatrModel.getSharhTakhfif(), MianginDarsadTakhfif * 100, MablaghTakhfifMiamgin, takhfifSenfiTitrSatrModel.getForJayezeh());

                            insertFaktorSatrTakhfif(context, Long.valueOf(gorohKala.getFiled1()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                    takhfifSenfiTitrSatrModel.getSharhTakhfif(), MianginDarsadTakhfif * 100, MablaghTakhfifMiamgin, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean calculateAghlam(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel)
    {
        try
        {
            long sumMablaghKol; // Sum Tedad Darkhast Bar Hasbe GorohKala..
            boolean isMoshtaryJadid = false;
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());

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

            if (takhfifSenfiTitrSatrModel.getIsPelekani() == 0 && takhfifSenfiTitrSatrModel.getCcGorohTakidi()==0 && takhfifSenfiTitrSatrModel.getCcMantagheh() == 0)// check takhfif pelekani and gorohTakidi
            {
                double sumAghlamGorohKala = 0;
                // -----------------------------------------
                if (takhfifSenfiTitrSatrModel.getNoeTedadRial() == getTedadRialAghlam())
                {
                    List<DataTableModel> gorohKalas = null;

                    String ccGorohMoshtary = String.valueOf(new MoshtaryDAO(context).getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).getCcNoeMoshtary()).trim();
                    if (ccGorohMoshtary.equals(ccGorohOmdeh) || ccGorohMoshtary.equals(ccGorohNamayandeh1) || ccGorohMoshtary.equals(ccGorohNamayandeh2))
                    {
                        int ccGoroh = ccGorohMoshtary.equals(ccGorohOmdeh) ? 2 : 5;
                        gorohKalas = darkhastFaktorSatrDAO.getTedadAghlamBeTafkikGorohKalaAndTakhfifSenfiAndNoeMoshtary(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), ccGoroh);
                    }
                    else
                    {
                        gorohKalas = darkhastFaktorSatrDAO.getTedadAghlamBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi());
                    }

                    List<DataTableModel> rowGorohKalas = darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),takhfifSenfiTitrSatrModel.getNoeGheymat());
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

                        if (DarajehBrandMoshtary == takhfifSenfiTitrSatrModel.getDarajeh())
                        {
                            // Satrhaye Takhfif..
                            List<TakhfifSenfiSatrModel> takhfifSenfiSatrs = new TakhfifSenfiSatrDAO(context).getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                    new int[]{getTedadRialTedad(), getTedadRialRial()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                                    NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()), sumTedadGorohKala, sumTedadBastehGorohKala,
                                    sumTedadKartonGorohKala, sumMablaghKolGorohKala, takhfifSenfiTitrSatrModel.getNoeTedadRial(),0);
                            //TODO:VAZN
                            for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs)
                            {
                                int zarib = 0;
                                if (takhfifSenfiSatr.getBeEza() == 0)
                                {
                                    zarib = 1;
                                }
                                if (takhfifSenfiTitrSatrModel.getNoeTedadRial() == getTedadRialAghlam())
                                {
                                    long mablaghTakhfif = 0;
                                    if (takhfifSenfiSatr.getAz() <= sumAghlamGorohKala && takhfifSenfiSatr.getTa() >= sumAghlamGorohKala)
                                    {
                                        mablaghTakhfif = Math.round(sumMablaghKol * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100));
                                        if (mablaghTakhfif != 0)
                                        {
                                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());

                                            for (DataTableModel row : rowGorohKalas)
                                            {
                                                if (row.getFiled2().equals(gorohKala.getFiled1()))
                                                {
                                                    long mablaghTakhfifSatr = Math.round((Double.valueOf(row.getFiled3()) * Double.valueOf(row.getFiled4())) * (takhfifSenfiSatr.getDarsadTakhfif() / 100));
                                                    insertFaktorSatrTakhfif(context,Long.valueOf(row.getFiled1()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                                            takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
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

    public boolean calculateMantaghe(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel, int ccMantaghehMoshtary)
    {
        long sumTedadGorohKala = 0; // Sum Tedad Darkhast Bar Hasbe
        // GorohKala..
        long sumTedadBastehGorohKala = 0;
        long sumTedadKartonGorohKala = 0;
        long sumMablaghKolGorohKala = 0; // Sum Tedad Darkhast Bar Hasbe
        long sumAghlamGorohKala = 0;
        long sumVaznGorohKala = 0;
        int tedadAghlam=0;
        String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());

        KalaDAO kalaDAO = new KalaDAO(context);
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);

        if (takhfifSenfiTitrSatrModel.getCcMantagheh() == ccMantaghehMoshtary)// check ccMantaghehTakhfif by ccMahaleh Moshtary
        {
            sumTedadGorohKala = 0;
            sumTedadBastehGorohKala = 0;
            sumTedadKartonGorohKala = 0;
            sumMablaghKolGorohKala = 0;
            sumAghlamGorohKala = 0;

            DecimalFormat twoDForm = new DecimalFormat("#.##");
            TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);
            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(context);
            boolean isMoshtaryJadid = selectFaktorShared.getBoolean(selectFaktorShared.getIsMoshtaryJadid(), false);

            // -----------------------------------------
            if (takhfifSenfiTitrSatrModel.getNoeTedadRial() != getTedadRialAghlam())
            {
                List<DataTableModel> gorohKalas = darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getOlaviat(),takhfifSenfiTitrSatrModel.getNoeGheymat());
                List<DataTableModel> rowGorohKalas = darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),takhfifSenfiTitrSatrModel.getNoeGheymat());

                for (DataTableModel gorohKala : gorohKalas)
                {
                    sumTedadGorohKala = Long.valueOf(gorohKala.getFiled2());
                    sumTedadBastehGorohKala = Long.valueOf(gorohKala.getFiled3());
                    sumTedadKartonGorohKala = Long.valueOf(gorohKala.getFiled4());
                    sumMablaghKolGorohKala = Math.round(Double.valueOf(gorohKala.getFiled5()));
                    sumAghlamGorohKala = Integer.valueOf(gorohKala.getFiled6());
                    sumVaznGorohKala = Integer.valueOf(gorohKala.getFiled8());
                    tedadAghlam = Integer.valueOf(gorohKala.getFiled9());

                    // ------------------ ccBrand -------------------------------
                    int ccBrand = kalaDAO.getBrandByccKalaCode(darkhastFaktorModel.getCcDarkhastFaktor(), Integer.valueOf(gorohKala.getFiled1()));
                    MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(context);
                    int DarajehBrandMoshtary = moshtaryRotbehDAO.getRotbehByccMoshtaryAndBrand(darkhastFaktorModel.getCcMoshtary(), ccBrand);
                    if (isMoshtaryJadid)
                    {
                        DarajehBrandMoshtary = selectFaktorShared.getInt(selectFaktorShared.getMoshtaryJadidDarajeh(), 4);
                    }

                    if (DarajehBrandMoshtary == takhfifSenfiTitrSatrModel.getDarajeh())
                    {
                        // Set Tedad Karton For TakhfifKartoni
                        TakhfifSenfiSatrModel takhfifSenfiSatrModel = takhfifSenfiSatrDAO.getByccTakhfifSenfi(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi()).get(0);
                        if (takhfifSenfiSatrModel.getCodeNoeBastehBandy() == getBasteBandiCarton())
                        {
                            DataTableModel dataTableModel = darkhastFaktorSatrDAO.getTedadKartonByccGorohKala(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), Integer.valueOf(gorohKala.getFiled1())).get(0);
                            sumTedadGorohKala = 0;
                            sumTedadBastehGorohKala = 0;
                            sumTedadKartonGorohKala = Long.valueOf(dataTableModel.getFiled2());
                            sumTedadKartonGorohKala = Long.valueOf(twoDForm.format(sumTedadKartonGorohKala));
                        }
                        // Satrhaye Takhfif..
                        List<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                new int[]{getTedadRialTedad(), getTedadRialRial(),getTedadRialVazn(),getTedadRialAghlam()}, new int[]{getBasteBandiCarton(), getBasteBandiBaste(), getBasteBandiAdad()},
                                NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()), sumTedadGorohKala, sumTedadBastehGorohKala,
                                sumTedadKartonGorohKala, sumMablaghKolGorohKala, takhfifSenfiTitrSatrModel.getNoeTedadRial(),0);
                        //Todo:Vazn
                        for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs)
                        {
                            int zarib = calculateZarib(takhfifSenfiSatr.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(),
                                    sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumTedadGorohKala, sumMablaghKolGorohKala, (sumVaznGorohKala/1000.0), tedadAghlam);
                            if (takhfifSenfiSatr.getMinTedadAghlam() == 0 && takhfifSenfiSatr.getMinRial() == 0)
                            {
                                long mablaghTakhfif = Math.round(sumMablaghKolGorohKala * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100));

                                if (mablaghTakhfif != 0)
                                {
                                    insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                            takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());

                                    for (DataTableModel row : rowGorohKalas)
                                    {
                                        if (row.getFiled2().equals(gorohKala.getFiled1()))
                                        {
                                            long mablaghTakhfifSatr = Math.round((Long.valueOf(row.getFiled3()) * Long.valueOf(row.getFiled4().toString())) * (takhfifSenfiSatr.getDarsadTakhfif() / 100));
                                            insertFaktorSatrTakhfif(context, Long.valueOf(row.getFiled1()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                                        }
                                    }
                                }
                            }
                            else if (takhfifSenfiSatr.getMinTedadAghlam() > 0 && takhfifSenfiSatr.getMinRial() == 0 && sumAghlamGorohKala >= takhfifSenfiSatr.getMinTedadAghlam())
                            {
                                long mablaghTakhfif = Math.round(sumMablaghKolGorohKala * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100));
                                if (mablaghTakhfif != 0)
                                {
                                    insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                            takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());

                                    for (DataTableModel row : rowGorohKalas)
                                    {
                                        if (row.getFiled2().equals(gorohKala.getFiled1()))
                                        {
                                            long mablaghTakhfifSatr = Math.round((Long.valueOf(row.getFiled3()) * Long.valueOf(row.getFiled4())) * (takhfifSenfiSatr.getDarsadTakhfif() / 100));
                                            insertFaktorSatrTakhfif(context, Long.valueOf(row.getFiled1()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                                    takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                                        }
                                    }
                                }
                            }
                            else if (takhfifSenfiSatr.getMinTedadAghlam() == 0 && takhfifSenfiSatr.getMinRial() > 0 && sumMablaghKolGorohKala >= takhfifSenfiSatr.getMinRial())
                            {
                                long mablaghTakhfif = Math.round(sumMablaghKolGorohKala * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100));
                                if (mablaghTakhfif != 0) {
                                    insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                            takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());

                                    for (DataTableModel row : rowGorohKalas)
                                    {
                                        if (row.getFiled2().equals(gorohKala.getFiled1()))
                                        {
                                            long mablaghTakhfifSatr = Math.round((Long.valueOf(row.getFiled3()) * Long.valueOf(row.getFiled4())) * (takhfifSenfiSatr.getDarsadTakhfif() / 100));
                                            insertFaktorSatrTakhfif(context, Long.valueOf(row.getFiled1()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(),
                                                    takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
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









}
