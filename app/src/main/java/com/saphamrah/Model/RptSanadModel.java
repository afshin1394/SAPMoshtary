package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RptSanadModel
{

    private static final String TABLE_NAME = "Rpt_Sanad";
    private static final String COLUMN_ccRpt_Sanad = "ccRpt_Sanad";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_CodeMoshtaryOld = "CodeMoshtaryOld";
    private static final String COLUMN_ShomarehFaktor = "ShomarehFaktor";
    private static final String COLUMN_TarikhFaktor = "TarikhFaktor";
    private static final String COLUMN_TarikhErsal = "TarikhErsal";
    private static final String COLUMN_TarikhSanad = "TarikhSanad";
    private static final String COLUMN_MablaghKhalesFaktor = "MablaghKhalesFaktor";
    private static final String COLUMN_ShomarehSanad = "ShomarehSanad";
    private static final String COLUMN_MablaghCheck = "MablaghCheck";
    private static final String COLUMN_MablaghTakhsis = "MablaghTakhsis";
    private static final String COLUMN_CodeVazeiatDariaftPardakht = "CodeVazeiatDariaftPardakht";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_ccGorohForosh = "ccGorohForosh";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccRpt_Sanad() {
        return COLUMN_ccRpt_Sanad;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_CodeMoshtaryOld() {
        return COLUMN_CodeMoshtaryOld;
    }
    public static String COLUMN_ShomarehFaktor() {
        return COLUMN_ShomarehFaktor;
    }
    public static String COLUMN_TarikhFaktor() {
        return COLUMN_TarikhFaktor;
    }
    public static String COLUMN_TarikhErsal() {
        return COLUMN_TarikhErsal;
    }
    public static String COLUMN_TarikhSanad() {
        return COLUMN_TarikhSanad;
    }
    public static String COLUMN_MablaghKhalesFaktor() {
        return COLUMN_MablaghKhalesFaktor;
    }
    public static String COLUMN_ShomarehSanad() {
        return COLUMN_ShomarehSanad;
    }
    public static String COLUMN_MablaghCheck() {
        return COLUMN_MablaghCheck;
    }
    public static String COLUMN_MablaghTakhsis() {
        return COLUMN_MablaghTakhsis;
    }
    public static String COLUMN_CodeVazeiatDariaftPardakht() {
        return COLUMN_CodeVazeiatDariaftPardakht;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_ccGorohForosh() {
        return COLUMN_ccGorohForosh;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }



    private int ccRpt_Sanad;

    @SerializedName("ccDarkhastFaktor")
    @Expose
    private long ccDarkhastFaktor;
    @SerializedName("ccMarkazForosh")
    @Expose
    private int ccMarkazForosh;
    @SerializedName("ccGorohForosh")
    @Expose
    private int ccGorohForosh;
    @SerializedName("ccForoshandeh")
    @Expose
    private int ccForoshandeh;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccNoeMoshtary")
    @Expose
    private int ccNoeMoshtary;
    @SerializedName("TarikhFaktorWithSlash")
    @Expose
    private String tarikhFaktorWithSlash;
    @SerializedName("TarikhErsal")
    @Expose
    private String tarikhErsal;
    @SerializedName("TarikhFaktor")
    @Expose
    private String tarikhFaktor;
    @SerializedName("TarikhErsalWithSlash")
    @Expose
    private String tarikhErsalWithSlash;
    @SerializedName("ShomarehFaktor")
    @Expose
    private int shomarehFaktor;
    @SerializedName("CodeMoshtaryOld")
    @Expose
    private String codeMoshtaryOld;
    @SerializedName("NameMoshtary")
    @Expose
    private String nameMoshtary;
    @SerializedName("MablaghKhalesFaktor")
    @Expose
    private float mablaghKhalesFaktor;
    @SerializedName("NameGoroh")
    @Expose
    private String nameGoroh;
    @SerializedName("NoeVosol")
    @Expose
    private String noeVosol;
    @SerializedName("MablaghTakhsis")
    @Expose
    private float mablaghTakhsis;
    @SerializedName("MablaghCheck")
    @Expose
    private float mablaghCheck;
    @SerializedName("TarikhSanad")
    @Expose
    private String tarikhSanad;
    @SerializedName("TarikhCheckWithSlash")
    @Expose
    private String tarikhCheckWithSlash;
    @SerializedName("ShomarehSanad")
    @Expose
    private String shomarehSanad;
    @SerializedName("EkhtelafRoozSanadVazFaktor")
    @Expose
    private int ekhtelafRoozSanadVazFaktor;
    @SerializedName("ModateVosol")
    @Expose
    private int modateVosol;
    @SerializedName("CodeVazeiatDariaftPardakht")
    @Expose
    private int codeVazeiatDariaftPardakht;


    public int getCcRpt_Sanad()
    {
        return ccRpt_Sanad;
    }

    public void setCcRpt_Sanad(int ccRpt_Sanad)
    {
        this.ccRpt_Sanad = ccRpt_Sanad;
    }

    public long getCcDarkhastFaktor()
    {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor)
    {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcMarkazForosh()
    {
        return ccMarkazForosh;
    }

    public void setCcMarkazForosh(int ccMarkazForosh)
    {
        this.ccMarkazForosh = ccMarkazForosh;
    }

    public int getCcGorohForosh()
    {
        return ccGorohForosh;
    }

    public void setCcGorohForosh(int ccGorohForosh)
    {
        this.ccGorohForosh = ccGorohForosh;
    }

    public int getCcForoshandeh()
    {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh)
    {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMoshtary()
    {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary)
    {
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcNoeMoshtary()
    {
        return ccNoeMoshtary;
    }

    public void setCcNoeMoshtary(int ccNoeMoshtary)
    {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }

    public String getTarikhFaktorWithSlash()
    {
        return tarikhFaktorWithSlash;
    }

    public void setTarikhFaktorWithSlash(String tarikhFaktorWithSlash)
    {
        this.tarikhFaktorWithSlash = tarikhFaktorWithSlash;
    }

    public String getTarikhErsal()
    {
        return tarikhErsal;
    }

    public void setTarikhErsal(String tarikhErsal)
    {
        this.tarikhErsal = tarikhErsal;
    }

    public String getTarikhFaktor()
    {
        return tarikhFaktor;
    }

    public void setTarikhFaktor(String tarikhFaktor)
    {
        this.tarikhFaktor = tarikhFaktor;
    }

    public String getTarikhErsalWithSlash()
    {
        return tarikhErsalWithSlash;
    }

    public void setTarikhErsalWithSlash(String tarikhErsalWithSlash)
    {
        this.tarikhErsalWithSlash = tarikhErsalWithSlash;
    }

    public int getShomarehFaktor()
    {
        return shomarehFaktor;
    }

    public void setShomarehFaktor(int shomarehFaktor)
    {
        this.shomarehFaktor = shomarehFaktor;
    }

    public String getCodeMoshtaryOld()
    {
        return codeMoshtaryOld;
    }

    public void setCodeMoshtaryOld(String codeMoshtaryOld)
    {
        this.codeMoshtaryOld = codeMoshtaryOld;
    }

    public String getNameMoshtary()
    {
        return nameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary)
    {
        this.nameMoshtary = nameMoshtary;
    }

    public float getMablaghKhalesFaktor()
    {
        return mablaghKhalesFaktor;
    }

    public void setMablaghKhalesFaktor(float mablaghKhalesFaktor)
    {
        this.mablaghKhalesFaktor = mablaghKhalesFaktor;
    }

    public String getNameGoroh()
    {
        return nameGoroh;
    }

    public void setNameGoroh(String nameGoroh)
    {
        this.nameGoroh = nameGoroh;
    }

    public String getNoeVosol()
    {
        return noeVosol;
    }

    public void setNoeVosol(String noeVosol)
    {
        this.noeVosol = noeVosol;
    }

    public float getMablaghTakhsis()
    {
        return mablaghTakhsis;
    }

    public void setMablaghTakhsis(float mablaghTakhsis)
    {
        this.mablaghTakhsis = mablaghTakhsis;
    }

    public float getMablaghCheck()
    {
        return mablaghCheck;
    }

    public void setMablaghCheck(float mablaghCheck)
    {
        this.mablaghCheck = mablaghCheck;
    }

    public String getTarikhSanad()
    {
        return tarikhSanad;
    }

    public void setTarikhSanad(String tarikhSanad)
    {
        this.tarikhSanad = tarikhSanad;
    }

    public String getTarikhCheckWithSlash()
    {
        return tarikhCheckWithSlash;
    }

    public void setTarikhCheckWithSlash(String tarikhCheckWithSlash)
    {
        this.tarikhCheckWithSlash = tarikhCheckWithSlash;
    }

    public String getShomarehSanad()
    {
        return shomarehSanad;
    }

    public void setShomarehSanad(String shomarehSanad)
    {
        this.shomarehSanad = shomarehSanad;
    }

    public int getEkhtelafRoozSanadVazFaktor()
    {
        return ekhtelafRoozSanadVazFaktor;
    }

    public void setEkhtelafRoozSanadVazFaktor(int ekhtelafRoozSanadVazFaktor)
    {
        this.ekhtelafRoozSanadVazFaktor = ekhtelafRoozSanadVazFaktor;
    }

    public int getModateVosol()
    {
        return modateVosol;
    }

    public void setModateVosol(int modateVosol)
    {
        this.modateVosol = modateVosol;
    }

    public int getCodeVazeiatDariaftPardakht()
    {
        return codeVazeiatDariaftPardakht;
    }

    public void setCodeVazeiatDariaftPardakht(int codeVazeiatDariaftPardakht)
    {
        this.codeVazeiatDariaftPardakht = codeVazeiatDariaftPardakht;
    }
}
