package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmargarGorohModel
{

    private static final String TABLE_NAME = "AmargarGoroh";
    private static final String COLUMN_ccAmargarGoroh = "ccAmargarGoroh";
    private static final String COLUMN_CodeAmargarGoroh = "CodeAmargarGoroh";
    private static final String COLUMN_SharhAmargarGoroh = "SharhAmargarGoroh";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";

    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccAmargarGoroh()
    {
        return COLUMN_ccAmargarGoroh;
    }
    public static String COLUMN_CodeAmargarGoroh()
    {
        return COLUMN_CodeAmargarGoroh;
    }
    public static String COLUMN_SharhAmargarGoroh()
    {
        return COLUMN_SharhAmargarGoroh;
    }
    public static String COLUMN_CodeVazeiat()
    {
        return COLUMN_CodeVazeiat;
    }



    @SerializedName("ccAmargarGoroh")
    @Expose
    private int ccAmargarGoroh;
    @SerializedName("CodeAmargarGoroh")
    @Expose
    private String codeAmargarGoroh;
    @SerializedName("SharhAmargarGoroh")
    @Expose
    private String sharhAmargarGoroh;
    @SerializedName("CodeVazeiat")
    @Expose
    private int codeVazeiat;
    @SerializedName("Id")
    @Expose
    private int id;


    public int getCcAmargarGoroh()
    {
        return ccAmargarGoroh;
    }
    public void setCcAmargarGoroh(int ccAmargarGoroh)
    {
        this.ccAmargarGoroh = ccAmargarGoroh;
    }

    public String getCodeAmargarGoroh()
    {
        return codeAmargarGoroh;
    }
    public void setCodeAmargarGoroh(String codeAmargarGoroh)
    {
        this.codeAmargarGoroh = codeAmargarGoroh;
    }

    public String getSharhAmargarGoroh()
    {
        return sharhAmargarGoroh;
    }
    public void setSharhAmargarGoroh(String sharhAmargarGoroh)
    {
        this.sharhAmargarGoroh = sharhAmargarGoroh;
    }

    public int getCodeVazeiat()
    {
        return codeVazeiat;
    }
    public void setCodeVazeiat(int codeVazeiat)
    {
        this.codeVazeiat = codeVazeiat;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
}
