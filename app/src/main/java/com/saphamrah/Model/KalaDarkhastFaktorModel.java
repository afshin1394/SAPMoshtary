package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class KalaDarkhastFaktorModel
{

    private int ccKalaCode;
    private String tarikhFaktor;
    private int tedadFaktor;


    public int getCcKalaCode()
    {
        return ccKalaCode;
    }
    public void setCcKalaCode(int ccKalaCode)
    {
        this.ccKalaCode = ccKalaCode;
    }

    public String getTarikhFaktor()
    {
        return tarikhFaktor;
    }
    public void setTarikhFaktor(String tarikhFaktor)
    {
        this.tarikhFaktor = tarikhFaktor;
    }

    public int getTedadFaktor()
    {
        return tedadFaktor;
    }
    public void setTedadFaktor(int tedadFaktor)
    {
        this.tedadFaktor = tedadFaktor;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "TedadPishnahadiDarkhast{" +
                "ccKalaCode=" + ccKalaCode +
                ", tarikhFaktor='" + tarikhFaktor + '\'' +
                ", tedadFaktor=" + tedadFaktor +
                '}';
    }
}
