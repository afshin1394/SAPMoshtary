package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class ParameterChildModel
{


    private static final String TABLE_NAME = "ParameterChild";
    private static final String COLUMN_ccChildParameter = "ccParameterChild";
    private static final String COLUMN_ccParameter = "ccParameter";
    private static final String COLUMN_Name = "Name";
    private static final String COLUMN_Value = "Value";
    private static final String COLUMN_txt = "txt";
    private static final String COLUMN_CodeSort = "CodeSort";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccChildParameter() {
        return COLUMN_ccChildParameter;
    }
    public static String COLUMN_ccParameter() {
        return COLUMN_ccParameter;
    }
    public static String COLUMN_Name() {
        return COLUMN_Name;
    }
    public static String COLUMN_Value() {
        return COLUMN_Value;
    }
    public static String COLUMN_txt() {
        return COLUMN_txt;
    }
    public static String COLUMN_CodeSort() {
        return COLUMN_CodeSort;
    }



    private Integer ccParameterChild;
    private Integer ccParameter;
    private String Name;
    private String Value;
    private String txt;
    private int CodeSort;


    public Integer getCcParameterChild()
    {
        return ccParameterChild;
    }

    public void setCcParameterChild(Integer ccParameterChild)
    {
        this.ccParameterChild = ccParameterChild;
    }

    public Integer getCcParameter() {
        return ccParameter;
    }

    public void setCcParameter(Integer ccParameter) {
        this.ccParameter = ccParameter;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getCodeSort() {
        return CodeSort;
    }

    public void setCodeSort(int codeSort) {
        CodeSort = codeSort;
    }



    @NonNull
    @Override
    public String toString() {
        return "ChildParameterModel{" +
                "ccChildParameter=" + ccParameterChild +
                ", ccParameter=" + ccParameter +
                ", Name='" + Name + '\'' +
                ", Value='" + Value + '\'' +
                ", txt='" + txt + '\'' +
                ", CodeSort=" + CodeSort +
                '}';
    }
}
