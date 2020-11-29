package com.saphamrah.UIModel;


import androidx.annotation.NonNull;

/**
 * save data of darkhastFaktor table and moshtary table
 */
public class CustomerDarkhastFaktorModel
{

    private long ccDarkhastFaktor;
    private String TarikhFaktor;
    private int ShomarehDarkhast;
    private int ShomarehFaktor;
    private int ccForoshandeh;
    private int ccMoshtary;
    private String TarikhErsal;
    private double MablaghKhalesFaktor;
    private int CodeVazeiat;
    private int ExtraProp_IsOld;
    private int ExtraProp_IsSend;
    private String UniqID_Tablet;
    private String fullNameMoshtary;
    private String codeMoshtary;
    private boolean haveEmzaImage;
    private boolean haveFaktorImage;


    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public String getTarikhFaktor() {
        return TarikhFaktor;
    }

    public void setTarikhFaktor(String tarikhFaktor) {
        TarikhFaktor = tarikhFaktor;
    }

    public int getShomarehDarkhast() {
        return ShomarehDarkhast;
    }

    public void setShomarehDarkhast(int shomarehDarkhast) {
        ShomarehDarkhast = shomarehDarkhast;
    }

    public int getShomarehFaktor() {
        return ShomarehFaktor;
    }

    public void setShomarehFaktor(int shomarehFaktor) {
        ShomarehFaktor = shomarehFaktor;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getTarikhErsal() {
        return TarikhErsal;
    }

    public void setTarikhErsal(String tarikhErsal) {
        TarikhErsal = tarikhErsal;
    }

    public double getMablaghKhalesFaktor() {
        return MablaghKhalesFaktor;
    }

    public void setMablaghKhalesFaktor(double mablaghKhalesFaktor) {
        MablaghKhalesFaktor = mablaghKhalesFaktor;
    }

    public int getCodeVazeiat() {
        return CodeVazeiat;
    }

    public void setCodeVazeiat(int codeVazeiat) {
        CodeVazeiat = codeVazeiat;
    }

    public int getExtraProp_IsOld() {
        return ExtraProp_IsOld;
    }

    public void setExtraProp_IsOld(int extraProp_IsOld) {
        ExtraProp_IsOld = extraProp_IsOld;
    }

    public int getExtraProp_IsSend() {
        return ExtraProp_IsSend;
    }

    public void setExtraProp_IsSend(int extraProp_IsSend) {
        ExtraProp_IsSend = extraProp_IsSend;
    }

    public String getUniqID_Tablet() {
        return UniqID_Tablet;
    }

    public void setUniqID_Tablet(String uniqID_Tablet) {
        UniqID_Tablet = uniqID_Tablet;
    }

    public String getFullNameMoshtary() {
        return fullNameMoshtary;
    }

    public void setFullNameMoshtary(String fullNameMoshtary) {
        this.fullNameMoshtary = fullNameMoshtary;
    }

    public String getCodeMoshtary() {
        return codeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary) {
        this.codeMoshtary = codeMoshtary;
    }

    public boolean getHaveEmzaImage() {
        return haveEmzaImage;
    }

    public void setHaveEmzaImage(boolean haveEmzaImage) {
        this.haveEmzaImage = haveEmzaImage;
    }

    public boolean getHaveFaktorImage() {
        return haveFaktorImage;
    }

    public void setHaveFaktorImage(boolean haveFaktorImage) {
        this.haveFaktorImage = haveFaktorImage;
    }


    @NonNull
    @Override
    public String toString() {
        return "CustomerDarkhastFaktorModel{" +
                "ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", TarikhFaktor='" + TarikhFaktor + '\'' +
                ", ShomarehDarkhast=" + ShomarehDarkhast +
                ", ShomarehFaktor=" + ShomarehFaktor +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMoshtary=" + ccMoshtary +
                ", TarikhErsal='" + TarikhErsal + '\'' +
                ", MablaghKhalesFaktor=" + MablaghKhalesFaktor +
                ", CodeVazeiat=" + CodeVazeiat +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld +
                ", ExtraProp_IsSend=" + ExtraProp_IsSend +
                ", UniqID_Tablet='" + UniqID_Tablet + '\'' +
                ", fullNameMoshtary='" + fullNameMoshtary + '\'' +
                ", codeMoshtary='" + codeMoshtary + '\'' +
                ", haveEmzaImage=" + haveEmzaImage +
                ", haveFaktorImage=" + haveFaktorImage +
                '}';
    }
}
