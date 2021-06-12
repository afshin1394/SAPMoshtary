package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KardexModel
{

    private static final String TABLE_NAME = "Kardex";
    private static final String COLUMN_ccKardex = "ccKardex";
    private static final String COLUMN_ccMarkazAnbar = "ccMarkazAnbar";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_ccAnbar = "ccAnbar";
    private static final String COLUMN_CodeNoeForm = "CodeNoeForm";
    private static final String COLUMN_CodeNoeAmalyat = "CodeNoeAmalyat";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";
    private static final String COLUMN_CodeNoeAnbar = "CodeNoeAnbar";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_MarjoeeKamel = "MarjoeeKamel";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccAfradMamurPakhsh = "ccAfradMamurPakhsh";
    private static final String COLUMN_ccRefrence = "ccRefrence";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";
    private static final String COLUMN_TarikhForm = "TarikhForm";
    private static final String COLUMN_TarikhFaktor = "TarikhFaktor";
    private static final String COLUMN_SumGheymatMarjoee = "SumGheymatMarjoee";
    private static final String COLUMN_SumTedadMarjoee = "SumTedadMarjoee";
    private static final String COLUMN_ExtraProp_ccElatMarjoeeKala = "ExtraProp_ccElatMarjoeeKala";










    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKardex() {
        return COLUMN_ccKardex;
    }
    public static String COLUMN_ccMarkazAnbar() {
        return COLUMN_ccMarkazAnbar;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_ccAnbar() {
        return COLUMN_ccAnbar;
    }
    public static String COLUMN_CodeNoeForm() {
        return COLUMN_CodeNoeForm;
    }
    public static String COLUMN_CodeNoeAmalyat() {
        return COLUMN_CodeNoeAmalyat;
    }
    public static String COLUMN_CodeVazeiat() {
        return COLUMN_CodeVazeiat;
    }
    public static String COLUMN_CodeNoeAnbar() {
        return COLUMN_CodeNoeAnbar;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_MarjoeeKamel() {
        return COLUMN_MarjoeeKamel;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccAfradMamurPakhsh() {
        return COLUMN_ccAfradMamurPakhsh;
    }
    public static String COLUMN_ccRefrence() {
        return COLUMN_ccRefrence;
    }
    public static String COLUMN_ExtraProp_IsOld() {
        return COLUMN_ExtraProp_IsOld;
    }
    public static String COLUMN_TarikhForm() {
        return COLUMN_TarikhForm;
    } public static String COLUMN_TarikhFaktor() {
    return COLUMN_TarikhFaktor;
}public static String COLUMN_SumGheymatMarjoee() {
    return COLUMN_SumGheymatMarjoee;
}public static String COLUMN_SumTedadMarjoee() {
    return COLUMN_SumTedadMarjoee;
}

public static String COLUMN_ExtraProp_ccElatMarjoeeKala() {
    return COLUMN_ExtraProp_ccElatMarjoeeKala;
}






    private int ccKardex;
    public int getCcKardex() {
        return ccKardex;
    }
    public void setCcKardex(int ccKardex) {
        this.ccKardex = ccKardex;
    }


    private int ccMarkazAnbar;
    public int getCcMarkazAnbar() {
        return ccMarkazAnbar;
    }
    public void setCcMarkazAnbar(int ccMarkazAnbar) {
        this.ccMarkazAnbar = ccMarkazAnbar;
    }


    private int ccMarkazForosh;
    public int getCcMarkazForosh() {
        return ccMarkazForosh;
    }
    public void setCcMarkazForosh(int ccMarkazForosh) {
        this.ccMarkazForosh = ccMarkazForosh;
    }


    private int ccAnbar;
    public int getCcAnbar() {
        return ccAnbar;
    }
    public void setCcAnbar(int ccAnbar) {
        this.ccAnbar = ccAnbar;
    }


    private int CodeNoeForm;
    public int getCodeNoeForm() {
        return CodeNoeForm;
    }
    public void setCodeNoeForm(int codeNoeForm) {
        CodeNoeForm = codeNoeForm;
    }


    private int CodeNoeAmalyat;
    public int getCodeNoeAmalyat() {
        return CodeNoeAmalyat;
    }
    public void setCodeNoeAmalyat(int codeNoeAmalyat) {
        CodeNoeAmalyat = codeNoeAmalyat;
    }


    private int CodeVazeiat;
    public int getCodeVazeiat() {
        return CodeVazeiat;
    }
    public void setCodeVazeiat(int codeVazeiat) {
        CodeVazeiat = codeVazeiat;
    }


    private int CodeNoeAnbar;
    public int getCodeNoeAnbar() {
        return CodeNoeAnbar;
    }
    public void setCodeNoeAnbar(int codeNoeAnbar) {
        CodeNoeAnbar = codeNoeAnbar;
    }


    private int ccMoshtary;
    public int getCcMoshtary() {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }


    private int MarjoeeKamel;
    public int getMarjoeeKamel() {
        return MarjoeeKamel;
    }
    public void setMarjoeeKamel(int marjoeeKamel) {
        MarjoeeKamel = marjoeeKamel;
    }


    private int ccForoshandeh;
    public int getCcForoshandeh() {
        return ccForoshandeh;
    }
    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }


    private int ccAfradMamurPakhsh;
    public int getCcAfradMamurPakhsh() {
        return ccAfradMamurPakhsh;
    }
    public void setCcAfradMamurPakhsh(int ccAfradMamurPakhsh) {
        this.ccAfradMamurPakhsh = ccAfradMamurPakhsh;
    }


    private int ccRefrence;
    public int getCcRefrence() {
        return ccRefrence;
    }
    public void setCcRefrence(int ccRefrence) {
        this.ccRefrence = ccRefrence;
    }


    private int ExtraProp_IsOld;
    public int getExtraProp_IsOld() {
        return ExtraProp_IsOld;
    }
    public void setExtraProp_IsOld(int extraProp_IsOld) {
        ExtraProp_IsOld = extraProp_IsOld;
    }

    private String TarikhForm;
    public String getTarikhForm() {
        return TarikhForm;
    }
    public void setTarikhForm(String tarikhForm) {
        TarikhForm = tarikhForm;
    }

    private String TarikhFaktor;
    public String getTarikhFaktor() {
        return TarikhFaktor;
    }
    public void setTarikhFaktor(String tarikhFaktor) {
        TarikhFaktor = tarikhFaktor;
    }

    private double SumGheymatMarjoee;
    public double getSumGheymatMarjoee() {
        return SumGheymatMarjoee;
    }
    public void setSumGheymatMarjoee(double sumGheymatMarjoee) {
        SumGheymatMarjoee = sumGheymatMarjoee;
    }

    private double SumTedadMarjoee;
    public double getSumTedadMarjoee() {
        return SumTedadMarjoee;
    }
    public void setSumTedadMarjoee(double sumTedadMarjoee) {
        SumTedadMarjoee = sumTedadMarjoee;
    }


    private int ExtraProp_ccElatMarjoeeKala;

    public int getExtraProp_ccElatMarjoeeKala() {
        return ExtraProp_ccElatMarjoeeKala;
    }

    public void setExtraProp_ccElatMarjoeeKala(int extraProp_ccElatMarjoeeKala) {
        ExtraProp_ccElatMarjoeeKala = extraProp_ccElatMarjoeeKala;
    }

    @NonNull
    @Override
    public String toString()
    {
        return  "ccKardex=" + ccKardex +
                ", ccMarkazAnbar=" + ccMarkazAnbar +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", ccAnbar=" + ccAnbar +
                ", CodeNoeForm=" + CodeNoeForm +
                ", CodeNoeAmalyat=" + CodeNoeAmalyat +
                ", CodeVazeiat=" + CodeVazeiat +
                ", CodeNoeAnbar=" + CodeNoeAnbar +
                ", ccMoshtary=" + ccMoshtary +
                ", MarjoeeKamel=" + MarjoeeKamel +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccAfradMamurPakhsh=" + ccAfradMamurPakhsh +
                ", ccRefrence=" + ccRefrence +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld +
                ", TarikhForm=" + TarikhForm +
                " , TarikhFaktor=" + TarikhFaktor +
                " , SumGheymatMarjoee=" + SumGheymatMarjoee +
                " , SumTedadMarjoee=" + SumTedadMarjoee+
                " , ExtraProp_ccElatMarjoeeKala=" + ExtraProp_ccElatMarjoeeKala;
    }

    // for convert string json
    public JSONObject toJsonForKardex(int  ccAfradMamorPakhsh , int ccAnbarSazmanSakhtar, int ccElatMarjoee)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccKardex_Tablet" , ccKardex);
            jsonObject.put("ccAnbar" , 0);
            jsonObject.put("CodeNoeForm" , CodeNoeForm);
            jsonObject.put("CodeNoeAmalyat" , CodeNoeAmalyat);
            jsonObject.put("TarikhForm" , TarikhForm);
            jsonObject.put("ccRefrence" , ccRefrence);
            jsonObject.put("CodeVazeiat" , CodeVazeiat);
            jsonObject.put("CodeNoeAnbar" , CodeNoeAnbar);
            jsonObject.put("ccUser" , ccAfradMamorPakhsh);
            jsonObject.put("ccMoshtary" , ccMoshtary);
            jsonObject.put("MarjoeeKamel" , MarjoeeKamel);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("ccAfradMamurPakhsh" , ccAfradMamurPakhsh);
            jsonObject.put("ccAfradForoshandeh" , 0);
            jsonObject.put("ccMarkazSazmanForoshSakhtarForosh" , 0);
            jsonObject.put("ccMarkazAnbar" , ccMarkazAnbar);
            jsonObject.put("ccNoeKardex" , 19);
            jsonObject.put("ccAnbarSazmanSakhtar" , ccAnbarSazmanSakhtar);
            jsonObject.put("ccElatMarjoee" , ccElatMarjoee);
            jsonObject.put("ccElatMarjoee" , ExtraProp_ccElatMarjoeeKala);


        }

//        ccKardex_Tablet BIGINT,
//        ccAnbar INT,
//        CodeNoeForm           INT,
//        CodeNoeAmalyat  INT,
//        TarikhForm      DATETIME,
//        ccRefrence BIGINT,
//        CodeVazeiat         INT,
//        CodeNoeAnbar         INT,
//        ccUser          INT,
//        ccMoshtary      INT,
//        MarjoeeKamel    INT,
//        ccForoshandeh INT,
//        ccAfradMamorPakhsh            INT,
//        ccAfradForoshandeh    INT,
//        ccMarkazSazmanForoshSakhtarForosh INT,
//        ccMarkazAnbar   INT,
//        ccNoeKardex          INT,
//        ccAnbarSazmanSakhtar  INT,
//                ccElatMarjoee
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONArray toJsonArrayKardex(ArrayList<KardexModel> models,int  ccAfradMamorPakhsh , int ccAnbarSazmanSakhtar, int ccElatMarjoee)
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            for (KardexModel model : models)
            {
                JSONObject jsonObject = model.toJsonForKardex(  ccAfradMamorPakhsh , ccAnbarSazmanSakhtar ,ccElatMarjoee );
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

    public JSONObject toJsonForKardexForSend(KardexModel model) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ccKardex", model.getCcKardex());
            jsonObject.put("ccMarkazAnbar", model.getCcMarkazAnbar());
            jsonObject.put("ccMarkazForosh", model.getCcMarkazForosh());
            jsonObject.put("ccAnbar", model.getCcAnbar());
            jsonObject.put("CodeNoeForm", model.getCodeNoeForm());
            jsonObject.put("CodeNoeAmalyat", model.getCodeNoeAmalyat());
            jsonObject.put("CodeVazeiat", model.getCodeVazeiat());
            jsonObject.put("CodeNoeAnbar", model.getCodeNoeAnbar());
            jsonObject.put("ccMoshtary", model.getCcMoshtary());
            jsonObject.put("MarjoeeKamel", model.getMarjoeeKamel());
            jsonObject.put("ccForoshandeh", model.getCcForoshandeh());
            jsonObject.put("ccAfradMamurPakhsh", model.getCcAfradMamurPakhsh());
            jsonObject.put("ccRefrence", model.getCcRefrence());
            jsonObject.put("ExtraProp_IsOld", model.getExtraProp_IsOld());
            jsonObject.put("TarikhForm", model.getTarikhForm());
            jsonObject.put("TarikhFaktor", model.getTarikhFaktor());
            jsonObject.put("SumGheymatMarjoee", model.getSumGheymatMarjoee());
            jsonObject.put("SumTedadMarjoee", model.getSumTedadMarjoee());
            jsonObject.put("ccElatMarjoeeKala", model.getExtraProp_ccElatMarjoeeKala());



        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }


}
