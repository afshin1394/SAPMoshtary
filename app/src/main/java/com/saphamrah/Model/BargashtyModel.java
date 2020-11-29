package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BargashtyModel
{

    private static final String TABLE_NAME = "Bargashty";
    private static final String COLUMN_ccAfradErsalKonandeh = "ccAfradErsalKonandeh";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccDariaftPardakht = "ccDariaftPardakht";
    private static final String COLUMN_ccMarkazAnbar = "ccMarkazAnbar";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_NameMarkazForosh = "NameMarkazForosh";
    private static final String COLUMN_ccSazmanForosh = "ccSazmanForosh";
    private static final String COLUMN_NameSazmanForosh = "NameSazmanForosh";
    private static final String COLUMN_ccMarkazSazmanForoshSakhtarForosh = "ccMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_NameMarkazSazmanForoshSakhtarForosh = "NameMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_CodeMoshtary = "CodeMoshtary";
    private static final String COLUMN_TarikhSanad = "TarikhSanad";
    private static final String COLUMN_ShomarehSanad = "ShomarehSanad";
    private static final String COLUMN_Mablagh = "Mablagh";
    private static final String COLUMN_MablaghMandeh = "MablaghMandeh";
    private static final String COLUMN_TarikhSanadWithSlash = "TarikhSanadWithSlash";
    private static final String COLUMN_ZamaneSabt = "ZamaneSabt";
    private static final String COLUMN_ZamaneSabtWithSlash = "ZamaneSabtWithSlash";


    public static String TableName() {
        return TABLE_NAME;
    }

    public static String COLUMN_ccAfradErsalKonandeh() {
        return COLUMN_ccAfradErsalKonandeh;
    }

    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }

    public static String COLUMN_ccDariaftPardakht() {
        return COLUMN_ccDariaftPardakht;
    }

    public static String COLUMN_ccMarkazAnbar() {
        return COLUMN_ccMarkazAnbar;
    }

    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }

    public static String COLUMN_NameMarkazForosh() {
        return COLUMN_NameMarkazForosh;
    }

    public static String COLUMN_ccSazmanForosh() {
        return COLUMN_ccSazmanForosh;
    }

    public static String COLUMN_NameSazmanForosh() {
        return COLUMN_NameSazmanForosh;
    }

    public static String COLUMN_ccMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_ccMarkazSazmanForoshSakhtarForosh;
    }

    public static String COLUMN_NameMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_NameMarkazSazmanForoshSakhtarForosh;
    }

    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }

    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }

    public static String COLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }

    public static String COLUMN_CodeMoshtary() {
        return COLUMN_CodeMoshtary;
    }

    public static String COLUMN_TarikhSanad() {
        return COLUMN_TarikhSanad;
    }

    public static String COLUMN_ShomarehSanad() {
        return COLUMN_ShomarehSanad;
    }

    public static String COLUMN_Mablagh() {
        return COLUMN_Mablagh;
    }

    public static String COLUMN_MablaghMandeh() {
        return COLUMN_MablaghMandeh;
    }

    public static String COLUMN_TarikhSanadWithSlash() {
        return COLUMN_TarikhSanadWithSlash;
    }

    public static String COLUMN_ZamaneSabt() {
        return COLUMN_ZamaneSabt;
    }

    public static String COLUMN_ZamaneSabtWithSlash() {
        return COLUMN_ZamaneSabtWithSlash;
    }




    @SerializedName("ccAfradErsalKonandeh")
    @Expose
    private Integer ccAfradErsalKonandeh;
    @SerializedName("ccDarkhastFaktor")
    @Expose
    private Long ccDarkhastFaktor;
    @SerializedName("ccDariaftPardakht")
    @Expose
    private Long ccDariaftPardakht;
    @SerializedName("ccMarkazAnbar")
    @Expose
    private Integer ccMarkazAnbar;
    @SerializedName("ccMarkazForosh")
    @Expose
    private Integer ccMarkazForosh;
    @SerializedName("NameMarkazForosh")
    @Expose
    private String NameMarkazForosh;
    @SerializedName("ccSazmanForosh")
    @Expose
    private Integer ccSazmanForosh;
    @SerializedName("NameSazmanForosh")
    @Expose
    private String NameSazmanForosh;
    @SerializedName("ccMarkazSazmanForoshSakhtarForosh")
    @Expose
    private Integer ccMarkazSazmanForoshSakhtarForosh;
    @SerializedName("NameMarkazSazmanForoshSakhtarForosh")
    @Expose
    private String NameMarkazSazmanForoshSakhtarForosh;
    @SerializedName("ccForoshandeh")
    @Expose
    private Integer ccForoshandeh;
    @SerializedName("ccMoshtary")
    @Expose
    private Integer ccMoshtary;
    @SerializedName("CodeMoshtary")
    @Expose
    private String CodeMoshtary;
    @SerializedName("NameMoshtary")
    @Expose
    private String NameMoshtary;
    @SerializedName("ShomarehSanad")
    @Expose
    private String ShomarehSanad;
    @SerializedName("Mablagh")
    @Expose
    private Float Mablagh;
    @SerializedName("MablaghMandeh")
    @Expose
    private Float MablaghMandeh;
    @SerializedName("TarikhSanad")
    @Expose
    private String TarikhSanad;
    @SerializedName("ZamaneSabt")
    @Expose
    private String ZamaneSabt;
    @SerializedName("ZamaneSabtWithSlash")
    @Expose
    private String ZamaneSabtWithSlash;
    @SerializedName("TarikhSanadWithSlash")
    @Expose
    private String TarikhSanadWithSlash;


    public Integer getCcAfradErsalKonandeh() {
        return ccAfradErsalKonandeh;
    }

    public void setCcAfradErsalKonandeh(Integer ccAfradErsalKonandeh) {
        this.ccAfradErsalKonandeh = ccAfradErsalKonandeh;
    }

    public Long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(Long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public Long getCcDariaftPardakht() {
        return ccDariaftPardakht;
    }

    public void setCcDariaftPardakht(Long ccDariaftPardakht) {
        this.ccDariaftPardakht = ccDariaftPardakht;
    }

    public Integer getCcMarkazAnbar() {
        return ccMarkazAnbar;
    }

    public void setCcMarkazAnbar(Integer ccMarkazAnbar) {
        this.ccMarkazAnbar = ccMarkazAnbar;
    }

    public Integer getCcMarkazForosh() {
        return ccMarkazForosh;
    }

    public void setCcMarkazForosh(Integer ccMarkazForosh) {
        this.ccMarkazForosh = ccMarkazForosh;
    }

    public String getNameMarkazForosh() {
        return NameMarkazForosh;
    }

    public void setNameMarkazForosh(String nameMarkazForosh) {
        NameMarkazForosh = nameMarkazForosh;
    }

    public Integer getCcSazmanForosh() {
        return ccSazmanForosh;
    }

    public void setCcSazmanForosh(Integer ccSazmanForosh) {
        this.ccSazmanForosh = ccSazmanForosh;
    }

    public String getNameSazmanForosh() {
        return NameSazmanForosh;
    }

    public void setNameSazmanForosh(String nameSazmanForosh) {
        NameSazmanForosh = nameSazmanForosh;
    }

    public Integer getCcMarkazSazmanForoshSakhtarForosh() {
        return ccMarkazSazmanForoshSakhtarForosh;
    }

    public void setCcMarkazSazmanForoshSakhtarForosh(Integer ccMarkazSazmanForoshSakhtarForosh) {
        this.ccMarkazSazmanForoshSakhtarForosh = ccMarkazSazmanForoshSakhtarForosh;
    }

    public String getNameMarkazSazmanForoshSakhtarForosh() {
        return NameMarkazSazmanForoshSakhtarForosh;
    }

    public void setNameMarkazSazmanForoshSakhtarForosh(String nameMarkazSazmanForoshSakhtarForosh) {
        NameMarkazSazmanForoshSakhtarForosh = nameMarkazSazmanForoshSakhtarForosh;
    }

    public Integer getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(Integer ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public Integer getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(Integer ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getCodeMoshtary() {
        return CodeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary) {
        CodeMoshtary = codeMoshtary;
    }

    public String getNameMoshtary() {
        return NameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary) {
        NameMoshtary = nameMoshtary;
    }

    public String getShomarehSanad() {
        return ShomarehSanad;
    }

    public void setShomarehSanad(String shomarehSanad) {
        ShomarehSanad = shomarehSanad;
    }

    public Float getMablagh() {
        return Mablagh;
    }

    public void setMablagh(Float mablagh) {
        Mablagh = mablagh;
    }

    public Float getMablaghMandeh() {
        return MablaghMandeh;
    }

    public void setMablaghMandeh(Float mablaghMandeh) {
        MablaghMandeh = mablaghMandeh;
    }

    public String getTarikhSanad() {
        return TarikhSanad;
    }

    public void setTarikhSanad(String tarikhSanad) {
        TarikhSanad = tarikhSanad;
    }

    public String getZamaneSabt() {
        return ZamaneSabt;
    }

    public void setZamaneSabt(String zamaneSabt) {
        ZamaneSabt = zamaneSabt;
    }

    public String getZamaneSabtWithSlash() {
        return ZamaneSabtWithSlash;
    }

    public void setZamaneSabtWithSlash(String zamaneSabtWithSlash) {
        ZamaneSabtWithSlash = zamaneSabtWithSlash;
    }

    public String getTarikhSanadWithSlash() {
        return TarikhSanadWithSlash;
    }

    public void setTarikhSanadWithSlash(String tarikhSanadWithSlash) {
        TarikhSanadWithSlash = tarikhSanadWithSlash;
    }


    @Override
    public String toString() {
        return "BargashtyModel{" +
                "ccAfradErsalKonandeh=" + ccAfradErsalKonandeh +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccDariaftPardakht=" + ccDariaftPardakht +
                ", ccMarkazAnbar=" + ccMarkazAnbar +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", NameMarkazForosh='" + NameMarkazForosh + '\'' +
                ", ccSazmanForosh=" + ccSazmanForosh +
                ", NameSazmanForosh='" + NameSazmanForosh + '\'' +
                ", ccMarkazSazmanForoshSakhtarForosh=" + ccMarkazSazmanForoshSakhtarForosh +
                ", NameMarkazSazmanForoshSakhtarForosh='" + NameMarkazSazmanForoshSakhtarForosh + '\'' +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMoshtary=" + ccMoshtary +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", ShomarehSanad='" + ShomarehSanad + '\'' +
                ", Mablagh=" + Mablagh +
                ", MablaghMandeh=" + MablaghMandeh +
                ", TarikhSanad='" + TarikhSanad + '\'' +
                ", ZamaneSabt='" + ZamaneSabt + '\'' +
                ", ZamaneSabtWithSlash='" + ZamaneSabtWithSlash + '\'' +
                ", TarikhSanadWithSlash='" + TarikhSanadWithSlash + '\'' +
                '}';
    }
}
