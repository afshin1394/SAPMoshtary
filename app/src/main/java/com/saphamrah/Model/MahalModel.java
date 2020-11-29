package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MahalModel
{

    public static final int MAHAL_TYPE_OSTAN = 2;
    public static final int MAHAL_TYPE_SHAHRESTAN = 3;
    public static final int MAHAL_TYPE_BAKHSH = 4;
    public static final int MAHAL_TYPE_SHAHR = 5;
    public static final int MAHAL_TYPE_MANTAGHE = 6;
    private static final String TABLE_NAME = "Mahal";
    private static final String COLUMN_ccMahal = "ccMahal";
    private static final String COLUMN_NameMahal = "NameMahal";
    private static final String COLUMN_CodeNoeMahal = "CodeNoeMahal";
    private static final String COLUMN_ccMahalLink = "ccMahalLink";
    private static final String COLUMN_PishShomareh = "PishShomareh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMahal() {
        return COLUMN_ccMahal;
    }
    public static String COLUMN_NameMahal() {
        return COLUMN_NameMahal;
    }
    public static String COLUMN_CodeNoeMahal() {
        return COLUMN_CodeNoeMahal;
    }
    public static String COLUMN_ccMahalLink() {
        return COLUMN_ccMahalLink;
    }
    public static String COLUMN_PishShomareh() {
        return COLUMN_PishShomareh;
    }




    @SerializedName("ccMahal")
    @Expose
    private Integer ccMahal;
    @SerializedName("NameMahal")
    @Expose
    private String NameMahal;
    @SerializedName("CodeNoeMahal")
    @Expose
    private Integer CodeNoeMahal;
    @SerializedName("ccMahalLink")
    @Expose
    private Integer ccMahalLink;
    @SerializedName("PishShomareh")
    @Expose
    private String PishShomareh;


    public Integer getCcMahal() {
        return ccMahal;
    }

    public void setCcMahal(Integer ccMahal) {
        this.ccMahal = ccMahal;
    }

    public String getNameMahal() {
        return NameMahal;
    }

    public void setNameMahal(String nameMahal) {
        NameMahal = nameMahal;
    }

    public Integer getCodeNoeMahal() {
        return CodeNoeMahal;
    }

    public void setCodeNoeMahal(Integer codeNoeMahal) {
        CodeNoeMahal = codeNoeMahal;
    }

    public Integer getCcMahalLink() {
        return ccMahalLink;
    }

    public void setCcMahalLink(Integer ccMahalLink) {
        this.ccMahalLink = ccMahalLink;
    }

    public String getPishShomareh() {
        return PishShomareh;
    }

    public void setPishShomareh(String pishShomareh) {
        PishShomareh = pishShomareh;
    }

    @Override
    public String toString()
    {
        return "ccMahal : " + ccMahal + " , NameMahal : " + NameMahal + " , CodeNoeMahal : " + CodeNoeMahal +
               " , ccMahalLink : " + ccMahalLink + " , PishShomareh : " +  PishShomareh;
    }


}
