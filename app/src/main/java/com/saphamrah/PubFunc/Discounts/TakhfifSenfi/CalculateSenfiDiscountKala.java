package com.saphamrah.PubFunc.Discounts.TakhfifSenfi;

import android.content.Context;
import android.util.Log;

import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
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

public class CalculateSenfiDiscountKala extends DiscountCalculation
{
    private Context context;


    public CalculateSenfiDiscountKala(Context context)
    {
        super(context);
        this.context = context;
    }


    public boolean calculateDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel)
    {
        try
        {
            String codeTakhfif = "";
            int zarib = -1;
            long Mablagh=0;
            long MablaghKol=0;
            KalaDAO kalaDAO = new KalaDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);
            ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrsForKala = darkhastFaktorSatrDAO.getByccDarkhastFaktorForTakhfifSenfiKala(darkhastFaktorModel.getCcDarkhastFaktor());
            codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());

            Log.d("takhfifKala" , "darkhastFaktorModel : " + darkhastFaktorModel.toString());

            for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrsForKala)
            {
                Log.d("takhfifKala" , "darkhastFaktorSatrModel : " + darkhastFaktorSatrModel.toString());
                KalaModel kala = kalaDAO.getByccKalaCode(darkhastFaktorSatrModel.getCcKalaCode());

                ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                        new int[]{getTedadRialTedad(),getTedadRialRial(),getTedadRialVazn(),getTedadRialAghlam()},
                        new int[]{getBasteBandiCarton(),getBasteBandiBaste(),getBasteBandiAdad()},
                        NAME_NOE_FIELD_KALA, darkhastFaktorSatrModel.getCcKalaCode(), darkhastFaktorSatrModel.getTedad3(),
                        darkhastFaktorSatrModel.getTedad3()/ kala.getTedadDarBasteh(),darkhastFaktorSatrModel.getTedad3()/ kala.getTedadDarKarton(),
                        darkhastFaktorSatrModel.getTedad3()* darkhastFaktorSatrModel.getMablaghForosh(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), ((darkhastFaktorSatrModel.getTedad3()* darkhastFaktorSatrModel.getVazn())/1000.0));

                for (TakhfifSenfiSatrModel takhfifSenfiSatrModel : takhfifSenfiSatrs)
                {
                    ArrayList<DataTableModel> darkhastFaktorSatrModelsForTakhfif = darkhastFaktorSatrDAO.getByccDarkhastFaktorAndccKalaCode(darkhastFaktorModel.getCcDarkhastFaktor(), darkhastFaktorSatrModel.getCcKalaCode(), takhfifSenfiTitrSatrModel.getOlaviat() , takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),takhfifSenfiTitrSatrModel.getNoeGheymat());
                    Log.d("takhfifKala" , "darkhastFaktorSatrModelsForTakhfif : " + darkhastFaktorSatrModelsForTakhfif);
                    for (DataTableModel model : darkhastFaktorSatrModelsForTakhfif)
                    {
                        KalaModel kalaMohasebeh = kalaDAO.getByccKalaCode(takhfifSenfiSatrModel.getCcGorohMohasebeh());
                        int mablaghVahedKalaMohasebeh = darkhastFaktorSatrDAO.getMablaghForoshByccKalaCode(takhfifSenfiSatrModel.getCcGorohMohasebeh(),darkhastFaktorModel.getCcDarkhastFaktor());
                        Log.d("takhfifKala" , "model : " + model.toString());
                        Log.d("takhfifKala" , "DarkhastFaktorSatrModel : " + model.toString());
                        Log.d("takhfifKala" , "kala : " + kala.toString());
                        Log.d("takhfifKala" , "mablaghVahedKalaMohasebeh : " + mablaghVahedKalaMohasebeh);
                        int sumTedadDarKarton = Integer.parseInt(model.getFiled3()) / kala.getTedadDarKarton();
                        int sumTedadDarBaste = Integer.parseInt(model.getFiled3()) / kala.getTedadDarBasteh();
                        Log.d("takhfifKala", "sumTedadDarKarton : " + sumTedadDarKarton);
                        Log.d("takhfifKala", "sumTedadDarBaste : " + sumTedadDarBaste);
                        Log.d("takhfifKala", "takhfifSenfiSatrModel : " + takhfifSenfiSatrModel);
                        zarib = calculateZarib(takhfifSenfiSatrModel.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatrModel.getCodeNoeBastehBandy(),
                                sumTedadDarKarton, sumTedadDarBaste, Integer.parseInt(model.getFiled3()), Double.parseDouble(model.getFiled6()), ((darkhastFaktorSatrModel.getTedad3()* darkhastFaktorSatrModel.getVazn())/1000.0),0);
                        Log.d("takhfifKala" , "MablaghKolFaktor : " + darkhastFaktorModel.getMablaghKolFaktor());
                        Log.d("takhfifKala" , "zarib : " + zarib);

                        int tedad = calculateTedad(takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatrModel.getCodeNoeBastehBandy(), kalaMohasebeh.getTedadDarKarton(), kalaMohasebeh.getTedadDarBasteh(), Double.parseDouble(model.getFiled3()));
                        Log.d("takhfifKala" , "tedad : " + tedad);
                        long MablaghTakhfifDarkhast = Math.round(Double.valueOf(model.getFiled6()));

                        Log.d("takhfifKala","CcTakhfifSenfi :" + takhfifSenfiSatrModel.getCcTakhfifSenfi() );
                        Log.d("takhfifKala","MablaghTakhfifDarkhast :" + MablaghTakhfifDarkhast);
                        if(takhfifSenfiTitrSatrModel.getOlaviat() == 0 || takhfifSenfiTitrSatrModel.getOlaviat() == 1 )//|| (takhfifSenfiTitrSatrModel.getCcTakhfifSenfi() == Integer.valueOf(model.getFiled7())))
                        {
                            MablaghKol = (long) (tedad * mablaghVahedKalaMohasebeh);
                            Log.d("takhfifKala","if MablaghKol :" + MablaghKol);
                        }
                        else
                        {
                            MablaghKol = (long) ((tedad * mablaghVahedKalaMohasebeh)-MablaghTakhfifDarkhast);
                            Log.d("takhfifKala","else MablaghKol :" + MablaghKol);
                        }
                        Log.d("takhfifKala" , "MablaghKol : " + MablaghKol + "takhfifSenfiTitrSatrModel.getOlaviat:" +takhfifSenfiTitrSatrModel.getOlaviat());
                        Mablagh =  calculateMablagh(takhfifSenfiTitrSatrModel.getNoeTedadRial(), (int) takhfifSenfiSatrModel.getBeEza(), (long) MablaghKol, (long) mablaghVahedKalaMohasebeh, takhfifSenfiSatrModel.getCcNoeField(), takhfifSenfiSatrModel.getCcGorohMohasebeh());
                        //double mablaghTakhfif = calculateMablaghTakhfif(Double.valueOf(model.getFiled6()), (tedad*kala.getMablaghForosh()), zarib, takhfifSenfiSatrModel.getBeEza(), takhfifSenfiSatrModel.getDarsadTakhfif(), takhfifSenfiTitrSatrModel.getNoeTedadRial());
                        long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, (long) mablaghVahedKalaMohasebeh, zarib, takhfifSenfiSatrModel.getBeEza(), takhfifSenfiSatrModel.getDarsadTakhfif(), takhfifSenfiTitrSatrModel.getNoeTedadRial());
                        Log.d("takhfifKala" , "mablaghTakhfif : " + mablaghTakhfif);
                        if (mablaghTakhfif > 0)
                        {
                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatrModel.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());
                            insertFaktorSatrTakhfif(context, Integer.parseInt(model.getFiled1()), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(), takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatrModel.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                        }
                    }
                }

            }
            return true;
        }
        catch (Exception e)
        {
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "CalculateSenfiDiscountKala", "", "calculateDiscount", "");
            return false;
        }
    }


    public boolean calculatePelekaniDiscount(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel)
    {
        if (takhfifSenfiTitrSatrModel.getIsPelekani() == 1)
        {
            //int zarib = 0;
            KalaDAO kalaDAO = new KalaDAO(context);
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(context);
            TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);
            ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrsForKala = darkhastFaktorSatrDAO.getByccDarkhastFaktorForTakhfifSenfiKala(darkhastFaktorModel.getCcDarkhastFaktor());
            String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());

            for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrsForKala)
            {
                double MianginDarsadTakhfif = 0;
                long SumMablaghTakhfif = 0;
                long MablaghTakhfifMiamgin = 0;
                long Tedadmax = 0;
                KalaModel kala = kalaDAO.getByccKalaCode(darkhastFaktorSatrModel.getCcKalaCode());
                List<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getByccTakhfifSenfi(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi());

                for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs)
                {
                    if (takhfifSenfiSatr.getCcNoeField() == kala.getCcKalaCode()) // Check ccKalaCode
                    {
                        double TedadBaghimanedehPele = 0;
                        double TedadHarPele = 0;
                        double MablaghTakhfif = 0;
                        if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == getBasteBandiAdad())
                        {
                            TedadBaghimanedehPele = darkhastFaktorSatrModel.getTedad3() - takhfifSenfiSatr.getTa();
                            if (TedadBaghimanedehPele >= takhfifSenfiSatr.getBeEza())
                            {
                                TedadHarPele = takhfifSenfiSatr.getTa();
                                //zarib = (int) (TedadHarPele / takhfifSenfiSatr.getBeEza());
                            }
                            else
                            {
                                TedadHarPele = darkhastFaktorSatrModel.getTedad3() - Tedadmax;
                                //zarib = (int) (TedadHarPele / takhfifSenfiSatr.getBeEza());
                            }
                        }
                        else if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == getBasteBandiBaste())
                        {
                            TedadBaghimanedehPele = darkhastFaktorSatrModel.getTedad3() - (takhfifSenfiSatr.getTa() * kala.getTedadDarBasteh());
                            if (TedadBaghimanedehPele >= (takhfifSenfiSatr.getBeEza() * kala.getTedadDarBasteh()))
                            {
                                TedadHarPele = takhfifSenfiSatr.getTa() * kala.getTedadDarBasteh();
                                //zarib = (int) (TedadHarPele / takhfifSenfiSatr.getBeEza() / kala.getTedadDarBasteh());
                            }
                            else
                            {
                                TedadHarPele = (((double) darkhastFaktorSatrModel.getTedad3() / kala.getTedadDarBasteh()) - Tedadmax) * kala.getTedadDarBasteh();
                                //zarib = (int) (TedadHarPele / kala.getTedadDarBasteh() / takhfifSenfiSatr.getBeEza());
                            }
                        }
                        else if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == getBasteBandiCarton())
                        {
                            TedadBaghimanedehPele = darkhastFaktorSatrModel.getTedad3() - (takhfifSenfiSatr.getTa() * kala.getTedadDarKarton());
                            if (TedadBaghimanedehPele >= (takhfifSenfiSatr.getBeEza() * kala.getTedadDarKarton())) //mosavi check shavad
                            {
                                TedadHarPele = takhfifSenfiSatr.getTa() * kala.getTedadDarBasteh();
                                //zarib = (int) (TedadHarPele / takhfifSenfiSatr.getBeEza() / kala.getTedadDarKarton());
                            }
                            else
                            {
                                TedadHarPele = (((double) darkhastFaktorSatrModel.getTedad3() / kala.getTedadDarKarton()) - Tedadmax) * kala.getTedadDarKarton();
                                //zarib = (int) (TedadHarPele / kala.getTedadDarKarton() / takhfifSenfiSatr.getBeEza());
                            }
                        }
                        if (TedadHarPele >= 0)
                        {
                            MablaghTakhfif = (TedadHarPele * darkhastFaktorSatrModel.getMablaghForosh()) * (takhfifSenfiSatr.getDarsadTakhfif() / 100);// (Zarib
                        }
                        if (MablaghTakhfif != 0)
                        {
                            SumMablaghTakhfif += MablaghTakhfif;
                        }

                        if (takhfifSenfiSatr.getDarsadTakhfif() != 0)
                        {
                            Tedadmax = (long) takhfifSenfiSatr.getTa();
                        }
                        // mosavi check shavad
                        if (TedadBaghimanedehPele < takhfifSenfiSatr.getBeEza() * kala.getTedadDarKarton() ||
                                (TedadBaghimanedehPele <= takhfifSenfiSatr.getBeEza() * kala.getTedadDarKarton() && takhfifSenfiSatr.getDarsadTakhfif() == 0))
                        {
                            break;
                        }
                    }
                }

                MianginDarsadTakhfif = SumMablaghTakhfif / (darkhastFaktorSatrModel.getTedad3() * darkhastFaktorSatrModel.getMablaghForosh());
                MablaghTakhfifMiamgin = Math.round((darkhastFaktorSatrModel.getMablaghForosh() * darkhastFaktorSatrModel.getTedad3()) * MianginDarsadTakhfif);
                if (MablaghTakhfifMiamgin > 0)
                {
                    insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                            takhfifSenfiTitrSatrModel.getSharhTakhfif(), MianginDarsadTakhfif * 100, MablaghTakhfifMiamgin, takhfifSenfiTitrSatrModel.getForJayezeh());

                    insertFaktorSatrTakhfif(context, darkhastFaktorSatrModel.getCcDarkhastFaktorSatr(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                            takhfifSenfiTitrSatrModel.getSharhTakhfif(), MianginDarsadTakhfif * 100, MablaghTakhfifMiamgin,
                            takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                }
            }
        }
        return true;
    }


    public boolean calculateMantaghe(DarkhastFaktorModel darkhastFaktorModel, TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel)
    {
        String codeTakhfif = new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());
        TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(context);
        KalaDAO kalaDAO = new KalaDAO(context);
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(context);
        int ccMantaghehMoshtary = moshtaryAddressDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).get(0).getCcMahaleh();
        List<DarkhastFaktorSatrModel> darkhastFaktorSatrs = new DarkhastFaktorSatrDAO(context).getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
        // Kala..
        for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
        {
            KalaModel kala = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());
            if (takhfifSenfiTitrSatrModel.getCcMantagheh() == ccMantaghehMoshtary)// check ccMantaghehTakhfif by ccMahaleh Moshtary
            {
                // Satrhaye Takhfif...
                List<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                        new int[]{getTedadRialTedad(),getTedadRialRial(),getTedadRialVazn(),getTedadRialAghlam()},
                        new int[]{getBasteBandiCarton(),getBasteBandiBaste(),getBasteBandiAdad()},
                        NAME_NOE_FIELD_KALA, darkhastFaktorSatr.getCcKalaCode(), darkhastFaktorSatr.getTedad3(),
                        darkhastFaktorSatr.getTedad3() / kala.getTedadDarBasteh(), darkhastFaktorSatr.getTedad3() / kala.getTedadDarKarton(), darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), ((darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getVazn())/1000.0));

                for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs)
                {
                    long Mablagh=0;
                    int zarib=0;
                    int tedad=0;
                    KalaModel kalaMohasebeh = kalaDAO.getByccKalaCode(takhfifSenfiSatr.getCcGorohMohasebeh());
                    zarib = calculateZarib(takhfifSenfiSatr.getBeEza(), takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandyBeEza(), kala.getTedadDarKarton(), kala.getTedadDarBasteh(), kala.getAdad(), darkhastFaktorModel.getMablaghKolFaktor(),((darkhastFaktorSatr.getTedad3()* darkhastFaktorSatr.getVazn())/1000.0),0);
                    Log.d("takhfifKalaMantaghe" , "zarib : " + zarib);

                    tedad = calculateTedad(takhfifSenfiTitrSatrModel.getNoeTedadRial(), takhfifSenfiSatr.getCodeNoeBastehBandy(), kalaMohasebeh.getTedadDarKarton(), kalaMohasebeh.getTedadDarBasteh(), kalaMohasebeh.getAdad());
                    Mablagh =  calculateMablagh(takhfifSenfiTitrSatrModel.getNoeTedadRial(), (int) takhfifSenfiSatr.getBeEza(), (long) (tedad*kalaMohasebeh.getMablaghForosh()), (long) kalaMohasebeh.getMablaghForosh(), takhfifSenfiSatr.getCcNoeField(), takhfifSenfiSatr.getCcGorohMohasebeh());


                    if (takhfifSenfiSatr.getGheymatForosh() == 0)
                    {
                        //double mablaghTakhfif = (darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100);
                        long mablaghTakhfif = calculateMablaghTakhfif(Mablagh, (long)kalaMohasebeh.getMablaghForosh(), zarib, takhfifSenfiSatr.getBeEza(), takhfifSenfiSatr.getDarsadTakhfif(), takhfifSenfiTitrSatrModel.getNoeTedadRial());
                        Log.d("takhfifKalaMantaghe" , "mablaghTakhfif : " + mablaghTakhfif);
                        // Insert In DarkhastFaktorTakhfif &
                        // DarkhastFaktorSatrTakhfif..
                        if (mablaghTakhfif != 0)
                        {
                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                    takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());
                            insertFaktorSatrTakhfif(context, darkhastFaktorSatr.getCcDarkhastFaktorSatr(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                    takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                        }
                    }
                    else if (takhfifSenfiSatr.getGheymatForosh() > 0 && darkhastFaktorSatr.getMablaghForosh() == takhfifSenfiSatr.getGheymatForosh())
                    {
                        long mablaghTakhfif = Math.round((darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100));
                        // Insert In DarkhastFaktorTakhfif &
                        // DarkhastFaktorSatrTakhfif..
                        if (mablaghTakhfif != 0) {
                            insertFaktorTakhfif(context, darkhastFaktorModel.getCcDarkhastFaktor(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                    takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh());
                            insertFaktorSatrTakhfif(context, darkhastFaktorSatr.getCcDarkhastFaktorSatr(), codeTakhfif, takhfifSenfiTitrSatrModel.getCcTakhfifSenfi(),
                                    takhfifSenfiTitrSatrModel.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifSenfiTitrSatrModel.getForJayezeh(), takhfifSenfiTitrSatrModel.getOlaviat());
                        }
                    }

                }
            }
        }
        return true;
    }

}

