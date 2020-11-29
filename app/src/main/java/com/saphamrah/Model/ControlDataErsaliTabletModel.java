package com.saphamrah.Model;

import org.json.JSONObject;

public class ControlDataErsaliTabletModel
{

    private float ccDarkhastFaktor;
    private int ccForoshandeh;
    private double mablaghKolDarkhast;
    private int tedadAghlamFaktor;
    private double mablaghTakhfifDarkhastTitr;
    private double mablaghTakhfifDarkhastSatr;
    private double mablaghTakhfifNaghdiDarkhastTitr;
    private int tedadAghlamTakhfif;
    private double mablaghKolVosolTitr;
    private double mablaghKolVosolSatr;
    /*private int TedadVosolTitr;
    private int TedadVosolSatr;*/
    private double mablaghKolMarjoee;
    private int tedadAghlamMarjoee;
    private int tedadJayeze;


    public float getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(float ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public double getMablaghKolDarkhast() {
        return mablaghKolDarkhast;
    }

    public void setMablaghKolDarkhast(double mablaghKolDarkhast) {
        this.mablaghKolDarkhast = mablaghKolDarkhast;
    }

    public int getTedadAghlamFaktor() {
        return tedadAghlamFaktor;
    }

    public void setTedadAghlamFaktor(int tedadAghlamFaktor) {
        this.tedadAghlamFaktor = tedadAghlamFaktor;
    }

    public double getMablaghTakhfifDarkhastTitr() {
        return mablaghTakhfifDarkhastTitr;
    }

    public void setMablaghTakhfifDarkhastTitr(double mablaghTakhfifDarkhastTitr) {
        this.mablaghTakhfifDarkhastTitr = mablaghTakhfifDarkhastTitr;
    }

    public double getMablaghTakhfifDarkhastSatr() {
        return mablaghTakhfifDarkhastSatr;
    }

    public void setMablaghTakhfifDarkhastSatr(double mablaghTakhfifDarkhastSatr) {
        this.mablaghTakhfifDarkhastSatr = mablaghTakhfifDarkhastSatr;
    }

    public double getMablaghTakhfifNaghdiDarkhastTitr()
    {
        return mablaghTakhfifNaghdiDarkhastTitr;
    }

    public void setMablaghTakhfifNaghdiDarkhastTitr(double mablaghTakhfifNaghdiDarkhastTitr)
    {
        this.mablaghTakhfifNaghdiDarkhastTitr = mablaghTakhfifNaghdiDarkhastTitr;
    }

    public int getTedadAghlamTakhfif() {
        return tedadAghlamTakhfif;
    }

    public void setTedadAghlamTakhfif(int tedadAghlamTakhfif) {
        this.tedadAghlamTakhfif = tedadAghlamTakhfif;
    }

    public double getMablaghKolVosolTitr() {
        return mablaghKolVosolTitr;
    }

    public void setMablaghKolVosolTitr(double mablaghKolVosolTitr) {
        this.mablaghKolVosolTitr = mablaghKolVosolTitr;
    }

    public double getMablaghKolVosolSatr() {
        return mablaghKolVosolSatr;
    }

    public void setMablaghKolVosolSatr(double mablaghKolVosolSatr) {
        this.mablaghKolVosolSatr = mablaghKolVosolSatr;
    }

    public double getMablaghKolMarjoee() {
        return mablaghKolMarjoee;
    }

    public void setMablaghKolMarjoee(double mablaghKolMarjoee) {
        this.mablaghKolMarjoee = mablaghKolMarjoee;
    }

    public int getTedadAghlamMarjoee() {
        return tedadAghlamMarjoee;
    }

    public void setTedadAghlamMarjoee(int tedadAghlamMarjoee) {
        this.tedadAghlamMarjoee = tedadAghlamMarjoee;
    }

    public int getTedadJayeze() {
        return tedadJayeze;
    }

    public void setTedadJayeze(int tedadJayeze) {
        this.tedadJayeze = tedadJayeze;
    }



    public JSONObject toJson()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccDarkhastFaktor" , ccDarkhastFaktor);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("Tedad_AghlamFaktor" , tedadAghlamFaktor);
            jsonObject.put("MablaghKolDarkhast" , mablaghKolDarkhast);
            jsonObject.put("Tedad_AghlamTakhfif" , tedadAghlamTakhfif);
            jsonObject.put("Mablagh_TakhfifDarkhastTitr" , mablaghTakhfifDarkhastTitr);
            jsonObject.put("Mablagh_TakhfifDarkhastSatr" , mablaghTakhfifDarkhastSatr);
            jsonObject.put("Mablagh_TakhfifNaghdiDarkhastTitr" , mablaghTakhfifNaghdiDarkhastTitr);
            jsonObject.put("Mablagh_VosolTitr" , mablaghKolVosolTitr);
            jsonObject.put("Mablagh_VosolSatr" , mablaghKolVosolSatr);
            jsonObject.put("Tedad_Marjoee" , tedadAghlamMarjoee);
            jsonObject.put("Mablagh_Marjoee" , mablaghKolMarjoee);
            jsonObject.put("Tedad_Jayezeh" , mablaghKolMarjoee);
            jsonObject.put("Tedad_Jayezeh" , tedadJayeze);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jsonObject;
    }


}
