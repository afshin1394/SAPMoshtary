package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class SystemConfigTabletModel
{

    private static final String TABLE_NAME = "SystemConfig_Tablet";
    /*private static final String COLUMN_UpdateJayezehTakhfif_Tablet = "UpdateJayezehTakhfif_Tablet";
    private static final String COLUMN_ccMarkaz_GetData = "ccMarkaz_GetData";
    private static final String COLUMN_UpdateGallery_Tablet = "UpdateGallery_Tablet";
    private static final String COLUMN_DarkhastViewType = "DarkhastViewType";
    private static final String COLUMN_FooterViewType = "FooterViewType";*/
    private static final String COLUMN_NamePrinter = "NamePrinter";
    private static final String COLUMN_SizePrint = "SizePrint";
    private static final String COLUMN_NoeFaktorPrint = "NoeFaktorPrint";
    private static final String COLUMN_NoeNaghshe="NoeNaghshe";
    private static final String COLUMN_GoodsNumberEachPage="GoodsShowNumberEachPage";
    private static final String COLUMN_SortTreasuryList="SortTreasuryList";
    /*private static final String COLUMN_DateServer = "DateServer";
    private static final String COLUMN_CrispID = "CrispID";*/


    public static String TableName() {
        return TABLE_NAME;
    }
    /*public static String COLUMN_UpdateJayezehTakhfif_Tablet() {
        return COLUMN_UpdateJayezehTakhfif_Tablet;
    }
    public static String COLUMN_ccMarkaz_GetData() {
        return COLUMN_ccMarkaz_GetData;
    }
    public static String COLUMN_UpdateGallery_Tablet() {
        return COLUMN_UpdateGallery_Tablet;
    }
    public static String COLUMN_DarkhastViewType() {
        return COLUMN_DarkhastViewType;
    }
    public static String COLUMN_FooterViewType() {
        return COLUMN_FooterViewType;
    }*/
    public static String COLUMN_NamePrinter() {
        return COLUMN_NamePrinter;
    }
    public static String COLUMN_SizePrint() {
        return COLUMN_SizePrint;
    }
    public static String COLUMN_NoeFaktorPrint() {
        return COLUMN_NoeFaktorPrint;
    }
    public static String COLUMN_NoeNaghshe(){ return COLUMN_NoeNaghshe;}

    public static String COLUMN_GoodsShowNumberEachPage() {
        return COLUMN_GoodsNumberEachPage;
    }

    public static String COLUMN_SortTreasuryList(){ return COLUMN_SortTreasuryList;}
/*public static String COLUMN_DateServer() {
        return COLUMN_DateServer;
    }
    public static String COLUMN_CrispID() {
        return COLUMN_CrispID;
    }*/



    private int UpdateJayezehTakhfif_Tablet;
    public int getUpdateJayezehTakhfif_Tablet() {
        return UpdateJayezehTakhfif_Tablet;
    }
    public void setUpdateJayezehTakhfif_Tablet(int updateJayezehTakhfif_Tablet) {
        UpdateJayezehTakhfif_Tablet = updateJayezehTakhfif_Tablet;
    }


    private int ccMarkaz_GetData;
    public int getCcMarkaz_GetData() {
        return ccMarkaz_GetData;
    }
    public void setCcMarkaz_GetData(int ccMarkaz_GetData) {
        this.ccMarkaz_GetData = ccMarkaz_GetData;
    }


    private int UpdateGallery_Tablet;
    public int getUpdateGallery_Tablet() {
        return UpdateGallery_Tablet;
    }
    public void setUpdateGallery_Tablet(int updateGallery_Tablet) {
        UpdateGallery_Tablet = updateGallery_Tablet;
    }


    private int DarkhastViewType;
    public int getDarkhastViewType() {
        return DarkhastViewType;
    }
    public void setDarkhastViewType(int darkhastViewType) {
        DarkhastViewType = darkhastViewType;
    }


    private int FooterViewType;
    public int getFooterViewType() {
        return FooterViewType;
    }
    public void setFooterViewType(int footerViewType) {
        FooterViewType = footerViewType;
    }


    private int NamePrinter;
    public int getNamePrinter() {
        return NamePrinter;
    }
    public void setNamePrinter(int namePrinter) {
        NamePrinter = namePrinter;
    }


    private int SizePrint;
    public int getSizePrint() {
        return SizePrint;
    }
    public void setSizePrint(int sizePrint) {
        SizePrint = sizePrint;
    }

    private int NoeFaktorPrint;
    public int getNoeFaktorPrint()
    {
        return NoeFaktorPrint;
    }
    public void setNoeFaktorPrint(int noeFaktorPrint)
    {
        NoeFaktorPrint = noeFaktorPrint;
    }

    private String DateServer;
    public String getDateServer() {
        return DateServer;
    }
    public void setDateServer(String dateServer) {
        DateServer = dateServer;
    }


    private String CrispID;
    public String getCrispID() {
        return CrispID;
    }
    public void setCrispID(String crispID) {
        CrispID = crispID;
    }


    private int NoeNaghshe;

    public int getNoeNaghshe() {
        return NoeNaghshe;
    }

    public void setNoeNaghshe(int noeNaghshe) {
        NoeNaghshe = noeNaghshe;
    }



    private int GoodsShowNumberEachPage;

    public int getGoodsShowNumberEachPage() {
        return GoodsShowNumberEachPage;
    }

    public void setGoodsShowNumberEachPage(int goodsShowNumberEachPage) {
        GoodsShowNumberEachPage = goodsShowNumberEachPage;
    }

    private int sortTreasuryList;

    public int getSortTreasuryList() {
        return sortTreasuryList;
    }

    public void setSortTreasuryList(int sortTreasuryList) {
        this.sortTreasuryList = sortTreasuryList;
    }

    @Override
    public String toString() {
        return "SystemConfigTabletModel{" +
                "UpdateJayezehTakhfif_Tablet=" + UpdateJayezehTakhfif_Tablet +
                ", ccMarkaz_GetData=" + ccMarkaz_GetData +
                ", UpdateGallery_Tablet=" + UpdateGallery_Tablet +
                ", DarkhastViewType=" + DarkhastViewType +
                ", FooterViewType=" + FooterViewType +
                ", NamePrinter=" + NamePrinter +
                ", SizePrint=" + SizePrint +
                ", NoeFaktorPrint=" + NoeFaktorPrint +
                ", DateServer='" + DateServer + '\'' +
                ", CrispID='" + CrispID + '\'' +
                ", NoeNaghshe=" + NoeNaghshe +
                ", GoodsShowNumberEachPage=" + GoodsShowNumberEachPage +
                ", SortTreasuryList=" + sortTreasuryList +
                '}';
    }
}
