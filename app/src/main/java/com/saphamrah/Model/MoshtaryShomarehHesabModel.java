package com.saphamrah.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class MoshtaryShomarehHesabModel implements Parcelable
{

    private static final String TABLE_NAME = "MoshtaryShomarehHesab";
    private static final String COLUMN_ccMoshtaryShomarehHesab = "ccMoshtaryShomarehHesab";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_CodeMoshtary = "CodeMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_ccBank = "ccBank";
    private static final String COLUMN_NameBank = "NameBank";
    private static final String COLUMN_ccNoeHesab = "ccNoeHesab";
    private static final String COLUMN_NameNoeHesab = "NameNoeHesab";
    private static final String COLUMN_ccShomarehHesab = "ccShomarehHesab";
    private static final String COLUMN_ShomarehHesab = "ShomarehHesab";
    private static final String COLUMN_NameShobeh = "NameShobeh";
    private static final String COLUMN_CodeShobeh = "CodeShobeh";
    private static final String COLUMN_ShartBardashtAzHesab = "ShartBardashtAzHesab";
    private static final String COLUMN_SahebanHesab = "SahebanHesab";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryShomarehHesab() {
        return COLUMN_ccMoshtaryShomarehHesab;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_CodeMoshtary() {
        return COLUMN_CodeMoshtary;
    }
    public static String COLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_ccBank() {
        return COLUMN_ccBank;
    }
    public static String COLUMN_NameBank() {
        return COLUMN_NameBank;
    }
    public static String COLUMN_ccNoeHesab() {
        return COLUMN_ccNoeHesab;
    }
    public static String COLUMN_NameNoeHesab() {
        return COLUMN_NameNoeHesab;
    }
    public static String COLUMN_ccShomarehHesab() {
        return COLUMN_ccShomarehHesab;
    }
    public static String COLUMN_ShomarehHesab() {
        return COLUMN_ShomarehHesab;
    }
    public static String COLUMN_NameShobeh() {
        return COLUMN_NameShobeh;
    }
    public static String COLUMN_CodeShobeh() {
        return COLUMN_CodeShobeh;
    }
    public static String COLUMN_ShartBardashtAzHesab() {
        return COLUMN_ShartBardashtAzHesab;
    }
    public static String COLUMN_SahebanHesab() {
        return COLUMN_SahebanHesab;
    }


    private int ccMoshtaryShomarehHesab;

    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("NameMoshtary")
    @Expose
    private String NameMoshtary;
    @SerializedName("NameTablo")
    @Expose
    private String NameTablo;
    @SerializedName("CodeEghtesady")
    @Expose
    private String CodeEghtesady;
    @SerializedName("ccShomarehHesab")
    @Expose
    private int ccShomarehHesab;
    @SerializedName("ShomarehHesab")
    @Expose
    private String ShomarehHesab;
    @SerializedName("ccNoeHesab")
    @Expose
    private int ccNoeHesab;
    @SerializedName("Faal")
    @Expose
    private boolean Faal;
    @SerializedName("NameNoeHesab")
    @Expose
    private String NameNoeHesab;
    @SerializedName("NameBank")
    @Expose
    private String NameBank;
    @SerializedName("ccBank")
    @Expose
    private int ccBank;
    @SerializedName("NameShobeh")
    @Expose
    private String NameShobeh;
    @SerializedName("CodeShobeh")
    @Expose
    private String CodeShobeh;
    @SerializedName("Jary")
    @Expose
    private boolean Jary;
    @SerializedName("CodeMoshtary")
    @Expose
    private String CodeMoshtary;
    @SerializedName("ccShahr")
    @Expose
    private String ccShahr;
    @SerializedName("NameShahr")
    @Expose
    private String NameShahr;
    @SerializedName("ccShahrestan")
    @Expose
    private String ccShahrestan;
    @SerializedName("NameShahrestan")
    @Expose
    private String NameShahrestan;
    @SerializedName("ccOstan")
    @Expose
    private String ccOstan;
    @SerializedName("CodeNoecheck")
    @Expose
    private int CodeNoecheck;
    @SerializedName("txtCodeNoeCheck")
    @Expose
    private String txtCodeNoeCheck;
    @SerializedName("SahebanHesab")
    @Expose
    private String SahebanHesab;
    @SerializedName("ShartBardashtAzHesab")
    @Expose
    private int ShartBardashtAzHesab;
    @SerializedName("ccMoshtary_Link")
    @Expose
    private String ccMoshtary_Link;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;
    @SerializedName("NoeVorod")
    @Expose
    private String NoeVorod;
    @SerializedName("CodeVazeiatHesab")
    @Expose
    private boolean CodeVazeiatHesab;
    @SerializedName("TarikhSabtHesab")
    @Expose
    private String TarikhSabtHesab;


    public int getCcMoshtaryShomarehHesab() {
        return ccMoshtaryShomarehHesab;
    }

    public void setCcMoshtaryShomarehHesab(int ccMoshtaryShomarehHesab) {
        this.ccMoshtaryShomarehHesab = ccMoshtaryShomarehHesab;
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

    public void setNameTablo(String NameTablo){
        this.NameTablo = NameTablo;
    }

    public String getNameTablo(){
        return this.NameTablo;
    }

    public void setCodeEghtesady(String CodeEghtesady){
        this.CodeEghtesady = CodeEghtesady;
    }

    public String getCodeEghtesady(){
        return this.CodeEghtesady;
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

    public void setCcNoeHesab(int ccNoeHesab){
        this.ccNoeHesab = ccNoeHesab;
    }

    public int getCcNoeHesab(){
        return this.ccNoeHesab;
    }

    public void setFaal(boolean Faal){
        this.Faal = Faal;
    }

    public boolean getFaal(){
        return this.Faal;
    }

    public void setNameNoeHesab(String NameNoeHesab){
        this.NameNoeHesab = NameNoeHesab;
    }

    public String getNameNoeHesab(){
        return this.NameNoeHesab;
    }

    public void setNameBank(String NameBank){
        this.NameBank = NameBank;
    }

    public String getNameBank(){
        return this.NameBank;
    }

    public void setCcBank(int ccBank){
        this.ccBank = ccBank;
    }

    public int getCcBank(){
        return this.ccBank;
    }

    public void setNameShobeh(String NameShobeh){
        this.NameShobeh = NameShobeh;
    }

    public String getNameShobeh(){
        return this.NameShobeh;
    }

    public void setCodeShobeh(String CodeShobeh){
        this.CodeShobeh = CodeShobeh;
    }

    public String getCodeShobeh(){
        return this.CodeShobeh;
    }

    public void setJary(boolean Jary){
        this.Jary = Jary;
    }

    public boolean getJary(){
        return this.Jary;
    }

    public void setCodeMoshtary(String CodeMoshtary){
        this.CodeMoshtary = CodeMoshtary;
    }

    public String getCodeMoshtary(){
        return this.CodeMoshtary;
    }

    public void setCcShahr(String ccShahr){
        this.ccShahr = ccShahr;
    }

    public String getCcShahr(){
        return this.ccShahr;
    }

    public void setNameShahr(String NameShahr){
        this.NameShahr = NameShahr;
    }

    public String getNameShahr(){
        return this.NameShahr;
    }

    public void setCcShahrestan(String ccShahrestan){
        this.ccShahrestan = ccShahrestan;
    }

    public String getCcShahrestan(){
        return this.ccShahrestan;
    }

    public void setNameShahrestan(String NameShahrestan){
        this.NameShahrestan = NameShahrestan;
    }

    public String getNameShahrestan(){
        return this.NameShahrestan;
    }

    public void setCcOstan(String ccOstan){
        this.ccOstan = ccOstan;
    }

    public String getCcOstan(){
        return this.ccOstan;
    }

    public void setCodeNoecheck(int CodeNoecheck){
        this.CodeNoecheck = CodeNoecheck;
    }

    public int getCodeNoecheck(){
        return this.CodeNoecheck;
    }

    public void setTxtCodeNoeCheck(String txtCodeNoeCheck){
        this.txtCodeNoeCheck = txtCodeNoeCheck;
    }

    public String getTxtCodeNoeCheck(){
        return this.txtCodeNoeCheck;
    }

    public void setSahebanHesab(String SahebanHesab){
        this.SahebanHesab = SahebanHesab;
    }

    public String getSahebanHesab(){
        return this.SahebanHesab;
    }

    public void setShartBardashtAzHesab(int ShartBardashtAzHesab){
        this.ShartBardashtAzHesab = ShartBardashtAzHesab;
    }

    public int getShartBardashtAzHesab(){
        return this.ShartBardashtAzHesab;
    }

    public void setCcMoshtary_Link(String ccMoshtary_Link){
        this.ccMoshtary_Link = ccMoshtary_Link;
    }

    public String getCcMoshtary_Link(){
        return this.ccMoshtary_Link;
    }

    public void setCodeVazeiat(int CodeVazeiat){
        this.CodeVazeiat = CodeVazeiat;
    }

    public int getCodeVazeiat(){
        return this.CodeVazeiat;
    }

    public void setNoeVorod(String NoeVorod){
        this.NoeVorod = NoeVorod;
    }

    public String getNoeVorod(){
        return this.NoeVorod;
    }

    public void setCodeVazeiatHesab(boolean CodeVazeiatHesab){
        this.CodeVazeiatHesab = CodeVazeiatHesab;
    }

    public boolean getCodeVazeiatHesab(){
        return this.CodeVazeiatHesab;
    }

    public void setTarikhSabtHesab(String TarikhSabtHesab){
        this.TarikhSabtHesab = TarikhSabtHesab;
    }

    public String getTarikhSabtHesab(){
        return this.TarikhSabtHesab;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(ccMoshtaryShomarehHesab);
        dest.writeInt(ccMoshtary);
        dest.writeString(NameMoshtary);
        dest.writeString(NameTablo);
        dest.writeString(CodeEghtesady);
        dest.writeInt(ccShomarehHesab);
        dest.writeString(ShomarehHesab);
        dest.writeInt(ccNoeHesab);
        //dest.writeByte((byte) (Faal ? 1 : 0)); // for read must use -> Faal = in.readByte() != 0;
        dest.writeValue(Faal); // for read must convert to boolean;
        dest.writeString(NameNoeHesab);
        dest.writeString(NameBank);
        dest.writeInt(ccBank);
        dest.writeString(NameShobeh);
        dest.writeString(CodeShobeh);
        //dest.writeByte((byte) (Jary ? 1 : 0)); // for read must use -> Jary = in.readByte() != 0;
        dest.writeValue(Jary); // for read must convert to boolean;
        dest.writeString(CodeMoshtary);
        dest.writeString(ccShahr);
        dest.writeString(NameShahr);
        dest.writeString(ccShahrestan);
        dest.writeString(NameShahrestan);
        dest.writeString(ccOstan);
        dest.writeInt(CodeNoecheck);
        dest.writeString(txtCodeNoeCheck);
        dest.writeString(SahebanHesab);
        dest.writeInt(ShartBardashtAzHesab);
        dest.writeString(ccMoshtary_Link);
        dest.writeInt(CodeVazeiat);
        dest.writeString(NoeVorod);
        //dest.writeByte((byte) (CodeVazeiatHesab ? 1 : 0)); // for read must use -> CodeVazeiatHesab = in.readByte() != 0;
        dest.writeValue(CodeVazeiatHesab); // for read must convert to boolean
        dest.writeString(TarikhSabtHesab);
    }



    public String toJsonString()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccShomarehHesab() , ccShomarehHesab);
        jsonObject.addProperty(COLUMN_ShomarehHesab() , ShomarehHesab);
        jsonObject.addProperty(COLUMN_ccBank() , ccBank);
        jsonObject.addProperty(COLUMN_CodeShobeh() , CodeShobeh);
        jsonObject.addProperty(COLUMN_NameShobeh() , NameShobeh);
        jsonObject.addProperty(COLUMN_ccNoeHesab() , ccNoeHesab);
        jsonObject.addProperty("Faal" , "true");
        jsonObject.addProperty("CodeMaly" , "");
        jsonObject.addProperty("CodeHesab4" , "");
        jsonObject.addProperty("NoeVorod" , "");
        return jsonObject.toString();
    }



    public JSONObject toJsonObjectShomarehHesab()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ShomarehHesab" , ShomarehHesab);
            jsonObject.put("ccBankShobeh" , ccBank);
            jsonObject.put("ccNoeHesab" , ccNoeHesab);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject toJsonObjectMoshtaryShomarehHesab()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccBank" , ccBank);
            jsonObject.put("NameShobeh" , NameShobeh);
            jsonObject.put("CodeShobeh" , CodeShobeh);
            jsonObject.put("CodeNoecheck" , CodeNoecheck);
            jsonObject.put("SahebanHesab" , SahebanHesab);
            jsonObject.put("ShartBardashtAzHesab" , ShartBardashtAzHesab);
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
        return "MoshtaryShomarehHesabModel{" +
                "ccMoshtaryShomarehHesab=" + ccMoshtaryShomarehHesab +
                ", ccMoshtary=" + ccMoshtary +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", NameTablo='" + NameTablo + '\'' +
                ", CodeEghtesady='" + CodeEghtesady + '\'' +
                ", ccShomarehHesab=" + ccShomarehHesab +
                ", ShomarehHesab='" + ShomarehHesab + '\'' +
                ", ccNoeHesab=" + ccNoeHesab +
                ", Faal=" + Faal +
                ", NameNoeHesab='" + NameNoeHesab + '\'' +
                ", NameBank='" + NameBank + '\'' +
                ", ccBank=" + ccBank +
                ", NameShobeh='" + NameShobeh + '\'' +
                ", CodeShobeh='" + CodeShobeh + '\'' +
                ", Jary=" + Jary +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                ", ccShahr='" + ccShahr + '\'' +
                ", NameShahr='" + NameShahr + '\'' +
                ", ccShahrestan='" + ccShahrestan + '\'' +
                ", NameShahrestan='" + NameShahrestan + '\'' +
                ", ccOstan='" + ccOstan + '\'' +
                ", CodeNoecheck=" + CodeNoecheck +
                ", txtCodeNoeCheck='" + txtCodeNoeCheck + '\'' +
                ", SahebanHesab='" + SahebanHesab + '\'' +
                ", ShartBardashtAzHesab=" + ShartBardashtAzHesab +
                ", ccMoshtary_Link='" + ccMoshtary_Link + '\'' +
                ", CodeVazeiat=" + CodeVazeiat +
                ", NoeVorod='" + NoeVorod + '\'' +
                ", CodeVazeiatHesab=" + CodeVazeiatHesab +
                ", TarikhSabtHesab='" + TarikhSabtHesab + '\'' +
                '}';
    }
}
