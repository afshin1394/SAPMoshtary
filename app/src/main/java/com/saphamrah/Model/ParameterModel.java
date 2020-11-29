package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class ParameterModel
{

    private static final String TABLE_NAME = "Parameter";
    private static final String COLUMN_ccParameter = "ccParameter";
    private static final String COLUMN_NoeParameter = "NoeParameter";
    private static final String COLUMN_Name = "Name";
    private static final String COLUMN_GetProgram = "GetProgram";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccParameter() {
        return COLUMN_ccParameter;
    }
    public static String COLUMN_NoeParameter() {
        return COLUMN_NoeParameter;
    }
    public static String COLUMN_Name() {
        return COLUMN_Name;
    }
    public static String COLUMN_GetProgram() {
        return COLUMN_GetProgram;
    }


    private int ccParameter;
    private int NoeParameter;
    private String Name;
    private int GetProgram;


    public Integer getCcParameter() {
        return ccParameter;
    }
    public void setCcParameter(Integer ccParameter) {
        this.ccParameter = ccParameter;
    }
    public int getNoeParameter() {
        return NoeParameter;
    }
    public void setNoeParameter(int noeParameter) {
        NoeParameter = noeParameter;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public int getGetProgram() {
        return GetProgram;
    }
    public void setGetProgram(int getProgram) {
        GetProgram = getProgram;
    }



    @NonNull
    @Override
    public String toString() {
        return "ParameterModel{" +
                "ccParameter=" + ccParameter +
                ", NoeParameter=" + NoeParameter +
                ", Name='" + Name +
                ", GetProgram='" + GetProgram + '\'' +
                '}';
    }


}
