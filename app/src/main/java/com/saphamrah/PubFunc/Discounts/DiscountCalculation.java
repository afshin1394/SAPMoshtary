package com.saphamrah.PubFunc.Discounts;

import android.content.Context;
import android.util.Log;

import com.saphamrah.DAO.DarkhastFaktorJayezehDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrTakhfifDAO;
import com.saphamrah.DAO.DarkhastFaktorTakhfifDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.TakhfifHajmiDAO;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrTakhfifModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.TakhfifHajmiTitrSatrModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class DiscountCalculation
{

    public static final int NAME_NOE_FIELD_KALA = 1;
    public static final int NAME_NOE_FIELD_GOROH_KALA = 2;
    public static final int NAME_NOE_FIELD_BRAND = 3;
    public static final int NAME_NOE_FIELD_TAMIN_KONANDE = 4;


    private static int basteBandiCarton = -1;
    private static int basteBandiBaste = -1;
    private static int basteBandiAdad = -1;
    private static int tedadRialTedad = -1;
    private static int tedadRialRial = -1;
    private static int tedadRialAghlam = -1;
    private static int tedadRialVazn = -1;

    public int getBasteBandiCarton()
    {
        return basteBandiCarton;
    }
    public int getBasteBandiBaste()
    {
        return basteBandiBaste;
    }
    public int getBasteBandiAdad()
    {
        return basteBandiAdad;
    }
    public int getTedadRialTedad()
    {
        return tedadRialTedad;
    }
    public int getTedadRialRial()
    {
        return tedadRialRial;
    }
    public int getTedadRialAghlam()
    {
        return tedadRialAghlam;
    }
    public int getTedadRialVazn()
    {
        return tedadRialVazn;
    }



    public DiscountCalculation(Context context)
    {}

    public DiscountCalculation(ArrayList<ParameterChildModel> parameterChildModels)
    {
		getConfig(parameterChildModels);
    }


    private void getConfig(ArrayList<ParameterChildModel> parameterChildModels)
    {
        for (ParameterChildModel model : parameterChildModels)
        {
            if (model.getCcParameterChild() == Constants.CC_CHILD_NOE_BASTE_BANDI_CARTON())
            {
                basteBandiCarton = Integer.valueOf(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_NOE_BASTE_BANDI_BASTE())
            {
                basteBandiBaste = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_NOE_BASTE_BANDI_ADAD())
            {
                basteBandiAdad = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_NOE_TEDAD_RIAL_TEDAD())
            {
                tedadRialTedad = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_NOE_TEDAD_RIAL_RIAL())
            {
                tedadRialRial = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_NOE_TEDAD_RIAL_AGHLAM)
            {
                tedadRialAghlam = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_NOE_TEDAD_RIAL_Vazn)
            {
                tedadRialVazn = Integer.parseInt(model.getValue());
            }
        }
    }


    public ArrayList<TakhfifHajmiTitrSatrModel> getTakhfifHajmis(Context context, DarkhastFaktorModel darkhastFaktorModel, int noeVosol, boolean shebheOmdeh)
    {
        TakhfifHajmiDAO takhfifHajmiDAO = new TakhfifHajmiDAO(context);
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(context);

        MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
        Log.d("takhfifHajmi" , "getCodeNoeHaml : " + darkhastFaktorModel.getCodeNoeHaml() + " , shebheOmdeh : " + shebheOmdeh + " , noeVosol : " + noeVosol);

        if(noeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD() || noeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_Resid_Naghd() || noeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh())
        {
            noeVosol = 1;
        }
        else if (noeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK() || noeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_RESID() || noeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh())
        {
            noeVosol = 2;
        }

        return takhfifHajmiDAO.getByMoshtaryWithSatr(moshtary, darkhastFaktorModel.getCodeNoeHaml(), shebheOmdeh, noeVosol);
    }


    public int calculateZarib(double beEza, int noeTedadRialTakhfif, int codeNoeBasteBandiTakhfif, double tedadCarton, double tedadBaste, double tedadAdad, double sumMablagh, double sumVazn)
    {
        Log.d("Takhfif" , "beEza : " + beEza + " , codeNoeBasteBandiTakhfif : " + codeNoeBasteBandiTakhfif + " , tedadBaste : " + tedadBaste + " , tedadCarton : " + tedadCarton);
        Log.d("Takhfif" , " , noeTedadRialTakhfif : " + noeTedadRialTakhfif + " , basteBandiAdad : " + basteBandiAdad + " , basteBandiBaste : " + basteBandiBaste + " , basteBandiCarton : " + basteBandiCarton);
        Log.d("Takhfif" , "sumMablagh : " + sumMablagh + ", sumVazn: " + sumVazn);
        int zarib = 0;
        if (beEza == 0)
        {
            zarib = 1;
        }
        else {
            if (noeTedadRialTakhfif == tedadRialTedad)
            {
                if (codeNoeBasteBandiTakhfif == basteBandiAdad) {
                    zarib = (int) (tedadAdad / beEza);
                    Log.d("Takhfif", "add - zarib" + zarib);
                } else if (codeNoeBasteBandiTakhfif == basteBandiBaste) {
                    zarib = (int) (tedadBaste / beEza);
                    Log.d("Takhfif", "baste - zarib" + zarib);
                } else if (codeNoeBasteBandiTakhfif == basteBandiCarton) {
                    zarib = (int) (tedadCarton / beEza);
                    Log.d("Takhfif", "carton - zarib" + zarib);
                }
            }
            else if (noeTedadRialTakhfif == tedadRialRial)
            {
                zarib = (int) (sumMablagh / beEza);
                Log.d("Takhfif", "rial-vazn - zarib" + zarib + " ,noeTedadRialTakhfif:" + noeTedadRialTakhfif + " , tedadRialRial:" + tedadRialRial + " , tedadRialVazn:" + tedadRialVazn);
            }
            else if (noeTedadRialTakhfif == tedadRialVazn)
            {
                zarib = (int) (sumVazn / beEza);
                Log.d("Takhfif", "rial-vazn - zarib" + zarib + " ,noeTedadRialTakhfif:" + noeTedadRialTakhfif + " , tedadRialRial:" + tedadRialRial + " , tedadRialVazn:" + tedadRialVazn);
            }

        }
        return zarib;
    }


    public int calculateTedad(int noeTedadRialTakhfif, int codeNoeBasteBandiTakhfif, double tedadCarton, double tedadBaste, double tedadAdad)
    {
        int tedad = 0;
        /*if (noeTedadRialTakhfif == tedadRialTedad)
        {*/
            if (codeNoeBasteBandiTakhfif == basteBandiAdad)
            {
                tedad = (int) tedadAdad;
                Log.d("takhfifKala","basteBandiAdad , tedad: " + tedad );
            }
            else if (codeNoeBasteBandiTakhfif == basteBandiBaste)
            {
                tedad = (int) tedadBaste;
                Log.d("takhfifKala","basteBandiBaste , tedad: " + tedad );
            }
            else if (codeNoeBasteBandiTakhfif == basteBandiCarton)
            {
                tedad = (int) tedadCarton;
                Log.d("takhfifKala","basteBandiCarton , tedad: " + tedad );
            }
            else
            {
                tedad = (int) tedadAdad;
                Log.d("takhfifKala","adad , tedad: " + tedad );
            }
        //}
        return tedad;
    }

    public long calculateMablaghVahed(int noeTedadRialTakhfif, int tedad, long vazn, long MablaghKol)
    {
        long mablaghVahed = 0;
        Log.d("takhfifKala", MablaghKol + " - " + vazn + " - " + tedad);
        if(noeTedadRialTakhfif == tedadRialTedad)
        {
            if(tedad>0)
                mablaghVahed = MablaghKol / tedad;
            Log.d("takhfifKala","calculateMablaghVahed : " + mablaghVahed + "- tedadRialTedad: " + tedadRialTedad );
        }
        else if(noeTedadRialTakhfif == tedadRialRial)
        {
            if(tedad>0)
                mablaghVahed = MablaghKol / tedad;
            Log.d("takhfifKala","calculateMablaghVahed : " + mablaghVahed + "- tedadRialRial: " + tedadRialRial );
        }
        else if(noeTedadRialTakhfif == tedadRialVazn)
        {
            double vaznKilogeram = vazn/1000.0;
            if(vaznKilogeram>0)
                mablaghVahed = Math.round(MablaghKol / vaznKilogeram);
            Log.d("takhfifKala","calculateMablaghVahed : " + mablaghVahed + "- tedadRialVazn: " + " - " + tedadRialVazn + " - " + vaznKilogeram);
        }
        return mablaghVahed;
    }

    public long calculateMablagh(int noeTedadRialTakhfif, int beEza, long MablaghKol, long MablaghVahed, int First, int Second)
    {
        long Mablagh = 0;
        if (beEza == 0)
        {
            Mablagh = MablaghKol;
            Log.d("takhfifKala","beEza=0 , Mablaghkol: " + MablaghKol );
        }
        else if(beEza>0 && First==Second && noeTedadRialTakhfif == tedadRialTedad)
        {
            Mablagh = MablaghVahed * beEza;
            Log.d("takhfifKala","First==Second: " + MablaghVahed + "-" + tedadRialTedad + "-" + beEza);
        }
        else if(beEza>0 && First==Second && noeTedadRialTakhfif == tedadRialRial)
        {
            Mablagh = beEza;
            Log.d("takhfifKala","First==Second: - tedadRialRial: " +tedadRialRial + "-" + beEza);
        }
        else if(beEza>0 && First==Second && noeTedadRialTakhfif == tedadRialVazn)
        {
            Mablagh = MablaghVahed * beEza;
            Log.d("takhfifKala","First==Second: - tedadRialVazn:  " +tedadRialVazn + "-" + beEza);
        }
        else if(beEza>0 && First!=Second && noeTedadRialTakhfif == tedadRialTedad)
        {
            Mablagh = MablaghKol;
            Log.d("takhfifKala","First!=Second: " + MablaghKol );
        }
        else if(beEza>0 && First!=Second && noeTedadRialTakhfif == tedadRialRial)
        {
            Mablagh = MablaghKol;
            Log.d("takhfifKala","First==Second: - tedadRialRial: " +tedadRialRial + "-" + beEza);
        }
        else if(beEza>0 && First!=Second && noeTedadRialTakhfif == tedadRialVazn)
        {
            Mablagh = MablaghKol;
            Log.d("takhfifKala","First==Second - tedadRialVazn: " + MablaghKol + "-" + tedadRialVazn + "-" + beEza);
        }
        return Mablagh;
    }

    /**
     * محاسبه کل مبلغ تخفیف
     * @param sumMablagh مجموع هزینه
     * @param zarib ضریب محاسبه شده
     * @param beEza به ازا
     * @param darsadTakhfif درصد تخفیف
     * @return اگر به درستی محاسبه شود که مبلغ تخفیف برگردانده میشود. در صورت بروز خطا -1 برگردانده می شود.
     */
    public long calculateMablaghTakhfif(long sumMablagh, long mablaghVahed, int zarib, double beEza, double darsadTakhfif, int noeTedadRial)
    {
        long mablaghTakhfif = 0;
        try
        {
            if (noeTedadRial == tedadRialRial)
            {
                if (beEza == 0)
                {
                    mablaghTakhfif = (long) (sumMablagh * (darsadTakhfif / 100));
                    Log.d("takhfifHajmi" , "tedadRialRial" + "beEza:" + beEza + "sumMablagh : " + sumMablagh + " ,darsadTakhfif:" + darsadTakhfif  );
                }
                else
                {
                    mablaghTakhfif = (long) (sumMablagh * (zarib * darsadTakhfif / 100));
                    Log.d("takhfifHajmi" , "tedadRialRial" + "beEza:" + beEza + "sumMablagh : " + sumMablagh + ",zarib:" + zarib +" ,darsadTakhfif:" + darsadTakhfif  );
                }
            }
            else if (noeTedadRial == tedadRialTedad)
            {
                if (beEza == 0)
                {
                    mablaghTakhfif = (long) (sumMablagh * (darsadTakhfif / 100));
                    Log.d("takhfifHajmi" , "tedadRialTedad" + "beEza:" + beEza + "sumMablagh : " + sumMablagh  +" ,darsadTakhfif:" + darsadTakhfif  );
                }
                else
                {
                    mablaghTakhfif = (long) (sumMablagh * (zarib * darsadTakhfif / 100));
                    Log.d("takhfifHajmi" , "tedadRialRial" + "beEza:" + beEza + "sumMablagh : " + sumMablagh + ",zarib:" + zarib +" ,darsadTakhfif:" + darsadTakhfif  );
                }
            }
            else if (noeTedadRial == tedadRialVazn)
            {
                if (beEza == 0)
                {
                    mablaghTakhfif = (long) (sumMablagh * (darsadTakhfif / 100));
                    Log.d("takhfifHajmi" , "tedadRialVazn" + "beEza:" + beEza + "sumMablagh : " + sumMablagh  +" ,darsadTakhfif:" + darsadTakhfif  );
                }
                else
                {
                    mablaghTakhfif = (long) (sumMablagh * (zarib * darsadTakhfif / 100));
                    Log.d("takhfifHajmi" , "tedadRialVazn" + "beEza:" + beEza + "sumMablagh : " + sumMablagh + ",zarib:" + zarib +" ,darsadTakhfif:" + darsadTakhfif  );
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        return mablaghTakhfif;
    }


    /**
     * محاسبه مبلغ تخفیف هر سطر
     * @param tedad تعداد
     * @param mablaghForosh مبلغ فروش
     * @param darsadTakhfif درصد تخفیف
     * @return اگر به درستی محاسبه شود که مبلغ تخفیف سطر برمیگردد وگرنه در صورت بروز خطا -1 برگردانده می شود.
     */
    public long calculateMablaghTakhfifSatr(int tedad, long mablaghForosh, double darsadTakhfif)
    {
        try
        {
            return Math.round((tedad * mablaghForosh) * (darsadTakhfif / 100));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }


    public boolean insertFaktorTakhfifHajmi(Context context, long ccDarkhastFaktor, String codeTakhfif, int ccTakhfifHajmi, String sharhTakhfif, double darsadTakhfif, long mablaghTakhfif, int ForJayezeh)
    {
        try
        {
            Log.d("takhfifHajmi","insertFaktorTakhfifHajmi-mablaghTakhfif: "+ mablaghTakhfif + " , (float)mablaghTakhfif:"+(float)mablaghTakhfif);
            DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(context);
            DarkhastFaktorTakhfifModel model = new DarkhastFaktorTakhfifModel();
            model.setCcDarkhastFaktor(ccDarkhastFaktor);
            model.setCcTakhfif(ccTakhfifHajmi);
            model.setSharhTakhfif(sharhTakhfif);
            model.setCodeNoeTakhfif(Integer.parseInt(codeTakhfif));
            model.setDarsadTakhfif((float) darsadTakhfif);
            model.setMablaghTakhfif(mablaghTakhfif);
            model.setExtraProp_ForJayezeh(ForJayezeh);
            model.setExtraProp_IsTakhfifMazad(0);
            darkhastFaktorTakhfifDAO.insert(model);
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "CalculateDiscount", "", "InsertFaktorTakhfifHajmi", "");
            return false;
        }
    }


    public boolean insertFaktorSatrTakhfifHajmi(Context context, long ccDarkhastFaktorSatr, String codeTakhfifHajmi, int ccTakhfifHajmi, String sharhTakhfif, double darsadTakhfif, long mablaghTakhfif, int ForJayezeh, int olaviat)
    {
        try
        {
            DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(context);
            DarkhastFaktorSatrTakhfifModel darkhastFaktorSatrTakhfifModel = new DarkhastFaktorSatrTakhfifModel();

            darkhastFaktorSatrTakhfifModel.setCcDarkhastFaktorSatr(ccDarkhastFaktorSatr);
            darkhastFaktorSatrTakhfifModel.setCcTakhfif(ccTakhfifHajmi);
            darkhastFaktorSatrTakhfifModel.setSharhTakhfif(sharhTakhfif);
            darkhastFaktorSatrTakhfifModel.setCodeNoeTakhfif(Integer.parseInt(codeTakhfifHajmi));
            darkhastFaktorSatrTakhfifModel.setDarsadTakhfif((float) darsadTakhfif);
            darkhastFaktorSatrTakhfifModel.setMablaghTakhfif(mablaghTakhfif);
            darkhastFaktorSatrTakhfifModel.setExtraProp_ForJayezeh(ForJayezeh);
            darkhastFaktorSatrTakhfifModel.setExtraProp_Olaviat(olaviat);

            darkhastFaktorSatrTakhfifDAO.insert(darkhastFaktorSatrTakhfifModel);
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "CalculateDiscount", "", "insertFaktorSatrTakhfifHajmi", "");
            return false;
        }
    }


    public void updateMablaghTakhfifDarkhastFaktor(Context context, long ccDarkhastFaktor , int ccTakhfif , long mablaghTakhfifTitr , long sumMablaghTakhfifSatr , String allMablaghTakhfifSatr)
    {
        DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(context);
        int intMaxDiff = 10;
        try
        {
            intMaxDiff = Integer.parseInt(new ParameterChildDAO(context).getValueByccChildParameter(Constants.CC_CHILD_MAX_DIFF_TAKHFIF_TITR_BY_SATR()));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "CalculateDiscount", "", "updateMablaghTakhfifDarkhastFaktor", "");
        }

        long diff = Math.abs(mablaghTakhfifTitr - sumMablaghTakhfifSatr);
        if (diff > 0 && diff <= intMaxDiff)
        {
            Log.d("takhfif" , "in diff , mablaghTakhfifTitr : " + mablaghTakhfifTitr + " , sumMablaghTakhfifSatr : " + sumMablaghTakhfifSatr);
            darkhastFaktorTakhfifDAO.updateMablaghTakhfif(ccDarkhastFaktor , ccTakhfif , mablaghTakhfifTitr,sumMablaghTakhfifSatr);
        }

    }

    public void setLogToDB(Context context, int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(context, logType, message, logClass, logActivity, functionParent, functionChild);
    }


    /**
     *
     * @param ccDarkhastFaktor
     * @param ccJayezeh
     * @param codeNoeJayezeh اگر 1 باشد به این معنی است که جایزه به صورت اتوماتیک داده شده ولی اگر 2 باشد به این معنی است که تیتر جایزه ثبت شده و فروشنده باید جایزه را انتخاب نماید(یعنی جایزه انتخابی است)
     * @param SharhJayezeh
     * @param ccKala
     * @param ccKalaCode
     * @param Tedad
     * @return
     */
    public boolean insertJayezeh(Context context, long ccDarkhastFaktor, int ccJayezeh, int codeNoeJayezeh, String SharhJayezeh,int ccKala, int ccKalaCode, int Tedad)
    {
        Log.d("jayezeh" , "ccDarkhastFaktor insert jayezeh : " + ccDarkhastFaktor);
        try
        {
            DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(context);
            DarkhastFaktorJayezehModel darkhastFaktorJayezehModel = new DarkhastFaktorJayezehModel();
            darkhastFaktorJayezehModel.setCcDarkhastFaktor(ccDarkhastFaktor);
            darkhastFaktorJayezehModel.setCcJayezeh(ccJayezeh);
            darkhastFaktorJayezehModel.setSharh(SharhJayezeh);
            darkhastFaktorJayezehModel.setCcKala(ccKala);
            darkhastFaktorJayezehModel.setCcKalaCode(ccKalaCode);
            darkhastFaktorJayezehModel.setTedad(Tedad);
            darkhastFaktorJayezehModel.setExtraProp_IsJayezehEntekhabi(0);
            darkhastFaktorJayezehModel.setExtraProp_CodeNoeJayezeh(codeNoeJayezeh);
            return darkhastFaktorJayezehDAO.insert(darkhastFaktorJayezehModel);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), "DiscountCalculation", "", "insertJayezeh", "");
            return false;
        }
    }

}
