package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoshtaryGharardadModel {


    private static final String TABLE_NAME = "MoshtaryGharardad";
    private static final String COLUMN_Radif = "Radif";
    private static final String COLUMN_ccMoshtaryGharardad = "ccMoshtaryGharardad";
    private static final String COLUMN_ccMoshtary = "ccMoshtary ";
    private static final String COLUMN_ccMoshtaryNoeGharardad = "ccMoshtaryNoeGharardad";
    private static final String COLUMN_NameMoshtaryNoeGharardad = "NameMoshtaryNoeGharardad";
    private static final String COLUMN_ShomarehGharardad = "ShomarehGharardad";
    private static final String COLUMN_TarikhGharardad = "TarikhGharardad";
    private static final String COLUMN_FromDate = "FromDate";
    private static final String COLUMN_EndDate = "EndDate";
    private static final String COLUMN_TarikhEtebar = "TarikhEtebar";
    private static final String COLUMN_ccNoeVisit = "ccNoeVisit";
    private static final String COLUMN_NameNoeVisit = "NameNoeVisit";
    private static final String COLUMN_CodeNoeHaml = "CodeNoeHaml";
    private static final String COLUMN_ModatVosol = "ModatVosol";
    private static final String COLUMN_ModatTakhirMojaz = "ModatTakhirMojaz";
    private static final String COLUMN_ccDarkhastFaktorNoeForosh = "ccDarkhastFaktorNoeForosh";
    private static final String COLUMN_CodeNoeVosolAzMoshtary = "CodeNoeVosolAzMoshtary";
    private static final String COLUMN_NameNoeVosolAzMoshtary = "NameNoeVosolAzMoshtary";
    private static final String COLUMN_ccSazmanForosh = "ccSazmanForosh";
    private static final String COLUMN_NameSazmanForosh="NameSazmanForosh";

    public static String TableName() {
        return TABLE_NAME;
    }

    public static String COLUMN_Radif() {
        return COLUMN_Radif;
    }

    public static String COLUMN_ccMoshtaryGharardad() {
        return COLUMN_ccMoshtaryGharardad;
    }

    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }

    public static String COLUMN_ccMoshtaryNoeGharardad() {
        return COLUMN_ccMoshtaryNoeGharardad;
    }

    public static String COLUMN_NameMoshtaryNoeGharardad() {
        return COLUMN_NameMoshtaryNoeGharardad;
    }

    public static String COLUMN_ShomarehGharardad() {
        return COLUMN_ShomarehGharardad;
    }

    public static String COLUMN_TarikhGharardad() {
        return COLUMN_TarikhGharardad;
    }

    public static String COLUMN_FromDate() {
        return COLUMN_FromDate;
    }

    public static String COLUMN_EndDate() {
        return COLUMN_EndDate;
    }

    public static String COLUMN_TarikhEtebar() {
        return COLUMN_TarikhEtebar;
    }

    public static String COLUMN_ccNoeVisit() {
        return COLUMN_ccNoeVisit;
    }

    public static String COLUMN_NameNoeVisit() {
        return COLUMN_NameNoeVisit;
    }

    public static String COLUMN_CodeNoeHaml() {
        return COLUMN_CodeNoeHaml;
    }

    public static String COLUMN_ModatVosol() {
        return COLUMN_ModatVosol;
    }

    public static String COLUMN_ModatTakhirMojaz() {
        return COLUMN_ModatTakhirMojaz;
    }

    public static String COLUMN_ccDarkhastFaktorNoeForosh() {
        return COLUMN_ccDarkhastFaktorNoeForosh;
    }

    public static String COLUMN_CodeNoeVosolAzMoshtary() {
        return COLUMN_CodeNoeVosolAzMoshtary;
    }

    public static String COLUMN_NameNoeVosolAzMoshtary() {
        return COLUMN_NameNoeVosolAzMoshtary;
    }

    public static String COLUMN_CcSazmanForosh() {
        return COLUMN_ccSazmanForosh;
    }

    public static String COLUMN_nameSazmanForosh(){return COLUMN_NameSazmanForosh;    }
    @SerializedName("Radif")
    @Expose
    private int radif;
    @SerializedName("ccMoshtaryGharardad")
    @Expose
    private Integer ccMoshtaryGharardad;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccMoshtaryNoeGharardad")
    @Expose
    private int ccMoshtaryNoeGharardad;
    @SerializedName("NameMoshtaryNoeGharardad")
    @Expose
    private String NameMoshtaryNoeGharardad;
    @SerializedName("ShomarehGharardad")
    @Expose
    private String ShomarehGharardad;
    @SerializedName("TarikhGharardad")
    @Expose
    private String TarikhGharardad;
    @SerializedName("FromDate")
    @Expose
    private String FromDate;
    @SerializedName("EndDate")
    @Expose
    private String EndDate;
    @SerializedName("TarikhEtebar")
    @Expose
    private String TarikhEtebar;
    @SerializedName("ccNoeVisit")
    @Expose
    private int ccNoeVisit;
    @SerializedName("NameNoeVisit")
    @Expose
    private String NameNoeVisit;
    @SerializedName("CodeNoeHaml")
    @Expose
    private int CodeNoeHaml;
    @SerializedName("ModatVosol")
    @Expose
    private int ModatVosol;
    @SerializedName("ModatTakhirMojaz")
    @Expose
    private int ModatTakhirMojaz;
    @SerializedName("ccDarkhastFaktorNoeForosh")
    @Expose
    private int ccDarkhastFaktorNoeForosh;
    @SerializedName("CodeNoeVosolAzMoshtary")
    @Expose
    private int CodeNoeVosolAzMoshtary;
    @SerializedName("NameNoeVosolAzMoshtary")
    @Expose
    private String NameNoeVosolAzMoshtary;
    @SerializedName("ccSazmanForosh")
    @Expose
    private int ccSazmanForosh;
    @SerializedName("NameSazmanForosh")
    @Expose
    private String NameSazmanForosh;


    public void setRadif(int radif) {
        this.radif = radif;
    }

    public int getRadif() {
        return radif;
    }

    public Integer getCcMoshtaryGharardad() {
        return ccMoshtaryGharardad;
    }

    public void setCcMoshtaryGharardad(Integer ccMoshtaryGharardad) {
        this.ccMoshtaryGharardad = ccMoshtaryGharardad;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcMoshtaryNoeGharardad() {
        return ccMoshtaryNoeGharardad;
    }

    public void setCcMoshtaryNoeGharardad(int ccMoshtaryNoeGharardad) {
        this.ccMoshtaryNoeGharardad = ccMoshtaryNoeGharardad;
    }

    public String getNameMoshtaryNoeGharardad() {
        return NameMoshtaryNoeGharardad;
    }

    public void setNameMoshtaryNoeGharardad(String nameMoshtaryNoeGharardad) {
        NameMoshtaryNoeGharardad = nameMoshtaryNoeGharardad;
    }

    public String getShomarehGharardad() {
        return ShomarehGharardad;
    }

    public void setShomarehGharardad(String shomarehGharardad) {
        ShomarehGharardad = shomarehGharardad;
    }

    public String getTarikhGharardad() {
        return TarikhGharardad;
    }

    public void setTarikhGharardad(String tarikhGharardad) {
        TarikhGharardad = tarikhGharardad;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getTarikhEtebar() {
        return TarikhEtebar;
    }

    public void setTarikhEtebar(String tarikhEtebar) {
        TarikhEtebar = tarikhEtebar;
    }

    public int getCcNoeVisit() {
        return ccNoeVisit;
    }

    public void setCcNoeVisit(int ccNoeVisit) {
        this.ccNoeVisit = ccNoeVisit;
    }

    public String getNameNoeVisit() {
        return NameNoeVisit;
    }

    public void setNameNoeVisit(String nameNoeVisit) {
        NameNoeVisit = nameNoeVisit;
    }

    public int getCodeNoeHaml() {
        return CodeNoeHaml;
    }

    public void setCodeNoeHaml(int codeNoeHaml) {
        CodeNoeHaml = codeNoeHaml;
    }

    public int getModatVosol() {
        return ModatVosol;
    }

    public void setModatVosol(int modatVosol) {
        ModatVosol = modatVosol;
    }

    public int getModatTakhirMojaz() {
        return ModatTakhirMojaz;
    }

    public void setModatTakhirMojaz(int modatTakhirMojaz) {
        ModatTakhirMojaz = modatTakhirMojaz;
    }

    public int getCcDarkhastFaktorNoeForosh() {
        return ccDarkhastFaktorNoeForosh;
    }

    public void setCcDarkhastFaktorNoeForosh(int ccDarkhastFaktorNoeForosh) {
        this.ccDarkhastFaktorNoeForosh = ccDarkhastFaktorNoeForosh;
    }

    public int getCodeNoeVosolAzMoshtary() {
        return CodeNoeVosolAzMoshtary;
    }

    public void setCodeNoeVosolAzMoshtary(int codeNoeVosolAzMoshtary) {
        CodeNoeVosolAzMoshtary = codeNoeVosolAzMoshtary;
    }

    public String getNameNoeVosolAzMoshtary() {
        return NameNoeVosolAzMoshtary;
    }

    public void setNameNoeVosolAzMoshtary(String nameNoeVosolAzMoshtary) {
        NameNoeVosolAzMoshtary = nameNoeVosolAzMoshtary;
    }

    public void setCcSazmanForosh(int ccSazmanForosh) {
        this.ccSazmanForosh = ccSazmanForosh;
    }

    public int getCcSazmanForosh() {
        return ccSazmanForosh;
    }

    public static String getCOLUMN_NameSazmanForosh() {
        return COLUMN_NameSazmanForosh;
    }

    public String getNameSazmanForosh() {
        return NameSazmanForosh;
    }

    public void setNameSazmanForosh(String nameSazmanForosh) {
        NameSazmanForosh = nameSazmanForosh;
    }

    @Override
    public String toString() {
        return "MoshtaryGharardadModel{" +
                "radif=" + radif +
                ", ccMoshtaryGharardad=" + ccMoshtaryGharardad +
                ", ccMoshtary=" + ccMoshtary +
                ", ccMoshtaryNoeGharardad=" + ccMoshtaryNoeGharardad +
                ", NameMoshtaryNoeGharardad='" + NameMoshtaryNoeGharardad + '\'' +
                ", ShomarehGharardad='" + ShomarehGharardad + '\'' +
                ", TarikhGharardad='" + TarikhGharardad + '\'' +
                ", FromDate='" + FromDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", TarikhEtebar='" + TarikhEtebar + '\'' +
                ", ccNoeVisit=" + ccNoeVisit +
                ", NameNoeVisit='" + NameNoeVisit + '\'' +
                ", CodeNoeHaml=" + CodeNoeHaml +
                ", ModatVosol=" + ModatVosol +
                ", ModatTakhirMojaz=" + ModatTakhirMojaz +
                ", ccDarkhastFaktorNoeForosh=" + ccDarkhastFaktorNoeForosh +
                ", CodeNoeVosolAzMoshtary=" + CodeNoeVosolAzMoshtary +
                ", NameNoeVosolAzMoshtary='" + NameNoeVosolAzMoshtary + '\'' +
                ", ccSazmanForosh=" + ccSazmanForosh +
                ", NameSazmanForosh='" + NameSazmanForosh + '\'' +
                '}';
    }
}
