package com.saphamrah.Model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;

import org.json.JSONObject;

public class DarkhastFaktorTakhfifModel
{

    private static final String TABLE_NAME = "DarkhastFaktorTakhfif";
    private static final String COLUMN_ccDarkhastFaktorTakhfif = "ccDarkhastFaktorTakhfif";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccTakhfif = "ccTakhfif";
    private static final String COLUMN_SharhTakhfif = "SharhTakhfif";
    private static final String COLUMN_CodeNoeTakhfif = "CodeNoeTakhfif";
    private static final String COLUMN_DarsadTakhfif = "DarsadTakhfif";
    private static final String COLUMN_MablaghTakhfif = "MablaghTakhfif";
    private static final String COLUMN_ExtraProp_ForJayezeh = "ExtraProp_ForJayezeh";
    private static final String COLUMN_ExtraProp_IsTakhfifMazad = "ExtraProp_IsTakhfifMazad";
    private static final String COLUMN_ExtraProp_MustSendToSql = "ExtraProp_MustSendToSql";
    private static final String COLUMN_ExtraProp_ccJayezehTakhfif = "ExtraProp_ccJayezehTakhfif";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDarkhastFaktorTakhfif() {
        return COLUMN_ccDarkhastFaktorTakhfif;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccTakhfif() {
        return COLUMN_ccTakhfif;
    }
    public static String COLUMN_SharhTakhfif() {
        return COLUMN_SharhTakhfif;
    }
    public static String COLUMN_CodeNoeTakhfif() {
        return COLUMN_CodeNoeTakhfif;
    }
    public static String COLUMN_DarsadTakhfif() {
        return COLUMN_DarsadTakhfif;
    }
    public static String COLUMN_MablaghTakhfif() {
        return COLUMN_MablaghTakhfif;
    }
    public static String COLUMN_ExtraProp_ForJayezeh() {
        return COLUMN_ExtraProp_ForJayezeh;
    }
    public static String COLUMN_ExtraProp_IsTakhfifMazad() {
        return COLUMN_ExtraProp_IsTakhfifMazad;
    }
    public static String COLUMN_ExtraProp_MustSendToSql() {
        return COLUMN_ExtraProp_MustSendToSql;
    }
    public static String COLUMN_ExtraProp_ccJayezehTakhfif() {
        return COLUMN_ExtraProp_ccJayezehTakhfif;
    }
    public static String COLUMN_ExtraProp_IsOld() {
        return COLUMN_ExtraProp_IsOld;
    }



    private int ccDarkhastFaktorTakhfif;
    public int getCcDarkhastFaktorTakhfif() {
        return ccDarkhastFaktorTakhfif;
    }
    public void setCcDarkhastFaktorTakhfif(int ccDarkhastFaktorTakhfif) {
        this.ccDarkhastFaktorTakhfif = ccDarkhastFaktorTakhfif;
    }


    private long ccDarkhastFaktor;
    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }
    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }


    private int ccTakhfif;
    public int getCcTakhfif() {
        return ccTakhfif;
    }
    public void setCcTakhfif(int ccTakhfif) {
        this.ccTakhfif = ccTakhfif;
    }


    private String SharhTakhfif;
    public String getSharhTakhfif() {
        return SharhTakhfif;
    }
    public void setSharhTakhfif(String sharhTakhfif) {
        SharhTakhfif = sharhTakhfif;
    }


    private int CodeNoeTakhfif;
    public int getCodeNoeTakhfif() {
        return CodeNoeTakhfif;
    }
    public void setCodeNoeTakhfif(int codeNoeTakhfif) {
        CodeNoeTakhfif = codeNoeTakhfif;
    }


    private float DarsadTakhfif;
    public float getDarsadTakhfif() {
        return DarsadTakhfif;
    }
    public void setDarsadTakhfif(float darsadTakhfif) {
        DarsadTakhfif = darsadTakhfif;
    }


    private long MablaghTakhfif;
    public long getMablaghTakhfif() {
        return MablaghTakhfif;
    }
    public void setMablaghTakhfif(long mablaghTakhfif) {
        MablaghTakhfif = mablaghTakhfif;
    }


    private int ExtraProp_ForJayezeh;
    public int getExtraProp_ForJayezeh() {
        return ExtraProp_ForJayezeh;
    }
    public void setExtraProp_ForJayezeh(int extraProp_ForJayezeh) {
        ExtraProp_ForJayezeh = extraProp_ForJayezeh;
    }


    private int ExtraProp_IsTakhfifMazad;
    public int getExtraProp_IsTakhfifMazad() {
        return ExtraProp_IsTakhfifMazad;
    }
    public void setExtraProp_IsTakhfifMazad(int extraProp_IsTakhfifMazad) {
        ExtraProp_IsTakhfifMazad = extraProp_IsTakhfifMazad;
    }


    private int ExtraProp_MustSendToSql;
    public int getExtraProp_MustSendToSql() {
        return ExtraProp_MustSendToSql;
    }
    public void setExtraProp_MustSendToSql(int extraProp_MustSendToSql) {
        ExtraProp_MustSendToSql = extraProp_MustSendToSql;
    }


    private int ExtraProp_ccJayezehTakhfif;
    public int getExtraProp_ccJayezehTakhfif() {
        return ExtraProp_ccJayezehTakhfif;
    }
    public void setExtraProp_ccJayezehTakhfif(int extraProp_ccJayezehTakhfif) {
        ExtraProp_ccJayezehTakhfif = extraProp_ccJayezehTakhfif;
    }


    private int ExtraProp_IsOld;
    public int getExtraProp_IsOld() {
        return ExtraProp_IsOld;
    }
    public void setExtraProp_IsOld(int extraProp_IsOld) {
        ExtraProp_IsOld = extraProp_IsOld;
    }


    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccDarkhastFaktorTakhfif" , ccDarkhastFaktorTakhfif);
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("ccDarkhastHavaleh" , 0);
            jsonObject.put("ccTakhfif" , ccTakhfif);
            jsonObject.put("CodeNoeTakhfif" , CodeNoeTakhfif);
            jsonObject.put("DarsadTakhfif" , DarsadTakhfif);
            jsonObject.put("MablaghTakhfif" , MablaghTakhfif);
            jsonObject.put("NoeMazad" , ExtraProp_IsTakhfifMazad);
            jsonObject.put("ccDarkhastFaktorPPC" , 0);
            jsonObject.put("ccJayezeh" , ExtraProp_ccJayezehTakhfif);
            jsonObject.put("SharhTakhfif" , SharhTakhfif);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString() {
        return "DarkhastFaktorTakhfifModel{" +
                "ccDarkhastFaktorTakhfif=" + ccDarkhastFaktorTakhfif +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccTakhfif=" + ccTakhfif +
                ", SharhTakhfif='" + SharhTakhfif + '\'' +
                ", CodeNoeTakhfif=" + CodeNoeTakhfif +
                ", DarsadTakhfif=" + DarsadTakhfif +
                ", MablaghTakhfif=" + MablaghTakhfif +
                ", ExtraProp_ForJayezeh=" + ExtraProp_ForJayezeh +
                ", ExtraProp_IsTakhfifMazad=" + ExtraProp_IsTakhfifMazad +
                ", ExtraProp_MustSendToSql=" + ExtraProp_MustSendToSql +
                ", ExtraProp_ccJayezehTakhfif=" + ExtraProp_ccJayezehTakhfif +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld +
                '}';
    }
}
