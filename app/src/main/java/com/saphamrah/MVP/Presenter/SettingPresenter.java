package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.SettingMVP;
import com.saphamrah.MVP.Model.SettingModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SettingPresenter implements SettingMVP.PresenterOps , SettingMVP.RequiredPresenterOps
{

    private SettingMVP.ModelOps mModel;
    private WeakReference<SettingMVP.RequiredViewOps> mView;


    public SettingPresenter(SettingMVP.RequiredViewOps mView)
    {
        this.mView = new WeakReference<>(mView);
        mModel = new SettingModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(SettingMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getSetting()
    {
        mModel.getSetting();
    }

    @Override
    public void getPaperSizesForPrint()
    {
        mModel.getPaperSizesForPrint();
    }

    @Override
    public void getPrinterTypes()
    {
        mModel.getPrinterTypes();
    }

    @Override
    public void getMapServiceType() {
        mModel.getMapServiceTypes();
    }

    @Override
    public void getPrintTypes()
    {
        mModel.getPrintTypes();
    }

    @Override
    public void getGoodsShowItemNumber() {
        mModel.getGoodsShowItemNumber();
    }

    @Override
    public void checkForSavePrinterPaperSize(String paperSizeValue)
    {
        try
        {
            int paperSize = Integer.parseInt(paperSizeValue.trim());
            if (paperSize > 0)
            {
                mModel.savePrinterPaperSize(paperSize);
            }
            else
            {
                mView.get().showToast(R.string.invalidPaperSizeForPrint, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        }
        catch (Exception e)
        {
            mView.get().showToast(R.string.invalidPaperSizeForPrint, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            e.printStackTrace();
            checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingPresenter", "", "checkForSavePrinterPaperSize*value=" + paperSizeValue, "");
        }
    }

    @Override
    public void checkForSavePrinterType(String printerTypeValue)
    {
        try
        {
            int printerType = Integer.parseInt(printerTypeValue.trim());
            mModel.savePrinterType(printerType);
        }
        catch (Exception e)
        {
            mView.get().showToast(R.string.invalidPrinterType, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            e.printStackTrace();
            checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingPresenter", "", "checkForSavePrinterPaperSize*value=" + printerTypeValue, "");
        }
    }

    @Override
    public void checkForSavePrintType(String printTypeValue)
    {
        try
        {
            int printType = Integer.parseInt(printTypeValue.trim());
            mModel.savePrintType(printType);
        }
        catch (Exception e)
        {
            mView.get().showToast(R.string.invalidPrintType, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            e.printStackTrace();
            checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingPresenter", "", "checkForSavePrintType*value=" + printTypeValue, "");
        }
    }

    @Override
    public void checkForSaveMapTypes(String mapTypeValue) {
        try
        {
            int mapType = Integer.parseInt(mapTypeValue.trim());
            mModel.saveMapServiceType(mapType);
        }
        catch (Exception e)
        {
            mView.get().showToast(R.string.invalidMapType, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            e.printStackTrace();
            checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingPresenter", "", "checkForSaveMapType*value=" + mapTypeValue, "");
        }
    }

    @Override
    public void checkSortTreasuryList(String mapTypeValue) {
        try
        {

            int sortTreasuryList = Integer.parseInt(mapTypeValue.trim());
            mModel.saveSortTreasuryList(sortTreasuryList);
        }
        catch (Exception e)
        {
            mView.get().showToast(R.string.invalidsortTreasuryList, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            e.printStackTrace();
            checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingPresenter", "", "checkForSaveMapType*value=" + mapTypeValue, "");
        }
    }

    @Override
    public void checkForSaveShowItemNumbers(String showItemNumbers) {
        try
        {
            int goodsShowItemNumber = Integer.parseInt(showItemNumbers.trim());
            Log.i("itemNumbersd", "checkForSaveShowItemNumbers: "+goodsShowItemNumber);
            mModel.saveShowGoodsItemNumber(goodsShowItemNumber);
        }
        catch (Exception e)
        {
            mView.get().showToast(R.string.invalidMapType, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            e.printStackTrace();
            checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingPresenter", "", "checkForSaveGoodsShowItemNumber*value=" + showItemNumbers, "");
        }
    }

    @Override
    public void clearData(String packageName)
    {
        if (packageName == null || packageName.trim().equals(""))
        {
            mView.get().showAlertDialog(R.string.errorGetPackageName, Constants.FAILED_MESSAGE());
        }
        else
        {
            try
            {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear " + packageName);
                mView.get().showToast(R.string.successfullyClearedData, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingPresenter", "", "clearData", "");
            }

        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }

    @Override
    public void getSortTreasuryList() {
        mModel.getSortTreasuryList();
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        try
        {
            return mView.get().getAppContext();
        }
        catch (NullPointerException e)
        {
            return null;
        }
    }

    @Override
    public void onGetSetting(String printerPaperSizeTitle, String printerTypeTitle, String printType,String mapServiceType,String goodItemsCountType , String sortTreasuryList)
    {
        mView.get().onGetSetting(printerPaperSizeTitle, printerTypeTitle, printType,mapServiceType,goodItemsCountType , sortTreasuryList);
    }

    @Override
    public void onGetPaperSizesForPrint(ArrayList<String> arrayListTitles, ArrayList<String> arrayListValues)
    {
        if (arrayListTitles.size() > 0 && arrayListValues.size() > 0 && arrayListTitles.size() == arrayListValues.size())
        {
            mView.get().onGetPaperSizesForPrint(arrayListTitles, arrayListValues);
        }
        else
        {
            mView.get().showToast(R.string.errorGetDataTitle, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetPrinterTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListValues)
    {
        if (arrayListTitles.size() > 0 && arrayListValues.size() > 0 && arrayListTitles.size() == arrayListValues.size())
        {
            mView.get().onGetPrinterTypes(arrayListTitles, arrayListValues);
        }
        else
        {
            mView.get().showToast(R.string.errorGetDataTitle, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetPrintTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListValues)
    {
        if (arrayListTitles.size() > 0 && arrayListValues.size() > 0 && arrayListTitles.size() == arrayListValues.size())
        {
            mView.get().onGetPrintTypes(arrayListTitles, arrayListValues);
        }
        else
        {
            mView.get().showToast(R.string.errorGetDataTitle, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetMapServiceTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues) {
        if (arrayListTitles.size() > 0 && arrayListStringValues.size() > 0 && arrayListTitles.size() == arrayListStringValues.size())
        {
            mView.get().onGetMapServiceTypes(arrayListTitles, arrayListStringValues);
        }
        else
        {
            mView.get().showToast(R.string.errorGetDataTitle, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetShowGoodsItemNumber(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues,ArrayList<String> arrayListImages) {
        if (arrayListTitles.size() > 0 && arrayListStringValues.size() > 0 && arrayListTitles.size() == arrayListStringValues.size() && arrayListImages.size()>0)
        {
            mView.get().onGetShowGoodsItemNumber(arrayListTitles, arrayListStringValues,arrayListImages);
        }
        else
        {
            mView.get().showToast(R.string.errorGetDataTitle, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onFailedUpdatePrinterPaperSize()
    {
        mView.get().showToast(R.string.updateFailed, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onFailedUpdatePrinterType()
    {
        mView.get().showToast(R.string.updateFailed, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedUpdateMapServiceType() {
        mView.get().showToast(R.string.updateFailed, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

    }
    @Override
    public void onFailedUpdateSortTreasuryList() {
        mView.get().showToast(R.string.updateFailed, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

    }

    @Override
    public void onFailedUpdatePrintType()
    {
        mView.get().showToast(R.string.updateFailed, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedUpdateGoodsShowNumber() {
        mView.get().showToast(R.string.updateFailed, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

    }

    @Override
    public void onGetSortTreasuryList(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues) {
        mView.get().onGetSortTreasuryList(arrayListTitles , arrayListStringValues);
    }
}
