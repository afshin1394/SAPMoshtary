package com.saphamrah.UIModel;

import androidx.annotation.NonNull;

public class JayezehEntekhabiMojodiModel
{

    private static final String COLUMN_ccJayezeh = "ccJayezeh";
    private static final String COLUMN_ccJayezehSatr = "ccJayezehSatr";
    private static final String COLUMN_ccJayezehSatrKala = "ccJayezehSatrKala";
    private static final String COLUMN_ccKala = "ccKala";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_CodeKalaOld = "CodeKalaOld";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_MablaghForosh = "MablaghForosh";
    private static final String COLUMN_ExtraProp_Tedad = "ExtraProp_Tedad";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";
    private static final String COLUMN_CodeNoe = "CodeNoe";
    private static final String COLUMN_ccTakhfifHajmi = "ccTakhfifHajmi";
    private static final String COLUMN_ccKalaCodeAsli = "ccKalaCodeAsli";


    private static final String COLUMN_ccKalaMojodi = "ccKalaMojodi";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_Tedad = "Tedad";
    private static final String COLUMN_ccDarkhastFaktor = "ccDarkhastFaktor";
    private static final String COLUMN_TarikhDarkhast = "TarikhDarkhast";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_TarikhTolid = "TarikhTolid";
    private static final String COLUMN_ZamaneSabt = "ZamaneSabt";
    private static final String COLUMN_TarikhEngheza = "TarikhEngheza";
    private static final String COLUMN_GheymatMasrafKonandeh = "GheymatMasrafKonandeh";
    private static final String COLUMN_GheymatForosh = "GheymatForosh";
    private static final String COLUMN_GheymatKharid = "GheymatKharid";
    private static final String COLUMN_ccTaminKonandeh = "ccTaminKonandeh";
    private static final String COLUMN_Max_Mojody = "Max_Mojody";
    private static final String COLUMN_Max_MojodyByShomarehBach = "Max_MojodyByShomarehBach";


    public static String COLUMN_ccJayezeh() {
        return COLUMN_ccJayezeh;
    }
    public static String COLUMN_ccJayezehSatr() {
        return COLUMN_ccJayezehSatr;
    }
    public static String COLUMN_ccJayezehSatrKala() {
        return COLUMN_ccJayezehSatrKala;
    }
    public static String COLUMN_ccKala() {
        return COLUMN_ccKala;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_CodeKalaOld() {
        return COLUMN_CodeKalaOld;
    }
    public static String COLUMN_NameKala() {
        return COLUMN_NameKala;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_MablaghForosh() {
        return COLUMN_MablaghForosh;
    }
    public static String COLUMN_ExtraProp_Tedad() {
        return COLUMN_ExtraProp_Tedad;
    }
    public static String COLUMN_ccNoeMoshtary() {
        return COLUMN_ccNoeMoshtary;
    }
    public static String COLUMN_CodeNoe() {
        return COLUMN_CodeNoe;
    }
    public static String COLUMN_ccTakhfifHajmi() {
        return COLUMN_ccTakhfifHajmi;
    }
    public static String COLUMN_ccKalaCodeAsli() {
        return COLUMN_ccKalaCodeAsli;
    }
    public static String COLUMN_ccKalaMojodi() {
        return COLUMN_ccKalaMojodi;
    }
    public static String COLUMN_Tedad() {
        return COLUMN_Tedad;
    }
    public static String COLUMN_GheymatMasrafKonandeh() {
        return COLUMN_GheymatMasrafKonandeh;
    }
    public static String COLUMN_GheymatForosh() {
        return COLUMN_GheymatForosh;
    }
    public static String COLUMN_GheymatKharid() {
        return COLUMN_GheymatKharid;
    }
    public static String COLUMN_TarikhEngheza() {
        return COLUMN_TarikhEngheza;
    }
    public static String COLUMN_Max_Mojody() {
        return COLUMN_Max_Mojody;
    }
    public static String COLUMN_Max_MojodyByShomarehBach() {
        return COLUMN_Max_MojodyByShomarehBach;
    }


    //JayezehEntekhabiModel
    private int ccJayezeh;
    private int ccJayezehSatr;
    private int ccJayezehSatrKala;
    private int ccKala;
    private int ccKalaCode;
    private int ccKalaCodeAsli;
    private String CodeKala;
    private String CodeKalaOld;
    private String NameKala;
    private int ccMarkazForosh;
    private double MablaghForosh;
    private int ccNoeMoshtary;
    private int CodeNoe;
    private int ccTakhfifHajmi;
    private int ExtraProp_Tedad;

    //KalaMojodiModel
    private int ccKalaMojodi;
    private int ccForoshandeh;
    private int Tedad;
    private long ccDarkhastFaktor;
    private String TarikhDarkhast;
    private String ShomarehBach;
    private String TarikhTolid;
    private String ZamaneSabt;
    private double GheymatMasrafKonandeh;
    private double GheymatForosh;
    private double GheymatKharid;
    private int ccTaminKonandeh;
    private int Max_Mojody;
    private int Max_MojodyByShomarehBach;


    //Kala
    private String TarikhEngheza;

    //DarkhastFaktorJayezeh
    private int selectedCount;


    public int getCcJayezeh() {
        return ccJayezeh;
    }

    public void setCcJayezeh(int ccJayezeh) {
        this.ccJayezeh = ccJayezeh;
    }

    public int getCcJayezehSatr() {
        return ccJayezehSatr;
    }

    public void setCcJayezehSatr(int ccJayezehSatr) {
        this.ccJayezehSatr = ccJayezehSatr;
    }

    public int getCcJayezehSatrKala() {
        return ccJayezehSatrKala;
    }

    public void setCcJayezehSatrKala(int ccJayezehSatrKala) {
        this.ccJayezehSatrKala = ccJayezehSatrKala;
    }

    public int getCcKala() {
        return ccKala;
    }

    public void setCcKala(int ccKala) {
        this.ccKala = ccKala;
    }

    public int getCcKalaCode() {
        return ccKalaCode;
    }

    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }

    public int getCcKalaCodeAsli() {
        return ccKalaCodeAsli;
    }

    public void setCcKalaCodeAsli(int ccKalaCodeAsli) {
        this.ccKalaCodeAsli = ccKalaCodeAsli;
    }

    public String getCodeKala() {
        return CodeKala;
    }

    public void setCodeKala(String codeKala) {
        CodeKala = codeKala;
    }

    public String getCodeKalaOld() {
        return CodeKalaOld;
    }

    public void setCodeKalaOld(String codeKalaOld) {
        CodeKalaOld = codeKalaOld;
    }

    public String getNameKala() {
        return NameKala;
    }

    public void setNameKala(String nameKala) {
        NameKala = nameKala;
    }

    public int getCcMarkazForosh() {
        return ccMarkazForosh;
    }

    public void setCcMarkazForosh(int ccMarkazForosh) {
        this.ccMarkazForosh = ccMarkazForosh;
    }

    public double getMablaghForosh() {
        return MablaghForosh;
    }

    public void setMablaghForosh(double mablaghForosh) {
        MablaghForosh = mablaghForosh;
    }

    public int getCcNoeMoshtary() {
        return ccNoeMoshtary;
    }

    public void setCcNoeMoshtary(int ccNoeMoshtary) {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }

    public int getCodeNoe() {
        return CodeNoe;
    }

    public void setCodeNoe(int codeNoe) {
        CodeNoe = codeNoe;
    }

    public int getCcTakhfifHajmi() {
        return ccTakhfifHajmi;
    }

    public void setCcTakhfifHajmi(int ccTakhfifHajmi) {
        this.ccTakhfifHajmi = ccTakhfifHajmi;
    }

    public int getExtraProp_Tedad() {
        return ExtraProp_Tedad;
    }

    public void setExtraProp_Tedad(int extraProp_Tedad) {
        ExtraProp_Tedad = extraProp_Tedad;
    }

    public int getCcKalaMojodi() {
        return ccKalaMojodi;
    }

    public void setCcKalaMojodi(int ccKalaMojodi) {
        this.ccKalaMojodi = ccKalaMojodi;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getTedad() {
        return Tedad;
    }

    public void setTedad(int tedad) {
        Tedad = tedad;
    }

    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public String getTarikhDarkhast() {
        return TarikhDarkhast;
    }

    public void setTarikhDarkhast(String tarikhDarkhast) {
        TarikhDarkhast = tarikhDarkhast;
    }

    public String getShomarehBach() {
        return ShomarehBach;
    }

    public void setShomarehBach(String shomarehBach) {
        ShomarehBach = shomarehBach;
    }

    public String getTarikhTolid() {
        return TarikhTolid;
    }

    public void setTarikhTolid(String tarikhTolid) {
        TarikhTolid = tarikhTolid;
    }

    public String getZamaneSabt()
    {
        return ZamaneSabt;
    }

    public void setZamaneSabt(String zamaneSabt)
    {
        ZamaneSabt = zamaneSabt;
    }

    public double getGheymatMasrafKonandeh() {
        return GheymatMasrafKonandeh;
    }

    public void setGheymatMasrafKonandeh(double gheymatMasrafKonandeh) {
        GheymatMasrafKonandeh = gheymatMasrafKonandeh;
    }

    public double getGheymatForosh() {
        return GheymatForosh;
    }

    public void setGheymatForosh(double gheymatForosh) {
        GheymatForosh = gheymatForosh;
    }

    public double getGheymatKharid() {
        return GheymatKharid;
    }

    public void setGheymatKharid(double gheymatKharid) {
        GheymatKharid = gheymatKharid;
    }

    public int getCcTaminKonandeh() {
        return ccTaminKonandeh;
    }

    public void setCcTaminKonandeh(int ccTaminKonandeh) {
        this.ccTaminKonandeh = ccTaminKonandeh;
    }

    public String getTarikhEngheza()
    {
        return TarikhEngheza;
    }

    public void setTarikhEngheza(String tarikhEngheza)
    {
        TarikhEngheza = tarikhEngheza;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public int getMax_Mojody()
    {
        return Max_Mojody;
    }

    public void setMax_Mojody(int max_Mojody)
    {
        Max_Mojody = max_Mojody;
    }

    public int getMax_MojodyByShomarehBach()
    {
        return Max_MojodyByShomarehBach;
    }

    public void setMax_MojodyByShomarehBach(int max_MojodyByShomarehBach)
    {
        Max_MojodyByShomarehBach = max_MojodyByShomarehBach;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "JayezehEntekhabiMojodiModel{" +
                "ccJayezeh=" + ccJayezeh +
                ", ccJayezehSatr=" + ccJayezehSatr +
                ", ccJayezehSatrKala=" + ccJayezehSatrKala +
                ", ccKala=" + ccKala +
                ", ccKalaCode=" + ccKalaCode +
                ", ccKalaCodeAsli=" + ccKalaCodeAsli +
                ", CodeKala='" + CodeKala + '\'' +
                ", CodeKalaOld='" + CodeKalaOld + '\'' +
                ", NameKala='" + NameKala + '\'' +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", MablaghForosh=" + MablaghForosh +
                ", ccNoeMoshtary=" + ccNoeMoshtary +
                ", CodeNoe=" + CodeNoe +
                ", ccTakhfifHajmi=" + ccTakhfifHajmi +
                ", ExtraProp_Tedad=" + ExtraProp_Tedad +
                ", ccKalaMojodi=" + ccKalaMojodi +
                ", ccForoshandeh=" + ccForoshandeh +
                ", Tedad=" + Tedad +
                ", ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", TarikhDarkhast='" + TarikhDarkhast + '\'' +
                ", ShomarehBach='" + ShomarehBach + '\'' +
                ", TarikhTolid='" + TarikhTolid + '\'' +
                ", ZamaneSabt='" + ZamaneSabt + '\'' +
                ", GheymatMasrafKonandeh=" + GheymatMasrafKonandeh +
                ", GheymatForosh=" + GheymatForosh +
                ", Gheymatkharid=" + GheymatKharid +
                ", ccTaminKonandeh=" + ccTaminKonandeh +
                ", Max_Mojody=" + Max_Mojody +
                ", Max_MojodyByShomarehBach=" + Max_MojodyByShomarehBach +
                ", TarikhEngheza='" + TarikhEngheza + '\'' +
                ", selectedCount=" + selectedCount +
                '}';
    }
}
