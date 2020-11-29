package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class MoshtaryAfradModel
{


    private static final String TABLE_NAME = "MoshtaryAfrad";
    private static final String COLUMN_ccMoshtaryAfrad = "ccMoshtaryAfrad";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccAfrad = "ccAfrad";
    private static final String COLUMN_FullNameMoshtaryAfrad = "FullNameMoshtaryAfrad";
    private static final String COLUMN_MojazEmza = "MojazEmza";
    private static final String COLUMN_TarafHesab = "TarafHesab";
    private static final String COLUMN_FName = "FName";
    private static final String COLUMN_LName = "LName";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryAfrad() {
        return COLUMN_ccMoshtaryAfrad;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccAfrad() {
        return COLUMN_ccAfrad;
    }
    public static String COLUMN_FullNameMoshtaryAfrad() {
        return COLUMN_FullNameMoshtaryAfrad;
    }
    public static String COLUMN_MojazEmza() {
        return COLUMN_MojazEmza;
    }
    public static String COLUMN_TarafHesab() {
        return COLUMN_TarafHesab;
    }
    public static String COLUMN_FName() {
        return COLUMN_FName;
    }
    public static String COLUMN_LName() {
        return COLUMN_LName;
    }


    private int ccMoshtaryAfrad;

    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("NameMoshtary")
    @Expose
    private String NameMoshtary;
    @SerializedName("ccAfrad")
    @Expose
    private int ccAfrad;
    @SerializedName("FName")
    @Expose
    private String FName;
    @SerializedName("LName")
    @Expose
    private String LName;
    @SerializedName("FullNameMoshtaryAfrad")
    @Expose
    private String FullNameMoshtaryAfrad;
    @SerializedName("Semat")
    @Expose
    private String Semat;
    @SerializedName("MojazEmza")
    @Expose
    private boolean MojazEmza;
    @SerializedName("CodeJensiat")
    @Expose
    private int CodeJensiat;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("Telephone")
    @Expose
    private String Telephone;
    @SerializedName("Fax")
    @Expose
    private String Fax;
    @SerializedName("Mobile")
    @Expose
    private String Mobile;
    @SerializedName("Dakhely")
    @Expose
    private String Dakhely;
    @SerializedName("TarafHesab")
    @Expose
    private boolean TarafHesab;
    @SerializedName("CodeMely")
    @Expose
    private String CodeMely;
    @SerializedName("CodeMoshtary")
    @Expose
    private String CodeMoshtary;


    public int getCcMoshtaryAfrad() {
        return ccMoshtaryAfrad;
    }

    public void setCcMoshtaryAfrad(int ccMoshtaryAfrad) {
        this.ccMoshtaryAfrad = ccMoshtaryAfrad;
    }

    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcMoshtary(){
        return this.ccMoshtary;
    }

    public void setNameMoshtary(String NameMoshtary){
        this.NameMoshtary = NameMoshtary;
    }

    public String getNameMoshtary(){
        return this.NameMoshtary;
    }

    public void setCcAfrad(int ccAfrad){
        this.ccAfrad = ccAfrad;
    }

    public int getCcAfrad(){
        return this.ccAfrad;
    }

    public void setFName(String FName){
        this.FName = FName;
    }

    public String getFName(){
        return this.FName;
    }

    public void setLName(String LName){
        this.LName = LName;
    }

    public String getLName(){
        return this.LName;
    }

    public void setFullNameMoshtaryAfrad(String FullNameMoshtaryAfrad){
        this.FullNameMoshtaryAfrad = FullNameMoshtaryAfrad;
    }

    public String getFullNameMoshtaryAfrad(){
        return this.FullNameMoshtaryAfrad;
    }

    public void setSemat(String Semat){
        this.Semat = Semat;
    }

    public String getSemat(){
        return this.Semat;
    }

    public void setMojazEmza(boolean MojazEmza){
        this.MojazEmza = MojazEmza;
    }

    public boolean getMojazEmza(){
        return this.MojazEmza;
    }

    public void setCodeJensiat(int CodeJensiat){
        this.CodeJensiat = CodeJensiat;
    }

    public int getCodeJensiat(){
        return this.CodeJensiat;
    }

    public void setEmail(String Email){
        this.Email = Email;
    }

    public String getEmail(){
        return this.Email;
    }

    public void setTitle(String Title){
        this.Title = Title;
    }

    public String getTitle(){
        return this.Title;
    }

    public void setTelephone(String Telephone){
        this.Telephone = Telephone;
    }

    public String getTelephone(){
        return this.Telephone;
    }

    public void setFax(String Fax){
        this.Fax = Fax;
    }

    public String getFax(){
        return this.Fax;
    }

    public void setMobile(String Mobile){
        this.Mobile = Mobile;
    }

    public String getMobile(){
        return this.Mobile;
    }

    public void setDakhely(String Dakhely){
        this.Dakhely = Dakhely;
    }

    public String getDakhely(){
        return this.Dakhely;
    }

    public void setTarafHesab(boolean TarafHesab){
        this.TarafHesab = TarafHesab;
    }

    public boolean getTarafHesab(){
        return this.TarafHesab;
    }

    public void setCodeMely(String CodeMely){
        this.CodeMely = CodeMely;
    }

    public String getCodeMely(){
        return this.CodeMely;
    }

    public void setCodeMoshtary(String CodeMoshtary){
        this.CodeMoshtary = CodeMoshtary;
    }

    public String getCodeMoshtary(){
        return this.CodeMoshtary;
    }



    /**
     *
     * @param customerMobile
     * @return
     */
    public String toJsonString(String customerMobile)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccAfrad , ccAfrad);
        jsonObject.addProperty(COLUMN_FName , FName);
        jsonObject.addProperty(COLUMN_LName , LName);
        jsonObject.addProperty("CodeJensiat" , 1);
        jsonObject.addProperty("Email" , "");
        jsonObject.addProperty("Title" , "");
        jsonObject.addProperty("Telephone" , "");
        jsonObject.addProperty("Fax" , "");
        jsonObject.addProperty("Mobile" , customerMobile);
        jsonObject.addProperty("Dakhely" , "");
        jsonObject.addProperty("ShomarehShenasnameh" , "");
        jsonObject.addProperty("NamePedar" , "");
        jsonObject.addProperty("UserName" , "");
        jsonObject.addProperty("PasswordHash" , "");
        jsonObject.addProperty("PasswordSalt" , "");
        jsonObject.addProperty("EnableLogin" , "false");
        jsonObject.addProperty("SessionID" , "");
        jsonObject.addProperty("SaatLogin" , "");
        jsonObject.addProperty("IsAdmin" , "false");
        jsonObject.addProperty("IsPersonel" , "0");
        return jsonObject.toString();
    }


    public JSONObject toJsonObject(String customerMobile)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("FName" , FName);
            jsonObject.put("LName" , LName);
            jsonObject.put("CodeJensiat" , CodeJensiat);
            jsonObject.put("Mobile" , customerMobile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString() {
        return "MoshtaryAfradModel{" +
                "ccMoshtaryAfrad=" + ccMoshtaryAfrad +
                ", ccMoshtary=" + ccMoshtary +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", ccAfrad=" + ccAfrad +
                ", FName='" + FName + '\'' +
                ", LName='" + LName + '\'' +
                ", FullNameMoshtaryAfrad='" + FullNameMoshtaryAfrad + '\'' +
                ", Semat='" + Semat + '\'' +
                ", MojazEmza=" + MojazEmza +
                ", CodeJensiat=" + CodeJensiat +
                ", Email='" + Email + '\'' +
                ", Title='" + Title + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", Fax='" + Fax + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Dakhely='" + Dakhely + '\'' +
                ", TarafHesab=" + TarafHesab +
                ", CodeMely='" + CodeMely + '\'' +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                '}';
    }


}
