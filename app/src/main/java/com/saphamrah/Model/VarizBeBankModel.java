package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VarizBeBankModel {

    public static final String TABLE_NAME = "VarizBeBank";


    @SerializedName("ccDariaftPardakht")
    @Expose
    private int ccDariaftPardakht;
    @SerializedName("NameMoshtary")
    @Expose
    private String namemoshtary;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("Mablagh")
    @Expose
    private long Mablagh;
    @SerializedName("CodeNoeVosol")
    @Expose
    private int CodeNoeVosol;
    @SerializedName("ExtraProp_IsSend")
    @Expose
    private int ExtraProp_IsSend;
    @SerializedName("ExtraProp_IsSelected")
    @Expose
    private int ExtraProp_IsSelected ;
    @SerializedName("Taeed")
    @Expose
    private int Taeed;
    @SerializedName("ExtraProp_ccBankSanad")
    @Expose
    private int ExtraProp_ccBankSanad ;
    @SerializedName("ExtraProp_NameShobehSanad")
    @Expose
    private String ExtraProp_NameShobehSanad ;
    @SerializedName("ExtraProp_CodeShobehSand")
    @Expose
    private String ExtraProp_CodeShobehSand ;
    @SerializedName("ExtraProp_ShomareHesabSanad")
    @Expose
    private String ExtraProp_ShomareHesabSanad ;
    @SerializedName("ExtraProp_ccShomareHesab")
    @Expose
    private int ExtraProp_ccShomareHesab ;
    @SerializedName("ExtraProp_ShomarehSanad")
    @Expose
    private String ExtraProp_ShomarehSanad ;
    @SerializedName("ExtraProp_TarikhSanad")
    @Expose
    private String ExtraProp_TarikhSanad ;
    @SerializedName("ExtraProp_NameBankSanad")
    @Expose
    private String ExtraProp_NameBankSanad ;
    @SerializedName("txtCodeNoeVosol")
    @Expose
    private String txtCodeNoeVosol ;


    public String getExtraProp_NameBankSanad() {
        return ExtraProp_NameBankSanad;
    }

    public void setExtraProp_NameBankSanad(String extraProp_NameBankSanad) {
        ExtraProp_NameBankSanad = extraProp_NameBankSanad;
    }

    public int getCcDariaftPardakht() {
        return ccDariaftPardakht;
    }

    public void setCcDariaftPardakht(int ccDariaftPardakht) {
        this.ccDariaftPardakht = ccDariaftPardakht;
    }

    public String getNamemoshtary() {
        return namemoshtary;
    }

    public void setNamemoshtary(String namemoshtary) {
        this.namemoshtary = namemoshtary;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public long getMablagh() {
        return Mablagh;
    }

    public void setMablagh(long mablagh) {
        Mablagh = mablagh;
    }

    public int getCodeNoeVosol() {
        return CodeNoeVosol;
    }

    public void setCodeNoeVosol(int codeNoeVosol) {
        CodeNoeVosol = codeNoeVosol;
    }

    public int getExtraProp_IsSend() {
        return ExtraProp_IsSend;
    }

    public void setExtraProp_IsSend(int extraProp_IsSend) {
        ExtraProp_IsSend = extraProp_IsSend;
    }

    public int getExtraProp_IsSelected() {
        return ExtraProp_IsSelected;
    }

    public void setExtraProp_IsSelected(int extraProp_IsSelected) {
        ExtraProp_IsSelected = extraProp_IsSelected;
    }

    public int getTaeed() {
        return Taeed;
    }

    public void setTaeed(int taeed) {
        Taeed = taeed;
    }

    public int getExtraProp_ccBankSanad() {
        return ExtraProp_ccBankSanad;
    }

    public void setExtraProp_ccBankSanad(int extraProp_ccBankSanad) {
        ExtraProp_ccBankSanad = extraProp_ccBankSanad;
    }

    public String getExtraProp_NameShobehSanad() {
        return ExtraProp_NameShobehSanad;
    }

    public void setExtraProp_NameShobehSanad(String extraProp_NameShobehSanad) {
        ExtraProp_NameShobehSanad = extraProp_NameShobehSanad;
    }

    public String getExtraProp_CodeShobehSand() {
        return ExtraProp_CodeShobehSand;
    }

    public void setExtraProp_CodeShobehSand(String extraProp_CodeShobehSand) {
        ExtraProp_CodeShobehSand = extraProp_CodeShobehSand;
    }

    public String getExtraProp_ShomareHesabSanad() {
        return ExtraProp_ShomareHesabSanad;
    }

    public void setExtraProp_ShomareHesabSanad(String extraProp_ShomareHesabSanad) {
        ExtraProp_ShomareHesabSanad = extraProp_ShomareHesabSanad;
    }

    public int getExtraProp_ccShomareHesab() {
        return ExtraProp_ccShomareHesab;
    }

    public void setExtraProp_ccShomareHesab(int extraProp_ccShomareHesab) {
        ExtraProp_ccShomareHesab = extraProp_ccShomareHesab;
    }

    public String getExtraProp_ShomarehSanad() {
        return ExtraProp_ShomarehSanad;
    }

    public void setExtraProp_ShomarehSanad(String extraProp_ShomarehSanad) {
        ExtraProp_ShomarehSanad = extraProp_ShomarehSanad;
    }

    public String getExtraProp_TarikhSanad() {
        return ExtraProp_TarikhSanad;
    }

    public void setExtraProp_TarikhSanad(String extraProp_TarikhSanad) {
        ExtraProp_TarikhSanad = extraProp_TarikhSanad;
    }

    public String getTxtCodeNoeVosol() {
        return txtCodeNoeVosol;
    }

    public void setTxtCodeNoeVosol(String txtCodeNoeVosol) {
        this.txtCodeNoeVosol = txtCodeNoeVosol;
    }

    public String getCOLUMN_ccDariaftPardakht() {
        return "ccDariaftPardakht";
    }

    public String getCOLUMN_namemoshtary() {
        return "NameMoshtary";
    }

    public String getCOLUMN_ccmoshtary() {
        return "ccMoshtary";
    }

    public String getCOLUMN_Mablagh() {
        return "Mablagh";
    }
    public String getCOLUMN_CodeNoeVosol() {
        return "CodeNoeVosol";
    }

    public String getCOLUMN_ExtraProp_IsSend() {
        return "ExtraProp_IsSend";
    }
    public String getCOLUMN_ExtraProp_IsSelected() {
        return "ExtraProp_IsSelected";
    }
    public String getCOLUMN_Taeed() {
        return "Taeed";
    }
    public String getCOLUMN_ExtraProp_ccBankSanad() {
        return "ExtraProp_ccBankSanad";
    }
    public String getCOLUMN_ExtraProp_NameShobehSanad() {
        return "ExtraProp_NameShobehSanad";
    }
    public String getCOLUMN_ExtraProp_CodeShobehSand() {
        return "ExtraProp_CodeShobehSand";
    }
    public String getCOLUMN_ExtraProp_ShomareHesabSanad() {
        return "ExtraProp_ShomareHesabSanad";
    }
    public String getCOLUMN_ExtraProp_ccShomareHesab() {
        return "ExtraProp_ccShomareHesab";
    }
    public String getCOLUMN_ExtraProp_ShomarehSanad() {
        return "ExtraProp_ShomarehSanad";
    }
    public String getCOLUMN_ExtraProp_TarikhSanad() {
        return "ExtraProp_TarikhSanad";
    }
    public String getCOLUMN_ExtraProp_NameBankSanad() {
        return "ExtraProp_NameBankSanad";
    }
    public String getCOLUMN_txtCodeNoeVosol() {
        return "txtCodeNoeVosol";
    }



    /// for recycler selected
    private int isSelectedRecycler ;

    public int getIsSelectedRecycler() {
        return isSelectedRecycler;
    }

    public void setIsSelectedRecycler(int isSelectedRecycler) {
        this.isSelectedRecycler = isSelectedRecycler;
    }


    // for convert string json
    public JSONObject toJsonForVarizBeBank()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {

            jsonObject.put("ccDariaftPardakht" , ccDariaftPardakht);
            jsonObject.put("NameMoshtary" , namemoshtary);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("Mablagh" , Mablagh);
            jsonObject.put("ccBankSanad" , ExtraProp_ccBankSanad);
            jsonObject.put("NameShobehSanad" , ExtraProp_NameShobehSanad);
            jsonObject.put("CodeShobehSand" , ExtraProp_CodeShobehSand);
            jsonObject.put("ccShomareHesab" , ExtraProp_ccShomareHesab);
            jsonObject.put("ShomarehSanad" , ExtraProp_ShomarehSanad);
            jsonObject.put("TarikhSanad" , ExtraProp_TarikhSanad);
            jsonObject.put("CodeNoeVosol" , CodeNoeVosol);
             }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONArray toJsonArrayVariz(ArrayList<VarizBeBankModel> models)
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            for (VarizBeBankModel model : models)
            {
                JSONObject jsonObject = model.toJsonForVarizBeBank();
                if (jsonObject != null)
                {
                    jsonArray.put(jsonObject);
                }
            }
            return jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONArray();
        }
    }

}
