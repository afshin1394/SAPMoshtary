package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.SettingMVP;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.Model.SystemConfigTabletModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import org.json.JSONObject;

import java.util.ArrayList;

public class SettingModel implements SettingMVP.ModelOps
{

    private SettingMVP.RequiredPresenterOps mPresenter;


    public SettingModel(SettingMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getSetting()
    {
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(mPresenter.getAppContext());
        ArrayList<SystemConfigTabletModel> systemConfigTabletModels = systemConfigTabletDAO.getAll();
        if (systemConfigTabletModels.size() > 0)
        {
            String[] arrayPaperSize = mPresenter.getAppContext().getResources().getStringArray(R.array.printerPaperSizeItems);
            String[] arrayPrinterType = mPresenter.getAppContext().getResources().getStringArray(R.array.printerTypeItems);
            String[] arrayPrintType = mPresenter.getAppContext().getResources().getStringArray(R.array.printTypeItems);
            String[] arrayMapServiceType=mPresenter.getAppContext().getResources().getStringArray(R.array.mapServiceTypeItems);
            String[] arrayGoodsNumberType=mPresenter.getAppContext().getResources().getStringArray(R.array.GoodsNumberItems);
            String printerPaperSize = "";
            String printerType = "";
            String printType = "";
            String mapServiceType = "";
            String goodsNumber="";

            try
            {
                for (String str : arrayPaperSize)
                {
                    JSONObject jsonObject = new JSONObject(str);
                    if (jsonObject.getString("value").trim().equals(String.valueOf(systemConfigTabletModels.get(0).getSizePrint())))
                    {
                        printerPaperSize = jsonObject.getString("title");
                        break;
                    }
                }

                for (String str : arrayPrinterType)
                {
                    JSONObject jsonObject = new JSONObject(str);
                    if (jsonObject.getString("value").trim().equals(String.valueOf(systemConfigTabletModels.get(0).getNamePrinter())))
                    {
                        printerType = jsonObject.getString("title");
                        break;
                    }
                }

                for (String str : arrayPrintType)
                {
                    JSONObject jsonObject = new JSONObject(str);
                    if (jsonObject.getString("value").trim().equals(String.valueOf(systemConfigTabletModels.get(0).getNoeFaktorPrint())))
                    {
                        printType = jsonObject.getString("title");
                        break;
                    }
                }

                for (String str :arrayMapServiceType)
                {
                    JSONObject jsonObject=new JSONObject(str);
                    if (jsonObject.getString("value").trim().equals(String.valueOf(systemConfigTabletModels.get(0).getNoeNaghshe())))
                    {
                        mapServiceType=jsonObject.getString("title");
                        break;
                    }
                }

                for (String str :arrayGoodsNumberType)
                {

                    JSONObject jsonObject=new JSONObject(str);
                    Log.i("comparisoninfind", "getSetting: "+jsonObject.getString("value").trim()+" "+String.valueOf(systemConfigTabletModels.get(0).getGoodsShowNumberEachPage()));

                    if (jsonObject.getString("value").trim().equals(String.valueOf(systemConfigTabletModels.get(0).getGoodsShowNumberEachPage())))
                    {
                        goodsNumber=jsonObject.getString("title");
                        break;
                    }
                }
                mPresenter.onGetSetting(printerPaperSize, printerType, printType,mapServiceType,goodsNumber);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingModel", "SettingActivity", "getSetting", "");
            }
        }
    }

    @Override
    public void getPaperSizesForPrint()
    {
        ArrayList<String> arrayListPaperSizeTitle = new ArrayList<>();
        ArrayList<String> arrayListPaperSizeValue = new ArrayList<>();
        String[] arrayPaperSize = mPresenter.getAppContext().getResources().getStringArray(R.array.printerPaperSizeItems);
        for (String str : arrayPaperSize)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(str);
                arrayListPaperSizeTitle.add(jsonObject.getString("title"));
                arrayListPaperSizeValue.add(jsonObject.getString("value"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingModel", "SettingActivity", "getPaperSizesForPrint", "");
            }
        }
        mPresenter.onGetPaperSizesForPrint(arrayListPaperSizeTitle , arrayListPaperSizeValue);
    }

    @Override
    public void getPrinterTypes()
    {
        ArrayList<String> arrayListTitle = new ArrayList<>();
        ArrayList<String> arrayListValue = new ArrayList<>();
        String[] arrayPrinterTypes = mPresenter.getAppContext().getResources().getStringArray(R.array.printerTypeItems);
        for (String str : arrayPrinterTypes)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(str);
                arrayListTitle.add(jsonObject.getString("title"));
                arrayListValue.add(jsonObject.getString("value"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingModel", "SettingActivity", "getPaperSizesForPrint", "");
            }
        }
        mPresenter.onGetPrinterTypes(arrayListTitle , arrayListValue);
    }

    @Override
    public void getMapServiceTypes() {
        ArrayList<String> arrayListTitle = new ArrayList<>();
        ArrayList<String> arrayListValue = new ArrayList<>();
        String[] arrayPrinterTypes = mPresenter.getAppContext().getResources().getStringArray(R.array.mapServiceTypeItems);
        for (String str : arrayPrinterTypes)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(str);
                arrayListTitle.add(jsonObject.getString("title"));
                arrayListValue.add(jsonObject.getString("value"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingModel", "SettingActivity", "getPaperSizesForPrint", "");
            }
        }
        mPresenter.onGetMapServiceTypes(arrayListTitle , arrayListValue);
    }

    @Override
    public void getPrintTypes()
    {
        ArrayList<String> arrayListTitle = new ArrayList<>();
        ArrayList<String> arrayListValue = new ArrayList<>();
        String[] arrayPrintTypes = mPresenter.getAppContext().getResources().getStringArray(R.array.printTypeItems);
        for (String str : arrayPrintTypes)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(str);
                arrayListTitle.add(jsonObject.getString("title"));
                arrayListValue.add(jsonObject.getString("value"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingModel", "SettingActivity", "getPrintTypes", "");
            }
        }
        mPresenter.onGetPrintTypes(arrayListTitle , arrayListValue);
    }

    @Override
    public void getGoodsShowItemNumber() {
        ArrayList<String> arrayListTitle = new ArrayList<>();
        ArrayList<String> arrayListValue = new ArrayList<>();
        ArrayList<String> arrayListImages= new ArrayList<>();
        String[] arrayGoodsShowNumberItems = mPresenter.getAppContext().getResources().getStringArray(R.array.GoodsNumberItems);



        for (String str : arrayGoodsShowNumberItems)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(str);
                arrayListTitle.add(jsonObject.getString("title"));
                arrayListValue.add(jsonObject.getString("value"));
                arrayListImages.add(jsonObject.getString("image"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SettingModel", "SettingActivity", "getPrintTypes", "");
            }
        }
        mPresenter.onGetShowGoodsItemNumber(arrayListTitle , arrayListValue,arrayListImages);
    }

    @Override
    public void savePrinterPaperSize(int paperSizeValue)
    {
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(mPresenter.getAppContext());
        if (!systemConfigTabletDAO.updatePrinterPaperSize(paperSizeValue))
        {
            mPresenter.onFailedUpdatePrinterPaperSize();
        }
    }

    @Override
    public void savePrinterType(int printerTypeValue)
    {
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(mPresenter.getAppContext());
        if (!systemConfigTabletDAO.updatePrinterType(printerTypeValue))
        {
            mPresenter.onFailedUpdatePrinterType();
        }
    }

    @Override
    public void savePrintType(int printTypeValue)
    {
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(mPresenter.getAppContext());
        if (!systemConfigTabletDAO.updatePrintType(printTypeValue))
        {
            mPresenter.onFailedUpdatePrintType();
        }
    }

    @Override
    public void saveMapServiceType(int mapServiceTypeValue) {
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(mPresenter.getAppContext());
        if (!systemConfigTabletDAO.updateMapType(mapServiceTypeValue))
        {
            mPresenter.onFailedUpdateMapServiceType();
        }
    }

    @Override
    public void saveShowGoodsItemNumber(int showGoodsItemNumber) {
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(mPresenter.getAppContext());
        Log.i("showGoodsNumber", "saveShowGoodsItemNumber: "+systemConfigTabletDAO.updateGoodsShowItemNumber(showGoodsItemNumber));
        if (!systemConfigTabletDAO.updateGoodsShowItemNumber(showGoodsItemNumber))
        {
            mPresenter.onFailedUpdateGoodsShowNumber();
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger ();
        logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_RESPONSE_CONTENT_LENGTH(), message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }
}
