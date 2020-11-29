package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class DarkhastFaktorRoozSortModel
{

    private static final String TABLE_NAME = "DarkhastFaktorRoozSort";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_Sort = "Sort";

    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccDarkhastFaktor()
    {
        return COLUMN_ccDarkhastFaktor;
    }
    public static String COLUMN_Sort()
    {
        return COLUMN_Sort;
    }



    private long ccDarkhastFaktor;
    private int Sort;

    public long getCcDarkhastFaktor()
    {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor)
    {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getSort()
    {
        return Sort;
    }

    public void setSort(int sort)
    {
        Sort = sort;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "DarkhastFaktorRoozSortModel{" +
                "ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", Sort=" + Sort +
                '}';
    }

}
