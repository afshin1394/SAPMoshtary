package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class JayezehSatrModel
{

    private static final String TABLE_NAME = "JayezehSatr";
    private static final String COLUMN_ccJayezehSatr = "ccJayezehSatr";
    private static final String COLUMN_ccJayezeh = "ccJayezeh";
    private static final String COLUMN_NameNoeField = "NameNoeField";
    private static final String COLUMN_ccNoeField = "ccNoeField";
    private static final String COLUMN_Az = "Az";
    private static final String COLUMN_Ta = "Ta";
    private static final String COLUMN_CodeNoeBastehBandy = "CodeNoeBastehBandy";
    private static final String COLUMN_BeEza = "BeEza";
    private static final String COLUMN_CodeNoeBastehBandyBeEza = "CodeNoeBastehBandyBeEza";
    private static final String COLUMN_TedadJayezeh = "TedadJayezeh";
    private static final String COLUMN_RialJayezeh = "RialJayezeh";
    private static final String COLUMN_ccKalaCodeJayezeh = "ccKalaCodeJayezeh";
    private static final String COLUMN_MohasebehAzMazad = "MohasebehAzMazad";
    private static final String COLUMN_NoeRialJayezeh = "NoeRialJayezeh";
    private static final String COLUMN_ccKalaJayezeh = "ccKalaJayezeh";
    private static final String COLUMN_ccKalaJayezehMazad = "ccKalaJayezehMazad";
    private static final String COLUMN_ccKalaCodeJayezehMazad = "ccKalaCodeJayezehMazad";
    private static final String COLUMN_Naghdy = "Naghdy";
    //private static final String COLUMN_JayezehSatrKalas = "JayezehSatrKalas";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccJayezehSatr() {
        return COLUMN_ccJayezehSatr;
    }
    public static String COLUMN_ccJayezeh() {
        return COLUMN_ccJayezeh;
    }
    public static String COLUMN_NameNoeField() {
        return COLUMN_NameNoeField;
    }
    public static String COLUMN_ccNoeField() {
        return COLUMN_ccNoeField;
    }
    public static String COLUMN_Az() {
        return COLUMN_Az;
    }
    public static String COLUMN_Ta() {
        return COLUMN_Ta;
    }
    public static String COLUMN_CodeNoeBastehBandy() {
        return COLUMN_CodeNoeBastehBandy;
    }
    public static String COLUMN_BeEza() {
        return COLUMN_BeEza;
    }
    public static String COLUMN_CodeNoeBastehBandyBeEza() {
        return COLUMN_CodeNoeBastehBandyBeEza;
    }
    public static String COLUMN_TedadJayezeh() {
        return COLUMN_TedadJayezeh;
    }
    public static String COLUMN_RialJayezeh() {
        return COLUMN_RialJayezeh;
    }
    public static String COLUMN_ccKalaCodeJayezeh() {
        return COLUMN_ccKalaCodeJayezeh;
    }
    public static String COLUMN_MohasebehAzMazad() {
        return COLUMN_MohasebehAzMazad;
    }
    public static String COLUMN_NoeRialJayezeh() {
        return COLUMN_NoeRialJayezeh;
    }
    public static String COLUMN_ccKalaJayezeh() {
        return COLUMN_ccKalaJayezeh;
    }
    public static String COLUMN_ccKalaJayezehMazad() {
        return COLUMN_ccKalaJayezehMazad;
    }
    public static String COLUMN_ccKalaCodeJayezehMazad() {
        return COLUMN_ccKalaCodeJayezehMazad;
    }
    public static String COLUMN_Naghdy() {
        return COLUMN_Naghdy;
    }
    /*public static String COLUMN_JayezehSatrKalas() {
        return COLUMN_JayezehSatrKalas;
    }*/


    /*[ccJayezehSatr] integer PRIMARY KEY Not NULL
    ,[ccJayezeh] int
    ,[NameNoeField] int
    ,[ccNoeField] int
    ,[Az] float
    ,[Ta] float
    ,[CodeNoeBastehBandy] int
    ,[BeEza] float
    ,[CodeNoeBastehBandyBeEza] int
    ,[TedadJayezeh] int
    ,[RialJayezeh] float
    ,[ccKalaCodeJayezeh] int
    ,[MohasebehAzMazad] bit
    ,[NoeRialJayezeh] int
    ,[ccKalaJayezeh] int
    ,[ccKalaJayezehMazad] int
    ,[ccKalaCodeJayezehMazad] int
    ,[Naghdy] tinyint
    ,[JayezehSatrKalas] nvarchar(200)*/



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


    private int ccJayezehSatr;
    public void setCcJayezehSatr(int ccJayezehSatr){
        this.ccJayezehSatr = ccJayezehSatr;
    }
    public int getCcJayezehSatr(){
        return this.ccJayezehSatr;
    }


    private int NameNoeField;
    public void setNameNoeField(int NameNoeField){
        this.NameNoeField = NameNoeField;
    }
    public int getNameNoeField(){
        return this.NameNoeField;
    }


    private String txtNameNoeField;
    public void setTxtNameNoeField(String txtNameNoeField){
        this.txtNameNoeField = txtNameNoeField;
    }
    public String getTxtNameNoeField(){
        return this.txtNameNoeField;
    }


    private String ccNoeField;
    public void setCcNoeField(String ccNoeField){
        this.ccNoeField = ccNoeField;
    }
    public String getCcNoeField(){
        return this.ccNoeField;
    }


    private String ccKalaCode;
    public void setCcKalaCode(String ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public String getCcKalaCode(){
        return this.ccKalaCode;
    }


    private String NameKala;
    public void setNameKala(String NameKala){
        this.NameKala = NameKala;
    }
    public String getNameKala(){
        return this.NameKala;
    }


    private String ccBrand;
    public void setCcBrand(String ccBrand){
        this.ccBrand = ccBrand;
    }
    public String getCcBrand(){
        return this.ccBrand;
    }


    private String NameBrand;
    public void setNameBrand(String NameBrand){
        this.NameBrand = NameBrand;
    }
    public String getNameBrand(){
        return this.NameBrand;
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


    private String ccTaminKonandeh;
    public void setCcTaminKonandeh(String ccTaminKonandeh){
        this.ccTaminKonandeh = ccTaminKonandeh;
    }
    public String getCcTaminKonandeh(){
        return this.ccTaminKonandeh;
    }


    private String NameTaminKonandeh;
    public void setNameTaminKonandeh(String NameTaminKonandeh){
        this.NameTaminKonandeh = NameTaminKonandeh;
    }
    public String getNameTaminKonandeh(){
        return this.NameTaminKonandeh;
    }


    private double Az;
    public void setAz(double Az){
        this.Az = Az;
    }
    public double getAz(){
        return this.Az;
    }


    private double Ta;
    public void setTa(double Ta){
        this.Ta = Ta;
    }
    public double getTa(){
        return this.Ta;
    }


    private int CodeNoeBastehBandy;
    public void setCodeNoeBastehBandy(int CodeNoeBastehBandy){
        this.CodeNoeBastehBandy = CodeNoeBastehBandy;
    }
    public int getCodeNoeBastehBandy(){
        return this.CodeNoeBastehBandy;
    }


    private String txtCodeNoeBastehBandy;
    public void setTxtCodeNoeBastehBandy(String txtCodeNoeBastehBandy){
        this.txtCodeNoeBastehBandy = txtCodeNoeBastehBandy;
    }
    public String getTxtCodeNoeBastehBandy(){
        return this.txtCodeNoeBastehBandy;
    }


    private float BeEza;
    public void setBeEza(float BeEza){
        this.BeEza = BeEza;
    }
    public float getBeEza(){
        return this.BeEza;
    }


    private int CodeNoeBastehBandyBeEza;
    public void setCodeNoeBastehBandyBeEza(int CodeNoeBastehBandyBeEza){
        this.CodeNoeBastehBandyBeEza = CodeNoeBastehBandyBeEza;
    }
    public int getCodeNoeBastehBandyBeEza(){
        return this.CodeNoeBastehBandyBeEza;
    }


    private String txtCodeNoeBastehBandyBeEza;
    public void setTxtCodeNoeBastehBandyBeEza(String txtCodeNoeBastehBandyBeEza){
        this.txtCodeNoeBastehBandyBeEza = txtCodeNoeBastehBandyBeEza;
    }
    public String getTxtCodeNoeBastehBandyBeEza(){
        return this.txtCodeNoeBastehBandyBeEza;
    }


    private int TedadJayezeh;
    public void setTedadJayezeh(int TedadJayezeh){
        this.TedadJayezeh = TedadJayezeh;
    }
    public int getTedadJayezeh(){
        return this.TedadJayezeh;
    }


    private float RialJayezeh;
    public void setRialJayezeh(float RialJayezeh){
        this.RialJayezeh = RialJayezeh;
    }
    public float getRialJayezeh(){
        return this.RialJayezeh;
    }


    private int ccKalaJayezeh;
    public void setCcKalaJayezeh(int ccKalaJayezeh){
        this.ccKalaJayezeh = ccKalaJayezeh;
    }
    public int getCcKalaJayezeh(){
        return this.ccKalaJayezeh;
    }


    private String NameKalaJayezeh;
    public void setNameKalaJayezeh(String NameKalaJayezeh){
        this.NameKalaJayezeh = NameKalaJayezeh;
    }
    public String getNameKalaJayezeh(){
        return this.NameKalaJayezeh;
    }


    private int ccKalaJayezehMazad;
    public void setCcKalaJayezehMazad(int ccKalaJayezehMazad){
        this.ccKalaJayezehMazad = ccKalaJayezehMazad;
    }
    public int getCcKalaJayezehMazad(){
        return this.ccKalaJayezehMazad;
    }


    private String NameKalaJayezehMazad;
    public void setNameKalaJayezehMazad(String NameKalaJayezehMazad){
        this.NameKalaJayezehMazad = NameKalaJayezehMazad;
    }
    public String getNameKalaJayezehMazad(){
        return this.NameKalaJayezehMazad;
    }


    private int NoeRialJayezeh;
    public void setNoeRialJayezeh(int NoeRialJayezeh){
        this.NoeRialJayezeh = NoeRialJayezeh;
    }
    public int getNoeRialJayezeh(){
        return this.NoeRialJayezeh;
    }


    private String txtNoeRialJayezeh;
    public void setTxtNoeRialJayezeh(String txtNoeRialJayezeh){
        this.txtNoeRialJayezeh = txtNoeRialJayezeh;
    }
    public String getTxtNoeRialJayezeh(){
        return this.txtNoeRialJayezeh;
    }


    private boolean MohasebehAzMazad;
    public void setMohasebehAzMazad(boolean MohasebehAzMazad){
        this.MohasebehAzMazad = MohasebehAzMazad;
    }
    public boolean getMohasebehAzMazad(){
        return this.MohasebehAzMazad;
    }


    private String txtMohasebehAzMazad;
    public void setTxtMohasebehAzMazad(String txtMohasebehAzMazad){
        this.txtMohasebehAzMazad = txtMohasebehAzMazad;
    }
    public String getTxtMohasebehAzMazad(){
        return this.txtMohasebehAzMazad;
    }


    private int ccKalaCodeJayezehMazad;
    public void setCcKalaCodeJayezehMazad(int ccKalaCodeJayezehMazad){
        this.ccKalaCodeJayezehMazad = ccKalaCodeJayezehMazad;
    }
    public int getCcKalaCodeJayezehMazad(){
        return this.ccKalaCodeJayezehMazad;
    }


    private int Naghdy;
    public void setNaghdy(int Naghdy){
        this.Naghdy = Naghdy;
    }
    public int getNaghdy(){
        return this.Naghdy;
    }


    private int ccKalaCodeJayezeh;
    public void setCcKalaCodeJayezeh(int ccKalaCodeJayezeh){
        this.ccKalaCodeJayezeh = ccKalaCodeJayezeh;
    }
    public int getCcKalaCodeJayezeh(){
        return this.ccKalaCodeJayezeh;
    }


    private String ccKalaCodeJayezeh1;
    public void setCcKalaCodeJayezeh1(String ccKalaCodeJayezeh1){
        this.ccKalaCodeJayezeh1 = ccKalaCodeJayezeh1;
    }
    public String getCcKalaCodeJayezeh1(){
        return this.ccKalaCodeJayezeh1;
    }


    private String FromDate;
    public void setFromDate(String FromDate){
        this.FromDate = FromDate;
    }
    public String getFromDate(){
        return this.FromDate;
    }


    private String EndDate;
    public void setEndDate(String EndDate){
        this.EndDate = EndDate;
    }
    public String getEndDate(){
        return this.EndDate;
    }


    private String SharhJayezeh;
    public void setSharhJayezeh(String SharhJayezeh){
        this.SharhJayezeh = SharhJayezeh;
    }
    public String getSharhJayezeh(){
        return this.SharhJayezeh;
    }


    private int CodeNoeHaml;
    public void setCodeNoeHaml(int CodeNoeHaml){
        this.CodeNoeHaml = CodeNoeHaml;
    }
    public int getCodeNoeHaml(){
        return this.CodeNoeHaml;
    }


    private int ccNoeFieldMoshtary;
    public void setCcNoeFieldMoshtary(int ccNoeFieldMoshtary){
        this.ccNoeFieldMoshtary = ccNoeFieldMoshtary;
    }
    public int getCcNoeFieldMoshtary(){
        return this.ccNoeFieldMoshtary;
    }


    private float SahmHesab;
    public void setSahmHesab(float SahmHesab){
        this.SahmHesab = SahmHesab;
    }
    public float getSahmHesab(){
        return this.SahmHesab;
    }


    private int Id;
    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }


    /*private String JayezehSatrKalas;
    public String getJayezehSatrKalas() {
        return JayezehSatrKalas;
    }
    public void setJayezehSatrKalas(String jayezehSatrKalas) {
        JayezehSatrKalas = jayezehSatrKalas;
    }*/



    @NonNull
    @Override
    public String toString()
    {
        return  "CodeNoeTakhfif=" + CodeNoeTakhfif +
                ", ccJayezeh=" + ccJayezeh +
                ", ccJayezehSatr=" + ccJayezehSatr +
                ", NameNoeField=" + NameNoeField +
                ", txtNameNoeField='" + txtNameNoeField + '\'' +
                ", ccNoeField=" + ccNoeField +
                ", ccKalaCode='" + ccKalaCode + '\'' +
                ", NameKala='" + NameKala + '\'' +
                ", ccBrand='" + ccBrand + '\'' +
                ", NameBrand='" + NameBrand + '\'' +
                ", ccGoroh=" + ccGoroh +
                ", NameGoroh='" + NameGoroh + '\'' +
                ", ccTaminKonandeh='" + ccTaminKonandeh + '\'' +
                ", NameTaminKonandeh='" + NameTaminKonandeh + '\'' +
                ", Az=" + Az +
                ", Ta=" + Ta +
                ", CodeNoeBastehBandy=" + CodeNoeBastehBandy +
                ", txtCodeNoeBastehBandy='" + txtCodeNoeBastehBandy + '\'' +
                ", BeEza=" + BeEza +
                ", CodeNoeBastehBandyBeEza=" + CodeNoeBastehBandyBeEza +
                ", txtCodeNoeBastehBandyBeEza='" + txtCodeNoeBastehBandyBeEza + '\'' +
                ", TedadJayezeh=" + TedadJayezeh +
                ", RialJayezeh=" + RialJayezeh +
                ", ccKalaJayezeh=" + ccKalaJayezeh +
                ", NameKalaJayezeh='" + NameKalaJayezeh + '\'' +
                ", ccKalaJayezehMazad=" + ccKalaJayezehMazad +
                ", NameKalaJayezehMazad='" + NameKalaJayezehMazad + '\'' +
                ", NoeRialJayezeh=" + NoeRialJayezeh +
                ", txtNoeRialJayezeh='" + txtNoeRialJayezeh + '\'' +
                ", MohasebehAzMazad=" + MohasebehAzMazad +
                ", txtMohasebehAzMazad='" + txtMohasebehAzMazad + '\'' +
                ", ccKalaCodeJayezehMazad=" + ccKalaCodeJayezehMazad +
                ", Naghdy=" + Naghdy +
                ", ccKalaCodeJayezeh=" + ccKalaCodeJayezeh +
                ", ccKalaCodeJayezeh1='" + ccKalaCodeJayezeh1 + '\'' +
                ", FromDate='" + FromDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", SharhJayezeh='" + SharhJayezeh + '\'' +
                ", CodeNoeHaml=" + CodeNoeHaml +
                ", ccNoeFieldMoshtary=" + ccNoeFieldMoshtary +
                ", SahmHesab=" + SahmHesab +
                ", Id=" + Id;
    }



}
