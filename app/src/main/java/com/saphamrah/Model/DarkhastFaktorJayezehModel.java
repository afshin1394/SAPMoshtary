package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

public class DarkhastFaktorJayezehModel
{

    private static final int CODE_NOE_JAYEZEH_AUTO = 1;
    private static final int CODE_NOE_JAYEZEH_ENTEKHABI = 2;

    public static int CodeNoeJayezehAuto()
    {
        return CODE_NOE_JAYEZEH_AUTO;
    }
    public static int CodeNoeJayezehEntekhabi()
    {
        return CODE_NOE_JAYEZEH_ENTEKHABI;
    }


    private static final String TABLE_NAME = "DarkhastFaktorJayezeh";
    private static final String COLUMN_ccDarkhastFaktorJayezeh = "ccDarkhastFaktorJayezeh";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccJayezeh = "ccJayezeh";
    private static final String COLUMN_Sharh = "Sharh";
    private static final String COLUMN_ccKala = "ccKala";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_Tedad = "Tedad";
    private static final String COLUMN_ExtraProp_IsJayezehEntekhabi = "ExtraProp_IsJayezehEntekhabi";
    private static final String COLUMN_ExtraProp_ccJayezehTakhfif = "ExtraProp_ccJayezehTakhfif";
    private static final String COLUMN_ExtraProp_CodeNoeJayezeh = "ExtraProp_CodeNoeJayezeh";
    private static final String COLUMN_ExtraProp_ccJayezehSatr = "ExtraProp_ccJayezehSatr";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDarkhastFaktorJayezeh() {
        return COLUMN_ccDarkhastFaktorJayezeh;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccJayezeh() {
        return COLUMN_ccJayezeh;
    }
    public static String COLUMN_Sharh() {
        return COLUMN_Sharh;
    }
    public static String COLUMN_ccKala() {
        return COLUMN_ccKala;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_Tedad() {
        return COLUMN_Tedad;
    }
    public static String COLUMN_ExtraProp_IsJayezehEntekhabi() {
        return COLUMN_ExtraProp_IsJayezehEntekhabi;
    }
    public static String COLUMN_ExtraProp_ccJayezehTakhfif() {
        return COLUMN_ExtraProp_ccJayezehTakhfif;
    }
    public static String COLUMN_ExtraProp_CodeNoeJayezeh() {
        return COLUMN_ExtraProp_CodeNoeJayezeh;
    }
    public static String COLUMN_ExtraProp_ccJayezehSatr() {
        return COLUMN_ExtraProp_ccJayezehSatr;
    }




    private int ccDarkhastFaktorJayezeh;
    private long ccDarkhastFaktor;
    private int ccJayezeh;
    private String Sharh;
    private int ccKala;
    private int ccKalaCode;
    private int Tedad;
    private int ExtraProp_IsJayezehEntekhabi;
    private int ExtraProp_ccJayezehTakhfif;
    private int ExtraProp_CodeNoeJayezeh;
    private int ExtraProp_ccJayezehSatr;

    // this field used for print
    private float mablaghMasrafKonandeh;


    public int getCcDarkhastFaktorJayezeh() {
        return ccDarkhastFaktorJayezeh;
    }

    public void setCcDarkhastFaktorJayezeh(int ccDarkhastFaktorJayezeh) {
        this.ccDarkhastFaktorJayezeh = ccDarkhastFaktorJayezeh;
    }

    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcJayezeh() {
        return ccJayezeh;
    }

    public void setCcJayezeh(int ccJayezeh) {
        this.ccJayezeh = ccJayezeh;
    }

    public String getSharh() {
        return Sharh;
    }

    public void setSharh(String sharh) {
        Sharh = sharh;
    }

    public int getCcKala() {
        return ccKala;
    }

    public void setCcKala(int ccKala) {
        this.ccKala = ccKala;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public int getTedad() {
        return Tedad;
    }

    public void setTedad(int tedad) {
        Tedad = tedad;
    }

    public int getExtraProp_IsJayezehEntekhabi() {
        return ExtraProp_IsJayezehEntekhabi;
    }

    public void setExtraProp_IsJayezehEntekhabi(int extraProp_IsJayezehEntekhabi) {
        ExtraProp_IsJayezehEntekhabi = extraProp_IsJayezehEntekhabi;
    }


    public int getExtraProp_ccJayezehTakhfif() {
        return ExtraProp_ccJayezehTakhfif;
    }

    public void setExtraProp_ccJayezehTakhfif(int extraProp_ccJayezehTakhfif) {
        ExtraProp_ccJayezehTakhfif = extraProp_ccJayezehTakhfif;
    }

    public int getExtraProp_CodeNoeJayezeh()
    {
        return ExtraProp_CodeNoeJayezeh;
    }

    public void setExtraProp_CodeNoeJayezeh(int extraProp_CodeNoeJayezeh)
    {
        ExtraProp_CodeNoeJayezeh = extraProp_CodeNoeJayezeh;
    }

    public int getExtraProp_ccJayezehSatr()
    {
        return ExtraProp_ccJayezehSatr;
    }

    public void setExtraProp_ccJayezehSatr(int extraProp_ccJayezehSatr)
    {
        ExtraProp_ccJayezehSatr = extraProp_ccJayezehSatr;
    }

    public float getMablaghMasrafKonandeh()
    {
        return mablaghMasrafKonandeh;
    }

    public void setMablaghMasrafKonandeh(float mablaghMasrafKonandeh)
    {
        this.mablaghMasrafKonandeh = mablaghMasrafKonandeh;
    }

    public String toJsonString()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccDarkhastFaktorJayezeh() , ccDarkhastFaktorJayezeh);
        jsonObject.addProperty(COLUMN_ccDarkhastFaktor() , ccDarkhastFaktorJayezeh);
        jsonObject.addProperty(COLUMN_ccJayezeh() , ccDarkhastFaktorJayezeh);
        jsonObject.addProperty(COLUMN_Sharh() , ccDarkhastFaktorJayezeh);
        jsonObject.addProperty(COLUMN_ccKala() , ccDarkhastFaktorJayezeh);
        jsonObject.addProperty(COLUMN_ccKalaCode() , ccDarkhastFaktorJayezeh);
        jsonObject.addProperty(COLUMN_Tedad() , ccDarkhastFaktorJayezeh);
        return jsonObject.toString();
    }


    @NonNull
    @Override
    public String toString()
    {
        return "DarkhastFaktorJayezehModel{" +
                "ccDarkhastFaktorJayezeh=" + ccDarkhastFaktorJayezeh +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccJayezeh=" + ccJayezeh +
                ", Sharh='" + Sharh + '\'' +
                ", ccKala=" + ccKala +
                ", ccKalaCode=" + ccKalaCode +
                ", Tedad=" + Tedad +
                ", ExtraProp_IsJayezehEntekhabi=" + ExtraProp_IsJayezehEntekhabi +
                ", ExtraProp_ccJayezehTakhfif=" + ExtraProp_ccJayezehTakhfif +
                ", ExtraProp_CodeNoeJayezeh=" + ExtraProp_CodeNoeJayezeh +
                ", ExtraProp_ccJayezehSatr=" + ExtraProp_ccJayezehSatr +
                '}';
    }
}
