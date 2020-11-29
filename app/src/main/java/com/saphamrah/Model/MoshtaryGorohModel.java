package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;


//TODO delete this class
public class MoshtaryGorohModel
{


    private static final String TABLE_NAME = "MoshtaryGoroh";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccGoroh = "ccGoroh";
    private static final String COLUMN_ccGorohLink = "ccGorohLink";
    private static final String COLUMN_NameGoroh = "NameGoroh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccGoroh() {
        return COLUMN_ccGoroh;
    }
    public static String COLUMN_ccGorohLink() {
        return COLUMN_ccGorohLink;
    }
    public static String COLUMN_NameGoroh() {
        return COLUMN_NameGoroh;
    }



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
    @SerializedName("ccGoroh")
    @Expose
    private int ccGoroh;
    @SerializedName("NameGoroh")
    @Expose
    private String NameGoroh;
    @SerializedName("NameGorohText")
    @Expose
    private String NameGorohText;
    @SerializedName("ccGorohLink")
    @Expose
    private int ccGorohLink;
    @SerializedName("NameGorohLink")
    @Expose
    private String NameGorohLink;
    @SerializedName("ccRoot")
    @Expose
    private int ccRoot;
    @SerializedName("NameRoot")
    @Expose
    private String NameRoot;
    @SerializedName("CodeNoeGoroh")
    @Expose
    private int CodeNoeGoroh;
    @SerializedName("CodeMoshtary")
    @Expose
    private String CodeMoshtary;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;
    @SerializedName("txtCodeVazeiat")
    @Expose
    private String txtCodeVazeiat;




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

    public void setCcGoroh(int ccGoroh){
        this.ccGoroh = ccGoroh;
    }

    public int getCcGoroh(){
        return this.ccGoroh;
    }

    public void setNameGoroh(String NameGoroh){
        this.NameGoroh = NameGoroh;
    }

    public String getNameGoroh(){
        return this.NameGoroh;
    }

    public void setNameGorohText(String NameGorohText){
        this.NameGorohText = NameGorohText;
    }

    public String getNameGorohText(){
        return this.NameGorohText;
    }

    public void setCcGorohLink(int ccGorohLink){
        this.ccGorohLink = ccGorohLink;
    }

    public int getCcGorohLink(){
        return this.ccGorohLink;
    }

    public void setNameGorohLink(String NameGorohLink){
        this.NameGorohLink = NameGorohLink;
    }

    public String getNameGorohLink(){
        return this.NameGorohLink;
    }

    public void setCcRoot(int ccRoot){
        this.ccRoot = ccRoot;
    }

    public int getCcRoot(){
        return this.ccRoot;
    }

    public void setNameRoot(String NameRoot){
        this.NameRoot = NameRoot;
    }

    public String getNameRoot(){
        return this.NameRoot;
    }

    public void setCodeNoeGoroh(int CodeNoeGoroh){
        this.CodeNoeGoroh = CodeNoeGoroh;
    }

    public int getCodeNoeGoroh(){
        return this.CodeNoeGoroh;
    }

    public void setCodeMoshtary(String CodeMoshtary){
        this.CodeMoshtary = CodeMoshtary;
    }

    public String getCodeMoshtary(){
        return this.CodeMoshtary;
    }

    public void setCodeVazeiat(int CodeVazeiat){
        this.CodeVazeiat = CodeVazeiat;
    }

    public int getCodeVazeiat(){
        return this.CodeVazeiat;
    }

    public void setTxtCodeVazeiat(String txtCodeVazeiat){
        this.txtCodeVazeiat = txtCodeVazeiat;
    }

    public String getTxtCodeVazeiat(){
        return this.txtCodeVazeiat;
    }


    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccGoroh" , ccGoroh);
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
        return "ccMoshtary : " + ccMoshtary + " , ccGoroh : " + ccGoroh + " , ccGorohLink : " + ccGorohLink + " , NameGoroh : " + NameGoroh;
    }


}
