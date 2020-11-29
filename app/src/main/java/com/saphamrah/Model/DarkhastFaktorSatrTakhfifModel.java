package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class DarkhastFaktorSatrTakhfifModel
{

    private static final String TABLE_NAME = "DarkhastFaktorSatrTakhfif";
    private static final String COLUMN_ccDarkhastFaktorSatrTakhfif = "ccDarkhastFaktorSatrTakhfif";
    private static final String COLUMN_ccDarkhastFaktorSatr = "ccDarkhastFaktorSatr";
    private static final String COLUMN_ccTakhfif = "ccTakhfif";
    private static final String COLUMN_SharhTakhfif = "SharhTakhfif";
    private static final String COLUMN_CodeNoeTakhfif = "CodeNoeTakhfif";
    private static final String COLUMN_DarsadTakhfif = "DarsadTakhfif";
    private static final String COLUMN_MablaghTakhfif = "MablaghTakhfif";
    private static final String COLUMN_ExtraProp_NotSendToSql = "ExtraProp_NotSendToSql";
    private static final String COLUMN_ExtraProp_ForJayezeh = "ExtraProp_ForJayezeh";
    private static final String COLUMN_ExtraProp_Olaviat = "ExtraProp_Olaviat";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDarkhastFaktorSatrTakhfif() {
        return COLUMN_ccDarkhastFaktorSatrTakhfif;
    }
    public static String COLUMN_ccDarkhastFaktorSatr() {
        return COLUMN_ccDarkhastFaktorSatr;
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
    public static String COLUMN_ExtraProp_NotSendToSql() {
        return COLUMN_ExtraProp_NotSendToSql;
    }
    public static String COLUMN_ExtraProp_ForJayezeh() {
        return COLUMN_ExtraProp_ForJayezeh;
    }
    public static String COLUMN_ExtraProp_Olaviat() {
        return COLUMN_ExtraProp_Olaviat;
    }



    @SerializedName("ccDarkhastFaktorSatr")
    @Expose
    private long ccDarkhastFaktorSatr;
    @SerializedName("ccDarkhastFaktorSatrTakhfif")
    @Expose
    private int ccDarkhastFaktorSatrTakhfif;
    @SerializedName("CodeNoeTakhfif")
    @Expose
    private int CodeNoeTakhfif;
    @SerializedName("ccTakhfif")
    @Expose
    private int ccTakhfif;
    @SerializedName("DarsadTakhfif")
    @Expose
    private float DarsadTakhfif;
    @SerializedName("MablaghTakhfif")
    @Expose
    private long MablaghTakhfif;

    private String SharhTakhfif;
    private int ExtraProp_NotSendToSql;
    private int ExtraProp_ForJayezeh;
    private int ExtraProp_Olaviat;

    public void setCcDarkhastFaktorSatr(long ccDarkhastFaktorSatr){
        this.ccDarkhastFaktorSatr = ccDarkhastFaktorSatr;
    }
    public long getCcDarkhastFaktorSatr(){
        return this.ccDarkhastFaktorSatr;
    }
    public void setCcDarkhastFaktorSatrTakhfif(int ccDarkhastFaktorSatrTakhfif){
        this.ccDarkhastFaktorSatrTakhfif = ccDarkhastFaktorSatrTakhfif;
    }
    public int getCcDarkhastFaktorSatrTakhfif(){
        return this.ccDarkhastFaktorSatrTakhfif;
    }
    public void setCodeNoeTakhfif(int CodeNoeTakhfif){
        this.CodeNoeTakhfif = CodeNoeTakhfif;
    }
    public int getCodeNoeTakhfif(){
        return this.CodeNoeTakhfif;
    }
    public void setCcTakhfif(int ccTakhfif){
        this.ccTakhfif = ccTakhfif;
    }
    public int getCcTakhfif(){
        return this.ccTakhfif;
    }
    public void setDarsadTakhfif(float DarsadTakhfif){
        this.DarsadTakhfif = DarsadTakhfif;
    }
    public float getDarsadTakhfif(){
        return this.DarsadTakhfif;
    }
    public void setMablaghTakhfif(long MablaghTakhfif){
        this.MablaghTakhfif = MablaghTakhfif;
    }
    public long getMablaghTakhfif(){
        return this.MablaghTakhfif;
    }
    public String getSharhTakhfif() {
        return SharhTakhfif;
    }
    public void setSharhTakhfif(String sharhTakhfif) {
        SharhTakhfif = sharhTakhfif;
    }
    public int getExtraProp_NotSendToSql() {
        return ExtraProp_NotSendToSql;
    }
    public void setExtraProp_NotSendToSql(int extraProp_NotSendToSql) {
        ExtraProp_NotSendToSql = extraProp_NotSendToSql;
    }

    public int getExtraProp_ForJayezeh() {
        return ExtraProp_ForJayezeh;
    }
    public void setExtraProp_ForJayezeh(int extraProp_ForJayezeh) {
        ExtraProp_ForJayezeh = extraProp_ForJayezeh;
    }

    public int getExtraProp_Olaviat()
    {
        return ExtraProp_Olaviat;
    }
    public void setExtraProp_Olaviat(int extraProp_Olaviat)
    {
        ExtraProp_Olaviat = extraProp_Olaviat;
    }


    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {

            jsonObject.put("ccDarkhastFaktorSatrTakhfif" , ccDarkhastFaktorSatrTakhfif);
            jsonObject.put("ccDarkhastFaktorSatr" , ccDarkhastFaktorSatr);
            jsonObject.put("ccTakhfif" , ccTakhfif);
            jsonObject.put("SharhTakhfif" , SharhTakhfif);
            jsonObject.put("CodeNoeTakhfif" , CodeNoeTakhfif);
            jsonObject.put("DarsadTakhfif" , DarsadTakhfif);
            jsonObject.put("MablaghTakhfif" , MablaghTakhfif);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "DarkhastFaktorSatrTakhfifModel{" +
                "ccDarkhastFaktorSatr=" + ccDarkhastFaktorSatr +
                ", ccDarkhastFaktorSatrTakhfif=" + ccDarkhastFaktorSatrTakhfif +
                ", CodeNoeTakhfif=" + CodeNoeTakhfif +
                ", ccTakhfif=" + ccTakhfif +
                ", DarsadTakhfif=" + DarsadTakhfif +
                ", MablaghTakhfif=" + MablaghTakhfif +
                ", SharhTakhfif='" + SharhTakhfif + '\'' +
                ", ExtraProp_NotSendToSql=" + ExtraProp_NotSendToSql +
                ", ExtraProp_ForJayezeh=" + ExtraProp_ForJayezeh +
                ", ExtraProp_Olaviat=" + ExtraProp_Olaviat +
                '}';
    }


}
