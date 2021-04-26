package com.saphamrah.Model;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

public class DariaftPardakhtPPCModel
{


    private static final String TABLE_NAME = "DariaftPardakhtPPC";
    private static final String COLUMN_ccDariaftPardakht = "ccDariaftPardakht";
    private static final String COLUMN_ccMarkazAnbar = "ccMarkazAnbar";
    private static final String COLUMN_CodeNoeVosol = "CodeNoeVosol";
    private static final String COLUMN_NameNoeVosol = "NameNoeVosol";
    private static final String COLUMN_ccShomarehHesab = "ccShomarehHesab";
    private static final String COLUMN_SharhShomarehHesab = "SharhShomarehHesab";
    private static final String COLUMN_ZamaneSabt = "ZamaneSabt";
    private static final String COLUMN_NameSahebHesab = "NameSahebHesab";
    private static final String COLUMN_ccBankSanad = "ccBankSanad";
    private static final String COLUMN_NameBankSanad = "NameBankSanad";
    private static final String COLUMN_NameShobehSanad = "NameShobehSanad";
    private static final String COLUMN_CodeShobehSanad = "CodeShobehSanad";
    private static final String COLUMN_ShomarehHesabSanad = "ShomarehHesabSanad";
    private static final String COLUMN_ccNoeHesabSanad = "ccNoeHesabSanad";
    private static final String COLUMN_NameNoeHesabSanad = "NameNoeHesabSanad";
    private static final String COLUMN_CodeNoeCheck = "CodeNoeCheck";
    private static final String COLUMN_NameNoeCheck = "NameNoeCheck";
    private static final String COLUMN_ShomarehSanad = "ShomarehSanad";
    private static final String COLUMN_TarikhSanad = "TarikhSanad";
    private static final String COLUMN_TarikhSanadShamsi = "TarikhSanadShamsi";
    private static final String COLUMN_Mablagh = "Mablagh";
    private static final String COLUMN_MablaghMandeh = "MablaghMandeh";
    private static final String COLUMN_ccDariaftPardakhtLink = "ccDariaftPardakhtLink";
    private static final String COLUMN_ccKardex = "ccKardex";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_IsPishDariaft = "IsPishDariaft";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_Tabdil_NaghdBeFish = "Tabdil_NaghdBeFish";
    private static final String COLUMN_NaghlAzGhabl = "NaghlAzGhabl";
    private static final String COLUMN_ccBrand = "ccBrand";
    private static final String COLUMN_ExtraProp_IsDirkard = "ExtraProp_IsDirkard";
    private static final String COLUMN_ExtraProp_IsSend = "ExtraProp_IsSend";
    private static final String COLUMN_ccAfradErsalKonandeh = "ccAfradErsalKonandeh";
    private static final String COLUMN_IsCheckMoshtary = "IsCheckMoshtary";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_ccMarkazSazmanForoshSakhtarForosh = "ccMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty = "ExtraProp_ccDaryaftPardakhtCheckBargashty";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDariaftPardakht() {
        return COLUMN_ccDariaftPardakht;
    }
    public static String COLUMN_ccMarkazAnbar() {
        return COLUMN_ccMarkazAnbar;
    }
    public static String COLUMN_CodeNoeVosol() {
        return COLUMN_CodeNoeVosol;
    }
    public static String COLUMN_NameNoeVosol() {
        return COLUMN_NameNoeVosol;
    }
    public static String COLUMN_ccShomarehHesab() {
        return COLUMN_ccShomarehHesab;
    }
    public static String COLUMN_SharhShomarehHesab() {
        return COLUMN_SharhShomarehHesab;
    }
    public static String COLUMN_ZamaneSabt() {
        return COLUMN_ZamaneSabt;
    }
    public static String COLUMN_NameSahebHesab() {
        return COLUMN_NameSahebHesab;
    }
    public static String COLUMN_ccBankSanad() {
        return COLUMN_ccBankSanad;
    }
    public static String COLUMN_NameBankSanad() {
        return COLUMN_NameBankSanad;
    }
    public static String COLUMN_NameShobehSanad() {
        return COLUMN_NameShobehSanad;
    }
    public static String COLUMN_CodeShobehSanad() {
        return COLUMN_CodeShobehSanad;
    }
    public static String COLUMN_ShomarehHesabSanad() {
        return COLUMN_ShomarehHesabSanad;
    }
    public static String COLUMN_ccNoeHesabSanad() {
        return COLUMN_ccNoeHesabSanad;
    }
    public static String COLUMN_NameNoeHesabSanad() {
        return COLUMN_NameNoeHesabSanad;
    }
    public static String COLUMN_CodeNoeCheck() {
        return COLUMN_CodeNoeCheck;
    }
    public static String COLUMN_NameNoeCheck() {
        return COLUMN_NameNoeCheck;
    }
    public static String COLUMN_ShomarehSanad() {
        return COLUMN_ShomarehSanad;
    }
    public static String COLUMN_TarikhSanad() {
        return COLUMN_TarikhSanad;
    }
    public static String COLUMN_TarikhSanadShamsi() {
        return COLUMN_TarikhSanadShamsi;
    }
    public static String COLUMN_Mablagh() {
        return COLUMN_Mablagh;
    }
    public static String COLUMN_MablaghMandeh() {
        return COLUMN_MablaghMandeh;
    }
    public static String COLUMN_ccDariaftPardakhtLink() {
        return COLUMN_ccDariaftPardakhtLink;
    }
    public static String COLUMN_ccKardex() {
        return COLUMN_ccKardex;
    }
    public static String COLUMN_CodeVazeiat() {
        return COLUMN_CodeVazeiat;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_IsPishDariaft() {
        return COLUMN_IsPishDariaft;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_Tabdil_NaghdBeFish() {
        return COLUMN_Tabdil_NaghdBeFish;
    }
    public static String COLUMN_NaghlAzGhabl() {
        return COLUMN_NaghlAzGhabl;
    }
    public static String COLUMN_ccBrand() {
        return COLUMN_ccBrand;
    }
    public static String COLUMN_ExtraProp_IsDirkard() {
        return COLUMN_ExtraProp_IsDirkard;
    }
    public static String COLUMN_ExtraProp_IsSend() {
        return COLUMN_ExtraProp_IsSend;
    }
    public static String COLUMN_ccAfradErsalKonandeh() {
        return COLUMN_ccAfradErsalKonandeh;
    }
    public static String COLUMN_IsCheckMoshtary() {
        return COLUMN_IsCheckMoshtary;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_ccMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_ccMarkazSazmanForoshSakhtarForosh;
    }
    public static String COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty() {
        return COLUMN_ExtraProp_ccDaryaftPardakhtCheckBargashty;
    }



    @SerializedName("ccDariaftPardakht")
    @Expose
    private int ccDariaftPardakht;
    @SerializedName("ccMarkazAnbar")
    @Expose
    private int ccMarkazAnbar;
    @SerializedName("ccMarkazForosh")
    @Expose
    private int ccMarkazForosh;
    @SerializedName("ccMarkazSazmanForoshSakhtarForosh")
    @Expose
    private int ccMarkazSazmanForoshSakhtarForosh;
    @SerializedName("CodeNoeVosol")
    @Expose
    private int CodeNoeVosol;
    @SerializedName("ccDarkhastFaktor")
    @Expose
    private long ccDarkhastFaktor;
    @SerializedName("NameNoeVosol")
    @Expose
    private String NameNoeVosol;
    @SerializedName("ccShomarehHesab")
    @Expose
    private int ccShomarehHesab;
    @SerializedName("SharhShomarehHesab")
    @Expose
    private String SharhShomarehHesab;
    @SerializedName("ZamaneSabt")
    @Expose
    private String ZamaneSabt;
    @SerializedName("NameSahebHesab")
    @Expose
    private String NameSahebHesab;
    @SerializedName("ccBankSanad")
    @Expose
    private int ccBankSanad;
    @SerializedName("NameBankSanad")
    @Expose
    private String NameBankSanad;
    @SerializedName("NameShobehSanad")
    @Expose
    private String NameShobehSanad;
    @SerializedName("CodeShobehSanad")
    @Expose
    private String CodeShobehSanad;
    @SerializedName("ShomarehHesabSanad")
    @Expose
    private String ShomarehHesabSanad;
    @SerializedName("ccNoeHesabSanad")
    @Expose
    private int ccNoeHesabSanad;
    @SerializedName("NameNoeHesabSanad")
    @Expose
    private String NameNoeHesabSanad;
    @SerializedName("ccShahrCheck")
    @Expose
    private String ccShahrCheck;
    @SerializedName("CodeNoeCheck")
    @Expose
    private int CodeNoeCheck;
    @SerializedName("NameNoeCheck")
    @Expose
    private String NameNoeCheck;
    @SerializedName("ShomarehSanad")
    @Expose
    private String ShomarehSanad;
    @SerializedName("TarikhSanadShamsi")
    @Expose
    private String TarikhSanadShamsi;
    @SerializedName("Mablagh")
    @Expose
    private double Mablagh;
    @SerializedName("MablaghMandeh")
    @Expose
    private double MablaghMandeh;
    @SerializedName("ccDariaftPardakhtLink")
    @Expose
    private long ccDariaftPardakhtLink;
    @SerializedName("ccUserSabtKonandeh")
    @Expose
    private int ccUserSabtKonandeh;
    @SerializedName("ccAfradErsalKonandeh")
    @Expose
    private int ccAfradErsalKonandeh;
    @SerializedName("ccKardex")
    @Expose
    private long ccKardex;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("IsPishDariaft")
    @Expose
    private int IsPishDariaft;
    @SerializedName("ccLinkTakhirTajil")
    @Expose
    private int ccLinkTakhirTajil;
    @SerializedName("TarikhSanad")
    @Expose
    private String TarikhSanad;
    @SerializedName("Tabdil_NaghdBeFish")
    @Expose
    private int Tabdil_NaghdBeFish;
    @SerializedName("NaghlAzGhabl")
    @Expose
    private int NaghlAzGhabl;
    @SerializedName("ccBrand")
    @Expose
    private int ccBrand;
    @SerializedName("IsCheckMoshtary")
    @Expose
    private int IsCheckMoshtary;
    @SerializedName("ExtraProp_ccDaryaftPardakhtCheckBargashty")
    @Expose
    private long ExtraProp_ccDaryaftPardakhtCheckBargashty;


    public long getExtraProp_ccDaryaftPardakhtCheckBargashty() {
        return ExtraProp_ccDaryaftPardakhtCheckBargashty;
    }

    public void setExtraProp_ccDaryaftPardakhtCheckBargashty(long extraProp_ccDaryaftPardakhtCheckBargashty) {
        ExtraProp_ccDaryaftPardakhtCheckBargashty = extraProp_ccDaryaftPardakhtCheckBargashty;
    }

    private int ExtraProp_IsDirkard;
    private int ExtraProp_IsSend;


    public void setCcDariaftPardakht(int ccDariaftPardakht){
        this.ccDariaftPardakht = ccDariaftPardakht;
    }
    public int getCcDariaftPardakht(){
        return this.ccDariaftPardakht;
    }
    public void setCcMarkazAnbar(int ccMarkazAnbar){
        this.ccMarkazAnbar = ccMarkazAnbar;
    }
    public int getCcMarkazAnbar(){
        return this.ccMarkazAnbar;
    }
    public void setCcMarkazForosh(int ccMarkazForosh){
        this.ccMarkazForosh = ccMarkazForosh;
    }
    public int getCcMarkazForosh(){
        return this.ccMarkazForosh;
    }
    public void setCcMarkazSazmanForoshSakhtarForosh(int ccMarkazSazmanForoshSakhtarForosh){
        this.ccMarkazSazmanForoshSakhtarForosh = ccMarkazSazmanForoshSakhtarForosh;
    }
    public int getCcMarkazSazmanForoshSakhtarForosh(){
        return this.ccMarkazSazmanForoshSakhtarForosh;
    }
    public void setCodeNoeVosol(int CodeNoeVosol){
        this.CodeNoeVosol = CodeNoeVosol;
    }
    public int getCodeNoeVosol(){
        return this.CodeNoeVosol;
    }
    public void setCcDarkhastFaktor(long ccDarkhastFaktor){
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }
    public long getCcDarkhastFaktor(){
        return this.ccDarkhastFaktor;
    }
    public void setNameNoeVosol(String NameNoeVosol){
        this.NameNoeVosol = NameNoeVosol;
    }
    public String getNameNoeVosol(){
        return this.NameNoeVosol;
    }
    public void setCcShomarehHesab(int ccShomarehHesab){
        this.ccShomarehHesab = ccShomarehHesab;
    }
    public int getCcShomarehHesab(){
        return this.ccShomarehHesab;
    }
    public void setSharhShomarehHesab(String SharhShomarehHesab){
        this.SharhShomarehHesab = SharhShomarehHesab;
    }
    public String getSharhShomarehHesab(){
        return this.SharhShomarehHesab;
    }
    public void setZamaneSabt(String ZamaneSabt){
        this.ZamaneSabt = ZamaneSabt;
    }
    public String getZamaneSabt(){
        return this.ZamaneSabt;
    }
    public void setNameSahebHesab(String NameSahebHesab){
        this.NameSahebHesab = NameSahebHesab;
    }
    public String getNameSahebHesab(){
        return this.NameSahebHesab;
    }
    public void setCcBankSanad(int ccBankSanad){
        this.ccBankSanad = ccBankSanad;
    }
    public int getCcBankSanad(){
        return this.ccBankSanad;
    }
    public void setNameBankSanad(String NameBankSanad){
        this.NameBankSanad = NameBankSanad;
    }
    public String getNameBankSanad(){
        return this.NameBankSanad;
    }
    public void setNameShobehSanad(String NameShobehSanad){
        this.NameShobehSanad = NameShobehSanad;
    }
    public String getNameShobehSanad(){
        return this.NameShobehSanad;
    }
    public void setCodeShobehSanad(String CodeShobehSanad){
        this.CodeShobehSanad = CodeShobehSanad;
    }
    public String getCodeShobehSanad(){
        return this.CodeShobehSanad;
    }
    public void setShomarehHesabSanad(String ShomarehHesabSanad){
        this.ShomarehHesabSanad = ShomarehHesabSanad;
    }
    public String getShomarehHesabSanad(){
        return this.ShomarehHesabSanad;
    }
    public void setCcNoeHesabSanad(int ccNoeHesabSanad){
        this.ccNoeHesabSanad = ccNoeHesabSanad;
    }
    public int getCcNoeHesabSanad(){
        return this.ccNoeHesabSanad;
    }
    public void setNameNoeHesabSanad(String NameNoeHesabSanad){
        this.NameNoeHesabSanad = NameNoeHesabSanad;
    }
    public String getNameNoeHesabSanad(){
        return this.NameNoeHesabSanad;
    }
    public void setCcShahrCheck(String ccShahrCheck){
        this.ccShahrCheck = ccShahrCheck;
    }
    public String getCcShahrCheck(){
        return this.ccShahrCheck;
    }
    public void setCodeNoeCheck(int CodeNoeCheck){
        this.CodeNoeCheck = CodeNoeCheck;
    }
    public int getCodeNoeCheck(){
        return this.CodeNoeCheck;
    }
    public void setNameNoeCheck(String NameNoeCheck){
        this.NameNoeCheck = NameNoeCheck;
    }
    public String getNameNoeCheck(){
        return this.NameNoeCheck;
    }
    public void setShomarehSanad(String ShomarehSanad){
        this.ShomarehSanad = ShomarehSanad;
    }
    public String getShomarehSanad(){
        return this.ShomarehSanad;
    }
    public void setTarikhSanadShamsi(String TarikhSanadShamsi){
        this.TarikhSanadShamsi = TarikhSanadShamsi;
    }
    public String getTarikhSanadShamsi(){
        return this.TarikhSanadShamsi;
    }
    public void setMablagh(double Mablagh){
        this.Mablagh = Mablagh;
    }
    public double getMablagh(){
        return this.Mablagh;
    }
    public void setMablaghMandeh(double MablaghMandeh){
        this.MablaghMandeh = MablaghMandeh;
    }
    public double getMablaghMandeh(){
        return this.MablaghMandeh;
    }
    public void setCcDariaftPardakhtLink(long ccDariaftPardakhtLink){
        this.ccDariaftPardakhtLink = ccDariaftPardakhtLink;
    }
    public long getCcDariaftPardakhtLink(){
        return this.ccDariaftPardakhtLink;
    }
    public void setCcUserSabtKonandeh(int ccUserSabtKonandeh){
        this.ccUserSabtKonandeh = ccUserSabtKonandeh;
    }
    public int getCcUserSabtKonandeh(){
        return this.ccUserSabtKonandeh;
    }
    public void setCcAfradErsalKonandeh(int ccAfradErsalKonandeh){
        this.ccAfradErsalKonandeh = ccAfradErsalKonandeh;
    }
    public int getCcAfradErsalKonandeh(){
        return this.ccAfradErsalKonandeh;
    }
    public void setCcKardex(long ccKardex){
        this.ccKardex = ccKardex;
    }
    public long getCcKardex(){
        return this.ccKardex;
    }
    public void setCodeVazeiat(int CodeVazeiat){
        this.CodeVazeiat = CodeVazeiat;
    }
    public int getCodeVazeiat(){
        return this.CodeVazeiat;
    }
    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }
    public int getCcMoshtary(){
        return this.ccMoshtary;
    }
    public void setIsPishDariaft(int IsPishDariaft){
        this.IsPishDariaft = IsPishDariaft;
    }
    public int getIsPishDariaft(){
        return this.IsPishDariaft;
    }
    public void setCcLinkTakhirTajil(int ccLinkTakhirTajil){
        this.ccLinkTakhirTajil = ccLinkTakhirTajil;
    }
    public int getCcLinkTakhirTajil(){
        return this.ccLinkTakhirTajil;
    }
    public void setTarikhSanad(String TarikhSanad){
        this.TarikhSanad = TarikhSanad;
    }
    public String getTarikhSanad(){
        return this.TarikhSanad;
    }
    public void setTabdil_NaghdBeFish(int Tabdil_NaghdBeFish){
        this.Tabdil_NaghdBeFish = Tabdil_NaghdBeFish;
    }
    public int getTabdil_NaghdBeFish(){
        return this.Tabdil_NaghdBeFish;
    }
    public void setNaghlAzGhabl(int NaghlAzGhabl){
        this.NaghlAzGhabl = NaghlAzGhabl;
    }
    public int getNaghlAzGhabl(){
        return this.NaghlAzGhabl;
    }
    public void setCcBrand(int ccBrand){
        this.ccBrand = ccBrand;
    }
    public int getCcBrand(){
        return this.ccBrand;
    }
    public void setIsCheckMoshtary(int IsCheckMoshtary){
        this.IsCheckMoshtary = IsCheckMoshtary;
    }
    public int getIsCheckMoshtary(){
        return this.IsCheckMoshtary;
    }

    public int getExtraProp_IsDirkard() {
        return ExtraProp_IsDirkard;
    }

    public void setExtraProp_IsDirkard(int extraProp_IsDirkard) {
        ExtraProp_IsDirkard = extraProp_IsDirkard;
    }

    public int getExtraProp_IsSend() {
        return ExtraProp_IsSend;
    }

    public void setExtraProp_IsSend(int extraProp_IsSend) {
        ExtraProp_IsSend = extraProp_IsSend;
    }


    public JSONObject toJsonObject(int ccMarkazForosh, int ccMarkazAnbar, int ccMarkazSazmanForoshSakhtarForosh, int codeNoeSanad, int codeNoeCheck, int codeNoeVosolVajhNaghd, String currentVersionNumber)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccDariaftPardakht" , ccDariaftPardakht);
            jsonObject.put("CodeNoeVorod" , Constants.CODE_NOE_VOROD_VOSOL_FAKTOR);//HCode change later
            jsonObject.put("CodeNoeDariaftPardakht" , 1);
            jsonObject.put("CodeNoeSanad" , codeNoeSanad);
            jsonObject.put("CodeNoeCheck" , codeNoeCheck);
            jsonObject.put("ccShomarehHesab" , ccShomarehHesab);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("ZamaneSabt" , ZamaneSabt);
            jsonObject.put("ccBankSanad" , ccBankSanad);
            jsonObject.put("NameShobehSanad" , NameShobehSanad);
            jsonObject.put("CodeShobehSanad" , CodeShobehSanad);
            jsonObject.put("ShomarehHesabSanad" , ShomarehHesabSanad);
            jsonObject.put("ccNoeHesabSanad" , ccNoeHesabSanad);
            jsonObject.put("ShomarehSanad" , ShomarehSanad);
            jsonObject.put("NameSahebHesab" , NameSahebHesab);
            jsonObject.put("TarikhSanad" , CodeNoeVosol != codeNoeVosolVajhNaghd ? TarikhSanad : "");
            jsonObject.put("Mablagh" , Mablagh);
            jsonObject.put("CounterCheck" , 0);
            jsonObject.put("ShomarehElamieh" , 0);
            jsonObject.put("TarikhElamieh" , "");
            jsonObject.put("ccDariaftPardakhtLink" , ccDariaftPardakhtLink);
            jsonObject.put("ccUserSabtKonandeh" , ccUserSabtKonandeh);
            jsonObject.put("ccAfradErsalKonandeh" , ccAfradErsalKonandeh);
            jsonObject.put("ccKardex" , ccKardex);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);
            jsonObject.put("Taeed" , 0);
            jsonObject.put("IsPishDariaft" , IsPishDariaft);
            jsonObject.put("Tabdil_NaghdBeFish" , Tabdil_NaghdBeFish);
            jsonObject.put("ccDarkhastFaktor_dp" , ccDarkhastFaktor);
            jsonObject.put("PPC_VersionNumber" , currentVersionNumber);
            jsonObject.put("ccMarkazAnbar" , ccMarkazAnbar);
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccMarkazSazmanForoshSakhtarForosh" , this.ccMarkazSazmanForoshSakhtarForosh==0 ? ccMarkazSazmanForoshSakhtarForosh : this.ccMarkazSazmanForoshSakhtarForosh);
            jsonObject.put("CodeNoeVosol" , CodeNoeVosol);
            jsonObject.put("ccDariaftPardakhtPPC" , ccDariaftPardakht);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    public JSONObject toJsonObjectCheckBargashty(int ccMarkazForosh, int ccMarkazAnbar, int ccMarkazSazmanForoshSakhtarForosh, int codeNoeSanad, int codeNoeCheck, int codeNoeVosolVajhNaghd, String currentVersionNumber  )
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccDariaftPardakht" , ccDariaftPardakht);
            jsonObject.put("CodeNoeVorod" , Constants.CODE_NOE_VOROD_VOSOL_BARGASHTY);//HCode change later
            jsonObject.put("CodeNoeDariaftPardakht" , 1);
            jsonObject.put("CodeNoeSanad" , codeNoeSanad);
            jsonObject.put("CodeNoeCheck" , codeNoeCheck);
            jsonObject.put("ccShomarehHesab" , ccShomarehHesab);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("ZamaneSabt" , ZamaneSabt);
            jsonObject.put("ccBankSanad" , ccBankSanad);
            jsonObject.put("NameShobehSanad" , NameShobehSanad);
            jsonObject.put("CodeShobehSanad" , CodeShobehSanad);
            jsonObject.put("ShomarehHesabSanad" , ShomarehHesabSanad);
            jsonObject.put("ccNoeHesabSanad" , ccNoeHesabSanad);
            jsonObject.put("ShomarehSanad" , ShomarehSanad);
            jsonObject.put("NameSahebHesab" , NameSahebHesab);
            jsonObject.put("TarikhSanad" , CodeNoeVosol != codeNoeVosolVajhNaghd ? TarikhSanad : "");
            jsonObject.put("Mablagh" , Mablagh);
            jsonObject.put("CounterCheck" , 0);
            jsonObject.put("ShomarehElamieh" , 0);
            jsonObject.put("TarikhElamieh" , "");
            jsonObject.put("ccDariaftPardakhtLink" , ccDariaftPardakhtLink);
            jsonObject.put("ccUserSabtKonandeh" , ccUserSabtKonandeh);
            jsonObject.put("ccAfradErsalKonandeh" , ccAfradErsalKonandeh);
            jsonObject.put("ccKardex" , ccKardex);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);
            jsonObject.put("Taeed" , 0);
            jsonObject.put("IsPishDariaft" , IsPishDariaft);
            jsonObject.put("Tabdil_NaghdBeFish" , Tabdil_NaghdBeFish);
            jsonObject.put("ccDarkhastFaktor_dp" , ccDarkhastFaktor);
            jsonObject.put("PPC_VersionNumber" , currentVersionNumber);
            jsonObject.put("ccMarkazAnbar" , ccMarkazAnbar);
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccMarkazSazmanForoshSakhtarForosh" , this.ccMarkazSazmanForoshSakhtarForosh==0 ? ccMarkazSazmanForoshSakhtarForosh : this.ccMarkazSazmanForoshSakhtarForosh);
            jsonObject.put("CodeNoeVosol" , CodeNoeVosol);
            jsonObject.put("ccDariaftPardakhtPPC" , ccDariaftPardakht);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject toJsonObjectCheckPishDariaft(int ccMarkazForosh, int ccMarkazAnbar, int ccMarkazSazmanForoshSakhtarForosh, int codeNoeSanad, int codeNoeCheck, int codeNoeVosolVajhNaghd, String currentVersionNumber , int ccSazmanForosh)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccDariaftPardakht" , ccDariaftPardakht);
            jsonObject.put("CodeNoeVorod" , Constants.CODE_NOE_VOROD_PISHDARYAFT);//HCode change later
            jsonObject.put("CodeNoeDariaftPardakht" , 1);
            jsonObject.put("CodeNoeSanad" , codeNoeSanad);
            jsonObject.put("CodeNoeCheck" , codeNoeCheck);
            jsonObject.put("ccShomarehHesab" , ccShomarehHesab);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("ZamaneSabt" , ZamaneSabt);
            jsonObject.put("ccBankSanad" , ccBankSanad);
            jsonObject.put("NameShobehSanad" , NameShobehSanad);
            jsonObject.put("CodeShobehSanad" , CodeShobehSanad);
//            jsonObject.put("ShomarehHesabSanad" , ShomarehHesabSanad);
            jsonObject.put("ccNoeHesabSanad" , ccNoeHesabSanad);
            jsonObject.put("ShomarehSanad" , ShomarehSanad);
            jsonObject.put("NameSahebHesab" , NameSahebHesab);
            jsonObject.put("TarikhSanad" , CodeNoeVosol != codeNoeVosolVajhNaghd ? TarikhSanad : "");
            jsonObject.put("Mablagh" , Mablagh);
            jsonObject.put("CounterCheck" , 0);
            jsonObject.put("ShomarehElamieh" , 0);
            jsonObject.put("TarikhElamieh" , "");
            jsonObject.put("ccDariaftPardakhtLink" , ccDariaftPardakhtLink);
            jsonObject.put("ccUserSabtKonandeh" , ccUserSabtKonandeh);
            jsonObject.put("ccAfradErsalKonandeh" , ccAfradErsalKonandeh);
            jsonObject.put("ccKardex" , ccKardex);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);
            jsonObject.put("Taeed" , 0);
            jsonObject.put("IsPishDariaft" , IsPishDariaft);
            jsonObject.put("Tabdil_NaghdBeFish" , Tabdil_NaghdBeFish);
            jsonObject.put("ccDarkhastFaktor_dp" , ccDarkhastFaktor);
            jsonObject.put("PPC_VersionNumber" , currentVersionNumber);
            jsonObject.put("ccMarkazAnbar" , ccMarkazAnbar);
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccMarkazSazmanForoshSakhtarForosh" , this.ccMarkazSazmanForoshSakhtarForosh==0 ? ccMarkazSazmanForoshSakhtarForosh : this.ccMarkazSazmanForoshSakhtarForosh);
            jsonObject.put("CodeNoeVosol" , CodeNoeVosol);
            jsonObject.put("ccDariaftPardakhtPPC" , ccDariaftPardakht);
            jsonObject.put("ShomarehHesabSanad" , ShomarehHesabSanad);
            jsonObject.put("ccSazmanForosh" , ccSazmanForosh);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    public JSONObject toJsonObjectWithDariaftPardakhtDarkhastFaktor(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel , int noeMasouliat , DarkhastFaktorModel darkhastFaktorModel , int codeNoeSanad , int codeNoeCheck, int codeNoeVosolVajhNaghd, String currentVersionNumber, JSONArray jsonArraydpdf)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            int ccMarkazForosh = 0;
            int ccMarkazAnbar = 0;
            int ccMarkazSazmanForoshSakhtarForosh = 0;
            if(noeMasouliat != 4)
            {
                ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
                ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbar();
                ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
            }
            else
            {
                ccMarkazForosh = darkhastFaktorModel.getCcMarkazForosh();
                ccMarkazAnbar = darkhastFaktorModel.getCcMarkazAnbar();
                ccMarkazSazmanForoshSakhtarForosh = darkhastFaktorModel.getCcMarkazSazmanForoshSakhtarForosh();
            }

            Log.d("dariaftpadrakht" , "CodeNoeVosol : " + CodeNoeVosol);

            jsonObject.put("ccDariaftPardakht" , ccDariaftPardakht);
            jsonObject.put("CodeNoeVorod" , 2);
            jsonObject.put("CodeNoeDariaftPardakht" , 1);
            jsonObject.put("CodeNoeSanad" , codeNoeSanad);
            jsonObject.put("CodeNoeCheck" , codeNoeCheck);
            jsonObject.put("ccShomarehHesab" , ccShomarehHesab);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("ZamaneSabt" , ZamaneSabt);
            jsonObject.put("ccBankSanad" , ccBankSanad);
            jsonObject.put("NameShobehSanad" , NameShobehSanad);
            jsonObject.put("CodeShobehSanad" , CodeShobehSanad);
            jsonObject.put("ShomarehHesabSanad" , ShomarehHesabSanad);
            jsonObject.put("ccNoeHesabSanad" , ccNoeHesabSanad);
            jsonObject.put("ShomarehSanad" , ShomarehSanad);
            jsonObject.put("NameSahebHesab" , NameSahebHesab);
            jsonObject.put("TarikhSanad" , CodeNoeVosol != codeNoeVosolVajhNaghd ? TarikhSanad : "");
            jsonObject.put("Mablagh" , Mablagh);
            jsonObject.put("CounterCheck" , 0);
            jsonObject.put("ShomarehElamieh" , 0);
            jsonObject.put("TarikhElamieh" , "");
            jsonObject.put("ccDariaftPardakhtLink" , ccDariaftPardakhtLink);
            jsonObject.put("ccUserSabtKonandeh" , ccUserSabtKonandeh);
            jsonObject.put("ccAfradErsalKonandeh" , ccAfradErsalKonandeh);
            jsonObject.put("ccKardex" , ccKardex);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);
            jsonObject.put("Taeed" , 0);
            jsonObject.put("IsPishDariaft" , IsPishDariaft);
            jsonObject.put("Tabdil_NaghdBeFish" , Tabdil_NaghdBeFish);
            jsonObject.put("ccDarkhastFaktor_dp" , ccDarkhastFaktor);
            jsonObject.put("PPC_VersionNumber" , currentVersionNumber);
            jsonObject.put("ccMarkazAnbar" , ccMarkazAnbar);
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccMarkazSazmanForoshSakhtarForosh" , this.ccMarkazSazmanForoshSakhtarForosh==0 ? ccMarkazSazmanForoshSakhtarForosh : this.ccMarkazSazmanForoshSakhtarForosh);
            jsonObject.put("CodeNoeVosol" , CodeNoeVosol);
            jsonObject.put("ccDariaftPardakhtPPC" , ccDariaftPardakht);
            jsonObject.put("dariaftPardakhtDarkhastFaktorPPCs" , jsonArraydpdf);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString() {
        return "DariaftPardakhtPPCModel{" +
                "ccDariaftPardakht=" + ccDariaftPardakht +
                ", ccMarkazAnbar=" + ccMarkazAnbar +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", ccMarkazSazmanForoshSakhtarForosh=" + ccMarkazSazmanForoshSakhtarForosh +
                ", CodeNoeVosol=" + CodeNoeVosol +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", NameNoeVosol='" + NameNoeVosol + '\'' +
                ", ccShomarehHesab=" + ccShomarehHesab +
                ", SharhShomarehHesab='" + SharhShomarehHesab + '\'' +
                ", ZamaneSabt='" + ZamaneSabt + '\'' +
                ", NameSahebHesab='" + NameSahebHesab + '\'' +
                ", ccBankSanad=" + ccBankSanad +
                ", NameBankSanad='" + NameBankSanad + '\'' +
                ", NameShobehSanad='" + NameShobehSanad + '\'' +
                ", CodeShobehSanad='" + CodeShobehSanad + '\'' +
                ", ShomarehHesabSanad='" + ShomarehHesabSanad + '\'' +
                ", ccNoeHesabSanad=" + ccNoeHesabSanad +
                ", NameNoeHesabSanad='" + NameNoeHesabSanad + '\'' +
                ", ccShahrCheck='" + ccShahrCheck + '\'' +
                ", CodeNoeCheck=" + CodeNoeCheck +
                ", NameNoeCheck='" + NameNoeCheck + '\'' +
                ", ShomarehSanad='" + ShomarehSanad + '\'' +
                ", TarikhSanadShamsi='" + TarikhSanadShamsi + '\'' +
                ", Mablagh=" + Mablagh +
                ", MablaghMandeh=" + MablaghMandeh +
                ", ccDariaftPardakhtLink=" + ccDariaftPardakhtLink +
                ", ccUserSabtKonandeh=" + ccUserSabtKonandeh +
                ", ccAfradErsalKonandeh=" + ccAfradErsalKonandeh +
                ", ccKardex=" + ccKardex +
                ", CodeVazeiat=" + CodeVazeiat +
                ", ccMoshtary=" + ccMoshtary +
                ", IsPishDariaft=" + IsPishDariaft +
                ", ccLinkTakhirTajil=" + ccLinkTakhirTajil +
                ", TarikhSanad='" + TarikhSanad + '\'' +
                ", Tabdil_NaghdBeFish=" + Tabdil_NaghdBeFish +
                ", NaghlAzGhabl=" + NaghlAzGhabl +
                ", ccBrand=" + ccBrand +
                ", IsCheckMoshtary=" + IsCheckMoshtary +
                '}';
    }
}
