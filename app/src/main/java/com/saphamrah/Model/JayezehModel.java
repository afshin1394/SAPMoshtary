package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class JayezehModel
{
    private static final String TABLE_NAME = "Jayezeh";
    private static final String COLUMN_ccJayezeh = "ccJayezeh";
    private static final String COLUMN_CodeNoe = "CodeNoe";
    private static final String COLUMN_SharhJayezeh = "SharhJayezeh";
    private static final String COLUMN_NoeTedadRial = "NoeTedadRial";
    private static final String COLUMN_NameNoeFieldMoshtary = "NameNoeFieldMoshtary";
    private static final String COLUMN_ccNoeFieldMoshtary = "ccNoeFieldMoshtary";
    private static final String COLUMN_ccMarkazSazmanForosh = "ccMarkazSazmanForosh";
    private static final String COLUMN_CodeNoeHaml = "CodeNoeHaml";
    private static final String COLUMN_CodeNoeMohasebeh = "CodeNoeMohasebeh";
    private static final String COLUMN_Darajeh = "Darajeh";
    private static final String COLUMN_IsJayezehEntekhabi = "IsJayezehEntekhabi";
    private static final String COLUMN_ccNoeSenf = "ccNoeSenf";
    private static final String COLUMN_NameNoeSenf = "NameNoeSenf";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccJayezeh() {
        return COLUMN_ccJayezeh;
    }
    public static String COLUMN_CodeNoe() {
        return COLUMN_CodeNoe;
    }
    public static String COLUMN_SharhJayezeh() {
        return COLUMN_SharhJayezeh;
    }
    public static String COLUMN_NoeTedadRial() {
        return COLUMN_NoeTedadRial;
    }
    public static String COLUMN_NameNoeFieldMoshtary() {
        return COLUMN_NameNoeFieldMoshtary;
    }
    public static String COLUMN_ccNoeFieldMoshtary() {
        return COLUMN_ccNoeFieldMoshtary;
    }
    public static String COLUMN_ccMarkazSazmanForosh() {
        return COLUMN_ccMarkazSazmanForosh;
    }
    public static String COLUMN_CodeNoeHaml() {
        return COLUMN_CodeNoeHaml;
    }
    public static String COLUMN_CodeNoeMohasebeh() {
        return COLUMN_CodeNoeMohasebeh;
    }
    public static String COLUMN_Darajeh() {
        return COLUMN_Darajeh;
    }
    public static String COLUMN_IsJayezehEntekhabi() {
        return COLUMN_IsJayezehEntekhabi;
    }
    public static String COLUMN_ccNoeSenf() {
        return COLUMN_ccNoeSenf;
    }
    public static String COLUMN_NameNoeSenf() {
        return COLUMN_NameNoeSenf;
    }




    private int CodeNoeTakhfif;
    public void setCodeNoeTakhfif(int CodeNoeTakhfif){
        this.CodeNoeTakhfif = CodeNoeTakhfif;
    }
    public int getCodeNoeTakhfif(){
        return this.CodeNoeTakhfif;
    }


    private int ccJayezeh;
    public void setCcJayezeh(int ccJayezeh){
        this.ccJayezeh = ccJayezeh;
    }
    public int getCcJayezeh(){
        return this.ccJayezeh;
    }


    private int CodeNoe;
    public void setCodeNoe(int CodeNoe){
        this.CodeNoe = CodeNoe;
    }
    public int getCodeNoe(){
        return this.CodeNoe;
    }


    private String SharhJayezeh;
    public void setSharhJayezeh(String SharhJayezeh){
        this.SharhJayezeh = SharhJayezeh;
    }
    public String getSharhJayezeh(){
        return this.SharhJayezeh;
    }


    private String FromDate;
    public void setFromDate(String FromDate){
        this.FromDate = FromDate;
    }
    public String getFromDate(){
        return this.FromDate;
    }


    private String FromDateWithoutSlash;
    public void setFromDateWithoutSlash(String FromDateWithoutSlash){
        this.FromDateWithoutSlash = FromDateWithoutSlash;
    }
    public String getFromDateWithoutSlash(){
        return this.FromDateWithoutSlash;
    }


    private String EndDate;
    public void setEndDate(String EndDate){
        this.EndDate = EndDate;
    }
    public String getEndDate(){
        return this.EndDate;
    }


    private String EndDateWithoutSlash;
    public void setEndDateWithoutSlash(String EndDateWithoutSlash){
        this.EndDateWithoutSlash = EndDateWithoutSlash;
    }
    public String getEndDateWithoutSlash(){
        return this.EndDateWithoutSlash;
    }


    private int NoeTedadRial;
    public void setNoeTedadRial(int NoeTedadRial){
        this.NoeTedadRial = NoeTedadRial;
    }
    public int getNoeTedadRial(){
        return this.NoeTedadRial;
    }


    private String txtNoeTedadRial;
    public void setTxtNoeTedadRial(String txtNoeTedadRial){
        this.txtNoeTedadRial = txtNoeTedadRial;
    }
    public String getTxtNoeTedadRial(){
        return this.txtNoeTedadRial;
    }


    private int NameNoeFieldMoshtary;
    public void setNameNoeFieldMoshtary(int NameNoeFieldMoshtary){
        this.NameNoeFieldMoshtary = NameNoeFieldMoshtary;
    }
    public int getNameNoeFieldMoshtary(){
        return this.NameNoeFieldMoshtary;
    }


    private String txtNameNoeFieldMoshtary;
    public void setTxtNameNoeFieldMoshtary(String txtNameNoeFieldMoshtary){
        this.txtNameNoeFieldMoshtary = txtNameNoeFieldMoshtary;
    }
    public String getTxtNameNoeFieldMoshtary(){
        return this.txtNameNoeFieldMoshtary;
    }


    private int ccNoeFieldMoshtary;
    public void setCcNoeFieldMoshtary(int ccNoeFieldMoshtary){
        this.ccNoeFieldMoshtary = ccNoeFieldMoshtary;
    }
    public int getCcNoeFieldMoshtary(){
        return this.ccNoeFieldMoshtary;
    }


    private int ccMarkazSazmanForosh;
    public int getCcMarkazSazmanForosh()
    {
        return ccMarkazSazmanForosh;
    }
    public void setCcMarkazSazmanForosh(int ccMarkazSazmanForosh)
    {
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }

    private int ccMoshtary;
    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }
    public int getCcMoshtary(){
        return this.ccMoshtary;
    }


    private String NameMoshtary;
    public void setNameMoshtary(String NameMoshtary){
        this.NameMoshtary = NameMoshtary;
    }
    public String getNameMoshtary(){
        return this.NameMoshtary;
    }


    private int ccGoroh;
    public void setCcGoroh(int ccGoroh){
        this.ccGoroh = ccGoroh;
    }
    public int getCcGoroh(){
        return this.ccGoroh;
    }


    private String NameGoroh;
    public void setNameGoroh(String NameGoroh){
        this.NameGoroh = NameGoroh;
    }
    public String getNameGoroh(){
        return this.NameGoroh;
    }


    private String NameGorohText;
    public void setNameGorohText(String NameGorohText){
        this.NameGorohText = NameGorohText;
    }
    public String getNameGorohText(){
        return this.NameGorohText;
    }


    private String CodeMoshtary;
    public void setCodeMoshtary(String CodeMoshtary){
        this.CodeMoshtary = CodeMoshtary;
    }
    public String getCodeMoshtary(){
        return this.CodeMoshtary;
    }


    private int CodeNoeHaml;
    public void setCodeNoeHaml(int CodeNoeHaml){
        this.CodeNoeHaml = CodeNoeHaml;
    }
    public int getCodeNoeHaml(){
        return this.CodeNoeHaml;
    }


    private int CodeNoeMohasebeh;
    public void setCodeNoeMohasebeh(int CodeNoeMohasebeh){
        this.CodeNoeMohasebeh = CodeNoeMohasebeh;
    }
    public int getCodeNoeMohasebeh(){
        return this.CodeNoeMohasebeh;
    }


    private int SahmHesab;
    public void setSahmHesab(int SahmHesab){
        this.SahmHesab = SahmHesab;
    }
    public int getSahmHesab(){
        return this.SahmHesab;
    }


    private int BeHesab;
    public void setBeHesab(int BeHesab){
        this.BeHesab = BeHesab;
    }
    public int getBeHesab(){
        return this.BeHesab;
    }


    private int Darajeh;
    public void setDarajeh(int Darajeh){
        this.Darajeh = Darajeh;
    }
    public int getDarajeh(){
        return this.Darajeh;
    }

    private int Id;
    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }


    private int IsJayezehentekhabi;
    public int getIsJayezehEntekhabi()
    {
        return IsJayezehentekhabi;
    }
    public void setIsJayezehEntekhabi(int isJayezehEntekhabi)
    {
        IsJayezehentekhabi = isJayezehEntekhabi;
    }

    private int ccNoeSenf;
    public void setCcNoeSenf(int ccNoeSenf){
        this.ccNoeSenf = ccNoeSenf;
    }
    public int getCcNoeSenf(){
        return this.ccNoeSenf;
    }


    private String NameNoeSenf;
    public void setNameNoeSenf(String NameNoeSenf){
        this.NameNoeSenf = NameNoeSenf;
    }
    public String getNameNoeSenf(){
        return this.NameNoeSenf;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "JayezehModel{" +
                "CodeNoeTakhfif=" + CodeNoeTakhfif +
                ", ccJayezeh=" + ccJayezeh +
                ", CodeNoe=" + CodeNoe +
                ", SharhJayezeh='" + SharhJayezeh + '\'' +
                ", FromDate='" + FromDate + '\'' +
                ", FromDateWithoutSlash='" + FromDateWithoutSlash + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", EndDateWithoutSlash='" + EndDateWithoutSlash + '\'' +
                ", NoeTedadRial=" + NoeTedadRial +
                ", txtNoeTedadRial='" + txtNoeTedadRial + '\'' +
                ", NameNoeFieldMoshtary=" + NameNoeFieldMoshtary +
                ", txtNameNoeFieldMoshtary='" + txtNameNoeFieldMoshtary + '\'' +
                ", ccNoeFieldMoshtary=" + ccNoeFieldMoshtary +
                ", ccMarkazSazmanForosh=" + ccMarkazSazmanForosh +
                ", ccMoshtary=" + ccMoshtary +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", ccGoroh=" + ccGoroh +
                ", NameGoroh='" + NameGoroh + '\'' +
                ", NameGorohText='" + NameGorohText + '\'' +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                ", CodeNoeHaml=" + CodeNoeHaml +
                ", CodeNoeMohasebeh=" + CodeNoeMohasebeh +
                ", SahmHesab=" + SahmHesab +
                ", BeHesab=" + BeHesab +
                ", Darajeh=" + Darajeh +
                ", Id=" + Id +
                ", IsJayezehentekhabi=" + IsJayezehentekhabi +
                ", ccNoeSenf=" + ccNoeSenf +
                ", NameNoeSenf=" + NameNoeSenf +
                '}';
    }
}
