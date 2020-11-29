package com.saphamrah.Model;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.DAO.DarkhastFaktorSatrTakhfifDAO;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DarkhastFaktorSatrModel
{

    private static final String TABLE_NAME = "DarkhastFaktorSatr";
    private static final String COLUMN_ccDarkhastFaktorSatr = "ccDarkhastFaktorSatr";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccTaminKonandeh = "ccTaminKonandeh";
    private static final String COLUMN_ccKala = "ccKala";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_Tedad3 = "Tedad3";
    private static final String COLUMN_CodeNoeKala = "CodeNoeKala";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_TarikhTolid = "TarikhTolid";
    private static final String COLUMN_MablaghForosh = "MablaghForosh";
    private static final String COLUMN_MablaghForoshKhalesKala = "MablaghForoshKhalesKala";
    private static final String COLUMN_MablaghTakhfifNaghdiVahed = "MablaghTakhfifNaghdiVahed";
    private static final String COLUMN_Maliat = "Maliat";
    private static final String COLUMN_Avarez = "Avarez";
    private static final String COLUMN_ccAfrad = "ccAfrad";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";
    private static final String COLUMN_TarikhEngheza = "TarikhEngheza";
    private static final String COLUMN_ccAnbarMarjoee = "ccAnbarMarjoee";
    private static final String COLUMN_ccAnbarGhesmat = "ccAnbarGhesmat";
    private static final String COLUMN_MablaghKharid = "MablaghKharid";
    private static final String COLUMN_GheymatMasrafKonandeh = "GheymatMasrafKonandeh";
    private static final String COLUMN_GheymatForoshAsli = "GheymatForoshAsli";
    private static final String COLUMN_Vazn = "Vazn";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDarkhastFaktorSatr() {
        return COLUMN_ccDarkhastFaktorSatr;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccTaminKonandeh() {
        return COLUMN_ccTaminKonandeh;
    }
    public static String COLUMN_ccKala() {
        return COLUMN_ccKala;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_Tedad3() {
        return COLUMN_Tedad3;
    }
    public static String COLUMN_CodeNoeKala() {
        return COLUMN_CodeNoeKala;
    }
    public static String COLUMN_ShomarehBach() {
        return COLUMN_ShomarehBach;
    }
    public static String COLUMN_TarikhTolid() {
        return COLUMN_TarikhTolid;
    }
    public static String COLUMN_MablaghForosh() {
        return COLUMN_MablaghForosh;
    }
    public static String COLUMN_MablaghForoshKhalesKala() {
        return COLUMN_MablaghForoshKhalesKala;
    }
    public static String COLUMN_MablaghTakhfifNaghdiVahed() {
        return COLUMN_MablaghTakhfifNaghdiVahed;
    }
    public static String COLUMN_Maliat() {
        return COLUMN_Maliat;
    }
    public static String COLUMN_Avarez() {
        return COLUMN_Avarez;
    }
    public static String COLUMN_ccAfrad() {
        return COLUMN_ccAfrad;
    }
    public static String COLUMN_ExtraProp_IsOld() {
        return COLUMN_ExtraProp_IsOld;
    }
    public static String COLUMN_TarikhEngheza() {
        return COLUMN_TarikhEngheza;
    }
    public static String COLUMN_ccAnbarMarjoee() {
        return COLUMN_ccAnbarMarjoee;
    }
    public static String COLUMN_ccAnbarGhesmat() {
        return COLUMN_ccAnbarGhesmat;
    }
    public static String COLUMN_MablaghKharid() {
        return COLUMN_MablaghKharid;
    }
    public static String COLUMN_GheymatMasrafKonandeh() {
        return COLUMN_GheymatMasrafKonandeh;
    }
    public static String COLUMN_GheymatForoshAsli() {
        return COLUMN_GheymatForoshAsli;
    }
    public static String COLUMN_Vazn() {
        return COLUMN_Vazn;
    }


    @SerializedName("ccDarkhastFaktor")
    @Expose
    private Long ccDarkhastFaktor;
    @SerializedName("ccDarkhastFaktorSatr")
    @Expose
    private int ccDarkhastFaktorSatr;
    @SerializedName("ccDarkhastFaktorSatrTaavoni")
    @Expose
    private String ccDarkhastFaktorSatrTaavoni;
    @SerializedName("ccDarkhastFaktorSatrPPC")
    @Expose
    private String ccDarkhastFaktorSatrPPC;
    @SerializedName("ccAfrad")
    @Expose
    private int ccAfrad;
    @SerializedName("CodeNoeKala")
    @Expose
    private int CodeNoeKala;
    @SerializedName("ccKala")
    @Expose
    private int ccKala;
    @SerializedName("ccKalaCode")
    @Expose
    private int ccKalaCode;
    @SerializedName("ccTaminKonandeh")
    @Expose
    private int ccTaminKonandeh;
    @SerializedName("ShomarehBach")
    @Expose
    private String ShomarehBach;
    @SerializedName("TarikhTolid")
    @Expose
    private String TarikhTolid;
    @SerializedName("TarikhEngheza")
    @Expose
    private String TarikhEngheza;
    @SerializedName("Tedad1")
    @Expose
    private int Tedad1;
    @SerializedName("Tedad2")
    @Expose
    private int Tedad2;
    @SerializedName("Tedad3")
    @Expose
    private int Tedad3;
    @SerializedName("MablaghForosh")
    @Expose
    private double MablaghForosh;
    @SerializedName("MablaghTakhfifDarkhast")
    @Expose
    private String MablaghTakhfifDarkhast;
    @SerializedName("MablaghTakhfifFaktor")
    @Expose
    private String MablaghTakhfifFaktor;
    @SerializedName("GheymatMiangin")
    @Expose
    private int GheymatMiangin;
    @SerializedName("ccTafkikJoze")
    @Expose
    private String ccTafkikJoze;
    @SerializedName("MojodyGhabelForosh")
    @Expose
    private String MojodyGhabelForosh;
    @SerializedName("DateVorod")
    @Expose
    private String DateVorod;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;
    @SerializedName("DarsadTakhfifTaavoni")
    @Expose
    private String DarsadTakhfifTaavoni;
    @SerializedName("ccUser")
    @Expose
    private String ccUser;
    @SerializedName("MablaghTakhfifNaghdiVahed")
    @Expose
    private float MablaghTakhfifNaghdiVahed;
    @SerializedName("DisCntType")
    @Expose
    private String DisCntType;
    @SerializedName("DisCntSubType")
    @Expose
    private String DisCntSubType;
    @SerializedName("GheymatKharid")
    @Expose
    private String GheymatKharid;
    @SerializedName("TarikhFaktor")
    @Expose
    private String TarikhFaktor;
    @SerializedName("Maliat")
    @Expose
    private float Maliat;
    @SerializedName("Avarez")
    @Expose
    private float Avarez;
    @SerializedName("MablaghForoshKhalesKala")
    @Expose
    private float MablaghForoshKhalesKala;
    @SerializedName("MablaghKharid")
    @Expose
    private float MablaghKharid;
    @SerializedName("MablaghMasrafKonandeh")
    @Expose
    private int MablaghMasrafKonandeh;
    @SerializedName("ccAnbarMarjoee")
    @Expose
    private int ccAnbarMarjoee;
    @SerializedName("ccAnbarGhesmat")
    @Expose
    private int ccAnbarGhesmat;
    @SerializedName("GheymatMasrafKonandeh")
    @Expose
    private float GheymatMasrafKonandeh;
    @SerializedName("darkhastFaktorSatrTakhfifs")
    @Expose
    private String darkhastFaktorSatrTakhfifs;

    private float GheymatForoshAsli;
    private float Vazn;
    private boolean ExtraProp_IsOld;



    public void setCcDarkhastFaktor(Long ccDarkhastFaktor){
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }
    public Long getCcDarkhastFaktor(){
        return this.ccDarkhastFaktor;
    }
    public void setCcDarkhastFaktorSatr(int ccDarkhastFaktorSatr){
        this.ccDarkhastFaktorSatr = ccDarkhastFaktorSatr;
    }
    public int getCcDarkhastFaktorSatr(){
        return this.ccDarkhastFaktorSatr;
    }
    public void setCcDarkhastFaktorSatrTaavoni(String ccDarkhastFaktorSatrTaavoni){
        this.ccDarkhastFaktorSatrTaavoni = ccDarkhastFaktorSatrTaavoni;
    }
    public String getCcDarkhastFaktorSatrTaavoni(){
        return this.ccDarkhastFaktorSatrTaavoni;
    }
    public void setCcDarkhastFaktorSatrPPC(String ccDarkhastFaktorSatrPPC){
        this.ccDarkhastFaktorSatrPPC = ccDarkhastFaktorSatrPPC;
    }
    public String getCcDarkhastFaktorSatrPPC(){
        return this.ccDarkhastFaktorSatrPPC;
    }
    public void setCcAfrad(int ccAfrad){
        this.ccAfrad = ccAfrad;
    }
    public int getCcAfrad(){
        return this.ccAfrad;
    }
    public void setCodeNoeKala(int CodeNoeKala){
        this.CodeNoeKala = CodeNoeKala;
    }
    public int getCodeNoeKala(){
        return this.CodeNoeKala;
    }
    public void setCcKala(int ccKala){
        this.ccKala = ccKala;
    }
    public int getCcKala(){
        return this.ccKala;
    }
    public void setCcKalaCode(int ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public int getCcKalaCode(){
        return this.ccKalaCode;
    }
    public void setCcTaminKonandeh(int ccTaminKonandeh){
        this.ccTaminKonandeh = ccTaminKonandeh;
    }
    public int getCcTaminKonandeh(){
        return this.ccTaminKonandeh;
    }
    public void setShomarehBach(String ShomarehBach){
        this.ShomarehBach = ShomarehBach;
    }
    public String getShomarehBach(){
        return this.ShomarehBach;
    }
    public void setTarikhTolid(String TarikhTolid){
        this.TarikhTolid = TarikhTolid;
    }
    public String getTarikhTolid(){
        return this.TarikhTolid;
    }
    public void setTarikhEngheza(String TarikhEngheza){
        this.TarikhEngheza = TarikhEngheza;
    }
    public String getTarikhEngheza(){
        return this.TarikhEngheza;
    }
    public void setTedad1(int Tedad1){
        this.Tedad1 = Tedad1;
    }
    public int getTedad1(){
        return this.Tedad1;
    }
    public void setTedad2(int Tedad2){
        this.Tedad2 = Tedad2;
    }
    public int getTedad2(){
        return this.Tedad2;
    }
    public void setTedad3(int Tedad3){
        this.Tedad3 = Tedad3;
    }
    public int getTedad3(){
        return this.Tedad3;
    }
    public void setMablaghForosh(double MablaghForosh){
        this.MablaghForosh = MablaghForosh;
    }
    public double getMablaghForosh(){
        return this.MablaghForosh;
    }
    public void setMablaghTakhfifDarkhast(String MablaghTakhfifDarkhast){
        this.MablaghTakhfifDarkhast = MablaghTakhfifDarkhast;
    }
    public String getMablaghTakhfifDarkhast(){
        return this.MablaghTakhfifDarkhast;
    }
    public void setMablaghTakhfifFaktor(String MablaghTakhfifFaktor){
        this.MablaghTakhfifFaktor = MablaghTakhfifFaktor;
    }
    public String getMablaghTakhfifFaktor(){
        return this.MablaghTakhfifFaktor;
    }
    public void setGheymatMiangin(int GheymatMiangin){
        this.GheymatMiangin = GheymatMiangin;
    }
    public int getGheymatMiangin(){
        return this.GheymatMiangin;
    }
    public void setCcTafkikJoze(String ccTafkikJoze){
        this.ccTafkikJoze = ccTafkikJoze;
    }
    public String getCcTafkikJoze(){
        return this.ccTafkikJoze;
    }
    public void setMojodyGhabelForosh(String MojodyGhabelForosh){
        this.MojodyGhabelForosh = MojodyGhabelForosh;
    }
    public String getMojodyGhabelForosh(){
        return this.MojodyGhabelForosh;
    }
    public void setDateVorod(String DateVorod){
        this.DateVorod = DateVorod;
    }
    public String getDateVorod(){
        return this.DateVorod;
    }
    public void setCodeVazeiat(int CodeVazeiat){
        this.CodeVazeiat = CodeVazeiat;
    }
    public int getCodeVazeiat(){
        return this.CodeVazeiat;
    }
    public void setDarsadTakhfifTaavoni(String DarsadTakhfifTaavoni){
        this.DarsadTakhfifTaavoni = DarsadTakhfifTaavoni;
    }
    public String getDarsadTakhfifTaavoni(){
        return this.DarsadTakhfifTaavoni;
    }
    public void setCcUser(String ccUser){
        this.ccUser = ccUser;
    }
    public String getCcUser(){
        return this.ccUser;
    }
    public void setMablaghTakhfifNaghdiVahed(float MablaghTakhfifNaghdiVahed){
        this.MablaghTakhfifNaghdiVahed = MablaghTakhfifNaghdiVahed;
    }
    public float getMablaghTakhfifNaghdiVahed(){
        return this.MablaghTakhfifNaghdiVahed;
    }
    public void setDisCntType(String DisCntType){
        this.DisCntType = DisCntType;
    }
    public String getDisCntType(){
        return this.DisCntType;
    }
    public void setDisCntSubType(String DisCntSubType){
        this.DisCntSubType = DisCntSubType;
    }
    public String getDisCntSubType(){
        return this.DisCntSubType;
    }
    public void setGheymatKharid(String GheymatKharid){
        this.GheymatKharid = GheymatKharid;
    }
    public String getGheymatKharid(){
        return this.GheymatKharid;
    }
    public void setTarikhFaktor(String TarikhFaktor){
        this.TarikhFaktor = TarikhFaktor;
    }
    public String getTarikhFaktor(){
        return this.TarikhFaktor;
    }
    public void setMaliat(float Maliat){
        this.Maliat = Maliat;
    }
    public float getMaliat(){
        return this.Maliat;
    }
    public void setAvarez(float Avarez){
        this.Avarez = Avarez;
    }
    public float getAvarez(){
        return this.Avarez;
    }
    public void setMablaghForoshKhalesKala(float MablaghForoshKhalesKala){
        this.MablaghForoshKhalesKala = MablaghForoshKhalesKala;
    }
    public float getMablaghForoshKhalesKala(){
        return this.MablaghForoshKhalesKala;
    }
    public void setMablaghKharid(float MablaghKharid){
        this.MablaghKharid = MablaghKharid;
    }
    public float getMablaghKharid(){
        return this.MablaghKharid;
    }
    public void setMablaghMasrafKonandeh(int MablaghMasrafKonandeh){
        this.MablaghMasrafKonandeh = MablaghMasrafKonandeh;
    }
    public int getMablaghMasrafKonandeh(){
        return this.MablaghMasrafKonandeh;
    }
    public void setCcAnbarMarjoee(int ccAnbarMarjoee){
        this.ccAnbarMarjoee = ccAnbarMarjoee;
    }
    public int getCcAnbarMarjoee(){
        return this.ccAnbarMarjoee;
    }
    public void setCcAnbarGhesmat(int ccAnbarGhesmat){
        this.ccAnbarGhesmat = ccAnbarGhesmat;
    }
    public int getCcAnbarGhesmat(){
        return this.ccAnbarGhesmat;
    }
    public void setGheymatMasrafKonandeh(float GheymatMasrafKonandeh){
        this.GheymatMasrafKonandeh = GheymatMasrafKonandeh;
    }
    public float getGheymatMasrafKonandeh(){
        return this.GheymatMasrafKonandeh;
    }
    public void setDarkhastFaktorSatrTakhfifs(String darkhastFaktorSatrTakhfifs){
        this.darkhastFaktorSatrTakhfifs = darkhastFaktorSatrTakhfifs;
    }
    public String getDarkhastFaktorSatrTakhfifs(){
        return this.darkhastFaktorSatrTakhfifs;
    }
    public boolean getExtraProp_IsOld() {
        return ExtraProp_IsOld;
    }
    public void setExtraProp_IsOld(boolean extraProp_IsOld) {
        ExtraProp_IsOld = extraProp_IsOld;
    }
    public float getGheymatForoshAsli() {
        return GheymatForoshAsli;
    }
    public void setGheymatForoshAsli(float gheymatForoshAsli) {
        GheymatForoshAsli = gheymatForoshAsli;
    }
    public float getVazn() {
        return Vazn;
    }
    public void setVazn(float vazn) {
        Vazn = vazn;
    }



    public JSONObject toJsonObject(Context context)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccDarkhastFaktorSatr" , ccDarkhastFaktorSatr);
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("ccTaminKonandeh" , ccTaminKonandeh);
            jsonObject.put("ccKala" , ccKala);
            jsonObject.put("ccKalaCode" , ccKalaCode);
            jsonObject.put("Tedad3" , Tedad3);
            jsonObject.put("CodeNoeKala" , CodeNoeKala);
            jsonObject.put("ShomarehBach" , ShomarehBach);
            jsonObject.put("TarikhTolid" , TarikhTolid == null ? "" : TarikhTolid);
            jsonObject.put("TarikhEngheza" , TarikhEngheza == null ? "" : TarikhEngheza);
            jsonObject.put("MablaghForosh" , MablaghForosh);
            jsonObject.put("MablaghForoshKhalesKala" , MablaghForoshKhalesKala);
            jsonObject.put("MablaghTakhfifNaghdiVahed" , MablaghTakhfifNaghdiVahed);
            jsonObject.put("Maliat" , Maliat);
            jsonObject.put("Avarez" , Avarez);
            jsonObject.put("ccAfrad" , ccAfrad);
            jsonObject.put("GheymatMasrafKonandeh" , GheymatMasrafKonandeh);
            jsonObject.put("GheymatForoshAsli" , GheymatForoshAsli);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorSatrModel", "", "toJsonObject", "");
        }
        return jsonObject;
    }

    public JSONObject toJsonObjectHavale(Context context)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccDarkhastHavalehSatr" , ccDarkhastFaktorSatr);
            jsonObject.put("ccDarkhastHavaleh" , ccDarkhastFaktor);
            jsonObject.put("ccTaminKonandeh" , ccTaminKonandeh);
            jsonObject.put("ccKalaCode" , ccKalaCode);
            jsonObject.put("Tedad3" , Tedad3);
            jsonObject.put("CodeNoeKala" , CodeNoeKala);
            jsonObject.put("ShomarehBach" , ShomarehBach);
            jsonObject.put("TarikhTolid" , TarikhTolid);
            jsonObject.put("TarikhEngheza" , TarikhEngheza);
            jsonObject.put("MablaghForosh" , MablaghForosh);
            //jsonObject.put("MablaghForoshKhalesKala" , MablaghForoshKhalesKala);
            jsonObject.put("MablaghTakhfifNaghdiVahed" , MablaghTakhfifNaghdiVahed);
            jsonObject.put("Maliat" , Maliat);
            jsonObject.put("Avarez" , Avarez);
            jsonObject.put("GheymatMasrafKonandeh" , GheymatMasrafKonandeh);
            jsonObject.put("GheymatForoshAsli" , GheymatForoshAsli);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorSatrModel", "", "toJsonObject", "");
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString() {
        return "DarkhastFaktorSatrModel{" +
                "ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccDarkhastFaktorSatr=" + ccDarkhastFaktorSatr +
                ", ccDarkhastFaktorSatrTaavoni='" + ccDarkhastFaktorSatrTaavoni + '\'' +
                ", ccDarkhastFaktorSatrPPC='" + ccDarkhastFaktorSatrPPC + '\'' +
                ", ccAfrad=" + ccAfrad +
                ", CodeNoeKala=" + CodeNoeKala +
                ", ccKala=" + ccKala +
                ", ccKalaCode=" + ccKalaCode +
                ", ccTaminKonandeh=" + ccTaminKonandeh +
                ", ShomarehBach='" + ShomarehBach + '\'' +
                ", TarikhTolid='" + TarikhTolid + '\'' +
                ", TarikhEngheza='" + TarikhEngheza + '\'' +
                ", Tedad1=" + Tedad1 +
                ", Tedad2=" + Tedad2 +
                ", Tedad3=" + Tedad3 +
                ", MablaghForosh=" + MablaghForosh +
                ", MablaghTakhfifDarkhast='" + MablaghTakhfifDarkhast + '\'' +
                ", MablaghTakhfifFaktor='" + MablaghTakhfifFaktor + '\'' +
                ", GheymatMiangin=" + GheymatMiangin +
                ", ccTafkikJoze='" + ccTafkikJoze + '\'' +
                ", MojodyGhabelForosh='" + MojodyGhabelForosh + '\'' +
                ", DateVorod='" + DateVorod + '\'' +
                ", CodeVazeiat=" + CodeVazeiat +
                ", DarsadTakhfifTaavoni='" + DarsadTakhfifTaavoni + '\'' +
                ", ccUser='" + ccUser + '\'' +
                ", MablaghTakhfifNaghdiVahed=" + MablaghTakhfifNaghdiVahed +
                ", DisCntType='" + DisCntType + '\'' +
                ", DisCntSubType='" + DisCntSubType + '\'' +
                ", GheymatKharid='" + GheymatKharid + '\'' +
                ", TarikhFaktor='" + TarikhFaktor + '\'' +
                ", Maliat=" + Maliat +
                ", Avarez=" + Avarez +
                ", MablaghForoshKhalesKala=" + MablaghForoshKhalesKala +
                ", MablaghKharid=" + MablaghKharid +
                ", MablaghMasrafKonandeh=" + MablaghMasrafKonandeh +
                ", ccAnbarMarjoee=" + ccAnbarMarjoee +
                ", ccAnbarGhesmat=" + ccAnbarGhesmat +
                ", GheymatMasrafKonandeh=" + GheymatMasrafKonandeh +
                ", Vazn='" + Vazn +
                ", darkhastFaktorSatrTakhfifs='" + darkhastFaktorSatrTakhfifs + '\'' +
                '}';
    }



}
