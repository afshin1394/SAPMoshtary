package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MahalCodePostiModel
{

    private static final String TABLE_NAME = "Mahal_CodePosti";
    private static final String COLUMN_ccMahalCodePosti = "ccMahal_CodePosti";
    private static final String COLUMN_ccMahal = "ccMahal";
    private static final String COLUMN_CodePosti_from = "CodePosti_from";
    private static final String COLUMN_CodePosti_to = "CodePosti_to";
    private static final String COLUMN_ccMantagheh = "ccMantagheh";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMahalCodePosti() {
        return COLUMN_ccMahalCodePosti;
    }
    public static String COLUMN_ccMahal() {
        return COLUMN_ccMahal;
    }
    public static String COLUMN_CodePosti_from() {
        return COLUMN_CodePosti_from;
    }
    public static String COLUMN_CodePosti_to() {
        return COLUMN_CodePosti_to;
    }
    public static String COLUMN_ccMantagheh() {
        return COLUMN_ccMantagheh;
    }



    @SerializedName("ccMahal_CodePosti")
    @Expose
    private int ccMahalCodePosti;
    @SerializedName("ccMahal")
    @Expose
    private int ccMahal;
    @SerializedName("CodePosti_from")
    @Expose
    private String CodePostiFrom;
    @SerializedName("CodePosti_to")
    @Expose
    private String CodePostiTo;
    @SerializedName("ccMantagheh")
    @Expose
    private int ccMantagheh;


    public int getCcMahalCodePosti() {
        return ccMahalCodePosti;
    }

    public void setCcMahalCodePosti(int ccMahalCodePosti) {
        this.ccMahalCodePosti = ccMahalCodePosti;
    }

    public int getCcMahal() {
        return ccMahal;
    }

    public void setCcMahal(int ccMahal) {
        this.ccMahal = ccMahal;
    }

    public String getCodePostiFrom() {
        return CodePostiFrom;
    }

    public void setCodePostiFrom(String codePostiFrom) {
        CodePostiFrom = codePostiFrom;
    }

    public String getCodePostiTo() {
        return CodePostiTo;
    }

    public void setCodePostiTo(String codePostiTo) {
        CodePostiTo = codePostiTo;
    }

    public int getCcMantagheh() {
        return ccMantagheh;
    }

    public void setCcMantagheh(int ccMantagheh) {
        this.ccMantagheh = ccMantagheh;
    }



//    @NonNull
//    @Override
//    public String toString()
//    {
//        return "ccMahalCodePosti : " + ccMahalCodePosti + " , ccMahal : " + ccMahal + " , CodePostiFrom : " + CodePostiFrom +
//                " , CodePostiTo : " + CodePostiTo + " , ccMantagheh : " + ccMantagheh;
//    }


}
