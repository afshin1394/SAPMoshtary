package com.saphamrah.Model;

import org.json.JSONObject;

public class DarkhastFaktorAfradForoshModel
{

    private static final String TABLE_NAME = "DarkhastFaktorAfradForosh";
    private static final String COLUMN_ccDarkhastFaktorAfradForosh = "ccDarkhastFaktorAfradForosh";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_ccAfradForoshandeh = "ccAfradForoshandeh";
    private static final String COLUMN_ccAfradForoshandehJaygozin = "ccAfradForoshandehJaygozin";
    private static final String COLUMN_ccAfradMamorPakhsh = "ccAfradMamorPakhsh";
    private static final String COLUMN_ccAfradMamorPakhshJaygozin = "ccAfradMamorPakhshJaygozin";
    private static final String COLUMN_ccAfradRanandeh = "ccAfradRanandeh";
    private static final String COLUMN_ccAfradRanandehJaygozin = "ccAfradRanandehJaygozin";
    private static final String COLUMN_ccAfradGorohForosh = "ccAfradGorohForosh";
    private static final String COLUMN_ccAfradSarGorohForosh = "ccAfradSarGorohForosh";

    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDarkhastFaktorAfradForosh() {
        return COLUMN_ccDarkhastFaktorAfradForosh;
    }
    public static String COLUMN_ccDarkhastFaktor() {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_ccAfradForoshandeh() {
        return COLUMN_ccAfradForoshandeh;
    }
    public static String COLUMN_ccAfradForoshandehJaygozin() {
        return COLUMN_ccAfradForoshandehJaygozin;
    }
    public static String COLUMN_ccAfradMamorPakhsh() {
        return COLUMN_ccAfradMamorPakhsh;
    }
    public static String COLUMN_ccAfradMamorPakhshJaygozin() {
        return COLUMN_ccAfradMamorPakhshJaygozin;
    }
    public static String COLUMN_ccAfradRanandeh() {
        return COLUMN_ccAfradRanandeh;
    }
    public static String COLUMN_ccAfradRanandehJaygozin() {
        return COLUMN_ccAfradRanandehJaygozin;
    }
    public static String COLUMN_ccAfradGorohForosh() {
        return COLUMN_ccAfradGorohForosh;
    }
    public static String COLUMN_ccAfradSarGorohForosh() {
        return COLUMN_ccAfradSarGorohForosh;
    }



    private long ccDarkhastFaktorAfradForosh;
    private long ccDarkhastFaktor;
    private int ccAfradForoshandeh;
    private int ccAfradForoshandehJaygozin;
    private int ccAfradMamorPakhsh;
    private int ccAfradMamorPakhshJaygozin;
    private int ccAfradRanandeh;
    private int ccAfradRanandehJaygozin;
    private int ccAfradGorohForosh;
    private int ccAfradSarGorohForosh;


    public long getCcDarkhastFaktorAfradForosh() {
        return ccDarkhastFaktorAfradForosh;
    }

    public void setCcDarkhastFaktorAfradForosh(long ccDarkhastFaktorAfradForosh) {
        this.ccDarkhastFaktorAfradForosh = ccDarkhastFaktorAfradForosh;
    }

    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcAfradForoshandeh() {
        return ccAfradForoshandeh;
    }

    public void setCcAfradForoshandeh(int ccAfradForoshandeh) {
        this.ccAfradForoshandeh = ccAfradForoshandeh;
    }

    public int getCcAfradForoshandehJaygozin() {
        return ccAfradForoshandehJaygozin;
    }

    public void setCcAfradForoshandehJaygozin(int ccAfradForoshandehJaygozin) {
        this.ccAfradForoshandehJaygozin = ccAfradForoshandehJaygozin;
    }

    public int getCcAfradMamorPakhsh() {
        return ccAfradMamorPakhsh;
    }

    public void setCcAfradMamorPakhsh(int ccAfradMamorPakhsh) {
        this.ccAfradMamorPakhsh = ccAfradMamorPakhsh;
    }

    public int getCcAfradMamorPakhshJaygozin() {
        return ccAfradMamorPakhshJaygozin;
    }

    public void setCcAfradMamorPakhshJaygozin(int ccAfradMamorPakhshJaygozin) {
        this.ccAfradMamorPakhshJaygozin = ccAfradMamorPakhshJaygozin;
    }

    public int getCcAfradRanandeh() {
        return ccAfradRanandeh;
    }

    public void setCcAfradRanandeh(int ccAfradRanandeh) {
        this.ccAfradRanandeh = ccAfradRanandeh;
    }

    public int getCcAfradRanandehJaygozin() {
        return ccAfradRanandehJaygozin;
    }

    public void setCcAfradRanandehJaygozin(int ccAfradRanandehJaygozin) {
        this.ccAfradRanandehJaygozin = ccAfradRanandehJaygozin;
    }

    public int getCcAfradGorohForosh() {
        return ccAfradGorohForosh;
    }

    public void setCcAfradGorohForosh(int ccAfradGorohForosh) {
        this.ccAfradGorohForosh = ccAfradGorohForosh;
    }

    public int getCcAfradSarGorohForosh() {
        return ccAfradSarGorohForosh;
    }

    public void setCcAfradSarGorohForosh(int ccAfradSarGorohForosh) {
        this.ccAfradSarGorohForosh = ccAfradSarGorohForosh;
    }


    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccDarkhastFaktorAfradForosh" , ccDarkhastFaktorAfradForosh);
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("ccAfradForoshandeh" , ccAfradForoshandeh);
            jsonObject.put("ccAfradForoshandehJaygozin" , ccAfradForoshandehJaygozin);
            jsonObject.put("ccAfradMamorPakhsh" , ccAfradMamorPakhsh);
            jsonObject.put("ccAfradMamorPakhshJaygozin" , ccAfradMamorPakhshJaygozin);
            jsonObject.put("ccAfradRanandeh" , ccAfradRanandeh);
            jsonObject.put("ccAfradRanandehJaygozin" , ccAfradRanandehJaygozin);
            jsonObject.put("ccAfradGorohForosh" , ccAfradGorohForosh);
            jsonObject.put("ccAfradSarGorohForosh" , ccAfradSarGorohForosh);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }

}
