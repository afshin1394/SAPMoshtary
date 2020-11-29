package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class MarkazShomarehHesabModel
{

    private static final String TABLE_NAME = "MarkazShomarehHesab";
    private static final String COLUMN_ccMarkaz = "ccMarkaz";
    private static final String COLUMN_ccShomarehHesab = "ccShomarehHesab";
    private static final String COLUMN_ShomarehHesab = "ShomarehHesab";
    private static final String COLUMN_ccBank = "ccBank";
    private static final String COLUMN_NameBank = "NameBank";
    private static final String COLUMN_CodeShobeh = "CodeShobeh";
    private static final String COLUMN_NameShobeh = "NameShobeh";
    private static final String COLUMN_ccNoeHesab = "ccNoeHesab";
    private static final String COLUMN_NameNoeHesab = "NameNoeHesab";
    private static final String COLUMN_ShomarehSheba = "ShomarehSheba";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMarkaz() {
        return COLUMN_ccMarkaz;
    }
    public static String COLUMN_ccShomarehHesab() {
        return COLUMN_ccShomarehHesab;
    }
    public static String COLUMN_ShomarehHesab() {
        return COLUMN_ShomarehHesab;
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
    public static String COLUMN_ccNoeHesab() {
        return COLUMN_ccNoeHesab;
    }
    public static String COLUMN_NameNoeHesab() {
        return COLUMN_NameNoeHesab;
    }
    public static String COLUMN_ShomarehSheba() {
        return COLUMN_ShomarehSheba;
    }




    @SerializedName("ccMarkaz")
    @Expose
    private int ccMarkaz;
    @SerializedName("ccShomarehHesab")
    @Expose
    private int ccShomarehHesab;
    @SerializedName("ShomarehHesab")
    @Expose
    private String ShomarehHesab;
    @SerializedName("ccBankShobeh")
    @Expose
    private int ccBankShobeh;
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
    @SerializedName("ccNoeHesab")
    @Expose
    private int ccNoeHesab;
    @SerializedName("NameNoeHesab")
    @Expose
    private String NameNoeHesab;
    @SerializedName("Faal")
    @Expose
    private boolean Faal;
    @SerializedName("CodeArzyRialy")
    @Expose
    private boolean CodeArzyRialy;
    @SerializedName("txtCodeArzyRialy")
    @Expose
    private String txtCodeArzyRialy;
    @SerializedName("TeleConnection")
    @Expose
    private String TeleConnection;
    @SerializedName("UserName")
    @Expose
    private String UserName;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("Jary")
    @Expose
    private boolean Jary;
    @SerializedName("NameMarkaz")
    @Expose
    private String NameMarkaz;
    @SerializedName("CodeMaly")
    @Expose
    private String CodeMaly;
    @SerializedName("CodeHesab4")
    @Expose
    private String CodeHesab4;
    @SerializedName("ccLink")
    @Expose
    private int ccLink;
    @SerializedName("CodeNoeVorod")
    @Expose
    private int CodeNoeVorod;

    private String ShomarehSheba;

    public void setCcMarkaz(int ccMarkaz){
        this.ccMarkaz = ccMarkaz;
    }
    public int getCcMarkaz(){
        return this.ccMarkaz;
    }
    public void setCcShomarehHesab(int ccShomarehHesab){
        this.ccShomarehHesab = ccShomarehHesab;
    }
    public int getCcShomarehHesab(){
        return this.ccShomarehHesab;
    }
    public void setShomarehHesab(String ShomarehHesab){
        this.ShomarehHesab = ShomarehHesab;
    }
    public String getShomarehHesab(){
        return this.ShomarehHesab;
    }
    public void setCcBankShobeh(int ccBankShobeh){
        this.ccBankShobeh = ccBankShobeh;
    }
    public int getCcBankShobeh(){
        return this.ccBankShobeh;
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
    public void setCcNoeHesab(int ccNoeHesab){
        this.ccNoeHesab = ccNoeHesab;
    }
    public int getCcNoeHesab(){
        return this.ccNoeHesab;
    }
    public void setNameNoeHesab(String NameNoeHesab){
        this.NameNoeHesab = NameNoeHesab;
    }
    public String getNameNoeHesab(){
        return this.NameNoeHesab;
    }
    public void setFaal(boolean Faal){
        this.Faal = Faal;
    }
    public boolean getFaal(){
        return this.Faal;
    }
    public void setCodeArzyRialy(boolean CodeArzyRialy){
        this.CodeArzyRialy = CodeArzyRialy;
    }
    public boolean getCodeArzyRialy(){
        return this.CodeArzyRialy;
    }
    public void setTxtCodeArzyRialy(String txtCodeArzyRialy){
        this.txtCodeArzyRialy = txtCodeArzyRialy;
    }
    public String getTxtCodeArzyRialy(){
        return this.txtCodeArzyRialy;
    }
    public void setTeleConnection(String TeleConnection){
        this.TeleConnection = TeleConnection;
    }
    public String getTeleConnection(){
        return this.TeleConnection;
    }
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    public String getUserName(){
        return this.UserName;
    }
    public void setPassword(String Password){
        this.Password = Password;
    }
    public String getPassword(){
        return this.Password;
    }
    public void setJary(boolean Jary){
        this.Jary = Jary;
    }
    public boolean getJary(){
        return this.Jary;
    }
    public void setNameMarkaz(String NameMarkaz){
        this.NameMarkaz = NameMarkaz;
    }
    public String getNameMarkaz(){
        return this.NameMarkaz;
    }
    public void setCodeMaly(String CodeMaly){
        this.CodeMaly = CodeMaly;
    }
    public String getCodeMaly(){
        return this.CodeMaly;
    }
    public void setCodeHesab4(String CodeHesab4){
        this.CodeHesab4 = CodeHesab4;
    }
    public String getCodeHesab4(){
        return this.CodeHesab4;
    }
    public void setCcLink(int ccLink){
        this.ccLink = ccLink;
    }
    public int getCcLink(){
        return this.ccLink;
    }
    public void setCodeNoeVorod(int CodeNoeVorod){
        this.CodeNoeVorod = CodeNoeVorod;
    }
    public int getCodeNoeVorod(){
        return this.CodeNoeVorod;
    }
    public void setShomarehSheba(String ShomarehSheba){
        this.ShomarehSheba = ShomarehSheba;
    }
    public String getShomarehSheba(){
        return this.ShomarehSheba;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "MarkazShomarehHesabModel{" +
                "ccMarkaz=" + ccMarkaz +
                ", ccShomarehHesab=" + ccShomarehHesab +
                ", ShomarehHesab='" + ShomarehHesab + '\'' +
                ", ccBankShobeh=" + ccBankShobeh +
                ", ccBank=" + ccBank +
                ", NameBank='" + NameBank + '\'' +
                ", CodeShobeh='" + CodeShobeh + '\'' +
                ", NameShobeh='" + NameShobeh + '\'' +
                ", ccNoeHesab=" + ccNoeHesab +
                ", NameNoeHesab='" + NameNoeHesab + '\'' +
                ", Faal=" + Faal +
                ", CodeArzyRialy=" + CodeArzyRialy +
                ", txtCodeArzyRialy='" + txtCodeArzyRialy + '\'' +
                ", TeleConnection='" + TeleConnection + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", Jary=" + Jary +
                ", NameMarkaz='" + NameMarkaz + '\'' +
                ", CodeMaly='" + CodeMaly + '\'' +
                ", CodeHesab4='" + CodeHesab4 + '\'' +
                ", ccLink=" + ccLink +
                ", CodeNoeVorod=" + CodeNoeVorod +
                ", ShomarehSheba='" + ShomarehSheba + '\'' +
                '}';
    }
}
