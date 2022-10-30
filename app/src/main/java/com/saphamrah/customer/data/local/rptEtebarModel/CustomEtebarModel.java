package com.saphamrah.customer.data.local.rptEtebarModel;

import java.util.ArrayList;
import java.util.List;

public class CustomEtebarModel {
    //MoshtaryModel - ForoshandeEtebarModel
    private int ccForoshandehMoshtary;
    private long SaghfEtebarRiali;
    private int SaghfEtebarTedadi;
    private int SaghfEtebarModat;

    private long EtebarRialAsnadShakhsi;
    private int EtebarTedadAsnadShakhsi;
    private int EtebarModatAsnadShakhsi;
    private long EtebarRialAsnadMoshtary;
    private int EtebarTedadAsnadMoshtary;
    private int EtebarModatAsnadMoshtary;

    private long EtebarRialMoavagh;
    private int EtebarTedadMoavagh;
    private int EtebarModatMoavagh;
    private long EtebarRialBargashty;
    private int EtebarTedadBargashty;
    private int EtebarModatBargashty;

    private long RialAsnad;
    private int TedadAsnad;
    private int ModatAsnad;
    private long RialMoavagh;
    private int TedadMoavagh;
    private int ModatMoavagh;
    private long RialBargashty;
    private int TedadBargashty;
    private int ModatBargashty;

    public CustomEtebarModel(int ccForoshandehMoshtary, long saghfEtebarRiali, int saghfEtebarTedadi, int saghfEtebarModat, long etebarRialAsnadShakhsi,
                             int etebarTedadAsnadShakhsi, int etebarModatAsnadShakhsi, long etebarRialAsnadMoshtary, int etebarTedadAsnadMoshtary,
                             int etebarModatAsnadMoshtary, long etebarRialMoavagh, int etebarTedadMoavagh, int etebarModatMoavagh, long etebarRialBargashty,
                             int etebarTedadBargashty, int etebarModatBargashty, long rialAsnad, int tedadAsnad, int modatAsnad, long rialMoavagh,
                             int tedadMoavagh, int modatMoavagh, long rialBargashty, int tedadBargashty, int modatBargashty)
    {
        this.ccForoshandehMoshtary = ccForoshandehMoshtary;
        SaghfEtebarRiali = saghfEtebarRiali;
        SaghfEtebarTedadi = saghfEtebarTedadi;
        SaghfEtebarModat = saghfEtebarModat;
        EtebarRialAsnadShakhsi = etebarRialAsnadShakhsi;
        EtebarTedadAsnadShakhsi = etebarTedadAsnadShakhsi;
        EtebarModatAsnadShakhsi = etebarModatAsnadShakhsi;
        EtebarRialAsnadMoshtary = etebarRialAsnadMoshtary;
        EtebarTedadAsnadMoshtary = etebarTedadAsnadMoshtary;
        EtebarModatAsnadMoshtary = etebarModatAsnadMoshtary;
        EtebarRialMoavagh = etebarRialMoavagh;
        EtebarTedadMoavagh = etebarTedadMoavagh;
        EtebarModatMoavagh = etebarModatMoavagh;
        EtebarRialBargashty = etebarRialBargashty;
        EtebarTedadBargashty = etebarTedadBargashty;
        EtebarModatBargashty = etebarModatBargashty;
        RialAsnad = rialAsnad;
        TedadAsnad = tedadAsnad;
        ModatAsnad = modatAsnad;
        RialMoavagh = rialMoavagh;
        TedadMoavagh = tedadMoavagh;
        ModatMoavagh = modatMoavagh;
        RialBargashty = rialBargashty;
        TedadBargashty = tedadBargashty;
        ModatBargashty = modatBargashty;
    }

    public List<RptEtebarParentModel> generateEtebarList() {
        List<RptEtebarParentModel> rptEtebarMoldeList = new ArrayList<>();
        RptEtebarRialiModel rptEtebarRiali;
        RptEtebarTedadiModatModel rptEtebarTedadi;
        RptEtebarTedadiModatModel rptEtebarModat;

        //set saghf etebarat
        rptEtebarRiali = new RptEtebarRialiModel(SaghfEtebarRiali, RialMoavagh + RialAsnad + RialBargashty, RptEtebarType.Saghf);
        rptEtebarMoldeList.add(rptEtebarRiali);
        rptEtebarTedadi = new RptEtebarTedadiModatModel(SaghfEtebarTedadi, TedadMoavagh + TedadAsnad + TedadBargashty, RptEtebarType.Saghf, EtebarType.Tedati);
        rptEtebarMoldeList.add(rptEtebarTedadi);
        //rptEtebarModat = new RptEtebarTedadiModatModel(SaghfEtebarModat, ModatMoavagh +ModatAsnad + ModatBargashty, RptEtebarType.Saghf, EtebarType.ModatEtebar);

        rptEtebarModat = new RptEtebarTedadiModatModel(SaghfEtebarModat, (Math.max(ModatMoavagh ,ModatAsnad)), RptEtebarType.Saghf, EtebarType.ModatEtebar);
        rptEtebarMoldeList.add(rptEtebarModat);

        //set etebar Moavagh
        rptEtebarRiali = new RptEtebarRialiModel(EtebarRialMoavagh, RialMoavagh, RptEtebarType.Moavagh);
        rptEtebarMoldeList.add(rptEtebarRiali);
        rptEtebarTedadi = new RptEtebarTedadiModatModel(EtebarTedadMoavagh, TedadMoavagh, RptEtebarType.Moavagh, EtebarType.Tedati);
        rptEtebarMoldeList.add(rptEtebarTedadi);
        rptEtebarModat = new RptEtebarTedadiModatModel(EtebarModatMoavagh, ModatMoavagh, RptEtebarType.Moavagh, EtebarType.ModatEtebar);
        rptEtebarMoldeList.add(rptEtebarModat);

        //set etebar Asnad (Shakhsi + Moshtari)
        rptEtebarRiali = new RptEtebarRialiModel(EtebarRialAsnadMoshtary + EtebarRialAsnadShakhsi, RialAsnad, RptEtebarType.Asnad);
        rptEtebarMoldeList.add(rptEtebarRiali);
        rptEtebarTedadi = new RptEtebarTedadiModatModel(EtebarTedadAsnadMoshtary + EtebarTedadAsnadShakhsi, TedadAsnad, RptEtebarType.Asnad, EtebarType.Tedati);
        rptEtebarMoldeList.add(rptEtebarTedadi);

        //set etebar Asnad Bargashti
        rptEtebarRiali = new RptEtebarRialiModel(EtebarRialBargashty, RialBargashty, RptEtebarType.AsnadBargashti);
        rptEtebarMoldeList.add(rptEtebarRiali);
        rptEtebarTedadi = new RptEtebarTedadiModatModel(EtebarTedadBargashty,TedadBargashty, RptEtebarType.AsnadBargashti, EtebarType.Tedati);
        rptEtebarMoldeList.add(rptEtebarTedadi);
        rptEtebarModat = new RptEtebarTedadiModatModel(EtebarModatBargashty,ModatBargashty, RptEtebarType.AsnadBargashti, EtebarType.ModatEtebar);
        rptEtebarMoldeList.add(rptEtebarModat);

        return rptEtebarMoldeList;
    }

    public int getCcForoshandehMoshtary() {
        return ccForoshandehMoshtary;
    }

    public long getSaghfEtebarRiali() {
        return SaghfEtebarRiali;
    }

    public int getSaghfEtebarTedadi() {
        return SaghfEtebarTedadi;
    }

    public int getSaghfEtebarModat() {
        return SaghfEtebarModat;
    }

    public long getEtebarRialAsnadShakhsi() {
        return EtebarRialAsnadShakhsi;
    }

    public int getEtebarTedadAsnadShakhsi() {
        return EtebarTedadAsnadShakhsi;
    }

    public int getEtebarModatAsnadShakhsi() {
        return EtebarModatAsnadShakhsi;
    }

    public long getEtebarRialAsnadMoshtary() {
        return EtebarRialAsnadMoshtary;
    }

    public int getEtebarTedadAsnadMoshtary() {
        return EtebarTedadAsnadMoshtary;
    }

    public int getEtebarModatAsnadMoshtary() {
        return EtebarModatAsnadMoshtary;
    }

    public long getEtebarRialMoavagh() {
        return EtebarRialMoavagh;
    }

    public int getEtebarTedadMoavagh() {
        return EtebarTedadMoavagh;
    }

    public int getEtebarModatMoavagh() {
        return EtebarModatMoavagh;
    }

    public long getEtebarRialBargashty() {
        return EtebarRialBargashty;
    }

    public int getEtebarTedadBargashty() {
        return EtebarTedadBargashty;
    }

    public int getEtebarModatBargashty() {
        return EtebarModatBargashty;
    }

    public long getRialAsnad() {
        return RialAsnad;
    }

    public int getTedadAsnad() {
        return TedadAsnad;
    }

    public int getModatAsnad() {
        return ModatAsnad;
    }

    public long getRialMoavagh() {
        return RialMoavagh;
    }

    public int getTedadMoavagh() {
        return TedadMoavagh;
    }

    public int getModatMoavagh() {
        return ModatMoavagh;
    }

    public long getRialBargashty() {
        return RialBargashty;
    }

    public int getTedadBargashty() {
        return TedadBargashty;
    }

    public int getModatBargashty() {
        return ModatBargashty;
    }


}
