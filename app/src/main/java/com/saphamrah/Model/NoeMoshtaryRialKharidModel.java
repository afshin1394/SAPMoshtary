package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class NoeMoshtaryRialKharidModel
{

    private static final String TABLE_NAME = "NoeMoshtaryRialKharid";
    private static final String COLUMN_ccNoeMoshtaryRialKharid = "ccNoeMoshtaryRialKharid";
    private static final String COLUMN_ccGoroh = "ccGoroh";
    private static final String COLUMN_HadeAghalMablaghKharid = "HadeAghalMablaghKharid";
    private static final String COLUMN_HadeAghalMablaghKharejAzMasir = "HadeAghalMablaghKharejAzMasir";
    private static final String COLUMN_HadeAghalTedadKharid = "HadeAghalTedadKharid";
    private static final String COLUMN_ccMarkazSazmanForoshSakhtarForosh = "ccMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_Darajeh = "Darajeh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccNoeMoshtaryRialKharid() {
        return COLUMN_ccNoeMoshtaryRialKharid;
    }
    public static String COLUMN_ccGoroh() {
        return COLUMN_ccGoroh;
    }
    public static String COLUMN_HadeAghalMablaghKharid() {
        return COLUMN_HadeAghalMablaghKharid;
    }
    public static String COLUMN_HadeAghalMablaghKharejAzMasir() {
        return COLUMN_HadeAghalMablaghKharejAzMasir;
    }
    public static String COLUMN_HadeAghalTedadKharid() {
        return COLUMN_HadeAghalTedadKharid;
    }
    public static String COLUMN_ccMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_ccMarkazSazmanForoshSakhtarForosh;
    }
    public static String COLUMN_Darajeh() {
        return COLUMN_Darajeh;
    }




    private int ccNoeMoshtaryRialKharid;
    private int ccGoroh;
    private float HadeAghalMablaghKharid;
    private float HadeAghalMablaghKharejAzMasir;
    private int HadeAghalTedadKharid;
    private int ccMarkazSazmanForoshSakhtarForosh;
    private int Darajeh;


    public int getCcNoeMoshtaryRialKharid() {
        return ccNoeMoshtaryRialKharid;
    }
    public void setCcNoeMoshtaryRialKharid(int ccNoeMoshtaryRialKharid) {
        this.ccNoeMoshtaryRialKharid = ccNoeMoshtaryRialKharid;
    }
    public int getCcGoroh() {
        return ccGoroh;
    }
    public void setCcGoroh(int ccGoroh) {
        this.ccGoroh = ccGoroh;
    }
    public float getHadeAghalMablaghKharid() {
        return HadeAghalMablaghKharid;
    }
    public void setHadeAghalMablaghKharid(float hadeAghalMablaghKharid) {
        HadeAghalMablaghKharid = hadeAghalMablaghKharid;
    }
    public float getHadeAghalMablaghKharejAzMasir() {
        return HadeAghalMablaghKharejAzMasir;
    }
    public void setHadeAghalMablaghKharejAzMasir(float hadeAghalMablaghKharejAzMasir) {
        HadeAghalMablaghKharejAzMasir = hadeAghalMablaghKharejAzMasir;
    }
    public int getHadeAghalTedadKharid() {
        return HadeAghalTedadKharid;
    }
    public void setHadeAghalTedadKharid(int hadeAghalTedadKharid) {
        HadeAghalTedadKharid = hadeAghalTedadKharid;
    }
    public int getCcMarkazSazmanForoshSakhtarForosh() {
        return ccMarkazSazmanForoshSakhtarForosh;
    }
    public void setCcMarkazSazmanForoshSakhtarForosh(int ccMarkazSazmanForoshSakhtarForosh) {
        this.ccMarkazSazmanForoshSakhtarForosh = ccMarkazSazmanForoshSakhtarForosh;
    }
    public int getDarajeh() {
        return Darajeh;
    }
    public void setDarajeh(int darajeh) {
        Darajeh = darajeh;
    }


    @NonNull
    @Override
    public String toString() {
        return "NoeMoshtaryRialKharidModel{" +
                "ccNoeMoshtaryRialKharid=" + ccNoeMoshtaryRialKharid +
                ", ccGoroh=" + ccGoroh +
                ", HadeAghalMablaghKharid=" + HadeAghalMablaghKharid +
                ", HadeAghalMablaghKharejAzMasir=" + HadeAghalMablaghKharejAzMasir +
                ", HadeAghalTedadKharid=" + HadeAghalTedadKharid +
                ", ccMarkazSazmanForoshSakhtarForosh=" + ccMarkazSazmanForoshSakhtarForosh +
                ", Darajeh=" + Darajeh +
                '}';
    }
}
