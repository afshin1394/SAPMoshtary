package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListKalaForMarjoeeModel
{

    private static final String TABLE_NAME = "ListKalaForMarjoee";
    private static final String COLUMN_ccKala = "ccKala";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_CodeKala = "CodeKala";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_ccTaminKonandeh = "ccTaminKonandeh";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_TarikhTolid = "TarikhTolid";
    private static final String COLUMN_TarikhEngheza = "TarikhEngheza";
    private static final String COLUMN_Tedad = "Tedad";
    private static final String COLUMN_MablaghForosh = "MablaghForosh";
    private static final String COLUMN_MablaghMasrafKonandeh = "MablaghMasrafKonandeh";
    private static final String COLUMN_IsKalaZayeatTolid = "IsKalaZayeatTolid";
    private static final String COLUMN_IsMabna = "IsMabna";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKala() {
        return COLUMN_ccKala;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_CodeKala() {
        return COLUMN_CodeKala;
    }
    public static String COLUMN_NameKala() {
        return COLUMN_NameKala;
    }
    public static String COLUMN_ccTaminKonandeh() {
        return COLUMN_ccTaminKonandeh;
    }
    public static String COLUMN_ShomarehBach() {
        return COLUMN_ShomarehBach;
    }
    public static String COLUMN_TarikhTolid() {
        return COLUMN_TarikhTolid;
    }
    public static String COLUMN_TarikhEngheza() {
        return COLUMN_TarikhEngheza;
    }
    public static String COLUMN_Tedad() {
        return COLUMN_Tedad;
    }
    public static String COLUMN_MablaghForosh() {
        return COLUMN_MablaghForosh;
    }
    public static String COLUMN_MablaghMasrafKonandeh() {
        return COLUMN_MablaghMasrafKonandeh;
    }
    public static String COLUMN_IsKalaZayeatTolid() {
        return COLUMN_IsKalaZayeatTolid;
    }
    public static String COLUMN_IsMabna() {
        return COLUMN_IsMabna;
    }


    @SerializedName("Radif")
    @Expose
    private Integer radif;
    @SerializedName("ccKala")
    @Expose
    private Integer ccKala;
    @SerializedName("ccKalaCode")
    @Expose
    private Integer ccKalaCode;
    @SerializedName("CodeKala")
    @Expose
    private String codeKala;
    @SerializedName("NameKala")
    @Expose
    private String nameKala;
    @SerializedName("ccTaminKonandeh")
    @Expose
    private Integer ccTaminKonandeh;
    @SerializedName("ShomarehBach")
    @Expose
    private String shomarehBach;
    @SerializedName("TarikhTolid")
    @Expose
    private String tarikhTolid;
    @SerializedName("TarikhEngheza")
    @Expose
    private String tarikhEngheza;
    @SerializedName("Tedad")
    @Expose
    private Integer tedad;
    @SerializedName("MablaghForosh")
    @Expose
    private float mablaghForosh;
    @SerializedName("MablaghMasrafKonandeh")
    @Expose
    private float mablaghMasrafKonandeh;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("IsKalaZayeatTolid")
    @Expose
    private Integer isKalaZayeatTolid;
    @SerializedName("IsMabna")
    @Expose
    private int isMabna;

    private float modifiedPrice;




    public Integer getRadif() {
        return radif;
    }

    public void setRadif(Integer radif) {
        this.radif = radif;
    }

    public Integer getCcKala() {
        return ccKala;
    }

    public void setCcKala(Integer ccKala) {
        this.ccKala = ccKala;
    }

    public Integer getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(Integer ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public String getCodeKala() {
        return codeKala;
    }

    public void setCodeKala(String codeKala) {
        this.codeKala = codeKala;
    }

    public String getNameKala() {
        return nameKala;
    }

    public void setNameKala(String nameKala) {
        this.nameKala = nameKala;
    }

    public Integer getCcTaminKonandeh() {
        return ccTaminKonandeh;
    }

    public void setCcTaminKonandeh(Integer ccTaminKonandeh) {
        this.ccTaminKonandeh = ccTaminKonandeh;
    }

    public String getShomarehBach() {
        return shomarehBach;
    }

    public void setShomarehBach(String shomarehBach) {
        this.shomarehBach = shomarehBach;
    }

    public String getTarikhTolid() {
        return tarikhTolid;
    }

    public void setTarikhTolid(String tarikhTolid) {
        this.tarikhTolid = tarikhTolid;
    }

    public String getTarikhEngheza() {
        return tarikhEngheza;
    }

    public void setTarikhEngheza(String tarikhEngheza) {
        this.tarikhEngheza = tarikhEngheza;
    }

    public Integer getTedad() {
        return tedad;
    }

    public void setTedad(Integer tedad) {
        this.tedad = tedad;
    }

    public float getMablaghForosh() {
        return mablaghForosh;
    }

    public void setMablaghForosh(float mablaghForosh) {
        this.mablaghForosh = mablaghForosh;
    }

    public float getMablaghMasrafKonandeh() {
        return mablaghMasrafKonandeh;
    }

    public void setMablaghMasrafKonandeh(float mablaghMasrafKonandeh) {
        this.mablaghMasrafKonandeh = mablaghMasrafKonandeh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsKalaZayeatTolid() {
        return isKalaZayeatTolid;
    }

    public void setIsKalaZayeatTolid(Integer isKalaZayeatTolid) {
        this.tedad = isKalaZayeatTolid;
    }


    public int getIsMabna() {
        return isMabna;
    }

    public void setIsMabna(int isMabna) {
        this.isMabna = isMabna;
    }

    public float getModifiedPrice() {
        return modifiedPrice;
    }

    public void setModifiedPrice(float modifiedPrice) {
        this.modifiedPrice = modifiedPrice;
    }

    @Override
    public String toString() {
        return "ListKalaForMarjoeeModel{" +
                "radif=" + radif +
                ", ccKala=" + ccKala +
                ", ccKalaCode=" + ccKalaCode +
                ", codeKala='" + codeKala + '\'' +
                ", nameKala='" + nameKala + '\'' +
                ", ccTaminKonandeh=" + ccTaminKonandeh +
                ", shomarehBach='" + shomarehBach + '\'' +
                ", tarikhTolid='" + tarikhTolid + '\'' +
                ", tarikhEngheza='" + tarikhEngheza + '\'' +
                ", tedad=" + tedad +
                ", mablaghForosh=" + mablaghForosh +
                ", mablaghMasrafKonandeh=" + mablaghMasrafKonandeh +
                ", id=" + id +
                ", isKalaZayeatTolid=" + isKalaZayeatTolid +
                ", isMabna=" + isMabna +
                '}';
    }
}
