package com.saphamrah.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bxl.config.editor.BXLConfigLoader;

import java.nio.ByteBuffer;

import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.config.JposEntry;

public class BixolonPrinter extends Printer
{

    private static BXLConfigLoader bxlConfigLoader;
    private static POSPrinter posPrinter;
    private Activity activity;


    //----------------------bixolon------------------------

    private static final String DEVICE_ADDRESS_START = " (";
    private static final String DEVICE_ADDRESS_END = ")";
    private static String logicalName = "";


    public BixolonPrinter(Activity activity, long ccDarkhastFaktor)
    {
        super(ccDarkhastFaktor);
        this.activity = activity;
        bxlConfigLoader = new BXLConfigLoader(activity);
        try
        {
            bxlConfigLoader.openFile();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            bxlConfigLoader.newFile();
        }
        posPrinter = new POSPrinter(activity);
    }


    @Override
    public String getPrinterStateMessage()
    {
        return "";
    }

    public String setPrinter(Intent data)
    {
        try
        {
            String device = data.getExtras().getString("EXTRA_DEVICE_NAME") + DEVICE_ADDRESS_START + data.getExtras().getString("EXTRA_DEVICE_ADDRESS") + DEVICE_ADDRESS_END;
            String name = device.substring(0, device.indexOf(DEVICE_ADDRESS_START));
            String address = device.substring(device.indexOf(DEVICE_ADDRESS_START) + DEVICE_ADDRESS_START.length(), device.indexOf(DEVICE_ADDRESS_END));
            for (Object entry : bxlConfigLoader.getEntries())
            {
                JposEntry jposEntry = (JposEntry) entry;
                bxlConfigLoader.removeEntry(jposEntry.getLogicalName());
            }

            logicalName = setProductName(name);
            bxlConfigLoader.addEntry(logicalName, BXLConfigLoader.DEVICE_CATEGORY_POS_PRINTER, logicalName, BXLConfigLoader.DEVICE_BUS_BLUETOOTH, address);

            bxlConfigLoader.saveFile();
            return logicalName;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }


    private static String setProductName(String name)
    {
        String productName = BXLConfigLoader.PRODUCT_NAME_SPP_R200II;

        if ((name.indexOf("SPP-R200II") >= 0))
        {
            if (name.length() > 10)
            {
                if (name.substring(10, 11).equals("I"))
                {
                    productName = BXLConfigLoader.PRODUCT_NAME_SPP_R200III;
                }
            }
        }
        else if ((name.indexOf("SPP-R210") >= 0))
        {
            productName = BXLConfigLoader.PRODUCT_NAME_SPP_R210;
        }
        else if ((name.indexOf("SPP-R310") >= 0))
        {
            productName = BXLConfigLoader.PRODUCT_NAME_SPP_R310;
        }
        else if ((name.indexOf("SPP-R300") >= 0))
        {
            productName = BXLConfigLoader.PRODUCT_NAME_SPP_R300;
        }
        else if ((name.indexOf("SPP-R400") >= 0))
        {
            productName = BXLConfigLoader.PRODUCT_NAME_SPP_R400;
        }

        return productName;
    }

    private Bitmap preparePrint(String filePath)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public void print(String filePath)
    {
        Bitmap bitmap = preparePrint(filePath);
        try
        {
            posPrinter = new POSPrinter(activity);
            posPrinter.open(logicalName);
            posPrinter.claim(0);
            posPrinter.setDeviceEnabled(true);

            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.put((byte) POSPrinterConst.PTR_S_RECEIPT);
            buffer.put((byte) brightness);
            buffer.put((byte) compress);
            buffer.put((byte) 0x00);

            posPrinter.printBitmap(buffer.getInt(0), bitmap, posPrinter.getRecLineWidth(), POSPrinterConst.PTR_BM_LEFT);

            closePrinter();
        }
        catch (JposException e)
        {
            e.printStackTrace();
        }
    }


    private void closePrinter()
    {
        try
        {
            posPrinter.close();
        }
        catch (JposException e)
        {
            e.printStackTrace();
            //Toast.makeText(PrintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
