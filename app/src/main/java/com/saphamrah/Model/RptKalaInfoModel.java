package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RptKalaInfoModel
{

    private static final String TABLE_NAME = "Rpt_KalaInfo";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_CodeKala = "CodeKala";
    private static final String COLUMN_NameBrand = "NameBrand";
    private static final String COLUMN_GheymatMasrafKonandeh = "GheymatMasrafKonandeh";
    private static final String COLUMN_GheymatForoshAsli = "GheymatForoshAsli";
    private static final String COLUMN_TedadDarKarton = "TedadDarKarton";
    private static final String COLUMN_TedadDarBasteh = "TedadDarBasteh";
    private static final String COLUMN_Tol = "Tol";
    private static final String COLUMN_Arz = "Arz";
    private static final String COLUMN_Ertefa = "Ertefa";
    private static final String COLUMN_NameVahedSize = "NameVahedSize";
    private static final String COLUMN_NameVahedShomaresh = "NameVahedShomaresh";
    private static final String COLUMN_VaznKhales = "VaznKhales";
    private static final String COLUMN_VaznNaKhales = "VaznNaKhales";
    private static final String COLUMN_VaznKarton = "VaznKarton";
    private static final String COLUMN_BarCode = "BarCode";
    private static final String COLUMN_VaznKartonTabdili = "VaznKartonTabdili";
    private static final String COLUMN_HajmKartonTabdili = "HajmKartonTabdili";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_MashmolMaliatAvarez = "MashmolMaliatAvarez";
    private static final String COLUMN_ccBrand = "ccBrand";
    private static final String COLUMN_ccGoroh = "ccGoroh";
    private static final String COLUMN_NameGoroh = "NameGoroh";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKalaCode()
    {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_NameKala()
    {
        return COLUMN_NameKala;
    }
    public static String COLUMN_CodeKala()
    {
        return COLUMN_CodeKala;
    }
    public static String COLUMN_NameBrand()
    {
        return COLUMN_NameBrand;
    }
    public static String COLUMN_GheymatMasrafKonandeh()
    {
        return COLUMN_GheymatMasrafKonandeh;
    }
    public static String COLUMN_GheymatForoshAsli()
    {
        return COLUMN_GheymatForoshAsli;
    }
    public static String COLUMN_TedadDarKarton()
    {
        return COLUMN_TedadDarKarton;
    }
    public static String COLUMN_TedadDarBasteh()
    {
        return COLUMN_TedadDarBasteh;
    }
    public static String COLUMN_Tol()
    {
        return COLUMN_Tol;
    }
    public static String COLUMN_Arz()
    {
        return COLUMN_Arz;
    }
    public static String COLUMN_Ertefa()
    {
        return COLUMN_Ertefa;
    }
    public static String COLUMN_NameVahedSize()
    {
        return COLUMN_NameVahedSize;
    }
    public static String COLUMN_NameVahedShomaresh()
    {
        return COLUMN_NameVahedShomaresh;
    }
    public static String COLUMN_VaznKhales()
    {
        return COLUMN_VaznKhales;
    }
    public static String COLUMN_VaznNaKhales()
    {
        return COLUMN_VaznNaKhales;
    }
    public static String COLUMN_VaznKarton()
    {
        return COLUMN_VaznKarton;
    }
    public static String COLUMN_BarCode()
    {
        return COLUMN_BarCode;
    }
    public static String COLUMN_VaznKartonTabdili()
    {
        return COLUMN_VaznKartonTabdili;
    }
    public static String COLUMN_HajmKartonTabdili()
    {
        return COLUMN_HajmKartonTabdili;
    }
    public static String COLUMN_ShomarehBach()
    {
        return COLUMN_ShomarehBach;
    }
    public static String COLUMN_MashmolMaliatAvarez()
    {
        return COLUMN_MashmolMaliatAvarez;
    }
    public static String COLUMN_ccBrand() {
        return COLUMN_ccBrand;
    }
    public static String COLUMN_ccGoroh() {
        return COLUMN_ccGoroh;
    }
    public static String COLUMN_NameGoroh() {
        return COLUMN_NameGoroh;
    }


    @SerializedName("ccKalaCode")
    @Expose
    private int ccKalaCode;
    @SerializedName("NameKala")
    @Expose
    private String nameKala;
    @SerializedName("NameBrand")
    @Expose
    private String nameBrand;
    @SerializedName("Tol")
    @Expose
    private double tol;
    @SerializedName("Arz")
    @Expose
    private double arz;
    @SerializedName("Ertefa")
    @Expose
    private double ertefa;
    @SerializedName("NameVahedSize")
    @Expose
    private String nameVahedSize;
    @SerializedName("NameVahedShomaresh")
    @Expose
    private String nameVahedShomaresh;
    @SerializedName("VaznKhales")
    @Expose
    private double vaznKhales;
    @SerializedName("VaznNaKhales")
    @Expose
    private double vaznNaKhales;
    @SerializedName("VaznKarton")
    @Expose
    private double vaznKarton;
    @SerializedName("CodeKala")
    @Expose
    private String codeKala;
    @SerializedName("BarCode")
    @Expose
    private String barCode;
    @SerializedName("TedadDarKarton")
    @Expose
    private int tedadDarKarton;
    @SerializedName("TedadDarBasteh")
    @Expose
    private int tedadDarBasteh;
    @SerializedName("VaznKartonTabdili")
    @Expose
    private double vaznKartonTabdili;
    @SerializedName("HajmKartonTabdili")
    @Expose
    private double hajmKartonTabdili;
    @SerializedName("GheymatForoshAsli")
    @Expose
    private double gheymatForoshAsli;
    @SerializedName("GheymatMasrafKonandeh")
    @Expose
    private double gheymatMasrafKonandeh;
    @SerializedName("ShomarehBach")
    @Expose
    private String shomarehBach;
    @SerializedName("MashmolMaliatAvarez")
    @Expose
    private int mashmolMaliatAvarez;
    @SerializedName("ccBrand")
    @Expose
    private int ccBrand;
    @SerializedName("ccGoroh")
    @Expose
    private int ccGoroh;
    @SerializedName("NameGoroh")
    @Expose
    private String NameGoroh;

    public int getCcKalaCode() {
        return ccKalaCode;
    }
    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public String getNameKala() {
        return nameKala;
    }
    public void setNameKala(String nameKala) {
        this.nameKala = nameKala;
    }

    public String getNameBrand() {
        return nameBrand;
    }
    public void setNameBrand(String nameBrand) {
        this.nameBrand = nameBrand;
    }

    public double getTol() {
        return tol;
    }
    public void setTol(double tol) {
        this.tol = tol;
    }

    public double getArz() {
        return arz;
    }
    public void setArz(double arz) {
        this.arz = arz;
    }

    public double getErtefa() {
        return ertefa;
    }
    public void setErtefa(double ertefa) {
        this.ertefa = ertefa;
    }

    public String getNameVahedSize() {
        return nameVahedSize;
    }
    public void setNameVahedSize(String nameVahedSize) {
        this.nameVahedSize = nameVahedSize;
    }

    public String getNameVahedShomaresh() {
        return nameVahedShomaresh;
    }
    public void setNameVahedShomaresh(String nameVahedShomaresh) {
        this.nameVahedShomaresh = nameVahedShomaresh;
    }

    public double getVaznKhales() {
        return vaznKhales;
    }
    public void setVaznKhales(double vaznKhales) {
        this.vaznKhales = vaznKhales;
    }

    public double getVaznNaKhales() {
        return vaznNaKhales;
    }
    public void setVaznNaKhales(double vaznNaKhales) {
        this.vaznNaKhales = vaznNaKhales;
    }

    public double getVaznKarton() {
        return vaznKarton;
    }
    public void setVaznKarton(double vaznKarton) {
        this.vaznKarton = vaznKarton;
    }

    public String getCodeKala() {
        return codeKala;
    }
    public void setCodeKala(String codeKala) {
        this.codeKala = codeKala;
    }

    public String getBarCode() {
        return barCode;
    }
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getTedadDarKarton() {
        return tedadDarKarton;
    }
    public void setTedadDarKarton(int tedadDarKarton) {
        this.tedadDarKarton = tedadDarKarton;
    }

    public int getTedadDarBasteh() {
        return tedadDarBasteh;
    }
    public void setTedadDarBasteh(int tedadDarBasteh) {
        this.tedadDarBasteh = tedadDarBasteh;
    }

    public double getVaznKartonTabdili() {
        return vaznKartonTabdili;
    }
    public void setVaznKartonTabdili(double vaznKartonTabdili) {
        this.vaznKartonTabdili = vaznKartonTabdili;
    }

    public double getHajmKartonTabdili() {
        return hajmKartonTabdili;
    }
    public void setHajmKartonTabdili(double hajmKartonTabdili) {
        this.hajmKartonTabdili = hajmKartonTabdili;
    }

    public double getGheymatForoshAsli() {
        return gheymatForoshAsli;
    }
    public void setGheymatForoshAsli(double gheymatForoshAsli) {
        this.gheymatForoshAsli = gheymatForoshAsli;
    }

    public double getGheymatMasrafKonandeh() {
        return gheymatMasrafKonandeh;
    }
    public void setGheymatMasrafKonandeh(double gheymatMasrafKonandeh) {
        this.gheymatMasrafKonandeh = gheymatMasrafKonandeh;
    }

    public String getShomarehBach() {
        return shomarehBach;
    }
    public void setShomarehBach(String shomarehBach) {
        this.shomarehBach = shomarehBach;
    }

    public int getMashmolMaliatAvarez() {
        return mashmolMaliatAvarez;
    }
    public void setMashmolMaliatAvarez(int mashmolMaliatAvarez) {
        this.mashmolMaliatAvarez = mashmolMaliatAvarez;
    }

    public int getCcBrand() {
        return ccBrand;
    }

    public void setCcBrand(int ccBrand) {
        this.ccBrand = ccBrand;
    }

    public int getCcGoroh() {
        return ccGoroh;
    }

    public void setCcGoroh(int ccGoroh) {
        this.ccGoroh = ccGoroh;
    }

    public String getNameGoroh() {
        return NameGoroh;
    }

    public void setNameGoroh(String nameGoroh) {
        NameGoroh = nameGoroh;
    }
}
