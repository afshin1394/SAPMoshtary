package com.saphamrah.Model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ElatAdamTahvilDarkhastModel {

   public final String TABLE_NAME = "ElatAdamTahvilDarkhast";

    public String COLUMN_ccElatAdamTahvilDarkhast() {
        return "ccElatAdamTahvilDarkhast";
    }
    public String COLUMN_CodeNoeVorod() {
        return "CodeNoeVorod";
    }
    public String COLUMN_NameNoeVorod() {
        return "NameNoeVorod";
    }

    private int ccElatAdamTahvilDarkhast;
    private int CodeNoeVorod;
    private String NameNoeVorod;

    public int getCcElatAdamTahvilDarkhast() {
        return ccElatAdamTahvilDarkhast;
    }

    public void setCcElatAdamTahvilDarkhast(int ccElatAdamTahvilDarkhast) {
        this.ccElatAdamTahvilDarkhast = ccElatAdamTahvilDarkhast;
    }

    public int getCodeNoeVorod() {
        return CodeNoeVorod;
    }

    public void setCodeNoeVorod(int codeNoeVorod) {
        CodeNoeVorod = codeNoeVorod;
    }

    public String getNameNoeVorod() {
        return NameNoeVorod;
    }

    public void setNameNoeVorod(String nameNoeVorod) {
        NameNoeVorod = nameNoeVorod;
    }

    public JSONObject toJsonArrayForSend(ElatAdamTahvilDarkhastModel model , DarkhastFaktorModel darkhastFaktorModel){
        JSONObject finalSend = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("CodeNoeVorod" , model.getCodeNoeVorod());
            jsonObject.put("ccDarkhastFaktor" , darkhastFaktorModel.getCcDarkhastFaktor());
            jsonObject.put("UniqID_Tablet" , darkhastFaktorModel.getUniqID_Tablet());
            jsonObject.put("CodeVazeiat" , darkhastFaktorModel.getCodeVazeiat());
            jsonArray.put(jsonObject);
            finalSend.put("DarkhastAdamTahvil" , jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return finalSend;
    }


}
