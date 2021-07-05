package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KalaModel
{

    private static final String TABLE_NAME = "Kala";
    private static final String COLUMN_Radif = "Radif";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_CodeKala = "CodeKala";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_TedadMojodyGhabelForosh = "TedadMojodyGhabelForosh";
    private static final String COLUMN_ccTaminKonandeh = "ccTaminKonandeh";
    private static final String COLUMN_TedadDarKarton = "TedadDarKarton";
    private static final String COLUMN_TedadDarBasteh = "TedadDarBasteh";
    private static final String COLUMN_Adad = "Adad";
    private static final String COLUMN_CodeSort = "CodeSort";
    private static final String COLUMN_MashmolMaliatAvarez = "MashmolMaliatAvarez";
    private static final String COLUMN_MablaghForosh = "MablaghForosh";
    private static final String COLUMN_MablaghMasrafKonandeh = "MablaghMasrafKonandeh";
    private static final String COLUMN_LastMablaghForosh = "LastMablaghForosh";
    private static final String COLUMN_ccGorohKala = "ccGorohKala";
    private static final String COLUMN_ccBrand = "ccBrand";
    private static final String COLUMN_MablaghKharid = "MablaghKharid";
    private static final String COLUMN_Tol = "Tol";
    private static final String COLUMN_Arz = "Arz";
    private static final String COLUMN_Ertefa = "Ertefa";
    private static final String COLUMN_ccVahedSize = "ccVahedSize";
    private static final String COLUMN_VaznKhales = "VaznKhales";
    private static final String COLUMN_VaznNaKhales = "VaznNaKhales";
    private static final String COLUMN_VaznKarton = "VaznKarton";
    private static final String COLUMN_ccVahedVazn = "ccVahedVazn";
    private static final String COLUMN_BarCode = "BarCode";
    private static final String COLUMN_TarikhTolid = "TarikhTolid";
    private static final String COLUMN_NameVahedVazn = "NameVahedVazn";
    private static final String COLUMN_NameBrand = "NameBrand";
    private static final String COLUMN_NameVahedSize = "NameVahedSize";
    private static final String COLUMN_ccVahedShomaresh = "ccVahedShomaresh";
    private static final String COLUMN_NameVahedShomaresh = "NameVahedShomaresh";
    private static final String COLUMN_TarikhEngheza = "TarikhEngheza";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_GheymatForoshAsli="GheymatForoshAsli";
    private static final String COLUMN_GheymatMasrafKonandehAsli="GheymatMasrafKonandehAsli";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_Radif() {
        return COLUMN_Radif;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_CodeKala() {
        return COLUMN_CodeKala;
    }
    public static String COLUMN_NameKala() {
        return COLUMN_NameKala;
    }
    public static String COLUMN_TedadMojodyGhabelForosh() {
        return COLUMN_TedadMojodyGhabelForosh;
    }
    public static String COLUMN_ccTaminKonandeh() {
        return COLUMN_ccTaminKonandeh;
    }
    public static String COLUMN_TedadDarKarton() {
        return COLUMN_TedadDarKarton;
    }
    public static String COLUMN_TedadDarBasteh() {
        return COLUMN_TedadDarBasteh;
    }
    public static String COLUMN_Adad() {
        return COLUMN_Adad;
    }
    public static String COLUMN_CodeSort() {
        return COLUMN_CodeSort;
    }
    public static String COLUMN_MashmolMaliatAvarez() {
        return COLUMN_MashmolMaliatAvarez;
    }
    public static String COLUMN_MablaghForosh() {
        return COLUMN_MablaghForosh;
    }
    public static String COLUMN_MablaghMasrafKonandeh() {
        return COLUMN_MablaghMasrafKonandeh;
    }
    public static String COLUMN_LastMablaghForosh() {
        return COLUMN_LastMablaghForosh;
    }
    public static String COLUMN_ccGorohKala() {
        return COLUMN_ccGorohKala;
    }
    public static String COLUMN_ccBrand() {
        return COLUMN_ccBrand;
    }
    public static String COLUMN_MablaghKharid() {
        return COLUMN_MablaghKharid;
    }
    public static String COLUMN_Tol() {
        return COLUMN_Tol;
    }
    public static String COLUMN_Arz() {
        return COLUMN_Arz;
    }
    public static String COLUMN_Ertefa() {
        return COLUMN_Ertefa;
    }
    public static String COLUMN_ccVahedSize() {
        return COLUMN_ccVahedSize;
    }
    public static String COLUMN_VaznKhales() {
        return COLUMN_VaznKhales;
    }
    public static String COLUMN_VaznNaKhales() {
        return COLUMN_VaznNaKhales;
    }
    public static String COLUMN_VaznKarton() {
        return COLUMN_VaznKarton;
    }
    public static String COLUMN_ccVahedVazn() {
        return COLUMN_ccVahedVazn;
    }
    public static String COLUMN_BarCode() {
        return COLUMN_BarCode;
    }
    public static String COLUMN_TarikhTolid() {
        return COLUMN_TarikhTolid;
    }
    public static String COLUMN_NameVahedVazn() {
        return COLUMN_NameVahedVazn;
    }
    public static String COLUMN_NameBrand() {
        return COLUMN_NameBrand;
    }
    public static String COLUMN_NameVahedSize() {
        return COLUMN_NameVahedSize;
    }
    public static String COLUMN_ccVahedShomaresh() {
        return COLUMN_ccVahedShomaresh;
    }
    public static String COLUMN_NameVahedShomaresh() {
        return COLUMN_NameVahedShomaresh;
    }
    public static String COLUMN_TarikhEngheza() {
        return COLUMN_TarikhEngheza;
    }
    public static String COLUMN_ShomarehBach() {
        return COLUMN_ShomarehBach;
    }
    public static String COLUMN_GheymatForoshAsli() {
        return COLUMN_GheymatForoshAsli;
    }
    public static String COLUMN_GheymatMasrafKonandehAsli() {
        return COLUMN_GheymatMasrafKonandehAsli;
    }




    private int Radif;
    public void setRadif(int Radif){
        this.Radif = Radif;
    }
    public int getRadif(){
        return this.Radif;
    }


    private int ccKalaCode;
    public void setCcKalaCode(int ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public int getCcKalaCode(){
        return this.ccKalaCode;
    }


    private String CodeKala;
    public void setCodeKala(String CodeKala){
        this.CodeKala = CodeKala;
    }
    public String getCodeKala(){
        return this.CodeKala;
    }


    private String NameKala;
    public void setNameKala(String NameKala){
        this.NameKala = NameKala;
    }
    public String getNameKala(){
        return this.NameKala;
    }


    private int TedadMojodyGhabelForosh;
    public void setTedadMojodyGhabelForosh(int TedadMojodyGhabelForosh){
        this.TedadMojodyGhabelForosh = TedadMojodyGhabelForosh;
    }
    public int getTedadMojodyGhabelForosh(){
        return this.TedadMojodyGhabelForosh;
    }


    private float LastMablaghForosh;
    public void setLastMablaghForosh(float LastMablaghForosh){
        this.LastMablaghForosh = LastMablaghForosh;
    }
    public float getLastMablaghForosh(){
        return this.LastMablaghForosh;
    }


    private float MablaghForosh;
    public void setMablaghForosh(float MablaghForosh){
        this.MablaghForosh = MablaghForosh;
    }
    public float getMablaghForosh(){
        return this.MablaghForosh;
    }


    private float MablaghMasrafKonandeh;
    public void setMablaghMasrafKonandeh(float MablaghMasrafKonandeh){
        this.MablaghMasrafKonandeh = MablaghMasrafKonandeh;
    }
    public float getMablaghMasrafKonandeh(){
        return this.MablaghMasrafKonandeh;
    }

    private float MablaghKharid;
    public void setMablaghKharid(float MablaghKharid){
        this.MablaghKharid = MablaghKharid;
    }
    public float getMablaghKharid(){
        return this.MablaghKharid;
    }


    private int TedadDarKarton;
    public void setTedadDarKarton(int TedadDarKarton){
        this.TedadDarKarton = TedadDarKarton;
    }
    public int getTedadDarKarton(){
        return this.TedadDarKarton;
    }


    private int TedadDarBasteh;
    public void setTedadDarBasteh(int TedadDarBasteh){
        this.TedadDarBasteh = TedadDarBasteh;
    }
    public int getTedadDarBasteh(){
        return this.TedadDarBasteh;
    }


    private int Adad;
    public void setAdad(int Adad){
        this.Adad = Adad;
    }
    public int getAdad(){
        return this.Adad;
    }


    private String CodeSort;
    public void setCodeSort(String CodeSort){
        this.CodeSort = CodeSort;
    }
    public String getCodeSort(){
        return this.CodeSort;
    }


    private String NameVahedVazn;
    public void setNameVahedVazn(String NameVahedVazn){
        this.NameVahedVazn = NameVahedVazn;
    }
    public String getNameVahedVazn(){
        return this.NameVahedVazn;
    }


    private int MashmolMaliatAvarez;
    public void setMashmolMaliatAvarez(int MashmolMaliatAvarez){
        this.MashmolMaliatAvarez = MashmolMaliatAvarez;
    }
    public int getMashmolMaliatAvarez(){
        return this.MashmolMaliatAvarez;
    }


    private int ccBrand;
    public void setCcBrand(int ccBrand){
        this.ccBrand = ccBrand;
    }
    public int getCcBrand(){
        return this.ccBrand;
    }


    private String NameBrand;
    public void setNameBrand(String NameBrand){
        this.NameBrand = NameBrand;
    }
    public String getNameBrand(){
        return this.NameBrand;
    }


    private float Tol;
    public void setTol(float Tol){
        this.Tol = Tol;
    }
    public float getTol(){
        return this.Tol;
    }


    private float Arz;
    public void setArz(float Arz){
        this.Arz = Arz;
    }
    public float getArz(){
        return this.Arz;
    }


    private float Ertefa;
    public void setErtefa(float Ertefa){
        this.Ertefa = Ertefa;
    }
    public float getErtefa(){
        return this.Ertefa;
    }


    private int ccVahedSize;
    public void setCcVahedSize(int ccVahedSize){
        this.ccVahedSize = ccVahedSize;
    }
    public int getCcVahedSize(){
        return this.ccVahedSize;
    }


    private String NameVahedSize;
    public void setNameVahedSize(String NameVahedSize){
        this.NameVahedSize = NameVahedSize;
    }
    public String getNameVahedSize(){
        return this.NameVahedSize;
    }


    private int ccVahedShomaresh;
    public void setCcVahedShomaresh(int ccVahedShomaresh){
        this.ccVahedShomaresh = ccVahedShomaresh;
    }
    public int getCcVahedShomaresh(){
        return this.ccVahedShomaresh;
    }


    private String NameVahedShomaresh;
    public void setNameVahedShomaresh(String NameVahedShomaresh){
        this.NameVahedShomaresh = NameVahedShomaresh;
    }
    public String getNameVahedShomaresh(){
        return this.NameVahedShomaresh;
    }


    private float VaznKhales;
    public void setVaznKhales(float VaznKhales){
        this.VaznKhales = VaznKhales;
    }
    public float getVaznKhales(){
        return this.VaznKhales;
    }


    private float VaznNaKhales;
    public void setVaznNaKhales(float VaznNaKhales){
        this.VaznNaKhales = VaznNaKhales;
    }
    public float getVaznNaKhales(){
        return this.VaznNaKhales;
    }


    private float VaznKarton;
    public void setVaznKarton(float VaznKarton){
        this.VaznKarton = VaznKarton;
    }
    public float getVaznKarton(){
        return this.VaznKarton;
    }


    private int ccVahedVazn;
    public void setCcVahedVazn(int ccVahedVazn){
        this.ccVahedVazn = ccVahedVazn;
    }
    public int getCcVahedVazn(){
        return this.ccVahedVazn;
    }


    private String BarCode;
    public void setBarCode(String BarCode){
        this.BarCode = BarCode;
    }
    public String getBarCode(){
        return this.BarCode;
    }


    private int ccTaminKonandeh;
    public void setCcTaminKonandeh(int ccTaminKonandeh){
        this.ccTaminKonandeh = ccTaminKonandeh;
    }
    public int getCcTaminKonandeh(){
        return this.ccTaminKonandeh;
    }


    private int ccGorohKala;
    public void setCcGorohKala(int ccGorohKala){
        this.ccGorohKala = ccGorohKala;
    }
    public int getCcGorohKala(){
        return this.ccGorohKala;
    }

    private String TarikhEngheza;
    public void setTarikhEngheza(String TarikhEngheza){
        this.TarikhEngheza = TarikhEngheza;
    }
    public String getTarikhEngheza(){
        return this.TarikhEngheza;
    }


    private String TarikhTolid;
    public void setTarikhTolid(String TarikhTolid){
        this.TarikhTolid = TarikhTolid;
    }
    public String getTarikhTolid(){
        return this.TarikhTolid;
    }


    private String ShomarehBach;
    public void setShomarehBach(String ShomarehBach){
        this.ShomarehBach = ShomarehBach;
    }
    public String getShomarehBach(){
        return this.ShomarehBach;
    }

    private int Id;
    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }


   private int GheymatForoshAsli;

    public int getGheymatForoshAsli() {
        return GheymatForoshAsli;
    }

    public void setGheymatForoshAsli(int gheymatForoshAsli) {
        this.GheymatForoshAsli = gheymatForoshAsli;
    }


    private float GheymatMasrafKonandehAsli;

    public float getGheymatMasrafKonandehAsli() {
        return GheymatMasrafKonandehAsli;
    }

    public void setGheymatMasrafKonandehAsli(float GheymatMasrafKonandehAsli) {
        this.GheymatMasrafKonandehAsli = GheymatMasrafKonandehAsli;
    }

    @Override
    public String toString() {
        return "KalaModel{" +
                "Radif=" + Radif +
                ", ccKalaCode=" + ccKalaCode +
                ", CodeKala='" + CodeKala + '\'' +
                ", NameKala='" + NameKala + '\'' +
                ", TedadMojodyGhabelForosh=" + TedadMojodyGhabelForosh +
                ", LastMablaghForosh=" + LastMablaghForosh +
                ", MablaghForosh=" + MablaghForosh +
                ", MablaghMasrafKonandeh=" + MablaghMasrafKonandeh +
                ", MablaghKharid=" + MablaghKharid +
                ", TedadDarKarton=" + TedadDarKarton +
                ", TedadDarBasteh=" + TedadDarBasteh +
                ", Adad=" + Adad +
                ", CodeSort='" + CodeSort + '\'' +
                ", NameVahedVazn='" + NameVahedVazn + '\'' +
                ", MashmolMaliatAvarez=" + MashmolMaliatAvarez +
                ", ccBrand=" + ccBrand +
                ", NameBrand='" + NameBrand + '\'' +
                ", Tol=" + Tol +
                ", Arz=" + Arz +
                ", Ertefa=" + Ertefa +
                ", ccVahedSize=" + ccVahedSize +
                ", NameVahedSize='" + NameVahedSize + '\'' +
                ", ccVahedShomaresh=" + ccVahedShomaresh +
                ", NameVahedShomaresh='" + NameVahedShomaresh + '\'' +
                ", VaznKhales=" + VaznKhales +
                ", VaznNaKhales=" + VaznNaKhales +
                ", VaznKarton=" + VaznKarton +
                ", ccVahedVazn=" + ccVahedVazn +
                ", BarCode='" + BarCode + '\'' +
                ", ccTaminKonandeh=" + ccTaminKonandeh +
                ", ccGorohKala=" + ccGorohKala +
                ", TarikhEngheza='" + TarikhEngheza + '\'' +
                ", TarikhTolid='" + TarikhTolid + '\'' +
                ", ShomarehBach='" + ShomarehBach + '\'' +
                ", Id=" + Id +
                ", gheymatForoshAsli=" + GheymatForoshAsli +
                ", GheymatMasrafKonanadehAsli=" + GheymatMasrafKonandehAsli +

                '}';
    }
}
