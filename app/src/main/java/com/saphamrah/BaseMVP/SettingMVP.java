package com.saphamrah.BaseMVP;

import android.content.Context;

import java.util.ArrayList;


public interface SettingMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void onGetSetting(String printerPaperSizeTitle, String printerTypeTitle, String printType, String mapServiceType, String goodsNumberItem,String sortTreasuryList);
        void onGetPaperSizesForPrint(ArrayList<String> arrayListTitles, ArrayList<String> arrayListValues);
        void onGetPrinterTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListValues);
        void onGetPrintTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListValues);
        void onGetMapServiceTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues);
        //TODO
        void onGetShowGoodsItemNumber(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues, ArrayList<String> arrayListImages);
        void showToast(int resId, int messageType, int duration);
        void showAlertDialog(int resId, int messageType);
        void onGetSortTreasuryList(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void getSetting();
        void getPaperSizesForPrint();
        void getPrinterTypes();
        void getMapServiceType();
        void getPrintTypes();
        //TODO
        void getGoodsShowItemNumber();

        void checkForSavePrinterPaperSize(String paperSizeValue);
        void checkForSavePrinterType(String printerTypeValue);
        void checkForSavePrintType(String printTypeValue);
        void checkForSaveMapTypes(String mapTypeValue);
        void checkSortTreasuryList(String mapTypeValue);
        //TODO
        void checkForSaveShowItemNumbers(String showItemNumbers);
        void clearData(String packageName);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void getSortTreasuryList();
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onGetSetting(String printerPaperSizeTitle, String printerTypeTitle, String printType, String mapServiceType, String goodsNumberItem ,String sortTreasuryList);
        void onGetPaperSizesForPrint(ArrayList<String> arrayListTitles, ArrayList<String> arrayListValues);
        void onGetPrinterTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListValues);
        void onGetPrintTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListValues);
        void onGetMapServiceTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues);

        //TODO
        void onGetShowGoodsItemNumber(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues, ArrayList<String> imageValues);
        void onFailedUpdatePrinterPaperSize();
        void onFailedUpdatePrinterType();
        void onFailedUpdateMapServiceType();
        void onFailedUpdateSortTreasuryList();
        void onFailedUpdatePrintType();
        void onFailedUpdateGoodsShowNumber();
        void onGetSortTreasuryList(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues);
    }


    interface ModelOps
    {
        void getSetting();
        void getPaperSizesForPrint();
        void getPrinterTypes();
        void getMapServiceTypes();
        void getPrintTypes();
        //TODO
        void getGoodsShowItemNumber();

        void savePrinterPaperSize(int paperSizeValue);
        void savePrinterType(int printerTypeValue);
        void savePrintType(int printTypeValue);
        void saveMapServiceType(int mapServiceTypeValue);
        void saveSortTreasuryList(int sortTreasuryList);
        //TODO
        void saveShowGoodsItemNumber(int showGoodsItemNumber);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void getSortTreasuryList();
    }


}
