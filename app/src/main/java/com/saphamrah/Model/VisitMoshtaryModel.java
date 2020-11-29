package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class VisitMoshtaryModel
{

    private static final String TABLE_NAME = "VisitMoshtary";
    private static final String COLUMN_ccVisitMoshtary = "ccVisitMoshtary";
    private static final String COLUMN_ccAmargar = "ccAmargar";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccPorseshnameh = "ccPorseshnameh";
    private static final String COLUMN_TarikhVisitMoshtary = "TarikhVisitMoshtary";
    private static final String COLUMN_CodeVazeiatMoshtary = "CodeVazeiatMoshtary";
    private static final String COLUMN_ccElatAdamMoarefiMoshtary = "ccElatAdamMoarefiMoshtary";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";
    private static final String COLUMN_CodeMoshtaryTekrari = "CodeMoshtaryTekrari";
    private static final String COLUMN_SaatVorod = "SaatVorod";
    private static final String COLUMN_SaatKhoroj = "SaatKhoroj";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccVisitMoshtary()
    {
        return COLUMN_ccVisitMoshtary;
    }
    public static String COLUMN_ccAmargar()
    {
        return COLUMN_ccAmargar;
    }
    public static String COLUMN_ccMoshtary()
    {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccPorseshnameh()
    {
        return COLUMN_ccPorseshnameh;
    }
    public static String COLUMN_TarikhVisitMoshtary()
    {
        return COLUMN_TarikhVisitMoshtary;
    }
    public static String COLUMN_CodeVazeiatMoshtary()
    {
        return COLUMN_CodeVazeiatMoshtary;
    }
    public static String COLUMN_ccElatAdamMoarefiMoshtary()
    {
        return COLUMN_ccElatAdamMoarefiMoshtary;
    }
    public static String COLUMN_ExtraProp_IsOld()
    {
        return COLUMN_ExtraProp_IsOld;
    }
    public static String COLUMN_CodeMoshtaryTekrari()
    {
        return COLUMN_CodeMoshtaryTekrari;
    }
    public static String COLUMN_SaatVorod()
    {
        return COLUMN_SaatVorod;
    }
    public static String COLUMN_SaatKhoroj()
    {
        return COLUMN_SaatKhoroj;
    }


    private int ccVisitMoshtary;
    private int ccAmargar;
    private int ccMoshtary;
    private int ccPorseshnameh;
    private String TarikhVisitMoshtary;
    private int CodeVazeiatMoshtary;
    private int ccElatAdamMoarefiMoshtary;
    private int ExtraProp_IsOld;
    private String CodeMoshtaryTekrari;
    private String SaatVorod;
    private String SaatKhoroj;


    public int getCcVisitMoshtary()
    {
        return ccVisitMoshtary;
    }
    public void setCcVisitMoshtary(int ccVisitMoshtary)
    {
        this.ccVisitMoshtary = ccVisitMoshtary;
    }

    public int getCcAmargar()
    {
        return ccAmargar;
    }
    public void setCcAmargar(int ccAmargar)
    {
        this.ccAmargar = ccAmargar;
    }

    public int getCcMoshtary()
    {
        return ccMoshtary;
    }
    public void setCcMoshtary(int ccMoshtary)
    {
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcPorseshnameh()
    {
        return ccPorseshnameh;
    }
    public void setCcPorseshnameh(int ccPorseshnameh)
    {
        this.ccPorseshnameh = ccPorseshnameh;
    }

    public String getTarikhVisitMoshtary()
    {
        return TarikhVisitMoshtary;
    }
    public void setTarikhVisitMoshtary(String tarikhVisitMoshtary)
    {
        TarikhVisitMoshtary = tarikhVisitMoshtary;
    }

    public int getCodeVazeiatMoshtary()
    {
        return CodeVazeiatMoshtary;
    }
    public void setCodeVazeiatMoshtary(int codeVazeiatMoshtary)
    {
        CodeVazeiatMoshtary = codeVazeiatMoshtary;
    }

    public int getCcElatAdamMoarefiMoshtary()
    {
        return ccElatAdamMoarefiMoshtary;
    }
    public void setCcElatAdamMoarefiMoshtary(int ccElatAdamMoarefiMoshtary)
    {
        this.ccElatAdamMoarefiMoshtary = ccElatAdamMoarefiMoshtary;
    }

    public int getExtraProp_IsOld()
    {
        return ExtraProp_IsOld;
    }
    public void setExtraProp_IsOld(int extraProp_IsOld)
    {
        ExtraProp_IsOld = extraProp_IsOld;
    }

    public String getCodeMoshtaryTekrari()
    {
        return CodeMoshtaryTekrari;
    }
    public void setCodeMoshtaryTekrari(String codeMoshtaryTekrari)
    {
        CodeMoshtaryTekrari = codeMoshtaryTekrari;
    }

    public String getSaatVorod()
    {
        return SaatVorod;
    }
    public void setSaatVorod(String saatVorod)
    {
        SaatVorod = saatVorod;
    }

    public String getSaatKhoroj()
    {
        return SaatKhoroj;
    }
    public void setSaatKhoroj(String saatKhoroj)
    {
        SaatKhoroj = saatKhoroj;
    }


    public JSONObject toJsonObject()
    {
        try
        {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(VisitMoshtaryModel.COLUMN_ccAmargar(), ccAmargar);
            jsonObject.put(VisitMoshtaryModel.COLUMN_ccMoshtary(), ccMoshtary);
            jsonObject.put(VisitMoshtaryModel.COLUMN_ccPorseshnameh(), ccPorseshnameh);
            jsonObject.put(VisitMoshtaryModel.COLUMN_TarikhVisitMoshtary(), TarikhVisitMoshtary);
            jsonObject.put(VisitMoshtaryModel.COLUMN_CodeVazeiatMoshtary(), CodeVazeiatMoshtary);
            jsonObject.put(VisitMoshtaryModel.COLUMN_ccElatAdamMoarefiMoshtary(), ccElatAdamMoarefiMoshtary);
            jsonObject.put(VisitMoshtaryModel.COLUMN_CodeMoshtaryTekrari(), CodeMoshtaryTekrari);
            jsonObject.put(VisitMoshtaryModel.COLUMN_SaatVorod(), SaatVorod);
            jsonObject.put(VisitMoshtaryModel.COLUMN_SaatKhoroj(), SaatKhoroj);

            return jsonObject;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    @NonNull
    @Override
    public String toString()
    {
        return "VisitMoshtaryModel{" +
                "ccVisitMoshtary=" + ccVisitMoshtary +
                ", ccAmargar=" + ccAmargar +
                ", ccMoshtary=" + ccMoshtary +
                ", ccPorseshnameh=" + ccPorseshnameh +
                ", TarikhVisitMoshtary='" + TarikhVisitMoshtary + '\'' +
                ", CodeVazeiatMoshtary=" + CodeVazeiatMoshtary +
                ", ccElatAdamMoarefiMoshtary=" + ccElatAdamMoarefiMoshtary +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld +
                ", CodeMoshtaryTekrari='" + CodeMoshtaryTekrari + '\'' +
                ", SaatVorod='" + SaatVorod + '\'' +
                ", SaatKhoroj='" + SaatKhoroj + '\'' +
                '}';
    }
}
