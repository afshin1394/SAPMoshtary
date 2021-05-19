package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElatMarjoeeKalaModel
{

    private static final String TABLE_NAME = "ElatMarjoeeKala";
    private static final String COLUMN_ccElatMarjoeeKala = "ccElatMarjoeeKala";
    private static final String COLUMN_Sharh = "Sharh";
    private static final String COLUMN_CodeNoeElat = "CodeNoeElat";
    private static final String COLUMN_IsZayeat = "IsZayeat";
    private static final String COLUMN_GetImage = "GetImage";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccElatMarjoeeKala() {
        return COLUMN_ccElatMarjoeeKala;
    }
    public static String COLUMN_Sharh() {
        return COLUMN_Sharh;
    }
    public static String COLUMN_CodeNoeElat() {
        return COLUMN_CodeNoeElat;
    }
    public static String COLUMN_IsZayeat() {
        return COLUMN_IsZayeat;
    }
    public static String COLUMN_GetImage() {
        return COLUMN_GetImage;
    }


    @SerializedName("ccElatMarjoeeKala")
    @Expose
    private Integer ccElatMarjoeeKala;
    @SerializedName("Sharh")
    @Expose
    private String Sharh;
    @SerializedName("CodeNoeElat")
    @Expose
    private Integer CodeNoeElat;
    @SerializedName("MasoleiatElat")
    @Expose
    private Integer MasoleiatElat;
    @SerializedName("CodeNoeAnbar")
    @Expose
    private Integer CodeNoeAnbar;
    @SerializedName("ViewInPPC")
    @Expose
    private String ViewInPPC;
    @SerializedName("IsZayeat")
    @Expose
    private Integer IsZayeat;
    @SerializedName("Id")
    @Expose
    private Integer Id;
    @SerializedName("GetImage")
    @Expose
    private Integer GetImage;


    public Integer getCcElatMarjoeeKala() {
        return ccElatMarjoeeKala;
    }

    public void setCcElatMarjoeeKala(Integer ccElatMarjoeeKala) {
        this.ccElatMarjoeeKala = ccElatMarjoeeKala;
    }

    public String getSharh() {
        return Sharh;
    }

    public void setSharh(String sharh) {
        Sharh = sharh;
    }

    public Integer getCodeNoeElat() {
        return CodeNoeElat;
    }

    public void setCodeNoeElat(Integer codeNoeElat) {
        CodeNoeElat = codeNoeElat;
    }

    public Integer getMasoleiatElat() {
        return MasoleiatElat;
    }

    public void setMasoleiatElat(Integer masoleiatElat) {
        MasoleiatElat = masoleiatElat;
    }

    public Integer getCodeNoeAnbar() {
        return CodeNoeAnbar;
    }

    public void setCodeNoeAnbar(Integer codeNoeAnbar) {
        CodeNoeAnbar = codeNoeAnbar;
    }

    public String getViewInPPC() {
        return ViewInPPC;
    }

    public void setViewInPPC(String viewInPPC) {
        ViewInPPC = viewInPPC;
    }

    public Integer getIsZayeat() {
        return IsZayeat;
    }

    public void setIsZayeat(Integer isZayeat) {
        IsZayeat = isZayeat;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getGetImage() {
        return GetImage;
    }

    public void setGetImage(Integer getImage) {
        GetImage = getImage;
    }

    @Override
    public String toString() {
        return "ccElatMarjoeeKala : " + ccElatMarjoeeKala + " , Sharh : " + Sharh + " , CodeNoeElat : " + CodeNoeElat +
                " , MasoleiatElat : " + MasoleiatElat + " , CodeNoeAnbar : " + CodeNoeAnbar + " , ViewInPPC : " + ViewInPPC +
                " , IsZayeat : " + IsZayeat + " , Id : " + Id + " , GetImage : " + GetImage;
    }

}
