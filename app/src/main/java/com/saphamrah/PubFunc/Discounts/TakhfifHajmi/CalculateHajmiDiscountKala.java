package com.saphamrah.PubFunc.Discounts.TakhfifHajmi;

import android.content.Context;
import android.util.Log;

import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
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

public class CalculateHajmiDiscountKala extends DiscountCalculation
{
    private Context context;


    public CalculateHajmiDiscountKala(Context context)
    {
        super(context);
        this.context = context;
    }


    public boolean calculateDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel)
    {
        try
        {
            String codeTakhfif = "";
            int zarib = -1;
            long Mablagh=0;
            long MablaghKol=0;
            KalaDAO kalaDAO = new KalaDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);
            ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrsForKala = darkhastFaktorSatrDAO.getByccDarkhastFaktorForTakhfifHajmiKala(darkhastFaktorModel.getCcDarkhastFaktor());
            codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());

            Log.d("takhfifKala" , "darkhastFaktorModel : " + darkhastFaktorModel.toString());

            for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrsForKala)
            {
                Log.d("takhfifKala" , "darkhastFaktorSatrModel : " + darkhastFaktorSatrModel.toString());
                KalaModel kala = kalaDAO.getByccKalaCode(darkhastFaktorSatrModel.getCcKalaCode());

                ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                        new int[]{getTedadRialTedad(),getTedadRialRial(),getTedadRialVazn(),getTedadRialAghlam()},
                        new int[]{getBasteBandiCarton(),getBasteBandiBaste(),getBasteBandiAdad()},
                        NAME_NOE_FIELD_KALA, darkhastFaktorSatrModel.getCcKalaCode(), darkhastFaktorSatrModel.getTedad3(),
                        darkhastFaktorSatrModel.getTedad3()/ kala.getTedadDarBasteh(),darkhastFaktorSatrModel.getTedad3()/ kala.getTedadDarKarton(),
                        darkhastFaktorSatrModel.getTedad3()* darkhastFaktorSatrModel.getMablaghForosh(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), ((darkhastFaktorSatrModel.getTedad3()* darkhastFaktorSatrModel.getVazn())/1000.0),0);

                for (TakhfifHajmiSatrModel takhfifHajmiSatrModel : takhfifHajmiSatrs)
                {
                    ArrayList<DataTableModel> darkhastFaktorSatrModelsForTakhfif = darkhastFaktorSatrDAO.getByccDarkhastFaktorAndccKalaCode(darkhastFaktorModel.getCcDarkhastFaktor(), darkhastFaktorSatrModel.getCcKalaCode(), takhfifHajmiTitrSatrModel.getOlaviat() , takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),takhfifHajmiTitrSatrModel.getNoeGheymat());
                    Log.d("takhfifKala" , "darkhastFaktorSatrModelsForTakhfif : " + darkhastFaktorSatrModelsForTakhfif);
                    for (DataTableModel model : darkhastFaktorSatrModelsForTakhfif)
                    {
                        KalaModel kalaMohasebeh = kalaDAO.getByccKalaCode(takhfifHajmiSatrModel.getCcGorohMohasebeh());
                        int mablaghVahedKalaMohasebeh = darkhastFaktorSatrDAO.getMablaghForoshByccKalaCode(takhfifHajmiSatrModel.getCcGorohMohasebeh(),darkhastFaktorModel.getCcDarkhastFaktor());
                        Log.d("takhfifKala" , "model : " + model.toString());
                        Log.d("takhfifKala" , "DarkhastFaktorSatrModel : " + model.toString());
                        Log.d("takhfifKala" , "kala : " + kala.toString());
                        Log.d("takhfifKala" , "mablaghVahedKalaMohasebeh : " + mablaghVahedKalaMohasebeh);
                        int sumTedadDarKarton = Integer.parseInt(model.getFiled3()) / kala.getTedadDarKarton();
                        int sumTedadDarBaste = Integer.parseInt(model.getFiled3()) / kala.getTedadDarBasteh();
                        Log.d("takhfifKala", "sumTedadDarKarton : " + sumTedadDarKarton);
                        Log.d("takhfifKala", "sumTedadDarBaste : " + sumTedadDarBaste);
                        Log.d("takhfifKala", "takhfifHajmiSatrModel : " + takhfifHajmiSatrModel);
                        zarib = calculateZarib(takhfifHajmiSatrModel.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatrModel.getCodeNoeBastehBandy(),
                                sumTedadDarKarton, sumTedadDarBaste, Integer.parseInt(model.getFiled3()), Double.parseDouble(model.getFiled6()), ((darkhastFaktorSatrModel.getTedad3()* darkhastFaktorSatrModel.getVazn())/1000.0),0);
                        Log.d("takhfifKala" , "MablaghKolFaktor : " + darkhastFaktorModel.getMablaghKolFaktor());
                        Log.d("takhfifKala" , "zarib : " + zarib);

                        int tedad = calculateTedad(takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatrModel.getCodeNoeBastehBandy(), kalaMohasebeh.getTedadDarKarton(), kalaMohasebeh.getTedadDarBasteh(), Double.parseDouble(model.getFiled3()));
                        Log.d("takhfifKala" , "tedad : " + tedad);
                        long MablaghTakhfifDarkhast = Math.round(Double.valueOf(model.getFiled6()));

                        Log.d("takhfifKala","CcTakhfifHajmi :" + takhfifHajmiSatrModel.getCcTakhfifHajmi() );
                        Log.d("takhfifKala","MablaghTakhfifDarkhast :" + MablaghTakhfifDarkhast);
                        if(takhfifHajmiTitrSatrModel.getOlaviat() == 0 || takhfifHajmiTitrSatrModel.getOlaviat() == 1 )//|| (takhfifHajmiTitrSatrModel.getCcTakhfifHajmi() == Integer.valueOf(model.getFiled7())))
                        {
                            MablaghKol = (long) (tedad * mablaghVahedKalaMohasebeh);
                            Log.d("takhfifKala","if MablaghKol :" + MablaghKol);
                        }
                        else
                        {
                            MablaghKol = (long) ((tedad * mablaghVahedKalaMohasebeh)-MablaghTakhfifDarkhast);
                            Log.d("takhfifKala","else MablaghKol :" + MablaghKol);
                        }
                        Log.d("takhfifKala" , "MablaghKol : " + MablaghKol + "takhfifHajmiTitrSatrModel.getOlaviat:" +takhfifHajmiTitrSatrModel.getOlaviat());
                        Mablagh =  calculateMablagh(takhfifHajmiTitrSatrModel.getNoeTedadRial(), (int) takhfifHajmiSatrModel.getBeEza(), (long) MablaghKol, (long) mablaghVahedKalaMohasebeh, takhfifHajmiSatrModel.getCcNoeField(), takhfifHajmiSatrModel.getCcGorohMohasebeh());
                        //double mablaghTakhfif = calculateMablaghTakhfif(Double.valueOf(model.getFiled6()), (tedad*kala.getMablaghForosh()), zarib, takhfifHajmiSatrModel.getBeEza(), takhfifHajmiSatrModel.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                        long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, (long) mablaghVahedKalaMohasebeh, zarib, takhfifHajmiSatrModel.getBeEza(), takhfifHajmiSatrModel.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                        Log.d("takhfifKala" , "mablaghTakhfif : " + mablaghTakhfif);
                        if (mablaghTakhfif > 0)
                        {
                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatrModel.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());
                            insertFaktorSatrTakhfif(context, Integer.parseInt(model.getFiled1()), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatrModel.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                        }
                    }
                }

            }
            return true;
        }
        catch (Exception e)
        {
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateHajmiDiscountKala", "", "calculateDiscount", "");
            return false;
        }
    }


    public boolean calculatePelekaniDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel)
    {
        if (takhfifHajmiTitrSatrModel.getIsPelekani() == 1)
        {
            //int zarib = 0;
            KalaDAO kalaDAO = new KalaDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);
            ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrsForKala = darkhastFaktorSatrDAO.getByccDarkhastFaktorForTakhfifHajmiKala(darkhastFaktorModel.getCcDarkhastFaktor());
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());

            for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrsForKala)
            {
                double MianginDarsadTakhfif = 0;
                long SumMablaghTakhfif = 0;
                long MablaghTakhfifMiamgin = 0;
                long Tedadmax = 0;
                KalaModel kala = kalaDAO.getByccKalaCode(darkhastFaktorSatrModel.getCcKalaCode());
                List<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getByccTakhfifHajmi(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi());

                for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                {
                    if (takhfifHajmiSatr.getCcNoeField() == kala.getCcKalaCode()) // Check ccKalaCode
                    {
                        double TedadBaghimanedehPele = 0;
                        double TedadHarPele = 0;
                        double MablaghTakhfif = 0;
                        if (takhfifHajmiSatr.getCodeNoeBastehBandyBeEza() == getBasteBandiAdad())
                        {
                            TedadBaghimanedehPele = darkhastFaktorSatrModel.getTedad3() - takhfifHajmiSatr.getTa();
                            if (TedadBaghimanedehPele >= takhfifHajmiSatr.getBeEza())
                            {
                                TedadHarPele = takhfifHajmiSatr.getTa();
                                //zarib = (int) (TedadHarPele / takhfifHajmiSatr.getBeEza());
                            }
                            else
                            {
                                TedadHarPele = darkhastFaktorSatrModel.getTedad3() - Tedadmax;
                                //zarib = (int) (TedadHarPele / takhfifHajmiSatr.getBeEza());
                            }
                        }
                        else if (takhfifHajmiSatr.getCodeNoeBastehBandyBeEza() == getBasteBandiBaste())
                        {
                            TedadBaghimanedehPele = darkhastFaktorSatrModel.getTedad3() - (takhfifHajmiSatr.getTa() * kala.getTedadDarBasteh());
                            if (TedadBaghimanedehPele >= (takhfifHajmiSatr.getBeEza() * kala.getTedadDarBasteh()))
                            {
                                TedadHarPele = takhfifHajmiSatr.getTa() * kala.getTedadDarBasteh();
                                //zarib = (int) (TedadHarPele / takhfifHajmiSatr.getBeEza() / kala.getTedadDarBasteh());
                            }
                            else
                            {
                                TedadHarPele = (((double) darkhastFaktorSatrModel.getTedad3() / kala.getTedadDarBasteh()) - Tedadmax) * kala.getTedadDarBasteh();
                                //zarib = (int) (TedadHarPele / kala.getTedadDarBasteh() / takhfifHajmiSatr.getBeEza());
                            }
                        }
                        else if (takhfifHajmiSatr.getCodeNoeBastehBandyBeEza() == getBasteBandiCarton())
                        {
                            TedadBaghimanedehPele = darkhastFaktorSatrModel.getTedad3() - (takhfifHajmiSatr.getTa() * kala.getTedadDarKarton());
                            if (TedadBaghimanedehPele >= (takhfifHajmiSatr.getBeEza() * kala.getTedadDarKarton())) //mosavi check shavad
                            {
                                TedadHarPele = takhfifHajmiSatr.getTa() * kala.getTedadDarBasteh();
                                //zarib = (int) (TedadHarPele / takhfifHajmiSatr.getBeEza() / kala.getTedadDarKarton());
                            }
                            else
                            {
                                TedadHarPele = (((double) darkhastFaktorSatrModel.getTedad3() / kala.getTedadDarKarton()) - Tedadmax) * kala.getTedadDarKarton();
                                //zarib = (int) (TedadHarPele / kala.getTedadDarKarton() / takhfifHajmiSatr.getBeEza());
                            }
                        }
                        if (TedadHarPele >= 0)
                        {
                            MablaghTakhfif = (TedadHarPele * darkhastFaktorSatrModel.getMablaghForosh()) * (takhfifHajmiSatr.getDarsadTakhfif() / 100);// (Zarib
                        }
                        if (MablaghTakhfif != 0)
                        {
                            SumMablaghTakhfif += MablaghTakhfif;
                        }

                        if (takhfifHajmiSatr.getDarsadTakhfif() != 0)
                        {
                            Tedadmax = (long) takhfifHajmiSatr.getTa();
                        }
                        // mosavi check shavad
                        if (TedadBaghimanedehPele < takhfifHajmiSatr.getBeEza() * kala.getTedadDarKarton() ||
                                (TedadBaghimanedehPele <= takhfifHajmiSatr.getBeEza() * kala.getTedadDarKarton() && takhfifHajmiSatr.getDarsadTakhfif() == 0))
                        {
                            break;
                        }
                    }
                }

                MianginDarsadTakhfif = SumMablaghTakhfif / (darkhastFaktorSatrModel.getTedad3() * darkhastFaktorSatrModel.getMablaghForosh());
                MablaghTakhfifMiamgin = Math.round((darkhastFaktorSatrModel.getMablaghForosh() * darkhastFaktorSatrModel.getTedad3()) * MianginDarsadTakhfif);
                if (MablaghTakhfifMiamgin > 0)
                {
                    insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                            takhfifHajmiTitrSatrModel.getSharhTakhfif(), MianginDarsadTakhfif * 100, MablaghTakhfifMiamgin, takhfifHajmiTitrSatrModel.getForJayezeh());

                    insertFaktorSatrTakhfif(context, darkhastFaktorSatrModel.getCcDarkhastFaktorSatr(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                            takhfifHajmiTitrSatrModel.getSharhTakhfif(), MianginDarsadTakhfif * 100, MablaghTakhfifMiamgin,
                            takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                }
            }
        }
        return true;
    }


    public boolean calculateMantaghe(DarkhastFaktorModel darkhastFaktorModel, TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel)
    {
        String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());
        TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(context);
        KalaDAO kalaDAO = new KalaDAO(context);
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(context);
        int ccMantaghehMoshtary = moshtaryAddressDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).get(0).getCcMahaleh();
        List<DarkhastFaktorSatrModel> darkhastFaktorSatrs = new DarkhastFaktorSatrDAO(context).getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
        // Kala..
        for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
        {
            KalaModel kala = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());
            if (takhfifHajmiTitrSatrModel.getCcMantagheh() == ccMantaghehMoshtary)// check ccMantaghehTakhfif by ccMahaleh Moshtary
            {
                // Satrhaye Takhfif...
                List<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                        new int[]{getTedadRialTedad(),getTedadRialRial(),getTedadRialVazn(),getTedadRialAghlam()},
                        new int[]{getBasteBandiCarton(),getBasteBandiBaste(),getBasteBandiAdad()},
                        NAME_NOE_FIELD_KALA, darkhastFaktorSatr.getCcKalaCode(), darkhastFaktorSatr.getTedad3(),
                        darkhastFaktorSatr.getTedad3() / kala.getTedadDarBasteh(), darkhastFaktorSatr.getTedad3() / kala.getTedadDarKarton(), darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), ((darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getVazn())/1000.0),0);

                for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                {
                    long Mablagh=0;
                    int zarib=0;
                    int tedad=0;
                    KalaModel kalaMohasebeh = kalaDAO.getByccKalaCode(takhfifHajmiSatr.getCcGorohMohasebeh());
                    zarib = calculateZarib(takhfifHajmiSatr.getBeEza(), takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandyBeEza(), kala.getTedadDarKarton(), kala.getTedadDarBasteh(), kala.getAdad(), darkhastFaktorModel.getMablaghKolFaktor(),((darkhastFaktorSatr.getTedad3()* darkhastFaktorSatr.getVazn())/1000.0),0);
                    Log.d("takhfifKalaMantaghe" , "zarib : " + zarib);

                    tedad = calculateTedad(takhfifHajmiTitrSatrModel.getNoeTedadRial(), takhfifHajmiSatr.getCodeNoeBastehBandy(), kalaMohasebeh.getTedadDarKarton(), kalaMohasebeh.getTedadDarBasteh(), kalaMohasebeh.getAdad());
                    Mablagh =  calculateMablagh(takhfifHajmiTitrSatrModel.getNoeTedadRial(), (int) takhfifHajmiSatr.getBeEza(), (long) (tedad*kalaMohasebeh.getMablaghForosh()), (long) kalaMohasebeh.getMablaghForosh(), takhfifHajmiSatr.getCcNoeField(), takhfifHajmiSatr.getCcGorohMohasebeh());


                    if (takhfifHajmiSatr.getGheymatForosh() == 0)
                    {
                        //double mablaghTakhfif = (darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (zarib * takhfifHajmiSatr.getDarsadTakhfif() / 100);
                        long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, (long)kalaMohasebeh.getMablaghForosh(), zarib, takhfifHajmiSatr.getBeEza(), takhfifHajmiSatr.getDarsadTakhfif(), takhfifHajmiTitrSatrModel.getNoeTedadRial());
                        Log.d("takhfifKalaMantaghe" , "mablaghTakhfif : " + mablaghTakhfif);
                        // Insert In DarkhastFaktorTakhfif &
                        // DarkhastFaktorSatrTakhfif..
                        if (mablaghTakhfif != 0)
                        {
                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                    takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());
                            insertFaktorSatrTakhfif(context, darkhastFaktorSatr.getCcDarkhastFaktorSatr(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                    takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                        }
                    }
                    else if (takhfifHajmiSatr.getGheymatForosh() > 0 && darkhastFaktorSatr.getMablaghForosh() == takhfifHajmiSatr.getGheymatForosh())
                    {
                        long mablaghTakhfif = Math.round((darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (zarib * takhfifHajmiSatr.getDarsadTakhfif() / 100));
                        // Insert In DarkhastFaktorTakhfif &
                        // DarkhastFaktorSatrTakhfif..
                        if (mablaghTakhfif != 0) {
                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                    takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());
                            insertFaktorSatrTakhfif(context, darkhastFaktorSatr.getCcDarkhastFaktorSatr(), codeTakhfif, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),
                                    takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh(), takhfifHajmiTitrSatrModel.getOlaviat());
                        }
                    }

                }
            }
        }
        return true;
    }

}

