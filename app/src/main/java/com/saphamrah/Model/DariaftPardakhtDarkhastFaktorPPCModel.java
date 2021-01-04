package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class DariaftPardakhtDarkhastFaktorPPCModel
{

    private static final String TABLE_NAME = "DariaftPardakhtDarkhastFaktorPPC";
    private static final String COLUMN_ccDariaftPardakhtDarkhastFaktor = "ccDariaftPardakhtDarkhastFaktor";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccDariaftPardakht = "ccDariaftPardakht";
    private static final String COLUMN_CodeNoeVosol = "CodeNoeVosol";
    private static final String COLUMN_NameNoeVosol = "NameNoeVosol";
    private static final String COLUMN_ShomarehSanad = "ShomarehSanad";
    private static final String COLUMN_TarikhSanad = "TarikhSanad";
    private static final String COLUMN_TarikhSanadShamsi = "TarikhSanadShamsi";
    private static final String COLUMN_MablaghDariaftPardakht = "MablaghDariaftPardakht";
    private static final String COLUMN_Mablagh = "Mablagh";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";
    private static final String COLUMN_ZamaneTakhsiseFaktor = "ZamaneTakhsiseFaktor";
    private static final String COLUMN_ccAfradErsalKonandeh = "ccAfradErsalKonandeh";
    private static final String COLUMN_ccMarkazAnbar = "ccMarkazAnbar";
    private static final String COLUMN_Tabdil_NaghdBeFish = "Tabdil_NaghdBeFish";
    private static final String COLUMN_ccTafkikJoze = "ccTafkikJoze";
    private static final String COLUMN_NaghlAzGhabl = "NaghlAzGhabl";
    private static final String COLUMN_IsForTasviehTakhir = "IsForTasviehTakhir";
    private static final String COLUMN_ZamaneTakhsiseFaktorShamsi = "ZamaneTakhsiseFaktorShamsi";
    private static final String COLUMN_ExtraProp_IsDirkard = "ExtraProp_IsDirkard";
    private static final String COLUMN_ExtraProp_ccKardexSatr = "ExtraProp_ccKardexSatr";
    private static final String COLUMN_ExtraProp_IsBestankari_ForTasviehTakhir = "ExtraProp_IsBestankari_ForTasviehTakhir";
    private static final String COLUMN_ExtraProp_IsSend = "ExtraProp_IsSend";
    private static final String COLUMN_ExtraProp_CanDelete = "ExtraProp_CanDelete";
    private static final String COLUMN_ExtraProp_IsTajil = "ExtraProp_IsTajil";
    private static final String COLUMN_ExtraProp_ccDarkhastFaktorServer = "ExtraProp_ccDarkhastFaktorServer";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_ccMarkazSazmanForoshSakhtarForosh = "ccMarkazSazmanForoshSakhtarForosh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_CcDariaftPardakhtDarkhastFaktor() {
        return COLUMN_ccDariaftPardakhtDarkhastFaktor;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccDariaftPardakht() {
        return COLUMN_ccDariaftPardakht;
    }
    public static String COLUMN_CodeNoeVosol() {
        return COLUMN_CodeNoeVosol;
    }
    public static String COLUMN_NameNoeVosol() {
        return COLUMN_NameNoeVosol;
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
    public static String COLUMN_MablaghDariaftPardakht() {
        return COLUMN_MablaghDariaftPardakht;
    }
    public static String COLUMN_Mablagh() {
        return COLUMN_Mablagh;
    }
    public static String COLUMN_CodeVazeiat() {
        return COLUMN_CodeVazeiat;
    }
    public static String COLUMN_ZamaneTakhsiseFaktor() {
        return COLUMN_ZamaneTakhsiseFaktor;
    }
    public static String COLUMN_ccAfradErsalKonandeh() {
        return COLUMN_ccAfradErsalKonandeh;
    }
    public static String COLUMN_ccMarkazAnbar() {
        return COLUMN_ccMarkazAnbar;
    }
    public static String COLUMN_Tabdil_NaghdBeFish() {
        return COLUMN_Tabdil_NaghdBeFish;
    }
    public static String COLUMN_ccTafkikJoze() {
        return COLUMN_ccTafkikJoze;
    }
    public static String COLUMN_NaghlAzGhabl() {
        return COLUMN_NaghlAzGhabl;
    }
    public static String COLUMN_IsForTasviehTakhir() {
        return COLUMN_IsForTasviehTakhir;
    }
    public static String COLUMN_ZamaneTakhsiseFaktorShamsi() {
        return COLUMN_ZamaneTakhsiseFaktorShamsi;
    }
    public static String COLUMN_ExtraProp_IsDirkard() {
        return COLUMN_ExtraProp_IsDirkard;
    }
    public static String COLUMN_ExtraProp_ccKardexSatr() {
        return COLUMN_ExtraProp_ccKardexSatr;
    }
    public static String COLUMN_ExtraProp_IsBestankari_ForTasviehTakhir() {
        return COLUMN_ExtraProp_IsBestankari_ForTasviehTakhir;
    }
    public static String COLUMN_ExtraProp_IsSend() {
        return COLUMN_ExtraProp_IsSend;
    }
    public static String COLUMN_ExtraProp_CanDelete() {
        return COLUMN_ExtraProp_CanDelete;
    }
    public static String COLUMN_ExtraProp_IsTajil() {
        return COLUMN_ExtraProp_IsTajil;
    }
    public static String COLUMN_ExtraProp_ccDarkhastFaktorServer() {
        return COLUMN_ExtraProp_ccDarkhastFaktorServer;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_ccMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_ccMarkazSazmanForoshSakhtarForosh;
    }




    private int ccDariaftPardakhtDarkhastFaktor;

    private long ccDarkhastFaktor;

    private long ccDariaftPardakht;

    private int CodeNoeVosol;

    private String NameNoeVosol;

    private String ShomarehSanad;

    private String TarikhSanad;

    private String TarikhSanadShamsi;

    private long MablaghDariaftPardakht;

    private long Mablagh;

    private int CodeVazeiat;

    private String ZamaneTakhsiseFaktor;

    private String ZamaneTakhsiseFaktorShamsi;

    private int ccAfradErsalKonandeh;

    private int ccMarkazAnbar;

    private int ccMarkazForosh;

    private int ccMarkazSazmanForoshSakhtarForosh;

    private String NameMarkazAnbar;

    private int ccUser;

    private int ccLinkTakhirTajilFaktor;

    private long ccTafkikJoze;

    private int Tabdil_NaghdBeFish;

    private int NaghlAzGhabl;

    private int IsForTasviehTakhir;

    private int ExtraProp_IsDirkard;
    private long ExtraProp_ccKardexSatr;
    private int ExtraProp_IsBestankari_ForTasviehTakhir;
    private int ExtraProp_IsSend;
    private int ExtraProp_CanDelete;
    private int ExtraProp_IsTajil;
    private long ExtraProp_ccDarkhastFaktorServer;


    public void setCcDariaftPardakhtDarkhastFaktor(int ccDariaftPardakhtDarkhastFaktor){
        this.ccDariaftPardakhtDarkhastFaktor = ccDariaftPardakhtDarkhastFaktor;
    }
    public int getCcDariaftPardakhtDarkhastFaktor(){
        return this.ccDariaftPardakhtDarkhastFaktor;
    }
    public void setCcDarkhastFaktor(long ccDarkhastFaktor){
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }
    public long getCcDarkhastFaktor(){
        return this.ccDarkhastFaktor;
    }
    public void setCcDariaftPardakht(long ccDariaftPardakht){
        this.ccDariaftPardakht = ccDariaftPardakht;
    }
    public long getCcDariaftPardakht(){
        return this.ccDariaftPardakht;
    }
    public void setCodeNoeVosol(int CodeNoeVosol){
        this.CodeNoeVosol = CodeNoeVosol;
    }
    public int getCodeNoeVosol(){
        return this.CodeNoeVosol;
    }
    public void setNameNoeVosol(String NameNoeVosol){
        this.NameNoeVosol = NameNoeVosol;
    }
    public String getNameNoeVosol(){
        return this.NameNoeVosol;
    }
    public void setShomarehSanad(String ShomarehSanad){
        this.ShomarehSanad = ShomarehSanad;
    }
    public String getShomarehSanad(){
        return this.ShomarehSanad;
    }
    public void setTarikhSanad(String TarikhSanad){
        this.TarikhSanad = TarikhSanad;
    }
    public String getTarikhSanad(){
        return this.TarikhSanad;
    }
    public void setTarikhSanadShamsi(String TarikhSanadShamsi){
        this.TarikhSanadShamsi = TarikhSanadShamsi;
    }
    public String getTarikhSanadShamsi(){
        return this.TarikhSanadShamsi;
    }
    public void setMablaghDariaftPardakht(long MablaghDariaftPardakht){
        this.MablaghDariaftPardakht = MablaghDariaftPardakht;
    }
    public double getMablaghDariaftPardakht(){
        return this.MablaghDariaftPardakht;
    }
    public void setMablagh(long Mablagh){
        this.Mablagh = Mablagh;
    }
    public double getMablagh(){
        return this.Mablagh;
    }
    public void setCodeVazeiat(int CodeVazeiat){
        this.CodeVazeiat = CodeVazeiat;
    }
    public int getCodeVazeiat(){
        return this.CodeVazeiat;
    }
    public void setZamaneTakhsiseFaktor(String ZamaneTakhsiseFaktor){
        this.ZamaneTakhsiseFaktor = ZamaneTakhsiseFaktor;
    }
    public String getZamaneTakhsiseFaktor(){
        return this.ZamaneTakhsiseFaktor;
    }
    public void setZamaneTakhsiseFaktorShamsi(String ZamaneTakhsiseFaktorShamsi){
        this.ZamaneTakhsiseFaktorShamsi = ZamaneTakhsiseFaktorShamsi;
    }
    public String getZamaneTakhsiseFaktorShamsi(){
        return this.ZamaneTakhsiseFaktorShamsi;
    }
    public void setCcAfradErsalKonandeh(int ccAfradErsalKonandeh){
        this.ccAfradErsalKonandeh = ccAfradErsalKonandeh;
    }
    public int getCcAfradErsalKonandeh(){
        return this.ccAfradErsalKonandeh;
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
    public void setNameMarkazAnbar(String NameMarkazAnbar){
        this.NameMarkazAnbar = NameMarkazAnbar;
    }
    public String getNameMarkazAnbar(){
        return this.NameMarkazAnbar;
    }
    public void setCcUser(int ccUser){
        this.ccUser = ccUser;
    }
    public int getCcUser(){
        return this.ccUser;
    }
    public void setCcLinkTakhirTajilFaktor(int ccLinkTakhirTajilFaktor){
        this.ccLinkTakhirTajilFaktor = ccLinkTakhirTajilFaktor;
    }
    public int getCcLinkTakhirTajilFaktor(){
        return this.ccLinkTakhirTajilFaktor;
    }
    public void setCcTafkikJoze(long ccTafkikJoze){
        this.ccTafkikJoze = ccTafkikJoze;
    }
    public long getCcTafkikJoze(){
        return this.ccTafkikJoze;
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
    public void setIsForTasviehTakhir(int IsForTasviehTakhir){
        this.IsForTasviehTakhir = IsForTasviehTakhir;
    }
    public int getIsForTasviehTakhir(){
        return this.IsForTasviehTakhir;
    }


    public static String getTableName() {
        return TABLE_NAME;
    }

    public int getExtraProp_IsDirkard() {
        return ExtraProp_IsDirkard;
    }

    public void setExtraProp_IsDirkard(int extraProp_IsDirkard) {
        ExtraProp_IsDirkard = extraProp_IsDirkard;
    }

    public long getExtraProp_ccKardexSatr() {
        return ExtraProp_ccKardexSatr;
    }

    public void setExtraProp_ccKardexSatr(long extraProp_ccKardexSatr) {
        ExtraProp_ccKardexSatr = extraProp_ccKardexSatr;
    }

    public int getExtraProp_IsBestankari_ForTasviehTakhir() {
        return ExtraProp_IsBestankari_ForTasviehTakhir;
    }

    public void setExtraProp_IsBestankari_ForTasviehTakhir(int extraProp_IsBestankari_ForTasviehTakhir) {
        ExtraProp_IsBestankari_ForTasviehTakhir = extraProp_IsBestankari_ForTasviehTakhir;
    }

    public int getExtraProp_IsSend() {
        return ExtraProp_IsSend;
    }

    public void setExtraProp_IsSend(int extraProp_IsSend) {
        ExtraProp_IsSend = extraProp_IsSend;
    }

    public int getExtraProp_CanDelete() {
        return ExtraProp_CanDelete;
    }

    public void setExtraProp_CanDelete(int extraProp_CanDelete) {
        ExtraProp_CanDelete = extraProp_CanDelete;
    }

    public int getExtraProp_IsTajil() {
        return ExtraProp_IsTajil;
    }

    public void setExtraProp_IsTajil(int extraProp_IsTajil) {
        ExtraProp_IsTajil = extraProp_IsTajil;
    }

    public long getExtraProp_ccDarkhastFaktorServer() {
        return ExtraProp_ccDarkhastFaktorServer;
    }

    public void setExtraProp_ccDarkhastFaktorServer(long extraProp_ccDarkhastFaktorServer) {
        ExtraProp_ccDarkhastFaktorServer = extraProp_ccDarkhastFaktorServer;
    }


    public JSONObject toJsonObject(int ccMarkazForosh, int ccMarkazAnbar, int ccMarkazSazmanForoshSakhtarForosh, int ccAfrad)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccDariaftPardakhtDarkhastFaktor" , ccDariaftPardakhtDarkhastFaktor);
            jsonObject.put("ccDarkhastFaktor" , ExtraProp_ccDarkhastFaktorServer);
            jsonObject.put("ccDariaftPardakht" , ccDariaftPardakht);
            jsonObject.put("Mablagh" , Mablagh);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);
            jsonObject.put("ZamaneTakhsiseFaktor" , ZamaneTakhsiseFaktor);
            jsonObject.put("ShomarehFaktor" , 0);
            jsonObject.put("SalFaktor" , 0);
            jsonObject.put("ccUser" , ccAfrad);
            jsonObject.put("Taeed" , 0);
            jsonObject.put("ccdpdf_PPC" , ccDariaftPardakhtDarkhastFaktor);
            jsonObject.put("IsForTasviehTakhir" , IsForTasviehTakhir);
            jsonObject.put("ccMarkazAnbar" , ccMarkazAnbar);
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccMarkazSazmanForoshSakhtarForosh" , this.ccMarkazSazmanForoshSakhtarForosh==0 ? ccMarkazSazmanForoshSakhtarForosh : this.ccMarkazSazmanForoshSakhtarForosh);
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
        return "DariaftPardakhtDarkhastFaktorPPCModel{" +
                "ccDariaftPardakhtDarkhastFaktor=" + ccDariaftPardakhtDarkhastFaktor +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccDariaftPardakht=" + ccDariaftPardakht +
                ", CodeNoeVosol=" + CodeNoeVosol +
                ", NameNoeVosol='" + NameNoeVosol + '\'' +
                ", ShomarehSanad='" + ShomarehSanad + '\'' +
                ", TarikhSanad='" + TarikhSanad + '\'' +
                ", TarikhSanadShamsi='" + TarikhSanadShamsi + '\'' +
                ", MablaghDariaftPardakht=" + MablaghDariaftPardakht +
                ", Mablagh=" + Mablagh +
                ", CodeVazeiat=" + CodeVazeiat +
                ", ZamaneTakhsiseFaktor='" + ZamaneTakhsiseFaktor + '\'' +
                ", ZamaneTakhsiseFaktorShamsi='" + ZamaneTakhsiseFaktorShamsi + '\'' +
                ", ccAfradErsalKonandeh=" + ccAfradErsalKonandeh +
                ", ccMarkazAnbar=" + ccMarkazAnbar +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", ccMarkazSazmanForoshSakhtarForosh=" + ccMarkazSazmanForoshSakhtarForosh +
                ", NameMarkazAnbar='" + NameMarkazAnbar + '\'' +
                ", ccUser=" + ccUser +
                ", ccLinkTakhirTajilFaktor=" + ccLinkTakhirTajilFaktor +
                ", ccTafkikJoze=" + ccTafkikJoze +
                ", Tabdil_NaghdBeFish=" + Tabdil_NaghdBeFish +
                ", NaghlAzGhabl=" + NaghlAzGhabl +
                ", IsForTasviehTakhir=" + IsForTasviehTakhir +
                '}';
    }
}
