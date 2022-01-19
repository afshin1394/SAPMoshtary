package com.saphamrah.Utils;

import android.app.Activity;
import android.content.Intent;
import android.device.PrinterManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.saphamrah.R;

public class UrovoPrinter extends Printer
{

    private Activity activity;
    private static PrinterManager mPrinterManager;


    public UrovoPrinter(Activity activity , long ccDarkhastFaktor)
    {
        super(ccDarkhastFaktor);
        this.activity = activity;
        this.mPrinterManager = new PrinterManager();
    }


    @Override
    public String setPrinter(Intent data)
    {
        return "";
    }

    @Override
    public void print(String filePath)
    {
        mPrinterManager.setupPage(384, -1);
        mPrinterManager.clearPage();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDensity = activity.getResources().getDisplayMetrics().densityDpi;
        options.inTargetDensity = activity.getResources().getDisplayMetrics().densityDpi;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        mPrinterManager.drawBitmap(bitmap, 0, 20);
        int ret = mPrinterManager.printPage(0);
        mPrinterManager.paperFeed(15);
        mPrinterManager.prn_setBlack(100);

        if (bitmap != null)
        {
            bitmap.recycle();
            bitmap = null;
        }

        mPrinterManager.close();
    }

    @Override
    public  boolean checkIsAvailable() {

       try{
           mPrinterManager.open();
           return true;
       }catch (Exception e){
           return false;
       }
    }


    public int getPrintState()
    {
        return mPrinterManager.getStatus();
    }
    @Override
    public String getPrinterStateMessage()
    {
        switch (getPrintState())
        {
            case 0:
                return activity.getResources().getString(R.string.printerStatusOK);
            case -1:
                return activity.getResources().getString(R.string.printerStatusEmptyPaper);
            case 2:
                return activity.getResources().getString(R.string.printerStatusHighTemperature);
            case -3:
                return activity.getResources().getString(R.string.printerStatusLowBattery);
        }
        return "";
    }

}
