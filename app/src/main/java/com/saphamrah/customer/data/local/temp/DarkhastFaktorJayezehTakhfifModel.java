package com.saphamrah.customer.data.local.temp;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DarkhastFaktorJayezehTakhfifModel  {

    private int NoeJayezehTakhfif;
    private int ccDarkhastFaktorJayezehTakhfif;
    private long ccDarkhastFaktor;
    private int ccJayezehTakhfif;
    private int ExtraProp_ccJayezehSatr;
    private String SharhJayezehTakhfif;
    private int CodeNoeJayezehTakhfif;
    private float MablaghJayezehTakhfif;
    private int ccKalaJayezeh;
    private int TedadJayezeh;
    private List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModelList;

    public DarkhastFaktorJayezehTakhfifModel(int ccDarkhastFaktorJayezehTakhfif, long ccDarkhastFaktor, int ccJayezehTakhfif, String sharhJayezehTakhfif, int tedadJayezeh,List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels) {
        this.ccDarkhastFaktorJayezehTakhfif = ccDarkhastFaktorJayezehTakhfif;
        this.ccDarkhastFaktor = ccDarkhastFaktor;
        this.ccJayezehTakhfif = ccJayezehTakhfif;
        this.SharhJayezehTakhfif = sharhJayezehTakhfif;
        this.TedadJayezeh = tedadJayezeh;
        this.jayezehEntekhabiMojodiModelList = jayezehEntekhabiMojodiModels;
    }


    public int getNoeJayezehTakhfif()
    {
        return NoeJayezehTakhfif;
    }
    public void setNoeJayezehTakhfif(int noeJayezehTakhfif)
    {
        NoeJayezehTakhfif = noeJayezehTakhfif;
    }


    public int getCcDarkhastFaktorJayezehTakhfif()
    {
        return ccDarkhastFaktorJayezehTakhfif;
    }
    public void setCcDarkhastFaktorJayezehTakhfif(int ccDarkhastFaktorJayezehTakhfif)
    {
        this.ccDarkhastFaktorJayezehTakhfif = ccDarkhastFaktorJayezehTakhfif;
    }


    public long getCcDarkhastFaktor()
    {
        return ccDarkhastFaktor;
    }
    public void setCcDarkhastFaktor(long ccDarkhastFaktor)
    {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }


    public int getCcJayezehTakhfif()
    {
        return ccJayezehTakhfif;
    }
    public void setCcJayezehTakhfif(int ccJayezehTakhfif)
    {
        this.ccJayezehTakhfif = ccJayezehTakhfif;
    }


    public int getExtraProp_ccJayezehSatr()
    {
        return ExtraProp_ccJayezehSatr;
    }
    public void setExtraProp_ccJayezehSatr(int extraProp_ccJayezehSatr)
    {
        ExtraProp_ccJayezehSatr = extraProp_ccJayezehSatr;
    }

    public String getSharhJayezehTakhfif()
    {
        return SharhJayezehTakhfif;
    }
    public void setSharhJayezehTakhfif(String sharhJayezehTakhfif)
    {
        SharhJayezehTakhfif = sharhJayezehTakhfif;
    }


    public int getCodeNoeJayezehTakhfif()
    {
        return CodeNoeJayezehTakhfif;
    }
    public void setCodeNoeJayezehTakhfif(int codeNoeJayezehTakhfif)
    {
        CodeNoeJayezehTakhfif = codeNoeJayezehTakhfif;
    }


    public float getMablaghJayezehTakhfif()
    {
        return MablaghJayezehTakhfif;
    }
    public void setMablaghJayezehTakhfif(float mablaghJayezehTakhfif)
    {
        MablaghJayezehTakhfif = mablaghJayezehTakhfif;
    }


    public int getCcKalaJayezeh()
    {
        return ccKalaJayezeh;
    }
    public void setCcKalaJayezeh(int ccKalaJayezeh)
    {
        this.ccKalaJayezeh = ccKalaJayezeh;
    }


    public int getTedadJayezeh()
    {
        return TedadJayezeh;
    }
    public void setTedadJayezeh(int tedadJayezeh)
    {
        TedadJayezeh = tedadJayezeh;
    }

    public List<JayezehEntekhabiMojodiModel> getJayezehEntekhabiMojodiModelList() {
        return jayezehEntekhabiMojodiModelList;
    }

    public void setJayezehEntekhabiMojodiModelList(List<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModelList) {
        this.jayezehEntekhabiMojodiModelList = jayezehEntekhabiMojodiModelList;
    }

    @Override
    public String toString() {
        return "DarkhastFaktorJayezehTakhfifModel{" +
                "NoeJayezehTakhfif=" + NoeJayezehTakhfif +
                ", ccDarkhastFaktorJayezehTakhfif=" + ccDarkhastFaktorJayezehTakhfif +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", ccJayezehTakhfif=" + ccJayezehTakhfif +
                ", ExtraProp_ccJayezehSatr=" + ExtraProp_ccJayezehSatr +
                ", SharhJayezehTakhfif='" + SharhJayezehTakhfif + '\'' +
                ", CodeNoeJayezehTakhfif=" + CodeNoeJayezehTakhfif +
                ", MablaghJayezehTakhfif=" + MablaghJayezehTakhfif +
                ", ccKalaJayezeh=" + ccKalaJayezeh +
                ", TedadJayezeh=" + TedadJayezeh +
                ", jayezehEntekhabiMojodiModelList=" + jayezehEntekhabiMojodiModelList +
                '}';
    }
}
