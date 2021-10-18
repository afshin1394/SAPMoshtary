package com.saphamrah.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class SuggestModel {

    public static final String TABLE_NAME = "Suggest";

    @SerializedName("ccSuggest")
    private int ccSuggest;
    @SerializedName("ccNoePishnehad")
    private int ccNoePishnehad;
    @SerializedName("ccForoshandeh")
    private int ccForoshandeh;
    @SerializedName("ccMoshtary")
    private int ccMoshtary;
    @SerializedName("ccAfrad")
    private int ccAfrad;
    @SerializedName("ccAmargar")
    private int ccAmargar;
    @SerializedName("ccMamorPakhsh")
    private int ccMamorPakhsh;
    @SerializedName("Description")
    private String Description;
    @SerializedName("DescriptionPishnehad")
    private String DescriptionPishnehad;
    @SerializedName("Date")
    private String Date;
    @SerializedName("ExtraProp_IsSend")
    private int ExtraProp_IsSend;

    private String ExtraProp_NameNoePishnehad;


    public String getCOLUMN_ccSuggest() {
        return "ccSuggest";
    }
    public String getCOLUMN_ccNoePishnehad() {
        return "ccNoePishnehad";
    }
    public String getCOLUMN_ccForoshandeh() {
        return "ccForoshandeh";
    }
    public String getCOLUMN_ccMoshtary() {
        return "ccMoshtary";
    }
    public String getCOLUMN_ccAfrad() {
        return "ccAfrad";
    }
    public String getCOLUMN_ccMamorPakhsh() {
        return "ccMamorPakhsh";
    }
    public String getCOLUMN_ccAmargar() {
        return "ccAmargar";
    }
    public String getCOLUMN_Description() {
        return "Description";
    }
    public String getCOLUMN_DescriptionPishnehad() {
        return "DescriptionPishnehad";
    }
    public String getCOLUMN_Date() {
        return "Date";
    }
    public String getCOLUMN_ExtraProp_IsSend() {
        return "ExtraProp_IsSend";
    }
    public String getCOLUMN_ExtraProp_NameNoePishnehad() {
        return "NameNoePishnehad";
    }

    public int getCcSuggest() {
        return ccSuggest;
    }

    public void setCcSuggest(int ccSuggest) {
        this.ccSuggest = ccSuggest;
    }

    public int getCcNoePishnehad() {
        return ccNoePishnehad;
    }

    public void setCcNoePishnehad(int ccNoePishnehad) {
        this.ccNoePishnehad = ccNoePishnehad;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public int getExtraProp_IsSend() {
        return ExtraProp_IsSend;
    }

    public void setExtraProp_IsSend(int extraProp_IsSend) {
        ExtraProp_IsSend = extraProp_IsSend;
    }

    public String getExtraProp_NameNoePishnahad() {
        return ExtraProp_NameNoePishnehad;
    }

    public void setExtraProp_NameNoePishnehad(String extraProp_NameNoePishnehad) {
        ExtraProp_NameNoePishnehad = extraProp_NameNoePishnehad;
    }

    public int getCcAfrad() {
        return ccAfrad;
    }

    public void setCcAfrad(int ccAfrad) {
        this.ccAfrad = ccAfrad;
    }

    public int getCcAmargar() {
        return ccAmargar;
    }

    public void setCcAmargar(int ccAmargar) {
        this.ccAmargar = ccAmargar;
    }

    public int getCcMamorPakhsh() {
        return ccMamorPakhsh;
    }

    public void setCcMamorPakhsh(int ccMamorPakhsh) {
        this.ccMamorPakhsh = ccMamorPakhsh;
    }

    public String getDescriptionPishnehad() {
        return DescriptionPishnehad;
    }

    public void setDescriptionPishnehad(String descriptionPishnehad) {
        DescriptionPishnehad = descriptionPishnehad;
    }

    public String toJsonString()
    {
        try
        {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("ccSuggest" , ccSuggest);
            jsonObject.put("ccNoePishnehad" , ccNoePishnehad);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("ccAfrad" , ccAfrad);
            jsonObject.put("ccAmargar" , ccAmargar);
            jsonObject.put("ccMamorPakhsh" , ccMamorPakhsh);
            jsonObject.put("Description" , Description);
            jsonObject.put("DescriptionPishnahad" , DescriptionPishnehad);
            jsonObject.put("Date" , Date);


            return jsonObject.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
