package com.saphamrah.Model;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saphamrah.UIModel.CustomerDarkhastFaktorModel;
import com.saphamrah.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DarkhastFaktorModel
{




    private static final String TABLE_NAME = "DarkhastFaktor";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_TarikhFaktor = "TarikhFaktor";
    private static final String COLUMN_ShomarehDarkhast = "ShomarehDarkhast";
    private static final String COLUMN_ShomarehFaktor = "ShomarehFaktor";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_TarikhErsal = "TarikhErsal";
    private static final String COLUMN_TarikhPishbinyTahvil = "TarikhPishbinyTahvil";
    private static final String COLUMN_ModatRoozRaasGiri = "ModatRoozRaasGiri";
    private static final String COLUMN_ModateVosol = "ModateVosol";
    private static final String COLUMN_CodeNoeVosolAzMoshtary = "CodeNoeVosolAzMoshtary";
    //private static final String COLUMN_MablaghFaktorPishAzTakhfif = "MablaghFaktorPishAzTakhfif";
    private static final String COLUMN_MablaghKhalesFaktor = "MablaghKhalesFaktor";
    private static final String COLUMN_BeMasoliat = "BeMasoliat";
    private static final String COLUMN_CodeNoeHaml = "CodeNoeHaml";
    private static final String COLUMN_SumMaliat = "SumMaliat";
    private static final String COLUMN_SumAvarez = "SumAvarez";
    private static final String COLUMN_SaatVorodBeMaghazeh = "SaatVorodBeMaghazeh";
    private static final String COLUMN_SaatKhorojAzMaghazeh = "SaatKhorojAzMaghazeh";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";
    private static final String COLUMN_Latitude = "Latitude";
    private static final String COLUMN_Longitude = "Longitude";
    private static final String COLUMN_PPC_VersionNumber = "PPC_VersionNumber";
    //private static final String COLUMN_PPC_Version_Date = "PPC_Version_Date";
    private static final String COLUMN_TakhfifNaghdy = "TakhfifNaghdy";
    private static final String COLUMN_ccAddressMoshtary = "ccAddressMoshtary";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";
    private static final String COLUMN_ExtraProp_MablaghTakhfifSenfiHajmi = "ExtraProp_MablaghTakhfifSenfiHajmi";
    private static final String COLUMN_MablaghKolFaktor = "MablaghKolFaktor";
    private static final String COLUMN_ExtraProp_InsertInPPC = "ExtraProp_InsertInPPC";
    private static final String COLUMN_ExtraProp_MablaghArzeshAfzoodeh = "ExtraProp_MablaghArzeshAfzoodeh";
    private static final String COLUMN_ExtraProp_SumTakhfifat = "ExtraProp_SumTakhfifat";
    private static final String COLUMN_ExtraProp_MablaghNahaeeFaktor = "ExtraProp_MablaghNahaeeFaktor";
    private static final String COLUMN_MarjoeeKamel = "MarjoeeKamel";
    private static final String COLUMN_NameNoeVosolAzMoshtary = "NameNoeVosolAzMoshtary";
    private static final String COLUMN_NameNoeHaml = "NameNoeHaml";
    private static final String COLUMN_NameVazeiat = "NameVazeiat";
    private static final String COLUMN_NameNoeTahvil = "NameNoeTahvil";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_ccSazmanForosh = "ccSazmanForosh";
    private static final String COLUMN_ccMarkazSazmanForosh = "ccMarkazSazmanForosh";
    private static final String COLUMN_ccMarkazSazmanForoshSakhtarForosh = "ccMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_ccMarkazAnbar = "ccMarkazAnbar";
    private static final String COLUMN_ModateTakhfif = "ModateTakhfif";
    private static final String COLUMN_FaktorRooz = "FaktorRooz";
    private static final String COLUMN_MablaghVosol = "MablaghVosol";
    private static final String COLUMN_MablaghMandeh = "MablaghMandeh";
    private static final String COLUMN_ccTafkikJoze = "ccTafkikJoze";
    private static final String COLUMN_Darajeh = "Darajeh";
    private static final String COLUMN_ExtraProp_IsMarjoeeKamel = "ExtraProp_IsMarjoeeKamel";
    private static final String COLUMN_ExtraProp_MablaghMandeh_NaghlAzGhabl = "ExtraProp_MablaghMandeh_NaghlAzGhabl";
    private static final String COLUMN_ExtraProp_Resid = "ExtraProp_Resid";
    private static final String COLUMN_ExtraProp_MablaghDariaftPardakht = "ExtraProp_MablaghDariaftPardakht";
    private static final String COLUMN_CodeMarkaz = "CodeMarkaz";
    private static final String COLUMN_ForForosh = "ForForosh";
    private static final String COLUMN_ForTasviehVosol = "ForTasviehVosol";
    private static final String COLUMN_ExtraProp_DarkhastFaktorImage = "ExtraProp_DarkhastFaktorImage";
    private static final String COLUMN_ExtraProp_IsSend = "ExtraProp_IsSend";
    private static final String COLUMN_ExtraProp_ShomarehDarkhast = "ExtraProp_ShomarehDarkhast";
    private static final String COLUMN_UniqID_Tablet = "UniqID_Tablet";
    private static final String COLUMN_CodeMoshtary = "CodeMoshtary";
    private static final String COLUMN_Noe_Faktor_Havaleh = "Noe_Faktor_Havaleh";
    private static final String COLUMN_ExtraProp_ShowFaktorMamorPakhsh = "ExtraProp_ShowFaktorMamorPakhsh";
    private static final String COLUMN_ccUser = "ccUser";
    private static final String COLUMN_ccDarkhastFaktorNoeForosh = "ccDarkhastFaktorNoeForosh";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";
    private static final String COLUMN_IsTajil = "IsTajil";
    private static final String COLUMN_IsTakhir = "IsTakhir";
    private static final String COLUMN_MoshtaryGharardadccSazmanForosh = "MoshtaryGharardadccSazmanForosh";
    private static final String COLUMN_ccMoshtaryGharardad = "ccMoshtaryGharardad";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_TarikhFaktor() {
        return COLUMN_TarikhFaktor;
    }
    public static String COLUMN_ShomarehDarkhast() {
        return COLUMN_ShomarehDarkhast;
    }
    public static String COLUMN_ShomarehFaktor() {
        return COLUMN_ShomarehFaktor;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_TarikhErsal() {
        return COLUMN_TarikhErsal;
    }
    public static String COLUMN_TarikhPishbinyTahvil() {
        return COLUMN_TarikhPishbinyTahvil;
    }
    public static String COLUMN_ModatRoozRaasGiri() {
        return COLUMN_ModatRoozRaasGiri;
    }
    public static String COLUMN_ModateVosol() {
        return COLUMN_ModateVosol;
    }
    public static String COLUMN_CodeNoeVosolAzMoshtary() {
        return COLUMN_CodeNoeVosolAzMoshtary;
    }
    /*public static String COLUMN_MablaghFaktorPishAzTakhfif() {
        return COLUMN_MablaghFaktorPishAzTakhfif;
    }*/
    public static String COLUMN_MablaghKhalesFaktor() {
        return COLUMN_MablaghKhalesFaktor;
    }
    public static String COLUMN_BeMasoliat() {
        return COLUMN_BeMasoliat;
    }
    public static String COLUMN_CodeNoeHaml() {
        return COLUMN_CodeNoeHaml;
    }
    public static String COLUMN_SumMaliat() {
        return COLUMN_SumMaliat;
    }
    public static String COLUMN_SumAvarez() {
        return COLUMN_SumAvarez;
    }
    public static String COLUMN_SaatVorodBeMaghazeh() {
        return COLUMN_SaatVorodBeMaghazeh;
    }
    public static String COLUMN_SaatKhorojAzMaghazeh() {
        return COLUMN_SaatKhorojAzMaghazeh;
    }
    public static String COLUMN_CodeVazeiat() {
        return COLUMN_CodeVazeiat;
    }
    public static String COLUMN_Latitude() {
        return COLUMN_Latitude;
    }
    public static String COLUMN_Longitude() {
        return COLUMN_Longitude;
    }
    public static String COLUMN_PPC_VersionNumber() {
        return COLUMN_PPC_VersionNumber;
    }
    /*public static String COLUMN_PPC_Version_Date() {
        return COLUMN_PPC_Version_Date;
    }*/
    public static String COLUMN_TakhfifNaghdy() {
        return COLUMN_TakhfifNaghdy;
    }
    public static String COLUMN_ccAddressMoshtary() {
        return COLUMN_ccAddressMoshtary;
    }
    public static String COLUMN_ExtraProp_IsOld() {
        return COLUMN_ExtraProp_IsOld;
    }
    public static String COLUMN_ExtraProp_MablaghTakhfifSenfiHajmi() {
        return COLUMN_ExtraProp_MablaghTakhfifSenfiHajmi;
    }
    public static String COLUMN_MablaghKolFaktor() {
        return COLUMN_MablaghKolFaktor;
    }
    public static String COLUMN_ExtraProp_InsertInPPC() {
        return COLUMN_ExtraProp_InsertInPPC;
    }
    public static String COLUMN_ExtraProp_MablaghArzeshAfzoodeh() {
        return COLUMN_ExtraProp_MablaghArzeshAfzoodeh;
    }
    public static String COLUMN_ExtraProp_SumTakhfifat() {
        return COLUMN_ExtraProp_SumTakhfifat;
    }
    public static String COLUMN_ExtraProp_MablaghNahaeeFaktor() {
        return COLUMN_ExtraProp_MablaghNahaeeFaktor;
    }
    public static String COLUMN_MarjoeeKamel() {
        return COLUMN_MarjoeeKamel;
    }
    public static String COLUMN_NameNoeVosolAzMoshtary() {
        return COLUMN_NameNoeVosolAzMoshtary;
    }
    public static String COLUMN_NameNoeHaml() {
        return COLUMN_NameNoeHaml;
    }
    public static String COLUMN_NameVazeiat() {
        return COLUMN_NameVazeiat;
    }
    public static String COLUMN_NameNoeTahvil() {
        return COLUMN_NameNoeTahvil;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_ccSazmanForosh() {
        return COLUMN_ccSazmanForosh;
    }
    public static String COLUMN_ccMarkazSazmanForosh() {
        return COLUMN_ccMarkazSazmanForosh;
    }
    public static String COLUMN_ccMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_ccMarkazSazmanForoshSakhtarForosh;
    }
    public static String COLUMN_ccMarkazAnbar() {
        return COLUMN_ccMarkazAnbar;
    }
    public static String COLUMN_ModateTakhfif() {
        return COLUMN_ModateTakhfif;
    }
    public static String COLUMN_FaktorRooz() {
        return COLUMN_FaktorRooz;
    }
    public static String COLUMN_MablaghVosol() {
        return COLUMN_MablaghVosol;
    }
    public static String COLUMN_MablaghMandeh() {
        return COLUMN_MablaghMandeh;
    }
    public static String COLUMN_ccTafkikJoze() {
        return COLUMN_ccTafkikJoze;
    }
    public static String COLUMN_Darajeh() {
        return COLUMN_Darajeh;
    }
    public static String COLUMN_ExtraProp_IsMarjoeeKamel() {
        return COLUMN_ExtraProp_IsMarjoeeKamel;
    }
    public static String COLUMN_ExtraProp_MablaghMandeh_NaghlAzGhabl() {
        return COLUMN_ExtraProp_MablaghMandeh_NaghlAzGhabl;
    }
    public static String COLUMN_ExtraProp_Resid() {
        return COLUMN_ExtraProp_Resid;
    }
    public static String COLUMN_ExtraProp_MablaghDariaftPardakht() {
        return COLUMN_ExtraProp_MablaghDariaftPardakht;
    }
    public static String COLUMN_CodeMarkaz() {
        return COLUMN_CodeMarkaz;
    }
    public static String COLUMN_ForForosh() {
        return COLUMN_ForForosh;
    }
    public static String COLUMN_ForTasviehVosol() {
        return COLUMN_ForTasviehVosol;
    }
    public static String COLUMN_ExtraProp_DarkhastFaktorImage() {
        return COLUMN_ExtraProp_DarkhastFaktorImage;
    }
    public static String COLUMN_ExtraProp_IsSend() {
        return COLUMN_ExtraProp_IsSend;
    }
    public static String COLUMN_ExtraProp_ShomarehDarkhast() {
        return COLUMN_ExtraProp_ShomarehDarkhast;
    }
    public static String COLUMN_UniqID_Tablet() {
        return COLUMN_UniqID_Tablet;
    }
    public static String COLUMN_CodeMoshtary() {
        return COLUMN_CodeMoshtary;
    }
    public static String COLUMN_Noe_Faktor_Havaleh() {
        return COLUMN_Noe_Faktor_Havaleh;
    }
    public static String COLUMN_ExtraProp_ShowFaktorMamorPakhsh() {
        return COLUMN_ExtraProp_ShowFaktorMamorPakhsh;
    }
    public static String COLUMN_ccUser() {
        return COLUMN_ccUser;
    }
    public static String COLUMN_ccDarkhastFaktorNoeForosh() {
        return COLUMN_ccDarkhastFaktorNoeForosh;
    }
    public static String COLUMN_ccNoeMoshtary() {
        return COLUMN_ccNoeMoshtary;
    }
    public static String COLUMN_IsTajil() {
        return COLUMN_IsTajil;
    }

    public static String COLUMN_IsTakhir() {
        return COLUMN_IsTakhir;
    }
    public static String COLUMN_MoshtaryGharardadccSazmanForosh() {
        return COLUMN_MoshtaryGharardadccSazmanForosh;
    }public static String COLUMN_ccMoshtaryGharardad() {
    return COLUMN_ccMoshtaryGharardad;
}


    @SerializedName("ccDarkhastFaktor")
    @Expose
    private long ccDarkhastFaktor;
    @SerializedName("ccDarkhastFaktorPPC")
    @Expose
    private int ccDarkhastFaktorPPC;
    @SerializedName("CodeNoeVorod")
    @Expose
    private int CodeNoeVorod;
    @SerializedName("ccMarkazForosh")
    @Expose
    private int ccMarkazForosh;
    @SerializedName("ccMarkazSazmanForosh")
    @Expose
    private int ccMarkazSazmanForosh;
    @SerializedName("ccMarkazSazmanForoshSakhtarForosh")
    @Expose
    private int ccMarkazSazmanForoshSakhtarForosh;
    @SerializedName("ccMarkazAnbar")
    @Expose
    private int ccMarkazAnbar;
    @SerializedName("ccForoshandeh")
    @Expose
    private int ccForoshandeh;
    @SerializedName("NoeForoshandeh")
    @Expose
    private int NoeForoshandeh;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccShahrMoshtary")
    @Expose
    private String ccShahrMoshtary;
    @SerializedName("ccAddressMoshtary")
    @Expose
    private int ccAddressMoshtary;
    @SerializedName("ShomarehDarkhast")
    @Expose
    private int ShomarehDarkhast;
    @SerializedName("TarikhDarkhast")
    @Expose
    private String TarikhDarkhast;
    @SerializedName("ShomarehFaktor")
    @Expose
    private int ShomarehFaktor;
    @SerializedName("TarikhFaktor")
    @Expose
    private String TarikhFaktor;
    @SerializedName("TarikhPishbinyTahvil")
    @Expose
    private String TarikhPishbinyTahvil;
    @SerializedName("TarikhErsal")
    @Expose
    private String TarikhErsal;
    @SerializedName("CodeNoeVosolAzMoshtary")
    @Expose
    private int CodeNoeVosolAzMoshtary;
    @SerializedName("ModateVosol")
    @Expose
    private int ModateVosol;
    @SerializedName("ModatRoozRaasGiri")
    @Expose
    private int ModatRoozRaasGiri;
    @SerializedName("CodeNoeHaml")
    @Expose
    private int CodeNoeHaml;
    @SerializedName("ccNoeMashin")
    @Expose
    private String ccNoeMashin;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;
    @SerializedName("MablaghKolDarkhast")
    @Expose
    private int MablaghKolDarkhast;
    @SerializedName("MablaghTakhfifDarkhastTitr")
    @Expose
    private int MablaghTakhfifDarkhastTitr;
    @SerializedName("MablaghTakhfifDarkhastSatr")
    @Expose
    private float MablaghTakhfifDarkhastSatr;
    @SerializedName("MablaghKolFaktor")
    @Expose
    private double MablaghKolFaktor;
    @SerializedName("MablaghTakhfifFaktorTitr")
    @Expose
    private int MablaghTakhfifFaktorTitr;
    @SerializedName("MablaghTakhfifFaktorSatr")
    @Expose
    private float MablaghTakhfifFaktorSatr;
    @SerializedName("MablaghEzafat")
    @Expose
    private int MablaghEzafat;
    @SerializedName("SaatVorodBeMaghazeh")
    @Expose
    private String SaatVorodBeMaghazeh;
    @SerializedName("SaatKhorojAzMaghazeh")
    @Expose
    private String SaatKhorojAzMaghazeh;
    @SerializedName("ccUser")
    @Expose
    private int ccUser;
    @SerializedName("ccNoeMoshtary")
    @Expose
    private int ccNoeMoshtary;
    @SerializedName("ccNoeSenf")
    @Expose
    private int ccNoeSenf;
    @SerializedName("ShomarehFaktorIndex")
    @Expose
    private int ShomarehFaktorIndex;
    @SerializedName("ccDorehMaly")
    @Expose
    private int ccDorehMaly;
    @SerializedName("BeMasoliat")
    @Expose
    private int BeMasoliat;
    @SerializedName("SumMaliat")
    @Expose
    private float SumMaliat;
    @SerializedName("SumAvarez")
    @Expose
    private float SumAvarez;
    @SerializedName("MablaghKhalesFaktor")
    @Expose
    private double MablaghKhalesFaktor;
    @SerializedName("MablaghKhalesDarkhast")
    @Expose
    private double MablaghKhalesDarkhast;
    @SerializedName("MablaghMandehMoshtary")
    @Expose
    private float MablaghMandehMoshtary;
    @SerializedName("ShomarehDarkhastindex")
    @Expose
    private String ShomarehDarkhastindex;
    @SerializedName("SumTedadFaktor")
    @Expose
    private int SumTedadFaktor;
    @SerializedName("NoeTahvil")
    @Expose
    private int NoeTahvil;
    @SerializedName("TaeedFaktorPPC")
    @Expose
    private int TaeedFaktorPPC;
    @SerializedName("Latitude")
    @Expose
    private float Latitude;
    @SerializedName("Longitude")
    @Expose
    private float Longitude;
    @SerializedName("TakhfifNaghdy")
    @Expose
    private float TakhfifNaghdy;
    @SerializedName("PPC_VersionNumber")
    @Expose
    private String PPC_VersionNumber;
    @SerializedName("ModateTakhfif")
    @Expose
    private int ModateTakhfif;
    @SerializedName("MarjoeeKamel")
    @Expose
    private int MarjoeeKamel;
    @SerializedName("NameNoeVosolAzMoshtary")
    @Expose
    private String NameNoeVosolAzMoshtary;
    @SerializedName("NameNoeHaml")
    @Expose
    private String NameNoeHaml;
    @SerializedName("NameVazeiat")
    @Expose
    private String NameVazeiat;
    @SerializedName("NameNoeTahvil")
    @Expose
    private String NameNoeTahvil;
    @SerializedName("FaktorRooz")
    @Expose
    private long FaktorRooz;
    @SerializedName("MablaghVosol")
    @Expose
    private long MablaghVosol;
    @SerializedName("MablaghMandeh")
    @Expose
    private long MablaghMandeh;
    @SerializedName("ccTafkikJoze")
    @Expose
    private long ccTafkikJoze;
    @SerializedName("Darajeh")
    @Expose
    private int Darajeh;
    @SerializedName("CodeMarkaz")
    @Expose
    private String CodeMarkaz;
    @SerializedName("ForTasviehVosol")
    @Expose
    private int ForTasviehVosol;
    @SerializedName("ForForosh")
    @Expose
    private int ForForosh;
    @SerializedName("darkhastFaktorTakhfifs")
    @Expose
    private String darkhastFaktorTakhfifs;
    @SerializedName("darkhastFaktorSatrs")
    @Expose
    private String darkhastFaktorSatrs;
    @SerializedName("adamDarkhasts")
    @Expose
    private String adamDarkhasts;
    @SerializedName("mojoodiGiris")
    @Expose
    private String mojoodiGiris;
    @SerializedName("UniqID_Tablet")
    @Expose
    private String UniqID_Tablet;
    @SerializedName("ccSazmanForosh")
    @Expose
    private int ccSazmanForosh;
    @SerializedName("Noe_Faktor_Havaleh")
    @Expose
    private int noeFaktorHavaleh;
    @SerializedName("ccDarkhastFaktorNoeForosh")
    @Expose
    private int ccDarkhastFaktorNoeForosh;
    @SerializedName("IsTajil")
    @Expose
    private int IsTajil;
    @SerializedName("IsTakhir")
    @Expose
    private int IsTakhir;
    @SerializedName("MoshtaryGharardadccSazmanForosh")
    @Expose
    private int MoshtaryGharardadccSazmanForosh;
    @SerializedName("ccMoshtaryGharardad")
    @Expose
    private int ccMoshtaryGharardad;



    //private float MablaghFaktorPishAzTakhfif;
    //private String PPC_Version_Date;
    private int ExtraProp_IsOld;
    private float ExtraProp_MablaghTakhfifSenfiHajmi;
    private int ExtraProp_InsertInPPC;
    private float ExtraProp_MablaghArzeshAfzoodeh;
    private float ExtraProp_SumTakhfifat;
    private double ExtraProp_MablaghNahaeeFaktor;
    private int ExtraProp_IsMarjoeeKamel;
    private long ExtraProp_MablaghMandeh_NaghlAzGhabl;
    private int ExtraProp_Resid;
    private long ExtraProp_MablaghDariaftPardakht;
    private String ExtraProp_DarkhastFaktorImage;
    private int ExtraProp_IsSend;
    private String ExtraProp_ShomarehDarkhast;
    private String CodeMoshtary;
    private int showFaktorMamorPakhsh;



    public void setCcDarkhastFaktor(long ccDarkhastFaktor){
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }
    public long getCcDarkhastFaktor(){
        return this.ccDarkhastFaktor;
    }
    public void setCcDarkhastFaktorPPC(int ccDarkhastFaktorPPC){
        this.ccDarkhastFaktorPPC = ccDarkhastFaktorPPC;
    }
    public int getCcDarkhastFaktorPPC(){
        return this.ccDarkhastFaktorPPC;
    }
    public void setCodeNoeVorod(int CodeNoeVorod){
        this.CodeNoeVorod = CodeNoeVorod;
    }
    public int getCodeNoeVorod(){
        return this.CodeNoeVorod;
    }
    public void setCcMarkazForosh(int ccMarkazForosh){
        this.ccMarkazForosh = ccMarkazForosh;
    }
    public int getCcMarkazForosh(){
        return this.ccMarkazForosh;
    }
    public void setCcMarkazSazmanForosh(int ccMarkazSazmanForosh){
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }
    public int getCcMarkazSazmanForosh(){
        return this.ccMarkazSazmanForosh;
    }
    public int getCcSazmanForosh() {
        return ccSazmanForosh;
    }
    public void setCcSazmanForosh(int ccSazmanForosh) {
        this.ccSazmanForosh = ccSazmanForosh;
    }
    public void setCcMarkazSazmanForoshSakhtarForosh(int ccMarkazSazmanForoshSakhtarForosh){
        this.ccMarkazSazmanForoshSakhtarForosh = ccMarkazSazmanForoshSakhtarForosh;
    }
    public int getCcMarkazSazmanForoshSakhtarForosh(){
        return this.ccMarkazSazmanForoshSakhtarForosh;
    }
    public void setCcMarkazAnbar(int ccMarkazAnbar){
        this.ccMarkazAnbar = ccMarkazAnbar;
    }
    public int getCcMarkazAnbar(){
        return this.ccMarkazAnbar;
    }
    public void setCcForoshandeh(int ccForoshandeh){
        this.ccForoshandeh = ccForoshandeh;
    }
    public int getCcForoshandeh(){
        return this.ccForoshandeh;
    }
    public void setNoeForoshandeh(int NoeForoshandeh){
        this.NoeForoshandeh = NoeForoshandeh;
    }
    public int getNoeForoshandeh(){
        return this.NoeForoshandeh;
    }
    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }
    public int getCcMoshtary(){
        return this.ccMoshtary;
    }
    /*public void setCcShahrMoshtary(String ccShahrMoshtary){
        this.ccShahrMoshtary = ccShahrMoshtary;
    }
    public String getCcShahrMoshtary(){
        return this.ccShahrMoshtary;
    }*/
    public void setCcAddressMoshtary(int ccAddressMoshtary){
        this.ccAddressMoshtary = ccAddressMoshtary;
    }
    public int getCcAddressMoshtary(){
        return this.ccAddressMoshtary;
    }
    public void setShomarehDarkhast(int ShomarehDarkhast){
        this.ShomarehDarkhast = ShomarehDarkhast;
    }
    public int getShomarehDarkhast(){
        return this.ShomarehDarkhast;
    }
    public void setTarikhDarkhast(String TarikhDarkhast){
        this.TarikhDarkhast = TarikhDarkhast;
    }
    public String getTarikhDarkhast(){
        return this.TarikhDarkhast;
    }
    public void setShomarehFaktor(int ShomarehFaktor){
        this.ShomarehFaktor = ShomarehFaktor;
    }
    public int getShomarehFaktor(){
        return this.ShomarehFaktor;
    }
    public void setTarikhFaktor(String TarikhFaktor){
        this.TarikhFaktor = TarikhFaktor;
    }
    public String getTarikhFaktor(){
        return this.TarikhFaktor;
    }
    public void setTarikhPishbinyTahvil(String TarikhPishbinyTahvil){
        this.TarikhPishbinyTahvil = TarikhPishbinyTahvil;
    }
    public String getTarikhPishbinyTahvil(){
        return this.TarikhPishbinyTahvil;
    }
    public void setTarikhErsal(String TarikhErsal){
        this.TarikhErsal = TarikhErsal;
    }
    public String getTarikhErsal(){
        return this.TarikhErsal;
    }
    public void setCodeNoeVosolAzMoshtary(int CodeNoeVosolAzMoshtary){
        this.CodeNoeVosolAzMoshtary = CodeNoeVosolAzMoshtary;
    }
    public int getCodeNoeVosolAzMoshtary(){
        return this.CodeNoeVosolAzMoshtary;
    }
    public void setModateVosol(int ModateVosol){
        this.ModateVosol = ModateVosol;
    }
    public int getModateVosol(){
        return this.ModateVosol;
    }
    public void setModatRoozRaasGiri(int ModatRoozRaasGiri){
        this.ModatRoozRaasGiri = ModatRoozRaasGiri;
    }
    public int getModatRoozRaasGiri(){
        return this.ModatRoozRaasGiri;
    }
    public void setCodeNoeHaml(int CodeNoeHaml){
        this.CodeNoeHaml = CodeNoeHaml;
    }
    public int getCodeNoeHaml(){
        return this.CodeNoeHaml;
    }
    /*public void setCcNoeMashin(String ccNoeMashin){
        this.ccNoeMashin = ccNoeMashin;
    }
    public String getCcNoeMashin(){
        return this.ccNoeMashin;
    }*/
    public void setCodeVazeiat(int CodeVazeiat){
        this.CodeVazeiat = CodeVazeiat;
    }
    public int getCodeVazeiat(){
        return this.CodeVazeiat;
    }
    public void setMablaghKolDarkhast(int MablaghKolDarkhast){
        this.MablaghKolDarkhast = MablaghKolDarkhast;
    }
    public int getMablaghKolDarkhast(){
        return this.MablaghKolDarkhast;
    }
    public void setMablaghTakhfifDarkhastTitr(int MablaghTakhfifDarkhastTitr){
        this.MablaghTakhfifDarkhastTitr = MablaghTakhfifDarkhastTitr;
    }
    public int getMablaghTakhfifDarkhastTitr(){
        return this.MablaghTakhfifDarkhastTitr;
    }
    public void setMablaghTakhfifDarkhastSatr(float MablaghTakhfifDarkhastSatr){
        this.MablaghTakhfifDarkhastSatr = MablaghTakhfifDarkhastSatr;
    }
    public float getMablaghTakhfifDarkhastSatr(){
        return this.MablaghTakhfifDarkhastSatr;
    }

    public void setMablaghKolFaktor(double MablaghKolFaktor){
        this.MablaghKolFaktor = MablaghKolFaktor;
    }
    public double getMablaghKolFaktor(){
        return this.MablaghKolFaktor;
    }

    public void setMablaghTakhfifFaktorTitr(int MablaghTakhfifFaktorTitr){
        this.MablaghTakhfifFaktorTitr = MablaghTakhfifFaktorTitr;
    }
    public int getMablaghTakhfifFaktorTitr(){
        return this.MablaghTakhfifFaktorTitr;
    }
    public void setMablaghTakhfifFaktorSatr(float MablaghTakhfifFaktorSatr){
        this.MablaghTakhfifFaktorSatr = MablaghTakhfifFaktorSatr;
    }
    public float getMablaghTakhfifFaktorSatr(){
        return this.MablaghTakhfifFaktorSatr;
    }
    public void setMablaghEzafat(int MablaghEzafat){
        this.MablaghEzafat = MablaghEzafat;
    }
    public int getMablaghEzafat(){
        return this.MablaghEzafat;
    }
    public void setSaatVorodBeMaghazeh(String SaatVorodBeMaghazeh){
        this.SaatVorodBeMaghazeh = SaatVorodBeMaghazeh;
    }
    public String getSaatVorodBeMaghazeh(){
        return this.SaatVorodBeMaghazeh;
    }
    public void setSaatKhorojAzMaghazeh(String SaatKhorojAzMaghazeh){
        this.SaatKhorojAzMaghazeh = SaatKhorojAzMaghazeh;
    }
    public String getSaatKhorojAzMaghazeh(){
        return this.SaatKhorojAzMaghazeh;
    }
    public void setCcUser(int ccUser){
        this.ccUser = ccUser;
    }
    public int getCcUser(){
        return this.ccUser;
    }
    public void setCcNoeMoshtary(int ccNoeMoshtary){
        this.ccNoeMoshtary = ccNoeMoshtary;
    }
    public int getCcNoeMoshtary(){
        return this.ccNoeMoshtary;
    }
    public void setCcNoeSenf(int ccNoeSenf){
        this.ccNoeSenf = ccNoeSenf;
    }
    public int getCcNoeSenf(){
        return this.ccNoeSenf;
    }
    public void setShomarehFaktorIndex(int ShomarehFaktorIndex){
        this.ShomarehFaktorIndex = ShomarehFaktorIndex;
    }
    public int getShomarehFaktorIndex(){
        return this.ShomarehFaktorIndex;
    }
    public void setCcDorehMaly(int ccDorehMaly){
        this.ccDorehMaly = ccDorehMaly;
    }
    public int getCcDorehMaly(){
        return this.ccDorehMaly;
    }
    public void setBeMasoliat(int BeMasoliat){
        this.BeMasoliat = BeMasoliat;
    }
    public int getBeMasoliat(){
        return this.BeMasoliat;
    }
    public void setSumMaliat(float SumMaliat){
        this.SumMaliat = SumMaliat;
    }
    public float getSumMaliat(){
        return this.SumMaliat;
    }
    public void setSumAvarez(float SumAvarez){
        this.SumAvarez = SumAvarez;
    }
    public float getSumAvarez(){
        return this.SumAvarez;
    }
    public void setMablaghKhalesFaktor(double MablaghKhalesFaktor){
        this.MablaghKhalesFaktor = MablaghKhalesFaktor;
    }
    public double getMablaghKhalesFaktor(){
        return this.MablaghKhalesFaktor;
    }
    public void setMablaghKhalesDarkhast(double MablaghKhalesDarkhast){
        this.MablaghKhalesDarkhast = MablaghKhalesDarkhast;
    }
    public double getMablaghKhalesDarkhast(){
        return this.MablaghKhalesDarkhast;
    }
    public void setMablaghMandehMoshtary(float MablaghMandehMoshtary){
        this.MablaghMandehMoshtary = MablaghMandehMoshtary;
    }
    public float getMablaghMandehMoshtary(){
        return this.MablaghMandehMoshtary;
    }
    public void setShomarehDarkhastindex(String ShomarehDarkhastindex){
        this.ShomarehDarkhastindex = ShomarehDarkhastindex;
    }
    public String getShomarehDarkhastindex(){
        return this.ShomarehDarkhastindex;
    }
    public void setSumTedadFaktor(int SumTedadFaktor){
        this.SumTedadFaktor = SumTedadFaktor;
    }
    public int getSumTedadFaktor(){
        return this.SumTedadFaktor;
    }
    public void setNoeTahvil(int NoeTahvil){
        this.NoeTahvil = NoeTahvil;
    }
    public int getNoeTahvil(){
        return this.NoeTahvil;
    }
    public void setTaeedFaktorPPC(int TaeedFaktorPPC){
        this.TaeedFaktorPPC = TaeedFaktorPPC;
    }
    public int getTaeedFaktorPPC(){
        return this.TaeedFaktorPPC;
    }
    public void setLatitude(float Latitude){
        this.Latitude = Latitude;
    }
    public float getLatitude(){
        return this.Latitude;
    }
    public void setLongitude(float Longitude){
        this.Longitude = Longitude;
    }
    public float getLongitude(){
        return this.Longitude;
    }
    public void setTakhfifNaghdy(float TakhfifNaghdy){
        this.TakhfifNaghdy = TakhfifNaghdy;
    }
    public float getTakhfifNaghdy(){
        return this.TakhfifNaghdy;
    }
    public void setPPC_VersionNumber(String PPC_VersionNumber){
        this.PPC_VersionNumber = PPC_VersionNumber;
    }
    public String getPPC_VersionNumber(){
        return this.PPC_VersionNumber;
    }
    public void setModateTakhfif(int ModateTakhfif){
        this.ModateTakhfif = ModateTakhfif;
    }
    public int getModateTakhfif(){
        return this.ModateTakhfif;
    }
    public void setMarjoeeKamel(int MarjoeeKamel){
        this.MarjoeeKamel = MarjoeeKamel;
    }
    public int getMarjoeeKamel(){
        return this.MarjoeeKamel;
    }
    public void setNameNoeVosolAzMoshtary(String NameNoeVosolAzMoshtary){
        this.NameNoeVosolAzMoshtary = NameNoeVosolAzMoshtary;
    }
    public String getNameNoeVosolAzMoshtary(){
        return this.NameNoeVosolAzMoshtary;
    }
    public void setNameNoeHaml(String NameNoeHaml){
        this.NameNoeHaml = NameNoeHaml;
    }
    public String getNameNoeHaml(){
        return this.NameNoeHaml;
    }
    public void setNameVazeiat(String NameVazeiat){
        this.NameVazeiat = NameVazeiat;
    }
    public String getNameVazeiat(){
        return this.NameVazeiat;
    }
    public void setNameNoeTahvil(String NameNoeTahvil){
        this.NameNoeTahvil = NameNoeTahvil;
    }
    public String getNameNoeTahvil(){
        return this.NameNoeTahvil;
    }
    public void setFaktorRooz(long FaktorRooz){
        this.FaktorRooz = FaktorRooz;
    }
    public long getFaktorRooz(){
        return this.FaktorRooz;
    }
    public void setMablaghVosol(long MablaghVosol){
        this.MablaghVosol = MablaghVosol;
    }
    public long getMablaghVosol(){
        return this.MablaghVosol;
    }
    public void setMablaghMandeh(long MablaghMandeh){
        this.MablaghMandeh = MablaghMandeh;
    }
    public long getMablaghMandeh(){
        return this.MablaghMandeh;
    }
    public void setCcTafkikJoze(long ccTafkikJoze){
        this.ccTafkikJoze = ccTafkikJoze;
    }
    public long getCcTafkikJoze(){
        return this.ccTafkikJoze;
    }
    public void setDarajeh(int Darajeh){
        this.Darajeh = Darajeh;
    }
    public int getDarajeh(){
        return this.Darajeh;
    }
    public void setCodeMarkaz(String CodeMarkaz){
        this.CodeMarkaz = CodeMarkaz;
    }
    public String getCodeMarkaz(){
        return this.CodeMarkaz;
    }
    public void setForTasviehVosol(int ForTasviehVosol){
        this.ForTasviehVosol = ForTasviehVosol;
    }
    public int getForTasviehVosol(){
        return this.ForTasviehVosol;
    }
    public void setForForosh(int ForForosh){
        this.ForForosh = ForForosh;
    }
    public int getForForosh(){
        return this.ForForosh;
    }
    public void setDarkhastFaktorTakhfifs(String darkhastFaktorTakhfifs){
        this.darkhastFaktorTakhfifs = darkhastFaktorTakhfifs;
    }
    public String getDarkhastFaktorTakhfifs(){
        return this.darkhastFaktorTakhfifs;
    }
    public void setDarkhastFaktorSatrs(String darkhastFaktorSatrs){
        this.darkhastFaktorSatrs = darkhastFaktorSatrs;
    }
    public String getDarkhastFaktorSatrs(){
        return this.darkhastFaktorSatrs;
    }
    public void setAdamDarkhasts(String adamDarkhasts){
        this.adamDarkhasts = adamDarkhasts;
    }
    public String getAdamDarkhasts(){
        return this.adamDarkhasts;
    }
    public void setMojoodiGiris(String mojoodiGiris){
        this.mojoodiGiris = mojoodiGiris;
    }
    public String getMojoodiGiris(){
        return this.mojoodiGiris;
    }
    public void setUniqID_Tablet(String UniqID_Tablet){
        this.UniqID_Tablet = UniqID_Tablet;
    }
    public String getUniqID_Tablet(){
        return this.UniqID_Tablet;
    }

    public int getIsTajil() {
        return IsTajil;
    }

    public void setIsTajil(int isTajil) {
        IsTajil = isTajil;
    }

    public int getIsTakhir() {
        return IsTakhir;
    }

    public void setIsTakhir(int isTakhir) {
        IsTakhir = isTakhir;
    }

    /*public float getMablaghFaktorPishAzTakhfif() {
        return MablaghFaktorPishAzTakhfif;
    }

    public void setMablaghFaktorPishAzTakhfif(float mablaghFaktorPishAzTakhfif) {
        MablaghFaktorPishAzTakhfif = mablaghFaktorPishAzTakhfif;
    }*/

    /*public String getPPC_Version_Date() {
        return PPC_Version_Date;
    }

    public void setPPC_Version_Date(String PPC_Version_Date) {
        this.PPC_Version_Date = PPC_Version_Date;
    }*/

    public int getExtraProp_IsOld() {
        return ExtraProp_IsOld;
    }

    public void setExtraProp_IsOld(int extraProp_IsOld) {
        ExtraProp_IsOld = extraProp_IsOld;
    }

    public float getExtraProp_MablaghTakhfifSenfiHajmi() {
        return ExtraProp_MablaghTakhfifSenfiHajmi;
    }

    public void setExtraProp_MablaghTakhfifSenfiHajmi(float extraProp_MablaghTakhfifSenfiHajmi) {
        ExtraProp_MablaghTakhfifSenfiHajmi = extraProp_MablaghTakhfifSenfiHajmi;
    }

    public int getExtraProp_InsertInPPC() {
        return ExtraProp_InsertInPPC;
    }

    public void setExtraProp_InsertInPPC(int extraProp_InsertInPPC) {
        ExtraProp_InsertInPPC = extraProp_InsertInPPC;
    }

    public float getExtraProp_MablaghArzeshAfzoodeh() {
        return ExtraProp_MablaghArzeshAfzoodeh;
    }

    public void setExtraProp_MablaghArzeshAfzoodeh(float extraProp_MablaghArzeshAfzoodeh) {
        ExtraProp_MablaghArzeshAfzoodeh = extraProp_MablaghArzeshAfzoodeh;
    }

    public float getExtraProp_SumTakhfifat() {
        return ExtraProp_SumTakhfifat;
    }

    public void setExtraProp_SumTakhfifat(float extraProp_SumTakhfifat) {
        ExtraProp_SumTakhfifat = extraProp_SumTakhfifat;
    }

    public double getExtraProp_MablaghNahaeeFaktor() {
        return ExtraProp_MablaghNahaeeFaktor;
    }

    public void setExtraProp_MablaghNahaeeFaktor(double extraProp_MablaghNahaeeFaktor) {
        ExtraProp_MablaghNahaeeFaktor = extraProp_MablaghNahaeeFaktor;
    }

    public int getExtraProp_IsMarjoeeKamel() {
        return ExtraProp_IsMarjoeeKamel;
    }

    public void setExtraProp_IsMarjoeeKamel(int extraProp_IsMarjoeeKamel) {
        ExtraProp_IsMarjoeeKamel = extraProp_IsMarjoeeKamel;
    }

    public long getExtraProp_MablaghMandeh_NaghlAzGhabl() {
        return ExtraProp_MablaghMandeh_NaghlAzGhabl;
    }

    public void setExtraProp_MablaghMandeh_NaghlAzGhabl(long extraProp_MablaghMandeh_NaghlAzGhabl) {
        ExtraProp_MablaghMandeh_NaghlAzGhabl = extraProp_MablaghMandeh_NaghlAzGhabl;
    }

    public int getExtraProp_Resid() {
        return ExtraProp_Resid;
    }

    public void setExtraProp_Resid(int extraProp_Resid) {
        ExtraProp_Resid = extraProp_Resid;
    }

    public long getExtraProp_MablaghDariaftPardakht() {
        return ExtraProp_MablaghDariaftPardakht;
    }

    public void setExtraProp_MablaghDariaftPardakht(long extraProp_MablaghDariaftPardakht) {
        ExtraProp_MablaghDariaftPardakht = extraProp_MablaghDariaftPardakht;
    }

    public String getExtraProp_DarkhastFaktorImage() {
        return ExtraProp_DarkhastFaktorImage;
    }

    public void setExtraProp_DarkhastFaktorImage(String extraProp_DarkhastFaktorImage) {
        ExtraProp_DarkhastFaktorImage = extraProp_DarkhastFaktorImage;
    }

    public int getExtraProp_IsSend() {
        return ExtraProp_IsSend;
    }

    public void setExtraProp_IsSend(int extraProp_IsSend) {
        ExtraProp_IsSend = extraProp_IsSend;
    }

    public String getExtraProp_ShomarehDarkhast() {
        return ExtraProp_ShomarehDarkhast;
    }

    public void setExtraProp_ShomarehDarkhast(String extraProp_ShomarehDarkhast) {
        ExtraProp_ShomarehDarkhast = extraProp_ShomarehDarkhast;
    }

    public String getCodeMoshtary() {
        return CodeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary) {
        CodeMoshtary = codeMoshtary;
    }

    public int getNoeFaktorHavaleh() {
        return noeFaktorHavaleh;
    }

    public void setNoeFaktorHavaleh(int noeFaktorHavaleh) {
        this.noeFaktorHavaleh = noeFaktorHavaleh;
    }

    public int getShowFaktorMamorPakhsh()
    {
        return showFaktorMamorPakhsh;
    }

    public void setShowFaktorMamorPakhsh(int showFaktorMamorPakhsh)
    {
        this.showFaktorMamorPakhsh = showFaktorMamorPakhsh;
    }

    public int getCcDarkhastFaktorNoeForosh()
    {
        return ccDarkhastFaktorNoeForosh;
    }
    public void setCcDarkhastFaktorNoeForosh(int ccDarkhastFaktorNoeForosh)
    {
        this.ccDarkhastFaktorNoeForosh = ccDarkhastFaktorNoeForosh;
    }
    public int getMoshtaryGharardadccSazmanForosh() {
        return MoshtaryGharardadccSazmanForosh;
    }

    public void setMoshtaryGharardadccSazmanForosh(int moshtaryGharardadccSazmanForosh) {
        MoshtaryGharardadccSazmanForosh = moshtaryGharardadccSazmanForosh;
    }
    public int getCcMoshtaryGhardad() {
        return ccMoshtaryGharardad;
    }

    public void setCcMoshtaryGhardad(int ccMoshtaryGharardad) {
        this.ccMoshtaryGharardad = ccMoshtaryGharardad;
    }

    public String toJsonStringForVosol(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, int noeMasouliat, int ccMarkazForosh, int ccGorohNoeMoshtary, int ccGorohNoeSenf, JSONArray jsonDarkhastFaktorSatrs, JSONArray darkhastFaktorTakhfifModels, float mablaghTakhfifFaktorSatr, JSONArray jsonEmza, JSONArray jsonMoshtaryPhotoPPCChidman, JSONArray jsonDariaftPardakht)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            int ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
            int ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();

            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            if(noeMasouliat == 4)
            {
                jsonObject.put("ccDarkhastHavaleh" , ccDarkhastFaktor);
            }
            jsonObject.put("TarikhFaktor" , TarikhFaktor);
            jsonObject.put("ShomarehDarkhast" , (int)ShomarehDarkhast);
            jsonObject.put("ShomarehFaktor" , 0);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("TarikhErsal" , new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
            jsonObject.put("ModatRoozRaasGiri" , ModatRoozRaasGiri);
            jsonObject.put("ModateVosol" , ModateVosol);
            jsonObject.put("CodeNoeVosolAzMoshtary" , CodeNoeVosolAzMoshtary);
            jsonObject.put("MablaghKolDarkhast" , MablaghKolDarkhast);
            jsonObject.put("MablaghKolFaktor" , MablaghKolFaktor);
            jsonObject.put("MablaghEzafat" , 0);
            jsonObject.put("MablaghTakhfifFaktorTitr" , 0);
            jsonObject.put("TakhfifNaghdy" , TakhfifNaghdy);
            jsonObject.put("MablaghKhalesFaktor" , MablaghKhalesDarkhast);
            jsonObject.put("BeMasoliat" , BeMasoliat);
            jsonObject.put("CodeNoeHaml" , CodeNoeHaml);
            jsonObject.put("SumMaliat" , SumMaliat);
            jsonObject.put("SumAvarez" , SumAvarez);
            jsonObject.put("SaatVorodBeMaghazeh" , SaatVorodBeMaghazeh);
            jsonObject.put("SaatKhorojAzMaghazeh" , SaatKhorojAzMaghazeh);
            jsonObject.put("ccAddressMoshtary" , ccAddressMoshtary);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);
            jsonObject.put("Latitude" , String.valueOf(Latitude));
            jsonObject.put("Longitude" , String.valueOf(Longitude));
            jsonObject.put("ccNoeMoshtary" , ccGorohNoeMoshtary);
            jsonObject.put("ccNoeSenf" , ccGorohNoeSenf);
            jsonObject.put("PPC_VersionNumber" , PPC_VersionNumber);
            jsonObject.put("NoeForoshandeh" , foroshandehMamorPakhshModel.getNoeForoshandehMamorPakhsh());
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccMarkazAnbar" , foroshandehMamorPakhshModel.getCcMarkazAnbar());
            jsonObject.put("ccMarkazSazmanForosh" , ccMarkazSazmanForosh);
            jsonObject.put("ccMarkazSazmanForoshSakhtarForosh" , ccMarkazSazmanForoshSakhtarForosh);
            jsonObject.put("ccMoshtaryGharardad" , ccMoshtaryGharardad);
            jsonObject.put("MoshtaryGharardadccSazmanForosh" , MoshtaryGharardadccSazmanForosh);
            jsonObject.put("UniqID_Tablet" , UniqID_Tablet);
            jsonObject.put("Darajeh" , Darajeh);
            jsonObject.put("DarkhastFaktorTakhfifs" , darkhastFaktorTakhfifModels);
            jsonObject.put("DarkhastFaktorSatrs" , jsonDarkhastFaktorSatrs);
            jsonObject.put("MablaghTakhfifFaktorSatr" , String.valueOf(mablaghTakhfifFaktorSatr));
            jsonObject.put("emzaMoshtary" , jsonEmza);
            jsonObject.put("moshtaryphotoppc" , jsonMoshtaryPhotoPPCChidman);
            jsonObject.put("dariaftPardakhtPPCs" , jsonDariaftPardakht);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    public JSONObject toJsonForVosol(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, int noeMasouliat, int ccMarkazForosh, int ccGorohNoeMoshtary, int ccGorohNoeSenf, CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            int ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
            int ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();

            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            if(noeMasouliat == 4)
            {
                jsonObject.put("ccDarkhastHavaleh" , ccDarkhastFaktor);
            }
            jsonObject.put("TarikhFaktor" , TarikhFaktor);
            jsonObject.put("ShomarehDarkhast" , (int)ShomarehDarkhast);
            jsonObject.put("ShomarehFaktor" , 0);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("TarikhErsal" , new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
            jsonObject.put("TarikhPishbinyTahvil",TarikhPishbinyTahvil);
            jsonObject.put("ModatRoozRaasGiri" , ModatRoozRaasGiri);
            jsonObject.put("ModateVosol" , ModateVosol);
            jsonObject.put("CodeNoeVosolAzMoshtary" , CodeNoeVosolAzMoshtary);
            jsonObject.put("MablaghKolDarkhast" , MablaghKolFaktor);
            jsonObject.put("MablaghKolFaktor" , MablaghKolFaktor);
            jsonObject.put("MablaghEzafat" , 0);
            jsonObject.put("MablaghTakhfifFaktorTitr" , 0);
            jsonObject.put("TakhfifNaghdy" , TakhfifNaghdy);
            jsonObject.put("MablaghKhalesFaktor" , MablaghKhalesDarkhast);
            jsonObject.put("BeMasoliat" , BeMasoliat);
            jsonObject.put("CodeNoeHaml" , CodeNoeHaml);
            jsonObject.put("SumMaliat" , SumMaliat);
            jsonObject.put("SumAvarez" , SumAvarez);
            jsonObject.put("SaatVorodBeMaghazeh" , SaatVorodBeMaghazeh);
            jsonObject.put("SaatKhorojAzMaghazeh" , SaatKhorojAzMaghazeh);
            jsonObject.put("ccAddressMoshtary" , ccAddressMoshtary);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);
            jsonObject.put("Latitude" , String.valueOf(Latitude));
            jsonObject.put("Longitude" , String.valueOf(Longitude));
            jsonObject.put("ccNoeMoshtary" , ccGorohNoeMoshtary);
            jsonObject.put("ccNoeSenf" , ccGorohNoeSenf);
            jsonObject.put("PPC_VersionNumber" , PPC_VersionNumber);
            jsonObject.put("NoeForoshandeh" , foroshandehMamorPakhshModel.getNoeForoshandehMamorPakhsh());
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccMarkazAnbar" , foroshandehMamorPakhshModel.getCcMarkazAnbar());
            jsonObject.put("ccMarkazSazmanForosh" , ccMarkazSazmanForosh);
            jsonObject.put("ccMarkazSazmanForoshSakhtarForosh" , ccMarkazSazmanForoshSakhtarForosh);
            jsonObject.put("ccMoshtaryGharardad" , customerDarkhastFaktorModel.getCcMoshtaryGharardad());
            jsonObject.put("MoshtaryGharardadccSazmanForosh" , customerDarkhastFaktorModel.getMoshtaryGharardadccSazmanForosh());
            jsonObject.put("UniqID_Tablet" , UniqID_Tablet);
            jsonObject.put("Darajeh" , Darajeh);
			jsonObject.put("ccDarkhastFaktorNoeForosh" , ccDarkhastFaktorNoeForosh);
			Log.d("DarkhastFaktorModel", "ccDarkhastFaktorNoeForosh : " + ccDarkhastFaktorNoeForosh + " ,TarikhPishBini: " + TarikhPishbinyTahvil);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }

    //TODO قرارداد
    public JSONObject toJsonStringForVosolSard(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, int ccMarkazForosh, int ccGorohNoeMoshtary, int ccGorohNoeSenf, CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            int ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
            int ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();

            jsonObject.put("ccDarkhastHavaleh" , ccDarkhastFaktor);
            jsonObject.put("TarikhHavaleh" , TarikhFaktor);
            jsonObject.put("ShomarehHavaleh" , ShomarehDarkhast);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("TarikhErsal" , TarikhErsal);
            jsonObject.put("TarikhPishbinyTahvil",TarikhPishbinyTahvil);
            jsonObject.put("ModatRoozRaasGiri" , ModatRoozRaasGiri);
            jsonObject.put("ModateVosol" , ModateVosol);
            jsonObject.put("CodeNoeVosolAzMoshtary" , CodeNoeVosolAzMoshtary);
            jsonObject.put("MablaghKolHavaleh" , MablaghKolFaktor);
            jsonObject.put("MablaghTakhfifHavalehTitr" , 0);
            jsonObject.put("MablaghKhalesHavaleh" , MablaghKhalesFaktor);
            jsonObject.put("MablaghVajhDaryaftyHavaleh" , MablaghKhalesFaktor);
            jsonObject.put("BeMasoliat" , BeMasoliat);
            jsonObject.put("SumMaliat" , SumMaliat);
            jsonObject.put("SumAvarez" , SumAvarez);
            jsonObject.put("SaatVorodBeMaghazeh" , SaatVorodBeMaghazeh);
            jsonObject.put("SaatKhorojAzMaghazeh" , SaatKhorojAzMaghazeh);
            jsonObject.put("ccAddressMoshtary" , ccAddressMoshtary);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);
            jsonObject.put("Latitude" , Latitude);
            jsonObject.put("Longitude" , Longitude);
            jsonObject.put("ccNoeMoshtary" , ccGorohNoeMoshtary);
            jsonObject.put("ccNoeSenf" , ccGorohNoeSenf);
            jsonObject.put("PPC_VersionNumber" , PPC_VersionNumber);
            jsonObject.put("ccNoeForoshandeh" , foroshandehMamorPakhshModel.getNoeForoshandehMamorPakhsh());
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccMarkazAnbar" , foroshandehMamorPakhshModel.getCcMarkazAnbar());
            jsonObject.put("ccAnbar" , foroshandehMamorPakhshModel.getCcAnbar());
            jsonObject.put("ccMarkazSazmanForosh" , ccMarkazSazmanForosh);
            jsonObject.put("ccMarkazSazmanForoshSakhtarForosh" , ccMarkazSazmanForoshSakhtarForosh);
            jsonObject.put("ccMoshtaryGharardad" , customerDarkhastFaktorModel.getCcMoshtaryGharardad());
            jsonObject.put("MoshtaryGharardadccSazmanForosh" , customerDarkhastFaktorModel.getMoshtaryGharardadccSazmanForosh());
            jsonObject.put("UniqID_Tablet" , UniqID_Tablet);
            jsonObject.put("Darajeh" , Darajeh);
			jsonObject.put("ccDarkhastFaktorNoeForosh" , ccDarkhastFaktorNoeForosh);

            Log.d("DarkhastFaktorModel", "ccDarkhastFaktorNoeForosh - vosolsard : " + ccDarkhastFaktorNoeForosh + " ,TarikhPishBini: " + TarikhPishbinyTahvil);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "DarkhastFaktorModel{" +
                "ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccDarkhastFaktorPPC=" + ccDarkhastFaktorPPC +
                ", CodeNoeVorod=" + CodeNoeVorod +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", ccMarkazSazmanForosh=" + ccMarkazSazmanForosh +
                ", ccMarkazSazmanForoshSakhtarForosh=" + ccMarkazSazmanForoshSakhtarForosh +
                ", ccMarkazAnbar=" + ccMarkazAnbar +
                ", ccForoshandeh=" + ccForoshandeh +
                ", NoeForoshandeh=" + NoeForoshandeh +
                ", ccMoshtary=" + ccMoshtary +
                ", ccShahrMoshtary='" + ccShahrMoshtary + '\'' +
                ", ccAddressMoshtary=" + ccAddressMoshtary +
                ", ShomarehDarkhast=" + ShomarehDarkhast +
                ", TarikhDarkhast='" + TarikhDarkhast + '\'' +
                ", ShomarehFaktor=" + ShomarehFaktor +
                ", TarikhFaktor='" + TarikhFaktor + '\'' +
                ", TarikhPishbinyTahvil='" + TarikhPishbinyTahvil + '\'' +
                ", TarikhErsal='" + TarikhErsal + '\'' +
                ", CodeNoeVosolAzMoshtary=" + CodeNoeVosolAzMoshtary +
                ", ModateVosol=" + ModateVosol +
                ", ModatRoozRaasGiri=" + ModatRoozRaasGiri +
                ", CodeNoeHaml=" + CodeNoeHaml +
                ", ccNoeMashin='" + ccNoeMashin + '\'' +
                ", CodeVazeiat=" + CodeVazeiat +
                ", MablaghKolDarkhast=" + MablaghKolDarkhast +
                ", MablaghTakhfifDarkhastTitr=" + MablaghTakhfifDarkhastTitr +
                ", MablaghTakhfifDarkhastSatr=" + MablaghTakhfifDarkhastSatr +
                ", MablaghKolFaktor=" + MablaghKolFaktor +
                ", MablaghTakhfifFaktorTitr=" + MablaghTakhfifFaktorTitr +
                ", MablaghTakhfifFaktorSatr=" + MablaghTakhfifFaktorSatr +
                ", MablaghEzafat=" + MablaghEzafat +
                ", SaatVorodBeMaghazeh='" + SaatVorodBeMaghazeh + '\'' +
                ", SaatKhorojAzMaghazeh='" + SaatKhorojAzMaghazeh + '\'' +
                ", ccUser=" + ccUser +
                ", ccNoeMoshtary=" + ccNoeMoshtary +
                ", ccNoeSenf=" + ccNoeSenf +
                ", ShomarehFaktorIndex=" + ShomarehFaktorIndex +
                ", ccDorehMaly=" + ccDorehMaly +
                ", BeMasoliat=" + BeMasoliat +
                ", SumMaliat=" + SumMaliat +
                ", SumAvarez=" + SumAvarez +
                ", MablaghKhalesFaktor=" + MablaghKhalesFaktor +
                ", MablaghKhalesDarkhast=" + MablaghKhalesDarkhast +
                ", MablaghMandehMoshtary=" + MablaghMandehMoshtary +
                ", ShomarehDarkhastindex='" + ShomarehDarkhastindex + '\'' +
                ", SumTedadFaktor=" + SumTedadFaktor +
                ", NoeTahvil=" + NoeTahvil +
                ", TaeedFaktorPPC=" + TaeedFaktorPPC +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", TakhfifNaghdy=" + TakhfifNaghdy +
                ", PPC_VersionNumber='" + PPC_VersionNumber + '\'' +
                ", ModateTakhfif=" + ModateTakhfif +
                ", MarjoeeKamel=" + MarjoeeKamel +
                ", NameNoeVosolAzMoshtary='" + NameNoeVosolAzMoshtary + '\'' +
                ", NameNoeHaml='" + NameNoeHaml + '\'' +
                ", NameVazeiat='" + NameVazeiat + '\'' +
                ", NameNoeTahvil='" + NameNoeTahvil + '\'' +
                ", FaktorRooz=" + FaktorRooz +
                ", MablaghVosol=" + MablaghVosol +
                ", MablaghMandeh=" + MablaghMandeh +
                ", ccTafkikJoze=" + ccTafkikJoze +
                ", Darajeh=" + Darajeh +
                ", CodeMarkaz='" + CodeMarkaz + '\'' +
                ", ForTasviehVosol=" + ForTasviehVosol +
                ", ForForosh=" + ForForosh +
                ", darkhastFaktorTakhfifs='" + darkhastFaktorTakhfifs + '\'' +
                ", darkhastFaktorSatrs='" + darkhastFaktorSatrs + '\'' +
                ", adamDarkhasts='" + adamDarkhasts + '\'' +
                ", mojoodiGiris='" + mojoodiGiris + '\'' +
                ", UniqID_Tablet='" + UniqID_Tablet + '\'' +
                ", ccSazmanForosh=" + ccSazmanForosh +
                ", noeFaktorHavaleh=" + noeFaktorHavaleh +
                ", ccDarkhastFaktorNoeForosh=" + ccDarkhastFaktorNoeForosh +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld +
                ", ExtraProp_MablaghTakhfifSenfiHajmi=" + ExtraProp_MablaghTakhfifSenfiHajmi +
                ", ExtraProp_InsertInPPC=" + ExtraProp_InsertInPPC +
                ", ExtraProp_MablaghArzeshAfzoodeh=" + ExtraProp_MablaghArzeshAfzoodeh +
                ", ExtraProp_SumTakhfifat=" + ExtraProp_SumTakhfifat +
                ", ExtraProp_MablaghNahaeeFaktor=" + ExtraProp_MablaghNahaeeFaktor +
                ", ExtraProp_IsMarjoeeKamel=" + ExtraProp_IsMarjoeeKamel +
                ", ExtraProp_MablaghMandeh_NaghlAzGhabl=" + ExtraProp_MablaghMandeh_NaghlAzGhabl +
                ", ExtraProp_Resid=" + ExtraProp_Resid +
                ", ExtraProp_MablaghDariaftPardakht=" + ExtraProp_MablaghDariaftPardakht +
                ", ExtraProp_DarkhastFaktorImage='" + ExtraProp_DarkhastFaktorImage + '\'' +
                ", ExtraProp_IsSend=" + ExtraProp_IsSend +
                ", ExtraProp_ShomarehDarkhast='" + ExtraProp_ShomarehDarkhast + '\'' +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                ", showFaktorMamorPakhsh=" + showFaktorMamorPakhsh +
                ", IsTajil=" + IsTajil +
                ", IsTakhir=" + IsTakhir +
                ", ccMoshtaryGharardad=" + ccMoshtaryGharardad +
                ", MoshtaryGharardadccSazmanForosh=" + MoshtaryGharardadccSazmanForosh+
                '}';
    }


}
