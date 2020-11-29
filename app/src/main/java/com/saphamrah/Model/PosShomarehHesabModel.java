package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PosShomarehHesabModel
{

    private static final String TABLE_NAME = "PosShomarehHesab";
    private static final String COLUMN_ccPosShomarehHesab = "ccPosShomarehHesab";
    private static final String COLUMN_ccShomarehHesab = "ccShomarehHesab";
    private static final String COLUMN_PosNumber = "PosNumber";
    private static final String COLUMN_ccBank = "ccBank";
    private static final String COLUMN_NameBank = "NameBank";
    private static final String COLUMN_CodeShobeh = "CodeShobeh";
    private static final String COLUMN_NameShobeh = "NameShobeh";
    private static final String COLUMN_ShomarehHesab = "ShomarehHesab";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccPosShomarehHesab() {
        return COLUMN_ccPosShomarehHesab;
    }
    public static String COLUMN_ccShomarehHesab() {
        return COLUMN_ccShomarehHesab;
    }
    public static String COLUMN_PosNumber() {
        return COLUMN_PosNumber;
    }
    public static String COLUMN_ccBank() {
        return COLUMN_ccBank;
    }
    public static String COLUMN_NameBank() {
        return COLUMN_NameBank;
    }
    public static String COLUMN_CodeShobeh() {
        return COLUMN_CodeShobeh;
    }
    public static String COLUMN_NameShobeh() {
        return COLUMN_NameShobeh;
    }
    public static String COLUMN_ShomarehHesab() {
        return COLUMN_ShomarehHesab;
    }



    @SerializedName("ccPosShomarehHesab")
    @Expose
    private int ccPosShomarehHesab;
    @SerializedName("ccShomarehHesab")
    @Expose
    private int ccShomarehHesab;
    @SerializedName("ccBank")
    @Expose
    private int ccBank;
    @SerializedName("NameBank")
    @Expose
    private String NameBank;
    @SerializedName("CodeShobeh")
    @Expose
    private String CodeShobeh;
    @SerializedName("NameShobeh")
    @Expose
    private String NameShobeh;
    @SerializedName("PosNumber")
    @Expose
    private String PosNumber;
    @SerializedName("ShomarehHesab")
    @Expose
    private String ShomarehHesab;


    public void setCcPosShomarehHesab(int ccPosShomarehHesab){
        this.ccPosShomarehHesab = ccPosShomarehHesab;
    }
    public int getCcPosShomarehHesab(){
        return this.ccPosShomarehHesab;
    }
    public void setCcShomarehHesab(int ccShomarehHesab){
        this.ccShomarehHesab = ccShomarehHesab;
    }
    public int getCcShomarehHesab(){
        return this.ccShomarehHesab;
    }
    public void setCcBank(int ccBank){
        this.ccBank = ccBank;
    }
    public int getCcBank(){
        return this.ccBank;
    }
    public void setNameBank(String NameBank){
        this.NameBank = NameBank;
    }
    public String getNameBank(){
        return this.NameBank;
    }
    public void setCodeShobeh(String CodeShobeh){
        this.CodeShobeh = CodeShobeh;
    }
    public String getCodeShobeh(){
        return this.CodeShobeh;
    }
    public void setNameShobeh(String NameShobeh){
        this.NameShobeh = NameShobeh;
    }
    public String getNameShobeh(){
        return this.NameShobeh;
    }
    public void setPosNumber(String PosNumber){
        this.PosNumber = PosNumber;
    }
    public String getPosNumber(){
        return this.PosNumber;
    }
    public void setShomarehHesab(String ShomarehHesab){
        this.ShomarehHesab = ShomarehHesab;
    }
    public String getShomarehHesab(){
        return this.ShomarehHesab;
    }


    @Override
    public String toString() {
        return "ccPosShomarehHesab : " + ccPosShomarehHesab + " , ccShomarehHesab : " + ccShomarehHesab + " , ccBank : " + ccBank +
                " , NameBank : " + NameBank + " , CodeShobeh : " + CodeShobeh + " , NameShobeh : " + NameShobeh +
                " , PosNumber : " + PosNumber + " , ShomarehHesab : " + ShomarehHesab;
    }


}
