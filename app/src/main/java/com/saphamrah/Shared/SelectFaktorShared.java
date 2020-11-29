package com.saphamrah.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

public class SelectFaktorShared
{

    private SharedPreferences sharedPreferences;
    private Context context;

    private String ccMoshtary = "ezQI6YvaOK";            //int
    private String ccForoshandeh = "oJbuCX7HpM";         //int
    private String saatVorodBeMaghazeh = "ZxwlT7teMz";
    private String saatKhorojAzMaghazeh = "pRY4qdB4vg";
    private String latitude = "Uvruy2sRxS";
    private String longitude = "vxnozSmtNi";
    private String haveMojoodiGiri = "N5qPJhEKLq"; //boolean

    private String ccDarkhastFaktor = "evqrgUwHaf"; //long
    private String ccGorohNoeMoshtary = "LxHXPfiPiu"; //int
    private String ccGorohNoeSenf = "wNAhpTnzwO";     //int
    private String hadeAghalMablaghKharid = "fbXiuqKICS";   //double
    private String hadeAghalTedadKharid = "fbXiuqKIDS";   //double
    private String hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid = "EtMOpAwWFC";    //double
    /*private String etebarRialy = "sqDvZEybLw";   //double
    private String etebarTedady = "NgVJofVjDN";   //double*/
    private String bedehy = "kQTCvQPyvY";   //double
    private String etebarRial = "nuTUgUVLKf";   //double
    private String etebarTedad = "kkNwvz";   //double
    private String etebarModat = "MFzCRu";   //double
    private String etebarBargashty = "ANxQZj";   //double
    private String etebarTedadBargashty = "jKddjB";   //double
    private String etebarModatBargashty = "RywtgC";   //double
    private String etebarAsnad = "UBuESf";   //double
    private String etebarTedadAsnad = "VymrCR";   //double
    private String etebarModatAsnad = "HWrAfC";   //double
    private String ccChildCodeNoeVosol = "cenCCpKDT";   //int
    private String sumTedadAghlamPishnehadi_BiSetareh = "HuPaUOwwHa";   //int
    private String sumTedadAghlamPishnehadi_SetarehDar = "sYdSvabMfO";   //int
    private String maxRoozFaktorMandehDar_NoeMoshtary = "HoPSiwAgJt";   //int
    private String maxTedadFaktorMandehDar_NoeMoshtary = "FHaolWDgDB";   //int
    private String mianginForosh = "aItGAHPZSd";                        //double
    private String modatCheckBargashtyForoshandeh = "AWeREfTuHV";         //int
    private String tedadChekBargashtyMoshtary = "RmbGaTUUrR";         //int
    private String tedadChekBargashtyForoshandeh = "nCiXKVxTKB";         //int
    private String bargashtyMoshtary = "JMvdncgTmd";         //double
    private String bargashtyForoshandeh = "HhBAMjKqae";         //double
    private String moavaghForoshandeh = "XzeDXgbarM";         //double
    private String tedadFaktorMandehDarNoeMoshtary_BishAzRooz = "CuiDMRtcGx";         //int
    private String asnadForoshandeh = "YqubqTNVsK";         //double
    private String roozVagheii = "apgSskTshq";         //int
    private String rialCheckBargashty_NoeMoshtary = "SWQsIxmVbM";       //double
    private String tedadCheckBargashty_NoeMoshtary = "tjxwBvChfl";         //int
    private String modatCheckBargashty_NoeMoshtary = "msFGaQRkqm";         //int
    private String sumMoavagh = "rBLiHeeQHB";         //double
    private String accurancy = "XiVfCvPOdi";         //double
    private String bearing = "iHnJLwfsLX";         //double
    private String speed = "LeYeeXDFAN";         //double
    private String altitude = "IfMKlzaSkb";         //double
    private String isForEdit = "FwWBsxsnRB";         //boolean
    private String codeMely = "sUICuQVJwu";            //string
    private String shenasehMely = "tDDmbwFFmJ";            //string
    private String dateOfServer = "VlPUOgmaqS";            //string
    private String ccMoshtaryParent = "rSBhGBSeju";         //int
    private String check_Kala1Setareh = "xGmIEGRKym";       //boolean
    private String isMoshtaryJadid = "vyjwEXxzEk";       //boolean
    private String updateSaatKhoroj = "hbQpmsnLFG";       //boolean
    private String vaznMashin = "YXXsCRKlXx";         //int
    private String hajmMashin = "tZlHQyXCxb";         //double
    private String ccMarkazForosh = "XBNjcYJHWK";         //int
    private String ccSazmanForosh = "XBNjcYJHfd";         //int
    private String ccMarkazSazmanForosh = "mMdC8fdggr";        //int
    private String moshtaryDarajeh = "TXWfDnQraw";         //int
    private String moshtaryJadidDarajeh = "HFdumLwYMD";         //int
    private String modatRoozRaasGiriFaktor = "bGGLoWfWJF";         //int
    private String tedadResidNaghd = "LstNAcmeKO";         //int
    private String tedadResid = "FizNMONxia";         //int
    private String mablaghMaxFaktorKhordeh_MoshtaryJadid = "YxdLPwVbhC";       //long
    private String mablaghMaxFaktorOmdeh_MoshtaryJadid = "wiHpmSEToH";       //long
    private String moshtaryForoshandehFlag = "EFjvaojCeJ";       //boolean
    private String isEtebarCheckBargashty = "LSAyPJfE1M";       //boolean
    private String isMojazForResid = "fI0MlJ0Exc";       //boolean
    private String isEtebarAsnad = "fI0MlrtExc";       //boolean
    private String ccMasirRooz = "QrojCgmI0a";
    private String ccTakhfifJayezes = "XzeDwHafE"; // String -> put ccTakhfif Of goods that have bonus
    private String insertDarkhastFaktorSatrForMamorPakhsh = "jBkUqkQsXY";
    private String verifiedMarjoee = "kmQ4w55fdw"; //String -> اگر به فرم مرجوعی رفته باشد این فیلد برابر 1 می شود و بدین معنی است که کاربر مرجوعی را تایید و ثبت کرده و دریافتش پرداختش ثبت شده وگرنه بدین معنی است که دریافت پرداخت برای مرجوعی ثبت نشده و مرجوعی های این فاکتور حذف میشود.
    private String ccKalaCodesOfKalaAsasi = "XfdHEx32";
    private String mablaghHavaleh = "XfdHExEa"; // مبلغ حواله مشتری جهت محاسبه اعتبار مامورپخش
    private String isMorajehShodeh = "XfdHExFd"; // برای مشتری عدم سفارش و عدم درخواست صبت شده است
    private String minModatHozor = "XfdRgxFd";// پارامتر حداقل مدت حضور در مغازه مشتری جهت ثبت عدم


    public SelectFaktorShared(Context context)
    {
        this.context = context;
        final String SHARED_NAME = "b0tplyv05izlu";
        try
        {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString() + "\n shared name : " + SHARED_NAME, "SelectFaktorShared", "", "constructor", "");
        }
    }


    ////////////////////////////////////////// PUT //////////////////////////////////////////

    public boolean putString(String key , String value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key , value);
            editor.apply();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "SelectFaktorShared", "", "putString", "");
            return false;
        }
    }


    public boolean putInt(String key , int value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key , value);
            editor.apply();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "SelectFaktorShared", "", "putInt", "");
            return false;
        }
    }

    public boolean putFloat(String key , float value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(key , value);
            editor.apply();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "SelectFaktorShared", "", "putFloat", "");
            return false;
        }
    }

    public boolean putBoolean(String key , boolean value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key , value);
            editor.apply();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "SelectFaktorShared", "", "putBoolean", "");
            return false;
        }
    }

    public boolean putLong(String key , long value)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key , value);
            editor.apply();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , value : " + value, "SelectFaktorShared", "", "putLong", "");
            return false;
        }
    }


    ////////////////////////////////////////// GET //////////////////////////////////////////


    public String getString(String key , String defaultValue)
    {
        try
        {
            return sharedPreferences.getString(key , defaultValue);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "SelectFaktorShared", "", "getString", "");
            return defaultValue;
        }
    }


    public int getInt(String key , int defaultValue)
    {
        try
        {
            return sharedPreferences.getInt(key , defaultValue);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "SelectFaktorShared", "", "getInt", "");
            return defaultValue;
        }
    }


    public float getFloat(String key , float defaultValue)
    {
        try
        {
            return sharedPreferences.getFloat(key , defaultValue);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "SelectFaktorShared", "", "getFloat", "");
            return defaultValue;
        }
    }

    public long getLong(String key , long defaultValue)
    {
        try
        {
            return sharedPreferences.getLong(key , defaultValue);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "SelectFaktorShared", "", "getLong", "");
            return defaultValue;
        }
    }


    public boolean getBoolean(String key , boolean defaultValue)
    {
        try
        {
            return sharedPreferences.getBoolean(key , defaultValue);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString() + "\n key : " + key + " , defaultValue : " + defaultValue, "SelectFaktorShared", "", "getBoolean", "");
            return defaultValue;
        }
    }


    public void remove(String key)
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), "SelectFaktorShared", "", "remove", "");
        }
    }

    public void removeAll()
    {
        try
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ccMoshtary);
            editor.remove(ccForoshandeh);
            editor.remove(saatVorodBeMaghazeh);
            editor.remove(saatKhorojAzMaghazeh);
            editor.remove(latitude);
            editor.remove(longitude);
            editor.remove(haveMojoodiGiri);
            editor.remove(ccDarkhastFaktor);
            editor.remove(ccGorohNoeMoshtary);
            editor.remove(ccGorohNoeSenf);
            editor.remove(hadeAghalMablaghKharid);
            editor.remove(hadeAghalTedadKharid);
            editor.remove(hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid);
            /*editor.remove(etebarRialy);
            editor.remove(etebarTedady);*/
            editor.remove(bedehy);
            editor.remove(etebarRial);
            editor.remove(etebarTedad);
            editor.remove(etebarModat);
            editor.remove(etebarBargashty);
            editor.remove(etebarTedadBargashty);
            editor.remove(etebarModatBargashty);
            editor.remove(etebarAsnad);
            editor.remove(etebarTedadAsnad);
            editor.remove(etebarModatAsnad);
            editor.remove(ccChildCodeNoeVosol);
            editor.remove(sumTedadAghlamPishnehadi_BiSetareh);
            editor.remove(sumTedadAghlamPishnehadi_SetarehDar);
            editor.remove(maxRoozFaktorMandehDar_NoeMoshtary);
            editor.remove(maxTedadFaktorMandehDar_NoeMoshtary);
            editor.remove(mianginForosh);
            editor.remove(modatCheckBargashtyForoshandeh);
            editor.remove(tedadChekBargashtyMoshtary);
            editor.remove(tedadChekBargashtyForoshandeh);
            editor.remove(bargashtyMoshtary);
            editor.remove(bargashtyForoshandeh);
            editor.remove(moavaghForoshandeh);
            editor.remove(tedadFaktorMandehDarNoeMoshtary_BishAzRooz);
            editor.remove(asnadForoshandeh);
            editor.remove(roozVagheii);
            editor.remove(rialCheckBargashty_NoeMoshtary);
            editor.remove(tedadCheckBargashty_NoeMoshtary);
            editor.remove(modatCheckBargashty_NoeMoshtary);
            editor.remove(sumMoavagh);
            editor.remove(accurancy);
            editor.remove(bearing);
            editor.remove(speed);
            editor.remove(altitude);
            editor.remove(isForEdit);
            editor.remove(codeMely);
            editor.remove(shenasehMely);
            editor.remove(dateOfServer);
            editor.remove(ccMoshtaryParent);
            editor.remove(check_Kala1Setareh);
            editor.remove(isMoshtaryJadid);
            editor.remove(updateSaatKhoroj);
            editor.remove(vaznMashin);
            editor.remove(hajmMashin);
            editor.remove(ccMarkazForosh);
            editor.remove(ccSazmanForosh);
            editor.remove(ccMarkazSazmanForosh);
            editor.remove(moshtaryDarajeh);
            editor.remove(moshtaryJadidDarajeh);
            editor.remove(modatRoozRaasGiriFaktor);
            editor.remove(tedadResidNaghd);
            editor.remove(tedadResid);
            editor.remove(mablaghMaxFaktorKhordeh_MoshtaryJadid);
            editor.remove(mablaghMaxFaktorOmdeh_MoshtaryJadid);
            editor.remove(moshtaryForoshandehFlag);
            editor.remove(isMojazForResid);
            editor.remove(isEtebarAsnad);
            editor.remove(isEtebarCheckBargashty);
            editor.remove(ccMasirRooz);
            editor.remove(ccTakhfifJayezes);
            editor.remove(insertDarkhastFaktorSatrForMamorPakhsh);
            editor.remove(verifiedMarjoee);
            editor.remove(ccKalaCodesOfKalaAsasi);
            editor.remove(mablaghHavaleh);
            editor.remove(isMorajehShodeh);
            editor.remove(minModatHozor);
            editor.apply();

            editor.clear();
            editor.apply();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "SelectFaktorShared", "", "removeAll", "");
        }
    }


    public void setDefaultRequestInfo()
    {
        putLong(getCcDarkhastFaktor() , 0L);
        putInt(getCcMoshtary() , 0);
        putInt(getCcGorohNoeMoshtary() , 0);
        putInt(getCcGorohNoeSenf() , 0);
        putFloat(getHadeAghalMablaghKharid() , 0);
        putFloat(getHadeAghalTedadKharid() , 0);
        putInt(getHadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid() , 0);
        /*putInt(getEtebarRialy() , 0);
        putInt(getEtebarTedady() , 0);*/
        putInt(getBedehy() , 0);
        putFloat(getEtebarRial() , 0);
        putFloat(getEtebarTedad() , 0);
        putFloat(getEtebarModat() , 0);
        putFloat(getEtebarBargashty() , 0);
        putFloat(getEtebarTedadBargashty() , 0);
        putFloat(getEtebarModatBargashty() , 0);
        putFloat(getEtebarAsnad() , 0);
        putFloat(getEtebarTedadAsnad() , 0);
        putFloat(getEtebarModatAsnad() , 0);
        putInt(getccChildCodeNoeVosol() , -1);
        putInt(getSumMoavagh() , 0);
        /*putFloat(getLatitude() , 0f);
        putFloat(getLongitude() , 0f);*/
        putString(getLatitude() , "0");
        putString(getLongitude() , "0");
        putBoolean(getIsForEdit() , false);
        putString(getCodeMely() , "");
        putString(getShenasehMely() , "");
        putBoolean(getCheck_Kala1Setareh() , true);
        putBoolean(getIsMoshtaryJadid() , false);
        putBoolean(getUpdateSaatKhoroj() , false);
        putInt(getVaznMashin() , 0);
        putInt(getHajmMashin() , 0);
        putInt(getCcMarkazForosh() , 0);
        putInt(getCcSazmanForosh() , 0);
        putInt(getCcMarkazSazmanForosh() , 0);
        putInt(getMoshtaryJadidDarajeh() , 4);
        putInt(getMoshtaryDarajeh() , 9);
        putInt(getModatRoozRaasGiriFaktor() , 0);
        putLong(getMablaghMaxFaktorOmdeh_MoshtaryJadid() , 0);
        putLong(getMablaghMaxFaktorKhordeh_MoshtaryJadid() , 0);
        putBoolean(getMoshtaryForoshandehFlag() , false);
        putInt(getCcMasirRooz() , 0);
        putString(getCcTakhfifJayezes() , "");
        putBoolean(getInsertDarkhastFaktorSatrForMamorPakhsh() , false);
        putBoolean(getVerifiedMarjoee() , false);
        putString(getCcKalaCodesOfKalaAsasi(), "");
        putLong(getMablaghHavaleh(), 0);
        putBoolean(getIsMorajehShodeh() , false);
        putInt(getMinModatHozor() , 0);
    }


    public String getCcMoshtary() {
        return ccMoshtary;
    }
    public String getCcForoshandeh() {
        return ccForoshandeh;
    }
    public String getSaatVorodBeMaghazeh() {
        return saatVorodBeMaghazeh;
    }
    public String getSaatKhorojAzMaghazeh() {
        return saatKhorojAzMaghazeh;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getHaveMojoodiGiri() {
        return haveMojoodiGiri;
    }
    public String getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }
    public String getCcGorohNoeMoshtary() {
        return ccGorohNoeMoshtary;
    }
    public String getCcGorohNoeSenf() {
        return ccGorohNoeSenf;
    }
    public String getHadeAghalMablaghKharid() {
        return hadeAghalMablaghKharid;
    }
    public String getHadeAghalTedadKharid() {
        return hadeAghalTedadKharid;
    }
    public String getHadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid() {
        return hadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid;
    }
    /*public String getEtebarRialy() {
        return etebarRialy;
    }
    public String getEtebarTedady() {
        return etebarTedady;
    }*/
    public String getBedehy() {
        return bedehy;
    }
    public String getEtebarRial()
    {
        return etebarRial;
    }
    public String getEtebarTedad()
    {
        return etebarTedad;
    }
    public String getEtebarModat()
    {
        return etebarModat;
    }
    public String getEtebarBargashty()
    {
        return etebarBargashty;
    }
    public String getEtebarTedadBargashty()
    {
        return etebarTedadBargashty;
    }
    public String getEtebarModatBargashty()
    {
        return etebarModatBargashty;
    }
    public String getEtebarAsnad()
    {
        return etebarAsnad;
    }
    public String getEtebarTedadAsnad()
    {
        return etebarTedadAsnad;
    }
    public String getEtebarModatAsnad()
    {
        return etebarModatAsnad;
    }
    public String getccChildCodeNoeVosol() {
        return ccChildCodeNoeVosol;
    }
    public String getSumTedadAghlamPishnehadi_BiSetareh() {
        return sumTedadAghlamPishnehadi_BiSetareh;
    }
    public String getSumTedadAghlamPishnehadi_SetarehDar() {
        return sumTedadAghlamPishnehadi_SetarehDar;
    }
    public String getMaxRoozFaktorMandehDar_NoeMoshtary() {
        return maxRoozFaktorMandehDar_NoeMoshtary;
    }
    public String getMaxTedadFaktorMandehDar_NoeMoshtary() {
        return maxTedadFaktorMandehDar_NoeMoshtary;
    }
    public String getMianginForosh() {
        return mianginForosh;
    }
    public String getModatCheckBargashtyForoshandeh() {
        return modatCheckBargashtyForoshandeh;
    }
    public String getTedadChekBargashtyMoshtary() {
        return tedadChekBargashtyMoshtary;
    }
    public String getTedadChekBargashtyForoshandeh() {
        return tedadChekBargashtyForoshandeh;
    }
    public String getBargashtyMoshtary() {
        return bargashtyMoshtary;
    }
    public String getBargashtyForoshandeh() {
        return bargashtyForoshandeh;
    }
    public String getMoavaghForoshandeh() {
        return moavaghForoshandeh;
    }
    public String getTedadFaktorMandehDarNoeMoshtary_BishAzRooz() {
        return tedadFaktorMandehDarNoeMoshtary_BishAzRooz;
    }
    public String getAsnadForoshandeh() {
        return asnadForoshandeh;
    }
    public String getRoozVagheii() {
        return roozVagheii;
    }
    public String getRialCheckBargashty_NoeMoshtary() {
        return rialCheckBargashty_NoeMoshtary;
    }
    public String getTedadCheckBargashty_NoeMoshtary() {
        return tedadCheckBargashty_NoeMoshtary;
    }
    public String getModatCheckBargashty_NoeMoshtary() {
        return modatCheckBargashty_NoeMoshtary;
    }
    public String getSumMoavagh() {
        return sumMoavagh;
    }
    public String getAccurancy() {
        return accurancy;
    }
    public String getBearing() {
        return bearing;
    }
    public String getSpeed() {
        return speed;
    }
    public String getAltitude() {
        return altitude;
    }
    public String getIsForEdit() {
        return isForEdit;
    }
    public String getCodeMely() {
        return codeMely;
    }
    public String getShenasehMely() {
        return shenasehMely;
    }
    public String getDateOfServer() {
        return dateOfServer;
    }
    public String getCcMoshtaryParent() {
        return ccMoshtaryParent;
    }
    public String getCheck_Kala1Setareh() {
        return check_Kala1Setareh;
    }
    public String getIsMoshtaryJadid() {
        return isMoshtaryJadid;
    }
    public String getUpdateSaatKhoroj() {
        return updateSaatKhoroj;
    }
    public String getVaznMashin() {
        return vaznMashin;
    }
    public String getHajmMashin() {
        return hajmMashin;
    }
    public String getCcMarkazForosh() {
        return ccMarkazForosh;
    }
    public String getCcSazmanForosh() {
        return ccSazmanForosh;
    }
    public String getCcMarkazSazmanForosh()
    {
        return ccMarkazSazmanForosh;
    }
    public String getMoshtaryDarajeh() {
        return moshtaryDarajeh;
    }
    public String getMoshtaryJadidDarajeh() {
        return moshtaryJadidDarajeh;
    }
    public String getModatRoozRaasGiriFaktor() {
        return modatRoozRaasGiriFaktor;
    }
    public String getTedadResidNaghd() {
        return tedadResidNaghd;
    }
    public String getTedadResid() {
        return tedadResid;
    }
    public String getMablaghMaxFaktorKhordeh_MoshtaryJadid() {
        return mablaghMaxFaktorKhordeh_MoshtaryJadid;
    }
    public String getMablaghMaxFaktorOmdeh_MoshtaryJadid() {
        return mablaghMaxFaktorOmdeh_MoshtaryJadid;
    }
    public String getMoshtaryForoshandehFlag() {
        return moshtaryForoshandehFlag;
    }
    public String getIsEtebarCheckBargashty() {
        return isEtebarCheckBargashty;
    }
    public String getIsMojazForResid() {
        return isMojazForResid;
    }
    public String getIsEtebarAsnad() {
        return isEtebarAsnad;
    }
    public String getCcMasirRooz() {
        return ccMasirRooz;
    }
    public String getCcTakhfifJayezes() {
        return ccTakhfifJayezes;
    }
    public String getInsertDarkhastFaktorSatrForMamorPakhsh() {
        return insertDarkhastFaktorSatrForMamorPakhsh;
    }
    public void setInsertDarkhastFaktorSatrForMamorPakhsh(String insertDarkhastFaktorSatrForMamorPakhsh) {
        this.insertDarkhastFaktorSatrForMamorPakhsh = insertDarkhastFaktorSatrForMamorPakhsh;
    }
    public String getVerifiedMarjoee()
    {
        return verifiedMarjoee;
    }
    public String getCcKalaCodesOfKalaAsasi()
    {
        return ccKalaCodesOfKalaAsasi;
    }
    public String getMablaghHavaleh() {
        return mablaghHavaleh;
    }
    public String getIsMorajehShodeh() {
        return isMorajehShodeh;
    }
    public String getMinModatHozor() {
        return minModatHozor;
    }

    /*selectFaktorShared.putInt(selectFaktorShared.getCcDarkhastFaktor() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getCcMoshtary() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getCcGorohNoeMoshtary() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getCcGorohNoeSenf() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getHadeAghalMablaghKharid() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getHadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getEtebarRialy() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getEtebarTedady() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getBedehy() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getMandehEtebar() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getSumMoavagh() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getLatitude() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getLongitude() , 0);
        selectFaktorShared.putBoolean(selectFaktorShared.getIsForEdit() , false);
        selectFaktorShared.putString(selectFaktorShared.getCodeMely() , "");
        selectFaktorShared.putString(selectFaktorShared.getShenasehMely() , "");
        selectFaktorShared.putBoolean(selectFaktorShared.getCheck_Kala1Setareh() , true);
        selectFaktorShared.putBoolean(selectFaktorShared.getIsMoshtaryJadid() , false);
        selectFaktorShared.putBoolean(selectFaktorShared.getUpdateSaatKhoroj() , false);
        selectFaktorShared.putInt(selectFaktorShared.getVaznMashin() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getHajmMashin() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getCcMarkazForosh() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getCcMarkazSazmanForosh() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getMoshtaryJadidDarajeh() , 4);
        selectFaktorShared.putInt(selectFaktorShared.getMoshtaryDarajeh() , 9);
        selectFaktorShared.putInt(selectFaktorShared.getModatRoozRaasGiriFaktor() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getMablaghMaxFaktorOmdeh_MoshtaryJadid() , 0);
        selectFaktorShared.putInt(selectFaktorShared.getMablaghMaxFaktorKhordeh_MoshtaryJadid() , 0);
        selectFaktorShared.putBoolean(selectFaktorShared.getMoshtaryForoshandehFlag() , false);*/



}
