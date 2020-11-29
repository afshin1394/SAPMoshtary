package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForoshandehModel
{


    private static final String TABLE_NAME = "Foroshandeh";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_FullNameForoshandeh = "FullNameForoshandeh";
    private static final String COLUMN_ccMarkazSazmanForoshSakhtarForosh = "ccMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_NameMarkazSazmanForoshSakhtarForosh = "NameMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_CodeForoshandeh = "CodeForoshandeh";
    private static final String COLUMN_ccAfradForoshandeh = "ccAfradForoshandeh";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_NameMarkazForosh = "NameMarkazForosh";
    private static final String COLUMN_ccSazmanForosh = "ccSazmanForosh";
    private static final String COLUMN_NameSazmanForosh = "NameSazmanForosh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_FullNameForoshandeh() {
        return COLUMN_FullNameForoshandeh;
    }
    public static String COLUMN_ccMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_ccMarkazSazmanForoshSakhtarForosh;
    }
    public static String COLUMN_NameMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_NameMarkazSazmanForoshSakhtarForosh;
    }
    public static String COLUMN_CodeForoshandeh() {
        return COLUMN_CodeForoshandeh;
    }
    public static String COLUMN_ccAfradForoshandeh() {
        return COLUMN_ccAfradForoshandeh;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_NameMarkazForosh() {
        return COLUMN_NameMarkazForosh;
    }
    public static String COLUMN_ccSazmanForosh() {
        return COLUMN_ccSazmanForosh;
    }
    public static String COLUMN_NameSazmanForosh() {
        return COLUMN_NameSazmanForosh;
    }




    @SerializedName("ccMarkazSazmanForoshSakhtarForosh")
    @Expose
    private Integer ccMarkazSazmanForoshSakhtarForosh;
    @SerializedName("NameMarkazSazmanForoshSakhtarForosh")
    @Expose
    private String nameMarkazSazmanForoshSakhtarForosh;
    @SerializedName("ccForoshandeh")
    @Expose
    private Integer ccForoshandeh;
    @SerializedName("CodeForoshandeh")
    @Expose
    private String codeForoshandeh;
    @SerializedName("FullNameForoshandeh")
    @Expose
    private String fullNameForoshandeh;
    @SerializedName("ccAfradForoshandeh")
    @Expose
    private Integer ccAfradForoshandeh;
    @SerializedName("ccMarkazForosh")
    @Expose
    private Integer ccMarkazForosh;
    @SerializedName("NameMarkazForosh")
    @Expose
    private String nameMarkazForosh;
    @SerializedName("ccSazmanForosh")
    @Expose
    private Integer ccSazmanForosh;
    @SerializedName("NameSazmanForosh")
    @Expose
    private String nameSazmanForosh;


    public Integer getCcMarkazSazmanForoshSakhtarForosh() {
        return ccMarkazSazmanForoshSakhtarForosh;
    }

    public void setCcMarkazSazmanForoshSakhtarForosh(Integer ccMarkazSazmanForoshSakhtarForosh) {
        this.ccMarkazSazmanForoshSakhtarForosh = ccMarkazSazmanForoshSakhtarForosh;
    }

    public String getNameMarkazSazmanForoshSakhtarForosh() {
        return nameMarkazSazmanForoshSakhtarForosh;
    }

    public void setNameMarkazSazmanForoshSakhtarForosh(String nameMarkazSazmanForoshSakhtarForosh) {
        this.nameMarkazSazmanForoshSakhtarForosh = nameMarkazSazmanForoshSakhtarForosh;
    }

    public Integer getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(Integer ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public String getCodeForoshandeh() {
        return codeForoshandeh;
    }

    public void setCodeForoshandeh(String codeForoshandeh) {
        this.codeForoshandeh = codeForoshandeh;
    }

    public String getFullNameForoshandeh() {
        return fullNameForoshandeh;
    }

    public void setFullNameForoshandeh(String fullNameForoshandeh) {
        this.fullNameForoshandeh = fullNameForoshandeh;
    }

    public Integer getCcAfradForoshandeh() {
        return ccAfradForoshandeh;
    }

    public void setCcAfradForoshandeh(Integer ccAfradForoshandeh) {
        this.ccAfradForoshandeh = ccAfradForoshandeh;
    }

    public Integer getCcMarkazForosh() {
        return ccMarkazForosh;
    }

    public void setCcMarkazForosh(Integer ccMarkazForosh) {
        this.ccMarkazForosh = ccMarkazForosh;
    }

    public String getNameMarkazForosh() {
        return nameMarkazForosh;
    }

    public void setNameMarkazForosh(String nameMarkazForosh) {
        this.nameMarkazForosh = nameMarkazForosh;
    }

    public Integer getCcSazmanForosh() {
        return ccSazmanForosh;
    }

    public void setCcSazmanForosh(Integer ccSazmanForosh) {
        this.ccSazmanForosh = ccSazmanForosh;
    }

    public String getNameSazmanForosh() {
        return nameSazmanForosh;
    }

    public void setNameSazmanForosh(String nameSazmanForosh) {
        this.nameSazmanForosh = nameSazmanForosh;
    }


    @NonNull
    @Override
    public String toString() {
        return "ForoshandehModel{" +
                "ccMarkazSazmanForoshSakhtarForosh=" + ccMarkazSazmanForoshSakhtarForosh +
                ", nameMarkazSazmanForoshSakhtarForosh='" + nameMarkazSazmanForoshSakhtarForosh + '\'' +
                ", ccForoshandeh=" + ccForoshandeh +
                ", codeForoshandeh='" + codeForoshandeh + '\'' +
                ", fullNameForoshandeh='" + fullNameForoshandeh + '\'' +
                ", ccAfradForoshandeh=" + ccAfradForoshandeh +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", nameMarkazForosh='" + nameMarkazForosh + '\'' +
                ", ccSazmanForosh=" + ccSazmanForosh +
                ", nameSazmanForosh='" + nameSazmanForosh + '\'' +
                '}';
    }
}
