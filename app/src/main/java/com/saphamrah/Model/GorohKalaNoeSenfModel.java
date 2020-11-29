package com.saphamrah.Model;

public class GorohKalaNoeSenfModel
{

    private static final String TABLE_NAME = "GorohKalaNoeSenf";
    private static final String COLUMN_ccGorohKalaNoeSenf = "ccGorohKalaNoeSenf";
    private static final String COLUMN_ccNoeSenf = "ccNoeSenf";
    private static final String COLUMN_ccGorohKala = "ccGorohKala";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccGorohKalaNoeSenf() {
        return COLUMN_ccGorohKalaNoeSenf;
    }
    public static String COLUMN_ccNoeSenf() {
        return COLUMN_ccNoeSenf;
    }
    public static String COLUMN_ccGorohKala() {
        return COLUMN_ccGorohKala;
    }



    private Integer ccGorohKalaNoeSenf;
    private Integer ccNoeSenf;
    private Integer ccGorohKala;


    public Integer getCcGorohKalaNoeSenf() {
        return ccGorohKalaNoeSenf;
    }

    public void setCcGorohKalaNoeSenf(Integer ccGorohKalaNoeSenf) {
        this.ccGorohKalaNoeSenf = ccGorohKalaNoeSenf;
    }

    public Integer getCcNoeSenf() {
        return ccNoeSenf;
    }

    public void setCcNoeSenf(Integer ccNoeSenf) {
        this.ccNoeSenf = ccNoeSenf;
    }

    public Integer getCcGorohKala() {
        return ccGorohKala;
    }

    public void setCcGorohKala(Integer ccGorohKala) {
        this.ccGorohKala = ccGorohKala;
    }

    @Override
    public String toString()
    {
        return "ccGorohKalaNoeSenf : " + ccGorohKalaNoeSenf + " , ccNoeSenf : " + ccNoeSenf + " , ccGorohKala : " + ccGorohKala;
    }



}
