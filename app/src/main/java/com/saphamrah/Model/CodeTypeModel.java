package com.saphamrah.Model;

public class CodeTypeModel
{

    private static final String TABLE_NAME = "CodeType";
    private static final String COLUMN_ccCodeType = "ccCodeType";
    private static final String COLUMN_NameCodeType = "NameCodeType";
    private static final String COLUMN_Pattern = "Pattern";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccCodeType() {
        return COLUMN_ccCodeType;
    }
    public static String COLUMN_NameCodeType() {
        return COLUMN_NameCodeType;
    }
    public static String COLUMN_Pattern() {
        return COLUMN_Pattern;
    }




    private int ccCodeType;
    private String NameCodeType;
    private String Pattern;


    public int getCcCodeType() {
        return ccCodeType;
    }
    public void setCcCodeType(int ccCodeType) {
        this.ccCodeType = ccCodeType;
    }
    public String getNameCodeType() {
        return NameCodeType;
    }
    public void setNameCodeType(String nameCodeType) {
        NameCodeType = nameCodeType;
    }
    public String getPattern() {
        return Pattern;
    }
    public void setPattern(String pattern) {
        Pattern = pattern;
    }


}
